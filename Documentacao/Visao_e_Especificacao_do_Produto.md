# Plataforma de pedidos, reservas e serviços

**Versão:** 1.0 · **Data:** 02/10/2026 · **Status:** Documento-base para validação e planejamento

## 1. Resumo

Plataforma web que conecta consumidores a lojas e prestadores de serviços. O consumidor pode descobrir estabelecimentos, fazer pedidos para delivery ou retirada e reservar serviços presenciais. Pagamentos online são feitos antes da confirmação final do pedido ou da reserva. Comerciantes administram sua loja e atendimentos; entregadores recebem ofertas de entrega e podem aceitá-las, com atribuição exclusiva.

O produto começa como **site responsivo**. Um aplicativo móvel nativo é uma evolução futura; não é requisito do MVP. O cadastro operacional de comerciantes e entregadores é criado por um administrador do sistema. A plataforma não deve armazenar dados de cartão: pagamentos e saques devem ser processados por provedores habilitados, sujeitos a análise técnica, contratual e regulatória.

## 2. Objetivo e resultados esperados

- Permitir que clientes encontrem ofertas locais e concluam pedidos ou reservas pela web.
- Dar aos comerciantes controle sobre catálogo, disponibilidade e atendimento.
- Distribuir entregas sem atribuição duplicada e com rastreabilidade de estados.
- Registrar pagamentos, estornos e repasses com conciliação financeira auditável.
- Validar a operação em uma região e conjunto de categorias antes de expandir.

**Indicadores iniciais (metas a definir após linha de base):** conversão de busca em pedido/reserva; taxa de pagamento aprovado; cancelamentos e reembolsos; tempo até aceite do comerciante e do entregador; entregas concluídas; reservas não comparecidas; falhas de atribuição duplicada (meta: zero); divergências de conciliação.

## 3. Escopo

### MVP web

- Cadastro, login, recuperação de acesso e perfil do cliente.
- Descoberta de estabelecimentos, páginas de loja, catálogo e disponibilidade.
- Pedido de delivery e pedido para retirada no local.
- Reserva de serviço presencial com seleção de modalidade, data/horário e itens/serviços aplicáveis.
- Pagamento antecipado integrado a provedor para confirmar pedido/reserva; estados pendentes, aprovados, recusados, expirados e reembolsados.
- Completar dados essenciais do cliente no primeiro fluxo transacional, antes da confirmação operacional.
- Painel do comerciante: perfil, catálogo/serviços, disponibilidade, notificações e gestão de pedidos e reservas em áreas separadas.
- Painel do administrador: cadastro e suspensão de comerciantes e entregadores, concessão de papéis e auditoria das ações administrativas.
- Painel do entregador: disponibilidade, ofertas, aceite/recusa e atualização das etapas da entrega.
- Histórico e notificações essenciais, com preferência por notificações no site e e-mail; canais finais dependem de decisão técnica.
- Registro de taxas, valores devidos, saques PIX via provedor e conciliação; saque sujeito a validação de titularidade, saldo disponível e regras antifraude.

### Fora do MVP / evolução

- Aplicativo móvel nativo, carteira armazenada pela plataforma, programa de fidelidade e recomendações avançadas.
- Pagamento em dinheiro ou pagamento na entrega/retirada, pois contradiz a regra inicial de pagamento antecipado.
- Reserva sem pagamento para modalidades com consumo/serviço contratado, exceto se uma política explícita por categoria vier a ser aprovada.
- Rastreamento GPS em tempo real, roteirização e despacho automático sofisticado.
- Saque instantâneo garantido: disponibilidade, prazo, limites e tarifas dependem do provedor e das regras aplicáveis.

## 4. Perfis e permissões

