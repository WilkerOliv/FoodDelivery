# FoodDelivery — como o projeto funciona

Plataforma de pedidos, reservas e entregas para cidades pequenas e comércios locais. Este documento descreve o que o código faz hoje; o que depende de configuração externa está marcado como tal. Documentos complementares: `Modelo_de_Dados.md` (tabelas e regras de integridade) e `Visao_e_Especificacao_do_Produto.md` (visão de produto).

## 1. Visão geral

| Camada | Tecnologia | Pasta |
|---|---|---|
| Interface web | JavaScript puro (módulo ES), sem framework, servido por um script Python | `Frontend/` |
| API | Java 21, Spring Boot 3.5, Spring Security (JWT em cookie `HttpOnly`), Spring Data JPA | `Backend/` |
| Banco | PostgreSQL (padrão), H2 em arquivo no perfil `dev-h2`; migrações Flyway versionadas | `Backend/src/main/resources/db/migration-common` e pastas específicas por banco |
| Pagamento | Mercado Pago (Marketplace + Checkout Pro) | `payments/` |
| Imagens de capa por IA | API de imagens da OpenAI (opcional) | `catalog/StoreImageAiController` |
| Inicialização local | `npm start` roda `scripts/dev.py`: sobe banco, API e interface juntos | raiz |

A interface fala com a API em `/api/v1`. Em desenvolvimento a interface roda em `127.0.0.1:5173` e a API em `127.0.0.1:8080`.

## 2. Papéis e permissões

Existem quatro papéis: `CUSTOMER`, `MERCHANT`, `COURIER` e `ADMIN`.

- Todo cadastro público cria uma conta de **cliente**. O CPF é obrigatório, validado e único.
- Vendedor e entregador **pedem acesso profissional** vinculado à conta existente (`POST /account/platform-requests/roles`). O administrador aprova ou recusa em `/admin/platform-requests/{id}/review`. Enquanto o pedido está pendente, a conta continua ativa como cliente. As rotas antigas de `operator-applications` são compatibilidade e não criam contas novas.
- Uma conta pode ter vários papéis concedidos (`granted_roles`) e escolhe o perfil ativo na interface. O administrador pode suspender um papel sem afetar os demais (`suspended_roles`).
- O administrador pode suspender a conta inteira e gerar senha temporária; a pessoa precisa trocá-la no próximo acesso (`password_change_required`).
- O administrador não precisa de CPF. Pode corrigir dados cadastrais das contas e gerenciar a cobertura de cidades em **Definições**; CPF continua obrigatório para clientes e profissionais e permanece uma coluna opcional/única, nunca a chave primária.
- Em **Definições > Integrações**, a interface informa se as variáveis privadas de SMTP, WhatsApp, OpenAI e Mercado Pago estão presentes. Segredos são configurados no ambiente do backend e não passam pelo navegador. O indicador não representa homologação do provedor.
- Trocar ou redefinir a senha incrementa `credential_version`, o que invalida os tokens anteriores.
- A recuperação pública usa códigos de uso único enviados por SMTP ou, opcionalmente, WhatsApp Cloud. O telefone precisa ser confirmado pelo próprio titular antes da recuperação por WhatsApp.
- Cada requisição autenticada valida assinatura, emissor e validade do JWT e confirma que a conta continua ativa.

## 3. Fluxo de compra (pedido)

```mermaid
stateDiagram-v2
    [*] --> AWAITING_PAYMENT: cliente cria o pedido
    AWAITING_PAYMENT --> AWAITING_ACCEPTANCE: pagamento aprovado
    AWAITING_PAYMENT --> CANCELLED: cliente cancela ou checkout expira
    AWAITING_ACCEPTANCE --> ACCEPTED: loja aceita
    AWAITING_ACCEPTANCE --> REJECTED: loja recusa (estorno)
    ACCEPTED --> PREPARING
    PREPARING --> READY
    READY --> COMPLETED
```

1. O cliente monta o carrinho de uma loja, escolhe **retirada** ou **entrega**, informa o endereço (entrega) e uma **observação opcional de até 500 caracteres**.
2. `POST /orders` cria o pedido em `AWAITING_PAYMENT`, congela subtotal, taxa de entrega e total e reserva o estoque.
3. `POST /payments/{id}/checkout` abre o checkout do Mercado Pago. O retorno é confirmado por `POST /payments/{id}/sync` e pelo webhook `POST /webhooks/mercadopago`, que exige assinatura válida.
4. Com o pagamento aprovado o pedido vai para `AWAITING_ACCEPTANCE` e a loja passa a vê-lo em `/merchant/orders`. **Pedidos ainda não pagos não aparecem para a loja.**
5. A loja atualiza o estado em `PATCH /merchant/orders/{id}/status`; cada mudança notifica o cliente. Recusar ou cancelar um pedido pago repõe o estoque e solicita o estorno integral.
6. A cada 60 segundos o `UnpaidCheckoutExpirationJob` cancela pedidos e reservas cujo pagamento não foi concluído, libera estoque ou horário e avisa as partes.

O cliente pode cancelar enquanto o pagamento não foi aprovado (`POST /orders/{id}/cancel`). Depois de pago, pode abrir uma solicitação (`POST /orders/{id}/cancellation-request`); a loja aceita ou recusa em `PATCH /merchant/orders/{id}/cancellation-request`. Aceitar repõe o estoque e solicita estorno integral. O estorno só aparece como concluído após confirmação do provedor.

## 4. Fluxo de reserva

`PENDING_PAYMENT → CONFIRMED → IN_SERVICE → COMPLETED`, com `CANCELLED` e `NO_SHOW` como saídas.

