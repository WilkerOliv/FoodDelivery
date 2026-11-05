import urllib.error
import unittest
from unittest.mock import Mock
from check_code_scanning import available


class CapabilitiesTest(unittest.TestCase):
    def test_available(self):
        response = Mock()
        response.__enter__ = Mock(return_value=response)
        response.__exit__ = Mock(return_value=False)
        response.status = 200
        self.assertTrue(available('example/repository', 'fake-token', Mock(return_value=response)))

    def test_unavailable_feature(self):
        for status in (403, 404):
            error = urllib.error.HTTPError('https://api.github.com', status, 'Unavailable', {}, None)
            self.assertFalse(available('example/repository', 'fake-token', Mock(side_effect=error)))

    def test_authentication_rate_limit_and_provider_errors_are_not_hidden(self):
        for status in (401, 429, 500):
            error = urllib.error.HTTPError('https://api.github.com', status, 'Error', {}, None)
            with self.assertRaises(urllib.error.HTTPError):
                available('example/repository', 'fake-token', Mock(side_effect=error))


if __name__ == '__main__':
    unittest.main()
