# Auditoria técnica e funcional do FoodDelivery — 03/11/2026

## Adendo de homologação — 04/11/2026

Foi implementado ambiente local isolado com comando `npm run start:demo`, banco H2 próprio, quatro contas fictícias, mercado/barbearia e profissionais de demonstração, simuladores de pagamento/estorno, captura de recuperação EMAIL/WHATSAPP, capa PNG local e fixtures de CEP/geocodificação. `npm run start:demo:mailpit` acrescenta entrega SMTP ao Mailpit, acessível apenas em loopback. Guia completo: [Homologação sem dados reais](Homologacao_sem_dados_reais.md).

Evidência: suíte Java com 60 testes aprovados, sem falhas/erros; frontend com 32 testes aprovados e sintaxe validada em 12 módulos; `git diff --check` sem erros. O roteiro HTTP `scripts/test_homologacao.py` passou com quatro perfis, restrição de inbox, tentativa de aprovação por outra conta, cookie isolado, checkout externo bloqueado, aprovação/recusa/repetição de pagamento, pedido na fila do vendedor, reserva paga, recuperação EMAIL/WHATSAPP, PNG simulado e cancelamento/estorno local. Mailpit recebeu um e-mail pelo SMTP local.

No navegador, foi confirmado login de cliente, abertura de mercado, adição à sacola, checkout simulado, aviso de recusa, aprovação e retorno à lista com pedido aguardando a loja. Esse teste não constitui revisão visual completa. A inspeção revelou cookie expirado bloqueando novo login após reinício; a resolução do cookie foi corrigida para permitir autenticação/recuperação e descoberta pública com cookie antigo, mantendo validação em rotas privadas e teste de regressão.

O simulador não homologa os provedores externos nem implementa saque/ledger de entregadores. O mapa visual ainda depende de Leaflet/tiles/fontes externos; busca geográfica e CEP usam somente Nantes e Iepê como fixtures locais. As conclusões e contagens abaixo pertencem à revisão original de 03/11/2026.

## Resumo executivo

O FoodDelivery já tem uma base funcional substancial para um marketplace local: API Spring Boot, PostgreSQL com migrações Flyway, autenticação, autorização por perfil, catálogo de produtos/serviços, pedidos, reservas, agenda, suporte, notificações, localização, pagamentos Mercado Pago e fluxos administrativos. Depois da primeira revisão, corrigi a exposição do bearer token no navegador, acrescentei proteção de origem para mutações por cookie, CSP na API e no servidor local e tornei públicas as rotas geográficas necessárias para a descoberta. A suíte ampliada agora contém 54 testes de backend e 31 de frontend; a verificação sintática continua cobrindo 10 módulos JavaScript.

Isso não comprova prontidão comercial ou segurança de produção. A interface é um frontend JavaScript/CSS próprio e concentrado em `Frontend/src/main.js`; não há testes automatizados ponta a ponta nem cobertura de acessibilidade. Pagamentos, estornos e serviços externos não foram homologados com credenciais/provedores reais nesta auditoria. As principais pendências são validar sessão/cabeçalhos no domínio HTTPS final, limitação de tentativas distribuída, autorização por objeto com testes abrangentes, homologação financeira e expansão dos testes reais por perfil.

**Conclusão:** adequado para desenvolvimento e piloto local controlado; **não declarar pronto para operação comercial em produção** até concluir os bloqueios externos e as evidências P0/P1 abaixo.

## Atualização corretiva — 03/11/2026

### Feito nesta rodada

