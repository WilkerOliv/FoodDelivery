# Modelo de dados do backend

**Banco:** PostgreSQL 17 · **Migrações:** Flyway em `Backend/src/main/resources/db/`; versões comuns V1–V8 e V10–V23, com V9 adaptada em `migration-postgres` e `migration-h2`.

## Entidades e relações

```mermaid
erDiagram
    ACCOUNTS ||--o{ STORES : owns
    ACCOUNTS ||--o{ CUSTOMER_ORDERS : places
    ACCOUNTS ||--o{ RESERVATIONS : makes
    ACCOUNTS ||--o{ ACCOUNT_NOTIFICATIONS : receives
    ACCOUNTS ||--o{ ADMIN_AUDIT_EVENTS : acts
    STORES ||--o{ CATALOG_ITEMS : offers
    STORES ||--o{ CUSTOMER_ORDERS : receives
    STORES ||--o{ RESERVATIONS : receives
    STORES ||--o| MARKETPLACE_PAYMENT_ACCOUNTS : connects
    CUSTOMER_ORDERS ||--|{ ORDER_LINES : contains
    CUSTOMER_ORDERS ||--o| PAYMENTS : paid_by
    RESERVATIONS ||--o| PAYMENTS : paid_by
    RESERVATIONS ||--o| RESERVATION_SLOTS : occupies
    CUSTOMER_ORDERS ||--o| DELIVERY_TASKS : delivered_as
    ACCOUNTS ||--o{ DELIVERY_TASKS : assigned_courier
```

| Tabela | Conteúdo |
|---|---|
| `accounts` | Identidade, e-mail único, hash de senha, perfil/endereço brasileiro, papel, situação e aprovação de acesso operacional. |
| `stores` | Estabelecimento do comerciante, categoria, endereço, imagem de capa, aprovação, comissão configurável e taxa fixa de entrega. |
| `catalog_items` | Produtos ou serviços, preço em centavos, estoque, disponibilidade, duração de serviço e indicador de serviço. |
| `customer_orders` | Cliente, loja, modalidade, endereço de entrega, subtotal, taxa de entrega congelada, total e estado. |
| `order_lines` | Snapshot do identificador, nome, quantidade e preço unitário dos itens comprados. |
| `payments` | Pagamento de um pedido ou reserva, dados de checkout/provedor e ciclo de vida de estornos. |
| `reservations` | Serviço, estabelecimento, cliente, intervalo início/fim, profissional opcional, valor congelado e estado. |
| `reservation_slots` | Bloqueio de intervalo por loja e recurso (profissional ou loja inteira), liberado quando a reserva deixa de ocupar o horário. |
| `delivery_tasks` | Oferta/atribuição e progresso de uma entrega associada a pedido de delivery. |
| `account_notifications` | Notificações persistidas e isoladas por conta, com data de leitura. |
| `admin_audit_events` | Ações administrativas instrumentadas, alvo, resumo seguro e data; não guarda credenciais ou documentos pessoais (V23). |
| `marketplace_payment_accounts` | Vínculo OAuth da loja com a conta Mercado Pago; tokens são armazenados criptografados. |
| `customer_addresses` | Livro de endereços do cliente, com endereço padrão (V7). |
| `operation_messages` | Mensagens de pedido, reserva ou entrega, com remetente e data (V11). |
| `support_tickets` | Chamados de suporte vinculados a uma conta, assunto, categoria e estado (V12). |
| `support_ticket_messages` | Histórico ordenado de mensagens do chamado, com remetente e data (V12). |
| `platform_requests` | Solicitações de perfil, cidade de operação ou nova loja; registra estado, responsável e decisão (V13). |
| `service_cities` | Cidades liberadas pelo administrador para aparecer na seleção de localização (V13). |
| `recovery_codes` | Hashes de códigos temporários de recuperação/verificação, com validade, tentativas, canal e consumo (V14). |
| `flyway_schema_history` | Versões, checksums e resultado das migrações aplicadas pelo Flyway. |

## Regras de negócio e integridade