- `POST /reservations` recebe loja, serviço, horário (mínimo de 30 minutos de antecedência) e **comentário opcional de até 500 caracteres**.
- Cada serviço guarda duração em minutos (5 a 480; padrão de 30 para itens antigos ou sem valor). A reserva congela esse intervalo. O servidor valida todo o intervalo contra a rotina/exceção de funcionamento e impede sobreposição para o mesmo profissional; quando não há profissional, a loja inteira ocupa um único recurso. Uma linha bloqueada da loja serializa verificações concorrentes.
- O intervalo é liberado ao cancelar, expirar, concluir ou marcar falta.
- O pagamento segue o mesmo caminho do pedido. A loja vê a reserva somente depois do pagamento (`/merchant/reservations`) e a atualiza em `PATCH /merchant/reservations/{id}`.
- O cliente pode solicitar cancelamento de reserva confirmada em `POST /reservations/{id}/cancellation-request`; o vendedor aceita/recusa em `PATCH /merchant/reservations/{id}/cancellation-request`. Aceitar libera o intervalo e inicia estorno integral se houve pagamento online.

### Horário de funcionamento por loja

- Em **Minha loja > Dados da loja > Funcionamento e exceções**, o vendedor pode ativar uma rotina semanal e selecionar dias independentes, com abertura e fechamento para cada dia. Dias não selecionados ficam fechados enquanto a rotina está ativa. Horários que cruzam a meia-noite são aceitos.
- A configuração é opcional. Lojas existentes começam sem restrição semanal. Exceções por data, como fechar no mesmo dia ou abrir/fechar em horário especial, substituem a rotina naquela data mesmo quando ela está desativada. Remover a exceção restaura a regra habitual.
- **Fechar hoje** usa a data no fuso configurado na loja. As exceções são limitadas a 90 registros; cada data pode ter apenas uma. A observação é privada, não é devolvida na rota pública.
- O horário é conferido pelo servidor ao criar o pedido, iniciar o checkout do pedido e criar a reserva. A reserva inteira (início e fim) precisa caber no intervalo de atendimento; a data e o horário enviados pelo cliente são convertidos usando o fuso da loja. A interface também informa quando a loja está fechada e bloqueia as ações naquele momento.
- Rotas: `GET/PUT /api/v1/merchant/stores/{id}/hours` para o vendedor proprietário; `GET /api/v1/stores/{id}/hours` para a vitrine pública. A migração V19 guarda ativação, rotina semanal e exceções.
- **Limites conhecidos:** não há capacidade maior que um por profissional, intervalos de pausa múltiplos ou agenda semanal individual por profissional. A concorrência entre reservas é protegida por lock transacional no banco; a suíte H2 não substitui homologação de concorrência em PostgreSQL.

## 5. Mensagens e comentários

| Recurso | Quem participa | Quando está aberto | Endpoints |
|---|---|---|---|
| Conversa do pedido | Cliente e dono da loja | Depois do pagamento e até o pedido terminar (`COMPLETED`, `REJECTED`, `CANCELLED`) | `GET/POST /orders/{id}/messages` |
| Conversa da reserva | Cliente e dono da loja | Depois do pagamento e até a reserva terminar (`COMPLETED`, `CANCELLED`, `NO_SHOW`) | `GET/POST /reservations/{id}/messages` |
| Chat com o entregador | Cliente do pedido e entregador atribuído | Somente com a entrega em `ASSIGNED`; encerra na coleta (`PICKED_UP`) | `GET/POST /deliveries/{id}/messages` |

Regras comuns:

- Cada mensagem tem de 1 a 1000 caracteres. Cada conversa devolve as últimas 100 mensagens em ordem cronológica.
- Quem não participa recebe acesso negado. A loja não acessa a conversa de pedido ou reserva ainda não paga.
- Depois que a conversa encerra, o histórico continua legível para os participantes, mas novas mensagens são recusadas.
- Cada mensagem gera uma notificação para a outra parte. Enquanto a pessoa não lê a anterior, não é criada outra com o mesmo título, para não encher a lista.
- A observação do cliente aparece para a loja e, no caso de entrega, também para o entregador depois do aceite. Ela deixa de ser exposta ao entregador quando a entrega é concluída.

Na interface, o painel de conversa consulta novas mensagens a cada 5 segundos enquanto está aberto e a aba está visível. As atualizações da lista não recriam o formulário, preservando o texto em digitação, e cada conversa mantém seu rascunho durante a sessão. Trocar de seção interrompe a consulta periódica; sair da conta ou expirar a sessão limpa conversas e rascunhos da memória do navegador.

## 6. Entrega

```mermaid
stateDiagram-v2
    [*] --> OFFERED: loja oferece pedido READY de entrega
    OFFERED --> ASSIGNED: entregador aceita
    ASSIGNED --> PICKED_UP: coletado
    PICKED_UP --> IN_TRANSIT: trajeto iniciado
    IN_TRANSIT --> DELIVERED: entregue
```

- A loja oferece um pedido de entrega que esteja `READY` (`POST /merchant/orders/{id}/delivery`).
- Entregadores veem ofertas sem endereço nem dados do cliente. Esses dados aparecem depois do aceite (`POST /courier/offers/{id}/accept`). O aceite é uma atualização condicional atômica: só um entregador consegue a oferta.
- O entregador avança o estado em `PATCH /courier/deliveries/{id}`. Só as transições da figura são aceitas.
- Durante `IN_TRANSIT` o entregador pode compartilhar a localização (`PATCH /courier/deliveries/{id}/location`). O cliente vê o trajeto e pode abrir o mapa externo.
- O botão do chat com o entregador só aparece depois que há entregador atribuído. Depois da coleta, ambos mantêm o histórico em modo leitura.

