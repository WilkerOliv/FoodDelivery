#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
PG_BIN="${PG_BIN:-/usr/lib/postgresql/17/bin}"
"$PG_BIN/pg_ctl" -D "$PWD/.postgres-data" stop -m fast
