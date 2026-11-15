#!/usr/bin/env python3
"""Temporary HTTPS preview behind a shared access password; never loads production .env."""
import base64
import hmac
import hashlib
import html
import http.client
import importlib.util
import json
import os
from pathlib import Path
import secrets
import signal
import subprocess
import tempfile
import threading
import time
from http.cookies import SimpleCookie, CookieError
from urllib.parse import unquote, urlsplit, parse_qs
from http.server import ThreadingHTTPServer

ROOT = Path(__file__).resolve().parent
FRONTEND = ROOT / 'Frontend'
if not FRONTEND.exists():  # Source checkout, used by focused tests.
    FRONTEND = ROOT.parent / 'Frontend'
# Packaged handler is private, outside the publicly served frontend directory.
handler_path = ROOT / 'frontend_handler.py'
if not handler_path.exists():
    handler_path = FRONTEND / 'scripts/start.py'
spec = importlib.util.spec_from_file_location('local_frontend', handler_path)
frontend = importlib.util.module_from_spec(spec)
spec.loader.exec_module(frontend)


def access_token(username, password):
    if not username or ':' in username or len(password) < 24:
        raise ValueError('Configure DEMO_ACCESS_USER e DEMO_ACCESS_PASSWORD (mínimo 24 caracteres).')
    return 'Basic ' + base64.b64encode(f'{username}:{password}'.encode()).decode()


