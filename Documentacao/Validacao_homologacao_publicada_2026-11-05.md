# Validação da homologação publicada — 05 e 06/11/2026

## Ambiente e limites

Destino autorizado: `https://fooddelivery-homologacao.onrender.com`. É um ambiente descartável com dados fictícios, pagamentos simulados e API Java acessível por gateway. O segredo de acesso externo não foi salvo no código, nos relatórios ou no GitHub.

Esta revisão distingue três tipos de evidência:

- **Publicação externa:** acesso HTTP ao serviço implantado.
- **Pacote local:** execução da mesma configuração Docker de homologação, com limite de memória e dados fictícios próprios. A execução final de 06/11 iniciou em 39,3 segundos com 512 MiB e 1 CPU.
- **Interface visual:** somente pode ser confirmada por interação real no navegador. O navegador integrado bloqueou o desafio HTTP Basic nesta sessão; a leitura da API não é apresentada como teste visual.

## Problema reproduzido e correção

O serviço publicado confirmou `mode=homologacao` e `ready=true`. A tentativa de iniciar as jornadas dos quatro perfis retornou `429` no login. A implementação anterior contava todos os acessos de autenticação pelo endereço do peer Java; no pacote publicado, esse peer é o gateway local. Usuários diferentes compartilhavam a mesma cota de dez tentativas.

A implementação atual:

1. Usa o e-mail normalizado para separar a cota estrita de login, recuperação e cadastro. O identificador é mantido como SHA-256 em memória, sem gravar e-mail ou senha na tabela de contagem.
2. Mantém um orçamento adicional por peer para limitar tentativas distribuídas entre muitos identificadores inventados. No login, são dez tentativas por conta e até cem por peer na janela de quinze minutos. Uma conta já bloqueada não consome novamente a cota coletiva.
3. Devolve a cota reservada após um login bem-sucedido. Logins válidos repetidos não esgotam o orçamento de falhas do gateway.
4. Executa a limitação depois da validação do JWT, para vincular operações sensíveis à identidade autenticada. A identidade não é extraída de um token não validado.
5. Ignora cabeçalhos `X-Forwarded-For` e `X-Real-IP` fornecidos pelo visitante. Mudar esses valores não contorna o limite de uma conta.
6. Limita o corpo dos endpoints públicos de autenticação a 8 KiB e preserva os bytes para o controlador validar normalmente. A senha não é extraída nem registrada pelo filtro.
7. Inclui alterações sensíveis por `PUT` e `DELETE`, além de `POST`, na proteção de reautenticação.
8. Preserva `Retry-After` no gateway e mostra ao usuário uma estimativa de espera quando a API retorna `429`.

Uma reserva de cota pertence à janela que a originou. Se a janela expirar durante o processamento, o sucesso de uma requisição antiga não reduz o contador de uma janela nova.

### Limites que continuam importantes

O contador continua local ao processo. Reiniciar a instância o reinicia; várias instâncias não compartilham contadores. O orçamento coletivo por peer ainda é uma proteção conservadora e pode afetar usuários atrás de um gateway sob ataque. Para escalar em produção, é necessário combinar limites de borda e armazenamento compartilhado, com uma política explícita de proxies confiáveis. Esta correção não declara uma solução distribuída nem remove essas limitações.

## Testes locais executados

| Camada | Resultado |
|---|---|
| Backend completo | 130 testes, sem falhas ou erros |
| JavaScript | Sintaxe válida em 23 módulos; 52 testes aprovados |
| Gateway e inicialização | 12 testes aprovados, incluindo preservação de `Retry-After` |
| Detecção de capacidade de segurança | 3 testes aprovados |
| Análise estática Semgrep CE | 192 regras, 206 arquivos, nenhum achado nas regras executadas |
| Pacote Docker, 512 MiB | Quatro perfis, cookie Secure/HttpOnly, bloqueio de origem, logins corretos repetidos e limitação isolada de falhas aprovados por HTTP |

O teste do pacote confirmou dez falhas para uma conta inexistente, bloqueio da tentativa seguinte e login válido de outra conta. Os testes unitários cobrem concorrência, normalização do e-mail, cabeçalhos falsificados, corpo grande e limitação por identidade autenticada.

## Revisão manual da interface local

A aplicação local foi reiniciada em 06/11 para carregar o código corrente. Os testes visuais utilizaram dados fictícios. A largura efetiva registrada no DOM foi de 345 px úteis (janela de celular de 360 px, descontada a barra vertical); nas telas verificadas o conteúdo não excedeu essa largura. A tentativa de alterar para tela larga não mudou a largura efetiva exposta pelo navegador: não é apresentada como prova de desktop.

