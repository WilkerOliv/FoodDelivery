# Engenharia e avaliação do projeto

Referência: código conferido em 07/11/2026. Leia a matriz de validação para distinguir execução comprovada, análise estática e dependências externas.

## 1. Estrutura e motivo das decisões

O backend é um monólito modular Java 21/Spring Boot. Identidade, catálogo, pedidos, reservas, entrega, pagamentos, suporte e administração são separados por pacote. Spring Data JPA fornece persistência; Flyway controla a evolução do esquema. PostgreSQL é o destino normal e H2 em modo PostgreSQL serve aos testes locais isolados.

Essa estrutura é adequada ao porte atual: pedidos, estoque e pagamentos precisam compartilhar transações, e um serviço único reduz falhas de rede e complexidade de operação. Dividir agora em microsserviços exigiria mensageria, conciliação distribuída e observabilidade sem benefício demonstrado por medição. O aprimoramento recomendado é manter os módulos e extrair serviços de aplicação de controladores grandes conforme os próximos casos forem alterados, com testes de contrato.

O frontend usa módulos JavaScript e renderização de templates. É simples de iniciar e não exige uma cadeia de build para cada mudança. Regras reutilizáveis de máscaras, horários, sessão, categorias, navegação, localização e administração ficam em arquivos próprios. `pageData.js` separa a consulta dos dados da sua aplicação ao estado e `pageLoading.js` controla carregamento, falha, repetição de consulta e descarte de respostas obsoletas. A chave de contexto inclui sessão, papel, página, loja e cidade. O custo atual está no `main.js`: contém muitas telas, estado e ligações de eventos. Uma migração total para outro framework durante uma correção geral acrescentaria risco. A direção de manutenção é extrair uma tela por vez, manter contratos da API e testes de jornadas. Não se deve tratar uma biblioteca visual como garantia de qualidade.

JPA está com `open-in-view=false`: relações necessárias à resposta precisam ser lidas dentro da transação, evitando consultas ocultas durante serialização. DTOs delimitam os dados públicos. Entidades ainda retornadas diretamente precisam continuar sem expor relações/segredos; padronizar todas as respostas com DTO é uma melhoria de manutenção, não uma prova de vulnerabilidade por si só.

### Mapa das responsabilidades

| Camada | Responsabilidade | Regra de manutenção |
|---|---|---|
| Tela e interação | Orientação, entrada, estado visual, feedback | Nunca confiar em botão desabilitado para autorizar operação. |
| Transporte `api.js` | Cookies, HTTP, erros e sessão expirada | Não persistir JWT/perfil em armazenamento acessível a scripts. |
| Controlador | HTTP, validação, identificação e papel | Confirmar propriedade do recurso, além do perfil. |
| Serviço/domínio | Sequência, valores, estoque, horários, transações | Falha deve desfazer efeitos relacionados na mesma transação. |
| Repositório | Consulta, persistência e bloqueio | Consulta privada sempre limitada ao titular/participante. |
| Migração | Colunas, chaves, índices e integridade | Não editar versões aplicadas em ambiente compartilhado. |
| Integração | Provedor externo, timeout e confirmação | Não anunciar sucesso financeiro antes de confirmação confiável. |

## 2. Inicialização e ambientes

`npm start` usa `scripts/dev.py` para coordenar a aplicação normal. A configuração de produção precisa de banco, chave JWT, credenciais administrativas, origens permitidas e TLS. Não copiar valores de demonstração para produção.

`npm run start:demo:mailpit` usa `scripts/homologacao.py`: valida portas, inicia API em 8181 e frontend em 5180, permite somente variáveis herdadas necessárias ao processo, gera chave JWT nova e não carrega `.env`. H2 e arquivos ficam em diretório separado. Mailpit recebe e-mails localmente. Um reinício invalida cookies de demonstração anteriores, por causa da nova chave.

O perfil `homologacao` não pode ser combinado com perfis reais. Checkout, conexão e sincronização financeira externas são bloqueados. Pagamento é uma transição fictícia; imagem é um PNG local; geocodificação usa fixtures. A caixa de mensagens exige ADMIN. Isso permite testar o comportamento da aplicação sem demonstrar que o fornecedor real está funcionando.

## 3. Identidade e perfis

Uma conta pode possuir acessos de cliente, vendedor e entregador. O papel ativo da interface muda os destinos visuais; a API consulta os papéis reais. Cliente não vira vendedor alterando estado JavaScript.

