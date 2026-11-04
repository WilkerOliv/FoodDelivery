# Capas das lojas: upload e aprovação

Implementação e verificação: 04/11/2026.

## Resultado e uso

A capa é uma foto do estabelecimento ou uma logo enviada pelo próprio vendedor. Ela identifica o comércio no cartão do catálogo e no perfil aberto pelo cliente. Não existe geração de imagens por IA, chave de IA ou cobrança por API nesse fluxo. Armazenamento, tráfego e backup continuam consumindo recursos da hospedagem.

### Vendedor

1. Abra **Minha loja > Aparência e pagamentos**. Havendo várias lojas, confira **Loja em edição**.
2. Escolha **PNG ou JPG**, com até **5 MB** e no máximo **4.096 × 4.096 pixels**.
3. Escolha **Logo: mostrar tudo** para preservar a imagem inteira com margens brancas, ou **Foto: preencher a capa** para preencher com recorte central. Não há esticamento nem reposicionamento manual do recorte.
4. Confira a prévia e envie para aprovação. O resultado final tem **1.200 × 600 pixels, proporção fixa 2:1**.
5. Aguarde a decisão. A capa anteriormente aprovada continua visível enquanto a nova aguarda análise. Sem capa anterior, o catálogo mantém sua apresentação padrão.
6. Se houver recusa, leia o motivo e envie outra imagem. A notificação leva à aparência da loja correspondente.

### Administrador

1. Abra **Visão geral > Imagens das lojas**.
2. Confira loja, vendedor e imagem pendente.
3. Use **Aprovar e publicar**, ou informe o motivo e **Recusar imagem**. Recusa sem motivo é bloqueada no navegador e na API.
4. A decisão gera notificação ao vendedor e registro na auditoria. A fila passa a mostrar as pendências restantes.

Aprovar a imagem não aprova ou reativa o estabelecimento. Uma loja precisa continuar aprovada, ativa e com titular habilitado para aparecer aos clientes.

## Contratos HTTP

Todas as rotas abaixo têm prefixo `/api/v1`.

| Método e caminho | Permissão e resultado |
|---|---|
| `POST /merchant/stores/{id}/cover` | MERCHANT titular. JSON `{dataUrl}` com PNG normalizado. Retorna 201 com solicitação PENDING; não altera a capa publicada. |
| `GET /merchant/stores/{id}/cover-submission` | MERCHANT titular. Retorna a prévia da capa aprovada e o último envio, com estado e motivo. |
| `GET /merchant/stores/{id}/published-cover` | MERCHANT titular. Prévia privada da capa vigente, inclusive se a loja estiver temporariamente indisponível ao público. |
| `GET /admin/store-covers` | ADMIN. Lista pendências por data crescente. |
| `PATCH /admin/store-covers/{id}/review` | ADMIN. JSON `{approved: true/false, note: "..."}`. Motivo obrigatório na recusa e limitado a 500 caracteres. |
| `GET /store-cover-submissions/{id}/image` | Apenas titular ou ADMIN. Prévia privada, `Cache-Control: no-store`. |
| `GET /store-assets/{fileName}` | Público somente para arquivo que seja a capa vigente de uma loja disponível. Outros arquivos retornam 404. |

Ausência de sessão retorna 401; papel/titularidade incompatível retorna 403. Conteúdo ou estado inválido é tratado como conflito pelo padrão da API (409); validação de campos obrigatórios/tamanho do contrato usa 400. Dez envios por loja em uma janela móvel de uma hora são permitidos; acima disso retorna 429. Um novo envio substitui a pendência anterior; a imagem substituída não pode mais ser aprovada. Duas decisões para a mesma solicitação não são aceitas.

## Engenharia e segurança

