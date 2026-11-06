# Validação manual complementar — 06/11/2026

## Escopo e método

Interações realizadas no navegador integrado em `https://fooddelivery-homologacao.onrender.com/`, com contas `@example.test` e sem cartão, dinheiro ou destinatários reais. Nenhum estado foi alterado diretamente no banco para obter resultado visual. A primeira parte registra a publicação anterior à correção; a seção de repetição após deploy identifica os resultados da versão corrigida.

Esta é uma amostra detalhada das jornadas indicadas; não é uma certificação de cada função, aparelho ou condição de falha. Testes automatizados e manuais são evidências distintas. Para comparação com produção, consulte [Fidelidade da homologação](Fidelidade_da_homologacao.md).

## Catálogo, estoque, cancelamento e estorno

Na etapa anterior desta mesma revisão, o vendedor criou `Produto QA manual 06-11`. Preço zero foi recusado com instrução de corrigir o valor; preço R$ 16,90 e estoque 3 foram aceitos. O editor salvou R$ 18,90; remoção recuperável exibiu Pausado e Reativar, e a reativação funcionou.

O cliente adicionou o produto e tentou superar três unidades: o carrinho permaneceu limitado a três e informou falta de unidades adicionais. O pedido de retirada `EA571BFE`, total fictício R$ 56,70, foi confirmado no simulador. O cliente solicitou cancelamento; o vendedor aceitou e estornou. O catálogo passou de estoque zero para três, inclusive no editor. A captura [estoque reposto](Evidencias_homologacao_publicada_2026-11-05/estoque-reposto-manual-publicado.jpg) registra esse resultado. O cliente viu Cancelado e “Estorno simulado confirmado. Nenhum dinheiro foi movimentado.” A notificação Cancelamento aceito abriu seus pedidos.

A recusa de pagamento foi acionada e permitiu nova tentativa; não foi registrada uma observação suficientemente completa do aviso da recusa para afirmar homologação de todo esse ramo. Não houve contato com Mercado Pago.

## Reinício da demonstração

Na retomada, o Render exibiu sua página de inicialização antes de voltar à entrada de avaliador e ao login. O painel voltou às duas lojas iniciais e zero chamados: os registros anteriores não foram preservados. Isso confirma a natureza descartável desta instância; não é comportamento aceitável para armazenamento comercial permanente.

## Novo pedido com entrega

O cliente comprou uma unidade de `Produto de teste`, R$ 15,00, em Mercado Demonstração, escolheu Receber em casa e utilizou o endereço fictício já cadastrado. A observação identifica o teste. A aprovação no simulador criou o pedido `AD7B13E1` como Aguardando a loja. O vendedor visualizou o pedido, a modalidade e a observação e avançou por Aceitar, Começar preparo e Marcar como pronto.

A tentativa inicial de ofertar a entrega foi bloqueada com orientação de configurar a remuneração. O administrador abriu Financeiro, definiu R$ 5,00 por entrega com confirmação de senha e viu a política salva. Trata-se de política fictícia de teste, sem transferência.

## Suporte

O cliente criou `QA manual entrega 06-11`, categoria Pedido, contendo `<b>literal</b>`. A conversa exibiu esse trecho como texto, sem interpretá-lo como marcação. O administrador abriu o chamado, enviou resposta, conferiu a mensagem persistida e retornou por “← Chamados” à fila administrativa correta. O chamado foi encerrado e a lista exibiu Encerrado e Reabrir, preservando a resposta. A captura `chamado-encerrado-manual-publicado.jpg` registra a fila final. O campo de resposta publicado não tem nome acessível explícito na árvore de acessibilidade. A correção desta revisão introduz rótulo visível, descrição do limite de 2.000 caracteres e orientação para não enviar senhas/códigos, compartilhados entre cliente e administrador.

## Testes de código repetidos nesta retomada

- Frontend: 57 testes aprovados; 25 módulos com sintaxe válida.
- Backend: 130 testes regulares, sem erros ou falhas.
- PostgreSQL 17 descartável: três testes aprovados, incluindo disputa de estoque, conflito de agenda e ausência dos controladores simuladores no perfil PostgreSQL normal.
- Restauração PostgreSQL: dump para outro banco descartável e comparação de contagens aprovados. Não inclui arquivos, chaves nem desastre do provedor.
- Gateway: 21 testes aprovados.
- Detecção de capacidade de segurança: três testes aprovados.

