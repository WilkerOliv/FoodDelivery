# Validação prática, visual e de engenharia

Data: 04/11/2026. Ambiente: homologação isolada local, API 8181, frontend 5180, H2 com migrações, Mailpit local. Contas e endereços fictícios. Nenhuma cobrança ou mensagem externa real foi realizada.

Resumo verificável: [resultados.json](Evidencias_2026-11-04/resultados.json). As referências dos 106 arquivos Java e 14 módulos JavaScript foram conferidas por SHA-256 contra a fonte final, sem divergências.

## Resultado e interpretação

- Backend: **68 testes, zero falhas, zero erros, zero ignorados**, incluindo migrações H2, recuperação, segurança de sessão, domínio, CPF administrativo, propriedade de catálogo, revisão administrativa e ciclo de entrega.
- Frontend: **32 testes aprovados**, além de verificação de sintaxe nos 14 módulos JavaScript.
- Supervisor local: **6 testes aprovados** com `python3 -m unittest scripts.test_dev`.
- `scripts/test_jornadas.py`: aprovado no servidor atualizado; CRUD, propriedade, suporte, administração e entrega completa.
- `scripts/test_homologacao.py`: aprovado; aprovação/recusa/repetição financeira fictícia, mensagens, imagem e cancelamento/estorno local.
- Manual no navegador: acessos dos quatro perfis, cenários e capturas descritos abaixo. Não confundir esse conjunto com teste manual de todas as combinações de cada botão.

A suíte de esquema define chave JWT exclusiva em configuração de teste; o comando Maven não depende de uma chave real em `.env`. A execução final passou integralmente.

O limite de login continuou ativo. Uma tentativa de rodar os roteiros junto dos logins manuais excedeu 10 tentativas/15 minutos e recebeu 429 corretamente. O roteiro de simulação foi repetido após reinício isolado e passou. Não houve afrouxamento da proteção para fazer testes passarem.

## Reprodução

```sh
# Terminal separado; aguarde a confirmação de frontend/API disponíveis.
npm run start:demo:mailpit

# Verificações independentes do login HTTP:
npm --prefix Frontend run check
npm --prefix Frontend test
python3 -m unittest scripts.test_dev
cd Backend
mvn test
```

Na raiz, `python3 scripts/test_jornadas.py` e `npm run test:demo` usam cinco logins cada. Rode-os em sessões separadas/reinicie o ambiente entre baterias e não misture com novos logins que ultrapassem o limite. O intervalo de recuperação de 60 segundos permanece no banco após reiniciar. Não use os roteiros contra produção.

## Matriz de jornadas HTTP

| Cenário | Resultado comprovado |
|---|---|
| Login ADMIN/cliente/vendedor/entregador | Cookie isolado; acesso ao domínio correspondente. |
| Cliente, vendedor e entregador tentam listar contas ADMIN | 403. |
| Cliente diferente tenta operar recurso alheio | Pedido/pagamento/chamado/chat/endereço recusados conforme contrato 403/404. |
| Cadastro e edição de item | Persistência de nome, preço, estoque e descrição. |
| Conversão produto → serviço → produto | Tipo, duração e cobrança aceitos conforme regra. |
| Remoção e reativação de catálogo | Exclusão lógica e retorno à publicação, preservando histórico. |
| Endereço privado | Criar/excluir pelo titular; outra conta não exclui. |
| Suporte | Criar, responder como ADMIN, fechar e reabrir mediante nova mensagem do titular. |
| Texto com HTML em mensagem | Armazenado como dado; não há execução de HTML no teste HTTP. A renderização escapada foi revisada no código; isso não é um teste universal de XSS. |
| Categoria adicional | Criar e excluir a categoria fictícia sem afetar categorias nativas. |
| Cidade | Criar e suspender cidade fictícia. |
| Aprovação administrativa | Aprovar solicitação de cidade e recusar segunda decisão sobre a mesma solicitação. |
| Editor administrativo da loja | PUT do perfil preserva os dados válidos e responde com visualização administrativa. |
| Oferta de entrega repetida | Retorna a mesma tarefa, sem criar duplicata. |
| Aceite e sequência de entrega | Atribuída → coletada → em trânsito → entregue. |
| Cliente cancela depois da coleta | 409; tarefa e pedido não são encerrados indevidamente. |
| Conclusão de entrega | Pedido COMPLETED e tarefa DELIVERED. |
| Endereço/observação no histórico do entregador | Campos nulos após conclusão. |
| Chat e GPS fictício | Somente participantes; atualização de ponto durante trajeto. |
| UUID malformado | 400, sem erro interno genérico. |
| Notificação | Leitura registrada para conta destinatária. |
| Pagamento fictício | Aprovar, recusar e repetir confirmação sem cobrança real. |
| Cancelamento e estorno fictício | Estado de estorno local concluído sem movimentar dinheiro. |
| Reserva paga fictícia | Reserva confirmada após simulação aprovada. |
| E-mail/WhatsApp fictícios | Códigos aparecem na caixa privada de teste; SMTP vai ao Mailpit. |
| Capa com imagem fictícia | Upload local, prévia privada e aprovação ADMIN, sem IA. |
| Checkout financeiro externo no modo demo | Bloqueado. |

