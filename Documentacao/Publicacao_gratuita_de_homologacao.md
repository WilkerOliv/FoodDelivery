# Publicação gratuita de homologação

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

- `render.yaml`: serviço gratuito e senha de acesso gerada pela plataforma.
- `deploy/Dockerfile.homologacao`: compilação Java 21 e execução da interface com gateway.
- `deploy/homologacao_publica.py`: proteção de acesso e inicialização isolada.
- `.dockerignore`: exclusão de arquivos privados e dados locais do contexto de build.

Envie para o GitHub a versão revisada do projeto com esses arquivos. Enquanto estiverem apenas no computador, o Render não conseguirá utilizá-los. A publicação não envia automaticamente alterações locais nem executa `git push`.

## 3. Criar o serviço

1. No painel Render, escolha **New → Blueprint**.
2. Conecte o repositório e selecione a branch que contém os arquivos novos.
3. Informe `render.yaml` como caminho do Blueprint, caso seja solicitado.
4. Confira: **um Web Service**, runtime **Docker**, plano **Free**, sem PostgreSQL e sem disco.
5. Confirme a criação. Se o nome do serviço já estiver ocupado, escolha outro; o endereço definitivo é o exibido pelo Render.
6. Aguarde o build e o estado **Live**. O build compila o backend; não é uma comprovação de todos os testes do projeto. A disponibilidade de recursos gratuitos depende da conta e das regras vigentes da plataforma.

O Blueprint não habilita atualização automática: após revisar novas alterações, use **Manual Deploy → Deploy latest commit**. Cada publicação perde os dados da demonstração anterior.

## 4. Abrir a demonstração

1. Abra a URL HTTPS exibida pelo Render, com formato `https://nome-do-servico.onrender.com`.
2. O navegador pedirá uma primeira autenticação:
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

Para trocar de perfil, use **Sair** dentro do site. Para encerrar também a autenticação externa, feche a sessão privada do navegador. Para revogar o acesso compartilhado, altere `DEMO_ACCESS_PASSWORD` (mínimo 24 caracteres) no Render e reinicie/publice novamente.

## 5. Conferência após a publicação

- Sem a primeira senha, `/`, `/demo.html` e os endereços da API devem solicitar autenticação.
- `/healthz` mostra apenas `ok`, sem dados ou credenciais.
- Abra cada um dos quatro perfis e confira navegação e permissões.
- Utilize apenas os simuladores de pagamento e mensagens disponíveis em homologação.
- Cadastre um item fictício e confirme que ele aparece até o próximo reinício.
- Reinicie o serviço e confirme que os dados de teste foram recriados.
- Verifique os logs se a API não iniciar; não copie segredos para issues públicas.

## Isolamento implementado

O processo não lê `.env` nem herda variáveis de banco, SMTP, WhatsApp ou Mercado Pago da hospedagem. Gera chaves temporárias, utiliza banco e uploads em pasta temporária e mantém a API Java apenas em loopback. O gateway exige autenticação em todas as páginas e operações, exceto a verificação de saúde. O cookie da aplicação exige HTTPS. A configuração de integrações reais fica bloqueada no gateway. As proteções existentes do perfil de homologação bloqueiam checkout e webhooks reais. Não há envio de credenciais para o JavaScript.

O serviço usa um limite de memória Java compatível com uma demonstração pequena; é necessário acompanhar o consumo real no Render. Se houver encerramento por falta de memória, reduza o uso simultâneo ou avalie outra hospedagem. Não habilite serviços pagos automaticamente para contornar limites.

## Estado da entrega e limites

Os arquivos e testes locais do gateway estão no repositório. A publicação externa depende de criar a conta, enviar o código revisado ao GitHub e concluir o Blueprint. Nenhuma URL pública foi criada por estes arquivos, e nenhum resultado na infraestrutura do Render é presumido antes da publicação.

Validação local realizada em 05/11/2026:

- `python3 scripts/test_public_preview.py`: cinco testes aprovados, cobrindo autenticação, arquivos privados, diretórios, flag de demonstração, bloqueio de configuração real e rejeição de senha fraca.
- `docker build -f deploy/Dockerfile.homologacao -t fooddelivery-preview:qa .`: pacote compilado com sucesso.
- `python3 scripts/test_preview_container.py`: pacote iniciado com 512 MiB e uma CPU; página, API, login dos quatro perfis, cookie Secure/HttpOnly e bloqueio de origem não autorizada conferidos por HTTP. O contêiner de teste e seus dados foram removidos ao terminar.

Esses testes não substituem a conferência visual nem a validação após publicar no Render, e não comprovam todos os fluxos de negócio. O uso de memória observado nesta inicialização não garante capacidade sob carga.

Esta modalidade não tem backups duráveis: os dados são descartáveis por definição. Para lançamento comercial, será necessária outra configuração para PostgreSQL persistente, armazenamento de imagens, backups e disponibilidade. Domínio próprio é opcional e pode ser associado posteriormente.

Referências verificadas em 05/11/2026:

- https://render.com/docs/free — limites, suspensão, filesystem temporário e bloqueio de SMTP no plano gratuito.
- https://render.com/docs/blueprint-spec — estrutura e variáveis geradas do Blueprint.
