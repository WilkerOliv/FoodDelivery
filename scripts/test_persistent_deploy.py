"""Disposable TLS PostgreSQL + production-shaped proxy; never reads private .env."""
import base64
import importlib.util
import json
import os
from pathlib import Path
import secrets
import subprocess
import tempfile
import time
import unittest
import urllib.error
import urllib.request

ROOT = Path(__file__).resolve().parents[1]
spec = importlib.util.spec_from_file_location('persistent', ROOT / 'deploy/persistente.py')
launcher = importlib.util.module_from_spec(spec)
spec.loader.exec_module(launcher)


def run(*args, **kwargs):
    return subprocess.run(args, check=True, text=True, **kwargs)


def environment(certificate):
    return dict(RENDER_EXTERNAL_URL='https://pilot.example.test',
        DATABASE_URL='jdbc:postgresql://qa-db:5432/postgres?sslmode=verify-full&sslrootcert=' + str(certificate),
        DATABASE_USERNAME='fooddelivery_app', DATABASE_PASSWORD=secrets.token_urlsafe(32),
        DATABASE_SCHEMA='fooddelivery', JWT_SECRET=base64.b64encode(secrets.token_bytes(32)).decode(),
        PRIVATE_CONFIG_ENCRYPTION_KEY=base64.b64encode(secrets.token_bytes(32)).decode(),
        STORE_ASSETS_PROVIDER='supabase', SUPABASE_PROJECT_URL='https://test-project.supabase.co',
        SUPABASE_SERVICE_ROLE_KEY='test-only-not-a-real-credential')


class ConfigurationTests(unittest.TestCase):
    def test_bootstrap_is_optional_but_requires_complete_credentials(self):
        with tempfile.NamedTemporaryFile() as ca:
            env = environment(ca.name)
            launcher.configuration(env)
            launcher.configuration({**env, 'APP_ADMIN_EMAIL': 'admin@example.test',
                                    'APP_ADMIN_PASSWORD': 'x' * 16})
            for extra in ({'APP_ADMIN_EMAIL': 'admin@example.test'},
                          {'APP_ADMIN_PASSWORD': 'x' * 16},
                          {'APP_ADMIN_EMAIL': 'admin@example.test', 'APP_ADMIN_PASSWORD': 'x' * 15}):
                with self.subTest(fields=tuple(extra)), self.assertRaises(ValueError):
                    launcher.configuration({**env, **extra})

    def test_runtime_role_uses_separate_migrator(self):
        with tempfile.NamedTemporaryFile() as ca:
            env = environment(ca.name)
            env.update(DATABASE_USERNAME='fooddelivery_runtime.project',
                       SPRING_FLYWAY_USER='fooddelivery_app.project',
                       SPRING_FLYWAY_PASSWORD=secrets.token_urlsafe(32))
            launcher.configuration(env)
            for override in ({'SPRING_FLYWAY_USER': 'fooddelivery_app.other'},
                             {'SPRING_FLYWAY_PASSWORD': ''},
                             {'SPRING_FLYWAY_PASSWORD': env['DATABASE_PASSWORD']}):
                with self.subTest(fields=tuple(override)), self.assertRaises(ValueError):
                    launcher.configuration({**env, **override})

    def test_requires_verified_tls_and_restricted_role(self):
        with tempfile.NamedTemporaryFile() as ca:
            env = environment(ca.name)
            configured = launcher.configuration(env)
            self.assertEqual(configured['MP_SANDBOX'], 'true')
            self.assertEqual(configured['AUTH_COOKIE_SECURE'], 'true')
            self.assertEqual(configured['APP_SECURITY_CLIENT_ADDRESS_HEADER'], 'X-Fooddelivery-Client-IP')
            for key, value in [('DATABASE_USERNAME', 'postgres'), ('DATABASE_SCHEMA', 'public'),
                    ('DATABASE_PASSWORD', 'short'), ('SPRING_PROFILES_ACTIVE', 'homologacao'),
                    ('JWT_SECRET', 'bad'), ('STORE_ASSETS_PROVIDER', 'local'),
                    ('RENDER_EXTERNAL_URL', 'http://localhost'),
                    ('DATABASE_URL', env['DATABASE_URL'].replace('verify-full', 'require')),
                    ('DATABASE_URL', env['DATABASE_URL'] + '&sslmode=disable')]:
                with self.subTest(key=key, value=value), self.assertRaises(ValueError):
                    launcher.configuration({**env, key: value})


