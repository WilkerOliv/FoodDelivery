# Banco e capas persistentes com plano gratuito

Atualizado em 14/11/2026. **Preparado no código, ainda não conectado a uma conta externa.** O proprietário informou que ainda não possui conta para banco/arquivos. Não existem credenciais de Supabase no projeto.

## Escolha e limites

A opção preparada é Supabase Free: PostgreSQL e um bucket privado para as capas. A tabela oficial consultada oferece 500 MB de banco, 1 GB de arquivos e 5 GB de saída; projetos gratuitos podem pausar após uma semana de inatividade. As franquias e políticas precisam ser conferidas antes de contratar/ativar. Não representa disponibilidade garantida para uma operação comercial. Fonte: [planos Supabase](https://supabase.com/pricing).

O Render atual continua sendo **demonstração isolada com H2**. Seu inicializador ignora credenciais externas deliberadamente. Colocar uma URL PostgreSQL nesse serviço não o transforma em produção. O plano gratuito também não elimina taxas do Mercado Pago nem custos eventuais de SMTP/WhatsApp.

## 1. Cadastro que falta ao proprietário

1. Acesse [Supabase](https://supabase.com/dashboard), crie a conta e ative autenticação em dois fatores.
2. Crie uma organização no plano Free e um projeto exclusivo para FoodDelivery. Selecione uma região próxima dos usuários.
3. Gere uma senha forte de banco e guarde-a em um gerenciador. Não coloque senhas, chaves ou strings completas de conexão em mensagens, prints ou Git.
4. Aguarde o banco ficar disponível. A ação que falta informar ao desenvolvimento é apenas: **“Projeto Supabase criado”**.

## 2. Separação do banco

A aplicação continua acessando PostgreSQL pelo backend Java. Não utiliza o Supabase Auth nem entrega acesso SQL ao navegador.

Use o script `deploy/postgres/supabase-private-schema.sql` em um **projeto novo**, conectado como administrador pelo `psql`. Ele cria a conta limitada `fooddelivery_app`, sem superusuário, criação de banco, criação de papéis, replicação ou bypass de RLS; cria o schema `fooddelivery`, retira acesso de PUBLIC ao schema e execução pública de novas funções. Falha se já houver objetos com esses nomes, sem substituir contas existentes.

A senha do aplicativo é lida de `APP_DATABASE_PASSWORD`. Exemplo de entrada privada no terminal:

```bash
read -r -s -p 'Nova senha aleatória do aplicativo (mínimo 24 caracteres): ' APP_DATABASE_PASSWORD
export APP_DATABASE_PASSWORD
# Conecte usando os dados exibidos em Connect; o psql solicitará a senha administrativa.
psql 'host=HOST_EXIBIDO_NO_PAINEL port=5432 dbname=postgres user=USUARIO_ADMIN_DO_PAINEL sslmode=verify-full sslrootcert=CAMINHO_DO_CERTIFICADO' -W -f deploy/postgres/supabase-private-schema.sql
unset APP_DATABASE_PASSWORD
```

Não adicione `fooddelivery` aos **Exposed schemas** da Data API, nem conceda acesso a `anon`/`authenticated`. Não copie instruções de exposição pública de schemas para este projeto. A autorização da aplicação é feita pela API Java. [Documentação de schemas](https://supabase.com/docs/guides/api/using-custom-schemas).

Em rede IPv4, use o pooler **Session**, com host/porta fornecidos em Connect; não use Transaction para migrações/JDBC persistente. O usuário personalizado no pooler segue o formato informado pelo fornecedor, normalmente `fooddelivery_app.REFERENCIA_DO_PROJETO`. Para conexão direta, o usuário é `fooddelivery_app`. Confirme no painel; não copie nomes de exemplo. [Conexão PostgreSQL](https://supabase.com/docs/guides/database/connecting-to-postgres).

Configuração privada do backend:

```dotenv
DATABASE_URL=jdbc:postgresql://HOST:5432/postgres?sslmode=verify-full&sslrootcert=/run/secrets/supabase-ca.crt
DATABASE_USERNAME=USUARIO_DO_APLICATIVO_NO_TIPO_DE_CONEXAO_ESCOLHIDO
DATABASE_PASSWORD=SENHA_PRIVADA_DO_APLICATIVO
DATABASE_SCHEMA=fooddelivery
DATABASE_POOL_SIZE=5
```

Monte o certificado CA oferecido pelo fornecedor no caminho indicado. Não desative a validação TLS. Hikari aplica o schema em cada conexão; Flyway migra esse mesmo schema. O padrão `public` continua compatível com instalações anteriores. Alterar `DATABASE_SCHEMA` **não move dados existentes**: não aponte uma base preenchida para outro schema como se fosse uma migração.

## 3. Armazenamento privado das capas

1. Em Storage, crie o bucket `store-covers`, **privado**.
2. Limite uploads a `image/png` e 5 MB. O vendedor pode selecionar JPG/PNG na interface; o frontend enquadra e o servidor valida/regrava PNG de 1200 × 600 pixels.
3. Guarde a URL do projeto e a chave legada `service_role` apenas no ambiente privado do servidor. O adaptador atual usa essa chave JWT; não substitua por chave pública `anon` nem por outro formato sem validar compatibilidade.
4. Configure:

```dotenv
STORE_ASSETS_PROVIDER=supabase
SUPABASE_PROJECT_URL=https://REFERENCIA.supabase.co
SUPABASE_STORAGE_BUCKET=store-covers
SUPABASE_SERVICE_ROLE_KEY=CHAVE_PRIVADA_SERVICE_ROLE
```

A chave tem privilégios elevados no serviço: não vai para HTML, JavaScript, formulário de vendedor ou retorno da API. [Controle de acesso do Storage](https://supabase.com/docs/guides/storage/security/access-control).

As URLs públicas do FoodDelivery permanecem `/api/v1/store-assets/...`. O backend verifica se a capa está aprovada e se a loja pode aparecer. As prévias continuam limitadas ao dono e ao administrador. O bucket permanece privado inclusive para capas aprovadas, pois o backend transmite a imagem autorizada.

Trocar de `local` para `supabase` **não copia imagens antigas**. Antes da troca, exporte e copie os arquivos mantendo exatamente os nomes, confira contagem/hash e preserve os originais para reversão. Nenhum arquivo local foi movido nesta entrega. Banco e arquivos precisam corresponder.

## 4. Publicação sem confundir teste com operação

Primeiro faça um piloto separado, em PostgreSQL de teste, usando `postgres` e Mercado Pago sandbox. As mesmas regras de negócio são utilizadas, mas sem ativar cobranças reais. Não misture `homologacao`/`dev-h2` com Storage externo: a aplicação rejeita essa combinação.

O pacote web de produção usa `deploy/Dockerfile.web` e o backend usa `Backend/Dockerfile`. A composição existente `deploy/compose.production.yaml` provisiona **PostgreSQL próprio** e substitui as variáveis de conexão; não serve para apontar ao Supabase sem adaptação explícita do ambiente. O Blueprint `render.yaml` continua exclusivo da demonstração. A publicação persistente precisa de configuração própria de hospedagem/proxy, mesmo que use um endereço gratuito do provedor. Não reaproveite o inicializador de demonstração.

Ainda é necessário validar, com a conta criada: conexão com TLS, migrações no schema privado, envio/leitura/moderação da capa, persistência após reinício, origem HTTPS/cookies, inicialização administrativa, backup externo e restauração conjunta. O teste local de PostgreSQL não confirma as permissões/limites da conta Supabase.

## 5. Recuperação

- Faça cópia externa cifrada do schema `fooddelivery`, dos objetos do bucket e das chaves de configuração necessárias. Um dump SQL não contém os bytes das imagens.
- Em banco Supabase compartilhado com schemas gerenciados, use `pg_dump --schema=fooddelivery --format=custom --no-owner` com conta limitada. Os scripts genéricos atuais de backup são validados para banco dedicado; não os execute sobre todos os schemas gerenciados como administrador.
- Guarde checksum e registre data, versão do aplicativo e quantidade de objetos. Teste restauração em destino descartável e meça o tempo real de recuperação.
- Não prometa recuperação automática apenas por usar um banco remoto. Retenção, cópia fora da conta e ensaio conjunto continuam pendentes de ativação.

## Evidências e limites desta entrega

Testados localmente: adaptador de disco, contrato HTTP simulado do Storage, limite de download, rejeição de endpoint inseguro, isolamento da demonstração; PostgreSQL real descartável com schema privado, concorrência, backup e restauração com rollback após falha.

Não testados com Supabase real: autenticação da chave, políticas do projeto/bucket, certificados, comportamento da franquia e pausa, nem restauração externa. Nenhuma conta foi criada ou cobrada automaticamente.
