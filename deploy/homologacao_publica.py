#!/usr/bin/env python3
"""Temporary HTTPS preview behind a shared access password; never loads production .env."""
import base64
import hmac
import importlib.util
import os
from pathlib import Path
import secrets
import signal
import subprocess
import tempfile
import threading
import time
from urllib.parse import unquote, urlsplit
import urllib.error
import urllib.request
from http.server import ThreadingHTTPServer

ROOT = Path(__file__).resolve().parent
FRONTEND = ROOT / 'Frontend'
if not FRONTEND.exists():  # Source checkout, used by focused tests.
    FRONTEND = ROOT.parent / 'Frontend'
spec = importlib.util.spec_from_file_location('local_frontend', FRONTEND / 'scripts/start.py')
frontend = importlib.util.module_from_spec(spec)
spec.loader.exec_module(frontend)


def access_token(username, password):
    if not username or ':' in username or len(password) < 24:
        raise ValueError('Configure DEMO_ACCESS_USER e DEMO_ACCESS_PASSWORD (mínimo 24 caracteres).')
    return 'Basic ' + base64.b64encode(f'{username}:{password}'.encode()).decode()


class PreviewHandler(frontend.FrontendHandler):
    expected_authorization = ''

    def do_HEAD(self):
        self.send_error(405)

    def do_GET(self):
        # Health reveals only readiness, never credentials or business data.
        if self.path == '/healthz':
            self.send_response(200)
            self.end_headers()
            self.wfile.write(b'ok')
            return
        if not self.authorized():
            return
        path = unquote(urlsplit(self.path).path)
        if path == '/runtime-config.js':
            self.send_response(200)
            self.send_header('Content-Type', 'text/javascript')
            self.end_headers()
            self.wfile.write(b'globalThis.FOODDELIVERY_DEMO=true;')
            return
        if path.startswith('/api/v1/'):
            return self.proxy()
        parts = Path(path).parts
        if any(part.startswith('.') for part in parts) or any(part in ('scripts', 'tests', 'node_modules') for part in parts):
            self.send_error(404)
            return
        # No directory listings and no traversal outside the static frontend.
        target = (FRONTEND / path.lstrip('/')).resolve()
        if not target.is_relative_to(FRONTEND.resolve()) or (target.is_dir() and path != '/'):
            self.send_error(404)
            return
        super().do_GET()

    def authorized(self):
        if hmac.compare_digest(self.headers.get('Authorization', '').encode(), self.expected_authorization.encode()):
            return True
        self.send_response(401)
        self.send_header('WWW-Authenticate', 'Basic realm="FoodDelivery - somente testes", charset="UTF-8"')
        self.end_headers()
        return False

    def mutate(self):
        if not self.authorized():
            return
        if self.headers.get('Transfer-Encoding'):
            self.send_error(400)
            return
        try:
            length = int(self.headers.get('Content-Length', '0'))
        except ValueError:
            self.send_error(400)
            return
        if length < 0 or length > 6_000_000:
            self.send_error(413)
            return
        if urlsplit(self.path).path == '/api/v1/admin/integration-settings':
            self.send_error(409, 'Integracoes reais indisponiveis nesta demonstracao')
            return
        self.proxy()

    do_POST = mutate
    do_PUT = mutate
    do_PATCH = mutate
    do_DELETE = mutate

    def end_headers(self):
        self.send_header('Cache-Control', 'no-store')
        self.send_header('X-Robots-Tag', 'noindex, nofollow, noarchive')
        super().end_headers()

    def setup(self):
        super().setup()
        self.connection.settimeout(35)


def main():
    PreviewHandler.expected_authorization = access_token(os.environ.get('DEMO_ACCESS_USER', 'avaliador'), os.environ.get('DEMO_ACCESS_PASSWORD', ''))
    origin = os.environ.get('RENDER_EXTERNAL_URL', '')
    parsed = urlsplit(origin)
    if parsed.scheme != 'https' or not parsed.hostname or parsed.path or parsed.query or parsed.fragment or parsed.username:
        raise ValueError('RENDER_EXTERNAL_URL deve conter a origem HTTPS pública do serviço.')
    # Never inherit database, SMTP, WhatsApp, payment or production credentials.
    env = {key: value for key, value in os.environ.items() if key in ('PATH', 'JAVA_HOME', 'LANG', 'LC_ALL')}
    env.update({'JWT_SECRET': base64.b64encode(secrets.token_bytes(32)).decode(),
                'SPRING_PROFILES_ACTIVE': 'homologacao'})
    frontend.DEMO = True
    with tempfile.TemporaryDirectory(prefix='fooddelivery-preview-') as data:
        args = ['java', '-Xms64m', '-Xmx256m', '-XX:MaxMetaspaceSize=128m', '-jar', str(ROOT / 'api.jar'),
                '--server.address=127.0.0.1', '--server.port=8181',
                '--app.private.encryption-key=' + base64.b64encode(secrets.token_bytes(32)).decode(),
                '--app.web.allowed-origins=' + origin, '--app.security.cookie-secure=true',
                '--app.payment.mercadopago.frontend-url=' + origin,
                '--spring.datasource.url=jdbc:h2:file:' + data + '/database;MODE=PostgreSQL',
                '--app.media.store-assets-dir=' + data + '/store-assets']
        api = subprocess.Popen(args, env=env)
        def terminate(*_):
            raise KeyboardInterrupt
        signal.signal(signal.SIGTERM, terminate)
        try:
            deadline = time.monotonic() + 180
            while time.monotonic() < deadline:
                if api.poll() is not None:
                    raise RuntimeError('A API de demonstração encerrou durante a inicialização.')
                try:
                    with urllib.request.urlopen('http://127.0.0.1:8181/api/v1/demo/status', timeout=1) as response:
                        if response.status == 200:
                            break
                except (OSError, urllib.error.URLError):
                    pass
                time.sleep(1)
            else:
                raise RuntimeError('Tempo de inicialização da API excedido.')
            os.chdir(FRONTEND)
            server = ThreadingHTTPServer(('0.0.0.0', int(os.environ.get('PORT', '10000'))), PreviewHandler)
            def watch_api():
                api.wait()
                server.shutdown()
            threading.Thread(target=watch_api, daemon=True).start()
            print('Homologação pronta: acesso protegido, dados fictícios e temporários.', flush=True)
            try:
                server.serve_forever()
            finally:
                server.server_close()
        except KeyboardInterrupt:
            pass
        finally:
            api.terminate()
            try:
                api.wait(timeout=20)
            except subprocess.TimeoutExpired:
                api.kill()
                api.wait()


if __name__ == '__main__':
    main()
