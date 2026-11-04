# Homologação local sem dados reais

Atualizado em 04/11/2026.

## Iniciar

Na raiz do projeto, execute `npm run start:demo`. São necessários Java 21, Maven, Python 3 e Node/npm. A primeira execução pode precisar de internet para baixar dependências Maven. Não é necessário cadastrar chave de API ou editar `.env`.

- Site: http://127.0.0.1:5180
- Caixa de mensagens simuladas: http://127.0.0.1:5180/demo.html
- API local: http://127.0.0.1:8181
- Banco H2 e mídia: `Backend/data/homologacao/`, separados do PostgreSQL e do H2 habitual.
- Encerrar: Ctrl+C no terminal do comando. Os dados de demonstração permanecem para a próxima execução; a chave JWT é nova em cada início, exigindo novo login.

O launcher não carrega `.env` nem herda credenciais/configurações Spring externas. Recusa iniciar se suas portas estiverem ocupadas e não reaproveita serviços existentes. API e frontend escutam apenas em loopback. O perfil não pode ser combinado com `prod`, `postgres` ou `dev-h2`. As sessões usam cookie próprio `fooddelivery_demo_session`, para não substituir a sessão habitual.

Depois de um reinício, um cookie antigo não impede novo login, logout, recuperação ou descoberta pública. As rotas privadas continuam validando a sessão e exigem novo login. Essa correção também se aplica ao ambiente habitual.

## Acessos de demonstração

Senha inicial de todas as contas: `DemoLocal!2026#Teste`.

| Papel | E-mail |
|---|---|
| Administrador | admin@example.test |
| Cliente | cliente@example.test |
| Vendedor | vendedor@example.test |
| Entregador | entregador@example.test |

O seed cria as contas e lojas uma única vez, de forma transacional. Os documentos sintéticos servem apenas para passar pela validação local e não identificam uma pessoa verificada. Não copie dados de clientes ou lojas reais para esse banco. O administrador não tem CPF. O vendedor tem um mercado com produto/estoque e uma barbearia com dois serviços e dois profissionais. Nantes/SP e Iepê/SP permitem testar comportamento dentro e fora da cidade.

## Pagamentos e cancelamentos

Crie um pedido ou uma reserva como cliente. No checkout aparecem **Simular pagamento aprovado** e **Simular pagamento recusado**. Aprovação executa o fluxo de domínio e as notificações reais da aplicação, sem consultar o Mercado Pago. Recusa permite tentar novamente. Aprovação repetida não duplica o evento financeiro. A API verifica o cliente proprietário; conhecer um ID não autoriza pagar outra conta.

Rotas: `POST /api/v1/demo/payments/{id}/approve` e `/decline`, existentes somente no perfil de homologação. Checkout/sync reais, conexão OAuth e webhooks ficam bloqueados nesse perfil. Cancelamentos que exigem estorno passam pelo fluxo normal; o job de estorno confirma um identificador `local-simulator-refund-*` em até aproximadamente 30 segundos, sem enviar transferência.

Esses pagamentos não homologam OAuth, assinaturas de webhook, cobrança, split, conciliação ou estorno do provedor. Esses casos exigem outra instalação com as credenciais e contas de teste oficiais do Mercado Pago.

## E-mail e WhatsApp sem envio

Solicite recuperação de senha por e-mail na tela de acesso. Entre em `demo.html` com o administrador e atualize a caixa: o código aparece ali. As mensagens são limitadas às 100 mais recentes, mantidas em memória e desaparecem ao reiniciar. A rota `GET /api/v1/demo/messages` exige papel administrativo.

Para WhatsApp, primeiro solicite verificação de telefone na conta, leia o código na mesma caixa e confirme-o. Só depois a recuperação por WhatsApp é elegível. A simulação preserva uso único, prazo de validade, bloqueio de reenvio e regras do código; não envia mensagens a números reais.

### SMTP com Mailpit, opcional

Execute `npm run start:demo:mailpit` em vez do comando básico. Requer Docker com Compose; o launcher baixa a imagem versionada e inicia um container exclusivo, sem relay externo. O serviço SMTP escuta em `127.0.0.1:1026`; leia os e-mails em http://127.0.0.1:8026. As mensagens também aparecem na caixa interna. WhatsApp continua apenas na caixa interna. Ctrl+C encerra API/frontend e para o container desse ambiente.

