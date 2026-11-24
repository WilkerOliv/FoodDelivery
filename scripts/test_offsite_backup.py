"""Cópia externa cifrada: age real, PostgreSQL e Storage simulados; nenhum segredo ou banco real."""
import json
import os
from pathlib import Path
import shutil
import subprocess
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
SCRIPTS = ROOT / 'Backend/scripts'
SECRET_KEY = 'sb-secret-chave-de-teste-nao-real'
PASSWORD = 'senha-de-teste-nao-real'

PG_STUB = '''#!/usr/bin/env python3
import os, sys, json
from pathlib import Path
with open(os.environ['CALL_LOG'], 'a') as out:
    out.write(json.dumps([Path(sys.argv[0]).name] + sys.argv[1:]) + '\\n')
if '--list' in sys.argv:
    sys.exit(0)
if Path(sys.argv[0]).name == 'pg_dump':
    for arg in sys.argv:
        if arg.startswith('--file='):
            Path(arg.split('=', 1)[1]).write_bytes(b'DUMP-EM-TEXTO-CLARO-DE-TESTE')
'''

CURL_STUB = '''#!/usr/bin/env python3
import os, sys, json
from pathlib import Path
args = sys.argv[1:]
with open(os.environ['CALL_LOG'], 'a') as out:
    out.write(json.dumps(['curl'] + args) + '\\n')
url = next(a for a in args if a.startswith('https://'))
target = args[args.index('--output') + 1]
names = json.loads(os.environ['STORAGE_OBJECTS'])
if '/object/list/' in url:
    body = json.dumps([{'name': n, 'id': 'x'} for n in names] + [{'name': 'pasta', 'id': None}])
    Path(target).write_text(body)
else:
    Path(target).write_bytes(('CAPA:' + url.rsplit('/', 1)[1]).encode())
'''


