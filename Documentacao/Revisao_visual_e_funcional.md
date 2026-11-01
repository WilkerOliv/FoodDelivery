# Revisão visual e funcional

## Escopo desta revisão

Esta revisão foi feita em duas passagens: (1) inspeção de fluxos, estados, dados e formulários; (2) correções seguidas de verificação automatizada e inspeção sintática. O objetivo é reduzir mensagens contraditórias e manter uma linguagem visual comum nos perfis de cliente, vendedor, entregador e administrador.

## Aprovação e publicação de lojas

- **Aprovação é uma decisão do administrador, não uma etapa de pagamento.** A loja aguarda análise até o administrador aprová-la ou reprová-la. Comissão do marketplace é configuração financeira para pedidos futuros e não bloqueia a aprovação.
- A administração revisa lojas na seção **Lojas** e também pode analisar a solicitação correspondente em **Solicitações**. Solicitações de loja usam `PATCH /api/v1/admin/platform-requests/{id}/review`; estabelecimentos legados sem solicitação usam `PATCH /api/v1/admin/stores/{id}/approval`.
- A loja aprovada fica publicada se a conta do proprietário e o perfil de vendedor estiverem ativos. Suspensão desativa a loja; a reativação exige que o perfil do vendedor também esteja ativo.
- A tela administrativa agora mapeia os estados comerciais para **Aguardando análise**, **Confirmada** e **Loja suspensa**. **Aguardando pagamento** continua reservado ao pagamento de pedidos e reservas.
- O botão de aprovação guarda o ID em `data-approve-store`; o listener deve recuperar o ID desse mesmo atributo. Clique repetido durante a chamada é descartado para evitar requisições duplicadas.

## Máscaras e valores

| Campo | Apresentação | Envio à API |
| --- | --- | --- |
| CPF | `000.000.000-00` | Somente dígitos |
| CNPJ | `00.000.000/0000-00` | Somente dígitos |
| Preço e taxa de entrega | `R$ 1.234,56` | Inteiro em centavos |
| Comissão | Percentual de 0 a 50 | Número percentual validado |

As funções puras ficam em `Frontend/src/inputMasks.js` e têm testes unitários. Taxa de entrega aceita de R$ 0,00 a R$ 1.000,00. Preço de item precisa ser maior que zero. Estoque permanece numérico inteiro.

## Harmonização visual

A camada compartilhada em `Frontend/theme.css` padroniza altura, borda, raio, espaçamento, tipografia, estados hover/foco/desabilitado e contraste de inputs, seletores e áreas de texto. A busca do cabeçalho mantém seu desenho compacto. Campos monetários usam números tabulares. Painéis, cartões, atalhos e estados vazios compartilham bordas e sombras discretas. Em telas pequenas, os campos aumentam para tamanho legível e formulários de comissão passam a uma coluna. A preferência de movimento reduzido continua respeitada.

Botões primários agora têm fundo preenchido e texto branco; botões claros e contornados têm borda e texto escuros visíveis sem hover. A navegação de filtros das lojas mostra contagens e permite alternar entre todas, aguardando aprovação, confirmadas e suspensas.

O JavaScript mantém as interações no padrão modular já adotado: campos recebem máscaras ao montar cada tela; menus de ações fecham quando o usuário clica fora ou pressiona Escape; avisos duplicados são agrupados, o mais antigo sai quando há mais de três e cada aviso pode ser dispensado manualmente. Os avisos usam `role` e `aria-live` de acordo com a gravidade. Isso evita adicionar um framework de componentes e um processo de build novo para interações que o app já consegue cobrir com módulos ES nativos.

## Matriz de revisão por área

