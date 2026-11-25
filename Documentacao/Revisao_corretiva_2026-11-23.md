# Revisão corretiva — 23/11/2026

Base conferida: `main` em `15e652f`. Esta revisão confronta os apontamentos do documento “Correções necessárias — FoodDelivery” com código e testes. Alterações em branch de revisão não significam publicação no Render.

## Webhook financeiro

O webhook consulta o recurso no Mercado Pago e usa a mesma validação financeira da conciliação antes de qualquer transição. Confere identificador externo, referência local, moeda BRL, valor exato em centavos e associação anterior ao provedor. Para estorno integral, exige também valor integral efetivamente estornado. Resposta ausente, valor inválido, fracionamento inferior a um centavo e estouro numérico são rejeitados com HTTP 409, sem alteração financeira. Indisponibilidade da consulta externa continua retornando 502, permitindo nova tentativa.

A assinatura HMAC continua obrigatória fora do simulador explicitamente configurado. Seu timestamp deve estar dentro de cinco minutos do relógio do servidor, inclusive para datas futuras. São aceitos segundos (10 dígitos) e milissegundos (13 dígitos), mantendo a representação original no cálculo da assinatura. Assinaturas expiradas retornam 401 antes de acesso ao repositório. Essa janela reduz replay; não substitui idempotência, conciliação nem teste das retentativas do fornecedor. Relógios dos servidores precisam estar sincronizados.

