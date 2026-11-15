# Arquitetura e operação do FoodDelivery

Atualizado em 7 de novembro de 2026.

## Arquitetura existente

O backend é um monólito modular em Spring Boot. Os pacotes separam identidade, catálogo, pedidos, reservas, pagamentos, entregas, notificações, atendimento e administração. O PostgreSQL guarda os dados; Flyway é o proprietário das alterações de esquema. Hibernate valida o modelo no início e não deve criar ou alterar tabelas automaticamente.

O frontend é uma aplicação JavaScript sem processo de compilação. `src/main.js` coordena navegação e telas; regras reutilizáveis ficam em módulos como `api.js`, `catalogPolicy.js`, `storeTime.js`, `storeLocation.js`, `adminSections.js` e `actionGuard.js`. Ao acrescentar comportamento, mantenha regra pura em módulo pequeno quando ela puder ser verificada sem DOM, mantenha a persistência no backend e evite duplicar uma regra de negócio apenas no navegador.

## Invariantes de reservas

- A duração do serviço é armazenada no item do catálogo em minutos, de 5 a 480, com 30 minutos para itens antigos e novos sem valor enviado.
- A duração é copiada para a reserva quando ela é criada; mudar o catálogo depois não altera compromisso existente.
- A API verifica que o intervalo inteiro cabe no horário semanal/exceção da loja, no fuso cadastrado para aquela loja.
- Reservas da mesma loja são criadas sob bloqueio da linha da loja. Isso serializa duas tentativas simultâneas durante a verificação de sobreposição e gravação.
- O profissional é o recurso quando selecionado. Sem profissional, usa-se uma chave fixa não nula para o recurso da loja. A restrição única do banco protege duplicidade exata mesmo com `NULL` e o teste cobre essa regressão.
- A consulta de intervalo usa início exclusivo/fim exclusivo para que um atendimento possa começar exatamente quando o anterior termina.

Ao adicionar outra forma de reserva (por exemplo, alterar horário), ela também precisa obter o mesmo bloqueio da loja e atualizar o intervalo/reserva e o recurso na mesma transação. Não faça somente uma checagem `exists` seguida de `save`: duas requisições podem observar simultaneamente que o horário ainda está livre.

## Solicitação de cancelamento depois do pagamento

Pedido ou reserva ainda não paga pode ser cancelado pelo cliente e liberar estoque/horário imediatamente. Depois do pagamento, o cliente envia uma solicitação; o comerciante aceita ou recusa pela tela da operação. Aceitar um pedido devolve o estoque e cria pedido de estorno integral. Aceitar uma reserva libera o horário e cria estorno se houve pagamento no site. O estado de estorno permanece sob confirmação do Mercado Pago e pode exigir revisão administrativa.

Essa implementação não estabelece política legal/comercial de prazos, categorias, multa ou direito de cancelamento. A decisão de aceitar é do comerciante. O cliente deve acompanhar o resultado e contatar a loja se a solicitação for recusada. Nunca mostrar estorno como concluído antes de o provedor confirmar.

## Localização da loja

O vendedor pode guardar latitude e longitude opcionais. A API rejeita coordenadas incompletas ou fora dos limites geográficos. Quando existem, a tela de detalhes cria um link de busca do Google Maps com o ponto. Sem coordenadas, o endereço textual ainda pode ser pesquisado no Maps.

O link não publica nem verifica uma empresa no Google Maps. A criação/edição de um Perfil da Empresa exige que o comerciante conecte e autorize a própria conta Google, que a API esteja habilitada e que o proprietário conclua verificações exigidas pelo Google. Essa integração não está implementada nem homologada.

## Segurança e trilha administrativa

