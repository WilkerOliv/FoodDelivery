# Modularização, limpeza e persistência — 14/11/2026

## Organização atual

`Frontend/src/main.js` compõe a aplicação: estado, navegação, carregamento e ligação entre módulos. As telas estão em `src/views/`: descoberta/categorias, pedidos/reservas/checkout, vendedor, entregador, administração, conta/financeiro/notificações e autenticação. As operações estão em `src/features/`: conta, administração, pedidos/entregas, suporte e diálogos de compra/reserva.

Cada fábrica recebe suas dependências explicitamente. As ações recebem callbacks de renderização por referência indireta, preservando o comportamento da camada que reinstala eventos e recupera foco. Nenhum módulo importa `main.js`. O verificador percorre subdiretórios, confere sintaxe e existência de imports relativos e rejeita ciclos. Ele não substitui testes de execução.

A separação mantém contratos HTTP e regras existentes, reduz o arquivo central de aproximadamente 252 KB para 116 KB e permite localizar alterações por domínio. Ainda existem funções extensas de montagem de HTML e estado compartilhado; esta entrega não declara que toda a arquitetura deixou de precisar de manutenção. A divisão de arquivos por si só não reduz os bytes totais baixados nem garante ganho de latência. Medir carregamento e consultas precede otimizações adicionais.

No backend, `AdminCoverageController` trata cidades, `AdminCategoryController` categorias e `AdminIntegrationStatusController` diagnóstico das integrações. Todos preservam autorização ADMIN. As mutações usam transação e auditoria com o administrador autenticado. `AdminController` conserva gestão de contas/lojas e os demais contratos não extraídos.

## Capas persistentes

`StoreImageStorage` separa o fluxo de moderação da infraestrutura de arquivo. `LocalStoreImageStorage` mantém compatibilidade com volumes existentes. `SupabaseStoreImageStorage` permite bucket privado, com HTTPS restrito ao domínio do projeto, sem redirecionar credenciais, nomes de arquivo validados e erros sem corpo do fornecedor.

Downloads têm limite de 5 MB aplicado durante o recebimento e prazo de 20 segundos para resposta completa. `LimitedImageSubscriber` cancela a recepção antes de acumular bytes excedentes. O frontend continua recebendo imagens pela API autorizada; não recebe chave de serviço. Falha ao gravar imagem não cria submissão válida, e rollback tenta remover o objeto enviado. Falha na compensação pode deixar objeto órfão: conciliação/limpeza externa e retenção não estão automatizadas nesta entrega.

`DATABASE_SCHEMA` separa as tabelas do aplicativo de schemas gerenciados. Hikari e Flyway usam o mesmo schema; `DATABASE_POOL_SIZE` limita o pool (padrão 5). Não há migração automática entre schemas/buckets. Procedimento: [Persistência gratuita](Persistencia_gratuita.md).

## Limpeza e publicação

As imagens Docker copiam apenas arquivos necessários à interface, em vez de publicar toda a pasta Frontend. Testes, scripts, documentação de desenvolvimento e manifests de dependências não fazem parte da raiz web. A demonstração mantém `demo.html`, necessário à caixa de mensagens fictícias; produção o exclui. O handler Python necessário ao gateway é copiado para `/app/frontend_handler.py`, fora da raiz pública. O teste do container falha imediatamente se o processo encerrar durante a inicialização.

Testes, migrações e evidências datadas foram preservados: são necessários para regressão, bancos existentes e rastreabilidade. Bancos locais e imagens existentes não foram apagados. Caches Python podem ser regenerados e não são versionados. Remover arquivos sem comprovar desuso não é uma otimização segura.

O gerador de referência agora inclui módulos em subdiretórios. As referências frontend/backend foram regeneradas a partir do código atual, sem conservar explicações das funções movidas nas posições antigas.

## Correções encontradas na validação

- Cartões administrativos estreitos agora usam uma coluna, textos maiores e separação entre descrição e ação.
- O teste de homologação geral passou a criar loja e catálogo próprios, com retirada e atendimento definidos. Antes dependia de configurações mutáveis da loja de demonstração; a recusa de retirada observada era correta. A loja criada pelo teste é suspensa ao encerrar, sem apagar o histórico.
- Expiração, saída e revogação de sessão usam uma limpeza compartilhada. Vitrine, rascunhos, checkout e dados administrativos não reaparecem ao entrar com outra conta. Dois testes cobrem isolamento e ausência de coleções compartilhadas.
- O status de capas não afirma mais que todo upload usa armazenamento local.

## Escopo de aceite

Testes automatizados verificam regras de domínio, autorização, armazenamento, sintaxe/imports, scripts e jornadas HTTP. Revisão manual local cobre login dos quatro perfis, navegação administrativa, configurações, catálogo com cadastro/edição de tipo/remoção, descoberta por categorias, vitrine e seleção de profissional, entregas/financeiro e comportamento responsivo.

Esses testes não certificam ausência de qualquer falha. Não houve validação de cobrança, mensagens externas ou Storage Supabase real. O Render publicado não foi alterado automaticamente. Os bloqueios operacionais continuam explícitos no [plano de conclusão](Plano_de_conclusao.md).

## Resultados executados

| Verificação | Resultado |
|---|---|
| Backend Maven | 142 testes, nenhuma falha ou erro |
| PostgreSQL descartável com schema privado | 3 testes de integração, migrações e ensaio de backup/restauração aprovados; script de criação do schema executado pelo inicializador PostgreSQL |
| Frontend | 90 testes aprovados; 42 módulos com sintaxe/imports válidos e sem ciclos |
| Ferramentas Python | 39 testes aprovados |
| Jornadas HTTP | Catálogo, quatro papéis, endereços, suporte, cidades/categorias, entrega/chat/localização aprovados |
| Homologação HTTP isolada | Aprovação/recusa/repetição de pagamento fictício, reserva, códigos simulados, capa moderada e estorno fictício aprovados |
| Container de homologação | Inicialização em 19,5 s com 512 MiB/1 CPU; saúde 503→200, gateway/API, quatro perfis, cookies, origem e limitação de login aprovados |
| Pacote web Docker | Construção aprovada; módulos presentes e testes/scripts/package.json ausentes da raiz pública |
| Navegador local | Quatro perfis, catálogo criado/editado/pausado, filtro de categorias, profissionais, financeiro; sem erros JavaScript registrados na aba ao finalizar |
| Responsividade | Painel/financeiro com largura solicitada de 390 px e painel em 1440 px; conteúdo sem overflow horizontal nas medidas observadas (375/1425 px úteis) |

Evidências visuais: [painel no celular](Evidencias_modularizacao_2026-11-14/admin-celular.jpg) e [painel desktop](Evidencias_modularizacao_2026-11-14/admin-desktop.jpg).

A execução não inclui todas as combinações possíveis, leitores de tela nem aparelhos físicos. Testes de pagamentos e mensagens usam simulação identificada; não comprovam liquidação ou entrega externa.