## 7. Lojas, catálogo e pagamentos

- Cada loja tem categoria, endereço, **cidade e estado** (usados para filtrar lojas pela cidade do cliente), capa, taxa fixa de entrega (R$ 0,00 a R$ 1.000,00) e comissão do marketplace (0% a 50%, definida pelo administrador).
- Lojas novas precisam de aprovação do administrador antes de aparecer.
- A capa pode ser enviada como arquivo (`POST /merchant/stores/{id}/cover`) ou gerada por IA (`POST /merchant/stores/{storeId}/ai/cover`, limite de 5 imagens por hora por conta).
- A loja conecta a conta do Mercado Pago por OAuth (`/merchant/stores/{storeId}/mercadopago/connect`). Os tokens ficam criptografados no banco.
- Estornos usam chave de idempotência. Tentativas que falham entram na rotina de repetição (`RefundRetryJob`) e, esgotadas, em `REFUND_REVIEW` para ação do administrador (`GET /admin/refunds`).

## 8. Notificações

- Mensagens importantes (pedido, pagamento, aprovação, mensagem nova) são gravadas por conta em `account_notifications` e ficam até a pessoa limpar (`DELETE /account/notifications`).
- A interface consulta `/account/notifications` a cada 30 segundos com a aba visível e mostra as novas como aviso temporário.
- Confirmações e erros da própria interface são avisos temporários e não são gravados.

## 9. Atendimento por chamados

O botão **Falar com atendimento** abre uma central onde cliente, vendedor ou entregador podem enviar uma solicitação e consultar o histórico. Cada chamado guarda assunto, categoria, estado, conta proprietária e mensagens com remetente e horário. Chamados ficam em `OPEN`, `IN_PROGRESS` ou `CLOSED`; uma resposta do usuário reabre um chamado encerrado. O administrador tem a seção **Chamados**, pode responder, encerrar e reabrir. O usuário só consulta chamados vinculados à própria conta; a API exige papel autenticado e valida a propriedade antes de ler ou responder. A resposta do cliente usa `POST /api/v1/support/tickets/{id}/messages`; a resposta administrativa usa `POST /api/v1/admin/support-tickets/{id}/messages`. Novos chamados e respostas geram notificações persistentes para a equipe.

A migração `V12__support_tickets.sql` cria `support_tickets` e `support_ticket_messages`. O painel administrativo carrega cada área separadamente: falhas pontuais preservam as demais informações, e indisponibilidade geral mostra uma orientação explícita para iniciar API e banco.

## 10. Localização e conta

O seletor de localização lista cidades que têm lojas ativas e aprovadas (`GET /store-locations`), aceita CEP via ViaCEP e filtra a descoberta por cidade/UF. O mapa incorporado é uma referência da cidade escolhida; ele não representa a posição em tempo real do cliente. A seleção fica salva no navegador. A página Minha conta separa dados pessoais, endereços, segurança e acessos profissionais em seções próprias para facilitar a navegação e reduzir formulários simultâneos. A tela de localização apresenta cidades com lojas ativas em uma lista e um campo de CEP abaixo; o mapa ajuda a conferir a cidade selecionada.

## 11. Mapa da API

Todas as rotas começam com `/api/v1`. Públicas: cadastro, login/logout e recuperação, pedido de acesso profissional, categorias, imagens/lojas públicas, cidades, busca e geocodificação de mapa (`/geography/search` e `/geography/reverse`), retorno e webhook do Mercado Pago, `/status` e `/actuator/health`. As demais exigem sessão válida. Login e troca de senha definem cookie `fooddelivery_session` `HttpOnly`; o frontend não armazena o bearer em `localStorage`. Clientes de API fora do navegador podem continuar usando `Authorization: Bearer`.

| Grupo | Rotas principais |
|---|---|
| Autenticação | `POST /auth/register`, `/auth/login`, `/auth/password-recovery`, `/auth/password-recovery/reset` |
| Conta | `GET /account/me`, `PUT /account/profile`, `POST /account/password`, `/account/addresses` (CRUD e padrão), `/account/notifications` |
| Catálogo | `GET /store-categories`, `GET /store-locations`, `GET /stores`, `GET /stores/{id}/items`, `/merchant/stores`, `/merchant/items/{id}` |
| Pedidos | `POST /orders`, `GET /orders/mine`, `POST /orders/{id}/cancel`, `GET /merchant/orders`, `PATCH /merchant/orders/{id}/status` |
| Reservas | `POST /reservations`, `GET /reservations/mine`, `POST /reservations/{id}/cancel`, `GET /merchant/reservations`, `PATCH /merchant/reservations/{id}` |
| Entregas | `/merchant/orders/{id}/delivery`, `/courier/offers`, `/courier/deliveries` |
| Mensagens | `/orders/{id}/messages`, `/reservations/{id}/messages`, `/deliveries/{id}/messages` |
| Pagamentos | `/payments/{id}`, `/payments/{id}/checkout`, `/payments/{id}/sync`, `/webhooks/mercadopago`, `/merchant/stores/{storeId}/mercadopago/*` |
| Administração | `/admin/stores`, `/admin/accounts`, `/admin/operators`, `/admin/operator-applications` (legado), `/admin/platform-requests` (perfis, cidades e lojas), `/admin/refunds`, `/admin/support-tickets` |
| Solicitações na conta | `GET /account/platform-requests`, `POST /account/platform-requests/roles`, `POST /account/platform-requests/cities` |
| Revisão administrativa | `PATCH /admin/platform-requests/{id}/review` com aprovação ou reprovação e nota opcional |
| Atendimento | `/support/tickets/mine`, `POST /support/tickets`, `/support/tickets/{id}` e `/messages` |
| Somente `dev-h2` | `/dev/payments/{id}/approve`, `/dev/reservations/{id}/approve-payment` simulam o pagamento sem o Mercado Pago |

