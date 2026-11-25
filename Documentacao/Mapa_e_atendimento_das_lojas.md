# Localização e modalidades de atendimento

> [NÃO VERIFICADO] Escopo da revisão de 25/11: instruções e resultados históricos sem evidência individual nesta página aguardam confirmação. Os testes repetidos têm comando e resultado na [matriz de aceite](Aceite_2026-11-25.md).

Implementação e validação: **05/11/2026**.

## Para o vendedor

Em **Minha loja > Dados da loja**, o bloco **Localização no mapa (opcional)** permite:

1. Abrir o mapa, procurar endereço/cidade e escolher um resultado.
2. Clicar na entrada do comércio ou arrastar o marcador para ajustar o ponto.
3. Salvar o perfil. O ponto só é persistido ao salvar; navegar ou buscar não publica alterações.
4. Remover a marcação e salvar novamente. O endereço escrito é preservado.

O mesmo bloco existe no cadastro de uma nova loja. Latitude e longitude podem ser preenchidas manualmente em uma seção expansível, como alternativa ao uso do mapa. Sem coordenadas, a loja continua no catálogo, desde que aprovada e ativa. Não inventamos uma coordenada a partir do nome da cidade.

**Privacidade:** o bloco informa que o ponto salvo fica público com a publicação da loja. Uma operação exclusivamente delivery pode deixar o mapa sem marcação. O navegador não solicita GPS para cadastrar o comércio; o ponto deve representar a entrada do estabelecimento, não a posição atual do dispositivo.

Busca e marcação não alteram automaticamente endereço, cidade ou UF. Um resultado pode ser apenas o centro de uma cidade; o vendedor deve ajustar o marcador no acesso correto e conferir os dados escritos. A coordenada é uma informação declarada pelo vendedor, não uma comprovação antifraude ou geográfica.

## Bandeiras de atendimento

O bloco **Como sua loja atende?** oferece três modalidades reais já suportadas pela plataforma:

| Opção | Efeito |
|---|---|
| Entrega (delivery) | Permite criar pedidos de produtos para entrega. A taxa definida pela loja continua sendo aplicada. |
| Retirada na loja | Permite pedidos de produtos para retirada pelo cliente. |
| Serviços no local | Permite reservas de serviços presenciais, com duração, profissional e horário conforme as regras da agenda. |

As opções podem ser combinadas. Apenas Entrega selecionada resulta na bandeira **Somente delivery**. Apenas Serviços no local permite reservas, mas não compras de produtos online. É obrigatório manter ao menos uma modalidade; isso é validado no navegador, no domínio Java e no banco.

Desativar uma modalidade impede novas operações nela e novos checkouts correspondentes. Não cancela automaticamente pedidos, reservas ou pagamentos existentes. Compromissos confirmados continuam na operação da loja e exigem comunicação e tratamento pelos fluxos de cancelamento aplicáveis.

Loja com produtos e serviços pode habilitar as três modalidades. O tipo de cada item continua separando produto de serviço; a configuração da loja não transforma um produto em um agendamento. Atendimento de mesa/consumo no salão e prestação de serviço em domicílio não foram adicionados como modalidades fictícias: exigiriam fluxos próprios.

## Para o cliente

- Cartões do catálogo e perfil do comércio apresentam bandeiras textuais, com cores e sem depender apenas de ícones.
- **Ver lojas no mapa** abre os pontos do resultado atual da cidade, busca e categoria. A lista de lojas permanece disponível.
- O marcador abre nome, endereço e modalidades; **Ver loja** abre o catálogo correspondente.
- Há botões com os nomes das mesmas lojas como alternativa ao mapa e para navegação por teclado. O marcador também aceita Enter.
- Loja sem marcação não recebe um ponto presumido. A interface informa quantas lojas têm ou não ponto marcado.
- O perfil da loja oferece seu mapa e o link externo de localização. O link não cadastra o comércio no Google Maps.
- Na sacola, modalidades não oferecidas ficam desativadas. Uma loja exclusivamente delivery já inicia a sacola em Entrega.
- Agendamento presencial desativado impede o botão de reserva; produto sem entrega e sem retirada apresenta indicação de compra online indisponível.

Somente estabelecimentos disponíveis retornados pelo catálogo público entram no mapa. Loja pendente, suspensa ou sem titular habilitado não é publicada para preencher o mapa.

## Administração

**Lojas > Editar dados da loja** contém o mesmo seletor de ponto e as modalidades. A API administrativa mantém autorização ADMIN e auditoria da alteração. Salvar um ponto não aprova ou reativa a loja silenciosamente.

## Código e persistência