O modo básico não necessita Docker e testa emissão/validação do código. O modo Mailpit também exercita a entrega SMTP. Nenhum dos dois confirma a entregabilidade de um provedor real.

## Capas, CEP e mapa

- O vendedor envia um arquivo PNG/JPG próprio; a prévia é normalizada para 1200 × 600 e passa pelo mesmo processo de aprovação administrativa usado no modo normal. Não há geração por IA nem chamada externa para imagens.
- CEPs de demonstração: `19645-000` (Nantes) e `19640-000` (Iepê). Outros CEPs retornam não encontrado na simulação. Os formulários preenchem Rua de Demonstração/Centro.
- Busca geográfica e clique no mapa usam fixtures locais de Nantes/Iepê. A identificação reversa escolhe uma dessas duas cidades; não equivale a um geocodificador completo. Para testes sem GPS pessoal, use a busca ou as coordenadas simuladas pelo navegador.
- Leaflet, fontes e tiles continuam sendo recursos públicos externos. O ambiente não é totalmente offline; a indisponibilidade desses recursos pode impedir o mapa visual, mantendo os formulários manuais disponíveis.

## Verificação reproduzível

Com a homologação iniciada, execute `npm run test:demo` (ou `python3 scripts/test_homologacao.py`). O roteiro verifica a marca do servidor antes de alterar registros e usa somente as contas fictícias desse ambiente. Cria um pedido e uma reserva e verifica quatro perfis, restrição da caixa de mensagens, cookie isolado, bloqueio de checkout real, simulação aprovada/recusada/repetida, tentativa de pagar pela conta de outro cliente, pedido na fila da loja, recuperação, WhatsApp simulado, geocodificação, upload com aprovação de capa e cancelamento com estorno simulado. Cria também `outro@example.test` para testar propriedade. Cada execução acrescenta registros fictícios; a reserva usa um dia futuro sorteado. Se alterar e-mail ou senha inicial, ajuste o roteiro ou restaure o ambiente de demonstração.

Os limites de autenticação continuam ativos: 10 logins por 15 minutos, 5 requisições de recuperação por hora e intervalo de 60 segundos para reenviar código à mesma conta. Evite executar o roteiro repetidamente em sequência; se reiniciar a API logo depois de solicitar código, espere pelo menos 60 segundos antes de testar recuperação novamente. O banco mantém o intervalo de reenvio mesmo depois de reiniciar. Para simular internet indisponível ou lentidão na interface, use Offline/Throttling nas ferramentas de rede do navegador; isso não altera dados nem credenciais.

Também execute `npm --prefix Frontend run check`, `npm --prefix Frontend test` e a suíte Maven com `JWT_SECRET` efêmero. Não misture o perfil de homologação com a suíte que usa H2 em memória.

## Limites e proteção

Verificação de 04/11/2026: 60 testes Java e 32 testes JavaScript aprovados, 12 módulos JavaScript com sintaxe válida e diff sem erros de whitespace. O roteiro HTTP passou incluindo estorno local e teste de propriedade de pagamento. Mailpit recebeu e-mail por SMTP. No navegador, login, compra fictícia, recusa, aprovação e retorno aos pedidos foram acionados; novo login depois de reiniciar a API também foi confirmado com cookie antigo presente.

- O ambiente contém credenciais conhecidas e códigos de recuperação visíveis ao administrador; deve continuar restrito à máquina local. Não publique esse perfil na internet.
- Não há saldo ou saque de entregador implementado. O modo de testes não inventa uma carteira financeira nem homologa repasses Pix.
- O teste HTTP não substitui revisão visual, acessibilidade, aparelhos reais, carga, recuperação de PostgreSQL, GPS real ou homologação de provedores.
- Para recomeçar, pare o ambiente e arquive a pasta `Backend/data/homologacao/` com outro nome. Ao iniciar, um novo banco de demonstração será criado. Não altere a pasta de dados habitual nem o PostgreSQL.

## Teste específico das capas

Execute `python3 scripts/test_store_covers.py` com a homologação iniciada. O roteiro usa três logins, gera PNG sintético e testa envio, proporção, permissões, prévia privada, substituição de pendência, motivo obrigatório, aprovação única e preservação da capa anterior. Altera somente dados do banco isolado. O teste completo `test_homologacao.py` também envia e aprova capa. Os limites de dez uploads/loja/hora continuam ativos; não executar repetidamente sem considerar a cota. Evidências da implementação atual: [Capas das lojas](Capas_das_lojas.md).
