#!/usr/bin/env bash
# Shared by backup/restore. Source only a trusted, operator-owned shell env file.
backend_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
private_env="${PRIVATE_ENV_FILE:-$backend_dir/.env}"
if [[ ! -f "$private_env" ]]; then
  echo 'Arquivo privado não encontrado. Defina PRIVATE_ENV_FILE.' >&2
  exit 2
fi
set -a
. "$private_env"
set +a
: "${DB_PORT:?Configure DB_PORT no arquivo privado}"
: "${DATABASE_USERNAME:?Configure DATABASE_USERNAME no arquivo privado}"
: "${DATABASE_PASSWORD:?Configure DATABASE_PASSWORD no arquivo privado}"
DATABASE_NAME="${DATABASE_NAME:-fooddelivery}"
DB_HOST="${DB_HOST:-127.0.0.1}"
# libpq treats '=' and URI prefixes in dbname as connection strings. Never allow
# a database confirmation to be redirected to a different connection this way.
if [[ ! "$DATABASE_NAME" =~ ^[A-Za-z0-9_][A-Za-z0-9_-]*$ ]]; then
  echo 'DATABASE_NAME deve ser um nome simples de banco, não uma URL.' >&2
  exit 2
fi
export PGPASSWORD="$DATABASE_PASSWORD"
export PGCONNECT_TIMEOUT="${PGCONNECT_TIMEOUT:-10}"
db_args=(--host="$DB_HOST" --port="$DB_PORT" --username="$DATABASE_USERNAME" --dbname="$DATABASE_NAME" --no-password)