| Perfil/área | Verificações desta revisão |
| --- | --- |
| Cliente — cadastro e perfil | CPF mascarado na criação e perfil; CPF salvo permanece somente leitura. |
| Vendedor — catálogo | Campo de preço em moeda brasileira; conversão validada para centavos antes da API. |
| Barbearias e outros serviços | Catálogo misto de produtos e serviços; serviço escolhe pagamento antecipado ou presencial após atendimento; pagamento presencial exige aceite explícito do risco de falta/não pagamento. |
| Vendedor — cadastro/edição da loja | CNPJ mascarado; taxa de entrega em BRL, com limite e validação; texto explícito de envio para análise. |
| Administrador — lojas | Status de análise coerente; ação de aprovação usa o ID do cartão; comissão com apresentação comum e validação de 0–50%. |
| Administrador — equipe/criação de acesso | CPF mascarado; CPF continua sendo enviado sem pontuação. |
| Chamados | Separados da aprovação comercial; a abertura de detalhes permanece ligada ao endpoint de suporte revisado anteriormente. |
| Localização, pedidos, reservas e entregas | Foram examinadas como partes que compartilham os mesmos controles globais e os estados próprios. O estado `PENDING_PAYMENT` permanece válido apenas nos fluxos de pedidos/reservas. |

## Verificações executadas

- `npm test` no frontend: 16 testes passam, incluindo máscaras, conversão de valores, status comercial, filtros, leitura do ID de aprovação e navegação móvel.
- `node --check` em `main.js`, `inputMasks.js` e `adminSections.js`.
- `git diff --check` sem erros de whitespace.
- Backend: `mvn test -q` executa os 16 testes sem parâmetros manuais para o agente Mockito; a migração Flyway V15 também foi validada em H2.

## Limites conhecidos

Esta revisão corrige e valida o código local. O envio de e-mail/WhatsApp, pagamentos e mapas dependem de credenciais ou serviços externos configurados no ambiente. A máscara é uma conveniência de interface; validação de CPF/CNPJ e autorização continuam sendo responsabilidade do backend.

## Auditoria ampla — 28/10/2026

Esta segunda passagem revisou os fluxos existentes nos módulos de identidade, catálogo, pedidos, reservas, entregas, mensagens, suporte, administração e pagamentos; as migrações Flyway; inicialização local; e as telas acessíveis pelo navegador local. Os documentos e alterações que já estavam no diretório foram mantidos.

### Resultado e correções desta passagem

- **Build de testes do backend:** os testes Mockito falhavam antes de executar os casos porque a JVM do ambiente bloqueia a autoanexação do agente Byte Buddy. O Maven agora resolve o JAR Mockito e o inicia como agente de teste. O comando `mvn test -q` executou os 16 casos sem falhas.
- **Docker/PostgreSQL local:** o compose estava usando porta 5432 e usuário `fooddelivery`, incompatíveis com o perfil local da API (5433 e `fooddelivery_app`). A configuração agora alinha porta, usuário, senha e healthcheck aos valores de ambiente do backend. O modo Docker é alternativo; o supervisor `npm start` continua destinado ao PostgreSQL local e não deve ser misturado com um container na mesma porta.
- **Navegação em celular:** em painéis com muitas rotas, os nomes da barra inferior ficavam espremidos. A navegação móvel passou a priorizar quatro destinos conforme o perfil e oferece **Menu** para abrir a navegação completa. A opção não aparece quando todos os destinos cabem. Foram adicionados testes para ambos os casos.
- **Migração do catálogo e reservas:** a migração V15 foi validada no perfil H2; todas as 15 migrações foram aplicadas e o Hibernate iniciou com validação do esquema.
- **Lojas e aprovação:** o código diferencia `Aguardando análise`, `Confirmada` e `Loja suspensa`; o administrador pode revisar a solicitação pendente ligada à loja ou usar a rota legada de aprovação. Comissão não participa da decisão de aprovação.
- **Chamados:** a inspeção estática confirmou rotas distintas para lista, detalhe, resposta e status administrativo, com checagem de propriedade no acesso do cliente e acesso administrativo restrito por função.
- **Reserva com pagamento presencial:** o backend cria a reserva como confirmada e sem pagamento online; a agenda do vendedor filtra somente reservas que ainda aguardam pagamento. Produtos não aceitam pagamento após serviço, e o cliente precisa confirmar que compreendeu o risco. Os testes de domínio cobrem essa regra.