## Testes de regra e regressão

Os testes de frontend cobrem máscaras de documentos/moeda, normalização de categoria, separação de seções administrativas, endpoints de atendimento, navegação móvel, repetição de clique, rotulagem de aprovação, conversão de fuso, horário mínimo, exceções e meia-noite, política de cobrança fora da cidade, coordenadas, remoção de CPF ADMIN e sessão sem JWT no storage.

Os novos testes de domínio verificam que concluir entrega conclui pedido e oculta endereço; cancelamento permitido encerra a tarefa; tarefa coletada recusa cancelamento; vendedor não conclui delivery antes do entregador. Alterar telefone/e-mail invalida códigos pendentes. Outro vendedor autenticado não edita/remove item alheio. A revisão administrativa bloqueia a conta antes da solicitação e recusa decisão repetida.

Esses testes não cobrem corrida real de múltiplas JVMs contra PostgreSQL. Um mock de repositório prova a chamada esperada, não o comportamento físico de lock no banco real.

## Revisão manual por perfil

### Administrador

1. Entrar, abrir “Lojas”, conferir loja aprovada e abrir “Editar dados da loja”.
2. Conferir nome, categoria, endereço, cidade, CNPJ opcional, taxa, fuso e coordenadas.
3. Em 360 × 800, conferir modal alcançável com rolagem vertical e cabeçalho legível abaixo do aviso de homologação.
4. Pressionar Escape: modal fecha e foco retorna a “Editar dados da loja”.

A persistência do editor foi exercitada por HTTP; a remoção de CPF administrativo possui testes específicos. Não foi alterado CPF de pessoa real.

### Cliente

1. Entrar, escolher Barbearias e confirmar somente o estabelecimento dessa categoria.
2. Abrir loja, reservar e verificar “Primeiro disponível”, “Profissional A” e “Profissional B”; selecionar A.
3. Abrir Mercado, adicionar produto e conferir sacola com R$ 15,00, retirada e total.
4. Continuar ao checkout fictício, simular aprovação, confirmar novo pedido em “Meus pedidos”.
5. Solicitar cancelamento e confirmar mensagem de espera pela decisão da loja.
6. Buscar Iepê por nome, selecionar resultado com CEP 19640-000 e aplicar região. A busca usa fixture, não valida geocodificação real.
7. Abrir notificação de pagamento e confirmar retorno ao pedido relacionado; conferir que o menu móvel alterna entre aberto e fechado.

### Vendedor

1. Entrar e conferir seletor de loja, publicação e abas sem faixa horizontal obrigatória.
2. Abrir edição de produto, mudar tipo visual para serviço e conferir duração/cobrança; cancelar sem modificar o produto base.
3. Cadastrar “Produto revisão visual” por R$ 12,50; confirmar item no catálogo.
4. Remover o item recém-criado; conferir “Pausado”, “Reativar” e aviso de preservação do histórico.
5. Abrir “Dados da loja”; conferir formulário da loja selecionada, dias da semana, exceção por data e fechamento de hoje.
6. Medir largura do documento nas vistas 360, 768 e 1440: não ultrapassou a largura disponível na página inspecionada.