Senhas usam BCrypt. JWT possui emissor, validade e versão de credenciais; a validação consulta se a conta continua ativa e se os papéis e a obrigação de trocar senha correspondem ao token. Suspensão/mudança de credenciais revoga a utilização de uma sessão anterior quando a requisição seguinte é validada.

O cookie é HttpOnly, SameSite=Lax e restrito à API. Em produção, `AUTH_COOKIE_SECURE=true` com HTTPS é obrigatório operacionalmente. Logout remove o cookie. Sessões por cookie são protegidas nas mutações pelo `SessionOriginFilter`, que exige `Origin` da lista permitida. O CSRF padrão do Spring está desativado; portanto essa validação de origem e o cookie não são opcionais. Mudanças de proxy/domínio precisam testar login, logout, cookies e CORS.

CPF identifica cadastros operacionais/clientes, mas não autentica a pessoa nem comprova identidade. ADMIN pode não ter CPF e pode removê-lo explicitamente no perfil administrativo. CNPJ é opcional para loja; um documento informado precisa ser válido/único. O vendedor mantém a vinculação original; o administrador pode corrigi-la por uma ação auditada.

Recuperação guarda hash do código, propósito, canal, validade, tentativas e consumo. Não devolve o código na API comum. Reenvio tem intervalo mínimo. O telefone só fica verificado após código válido; mudar o número ou corrigir o e-mail administrativamente remove os códigos pendentes de verificação e recuperação, para evitar reaproveitamento do código destinado ao contato anterior. As alterações do perfil usam bloqueio da conta.

O MFA administrativo TOTP está implementado: configuração com senha, chave cifrada, confirmação, códigos de recuperação com hash, controle de repetição e revogação de sessões ao ativar/desativar. Sua ativação operacional e guarda dos códigos continuam sob responsabilidade do administrador.

**Limites:** não há prova documental/KYC ou detecção distribuída de fraude. A limitação de autenticação usa identificador normalizado com hash e orçamento adicional por peer; operações sensíveis usam identidade JWT validada. Os contadores continuam locais ao processo; múltiplas réplicas e proxies exigem contadores compartilhados e política de origens confiáveis. Não confiar livremente em `X-Forwarded-For`.

## 4. Catálogo, cidades e publicação

Categorias nativas continuam preservadas. O administrador cria categorias adicionais e só exclui as próprias quando não existem lojas vinculadas. A descoberta usa categorias disponíveis na região e oferece acesso à grade completa e retorno. A pontuação interna não é informação exibida ao visitante.

Uma loja só aparece quando ativa, aprovada e vinculada a vendedor habilitado. Aprovação de cadastro não depende de pagamento. Cidade/UF ausentes, loja suspensa, vendedor suspenso ou filtros de região podem ocultar uma loja legitimamente; a tela de edição precisa orientar sobre esses estados. A comparação de cidade usa normalização de acentos e caixa, incluindo a busca de lojas.

O catálogo permite produto e serviço no mesmo estabelecimento. Produto tem estoque; serviço tem duração, agenda e política de cobrança. Editar permite converter o tipo, respeitando as regras da API. Remover é exclusão lógica: o item desaparece para clientes, mas compras antigas preservam o snapshot e o vendedor pode reativá-lo. Esse desenho evita destruir evidência de transações passadas.

Máscaras transformam digitação em apresentação brasileira, mas a API recebe valores canônicos. Dinheiro é armazenado em centavos inteiros; documentos são normalizados. A máscara não pode ser usada como validação exclusiva. A rotina de moeda é testada com entradas brasileiras.

## 5. Funcionamento e agenda

Cada loja tem fuso IANA, configuração semanal opcional e exceções por data. Dias podem ser alternados; domingo pode fechar em horário diferente. Uma exceção pode fechar o dia inteiro ou estabelecer expediente especial. “Fechar hoje” permite lidar com imprevistos sem reescrever a semana.

O fechamento é exclusivo: uma compra exatamente às 20h não passa quando a loja fecha às 20h. Serviço precisa caber integralmente no expediente. Um período 22h–02h pertence à noite de abertura e à madrugada do dia seguinte, não à madrugada anterior. O frontend fornece feedback; o servidor repete a validação na operação real.

Na reserva, o cliente escolhe profissional ou primeiro disponível. O intervalo usa duração congelada no momento da reserva; editar um serviço depois não muda compromissos existentes. A transação bloqueia a loja para consultar conflito e criar o slot. O recurso é o profissional ou uma chave fixa para a loja quando não existe profissional. Há proteção de duplicidade e validação de sobreposição.