### Evidência visual e conteúdo

A inspeção do navegador foi feita em largura móvel (352 px) e no fluxo público de descoberta. A sessão visual aberta ainda serviu a barra antiga de sete itens depois da atualização; a tentativa de iniciar a cópia atual numa porta separada foi bloqueada pela permissão de rede local do ambiente. Por isso, o limite de destinos móveis foi verificado por teste unitário e sintaxe, mas ainda precisa de confirmação visual quando o projeto for iniciado pela cópia atualizada. As abas internas do vendedor e do administrador continuam roláveis horizontalmente em telas estreitas. Na ficha pública da **Barbearia de nantes**, a tela mostrava “Esta loja está preparando o cardápio”; a área do catálogo do vendedor também estava vazia. Isso é uma condição dos dados da loja observada, e não foi criada nenhuma oferta de teste ou alterado dado persistente. O vendedor precisa cadastrar pelo menos um serviço ativo para que o cliente possa reservar.

As imagens mostram mensagens de erro de chamadas administrativas em uma execução anterior. O frontend atual usa as rotas acima e exibe a mensagem retornada pela API; não foi possível atribuir a causa dessas ocorrências sem reproduzir uma ação mutável na base que contém as contas/lojas do usuário. O painel não teve aprovação, suspensão, redefinição de senha ou envio de chamado efetuado nesta auditoria.

### Revisão da arquitetura e dados

- **Frontend:** JavaScript com módulos ES nativos e CSS compartilhado; não há framework de componentes/build instalado. A navegação móvel foi isolada num helper puro e coberta por teste. A estrutura atual permite manter o app sem acrescentar uma cadeia de build maior para essa interação.
- **Backend:** monólito modular Spring Boot 3.5 / Java 21; autorização JWT stateless e controle por função, validação de entrada, separação entre entidades JPA e DTOs públicos no catálogo, checagens de propriedade para recursos por loja/conta e operações transacionais.
- **Persistência:** PostgreSQL é o destino persistente local/produção; Flyway mantém migrações comuns e diferenças PostgreSQL/H2; Hibernate usa `validate`, sem alterar o esquema por conta própria. Chaves de CPF e CNPJ são únicas e os documentos não são IDs primários. Dinheiro usa centavos inteiros; datas persistidas usam instantes UTC.
- **Integrações:** ViaCEP é consultado no navegador com preenchimento manual como alternativa; Mercado Pago, SMTP, WhatsApp Cloud e geração OpenAI dependem de credenciais/configuração. Nesta execução não foi feita transação real com esses provedores. O fluxo local de pagamento de desenvolvimento só é habilitado no perfil `dev-h2`.
- **Segurança:** hash de senha fica fora da serialização; tokens são invalidados por versão de credencial e mudanças de perfis; recuperação usa código de uso único armazenado como hash; tokens de loja do Mercado Pago são criptografados. O segredo JWT embutido é apenas fallback de desenvolvimento e deve ser substituído por `JWT_SECRET` no perfil de produção.

### Verificações executadas

| Verificação | Resultado |
| --- | --- |
| `cd Backend && mvn test -q` | 16 testes, 0 falhas e 0 erros; validação H2/Flyway v15 incluída |
| `cd Frontend && npm test` | 16 testes aprovados |
| `node --check` em `main.js`, `mobileNavigation.js`, `inputMasks.js` e `adminSections.js` | Sem erros de sintaxe |
| `python3 -m unittest scripts.test_dev -v` na raiz | 6 testes de inicialização aprovados |
| `git diff --check` | Sem erros de whitespace |

O compose foi revisado e validado com `docker compose config --quiet`; o daemon Docker e uma inicialização com volume limpo não foram executados nesta sessão.

### Riscos e limites ainda presentes