## 10. Configuração e execução

Variáveis em `Backend/.env.example` (copie para `.env`; nunca versione valores reais):

| Variável | Para que serve | Obrigatória |
|---|---|---|
| `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD` | Banco PostgreSQL | Sim (perfil `postgres` e `prod`) |
| `JWT_SECRET` | Assinatura dos tokens | Sim em todos os perfis |
| `FOODDELIVERY_ALLOWED_ORIGINS` | Origens completas permitidas no CORS e nas mutações com cookie | Sim para domínio público; padrão inclui apenas portas locais |
| `AUTH_COOKIE_SECURE` | Exige HTTPS para enviar o cookie | `true` em produção; `false` apenas no desenvolvimento HTTP |
| `APP_ADMIN_EMAIL`, `APP_ADMIN_PASSWORD` | Administrador inicial | Sim na primeira execução |
| `MP_CLIENT_ID`, `MP_CLIENT_SECRET`, `MP_REDIRECT_URI`, `MP_TOKEN_ENCRYPTION_KEY`, `MP_WEBHOOK_SECRET`, `MP_NOTIFICATION_BASE_URL`, `MP_SANDBOX` | Mercado Pago | Sim para pagamentos reais |
| `OPENAI_API_KEY`, `OPENAI_IMAGE_MODEL` | Geração de capa por IA | Não; sem chave a rota responde indisponível |
| `STORE_ASSETS_DIR` | Pasta das capas | Não |

Perfis: `postgres` (padrão), `dev-h2` (banco em arquivo e rotas de pagamento simulado) e `prod`.

Execução local: `npm start` na raiz. Docker: `Backend/Dockerfile` usa Java 21, a mesma versão do `pom.xml`.

### CPF da conta administrativa

O CPF não é obrigatório para contas com papel administrativo. Em **Definições > Perfil do administrador**, marque **“Remover o CPF desta conta administrativa”** e salve. Deixar o campo vazio sem marcar essa opção mantém o CPF existente. A API só permite essa remoção para contas que tenham o papel `ADMIN` registrado; clientes, vendedores e entregadores mantêm a exigência de CPF para seus fluxos protegidos.

## 11. Pontos que dependem de configuração externa

- Mercado Pago: credenciais reais ou de teste, URL pública para webhook e homologação do estorno automático.
- OpenAI: chave de API; sem ela a geração de capa não funciona.
- Produção: `JWT_SECRET` forte, banco próprio, HTTPS e domínio para o retorno do pagamento.
- Produção: defina `FOODDELIVERY_ALLOWED_ORIGINS` apenas com o endereço oficial do frontend e mantenha `AUTH_COOKIE_SECURE=true`. Com `SameSite=Lax`, frontend e API precisam compartilhar o mesmo site; TLS/HSTS devem ser configurados no proxy/CDN.

## 12. Limites conhecidos

- Não há testes automatizados de ponta a ponta. Há testes unitários para backend e testes de helpers/regressões de frontend; eles não cobrem jornadas completas no navegador nem a matriz de aparelhos.
- Reservas têm duração de serviço, horários semanais, exceções, fuso configurável, capacidade e escolha de profissional/“Primeiro disponível”. Ainda não há pausas múltiplas por dia ou capacidade superior a uma reserva simultânea por profissional.
- Não há indicador de "não lido" por conversa; o aviso de mensagem nova chega pela notificação.
- A sessão do navegador usa cookie `HttpOnly` e `SameSite=Lax`; mutações com cookie também validam `Origin` contra a lista CORS. O frontend local e a API publicam CSP. HSTS e a política final de CSP ainda precisam ser configurados e verificados no domínio HTTPS de produção.
- A API não aplica limite distribuído de tentativas de login nem de envio de mensagens. Em produção, configure limitação por IP/conta no gateway ou em armazenamento compartilhado para evitar força bruta e abuso sem depender de uma única instância.
- A geração de capa por IA usa um único modelo e provedor, sem repetição nem alternativa em caso de falha.

## Atualização de catálogo, agenda e suporte (31/10/2026)

- **Cliente:** conta legada sem CPF ou telefone pode acessar o site, mas deve completar os dados em **Minha conta > Dados pessoais** antes de comprar ou reservar. O servidor valida CPF e telefone em cada fluxo de compra/reserva.
- **Vendedor:** a tela **Catálogo** permite editar, remover e reativar itens. Remover é exclusão lógica: muda o item para inativo, deixa de exibi-lo na loja e mantém o histórico operacional. A autorização confirma que o item pertence à loja do vendedor logado.
- **Profissionais e reservas:** a agenda permite cadastrar profissionais e ativá-los/suspendê-los. O cliente pode selecionar um profissional ou pedir o primeiro disponível. O backend verifica a disponibilidade e registra o vínculo na reserva e no slot; slots legados sem profissional também bloqueiam o horário geral.
- **Pagamento fora da cidade:** uma reserva de serviço com pagamento após atendimento só é permitida se cliente e loja tiverem cidade e UF iguais. Caso contrário, será necessário pagar online na plataforma, mesmo que o vendedor tenha habilitado pagamento posterior. O perfil no servidor é a fonte da comparação.
- **Fuso operacional:** cada loja usa fuso IANA editável, com `America/Sao_Paulo` como padrão. O cliente escolhe o horário no relógio da loja; a interface converte para UTC e exibe as reservas no fuso correspondente. O backend valida o identificador do fuso, e a migração V17 atualiza slots para a unicidade por profissional e instante.
- **Dados da loja:** **Minha loja > Dados da loja** edita o perfil da loja selecionada. O seletor de loja sincroniza catálogo, integração de pagamento e lista de profissionais. O cadastro adicional segue separado para não sobrescrever a loja atual.
- **Chamados:** o retorno da conversa administrativa volta à listagem administrativa e atualiza os chamados; não abre a central do cliente.
- **Telas estreitas:** as abas do vendedor quebram em várias linhas, sem exigir rolagem lateral. Ações e campos do catálogo também podem se reorganizar em coluna.

