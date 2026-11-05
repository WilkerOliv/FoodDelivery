# Guia de atividades e comportamento esperado

Data: 04/11/2026. Consulte a referência técnica para assinaturas e a matriz para os cenários realmente testados.

## Entrada e conta

1. Entre com e-mail e senha. “Saiba mais” explica a proposta do serviço.
2. “Criar conta” pede os dados definidos para cadastro. Um CPF digitado corretamente não prova identidade; não usar documentos de pessoas reais no ambiente fictício.
3. “Esqueceu sua senha?” inicia recuperação pelo canal disponível. A mensagem pública não deve revelar se uma conta existe. É necessário configurar o canal em produção; em homologação, ADMIN consulta a caixa de mensagens.
4. “Minha conta” reúne dados, endereços e solicitações de outros perfis. Mudar telefone exige nova verificação. CPF de cliente/profissional não é um campo livre para criar várias identidades.
5. “Sair da conta” pede confirmação e limpa a sessão. Expiração ou mudança de credenciais exige entrar novamente.

## Cliente: região e descoberta

- O botão de cidade abre busca por nome, CEP, localização e mapa. GPS só é consultado mediante ação e permissão do navegador. Negar a permissão deve preservar a alternativa manual.
- Confira cidade e UF antes de aplicar. CEP pode faltar no resultado; cidade pode ter vários CEPs. Selecionar região não altera automaticamente a residência verificada da conta.
- Categorias em destaque são cartões com nome, ícone e quantidade. “Todas as categorias” abre a grade; escolher uma abre os estabelecimentos daquele tipo; “Todas as categorias” permite voltar sem reiniciar a navegação.
- Sem lojas na região, a interface apresenta estado vazio e permite mudar cidade. Não deve inventar comércio nem exibir loja suspensa para preencher a tela.
- Busca e categoria são filtros de descoberta, não permissões. A API verifica disponibilidade outra vez ao operar.

## Cliente: produto e compra

1. Abra um estabelecimento e veja opções/preços.
2. Produto disponível pode ser incluído na sacola. Estoque e preço finais são conferidos pelo servidor.
3. Escolha retirada ou entrega quando disponível. Entrega exige endereço; a taxa da loja é somada ao total.
4. Confira itens, quantidade, total e observação. Perfil incompleto deve ser corrigido antes de finalizar.
5. Continue para pagamento. Em homologação existem botões explícitos para simular aprovação/recusa; não há cartão nem dinheiro real.
6. Acompanhe em “Meus pedidos”. A compra só avança após confirmação financeira, salvo regra específica de reserva com pagamento posterior.
7. Antes de pagar, cancelar libera checkout/estoque. Após pagar, solicitar cancelamento envia decisão à loja. Um pedido coletado não aceita cancelamento comum; fale com atendimento.
8. “Mensagens” abre conversa da operação. Ter o link não dá acesso a outra pessoa.

## Cliente: serviço e reserva

1. Abra um item de serviço e escolha “Reservar”.
2. Escolha um profissional ou “Primeiro disponível”, quando a loja tem equipe cadastrada.
3. Selecione data/hora no fuso informado. O intervalo inteiro precisa caber no expediente e não coincidir com outro atendimento do recurso.
4. Observe a política de cobrança. Serviço pode permitir pagar à loja depois do atendimento; fora da cidade persistida da loja/cliente, pagamento antecipado é exigido pela API.
5. A reserva online depende de pagamento aprovado. Reserva com cobrança posterior é confirmada sem pagamento online e mantém essa indicação.
6. Acompanhe em “Reservas”. Cancelamento pago segue decisão da loja e estorno; compromisso não pago pode liberar o horário de acordo com seu estado.

Não confundir escolher uma cidade no mapa com comprovar presença física. Não há detecção infalível de localização/fraude.

## Vendedor: lojas e catálogo

- “Minha loja” permite alternar o estabelecimento quando o titular possui mais de um. Confira o seletor “Loja em edição” antes de salvar.
- “Resumo” informa publicação e próximos passos. A aprovação administrativa não cobra uma taxa de aprovação.
- “Dados da loja” edita nome, categoria, endereço, região, descrição, fuso, ponto opcional no mapa, modalidades de atendimento e taxa. CNPJ é opcional no cadastro; correção posterior de documento vinculado cabe ao administrador.
- “Adicionar outra loja” cria estabelecimento separado com análise própria. Não é a edição da loja atual.
- “Catálogo” cadastra produto ou serviço. Produto usa estoque. Serviço usa duração e cobrança antecipada/posterior. A escolha de cobrança posterior exige atenção ao risco de não comparecimento/inadimplência.
- “Editar” permite mudar os campos do item, inclusive tipo, duração e cobrança. “Remover” pausa a publicação preservando histórico; “Reativar” devolve à vitrine quando as demais condições forem atendidas.
- Itens antigos em pedidos/reservas mantêm preço/nome/duração congelados: editar catálogo não muda acordos existentes.
- “Aparência e pagamentos” reúne capa e conexão financeira. Escolha foto ou logo, ajuste o enquadramento 2:1 e envie para análise. A capa anterior continua visível; só ADMIN publica a nova imagem. Recusas mostram o motivo e permitem novo envio. O ambiente fictício bloqueia conexão financeira real.

## Vendedor: expediente e exceções