| Perfil | Como é criado | Capacidades principais |
|---|---|---|
| Cliente | Auto cadastro | Consultar lojas; gerenciar dados; pagar; pedir delivery/retirada; reservar; acompanhar e cancelar conforme política; consultar histórico. |
| Comerciante/prestador | Criado e habilitado por administrador | Editar somente suas lojas e ofertas; configurar horário/capacidade; aceitar ou recusar pedidos e reservas; atualizar preparação, retirada e conclusão; consultar notificações e visão financeira permitida. |
| Entregador | Criado e habilitado por administrador | Definir disponibilidade; receber ofertas elegíveis; aceitar ou recusar; acompanhar somente entregas atribuídas; atualizar coleta e entrega. |
| Administrador do sistema | Provisionado de forma controlada | Criar, revisar, suspender e reativar comerciantes/entregadores; atribuir papéis mínimos necessários; consultar trilha de auditoria e suporte operacional. |

Aplicar autorização no servidor em todas as operações. Um comerciante nunca altera dados de outra loja; um entregador só vê os dados estritamente necessários às ofertas/entregas atribuídas. Exigir MFA para administradores e proteger ações financeiras/administrativas sensíveis.

## 5. Regras de negócio e decisões recomendadas

1. **Conta e cadastro progressivo:** no cadastro inicial solicitar apenas dados básicos e credenciais. Antes do primeiro pedido/reserva, exigir os dados necessários à operação. Para delivery, isso inclui endereço e instruções de entrega; para retirada, contato; para reserva, contato e dados exigidos pelo estabelecimento. Coletar documento ou data de nascimento somente quando houver necessidade justificada. A transação permanece em rascunho até os dados obrigatórios serem validados.
2. **Pagamento e confirmação:** criar pedido/reserva pendente, reservar temporariamente estoque/capacidade e iniciar pagamento. Confirmar operação apenas com confirmação confiável do provedor (webhook autenticado ou consulta de status). Se o pagamento falhar/expirar, liberar estoque/capacidade. Idempotência deve impedir pedido ou cobrança duplicada em retentativas.
3. **Retirada:** cliente paga online antes de o estabelecimento aceitar/preparar definitivamente. Exibir instruções e código de retirada após a confirmação.
4. **Delivery:** pagamento antecipado é requisito para confirmar o pedido e encaminhá-lo à operação. Entrega só entra na fila de ofertas depois de aceita/preparada segundo configuração operacional. Falta de entregador não deve perder o pagamento: apresentar estado e prazo, permitir cancelamento conforme política e iniciar estorno quando devido.
5. **Reserva:** pagamento é obrigatório antes de confirmar a reserva. A reserva deve conter data, horário, quantidade de pessoas quando aplicável, serviço/itens, duração e política de atraso/cancelamento. Bloquear capacidade/horário de forma atômica para evitar dupla reserva. Definir janela de tolerância e regra de cancelamento/reembolso por categoria/loja.
6. **Categorias:** restaurantes, barbeiros, cabeleireiros e outros serviços têm necessidades distintas. Modelar ofertas como produtos ou serviços, com duração, capacidade, opção delivery/retirada/presencial e regras de agendamento configuráveis; evitar assumir que todo serviço é um produto com estoque.
7. **Aceite de pedidos:** o comerciante deve aceitar ou recusar em prazo configurável. Se recusar ou expirar sem resposta, cancelar a operação e iniciar estorno quando o pagamento tiver sido aprovado.
8. **Atribuição de entregador:** cada pedido pode ter no máximo uma atribuição ativa. A aceitação deve ser uma operação atômica condicionada a “sem entregador atribuído” e “oferta ainda válida”; somente uma confirmação vence. Os demais recebem indisponibilidade. Expirar ofertas e permitir redistribuição controlada se houver recusa, timeout ou falha.
9. **Estados separados:** estados de pagamento, pedido/reserva e entrega são entidades relacionadas, mas não intercambiáveis. Guardar histórico de transições com autor e data.
10. **Financeiro:** separar valor bruto, comissão/taxas, valor líquido do comerciante, saldo pendente e disponível. Só liberar saque após liquidação e conciliação; saques têm idempotência e estado próprio. Estornos e chargebacks ajustam os saldos e podem bloquear retiradas relacionadas.
11. **Cancelamento:** política explícita por etapa e categoria. Mostrar antes do pagamento quem pode cancelar, prazos, taxas e previsão de reembolso. Não prometer estorno imediato; refletir o estado informado pelo provedor.
12. **Fuso e valores:** persistir horários com fuso e apresentar hora local do estabelecimento; armazenar valores em unidade monetária mínima inteira e moeda explícita.