**Verificações desta alteração:** Frontend: 18 testes aprovados e sintaxe de `main.js` válida. Backend: `mvn test -q` concluiu com sucesso e Flyway aplicou até V17 no banco H2 em memória. O frontend respondeu em `127.0.0.1:5173`; a API na porta `8080` respondeu no endpoint de status, mas falhou em `/actuator/health`. O iniciador `npm start` interrompeu a inicialização para não criar uma segunda API. Assim, não foi possível confirmar visualmente as telas internas nem autenticar os seis fluxos contra o PostgreSQL local nesta rodada; nenhum dado ou arquivo do banco foi apagado.
- Conciliação contábil, chargebacks e saque iniciado pela plataforma não existem.

## 13. Revisão técnica do estado atual

Atualização da revisão: suspensão da conta ou do perfil de vendedor retira as lojas do catálogo e exige nova aprovação para publicação. A aprovação de uma loja também verifica o estado da conta e do perfil do vendedor. Enquanto o pedido de cadastro da loja estiver pendente, seus dados não podem ser editados. A área administrativa concentra as ações de ativação por perfil em “Contas e segurança”; a lista de perfis operacionais é apenas informativa para evitar suspender acidentalmente outro perfil da mesma conta.

Revisão realizada em 28/10/2026 sobre os fluxos de compra, reserva, entrega, mensagens, autorização e migrações:

- As áreas do administrador são renderizadas por identificadores nomeados (`stats`, `applications`, `stores`, `team`, `finance` e `tickets`), nunca por posição dos elementos. Os cartões de lojas, solicitações e contas levam diretamente à respectiva tela de análise. Chamados ficam isolados da criação de acessos.
- A área **Equipe** agora tem três subseções independentes: **Perfis operacionais**, **Contas e segurança** e **Criar acesso**. Somente o conteúdo escolhido é exibido. O atalho “Abrir contas e segurança” seleciona a subseção correta. As ações de conta ficam agrupadas no menu de três pontos de cada cartão.
- A tela de chamados consulta os detalhes em `GET /api/v1/admin/support-tickets/{id}` (e respostas em `POST /api/v1/admin/support-tickets/{id}/messages`). O endereço anterior do frontend não correspondia à rota do backend e fazia o modal falhar ao abrir.
- A aprovação de loja usa `PATCH /api/v1/admin/platform-requests/{id}/review` quando existe solicitação pendente vinculada à loja; cadastros legados sem solicitação usam `PATCH /api/v1/admin/stores/{id}/approval`. A interface recarrega as informações administrativas após a decisão.
- O identificador do botão **Aprovar loja** é lido de `data-approve-store`; o listener deve usar esse mesmo atributo para montar a rota. Ações administrativas repetidas enquanto uma requisição está em andamento são ignoradas para evitar envios concorrentes.
- A aprovação comercial da loja não depende de pagamento. A decisão do administrador é a aprovação: o backend marca a loja como aprovada e ativa; então ela pode aparecer aos clientes se a conta do vendedor e o perfil profissional estiverem ativos. Taxas/comissões configuradas são regras financeiras do marketplace e não uma cobrança para liberar a loja.
- A seção de revisão de lojas diferencia **Aguardando análise**, **Confirmada** e **Loja suspensa**. Os estados de pagamento pertencem a pedidos e reservas, não ao cadastro/aprovação de estabelecimentos.
- Os campos de CPF/CNPJ exibem máscaras no navegador, mas enviam apenas os dígitos para a API. A máscara monetária exibe BRL com vírgula decimal e converte para centavos antes do envio; os valores persistidos e calculados continuam inteiros em centavos.
- A resposta de cliente usa `POST /support/tickets/{id}/messages`; a resposta administrativa usa `POST /admin/support-tickets/{id}/messages`. O frontend atualiza a conversa e a lista após o envio.
- A descoberta da página inicial mostra a quantidade de lojas por categoria na cidade selecionada. As categorias rolam horizontalmente em telas estreitas e exibem estado ativo, foco e contagem sem cortar o conteúdo.

