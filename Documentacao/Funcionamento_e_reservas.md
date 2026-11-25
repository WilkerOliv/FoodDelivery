# Funcionamento da loja e reservas

> [NÃO VERIFICADO] Escopo da revisão de 25/11: instruções e resultados históricos sem evidência individual nesta página aguardam confirmação. Os testes repetidos têm comando e resultado na [matriz de aceite](Aceite_2026-11-25.md).

Revisão: 05/11/2026. Este documento descreve a implementação atual e seus testes. Valores e contas usados na homologação são fictícios.

## 1. Configuração pelo vendedor e pelo administrador

- Vendedor: **Minha loja → Dados da loja → Funcionamento e exceções**. Confira “Loja em edição” quando houver mais de um estabelecimento.
- Administrador: **Lojas → Editar dados → Configurar dias, turnos e exceções**. Perfil e funcionamento são salvos separadamente.
- O CNPJ continua opcional. Não é necessário ter CNPJ para configurar o expediente.
- A rotina semanal é opcional. Desativada, não restringe os dias da semana; exceções por data continuam valendo.
- Ativada, cada dia pode ficar fechado, funcionar 24 horas ou ter até seis turnos. Exemplo: segunda, quarta e sexta, 08:00–12:00 e 14:00–20:00; domingo, 08:00–12:00. Dias desmarcados não aceitam novas operações.
- “Adicionar turno” permite pausas. “Remover turno” remove apenas a faixa em edição. “Salvar funcionamento” valida e persiste a configuração inteira.
- Horários de abertura e fechamento precisam ser diferentes. Para um dia inteiro use “24 horas”; internamente é 00:00–24:00. Fechamento anterior à abertura representa a madrugada seguinte.
- Turnos sobrepostos são rejeitados, inclusive entre dias e na virada domingo/segunda. Turnos adjacentes são permitidos.

### Datas especiais e imprevistos

“Fechar hoje” usa a data no fuso da loja. O editor de data permite fechar o dia inteiro ou definir turnos especiais, inclusive um encerramento antecipado. Há até 90 datas; editar uma data substitui sua regra, remover restaura a rotina habitual. Uma observação interna ajuda a operação e não é exibida ao cliente.

A exceção substitui **o dia inteiro do calendário**: fechar a terça também bloqueia a continuação de um turno iniciado na segunda. Sem exceção na terça, o turno da segunda pode continuar normalmente pela madrugada. Esta precedência evita que um fechamento excepcional seja contornado pelo expediente do dia anterior.

## 2. Compras e agendamentos

| Situação | Comportamento |
|---|---|
| Comprar produto com loja fechada agora | A criação e o início do checkout são rejeitados pelo servidor. |
| Reservar serviço futuro com loja fechada agora | Permitido, se o intervalo futuro couber no expediente, houver recurso livre e a loja estiver publicada e aceitar atendimento presencial. |
| Serviço atravessa pausa ou termina depois do fechamento | Rejeitado, mesmo que seu início esteja em horário aberto. |
| Serviço termina exatamente no fechamento | Permitido. O fechamento é exclusivo para novos inícios. |
| Loja sem restrição semanal | Agenda continua sujeita às exceções datadas e aos conflitos por profissional/recurso. |
| Expediente alterado depois de uma reserva | Exige a decisão descrita abaixo quando houver impacto em reservas ativas. |

A duração do serviço, de 5 a 480 minutos, é congelada na reserva. A API compara a cobertura exata do intervalo com os períodos abertos; não usa amostragem que possa ignorar uma pausa curta. A interface repete a validação para orientar imediatamente, mas o servidor é a autoridade. Seleção do profissional ou primeiro disponível e antecedência mínima permanecem aplicadas.

Pedidos de produtos já aceitos não são cancelados por uma alteração de expediente. Continuam no fluxo operacional existente. Não existe promessa de concluir uma entrega antes do fechamento: o transporte já aceito tem seu próprio ciclo.

