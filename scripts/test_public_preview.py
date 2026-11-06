"""Focused HTTP checks for the preview gateway; no external accounts or providers."""
import io
import re
from email.message import Message
import importlib.util
import os
from pathlib import Path
import threading
import unittest
from unittest.mock import Mock, patch
import urllib.error
import urllib.request
from urllib.parse import urlencode
from http.server import ThreadingHTTPServer

ROOT = Path(__file__).resolve().parents[1]
spec = importlib.util.spec_from_file_location('preview', ROOT / 'deploy/homologacao_publica.py')
preview = importlib.util.module_from_spec(spec)
spec.loader.exec_module(preview)


class PreviewTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.cwd = os.getcwd()
        os.chdir(ROOT / 'Frontend')
        preview.PreviewHandler.expected_authorization = preview.access_token('avaliador', 'x' * 32)
        cls.server = ThreadingHTTPServer(('127.0.0.1', 0), preview.PreviewHandler)
        cls.server.api_ready = threading.Event()
        cls.server.api_ready.set()
        cls.thread = threading.Thread(target=cls.server.serve_forever, daemon=True)
        cls.thread.start()
        cls.url = 'http://127.0.0.1:' + str(cls.server.server_port)
        cls.server.public_origin = cls.url

    def setUp(self):
        preview.PreviewHandler.attempts.clear()

    def gate_form(self):
        with urllib.request.urlopen(urllib.request.Request(self.url + '/', headers={'Accept': 'text/html'})) as response:
            content = response.read().decode()
            token = re.search(r'name="challenge" value="([^"]+)"', content).group(1)
            cookie = next(value.split(';')[0] for value in response.headers.get_all('Set-Cookie') if value.startswith(preview.PreviewHandler.challenge_cookie + '='))
            return content, token, cookie, response.headers

    def submit_gate(self, token, cookie, password='x' * 32, origin=None):
        class NoRedirect(urllib.request.HTTPRedirectHandler):
            def redirect_request(self, *args):
                return None
        data = urlencode({'username': 'avaliador', 'password': password, 'challenge': token}).encode()
        request = urllib.request.Request(self.url + '/_preview/login', data=data, headers={
            'Content-Type': 'application/x-www-form-urlencoded', 'Cookie': cookie,
            'Origin': self.url if origin is None else origin})
        try:
            return urllib.request.build_opener(NoRedirect()).open(request)
        except urllib.error.HTTPError as error:
            return error

    def test_browser_receives_stable_form_without_basic_dialog(self):
        content, token, cookie, headers = self.gate_form()
        self.assertIn('Usuário de acesso', content)
        self.assertIn('Senha de acesso', content)
        self.assertNotIn('WWW-Authenticate', headers)
        self.assertNotIn('x' * 32, content)
        self.assertTrue(preview.PreviewHandler.valid_token(token, 'csrf', 600))
        for attribute in ('Secure', 'HttpOnly', 'SameSite=Strict', 'Path=/'):
            self.assertIn(attribute, headers['Set-Cookie'])

    def test_expired_browser_api_session_does_not_open_native_auth_dialog(self):
        with self.request('/api/v1/demo/messages') as response:
            self.assertEqual(response.status, 401)
            self.assertNotIn('WWW-Authenticate', response.headers)
            self.assertIn('Atualize a página', response.read().decode())

    def test_gate_login_issues_signed_cookie_and_does_not_disclose_password(self):
        _, token, cookie, _ = self.gate_form()
        with self.submit_gate(token, cookie) as response:
            self.assertEqual(response.status, 303)
            self.assertEqual(response.headers['Location'], '/')
            signed = next(value.split(';')[0] for value in response.headers.get_all('Set-Cookie') if value.startswith(preview.PreviewHandler.gate_cookie + '='))
            self.assertNotIn('x' * 32, signed)
        with urllib.request.urlopen(urllib.request.Request(self.url + '/runtime-config.js', headers={'Cookie': signed})) as response:
            self.assertEqual(response.status, 200)
            self.assertIn(b'FOODDELIVERY_DEMO=true', response.read())

    def test_wrong_gate_password_does_not_issue_session(self):
        _, token, cookie, _ = self.gate_form()
        with self.submit_gate(token, cookie, password='wrong') as response:
            self.assertEqual(response.status, 403)
            self.assertNotIn('WWW-Authenticate', response.headers)
            self.assertFalse(any(value.startswith(preview.PreviewHandler.gate_cookie + '=') for value in response.headers.get_all('Set-Cookie', [])))
            self.assertIn('incorretos', response.read().decode())

    def test_gate_rejects_cross_origin_and_missing_challenge(self):
        _, token, cookie, _ = self.gate_form()
        with self.submit_gate(token, cookie, origin='https://outside.example') as response:
            self.assertEqual(response.status, 403)
        with self.submit_gate('', cookie) as response:
            self.assertEqual(response.status, 403)
        with self.submit_gate(token, '') as response:
            self.assertEqual(response.status, 403)

    def test_session_tampering_expiration_and_restart_are_rejected(self):
        token = preview.PreviewHandler.signed_token('gate')
        self.assertTrue(preview.PreviewHandler.valid_token(token, 'gate', 28800))
        self.assertFalse(preview.PreviewHandler.valid_token(token + '0', 'gate', 28800))
        self.assertFalse(preview.PreviewHandler.valid_token(token[:-1] + 'é', 'gate', 28800))
        self.assertFalse(preview.PreviewHandler.valid_token(token, 'csrf', 600))
        with patch.object(preview.time, 'time', return_value=int(token.split('.')[1]) + 28801):
            self.assertFalse(preview.PreviewHandler.valid_token(token, 'gate', 28800))
        with patch.object(preview.PreviewHandler, 'session_key', b'new-instance-key'):
            self.assertFalse(preview.PreviewHandler.valid_token(token, 'gate', 28800))
        with self.assertRaises(urllib.error.HTTPError) as result:
            urllib.request.urlopen(urllib.request.Request(self.url + '/runtime-config.js', headers={'Cookie': preview.PreviewHandler.gate_cookie + '=forged'}))
        self.assertEqual(result.exception.code, 401)

    def test_gate_failed_attempts_are_limited(self):
        for _ in range(10):
            _, token, cookie, _ = self.gate_form()
            with self.submit_gate(token, cookie, password='wrong') as response:
                self.assertEqual(response.status, 403)
        _, token, cookie, _ = self.gate_form()
        with self.submit_gate(token, cookie) as response:
            self.assertEqual(response.status, 429)
            self.assertGreater(int(response.headers['Retry-After']), 0)

    def test_basic_clients_cannot_bypass_gate_failed_attempt_budget(self):
        for _ in range(10):
            request = urllib.request.Request(self.url + '/runtime-config.js', headers={'Authorization': 'Basic invalid'})
            with self.assertRaises(urllib.error.HTTPError) as result:
                urllib.request.urlopen(request)
            self.assertEqual(result.exception.code, 401)
        _, token, cookie, _ = self.gate_form()
        with self.submit_gate(token, cookie) as response:
            self.assertEqual(response.status, 429)

    def test_gate_rejects_oversized_and_unexpected_body_types(self):
        for data, content_type, expected in ((b'x' * 4097, 'application/x-www-form-urlencoded', 413),
                                              (b'{}', 'application/json', 415)):
            request = urllib.request.Request(self.url + '/_preview/login', data=data,
                headers={'Origin': self.url, 'Content-Type': content_type})
            with self.assertRaises(urllib.error.HTTPError) as result:
                urllib.request.urlopen(request)
            self.assertEqual(result.exception.code, expected)

    @classmethod
    def tearDownClass(cls):
        cls.server.shutdown()
        cls.server.server_close()
        cls.thread.join()
        os.chdir(cls.cwd)

    def request(self, path, authenticated=False, method='GET'):
        headers = {'Authorization': preview.PreviewHandler.expected_authorization} if authenticated else {}
        try:
            return urllib.request.urlopen(urllib.request.Request(self.url + path, headers=headers, method=method))
        except urllib.error.HTTPError as error:
            return error

    def test_only_health_is_public(self):
        with self.request('/healthz') as response:
            self.assertEqual(response.read(), b'ok')
        for path in ('/', '/demo.html', '/api/v1/demo/messages', '/runtime-config.js'):
            with self.request(path) as response:
                self.assertEqual(response.status, 401)
                self.assertNotIn('WWW-Authenticate', response.headers)

    def test_static_and_demo_flag(self):
        with self.request('/', True) as response:
            self.assertEqual(response.status, 200)
            self.assertIn('noindex', response.headers['X-Robots-Tag'])
            self.assertEqual(response.headers['Cache-Control'], 'no-store')
        with self.request('/runtime-config.js', True) as response:
            self.assertEqual(response.read(), b'globalThis.FOODDELIVERY_DEMO=true;')

    def test_private_files_and_directory_listing_blocked(self):
        for path in ('/.env', '/scripts/start.py', '/tests/', '/src/', '/../README.md', '/%2e%2e/README.md'):
            with self.request(path, True) as response:
                self.assertEqual(response.status, 404, path)

    def test_mutations_require_gate_and_real_integrations_blocked(self):
        for method in ('POST', 'PUT', 'PATCH', 'DELETE'):
            with self.request('/api/v1/admin/integration-settings', method=method) as response:
                self.assertEqual(response.status, 401)
            with self.request('/api/v1/admin/integration-settings', True, method) as response:
                self.assertEqual(response.status, 409)

    def test_weak_gate_rejected(self):
        with self.assertRaises(ValueError):
            preview.access_token('avaliador', '123')
        with self.assertRaises(ValueError):
            preview.access_token('a:b', 'x' * 32)

    def test_startup_health_is_503_and_gate_remains_required(self):
        self.server.api_ready.clear()
        try:
            for path in ('/healthz', '/', '/api/v1/demo/status'):
                with self.request(path, True) as response:
                    self.assertEqual(response.status, 503)
                    self.assertEqual(response.headers['Retry-After'], '10')
            with self.request('/') as response:
                self.assertEqual(response.status, 401)
            with self.request('/api/v1/auth/login', True, 'POST') as response:
                self.assertEqual(response.status, 503)
        finally:
            self.server.api_ready.set()
        with self.request('/healthz') as response:
            self.assertEqual(response.status, 200)