- A aplicação está organizada como um **monólito modular**: controllers definem as rotas, serviços concentram regras de negócio, entidades/repositorios persistem no banco e o frontend chama a API autenticada. Os limites dos módulos ficam em `Backend/src/main/java/br/com/fooddelivery/`.
- A compra mantém o preço dos itens, taxa de entrega e endereço como dados do pedido. O pagamento é uma etapa separada e confirmada pelo provedor; só então a loja recebe o pedido.
- A reserva bloqueia o horário no banco. Serviço com pagamento antecipado fica pendente até a confirmação do pagamento; serviço com pagamento após atendimento fica confirmado de imediato e é pago diretamente à loja.
- A entrega separa oferta, atribuição, coleta, trajeto e conclusão. Endereço e chat só são liberados ao entregador atribuído. O chat aceita novas mensagens até a coleta; após isso, o histórico permanece disponível em modo de leitura.
- As rotas de mensagens validam o papel e, além disso, verificam se o usuário é o cliente da operação, o proprietário da loja ou o entregador que recebeu aquela entrega. O banco exige que cada mensagem esteja ligada a exatamente um pedido, reserva ou entrega.
- O projeto compila com Maven e os arquivos JavaScript passam na verificação sintática. A suíte Maven foi executada com sucesso após configurar o agente Byte Buddy que o Mockito usa para criar mocks no JDK 21. A migração V14 adiciona confirmação de telefone e hashes de recuperação; a V15 adiciona as opções de pagamento de serviços e reservas; a V16 persiste seções adicionais de comércio e serviço.
- A conversa atualiza por consultas periódicas, não por WebSocket ou notificações push. Para alto volume, a evolução natural é mover mensagens para entrega em tempo real e limitar consultas por usuário/dispositivo.

As migrações comuns ficam em `Backend/src/main/resources/db/migration-common`; somente a migração V9 varia por banco em `migration-postgres` e `migration-h2`. Essa separação mantém os checksums já aplicados em PostgreSQL e adapta o índice único de CPF à sintaxe do H2.

O documento descreve o código local revisado. Integrações de pagamento real e geração por IA dependem das credenciais externas listadas acima; a aprovação comercial e a homologação dos fluxos de estorno devem ocorrer antes de receber pagamentos reais.

## Segurança, notificações e recuperação operacional (V18)

- **Chave de assinatura JWT:** `JWT_SECRET` passou a ser obrigatória em todos os perfis. O valor padrão anterior era conhecido e permitiria a falsificação de tokens caso a API fosse iniciada sem configuração. `Backend/scripts/run-api.sh` carrega a chave do arquivo local `Backend/.env`, que não deve ser versionado nem compartilhado. Em instalação nova, gere uma chave aleatória com `openssl rand -base64 32` e salve-a nesse arquivo. Tokens existentes deixam de valer quando a instância passa a usar a nova chave; usuários precisam entrar novamente.
- **Cabeçalhos HTTP:** a API bloqueia enquadramento externo, MIME sniffing e envio desnecessário do referenciador, além de restringir câmera/microfone e manter a geolocalização disponível para o próprio site. API e servidor frontend local aplicam CSP; o host/CDN de produção precisa aplicar política equivalente e HSTS.
- **Sessão web (03/11/2026):** JWT do login/troca de senha é entregue em cookie `HttpOnly`, removido do JSON e do armazenamento local. `AUTH_COOKIE_SECURE` ativa `Secure` em produção. O logout limpa o cookie no servidor; CORS e validação de `Origin` usam `FOODDELIVERY_ALLOWED_ORIGINS`. O servidor local aplica CSP e cabeçalhos de proteção; produção deve aplicar a política equivalente no host/CDN e HSTS no terminador TLS.
- **Notificações direcionáveis:** a V18 acrescenta `target_type` e `target_id`; os tipos são uma enumeração interna, nunca URLs fornecidas pelo usuário. Avisos de pedido, reserva, entrega e chamado podem abrir a área correspondente e destacar o registro exato. Cada tela continua carregando os dados por API autenticada, que verifica a titularidade antes de exibir informações privadas.
- **Recebimentos de vendedor:** o checkout usa o token OAuth da própria loja e direciona o pagamento ao Mercado Pago conectado. O vendedor pode abrir o portal do provedor a partir de **Financeiro** e consultar saldo e retirada lá. O FoodDelivery não recebe a senha do provedor nem inicia saque em nome do vendedor.
- **Entregadores:** ainda não há remuneração por corrida, saldo devido persistido, chave Pix de repasse ou API de transferência configurados. A aba **Financeiro** informa esse estado e não inventa saldo nem aceita pedidos de saque sem obrigação registrada. Para habilitar repasses, definir remuneração e origem dos fundos, criar ledger imutável com idempotência, verificar titularidade da chave Pix e homologar API de transferência e webhook de liquidação.
- **Backups PostgreSQL:** `Backend/scripts/backup-db.sh` cria dump customizado, testa se o arquivo pode ser lido e gera SHA-256, com permissões restritas. `Backend/scripts/restore-db.sh` verifica o hash e exige `RESTORE_CONFIRM_DATABASE` com o nome exato do banco antes de substituir objetos. A restauração é destrutiva para o conteúdo atual. Armazene cópias criptografadas fora do disco/volume principal e faça ensaios periódicos em banco isolado; estes scripts não configuram agendamento, criptografia externa, replicação nem recuperação ponto no tempo.

### Teste e recuperação manual

1. Configure `DATABASE_NAME` (opcional; padrão `fooddelivery`), `DB_PORT`, `DATABASE_USERNAME` e `DATABASE_PASSWORD` em `Backend/.env`.
2. Rode `Backend/scripts/backup-db.sh`; guarde o `.dump` e o `.sha256` em armazenamento protegido fora da máquina.
3. Para recuperação, pare a API, confirme que o backup correto está disponível e execute `RESTORE_CONFIRM_DATABASE=fooddelivery Backend/scripts/restore-db.sh /caminho/fooddelivery-data-hora.dump`.
4. Inicie a API, confira `/actuator/health`, valide registros essenciais e faça uma nova cópia após a recuperação.