- O login e a troca de senha agora enviam a sessão em cookie `HttpOnly`, `SameSite=Lax`, com escopo `/api/v1`; no perfil de produção o cookie é `Secure`. O JWT não é mais devolvido no JSON do login/troca de senha nem guardado no `localStorage` pelo frontend.
- O frontend envia cookies explicitamente, limpa o bearer legado no navegador e mantém no armazenamento local apenas a preferência do perfil ativo. A preferência nunca autentica: a conta e as permissões são carregadas de `/account/me` a cada início.
- Logout invalida o cookie no servidor. Um `401` na checagem inicial de visitante não mostra falso aviso de sessão expirada.
- CORS lê `FOODDELIVERY_ALLOWED_ORIGINS` pela configuração Spring, aceita apenas origens exatas e credenciais. Para mutações autenticadas pelo cookie, a API também exige um `Origin` que esteja na mesma lista; integrações Bearer sem cookie não dependem desse cabeçalho.
- A API publica CSP restritiva, `nosniff`, bloqueio de frames, política de referenciador e permissões. O servidor local do frontend acrescenta CSP e cabeçalhos de navegador às respostas.
- As consultas de mapa estão explicitamente públicas, com validação geográfica já existente. A consulta pública de geocoding não mantém threads dormindo para limitar o provedor e o cache agora tem limite de tamanho.
- Foi adicionado workflow de segurança no GitHub Actions: CodeQL para Java/JavaScript em push, PR e agenda semanal; revisão de dependências de PR; geração e retenção temporária de SBOM CycloneDX do backend.
- Entraram nove testes no backend e dois no frontend para atributos/limpeza do cookie, resolução de cookie/Bearer, rejeição de origem hostil, sessão sem token em storage e sondagem de visitante.

### Validação após as correções

- Frontend: `npm run check` passou (10 módulos JavaScript) e `npm test` passou (28 testes de fluxo/helpers, 2 testes de sessão e 1 teste de perfil administrativo).
- Backend: a execução de `JWT_SECRET=<chave efêmera> mvn -q test` concluiu com 54 testes e sem falhas. Os logs incluem um erro de constraint esperado no teste de concorrência de reservas; a asserção confirma que apenas uma reserva ocupa o horário.
- Não fiz nesta rodada uma nova navegação manual em todas as telas nem uma transação/estorno em sandbox. Os testes de código não homologam integrações externas ou o deploy.

### Limites de implantação da sessão/CSP

- Produção precisa servir a aplicação sob HTTPS, manter `AUTH_COOKIE_SECURE=true` e configurar `FOODDELIVERY_ALLOWED_ORIGINS` apenas com a origem pública oficial. Frontend e API devem ser same-site para que `SameSite=Lax` funcione com chamadas `fetch` autenticadas. Se forem domínios de site diferentes, é necessária arquitetura de sessão/CORS apropriada, com `SameSite=None; Secure` e proteção CSRF adicional.
- HSTS deve ser aplicado no proxy/CDN que termina TLS, após confirmar HTTPS em todos os subdomínios. Não foi ativado no serviço local, que usa HTTP.
- A política de conteúdo local permite as origens externas usadas hoje (Leaflet, Google Fonts, ViaCEP e tiles OpenStreetMap). Em produção, a política deve ser ajustada para o domínio real de assets/API e validada nos fluxos de mapa, imagens, checkout e fontes. Ela não substitui revisão de XSS.

### Correção de CPF administrativo — 03/11/2026

- A tela **Definições > Perfil do administrador** agora apresenta a ação explícita **“Remover o CPF desta conta administrativa”** quando existe CPF cadastrado. Deixar o campo vazio sem marcar a opção preserva o número atual, evitando uma remoção acidental.
- Ao confirmar a ação, o frontend envia `clearCpf=true` e não envia um CPF. Ao receber sucesso, remove o documento também do estado em memória para ele não reaparecer na tela.
- A API aceita a remoção somente quando a conta tem `ADMIN` entre os papéis registrados. Clientes, vendedores e entregadores continuam com CPF obrigatório. A regra usa os papéis registrados porque uma conta administrativa pode ter outro papel selecionado como ativo.
- A alteração continua na trilha de auditoria sem CPF, senha ou token no evento.
- Evidência adicionada: três testes Java cobrem remoção permitida, conta com papel administrativo registrado e bloqueio de perfil profissional; um teste JavaScript confirma o payload e a remoção do estado no navegador.

