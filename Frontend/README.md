# FoodDelivery — interface web

Frontend responsivo em JavaScript modular, HTML e CSS, sem biblioteca de build ou dependências instaladas. A interface consome o backend Spring Boot em `http://127.0.0.1:8080/api/v1`.

## Executar no notebook

Na raiz do projeto, execute `npm start`. O comando inicia PostgreSQL, API e interface juntos. Abra `http://127.0.0.1:5173`.

O backend libera CORS apenas para `localhost:5173` e `127.0.0.1:5173`. Para outro endereço, altere a lista de origens em `Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java`.

## Telas

- Entrar e cadastro progressivo do cliente.
- Descoberta e pesquisa local de lojas/categorias; catálogo de produtos e serviços.
- Seções de restaurantes, mercados, conveniências, lanchonetes, pizzarias, cafeterias, farmácias e serviços.
- Sacola com retirada ou delivery, endereço, taxa fixa de entrega da loja e checkout Mercado Pago.
- Histórico de pedidos, reservas e perfil; observações enviadas no fechamento da compra/reserva.
- Conversas com a loja após a confirmação do pagamento e chat do entregador enquanto ele está a caminho da coleta; o histórico fica disponível após o encerramento.
- Painel de comerciante com criação/edição de loja, catálogo, pedidos e agenda.
- Painel do entregador com ofertas, aceite exclusivo e progresso da entrega.
- Painel administrativo para operadores e aprovação de lojas.
- Administração de contas, suspensão por perfil, redefinição de senha com troca obrigatória e solicitações vinculadas à conta existente.
- Cadastro de vendedores e entregadores sujeito à aprovação administrativa; ferramentas de suspensão e reativação.
- Personalização de capa da loja com prévia de imagem gerada por IA, quando o backend estiver configurado.
- Central de avisos da interface, mensagens de confirmação, erros e estados vazios.

Notificações importantes de pedidos, reservas, pagamentos e aprovações ficam no histórico da conta. Mensagens de confirmação e falha da interface são temporárias. O checkout usa o Mercado Pago; para iniciar cobranças, a conta da loja e as credenciais do marketplace precisam estar configuradas.
