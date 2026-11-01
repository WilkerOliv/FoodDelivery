#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

if [[ $# -ne 1 || ! -f "$1" || ! -f "$1.sha256" ]]; then
  echo "Uso: RESTORE_CONFIRM_DATABASE=fooddelivery $0 caminho/backup.dump" >&2
  exit 2
fi
set -a
. ./.env
set +a
DATABASE_NAME="${DATABASE_NAME:-fooddelivery}"
if [[ "${RESTORE_CONFIRM_DATABASE:-}" != "$DATABASE_NAME" ]]; then
  echo "Recuperação bloqueada. Defina RESTORE_CONFIRM_DATABASE exatamente como o banco de destino ($DATABASE_NAME)." >&2
  exit 2
fi
sha256sum --check "$1.sha256"
: "${DB_PORT:?Configure DB_PORT em Backend/.env}"
: "${DATABASE_USERNAME:?Configure DATABASE_USERNAME em Backend/.env}"
: "${DATABASE_PASSWORD:?Configure DATABASE_PASSWORD em Backend/.env}"
export PGPASSWORD="$DATABASE_PASSWORD"
pg_restore --host=127.0.0.1 --port="$DB_PORT" --username="$DATABASE_USERNAME" \
  --dbname="$DATABASE_NAME" --clean --if-exists --no-owner --exit-on-error "$1"
echo "Recuperação concluída. Reinicie a API e valide /actuator/health e a integridade dos dados."
