#!/usr/bin/env python3
"""Start and supervise the local database, API, and web app together."""

from __future__ import annotations

import os
import subprocess
import sys
import time
import urllib.request
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
BACKEND = ROOT / "Backend"
FRONTEND = ROOT / "Frontend"


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
        pg_bin = Path(os.environ.get("PG_BIN", "/usr/lib/postgresql/17/bin"))
        status = subprocess.run([str(pg_bin / "pg_ctl"), "-D", str(BACKEND / ".postgres-data"), "status"],
                                cwd=BACKEND, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL, check=False)
        database_was_running = status.returncode == 0
        subprocess.run([str(BACKEND / "scripts/start-db.sh")], cwd=BACKEND, check=True)
        database_started = not database_was_running
        api = subprocess.Popen([str(BACKEND / "scripts/run-api.sh")], cwd=BACKEND)
        deadline = time.monotonic() + 90
        while time.monotonic() < deadline:
            if api.poll() is not None:
                raise RuntimeError("A API foi encerrada antes de ficar disponível. Confira a configuração do PostgreSQL e o log acima.")
            try:
                with urllib.request.urlopen("http://127.0.0.1:8080/api/v1/status", timeout=2) as response:
                    if response.status == 200:
                        break
            except Exception:
                time.sleep(1)
        else:
            raise RuntimeError("A API não respondeu em 90 segundos. Confira o log acima.")

        frontend = subprocess.Popen([sys.executable, "scripts/start.py"], cwd=FRONTEND)
        print("FoodDelivery disponível em http://127.0.0.1:5173", flush=True)
        print("API disponível em http://127.0.0.1:8080/api/v1/status", flush=True)
        while api.poll() is None and frontend.poll() is None:
            time.sleep(1)
        return api.returncode or frontend.returncode or 0
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