### Fuso horário

A loja usa um identificador IANA, com Brasília como padrão. Reservas guardam instantes fixos e são exibidas no relógio da loja. Trocar o fuso com reservas futuras ou em atendimento é bloqueado para vendedor e administrador: esses compromissos precisam ser resolvidos primeiro. Isso evita mudar silenciosamente o horário combinado. Sem compromissos ativos, a troca é permitida.

## 3. Prévia e tratamento de compromissos afetados

Toda gravação da interface consulta uma prévia antes de confirmar. Ela lista serviço, cliente, instante, estado e modalidade de pagamento dos compromissos que deixarão de caber no novo expediente.

1. **Voltar sem salvar:** não muda o expediente nem as reservas; os controles voltam a ficar disponíveis.
2. **Manter reservas e avisar:** preserva horário, estado, pagamento pendente e ocupação do profissional. A reserva recebe uma exceção explícita ao expediente e aviso vinculado na plataforma. Novas reservas continuam sujeitas à regra nova. O checkout da reserva preservada pode prosseguir, respeitando as demais validações.
3. **Cancelar reservas e avisar:** cancela atendimentos ainda não iniciados, libera os slots e encerra o checkout pendente. Pagamentos online aprovados recebem solicitação de estorno integral. Um pagamento recebido tardiamente também segue para estorno e não reativa a reserva.
4. **Em atendimento:** sempre preservado e avisado, inclusive quando o operador escolher cancelar os demais.

A interface informa quantos avisos, cancelamentos e preservações foram realizados. O aviso do cliente usa texto fixo e não divulga o motivo interno do fechamento. Reservas mantidas exibem esse aviso em suas telas. Se uma alteração posterior voltar a comportar o atendimento, a exceção individual é removida. Repetir a mesma gravação não repete os avisos de uma reserva já preservada.

O aviso é **interno ao site**, vinculado à reserva. Esta funcionalidade não promete envio por WhatsApp, SMS ou e-mail. Estorno solicitado não é dinheiro já creditado: a conclusão depende da confirmação do provedor. Na homologação, o provedor é simulado.

## 4. Implementação e segurança

### Backend

- `StoreOperatingHours`: normaliza turnos e exceções, interpreta os dados legados, valida limites/sobreposição e calcula abertura e cobertura integral no fuso da loja.
- `StoreHoursChangeService`: separa prévia e gravação, verifica titularidade, coordena reservas/pagamentos/slots, emite avisos e registra auditoria `STORE_HOURS_CHANGED`.
- A loja é bloqueada durante a prévia e a gravação, usando o mesmo bloqueio da criação de reservas. A gravação também bloqueia as reservas ativas futuras. Isso serializa as operações concorrentes relevantes.
- A prévia devolve uma revisão do expediente e os IDs afetados. Confirmar com revisão ou conjunto de reservas desatualizado resulta em conflito e exige nova prévia. A API não confia na contagem enviada pelo navegador.
- Alteração de expediente, cancelamento, liberação de slot, estado de pagamento, aviso e auditoria participam da mesma transação. Falha implica rollback. O processamento externo do estorno permanece no mecanismo existente de repetição/idempotência.
- `@DynamicUpdate` na loja reduz o risco de gravações de outros campos sobrescreverem JSON de expediente desatualizado. Atualizações de perfil usam bloqueio da loja.
- Rotas de administrador exigem ADMIN; rotas do vendedor exigem MERCHANT e titularidade. A exceção individual de reserva só é criada no servidor e não pode ser solicitada pelo cliente.

### Contratos HTTP

Prefixo `/api/v1`:

