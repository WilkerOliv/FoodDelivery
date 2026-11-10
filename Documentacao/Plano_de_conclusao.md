# Plano de conclusão e limites para lançamento

Atualizado em 10/11/2026. Este quadro distingue implementação, execução demonstrada e operação externa. “Implementado” não significa homologado para cobrar clientes reais.

## Etapas internas já entregues

| Área | Implementação atual | Evidência / limite |
|---|---|---|
| Catálogo e aprovação | Produtos/serviços, edição de tipo, pausa/reativação; aprovação administrativa independente de pagamento | Testes HTTP e jornadas registradas; filtros de cidade continuam delimitando descoberta. |
| Agenda | Turnos, pausas, madrugada, exceções, profissional/primeiro disponível, prévia de impacto | Testes de domínio e concorrência; seleção não bloqueia horário até confirmação. |
| Administração | Cidades, categorias adicionais, perfil sem CPF, configuração protegida, MFA e auditoria | Categorias nativas preservadas; segredos existentes não são revelados. Ativação do MFA é operacional. |
| Entregas e repasses | Aceite exclusivo, etapas, crédito único com tarifa fixa, solicitação verificada e aprovação | Jornada publicada em 06/11; transferência de dinheiro é externa/manual. |
| Atendimento | Fila administrativa, respostas identificadas, retorno correto, avisos com destino interno | Jornada publicada em 06/11; entrega externa SMTP/WhatsApp não foi comprovada. |
| Integridade PostgreSQL | Migrações, disputa de estoque/agenda, isolamento do simulador e dump/restauração descartável | Execução local aprovada; não restaura imagens/chaves nem mede recuperação de produção. |
| Robustez do navegador | Consultas separadas do estado, descarte de respostas obsoletas, carga/falha/repetição explícitas | Testes automatizados e falha real de rede induzida na aba local; ver documento específico. |

## Próximos blocos, na ordem

1. **Aceite da versão publicada:** o usuário confirmou o deploy Live em 10/11. A consulta pública a `/healthz` respondeu `ok` nesta revisão. Isso comprova resposta do gateway, não o commit servido nem todas as jornadas; ainda é necessário conferir a versão autenticada e repetir os fluxos alterados. O código de referência desta revisão é `3c456d7`.
2. **Integrações de teste:** Mercado Pago sandbox com OAuth, webhook público assinado, recusa/atraso/duplicidade e estorno confirmado. SMTP e WhatsApp exigem conta/configuração e teste de entrega; simulador não atesta o fornecedor. Nenhuma cobrança real é necessária para o sandbox.
3. **Persistência e recuperação completas:** provisionar PostgreSQL persistente e armazenamento de capas, guardar chaves fora do repositório, cópia externa criptografada com retenção, ensaio conjunto e RPO/RTO medidos. H2 de demonstração no Render pode ser reiniciado/perdido e não é banco de produção.
4. **Operação segura:** ativar MFA, confirmar cookies Secure/origens/proxy finais, monitoramento com alertas acionáveis, rotação e procedimento de incidente. Contadores de abuso são locais; múltiplas réplicas exigem mecanismo compartilhado.
5. **Manutenção e desempenho:** extrair gradualmente telas do `main.js`, paginar consultas/listas e medir carga/consultas antes de otimizações. A separação do carregamento desta etapa é uma melhoria parcial, não a conclusão da arquitetura inteira.
6. **Aceite com usuários:** aparelhos físicos, teclado, zoom/leitor de tela e tarefas acompanhadas com pessoas de pouca familiaridade digital. Verificação de largura no navegador não substitui esses testes.
7. **Regras operacionais e privacidade:** política de cancelamento, retenção/exclusão de mensagens e dados, conferência de repasses, atendimento e revisão dos textos legais conforme o negócio real.

## O que não está incluído na implementação atual

- Transferência Pix automática de repasses: a administração registra o pagamento feito externamente.
- Publicação automática da empresa no Google Maps: o ponto no mapa do site não cria um Perfil da Empresa.
- Aplicativos nativos, roteirização/ETA e capacidade simultânea maior que um por recurso de agenda. São evoluções de produto, não promessas do ambiente atual.
- Geração de capas por IA: foi substituída, por solicitação do proprietário, por upload com moderação. Não é uma pendência.

## Critérios para chamar de pronto

- Fluxo tem regra no servidor, tratamento de falha e teste reproduzível.
- Integração externa tem evidência do fornecedor em ambiente de teste, sem confundir simulação com liquidação.
- O ambiente publicado corresponde ao código validado.
- Backup recupera banco, arquivos e configuração necessária, com prazo medido.
- Limites conhecidos têm responsável e decisão explícita antes da operação comercial.

Relatórios datados preservam o escopo histórico dos testes. A referência gerada inventaria funções; não executa todas as combinações de uso.
