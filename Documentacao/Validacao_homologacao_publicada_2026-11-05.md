# Validação da homologação publicada — 05 e 06/11/2026

## Ambiente e limites

Destino autorizado: `https://fooddelivery-homologacao.onrender.com`. É um ambiente descartável com dados fictícios, pagamentos simulados e API Java acessível por gateway. O segredo de acesso externo não foi salvo no código, nos relatórios ou no GitHub.

Esta revisão distingue três tipos de evidência:

- **Publicação externa:** acesso HTTP ao serviço implantado.
- **Pacote local:** execução da mesma configuração Docker de homologação, com limite de memória e dados fictícios próprios. A execução final de 06/11 iniciou em 39,3 segundos com 512 MiB e 1 CPU.
- **Interface visual:** somente pode ser confirmada por interação real no navegador. O desafio HTTP Basic inicialmente impediu a entrada pelo navegador integrado. Após publicar a página própria de acesso, a entrada externa e os painéis dos quatro perfis foram conferidos na publicação; veja o encerramento abaixo. A leitura da API não é apresentada como teste visual.

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
| Gateway e inicialização | 21 testes aprovados, incluindo formulário protegido, sessão externa e preservação de `Retry-After` |
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

O pacote atualizado já foi publicado e as cinco jornadas passaram novamente em 06/11, em 197 requisições. O arquivo `resultado-api-publicacao-atualizada.json` registra esse resultado. As cinco jornadas também passaram na árvore corrente com banco em memória isolado; a repetição inicial no banco persistente foi interrompida por uma loja que testes anteriores haviam deixado sem retirada, sem alterar esses dados de exploração. O registro independente está em `resultado-jornadas-isoladas.txt`.

A correção adicional da página de entrada foi publicada e conferida no navegador integrado em 06/11, conforme o encerramento abaixo. Nenhum pagamento, mensagem ou repasse real foi executado.

## Documentação estrutural atualizada

As referências foram regeneradas a partir do código final: 143 arquivos Java com 1.128 funções, 23 módulos JavaScript com 895 funções e inventário de 209 atributos de interface. Cada referência inclui caminho e resumo estrutural; a análise não transforma testes amostrais em garantia de ausência de vulnerabilidades. A revisão do código corrente preserva a necessidade de limites distribuídos em produção, validação real de provedores e testes de acessibilidade com usuários.

## Correção adicional: caixa de acesso desaparece no navegador integrado

Após a publicação, o usuário confirmou que a caixa Basic aparecia e sumia em menos de um segundo. A entrada HTML do gateway foi substituída por uma página própria; a autenticação externa continua obrigatória. A implementação, expiração, prevenção de CSRF, limite compartilhado entre formulário/Basic e testes estão explicados em `Publicacao_gratuita_de_homologacao.md`. Nenhuma credencial real foi registrada na documentação ou na captura.

A revisão manual local confirmou: formulário estável, campos editáveis, envio com credencial fictícia e chegada ao login normal do site. A largura efetivamente observada nessa conferência foi de 1280 px sem transbordamento. O navegador não aplicou a nova tentativa de largura menor; não se declara uma segunda prova móvel para esse formulário. Evidência: `acesso-homologacao-local.jpg`.

O resultado dos 21 testes e do pacote atualizado não altera o significado das 197 requisições anteriores: elas validam a publicação anterior à entrada HTML. A alteração adicional do commit `df707c5` foi depois publicada e recebeu a conferência manual descrita abaixo.

## Encerramento da validação do acesso publicado — 06/11/2026

Após o usuário confirmar o novo deploy, o navegador integrado exibiu a página própria **Acesso à homologação**. Os dois campos permaneceram disponíveis para preenchimento. A entrada de avaliador levou ao login do aplicativo, sem a caixa Basic que desaparecia. A senha externa não foi registrada neste documento nem nas capturas.

Conferências manuais na publicação atual:

- Administrador: login, carregamento do painel, restauração das duas sessões após atualizar a página, navegação para Definições e leitura das cidades cadastradas.
- Cliente: login e carregamento da vitrine com Mercado Demonstração e Barbearia Demonstração.
- Vendedor: login, painel da loja, catálogo e abertura do editor com nome, descrição, preço, tipo e estoque; editor fechado sem salvar mudanças.
- Entregador: login e carregamento da área de entregas.
- A saída do aplicativo permitiu trocar de conta sem repetir o acesso de avaliador e sem reabrir a caixa nativa.

Esta etapa encerra a pendência de compatibilidade da entrada externa; não equivale a executar novamente cada operação de negócio pela interface. As cinco jornadas HTTP e suas 197 requisições continuam documentadas separadamente, assim como os testes do gateway. Evidências da publicação: `Evidencias_homologacao_publicada_2026-11-05/admin-publicado-acesso-confirmado.jpg` e `entregador-publicado-acesso-confirmado.jpg`. Ao terminar, a conta de teste do aplicativo foi desconectada; o acesso de avaliador continua limitado pela expiração já documentada.

## Continuação da revisão — 06/11/2026

A revisão complementar está em [Fidelidade da homologação](Fidelidade_da_homologacao.md) e no relatório de [jornadas manuais complementares](Validacao_manual_complementar_2026-11-06.md). A suíte Java foi repetida: 130 testes regulares aprovados, mais três testes com PostgreSQL real. O frontend passou em 57 testes, com sintaxe válida em 25 módulos. O gateway passou em 21 testes e a detecção de capacidade de segurança em três. As referências atuais incluem 25 módulos JavaScript e 897 funções explícitas; os números anteriores neste documento identificam a versão daquela etapa.

### Confirmação manual após deploy de 4c55b8a

O navegador confirmou a versão dos recursos `20261106-delivery-qa-1`. O novo pedido 29D34FF4 passou por recusa/aprovação simuladas, aceite/preparo da loja, oferta, aceite do entregador, coleta, trajeto e entrega. O crédito interno de R$ 5,00 e a ocultação dos dados no histórico do entregador persistiram após recarregamento. O comprador viu Entregue. Os campos de resposta do suporte foram encontrados pelos novos rótulos em ambos os perfis; envio, encerramento e navegação por notificações foram conferidos. O [relatório complementar](Validacao_manual_complementar_2026-11-06.md) registra os passos e as capturas. A pendência de repetição manual dessa correção está encerrada; provedores reais e demais limites de produção permanecem fora dessa evidência.