O expediente admite até seis turnos por dia, pausas, 24 horas e exceções datadas. Alterações com impacto exigem prévia e decisão: manter reservas e avisar ou cancelar, liberar slots e solicitar estorno quando aplicável. Atendimentos iniciados são preservados. Reservas futuras independem da abertura no instante da compra. Veja [Funcionamento e reservas](Funcionamento_e_reservas.md).

A disponibilidade semanal e as exceções do profissional estão implementadas com consulta prévia de impacto. A grade lista horários livres combinando expediente da loja, disponibilidade do profissional, duração do serviço, antecedência e ocupação. O cliente escolhe uma pessoa ou o primeiro disponível. A consulta não segura o horário: a API revalida no momento da confirmação. Capacidade maior que um no mesmo recurso não é modelada; profissionais diferentes são recursos separados.

## 6. Compras, estoque e cancelamentos

Criar pedido valida conta, loja, itens, modalidade, horário e quantidades. O preço vem do catálogo do servidor. Linhas, subtotal, taxa e total são snapshots; alterações futuras não reescrevem compras existentes. A quantidade fica reservada no checkout e é liberada ao expirar ou cancelar conforme o estado.

O pedido evolui por estados permitidos. O vendedor aceita, prepara e marca pronto; para retirada, confirma conclusão. Delivery é concluído pela entrega, não antecipadamente pelo vendedor. Mutações do pedido usam bloqueio da linha; estoque, pagamentos, reservas e entregas também têm versões para detectar conflito.

Cliente cancela checkout não pago; depois de pago, solicita cancelamento e a loja decide. Quando permitido, o cancelamento repõe estoque/libera intervalo e solicita estorno. A existência de uma entrega associada é considerada: antes da coleta, ela é cancelada junto; após a coleta, o fluxo comum é bloqueado para atendimento. Isso evita mercadoria em trânsito com pedido cancelado e estoque reposto indevidamente.

**Atenção:** uma transação de banco não torna uma chamada externa atômica. Estorno e confirmação precisam de idempotência, tentativa e conciliação. Cobrir eventos duplicados em teste não garante todas as sequências do provedor.

## 7. Entrega e privacidade

Ofertas expõem identificação da operação e loja, sem endereço do cliente. Aceitar é uma atualização condicional: somente um entregador ganha a tarefa. O pedido deve estar pronto e sem solicitação de cancelamento pendente. Ofertar novamente o mesmo pedido retorna a tarefa existente.

O responsável avança de atribuída para coletada, em trânsito e entregue. O servidor verifica titularidade. Ao concluir, o pedido se torna concluído na mesma transação. Localização só é aceita durante o trajeto; é limpa ao concluir/cancelar. Coordenadas precisam estar nos limites geográficos.

Tarefas encerradas omitem endereço e observação na resposta ao entregador. O histórico de conversa pode continuar contendo texto digitado pelos participantes, inclusive informações pessoais que eles próprios escreveram; a ocultação do campo de endereço não apaga esse histórico. É necessário definir retenção e política de acesso antes de operação pública em escala.

O valor do pedido não é remuneração da entrega. A administração define um valor fixo por entrega, congelado na tarefa; concluir a entrega gera crédito único. A aba financeira mostra acumulado, reservado, pago e disponível. A solicitação exige senha, CPF válido, código de verificação e chave idempotente, reserva saldo e depende de aprovação administrativa. O pagamento é feito externamente e seu comprovante é registrado pela administração; a plataforma não inicia uma transferência Pix. Entregas antigas sem valor registrado não geram remuneração retroativa inventada.

## 8. Dinheiro e integrações

Mercado Pago usa conexão de conta, checkout, webhook, consulta e estorno. Tokens de conexão são criptografados no armazenamento. Assinatura de webhook e confirmação pelo servidor são a fronteira de confiança; um clique na tela não pode marcar dinheiro real como recebido.

Pagamentos tardios de operações canceladas iniciam estorno em vez de ressuscitar a compra. Estornos têm chave persistida para repetição, tentativas e estado de revisão. Expiração de checkout e repetição de estorno são jobs agendados. O administrador tem acompanhamento de casos em revisão.

**Ainda depende de homologação:** OAuth, sandbox do fornecedor, assinatura/URL pública de webhook, recusas reais, reenvio, atraso, saldo insuficiente, chargeback, indisponibilidade e credenciais por estabelecimento. Simulador não substitui isso. Existe conciliação por pagamento: consulta o provedor e confere identificador, referência externa, valor e moeda antes de atualizar estados; divergências ficam para revisão e a operação é auditada. Na homologação retorna SIMULATED sem consultar o provedor. Isso não é conciliação de extrato/liquidação nem razão contábil de dupla entrada, e não executa transferências Pix ao entregador.