class OffsiteBackupTest(unittest.TestCase):
    def setUp(self):
        self.tmp = tempfile.TemporaryDirectory()
        self.addCleanup(self.tmp.cleanup)
        self.base = Path(self.tmp.name)
        self.bin = self.base / 'bin'
        self.bin.mkdir()
        self.log = self.base / 'calls'
        for name, source in (('pg_dump', PG_STUB), ('pg_restore', PG_STUB), ('curl', CURL_STUB)):
            (self.bin / name).write_text(source)
            (self.bin / name).chmod(0o700)
        identity = self.base / 'identity.txt'
        generated = subprocess.run(['age-keygen', '-o', str(identity)], capture_output=True, text=True, check=True)
        self.recipient = generated.stderr.split('Public key:')[1].strip()
        self.identity = identity
        self.config = self.base / 'private.env'
        self.config.write_text(f'DB_PORT=5432\nDATABASE_USERNAME=fooddelivery_app\nDATABASE_PASSWORD={PASSWORD}\nDATABASE_NAME=postgres\n')
        self.out = self.base / 'offsite'
        self.env = {**os.environ, 'PATH': str(self.bin) + os.pathsep + os.environ['PATH'],
                    'PRIVATE_ENV_FILE': str(self.config), 'CALL_LOG': str(self.log),
                    'BACKUP_AGE_RECIPIENT': self.recipient, 'OFFSITE_DIR': str(self.out),
                    'SUPABASE_PROJECT_URL': 'https://projeto-teste.supabase.co',
                    'SUPABASE_SECRET_KEY': SECRET_KEY,
                    'STORAGE_OBJECTS': json.dumps(['cover-11111111-1111-1111-1111-111111111111.png',
                                                    'cover-22222222-2222-2222-2222-222222222222.png']),
                    'AGE_IDENTITY_FILE': str(self.identity), 'RESTORE_CONFIRM_DATABASE': 'postgres'}

    def run_script(self, name, *args, **extra):
        return subprocess.run(['bash', str(SCRIPTS / name), *map(str, args)], cwd=self.base,
                              env={**self.env, **extra}, capture_output=True, text=True)

    def backup(self, **extra):
        result = self.run_script('backup-offsite.sh', **extra)
        self.assertEqual(result.returncode, 0, result.stderr)
        return next(self.out.glob('*.tar.age')), result

    def test_output_is_encrypted_private_and_secrets_stay_out_of_logs(self):
        archive, result = self.backup()
        self.assertEqual(archive.stat().st_mode & 0o777, 0o600)
        raw = archive.read_bytes()
        self.assertNotIn(b'DUMP-EM-TEXTO-CLARO', raw)
        self.assertNotIn(b'CAPA:', raw)
        self.assertTrue(raw.startswith(b'age-encryption.org/'))
        visible = result.stdout + result.stderr + self.log.read_text()
        self.assertNotIn(PASSWORD, visible)
        self.assertNotIn(SECRET_KEY, visible)
        self.assertEqual(self.out.stat().st_mode & 0o777, 0o700)

    def test_dump_is_limited_to_the_private_schema_and_storage_uses_header_file(self):
        self.backup()
        calls = [json.loads(line) for line in self.log.read_text().splitlines()]
        dump = next(c for c in calls if c[0] == 'pg_dump')
        self.assertIn('--schema=fooddelivery', dump)
        curl = [c for c in calls if c[0] == 'curl']
        self.assertEqual(len(curl), 3)  # listagem + duas capas; a pasta sem id é ignorada
        for call in curl:
            self.assertTrue(any(a.startswith('@') for a in call))
            self.assertIn('--proto', call)

    def test_round_trip_restores_database_and_files_and_reports_time(self):
        archive, _ = self.backup()
        files = self.base / 'capas'
        self.log.unlink()
        result = self.run_script('restore-offsite.sh', archive, RESTORE_FILES_DIR=str(files))
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertIn('--single-transaction', self.log.read_text())
        self.assertEqual(sorted(p.name for p in files.iterdir()),
                         ['cover-11111111-1111-1111-1111-111111111111.png', 'cover-22222222-2222-2222-2222-222222222222.png'])
        self.assertIn('RTO', result.stdout)

    def test_tampered_or_wrong_key_blocks_before_touching_the_database(self):
        archive, _ = self.backup()
        self.log.unlink()
        wrong = self.base / 'outra.txt'
        subprocess.run(['age-keygen', '-o', str(wrong)], capture_output=True, check=True)
        self.assertNotEqual(self.run_script('restore-offsite.sh', archive, AGE_IDENTITY_FILE=str(wrong)).returncode, 0)
        with open(archive, 'r+b') as handle:
            handle.seek(-5, 2)
            handle.write(b'XXXXX')
        result = self.run_script('restore-offsite.sh', archive)
        self.assertNotEqual(result.returncode, 0)
        self.assertIn('checksum', result.stderr.lower())
        self.assertFalse(self.log.exists() and 'pg_restore' in self.log.read_text())

    def test_wrong_database_confirmation_blocks_restore(self):
        archive, _ = self.backup()
        result = self.run_script('restore-offsite.sh', archive, RESTORE_CONFIRM_DATABASE='outro')
        self.assertNotEqual(result.returncode, 0)

    def test_invalid_inputs_are_refused(self):
        self.assertEqual(self.run_script('backup-offsite.sh', BACKUP_AGE_RECIPIENT='nao-e-chave').returncode, 2)
        bad = json.dumps(['../escape.png'])
        result = self.run_script('backup-offsite.sh', STORAGE_OBJECTS=bad)
        self.assertEqual(result.returncode, 3)
        self.assertFalse(list(self.out.glob('*.tar.age')))
        self.assertEqual(self.run_script('backup-offsite.sh', SUPABASE_PROJECT_URL='http://inseguro.example.test').returncode, 2)
        self.assertEqual(self.run_script('backup-offsite.sh', BACKUP_SCHEMA='x; drop').returncode, 2)

    def test_malicious_archive_paths_are_rejected(self):
        evil = self.base / 'evil'
        (evil / 'db').mkdir(parents=True)
        (evil / 'manifest.json').write_text('{"dump":"a.dump"}')
        tar = self.base / 'evil.tar'
        subprocess.run(['tar', '--create', '--file', str(tar), '--directory', str(evil),
                        '--transform', 's,^\\./manifest.json,../manifest.json,', '.'], check=True)
        archive = self.base / 'evil.tar.age'
        subprocess.run(['age', '--recipient', self.recipient, '--output', str(archive), str(tar)], check=True)
        sidecar = Path(str(archive) + '.sha256')
        digest = subprocess.run(['sha256sum', str(archive)], capture_output=True, text=True, check=True).stdout.split()[0]
        sidecar.write_text(digest + '  evil.tar.age\n')
        result = self.run_script('restore-offsite.sh', archive)
        self.assertNotEqual(result.returncode, 0)
        self.assertIn('inseguros', result.stderr)

    def test_links_and_large_unsafe_archives_are_rejected_before_restore(self):
        import tarfile
        import hashlib
        for kind in ('symlink', 'absolute', 'traversal'):
            with self.subTest(kind=kind):
                tar = self.base / (kind + '.tar')
                with tarfile.open(tar, 'w') as output:
                    bad = tarfile.TarInfo('/escape' if kind == 'absolute' else '../escape' if kind == 'traversal' else 'link')
                    if kind == 'symlink':
                        bad.type = tarfile.SYMTYPE
                        bad.linkname = '/tmp/escape'
                    output.addfile(bad)
                    for index in range(2500):
                        output.addfile(tarfile.TarInfo('storage/file-' + str(index)))
                archive = self.base / (kind + '.tar.age')
                subprocess.run(['age', '--recipient', self.recipient, '--output', str(archive), str(tar)], check=True)
                Path(str(archive) + '.sha256').write_text(hashlib.sha256(archive.read_bytes()).hexdigest() + '  archive\n')
                result = self.run_script('restore-offsite.sh', archive)
                self.assertEqual(result.returncode, 2)
                self.assertIn('bloqueada', result.stderr)
                self.assertFalse(self.log.exists() and 'pg_restore' in self.log.read_text())


if __name__ == '__main__':
    unittest.main()
