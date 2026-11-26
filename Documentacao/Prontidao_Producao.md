# Prontidão para produção

**[NÃO VERIFICADO] NO-GO para clientes reais.** A decisão exige evidência do ambiente definitivo e assinatura do dono. Os testes de homologação não autorizam movimentação financeira real.

| ID | Área | Requisito | Prioridade | Estado | Evidência ou critério de saída | Responsável |
|---|---|---|---|---|---|---|
| G01 | Integração | PRs temáticos e proteção | P0 | [EXECUTADO] | `gh pr view N --json state` → MERGED para #3–#10. API de proteção da main → 10 verificações obrigatórias, inclusive Trivy. | Engenharia |
| G02 | Produto | Escopo RF01–15, E1–12 e regras 1–12 | P0 | [EXECUTADO] | `git show 69ce23d:Documentacao/Visao_e_Especificacao_do_Produto.md` recuperou a v1.0. [Comparação por requisito](_projeto_claude/Visao_e_Especificacao_do_Produto.md) | Produto |
| G03 | Cadeia de suprimentos | Boot 4 e zero HIGH/CRITICAL sem exceção | P0 | [NÃO VERIFICADO] | Exigir SBOM Maven e scans das imagens **por digest** e da imagem final. A aprovação do #3 inclui exceção temporária. | Engenharia |
| G04 | Jornada de compra | Pedido idempotente e testes de navegador por papel | P0 | [NÃO VERIFICADO] | Exercitar retentativa após perda da resposta. Exigir um único pedido, pagamento e débito de estoque. | Engenharia |
| G05 | Segurança | ASVS nível 2 e matriz de autorização completa | P0 | [NÃO VERIFICADO] | Associar cada endpoint e perfil a casos permitidos e negados, revisar cookies, CSRF, MFA e cotas. | Segurança |
| G06 | Banco | Permissões, integridade financeira e dados pessoais | P0 | [NÃO VERIFICADO] | #6 separa os papéis. Validar políticas de linha, cifragem, restrições e restauração no destino. | Banco |
| G07 | Serviços externos | Mapas e dependências do navegador | P0 | [NÃO VERIFICADO] | Demonstrar limite agregado, cache, desligamento e fallback. | Engenharia |
| G08 | Infraestrutura | Produção paga, isolada, domínio, SMTP, MFA | P0 | [NÃO VERIFICADO] | Serviço, banco e bucket distintos do piloto. Registrar evidência mascarada de isolamento e disponibilidade. | Dono |
| G09 | Operação | Recuperação, carga e alertas recebidos | P0 | [NÃO VERIFICADO] | Restaurar cópia independente em destino vazio. Medir RPO/RTO, carga e entrega do alerta. | Operação |
| G10 | Experiência | Acessibilidade, dispositivos e rede instável | P0 | [NÃO VERIFICADO] | UAT autenticado dos quatro perfis, tarefas por teclado e leitor de tela, layout e retomada em rede lenta. | Produto |
| G11 | Documentação | Código, banco e referências reproduzíveis | P1 | [NÃO VERIFICADO] | [Auditoria](Auditoria_Documental.md). Reconciliar #12 antes de integrar. | Engenharia |
| G12 | Jurídico e fiscal | Minutas revisadas e aprovadas | P0 | [NÃO VERIFICADO] | Parecer do advogado e validação fiscal do contador, com versão e data. | Dono |
| G13 | Financeiro | Sandbox, conciliação, chargeback e repasse | P0 | [NÃO VERIFICADO] | Confrontar identificadores, moeda, valor, tarifas e saldo com provedor de teste. | Financeiro |
| G14 | Lançamento | Pentest independente, reteste e aceite residual | P0 | [NÃO VERIFICADO] | Laudo e reteste independentes, assinatura e data da decisão. | Dono |
| G15 | Expansão | PWA e Pix automático | P1 | [NÃO VERIFICADO] | Decidir após o piloto; verificar contrato e beneficiário antes de qualquer automação financeira. | Produto |

[EXECUTADO] `git cherry codex/verificacao-conjunta codex/p1-webhook` → `- f74a16b`; com `codex/p1-limites` → `- 750d924`. O sinal negativo identifica alteração com patch equivalente na integração de teste.

[Decisões propostas](adr/0001-piloto.md) · [Próximo passo do dono](Acoes_do_Dono.md)
