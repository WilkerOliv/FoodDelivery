#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
PG_BIN="${PG_BIN:-/usr/lib/postgresql/17/bin}"
set -a
# shellcheck disable=SC1091
. ./.env
set +a
if ! "$PG_BIN/pg_ctl" -D "$PWD/.postgres-data" status >/dev/null 2>&1; then
  "$PG_BIN/pg_ctl" -D "$PWD/.postgres-data" -l "$PWD/.postgres-data/server.log" -o "-p $DB_PORT -h 127.0.0.1 -k $PWD/.postgres-data" start
else
  echo "PostgreSQL já está em execução na porta $DB_PORT."
fi
