# Publicação gratuita de homologação

> [NÃO VERIFICADO] Escopo da revisão de 25/11: instruções e resultados históricos sem evidência individual nesta página aguardam confirmação. Os testes repetidos têm comando e resultado na [matriz de aceite](Aceite_2026-11-25.md).

Este guia descreve exclusivamente a **demonstração descartável**. O `render.yaml` atual corresponde ao [piloto persistente com Supabase](Persistencia_gratuita.md). Para aquele ambiente, siga o guia de persistência; os dados não devem ser tratados como descartáveis.

## Finalidade

Uma demonstração privada, acessível por HTTPS em um subdomínio `onrender.com`, para avaliar cliente, vendedor, entregador e administrador. Não exige comprar domínio. O servidor Java e a interface são publicados juntos em um único serviço Docker gratuito, com API na mesma origem. Não há banco externo nem disco pago neste modelo.

**Não use dados pessoais, imagens privadas, credenciais de integração ou pedidos reais.** O banco H2 e os uploads são temporários. Todo reinício recria a demonstração do zero. O plano gratuito pode suspender o serviço após inatividade; a primeira abertura pode demorar. Não é a configuração de produção nem a homologação do PostgreSQL ou de pagamentos reais.

## 1. Criar a conta

1. Acesse https://dashboard.render.com/ e escolha **Sign Up**.
2. Entre com sua conta do GitHub e confirme seu e-mail, se solicitado.
3. Escolha o workspace gratuito/Hobby. Não selecione uma instância paga.
4. Autorize o Render a ler **somente** o repositório `WilkerOliv/FoodDelivery`.

Não envie senha, código de autenticação ou token pelo chat.

## 2. Disponibilizar os arquivos

O Render precisa receber os arquivos presentes neste checkout:

- `deploy/Dockerfile.homologacao`: compilação Java 21 e execução da interface com gateway.
- `deploy/homologacao_publica.py`: proteção de acesso e inicialização isolada.
- `.dockerignore`: exclusão de arquivos privados e dados locais do contexto de build.

Envie para o GitHub a versão revisada do projeto com esses arquivos. Enquanto estiverem apenas no computador, o Render não conseguirá utilizá-los. A publicação não envia automaticamente alterações locais nem executa `git push`.

## 3. Criar o serviço

1. No painel Render, escolha **New → Web Service** para uma demonstração separada.
2. Conecte o repositório e selecione a branch que contém os arquivos novos.
3. Configure runtime Docker, contexto `.`, Dockerfile `./deploy/Dockerfile.homologacao`, health check `/healthz`, publicação automática desligada e variáveis privadas `DEMO_ACCESS_USER=avaliador` e `DEMO_ACCESS_PASSWORD` com senha aleatória forte. Não reutilize a senha administrativa do site.
4. Confira: **um Web Service**, plano **Free**, sem PostgreSQL externo e sem disco.
5. Confirme a criação. Se o nome do serviço já estiver ocupado, escolha outro; o endereço definitivo é o exibido pelo Render.
6. Aguarde o build e o estado **Live**. O build compila o backend; não é uma comprovação de todos os testes do projeto. A disponibilidade de recursos gratuitos depende da conta e das regras vigentes da plataforma.

Com publicação automática desligada, após revisar novas alterações use **Manual Deploy → Deploy latest commit**. Cada publicação perde os dados da demonstração anterior. Não aplique esse procedimento ao piloto persistente existente como forma de restauração.

## 4. Abrir a demonstração

1. Abra a URL HTTPS exibida pelo Render, com formato `https://nome-do-servico.onrender.com`.
2. O site exibirá a página **Acesso à homologação** (sem a caixa de autenticação do navegador). Preencha:
   - usuário: `avaliador`;
   - senha: no painel do serviço, abra **Environment** e revele `DEMO_ACCESS_PASSWORD`.
3. Guarde essa senha em local privado. Ela é diferente da senha das contas de teste e é gerada pelo Render. Não a coloque no repositório.
4. Depois de entrar, use uma das contas fictícias:

