# Food Delivery API

API backend em Java 21 e Spring Boot 3.5, organizada como monólito modular. O perfil padrão usa PostgreSQL local persistente; `dev-h2` é uma alternativa local baseada em arquivo. O backend suporta checkout Mercado Pago em split de marketplace, OAuth por loja, webhooks assinados, contas com perfis vinculados, notificações por conta, pedidos, reservas, entregas e atendimento com chamados privados por conta.

## Executar

Requisitos: PostgreSQL 17, JDK 21 e Maven 3.6.3 ou superior. No notebook configurado, o servidor PostgreSQL do projeto fica em `127.0.0.1:5433`, separado do cluster de sistema.

```bash
npm start
```

Execute `npm start` na raiz do projeto para iniciar banco, API e interface juntos. O supervisor primeiro verifica os serviços já ativos: reutiliza uma API saudável na porta 8080 e um frontend FoodDelivery em 5173–5175, sem encerrar processos que não iniciou; se a porta da API estiver ocupada por outro serviço, informa o conflito antes de criar uma instância duplicada. Para iniciar somente a API, use `./scripts/setup-local-postgres.sh` e depois `./scripts/run-api.sh` dentro desta pasta.

O script de configuração inicializa os dados em `Backend/.postgres-data`, cria banco/usuário dedicados e grava credenciais aleatórias do banco e do administrador inicial em `Backend/.env` (permissão `600`, ignorado pelo Git). O serviço inicia em `http://localhost:8080`. Saúde: `GET /api/v1/status` e `GET /actuator/health`.

Para abrir no pgAdmin, crie uma conexão com host `127.0.0.1`, porta `5433`, banco `fooddelivery`, usuário `fooddelivery_app` e a senha `DATABASE_PASSWORD` que consta em `Backend/.env`. Para administração local, use o usuário `fooddelivery_admin`, senha `PGADMIN_DB_PASSWORD` do mesmo arquivo e banco de manutenção `postgres`. As senhas antigas do pgAdmin/PostgreSQL do sistema não são alteradas.

Parar/iniciar o servidor de banco depois: `./scripts/stop-db.sh` e `./scripts/start-db.sh`. A pasta `.postgres-data` contém os dados persistentes e deve ser incluída nos backups locais; não a apague se quiser preservar o banco.

O administrador inicial é `admin@fooddelivery.local`; consulte a senha aleatória `APP_ADMIN_PASSWORD` no arquivo `.env`. O app cria esta conta na primeira inicialização; cadastro público cria exclusivamente clientes. Troque a senha após entrar e não use estas credenciais fora do notebook.

## Fluxo local

