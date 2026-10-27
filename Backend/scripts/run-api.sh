#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
./scripts/start-db.sh
set -a
# shellcheck disable=SC1091
. ./.env
set +a
exec mvn spring-boot:run