- Rotas sem correspondência explícita exigem JWT autenticado; requisições anônimas são negadas por padrão. Rotas públicas continuam declaradas explicitamente. A troca obrigatória de senha limita a sessão às rotas explicitamente autorizadas para esse fluxo.
- Login e recuperação limitam identificadores normalizados com hash e um orçamento adicional por peer; operações sensíveis usam identidade JWT validada. Cadastro também possui limitação. O filtro não confia livremente em `X-Forwarded-For`. As janelas e limites ficam em `AuthRateLimitFilter`.
- A limitação é local ao processo e não substitui um gateway/WAF nem armazenamento compartilhado. Atrás de proxy, pode agrupar usuários no endereço do proxy; configurar endereços confiáveis requer definir e testar a topologia antes de passar a confiar em cabeçalhos encaminhados. Para múltiplas réplicas, mover contadores para Redis ou serviço equivalente.
- V23 mantém eventos de ações administrativas sensíveis e uma consulta autenticada de administrador em `GET /api/v1/admin/audit-events`. Resumos não devem conter credenciais, CPF ou tokens. A trilha cobre mutações administrativas instrumentadas no controlador atual; ainda não é completa para todo o sistema, nem imutável contra acesso direto ao banco. Restringir permissões SQL, definir retenção/exportação e acrescentar eventos de autenticação, pagamentos, suporte e mudanças feitas fora desse controlador antes de tratá-la como trilha de conformidade.
- Os testes H2 verificam migrações, autorização anônima/autenticada, limitação por origem e formato dos eventos. Isso não equivale a teste de invasão, auditoria independente ou validação de PostgreSQL em produção.

## Migrações recentes

- `V20__reservation_capacity_and_service_duration.sql`: duração do serviço, início/fim do compromisso, chave de recurso não nula e índices de sobreposição.
- `V21__customer_cancellation_requests.sql`: estado, momento e observação das solicitações de cancelamento de pedido e reserva.
- `V22__store_map_coordinates.sql`: latitude e longitude opcionais de cada loja.
- `V23__admin_audit_events.sql`: registro de eventos administrativos sensíveis, com referência opcional ao administrador e resumo seguro.

Não editar uma migração já aplicada em um ambiente compartilhado. Corrigir esquema com uma nova migração versionada. Antes de publicar V20+, faça backup do PostgreSQL; Flyway é executado na inicialização e o H2 em modo PostgreSQL dos testes não substitui uma execução contra a versão real do PostgreSQL.

## Verificações locais e automáticas

Na raiz, `npm start` inicia o ambiente local conforme `scripts/dev.py`. Para as verificações:

```sh
cd Frontend
npm run check
npm test

cd ../Backend
JWT_SECRET='<chave de teste com pelo menos 256 bits>' mvn test
```

O workflow `.github/workflows/quality.yml` executa sintaxe/testes do frontend, testes Spring com migrações H2 e o job `PostgreSQL concurrency and restore`. Este último executa `scripts/test_postgres.py` contra PostgreSQL 17 descartável: disputa de estoque, conflito de agenda, isolamento do simulador e dump/restauração em outro banco com conferência de contagens. A execução local passou e o job remoto passou no workflow de qualidade do commit `3c456d7` (execução `42335443672`). O conjunto não faz chamada real a Mercado Pago, SMTP, WhatsApp Cloud, Google Maps ou ViaCEP, nem substitui testes em dispositivos físicos ou recuperação completa de produção.


## Configurações externas e limites que continuam

- **Mercado Pago:** testar OAuth de vendedor, pagamento aprovado/recusado/expirado, webhook público HTTPS, estorno confirmado, saldo insuficiente e casos que requerem revisão. As credenciais e permissões variam por conta.
- **SMTP/WhatsApp:** validar entrega efetiva. WhatsApp necessita de template aprovado e número de produção autorizado.
- **Repasse de entregador:** remuneração fixa definida pelo administrador, congelada por entrega, crédito único na conclusão, saldo reservado e solicitação com senha/código/idempotência estão implementados. Administração aprova e registra pagamento feito externamente. A plataforma não executa transferência Pix; titularidade, pagamento externo e comprovante precisam de homologação operacional.
- **Mapas:** seleção opcional de ponto e mapa público de lojas estão implementados, com lista alternativa. O link Google Maps existe; publicação automática no Perfil da Empresa e planejamento de rotas não existem.
- **Logística:** taxa fixa por loja; sem zonas, cálculo de quilometragem, ETA ou despacho otimizado.
- **Agenda:** múltiplos turnos, pausas, exceções por data, disponibilidade por profissional, prévia de impacto e grade de horários livres estão implementados. Capacidade maior que um por recurso e política comercial/legal completa de cancelamento não estão modeladas.
- **Segurança/operação:** MFA TOTP administrativo está implementado e depende de ativação e guarda dos códigos pelo administrador. Limitador distribuído, topologia de proxy validada, cobertura completa e proteção contra alteração da trilha, monitoramento/alertas e rotina externa de backup continuam dependências operacionais.
- **Disponibilidade:** Docker local fornece PostgreSQL para desenvolvimento, mas não define sozinho HTTPS, publicação do frontend/API, armazenamento compartilhado de imagens, rotação de segredos ou recuperação de produção.