## 6. Fluxos principais

### 6.1 Cliente — pedido

1. Entra ou cria conta com dados básicos.
2. Informa localização/endereço e escolhe delivery ou retirada.
3. Consulta lojas abertas, prazo estimado, taxa e catálogo; monta carrinho.
4. Se necessário, completa os dados essenciais e confirma endereço/contato.
5. O sistema valida preço, disponibilidade, área atendida e capacidade; cria operação pendente com prazo de retenção.
6. Cliente paga no provedor. O sistema confirma pelo retorno seguro do provedor, envia ao comerciante e exibe protocolo/estado.
7. Comerciante aceita, prepara e marca pronto; para delivery, oferta é publicada a entregadores elegíveis; para retirada, cliente recebe instrução/código.
8. Acompanham-se estados até conclusão, cancelamento ou reembolso.

### 6.2 Cliente — reserva de serviço presencial

1. Escolhe loja, serviço/itens, quantidade e modalidade presencial.
2. Seleciona data/horário disponível e fornece dados essenciais.
3. Sistema bloqueia capacidade temporariamente e inicia pagamento.
4. Com pagamento confirmado, converte bloqueio em reserva confirmada e notifica cliente e estabelecimento.
5. Comerciante confirma preparação/atendimento e marca comparecimento/conclusão ou ausência, registrando motivo quando necessário.

### 6.3 Comerciante

1. Administrador cria usuário, vincula estabelecimento e concede papel.
2. Comerciante completa perfil, horário, área atendida, catálogo/serviços, preços, capacidade e políticas; publicação pode depender de aprovação administrativa.
3. Recebe pedidos e reservas em filas separadas, com alerta de prazo.
4. Aceita/recusa e atualiza somente transições válidas; acompanha preparação, retirada ou atendimento.
5. Consulta extrato de valores e saques, sem editar lançamentos liquidados.

### 6.4 Entregador

1. Administrador cria e habilita conta após validação definida pela operação.
2. Entregador fica disponível e recebe ofertas compatíveis.
3. Aceita ou recusa. No aceite, o servidor disputa atomicamente a atribuição; só um entregador fica associado.
4. Atualiza coleta e entrega, com horário e evidência mínima definida pela operação.
5. Se perder a disputa ou a oferta expirar, recebe atualização e pode seguir para outra oferta.

## 7. Modelo de estados

**Pagamento:** `pendente → autorizado/confirmado → liquidado`; alternativas: `falhou`, `expirado`, `estorno_pendente → estornado`, `contestação`.

**Pedido:** `aguardando_pagamento → aguardando_aceite → aceito → em_preparação → pronto → concluído`; alternativas controladas: `recusado`, `cancelado`, `expirado`.

**Reserva:** `aguardando_pagamento → confirmada → em_atendimento → concluída`; alternativas: `cancelada`, `expirada`, `não_compareceu`.

**Entrega:** `aguardando_oferta → ofertada → atribuída → coletada → em_rota → entregue`; alternativas: `sem_entregador`, `falha`, `cancelada`, `reoferta`.

Cada transição deve validar estado anterior, papel autorizado, motivo exigido quando aplicável e efeitos financeiros/operacionais. Interfaces não são fonte de verdade para a transição.

## 8. Requisitos funcionais priorizados

### Prioridade P0 — necessária ao MVP