| Perfil | E-mail |
|---|---|
| Administrador | `admin@example.test` |
| Cliente | `cliente@example.test` |
| Vendedor | `vendedor@example.test` |
| Entregador | `entregador@example.test` |

Senha das contas fictícias: `DemoLocal!2026#Teste`. Essa senha conhecida só é aceitável neste ambiente isolado atrás da primeira autenticação. Compartilhe a senha de acesso externo apenas com avaliadores de confiança: eles têm acesso aos dados fictícios e às contas da demonstração.

Para trocar de perfil, use **Sair** dentro do site. O acesso externo usa um cookie protegido por até oito horas; reiniciar o serviço invalida todas essas sessões. Para encerrar imediatamente o acesso externo neste computador, apague os dados deste site no navegador ou use uma janela privada e feche-a ao terminar. Para revogar o acesso compartilhado, altere `DEMO_ACCESS_PASSWORD` (mínimo 24 caracteres) no Render e reinicie/publice novamente.

## 5. Conferência após a publicação

- Sem a primeira senha, `/`, `/demo.html` e os endereços da API devem solicitar autenticação.
- `/healthz` retorna `503` enquanto a API inicia e `200` com `ok` quando os dados fictícios estão prontos, sem expor dados ou credenciais.
- Abra cada um dos quatro perfis e confira navegação e permissões.
- Utilize apenas os simuladores de pagamento e mensagens disponíveis em homologação.
- Cadastre um item fictício e confirme que ele aparece até o próximo reinício.
- Reinicie o serviço e confirme que os dados de teste foram recriados.
- Verifique os logs se a API não iniciar; não copie segredos para issues públicas.

## Isolamento implementado

O processo não lê `.env` nem herda variáveis de banco, SMTP, WhatsApp ou Mercado Pago da hospedagem. Gera chaves temporárias, utiliza banco e uploads em pasta temporária e mantém a API Java apenas em loopback. O gateway exige autenticação em todas as páginas e operações, exceto a verificação de saúde. O cookie da aplicação exige HTTPS. A configuração de integrações reais fica bloqueada no gateway. As proteções existentes do perfil de homologação bloqueiam checkout e webhooks reais. Não há envio de credenciais para o JavaScript.

O serviço usa um limite de memória Java compatível com uma demonstração pequena; é necessário acompanhar o consumo real no Render. Se houver encerramento por falta de memória, reduza o uso simultâneo ou avalie outra hospedagem. Não habilite serviços pagos automaticamente para contornar limites.

A porta pública abre antes de a API terminar de iniciar. Durante esse período, a verificação de saúde responde `503` e o acesso continua protegido. O inicializador aguarda até 600 segundos, exige confirmação de que a carga de dados fictícios terminou e encerra o pacote se a API falhar. O Render decide quando encaminhar tráfego a partir de `/healthz`; não altere essa rota para retornar sucesso durante a inicialização. Em instâncias com pouca CPU, a primeira inicialização pode levar alguns minutos.

## Estado da entrega e limites

Os arquivos e testes locais do gateway estão no repositório. A publicação externa depende de criar a conta, enviar o código revisado ao GitHub e concluir o Blueprint. Nenhuma URL pública foi criada por estes arquivos, e nenhum resultado na infraestrutura do Render é presumido antes da publicação.

Validação local realizada em 05/11/2026:

- `python3 scripts/test_public_preview.py`: 21 testes aprovados, cobrindo autenticação, arquivos privados, diretórios, flag de demonstração, bloqueio de configuração real, rejeição de senha fraca, saúde durante a inicialização, espera acima de 180 segundos, falha do processo e limite de espera e proxy restrito a loopback.
- `docker build -f deploy/Dockerfile.homologacao -t fooddelivery-preview:qa .`: pacote compilado com sucesso.
- `python3 scripts/test_preview_container.py`: pacote iniciado com 512 MiB e uma CPU; página, API, login dos quatro perfis, cookie Secure/HttpOnly e bloqueio de origem não autorizada conferidos por HTTP. O contêiner de teste e seus dados foram removidos ao terminar.