## Escopo, método e limites

- Revisão estática do frontend, controladores/serviços Spring, autorização, configuração, esquema e migrações PostgreSQL/Flyway, scripts operacionais e documentação.
- Testes executados: `mvn -q test` com `JWT_SECRET` efêmero válido; `npm --prefix Frontend run check`; `npm --prefix Frontend test`.
- Teste manual no navegador local: login de cliente e administrador; tela de descoberta e filtros/categorias; abertura de loja e catálogo; formulário de reserva; perfil/endereço/segurança/acessos; navegação inferior móvel; estados vazios de pedidos e reservas; suporte existente e detalhe de chamado; abas administrativas de chamados e solicitações.
- Painéis de vendedor e entregador não foram percorridos manualmente nesta rodada; esses domínios foram revisados por código e testes existentes. As ações de alteração/exclusão de loja, item, conta, pedido e reserva não foram executadas em registros preexistentes.
- Foram usados dados já existentes. Não foram submetidas compras, cobranças, estornos, alterações ou exclusões de cadastros reais. Não foi concedida permissão de GPS nem enviada comunicação externa.
- O ambiente local funciona pelo navegador. As portas locais não estavam acessíveis ao `curl` no processo de terminal isolado; por isso, os códigos HTTP da API e os cabeçalhos em resposta não foram medidos por essa via.
- A conta cliente de teste não tinha CPF/telefone completos; a interface bloqueia o fechamento de compra/reserva até preencher dados. O fluxo de pagamento não foi concluído.
- Não foi possível comprovar as integrações reais, política operacional, recuperação de backup em ambiente isolado, carga, exploração ofensiva ou compatibilidade de produção. “Não verificado” não significa “falhou”.

## Resultado dos testes

Comandos executados com resultado aprovado: `JWT_SECRET=<chave-efêmera> mvn -q test` (54 testes); `npm --prefix Frontend run check` (10 módulos); `npm --prefix Frontend test` (31 testes). A variável JWT foi gerada apenas para iniciar os testes locais e não foi gravada no repositório.

| Camada | Resultado observado | Limite |
|---|---|---|
| Backend | `mvn -q test`: 54 testes aprovados, 0 falhas | Os testes existentes não cobrem todos os endpoints nem execução concorrente em produção |
| Frontend | Verificação sintática de 10 módulos; 31 testes passaram | Testes focam lógica e regressões pontuais, não a jornada inteira no browser |
| Cliente no browser | Login, navegação, categorias, loja, perfil e suporte existentes renderizaram | Compra/reserva real bloqueada pelos dados incompletos; sem pagamento simulado ou real |
| Administração no browser | Login, visão geral, lista/detalhe de chamado, retorno à lista e solicitações/histórico acessíveis | Ações mutáveis da administração foram apenas revisadas no código, não executadas sobre registros |
| Responsividade | Janela móvel estreita de 410 px sem overflow horizontal no fluxo de cliente observado; navegação inferior operável | Não é uma matriz completa de aparelhos, zoom, leitores de tela ou todos os formulários |
| Serviços externos | Não homologados nesta sessão | Mercado Pago, SMTP, WhatsApp, OpenAI, geocodificação/tiles e GPS dependem de credenciais, rede, permissões ou contas de teste |

### Observações manuais reproduzíveis

