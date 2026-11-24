#!/usr/bin/env python3
"""Same-origin Render pilot: real PostgreSQL/storage, no demo seed or real charges."""
import base64
import os
from pathlib import Path
import signal
import subprocess
import time
from urllib.parse import parse_qs, urlsplit


def configuration(source):
    env = dict(source)
    origin = env.get('RENDER_EXTERNAL_URL', '')
    parsed = urlsplit(origin)
    if (parsed.scheme != 'https' or not parsed.hostname or parsed.username or parsed.password
            or parsed.path or parsed.query or parsed.fragment):
        raise ValueError('Configure a origem HTTPS pública em RENDER_EXTERNAL_URL.')
    url = env.get('DATABASE_URL', '')
    database = urlsplit(url.removeprefix('jdbc:'))
    options = parse_qs(database.query)
    ca = options.get('sslrootcert', [''])[0]
    if (not url.startswith('jdbc:postgresql://') or not database.hostname
            or database.username or database.password or database.path != '/postgres'
            or options.get('sslmode') != ['verify-full'] or not ca
            or not Path(ca).is_file()):
        raise ValueError('Configure PostgreSQL com TLS verify-full e certificado CA legível.')
    if env.get('DATABASE_SCHEMA') != 'fooddelivery':
        raise ValueError('Este piloto exige o schema privado fooddelivery.')
    runtime_user = env.get('DATABASE_USERNAME', '')
    role, _, pooler = runtime_user.partition('.')
    if role == 'fooddelivery_runtime':
        owner = 'fooddelivery_app' + ('.' + pooler if pooler else '')
        if (env.get('SPRING_FLYWAY_USER') != owner
                or len(env.get('SPRING_FLYWAY_PASSWORD', '')) < 24
                or env['SPRING_FLYWAY_PASSWORD'] == env.get('DATABASE_PASSWORD')):
            raise ValueError('Configure a credencial distinta de migração para o Flyway.')
    elif role != 'fooddelivery_app':
        raise ValueError('Use uma conta restrita do projeto para o banco.')
    if len(env.get('DATABASE_PASSWORD', '')) < 24:
        raise ValueError('Configure a senha privada do banco com no mínimo 24 caracteres.')
    admin_email = env.get('APP_ADMIN_EMAIL', '').strip()
    admin_password = env.get('APP_ADMIN_PASSWORD', '')
    if (admin_email or admin_password) and (not admin_email or len(admin_password) < 16):
        raise ValueError('Configure APP_ADMIN_EMAIL e APP_ADMIN_PASSWORD (mínimo 16 caracteres) juntos.')
    for key in ('JWT_SECRET', 'PRIVATE_CONFIG_ENCRYPTION_KEY'):
        try:
            decoded = base64.b64decode(env.get(key, ''), validate=True)
        except ValueError:
            decoded = b''
        if len(decoded) != 32:
            raise ValueError(f'{key} deve conter 32 bytes aleatórios em Base64.')
    if (env.get('STORE_ASSETS_PROVIDER') != 'supabase'
            or not env.get('SUPABASE_PROJECT_URL')
            or not (env.get('SUPABASE_SECRET_KEY') or env.get('SUPABASE_SERVICE_ROLE_KEY'))):
        raise ValueError('Configure o armazenamento privado Supabase antes de publicar.')
    if env.get('SPRING_PROFILES_ACTIVE', 'postgres') != 'postgres':
        raise ValueError('Este inicializador usa apenas postgres; demonstração e produção são separados.')
    env.update(SPRING_PROFILES_ACTIVE='postgres', AUTH_COOKIE_SECURE='true',
               FOODDELIVERY_ALLOWED_ORIGINS=origin, FRONTEND_URL=origin,
               MP_SANDBOX='true', MP_TEST_WEBHOOK_SIGNATURE='false',
               MP_REDIRECT_URI=origin + '/api/v1/payments/mercadopago/callback',
               MP_NOTIFICATION_BASE_URL=origin,
               SERVER_ADDRESS='127.0.0.1', SERVER_PORT='8181',
               APP_SECURITY_CLIENT_ADDRESS_HEADER='X-Fooddelivery-Client-IP',
               MANAGEMENT_ENDPOINT_HEALTH_GROUP_READINESS_INCLUDE='readinessState,db',
               MANAGEMENT_ENDPOINT_HEALTH_SHOW_DETAILS='never')
    return env


def main():
    env = configuration(os.environ)
    processes = []
    def stop(*_):
        raise KeyboardInterrupt
    signal.signal(signal.SIGTERM, stop)
    try:
        # Proxy needs no database, storage or signing credentials.
        proxy_env = {key: env[key] for key in ('PATH', 'PORT') if key in env}
        # Its runtime files must stay writable without granting access to /app.
        proxy_env.update(XDG_CONFIG_HOME='/tmp/fooddelivery-caddy/config',
                         XDG_DATA_HOME='/tmp/fooddelivery-caddy/data')
        processes.append(subprocess.Popen(['caddy', 'run', '--config', '/app/Caddyfile',
                                          '--adapter', 'caddyfile'], env=proxy_env))
        processes.append(subprocess.Popen(['java', '-Xms64m', '-Xmx256m',
            '-XX:MaxMetaspaceSize=128m', '-jar', '/app/api.jar'], env=env))
        while True:
            for process in processes:
                if process.poll() is not None:
                    raise RuntimeError('Um processo do serviço encerrou; reinicialização necessária.')
            time.sleep(0.5)
    except KeyboardInterrupt:
        pass
    finally:
        for process in processes:
            if process.poll() is None:
                process.terminate()
        deadline = time.monotonic() + 25
        for process in processes:
            try:
                process.wait(timeout=max(0.1, deadline - time.monotonic()))
            except subprocess.TimeoutExpired:
                process.kill()
                process.wait()


if __name__ == '__main__':
    main()