1. Cadastre cliente em `POST /api/v1/auth/register` com `{ "name": "Ana Silva", "email": "ana@example.com", "password": "senha-com-12-caracteres" }`; autentique em `POST /api/v1/auth/login` e envie o token como `Authorization: Bearer <token>`.
2. Complete o perfil em `PUT /api/v1/account/profile` (`name`, `phone`, `addressLine`, `addressComplement`, `neighborhood`, `city`, `state`, `postalCode`). Clientes também podem manter endereços separados e escolher o principal em `/api/v1/account/addresses` (`GET`, `POST`, `PUT /{id}`, `PATCH /{id}/default`, `DELETE /{id}`). O checkout guarda o endereço escolhido como cópia no pedido. O frontend consulta CEP pelo serviço público [ViaCEP](https://viacep.com.br/) e permite preencher os campos manualmente se o serviço estiver indisponível. Notificações da conta ficam disponíveis em `/api/v1/account/notifications`.
3. O cadastro público cria clientes em `POST /api/v1/auth/register`. Vendedores e entregadores podem pedir acesso em `POST /api/v1/auth/operator-applications`; as contas ficam bloqueadas até a aprovação em `PATCH /api/v1/admin/operator-applications/{id}/approve` (ou podem ser recusadas). O admin também pode provisionar, suspender e reativar operadores. Administradores adicionais são provisionados fora da API. Comerciante cria loja em `POST /api/v1/merchant/stores`; admin aprova ou suspende em `/api/v1/admin/stores/{id}/approval` e `/active`; comerciante cadastra itens em `POST /api/v1/merchant/stores/{storeId}/items`. Cada leitura e alteração de loja/item valida o proprietário pelo token.
4. Cliente consulta `GET /api/v1/stores`, itens em `/api/v1/stores/{storeId}/items`, e cria pedido em `POST /api/v1/orders`. A resposta contém `paymentId`.
5. O cliente abre o Checkout Pro por `POST /api/v1/payments/{paymentId}/checkout`. A operação só avança após a confirmação verificada pelo Mercado Pago. O simulador de pagamento existe somente com o perfil `dev-h2`.
6. Comerciante avança estado via `PATCH /api/v1/merchant/orders/{id}/status` usando `ACCEPTED`, `PREPARING`, `READY`, `COMPLETED` conforme a transição permitida. Um pedido delivery pronto pode ser ofertado via `POST /api/v1/merchant/orders/{orderId}/delivery`.
7. Entregador consulta ofertas em `GET /api/v1/courier/offers`, aceita em `POST /api/v1/courier/offers/{deliveryId}/accept` e atualiza para `PICKED_UP`, `IN_TRANSIT`, `DELIVERED`. Durante `IN_TRANSIT`, pode compartilhar a localização do aparelho em `PATCH /api/v1/courier/deliveries/{deliveryId}/location`; o endpoint só aceita o entregador atribuído e a posição é removida ao concluir. O cliente vê as etapas e a última posição em `GET /api/v1/orders/mine`, que o frontend atualiza periodicamente. A atribuição usa atualização condicional atômica: somente um aceite vence.
8. Comerciante cadastra um serviço com `serviceItem: true`. Cliente cria reserva em `POST /api/v1/reservations` indicando `storeId`, `serviceItemId` e `startsAt`; o preço vem do catálogo. A resposta traz o `paymentId` para abrir o checkout. Comerciante consulta `/api/v1/merchant/reservations` e atualiza estado em `PATCH /api/v1/merchant/reservations/{id}`. Pagamentos pendentes expiram em 15 minutos; estoque e horários são liberados automaticamente.

## Atendimento

Clientes, vendedores e entregadores podem abrir chamados em `POST /api/v1/support/tickets`, consultar sua lista em `GET /api/v1/support/tickets/mine` e responder em `/api/v1/support/tickets/{id}/messages`. A equipe administrativa usa `GET /api/v1/admin/support-tickets`, consulta detalhes, responde e altera o estado por `PATCH /api/v1/admin/support-tickets/{id}/status`. O backend verifica a propriedade dos chamados e grava as mensagens na migração Flyway V12.

## Recuperação de senha

Na tela de login, a pessoa solicita um código usando o e-mail da conta e escolhe e-mail ou WhatsApp. A API sempre responde com a mesma mensagem neutra, inclusive quando a conta não existe ou o canal não está configurado. O código tem seis dígitos, expira em 10 minutos, só pode ser usado uma vez, aceita no máximo cinco tentativas e tem intervalo mínimo de 60 segundos entre reenvios. A migração V14 guarda somente o hash do código. A redefinição incrementa `credential_version`, invalidando os tokens ativos. O administrador continua podendo gerar uma senha temporária pelo painel; ela é exibida uma única vez e a conta deve trocá-la no próximo acesso.

Para e-mail, configure `SMTP_HOST`, `SMTP_PORT`, `SMTP_USERNAME`, `SMTP_PASSWORD` e `RECOVERY_EMAIL_FROM` no `Backend/.env`. Sem SMTP, nenhum código é enviado e a resposta pública permanece neutra. Para WhatsApp, habilite `WHATSAPP_CLOUD_ENABLED` e informe `WHATSAPP_PHONE_NUMBER_ID`, `WHATSAPP_ACCESS_TOKEN`, `WHATSAPP_GRAPH_VERSION`, `WHATSAPP_OTP_TEMPLATE` e `WHATSAPP_TEMPLATE_LANGUAGE`. O template precisa estar aprovado pela Meta e ter um parâmetro de texto `{{1}}` no corpo. O usuário confirma seu telefone em **Minha conta > Segurança** antes que o WhatsApp possa ser usado para redefinir a senha. Ao mudar o telefone, a confirmação anterior é removida. Credenciais devem ficar apenas no `.env` privado ou no gerenciador de segredos da implantação.

## Mercado Pago para marketplace

O checkout usa **Split de Pagamentos 1:1 com Checkout Pro**. Cada comerciante conecta a própria conta Mercado Pago pelo painel, via OAuth; o token fica criptografado no banco com AES-GCM. O pagamento é criado em nome da conta do vendedor, e a comissão por loja é enviada como `marketplace_fee`. A conta do vendedor recebe o saldo líquido no Mercado Pago; o vendedor controla os saques Pix no aplicativo/conta Mercado Pago, conforme os recursos disponíveis para a conta dele.

1. Crie no painel de desenvolvedor do Mercado Pago uma aplicação de pagamentos online, modelo **Marketplace**, Checkout Pro.
2. Cadastre como URL OAuth de redirecionamento o valor de `MP_REDIRECT_URI` (local: `http://127.0.0.1:8080/api/v1/payments/mercadopago/callback`; produção deve usar HTTPS).
3. Copie `client_id` e `client_secret` para `MP_CLIENT_ID` e `MP_CLIENT_SECRET` no arquivo privado `Backend/.env`. Nunca envie esses segredos no chat, frontend ou Git.
4. Gere `MP_TOKEN_ENCRYPTION_KEY` com `openssl rand -base64 32` e mantenha o valor privado e em backup seguro. Já foi criada uma chave local neste notebook. Perdê-la exige que cada vendedor reconecte a conta.
5. Para testar Webhooks em ambiente local, publique a API por um túnel HTTPS e defina `MP_NOTIFICATION_BASE_URL` como a origem pública do túnel, sem o caminho `/api`. Configure no Mercado Pago a chave de assinatura de Webhooks em `MP_WEBHOOK_SECRET`. Use contas de teste e `MP_SANDBOX=true`.
6. No painel administrativo do Pronto, configure a comissão percentual por loja. Ela pode variar de 0% a 50%; o valor é fixado para cada checkout criado.

Em produção, o backend bloqueia o checkout se não houver URL de notificação HTTPS e chave de webhook. Retorno do navegador não é considerado prova de pagamento: a confirmação exige validação da assinatura e consulta autenticada do pagamento. Chamadas HTTP ao Mercado Pago usam timeout de 5 s para conexão e 15 s para resposta; falha temporária ao validar um webhook retorna erro de gateway para permitir nova tentativa pelo provedor. Split 1:1 precisa de autorização OAuth de cada vendedor e da habilitação comercial exigida pelo Mercado Pago.

## Imagens de capa com IA

O painel do vendedor pode gerar uma imagem para a capa da loja e aplicar a prévia depois da revisão. Configure `OPENAI_API_KEY` no ambiente do backend; a chave nunca é enviada ao navegador. O modelo padrão é `gpt-image-2.5-sunburst` e pode ser alterado por `OPENAI_IMAGE_MODEL`. As imagens são salvas no diretório `STORE_ASSETS_DIR` (padrão `Backend/data/store-assets`); inclua esse diretório nos backups e use armazenamento de objetos compartilhado em implantações com múltiplas instâncias. O endpoint limita cada conta a cinco gerações por hora por instância; para operação distribuída, substitua esse limitador em memória por uma cota compartilhada antes de escalar. O serviço exige credenciais e pode exigir verificação da organização OpenAI. Consulte a [documentação oficial de geração de imagens](https://developers.openai.com/api/docs/guides/image-generation).

Categorias disponíveis para lojas: Restaurantes, Mercados, Conveniências, Lanchonetes, Pizzarias, Cafeterias, Farmácias, Beleza, Barbearias e Serviços. O catálogo público lista as categorias em `GET /api/v1/store-categories`.

Quando o comerciante recusa ou cancela um pedido/reserva já pago, o backend cria um estorno integral com chave idempotente, registra tentativas e confirmações e tenta novamente falhas ambíguas. Reembolsos aceitos pelo provedor ficam pendentes até a confirmação por webhook; falhas que exijam intervenção aparecem em `GET /api/v1/admin/refunds` e no painel de administração como `REFUND_REVIEW`. A equipe deve conferir esses casos no Mercado Pago. No Split 1:1, o Mercado Pago desconta proporcionalmente o estorno das contas envolvidas; se o saldo do vendedor não for suficiente, o estorno integral pode precisar de ação manual. O cliente pode cancelar um pedido/reserva ainda não pago; depois da aprovação do pagamento, o cancelamento pelo cliente com política por categoria, disputas/chargebacks, conciliação de liquidação e saque Pix iniciado pelo Pronto continuam fora do escopo implementado. Faça homologação com credenciais de teste antes de habilitar cobrança comercial.

Valores monetários são enviados como centavos inteiros (por exemplo, R$ 19,90 = `1990`). Datas de reserva devem ser ISO-8601 com fuso, por exemplo `2026-10-05T18:00:00-03:00`.

## Rotas principais

| Área | Rotas |
|---|---|
| Conta | `POST /api/v1/auth/register`, `POST /api/v1/auth/login`, `POST /api/v1/auth/password-recovery`, `POST /api/v1/auth/password-recovery/reset`, `GET /api/v1/account/me`, `PUT /api/v1/account/profile`, `/api/v1/account/addresses`, `/api/v1/account/phone-verification` |
| Catálogo | `GET /api/v1/store-locations`, `GET /api/v1/stores`, `GET /api/v1/stores/{id}/items`, `/api/v1/merchant/stores`, `/api/v1/merchant/items` |
| Pedidos | `POST /api/v1/orders`, `POST /api/v1/orders/{id}/cancel` (antes do pagamento), `GET /api/v1/orders/mine`, `/api/v1/merchant/orders` |
| Reservas | `POST /api/v1/reservations`, `POST /api/v1/reservations/{id}/cancel` (antes do pagamento), `GET /api/v1/reservations/mine`, `/api/v1/merchant/reservations` |
| Entregas | `/api/v1/merchant/orders/{id}/delivery`, `/api/v1/courier/offers`, `/api/v1/courier/deliveries`, `PATCH /api/v1/courier/deliveries/{id}/location` |
| Mensagens | `GET/POST /api/v1/orders/{id}/messages`, `/api/v1/reservations/{id}/messages`, `/api/v1/deliveries/{id}/messages` (liberadas após o pagamento; chat do entregador encerra na coleta) |
| Atendimento | `POST /api/v1/support/tickets`, `GET /api/v1/support/tickets/mine`, `/api/v1/admin/support-tickets` |
| Administração | `/api/v1/admin/operators`, `/api/v1/admin/stores/{id}/approval`, `GET /api/v1/admin/refunds` |

## Execução com PostgreSQL

Ative `SPRING_PROFILES_ACTIVE=prod` e forneça `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`, `JWT_SECRET` (chave Base64 de pelo menos 256 bits), `APP_ADMIN_EMAIL` e `APP_ADMIN_PASSWORD`. O Flyway aplica migrações comuns de `src/main/resources/db/migration-common` e a migração PostgreSQL específica de `migration-postgres`; o Hibernate valida o esquema sem alterá-lo.

## Limites desta entrega

Ainda não estão implementados MFA, notificações transacionais por push, logística por distância/mapas, chargebacks, conciliação de liquidação, saque Pix iniciado pela plataforma ou cancelamento de pedido/reserva pelo cliente após pagamento. O envio de recuperação depende de SMTP/WhatsApp Cloud configurados; WhatsApp exige template aprovado pela Meta. O sistema também não mantém uma trilha de auditoria completa das transições com autor, motivo e horário, nem SLA configurável de aceite do comerciante. A taxa de entrega atual é fixa por loja e configurada pelo comerciante; o estoque reservado é devolvido em expiração do checkout e cancelamento/recusa da loja ou do cliente antes do pagamento. O horário de reservas é pontual, sem duração/capacidade por equipe ou categoria. O fluxo de estorno automático precisa ser homologado com o Mercado Pago e pode exigir intervenção quando houver saldo insuficiente na conta do vendedor. Esses pontos precisam ser avaliados antes de uma operação comercial ampla.

## Modelo de dados

O esquema inicial está em `src/main/resources/db/migration-common/V1__initial_schema.sql`; migrações posteriores estendem endereço/perfil, pagamentos do marketplace, expiração/slots de reserva, rastreio idempotente de estornos (V5), solicitações de acesso e marca da loja (V6), endereços do cliente (V7), localização do entregador (V8), segurança de contas e cidade da loja (V9), versão de credencial (V10), mensagens de operação (V11), chamados (V12), CNPJ e solicitações de plataforma (V13) e recuperação de senha/telefone confirmado (V14). O perfil `dev-h2` aplica a V9 equivalente em `src/main/resources/db/migration-h2`. Veja `Documentacao/Funcionamento_do_Projeto.md` para o funcionamento completo. Valores de pedido ficam em centavos e datas são armazenadas com fuso. Restrições únicas impedem conta repetida por e-mail/CPF, mais de um pagamento por pedido, dois pedidos de entrega para o mesmo pedido e duas reservas ativas da mesma loja no mesmo instante.