class StartupTest(unittest.TestCase):
    def test_accepts_slow_start_beyond_180_seconds_and_waits_for_seed(self):
        elapsed = [0]
        api = Mock()
        api.poll.return_value = None
        def sleep(seconds):
            elapsed[0] += seconds
        with patch.object(preview, 'demo_ready', side_effect=lambda: elapsed[0] >= 195):
            preview.wait_for_api(api, clock=lambda: elapsed[0], sleep=sleep)
        self.assertEqual(elapsed[0], 195)

    def test_process_exit_fails_immediately(self):
        api = Mock()
        api.poll.return_value = 1
        with self.assertRaisesRegex(RuntimeError, 'encerrou'):
            preview.wait_for_api(api)

    def test_unavailable_api_has_bounded_startup(self):
        api = Mock()
        api.poll.return_value = None
        elapsed = [0]
        def sleep(seconds):
            elapsed[0] += seconds
        with patch.object(preview, 'demo_ready', return_value=False):
            with self.assertRaisesRegex(RuntimeError, 'excedido'):
                preview.wait_for_api(api, timeout=3, clock=lambda: elapsed[0], sleep=sleep)
        self.assertEqual(elapsed[0], 3)

    def test_health_probe_requires_expected_mode_and_completed_seed(self):
        for payload, expected in ((b'{"mode":"homologacao","ready":true}', True),
                                  (b'{"mode":"homologacao","ready":false}', False),
                                  (b'{"mode":"production","ready":true}', False),
                                  (b'{"mode":"homologacao","ready":"true"}', False),
                                  (b'invalid', False)):
            response = io.BytesIO(payload)
            response.status = 200
            connection = Mock()
            connection.getresponse.return_value = response
            with patch.object(preview.http.client, 'HTTPConnection', return_value=connection):
                self.assertEqual(preview.demo_ready(), expected)
                connection.request.assert_called_once_with('GET', '/api/v1/demo/status')
                connection.close.assert_called_once()