class PreviewHandler(frontend.FrontendHandler):
    expected_authorization = ''
    session_key = secrets.token_bytes(32)
    gate_cookie = '__Host-fooddelivery_preview'
    challenge_cookie = '__Host-fooddelivery_preview_csrf'
    attempts = {}
    attempts_lock = threading.Lock()

    @classmethod
    def signed_token(cls, purpose):
        payload = f'{purpose}.{int(time.time())}.{secrets.token_hex(16)}'
        signature = hmac.new(cls.session_key, payload.encode(), hashlib.sha256).hexdigest()
        return payload + '.' + signature

    @classmethod
    def valid_token(cls, token, purpose, lifetime):
        if not token or len(token) > 180:
            return False
        try:
            kind, issued, nonce, signature = token.split('.')
            age = time.time() - int(issued)
        except (ValueError, TypeError):
            return False
        expected = hmac.new(cls.session_key, f'{kind}.{issued}.{nonce}'.encode(), hashlib.sha256).hexdigest()
        return kind == purpose and 0 <= age < lifetime and hmac.compare_digest(signature.encode(), expected.encode())

    def cookie_value(self, name):
        try:
            cookies = SimpleCookie(self.headers.get('Cookie', ''))
            return cookies[name].value if name in cookies else ''
        except CookieError:
            return ''

    def cookie_header(self, name, value, age):
        self.send_header('Set-Cookie', f'{name}={value}; Path=/; Max-Age={age}; Secure; HttpOnly; SameSite=Strict')

    def login_page(self, message='', status=200):
        challenge = self.signed_token('csrf')
        body = ('''<!doctype html><html lang="pt-BR"><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>Acesso à homologação · FoodDelivery</title><link rel="stylesheet" href="/_preview/access.css">
<main><span class="badge">AMBIENTE DE TESTES</span><h1>Conheça o FoodDelivery</h1>
<p>Entre com o acesso de avaliador para explorar a demonstração. Os cadastros são fictícios e nenhum pagamento ou mensagem real será enviado.</p>
''' + (f'<p class="error" role="alert">{html.escape(message)}</p>' if message else '') + '''
<form method="post" action="/_preview/login"><input type="hidden" name="challenge" value="''' + challenge + '''">
<label for="gate-user">Usuário de acesso</label><input id="gate-user" name="username" autocomplete="username" required maxlength="100" autofocus>
<label for="gate-password">Senha de acesso</label><input id="gate-password" name="password" type="password" autocomplete="current-password" required maxlength="512">
<button type="submit">Acessar demonstração →</button></form><small>Depois desta entrada, escolha sua conta de teste no site. Este acesso é temporário e não substitui o login de cliente, vendedor, entregador ou administrador.</small></main></html>''').encode()
        self.send_response(status)
        self.send_header('Content-Type', 'text/html; charset=utf-8')
        self.cookie_header(self.challenge_cookie, challenge, 600)
        self.end_headers()
        self.wfile.write(body)

    def check_gate_credentials(self, candidate):
        # Both browser forms and Basic clients share the failed-attempt budget.
        now = time.monotonic()
        peer = self.client_address[0]
        with self.attempts_lock:
            for key in list(self.attempts):
                if now - self.attempts[key][0] >= 900:
                    del self.attempts[key]
            if peer not in self.attempts and len(self.attempts) >= 1024:
                self.send_response(429)
                self.send_header('Retry-After', '900')
                self.end_headers()
                return None
            started, count = self.attempts.get(peer, (now, 0))
            if count >= 10:
                self.send_response(429)
                self.send_header('Retry-After', str(max(1, int(900 - (now - started)))))
                self.end_headers()
                self.wfile.write('Muitas tentativas. Aguarde antes de tentar novamente.'.encode())
                return None
            self.attempts[peer] = (started, count + 1)
        valid = bool(self.expected_authorization) and hmac.compare_digest(candidate.encode(), self.expected_authorization.encode())
        if valid:
            with self.attempts_lock:
                self.attempts.pop(peer, None)
        return valid

    def login(self):
        if self.headers.get('Origin') != self.server.public_origin:
            return self.send_error(403, 'Origem de acesso invalida')
        if self.headers.get('Transfer-Encoding'):
            return self.send_error(400)
        try:
            length = int(self.headers.get('Content-Length', '0'))
        except ValueError:
            return self.send_error(400)
        if length <= 0 or length > 4096:
            return self.send_error(413)
        if self.headers.get_content_type() != 'application/x-www-form-urlencoded':
            return self.send_error(415)
        try:
            fields = parse_qs(self.rfile.read(length).decode('utf-8'), max_num_fields=3, strict_parsing=True)
        except (ValueError, UnicodeError):
            return self.send_error(400)
        if any(len(values) != 1 for values in fields.values()):
            return self.send_error(400)
        challenge = fields.get('challenge', [''])[0]
        if not self.valid_token(challenge, 'csrf', 600) or not hmac.compare_digest(challenge.encode(), self.cookie_value(self.challenge_cookie).encode()):
            return self.login_page('A página expirou. Digite o acesso novamente.', 403)
        username, password = fields.get('username', [''])[0], fields.get('password', [''])[0]
        candidate = 'Basic ' + base64.b64encode(f'{username}:{password}'.encode()).decode()
        valid = self.check_gate_credentials(candidate)
        if valid is None:
            return
        if not valid:
            return self.login_page('Usuário ou senha de acesso incorretos.', 403)
        self.send_response(303)
        self.send_header('Location', '/')
        self.cookie_header(self.gate_cookie, self.signed_token('gate'), 28800)
        self.cookie_header(self.challenge_cookie, '', 0)
        self.end_headers()

    def is_ready(self):
        return self.server.api_ready.is_set()

    def starting(self):
        self.send_response(503)
        self.send_header('Content-Type', 'text/plain; charset=utf-8')
        self.send_header('Retry-After', '10')
        self.end_headers()
        self.wfile.write('A demonstração está iniciando. Aguarde e atualize a página.'.encode())

    def do_HEAD(self):
        self.send_error(405)

    def do_GET(self):
        # Health reveals only readiness, never credentials or business data.
        if self.path == '/healthz':
            if not self.is_ready():
                return self.starting()
            self.send_response(200)
            self.end_headers()
            self.wfile.write(b'ok')
            return
        if self.path == '/_preview/access.css':
            self.send_response(200)
            self.send_header('Content-Type', 'text/css; charset=utf-8')
            self.end_headers()
            self.wfile.write(b'*{box-sizing:border-box}body{margin:0;min-height:100vh;display:grid;place-items:center;background:#f8f5f1;color:#27221f;font:17px/1.55 system-ui,sans-serif;padding:24px}main{width:100%;max-width:520px;padding:32px;background:white;border:1px solid #e5dcd5;border-radius:24px;box-shadow:0 18px 60px #45230e12}h1{font-size:clamp(26px,6vw,34px);line-height:1.2;margin:18px 0}p,small{color:#62564d}.badge{font-size:12px;letter-spacing:.1em;font-weight:750;color:#9c3000}form{display:grid;gap:10px;margin:24px 0}label{font-weight:650}input,button{font:inherit;width:100%;min-width:0;min-height:50px;border-radius:12px;padding:12px 14px}input{border:1px solid #b9a99d;margin-bottom:8px}button{border:0;background:#ac3200;color:#fff;font-weight:700;cursor:pointer}input:focus-visible,button:focus-visible{outline:3px solid #175a9d;outline-offset:3px}.error{padding:12px;background:#fff0eb;border-left:4px solid #ad2600;border-radius:8px;color:#8c2100}small{display:block;font-size:14px}@media(max-width:400px){body{padding:12px}main{padding:22px}}')
            return
        if not self.authorized():
            return
        if not self.is_ready():
            return self.starting()
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
        if self.valid_token(self.cookie_value(self.gate_cookie), 'gate', 28800):
            return True
        authorization = self.headers.get('Authorization', '')
        if authorization:
            valid = self.check_gate_credentials(authorization)
            if valid is None:
                return False
            if valid:
                return True
        if self.command == 'GET' and 'text/html' in self.headers.get('Accept', '') and not urlsplit(self.path).path.startswith('/api/'):
            self.login_page()
            return False
        self.send_response(401)
        api_request = urlsplit(self.path).path.startswith('/api/')
        if api_request:
            self.send_header('Content-Type', 'application/json; charset=utf-8')
        # Basic remains accepted preemptively by HTTP test clients. Never ask the
        # browser to open a native challenge, including for favicon/subresources.
        self.end_headers()
        if api_request:
            self.wfile.write(json.dumps({'message': 'O acesso à homologação expirou. Atualize a página para entrar novamente.'}, ensure_ascii=False).encode())
        return False

    def mutate(self):
        if self.command == 'POST' and self.path == '/_preview/login':
            return self.login()
        if not self.authorized():
            return
        if not self.is_ready():
            return self.starting()
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
        self.send_header('X-Robots-Tag', 'noindex, nofollow, noarchive')
        self.send_header('X-Frame-Options', 'DENY')
        super().end_headers()

    def setup(self):
        super().setup()
        self.connection.settimeout(35)


