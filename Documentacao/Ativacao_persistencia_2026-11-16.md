# Ativação da persistência — 16/11/2026

> Registro histórico. A publicação persistente foi confirmada em 17/11; consulte [resultado e limites atuais](Publicacao_persistente_2026-11-17.md).

## Estado comprovado

- Supabase provisionado, schema `fooddelivery` pertencente a `fooddelivery_app`.
- Conta SQL sem superusuário, criação de banco/papéis ou bypass de RLS. `anon` e `authenticated` não têm USAGE no schema.
- Conexão PostgreSQL real autenticada com validação de certificado e hostname (`verify-full`).
- Aplicação Java inicializada contra o Supabase, migrações executadas durante a inicialização e login administrativo confirmado pela API. Cookie com Secure e HttpOnly. Não foram criadas contas fictícias por seed.
- Bucket `store-covers` privado: envio de PNG sintético, leitura autenticada idêntica, acesso público recusado e limpeza do objeto confirmados em 15/11.
- Contêiner persistente validado novamente em 16/11 com PostgreSQL descartável, TLS, migrações, login, rejeição de origem externa, preservação de conta/sessão após reinício e recuperação após queda do banco.
- As credenciais não estão no Git. O certificado público fica no Secret File do Render; a configuração privada local fica fora do repositório, com arquivo restrito ao proprietário.

## Arquitetura entregue

`deploy/Dockerfile.persistente` compila Java e empacota somente os arquivos públicos necessários da interface. Executa Caddy e Java como usuário não privilegiado. `deploy/Caddyfile.persistente` encaminha a API para loopback, restringe tamanho de requisições, define cabeçalhos de segurança e mantém arquivos de teste fora da publicação.

`deploy/persistente.py` valida origem HTTPS, conta/schema SQL, certificado, tamanho de senhas e chaves antes de iniciar os processos. O proxy não recebe as credenciais do banco. Se um processo falha, o outro é encerrado para que a plataforma detecte a falha. `/healthz` exige prontidão e conexão ao banco. Mercado Pago permanece em sandbox e o simulador de demonstração fica desligado.

`SupabaseStoreImageStorage` usa `apikey` para chaves secretas atuais; Bearer permanece apenas para a chave JWT legada. Capas não ganham URL pública do bucket: a autorização continua na API Java. A suíte de contrato valida os cabeçalhos e rejeita endpoints não permitidos.

O SQL de provisionamento concede ao administrador existente a possibilidade de assumir o papel limitado para atribuir o schema. Não concede privilégios administrativos à aplicação. Migrações permanecem sob Flyway; não foi introduzido um segundo histórico de migração pela CLI Supabase.

## Ocorrências verificadas

Após a correção das credenciais, o pooler recusou inicialmente a senha e depois aceitou uma tentativa espaçada, sem nova troca. Isso é compatível com o cache documentado pelo fornecedor.

Na retomada posterior, a aplicação e o psql receberam `Connection reset` durante TLS. Um teste separado confirmou que a porta responde ao pedido SSL, mas encerra o handshake antes da autenticação. Portanto, essa ocorrência não comprova senha incorreta. A validação TLS foi mantida. A causa de rede/provedor ainda não foi isolada.

## Pendências exatas

1. Publicar a imagem persistente no Render e confirmar conexão a partir daquela rede. O serviço conferido anteriormente ainda usa o Dockerfile da demonstração.
2. Conferir login pelo navegador publicado, contagem final das migrações e preservação de dados após reinício no Render. O resultado do PostgreSQL descartável não substitui essa evidência.
3. Validar o fluxo completo de envio/moderação de capa pela interface publicada. O teste direto de Storage não substitui essa jornada.
4. Ativar backup externo cifrado e ensaiar restauração conjunta de banco, imagens e chaves. Persistência remota não é backup.
5. Homologar pagamentos e entrega de mensagens com os respectivos fornecedores antes da operação comercial.

O novo pacote abre o login/catálogo do site diretamente, substituindo a barreira adicional de avaliador da demonstração. As áreas privadas continuam exigindo autenticação e autorização do backend. Essa mudança de exposição deve ser confirmada antes de aplicar a publicação pelo navegador.

## Reprodução sem credenciais reais

```sh
python3 scripts/test_persistent_deploy.py
docker build -f deploy/Dockerfile.persistente -t fooddelivery-persistent:qa .
python3 scripts/test_persistent_deploy.py --container
mvn -q -f Backend/pom.xml -Dtest=StoreImageStorageTest test
```

O teste de contêiner cria banco, rede e senhas descartáveis e remove seus recursos ao terminar. Não lê o arquivo privado do piloto. O workflow `Project quality` executa a mesma verificação em CI.