Referências oficiais consultadas: [validação de webhook do SDK](https://github.com/mercadopago/sdk-nodejs/blob/master/docs/webhooks.md) e [formato da assinatura](https://www.mercadopago.com.br/developers/pt/docs/wallet-connect/notifications). A política de cinco minutos é uma decisão local baseada no exemplo do SDK, não uma garantia de prazo de entrega do fornecedor.

Testes `MercadoPagoWebhookTest` cobrem janela temporal, unidades, assinatura criptograficamente correta porém expirada, valores nulos/inválidos/fracionados/excessivos/divergentes, ID divergente, estorno parcial e aprovação válida. As respostas externas são simuladas; isso não é homologação do Mercado Pago.

## Compatibilidade da migração V9

`V9MigrationEquivalenceIT`, executado por `scripts/test_postgres.py`, aplica as duas variantes em bancos descartáveis. Compara nomes/tipos/tamanhos/nulabilidade de colunas e testa backfill do papel existente, defaults, CPF único com vários valores nulos, papéis permitidos e proibição de papel concedido nulo.

A diferença entre índice parcial PostgreSQL e índice comum H2 é intencional. Os dois preservam a regra de CPF único opcional nos casos testados. Nenhuma migração já aplicada foi reescrita e nenhum checksum publicado foi alterado. O teste protege a V9, não certifica equivalência completa de todos os recursos dos dois bancos.

## Dependências

A análise de vulnerabilidades passa a consumir o SBOM Maven resolvido no workflow `Security checks`, em push, pull request e execução semanal. Usa Trivy com ação fixada por SHA e versão explícita do executável. Falha para alertas HIGH/CRITICAL, inclusive sem correção disponível; falha de download/análise também não é tratada como sucesso. O relatório JSON fica como artefato por 30 dias. O job não depende de Code Scanning pago nem recebe os segredos da aplicação.

A primeira execução local encontrou 32 alertas HIGH nas dependências da base. Atualizações compatíveis nesta branch: Spring Boot 3.5.16, Jackson BOM 2.21.8 e Tomcat 10.1.62, mantendo Java 21. Os overrides devem ser revistos quando a plataforma gerenciada for atualizada; não são versões fixadas indefinidamente.

## Apontamentos que não justificam exclusão de arquivos

- `Backend/Dockerfile` já usa Java 21 nas duas etapas. É usado pelo `deploy/compose.production.yaml`; removê-lo quebraria esse caminho de instalação.
- As capturas locais de configuração de 15 e 16/11, sem referências no código e não versionadas, foram removidas na limpeza autorizada de 24/11. Não foram publicadas: capturas de configuração podem conter dados privados. Os procedimentos atuais permanecem no guia de recuperação e persistência.
- Geração de capas por IA, Pix automático, cadastro automático no Google Maps e aplicativo nativo não são entregas atuais.

## Pendências que permanecem

| Área | Evidência necessária para concluir |
|---|---|
| Mercado Pago | Sandbox real: OAuth, assinatura, entrega atrasada, duplicidade, recusa, estorno e conciliação; testes locais não substituem fornecedor. |
| Backup externo real | Chave de recuperação guardada separadamente, segredos de automação, primeira cópia cifrada e restauração conjunta de banco/imagens/chaves com RPO/RTO medidos. O ensaio desta revisão é descartável. |
| Operação | MFA do administrador real, entrega de alerta, domínio/origens e cookies conferidos na publicação atual. |
| SMTP | Entrega e recuperação de conta com o provedor escolhido. WhatsApp pode permanecer desligado. |
| Proxy/rate limit | Demonstrar diferenciação de clientes reais no Render sem confiar em cabeçalhos forjados. Contador compartilhado antes de múltiplas réplicas. |
| Banco | Separar credenciais de execução/migração; não mudar privilégios remotamente sem ensaio e plano de recuperação. |
| Retenção | Definir política de auditoria/mensagens e preservar evidência financeira. |
| Aceite publicado | Jornada de capa com aprovação, sessão após deploy e revisão autenticada da versão final. |
| Acessibilidade | Teclado, zoom, leitor de tela, aparelhos físicos e tarefas acompanhadas. |
| Manutenção frontend | Continuar extração de responsabilidades de `main.js`, protegendo os fluxos com testes. |

Não houve alteração de credenciais, banco real, cobranças ou configurações do Render nesta revisão.

## Resultado final dos testes desta revisão

- `mvn -f Backend/pom.xml clean test org.cyclonedx:cyclonedx-maven-plugin:2.9.1:makeAggregateBom`: aprovado com as novas versões.
- `python3 scripts/test_postgres.py`: aprovado após a atualização; inclui concorrência, V9 entre dialetos, dump/restauração e rollback de restauração interrompida. Total dos relatórios JUnit: **153 testes, zero falhas, zero erros, zero ignorados**.
- Trivy 0.69.3 com base de vulnerabilidades obtida em 23/11: **32 → 2 alertas HIGH**, zero CRITICAL no recorte. A execução final retorna **1**, como esperado pela política de bloqueio. Não houve supressão nem `continue-on-error`.
- Permanecem `CVE-2026-47884` e `CVE-2026-47890` em `spring-webmvc:6.2.19`. Os avisos oficiais exigem, respectivamente, XsltView com mapeamento amplo e fragmentos de views enviados por SSE. A busca no código principal não encontrou `XsltView`, `ViewResolver`, `ModelAndView`, `FragmentsRendering` ou `SseEmitter`. Isso é evidência de ausência dos caminhos conhecidos no código, não remoção das falhas da biblioteca.
- A correção aberta indicada pelo fornecedor é Spring Framework 7.0.9 ou posterior; a correção 6.2.20 exige suporte Enterprise. Migrar para Spring Boot 4/Framework 7 requer revisão específica de compatibilidade, JSON, segurança e publicação. Não misturar Framework 7 isoladamente com Boot 3.5.
- Fontes: [CVE-2026-47884](https://spring.io/security/cve-2026-47884/) e [CVE-2026-47890](https://spring.io/security/cve-2026-47890/). O fornecedor classifica a primeira como MEDIUM e a segunda como LOW; o scanner usado as apresenta como HIGH. Ambas ficam visíveis, sem reclassificação manual para contornar o bloqueio.

Esta branch deve permanecer em revisão enquanto o bloqueio de dependências não for resolvido ou houver uma avaliação formal de aplicabilidade com evidência e prazo de revisão. Não foi feito merge nem deploy nesta etapa.

## Continuação local

O piloto aceita uma conta de execução dedicada. O procedimento de troca e
reversão está em [Banco de produção](Recuperacao_e_banco_de_producao.md).
O teste do contêiner agora troca a conta após a primeira inicialização e
revalida a sessão depois de recriar a API.

Em instalações PostgreSQL, a migração V32 cria a tabela de contadores.
Para compartilhar o limite entre processos, configure
`APP_SECURITY_SHARED_RATE_LIMIT=true` em **todas** as réplicas. O padrão
continua sendo memória local. As chaves persistidas são hashes, as janelas
usam o relógio do banco e o tráfego aciona a limpeza das expiradas.
Uma falha de banco interrompe a operação protegida. Antes de escalar,
execute `scripts/test_postgres.py` e confirme a identificação do cliente
no proxy real. Ao trocar o modo de contagem, as janelas do modo anterior
deixam de participar do limite.

A marcação da navegação e do cabeçalho saiu de `main.js` para
`views/shellView.js`. A ligação de eventos continua no controlador.

O workflow **Disponibilidade do piloto** permite uma consulta manual a
`/healthz`. Com a variável de repositório `MONITOR_PILOT=true` e o workflow
na branch padrão, o Actions agenda duas consultas por hora. Três tentativas
sem resposta JSON com `status=UP` fazem o job falhar. A entrega depende das
preferências de notificação de falhas do responsável no GitHub. Registrar um
teste de recebimento antes de considerar o alerta operacional.

Validação desta continuação: 149 testes Maven aprovados, suíte do frontend
aprovada e 43 módulos com sintaxe e imports conferidos. O HTML produzido
pelo cabeçalho coincidiu em 16 comparações com a versão anterior. Passaram
três testes de configuração do piloto e nove testes dos scripts de backup.
Um PostgreSQL 17 descartável, em modo de usuário único, executou os scripts
V1–V32, as verificações de privilégios e os cenários sequenciais de limite,
expiração e devolução de crédito. O ensaio JDBC concorrente e o contêiner
TLS dependem da execução em um ambiente com sockets e Docker disponíveis.
A suíte de backup externo cifrado ficou bloqueada pela ausência de
`age-keygen`. A publicação, os provedores e a entrega do alerta permanecem
sem validação nesta etapa.

## Verificação para integração — 24/11/2026

A revisão local foi retomada com Docker e sockets disponíveis. O teste JDBC
concorrente do contador compartilhado passou: dois objetos independentes disputam
o mesmo orçamento no PostgreSQL, a janela expira e uma devolução antiga não credita
a janela nova. Isso não demonstra ainda a identificação de visitantes no Render.

Resultados locais: 154 testes JUnit sem falhas/erros, suíte frontend aprovada,
43 módulos JavaScript verificados, três testes de configuração aprovados.
O ensaio PostgreSQL inclui migrações, equivalência V9, concorrência, restauração
e rollback. A ferramenta `age` do repositório Ubuntu foi extraída em diretório
temporário para os testes, sem criar chave de recuperação de produção.

A execução real dos testes cifrados encontrou um problema no encadeamento
`tar | grep -q` com `pipefail`: SIGPIPE ou falha da listagem podia mascarar a
identificação de entrada insegura. O restore agora materializa as listagens,
verifica seus códigos de saída e só depois inspeciona nomes e tipos. A opção
que preserva nomes absolutos é usada apenas ao listar, nunca ao extrair.
Passaram 17 testes de scripts de backup, incluindo arquivos grandes com symlink,
caminho absoluto e `..`, recusados antes de executar `pg_restore`.

A `main` remota permanece em `15e652f`. O PR #2 já tinha reprovação em análise
de dependências e dependency-review. O pedido de integrar somente se não houver
pendências não autoriza ignorar essas reprovações. As mudanças são enviadas à
branch de revisão; integração e publicação continuam pendentes. A política de
retenção de mensagens também continua sem definição; nenhum expurgo foi ativado.

O ensaio `scripts/test_persistent_deploy.py --container` também passou em 24/11:
PostgreSQL com certificado TLS verificado, aplicação sem privilégios extras,
contadores compartilhados, migração para a conta `fooddelivery_runtime`, bloqueio
de criação no schema/escrita no Flyway/exclusão de auditoria, preservação da conta
e da sessão após recriar a API e recuperação após parar/reiniciar o banco.
Todos os recursos usados eram descartáveis e foram removidos pelo teste.
Isso encerra as restrições locais de execução anteriormente descritas; não
substitui a troca de credenciais nem o aceite no ambiente publicado.