Os backups cobrem o PostgreSQL. Arquivos enviados de lojas ficam em `STORE_ASSETS_DIR` e precisam de uma política de cópia própria. Não foi habilitada uma transferência real de dinheiro nem declarada homologação de SMTP, WhatsApp, OpenAI ou Mercado Pago nesta mudança.

## 14. Identidade, perfis, cidades e lojas (V13)

- O cadastro público cria somente a conta de cliente e exige CPF válido. O UUID da conta é a chave primária; CPF tem unicidade própria e nunca é usado como chave ou identificador de rota. Contas suspensas continuam ocupando o CPF e não podem ser recriadas com outra função.
- Cliente, vendedor e entregador são perfis vinculados à mesma conta. Em **Minha conta > Acessos**, a pessoa solicita vendedor ou entregador; a permissão só entra em vigor após aprovação administrativa. A rota antiga de cadastro profissional não cria contas novas e orienta a usar o perfil existente.
- Em **Minha conta > Acessos**, qualquer perfil pode pedir a liberação do FoodDelivery em uma cidade informando município, UF e CEP de referência. O pedido fica pendente; após aprovação, a cidade aparece na lista de localização, mesmo antes de haver loja publicada.
- O vendedor pode informar CNPJ válido, único e imutável quando a loja possuir um; o campo é opcional. A criação gera solicitação de loja; até a aprovação, a loja não aparece para clientes. Lojas adicionais são solicitadas individualmente e o painel permite selecionar qual unidade editar.
- O administrador consulta pendências e histórico em **Solicitações**, aprova ou reprova cada pedido e o sistema registra o responsável e o horário da decisão. A aprovação de perfil, cidade e loja executa a mudança de acesso/publicação correspondente e avisa a conta.

## 15. Recuperação de senha e confirmação de telefone (V14)

- Na tela de login, a pessoa solicita recuperação pelo e-mail cadastrado e escolhe SMTP ou WhatsApp. A API retorna a mesma resposta genérica sem confirmar se a conta existe.
- O código possui seis dígitos, validade de dez minutos, uso único, cinco tentativas e intervalo mínimo de 60 segundos entre emissões. A tabela `recovery_codes` armazena somente o hash BCrypt do código, o prazo, o canal, a contagem de tentativas e o estado de consumo.
- Para recuperação por WhatsApp, o usuário precisa receber e confirmar um código no telefone da conta em **Minha conta > Segurança**. Se o telefone for alterado, o status confirmado volta a falso. O número não confirmado não pode receber código de redefinição por WhatsApp.
- A redefinição chama `Account.changePassword`, incrementando `credential_version`; o validador JWT verifica essa versão em cada autenticação, revogando sessões anteriores. O reset do administrador permanece separado: gera uma senha temporária de uso único exibida no painel, exige troca no próximo acesso e também revoga tokens.
- Os provedores são opcionais em ambiente local. SMTP usa `SMTP_*` e `RECOVERY_EMAIL_FROM`; WhatsApp usa a API Cloud oficial com credenciais apenas no backend e um template de mensagem aprovado. Falhas dos provedores não expõem detalhes nem códigos na resposta HTTP.

## 16. Catálogo de produtos, serviços e pagamento presencial (V15)

- Uma mesma loja, inclusive barbearias, pode oferecer produtos e serviços no mesmo catálogo. Produtos entram na sacola e seguem o checkout; serviços abrem o fluxo de reserva por data e horário.
- Cada serviço pode cobrar antecipadamente pelo checkout ou depois do atendimento, diretamente na loja. O padrão é pagamento antecipado. O vendedor precisa reconhecer o risco de falta do cliente e de não recebimento para habilitar a segunda opção.
- Reservas com pagamento antecipado continuam em `PENDING_PAYMENT` até o provedor confirmar. Reservas com pagamento presencial são confirmadas ao salvar e não geram uma entidade `Payment`; o cliente e a loja recebem aviso claro de que o pagamento ocorrerá fora do site.
- A opção de pagamento é copiada para a reserva no momento da criação. Alterar a configuração do serviço não modifica reservas existentes. A migração V15 adiciona colunas compatíveis com dados antigos, que permanecem no modo antecipado.
- A administração pode filtrar estabelecimentos por todos, aguardando aprovação, confirmados e suspensos. Aprovar é decisão administrativa; não existe etapa de pagamento para publicar uma loja.
- A tela do vendedor carrega lojas, catálogo, pagamentos, solicitações e pedidos de forma independente. Se um serviço auxiliar falhar, o restante do painel continua disponível e a interface aponta a área afetada.
- O formulário **Adicionar ao catálogo** usa uma grade responsiva própria: o painel do catálogo e o formulário empilham quando a área útil fica menor, e os campos de preço/tipo deixam de esticar um ao outro quando a ajuda de preço ocupa mais linhas. Em telas estreitas, os campos passam para uma coluna para que opções como “Produto à venda” e “Serviço agendado” permaneçam legíveis.
- As rotas de loja e catálogo entregam DTOs (respostas com campos explícitos), não entidades JPA. Isso evita serializar relações tardias do Hibernate depois que a sessão do banco fecha; também mantém os dados de conta do proprietário fora da resposta de catálogo.
- Validação local de 28/10/2026 com o acesso de teste da barbearia: login HTTP 200, lista de lojas 200 (loja ativa/aprovada), catálogo 200 e reservas 200. O painel do navegador mostrou a loja como **Confirmada** e **Publicada**, e a aba Catálogo exibiu o tipo de serviço, modo de cobrança e alerta de risco.

## 17. Seções personalizadas do marketplace (V16)

