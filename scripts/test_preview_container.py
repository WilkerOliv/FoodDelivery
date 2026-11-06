"""Smoke-test the already built preview image with a 512 MiB memory limit."""
import argparse
import base64
import json
import re
import secrets
import subprocess
import time
import urllib.error
import urllib.request
from urllib.parse import urlencode


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--cpus', default='1', help='Use 0.1 para reproduzir um início com CPU restrita.')
    options = parser.parse_args()
    name = 'fooddelivery-preview-qa-' + secrets.token_hex(4)
    password = secrets.token_urlsafe(32)
    origin = 'https://preview.example.test'
    container = subprocess.check_output([
        'docker', 'run', '-d', '--name', name, '--memory=512m', '--cpus=' + options.cpus,
        '-p', '127.0.0.1::10000', '-e', 'RENDER_EXTERNAL_URL=' + origin,
        '-e', 'DEMO_ACCESS_PASSWORD=' + password, 'fooddelivery-preview:qa'], text=True).strip()
    try:
        mapping = json.loads(subprocess.check_output(['docker', 'inspect', container], text=True))[0]
        port = mapping['NetworkSettings']['Ports']['10000/tcp'][0]['HostPort']
        base = 'http://127.0.0.1:' + port
        authorization = 'Basic ' + base64.b64encode(('avaliador:' + password).encode()).decode()
        def request(path, body=None, cookie=None, authenticated=True, request_origin=origin, accept=None):
            headers = {'Origin': request_origin, 'Content-Type': 'application/json'}
            if accept:
                headers['Accept'] = accept
            if authenticated:
                headers['Authorization'] = authorization
            if cookie:
                headers['Cookie'] = cookie
            data = json.dumps(body).encode() if body is not None else None
            try:
                return urllib.request.urlopen(urllib.request.Request(base + path, data=data, headers=headers), timeout=10)
            except urllib.error.HTTPError as error:
                return error
        started = time.monotonic()
        deadline = started + 600
        saw_startup = False
        while time.monotonic() < deadline:
            try:
                with request('/healthz', authenticated=False) as response:
                    if response.status == 200:
                        break
                    assert response.status == 503, response.status
                    saw_startup = True
            except OSError:
                pass
            time.sleep(1)
        else:
            raise AssertionError('O contêiner não ficou pronto em 600 segundos; consulte docker logs ' + name)
        assert saw_startup, 'A porta deve responder durante a inicialização, com saúde 503.'
        startup_seconds = round(time.monotonic() - started, 1)
        with request('/', authenticated=False) as response:
            assert response.status == 401
        with request('/') as response:
            assert response.status == 200
        with request('/', authenticated=False, accept='text/html') as response:
            assert response.status == 200
            assert response.headers.get('WWW-Authenticate') is None
            form = response.read().decode()
            challenge = re.search(r'name="challenge" value="([^"]+)"', form).group(1)
            csrf_cookie = next(value.split(';')[0] for value in response.headers.get_all('Set-Cookie') if value.startswith('__Host-fooddelivery_preview_csrf='))
        class NoRedirect(urllib.request.HTTPRedirectHandler):
            def redirect_request(self, *args):
                return None
        login = urllib.request.Request(base + '/_preview/login',
            data=urlencode({'username': 'avaliador', 'password': password, 'challenge': challenge}).encode(),
            headers={'Origin': origin, 'Cookie': csrf_cookie, 'Content-Type': 'application/x-www-form-urlencoded'})
        try:
            response = urllib.request.build_opener(NoRedirect()).open(login, timeout=10)
        except urllib.error.HTTPError as error:
            response = error
        with response:
            assert response.status == 303
            session_cookie = next(value for value in response.headers.get_all('Set-Cookie') if value.startswith('__Host-fooddelivery_preview='))
            assert all(flag in session_cookie for flag in ('Secure', 'HttpOnly', 'SameSite=Strict', 'Path=/'))
        with request('/runtime-config.js', authenticated=False, cookie=session_cookie.split(';')[0]) as response:
            assert response.status == 200
        with request('/api/v1/demo/status') as response:
            assert json.load(response)['ready'] is True
        for role in ('admin', 'cliente', 'vendedor', 'entregador'):
            with request('/api/v1/auth/login', {'email': role + '@example.test', 'password': 'DemoLocal!2026#Teste'}) as response:
                assert response.status == 200, (role, response.status)
                session = response.headers['Set-Cookie']
                assert 'Secure' in session and 'HttpOnly' in session
                cookie = session.split(';')[0]
            with request('/api/v1/account/me', cookie=cookie) as response:
                assert response.status == 200, (role, response.status)
            with request('/api/v1/auth/logout', {}, cookie, request_origin='https://untrusted.example.test') as response:
                assert response.status == 403
        # Reproduce the shared-gateway failure: more than ten valid sign-ins must work.
        for _ in range(12):
            with request('/api/v1/auth/login', {'email': 'cliente@example.test', 'password': 'DemoLocal!2026#Teste'}) as response:
                assert response.status == 200, response.status
        for _ in range(10):
            with request('/api/v1/auth/login', {'email': 'inexistente@example.test', 'password': 'IncorrectPassword123!'}) as response:
                assert response.status == 401, response.status
        with request('/api/v1/auth/login', {'email': 'inexistente@example.test', 'password': 'IncorrectPassword123!'}) as response:
            assert response.status == 429
        with request('/api/v1/auth/login', {'email': 'admin@example.test', 'password': 'DemoLocal!2026#Teste'}) as response:
            assert response.status == 200, response.status
        print(f'PASS: contêiner com 512 MiB e {options.cpus} CPU; início em {startup_seconds}s; saúde 503→200, gateway, API, quatro perfis, cookie protegido, bloqueio de origem, logins válidos repetidos e limitação isolada de falhas.')
    finally:
        subprocess.run(['docker', 'rm', '-f', '-v', container], check=True, stdout=subprocess.DEVNULL)


if __name__ == '__main__':
    main()
