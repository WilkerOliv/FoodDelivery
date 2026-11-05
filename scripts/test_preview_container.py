"""Smoke-test the already built preview image with a 512 MiB memory limit."""
import base64
import json
import secrets
import subprocess
import time
import urllib.error
import urllib.request


def main():
    name = 'fooddelivery-preview-qa-' + secrets.token_hex(4)
    password = secrets.token_urlsafe(32)
    origin = 'https://preview.example.test'
    container = subprocess.check_output([
        'docker', 'run', '-d', '--name', name, '--memory=512m', '--cpus=1',
        '-p', '127.0.0.1::10000', '-e', 'RENDER_EXTERNAL_URL=' + origin,
        '-e', 'DEMO_ACCESS_PASSWORD=' + password, 'fooddelivery-preview:qa'], text=True).strip()
    try:
        mapping = json.loads(subprocess.check_output(['docker', 'inspect', container], text=True))[0]
        port = mapping['NetworkSettings']['Ports']['10000/tcp'][0]['HostPort']
        base = 'http://127.0.0.1:' + port
        authorization = 'Basic ' + base64.b64encode(('avaliador:' + password).encode()).decode()
        def request(path, body=None, cookie=None, authenticated=True, request_origin=origin):
            headers = {'Origin': request_origin, 'Content-Type': 'application/json'}
            if authenticated:
                headers['Authorization'] = authorization
            if cookie:
                headers['Cookie'] = cookie
            data = json.dumps(body).encode() if body is not None else None
            try:
                return urllib.request.urlopen(urllib.request.Request(base + path, data=data, headers=headers), timeout=10)
            except urllib.error.HTTPError as error:
                return error
        deadline = time.monotonic() + 180
        while time.monotonic() < deadline:
            try:
                with request('/healthz', authenticated=False) as response:
                    if response.status == 200:
                        break
            except OSError:
                pass
            time.sleep(1)
        else:
            raise AssertionError('O contêiner não ficou pronto em 180 segundos; consulte docker logs ' + name)
        with request('/', authenticated=False) as response:
            assert response.status == 401
        with request('/') as response:
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
        print('PASS: contêiner com 512 MiB, gateway, API, quatro perfis, cookie protegido e bloqueio de origem.')
    finally:
        subprocess.run(['docker', 'rm', '-f', '-v', container], check=True, stdout=subprocess.DEVNULL)


if __name__ == '__main__':
    main()
