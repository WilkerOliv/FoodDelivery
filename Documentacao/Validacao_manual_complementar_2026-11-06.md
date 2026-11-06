# Validação manual complementar — 06/11/2026

## Escopo e método

Interações realizadas no navegador integrado em `https://fooddelivery-homologacao.onrender.com/`, com contas `@example.test` e sem cartão, dinheiro ou destinatários reais. Nenhum estado foi alterado diretamente no banco para obter resultado visual. O código em execução nesta publicação antecede a correção de rótulo de suporte desta revisão.

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

**Pendente após publicação da correção:** repetir aceite, coleta, trajeto, conclusão, crédito e ocultação final do endereço por interação manual; conferir os novos rótulos e mensagens na interface. Os testes HTTP anteriores de entrega completa estão documentados separadamente e não substituem essa repetição. O Render exige publicação manual, com apagamento dos dados fictícios; será necessário recriar o pedido nessa versão.

## Comunicação de falhas

A mensagem genérica de erro 5xx afirmava que os dados estavam preservados, sem a resposta poder comprovar isso. Ela agora orienta consultar o andamento antes de repetir uma operação. A falha de conexão usa orientação compreensível ao usuário e também não presume se a gravação aconteceu. Dois testes cobrem essas mensagens. Isso reduz uma instrução enganosa, mas não implementa idempotência universal nem reconciliação automática de toda ação do site.
