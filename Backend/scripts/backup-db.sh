#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

set -a
. ./.env
set +a

: "${DB_PORT:?Configure DB_PORT em Backend/.env}"
: "${DATABASE_USERNAME:?Configure DATABASE_USERNAME em Backend/.env}"
: "${DATABASE_PASSWORD:?Configure DATABASE_PASSWORD em Backend/.env}"
DATABASE_NAME="${DATABASE_NAME:-fooddelivery}"
BACKUP_DIR="${BACKUP_DIR:-$PWD/backups}"
mkdir -p "$BACKUP_DIR"
chmod 700 "$BACKUP_DIR"
timestamp="$(date -u +%Y%m%dT%H%M%SZ)"
archive="$BACKUP_DIR/${DATABASE_NAME}-${timestamp}.dump"

export PGPASSWORD="$DATABASE_PASSWORD"
pg_dump --host=127.0.0.1 --port="$DB_PORT" --username="$DATABASE_USERNAME" \
  --dbname="$DATABASE_NAME" --format=custom --no-owner --file="$archive"
pg_restore --list "$archive" >/dev/null
sha256sum "$archive" > "$archive.sha256"
chmod 600 "$archive" "$archive.sha256"
printf 'Backup validado: %s\n' "$archive"