1. **Cliente:** login bem-sucedido; tela inicial mostrou três lojas e categorias correspondentes. A página “Todas as categorias” apresentou 11 categorias e a filtragem de Mercado levou à loja esperada; o retorno à lista funcionou.
2. **Catálogo e reserva:** o catálogo da loja apresentou produto e serviços. Abrir a reserva exibiu seleção de data/profissional/pagamento. A conta do cliente não tinha CPF/telefone completos e não foi possível concluir a etapa de compra/reserva. O aviso de pagamento antecipado “fora da cidade” foi exibido mesmo com a região de navegação definida para Nantes. Como o endereço/cidade do perfil do cliente estava vazio, a regra pode estar comparando o endereço da conta com o endereço da loja; a interface deveria explicar a diferença entre região de busca e cidade usada para elegibilidade.
3. **Minha conta:** abas Dados pessoais, Endereços, Segurança e Acessos foram exibidas. CPF/telefone ausentes deixam operações protegidas desabilitadas/condicionadas, conforme o fluxo de cadastro progressivo.
4. **Suporte:** o chamado existente foi aberto, a conversa e o botão “← Chamados” estavam disponíveis; o retorno à lista foi acionado. Não foi criado um novo chamado para evitar gravar ruído no histórico.
5. **Administrador:** login aceito; visão geral mostrou contagens coerentes com os registros disponíveis; a área Chamados mostrou o ticket de teste fechado; o detalhe abriu; a navegação alcançou Solicitações e histórico. Lojas, Equipe, Financeiro e Definições foram selecionadas para inspeção, mas não foi feita homologação de cada ação mutável.
6. **Cliente sem dados obrigatórios:** bloqueio ao finalizar compra/reserva é uma dependência de cadastro e não falha de API observada. A experiência deve levar diretamente ao campo faltante e retornar à etapa sem perder a sacola/reserva.

## Inventário funcional

### Confirmado no código e/ou no teste desta rodada

- **Autenticação e contas:** registro/login, perfis vinculados a uma conta, recuperação de senha, troca obrigatória de senha temporária, suspensão e versão de credencial para invalidar tokens antigos.
- **Catálogo:** cadastro/consulta/edição/remoção lógica de itens; tipos produto e serviço; controle de estoque; profissionais de loja; categorias fixas e personalizadas. A remoção usa exclusão lógica segundo a migração `V17`.
- **Lojas:** cadastro, edição por vendedor, aprovação administrativa, suspensão/reativação, comissão, horário semanal e exceções de fechamento/horário especial, localização opcional.
- **Disponibilidade e reservas:** fuso por loja, duração/capacidade, escolha de profissional/primeiro disponível, bloqueio de intervalos conflitantes e regra de pagamento antecipado fora da cidade; regras têm testes unitários dedicados.
- **Pedidos e pagamentos:** criação do pedido no servidor, validação de linhas/preços/estoque, checkout, status, cancelamento de não pagos, solicitações de cancelamento posteriores e tratamento de reembolso.
- **Entrega:** oferta com aceite concorrente, progressão de estados e compartilhamento de localização para entrega atribuída.
- **Administração:** solicitações, cidades/categorias, gerenciamento de contas, lojas, suporte, financeiro, definições, integrações e trilha de eventos administrativos.
- **Suporte e notificações:** tickets com mensagens, status e notificações com alvo de navegação; páginas cliente/admin foram testadas parcialmente.
- **Dados:** PostgreSQL é perfil padrão, Hibernate valida esquema e Flyway aplica migrações. Existem scripts de backup/restauração com checksum e confirmação explícita do banco destino.

### Existe, mas precisa de homologação ou cobertura adicional

- Mercado Pago OAuth/checkout/webhook/estorno, incluindo estados atrasados, assinaturas inválidas, repetição, divergência de valor e saldo insuficiente.
- SMTP e WhatsApp para recuperação de conta; OpenAI para geração de capa; ViaCEP/geocodificação e carregamento de mapa.
- Horários de funcionamento, exceções, seleção de profissional e concorrência já possuem lógica e testes de unidade, mas ainda faltam fluxos de browser e testes contra PostgreSQL para fuso/horário de verão, virada de dia, horário noturno, encerramento antecipado e disputa simultânea.
- “Financeiro” de lojista encaminha o vendedor ao Mercado Pago para saldo/saque. O sistema não recebe senha do provedor nem inicia esse saque.
- A tela do entregador informa corretamente que não há remuneração, saldo devido nem saque Pix implementado. **Repasses de entregadores pela plataforma continuam pendentes**: não há ledger de ganhos nem API de payout.
- Localização por navegador é acionada manualmente, e coordenadas precisas não são persistidas conforme a interface. A experiência depende do consentimento de geolocalização e de serviços de mapa/geocodificação disponíveis.