Esses testes não substituem a conferência visual nem a validação após publicar no Render, e não comprovam todos os fluxos de negócio. O uso de memória observado nesta inicialização não garante capacidade sob carga.

Esta modalidade não tem backups duráveis: os dados são descartáveis por definição. Para lançamento comercial, será necessária outra configuração para PostgreSQL persistente, armazenamento de imagens, backups e disponibilidade. Domínio próprio é opcional e pode ser associado posteriormente.

Referências verificadas em 05/11/2026:

- https://render.com/docs/free — limites, suspensão, filesystem temporário e bloqueio de SMTP no plano gratuito.
- https://render.com/docs/blueprint-spec — estrutura e variáveis geradas do Blueprint.

## Entrada compatível com o navegador integrado

A entrada externa agora é um formulário servido pelo próprio gateway, sem JavaScript e sem recursos de terceiros. A senha é enviada por POST à mesma origem HTTPS; não é inserida no endereço, nos logs ou no armazenamento do frontend. O usuário padrão continua `avaliador`, configurável por `DEMO_ACCESS_USER`. Depois da entrada externa aparece o login normal das contas fictícias do aplicativo.

- `login_page`: emite um desafio assinado de dez minutos e seu cookie HttpOnly. O formulário tem rótulos, foco visível, campos adaptáveis à largura e aviso claro de simulação. Pedidos HTML não recebem o desafio Basic que desaparece no navegador integrado.
- `login`: exige a origem HTTPS configurada no serviço, formulário do mesmo site e desafio que corresponda ao cookie; limita o corpo a 4 KiB e rejeita campos repetidos/tipos inesperados. Compara a credencial em tempo constante.
- `signed_token` / `valid_token`: assinam propósito, instante e nonce com HMAC-SHA256 e chave aleatória exclusiva do processo. Token expirado, adulterado, de outro propósito ou de uma instância anterior é recusado. A senha não faz parte do cookie.
- `authorized`: aceita a sessão externa válida ou o acesso Basic dos roteiros HTTP. A sessão não concede um papel no aplicativo: as regras de cliente, vendedor, entregador e administrador continuam no Java.
- `check_gate_credentials`: formulário e clientes Basic compartilham o limite de dez falhas por peer em quinze minutos, com armazenamento em memória limitado. Cabeçalhos de IP fornecidos pelo cliente não são confiados. Atrás de proxy, esse orçamento pode ser compartilhado; esta proteção não é distribuída entre instâncias.
- Cookies de acesso e desafio usam prefixo `__Host-`, `Secure`, `HttpOnly`, `SameSite=Strict`, caminho `/` e nenhum Domain. O acesso expira em oito horas e a sessão é substituída a cada login. Respostas usam `no-store`, `noindex` e `X-Frame-Options: DENY`.

Saúde e o estilo da página de entrada são públicos, sem dados de negócio. API, caixa de mensagens fictícias e arquivos do aplicativo permanecem protegidos. Nenhuma resposta do gateway envia `WWW-Authenticate`, inclusive arquivos secundários como favicon, para não reabrir a caixa nativa. Na API, a expiração retorna 401 com orientação para atualizar a página. Requisições de ferramentas sem preferência por HTML continuam recebendo 401 quando não autenticadas; os roteiros existentes permanecem compatíveis. O HTML público de entrada não significa que a demonstração ficou aberta.

Testes adicionais exercitam autenticação pelo formulário, cookie assinado, credencial incorreta, origem indevida, desafio ausente, expiração, adulteração, reinício, tamanho/tipo do corpo e limite compartilhado com Basic. O contêiner também executa a entrada por formulário e acessa um recurso protegido com o cookie resultante. A conferência manual local confirmou que o botão leva ao login do aplicativo sem abrir a caixa nativa.