- Em **Definições > Categorias**, o administrador cria uma seção com nome e ícone. Ela passa a aparecer na busca de clientes e na seleção de categoria do cadastro de lojas, porque ambas consomem `GET /api/v1/store-categories`.
- As dez categorias que já existem permanecem fixas no catálogo de domínio. O painel lista essas opções como protegidas; não permite renomear, suspender ou excluir nenhuma delas.
- A administração pode excluir apenas as seções adicionais que criou. Se alguma loja estiver usando uma dessas seções, a API recusa a exclusão e informa quantas lojas precisam primeiro ser associadas a outra categoria. Lojas e histórico não são apagados junto com uma categoria.
- O backend valida a categoria ao cadastrar ou atualizar uma loja. Portanto, enviar uma categoria inventada diretamente à API não contorna a lista disponível.
- A tabela `store_categories`, criada pela migração V16, armazena nome, slug único, ícone e data de criação. As rotas administrativas são `GET/POST /api/v1/admin/store-categories` e `DELETE /api/v1/admin/store-categories/{id}`; exigem papel de administrador.


## Recuperação de inicialização local (31/10/2026)

Quando uma instância antiga da API ainda responde ao endpoint de status, mas o healthcheck falha, `npm start` verifica se o PostgreSQL local do projeto está ativo. Se o banco estiver parado, inicia o serviço existente a partir do diretório de dados já configurado e aguarda até 20 segundos pela recuperação do healthcheck da API. O inicializador não remove arquivos, recria o banco nem inicia outra API em uma porta ocupada. Se a saúde continuar falhando, encerra com erro em vez de declarar o sistema operacional.

Na recuperação observada em 31/10/2026, `/api/v1/status` retornou HTTP 200 enquanto `/actuator/health` expirava; após iniciar o PostgreSQL, o healthcheck retornou `UP`, e `npm start` detectou os serviços ativos nas portas 8080 e 5173.

## Autorização, limitação de tentativas e auditoria (01/11/2026)

- Rotas não listadas explicitamente como públicas exigem uma sessão JWT autenticada. Uma sessão com troca obrigatória de senha não pode acessar essas rotas; as exceções necessárias para concluir a troca continuam explicitamente autorizadas. Testes cobrem chamadas anônimas, autenticadas e sessão com troca pendente.
- O backend limita por IP de origem as tentativas de login (10/15 min), recuperação (5/h) e cadastro (10/h), respondendo `429` e `Retry-After`. Os contadores residem no processo e não leem `X-Forwarded-For`; em proxy ou múltiplas instâncias é necessário configurar limitador compartilhado considerando a topologia.
- V23 acrescenta `admin_audit_events`; administradores consultam `GET /api/v1/admin/audit-events?page=0&size=50` na aba **Definições > Auditoria**. A lista registra mutações administrativas instrumentadas sem senha, CPF ou token.
- A tabela e a autorização padrão são verificadas nos testes automatizados com H2. Isso não certifica implantação no PostgreSQL real, cobertura completa de auditoria, proteção contra alteração por quem tem acesso direto ao banco, nem mitigação distribuída de abuso.
- **Ativação local confirmada em 01/11/2026:** foi validado backup antes da inicialização. A API iniciou no PostgreSQL local e Flyway avançou para V23. Health retornou `UP`; categorias públicas retornaram HTTP 200; auditoria administrativa sem login retornou HTTP 401; interface retornou HTTP 200. A API e o frontend ficaram ativos depois da conferência.

## Localização do cliente e navegação por categorias (02/11/2026)

- A região do cliente pode ser identificada por GPS ao tocar em **Usar minha localização**. O navegador precisa conceder permissão e a página deve estar em HTTPS ou `localhost`. A localização não é acompanhada continuamente; coordenadas precisas são usadas apenas durante a consulta e não são gravadas. A cidade/UF e, quando disponível, o CEP ficam salvos como preferência de navegação.
- O seletor de região permite buscar município por nome e UF, escolher uma cidade disponível, consultar CEP via ViaCEP ou clicar em um ponto do mapa. Se o ViaCEP não reconhecer um CEP, a consulta tenta localizar o código pelo OpenStreetMap/Nominatim. A busca por município e o clique no mapa usam a API autenticada `GET /api/v1/geography/search?query=Iepê%2C%20SP` e `GET /api/v1/geography/reverse?latitude=...&longitude=...`; CEP é preenchido quando o serviço o retorna. Uma cidade pode ter muitos CEPs, portanto escolher somente o município não produz um CEP único.
- O mapa usa Leaflet 1.9.4 e os tiles do OpenStreetMap com atribuição visível. O backend identifica a aplicação com `FoodDelivery/1.0`, limita a uma consulta externa por vez e a pelo menos 1,1 segundo entre consultas, e mantém cache de resultados por 24 horas em memória. O serviço público Nominatim não oferece SLA e impõe limite de uso; limitação/cache são locais à instância e não compartilham estado entre réplicas. Para operação comercial em escala, use um provedor contratado/configurado ou instância própria. A página oferece cidade cadastrada e CEP como alternativas se o mapa/serviço externo não carregar.
- Filtros e contagens de categorias agora reconhecem acentos, singular/plural das categorias padrão e nomes personalizados sem alterar a categoria armazenada. Os botões de seção **Ver todas** agora abrem a lista de estabelecimentos; a página de categoria mantém o retorno para todas as categorias.
- A preferência de região filtra estabelecimentos por cidade e UF na consulta pública. Se GPS for negado, não existir suporte no navegador ou a consulta externa falhar, o cliente continua podendo selecionar uma cidade da lista ou buscar pelo CEP.