def demo_ready():
    connection = http.client.HTTPConnection('127.0.0.1', 8181, timeout=2)
    try:
        connection.request('GET', '/api/v1/demo/status')
        response = connection.getresponse()
        if response.status != 200:
            return False
        status = json.load(response)
        return status.get('mode') == 'homologacao' and status.get('ready') is True
    except (OSError, http.client.HTTPException, ValueError, AttributeError):
        return False
    finally:
        connection.close()


def wait_for_api(api, timeout=600, clock=time.monotonic, sleep=time.sleep):
    """Allow slow free-tier cold starts, but only accept a completely seeded demo."""
    deadline = clock() + timeout
    while clock() < deadline:
        if api.poll() is not None:
            raise RuntimeError('A API de demonstração encerrou durante a inicialização.')
        if demo_ready():
            return
        sleep(1)
    raise RuntimeError('Tempo de inicialização da API excedido (600 segundos por padrão).')


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
        # Bind Render's public port immediately; readiness remains 503 until Java and seed finish.
        os.chdir(FRONTEND)
        server = ThreadingHTTPServer(('0.0.0.0', int(os.environ.get('PORT', '10000'))), PreviewHandler)
        server.api_ready = threading.Event()
        server.public_origin = origin
        server_thread = threading.Thread(target=server.serve_forever, daemon=True)
        server_thread.start()
        print('Porta pública aberta; aguardando a API. /healthz retorna 503 durante a inicialização.', flush=True)
        api = None
        def terminate(*_):
            raise KeyboardInterrupt
        signal.signal(signal.SIGTERM, terminate)
        try:
            api = subprocess.Popen(args, env=env)
            wait_for_api(api)
            server.api_ready.set()
            print('Homologação pronta: acesso protegido, dados fictícios e temporários.', flush=True)
            exit_code = api.wait()
            server.api_ready.clear()
            raise RuntimeError(f'A API de demonstração encerrou (código {exit_code}).')
        except KeyboardInterrupt:
            pass
        finally:
            server.api_ready.clear()
            server.shutdown()
            server.server_close()
            server_thread.join(timeout=5)
            if api is not None and api.poll() is None:
                api.terminate()
                try:
                    api.wait(timeout=20)
                except subprocess.TimeoutExpired:
                    api.kill()
                    api.wait()


if __name__ == '__main__':
    main()