1. **Alta — integração e homologação externa:** credenciais reais de Mercado Pago, SMTP, WhatsApp, OpenAI e disponibilidade de ViaCEP não foram verificadas nem podem ser garantidas por testes offline. Configure sandbox e faça homologação antes de receber pagamentos reais.
2. **Média — cobertura de segurança por fluxo:** há checagens de autorização e testes de unidade, mas a suíte não faz uma matriz completa de chamadas HTTP autenticadas para cada papel (cliente, vendedor, entregador e administrador) com tentativas de acesso cruzado.
3. **Média — rastreabilidade administrativa:** o projeto não mantém trilha de auditoria imutável e completa para decisões e mudanças de conta (autor, valor anterior, motivo e horário). Essa lacuna já aparece em “Limites desta entrega” do guia operacional.
4. **Média — localização e rastreio:** coordenadas do entregador e atualização periódica estão implementadas, mas não equivalem a despacho por distância, roteamento turn-by-turn, geocodificação garantida ou SLA de entregador. O navegador exige permissão de localização e HTTPS fora do ambiente local.
5. **Média — agenda (corrigido em 01/11/2026):** a duração do serviço e a sobreposição do intervalo completo agora são validadas; seguem pendentes grade configurável, pausas múltiplas e jornada por profissional.
6. **Baixa — volume de dados:** listagens administrativas e algumas consultas fazem carregamento integral e a composição de alguns cartões pode gerar consultas repetidas. Para o volume pequeno/local atual é funcional; antes de crescer, adotar paginação, filtros no servidor e métricas de consultas.

Os testes não substituem homologação operacional. O navegador permitiu examinar telas, mas ações de escrita no banco real foram deliberadamente evitadas; integrações que dependem de segredos permanecem não verificadas, não aprovadas.

## Descoberta por categorias e cobertura local (30/10/2026)

A página **Descobrir** apresenta cartões ilustrados apenas para categorias com atividade na cidade selecionada e reserva o último cartão para **Todas as categorias**. Esse atalho abre a rota `#categories`; ao escolher uma categoria, a rota `#categoryStores` mostra apenas os estabelecimentos públicos disponíveis nela. O botão **Todas as categorias** retorna ao catálogo completo, e o histórico do navegador também mantém a navegação entre essas telas.

A interface não apresenta classificação, pontuação ou volume de vendas por categoria. O endpoint público que expunha esse cálculo foi removido. A descoberta mostra apenas a quantidade de lojas disponíveis em cada categoria; categorias oficiais continuam preservadas, e as personalizadas seguem sob gestão administrativa.

## Escritas administrativas e avisos de cadastro

Os formulários de cidade e categoria agora tratam a resposta da gravação separadamente da atualização das listas. Depois que a API confirma a gravação, a interface informa sucesso mesmo se a consulta posterior falhar e mantém o registro retornado visível até a próxima atualização. Falhas da própria gravação continuam apresentadas como erro. O mesmo cuidado foi aplicado à edição e suspensão/reativação de cidades e à exclusão de categoria.

Na descoberta, a faixa horizontal antiga de categorias foi substituída por cartões em grade responsiva. A grade se reorganiza conforme a largura da tela e não usa rolagem horizontal; nomes longos podem quebrar linha e os cartões mantêm uma área confortável para toque. Essa regra também cobre a marcação antiga caso uma sessão ou versão em cache ainda a renderize.

## Ícone do navegador

O frontend agora publica `Frontend/favicon.svg` e `Frontend/favicon.ico`, com a marca FoodDelivery. Isso atende o pedido automático do navegador por `/favicon.ico` e elimina o 404 no servidor estático iniciado por `npm start`.

## Correções dos fluxos de cliente, vendedor, agenda e suporte (31/10/2026)

