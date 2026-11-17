#!/usr/bin/env bash
# Ensaio/recuperação a partir da cópia cifrada. Mede o tempo total (RTO do ensaio) ao final.
# Uso: AGE_IDENTITY_FILE=chave.txt RESTORE_CONFIRM_DATABASE=banco [RESTORE_FILES_DIR=pasta] \
#      PRIVATE_ENV_FILE=destino.env bash restore-offsite.sh fooddelivery-....tar.age
set -euo pipefail
umask 077
here="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
if [[ $# -ne 1 || ! -f "$1" || ! -f "$1.sha256" || ! -f "${AGE_IDENTITY_FILE:-/nonexistent}" ]]; then
  echo "Uso: AGE_IDENTITY_FILE=chave.txt RESTORE_CONFIRM_DATABASE=banco $0 copia.tar.age (com .sha256 ao lado)" >&2
  exit 2
fi
case "$1" in /*) archive="$1" ;; *) archive="$PWD/$1" ;; esac
mapfile -t lines < "$archive.sha256"
if [[ ${#lines[@]} -ne 1 || ! "${lines[0]}" =~ ^([a-fA-F0-9]{64})[[:space:]] ]]; then
  echo 'Checksum ausente ou inválido.' >&2
  exit 2
fi
actual="$(sha256sum -- "$archive")"
if [[ "${actual:0:64}" != "${BASH_REMATCH[1],,}" ]]; then
  echo 'Recuperação bloqueada: cópia corrompida ou checksum divergente.' >&2
  exit 2
fi
work="$(mktemp -d)"
trap 'rm -rf -- "$work"' EXIT
age --decrypt --identity "$AGE_IDENTITY_FILE" --output "$work/payload.tar" "$archive"
# Recusa caminhos absolutos, '..' e tipos que não sejam arquivo/diretório antes de extrair.
if tar --list --verbose --file "$work/payload.tar" | awk '{print substr($1,1,1)}' | grep -qv '^[-d]$'; then
  echo 'A cópia contém entradas que não são arquivos comuns; recuperação bloqueada.' >&2
  exit 2
fi
if tar --list --file "$work/payload.tar" | grep -Eq '(^/|(^|/)\.\.(/|$))'; then
  echo 'A cópia contém caminhos inseguros; recuperação bloqueada.' >&2
  exit 2
fi
mkdir "$work/payload"
tar --extract --no-same-owner --file "$work/payload.tar" --directory "$work/payload"
dump="$(jq -er '.dump' "$work/payload/manifest.json")"
if [[ ! "$dump" =~ ^[A-Za-z0-9_][A-Za-z0-9._-]*\.dump$ ]]; then
  echo 'Manifesto inválido.' >&2
  exit 2
fi
bash "$here/restore-db.sh" "$work/payload/db/$dump"
if [[ -n "${RESTORE_FILES_DIR:-}" ]]; then
  mkdir -p "$RESTORE_FILES_DIR"
  find "$work/payload/storage" -type f -exec cp -- {} "$RESTORE_FILES_DIR/" \;
  echo "Arquivos de capa copiados para $RESTORE_FILES_DIR. Envie-os ao bucket do ambiente restaurado."
fi
echo "Ensaio concluído em ${SECONDS}s. Registre este valor como RTO medido e a idade da cópia como RPO."
