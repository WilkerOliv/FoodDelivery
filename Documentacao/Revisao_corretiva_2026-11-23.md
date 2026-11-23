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
- As pastas de evidências de 15 e 16/11 estão locais e não versionadas. A busca nos arquivos rastreados desta base não encontrou referências textuais a esses nomes. Elas não foram publicadas automaticamente: capturas de configuração podem conter dados privados. Não foram apagadas.
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