### Não implementado ou ainda sem evidência suficiente

- Saque Pix iniciado pelo FoodDelivery e ledger imutável de obrigações/repasses a entregadores.
- Conciliação contábil, chargebacks e rotina operacional financeira completa.
- Homologação real de integrações e operação externa de notificações.
- Testes ponta a ponta para cada papel e jornada; matriz ampla de autorização por papel/objeto; acessibilidade automatizada; carga e teste de recuperação real.
- Evidência de retenção/expurgo, exportação/apagamento de dados pessoais, política de privacidade/termos operacionais publicados e resposta a incidente.
- Pipeline de produção auditado, observabilidade, alertas, gestão de segredos e deploy/rollback verificados.

## Achados priorizados

### P1 — corrigir antes de exposição pública

1. **Sessão por cookie foi implementada, mas falta validação em implantação HTTPS real.** Os controles `HttpOnly`, `SameSite=Lax`, `Secure` em produção, CORS de origens exatas e validação de `Origin` reduzem roubo e CSRF. Ainda falta verificar cadeia de proxy/TLS e fluxo de OAuth/retorno Mercado Pago sob os domínios finais.
2. **CSP agora está configurada para a API e o frontend local.** Não há evidência de cabeçalho CSP/HSTS no domínio de produção, pois não há deploy/proxy desta auditoria. As origens externas ainda estão permitidas para mapa/fontes; CSP precisa ser validada na hospedagem final e HSTS deve ficar no terminador TLS.
3. **Rate limit de autenticação é local ao processo.** `AuthenticationRateLimitFilter` armazena janelas em `LinkedHashMap` em memória. Reinício limpa os limites; instâncias múltiplas não compartilham contagem. Adotar limite no gateway/WAF ou armazenamento distribuído, combinar IP e identidade com cuidado e monitorar tentativas.
4. **Validação da matriz de autorização por objeto ainda incompleta como evidência.** Há `@PreAuthorize` em muitos endpoints e verificações de propriedade em partes do fluxo, mas falta suíte sistemática de IDOR: usuário A tenta ler/editar pedido, reserva, item, endereço, mensagem, conta, loja e entrega de usuário B; cada combinação deve responder sem revelar dado. Executar esses testes de API antes de produção.
5. **Pagamentos e estornos não estão homologados nesta revisão.** O código implementa validação de webhook/valor, idempotência de reembolso e estados de pagamento, mas teste unitário não confirma credenciais, split, taxas, OAuth, webhook público HTTPS, repetição, política de cancelamento, contestação e reconciliação com o provedor. Não aceitar dinheiro real antes do roteiro de sandbox e aprovação operacional/contábil.

### P2 — qualidade operacional e proteção de dados

