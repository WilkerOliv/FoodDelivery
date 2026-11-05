# Publicação e verificações de segurança

## Inicialização da homologação no Render

O gateway abre `0.0.0.0:$PORT` antes de a API Java concluir a inicialização. A API permanece acessível apenas por loopback, em `127.0.0.1:8181`.

- `/healthz`: responde `503` com `Retry-After: 10` durante a inicialização e `200` somente quando a API informa `mode=homologacao` e `ready=true`.
- Páginas e operações continuam exigindo a senha externa. Um avaliador autenticado recebe uma resposta temporária `503` enquanto o sistema ainda inicia.
- O inicializador aceita até 600 segundos de espera. A carga fictícia completa é obrigatória; abrir uma porta Java não é suficiente para declarar o serviço pronto.
- Falha do processo Java ou esgotamento da espera encerra o pacote. O encerramento libera o servidor HTTP, o processo filho e os arquivos temporários.
- Chamadas internas usam uma conexão HTTP com destino fixo. Não seguem redirecionamentos e não interpretam esquemas de arquivo ou proxies vindos do ambiente.

O log fornecido em 05/11/2026 mostrou uma inicialização Java em aproximadamente 188 segundos, seguida de encerramento por prazo excedido. Isso comprova uma incompatibilidade do prazo de inicialização com a instância usada; o log não comprovou erro de compilação nem falha nas migrações. As 31 migrações foram aplicadas com sucesso naquela execução. O tratamento atual acima cobre inicializações lentas, sem declarar uma instância saudável antes da hora.

## Como publicar a correção

1. Abra o serviço **fooddelivery-homologacao** no Render.
2. Selecione **Manual Deploy → Deploy latest commit**, após a correção estar na branch `main` do GitHub.
3. Aguarde o estado **Live**. Durante o início, respostas `503` do health check são esperadas.
4. Confira a URL HTTPS e a primeira autenticação com `avaliador` e `DEMO_ACCESS_PASSWORD`.
5. Não crie outro serviço nem altere o plano Free para corrigir essa falha de inicialização.

**Manual sync**, na página do Blueprint, sincroniza a definição dos serviços. Para publicar código novo com atualização automática desligada, use o deploy manual dentro do próprio serviço.

## Segurança no GitHub Actions

O fluxo `Security checks` tem cobertura explícita:

| Verificação | Quando executa | Resultado e limite |
|---|---|---|
| Semgrep Community Edition 1.184.0 | Push, pull request e agenda semanal | Regras `p/security-audit` e `p/secrets`; findings ou falha da ferramenta fazem o job falhar. Relatório SARIF disponível nos artefatos da execução, sem depender da aba Code Scanning. Não usa login, token Semgrep ou envio de métricas. |
| SBOM CycloneDX do backend | Push, pull request e agenda semanal | Inventário de dependências; não é, isoladamente, um diagnóstico de vulnerabilidades. |
| CodeQL | A API do GitHub permite consultar análises de Code Scanning e `CODEQL_ENABLED` não é `false` | JavaScript usa `build-mode: none`; Java usa compilação manual com Java 21, no diretório `Backend`. A habilitação em repositório privado depende de uma licença/funcionalidade GitHub já disponível. |
| Dependency Review | Pull request em repositório público ou `DEPENDENCY_REVIEW_ENABLED=true` | Requer funcionalidade habilitada na conta para repositórios privados. Não executa em push comum. |

O usuário confirmou que o repositório deve permanecer **privado**. As consultas de visibilidade variaram durante a investigação, e a API continuou informando Code Scanning indisponível. Por isso, a decisão de executar CodeQL usa a disponibilidade real da API, e não a visibilidade registrada no evento de push. Esta correção não alterou a visibilidade, não contratou serviços e não habilitou serviços pagos. CodeQL e Dependency Review não são apresentados como executados quando estão indisponíveis: seus jobs ficam explicitamente ignorados, e um resumo informa a cobertura. A análise Semgrep continua executando no repositório privado.

A variável `CODEQL_ENABLED=false` desativa explicitamente o job opcional. Definir `true` não contorna a consulta de disponibilidade nem concede licença. Não ative `DEPENDENCY_REVIEW_ENABLED` sem confirmar acesso ao recurso nas configurações do GitHub. A consulta de disponibilidade trata respostas 403/404 como recurso indisponível; erros de autenticação (401), limite de requisições (429), rede e falhas do provedor fazem a verificação falhar, em vez de fingir sucesso. Os dois motores de análise têm coberturas diferentes: Semgrep não equivale a uma prova de ausência de vulnerabilidades ou à execução de CodeQL.

### Escopo da análise gratuita

Inclui `Backend/src/main`, `Frontend/src`, `Frontend/scripts` e `deploy`. A versão da ferramenta está fixada; os pacotes de regras são obtidos do registro do fornecedor. O código é analisado pelo CLI na máquina que executa o job. Sem credenciais de produção, resultados SARIF ficam nos artefatos privados do repositório por 30 dias. Revisão de dependências, testes de autorização, avaliação manual e homologação externa continuam tendo funções próprias.

## Verificações reproduzíveis

```sh
python3 scripts/test_public_preview.py
docker build -f deploy/Dockerfile.homologacao -t fooddelivery-preview:qa .
python3 scripts/test_preview_container.py
python3 scripts/test_preview_container.py --cpus 0.1
```

O último comando aplica uma restrição artificial de CPU para testar uma inicialização mais lenta, mantendo 512 MiB de memória. Não é uma medição contratual dos recursos do Render. Os contêineres de teste são removidos ao terminar. O fluxo `Project quality` inclui os testes de regressão do gateway para evitar a volta de uma declaração prematura de saúde ou da rejeição de inícios acima de 180 segundos.

Resultados locais desta correção: onze testes do gateway/inicializador e três testes da detecção de disponibilidade aprovados; Semgrep executou 192 regras sobre 204 arquivos e não encontrou apontamentos nesse escopo. O pacote também passou nos testes HTTP dos quatro perfis com 512 MiB: iniciou em 19,1 segundos com uma CPU e em 453,3 segundos no ensaio com 0,1 CPU, mantendo saúde `503` durante o início e `200` somente depois de pronto. Os contêineres temporários foram removidos. A implantação externa só pode ser considerada confirmada após o Render mostrar **Live** e a URL responder corretamente.

Referências oficiais verificadas em 05/11/2026:

- https://render.com/docs/web-services — porta pública e binding.
- https://render.com/docs/health-checks — prontidão e prazo de até 15 minutos para novos deploys.
- https://docs.github.com/en/code-security/how-tos/find-and-fix-code-vulnerabilities/manage-your-configuration/codeql-for-compiled-languages — compilação e requisitos do CodeQL.
- https://github.com/semgrep/semgrep — CLI e desativação de métricas.
