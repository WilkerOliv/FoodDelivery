#!/usr/bin/env python3
"""Serve the static frontend on the first available local development port."""

from http.server import SimpleHTTPRequestHandler, ThreadingHTTPServer
import os
from pathlib import Path
import socket


HOST = "127.0.0.1"
DEFAULT_PORT = int(os.environ.get("PORT", "5173"))
ALLOWED_PORTS = (5173, 5174, 5175)


def available_ports():
    preferred = (DEFAULT_PORT, *ALLOWED_PORTS)
    return tuple(dict.fromkeys(port for port in preferred if port in ALLOWED_PORTS))


def main():
    os.chdir(Path(__file__).resolve().parent.parent)
    for port in available_ports():
        try:
            server = ThreadingHTTPServer((HOST, port), SimpleHTTPRequestHandler)
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
