# Modelo de dados do backend

**Banco:** PostgreSQL 17 · **Migrações:** `Backend/src/main/resources/db/migration/V1` a `V6` (Flyway).

## Entidades e relações

```mermaid
erDiagram
    ACCOUNTS ||--o{ STORES : owns
    ACCOUNTS ||--o{ CUSTOMER_ORDERS : places
    ACCOUNTS ||--o{ RESERVATIONS : makes
    ACCOUNTS ||--o{ ACCOUNT_NOTIFICATIONS : receives
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
| `catalog_items` | Produtos ou serviços, preço em centavos, estoque, disponibilidade e indicador de serviço. |
| `customer_orders` | Cliente, loja, modalidade, endereço de entrega, subtotal, taxa de entrega congelada, total e estado. |
| `order_lines` | Snapshot do identificador, nome, quantidade e preço unitário dos itens comprados. |
| `payments` | Pagamento de um pedido ou reserva, dados de checkout/provedor e ciclo de vida de estornos. |
| `reservations` | Serviço, estabelecimento, cliente, horário, valor congelado e estado. |
| `reservation_slots` | Bloqueio único do horário ativo de uma loja, liberado quando a reserva deixa de ocupar o horário. |
| `delivery_tasks` | Oferta/atribuição e progresso de uma entrega associada a pedido de delivery. |
| `account_notifications` | Notificações persistidas e isoladas por conta, com data de leitura. |
| `marketplace_payment_accounts` | Vínculo OAuth da loja com a conta Mercado Pago; tokens são armazenados criptografados. |
| `flyway_schema_history` | Versões, checksums e resultado das migrações aplicadas pelo Flyway. |

## Regras de negócio e integridade

- E-mail de conta é único; a busca de identidade não diferencia maiúsculas de minúsculas.
- Valores monetários são inteiros em centavos (BRL), sem aritmética de ponto flutuante.
- Cada loja configura sua própria **taxa fixa de entrega**, cobrada somente em pedidos de delivery. Ela é copiada para o pedido no checkout; uma edição futura do perfil da loja não altera pedidos existentes. O valor permitido atualmente vai de R$ 0,00 a R$ 1.000,00.
- Cada loja também configura a comissão do marketplace em pontos-base (0% a 50%). A comissão e a taxa de entrega são conceitos separados.
- Pedidos guardam snapshots de subtotal, taxa, total e linhas; reservas guardam snapshot do valor do serviço.
- Pagamento pertence exclusivamente a um pedido ou a uma reserva; uma constraint impede ambos (ou nenhum) e há no máximo um pagamento por alvo.
- Um horário de reserva ativo é único por loja no banco. A tabela `reservation_slots` impede disputa concorrente; slots de reserva cancelada/expirada são liberados transacionalmente.
- Notificações são associadas a `account_id`; endpoints autenticados consultam apenas a conta da sessão.
- Solicitações públicas de vendedores e entregadores ficam pendentes e inativas até a aprovação administrativa; `operator_application_status` registra o resultado.
- Cada requisição autenticada valida assinatura, emissor e validade do JWT e consulta se a conta do titular continua ativa; a suspensão administrativa revoga imediatamente o acesso do token existente.
- A atribuição de entregador é feita por atualização condicional atômica: só uma requisição consegue atribuir uma tarefa ofertada e ainda sem entregador.
- Estados de pedido, reserva, pagamento e entrega têm transições controladas no domínio. Pagamentos não confirmam pedidos cancelados sem iniciar o fluxo de estorno.
- Estoque é reservado ao criar pedido; é liberado quando o checkout expira e reposto quando a loja rejeita/cancela o pedido. Itens de serviço não usam estoque.
- O cliente pode cancelar pedido ou reserva antes da aprovação do pagamento; a ação falha/encerra o checkout, libera estoque/horário e avisa a loja. Operações pagas seguem o fluxo de cancelamento/estorno da loja.
- Colunas `version` dão suporte a controle otimista de concorrência em catálogo, pedido, pagamento, reserva e entrega.
- Chaves estrangeiras protegem relações com clientes, lojas, serviços, pagamentos e tarefas.
- Webhooks do Mercado Pago validam assinatura; a rota é publicamente acessível para permitir chamadas do provedor, mas requisições sem assinatura válida não podem atualizar pagamentos.

## Estornos

Cancelamento/recusa pela loja de pedido ou reserva já pago gera pedido de estorno integral. A chave de idempotência persistida é reutilizada em tentativas após falha ambígua; tentativas e referência do provedor são registradas em `payments`. Confirmações assíncronas chegam por webhook. Casos que excedem as tentativas ou exigem intervenção entram em `REFUND_REVIEW` para acompanhamento administrativo. O estado do pagamento fica separado do estado do pedido/reserva.

## Limites conhecidos

- Horários de reserva são pontuais; duração, capacidade por profissional/equipe e grade de horários não estão modeladas.
- `order_lines.catalog_item_id` e `reservations.catalog_item_id` preservam o identificador do item como snapshot, sem FK que impediria removê-lo do catálogo.
- O estorno automático precisa de homologação com credenciais reais/de teste do Mercado Pago. Em Marketplace Split 1:1, saldo insuficiente na conta do vendedor pode exigir ação manual no provedor.
- Conciliação contábil/liquidação, chargebacks, saque Pix iniciado pela plataforma e cancelamento iniciado pelo cliente ainda não fazem parte do modelo implementado.
- Capas são gravadas em armazenamento de arquivos configurável; o banco mantém somente `stores.cover_image_url`.