| Perfil | Ações realizadas e resultado observado |
|---|---|
| Cliente | Login; todas as categorias; barbearia; retorno às categorias; escolha de profissional ou primeiro disponível; seleção de data/horário; pagamento simulado aprovado; reserva confirmada com profissional; solicitação de cancelamento exibida como aguardando resposta. |
| Vendedor | Login; seleção da loja; catálogo e abas em celular; cadastro de item fictício; edição do produto para serviço de 45 minutos; salvamento; remoção recuperável com estado Pausado e botão Reativar. |
| Entregador | Login; entregas e histórico; informações pessoais do cliente ocultas após conclusão; acesso pelo menu ao financeiro; quatro saldos, formulário de senha/código e histórico; solicitação desabilitada sem verificação. Nenhum repasse real foi enviado. |
| Administrador | Login; Definições; cidades com controles de salvar/suspender; perfil com CPF opcional vazio; controles de segundo fator; lista de chamados, abertura e retorno por “← Chamados” à mesma fila. O texto de teste com HTML apareceu como texto, sem execução. |

### Correções de interface verificadas

- O formulário de criação e o botão de edição do catálogo agora têm ligação explícita com seus manipuladores. Antes da correção reproduzida, o formulário fazia navegação nativa e o item não era cadastrado. `catalogBindings.js` centraliza as duas ligações, com teste de regressão e confirmação manual de persistência. A remoção mantém histórico e permite reativação.
- As reservas do cliente agora aparecem em **Em atendimento**, **Próximas reservas** (mais próximas primeiro) e **Histórico**. Os testes cobrem datas vencidas/inválidas e garantem que a lista original não seja alterada.
- O servidor local também envia `Cache-Control: no-store`; a versão do recurso principal foi atualizada para evitar reutilização do JavaScript antigo no próximo carregamento. O gateway publicado herda essa proteção.

Evidências salvas em `Evidencias_homologacao_publicada_2026-11-05/`: `reservas-local-celular.jpg`, `catalogo-local-celular.jpg`, `financeiro-entregador-local-celular.jpg` e `chamados-admin-local-celular.jpg`. Os dois últimos registros são de 06/11; o nome da pasta identifica o início da revisão.

**Limites da revisão:** a lista acima identifica as ações realmente executadas; não equivale a cada botão existente ou a todos os aparelhos e leitores de tela. Ativação/remoção de MFA e alterações de senha não foram feitas pelo navegador. Os contratos financeiros, suporte e expediente também foram exercitados pelas jornadas HTTP descritas abaixo. Falhas de rede reais, permissões de geolocalização e integrações externas exigem avaliação própria antes de produção.

## Roteiro reproduzível na publicação

`scripts/test_published_preview.py` exige o segredo externo por entrada oculta no terminal, confirma o modo de homologação antes de executar jornadas e reutiliza as sessões dos perfis. Recusa outro domínio e não segue redirecionamentos com a credencial de acesso. O relatório registra métodos, rotas e status HTTP; não inclui corpos de requisição, senhas, códigos ou cookies.

```sh
python3 scripts/test_published_preview.py --report /tmp/fooddelivery-published-api-report.json
```

As jornadas existentes abrangem catálogo/tipo/remoção, pedidos, pagamento simulado, reservas, suporte, categorias, cidades, permissões, entrega, remuneração, verificação de repasse, modalidades e alterações de expediente. Após a janela antiga de bloqueio terminar, foi possível executar as cinco jornadas na versão então publicada: todas passaram, em 197 requisições. As sessões foram reutilizadas para não repetir autenticações desnecessárias. Isso valida os contratos exercitados naquele deploy; a nova correção do contador de login foi comprovada localmente e ainda precisa ser implantada no Render. O resultado detalhado, sem credenciais, está em `Evidencias_homologacao_publicada_2026-11-05/resultado-api.json`.

## Próxima aplicação no Render

Depois do envio do commit para `main`, abra o serviço **fooddelivery-homologacao → Manual Deploy → Deploy latest commit**. A publicação apaga os dados fictícios anteriores. Aguarde **Live** antes de repetir o roteiro. Isso mantém a proteção externa e aplica a correção do limite de login.

Pendente nesta etapa: confirmar o comportamento do novo limite de login na próxima implantação. A revisão visual da publicação permanece bloqueada pela autenticação HTTP Basic no navegador de testes; a cópia local permite avaliação independente da interface. Nenhum pagamento, mensagem ou repasse real foi executado.

## Documentação estrutural atualizada

As referências foram regeneradas a partir do código final: 143 arquivos Java com 1.128 funções, 23 módulos JavaScript com 895 funções e inventário de 209 atributos de interface. Cada referência inclui caminho e resumo estrutural; a análise não transforma testes amostrais em garantia de ausência de vulnerabilidades. A revisão do código corrente preserva a necessidade de limites distribuídos em produção, validação real de provedores e testes de acessibilidade com usuários.
