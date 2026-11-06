#!/usr/bin/env python3
"""Run disposable API journeys against a protected demo; never writes access secrets to disk."""
import argparse
import base64
import getpass
import http.cookiejar
import importlib
import json
from pathlib import Path
import time
import urllib.error
import urllib.request

ACCESS_PASSWORD = None
BASE = None
AUTHORIZATION = None
SESSIONS = {}
CALLS = []
ACCOUNT_PASSWORD = 'DemoLocal!2026#Teste'


class NoRedirect(urllib.request.HTTPRedirectHandler):
    def redirect_request(self, request, file, code, message, headers, new_url):
        return None  # Never transmit the shared access header to a redirect destination.


def open_request(request, timeout=30):
    # Compatibility helper for the runtime flag checks used by existing local journeys.
    if isinstance(request, str):
        request = urllib.request.Request(request)
    if not request.full_url.startswith(BASE + '/'):
        raise RuntimeError('O teste tentou sair da origem de homologação autorizada.')
    request.add_header('Authorization', AUTHORIZATION)
    return urllib.request.build_opener(NoRedirect()).open(request, timeout=timeout)


class PublishedClient:
    def __init__(self):
        self.jar = http.cookiejar.CookieJar()
        self.http = urllib.request.build_opener(NoRedirect(), urllib.request.HTTPCookieProcessor(self.jar))

    def login(self, name):
        if name in SESSIONS:
            self.jar, self.http = SESSIONS[name]
            return
        result = self.call('/auth/login', 'POST', {'email': name + '@example.test', 'password': ACCOUNT_PASSWORD})
        SESSIONS[name] = (self.jar, self.http)
        return result

    def call(self, path, method='GET', body=None, expected=200):
        data = None if body is None else json.dumps(body).encode()
        request = urllib.request.Request(BASE + '/api/v1' + path, data=data, method=method,
            headers={'Content-Type': 'application/json', 'Origin': BASE, 'Authorization': AUTHORIZATION})
        try:
            response = self.http.open(request, timeout=30)
        except urllib.error.HTTPError as error:
            response = error
        with response:
            raw = response.read()
            CALLS.append({'method': method, 'path': path, 'status': response.status, 'expected': expected})
            if response.status != expected:
                retry = response.headers.get('Retry-After')
                wait = f'; tente novamente após {retry}s' if response.status == 429 and retry else ''
                raise AssertionError(f'{method} {path}: HTTP {response.status}, esperado {expected}{wait}')
            return json.loads(raw) if raw and 'json' in response.headers.get('Content-Type', '') else None


def main():
    global BASE, AUTHORIZATION, ACCESS_PASSWORD
    parser = argparse.ArgumentParser()
    parser.add_argument('--url', default='https://fooddelivery-homologacao.onrender.com')
    parser.add_argument('--report', required=True)
    options = parser.parse_args()
    BASE = options.url.rstrip('/')
    if BASE != 'https://fooddelivery-homologacao.onrender.com':
        raise SystemExit('Este roteiro autoriza apenas a homologação FoodDelivery informada pelo usuário.')
    ACCESS_PASSWORD = getpass.getpass('Senha de acesso externo (não será salva): ')
    AUTHORIZATION = 'Basic ' + base64.b64encode(('avaliador:' + ACCESS_PASSWORD).encode()).decode()
    status = PublishedClient().call('/demo/status')
    if status.get('mode') != 'homologacao' or status.get('ready') is not True:
        raise SystemExit('Servidor não confirmou homologação pronta. Nenhuma jornada foi executada.')
    with open_request(BASE + '/runtime-config.js') as response:
        assert b'FOODDELIVERY_DEMO=true' in response.read()
    suites = []
    try:
        for name in ('admin', 'cliente', 'vendedor', 'entregador'):
            PublishedClient().login(name)
    except Exception as error:
        report = {'environment': BASE, 'mode': 'homologacao', 'method': 'HTTP API; não é teste visual',
                  'suites': [{'suite': 'preflight_four_roles', 'status': 'FAIL', 'error': str(error)}],
                  'calls': CALLS, 'secretsSaved': False}
        output = Path(options.report)
        output.parent.mkdir(parents=True, exist_ok=True)
        output.write_text(json.dumps(report, ensure_ascii=False, indent=2) + '\n')
        print('Bloqueio de autenticação; relatório salvo sem credenciais:', str(error), flush=True)
        return 1
    # Local suites receive the protected transport without changing their local defaults.
    for name in ('test_homologacao', 'test_jornadas', 'test_courier_finance', 'test_store_location_modes', 'test_store_hours'):
        module = importlib.import_module(name)
        module.BASE = BASE
        module.Client = PublishedClient
        started = time.monotonic()
        before = len(CALLS)
        try:
            # Modules use urllib only to read the runtime flag; business calls use the client.
            original = module.urllib.request.urlopen
            module.urllib.request.urlopen = open_request
            try:
                module.main()
            finally:
                module.urllib.request.urlopen = original
            outcome = {'suite': name, 'status': 'PASS'}
        except Exception as error:
            outcome = {'suite': name, 'status': 'FAIL', 'error': str(error)[:500]}
            print('FALHOU:', name, outcome['error'], flush=True)
        outcome.update(seconds=round(time.monotonic() - started, 2), calls=len(CALLS) - before)
        suites.append(outcome)
    report = {'environment': BASE, 'mode': 'homologacao', 'method': 'HTTP API; não é teste visual',
              'suites': suites, 'calls': CALLS, 'secretsSaved': False}
    output = Path(options.report)
    output.parent.mkdir(parents=True, exist_ok=True)
    output.write_text(json.dumps(report, ensure_ascii=False, indent=2) + '\n')
    print(json.dumps({'suites': suites, 'requests': len(CALLS)}, ensure_ascii=False), flush=True)
    return 1 if any(s['status'] == 'FAIL' for s in suites) else 0


if __name__ == '__main__':
    raise SystemExit(main())
