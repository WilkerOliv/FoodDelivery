# Banco de produção e recuperação

> [NÃO VERIFICADO] Escopo da revisão de 25/11: instruções e resultados históricos sem evidência individual nesta página aguardam confirmação. Os testes repetidos têm comando e resultado na [matriz de aceite](Aceite_2026-11-25.md).

Revisão de 11/11/2026. Estes procedimentos são para PostgreSQL; o pacote opcional de demonstração H2 usa dados descartáveis.

## Contas separadas

`deploy/compose.production.yaml` usa `fooddelivery_admin` somente para inicialização/administração do PostgreSQL. A API recebe `fooddelivery_app`, proprietária do banco da aplicação para executar Flyway, sem SUPERUSER, CREATEDB, CREATEROLE, REPLICATION ou BYPASSRLS. Essa separação reduz o alcance de uma falha na aplicação, mas não impede que a proprietária altere seu próprio esquema. Separar também a conta de migração da conta de execução é uma evolução adicional.

O arquivo `deploy/postgres/10-app-role.sql` é aplicado **somente na primeira inicialização de um volume vazio**. Configure `PGADMIN_DB_PASSWORD` e `DATABASE_PASSWORD` com valores distintos em ambiente privado. Não publique esses valores nem passe o arquivo privado inteiro ao contêiner web. O Compose sobrescreve `PGADMIN_DB_PASSWORD` com vazio na API. Ainda assim, mantenha no arquivo apontado por `PRIVATE_ENV_FILE` somente os segredos de que a API precisa; esse arquivo é carregado pelo serviço.

**Volume já existente:** mudar o Compose não altera usuários existentes. Não apague o volume para aplicar esta correção. Antes da atualização, um operador com acesso PostgreSQL deve criar/verificar uma conta administrativa separada, testar sua conexão, fazer backup e retirar os cinco privilégios da conta da aplicação. Verifique `pg_roles` e execute as migrações em uma cópia isolada. Sem acesso ao banco de produção, essa etapa não é declarada concluída.

## Backup portátil e privado

Os scripts aceitam `PRIVATE_ENV_FILE` para um arquivo privado confiável, no formato shell. Sem essa variável, usam `Backend/.env`. O arquivo é executado pelo shell: não use um arquivo recebido de fonte desconhecida.

Campos: `DB_HOST` (padrão `127.0.0.1`), `DB_PORT`, `DATABASE_NAME` (padrão `fooddelivery`), `DATABASE_USERNAME`, `DATABASE_PASSWORD` e `BACKUP_DIR` opcional. Para um serviço PostgreSQL remoto, configure também as opções libpq de TLS, incluindo `PGSSLMODE=verify-full` e a CA necessária. Os clientes `pg_dump`/`pg_restore` devem ser compatíveis com a versão do servidor.

```sh
PRIVATE_ENV_FILE=/caminho/privado/banco.env bash Backend/scripts/backup-db.sh
```

- `umask 077`: diretório privado e arquivos acessíveis somente ao proprietário desde sua criação.
- Diretório temporário único: execuções simultâneas não sobrescrevem o mesmo arquivo.
- O arquivo é publicado depois de `pg_dump` e da leitura do catálogo por `pg_restore --list`.
- SHA-256 acompanha o dump. É verificação de integridade acidental, **não assinatura nem criptografia**. Proteja ambos contra alteração.
- Copie dump e `.sha256` juntos. A verificação continua funcionando em outro diretório ou com outro nome, pois compara o hash do dump fornecido, sem abrir o caminho indicado no checksum.
- Falha antes da publicação remove os temporários. Um dump sem checksum não é considerado completo.

## Restauração

Pare a API e os processadores antes de restaurar um banco operacional. A confirmação abaixo é destrutiva para os objetos presentes no dump. Use um banco descartável para ensaios.

```sh
PRIVATE_ENV_FILE=/caminho/privado/destino.env \
RESTORE_CONFIRM_DATABASE=fooddelivery_restore_qa \
bash Backend/scripts/restore-db.sh /copia/backup.dump
```

`DATABASE_NAME` no arquivo deve coincidir exatamente com a confirmação. O script valida hash e formato antes de conectar. `--single-transaction --exit-on-error` desfaz as alterações da restauração se um comando SQL falhar. Ele não desfaz ações de outros processos nem fornece rollback depois de uma restauração concluída. Objetos adicionais que não pertencem ao dump podem permanecer; para uma recuperação limpa, prefira banco vazio previamente criado pelo operador.

Depois: conferir migrações, contagens, amostras de pedidos/reservas, saldo/repasses, acesso das contas e arquivos de capa antes de reabrir a aplicação.

## Recuperação completa: dependências operacionais

O dump não inclui arquivos de capa, configurações privadas, chaves de criptografia ou conta externa do provedor. Para um ponto de recuperação coerente:

1. Suspenda escritas e filas; registre horário UTC e versão publicada.
2. Gere o dump e copie o diretório de capas preservando nomes/permissões.
3. Guarde configuração e chaves em cofre/cópia cifrada separada, com acesso restrito.
4. Envie cópias cifradas para armazenamento externo, defina retenção e verifique a recuperação a partir desse destino.
5. Restaure banco, capas e configuração em ambiente isolado, sem mensagens/cobranças reais; meça perda máxima de dados (RPO) e tempo de recuperação (RTO).

