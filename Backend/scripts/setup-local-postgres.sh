#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."

PG_BIN="${PG_BIN:-/usr/lib/postgresql/17/bin}"
DATA_DIR="$PWD/.postgres-data"
ENV_FILE="$PWD/.env"

for binary in initdb pg_ctl psql; do
  if [[ ! -x "$PG_BIN/$binary" ]]; then
    echo "Não encontrei $PG_BIN/$binary. Instale o servidor PostgreSQL 17 ou defina PG_BIN." >&2
    exit 1
  fi
done

if [[ ! -f "$ENV_FILE" ]]; then
  umask 077
  ADMIN_PASSWORD="$(openssl rand -hex 24)"
  APP_PASSWORD="$(openssl rand -hex 24)"
  JWT_SECRET_VALUE="$(openssl rand -base64 48 | tr -d '\n')"
  cat > "$ENV_FILE" <<EOF
DB_PORT=5433
DATABASE_URL=jdbc:postgresql://127.0.0.1:5433/fooddelivery
DATABASE_USERNAME=fooddelivery_app
DATABASE_PASSWORD=$APP_PASSWORD
PGADMIN_DB_USER=fooddelivery_admin
PGADMIN_DB_PASSWORD=$ADMIN_PASSWORD
JWT_SECRET=$JWT_SECRET_VALUE
EOF
fi

if ! grep -q '^APP_ADMIN_EMAIL=' "$ENV_FILE"; then
  APP_ADMIN_PASSWORD="$(openssl rand -hex 24)"
  printf '\nAPP_ADMIN_EMAIL=admin@fooddelivery.local\nAPP_ADMIN_PASSWORD=%s\n' "$APP_ADMIN_PASSWORD" >> "$ENV_FILE"
fi
chmod 600 "$ENV_FILE"
echo "Credenciais locais do banco e administrador inicial salvas em Backend/.env (arquivo ignorado pelo Git). Guarde uma cópia segura."

set -a
# shellcheck disable=SC1091
. "$ENV_FILE"
set +a

if [[ ! -f "$DATA_DIR/PG_VERSION" ]]; then
  if [[ -e "$DATA_DIR" ]] && [[ -n "$(ls -A "$DATA_DIR")" ]]; then
    echo "O diretório $DATA_DIR existe, mas não contém um cluster reconhecível; preserve-o e investigue antes de continuar." >&2
    exit 1
  fi
  mkdir -p "$DATA_DIR"
  "$PG_BIN/initdb" -D "$DATA_DIR" -U "$PGADMIN_DB_USER" --pwfile=<(printf '%s\n' "$PGADMIN_DB_PASSWORD") --auth-local=scram-sha-256 --auth-host=scram-sha-256 --encoding=UTF8 --locale=C.UTF-8
fi

if ! "$PG_BIN/pg_ctl" -D "$DATA_DIR" status >/dev/null 2>&1; then
  "$PG_BIN/pg_ctl" -D "$DATA_DIR" -l "$DATA_DIR/server.log" -o "-p $DB_PORT -h 127.0.0.1 -k $DATA_DIR" start
fi

if PGPASSWORD="$PGADMIN_DB_PASSWORD" "$PG_BIN/psql" -h 127.0.0.1 -p "$DB_PORT" -U "$PGADMIN_DB_USER" -d postgres -tAc "SELECT 1 FROM pg_roles WHERE rolname='fooddelivery_app'" | grep -q 1; then
  # Keep the existing local cluster in sync with Backend/.env when credentials rotate.
  PGPASSWORD="$PGADMIN_DB_PASSWORD" "$PG_BIN/psql" -v ON_ERROR_STOP=1 -v app_password="$DATABASE_PASSWORD" -h 127.0.0.1 -p "$DB_PORT" -U "$PGADMIN_DB_USER" -d postgres -f "$PWD/db/update_app_role.sql"
else
  PGPASSWORD="$PGADMIN_DB_PASSWORD" "$PG_BIN/psql" -v ON_ERROR_STOP=1 -v app_password="$DATABASE_PASSWORD" -h 127.0.0.1 -p "$DB_PORT" -U "$PGADMIN_DB_USER" -d postgres -f "$PWD/db/create_app_role.sql"
fi

echo "PostgreSQL local pronto em 127.0.0.1:$DB_PORT; banco: fooddelivery; usuário da aplicação: $DATABASE_USERNAME."