SMTP e WhatsApp Cloud têm configuração, mas dependem de credenciais e serviços externos. ViaCEP e geocodificação pública podem falhar ou limitar tráfego. A seleção de uma cidade não deve inventar um CEP único: muitas cidades têm vários CEPs; o ponto/endereço precisa ser revisado. Localização depende de consentimento do navegador e não é prova antifraude. Coordenadas do cliente são manipuláveis e podem estar desatualizadas. A política de pagamento posterior usa a cidade persistida da conta; não promete presença física comprovada.

Não foi adicionada API externa por conveniência durante a revisão: isso exigiria escolher fornecedor, orçamento, limites, retenção de dados e contrato de operação. A infraestrutura de simulação já permite testar sem essas transmissões.

## 9. Suporte, comunicação e notificações

Chamados pertencem ao titular; administração tem fila própria. Resposta, fechamento e reabertura mantêm histórico. O retorno do detalhe administrativo deve ir à lista administrativa. Mensagens têm tamanho limitado e são escapadas na renderização, sem interpretar HTML enviado pelo usuário.

Conversas de pedido/reserva/entrega conferem participação, não apenas papel. Notificações são privadas por conta. Destino é tipo interno e identificador; não uma URL arbitrária fornecida por terceiro. Marcar leitura também confere titularidade. Aviso clicável não dispensa revalidação de permissão no destino.

## 10. Administração e seu limite correto

ADMIN gerencia cidades, categorias adicionais, acessos, suspensão, aprovação, comissão, dados da loja, perfis e atendimento. A seção “Definições” substitui “Minha conta” somente para esse papel. O editor administrativo da loja usa DOM e valores textuais, valida dados e registra evento.

Poder administrativo não significa editar diretamente qualquer campo de estoque, cobrança ou estado para um valor arbitrário: isso poderia quebrar integridade financeira. Mudanças precisam de operação com regras e trilha. Tampouco deve ser possível ler senhas antigas ou receber tokens privados no navegador. O painel permite editar uma lista explícita de configurações de SMTP, WhatsApp e Mercado Pago com reautenticação. Valores são cifrados com AES-GCM e contexto autenticado, e o status retorna presença sem revelar segredos existentes. Chaves mestras e opções fora da lista continuam sob gestão privada do servidor. Presença de configuração não comprova funcionamento do provedor; gravações reais permanecem bloqueadas na demonstração.

A aprovação de solicitação bloqueia primeiro a conta e depois a solicitação, evita sobrescrever perfis em aprovações simultâneas e recusa decisões repetidas. A leitura inicial do titular é uma projeção de UUID, evitando carregar um estado mutável antigo antes do bloqueio.

A trilha V23 registra mutações administrativas instrumentadas. Não é imutável contra administrador do banco e não cobre toda atividade em todos os controladores. Não foi apresentada como auditoria de conformidade completa.

## 11. UI/UX, acessibilidade e responsividade

O objetivo é reduzir dependência de experiência prévia: termos diretos, rótulos visíveis, valor junto da ação, botões com texto, confirmação em ações relevantes, feedback de falha e estado pendente. Cor complementa a indicação textual; não deve ser o único significado. Menus móveis mostram destinos prioritários e opção para os demais.

Os componentes compartilhados definem foco visível, área de toque mínima para controles principais, entrada com fonte legível, quebra de botões em telas pequenas e redução de movimento. Diálogos recebem nome acessível, foco inicial, contenção de Tab, fechamento por Escape e retorno ao botão anterior. O documento de evidências registra as larguras realmente avaliadas.

**Limites:** não foi conduzido estudo com pessoas idosas, teste completo com leitor de tela, certificação WCAG ou matriz de todos os navegadores. Capturas e inspeção DOM não demonstram usabilidade para toda população. Priorizar sessões acompanhadas com tarefas reais, zoom 200%, teclado/leitor e aparelhos físicos antes de expandir operação.

## 12. Segurança, consistência e operação