- E-mail de conta é único; a busca de identidade não diferencia maiúsculas de minúsculas.
- CPF é validado e armazenado em coluna única, separado do UUID da conta; é obrigatório para cadastro de clientes e perfis profissionais, mas opcional para `ADMIN`. CPF permanece reservado mesmo quando a conta ou um perfil é suspenso; o cadastro nunca cria uma nova identidade para outro tipo de acesso.
- Perfis de cliente, vendedor e entregador pertencem à mesma conta. A solicitação de perfil é revisada pelo administrador; cada decisão aprovada adiciona o perfil à conta sem criar outro usuário.
- A liberação de uma cidade e o cadastro de cada loja são solicitações independentes com histórico de revisão administrativa.
- O administrador também pode cadastrar cidades diretamente em **Definições > Cidades**; cidade suspensa continua registrada e pode ser reativada.
- O CNPJ da loja é opcional. Quando informado, deve ser válido e único; o vendedor não o troca depois de vinculado. O administrador pode corrigir ou remover o documento em **Lojas > Editar dados da loja**, com evento de auditoria. Lojas adicionais passam pela aprovação individual.
- Valores monetários são inteiros em centavos (BRL), sem aritmética de ponto flutuante.
- Cada loja configura sua própria **taxa fixa de entrega**, cobrada somente em pedidos de delivery. Ela é copiada para o pedido no checkout; uma edição futura do perfil da loja não altera pedidos existentes. O valor permitido atualmente vai de R$ 0,00 a R$ 1.000,00.
- Cada loja também configura a comissão do marketplace em pontos-base (0% a 50%). A comissão e a taxa de entrega são conceitos separados.
- Pedidos guardam snapshots de subtotal, taxa, total e linhas; reservas guardam snapshot do valor do serviço.
- Reservas de serviços só podem ser pagas diretamente à loja após o atendimento quando cidade e UF persistidas do cliente correspondem às da loja. Fora da cidade, o backend exige pagamento antecipado, ainda que a configuração do serviço aceite pagamento posterior.
- Pagamento pertence exclusivamente a um pedido ou a uma reserva; uma constraint impede ambos (ou nenhum) e há no máximo um pagamento por alvo.
- O funcionamento semanal e as exceções da loja ficam em `operating_hours_enabled`, `weekly_hours_json` e `hours_exceptions_json` (migração V19); o fuso é o IANA configurado na própria loja. A regra semanal começa desativada para manter lojas legadas compatíveis. Observações de exceções são privadas e removidas da resposta pública.
- Uma reserva ocupa o intervalo completo definido pela duração congelada do serviço. A API serializa a verificação por loja e bloqueia sobreposição no recurso escolhido (profissional ou loja inteira quando não há escolha). A chave `slot_key` não nula e a constraint única também protegem horários idênticos; slots cancelados/expirados são liberados transacionalmente.
- Notificações são associadas a `account_id`; endpoints autenticados consultam apenas a conta da sessão.
- Solicitações de vendedores e entregadores ficam pendentes até a aprovação administrativa; a conta continua ativa como cliente e `operator_application_status` registra o resultado.
- Solicitações novas de perfil e cidade são serializadas por bloqueio da conta durante a verificação e gravação, reduzindo duplicatas concorrentes da mesma pessoa. Dados de uma loja não podem ser editados enquanto a solicitação estiver pendente.
- Uma loja só é considerada disponível quando está ativa e aprovada e sua conta proprietária está ativa com o perfil de vendedor liberado. Suspender a conta ou o perfil de vendedor também desativa e retira a aprovação das lojas; reativar a conta não republica automaticamente as lojas.
- Cada requisição autenticada valida assinatura, emissor e validade do JWT e consulta se a conta do titular continua ativa; a suspensão administrativa revoga imediatamente o acesso do token existente.
- A atribuição de entregador é feita por atualização condicional atômica: só uma requisição consegue atribuir uma tarefa ofertada e ainda sem entregador.
- Mutações do pedido usam bloqueio da linha do pedido. Ofertar entrega é idempotente; cancelamento permitido cancela também a tarefa. Depois de coletar, o cancelamento comum é recusado e exige atendimento. A conclusão pelo entregador conclui o pedido; o vendedor não pode concluir um delivery antecipadamente. Endereço e observação deixam de compor a resposta das tarefas encerradas.
- Alterações do livro de endereços bloqueiam a conta antes de escolher o endereço padrão, serializando mudanças concorrentes dessa pessoa.
- Alterar telefone ou corrigir o e-mail administrativamente invalida códigos de verificação e recuperação pendentes. Um código emitido para o contato anterior não comprova o novo contato.
- Estados de pedido, reserva, pagamento e entrega têm transições controladas no domínio. Pagamentos não confirmam pedidos cancelados sem iniciar o fluxo de estorno.
- Estoque é reservado ao criar pedido; é liberado quando o checkout expira e reposto quando a loja rejeita/cancela o pedido. Itens de serviço não usam estoque.
- O cliente pode cancelar pedido ou reserva antes da aprovação do pagamento; a ação falha/encerra o checkout, libera estoque/horário e avisa a loja. Depois do pagamento, o cliente pode solicitar cancelamento e a loja aceita ou recusa. Aceitar pedido pago repõe estoque e inicia estorno integral; aceitar reserva libera o intervalo e inicia estorno se houve pagamento online. O estado de estorno só é concluído após confirmação do provedor.
- Cada loja pode guardar latitude/longitude opcionais. Valores precisam ser informados em par e estar dentro dos limites geográficos. O link de mapa é calculado na interface; não há publicação automática no Google Business Profile.
- Colunas `version` dão suporte a controle otimista de concorrência em catálogo, pedido, pagamento, reserva e entrega.
- Chaves estrangeiras protegem relações com clientes, lojas, serviços, pagamentos e tarefas.
- Webhooks do Mercado Pago validam assinatura; a rota é publicamente acessível para permitir chamadas do provedor, mas requisições sem assinatura válida não podem atualizar pagamentos.

