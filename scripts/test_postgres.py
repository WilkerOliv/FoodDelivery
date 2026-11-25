#!/usr/bin/env python3
"""Disposable PostgreSQL concurrency/restore drill. Never reads Backend/.env."""
import os
import sys
import pathlib
import secrets
import subprocess
import tempfile
import time

ROOT = pathlib.Path(__file__).resolve().parents[1]

def run(*args, **kwargs):
    return subprocess.run(args, check=True, text=True, **kwargs)

def main():
    private_schema = '--private-schema' in sys.argv
    name = 'fooddelivery-qa-' + secrets.token_hex(6)
    password = secrets.token_urlsafe(32)
    env = {k: v for k, v in os.environ.items()
           if k in ['PATH', 'HOME', 'JAVA_HOME', 'MAVEN_HOME', 'LANG', 'LC_ALL', 'TMPDIR']}
    with tempfile.TemporaryDirectory(prefix='fooddelivery-restore-') as directory:
        config = pathlib.Path(directory) / 'private.env'
        config.write_text('DB_HOST=127.0.0.1\nDB_PORT=5432\nDATABASE_NAME=fooddelivery\n'
                          'DATABASE_USERNAME=fooddelivery_app\nDATABASE_PASSWORD=' + password +
                          '\nBACKUP_DIR=/drill/backups\n')
        config.chmod(0o600)
        def sql(db, statement, user='fooddelivery_admin'):
            return run('docker', 'exec', '-e', 'PGOPTIONS=-c search_path=' + ('fooddelivery' if private_schema else 'public'), name, 'psql', '-v', 'ON_ERROR_STOP=1',
                       '-U', user, '-d', db, '-Atc', statement, capture_output=True).stdout.strip()
        def restore(check=True):
            result = subprocess.run(['docker', 'exec', '--user', f'{os.getuid()}:{os.getgid()}', '-e', 'PRIVATE_ENV_FILE=/drill/restore.env',
                                   '-e', 'RESTORE_CONFIRM_DATABASE=fooddelivery_restore_qa', name,
                                   'bash', '/project/Backend/scripts/restore-db.sh', '/drill/moved.dump'],
                                  check=False, text=True, capture_output=True)
            if check and result.returncode:
                raise RuntimeError('Restore failed: ' + result.stderr)
            return result
        try:
            run('docker', 'run', '--detach', '--name', name, '--publish', '127.0.0.1::5432',
                '--mount', f'type=bind,source={ROOT},target=/project,readonly',
                '--mount', f'type=bind,source={directory},target=/drill',
                '--mount', f"type=bind,source={ROOT}/deploy/postgres/{'supabase-private-schema.sql' if private_schema else '10-app-role.sql'},target=/docker-entrypoint-initdb.d/10-app-role.sql,readonly",
                '--env', 'POSTGRES_DB=' + ('fooddelivery' if private_schema else 'postgres'), '--env', 'POSTGRES_USER=fooddelivery_admin',
                '--env', 'POSTGRES_PASSWORD=' + secrets.token_urlsafe(32),
                '--env', 'APP_DATABASE_PASSWORD=' + password, '--env', 'DATABASE_PASSWORD=' + password, 'postgres:17-alpine', stdout=subprocess.DEVNULL)
            deadline = time.monotonic() + 60
            while time.monotonic() < deadline:
                # TCP probe cannot accidentally observe the temporary init socket server.
                probe = subprocess.run(['docker', 'exec', name, 'pg_isready', '-h', '127.0.0.1',
                                        '-U', 'fooddelivery_app', '-d', 'fooddelivery'],
                                       stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
                if probe.returncode == 0:
                    break
                time.sleep(1)
            else:
                raise RuntimeError('PostgreSQL descartável não ficou pronto.')
            flags = sql('postgres', "select rolsuper,rolcreatedb,rolcreaterole,rolreplication,rolbypassrls from pg_roles where rolname='fooddelivery_app'")
            assert flags == 'f|f|f|f|f', 'A aplicação recebeu privilégios administrativos.'
            port = run('docker', 'port', name, '5432/tcp', capture_output=True).stdout.strip().rsplit(':', 1)[1]
            if private_schema:
                env['DATABASE_SCHEMA'] = 'fooddelivery'
            env.update(QA_DATABASE_URL=f'jdbc:postgresql://127.0.0.1:{port}/fooddelivery',
                       QA_DATABASE_USERNAME='fooddelivery_app', QA_DATABASE_PASSWORD=password)
            run('mvn', '-f', str(ROOT/'Backend/pom.xml'), '-Dtest=PostgresConcurrencyIT,V9MigrationEquivalenceIT,PostgresRateWindowsIT', 'test', '-q', env=env)
            sql('fooddelivery', "create table restore_probe(value text); insert into restore_probe values('snapshot')", 'fooddelivery_app')
            run('docker', 'exec', '--user', f'{os.getuid()}:{os.getgid()}', '-e', 'PRIVATE_ENV_FILE=/drill/private.env', name,
                'bash', '/project/Backend/scripts/backup-db.sh')
            archive = next((pathlib.Path(directory)/'backups').glob('*.dump'))
            archive.rename(pathlib.Path(directory)/'moved.dump')
            pathlib.Path(str(archive)+'.sha256').rename(pathlib.Path(directory)/'moved.dump.sha256')
            (pathlib.Path(directory)/'restore.env').write_text(config.read_text().replace('DATABASE_NAME=fooddelivery\n', 'DATABASE_NAME=fooddelivery_restore_qa\n'))
            sql('postgres', 'create database fooddelivery_restore_qa owner fooddelivery_app')
            restore()
            counts = [sql(db, 'select (select count(*) from customer_orders),(select count(*) from reservations),(select count(*) from payments),(select count(*) from flyway_schema_history where success)')
                      for db in ['fooddelivery', 'fooddelivery_restore_qa']]
            assert counts[0] == counts[1], 'Restauração divergiu do banco de origem.'
            # A dependent view blocks DROP, forcing a real failure within restore.
            sql('fooddelivery_restore_qa', "update restore_probe set value='target-preserved'; create view restore_guard as select * from restore_probe", 'fooddelivery_app')
            failed = restore(check=False)
            assert failed.returncode != 0, 'O cenário deveria rejeitar a restauração.'
            assert sql('fooddelivery_restore_qa', 'select value from restore_probe') == 'target-preserved'
            assert sql('fooddelivery_restore_qa', 'select count(*) from customer_orders') == counts[0].split('|')[0]
            sql('fooddelivery_restore_qa', 'drop view restore_guard', 'fooddelivery_app')
            restore()
            assert sql('fooddelivery_restore_qa', 'select value from restore_probe') == 'snapshot'
            print('APROVADO: usuário sem superpoderes, migrações, concorrência, backup movido, restauração e rollback real após falha.')
        finally:
            subprocess.run(['docker', 'rm', '--force', '--volumes', name], stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)

if __name__ == '__main__':
    main()