- **Cadastro legado de cliente:** clientes sem CPF válido ou telefone continuam podendo entrar e navegar, mas a compra e a reserva são bloqueadas com explicação. O atalho leva a **Minha conta > Dados pessoais**; após salvar, o perfil e os dados de checkout são recarregados. A API valida CPF e telefone no servidor, então a exigência não depende apenas do formulário.
- **Catálogo do vendedor:** cada item oferece **Editar**, **Remover** e, quando removido, **Reativar**. Remover executa exclusão lógica (`active=false`), preservando vínculos e histórico. As rotas verificam que o item pertence a uma loja do vendedor autenticado.
- **Agenda da barbearia:** o vendedor pode cadastrar profissionais e suspender/reativar cada cadastro. Na reserva, o cliente escolhe um nome ou **Primeiro disponível**; o backend escolhe apenas profissionais ativos sem reserva naquele instante. A migração V17 cria `store_professionals`, associa profissional opcional a reserva/slot e torna a unicidade do horário profissional-específica. Lojas sem profissionais cadastrados conservam a agenda legada por loja.
- **Fuso da loja:** cadastro e edição da loja permitem definir o fuso (Brasília como padrão, com opções para regiões brasileiras e UTC). O backend valida identificadores IANA, entrega o fuso nos DTOs de loja/reserva e mantém instantes em UTC. A tela converte entrada e exibição usando o fuso da loja e rejeita horários locais inexistentes durante mudança de relógio.
- **Chamados administrativos:** o botão de retorno do detalhe recarrega a lista administrativa e fecha o detalhe; não encaminha à central do cliente. Respostas e atualização de status continuam no detalhe do chamado.
- **Dados da loja:** a aba usa o formulário de edição associado à loja atualmente selecionada. Trocar loja sincroniza catálogo, conexão de pagamento e equipe profissional; o formulário separado de **Adicionar outra loja** permanece disponível.
- **Responsividade do vendedor:** abas da loja quebram linha em telas estreitas, cartões do catálogo permitem que ações ocupem novas linhas e os formulários usam grade adaptativa. Isso elimina a rolagem horizontal na navegação interna do vendedor.

### Validação desta rodada

- `cd Frontend && npm test`: 18 testes aprovados, incluindo conversão de horários entre fuso local da loja e UTC, limite de antecedência e horário inexistente na transição de horário de verão.
- `node --check Frontend/src/main.js`: sintaxe válida.
- `cd Backend && mvn test -q`: concluído com sucesso; H2/Flyway aplicou as 17 migrações e inicializou Hibernate.
- `git diff --check`: sem erros de whitespace.
- Diagnóstico da aplicação local: a API antiga em `8080` mantinha `/api/v1/status` online, mas `/actuator/health` expirava porque o PostgreSQL local estava parado. O banco foi iniciado sem apagar nem migrar dados; em seguida, a API respondeu `UP` e `npm start` reconheceu API e frontend ativos em `8080` e `5173`.
- `scripts/dev.py` agora, diante de uma API conhecida como FoodDelivery mas sem healthcheck, verifica o estado do PostgreSQL e o inicia se estiver parado; aguarda até 20 segundos pela recuperação da API antes de classificar a instância como indisponível. Continua sem lançar uma segunda API sobre uma porta ocupada.
- A aplicação foi iniciada nesta sessão e os endpoints de status e health foram confirmados. Os seis fluxos visuais autenticados continuam sem revalidação pelo navegador nesta rodada.


## Ajustes de catálogo, cidade e experiência pública (31/10/2026)