| Componente | Responsabilidade |
|---|---|
| `Frontend/src/storeMaps.js` | Carregamento compartilhado do Leaflet, edição opcional, busca explícita, marcadores, popups, lista alternativa e descarte de mapas removidos. |
| `Frontend/src/storeOptions.js` | Campos das modalidades, interpretação das seleções e bandeiras públicas. |
| `Frontend/src/main.js` | Integração nos formulários, catálogo, perfil e sacola; encaminha os valores à API. |
| `Frontend/src/adminStoreEditor.js` | Reutiliza os controles na edição administrativa. |
| `Store` | Configura modalidades e rejeita operações incompatíveis. Coordenadas continuam opcionais e precisam formar um par válido. |
| `CatalogController` / `AdminController` | Persistem ponto e modalidades, respeitando titularidade ou papel administrativo. |
| `OrderService` | Verifica a modalidade antes de reservar estoque ou criar pagamento. Bloqueia a loja durante a criação para serializar operações concorrentes. |
| `ReservationController` | Verifica atendimento presencial antes de reservar um recurso/horário. |
| `MercadoPagoCheckoutService` | Confere novamente as modalidades ao iniciar checkout de pedido ou reserva. |

A migração **V25** adiciona `delivery_enabled`, `pickup_enabled` e `in_person_enabled`, com restrição de ao menos uma opção ativa. Valores iniciais `true` preservam o comportamento anterior das lojas existentes; o vendedor deve ajustar as opções reais. Na requisição, os três campos são Boolean opcionais: omissão mantém a configuração existente; em loja nova vale o padrão compatível. `false` é uma escolha explícita, não deve ser interpretado como campo omitido.

As rotas continuam sendo `POST /api/v1/merchant/stores`, `PUT /api/v1/merchant/stores/{id}` e `PUT /api/v1/admin/stores/{id}/profile`. Respostas de lojas públicas, do titular e administrativas incluem coordenadas e modalidades. Não foi criada uma rota pública que exponha lojas ocultas.

## Segurança, conexão e limites

- Popups e resultados de busca usam elementos DOM e texto; nomes e endereços não são executados como HTML.
- Coordenadas são conferidas na API; o frontend não substitui autorização. Um cliente não pode editar uma loja conhecendo seu identificador.
- A busca usa a rota de geografia existente, com limite de 100 caracteres, cache e controle de acesso ao provedor. Não é disparada uma requisição a cada tecla.
- Leaflet continua na versão existente 1.9.4. As imagens do mapa vêm do OpenStreetMap. A política CSP foi corrigida no HTML e no servidor local para permitir especificamente `tile.openstreetmap.org`, mantendo as outras restrições.
- O mapa é carregado após ação explícita. Sem biblioteca ou conexão, seleção numérica e lista de lojas continuam disponíveis. Falha nos blocos do mapa exibe aviso.
- O desenho dos mapas depende da internet mesmo em homologação; a busca geográfica em homologação usa cidades simuladas. Isso não homologa endereço de comércio real.
- Não há rastreamento contínuo de clientes, publicação automática no Google, cálculo de raio de entrega, rotas, clustering ou verificação de que o endereço pertence ao vendedor.
- Muitos estabelecimentos sobrepostos ou uma cidade com volume alto exigem futura estratégia de agrupamento, paginação e capacidade do provedor. A implementação não certifica escala ilimitada.

## Verificação executada

- Suíte Maven: **81 testes aprovados**, incluindo quatro novos sobre compatibilidade, somente delivery, somente atendimento presencial e rejeição de todas as opções desativadas.
- Frontend: **40 testes aprovados** e sintaxe válida em **17 módulos**.
- `python3 scripts/test_store_location_modes.py`: criação sem ponto, invisibilidade antes da aprovação, aprovação, edição administrativa do ponto, publicação, titularidade, remoção de marcação, rejeição de coordenadas incompletas e de todas as modalidades desativadas. Também verificou retirada/entrega/agendamento incompatíveis e criou/cancelou um pedido permitido sem pagar. Executado somente no banco isolado; a loja adicional de teste foi suspensa ao encerrar e as configurações originais foram restauradas.
- Navegador: vendedor abriu o mapa, buscou Nantes simulada, escolheu o resultado, clicou no mapa, marcou somente delivery e salvou. Cliente abriu o marcador, acessou a loja e adicionou um produto: entrega selecionada e retirada desativada. Não houve compra ou cobrança real.
- Tela de 360 × 800: documento com largura 345 (descontada a barra vertical) e mapa 273,2, sem rolagem horizontal. Os controles de zoom foram posicionados abaixo para não cobrir o popup em telas estreitas.
- Imagens dos blocos conferidas: oito de oito carregadas após corrigir a CSP.

Evidências: [mapa público](Evidencias_mapas_2026-11-05/01-mapa-cliente.png) e [mapa no celular](Evidencias_mapas_2026-11-05/02-mapa-celular.png).

Esses resultados cobrem os cenários identificados; não equivalem a ensaio de carga, homologação de todos os aparelhos ou validação física de endereços.