- `Frontend/src/storeCovers.js` mantém seleção, enquadramento, prévia, envio e fila administrativa. Dados como nome da loja e motivo são inseridos como texto, sem executar HTML enviado por usuário. Envio desabilita controles para evitar repetição acidental. Resultado de leitura de arquivo antigo não sobrescreve uma seleção mais recente.
- A transformação para PNG ocorre em canvas 1200 × 600. Foto usa escala que cobre a área e recorta o centro; logo usa escala que cabe na área e centraliza. O teste puro de geometria verifica os dois casos.
- A API não confia no MIME ou na extensão fornecidos pelo navegador: valida base64, limite de bytes, leitura PNG e dimensões antes da decodificação completa. Recodifica a imagem, sem copiar metadados do arquivo de entrada. SVG e outros formatos não entram no contrato.
- O nome no disco é aleatório e criado pelo servidor. Nome original e caminhos enviados pelo usuário não são usados. A resolução do caminho permanece dentro do diretório configurado; respostas usam tipo `image/png` e `nosniff`.
- A rota pública confere a referência vigente e a disponibilidade comercial. Conhecer o identificador de uma solicitação não autoriza ler sua prévia. Arquivos não são expostos diretamente pelo servidor estático do frontend.
- Upload e decisão bloqueiam a mesma linha de loja; a decisão também bloqueia a solicitação. Isso serializa a troca de pendência e a aprovação e protege a cota contra requisições concorrentes. O histórico tem versão otimista.
- A aprovação, a troca da referência da loja, a auditoria e a notificação participam da mesma transação. Falha antes do commit não publica a nova referência. Arquivo recém-gravado é removido se a transação for revertida; o banco e o filesystem não constituem uma transação distribuída.
- Somente a capa vigente é retornada publicamente; substituição troca a URL. A resposta pública exige revalidação (`no-cache`). Uma cópia já baixada ou armazenada em cache antes da mudança não pode ser recolhida do dispositivo do cliente.

## Persistência e operação

A migração **V24** cria `store_cover_submissions`: loja, arquivo único, situação, motivo, data do envio, data/administrador da decisão e versão. Os estados são PENDING, APPROVED, REJECTED e SUPERSEDED. Índices atendem fila e cota por loja/data. A referência pública continua em `stores.cover_image_url`.

Capas anteriormente publicadas são preservadas. A migração não reapresenta automaticamente imagens antigas para aprovação. Todo novo envio e toda substituição passam pelo fluxo novo.

`STORE_ASSETS_DIR` define o diretório de imagens. O diretório padrão normal é `Backend/data/store-assets`; homologação usa armazenamento separado. A aplicação precisa de permissão de leitura/escrita e o diretório não deve ser exposto por um serviço estático independente. Inclua **banco e imagens no backup** e confira a restauração dos dois. A V24 é aplicada pelo Flyway ao iniciar a API.

Os arquivos recusados, substituídos e as capas antigas são mantidos para histórico, com acesso público bloqueado quando não são vigentes. Não existe expurgo automático. Defina retenção e acompanhe espaço em disco antes de operação prolongada. Uma queda abrupta entre gravar o arquivo e confirmar o banco pode deixar arquivo órfão; isso não o publica. Limpeza operacional deve comparar referências antes de excluir qualquer arquivo. Múltiplas instâncias precisam compartilhar armazenamento ou usar adaptação para armazenamento de objetos; disco efêmero de um contêiner não é suficiente.

## Testes e evidências

- **Backend:** suíte Maven com 77 testes; nove específicos cobrem validação PNG/proporção, preservação da capa, substituição, titularidade, arquivo privado, cota, prévia do titular com loja indisponível, aprovação única e motivo obrigatório.
- **Frontend:** 37 testes aprovados, cinco novos para proporção, enquadramento de logo/foto e dimensões inválidas. Sintaxe verificada em 15 módulos.
- **HTTP:** `python3 scripts/test_store_covers.py`, apenas em homologação, confere sessão/papéis, prévia privada, proporção incorreta, substituição, recusa, aprovação e preservação de capa anterior. O roteiro completo `test_homologacao.py` também contém envio e aprovação.
- **Navegador:** seleção de arquivo fictício, enquadramento de foto, envio PENDING, motivo obrigatório ao recusar, aprovação ADMIN e abertura do perfil como cliente. Não houve upload de fotos reais nem uso de serviço externo de imagens.
- **Responsividade:** inspeção em 360 × 800 e 1440 × 1000. No perfil móvel: documento com largura 360, modal 342 e imagem 288,8 × 144,4; sem rolagem horizontal. Imagens carregadas na proporção 2:1.

Evidências visuais:

- [Fila de aprovação](Evidencias_capas_2026-11-04/01-aprovacao-desktop.png).
- [Capa publicada no perfil](Evidencias_capas_2026-11-04/02-capa-publicada.png).
- [Perfil no celular](Evidencias_capas_2026-11-04/03-capa-celular.png).

Esses resultados verificam os cenários descritos. Não demonstram capacidade sob carga, leitura por todos os leitores de tela, resistência a todo arquivo malformado ou restauração de produção. Moderação humana continua necessária para avaliar conteúdo e direito de uso das imagens.