- **Ícones coerentes:** serviços usam um calendário (`🗓️`) e produtos usam uma caixa (`📦`); não existe mais tesoura como ícone genérico de serviço.
- **Edição completa de catálogo:** o vendedor pode alterar nome, descrição, preço, estoque, tipo (produto/serviço) e regra de pagamento. Ao converter para serviço, estoque é zerado; ao converter para produto, o campo de estoque é reativado. Pagamento após o atendimento exige aceite explícito do risco novamente. Registros antigos mantêm os snapshots de pedido e reserva.
- **Remoção recuperável:** `DELETE /merchant/items/{id}` marca `active=false` por uma atualização SQL limitada ao dono da loja, incrementa a versão e preserva os vínculos históricos; a ação é repetível e o vendedor pode reativar o item.
- **Pagamento fora da cidade:** a API compara cidade e UF do perfil persistido do cliente com cidade e UF cadastradas na loja, normalizando acentos e caixa. Se diferirem ou faltarem, uma reserva oferecida com pagamento posterior vira uma reserva pendente de pagamento antecipado. O navegador avisa antes do envio, mas a regra é aplicada no backend. Compras de produtos já são antecipadas para todos.
- **Notificação do comerciante:** pedidos e reservas com pagamento online geram notificação direcionada à operação quando o provedor confirma o pagamento; a mensagem deixa explícito que a plataforma confirmou o pagamento.
- **Região sem lojas:** o estado vazio explica que as lojas aparecem na cidade em que foram cadastradas e oferece trocar de cidade, evitando sugerir que dados de outra cidade sumiram. A escolha de região não altera o endereço real da loja.
- **Acesso e confiança:** a tela de login ganhou cores mais legíveis, contraste, apresentação responsiva e uma janela “Saiba mais” com explicação para clientes, comerciantes e entregadores.
- **Mapa:** a prévia continua centrada na cidade selecionada e agora oferece um link explícito para abrir o mapa completo.
- **Categorias:** a grade não exibe pontos, vendas concluídas ou tabela de classificação. A lista completa mantém contagem de lojas por categoria e navegação de retorno.

A política de pagamento usa a cidade/UF do perfil do cliente como referência persistente. Alterar a cidade de navegação não muda o endereço do cliente nem o da loja; isso impede que um parâmetro editável do navegador reduza a exigência de pagamento antecipado.

### Verificações desta revisão (31/10/2026)

- Navegador autenticado como vendedor: catálogo abriu com ícones de caixa para produto e calendário para serviço. O editor abriu os campos de tipo, estoque e forma de pagamento; ao alternar serviço para produto, estoque apareceu e forma de pagamento de serviço foi ocultada. A edição foi cancelada sem gravar alterações.
- Navegador: a seleção de Iepê mostrou 0 lojas e explicou que nenhuma loja aprovada está disponível nessa região; a ação de trocar de cidade estava disponível. A seleção foi restaurada para Nantes. Nenhum endereço real da loja foi alterado.
- Backend: teste H2/Flyway confirma remoção lógica por SQL, repetibilidade, preservação do registro e bloqueio para usuário que não é dono da loja.
- Reinício local: `npm start` iniciou PostgreSQL e API do projeto; `/actuator/health` retornou `UP`, `/api/v1/status` retornou `online` e a interface autenticada voltou a carregar catálogo e dados da loja.
- `cd Frontend && npm test`: 20 testes aprovados.
- `JWT_SECRET=<chave temporária de teste em Base64> mvn -q test`: 21 testes aprovados, sem falhas ou erros; inclui 18 migrações Flyway no contexto H2.
- `node --check` em `Frontend/src/main.js` e `Frontend/src/catalogPolicy.js`, mais `git diff --check`: sem erros.

Não foram criados pedidos ou reservas reais, nem enviados pagamentos a um provedor. SMTP, WhatsApp, geocodificação externa e credenciais reais de pagamento exigem configuração e homologação fora da suíte local. O fluxo de cidades utiliza a região selecionada pelo usuário e o CEP informado; GPS automático não foi validado nesta rodada.

## Correção de catálogo e publicação da loja antiga (01/11/2026)

- **Remover/reativar catálogo:** a tela chamava `setCatalogItemActive`, que não existia no JavaScript; por isso o clique gerava `ReferenceError` e nenhuma requisição era enviada. A função agora chama `DELETE /merchant/items/{id}` para arquivar logicamente e atualiza o catálogo após a confirmação do servidor. Para reativar, usa a atualização autenticada do mesmo item, preservando os dados. Ações repetidas ficam bloqueadas enquanto a operação está em andamento e falhas exibem a mensagem devolvida pela API.
- **Loja ausente da vitrine:** a “Venda da esquina” tinha cidade, UF e CNPJ vazios no registro legado. A loja aparecia como aprovada no painel do vendedor, mas a busca pública por cidade não podia incluí-la sem localização. A aba **Dados da loja** agora explica essa condição e sugere como rascunho a região atualmente selecionada; o vendedor precisa salvar para confirmar. A cidade e UF sugeridas (Nantes/SP) foram salvas a pedido do responsável, e a loja passou a aparecer no endpoint público e na descoberta da cidade.
- **CNPJ da loja:** o campo agora é opcional na criação e na edição. Quando informado, precisa ser válido, único e não pode ser trocado depois de salvo.
- **Layout estreito:** o cabeçalho móvel passou a usar uma linha para navegação/ações e outra para busca. No catálogo, preço, ações e texto ocupam linhas próprias quando necessário; os botões permanecem com área de toque legível. Conferência visual feita a 560 px, sem rolagem horizontal.

