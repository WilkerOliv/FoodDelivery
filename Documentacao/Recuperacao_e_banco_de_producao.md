# Banco de produção e recuperação

Revisão de 11/11/2026. Estes procedimentos são para PostgreSQL; a demonstração H2 do Render continua descartável.

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

## Evidência de 11/11

- Nove testes isolados de scripts: permissões, backup movido, checksum de outro arquivo, confirmação incorreta, nome de conexão proibido, dump com falha, nomes únicos, checksum múltiplo e falha de restore.
- PostgreSQL 17 descartável: inicialização pelo SQL de produção, conferência dos cinco privilégios desativados, três testes de integração (migrações/concorrência/isolamento), dump pelos scripts reais e restauração após mover os arquivos.
- Falha SQL provocada por uma view dependente: restore rejeitado, dados anteriores preservados; removido o impedimento, a restauração concluiu e recuperou o snapshot.
- Nenhum banco existente ou dado real foi restaurado/modificado.
