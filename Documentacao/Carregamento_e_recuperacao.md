# Carregamento, sessão e recuperação de consultas

> [NÃO VERIFICADO] Escopo da revisão de 25/11: instruções e resultados históricos sem evidência individual nesta página aguardam confirmação. Os testes repetidos têm comando e resultado na [matriz de aceite](Aceite_2026-11-25.md).

Implementação revisada entre 06 e 07/11/2026; verificação manual local em 07/11. Ambiente: `http://127.0.0.1:5180`, perfil isolado `homologacao`, contas e registros fictícios.

## Responsabilidades e motivos

### `pageData.js`

`fetchPageData` recebe um retrato do contexto e retorna `{data, warning}` sem escrever no estado global. As consultas independentes usam `Promise.all` ou `allSettled`, conforme seja seguro apresentar resultado parcial. Isso evita que uma resposta de loja anterior altere metade do painel atual.

- Loja selecionada é resolvida pela lista autorizada do vendedor; ausência da lista por falha não vira formulário de cadastro.
- Falha no catálogo é erro recuperável, nunca catálogo vazio apresentado como sucesso.
- Agenda acessada diretamente carrega a lista de lojas antes de consultar profissionais.
- Ofertas e entregas chegam como um único resultado; falha impede misturar conjuntos de instantes diferentes no carregamento central.
- Administração admite resultado parcial com aviso; campos sem resposta são normalizados, sem reapresentar registros antigos como atualizados. Falha total oferece repetição.
- Cidade e UF são codificadas na consulta; o servidor aplica sua própria filtragem/autorização.

### `pageLoading.js`

`createPageLoader` mantém uma revisão monotônica e um `AbortController`. Cada navegação cancela a anterior. Antes de aplicar o resultado, confere revisão, sinal e chave de contexto. A chave inclui sessão, conta, papel, página, loja e região. Mesmo um fornecedor que ignore o cancelamento não pode publicar resultado obsoleto.

`invalidate` descarta consultas pendentes ao sair ou mudar de sessão/papel. `pageLoadFeedback` distingue carregando, falha e resultado pronto; escapa rótulos/avisos, não inclui erro bruto do servidor e oferece botão acessível de repetição.

Cancelar uma consulta no navegador não desfaz uma operação recebida pelo servidor. O botão **Tentar novamente** refaz apenas a leitura da página; não repete pagamento, cadastro ou cancelamento. Após falha numa mutação, o usuário ainda deve conferir o andamento antes de reenviar.

### `api.js` e integração em `main.js`

Cada requisição captura a geração da sessão. Confere sinal e geração antes/depois do transporte e da leitura JSON. Um 401 atrasado de conta anterior não encerra a conta atual. Cookie HttpOnly continua sendo o mecanismo de sessão; esse controle visual não substitui autorização na API.

A inicialização apresenta **Preparando seu acesso…** até concluir a consulta inicial de sessão, evitando redesenhar o formulário durante a digitação. Navegação e atualização explícita usam o carregador central. Polling de pedidos confere contexto e revisão antes de publicar dados e evita redesenhar enquanto o usuário digita. Outros diálogos e mutações continuam com seus controles próprios; esta etapa não afirma que todos os caminhos assíncronos foram centralizados.

## Testes automatizados

`npm test --prefix Frontend`: 77 testes aprovados nesta revisão (20 adicionais: 17 de carregamento/dados e 3 de sessão). `npm run check --prefix Frontend`: sintaxe válida em 27 módulos.

Casos novos: carregamento antes da resposta; cancelamento; resposta antiga que ignora abort; troca de contexto/conta; falha seguida de recuperação; falha obsoleta; normalização da loja; escape; sinal encaminhado; atualização conjunta do entregador; falha da lista de lojas/catálogo; loja selecionada; agenda direta com profissionais; administração parcial/total; cidade codificada; 401 antigo e leitura JSON após mudança de sessão.

O job `PostgreSQL concurrency and restore` acrescenta `scripts/test_postgres.py` ao workflow de qualidade. A execução local anterior passou com PostgreSQL 17 descartável, concorrência, bloqueio do simulador e restauração com contagens conferidas. Isso não comprova recuperação completa de produção nem chamadas reais a provedores. O resultado remoto do novo job deve ser acompanhado após o push.

## Verificação manual desta etapa

- Login local do cliente concluiu sem perda dos valores digitados.
- Meus pedidos e Minhas reservas exibiram os registros fictícios persistidos.
- A conexão da aba foi temporariamente colocada offline; ao abrir Meus pedidos, apareceu **Não foi possível carregar esta área**, sem apresentar lista vazia.
- A conexão foi restabelecida; **Tentar novamente** mostrou carregamento e recuperou os pedidos existentes. A alteração temporária da rede foi removida.
- Logout e login como vendedor concluíram; selecionar Barbearia Demonstração exibiu seu catálogo, sem itens do mercado. Agenda mostrou Profissional A e Profissional B.

- Entregador: histórico mostrou endereço/observação ocultos nas entregas encerradas; Financeiro mostrou saldo e formulário verificado, sem solicitar transferência.
- Administrador: painel e Definições carregaram; Perfil do administrador mostrou CPF opcional.
- Inspeção de Definições em viewport 390 × 844 e 1440 × 900: largura de conteúdo igual à área útil (375 e 1425 px, descontada a barra vertical), sem transbordamento horizontal. Capturas [celular](Evidencias_carregamento_2026-11-07/admin-mobile.png) e [desktop](Evidencias_carregamento_2026-11-07/admin-desktop.png). O tamanho original foi restaurado.
- Console da aba retornou zero erros capturados ao final. As quatro contas foram encerradas pelo fluxo de logout; a aba terminou no login.

Evidência da falha recuperável: [captura](Evidencias_carregamento_2026-11-07/falha-consulta.png).

Não foram realizados pagamentos reais, envio externo de mensagens ou alteração de credenciais. Não se trata de teste de carga ou de todos os aparelhos/navegadores. A versão local requer publicação para que essas alterações estejam no Render.
