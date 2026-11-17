#!/usr/bin/env bash
set -euo pipefail
umask 077
. "$(dirname "$0")/database-env.sh"
BACKUP_DIR="${BACKUP_DIR:-$backend_dir/backups}"
mkdir -p "$BACKUP_DIR"
chmod 700 "$BACKUP_DIR"
# A private, unique directory prevents concurrent runs overwriting one another.
staging="$(mktemp -d "$BACKUP_DIR/.backup-XXXXXXXX")"
trap 'rm -rf -- "$staging"' EXIT
archive_name="${DATABASE_NAME}-$(date -u +%Y%m%dT%H%M%SZ)-${staging##*-}.dump"
# Um serviço gerenciado costuma limitar a conta da aplicação a um schema privado.
schema_args=()
if [[ -n "${BACKUP_SCHEMA:-}" ]]; then
  if [[ ! "$BACKUP_SCHEMA" =~ ^[A-Za-z_][A-Za-z0-9_]*$ ]]; then
    echo 'BACKUP_SCHEMA deve ser um nome simples de schema.' >&2
    exit 2
  fi
  schema_args=(--schema="$BACKUP_SCHEMA")
fi
pg_dump "${db_args[@]}" ${schema_args[@]+"${schema_args[@]}"} --format=custom --no-owner --file="$staging/$archive_name"
pg_restore --list "$staging/$archive_name" >/dev/null
(cd "$staging" && sha256sum "$archive_name" > "$archive_name.sha256")
# Publish only a complete, readable archive. Missing checksum means incomplete.
mv -- "$staging/$archive_name" "$BACKUP_DIR/$archive_name"
mv -- "$staging/$archive_name.sha256" "$BACKUP_DIR/$archive_name.sha256"
printf 'Backup validado: %s\n' "$BACKUP_DIR/$archive_name"
