# Fidelidade da homologação e critérios para lançamento

> [NÃO VERIFICADO] Escopo da revisão de 25/11: instruções e resultados históricos sem evidência individual nesta página aguardam confirmação. Os testes repetidos têm comando e resultado na [matriz de aceite](Aceite_2026-11-25.md).

Verificação do código e dos testes em 06/11/2026. A homologação não é uma comprovação integral da operação com usuários, dinheiro e provedores reais.

## O que é compartilhado

Os cadastros, catálogo, estoque, pedido, reserva, suporte, notificações internas, entrega e registro financeiro usam os controladores, serviços, entidades e validações da aplicação. Não existe uma vitrine de demonstração com compras apenas no estado do navegador. Ações normais fazem requisições à API e persistem no banco da instância.

A aprovação simulada em `DemoController.payment` confere o titular e chama `PaymentService.confirmForDevelopment`; esse método chama a mesma `confirmProvider` que confirma operações no fluxo real. Assim, estados internos e avisos após a confirmação são exercitados. Isso não testa como o fornecedor determina que a operação foi paga.

## Diferenças deliberadas e seu efeito na evidência

| Fronteira | Demonstração | Operação real / evidência ainda necessária |
|---|---|---|
| Checkout | Botões aprovam ou recusam operação fictícia. OAuth, checkout e webhooks reais são bloqueados por `DemoExternalGuard`. | Conta de teste do Mercado Pago, checkout no provedor, assinatura, referência, valor, moeda, atrasos, eventos repetidos e retorno ao site. Testar sandbox antes de receber dinheiro real. |
| Estorno | `MercadoPagoRefundService.attempt` pode concluir localmente sem consulta externa. | Solicitação, resposta pendente/recusada, consulta e confirmação do provedor. A mensagem REFUNDED da simulação não é prova de dinheiro devolvido. |
| Conciliação | `PaymentReconciliationService` registra SIMULATED e não consulta o provedor. | Validar resposta real por identificador, referência externa, valor e moeda; tratar divergências e contestação. |
| E-mail e WhatsApp | `RecoveryMessageSender` captura código na caixa fictícia. | Entrega efetiva, remetente/número, templates, bloqueios, limites, expiração, atrasos e indisponibilidade. Obter um código da caixa de testes não homologa o canal real. |
| Cidade, geocodificação e CEP | Respostas predefinidas para cidades/CEPs de teste. O reverse fictício não representa a geografia arbitrária real. | Consentimento e precisão em aparelho físico; busca/reverse do fornecedor; CEP inexistente, cidade ambígua, múltiplos CEPs, limites e falhas de rede. |
| Banco | Render de demonstração usa H2 e dados temporários; reiniciar/publicar pode apagar registros. | PostgreSQL persistente, concorrência, migrações, conexões, backup de banco e arquivos, chaves privadas, ensaio de restauração e monitoramento. |
| Contas e lojas prontas | `DemoBootstrap` cria contas com perfil preenchido, lojas aprovadas, itens e profissionais. | Testar separadamente conta recém-cadastrada, perfil incompleto, solicitação pendente, aprovação/recusa e primeiro acesso. Começar com loja aprovada não prova o onboarding. |
| Acesso externo | Senha compartilhada e cookie de avaliador protegem a demonstração. | Essa barreira não é o login do cliente nem substitui autenticação/autorização da API. A publicação comercial deve ter seu domínio, TLS, origens e política de acesso próprios. |
| Repasses | Saldo, verificação, aprovação e registro podem ser exercitados com valores fictícios. Não há transferência bancária automática. | Conferir titular, pagamento externo e comprovante. Registrar PAID não comprova que o banco creditou o beneficiário. |

## Evidência fora do H2 executada nesta revisão

Foi executado `python3 scripts/test_postgres.py`, que cria PostgreSQL 17 descartável, não lê o `.env` da aplicação e remove o contêiner ao terminar. `PostgresConcurrencyIT` usa o perfil `postgres`, sem ativar `homologacao`.

Resultados aprovados:

1. Duas compras concorrentes disputando a última unidade: apenas uma reserva o item, com estoque final zero.
2. Duas reservas sobrepostas: apenas uma confirma o intervalo.
3. Aplicação das migrações PostgreSQL nesse banco.
4. Perfil PostgreSQL normal com simulação desativada e sem os controladores de pagamento de demonstração/desenvolvimento.
5. `pg_dump` e `pg_restore` para outro banco descartável, com contagens de pedidos, reservas, pagamentos e migrações conferidas.

**Alcance:** são dois testes de concorrência, um teste de isolamento dos simuladores e um ensaio de restauração de banco. Não cobrem toda a aplicação com PostgreSQL, arquivos de capa, cofres/chaves, carga esperada, desastre do provedor ou RPO/RTO de produção. As fixtures desse teste também criam contas e loja diretamente; não são uma validação do cadastro público.

## Separação entre ambientes

`DemoEnvironment` recusa combinar homologação com perfis de produção/banco normal. `ProductionGuard` exige HTTPS, cookie Secure e proíbe perfil de demonstração/H2 e opções de assinatura simulada no perfil de produção. O endpoint `/api/v1/demo/payments` só existe no perfil homologacao; os endpoints legados `/api/v1/dev/payments` e `/api/v1/dev/reservations` só existem no perfil `dev-h2`. Eles não são expostos no perfil PostgreSQL normal, conforme o teste executado. A implantação comercial deve ativar explicitamente `prod`: usar PostgreSQL por si só não substitui esse perfil e suas validações. A flag de frontend não concede autorização ao servidor para simular.

Essas proteções devem continuar testadas a cada mudança. Presença de código de proteção não comprova que toda configuração futura estará correta.

## Critérios de aprovação

- Registrar cenário, ambiente, caminho executado, resultado e limite da evidência. Diferenciar revisão de código, teste automatizado, interação manual, sandbox do fornecedor e operação real.
- Não mudar estados financeiros diretamente para produzir uma tela de sucesso. A simulação deve entrar na fronteira documentada e exercitar as regras compartilhadas.
- Não apresentar retorno imediato de fornecedor fictício como prova de prazo, disponibilidade ou liquidação real.
- Repetir as jornadas críticas com cadastro novo e dados incompletos, além das contas previamente preparadas.
- Homologar adapters externos no sandbox/contas de teste do fornecedor com credenciais próprias e destinos controlados; depois avaliar a prontidão operacional antes de cobrança pública.
- Tratar alterações entre H2 e PostgreSQL, origem/cookies, serviços externos e hospedagem como riscos de implantação a verificar, não como detalhes automaticamente garantidos pelos testes locais.

## Conclusão

A demonstração fornece evidência útil das regras internas e das interações exercitadas. Ela não garante equivalência integral com produção nem ausência de falhas. O lançamento com dinheiro e usuários reais permanece condicionado às validações externas e operacionais acima. Dados fictícios tornam o teste seguro; não devem tornar sua conclusão mais abrangente que a evidência.