class FixedProxyTest(unittest.TestCase):
    def test_proxy_keeps_loopback_destination_and_does_not_follow_redirect(self):
        handler = Mock()
        handler.path = '/api/v1/example?next=file:///etc/passwd'
        handler.command = 'GET'
        handler.headers = {'Authorization': 'Basic external-gate-only', 'Cookie': 'session=test'}
        handler.rfile = io.BytesIO()
        handler.wfile = io.BytesIO()
        response = Mock()
        response.status = 302
        response.read.return_value = b'redirect'
        response.headers = Message()
        response.headers['Location'] = 'https://untrusted.example.test'
        connection = Mock()
        connection.getresponse.return_value = response
        with patch.object(preview.frontend, 'DEMO', True), patch.object(preview.frontend.http.client, 'HTTPConnection', return_value=connection) as factory:
            preview.frontend.FrontendHandler.proxy(handler)
        factory.assert_called_once_with('127.0.0.1', 8181, timeout=30)
        connection.request.assert_called_once_with('GET', handler.path, body=None, headers={'Cookie': 'session=test'})
        handler.send_response.assert_called_once_with(302)
        connection.close.assert_called_once()
        self.assertEqual(handler.wfile.getvalue(), b'redirect')

    def test_proxy_preserves_retry_information(self):
        handler = Mock()
        handler.path = '/api/v1/auth/login'
        handler.command = 'POST'
        handler.headers = {'Content-Length': '0'}
        handler.rfile = io.BytesIO()
        handler.wfile = io.BytesIO()
        response = Mock()
        response.status = 429
        response.read.return_value = b'{"message":"Muitas tentativas"}'
        response.headers = Message()
        response.headers['Retry-After'] = '120'
        connection = Mock()
        connection.getresponse.return_value = response
        with patch.object(preview.frontend, 'DEMO', True), patch.object(preview.frontend.http.client, 'HTTPConnection', return_value=connection):
            preview.frontend.FrontendHandler.proxy(handler)
        handler.send_response.assert_called_once_with(429)
        handler.send_header.assert_any_call('Retry-After', '120')


if __name__ == '__main__':
    unittest.main()
