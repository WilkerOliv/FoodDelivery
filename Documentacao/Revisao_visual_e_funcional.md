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
5. **Média — agenda:** a reserva ocupa um único horário por loja, sem agenda por profissional, duração de serviço, intervalos, capacidade por equipe ou fuso operacional validado em interface. A especificação de agenda por profissional permanece em documento de design e não foi implementada nesta revisão.
6. **Baixa — volume de dados:** listagens administrativas e algumas consultas fazem carregamento integral e a composição de alguns cartões pode gerar consultas repetidas. Para o volume pequeno/local atual é funcional; antes de crescer, adotar paginação, filtros no servidor e métricas de consultas.

Os testes não substituem homologação operacional. O navegador permitiu examinar telas, mas ações de escrita no banco real foram deliberadamente evitadas; integrações que dependem de segredos permanecem não verificadas, não aprovadas.