O cliente voltou ao atendimento, viu o chamado Encerrado, abriu a conversa, leu a resposta administrativa e encontrou o aviso de encerramento sem formulário de nova resposta. Evidência: [resposta recebida pelo cliente](Evidencias_homologacao_publicada_2026-11-05/resposta-suporte-cliente-publicado.jpg).

## Correções documentadas

O formulário de resposta de suporte ganhou controles com rótulos explícitos; a identificação “Homologação local” do checkout foi substituída por “Ambiente de homologação”, aplicável também ao Render. O teste PostgreSQL de isolamento dos simuladores foi acrescentado. A análise de engenharia foi atualizada para refletir os recursos já implementados de MFA, agenda profissional, integrações protegidas, conciliação e remuneração de entregadores, preservando seus limites operacionais. As referências de funções foram regeneradas a partir do código final.

## Defeito confirmado no aceite da entrega

Após a política de remuneração, o vendedor ofertou a entrega e o entregador viu o pedido AD7B13E1, remuneração R$ 5,00 e endereço oculto. O clique em Aceitar entrega não concluiu a atribuição. O código renderiza `data-accept-delivery` com o identificador, mas o manipulador lia `dataset.id`, inexistente no botão. Isso explica por que uma jornada HTTP pode passar e a interação visual falhar.

A correção usa `deliveryBindings.js` para ler `dataset.acceptDelivery`. O teste de regressão dispara cliques de duas ofertas sem `data-id` e confere seus identificadores distintos. Não foi contornado o botão com chamada direta à API para declarar sucesso manual. A captura `oferta-aceite-bloqueado-publicado.jpg` registra a etapa bloqueada.

**Pendência desta etapa encerrada após o deploy de 4c55b8a:** aceite, coleta, trajeto, conclusão, crédito e ocultação final foram repetidos pela interface, conforme a seção abaixo. Os rótulos do suporte e a mensagem de recusa simulada também foram conferidos. As novas mensagens genéricas de falha de conexão/5xx continuam cobertas pelos testes automatizados; não se provocou uma interrupção de rede ou erro 5xx real nesta rodada. Os testes HTTP anteriores permanecem evidência separada. O Render exige publicação manual e reinicia os dados fictícios.

## Comunicação de falhas

A mensagem genérica de erro 5xx afirmava que os dados estavam preservados, sem a resposta poder comprovar isso. Ela agora orienta consultar o andamento antes de repetir uma operação. A falha de conexão usa orientação compreensível ao usuário e também não presume se a gravação aconteceu. Dois testes cobrem essas mensagens. Isso reduz uma instrução enganosa, mas não implementa idempotência universal nem reconciliação automática de toda ação do site.

## Repetição após publicar 4c55b8a

O usuário confirmou o deploy e a página foi recarregada no navegador. Os recursos `main.js` e `theme.css` exibiram a versão `20261106-delivery-qa-1`. A sessão externa foi autenticada novamente pelo formulário, sem caixa Basic nativa. Os dados voltaram ao conjunto inicial, por se tratar de instância descartável.

- Administração: remuneração fictícia de R$ 5,00 por entrega configurada pela interface com confirmação da senha; o valor salvo foi exibido.
- Cliente: compra de uma unidade de Produto de teste, R$ 15,00, com Receber em casa e endereço fictício. A recusa simulada exibiu “Pagamento simulado recusado — Você pode tentar novamente ou cancelar a operação.” A aprovação posterior do mesmo checkout criou o pedido `29D34FF4`, Aguardando a loja. O título agora é Ambiente de homologação. Evidência: `checkout-recusa-repeticao-publicado.jpg`.
- Suporte do cliente: criado `QA 4c55b8a: rótulos do atendimento`; o campo Mensagem para o atendimento foi encontrado pelo seu rótulo, com descrição do limite e aviso para não enviar senhas/códigos. Um complemento foi enviado por esse campo e apareceu na conversa. Evidência: `suporte-rotulo-cliente-corrigido-publicado.jpg`.

A largura efetiva da janela nesta sessão foi 594 px, com 578 px de documento; não se apresenta essa observação como prova de todas as larguras. Mensagens transitórias de lista vazia durante carregamento ainda foram observadas antes dos pedidos aparecerem; não houve perda do pedido nessa observação, mas esse feedback transitório merece melhoria de UX.

### Entrega concluída pelo fluxo corrigido