| Área | Proteção atual | Risco que permanece / próxima ação |
|---|---|---|
| Autorização | Papel + titularidade/participação em operações privadas | Ampliar matriz HTTP a cada nova rota, incluindo abuso de campo e concorrência. |
| Autenticação | BCrypt, JWT validado contra conta, versão de credencial | Ativação operacional do MFA e limitação compartilhada antes de múltiplas réplicas. |
| CSRF/CORS | Origem obrigatória nas mutações com cookie e lista explícita | Revalidar no domínio/proxy final; TLS e cookie Secure. |
| XSS | Escape de dados nas telas; editor DOM | Templates HTML grandes exigem revisão de cada interpolação; teste de payload não prova ausência de todos os sinks. |
| Entrada HTTP | Bean Validation, limites de texto/lista e 400 para payload/UUID inválidos | Testar limites de tamanho no proxy e upload; orçamento de recursos sob carga. |
| Concorrência | Locks de conta/loja/pedido e versões de entidades | H2 não demonstra comportamento completo de locks/deadlocks PostgreSQL. |
| Recuperação | Expiração, idempotência, retries, rollback de banco | Backup de banco + arquivos, restauração isolada, RPO/RTO e alerta real. |
| Segredos | Variáveis privadas, tokens cifrados, ausência de JWT no storage JS | Cofre/rotação, permissões de serviço, proteção de backups e logs. |
| Privacidade | Acesso privado e ocultação de endereço de entrega encerrada | Retenção, exclusão/anonimização e histórico de mensagens precisam de política operacional. |
| Dependências | Versões declaradas e workflows de qualidade/segurança existentes | Executar e acompanhar alertas; workflow no repositório não significa scan concluído. |

`ApiExceptionHandler` converte erros de domínio e concorrência em respostas controladas; entrada JSON/UUID inválida retorna 400. Não devolver stack trace ao cliente. Logs precisam de identificador de correlação sem senha, documento, código ou token.

Scripts de backup usam dump PostgreSQL e checksum, com confirmação explícita de banco no restore. O ensaio PostgreSQL descartável foi executado localmente e na CI; ele não comprova recuperação completa de produção. Também é necessário guardar arquivos de capa, configuração segura e chaves de criptografia de tokens; dump sozinho não recupera tudo. Não foi restaurado o banco real nesta revisão.

## 13. Avaliação crítica e ordem de entrega

**Pontos fortes:** módulos de domínio, snapshots financeiros, dinheiro em centavos, migrações, sessões sem JWT no storage, controle de propriedade, modelo de profissional, exceções de expediente, exclusão lógica, simulação isolada e teste reproduzível.

**Custos atuais:** frontend central grande, controladores com regras e DTOs misturados, consultas/listas sem paginação em vários domínios, polling, integrações externas não homologadas e pouca evidência operacional de escala. Padronizar estilo e extrair módulos reduz risco de manutenção mais do que adicionar plugins sem necessidade.

Prioridades antes de cobrar publicamente:

1. Homologar pagamentos/estornos e reconciliação com o provedor, usando contas de teste e webhook público controlado.
2. Homologar a rotina operacional de pagamento externo dos repasses, conferência de titular e comprovante; a remuneração fixa e o fluxo de aprovação já existem, mas não comprovam transferência real.
3. Manter os testes PostgreSQL concorrentes e o ensaio de dump/restauração descartável já executados. Completar recuperação conjunta de banco, arquivos e chaves com destino externo, retenção e RPO/RTO definidos.
4. Publicar em HTTPS, cookie Secure, origens finais e proxy configurado; validar limites, logs, alertas e segredos.
5. Executar varredura de dependências/segurança, análise independente de autorização e testes de carga.
6. Completar trilha administrativa, política de retenção, termos e privacidade com regras operacionais efetivamente adotadas.
7. Extrair telas do `main.js`, paginar listas e medir consultas antes de otimizar sem evidência.
8. Realizar teste assistido com pessoas de pouca familiaridade digital e leitor de tela/aparelhos reais.

Não há base técnica para afirmar “sem margem de erro” ou segurança perfeita. A entrega fornece código corrigido, invariantes explícitas e evidências reproduzíveis. Uma função descrita não deve ser tratada como validada em todas as condições só por constar nesta documentação.

## Capa do comércio e aprovação

A capa é enviada pelo vendedor, sem geração por IA ou chave externa. O navegador normaliza foto/logo para 1200 × 600 (2:1), preservando proporção. A API confere o PNG, tamanho e dimensões e o recodifica. Uma solicitação privada aguarda decisão ADMIN; somente aprovação altera a referência pública. Recusa requer motivo; novo envio substitui a pendência anterior. Locks na loja e na solicitação impedem aprovar silenciosamente uma versão já substituída. O limite de dez envios/loja/hora usa o banco e a mesma trava da operação. Detalhes, contratos, preservação de capas antigas e obrigações de armazenamento: [Capas das lojas](Capas_das_lojas.md).