- **RF01** Criar conta de cliente, autenticar, sair e recuperar acesso com proteção contra abuso.
- **RF02** Completar e validar dados do cliente conforme o tipo de transação.
- **RF03** Listar lojas por localização, modalidade, disponibilidade e categoria; exibir horários, taxas, prazos e políticas relevantes.
- **RF04** Gerenciar produtos/serviços, preços, variantes, disponibilidade e dados de agendamento.
- **RF05** Criar carrinho/pedido e reservar horário/capacidade com expiração, validação de preço no servidor e prevenção de duplicidade.
- **RF06** Integrar pagamento web com provedor, webhooks verificados, idempotência, consulta de status e registro de estorno.
- **RF07** Confirmar pedido/reserva só após pagamento válido e notificar partes interessadas.
- **RF08** Fornecer painéis do comerciante com filas separadas de pedidos e reservas e transições permitidas.
- **RF09** Prover fluxo de retirada com instruções e conclusão rastreável.
- **RF10** Criar ofertas de delivery e garantir atribuição exclusiva de entregador.
- **RF11** Permitir operação e atualização de entrega apenas pelo entregador atribuído.
- **RF12** Prover painel administrativo para cadastro, vínculo, papel, suspensão e auditoria de comerciantes e entregadores.
- **RF13** Manter histórico de operações, notificações e consulta de pedidos/reservas pelo cliente.
- **RF14** Registrar lançamentos financeiros, saldo por estado, pedido de saque PIX e conciliação pelo provedor.
- **RF15** Permitir cancelamento conforme políticas configuradas, com efeito e status de reembolso rastreáveis.

### Prioridade P1 — após validar operação básica

- Avaliações verificadas, cupons, busca avançada, reoferta automatizada, painel de métricas, relatórios e notificações multicanal.
- Cadastro de equipe do comerciante com papéis granulares; múltiplas unidades e regras específicas por categoria.

## 9. Requisitos não funcionais

- **Segurança:** HTTPS; senhas armazenadas com algoritmo apropriado; gestão segura de sessões; MFA administrativo; autorização por papel e vínculo; proteção contra CSRF/XSS/SQL injection; limites de requisição; gestão de segredos; trilha de auditoria imutável para ações sensíveis.
- **Privacidade:** minimização de dados, finalidade e retenção documentadas, controles de acesso, exportação/exclusão conforme obrigações aplicáveis, consentimentos quando necessários e avaliação de conformidade com LGPD antes de produção.
- **Pagamentos:** não guardar número completo de cartão/CVV; verificar assinatura de webhook; tolerar eventos repetidos e fora de ordem; conciliar operações com o provedor.
- **Consistência:** transições críticas e disputa de entregador atômicas; unicidade de atribuição ativa por entrega; reserva de estoque/capacidade concorrente segura.
- **Disponibilidade e recuperação:** definir metas de disponibilidade, RPO/RTO, backup testado, observabilidade e procedimento de incidente antes do lançamento.
- **Desempenho e acessibilidade:** metas a definir por teste de carga; páginas responsivas, navegação por teclado, rótulos acessíveis e contraste adequado.
- **Manutenibilidade:** APIs documentadas, migrações versionadas, logs estruturados sem dados sensíveis, ambientes separados e configuração explícita de estados/políticas.

## 10. Épicos e backlog inicial

Estimativas são deliberadamente relativas (S/M/L); o time refina depois de escolher tecnologia, provedor e regras comerciais.

