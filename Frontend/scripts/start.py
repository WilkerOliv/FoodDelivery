#!/usr/bin/env python3
"""Serve the static frontend on the first available local development port."""

from http.server import SimpleHTTPRequestHandler, ThreadingHTTPServer
import os
from pathlib import Path
import socket
import urllib.request
import urllib.error
import json


HOST = "127.0.0.1"
DEFAULT_PORT = int(os.environ.get("FOODDELIVERY_FRONTEND_PORT", os.environ.get("PORT", "5173")))
DEMO = os.environ.get("FOODDELIVERY_DEMO") == "1"
ALLOWED_PORTS = (5180,) if DEMO else (5173, 5174, 5175)
CONTENT_SECURITY_POLICY = (
    "default-src 'self'; script-src 'self' https://cdn.jsdelivr.net; "
    "style-src 'self' 'unsafe-inline' https://fonts.googleapis.com https://cdn.jsdelivr.net; "
    "font-src 'self' https://fonts.gstatic.com data:; "
    "img-src 'self' data: blob: http://localhost:8080 http://127.0.0.1:8080 https://*.tile.openstreetmap.org; "
    "connect-src 'self' http://localhost:8080 http://127.0.0.1:8080 https://viacep.com.br; "
    "frame-src https://www.openstreetmap.org; object-src 'none'; base-uri 'self'; form-action 'self'"
)


class FrontendHandler(SimpleHTTPRequestHandler):
    def do_GET(self):
        if DEMO and self.path == '/runtime-config.js':
            body=b'globalThis.FOODDELIVERY_DEMO=true;'
            self.send_response(200);self.send_header('Content-Type','text/javascript');self.send_header('Cache-Control','no-store');self.end_headers();self.wfile.write(body);return
        if DEMO and self.path.startswith('/api/v1/'):
            return self.proxy()
        super().do_GET()

    def do_POST(self): self.proxy()
    def do_PUT(self): self.proxy()
    def do_PATCH(self): self.proxy()
    def do_DELETE(self): self.proxy()

    def proxy(self):
        if not DEMO or not self.path.startswith('/api/v1/'):
            self.send_error(404);return
        length=int(self.headers.get('Content-Length','0'))
        if length > 10_000_000: self.send_error(413);return
        data=self.rfile.read(length) if length else None
        headers={key:self.headers[key] for key in ('Content-Type','Cookie','Origin') if key in self.headers}
        request=urllib.request.Request('http://127.0.0.1:8181'+self.path,data=data,headers=headers,method=self.command)
        try:
            result=urllib.request.urlopen(request,timeout=30)
        except urllib.error.HTTPError as error: result=error
        except urllib.error.URLError: self.send_error(502,'API de homologação indisponível');return
        with result:
            body=result.read();self.send_response(result.status)
            for key in ('Content-Type','Set-Cookie','Cache-Control'):
                for value in result.headers.get_all(key,[]): self.send_header(key,value)
            self.end_headers();self.wfile.write(body)

    def end_headers(self):
        self.send_header("Content-Security-Policy", CONTENT_SECURITY_POLICY)
        self.send_header("X-Content-Type-Options", "nosniff")
        self.send_header("Referrer-Policy", "strict-origin-when-cross-origin")
        self.send_header("Permissions-Policy", "camera=(), microphone=(), geolocation=(self)")
        self.send_header("X-Frame-Options", "DENY")
        super().end_headers()


def available_ports():
    preferred = (DEFAULT_PORT, *ALLOWED_PORTS)
    return tuple(dict.fromkeys(port for port in preferred if port in ALLOWED_PORTS))


def main():
    os.chdir(Path(__file__).resolve().parent.parent)
    for port in available_ports():
        try:
            server = ThreadingHTTPServer((HOST, port), FrontendHandler)
            break
        except OSError as error:
            if error.errno not in (98, 48, 10048):
                raise
    else:
        raise SystemExit("As portas 5173, 5174 e 5175 já estão ocupadas. Feche uma instância do frontend e tente novamente.")

    if port != DEFAULT_PORT:
        print(f"A porta {DEFAULT_PORT} já estava ocupada; usando http://{HOST}:{port}", flush=True)
    else:
        print(f"Frontend disponível em http://{HOST}:{port}", flush=True)
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        print("\nFrontend encerrado.")
    finally:
        server.server_close()


if __name__ == "__main__":
    main()
