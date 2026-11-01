# Auditoria manual do FoodDelivery — 30/10/2026

## Escopo e ambiente

Revisão feita manualmente no navegador local, com as contas de administrador, cliente e vendedor de teste que já estavam cadastradas. Foram conferidos navegação, telas vazias, formulários, máscaras, mensagens de sucesso/erro, adaptação móvel e integrações chamadas pelo próprio site. A API respondeu `online` em `8080`, a interface respondeu em `5173` e o console do navegador não registrou erros ou avisos durante a sessão.

Nenhuma loja, conta ou configuração preexistente foi aprovada, suspensa, editada ou excluída. A cidade do cabeçalho foi restaurada para Nantes após a consulta de CEP. A categoria, o produto e o chamado usados nos ensaios foram temporários: a categoria foi removida pela tela administrativa; produto e chamado foram removidos do banco somente após confirmar que eram registros criados nesta auditoria e sem pedido/reserva vinculados. O carrinho voltou a ficar vazio.

## Fluxos percorridos

| Fluxo | Resultado | Observação |
|---|---|---|
| Entrada de administrador, cliente e vendedor | Passou | As três contas de teste abriram os respectivos painéis. A sessão de administrador manteve a última área administrativa usada após sair e entrar novamente. |
| Descoberta e categorias | Passou | A página inicial exibiu quatro cartões em desktop e grade de duas colunas no celular, sem rolagem lateral nessa seção. “Todas as categorias”, classificação, abertura de categoria e retorno funcionaram. |
| Localização | Passou com ressalva | O seletor exibiu Iepê e Nantes, mapa incorporado e busca por CEP. A consulta de teste `01001-000` identificou São Paulo; a região foi restaurada para Nantes. O cartão de integração ainda classifica o mapa como não homologado, embora o mapa tenha carregado nesta sessão. |
| Categorias administrativas | Passou | Criei “Auditoria visual QA 3010”, confirmei o aviso de sucesso e removi a categoria pela própria tela. As categorias oficiais e a categoria personalizada preexistente foram preservadas. |
| Atualização do painel administrativo | Passou | “Atualizar dados” carregou novamente as informações e mostrou confirmação. |
| Gestão de lojas e filtros | Parcial | Os filtros Todas, Aguardando aprovação, Confirmadas e Suspensas responderam. Há 3 lojas confirmadas e nenhuma aguardando ou suspensa. A cópia deixa claro que a aprovação publica a loja sem uma etapa de pagamento. Como não havia loja pendente, não alterei uma loja existente para simular aprovação/reprovação. |
| Solicitações da plataforma | Parcial | A fila abriu e mostrou o estado vazio e o histórico. Não havia solicitação pendente para decidir. |
| Contas e segurança | Parcial | O atalho “Abrir contas e segurança”, abas, menu de três pontos e formulário de edição abriram. Não executei suspensão nem redefinição de senha porque alterariam contas preexistentes. |
| Atendimento | Passou, com defeito de navegação | O cliente abriu chamado; o administrador respondeu e encerrou. O registro temporário foi limpo. Ao usar “← Chamados” dentro do diálogo administrativo, abriu-se o formulário de atendimento do cliente, sem a lista do administrador. |
| Catálogo do vendedor | Parcial | O preço foi formatado como `R$ 1,23`; cadastrei um produto de teste e confirmei que apareceu no catálogo. A interface não apresentou ação de editar/remover o cartão do produto. O item temporário foi limpo diretamente do banco após confirmar que não tinha referências. |
| Dados da loja | Parcial | A aba intitulada “Dados da loja” mostra “Adicionar outra loja”; não oferece campos para editar a loja atual. Não enviei uma nova loja porque o fluxo exige CNPJ e criaria um cadastro operacional permanente. |
| Pedidos do cliente e do vendedor | Parcial | A sacola aceitou o produto e o removeu ao diminuir a quantidade a zero. O histórico do cliente e a fila do vendedor apresentaram estados vazios. Não foi possível criar pedido para verificar pagamento, entrega ou cancelamento. |
| Reservas e agenda | Parcial | A barbearia apareceu como categoria de serviço, com “Degradê”, preço, cobrança após atendimento, aviso de risco, data/hora e campo de comentário. A reserva foi bloqueada pela ausência de telefone. A tela não oferece seleção de profissional nem opção “primeiro disponível”. A agenda do vendedor abriu no estado vazio. |
| Endereços e segurança do cliente | Parcial | A aba Endereços e o formulário de novo endereço abriram; o formulário foi cancelado sem gravação. A aba Segurança mostrou troca de senha e recuperação por WhatsApp desabilitada sem telefone. Não troquei a senha da conta de teste. |
| Integrações administrativas | Passou na consulta de estado | O botão de atualização respondeu. SMTP, WhatsApp, geração de imagem e checkout/Mercado Pago aparecem incompletos; ViaCEP e mapa aparecem configurados sem chave, mas não homologados. O mapa e a consulta de CEP funcionaram no ensaio local. |
| Financeiro | Parcial | A tela de estornos abriu no estado vazio. Sem transações ou estornos pendentes não foi possível verificar um estorno real. |