6. **Scanner de imagem/segredos/DAST e atualização contínua ainda ausentes.** Um workflow agora solicita CodeQL, revisão de dependências em PR e SBOM do backend; esses checks ainda precisam executar no repositório conectado e reportar seus resultados. Acrescentar scanner de imagem e segredos, DAST e política de atualização/dependências.
7. **Frontend concentrado num arquivo grande.** `Frontend/src/main.js` agrega estado, navegação, renderização, formulários e ações em um único módulo. Isso eleva risco de regressão, estado implícito e teste frágil. Dividir por domínio (auth, discovery, merchant, admin, support, checkout) e adotar componentes/renderização com contrato claro, preservando o design atual.
8. **Cobertura automatizada desbalanceada.** 54 testes de backend e 31 no frontend são positivos, mas nenhum Playwright/Cypress foi identificado. Os testes front agora estão em `adminAndSupport.test.js` e `apiSession.test.js` e há foco em helpers/regressões; faltam jornadas completas, API com PostgreSQL, browser em desktop/mobile, acessibilidade e visual snapshots com critérios estáveis.
9. **Backup existe, restauração não foi exercitada.** `backup-db.sh` valida leitura do dump e checksum. `restore-db.sh` exige confirmação do banco e substitui dados, mas um restore em banco isolado, validação pós-restore e recuperação de `STORE_ASSETS_DIR` não foram comprovados. Executar ensaio periódico com RPO/RTO definidos e cópia segura de mídia.
10. **Segredos exigem gestão de implantação rigorosa.** `JWT_SECRET` obrigatório e chaves de integração são parâmetros de ambiente, o que evita valores secretos embutidos. Ainda faltam evidências de secret manager, rotação, auditoria, prevenção de vazamento em logs/CI e separação por ambiente. A aplicação deve falhar fechada quando segredos de produção faltarem.
11. **Dados pessoais e retenção:** CPF/endereço/telefone são dados sensíveis; falta revisar minimização, mascaramento, acesso administrativo auditável para leitura/exportação, retenção e expurgo, consentimento/avisos de localização e resposta a incidentes. CPF obrigatório para cliente pode ser regra do negócio, mas deve ser justificado e informado antes do usuário chegar ao checkout.
12. **Mapa/geocodificação dependem de terceiros.** Busca nominal, CEP e mapa são experiências contingentes à disponibilidade do serviço e regras de uso/atribuição do provedor. Validar limites, cache, timeout, respostas ambíguas, município sem CEP e falha de tiles; oferecer escolha manual como fallback.
13. **Observabilidade e execução assíncrona:** confirmar métricas, correlação de requisições, logs estruturados sem PII/token, alertas para pagamentos/estornos/jobs, fila durável e retomada após falha. Jobs periódicos e tentativas não substituem uma fila durável quando o volume crescer.

### P3 — melhoria de UX e manutenção

14. **Separar região de descoberta da localização do endereço de atendimento.** O cliente viu a regra “fora da cidade” com a navegação em Nantes, mas sem cidade/endereço salvo no perfil. Explicar claramente qual endereço/cidade vale para pedido/reserva e permitir corrigir sem perder o fluxo.
15. **Usabilidade de cadastros progressivos:** indicar previamente que CPF e telefone são necessários, destacar o campo pendente, retornar ao checkout após salvar e preservar seleção/sacola; testar cliente sem CPF e telefone.
16. **Acessibilidade:** automatizar WCAG 2.2 AA para contraste, foco, labels, teclado, modais, mensagens de erro, zoom/reflow, áreas de toque e semântica. A revisão visual manual não substitui leitor de tela.
17. **Conteúdo e estados operacionais:** revisar mensagens do português, singular/plural (“1 loja disponíveis” foi observado em conteúdo/categorias), erros com orientação útil, estados carregando/vazio/offline e prevenção de clique duplo.
18. **Compatibilidade visual:** criar matriz mínima 360/390/768/1024/1440 px e zoom 200%, cobrindo login, loja, catálogo, edição, checkout, agenda, suporte, financeiro e definições; há CSS responsivo, mas não há prova para todas as telas.

## Segurança: pontos fortes e risco remanescente

### Controles positivos verificados no código

- Chave JWT é obrigatória e exige ao menos 256 bits; valida emissor, conta ativa, perfis autorizados, versão da credencial e troca obrigatória.
- BCrypt para senha; CORS restrito a origens locais explicitadas; API stateless e autorização default-deny; verificação por role via `@PreAuthorize`.
- `open-in-view` está desativado; erro HTTP não inclui mensagem interna; console H2 só aparece no perfil dev e é desativado em prod.
- Validação de webhook Mercado Pago por HMAC e comparação em tempo constante; validação de dados do pagamento e chave idempotente para estornos conforme implementação.
- Concorrência protegida em caminhos críticos por transação/lock/constraints, incluindo reserva única e aceite de entrega atômico; isso é apoiado por testes específicos.
- Não confiar em headers de proxy no rate limit reduz spoofing simples de IP, mas também torna necessária configuração correta do proxy/gateway quando implantado.