Destino externo, retenção, cifragem, agendamento e ensaio conjunto de banco/arquivos/chaves **ainda precisam de implantação operacional**. Nenhum backup real foi enviado a um serviço externo nesta revisão.

## Cópia externa cifrada (implementada em 17/11/2026; ainda sem execução real)

`Backend/scripts/backup-offsite.sh` gera um pacote único com o dump do schema privado (`fooddelivery`), as capas do bucket privado e um manifesto, cifrado com [age](https://age-encryption.org). A cópia usa só a chave **pública** (`BACKUP_AGE_RECIPIENT`); a identidade privada fica fora do GitHub e do Render. `Backend/scripts/restore-offsite.sh` verifica o SHA-256, decifra, recusa caminhos inseguros, restaura o banco com `restore-db.sh` (que exige a confirmação do banco de destino) e copia as capas para uma pasta. Ao final imprime o tempo gasto, que serve de RTO do ensaio.

O fluxo `.github/workflows/backup-externo.yml` roda todos os dias às 06:17 UTC e sob demanda, e guarda o pacote cifrado como artefato por 30 dias. Esse é o **RPO máximo teórico de 24 horas**; ele só vale depois do primeiro ensaio. Configure estes segredos do repositório:

| Segredo | Conteúdo |
|---|---|
| `BACKUP_DB_HOST`, `BACKUP_DB_PORT` | Endereço do PostgreSQL do Supabase |
| `BACKUP_DB_USER`, `BACKUP_DB_PASSWORD` | Conta restrita `fooddelivery_app.<projeto>`, a mesma do Render |
| `BACKUP_DB_CA` | Certificado CA em PEM (conexão `verify-full`) |
| `SUPABASE_PROJECT_URL`, `SUPABASE_SECRET_KEY` | Para baixar as capas do bucket `store-covers` |
| `BACKUP_AGE_RECIPIENT` | Chave pública gerada com `age-keygen` |

Gere o par de chaves em uma máquina sua: `age-keygen -o chave-backup.txt`. Guarde `chave-backup.txt` em cofre offline, com uma segunda cópia separada, e coloque só a linha `Public key` no segredo. **Sem essa chave privada, a cópia não pode ser recuperada.**

Limites que continuam valendo:
- Os artefatos do GitHub são um destino externo ao Render e ao Supabase, mas não são um cofre independente: expiram em 30 dias e dependem da conta do GitHub. Para retenção longa, copie periodicamente para um terceiro destino sob seu controle.
- `JWT_SECRET`, `PRIVATE_CONFIG_ENCRYPTION_KEY` e as demais chaves **não** entram na cópia. [LIDO NO CÓDIGO] `PrivateCipher.java:15` depende de PRIVATE_CONFIG_ENCRYPTION_KEY para configurações privadas e MFA. Os tokens OAuth do Mercado Pago usam MP_TOKEN_ENCRYPTION_KEY (`MarketplaceTokenCipher.java:40`). JWT_SECRET mantém a validade das sessões assinadas. Guarde-as em cofre separado.
- As capas são copiadas para uma pasta; o envio de volta ao bucket do ambiente restaurado é manual.
- O dump cobre só o schema `fooddelivery`. Usuários do Supabase Auth e outros schemas não são usados pela aplicação e não entram.

Ensaio de restauração (criar um banco descartável vazio, nunca o de produção):

```sh
AGE_IDENTITY_FILE=chave-backup.txt \
PRIVATE_ENV_FILE=/caminho/privado/destino.env \
RESTORE_CONFIRM_DATABASE=fooddelivery_restore_qa \
RESTORE_FILES_DIR=./capas-restauradas \
bash Backend/scripts/restore-offsite.sh fooddelivery-AAAAMMDDTHHMMSSZ-XXXX.tar.age
```

Evidência desta etapa: `scripts/test_offsite_backup.py` (7 testes, com `age` real e PostgreSQL/Storage simulados) cobre cifragem, ausência de segredos nos registros, schema limitado, ida e volta, adulteração, chave errada, confirmação de banco errada, entradas inválidas e caminhos maliciosos. **Nenhuma cópia real foi gerada nem restaurada**; o RPO/RTO reais continuam sem medição.

## Evidência de 11/11

- Nove testes isolados de scripts: permissões, backup movido, checksum de outro arquivo, confirmação incorreta, nome de conexão proibido, dump com falha, nomes únicos, checksum múltiplo e falha de restore.
- PostgreSQL 17 descartável: inicialização pelo SQL de produção, conferência dos cinco privilégios desativados, três testes de integração (migrações/concorrência/isolamento), dump pelos scripts reais e restauração após mover os arquivos.
- Falha SQL provocada por uma view dependente: restore rejeitado, dados anteriores preservados; removido o impedimento, a restauração concluiu e recuperou o snapshot.
- Nenhum banco existente ou dado real foi restaurado/modificado.