### Validação desta correção

- Navegador autenticado como vendedor: salvar a localização foi confirmado pela notificação de sucesso; a aba deixou de exibir o alerta de localização ausente.
- `GET /api/v1/stores?city=Nantes&state=SP`: retornou “Venda da esquina” com `active=true` e `approved=true`, além das lojas já publicadas.
- Tela **Descobrir**: exibiu 3 lugares e o cartão de Mercados abriu “Venda da esquina”.
- Remoção lógica manual do item de teste `teste`: o botão mudou para **Reativar** e o aviso confirmou preservação do histórico; a reativação retornou o botão **Remover**. O item foi restaurado ao estado ativo ao final da verificação.
- Frontend: `npm test` — 20 aprovados; `node --check Frontend/src/main.js` e `git diff --check` sem erros.
- Backend: `JWT_SECRET=<chave efêmera em Base64> mvn test -q` — 21 testes aprovados, sem falhas ou erros. A primeira execução sem `JWT_SECRET` falhou ao inicializar o contexto de teste; com a chave de teste local exigida pela configuração, toda a suíte passou.
- Reinício local: a API foi reiniciada para usar o código novo; PostgreSQL permaneceu ativo, sem recriação ou limpeza dos dados. API e frontend voltaram a responder nas portas 8080 e 5173.

## Horários da loja e CNPJ opcional (01/11/2026)

- O comerciante pode ativar a rotina semanal por loja, selecionar qualquer combinação de dias e definir abertura/fechamento de cada um. Dias não selecionados ficam fechados; intervalos que cruzam a meia-noite são tratados até o horário de encerramento.
- Exceções por data substituem a rotina: fechamento integral ou horário especial/fechamento antecipado. O atalho **Fechar hoje** usa o fuso local da loja. As exceções valem mesmo quando a rotina semanal está desativada; removê-las restaura a rotina. Observações internas não são expostas na rota pública.
- A verificação no servidor bloqueia novo pedido quando a loja está fechada, confere novamente no início do checkout e exige que todo o intervalo da reserva caiba na janela de funcionamento. O painel/modal também desabilita ações quando identifica a loja fechada. A validação do servidor é a autoridade final em caso de tela desatualizada.
- O cadastro de lojas aceita CNPJ ausente. Quando preenchido, o CNPJ é validado, único e imutável. Lojas legadas permanecem sem restrição semanal e sem necessidade de CNPJ.
- Revisão manual no navegador, sessão de vendedor, em viewport móvel (578 × 678): a aba **Dados da loja** mostrou a configuração sem rolagem horizontal; ao ativar a rotina e selecionar quinta-feira, o campo de abertura ficou habilitado; ao desfazer os controles, voltou ao estado desativado. Nenhuma alteração foi enviada ou salva durante essa inspeção.
- A tela de carregamento que persistia por causa de módulo ES antigo em cache foi corrigida com uma versão explícita nas URLs de `main.js` e `storeTime.js`; após recarregar, a descoberta e o painel do vendedor abriram normalmente. Os erros de console anteriores à correção eram históricos e não reapareceram na página já atualizada.
- A migração V19 foi aplicada no PostgreSQL local e em H2 durante os testes. `GET /api/v1/store-locations`, `GET /api/v1/stores` e `GET /api/v1/stores/{id}/hours` responderam sem autenticação; a consulta pública removeu as observações das exceções.
- Frontend: `npm test` — 23 aprovados; `node --check` em `src/main.js` e `src/storeTime.js` sem erros.
- Backend: `JWT_SECRET=<chave temporária de teste em Base64> mvn -q test` — 27 testes, 0 falhas e 0 erros; o contexto H2 validou as 19 migrações e Hibernate validou o esquema.
- `npm start` reiniciado com sucesso. `/actuator/health` retornou `UP` e a interface respondeu HTTP 200 em `127.0.0.1:5173`.