### Ainda precisa evidência ou implementação

- Verificar em HTTPS público a sessão por cookie, CSP aplicada pela hospedagem e HSTS no proxy/CDN.
- Limites distribuídos, bloqueio/alerta contra credential stuffing e teste com reverse proxy real.
- Teste completo de autorização horizontal e vertical em todos os recursos e verbos HTTP.
- Webhook tem assinatura HMAC e consultas ao provedor para confirmar pagamento; repetir notificação é esperado. A assinatura inclui timestamp, mas não há registro durável de `x-request-id` nem reconciliação periódica independente. Adicionar deduplicação/reconciliação se a semântica confirmada pelo Mercado Pago exigir, sem inventar uma janela de expiração que rejeite notificações legítimas atrasadas.
- Executar e configurar alertas do novo workflow no repositório conectado; scanner de imagem/container, segredo e DAST ainda não foram adicionados nem executados.
- Política de dados e processos de LGPD: minimização, finalidade, retenção, exportação, exclusão quando aplicável, base legal e incidentes.

## Banco, arquitetura e operação

- **Pontos fortes:** PostgreSQL é padrão; 22 migrações comuns versionadas no Flyway, além da migração V9 específica para cada dialeto; esquema validado no startup em vez de autoalteração; timestamps/hibernate em UTC; constraints únicas para CPF/e-mail/slots; transações nos serviços de pedido, reserva, pagamento, suporte e reembolso; backup custom format e checksum.
- **Riscos/manutenção:** confirmar migrações de recuperação e cenários de upgrade/reversão em banco de produção clonado; não usar `ddl-auto=update`; configurar pool/timeouts/índices e monitorar queries lentas; versionar e restaurar os arquivos de mídia juntamente com banco; alertar falha do backup; restringir credencial da API a privilégios mínimos.
- **Arquitetura:** monólito modular é razoável no estágio atual e reduz custo operacional. A maior dívida observável é UI monolítica, ausência de cobertura E2E, e integração de serviços externos ainda centralizada sem evidência de fila/eventos duráveis. Não há justificativa imediata para microserviços.
- **Implantação:** a configuração de produção usa PostgreSQL, `JWT_SECRET` e Mercado Pago sem sandbox. Isso é um perfil de configuração, não evidência de deploy seguro. Fazer hardening de TLS, proxy confiável, domínio/CORS, secrets, logs, health/readiness, backup remoto e plano de rollback.

## Pronto / manter sem reescrever agora

- Não substituir o backend modular Spring Boot nem migrar banco por moda: a base atual é apropriada para piloto e tem transações/migrações.
- Não inventar saldo ou saque para entregador; o produto informa claramente que os repasses ainda não existem.
- Não mudar categorias pré-definidas sem necessidade; categorias personalizadas foram separadas da lista fixa.
- Manter horário por loja, exceções de fechamento, fuso, capacidade e bloqueio concorrente; completar testes de integração antes de alterar a regra.
- Manter segredos fora do código e `JWT_SECRET` sem fallback conhecido.
- Manter backup com checksum e confirmação para restauração; adicionar ensaios em ambiente isolado, sem remover essas salvaguardas.
- Manter checagens de estado/ownership no servidor; não depender da UI para autorização.
- Não considerar integração “ativa” apenas porque a variável de ambiente está preenchida; homologar cada provedor e estado operacional.

## Plano recomendado de fechamento

### P0 — antes de abrir produção

