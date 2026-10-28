#!/usr/bin/env python3
"""Start and supervise the local database, API, and web app together."""

from __future__ import annotations

import os
import subprocess
import sys
import time
import urllib.request
import urllib.error
import json
import socket
from urllib.parse import urlparse, urlunparse
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
BACKEND = ROOT / "Backend"
FRONTEND = ROOT / "Frontend"
API_STATUS_URL = "http://127.0.0.1:8080/api/v1/status"
FRONTEND_PORTS = tuple(dict.fromkeys((int(os.environ.get("FOODDELIVERY_FRONTEND_PORT", "5173")), 5173, 5174, 5175)))


def port_is_open(url: str) -> bool:
    parsed = urlparse(url)
    try:
        with socket.create_connection((parsed.hostname, parsed.port or (443 if parsed.scheme == "https" else 80)), timeout=0.4):
            return True
    except OSError:
        return False


def probe_api(status_url: str) -> str:
    """Return healthy, unhealthy, occupied, or available for the configured API port."""
    parsed = urlparse(status_url)
    health_url = urlunparse((parsed.scheme, parsed.netloc, "/actuator/health", "", "", ""))
    try:
        with urllib.request.urlopen(status_url, timeout=1) as response:
            identity = json.loads(response.read(4096))
    except (OSError, ValueError, urllib.error.URLError):
        return "occupied" if port_is_open(status_url) else "available"
    if identity.get("status") != "online" or identity.get("service") != "food-delivery-api":
        return "occupied"
    try:
        with urllib.request.urlopen(health_url, timeout=1) as response:
            health = json.loads(response.read(4096))
        return "healthy" if health.get("status") == "UP" else "unhealthy"
    except (OSError, ValueError, urllib.error.URLError):
        return "unhealthy"


def frontend_is_healthy(url: str) -> bool:
    try:
        with urllib.request.urlopen(url, timeout=1) as response:
            return response.status == 200 and b"FoodDelivery" in response.read(65536)
    except (OSError, urllib.error.URLError):
        return False


def start_api_if_needed(state: str, launch):
    """Launch only when the API port is free; leave reused services unowned."""
    if state == "healthy":
        return None
    if state != "available":
        raise RuntimeError(f"Não é seguro iniciar outra API: estado atual da porta é '{state}'.")
    return launch()


def stop(process: subprocess.Popen | None) -> None:
    if process is None or process.poll() is not None:
        return
    process.terminate()
    try:
        process.wait(timeout=10)
    except subprocess.TimeoutExpired:
        process.kill()
        process.wait(timeout=5)


def main() -> int:
    api: subprocess.Popen | None = None
    frontend: subprocess.Popen | None = None
    database_started = False
    try:
        api_state = probe_api(API_STATUS_URL)
        if api_state == "healthy":
            print(f"API FoodDelivery já está ativa em {API_STATUS_URL}; usando a instância existente.", flush=True)
        elif api_state == "unhealthy":
            raise RuntimeError(f"A API FoodDelivery responde em {API_STATUS_URL}, mas a verificação de saúde falhou. Corrija essa instância antes de iniciar outra.")
        elif api_state == "occupied":
            raise RuntimeError(f"A porta da API está ocupada por outro serviço ({API_STATUS_URL}). Encerre o serviço conflitante antes de iniciar o FoodDelivery.")
        else:
            pg_bin = Path(os.environ.get("PG_BIN", "/usr/lib/postgresql/17/bin"))
            status = subprocess.run([str(pg_bin / "pg_ctl"), "-D", str(BACKEND / ".postgres-data"), "status"],
                                    cwd=BACKEND, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=False)
            database_was_running = status.returncode == 0
            subprocess.run([str(BACKEND / "scripts/start-db.sh")], cwd=BACKEND, check=True)
            database_started = not database_was_running
            api_env = os.environ.copy()
            api_env.pop("PORT", None)
            api = start_api_if_needed(api_state, lambda: subprocess.Popen(
                [str(BACKEND / "scripts/run-api.sh")], cwd=BACKEND, env=api_env))
            deadline = time.monotonic() + 90
            while time.monotonic() < deadline:
                if api.poll() is not None:
                    raise RuntimeError("A API foi encerrada antes de ficar disponível. Confira a configuração do PostgreSQL e o log acima.")
                if probe_api(API_STATUS_URL) == "healthy":
                    break
                time.sleep(1)
            else:
                raise RuntimeError("A API não passou pela verificação de saúde em 90 segundos. Confira o log acima.")
            print(f"API disponível em {API_STATUS_URL}", flush=True)

        frontend_url = None
        for port in FRONTEND_PORTS:
            candidate = f"http://127.0.0.1:{port}/"
            if frontend_is_healthy(candidate):
                frontend_url = candidate
                print(f"Frontend FoodDelivery já está ativo em {candidate}; usando a instância existente.", flush=True)
                break
        if frontend_url is None:
            frontend_env = os.environ.copy()
            frontend_env.pop("PORT", None)
            frontend_env["FOODDELIVERY_FRONTEND_PORT"] = str(FRONTEND_PORTS[0])
            frontend = subprocess.Popen([sys.executable, "scripts/start.py"], cwd=FRONTEND, env=frontend_env)

        while (api is None or api.poll() is None) and (frontend is None or frontend.poll() is None):
            time.sleep(1)
        return (api.returncode if api is not None else 0) or (frontend.returncode if frontend is not None else 0) or 0
    except KeyboardInterrupt:
        return 0
    except (subprocess.CalledProcessError, RuntimeError) as error:
        print(f"Não foi possível iniciar o FoodDelivery: {error}", file=sys.stderr, flush=True)
        return 1
    finally:
        stop(frontend)
        stop(api)
        if database_started:
            subprocess.run([str(BACKEND / "scripts/stop-db.sh")], cwd=BACKEND,
                           stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=False)


if __name__ == "__main__":
    raise SystemExit(main())