1. Em “Dados da loja”, abra “Funcionamento e exceções”.
2. Ative “Usar horário de funcionamento” se quiser restrição. Sem ativação, a semana não limita operações, mas exceções datadas ainda são consideradas.
3. Marque somente os dias em que abre e defina início/fim de cada um. É possível segunda/quarta/sexta, só um dia ou toda semana com domingo diferente.
4. Salve funcionamento. O servidor verifica compras e o intervalo das reservas.
5. Para imprevisto, use “Fechar hoje” ou escolha uma data e salve exceção de dia fechado/horário especial.
6. Se houver reservas afetadas, revise a prévia e escolha manter e avisar, ou cancelar e avisar. O cancelamento libera a agenda e solicita estorno de pagamentos aprovados; atendimentos em andamento são preservados.

Cada dia aceita até seis turnos, pausas e a opção 24 horas. A rotina individual por profissional ainda não é modelada. Consulte [Funcionamento e reservas](Funcionamento_e_reservas.md) para regras e testes.

## Vendedor: operação

- “Pedidos da loja” mostra itens, modalidade, observação, valor, estado e ações permitidas. Aceite, prepare e marque pronto na sequência.
- Em retirada, finalize a entrega ao cliente. Em delivery, oferte a tarefa e o entregador conclui após o trajeto.
- Solicitações de cancelamento precisam de decisão. Aceitar quando permitido libera estoque/horário e solicita estorno; não prometer crédito instantâneo.
- “Agenda” acompanha reservas e profissionais. Preserve o profissional correto; duração e conflito são avaliados pelo servidor.
- “Financeiro” do vendedor orienta sobre conexão e recebimento pelo provedor. Não confundir total vendido com saldo já disponível para saque.

## Entregador

1. “Entregas” mostra ofertas sem endereço privado. Aceitar vincula a tarefa à conta; outro entregador não consegue aceitar a mesma oferta.
2. Depois do aceite, consulte o endereço/observação necessários ao serviço.
3. Marque coletado, inicie trajeto e marque entregue somente na etapa correspondente. Não é possível pular a sequência.
4. Compartilhamento de localização só funciona durante o trajeto e mediante permissão. O sistema limpa as coordenadas ao encerrar.
5. Ao concluir, pedido e entrega ficam concluídos; a resposta do histórico deixa de expor endereço/observação.
6. Valor do pedido não representa remuneração da corrida. “Financeiro” informa a ausência de remuneração/repasse automatizado; não existe saque Pix funcional escondido atrás dessa tela.
7. Ocorrência após coleta exige atendimento. Não cancelar por um caminho que devolva estoque de mercadoria já em trânsito.

## Administrador

- “Visão geral” leva às áreas de chamados, solicitações, lojas, equipe e financeiro.
- “Chamados” abre a fila administrativa; abra um, responda, encerre/reabra e volte à lista administrativa.
- “Solicitações” permite analisar acesso profissional, cidade e loja, preservando a conta e os dados enviados. Filtros separam situações.
- “Lojas” aprova/suspende, define comissão e oferece “Editar dados da loja”. A edição administrativa pode corrigir CNPJ opcional, região e demais dados públicos; salvar não significa aprovar silenciosamente.
- “Imagens das lojas” mostra as capas pendentes. Confira a imagem e aprove ou informe motivo para recusar. A decisão avisa o vendedor e não altera a aprovação comercial da loja.
- “Equipe” separa perfis operacionais, contas/segurança e criação de acesso. Suspensão profissional não deve ser confundida com suspensão de toda a conta.
- “Financeiro” acompanha estornos que exigem atenção. Não marca dinheiro ficticiamente como entregue ao banco do cliente.
- “Definições > Cidades” cria, edita e suspende cobertura. “Categorias” cria seções adicionais e exclui as próprias sem lojas vinculadas; as categorias nativas são preservadas.
- “Integrações” informa configuração de serviços. Chaves não devem ser exibidas ao navegador; a configuração privada do servidor não é um formulário universal de credenciais.
- “Perfil do administrador” permite atualizar dados e remover explicitamente CPF. CPF não é necessário para administrar.
- Auditoria registra ações instrumentadas. Não serve para alterar retroativamente fatos financeiros nem para consultar senhas de usuários.

## Feedback, teclado e telas pequenas

Botões principais têm texto e contraste visível antes do hover. Campos têm rótulo; mensagens explicam resultado/falha. Janelas usam Tab/Shift+Tab, Escape e devolução de foco. Menus móveis exibem destinos prioritários e “Menu”. Formulários quebram para uma coluna em telas estreitas; uma janela longa pode rolar verticalmente para tornar todos os campos alcançáveis.

Se um fornecedor falhar, preserve os dados já preenchidos quando possível, explique a falha e permita tentar novamente. Não repetir pagamento cegamente; consulte o estado existente antes de criar outra operação.

## Localização e atendimento

O vendedor marca opcionalmente a entrada do comércio no mapa e salva o perfil. O cliente encontra as lojas marcadas em **Ver lojas no mapa** e vê as bandeiras Entrega, Retirada na loja e Agendamento presencial. Somente Entrega habilitada apresenta **Somente delivery**. Opções desativadas são rejeitadas pela API, além de indisponíveis na interface. [Guia completo e verificação](Mapa_e_atendimento_das_lojas.md).