## Problemas encontrados

### Prioridade alta

1. **Conta de cliente legada não consegue completar cadastro para comprar ou reservar.** O cadastro de teste não tem CPF nem telefone. A edição exige CPF e o site bloqueia salvar quando ele está ausente; o checkout e a reserva também exigem telefone. A reserva informa “Complete seu telefone antes de reservar”. Isso impede o fluxo principal para contas migradas/incompletas. É necessário um fluxo seguro de regularização de cadastro, sem aceitar CPF inválido ou permitir duplicidade.
2. **Não há remoção de produto no painel do vendedor.** Depois de criado, o item aparece como texto sem menu de ações. A API também expõe criação e atualização, mas não uma operação de exclusão/desativação acessível ao vendedor. O controle de estoque não substitui essa ação.
3. **Agendamento de barbearia não atende ao modelo aprovado anteriormente.** A reserva não apresenta profissional, disponibilidade individual, nem “primeiro disponível”; a agenda fica apenas vazia. O horário digitado não está associado visualmente a uma agenda por profissional.
4. **Não foi possível testar compra, cancelamento de pedido, aprovação de loja ou reprovação de perfil com mudança real.** O cliente não consegue passar do requisito de telefone/CPF e não há pedidos; a fila administrativa não tinha pendências. Esses fluxos ficam sem homologação manual nesta sessão.

### Prioridade média

5. **Retorno do chamado no painel administrativo está ligado ao atendimento do cliente.** Dentro do diálogo de chamado, “← Chamados” abre a janela de novo atendimento do cliente, enquanto a lista administrativa continua por trás.
6. **Aba “Dados da loja” não edita os dados da loja atual.** Ela apresenta somente o formulário para adicionar uma loja adicional, apesar do título e da descrição sugerirem edição.
7. **As abas do painel do vendedor ainda rolam horizontalmente em telas estreitas.** A tela de categorias foi corrigida e não rola, mas “Resumo, Catálogo, Dados da loja, Aparência e pagamentos” continuam com a última aba parcialmente fora da área visível.
8. **Campos de alguns formulários não expõem rótulos completos na árvore acessível.** No formulário de endereço e em campos de senha/preço, parte dos controles aparece sem nome acessível no snapshot do navegador. Uma revisão por teclado e leitor de tela ainda é necessária.
9. **Integrações reais continuam incompletas.** SMTP, WhatsApp, OpenAI e Mercado Pago não estão configurados; por isso não foi possível validar entrega de código, imagem por IA ou pagamento/webhook. O estado “configurado” de ViaCEP/mapa não prova disponibilidade contínua fora deste ensaio.

## Limites desta auditoria

Este documento registra o que foi observado na interface e nas respostas locais durante a sessão. Não comprova liquidação de pagamento, entrega de e-mail/WhatsApp, autenticação externa de Mercado Pago, webhook real, entrega física, permissões de localização do navegador ou conformidade completa de acessibilidade. Cancelamento de pedido e aprovação/reprovação de loja não foram marcados como aprovados: faltaram registros pendentes seguros para testar.

## Próximas correções recomendadas

1. Criar uma etapa de regularização de CPF/telefone para contas legadas e só liberar checkout/agendamento após confirmação válida.
2. Adicionar ações claras de editar, desativar e remover produto, com confirmação e proteção quando o produto já estiver em pedidos/reservas.
3. Implementar disponibilidade por profissional e seleção “escolher profissional”/“primeiro disponível”, usando o fuso horário configurado na loja.
4. Corrigir o retorno do diálogo administrativo de chamados e expor a edição da loja atual separada do cadastro de nova loja.
5. Adaptar as abas do vendedor a telas estreitas sem ocultar a última opção; associar rótulos programáticos aos campos.
6. Preparar dados de homologação isolados para pedido/cancelamento, aprovação/reprovação e estorno, sem alterar lojas ou contas reais de teste.
