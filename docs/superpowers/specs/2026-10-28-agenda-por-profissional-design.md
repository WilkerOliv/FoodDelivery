# Agenda de serviços por profissional

## Intenção

Permitir que barbearias e outros negócios de serviço publiquem uma agenda real, com duração de cada atendimento, disponibilidade dos profissionais e prevenção de conflitos. O cliente poderá escolher um profissional ou pedir o primeiro horário disponível. A experiência deve continuar simples para pessoas com pouca familiaridade com aplicativos.

## Situação atual

- O catálogo já distingue produtos e serviços; serviços podem cobrar antes do atendimento ou diretamente na loja depois dele.
- O cliente abre uma reserva pela loja, escolhe data/hora e pode deixar um comentário. Ainda não escolhe o profissional nem consulta horários disponíveis.
- A API grava o início da reserva e protege apenas a igualdade entre `store_id` e `starts_at`. Não há duração do serviço nem capacidade por profissional, então horários parcialmente sobrepostos não são detectados.
- Pagamentos pendentes ocupam o horário até cancelamento ou expiração; a opção de pagamento direto cria uma reserva confirmada de imediato.
- O painel do vendedor já possui a seção **Agenda**, que lista reservas e permite avançar seus estados.

## Objetivos

- Permitir que o vendedor cadastre e pause profissionais vinculados à própria loja.
- Configurar duração em minutos por serviço e disponibilidade semanal por profissional.
- Mostrar ao cliente apenas horários válidos, no fuso da cidade/loja, com opção de escolher alguém ou pedir o primeiro disponível.
- Reservar de forma atômica o intervalo completo, impedindo duas reservas conflitantes para o mesmo profissional, inclusive em requisições simultâneas.
- Manter os fluxos atuais de pagamento antecipado e pagamento direto, chat, comentário e notificações.
- Identificar o profissional na reserva do cliente e na agenda do vendedor.

## Fora de escopo

- Criar login individual, senha ou novo papel de acesso para cada profissional. No primeiro corte, o vendedor administra os nomes e a agenda da equipe.
- Folha de pagamento, comissão por profissional, recorrência de reservas, integração com calendários externos ou confirmação automática por WhatsApp.
- Alterar a política de pagamento, cancelamento ou estorno já existente.

## Abordagens consideradas

1. **Agenda simples por loja:** cada serviço recebe duração e a loja tem uma única capacidade. É menor, porém impede duas pessoas da equipe de atenderem simultaneamente.
2. **Agenda por profissional (escolhida):** duração por serviço, equipe, disponibilidade individual e reserva por profissional. Suporta o uso real de barbearias e outros serviços locais.
3. **Manter horários pontuais:** preserva a regra atual. Não resolve sobreposição nem escala de equipe.

## Decisões confirmadas

- A agenda será por profissional.
- No agendamento, o cliente poderá escolher um profissional específico ou selecionar **Qualquer profissional disponível**.
- O vendedor configura a equipe e os horários; não haverá nova conta para os profissionais neste escopo.
- Cada loja configura seu fuso horário, com `America/Sao_Paulo` como padrão inicial; horários são apresentados localmente e armazenados como instantes inequívocos.
- Aprovar, suspender ou reativar uma loja continua sendo decisão administrativa independente da agenda.

## Proposta de experiência

### Vendedor

- A seção **Agenda** passa a ter destinos separados: **Reservas**, **Profissionais** e **Horários de atendimento**.
- O cadastro de profissional pede somente nome de exibição, serviços que atende e estado ativo/inativo. A tela de horários permite configurar a semana e exceções/dias de folga.
- Cada serviço apresenta duração em minutos. O formulário mostra um valor padrão de 30 minutos, editável pelo vendedor, e esclarece que reservas existentes não mudam quando a duração futura é alterada.
- A agenda informa serviço, cliente, horário de início e término e profissional. Filtros de dia, profissional e situação mantêm a lista utilizável em telas pequenas.
- Uma loja sem profissional ativo ou sem horário configurado recebe uma orientação explícita e não publica horários reserváveis.

