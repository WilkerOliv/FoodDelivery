"""Focused HTTP checks for the preview gateway; no external accounts or providers."""
import base64
import importlib.util
import os
from pathlib import Path
import threading
import unittest
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


if __name__ == '__main__':
    unittest.main()