### Entregador

1. Entrar e conferir ausência de ofertas pendentes e entregas concluídas criadas pelo roteiro HTTP.
2. Conferir texto que distingue valor do pedido de remuneração e informa ocultação de endereço.
3. Abrir “Financeiro”: a tela declara ausência de remuneração/repasse configurados, sem saldo ou saque fictício.
4. Conferir navegação móvel em 320 × 800; documento não ultrapassou a largura da viewport.

A sequência completa de entrega foi exercitada por HTTP, não por todos os cliques de campo em aparelho físico.

## Evidências visuais

As capturas apresentam estado fictício. Captura de uma tela não comprova todas as ações daquela seção.

| Arquivo | Evidência |
|---|---|
| [01-admin-loja-360.png](Evidencias_2026-11-04/01-admin-loja-360.png) | Editor administrativo móvel, sem corte superior pelo aviso de demonstração. |
| [02-cliente-reserva-360.png](Evidencias_2026-11-04/02-cliente-reserva-360.png) | Profissional selecionado e campos de reserva. |
| [03-vendedor-edicao-360.png](Evidencias_2026-11-04/03-vendedor-edicao-360.png) | Tipo serviço, duração e cobrança no editor. |
| [04-vendedor-dados-1440.png](Evidencias_2026-11-04/04-vendedor-dados-1440.png) | Dados do estabelecimento em tela ampla. |
| [05-vendedor-remocao-1440.png](Evidencias_2026-11-04/05-vendedor-remocao-1440.png) | Item pausado e ação de reativar. |
| [06-entregador-320.png](Evidencias_2026-11-04/06-entregador-320.png) | Organização móvel da área de entregas. |
| [07-cliente-compra-360.png](Evidencias_2026-11-04/07-cliente-compra-360.png) | Pedido criado e pago ficticiamente. |

A navegação entre páginas/seções redefine a rolagem para o início. Na verificação final de pedido em 360 pixels: `scrollY=0`, título abaixo do cabeçalho e largura do documento menor que a viewport.

## Avaliação visual e acessível

- Botões primários preenchidos, secundários com contorno e ações de remoção com texto/cor próprios.
- Campos de formulário e labels identificáveis; ícone de busca tem nome acessível textual.
- Modal longo usa rolagem vertical; o conteúdo não precisa escapar lateralmente para ficar alcançável.
- Foco visível, rótulos dinâmicos e Escape/Tab tratados por módulo comum.
- Fonte de campo legível e limite de movimento respeitando preferência do sistema.
- Menu móvel abre e fecha; histórico de entrega não é rotulado apenas como operação em andamento.

Não foi medida automaticamente a razão de contraste de todas as cores computadas; não declarar conformidade WCAG completa. Não houve testes com idosos/baixa leitura, leitor de tela real, zoom de sistema ou todos os aparelhos. Isso permanece na validação pré-lançamento.

## Sem comprovação nesta sessão

Pagamentos e saques reais, WhatsApp/SMTP externos, geolocalização física/GPS de pessoas, publicação de empresa no Google, stress de concorrência PostgreSQL, carga/escala, falha de disco, restauração de backup, pentest independente, execução dos workflows GitHub e auditoria legal de retenção.

O projeto não está certificado como pronto para operação financeira pública só porque testes locais passaram. A análise de engenharia descreve as pendências e a ordem recomendada.

## Upload e moderação de capas

Implementação substitui geração de imagens por envio de foto/logo pelo vendedor, com aprovação ADMIN. Suíte atual: 77 testes Java, 37 JavaScript e sintaxe válida em 15 módulos. O roteiro `test_store_covers.py` aprovou permissões, prévia privada, substituição, motivo de recusa, aprovação única e preservação da capa anterior. Teste manual com vendedor, administrador e cliente confirmou a publicação e a proporção 2:1, em 360 e 1440 pixels de largura. Procedimento, contratos, evidências e limites estão em [Capas das lojas](Capas_das_lojas.md).
