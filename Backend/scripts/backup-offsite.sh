#!/usr/bin/env bash
# Cópia externa cifrada: dump do banco + objetos do bucket privado de capas, cifrados com age.
# Somente a chave PÚBLICA (BACKUP_AGE_RECIPIENT) é necessária aqui; a identidade privada fica offline.
# Chaves da aplicação (JWT_SECRET, PRIVATE_CONFIG_ENCRYPTION_KEY) NÃO entram na cópia: guarde-as em cofre separado.
set -euo pipefail
umask 077
here="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
if [[ ! "${BACKUP_AGE_RECIPIENT:-}" =~ ^age1[a-z0-9]{50,}$ ]]; then
  echo 'Defina BACKUP_AGE_RECIPIENT com a chave pública age (age1...).' >&2
  exit 2
fi
out_dir="${OFFSITE_DIR:-$PWD/offsite}"
mkdir -p "$out_dir"
chmod 700 "$out_dir"
work="$(mktemp -d)"
trap 'rm -rf -- "$work"' EXIT
mkdir -p "$work/payload/db" "$work/payload/storage"

BACKUP_DIR="$work/payload/db" BACKUP_SCHEMA="${BACKUP_SCHEMA:-fooddelivery}" bash "$here/backup-db.sh" >/dev/null

objects=0
if [[ -n "${SUPABASE_PROJECT_URL:-}" ]]; then
  : "${SUPABASE_SECRET_KEY:?Configure SUPABASE_SECRET_KEY para copiar as capas}"
  bucket="${SUPABASE_STORAGE_BUCKET:-store-covers}"
  base="${SUPABASE_PROJECT_URL%/}"
  if [[ ! "$base" =~ ^https://[A-Za-z0-9.-]+$ || ! "$bucket" =~ ^[A-Za-z0-9_-]+$ ]]; then
    echo 'SUPABASE_PROJECT_URL deve ser HTTPS sem caminho e o bucket um nome simples.' >&2
    exit 2
  fi
  # A chave vai por arquivo de cabeçalhos, nunca na linha de comando visível a outros processos.
  printf 'Authorization: Bearer %s\napikey: %s\n' "$SUPABASE_SECRET_KEY" "$SUPABASE_SECRET_KEY" > "$work/auth"
  offset=0
  while :; do
    curl --fail --silent --show-error --proto '=https' --max-time 60 -X POST \
      -H "@$work/auth" -H 'Content-Type: application/json' \
      --data "{\"prefix\":\"\",\"limit\":1000,\"offset\":$offset}" \
      --output "$work/list.json" "$base/storage/v1/object/list/$bucket"
    names="$(jq -r '.[] | select(.id != null) | .name' "$work/list.json")"
    count="$(jq 'length' "$work/list.json")"
    while IFS= read -r name; do
      [[ -z "$name" ]] && continue
      if [[ ! "$name" =~ ^[A-Za-z0-9][A-Za-z0-9._-]{0,200}$ ]]; then
        echo 'Nome de objeto inesperado no bucket; cópia interrompida para revisão.' >&2
        exit 3
      fi
      curl --fail --silent --show-error --proto '=https' --max-time 120 -H "@$work/auth" \
        --output "$work/payload/storage/$name" "$base/storage/v1/object/$bucket/$name"
      objects=$((objects + 1))
    done <<< "$names"
    (( count < 1000 )) && break
    offset=$((offset + 1000))
  done
  rm -f "$work/auth" "$work/list.json"
fi

dump="$(cd "$work/payload/db" && ls -- *.dump)"
jq -n --arg created "$(date -u +%Y-%m-%dT%H:%M:%SZ)" --arg dump "$dump" \
  --arg schema "${BACKUP_SCHEMA:-fooddelivery}" --argjson objects "$objects" \
  '{created_at_utc:$created, dump:$dump, schema:$schema, storage_objects:$objects, format:1}' > "$work/payload/manifest.json"

stamp="$(date -u +%Y%m%dT%H%M%SZ)"
name="fooddelivery-${stamp}-${work##*.}.tar.age"
tar --create --file "$work/payload.tar" --directory "$work/payload" .
age --recipient "$BACKUP_AGE_RECIPIENT" --output "$work/$name" "$work/payload.tar"
(cd "$work" && sha256sum "$name" > "$name.sha256")
mv -- "$work/$name" "$out_dir/$name"
mv -- "$work/$name.sha256" "$out_dir/$name.sha256"
printf 'Cópia cifrada: %s (objetos de capa: %s)\n' "$out_dir/$name" "$objects"