| ID | Épico / história | Prioridade | Critérios de aceite resumidos | Tamanho |
|---|---|---:|---|---:|
| E1 | Como cliente, quero criar conta e recuperar acesso | P0 | Valida entradas; sessão segura; recuperação expira e não revela se e-mail existe. | M |
| E2 | Como cliente, quero completar meu perfil no primeiro fluxo | P0 | Campos variam por modalidade; não permite prosseguir com dados obrigatórios ausentes; explica uso. | M |
| E3 | Como cliente, quero encontrar lojas e serviços disponíveis | P0 | Filtros respeitam localização, horário e modalidade; mostra preço/prazo/política. | L |
| E4 | Como comerciante, quero configurar loja, catálogo e horários | P0 | Só altera lojas vinculadas; valida dados; disponibilidade publicada corresponde à configuração. | L |
| E5 | Como cliente, quero montar pedido para delivery ou retirada | P0 | Revalida preço e disponibilidade no servidor; mantém snapshot do preço comprado; expiração libera capacidade. | L |
| E6 | Como cliente, quero reservar serviço presencial | P0 | Horários concorrentes não duplicam capacidade; reserva só confirma após pagamento; política visível. | L |
| E7 | Como cliente, quero pagar online e acompanhar status | P0 | Sucesso/falha/timeout/webhook repetido tratados; não cobra nem cria operação duplicada. | L |
| E8 | Como comerciante, quero aceitar e acompanhar pedidos e reservas | P0 | Filas distintas; prazos claros; transições autorizadas; recusa inicia fluxo de reembolso quando aplicável. | L |
| E9 | Como entregador, quero aceitar uma entrega disponível | P0 | Em concorrência, exatamente um aceite é confirmado; demais recebem estado atualizado; ação fica auditada. | L |
| E10 | Como administrador, quero criar e suspender operadores | P0 | Papel mínimo; credencial segura; suspensão revoga acesso; toda ação auditada. | M |
| E11 | Como comerciante, quero ver saldo e solicitar saque PIX | P0* | Apenas saldo disponível; chave validada por provedor; idempotência; estado e falhas visíveis. | L |
| E12 | Como suporte, quero tratar cancelamentos e conciliar pagamentos | P0 | Ações justificadas e auditadas; relatório identifica divergências; reembolso não duplica. | L |

`*` O saque é parte solicitada do produto, mas a inclusão no primeiro lançamento depende de provedor/plataforma aptos a split, saldo e payout. Se indisponível, lançar pedidos e conciliação e manter repasse manual controlado como processo operacional temporário, sujeito a validação legal/financeira.

### Exemplos de critérios detalhados

**Atribuição exclusiva:** Dado um pedido em oferta válida e dois entregadores elegíveis, quando ambos tentam aceitar simultaneamente, então uma única transação altera a atribuição para um entregador, a outra retorna conflito/indisponível e não cria vínculo ativo; o resultado é persistido mesmo após repetição da requisição.

**Reserva paga:** Dado horário com capacidade disponível, quando dois clientes tentam reservar a última vaga, então só uma retenção temporária é criada; apenas pagamento confirmado antes do vencimento transforma a retenção em reserva; falha/expiração libera a vaga.

**Confirmação de pagamento:** Dado callback repetido ou atrasado, quando o sistema processa o evento, então não duplica pedido, reserva, saldo ou reembolso, e consulta o estado autoritativo do pagamento quando necessário.

## 11. Scrum e Kanban

Aplicar Scrum para ciclos de descoberta e entrega de incrementos, usando um quadro Kanban para visualizar o fluxo diário. Scrum e Kanban podem coexistir: manter objetivos e eventos de Sprint, limitando trabalho em progresso e medindo fluxo. Não chamar o quadro de “Kabam”; o nome correto é **Kanban**.

### Papéis Scrum

- **Product Owner:** prioriza valor, mantém Product Goal e Product Backlog, esclarece critérios e decide escopo de Sprint.
- **Scrum Master:** ajuda o time a aplicar Scrum, remove impedimentos e melhora fluxo; não é gerente de tarefas.
- **Developers:** time multifuncional responsável por plano da Sprint, qualidade e incremento utilizável. Incluir produto, design, engenharia, QA e operações conforme tamanho do time; segurança/privacidade e financeiro participam quando necessário.
- **Stakeholders:** clientes, comerciantes, entregadores, suporte e administração contribuem com feedback; não atribuem trabalho diretamente durante Sprint.

### Cadência proposta