def container_test():
    suffix = secrets.token_hex(5)
    network, db, app = ['fd-persist-' + kind + '-' + suffix for kind in ('net', 'db', 'app')]
    created = []
    with tempfile.TemporaryDirectory(prefix='fd-persistence-') as directory:
        folder = Path(directory)
        folder.chmod(0o755)  # postgres must traverse the mounted directory to read its CA.
        env = environment('/run/secrets/ca.crt')
        admin_password = secrets.token_urlsafe(24)
        env.update(APP_ADMIN_EMAIL='admin@persistent.example.test', APP_ADMIN_PASSWORD=admin_password,
                   APP_SECURITY_SHARED_RATE_LIMIT='true')
        envfile = folder / 'private.env'
        envfile.write_text(''.join(f'{k}={v}\n' for k, v in env.items()))
        envfile.chmod(0o600)
        run('openssl', 'req', '-x509', '-newkey', 'rsa:2048', '-nodes', '-days', '1',
            '-subj', '/CN=qa-db', '-addext', 'subjectAltName=DNS:qa-db',
            '-keyout', str(folder/'server.key'), '-out', str(folder/'ca.crt'),
            stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
        # The database copies the key to a postgres-owned 0600 file before starting.
        run('docker', 'network', 'create', network, stdout=subprocess.DEVNULL)
        try:
            run('docker', 'run', '-d', '--name', db, '--network', network, '--network-alias', 'qa-db',
                '-v', f'{folder}:/tls:ro', '-v', f'{ROOT}/deploy/postgres/supabase-private-schema.sql:/docker-entrypoint-initdb.d/10-app.sql:ro',
                '-e', 'POSTGRES_PASSWORD=' + secrets.token_urlsafe(32),
                '-e', 'APP_DATABASE_PASSWORD=' + env['DATABASE_PASSWORD'],
                '--entrypoint', 'sh', 'postgres:17-alpine', '-c',
                'cp /tls/server.key /tmp/server.key && chown postgres:postgres /tmp/server.key && chmod 600 /tmp/server.key && exec docker-entrypoint.sh postgres -c ssl=on -c ssl_cert_file=/tls/ca.crt -c ssl_key_file=/tmp/server.key',
                stdout=subprocess.DEVNULL)
            created.append(db)
            for _ in range(60):
                if subprocess.run(['docker', 'exec', db, 'pg_isready', '-h', '127.0.0.1', '-U', 'postgres'],
                                  stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL).returncode == 0:
                    break
                time.sleep(1)
            else:
                raise AssertionError('PostgreSQL não iniciou.')
            def start_app():
                run('docker', 'run', '-d', '--name', app, '--network', network, '--memory=512m', '--cpus=1',
                    '--cap-drop=ALL', '--security-opt', 'no-new-privileges',  # reproduz as restrições do Render
                    '-p', '127.0.0.1::10000', '--env-file', str(envfile),
                    '-v', f'{folder}/ca.crt:/run/secrets/ca.crt:ro', 'fooddelivery-persistent:qa', stdout=subprocess.DEVNULL)
                created.append(app)
            start_app()
            port = run('docker', 'port', app, '10000/tcp', capture_output=True).stdout.strip().rsplit(':', 1)[1]
            base = 'http://127.0.0.1:' + port
            def request(path, body=None, cookie=None, origin=env['RENDER_EXTERNAL_URL'], extra=None):
                headers = {'Content-Type': 'application/json', 'Origin': origin}
                if cookie:
                    headers['Cookie'] = cookie
                headers.update(extra or {})
                try:
                    return urllib.request.urlopen(urllib.request.Request(base + path,
                        data=json.dumps(body).encode() if body is not None else None, headers=headers), timeout=10)
                except urllib.error.HTTPError as error:
                    return error
            def ready():
                for _ in range(180):
                    if run('docker', 'inspect', '--format', '{{.State.Running}}', app, capture_output=True).stdout.strip() != 'true':
                        raise AssertionError('Container encerrou. Consulte os logs privados de teste.')
                    try:
                        with request('/healthz') as response:
                            if response.status == 200:
                                return
                    except OSError:
                        pass
                    time.sleep(1)
                raise AssertionError('O serviço não ficou pronto.')
            ready()
            startup_log = run('docker', 'logs', app, capture_output=True)
            assert 'permission denied' not in (startup_log.stdout + startup_log.stderr).lower()
            with request('/') as response:
                assert response.status == 200 and 'Content-Security-Policy' in response.headers
            for path in ('/demo.html', '/scripts/start.py', '/package.json', '/actuator/health'):
                with request(path) as response:
                    assert response.status == 404, (path, response.status)
            with request('/runtime-config.js') as response:
                assert response.read() == b'globalThis.FOODDELIVERY_DEMO=false;'
            with request('/api/v1/auth/login', {'email': env['APP_ADMIN_EMAIL'], 'password': admin_password}) as response:
                assert response.status == 200
                session = response.headers['Set-Cookie']
                assert 'Secure' in session and 'HttpOnly' in session
                cookie = session.split(';')[0]
            with request('/api/v1/account/me', cookie=cookie) as response:
                identity = json.load(response)
            with request('/api/v1/auth/logout', {}, cookie, 'https://attacker.example.test') as response:
                assert response.status == 403
            # Visitantes diferentes atrás do proxy não dividem o orçamento coletivo de login.
            # O X-Forwarded-For forjado à esquerda não pode trocar o bucket: o valor confiável é o da direita.
            statuses = []
            for number in range(101):
                with request('/api/v1/auth/login', {'email': f'inventado{number}@example.test', 'password': 'senha-incorreta-123'},
                             extra={'X-Forwarded-For': f'203.0.113.{number % 200 + 1}, 198.51.100.20'}) as response:
                    statuses.append(response.status)
            assert statuses[-1] == 429 and 429 not in statuses[:-1], statuses
            with request('/api/v1/auth/login', {'email': 'outra-pessoa@example.test', 'password': 'senha-incorreta-123'},
                         extra={'X-Forwarded-For': '198.51.100.21'}) as response:
                assert response.status == 401, response.status
            # Upgrade an already migrated database and redeploy with the runtime login.
            run('docker', 'rm', '-f', app, stdout=subprocess.DEVNULL)
            created.remove(app)
            runtime_password = secrets.token_urlsafe(32)
            provision = (ROOT / 'deploy/postgres/supabase-runtime-role.sql').read_text()
            run('docker', 'exec', '-i', db, 'psql', '-U', 'postgres', '-v', 'ON_ERROR_STOP=1',
                input=provision + "\nALTER ROLE fooddelivery_runtime PASSWORD '" + runtime_password + "';",
                stdout=subprocess.DEVNULL)
            privileges = run('docker', 'exec', db, 'psql', '-U', 'fooddelivery_runtime', '-d', 'postgres',
                '-Atc', """SELECT has_schema_privilege(current_user,'fooddelivery','CREATE'),
                                  has_table_privilege(current_user,'flyway_schema_history','INSERT'),
                                  has_table_privilege(current_user,'admin_audit_events','DELETE'),
                                  has_table_privilege(current_user,'admin_audit_events','INSERT'),
                                  has_table_privilege(current_user,'accounts','UPDATE')""",
                capture_output=True).stdout.strip()
            assert privileges == 'f|f|f|t|t', privileges
            env.update(SPRING_FLYWAY_USER=env['DATABASE_USERNAME'],
                       SPRING_FLYWAY_PASSWORD=env['DATABASE_PASSWORD'],
                       DATABASE_USERNAME='fooddelivery_runtime', DATABASE_PASSWORD=runtime_password)
            envfile.write_text(''.join(f'{k}={v}\n' for k, v in env.items()))
            start_app()
            port = run('docker', 'port', app, '10000/tcp', capture_output=True).stdout.strip().rsplit(':', 1)[1]
            base = 'http://127.0.0.1:' + port
            ready()
            with request('/api/v1/account/me', cookie=cookie) as response:
                assert response.status == 200 and json.load(response)['id'] == identity['id']
            with request('/api/v1/auth/login', {'email': 'admin@example.test', 'password': 'DemoLocal!2026#Teste'}) as response:
                assert response.status == 401
            # Database failure must remove readiness; recovery must restore it.
            run('docker', 'stop', db, stdout=subprocess.DEVNULL)
            try:
                with request('/healthz') as response:
                    assert response.status != 200
            except (OSError, TimeoutError):
                pass
            run('docker', 'start', db, stdout=subprocess.DEVNULL)
            ready()
            print('PASS: PostgreSQL TLS verificado, migrações privadas, proxy, cookie seguro, origem, ausência de seed demo, conta/sessão após reinício e recuperação após queda do banco.')
        except Exception:
            if app in created:
                log = run('docker', 'logs', app, capture_output=True)
                (ROOT / 'Backend' / 'persistent-test.log').write_text(log.stdout + log.stderr)
            raise
        finally:
            for name in reversed(created):
                run('docker', 'rm', '-f', '-v', name, stdout=subprocess.DEVNULL)
            run('docker', 'network', 'rm', network, stdout=subprocess.DEVNULL)


if __name__ == '__main__':
    import sys
    if '--container' in sys.argv:
        container_test()
    else:
        unittest.main()
