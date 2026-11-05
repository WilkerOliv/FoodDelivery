"""Focused HTTP checks for the preview gateway; no external accounts or providers."""
import io
from email.message import Message
import importlib.util
import os
from pathlib import Path
import threading
import unittest
from unittest.mock import Mock, patch
import urllib.error
import urllib.request
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


if __name__ == '__main__':
    unittest.main()
