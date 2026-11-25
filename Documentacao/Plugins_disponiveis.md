# Plugins habilitados para o FoodDelivery

[EXECUTADO] Em 25/11/2026, `codex plugin list --json` retornou **51 instalados, 51 habilitados e 0 desabilitados**. A consulta do aplicativo para a pasta do projeto confirmou os mesmos 51 identificadores com estado `AVAILABLE`.

| Área | Plugins habilitados |
|---|---|
| Programação, arquitetura, revisão e entrega | Code, Code Assistant, Code Ontology Companion, Codelit, Codex Coordinator, Codex Dev Workflows, Codex Engineering Guardrails, codex-sdlc, Develoop, Development OS, stark AI Developer Tools, GitHub |
| Banco, infraestrutura e segurança | Supabase, Vercel, Auth0, 1Password Developer Tools, Daybreak Defense Factory, Skill Risk Check |
| Interfaces, sites e aplicativos | Figma, Floot, Mobile App Builder, Product Design, Sites, ui-consistency, UI UX Designer, 유아이볼 (uibowl), Web & Mobile App Builder, ThoughtfulBits Skills |
| Documentação e criação de materiais | Documents, PDF, Spreadsheets, Presentations, Template Creator, Notion, Pages, Default templates, Visualize (openai-curated-remote), Visualize (openai-bundled) |
| Navegador, ferramentas de trabalho e outros | Browser, unified-computer-use, codex-app-tools, Codex Browser Recorder, Remote Desktop Commander, Plugin Management, CodexPluginStats, TokenX, Codex Usage, Codex Material Themes, Pets, OpenAI Ads Conversions, Relewise Developer |

[EXECUTADO] Para repetir a contagem no ambiente de desenvolvimento:

```sh
codex plugin list --json | jq '.installed | {instalados: length, habilitados: map(select(.enabled)) | length}'
```

[NÃO VERIFICADO] A autenticação em cada serviço externo e a execução individual de todas as funções ficam fora desta conferência. O Codex pode usar os plugins habilitados conforme a tarefa.