**Limite funcional no momento da revisão:** reservas guardavam somente o instante de início, sem duração do serviço. Corrigido pela migração V20, conforme a atualização de riscos abaixo.

## Atualização da revisão de riscos (01/11/2026)

Os limites históricos descritos acima sobre duração de serviço, intervalo de reserva e cancelamento pós-pagamento foram tratados na atualização seguinte. A revisão original permanece como registro do estado observado naquela data; use este adendo e `Arquitetura_e_operacao.md` para o comportamento atual.

- **Concorrência e duração de reservas:** a migração V20 guarda duração no item e início/fim na reserva. O backend bloqueia a linha da loja durante a decisão de disponibilidade, consulta sobreposição de intervalos e ocupa o profissional escolhido ou o recurso da loja inteira. O mesmo intervalo precisa caber no horário de funcionamento. Testes de domínio e esquema verificam duração, janela completa e unicidade quando não há profissional.
- **Cancelamento pós-pagamento:** o cliente pode solicitar em pedido/reserva; a operação fica bloqueada para avanço até a decisão do vendedor. Aceitar pedido repõe estoque e inicia estorno; aceitar reserva libera o intervalo e inicia estorno apenas quando houve pagamento online. Recusar permite continuar a operação. O provedor continua sendo a autoridade sobre o resultado financeiro final.
- **Localização de loja:** V22 armazena coordenadas opcionais, com validação de par e limites, e o painel gera um link do Google Maps. Isso não cria nem edita automaticamente um Perfil da Empresa do Google.
- **Validação desta atualização:** os novos testes de cancelamento, coordenadas, agenda e migrações são parte da suíte. A validação automatizada local não comprova checkout/estorno real, entrega de mensagens, migração PostgreSQL em produção nem inspeção visual manual em todos os tamanhos de tela.
- **Riscos prioritários ainda abertos:** homologação externa de pagamentos/estornos e notificações; disponibilidade/recuperação operacional fora da máquina local; MFA e rate limit distribuído; trilha administrativa completa; tarifa/ledger e repasse de entregadores; e capacidade avançada de agenda por profissional. A relação e as dependências estão detalhadas no guia de arquitetura e operação.

### Segurança adicional e evidência local (01/11/2026)

- **Autorização padrão:** foi fechado um caminho de acesso anônimo a rotas não classificadas: rotas sem regra pública explícita agora exigem JWT autenticado. Testes unitários verificam visitante, sessão JWT normal e usuário forçado a trocar senha.
- **Abuso de autenticação:** o servidor limita por endereço remoto as requisições de login, recuperação e cadastro. A proteção é por instância e não confia em `X-Forwarded-For`; implantação atrás de proxy/múltiplas réplicas ainda requer gateway ou armazenamento distribuído consciente da topologia.
- **Auditoria administrativa:** V23 e a aba **Definições > Auditoria** registram as ações administrativas instrumentadas e exibem um histórico recente. O registro exclui deliberadamente CPF, senha e tokens, mas ainda não cobre toda alteração sensível, não detecta alteração feita diretamente no banco e não substitui armazenamento imutável ou trilha de conformidade.
- **Verificação:** suíte Java executada com H2/Flyway e testes de autorização/limites; `npm run check` e `npm test` executados. Essa validação não atesta serviços externos, banco PostgreSQL de produção, inspeção de navegador nesta rodada, pentest ou operação pronta para cobrança real.