1. Validar sessão por cookie, CORS, CSP e HSTS no domínio real HTTPS e no fluxo OAuth de pagamento.
2. Criar suite de autorização por perfil e propriedade, incluindo IDs cruzados, e resolver qualquer leitura/escrita não autorizada.
3. Homologar Mercado Pago em sandbox: OAuth, compra aprovada/recusada/expirada, webhook repetido/fora de ordem/inválido, cancelamento, estorno, saldo insuficiente e conciliação.
4. Executar testes E2E Playwright por cliente, vendedor, entregador e admin em viewport móvel/desktop; não realizar cobrança real.
5. Implantar secret manager/rotação e rate limit no gateway; revisar proxy, HTTPS, CORS e logs sem PII.

### P1 — antes/depois do piloto

6. Ensaiar backup e recuperação PostgreSQL + mídias, medir RPO/RTO e documentar responsáveis.
7. Configurar alertas/logs/métricas para auth, jobs, pagamentos, webhooks, estornos, reserva e entregas.
8. Confirmar execução CodeQL/revisão de dependências/SBOM no GitHub; adicionar scanner de imagem/segredos/DAST e política de atualização de dependências.
9. Concluir checklist LGPD, acessibilidade WCAG e matriz visual/dispositivos.
10. Decidir modelo comercial e operacional de repasse de entregadores; só então construir ledger, titularidade Pix, idempotência e integração de payout.

### P2 — evolução

11. Modularizar frontend, introduzir testes de contrato e componentes acessíveis.
12. Consolidar desenho de fila durável para webhooks, e-mail/WhatsApp e jobs com retries, dead-letter e replay administrativo auditado.
13. Melhorar onboarding para CPF/telefone e comunicação de cidade/localização com fallback manual e preservação de carrinho.

## Resultado geral

**Prós:** base full-stack real; persistência PostgreSQL/Flyway; perfis e autorização no servidor; fluxo de compra/reserva desenhado; práticas úteis de transação/idempotência/concorrência; agenda com horário/fuso/exceções; suporte e administração; testes existentes passam; layout móvel observado sem overflow no fluxo amostrado; documentação do funcionamento e limites financeiros já cobre vários riscos.

**Contras:** grande parte da validação ainda é unitária; fluxo financeiro externo não foi homologado; ausência de E2E e auditoria de acessibilidade; CSP/HSTS ainda não têm evidência no domínio publicado; limitador em memória não escala; saque/repasses de entregadores não existe; recuperação real de desastre e observabilidade não foram provadas; front concentrado em módulo extenso.

**Status:** há um produto de desenvolvimento sólido em evolução, com vários fluxos implementados e testados em nível unitário. Ainda faltam controles e evidências indispensáveis antes de afirmar “sem falhas” ou ativar pagamentos e operação pública. Uma auditoria não pode provar ausência de todo bug; o objetivo é explicitar o que foi realmente verificado e os riscos restantes.

## Referências de código consultadas

- Sessão/cookie no navegador: `Frontend/src/api.js`, `Backend/src/main/java/br/com/fooddelivery/identity/AuthSessionCookie.java` e `Backend/src/main/java/br/com/fooddelivery/config/CookieOrBearerTokenResolver.java`.
- Cabeçalhos, CORS e rotas públicas/privadas: `Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java`.
- Limite de autenticação por processo: `Backend/src/main/java/br/com/fooddelivery/config/AuthenticationRateLimitFilter.java`.
- Assinatura e validação do webhook: `Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoWebhookController.java`.
- Regras financeiras visíveis ao vendedor/entregador: `Frontend/src/main.js` (renderizadores financeiros); limites de repasse registrados em `Documentacao/Funcionamento_do_Projeto.md` e `Documentacao/Modelo_de_Dados.md`.
- Transações, reservas concorrentes, slots, exclusão lógica, horários e auditoria: `Backend/src/main/java/br/com/fooddelivery/` e `Backend/src/main/resources/db/migration-common/`.
- Backup/restauração: `Backend/scripts/backup-db.sh` e `Backend/scripts/restore-db.sh`.
- Testes: `Backend/src/test/java/` e `Frontend/tests/adminAndSupport.test.js`.
