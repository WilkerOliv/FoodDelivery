import io
import json
import unittest
from unittest.mock import patch

from scripts import dev


class StartupProbeTests(unittest.TestCase):
    @patch("scripts.dev.port_is_open", return_value=True)
    @patch("scripts.dev.urllib.request.urlopen")
    def test_reuses_fooddelivery_api_already_on_default_port(self, open_url, _port_open):
        status = io.BytesIO(json.dumps({"status": "online", "service": "food-delivery-api"}).encode())
        health = io.BytesIO(json.dumps({"status": "UP"}).encode())
        open_url.side_effect = [status, health]

        self.assertEqual(dev.probe_api("http://127.0.0.1:8080/api/v1/status"), "healthy")

    @patch("scripts.dev.subprocess.Popen")
    def test_healthy_existing_api_is_not_launched_again(self, popen):
        self.assertIsNone(dev.start_api_if_needed("healthy", lambda: popen()))
        popen.assert_not_called()

    @patch("scripts.dev.port_is_open", return_value=True)
    @patch("scripts.dev.urllib.request.urlopen", side_effect=OSError("unexpected service"))
    def test_reports_unrecognized_occupant_instead_of_spawning_duplicate(self, _open_url, _port_open):
        self.assertEqual(dev.probe_api("http://127.0.0.1:8080/api/v1/status"), "occupied")

    @patch("scripts.dev.port_is_open", return_value=False)
    @patch("scripts.dev.urllib.request.urlopen", side_effect=OSError("connection refused"))
    def test_reports_api_port_available_when_no_server_is_listening(self, _open_url, _port_open):
        self.assertEqual(dev.probe_api("http://127.0.0.1:8080/api/v1/status"), "available")

    @patch("scripts.dev.urllib.request.urlopen")
    def test_detects_existing_fooddelivery_frontend(self, open_url):
        response = io.BytesIO(b"<html><title>FoodDelivery</title></html>")
        response.status = 200
        open_url.return_value.__enter__.return_value = response

        self.assertTrue(dev.frontend_is_healthy("http://127.0.0.1:5173/"))

    @patch("scripts.dev.urllib.request.urlopen", side_effect=OSError("connection refused"))
    def test_detects_frontend_needs_starting(self, _open_url):
        self.assertFalse(dev.frontend_is_healthy("http://127.0.0.1:5173/"))


if __name__ == "__main__":
    unittest.main()