| Método e rota | Acesso e retorno |
|---|---|
| `GET /stores/{id}/hours` | Configuração pública, sem observações internas. |
| `GET /merchant/stores/{id}/hours` | Proprietário; configuração completa. |
| `POST /merchant/stores/{id}/hours/preview` | Proprietário; configuração normalizada, `revision`, `affected`. |
| `PUT /merchant/stores/{id}/hours` | Proprietário; configuração normalizada e `changeSummary`. |
| `GET /admin/stores/{id}/hours` | Administrador; configuração completa. |
| `POST /admin/stores/{id}/hours/preview` | Administrador; mesma proteção de impacto. |
| `PUT /admin/stores/{id}/hours` | Administrador; mesma proteção de confirmação. |

Corpo de configuração: `enabled`, `weeklyShifts` (dia → lista de `{opensAt, closesAt}`), `exceptions` (data, `closed`, `shifts`, `note`). Confirmação acrescenta `conflictAction` (`KEEP` ou `CANCEL`), `expectedAffectedIds` e `revision`. O retorno de gravação mantém os campos de configuração no nível principal e acrescenta `changeSummary: {cancelled, kept, notified}`.

`weekly` continua disponível com o primeiro turno de cada dia para compatibilidade. Consumidores atualizados devem usar `weeklyShifts`; ignorar a lista perde as pausas e os demais turnos.

### Persistência

O JSON semanal existente passa a gravar listas por dia; a leitura aceita tanto objetos legados quanto listas novas. As exceções preservam os campos iniciais de compatibilidade e acrescentam a lista completa. A migração V26 adiciona `operating_hours_override`, `operating_hours_notice`, `operating_hours_changed_at` e índice de busca em reservas. Não altera preço, duração ou instante de reservas existentes.

Faça backup antes de atualizar produção. Voltar apenas o executável para uma versão que não entende listas de turnos não é um rollback compatível dos dados.

### Frontend

`storeHours.js` monta o editor com elementos DOM, carrega/edita a configuração e controla a prévia. Desabilita ações enquanto há requisição, conserva o rascunho em falhas e trata Escape/fechamento como desistência. Usa a configuração normalizada da própria resposta de gravação, sem uma segunda leitura capaz de anunciar falha depois de um salvamento bem-sucedido. `storeTime.js` contém as regras de feedback local. A vitrine usa `renderPublicHours`; o editor administrativo reutiliza o mesmo componente com rotas protegidas próprias. Em celular, os campos dos turnos são empilhados para não cortar os dígitos do horário.

## 5. Verificação e limites

- Suíte Java: **97 testes aprovados**, sem falhas ou testes ignorados; inclui múltiplos turnos, sobreposição, 24 horas, madrugada, pausa curta, exceção de dia inteiro, decisões KEEP/CANCEL, pagamento, titularidade, administrador, prévia antiga e fuso.
- Frontend: 46 testes passaram e 18 módulos passaram na verificação sintática.
- `python3 scripts/test_store_hours.py`: aprovado em ambiente isolado, cobrindo permissões de vendedor/cliente/admin, proteção de fuso com compromissos ativos, pausa, serviço que ultrapassa fechamento, reservas futuras, privacidade, preservação, conflito de prévia, cancelamento, slot livre, aviso único, pagamento tardio e conclusão de estorno simulado.
- Revisão manual no navegador: adicionar/remover turno; desistir da prévia; manter e cancelar uma reserva fictícia; rejeitar agendamento na pausa; confirmar agendamento futuro com a loja fechada no dia atual; revisão em desktop e celular de 360 px; aviso do cliente redirecionando para a reserva correta; acesso e salvamento do expediente pelo administrador.
- Evidências em `Evidencias_horarios_2026-11-05/`. Capturas de diálogos com desfoque falharam no navegador desta sessão; seus textos e ações foram verificados pela árvore acessível. A limitação de captura não foi tratada como aprovação visual do diálogo.

Ainda não há escala semanal individual de profissional, capacidade maior que um por recurso ou grade que liste todos os slots livres. São recursos distintos do expediente da loja. Os testes locais H2 e pagamentos simulados não comprovam concorrência sob carga em PostgreSQL nem estornos financeiros reais. Essas verificações continuam necessárias antes de produção.
