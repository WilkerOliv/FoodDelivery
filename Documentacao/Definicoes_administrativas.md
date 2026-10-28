# Definições administrativas

Esta área é exclusiva do perfil `ADMIN`. O item **Minha conta** passa a se chamar **Definições** apenas para o administrador; os demais perfis continuam vendo **Minha conta**.

## Seções

- **Cidades:** cadastrar cidade, UF e CEP de referência; editar ou suspender e reativar cidades. Suspender preserva o cadastro e o histórico, mas remove a cidade da lista de cobertura ativa. Uma cidade ativa pode ser escolhida por clientes e usada para descoberta de lojas.
- **Categorias:** criar novas seções de comércio ou serviço, com nome e ícone, para que apareçam nas buscas e no cadastro de lojas. As categorias padrão do sistema ficam protegidas e não podem ser alteradas ou excluídas. Seções criadas pela administração podem ser excluídas quando nenhuma loja as utiliza; isso evita deixar lojas com uma categoria órfã.
- **Integrações:** mostra quais configurações do backend estão presentes para SMTP, WhatsApp Cloud, OpenAI, Mercado Pago, ViaCEP e mapas. A tela mostra nomes de variáveis e estado de configuração, nunca valores secretos.
- **Perfil do administrador:** nome, e-mail, telefone e CPF opcional.

O painel de **Administração** continua separado para pedidos operacionais: solicitações, lojas, equipe/contas, financeiro e chamados. Em **Equipe > Contas e segurança**, a opção **Editar dados** permite ao administrador corrigir nome, e-mail, CPF, telefone e endereço, além de redefinir a senha de qualquer conta administrativa. A redefinição revoga os tokens anteriores e exige troca da senha temporária. Confirme a identidade antes de alterar dados cadastrais. O CPF continua obrigatório para clientes e perfis profissionais, e não é chave primária. Somente contas `ADMIN` podem ficar sem CPF.

## API administrativa

Todas as rotas exigem JWT válido com papel `ADMIN`.

| Método e caminho | Uso |
|---|---|
| `GET /api/v1/admin/service-cities` | Lista cidades ativas e suspensas, em ordem alfabética. |
| `POST /api/v1/admin/service-cities` | Cria cidade ativa (`city`, `state`, `postalCode` opcional, `enabled: true`). |
| `PUT /api/v1/admin/service-cities/{id}` | Atualiza cidade/UF/CEP e o estado `enabled`. |
| `GET /api/v1/admin/store-categories` | Lista categorias padrão protegidas e categorias adicionais criadas pela administração. |
| `POST /api/v1/admin/store-categories` | Cria uma categoria personalizada (`name`, `emoji`), disponível imediatamente para vendedores e clientes. |
| `DELETE /api/v1/admin/store-categories/{id}` | Exclui categoria personalizada sem lojas vinculadas. Categorias padrão não têm identificador de exclusão. |
| `GET /api/v1/admin/integrations` | Informa presença das configurações sem revelar segredos. |
| `PUT /api/v1/admin/accounts/{id}/profile` | Corrige dados pessoais de uma conta; CPF omitido preserva o atual. `clearCpf: true` só pode remover CPF da conta administrativa. |

As cidades têm identificador UUID próprio e restrição única por cidade/UF no banco. O CEP é armazenado sem máscara. A lista pública de localização combina cidades de cobertura cadastradas com cidades de lojas aprovadas. Categorias adicionais têm UUID e slug únicos; a lista pública combina as categorias padrão imutáveis com as categorias administrativas persistidas na migração V16. A validação de categoria também é feita no backend, portanto o vendedor não pode enviar categorias inexistentes nem alterar as categorias padrão.

## Credenciais das integrações

As chaves ficam em `Backend/.env` durante o desenvolvimento local ou em um gerenciador de segredos no servidor. **Não são gravadas pelo navegador nem armazenadas na tabela de contas.** Após editar variáveis no ambiente, reinicie a API e use **Definições > Integrações > Atualizar status**.

| Serviço | Variáveis principais | Observação |
|---|---|---|
| SMTP | `SMTP_HOST`, `SMTP_PORT`, `SMTP_USERNAME`, `SMTP_PASSWORD`, `RECOVERY_EMAIL_FROM` | Sem servidor SMTP funcional, e-mails de recuperação não são entregues. |
| WhatsApp Cloud | `WHATSAPP_CLOUD_ENABLED`, `WHATSAPP_PHONE_NUMBER_ID`, `WHATSAPP_ACCESS_TOKEN`, `WHATSAPP_OTP_TEMPLATE` | Exige template de OTP aprovado pela Meta e número autorizado. |
| OpenAI | `OPENAI_API_KEY`, `OPENAI_IMAGE_MODEL` | Usada pelo backend para gerar capas; disponibilidade e cobrança dependem da conta/projeto. |
| Mercado Pago | `MP_CLIENT_ID`, `MP_CLIENT_SECRET`, `MP_TOKEN_ENCRYPTION_KEY`, `MP_WEBHOOK_SECRET`, `MP_NOTIFICATION_BASE_URL` | OAuth, webhooks públicos HTTPS, credenciais de vendedores e ambiente de produção ainda exigem homologação externa. |
| ViaCEP | Sem chave | Serviço público consultado pela interface; o painel apenas identifica que não requer segredo. |
| Mapas | Sem chave no mapa incorporado atual | O mapa requer internet; não é um serviço de rastreamento ou geocodificação homologado. |

O estado **Campos presentes** significa que os valores locais necessários estão preenchidos. Não significa que credenciais foram autenticadas nem que e-mail, WhatsApp, pagamento, IA, CEP ou mapa foram testados de ponta a ponta. Use credenciais de teste primeiro; não cole segredos no chamado ou no chat. A configuração pela tela de administração não foi implementada: gravar e reiniciar segredos através de uma página web exigiria um cofre de segredos e um processo operacional seguro, então este painel informa o caminho suportado sem expor chaves.

## CPF do administrador

`AdminBootstrap` cria a conta administrativa sem exigir CPF. No cadastro/edição de perfil, o CPF é opcional para `ADMIN`; clientes e operadores mantêm a validação obrigatória no cadastro. O banco mantém o CPF como coluna única anulável e o ID UUID é a chave primária. O endpoint administrativo permite corrigir o CPF mediante validação e verificação de duplicidade; para demais contas, o administrador pode substituir, mas não remover, o CPF.