- Sprint de 2 semanas, ajustável após alguns ciclos.
- Sprint Planning: objetivo, capacidade, itens selecionados e plano inicial.
- Daily Scrum: 15 minutos, Developers inspecionam progresso e ajustam plano.
- Refinamento contínuo: detalhar histórias futuras; não é evento obrigatório do Scrum.
- Sprint Review: demonstrar incremento e adaptar backlog com stakeholders.
- Sprint Retrospective: escolher melhoria verificável para o ciclo seguinte.
- Product Goal inicial: validar uma jornada completa de pedido/reserva e operação em uma área piloto com pagamento e tratamento operacional confiáveis.

### Quadro Kanban sugerido

`Ideias → Backlog pronto → Em desenvolvimento → Revisão de código → Verificação → Pronto para liberar → Concluído`

- Colunas devem representar etapas reais; política explícita de entrada/saída e responsável por desbloqueio.
- Limite inicial de WIP: 1 item por pessoa em desenvolvimento e revisão; calibrar com o time. Puxar novo trabalho só quando houver capacidade.
- Bloqueios visíveis com motivo, responsável e próxima ação; itens urgentes seguem política de expedição acordada e não viram atalho silencioso.
- Medir lead time, cycle time, throughput, idade dos itens e bloqueios; usar dados para previsão, não metas individuais.
- Definição de pronto para desenvolvimento (Ready): valor/usuário claros, critérios testáveis, dependências e política de dados/pagamento conhecidas, tamanho que caiba no fluxo.

### Definição de Concluído (DoD)

Uma história só é concluída quando os critérios de aceite passam; revisão por par concluída; controles de autorização e concorrência considerados; logs/auditoria sem segredo ou dado pessoal desnecessário; acessibilidade básica revisada; documentação operacional/API atualizada; mudanças implantadas em ambiente de validação; e PO aceita o incremento. Para pagamentos, incluir cenários de timeout, repetição, webhook inválido/duplicado e conciliação. **Não liberar produção** enquanto revisão de segurança, privacidade, suporte a estorno e plano de recuperação não estiverem aprovados.

## 12. Plano incremental sugerido

1. **Descoberta e decisões:** região/categorias piloto, políticas de cancelamento, logística, taxas/comissão, provedor de pagamento e payout, dados mínimos e requisitos fiscais/privacidade.
2. **Fundação:** arquitetura, identidade, papéis, auditoria, modelo de loja/ofertas, ambiente e observabilidade.
3. **Compra simples:** descoberta, catálogo, carrinho, pagamento e retirada; operação do comerciante.
4. **Agenda e reservas:** disponibilidade, bloqueio concorrente, pagamento e operação de serviço presencial.
5. **Delivery:** fila de entregas, aceite atômico, estados e prova de entrega.
6. **Financeiro e piloto:** conciliação, estorno, payout PIX, suporte, carga/segurança, operação piloto e feedback.

As etapas não são estimativas de prazo; a equipe deve planejar por capacidade e validar dependências. O MVP pode ser lançado por modalidade se cada fluxo liberado estiver completo e suportado operacionalmente.

## 13. Riscos e mitigação

| Risco | Tratamento recomendado |
|---|---|
| Cobrança duplicada ou pedido sem confirmação | Idempotência, estados explícitos, verificação server-side e conciliação diária. |
| Dupla reserva ou dupla atribuição de entrega | Restrições e transações atômicas no banco; testes de concorrência antes de produção. |
| Provedor não suporta split/payout adequado | Selecionar provedor antes de fechar desenho financeiro; piloto financeiro e plano operacional alternativo revisado. |
| Comerciante não aceita a tempo / sem entregador | SLA configurável, timeout, comunicação clara, cancelamento e estorno rastreável. |
| Dados excessivos ou exposição entre perfis | Minimização, revisão de acesso por papel, retenção e avaliação LGPD. |
| Categorias têm operação incompatível | Configurações por modalidade/categoria e lançamento gradual; evitar regras universais inadequadas. |
| Disputa, fraude, chargeback ou entrega contestada | Evidências e trilha de eventos, política publicada, limites e revisão humana de casos. |
| Saque PIX errado ou fraudulento | Validação de beneficiário pelo provedor, MFA/reauth em alteração, limites, idempotência e reconciliação. |