## Evoluções das migrações V7 a V23

- **V7:** `customer_addresses`.
- **V8:** `delivery_tasks` ganha latitude, longitude e data da localização do entregador.
- **V9:** `accounts` ganha `cpf`, `granted_roles`, `suspended_roles`, `requested_operator_role` e `password_change_required`; `stores` ganha `city` e `state`. Há scripts equivalentes para PostgreSQL e H2, mantendo a mesma versão e os checksums já aplicados no PostgreSQL.
- **V10:** `accounts.credential_version` invalida tokens quando a senha muda.
- **V11:** `customer_note` em pedidos e reservas e a tabela `operation_messages`.
- **V12:** `support_tickets` e `support_ticket_messages`, com vínculo à conta proprietária, estado e autoria de cada mensagem.
- **V13:** coluna única `stores.cnpj`, tabelas `platform_requests` e `service_cities`, com índices por conta/data e estado/data para análise e histórico.
- **V14:** `accounts.phone_verified` e tabela `recovery_codes`. O código em texto puro nunca é persistido; a expiração é de 10 minutos, a contagem de tentativas é limitada e o consumo é registrado.
- **V15:** `catalog_items.pay_after_service` e `reservations.pay_after_service`. A escolha do vendedor é copiada à reserva; pagamento após serviço só é permitido em itens de serviço e reservas assim são confirmadas sem criar um pagamento online.
- **V16:** tabela `store_categories` para seções adicionais criadas pela administração. Categorias padrão continuam no código e não são modificáveis por esta tabela. Seções personalizadas têm slug único e só podem ser removidas quando não houver loja usando a categoria.
- **V17:** profissionais vinculados às lojas e exclusão lógica de itens do catálogo.
- **V18:** `account_notifications.target_type` e `target_id`, destino interno controlado por enum para tornar alguns avisos clicáveis sem armazenar URLs arbitrárias.
- **V19:** rotina semanal, fuso/ativação e exceções de funcionamento por loja.
- **V20:** duração dos serviços, `ends_at` de reserva/slot e chave não nula de recurso para bloquear intervalos concorrentes inclusive quando não há profissional escolhido.
- **V21:** estado, data e motivo da solicitação de cancelamento pelo cliente em pedido e reserva.
- **V22:** latitude e longitude opcionais da loja.
- **V23:** tabela `admin_audit_events`, com ator opcional (preservando o evento se a conta for removida), ação, alvo e resumo seguro. O endpoint de leitura fica restrito ao administrador. A cobertura atual inclui mutações administrativas instrumentadas, mas não é uma trilha completa/imodificável de conformidade.

Contas de operador pendentes continuam ativas como cliente até a aprovação; o acesso profissional só é concedido pelo administrador.

## Estornos

Cancelamento/recusa pela loja de pedido ou reserva já pago gera pedido de estorno integral. A chave de idempotência persistida é reutilizada em tentativas após falha ambígua; tentativas e referência do provedor são registradas em `payments`. Confirmações assíncronas chegam por webhook. Casos que excedem as tentativas ou exigem intervenção entram em `REFUND_REVIEW` para acompanhamento administrativo. O estado do pagamento fica separado do estado do pedido/reserva.

## Limites conhecidos

- A agenda bloqueia o intervalo completo do serviço e impede sobreposição por profissional/recurso. Ainda não há capacidade maior que um por recurso, grade de intervalos configurável, múltiplas pausas por dia ou disponibilidade semanal individual de cada profissional.
- `order_lines.catalog_item_id` e `reservations.catalog_item_id` preservam o identificador do item como snapshot, sem FK que impediria removê-lo do catálogo.
- O estorno automático precisa de homologação com credenciais reais/de teste do Mercado Pago. Em Marketplace Split 1:1, saldo insuficiente na conta do vendedor pode exigir ação manual no provedor.
- Conciliação contábil/liquidação, chargebacks e saque Pix iniciado pela plataforma ainda não fazem parte do modelo implementado. Cancelamentos pós-pagamento iniciados pelo cliente seguem solicitação com decisão da loja.
- Capas são gravadas em armazenamento de arquivos configurável; o banco mantém somente `stores.cover_image_url`.

## Moderação de capas — V24

`store_cover_submissions` referencia a loja e guarda arquivo aleatório único, status, motivo, data de criação, data/administrador da decisão e versão otimista. Estados: PENDING, APPROVED, REJECTED, SUPERSEDED. Índices por loja/data e status/data atendem cota e fila. Aprovação troca `stores.cover_image_url` dentro da mesma transação de auditoria/notificação. Arquivos são persistidos separadamente e precisam acompanhar o backup do banco. Rejeitados/substituídos ficam privados; não há purga automática de histórico.
