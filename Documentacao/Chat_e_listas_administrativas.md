# Chat e listas administrativas

> [NÃO VERIFICADO] Escopo da revisão de 25/11: instruções e resultados históricos sem evidência individual nesta página aguardam confirmação. Os testes repetidos têm comando e resultado na [matriz de aceite](Aceite_2026-11-25.md).

[NÃO VERIFICADO] Relato histórico de 13/11/2026. Esta entrega não provisiona banco de produção nem homologa fornecedores externos.

## Conversas

`Frontend/src/chatSession.js` concentra o ciclo de vida de uma abertura. Cada abertura possui um objeto de sessão próprio e um `AbortController`. Fechar, navegar ou trocar de sessão invalida esse objeto. Mesmo uma resposta que ignore o cancelamento só pode atualizar a interface se ainda pertencer à abertura atual. Isso também funciona quando o usuário abre A, depois B e novamente A.

- Carregamento, atualização periódica e envio verificam a abertura atual após a resposta.
- O temporizador de atualização é criado apenas após terminar a consulta anterior; consultas lentas não acumulam novos GETs.
- A aba invisível não inicia consulta periódica. A próxima consulta acontece após o intervalo quando ela voltar a ficar visível.
- O envio impede submissões simultâneas na mesma abertura. Uma consulta iniciada antes do envio não pode substituir sua resposta por um histórico anterior.
- Respostas antigas não apagam o rascunho de outra abertura nem exibem erros de uma conversa já fechada.
- É possível fechar a conversa enquanto ela ainda está carregando.

Cancelar a requisição no navegador não desfaz uma mensagem já recebida pelo servidor. Em resultado incerto, a interface orienta consultar a conversa antes de reenviar. Esta alteração não implementa idempotência de mensagens nem entrega por WebSocket. O intervalo permanece em cinco segundos; recuo progressivo após falhas continua sendo uma evolução possível.

## Listas administrativas

Contas, operadores, lojas e chamados passam a usar páginas de 25 registros na interface, com Anterior/Próxima, total e número da página. Os componentes quebram linha em telas estreitas.

### Contrato HTTP

Todos exigem administrador:

- `GET /api/v1/admin/accounts/page?page=0&size=25`
- `GET /api/v1/admin/operators/page?page=0&size=25`
- `GET /api/v1/admin/stores/page?page=0&size=25&status=ALL`
- `GET /api/v1/admin/support-tickets/page?page=0&size=25`
- `GET /api/v1/admin/list-summary`

A resposta de página contém `content`, `number`, `size`, `totalElements` e `totalPages`. O servidor aceita tamanho de 1 a 50 e página de 0 a 100000. Valores inválidos são rejeitados. A ordenação é por nome e ID; chamados usam atualização decrescente e ID como desempate. Não há ordenação arbitrária fornecida pelo navegador.

Os filtros de lojas (`ALL`, `PENDING_REVIEW`, `CONFIRMED`, `STORE_SUSPENDED`) são aplicados no banco antes do limite da página. Profissionais são filtrados pelos papéis registrados no banco, incluindo papel secundário ou suspenso; não se carrega toda a tabela para filtrar em Java.

Os quatro caminhos antigos, sem `/page`, mantêm a resposta em lista, mas ficam limitados aos primeiros 50 registros. Clientes que precisem da lista completa devem percorrer os novos endpoints. O frontend foi atualizado, incluindo recargas após alterações. Não utilizar os caminhos antigos como exportação completa.

O resumo consulta contagens globais: seus indicadores não são o tamanho da página atual. Se a última página desaparecer após uma mudança, o carregador tenta a última página válida. A mudança de página/filtro participa da identificação da carga, impedindo que uma resposta antiga substitua a seleção mais recente.

## Evidências desta rodada

- Backend: 135 testes, zero falhas/erros/ignorados na execução Maven local; inclui cinco testes novos de paginação, filtros, contagens, limites, papéis secundários e bloqueio a cliente.
- Frontend: 88 testes aprovados; inclui sete cenários de concorrência de chat e quatro de paginação.
- Sintaxe: 29 módulos JavaScript válidos.
- Revisão manual local: login administrativo, totais globais, lista de lojas e seleção de Confirmadas. O filtro mostrou duas confirmadas entre oito lojas; o rodapé mostrou dois registros, página 1 de 1 e botões desabilitados corretamente. Visual conferido em viewport estreito, sem sobreposição dos controles. Console sem erros nessa navegação.
- Navegação entre páginas com mais de 25 registros foi validada automaticamente; não foi repetida manualmente nesta rodada.
- Não foram repetidas todas as jornadas de compra/entrega nem o ensaio PostgreSQL nesta entrega. Os testes novos de banco usam H2 compatível com PostgreSQL; a validação de concorrência PostgreSQL continua separada.

## Limites e próximos passos

Esta paginação cobre quatro listas administrativas. Solicitações, reembolsos, histórico de mensagens e listas de cliente/vendedor ainda precisam de avaliação específica de volume. Busca textual, índices adicionais e custo das consultas devem ser definidos com medições. Paginação por deslocamento não é uma fotografia imutável: alterações simultâneas podem mudar a posição dos registros entre consultas.

Permanecem os bloqueadores de lançamento descritos no [plano de conclusão](Plano_de_conclusao.md): persistência, fornecedores reais de teste, recuperação completa, monitoramento e aceite de acessibilidade. A revisão local não comprova que o Render já executa esta versão.
