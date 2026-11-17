# Publicação persistente — 17/11/2026

## Resultado confirmado

A correção da branch `fix/render-caddy-capability` foi revisada, integrada à `main` e publicada no Render. O commit `8c1769f` ficou Live no serviço `fooddelivery-homologacao`, publicação `dep-dbtsnckipgvc73a1th70`. Hoje o Live é `40209c3`, que inclui essa correção (veja os limites abaixo).

O ambiente publicado usa PostgreSQL e Storage no Supabase, com o modo de demonstração desligado. A conta administrativa previamente criada no banco foi preservada. A barreira adicional de avaliador foi substituída pelo login normal, conforme autorização do proprietário. Isso não habilita cobranças reais: Mercado Pago permanece em sandbox.

## Correções e decisões

- **Execução do Caddy:** o binário é recriado sem o atributo `cap_net_bind_service`. A porta 10000 não exige essa permissão. O contêiner continua sem privilégios adicionais; o teste aplica `--cap-drop=ALL` e `no-new-privileges`.
- **Arquivos temporários:** o commit `4931268` direciona configuração e dados internos do proxy para `/tmp/fooddelivery-caddy`, evitando tentativas de escrita em `/app`. O proxy continua recebendo somente as variáveis necessárias, sem credenciais SQL, Storage ou assinatura. Esses arquivos são temporários; dados de negócio permanecem no Supabase.
- **Configuração do proxy:** o Caddyfile foi formatado com a ferramenta do próprio Caddy. Não houve mudança nas regras de acesso ou TLS.

## Evidências executadas

| Verificação | Resultado e alcance |
|---|---|
| Regressão com imagem anterior e permissões restritas | Reprodução de `Operation not permitted` ao executar Caddy. |
| Imagem corrigida com PostgreSQL descartável e TLS | Passou: migrações, login, cookie seguro, origem externa recusada, conta/sessão preservadas após reinício e recuperação após interrupção do banco. |
| Imagem com ajuste de diretórios (`4931268`) | Mesma suíte aprovada; teste verifica ausência de `permission denied` na inicialização. |
| Configuração do inicializador | Dois testes aprovados; TLS verificado, papel limitado e credenciais completas exigidos. |
| Supabase real | 31 migrações bem-sucedidas; Render confirmou schema na versão 31 e conexão `verify-full`. |
| `GET /healthz` publicado | HTTP 200, `status: UP`. |
| `/runtime-config.js` publicado | HTTP 200, `FOODDELIVERY_DEMO=false`. |
| Arquivos internos | `/demo.html`, `/package.json` e `/actuator/health` retornaram 404. |
| Conta sem autenticação | `/api/v1/account/me` retornou 401. |
| Login administrativo publicado pela API | HTTP 200; perfil ADMIN e cookie Secure/HttpOnly. Nenhuma credencial foi registrada neste documento. |
| Identidade persistida | Identificador da conta publicada igual ao salvo na validação anterior contra o Supabase. |
| Requisição autenticada de origem externa | Logout com origem não autorizada recusado com HTTP 403. |
| CI de `8c1769f` e `4931268` | `Project quality` e `Security checks` concluídos com sucesso. |

## Limites e próximos testes

1. **Versão publicada (atualizado em 17/11):** o proprietário confirmou no painel do Render que o commit Live é `40209c3`, o último da `main`. Ele inclui `8c1769f` e `4931268`; `40209c3` só altera documentação. O serviço também responde `/healthz` com `UP` e `/runtime-config.js` com `FOODDELIVERY_DEMO=false`. Essa confirmação foi feita por uma pessoa no painel; o ambiente automatizado não vê o commit publicado.
2. A página de login normal foi observada no navegador. O aceite visual autenticado e a continuidade da sessão após a próxima publicação ainda não foram concluídos: o controle do navegador integrado deixou de responder durante a retomada. O teste de login pela API não substitui essa verificação visual.
3. O serviço gratuito entrou em repouso e a primeira requisição aguardou a inicialização. Não confundir essa espera com perda do banco; a identidade preservada foi conferida após o retorno.
4. Upload, moderação e leitura de capa pela aplicação publicada ainda precisam de uma jornada completa. O teste direto do bucket privado em 15/11 não substitui esse fluxo.
5. Persistência remota não equivale a backup. Permanecem cópia externa cifrada, retenção e ensaio de restauração conjunta de banco, imagens e chaves, com prazo medido.
6. Pagamentos, SMTP e WhatsApp precisam de homologação com os fornecedores antes de operação comercial. Este resultado não declara todos os fluxos do projeto prontos para produção.

## Reprodução local

```sh
python3 scripts/test_persistent_deploy.py
docker build -f deploy/Dockerfile.persistente -t fooddelivery-persistent:qa .
python3 scripts/test_persistent_deploy.py --container
```

O teste usa banco e senhas descartáveis e remove seus próprios recursos. Não lê as credenciais privadas do piloto. Os segredos reais permanecem fora do repositório.

CI: [qualidade de 4931268](https://github.com/WilkerOliv/FoodDelivery/actions/runs/44120683455), [segurança de 4931268](https://github.com/WilkerOliv/FoodDelivery/actions/runs/44120684056).