Para o pedido `29D34FF4`, o vendedor aceitou, iniciou preparo, marcou como pronto e enviou a oferta. O aviso Entrega oferecida confirmou o envio. O entregador visualizou uma oferta com remuneração R$ 5,00 sem endereço do cliente. O clique Aceitar entrega funcionou e exibiu o endereço fictício e a observação, com o botão Marcar como coletado. Evidência: `entrega-aceita-corrigida-publicado.jpg`.

Em seguida, o entregador marcou como coletado, iniciou trajeto e marcou como entregue. A tela passou a Entregue e mostrou “Operação encerrada. Endereço e observação do cliente ficam ocultos.” Uma leitura do DOM visível confirmou ausência do endereço e da observação utilizados no teste. Após recarregar, foi necessário reabrir Entregas porque a URL anterior estava na raiz; a entrega continuou Entregue e os dados continuaram ocultos. Evidências: `entrega-concluida-privacidade-publicado.jpg` e `entrega-concluida-privacidade-publicado.txt`. O cartão foi enquadrado para tornar visíveis o estado e a orientação de privacidade. Não foi compartilhada a localização real do equipamento.

O Financeiro do entregador mostrou Acumulado R$ 5,00, Disponível para solicitar R$ 5,00, Em análise R$ 0,00 e Pagamento registrado R$ 0,00; Ganhos por entrega exibiu uma entrega `24298932`, R$ 5,00. Isso comprova o registro interno exercitado; não comprova transferência bancária nem liquidação externa.

O financeiro foi recarregado e manteve Acumulado/Disponível de R$ 5,00 e apenas uma linha de ganho para a entrega 24298932. Isso não é um teste de duplicação concorrente do evento de conclusão; confirma que a consulta/reabertura não duplicou o crédito observado. O registro da árvore de acessibilidade está em `financeiro-credito-entrega-publicado.txt`. Algumas tentativas de captura de imagem falharam na ferramenta do navegador; nesses casos, não foi criada uma evidência de imagem fictícia nem se confundiu falha de captura com falha da operação.

### Suporte administrativo e confirmação pelo comprador

O administrador abriu o chamado dessa rodada, encontrou o campo pelo rótulo Resposta ao cliente e enviou a resposta. Após salvar, o rótulo continuou presente e a mensagem apareceu na conversa. O botão ← Chamados retornou à fila; Encerrar exibiu Encerrado e Reabrir. Evidência textual: `suporte-rotulo-admin-corrigido-publicado.txt`.

O cliente abriu Notificações e clicou Resposta do atendimento: a conversa correta abriu como Encerrado, com as três mensagens (abertura, complemento e resposta administrativa), sem formulário de nova resposta. Depois, a notificação de pedido pronto abriu Meus pedidos e o pedido 29D34FF4 já estava Entregue, com Pedido entregue, histórico do chat de entrega e sem Solicitar cancelamento. A notificação antiga é um atalho para a situação atual; não altera o pedido para um estado anterior. Evidências: `pedido-entregue-comprador-publicado.jpg` e `pedido-entregue-comprador-publicado.txt`.

### Resultado e limites desta repetição

A correção do identificador do aceite foi validada no navegador publicado, usando os quatro perfis nos passos descritos. Nenhuma chamada direta à API, console de aplicação ou alteração no banco substituiu um botão para forçar o resultado. A remuneração e os estados foram derivados das ações normais da interface. A revisão visual das capturas de suporte e pedido confirmou texto legível, rótulo exposto, botões visíveis e ausência de transbordamento horizontal na largura efetivamente observada; não cobre todos os aparelhos nem uso por leitor de tela.

Esta rodada encerra a pendência específica de entrega completa e dos rótulos após o deploy. Não ativa nem homologa Mercado Pago real, entrega de e-mail/WhatsApp, Pix, GPS real, persistência comercial ou recuperação de desastre. Não foi solicitado nem registrado um repasse bancário. Os limites gerais de [Fidelidade da homologação](Fidelidade_da_homologacao.md) permanecem válidos. O feedback transitório de lista vazia durante carregamento fica registrado como melhoria de UX, sem atribuir a ele perda de dados que não ocorreu no teste.

Ao terminar, a conta do aplicativo foi desconectada e a aba ficou no login. Os registros fictícios foram preservados para inspeção até o próximo reinício da instância. As alterações finais desta rodada são documentação e evidências, sem mudança de código de execução nem necessidade de novo deploy para esta validação.
