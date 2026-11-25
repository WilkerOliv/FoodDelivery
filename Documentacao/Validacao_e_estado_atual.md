# Validação e estado atual

Consolidado em 24/11/2026. Este documento substitui relatórios históricos de
04–17/11. Os resultados abaixo foram registrados nas revisões indicadas; a
limpeza documental não executou novamente as jornadas da aplicação.

## Código em revisão e publicação são diferentes

A revisão está em `codex/webhook-validation`, no PR #2. A última base remota
conferida é `main` em `15e652f`. Não houve merge ou deploy nesta limpeza.
A análise de dependências ainda bloqueia a integração: dois alertas HIGH em
`spring-webmvc:6.2.19`. Detalhes e limites da avaliação estão na
[revisão corretiva](Revisao_corretiva_2026-11-23.md).

## Evidência local mais recente — 24/11

| Verificação | Resultado registrado |
|---|---|
| Backend | 154 testes JUnit, sem falhas ou erros. |
| Frontend | Suíte aprovada; sintaxe e imports de 43 módulos conferidos. |
| PostgreSQL descartável | Migrações, equivalência V9 H2/PostgreSQL, concorrência de estoque/agenda/limites, dump, restauração e rollback aprovados. |
| Inicialização persistente | Três testes de configuração aprovados. |
| Imagem persistente com TLS | Privilégios restritos, troca para conta de execução, sessão após recriação e recuperação da queda do banco aprovados. |
| Backup | 17 testes de scripts aprovados, incluindo cifragem real e recusa de arquivos inseguros antes da restauração. |

Esses ensaios usam recursos descartáveis. Não comprovam restauração dos dados
reais, entrega SMTP, processamento do Mercado Pago ou publicação dessas mudanças.

## Última publicação persistente registrada — 17/11

Foi registrado PostgreSQL e Storage Supabase com demonstração desligada, login
normal, 31 migrações aplicadas e TLS `verify-full`. Saúde respondeu `UP`; login
administrativo pela API, cookie Secure/HttpOnly, recusa de origem externa e
identidade persistida foram confirmados. O proprietário confirmou `40209c3`
como Live naquela data. Isso não identifica o commit atualmente no Render.

O teste direto do bucket privado confirmou envio, leitura e recusa de acesso
público. Ainda falta a jornada completa de envio/moderação de capa pela interface
persistente publicada e a conferência da sessão depois da próxima publicação.

## Evidência visual anterior e alcance

As revisões de 04–14/11 registraram jornadas fictícias dos quatro perfis:
cadastro/edição/pausa de catálogo, escolha de profissional, compra, cancelamento,
reposição de estoque, entrega, financeiro, suporte e notificações. Foram
inspecionadas telas móveis e desktop. Essas observações pertencem à versão
testada; não certificam todos os botões da versão atual.

Capturas ainda úteis às regras de capas, mapa, expediente e recuperação de
consulta permanecem nos respectivos guias. Relatórios duplicados e capturas
de telas substituídas foram retirados da árvore ativa; os arquivos antes
versionados continuam recuperáveis no histórico Git.

## Como reproduzir as verificações

Na raiz do repositório:

```sh
npm --prefix Frontend run check
npm --prefix Frontend test
mvn -f Backend/pom.xml test
python3 scripts/test_postgres.py
python3 scripts/test_persistent_deploy.py
python3 scripts/test_persistent_deploy.py --container
```

Os ensaios PostgreSQL e contêiner exigem Docker e recursos locais disponíveis.
Para jornadas com dados fictícios, use [homologação isolada](Homologacao_sem_dados_reais.md).
Para cifragem, backup externo e restauração, siga
[banco e recuperação](Recuperacao_e_banco_de_producao.md). Nunca use produção
como destino de um teste destrutivo.

## Bloqueios que a limpeza não remove

- Homologação real no sandbox do Mercado Pago e entrega SMTP.
- Primeiro backup externo real e restauração com RPO/RTO medidos.
- MFA administrativo, origens/cookies, identificação de visitantes no proxy e recebimento de alerta no ambiente publicado.
- Aplicação remota da separação de credenciais e do contador compartilhado, quando adotados.
- Aceite visual autenticado, capas, teclado, zoom, leitor de tela e aparelhos físicos.
- Política de retenção e tratamento de registros em disputa; nenhum expurgo foi autorizado por esta limpeza.
- Dois alertas HIGH de dependências e aprovação da revisão antes de integrar.

A ordem de trabalho está no [plano de conclusão](Plano_de_conclusao.md).