## 14. Decisões já confirmadas e estado do primeiro corte

Decisões recebidas do responsável pelo produto:

- O produto inicial será **site responsivo**, operando no **Brasil** e em **BRL**.
- A identidade visual será **laranja e escura**, inspirada na linguagem de delivery do iFood, sem copiar marca ou ativos proprietários.
- O provedor previsto é **Mercado Pago**; a estratégia é conectar a conta de cada vendedor e usar checkout de marketplace.
- A **comissão é configurável por loja**.
- A **taxa de entrega é fixa por loja e ajustada pelo comerciante**; o valor é mostrado antes do pagamento e congelado no pedido.

No código atual, existem cadastro/login e perfis com papéis, painéis por perfil, catálogo e perfil da loja, pedidos de delivery/retirada, reservas pagas, notificações persistidas por conta, checkout Mercado Pago com OAuth por loja, webhook validado, bloqueio concorrente de horário/entrega, expiração de checkout, estoque temporário, estorno automático rastreado e telas responsivas. Categorias de lojas cobrem restaurantes, mercados, conveniências, lanchonetes, pizzarias, cafeterias, farmácias e serviços. Vendedores e entregadores podem solicitar acesso, sujeito à aprovação administrativa; administradores podem aprovar, recusar, suspender e reativar operadores e lojas. Vendedores podem gerar e salvar capas de loja com IA quando a chave OpenAI estiver configurada no backend.

O primeiro corte ainda não deve ser tratado como pronto para operação comercial: recuperação de senha, MFA, cancelamento pelo cliente após o pagamento com política, agenda com duração/capacidade, SLA de aceite, trilha de auditoria completa, conciliação financeira/chargebacks e saque Pix iniciado pela plataforma continuam pendentes. O cliente consegue cancelar um pedido/reserva ainda não pago, liberando o estoque/horário. A integração Mercado Pago requer credenciais e homologação. A geração de imagens depende da configuração de chave e cotas de uso OpenAI; armazenamento local de capas e limitação de geração em memória precisam ser substituídos por armazenamento e cotas compartilhados em implantação multi-instância. Também faltam validação operacional, testes automatizados abrangentes, revisão de acessibilidade/segurança e política publicada por categoria.

## 15. Decisões pendentes antes do compromisso de prazo

1. Cidade/região piloto e categorias que entram primeiro.
2. Quem define raio/área de entrega, SLA e responsabilidade pelos entregadores.
3. Habilitação comercial do Mercado Pago, prazos/taxas, regras de split e obrigações de KYC.
4. Regras de cancelamento, no-show, atraso, estorno parcial e disputa por categoria.
5. Dados obrigatórios por modalidade, base legal, retenção, termos e políticas de privacidade.
6. Como serão configurados capacidade, duração, estoque e horários por loja.
7. Canais de notificação, suporte e horário de operação.
8. Metas mensuráveis de disponibilidade, desempenho, escala inicial e recuperação.
9. Tecnologia de hospedagem, integrações operacionais e estimativa após descoberta.

## 16. Glossário

- **Pedido:** compra de produtos, com delivery ou retirada.
- **Reserva:** bloqueio confirmado de capacidade/horário para atendimento presencial, associado a serviços e/ou itens.
- **Retenção:** bloqueio temporário de estoque ou horário enquanto o pagamento é concluído.
- **Conciliação:** comparação dos registros internos com liquidações/estornos reportados pelo provedor.
- **DoD:** Definition of Done, critérios de qualidade compartilhados para considerar incremento concluído.
- **WIP:** Work in Progress, itens simultaneamente em andamento no quadro.

---

Este documento orienta descoberta e refinamento. Políticas financeiras, legais, privacidade e operação devem ser validadas com especialistas e com o provedor escolhido antes do lançamento.
