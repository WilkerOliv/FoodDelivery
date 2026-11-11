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
pg_dump "${db_args[@]}" --format=custom --no-owner --file="$staging/$archive_name"
pg_restore --list "$staging/$archive_name" >/dev/null
(cd "$staging" && sha256sum "$archive_name" > "$archive_name.sha256")
# Publish only a complete, readable archive. Missing checksum means incomplete.
mv -- "$staging/$archive_name" "$BACKUP_DIR/$archive_name"
mv -- "$staging/$archive_name.sha256" "$BACKUP_DIR/$archive_name.sha256"
printf 'Backup validado: %s\n' "$BACKUP_DIR/$archive_name"
