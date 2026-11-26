# ADR 0001 — Limites do primeiro piloto

[NÃO VERIFICADO] Proposta para assinatura. Os padrões abaixo servem ao ensaio e requerem aprovação antes da oferta comercial.

| Decisão | Alternativas | Padrão proposto e motivo |
|---|---|---|
| Local e categorias | Uma cidade ou várias | Uma cidade com lojistas convidados. Iniciar com retirada e serviço presencial para reduzir o número de operações simultâneas. |
| Área e responsabilidade da entrega | Frota da plataforma ou da loja | Manter delivery comercial fechado até aprovar raio, SLA e responsável por incidentes. |
| Habilitação e taxas | Split contratado ou repasse controlado | Confirmar tarifa, KYC e beneficiário no fornecedor antes de publicar valores. |
| Cancelamentos | Regra única ou por categoria | Exibir política por categoria antes de pagar. Divergências exigem suporte e registro. |
| Dados | Cadastro completo inicial ou progressivo | Solicitar apenas o necessário à modalidade. Retenção, base e direitos sujeitos a revisão jurídica. |
| Capacidade | Horário livre ou capacidade configurada | Exigir duração e profissional/recurso nos serviços, validar estoque na transação do pedido. |
| Canais | Chat interno, SMTP, WhatsApp | Chat interno e SMTP comprovado para recuperação. Publicar horário e responsável de plantão. |
| Metas | Alta disponibilidade imediata ou piloto assistido | Propor RPO de 24 h e RTO de 4 h. Aprovar só após ensaio cronometrado. Informar pico de pedidos/hora e simultaneidade para teste a 10×. |
| Hospedagem | Compartilhar piloto ou serviço dedicado | Produção separada, paga, com backup independente e alerta com destinatário verificado. |

[NÃO VERIFICADO] Decisão de repasse proposta: controle manual com reautenticação, beneficiário validado e dupla aprovação acima de limite a definir. Manter indisponível até existir controle demonstrado. A alternativa de payout automático depende de habilitação do fornecedor.

Aprovador: ______  Cidade: ______  Pico/hora: ______  Limite de repasse: ______  Data: ______
