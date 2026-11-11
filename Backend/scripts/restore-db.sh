#!/usr/bin/env bash
set -euo pipefail
umask 077
if [[ $# -ne 1 || ! -f "$1" || ! -f "$1.sha256" ]]; then
  echo "Uso: RESTORE_CONFIRM_DATABASE=fooddelivery $0 caminho/backup.dump" >&2
  exit 2
fi
case "$1" in
  /*) archive="$1" ;;
  *) archive="$PWD/$1" ;;
esac
# Check the supplied archive itself, not an arbitrary path named by the sidecar.
# Accept the original sha256sum format, including backups moved to another host.
mapfile -t checksum_lines < "$archive.sha256"
if [[ ${#checksum_lines[@]} -ne 1 || ! "${checksum_lines[0]}" =~ ^([a-fA-F0-9]{64})[[:space:]] ]]; then
  echo 'Checksum ausente ou inválido.' >&2
  exit 2
fi
expected="${BASH_REMATCH[1],,}"
actual="$(sha256sum -- "$archive")"
if [[ "${actual:0:64}" != "$expected" ]]; then
  echo 'Recuperação bloqueada: arquivo corrompido ou checksum divergente.' >&2
  exit 2
fi
pg_restore --list "$archive" >/dev/null
. "$(dirname "$0")/database-env.sh"
if [[ "${RESTORE_CONFIRM_DATABASE:-}" != "$DATABASE_NAME" ]]; then
  echo "Recuperação bloqueada. Defina RESTORE_CONFIRM_DATABASE exatamente como o banco de destino ($DATABASE_NAME)." >&2
  exit 2
fi
pg_restore "${db_args[@]}" --clean --if-exists --no-owner --exit-on-error --single-transaction "$archive"
echo 'Recuperação concluída. Reinicie a API e valide saúde e integridade dos dados.'
