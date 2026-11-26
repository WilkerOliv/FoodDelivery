# Banco e capas persistentes com plano gratuito

> [NÃO VERIFICADO] Escopo da revisão de 25/11: instruções e resultados históricos sem evidência individual nesta página aguardam confirmação. Os testes repetidos têm comando e resultado na [matriz de aceite](Aceite_2026-11-25.md).

Atualizado em 16/11/2026. **Conta Supabase criada, conexão SQL com TLS confirmada, schema privado e Storage configurados. Publicação persistente em validação.** As credenciais ficam no ambiente privado do Render e em arquivo local protegido fora do repositório; não são versionadas.

## Escolha e limites

A opção preparada é Supabase Free: PostgreSQL e um bucket privado para as capas. A tabela oficial consultada oferece 500 MB de banco, 1 GB de arquivos e 5 GB de saída; projetos gratuitos podem pausar após uma semana de inatividade. As franquias e políticas precisam ser conferidas antes de contratar/ativar. Não representa disponibilidade garantida para uma operação comercial. Fonte: [planos Supabase](https://supabase.com/pricing).

A demonstração isolada com H2 continua disponível em `deploy/Dockerfile.homologacao`. Seu inicializador ignora credenciais externas deliberadamente. O Blueprint `render.yaml` passa a utilizar `deploy/Dockerfile.persistente`, que conecta PostgreSQL e Storage externos. Alterar variáveis sem publicar a imagem persistente não muda o modo do processo em execução. O plano gratuito também não elimina taxas do Mercado Pago nem custos eventuais de SMTP/WhatsApp.

## 1. Conta e projeto

1. Acesse [Supabase](https://supabase.com/dashboard), crie a conta e ative autenticação em dois fatores.
2. Crie uma organização no plano Free e um projeto exclusivo para FoodDelivery. Selecione uma região próxima dos usuários.
3. Gere uma senha forte de banco e guarde-a em um gerenciador. Não coloque senhas, chaves ou strings completas de conexão em mensagens, prints ou Git.
4. Aguarde o banco ficar disponível. O projeto utilizado nesta implantação já está ativo no plano Free, região East US (North Virginia).

## 2. Separação do banco

A aplicação continua acessando PostgreSQL pelo backend Java. Não utiliza o Supabase Auth nem entrega acesso SQL ao navegador.

Use o script `deploy/postgres/supabase-private-schema.sql` em um **projeto novo**, conectado como administrador pelo `psql`. Ele cria a conta limitada `fooddelivery_app`, sem superusuário, criação de banco, criação de papéis, replicação ou bypass de RLS; cria o schema `fooddelivery`, retira acesso de PUBLIC ao schema e execução pública de novas funções. Falha se já houver objetos com esses nomes, sem substituir contas existentes.

Se o usuário já foi criado pelo painel, use `deploy/postgres/supabase-existing-role.sql`: valida que somente login está permitido, concede ao administrador existente `postgres` a capacidade de assumir esse papel e cria o schema privado. Não altera senhas. Essa concessão é necessária porque o administrador gerenciado do Supabase não é superusuário.

No script de criação via psql, a senha do aplicativo é lida de `APP_DATABASE_PASSWORD`. Exemplo de entrada privada no terminal:

```bash
read -r -s -p 'Nova senha aleatória do aplicativo (mínimo 24 caracteres): ' APP_DATABASE_PASSWORD
export APP_DATABASE_PASSWORD
# Conecte usando os dados exibidos em Connect; o psql solicitará a senha administrativa.
psql 'host=HOST_EXIBIDO_NO_PAINEL port=5432 dbname=postgres user=USUARIO_ADMIN_DO_PAINEL sslmode=verify-full sslrootcert=CAMINHO_DO_CERTIFICADO' -W -f deploy/postgres/supabase-private-schema.sql
unset APP_DATABASE_PASSWORD
```

Não adicione `fooddelivery` aos **Exposed schemas** da Data API, nem conceda acesso a `anon`/`authenticated`. Não copie instruções de exposição pública de schemas para este projeto. A autorização da aplicação é feita pela API Java. [Documentação de schemas](https://supabase.com/docs/guides/api/using-custom-schemas).

Em rede IPv4, use o pooler **Session**, com host/porta fornecidos em Connect; não use Transaction para migrações/JDBC persistente. O usuário personalizado no pooler segue o formato informado pelo fornecedor, normalmente `fooddelivery_app.REFERENCIA_DO_PROJETO`. Para conexão direta, o usuário é `fooddelivery_app`. Confirme no painel; não copie nomes de exemplo. [Conexão PostgreSQL](https://supabase.com/docs/guides/database/connecting-to-postgres).

Depois de trocar uma senha, o pooler pode recusar temporariamente a credencial nova por cache. Confirme usuário, projeto e salvamento; tente novamente com poucas tentativas espaçadas. Evite redefinir a senha repetidamente ou iniciar um ciclo agressivo de reconexões. Quando houver IPv6 disponível, uma conexão direta permite distinguir a senha incorreta do atraso do pooler. Em 16/11, a conexão deste projeto passou após nova tentativa, sem nova troca de senha. [Diagnóstico oficial do Supabase](https://supabase.com/kb/troubleshooting/supavisor-error-password-authentication-failed-after-password-rotation).

Configuração privada do backend:

```dotenv
DATABASE_URL=jdbc:postgresql://HOST:5432/postgres?sslmode=verify-full&sslrootcert=/run/secrets/supabase-ca.crt
DATABASE_USERNAME=USUARIO_DO_APLICATIVO_NO_TIPO_DE_CONEXAO_ESCOLHIDO
DATABASE_PASSWORD=SENHA_PRIVADA_DO_APLICATIVO
DATABASE_SCHEMA=fooddelivery
DATABASE_POOL_SIZE=5
```

No Render, adicione o certificado CA como Secret File chamado `supabase-ca.crt` e use `sslrootcert=/etc/secrets/supabase-ca.crt` na URL. Em outro ambiente, monte o certificado CA oferecido pelo fornecedor no caminho indicado. Não desative a validação TLS. Hikari aplica o schema em cada conexão; Flyway migra esse mesmo schema. O padrão `public` continua compatível com instalações anteriores. Alterar `DATABASE_SCHEMA` **não move dados existentes**: não aponte uma base preenchida para outro schema como se fosse uma migração.

## 3. Armazenamento privado das capas

1. Em Storage, crie o bucket `store-covers`, **privado**.
2. Limite uploads a `image/png` e 5 MB. O vendedor pode selecionar JPG/PNG na interface; o frontend enquadra e o servidor valida/regrava PNG de 1200 × 600 pixels.
3. Guarde a URL do projeto e uma chave secreta `sb_secret_...` apenas no ambiente privado do servidor. O adaptador envia essa chave somente no cabeçalho `apikey`. A compatibilidade com a chave JWT legada `service_role` permanece disponível, com `apikey` e `Authorization`, para instalações existentes. Nunca use a chave pública para substituir a credencial do backend.
4. Configure:

```dotenv
STORE_ASSETS_PROVIDER=supabase
SUPABASE_PROJECT_URL=https://REFERENCIA.supabase.co
SUPABASE_STORAGE_BUCKET=store-covers
SUPABASE_SECRET_KEY=CHAVE_PRIVADA_SB_SECRET
```

A chave tem privilégios elevados no serviço: não vai para HTML, JavaScript, formulário de vendedor ou retorno da API. [Controle de acesso do Storage](https://supabase.com/docs/guides/storage/security/access-control).

As URLs públicas do FoodDelivery permanecem `/api/v1/store-assets/...`. O backend verifica se a capa está aprovada e se a loja pode aparecer. As prévias continuam limitadas ao dono e ao administrador. O bucket permanece privado inclusive para capas aprovadas, pois o backend transmite a imagem autorizada.

Trocar de `local` para `supabase` **não copia imagens antigas**. Antes da troca, exporte e copie os arquivos mantendo exatamente os nomes, confira contagem/hash e preserve os originais para reversão. Nenhum arquivo local foi movido nesta entrega. Banco e arquivos precisam corresponder.

## 4. Publicação sem confundir teste com operação

Primeiro faça um piloto separado, em PostgreSQL de teste, usando `postgres` e Mercado Pago sandbox. As mesmas regras de negócio são utilizadas, mas sem ativar cobranças reais. Não misture `homologacao`/`dev-h2` com Storage externo: a aplicação rejeita essa combinação.

O Blueprint `render.yaml` utiliza `deploy/Dockerfile.persistente`: Java e Caddy no mesmo contêiner não privilegiado, uma origem HTTPS pública e PostgreSQL/Storage externos. O Render termina HTTPS; o Caddy atende a porta interna e encaminha `/api/*` para Java em loopback. O supervisor encerra ambos os processos se um falhar. `/healthz` inclui a prontidão da aplicação e a conexão ao banco.

O inicializador exige `postgres`, conta SQL restrita, schema privado, TLS `verify-full`, certificado CA legível e chaves estáveis. Força cookies Secure, origem pública exata, Mercado Pago sandbox e assinatura de teste desativada. Não cria contas ou lojas fictícias. O administrador inicial vem de `APP_ADMIN_EMAIL` e `APP_ADMIN_PASSWORD` (mínimo 16 caracteres); dados de bootstrap não substituem uma conta que já existe.

O pacote separado de produção continua usando `deploy/Dockerfile.web`, `Backend/Dockerfile` e `deploy/compose.production.yaml`, com PostgreSQL próprio. Não confundir essas opções de publicação. O piloto persistente não representa homologação de pagamentos reais.

Ainda é necessário validar, com a conta criada: conexão com TLS, migrações no schema privado, envio/leitura/moderação da capa, persistência após reinício, origem HTTPS/cookies, inicialização administrativa, backup externo e restauração conjunta. O teste local de PostgreSQL não confirma as permissões/limites da conta Supabase.

## 5. Recuperação

- Faça cópia externa cifrada do schema `fooddelivery`, dos objetos do bucket e das chaves de configuração necessárias. Um dump SQL não contém os bytes das imagens.
- Em banco Supabase compartilhado com schemas gerenciados, use `pg_dump --schema=fooddelivery --format=custom --no-owner` com conta limitada. Os scripts genéricos atuais de backup são validados para banco dedicado; não os execute sobre todos os schemas gerenciados como administrador.
- Guarde checksum e registre data, versão do aplicativo e quantidade de objetos. Teste restauração em destino descartável e meça o tempo real de recuperação.
- Não prometa recuperação automática apenas por usar um banco remoto. Retenção, cópia fora da conta e ensaio conjunto continuam pendentes de ativação.

## Evidências e limites desta entrega

Testados localmente: adaptador de disco, contrato HTTP simulado do Storage, limite de download, rejeição de endpoint inseguro, isolamento da demonstração; PostgreSQL real descartável com schema privado, concorrência, backup e restauração com rollback após falha.

Testado com Supabase real em 15/11/2026: envio de PNG sintético, leitura autenticada com bytes idênticos, recusa de acesso público ao bucket e remoção do objeto de teste. O schema `fooddelivery` pertence a `fooddelivery_app`, sem privilégios administrativos.

A conexão SQL, o login administrativo e a publicação persistente foram registrados em 16–17/11. Isso não confirma a versão publicada hoje. Consulte o [estado de validação](Aceite_2026-11-25.md) para os resultados e pendências: jornada de capas, sessão após nova publicação, pagamentos e restauração externa conjunta. Não houve contratação de plano pago nesta revisão.
