"""Backup CLI regression tests; fake PostgreSQL commands, no user DB or .env."""
import hashlib
import os
from pathlib import Path
import shutil
import subprocess
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]

class BackupScriptsTest(unittest.TestCase):
    def setUp(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.addCleanup(self.tmp.cleanup)
        self.base = Path(self.tmp.name)
        self.bin = self.base / 'bin'
        self.bin.mkdir()
        self.log = self.base / 'calls'
        for name in ('pg_dump', 'pg_restore'):
            script = self.bin / name
            script.write_text('''#!/usr/bin/env python3
import os,sys,json
from pathlib import Path
with open(os.environ['CALL_LOG'],'a') as out:out.write(json.dumps([Path(sys.argv[0]).name]+sys.argv[1:])+'\\n')
if '--list' in sys.argv:sys.exit(0)
if Path(sys.argv[0]).name=='pg_dump':
 for arg in sys.argv:
  if arg.startswith('--file='):Path(arg.split('=',1)[1]).write_bytes(b'fake-custom-dump')
 if os.environ.get('FAIL_DUMP'):sys.exit(1)
if os.environ.get('FAIL_RESTORE'):sys.exit(1)
''')
            script.chmod(0o700)
        self.config = self.base / 'private.env'
        self.config.write_text('DB_PORT=5432\nDATABASE_USERNAME=test\nDATABASE_PASSWORD=not-real\nDATABASE_NAME=fixture\n')
        self.env = {**os.environ, 'PATH':str(self.bin)+os.pathsep+os.environ['PATH'],
                    'PRIVATE_ENV_FILE':str(self.config),'BACKUP_DIR':str(self.base/'backups'),
                    'CALL_LOG':str(self.log),'RESTORE_CONFIRM_DATABASE':'fixture'}
        for name in ('FAIL_DUMP','FAIL_RESTORE'):self.env.pop(name,None)

    def run_script(self, name, *args):
        return subprocess.run(['bash',str(ROOT/'Backend/scripts'/name),*map(str,args)],
                              cwd=self.base,env=self.env,capture_output=True,text=True)

    def backup(self):
        result=self.run_script('backup-db.sh')
        self.assertEqual(result.returncode,0,result.stderr)
        return next((self.base/'backups').glob('*.dump'))

    def test_private_files_and_relative_checksum(self):
        dump=self.backup()
        self.assertEqual(dump.stat().st_mode & 0o777,0o600)
        sidecar=Path(str(dump)+'.sha256')
        self.assertEqual(sidecar.stat().st_mode & 0o777,0o600)
        self.assertNotIn(str(self.base),sidecar.read_text())
        self.assertNotIn('not-real',self.log.read_text())

    def test_moved_backup_restores_in_one_transaction(self):
        dump=self.backup();target=self.base/'moved.dump'
        shutil.move(dump,target);shutil.move(str(dump)+'.sha256',str(target)+'.sha256')
        result=self.run_script('restore-db.sh',target)
        self.assertEqual(result.returncode,0,result.stderr)
        self.assertIn('--single-transaction',self.log.read_text())

    def test_checksum_cannot_validate_a_different_file(self):
        dump=self.backup();good=self.base/'good.dump';shutil.copyfile(dump,good)
        Path(str(dump)+'.sha256').write_text(hashlib.sha256(good.read_bytes()).hexdigest()+'  '+str(good)+'\n')
        dump.write_bytes(b'corrupted')
        self.log.unlink()
        self.assertNotEqual(self.run_script('restore-db.sh',dump).returncode,0)
        self.assertFalse(self.log.exists())

    def test_wrong_database_confirmation_blocks_mutation(self):
        dump=self.backup();self.env['RESTORE_CONFIRM_DATABASE']='another'
        self.assertNotEqual(self.run_script('restore-db.sh',dump).returncode,0)
        self.assertNotIn('--clean',self.log.read_text())

    def test_connection_string_database_rejected(self):
        self.config.write_text(self.config.read_text().replace('DATABASE_NAME=fixture',"DATABASE_NAME='host=elsewhere dbname=other'"))
        self.assertNotEqual(self.run_script('backup-db.sh').returncode,0)
        self.assertFalse(self.log.exists())

    def test_failed_dump_is_not_published(self):
        self.env['FAIL_DUMP']='1'
        self.assertNotEqual(self.run_script('backup-db.sh').returncode,0)
        self.assertEqual(list((self.base/'backups').iterdir()),[])

    def test_repeated_backups_have_unique_names(self):
        self.backup();self.backup()
        self.assertEqual(len(list((self.base/'backups').glob('*.dump'))),2)

    def test_multiline_checksum_rejected(self):
        dump=self.backup();sidecar=Path(str(dump)+'.sha256')
        sidecar.write_text(sidecar.read_text()*2)
        self.log.unlink()
        self.assertNotEqual(self.run_script('restore-db.sh',dump).returncode,0)
        self.assertFalse(self.log.exists())

    def test_restore_failure_does_not_report_success(self):
        dump=self.backup();self.env['FAIL_RESTORE']='1'
        result=self.run_script('restore-db.sh',dump)
        self.assertNotEqual(result.returncode,0)
        self.assertNotIn('Recuperação concluída',result.stdout)

if __name__=='__main__':unittest.main()