### Cliente

- Ao escolher **Reservar**, a pessoa seleciona data, opção de profissional e um horário retornado como disponível. Comentário e regras de pagamento continuam no mesmo fluxo.
- **Qualquer profissional disponível** deixa a escolha com o sistema; o backend seleciona um profissional ativo que cubra todo o intervalo.
- Antes da confirmação, a pessoa revisa loja, serviço, profissional, data/hora, duração, preço e forma de pagamento.
- A reserva salva o nome do profissional e o horário final como snapshot, para que alterações posteriores de equipe ou duração não mudem o histórico.

## Proposta de implementação

- Adicionar profissionais relacionados à loja, sem permitir leitura ou edição por outras lojas. A autorização deriva sempre da propriedade da loja já autenticada.
- Guardar duração do serviço no item de catálogo, com padrão retrocompatível de 30 minutos e limite validado pelo backend.
- Modelar horas semanais e exceções no fuso configurado para a loja (`America/Sao_Paulo` por padrão). Uma API de disponibilidade recebe serviço, intervalo de datas e profissional opcional e retorna horários válidos; a API de criação revalida tudo dentro da transação. Instantes persistidos devem ser timezone-aware/UTC para tratar transições de horário sem ambiguidades.
- Gravar `professional_id` e `ends_at` na reserva. Para dados antigos, preencher `ends_at` como início + 30 minutos e manter profissional nulo, preservando reservas históricas.
- Substituir a unicidade por instante por proteção de intervalo por profissional. A criação deve serializar operações concorrentes do mesmo profissional/dia e rejeitar interseção de intervalos; horários adjacentes são aceitos. Reservas aguardando pagamento ocupam o intervalo até cancelamento ou expiração; estados que liberam agenda mantêm a regra atual.
- Para **Qualquer profissional disponível**, resolver profissional e gravar a reserva na mesma transação; não depender somente da consulta de disponibilidade do navegador.
- Preservar a moeda em centavos e as regras atuais: pagamento antecipado cria checkout e segura o intervalo; pagamento direto confirma a reserva e informa cliente e loja.
- Adicionar migração Flyway comum e testes que cubram os bancos suportados, incluindo migração de dados existentes.

## Critérios de aceite

1. O vendedor cria/edita/inativa profissionais e define horários sem conseguir acessar outra loja.
2. O catálogo aceita duração válida por serviço; produto comum não ganha duração de agendamento.
3. O cliente vê horários por disponibilidade real, escolhe profissional ou qualquer disponível e recebe confirmação com profissional e fim do atendimento.
4. Reservas sobrepostas para o mesmo profissional são rejeitadas mesmo sob concorrência; horários adjacentes e reservas de profissionais diferentes podem coexistir.
5. Reserva pendente de pagamento ocupa horário; cancelamento/expiração libera; reserva paga ou presencial confirmada continua ocupando até o status que encerra/libera conforme regra existente.
6. Falha de pagamento, reenvio idempotente e duas requisições simultâneas não duplicam reserva nem checkout.
7. Formulários, calendário/lista e estados vazios mantêm os padrões responsivos e acessíveis já presentes no FoodDelivery.

## Chamadas que ficam para o implementador

- Definir granularidade dos horários exibidos (sugestão: blocos de 15 minutos) sem limitar durações a múltiplos de 15.
- Usar bloqueio transacional por profissional e dia junto a uma verificação de interseção no banco; não confiar em validação exclusiva do frontend.
- Reutilizar componentes, cores, controles de formulário e estados responsivos existentes em `Frontend/src/main.js` e `Frontend/theme.css`.
- Se o volume exigir, otimizar a consulta de disponibilidade sem mudar o contrato funcional.

## Aprovação

Este documento descreve o desenho proposto, ainda sem implementação. A implementação começa somente após aprovação do usuário.