## Backups e continuidade

Os scripts `Backend/scripts/backup-db.sh` e `restore-db.sh` geram dump privado com hash portátil e restauram em transação única, mediante confirmação do banco. O ensaio PostgreSQL de 11/11 validou também rollback após falha SQL. [Procedimentos, conta restrita e limites](Recuperacao_e_banco_de_producao.md). Isso protege contra erro humano em uma operação local, mas não comprova backup fora do equipamento, retenção, criptografia, rotina automática ou tempo de recuperação. Antes de produção, definir responsáveis, periodicidade, retenção, cópia externa criptografada e fazer teste de restauração em ambiente isolado. Não restaurar em produção como teste.

## Manutenção recomendada

1. Manter alterações de banco exclusivamente em novas migrações Flyway.
2. Para cada fluxo financeiro ou de agenda, cobrir repetição da chamada, falha no provedor, concorrência e autorização entre contas.
3. Continuar extraindo regras independentes de `main.js` para módulos testáveis; não migrar o framework de interface sem uma necessidade de produto e um plano de regressão.
4. Executar teste de navegador nas jornadas de cliente, vendedor, entregador e administrador em tamanhos móveis e desktop antes de publicação.
5. Homologar as integrações externas em contas de teste e documentar configurações sem gravar segredos no repositório.
6. Revisar e commitar alterações em unidades coerentes; não publicar o estado de trabalho sem inspeção do diff e migrações.

## Expediente e alterações concorrentes

A rotina semanal admite múltiplos turnos e exceções datadas. A alteração bloqueia loja e reservas futuras, verifica revisão da prévia e exige decisão explícita sobre compromissos afetados. Cancelamentos, slots, avisos internos e solicitação de estorno são transacionais; o estorno externo segue o processamento idempotente já existente. Mudança de fuso com compromissos ativos é impedida. Veja [Funcionamento e reservas](Funcionamento_e_reservas.md), incluindo compatibilidade dos JSONs, V26 e limitações de rollback.

## Concorrência no navegador

`pageData.js` consulta dados sem alterar o estado compartilhado. `pageLoading.js` publica apenas o resultado da navegação vigente e aborta a consulta anterior. A chave considera sessão, papel, página, loja e cidade. `api.js` rejeita respostas de uma sessão anterior, inclusive um 401 atrasado. Isso evita misturar painéis ao trocar de conta ou loja, mas não substitui autorização no servidor nem desfaz uma mutação já recebida pela API. A inicialização libera o formulário após consultar a sessão, evitando perda de foco na digitação. Consulte [Carregamento e recuperação](Carregamento_e_recuperacao.md).

## Módulos e armazenamento atualizados em 14/11/2026

As telas/ações principais agora são compostas a partir de `Frontend/src/views` e `Frontend/src/features`. O estado de conta é limpo por `sessionState.js` ao sair, expirar ou revogar a sessão. Cidades, categorias e status de integrações possuem controladores administrativos próprios. As capas utilizam a interface `StoreImageStorage`, com disco local ou bucket Supabase privado. Consulte [decisões e testes](Modularizacao_e_validacao_2026-11-14.md) e [configuração gratuita](Persistencia_gratuita.md).
