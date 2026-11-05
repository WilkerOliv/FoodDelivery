# Referência completa de funções — frontend JavaScript

Gerada em 05/11/2026 a partir da árvore sintática do código final. Inclui funções explícitas, métodos privados, construtores e callbacks. Não é uma certificação de segurança: use junto da análise de engenharia e da matriz de testes. Cobertura estrutural: **18 arquivos e 811 funções explícitas**. Hash agregado: `52aaf6fe439f288046d4cf8c97d599f87d3bfaa7af347539d2081eeb7f377401`.

Cada seção identifica localização, assinatura, regras locais, chamadas e efeitos observáveis. Getters implícitos de `record`, métodos herdados de bibliotecas e APIs nativas são descritos por seus contratos, não reproduzidos como código do projeto.

Regeneração: criar ambiente Python isolado, instalar `scripts/reference-requirements.txt` e executar `python scripts/generate_reference.py` na raiz.

## Frontend/src/accessibility.js

Acessibilidade dinâmica. Associa rótulos, identifica diálogos, contém Tab, aceita Escape e devolve foco. Não substitui validação com leitor de tela e pessoas reais.

Fonte: [accessibility.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js) · SHA-256 `64721a1eb93f1acfcf1dfd267bfa1d2b53b8ff8f0677fb0d9e78265d8a243dac`

### initializeAccessibility — linha 2

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:2)

```javascript
function initializeAccessibility(root=document)
```

Executa a etapa `initialize Accessibility` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `node.getClientRects`; `[...dialog.querySelectorAll`; `dialog.querySelectorAll`; `root.querySelectorAll`; `field.querySelector`; `label.contains`; `input.getAttribute`; `[...root.querySelectorAll`; `dialogs.at`; `root.body.classList.toggle`; `Boolean`; `next.setAttribute`; `next.querySelector`; ``; `controls`; `returnFocus.focus`; `root.addEventListener`; `visible`; `activeDialog.querySelector`; `event.preventDefault`; `close.click`; `items.at`; `activeDialog.contains`; `last.focus`; `first.focus`; `observer.observe`; `synchronize`; `observer.disconnect`.

**Decisões condicionais examinadas:**
- `(!input||!label||label.contains(input)||label.htmlFor||input.getAttribute('aria-label'))`
- `(!input.id)`
- `(next)`
- `(heading)`
- `(!heading.id)`
- `(next===activeDialog)`
- `(!activeDialog)`
- `(returnFocus?.isConnected)`
- `(!activeDialog||!visible(activeDialog))`
- `(event.key==='Escape')`
- `(close)`
- `(event.key!=='Tab')`
- `(!items.length)`
- `(event.shiftKey&&(root.activeElement===first||!activeDialog.contains(root.activeElement)))`
- `(!event.shiftKey&&(root.activeElement===last||!activeDialog.contains(root.activeElement)))`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`
- `return;`
- `return;`
- `return ()=>observer.disconnect();`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
next.setAttribute('role','dialog');next.setAttribute('aria-modal','true');
if(heading){if(!heading.id)heading.id=`dialog-title-${++sequence}`;next.setAttribute('aria-labelledby',heading.id);}
root.addEventListener('keydown',event=>{
```

**Decisão de engenharia:** Acessibilidade dinâmica. Associa rótulos, identifica diálogos, contém Tab, aceita Escape e devolve foco. Não substitui validação com leitor de tela e pessoas reais. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### visible — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:6)

```javascript
node=>
```

Executa a etapa `visible` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `node.getClientRects`.

**Decisão de engenharia:** Acessibilidade dinâmica. Associa rótulos, identifica diálogos, contém Tab, aceita Escape e devolve foco. Não substitui validação com leitor de tela e pessoas reais. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### controls — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:7)

```javascript
dialog=>
```

Executa a etapa `controls` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `[...dialog.querySelectorAll`; `dialog.querySelectorAll`.

**Decisão de engenharia:** Acessibilidade dinâmica. Associa rótulos, identifica diálogos, contém Tab, aceita Escape e devolve foco. Não substitui validação com leitor de tela e pessoas reais. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### synchronize — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:8)

```javascript
function synchronize()
```

Executa a etapa `synchronize` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `root.querySelectorAll`; `field.querySelector`; `label.contains`; `input.getAttribute`; `[...root.querySelectorAll`; `dialogs.at`; `root.body.classList.toggle`; `Boolean`; `next.setAttribute`; `next.querySelector`; ``; `controls`; `returnFocus.focus`.

**Decisões condicionais examinadas:**
- `(!input||!label||label.contains(input)||label.htmlFor||input.getAttribute('aria-label'))`
- `(!input.id)`
- `(next)`
- `(heading)`
- `(!heading.id)`
- `(next===activeDialog)`
- `(!activeDialog)`
- `(returnFocus?.isConnected)`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
next.setAttribute('role','dialog');next.setAttribute('aria-modal','true');
if(heading){if(!heading.id)heading.id=`dialog-title-${++sequence}`;next.setAttribute('aria-labelledby',heading.id);}
```

**Decisão de engenharia:** Acessibilidade dinâmica. Associa rótulos, identifica diálogos, contém Tab, aceita Escape e devolve foco. Não substitui validação com leitor de tela e pessoas reais. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@9:45 — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:9)

```javascript
field=>
```

Executa a etapa `callback@9:45` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `field.querySelector`; `label.contains`; `input.getAttribute`.

**Decisões condicionais examinadas:**
- `(!input||!label||label.contains(input)||label.htmlFor||input.getAttribute('aria-label'))`
- `(!input.id)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Acessibilidade dinâmica. Associa rótulos, identifica diálogos, contém Tab, aceita Escape e devolve foco. Não substitui validação com leitor de tela e pessoas reais. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@30:35 — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:30)

```javascript
event=>
```

Executa a etapa `callback@30:35` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `visible`; `activeDialog.querySelector`; `event.preventDefault`; `close.click`; `controls`; `items.at`; `activeDialog.contains`; `last.focus`; `first.focus`.

**Decisões condicionais examinadas:**
- `(!activeDialog||!visible(activeDialog))`
- `(event.key==='Escape')`
- `(close)`
- `(event.key!=='Tab')`
- `(!items.length)`
- `(event.shiftKey&&(root.activeElement===first||!activeDialog.contains(root.activeElement)))`
- `(!event.shiftKey&&(root.activeElement===last||!activeDialog.contains(root.activeElement)))`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`

**Decisão de engenharia:** Acessibilidade dinâmica. Associa rótulos, identifica diálogos, contém Tab, aceita Escape e devolve foco. Não substitui validação com leitor de tela e pessoas reais. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@44:10 — linha 44

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:44)

```javascript
()=>
```

Executa a etapa `callback@44:10` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `observer.disconnect`.

**Decisão de engenharia:** Acessibilidade dinâmica. Associa rótulos, identifica diálogos, contém Tab, aceita Escape e devolve foco. Não substitui validação com leitor de tela e pessoas reais. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/actionGuard.js

Controle visual de ações assíncronas. Evita repetição acidental de clique; idempotência e concorrência continuam sendo responsabilidade do backend.

Fonte: [actionGuard.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/actionGuard.js) · SHA-256 `e676be6890324eaa1ded5877da60ac1acfae36dd7684140a7f7ff6c18cc61463`

### runOnce — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/actionGuard.js:4)

```javascript
async function runOnce(key, action)
```

Executa a etapa `run Once` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `pendingActions.has`; `pendingActions.add`; `action`; `pendingActions.delete`.

**Decisões condicionais examinadas:**
- `(pendingActions.has(key))`

**Saída declarada:**
- `return undefined;`
- `return await action();`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
pendingActions.delete(key);
```

**Decisão de engenharia:** Controle visual de ações assíncronas. Evita repetição acidental de clique; idempotência e concorrência continuam sendo responsabilidade do backend. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/adminProfile.js

Edição administrativa de perfil. CPF é opcional e pode ser explicitamente removido; não reter o valor antigo no estado após a resposta.

Fonte: [adminProfile.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminProfile.js) · SHA-256 `197cf297ad9486189b73e115a21cb772f1784a3bfeb7500c19dca0e9d94883d5`

### clean — linha 1

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminProfile.js:1)

```javascript
value =>
```

Executa a etapa `clean` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Edição administrativa de perfil. CPF é opcional e pode ser explicitamente removido; não reter o valor antigo no estado após a resposta. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### adminProfilePayload — linha 3

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminProfile.js:3)

```javascript
function adminProfilePayload(values, account)
```

Executa a etapa `admin Profile Payload` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `values.get`; `clean`; `String`.

**Decisões condicionais examinadas:**
- `clearCpf`

**Saída declarada:**
- `return { name: String(values.get('name') || '').trim(), email: String(values.get('email') || '').trim(), phone: clean(values.get('phone')), cpf, clearCpf, addressLine: account.addressLine || null, addressComplement: account.addressComplement || null, neighborhood: account.neighborhood || null, city: account.city || null, state: account.state || null, postalCode: account.postalCode || null, };`

**Decisão de engenharia:** Edição administrativa de perfil. CPF é opcional e pode ser explicitamente removido; não reter o valor antigo no estado após a resposta. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### applyAdminProfileResponse — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminProfile.js:21)

```javascript
function applyAdminProfileResponse(account, response, payload)
```

Executa a etapa `apply Admin Profile Response` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `(payload.clearCpf)`
- `(payload.cpf)`

**Saída declarada:**
- `return next;`

**Decisão de engenharia:** Edição administrativa de perfil. CPF é opcional e pode ser explicitamente removido; não reter o valor antigo no estado após a resposta. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/adminSections.js

Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API.

Fonte: [adminSections.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js) · SHA-256 `904e50a385b55fd2e7cecb7e732e45e655e5f47289e106185a733d71acdf43da`

### renderAdminAreas — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:18)

```javascript
function renderAdminAreas(content, section)
```

Executa a etapa `render Admin Areas` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** ``.

**Saída declarada:**
- `return (adminAreasBySection[section] || adminAreasBySection.overview) .map(name => content[name] || '') .join('');`

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@20:10 — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:20)

```javascript
name =>
```

Executa a etapa `callback@20:10` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderAdminTeamArea — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:24)

```javascript
function renderAdminTeamArea(content, subsection)
```

Executa a etapa `render Admin Team Area` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** ``.

**Saída declarada:**
- `return (adminTeamAreas[subsection] || adminTeamAreas.profiles) .map(name => content[name] || '') .join('');`

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@26:10 — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:26)

```javascript
name =>
```

Executa a etapa `callback@26:10` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### supportDetailPath — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:30)

```javascript
function supportDetailPath(isAdmin, id)
```

Executa a etapa `support Detail Path` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `encodeURIComponent`.

**Decisões condicionais examinadas:**
- `isAdmin`

**Saída declarada:**
- `return isAdmin ? \`/admin/support-tickets/${ticketId}\` : \`/support/tickets/${ticketId}\`;`

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### supportMessagePath — linha 35

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:35)

```javascript
function supportMessagePath(isAdmin, id)
```

Executa a etapa `support Message Path` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `encodeURIComponent`.

**Decisões condicionais examinadas:**
- `isAdmin`

**Saída declarada:**
- `return isAdmin ? \`/admin/support-tickets/${ticketId}/messages\` : \`/support/tickets/${ticketId}/messages\`;`

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeApprovalPath — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:42)

```javascript
function storeApprovalPath(storeId, platformRequestId)
```

Executa a etapa `store Approval Path` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `encodeURIComponent`.

**Decisões condicionais examinadas:**
- `(platformRequestId)`

**Saída declarada:**
- `return \`/admin/platform-requests/${encodeURIComponent(platformRequestId)}/review\`;`
- `return \`/admin/stores/${encodeURIComponent(storeId)}/approval\`;`

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### adminStoreStatus — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:47)

```javascript
function adminStoreStatus(store)
```

Executa a etapa `admin Store Status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `(!store.active)`
- `store.approved`

**Saída declarada:**
- `return 'STORE_SUSPENDED';`
- `return store.approved ? 'CONFIRMED' : 'PENDING_REVIEW';`

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### filterAdminStores — linha 52

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:52)

```javascript
function filterAdminStores(stores = [], status = 'ALL')
```

Executa a etapa `filter Admin Stores` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.filter`; `adminStoreStatus`.

**Saída declarada:**
- `return stores.filter(store => status === 'ALL' || adminStoreStatus(store) === status);`

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@53:24 — linha 53

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:53)

```javascript
store =>
```

Executa a etapa `callback@53:24` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `adminStoreStatus`.

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeApprovalId — linha 56

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSections.js:56)

```javascript
function storeApprovalId(button)
```

Executa a etapa `store Approval Id` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return button?.dataset?.approveStore;`

**Decisão de engenharia:** Separação e normalização das seções administrativas. Filtros e contagens devem refletir os dados autorizados retornados pela API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/adminStoreEditor.js

Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração.

Fonte: [adminStoreEditor.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js) · SHA-256 `1ad966f69cb0ea3b6cb8074be77f05378c2ed946657fc743fab3b8d1c5865fe2`

### editStoreDialog — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:9)

```javascript
function editStoreDialog(store,categories,{onSaved,onError,onHoursSaved=()=>{}})
```

Executa a etapa `edit Store Dialog` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.createElement`; `overlay.querySelector`; `formatCnpjMask`; `formatCurrencyValue`; `label.append`; `document.createTextNode`; `input.append`; `String`; `['name','category','address','city','state','timezone'].includes`; `input.addEventListener`; `form.append`; `form.insertAdjacentHTML`; `storeMapFields`; `fulfillmentFields`; `overlay.remove`; `event.preventDefault`; `parseCurrencyDecimal`; `data.get`; `Number.isFinite`; `Object.fromEntries`; `['name','description','address','category','city','state','cnpj','timezone'].map`; `payload.state.toUpperCase`; `payload.cnpj.replace`; `Math.round`; `Object.assign`; `coordinatesFromForm`; `fulfillmentPayload`; `api`; `encodeURIComponent`; `JSON.stringify`; `onSaved`; `onError`; `hours.querySelector`; `mountOperatingHours`; `renderHours`; `onHoursSaved`; `document.body.append`; `mountLocationPicker`; `form.querySelector`.

**Rejeições locais:**
- `throw new Error('Informe uma taxa entre R$ 0,00 e R$ 1.000,00.');`

**Decisões condicionais examinadas:**
- `type==='select'`
- `type==='textarea'`
- `(input.tagName==='INPUT')`
- `(type==='select')`
- `(limits[name])`
- `(name==='cnpj')`
- `(event.target===overlay)`
- `(save.disabled)`
- `(!Number.isFinite(fee)||fee<0||fee>1000)`
- `(!overlay.isConnected)`

**Saída declarada:**
- `return;`
- `return;`
- `return overlay;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
overlay.innerHTML='<section class="modal admin-edit-modal"><div class="modal-head"><h2>Editar dados da loja</h2><button class="close-button" type="button" aria-label="Fechar">×</button></div><p>Confira as informações antes de salvar. Aprovação e suspensão são controladas na lista de lojas.</p><form class="form-grid"></form></section>';
if(type==='select'){for(const category of categories){const option=document.createElement('option');option.value=category;option.textContent=category;input.append(option);}}
if(name==='cnpj')input.addEventListener('input',()=>{input.value=formatCnpjMask(input.value);});
const save=document.createElement('button');save.type='submit';save.className='btn span-2';save.textContent='Salvar dados da loja';form.append(save);
const updated=await api(`/admin/stores/${encodeURIComponent(store.id)}/profile`,{method:'PUT',body:JSON.stringify(payload)});overlay.remove();await onSaved(updated);
const hours=document.createElement('section');hours.className='admin-hours-editor';hours.innerHTML='<h3>Funcionamento da loja</h3><p>Perfil e funcionamento são salvos separadamente. Alterações que afetam reservas exigem uma decisão na prévia.</p><button class="btn outline" type="button">Configurar dias, turnos e exceções</button><div data-admin-hours hidden></div>';overlay.querySelector('.modal').append(hours);
hoursButton.onclick=async()=>{hoursButton.disabled=true;try{const config=await api(hoursPath);if(!overlay.isConnected)return;hoursRoot.hidden=false;renderHours(config);hoursButton.hidden=true;}catch(error){hoursButton.disabled=false;onError(error);}};
```

**Decisão de engenharia:** Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@9:80 — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:9)

```javascript
()=>
```

Executa a etapa `callback@9:80` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@21:53 — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:21)

```javascript
()=>
```

Executa a etapa `callback@21:53` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `formatCnpjMask`.

**Decisão de engenharia:** Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@26:50 — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:26)

```javascript
()=>
```

Executa a etapa `callback@26:50` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `overlay.remove`.

**Decisão de engenharia:** Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@27:19 — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:27)

```javascript
event=>
```

Executa a etapa `callback@27:19` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `overlay.remove`.

**Decisões condicionais examinadas:**
- `(event.target===overlay)`

**Decisão de engenharia:** Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@28:17 — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:28)

```javascript
async event=>
```

Executa a etapa `callback@28:17` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `parseCurrencyDecimal`; `data.get`; `Number.isFinite`; `Object.fromEntries`; `['name','description','address','category','city','state','cnpj','timezone'].map`; `String`; `payload.state.toUpperCase`; `payload.cnpj.replace`; `Math.round`; `Object.assign`; `coordinatesFromForm`; `fulfillmentPayload`; `api`; `encodeURIComponent`; `JSON.stringify`; `overlay.remove`; `onSaved`; `onError`.

**Rejeições locais:**
- `throw new Error('Informe uma taxa entre R$ 0,00 e R$ 1.000,00.');`

**Decisões condicionais examinadas:**
- `(save.disabled)`
- `(!Number.isFinite(fee)||fee<0||fee>1000)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const updated=await api(`/admin/stores/${encodeURIComponent(store.id)}/profile`,{method:'PUT',body:JSON.stringify(payload)});overlay.remove();await onSaved(updated);
```

**Decisão de engenharia:** Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@31:121 — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:31)

```javascript
key=>
```

Executa a etapa `callback@31:121` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`; `data.get`.

**Decisão de engenharia:** Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderHours — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:38)

```javascript
config=>
```

Executa a etapa `render Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `mountOperatingHours`; `renderHours`; `onHoursSaved`.

**Decisão de engenharia:** Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@38:122 — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:38)

```javascript
(configuration,summary)=>
```

Executa a etapa `callback@38:122` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `renderHours`; `onHoursSaved`.

**Decisão de engenharia:** Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@39:23 — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:39)

```javascript
async()=>
```

Executa a etapa `callback@39:23` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `renderHours`; `onError`.

**Decisões condicionais examinadas:**
- `(!overlay.isConnected)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async()=>{hoursButton.disabled=true;try{const config=await api(hoursPath);if(!overlay.isConnected)return;hoursRoot.hidden=false;renderHours(config);hoursButton.hidden=true;}catch(error){hoursButton.disabled=false;onError(error);}}
```

**Decisão de engenharia:** Editor administrativo criado com elementos DOM e valores textuais. Evita interpolar dados de usuário em atributos HTML; a API valida novamente cada alteração. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/api.js

Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade.

Fonte: [api.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/api.js) · SHA-256 `54f9243ea9d97d59362824f26b99cd1c8cf5ca5cec24af0cd095adbcb3d669cb`

### apiAssetUrl — linha 3

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/api.js:3)

```javascript
(path) =>
```

Executa a etapa `api Asset Url` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `path`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### api — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/api.js:8)

```javascript
async function api(path, options = {})
```

Executa a etapa `api` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `fetch`; `['/auth/login', '/auth/register', '/auth/logout', '/account/me'].includes`; `localStorage.removeItem`; `window.dispatchEvent`; `response.json`; `friendlyStatus`; `response.headers.get`; `type.includes`.

**Rejeições locais:**
- `throw new Error('Não conseguimos falar com o servidor. Confira se o banco e o backend estão iniciados e tente novamente.');`
- `throw new Error(message);`

**Decisões condicionais examinadas:**
- `(response.status === 401 && !['/auth/login', '/auth/register', '/auth/logout', '/account/me'].includes(path))`
- `(!response.ok)`
- `(response.status === 204)`
- `type.includes('application/json')`

**Saída declarada:**
- `return null;`
- `return type.includes('application/json') ? response.json() : null;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
localStorage.removeItem('pronto_account');
```

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### friendlyStatus — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/api.js:31)

```javascript
function friendlyStatus(status)
```

Executa a etapa `friendly Status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `(status === 400)`
- `(status === 401)`
- `(status === 403)`
- `(status === 404)`
- `(status === 409)`
- `(status >= 500)`

**Saída declarada:**
- `return 'Alguns dados precisam de atenção. Confira o formulário e tente novamente.';`
- `return 'Seus dados de acesso não foram aceitos. Confira o e-mail e a senha.';`
- `return 'Esta área não está disponível para o seu perfil.';`
- `return 'Não encontramos esse registro. Atualize a página e tente novamente.';`
- `return 'Essa ação entrou em conflito com uma atualização recente. Recarregue e tente de novo.';`
- `return 'Tivemos um problema no servidor. Seus dados foram preservados; tente novamente em instantes.';`
- `return 'Não foi possível concluir agora. Tente novamente.';`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### money — linha 41

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/api.js:41)

```javascript
(cents = 0) =>
```

Executa a etapa `money` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `new Intl.NumberFormat`.

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### shortDate — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/api.js:42)

```javascript
(date) =>
```

Executa a etapa `short Date` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `new Intl.DateTimeFormat`.

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/catalogPolicy.js

Regras visuais de catálogo, região e pagamento posterior. A política definitiva é aplicada na API, pois a cidade selecionada no navegador é manipulável.

Fonte: [catalogPolicy.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogPolicy.js) · SHA-256 `70112de635c254ddfe69bb3eb2d4e2248d7eb13ba12d39de617cb10cbb34471d`

### normalizeCity — linha 1

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogPolicy.js:1)

```javascript
function normalizeCity(value)
```

Executa a etapa `normalize City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Saída declarada:**
- `return String(value || '').normalize('NFD').replace(/[\u0300-\u036f]/g, '').trim().toLocaleLowerCase('pt-BR');`

**Decisão de engenharia:** Regras visuais de catálogo, região e pagamento posterior. A política definitiva é aplicada na API, pois a cidade selecionada no navegador é manipulável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### categoryMatches — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogPolicy.js:18)

```javascript
function categoryMatches(storeCategory, selectedCategory)
```

Executa a etapa `category Matches` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `normalizeCity`; `normalize`; `Object.entries`; `aliases.some`; `categoryAliases[canonical].some`.

**Decisões condicionais examinadas:**
- `(!selected || !store)`
- `(canonical)`

**Saída declarada:**
- `return false;`
- `return categoryAliases[canonical].some(alias => normalize(alias) === store);`
- `return selected === store;`

**Decisão de engenharia:** Regras visuais de catálogo, região e pagamento posterior. A política definitiva é aplicada na API, pois a cidade selecionada no navegador é manipulável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### normalize — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogPolicy.js:19)

```javascript
value =>
```

Executa a etapa `normalize` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `normalizeCity`.

**Decisão de engenharia:** Regras visuais de catálogo, região e pagamento posterior. A política definitiva é aplicada na API, pois a cidade selecionada no navegador é manipulável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@23:58 — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogPolicy.js:23)

```javascript
([, aliases]) =>
```

Executa a etapa `callback@23:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `aliases.some`; `normalize`.

**Decisão de engenharia:** Regras visuais de catálogo, região e pagamento posterior. A política definitiva é aplicada na API, pois a cidade selecionada no navegador é manipulável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@23:88 — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogPolicy.js:23)

```javascript
alias =>
```

Executa a etapa `callback@23:88` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `normalize`.

**Decisão de engenharia:** Regras visuais de catálogo, região e pagamento posterior. A política definitiva é aplicada na API, pois a cidade selecionada no navegador é manipulável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@24:57 — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogPolicy.js:24)

```javascript
alias =>
```

Executa a etapa `callback@24:57` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `normalize`.

**Decisão de engenharia:** Regras visuais de catálogo, região e pagamento posterior. A política definitiva é aplicada na API, pois a cidade selecionada no navegador é manipulável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### customerOutsideStoreCity — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogPolicy.js:28)

```javascript
function customerOutsideStoreCity(customer, store)
```

Executa a etapa `customer Outside Store City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `normalizeCity`; `String`.

**Decisões condicionais examinadas:**
- `(!customer?.city || !customer?.state || !store?.city || !store?.state)`

**Saída declarada:**
- `return true;`
- `return normalizeCity(customer.city) !== normalizeCity(store.city) || String(customer.state).trim().toUpperCase() !== String(store.state).trim().toUpperCase();`

**Decisão de engenharia:** Regras visuais de catálogo, região e pagamento posterior. A política definitiva é aplicada na API, pois a cidade selecionada no navegador é manipulável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### catalogItemIcon — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogPolicy.js:34)

```javascript
function catalogItemIcon(serviceItem)
```

Executa a etapa `catalog Item Icon` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `serviceItem`

**Saída declarada:**
- `return serviceItem ? '🗓️' : '📦';`

**Decisão de engenharia:** Regras visuais de catálogo, região e pagamento posterior. A política definitiva é aplicada na API, pois a cidade selecionada no navegador é manipulável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/demoInbox.js

Caixa de mensagens fictícias. Exige administração na API e existe para conferir avisos sem entregar mensagem a pessoas reais.

Fonte: [demoInbox.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/demoInbox.js) · SHA-256 `6ccc2d3ae6e3af63832d1cf684f761ec9a2622ea3c3552701f6e2304ba56363a`

### refresh — linha 3

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/demoInbox.js:3)

```javascript
async function refresh()
```

Executa a etapa `refresh` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `document.querySelector`; `target.replaceChildren`; `document.createElement`; `new Date`; `target.append`.

**Decisões condicionais examinadas:**
- `(!globalThis.FOODDELIVERY_DEMO)`
- `messages.length`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if(!globalThis.FOODDELIVERY_DEMO){status.textContent='Disponível somente no ambiente de homologação.';return;}
try{const messages=await api('/demo/messages');const target=document.querySelector('#messages');target.replaceChildren();
for(const message of messages){const card=document.createElement('article');card.className='order-card';card.textContent=`${message.channel} · ${message.recipient} · Código: ${message.code} · ${new Date(message.createdAt).toLocaleString('pt-BR')}`;target.append(card);}
status.textContent=messages.length?`${messages.length} mensagem(ns) capturada(s).`:'Nenhuma mensagem capturada. Solicite recuperação de senha no site.';
}catch(error){status.textContent=error.message;}
```

**Decisão de engenharia:** Caixa de mensagens fictícias. Exige administração na API e existe para conferir avisos sem entregar mensagem a pessoas reais. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@10:65 — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/demoInbox.js:10)

```javascript
async event=>
```

Executa a etapa `callback@10:65` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `api`; `JSON.stringify`; `Object.fromEntries`; `refresh`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async event=>{event.preventDefault();const form=new FormData(event.currentTarget);try{await api('/auth/login',{method:'POST',body:JSON.stringify(Object.fromEntries(form))});await refresh();}catch(error){status.textContent=error.message;}}
```

**Decisão de engenharia:** Caixa de mensagens fictícias. Exige administração na API e existe para conferir avisos sem entregar mensagem a pessoas reais. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/inputMasks.js

Apresentação e interpretação de CPF, CNPJ, CEP, telefone e dinheiro. Máscara melhora digitação; não prova identidade e não substitui validação no servidor.

Fonte: [inputMasks.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/inputMasks.js) · SHA-256 `244e92d3e625b6d0f1f6240f89b84e15dd3c3754c723d5c1ee5fe4748927d315`

### digitsOnly — linha 1

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/inputMasks.js:1)

```javascript
value =>
```

Executa a etapa `digits Only` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Apresentação e interpretação de CPF, CNPJ, CEP, telefone e dinheiro. Máscara melhora digitação; não prova identidade e não substitui validação no servidor. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### formatCpfMask — linha 3

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/inputMasks.js:3)

```javascript
function formatCpfMask(value)
```

Executa a etapa `format Cpf Mask` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `digitsOnly`; `digits .replace`.

**Saída declarada:**
- `return digits .replace(/^(\d{3})(\d)/, '$1.$2') .replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3') .replace(/\.(\d{3})(\d{1,2})$/, '.$1-$2');`

**Decisão de engenharia:** Apresentação e interpretação de CPF, CNPJ, CEP, telefone e dinheiro. Máscara melhora digitação; não prova identidade e não substitui validação no servidor. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### formatCnpjMask — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/inputMasks.js:11)

```javascript
function formatCnpjMask(value)
```

Executa a etapa `format Cnpj Mask` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `digitsOnly`; `digits .replace`.

**Saída declarada:**
- `return digits .replace(/^(\d{2})(\d)/, '$1.$2') .replace(/^(\d{2})\.(\d{3})(\d)/, '$1.$2.$3') .replace(/\.(\d{3})(\d)/, '.$1/$2') .replace(/(\d{4})(\d{1,2})$/, '$1-$2');`

**Decisão de engenharia:** Apresentação e interpretação de CPF, CNPJ, CEP, telefone e dinheiro. Máscara melhora digitação; não prova identidade e não substitui validação no servidor. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### formatCurrencyMask — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/inputMasks.js:22)

```javascript
function formatCurrencyMask(value)
```

Executa a etapa `format Currency Mask` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Number`; `digitsOnly`; `currencyFormatter.format`.

**Saída declarada:**
- `return currencyFormatter.format(cents / 100);`

**Decisão de engenharia:** Apresentação e interpretação de CPF, CNPJ, CEP, telefone e dinheiro. Máscara melhora digitação; não prova identidade e não substitui validação no servidor. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### formatCurrencyValue — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/inputMasks.js:27)

```javascript
function formatCurrencyValue(decimalValue)
```

Executa a etapa `format Currency Value` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Number`; `currencyFormatter.format`; `Number.isFinite`.

**Decisões condicionais examinadas:**
- `Number.isFinite(value)`

**Saída declarada:**
- `return currencyFormatter.format(Number.isFinite(value) ? value : 0);`

**Decisão de engenharia:** Apresentação e interpretação de CPF, CNPJ, CEP, telefone e dinheiro. Máscara melhora digitação; não prova identidade e não substitui validação no servidor. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### parseCurrencyDecimal — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/inputMasks.js:32)

```javascript
function parseCurrencyDecimal(value)
```

Executa a etapa `parse Currency Decimal` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`; `normalized.includes`; `normalized.replace`; `Number`; `Number.isFinite`.

**Decisões condicionais examinadas:**
- `(normalized.includes(','))`
- `Number.isFinite(parsed)`

**Saída declarada:**
- `return Number.isFinite(parsed) ? parsed : NaN;`

**Decisão de engenharia:** Apresentação e interpretação de CPF, CNPJ, CEP, telefone e dinheiro. Máscara melhora digitação; não prova identidade e não substitui validação no servidor. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/main.js

Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea.

Fonte: [main.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js) · SHA-256 `3c7217c98fdee1613560223ead67c5840a8af73c8b2457583e02cc9e9590ebe4`

### $ — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:19)

```javascript
(selector, root = document) =>
```

Executa a etapa `$` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `root.querySelector`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### statusClass — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:32)

```javascript
s =>
```

Executa a etapa `status Class` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `['COMPLETED','DELIVERED','CONFIRMED','REFUNDED','CLOSED'].includes`; `['REJECTED','CANCELLED','NO_SHOW','REFUND_REVIEW','STORE_SUSPENDED'].includes`; `['ACCEPTED','PREPARING','READY','IN_SERVICE','ASSIGNED','PICKED_UP','IN_TRANSIT','IN_PROGRESS'].includes`.

**Decisões condicionais examinadas:**
- `['COMPLETED','DELIVERED','CONFIRMED','REFUNDED','CLOSED'].includes(s)`
- `['REJECTED','CANCELLED','NO_SHOW','REFUND_REVIEW','STORE_SUSPENDED'].includes(s)`
- `['ACCEPTED','PREPARING','READY','IN_SERVICE','ASSIGNED','PICKED_UP','IN_TRANSIT','IN_PROGRESS'].includes(s)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### esc — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:33)

```javascript
value =>
```

Executa a etapa `esc` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@33:62 — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:33)

```javascript
c =>
```

Executa a etapa `callback@33:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### icon — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:34)

```javascript
function icon(name,size=18)
```

Executa a etapa `icon` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return \`<svg aria-hidden="true" width="${size}" height="${size}" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="${paths[name]||paths.grid}"/></svg>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@39:23 — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:39)

```javascript
() =>
```

Executa a etapa `callback@39:23` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `JSON.parse`; `localStorage.getItem`; `localStorage.setItem`; `JSON.stringify`.

**Decisões condicionais examinadas:**
- `(!raw)`

**Saída declarada:**
- `return null;`
- `return hint;`
- `return null;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const raw=JSON.parse(localStorage.getItem('pronto_account') || 'null');
localStorage.setItem('pronto_account',JSON.stringify(hint));
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### persistAccountHint — linha 48

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:48)

```javascript
function persistAccountHint(account)
```

Executa a etapa `persist Account Hint` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `localStorage.removeItem`; `localStorage.setItem`; `JSON.stringify`.

**Decisões condicionais examinadas:**
- `(!account)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if (!account) { localStorage.removeItem('pronto_account'); return; }
localStorage.setItem('pronto_account', JSON.stringify(hint));
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@53:24 — linha 53

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:53)

```javascript
() =>
```

Executa a etapa `callback@53:24` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `JSON.parse`; `localStorage.getItem`.

**Saída declarada:**
- `return JSON.parse(localStorage.getItem('fooddelivery_location') || 'null');`
- `return null;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
() => { try { return JSON.parse(localStorage.getItem('fooddelivery_location') || 'null'); } catch { return null; } }
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### knownPage — linha 54

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:54)

```javascript
page =>
```

Executa a etapa `known Page` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Object.hasOwn`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### notificationKey — linha 55

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:55)

```javascript
accountId =>
```

Executa a etapa `notification Key` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### readLocalNotifications — linha 56

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:56)

```javascript
() =>
```

Executa a etapa `read Local Notifications` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### notify — linha 80

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:80)

```javascript
function notify(title, message, kind = 'success', persist = false)
```

Executa a etapa `notify` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `/cannot read properties|typeerror|referenceerror|nullpointerexception|sql exception|stack trace/i.test`; `String`; `crypto.randomUUID`; `new Date`; `[entry, ...state.notifications].slice`; `localStorage.setItem`; `notificationKey`; `JSON.stringify`; `state.notifications.filter`; `$`; `[...region.querySelectorAll`; `region.querySelectorAll`; `document.createElement`; `toast.setAttribute`; `esc`; `toast.querySelector`; `toast.remove`; `region.append`; `region.querySelector`; `setTimeout`; `toast.classList.add`.

**Decisões condicionais examinadas:**
- `kind==='error'&&/cannot read properties|typeerror|referenceerror|nullpointerexception|sql exception|stack trace/i.test(String(message))`
- `(persist)`
- `(region)`
- `([...region.querySelectorAll('.toast')].some(item=>item.dataset.signature===signature))`
- `kind==='error'`
- `kind === 'error'`
- `kind === 'info'`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
localStorage.setItem(notificationKey(state.account?.id), JSON.stringify(state.notifications.filter(n => n.localOnly !== false).slice(0,40)));
toast.setAttribute('role',kind==='error'?'alert':'status');
toast.setAttribute('aria-live',kind==='error'?'assertive':'polite');
toast.innerHTML = `<span class="toast-icon" aria-hidden="true">${kind === 'error' ? '⚠' : kind === 'info' ? 'ℹ' : '✓'}</span><div class="toast-copy"><strong>${esc(title)}</strong><p>${esc(safeMessage)}</p></div><button class="toast-dismiss" type="button" aria-label="Dispensar aviso">×</button>`;
toast.querySelector('.toast-dismiss').addEventListener('click',()=>toast.remove());
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@87:104 — linha 87

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:87)

```javascript
n =>
```

Executa a etapa `callback@87:104` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@92:52 — linha 92

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:92)

```javascript
item=>
```

Executa a etapa `callback@92:52` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@99:68 — linha 99

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:99)

```javascript
()=>
```

Executa a etapa `callback@99:68` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `toast.remove`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@102:16 — linha 102

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:102)

```javascript
()=>
```

Executa a etapa `callback@102:16` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `toast.classList.add`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@103:16 — linha 103

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:103)

```javascript
() =>
```

Executa a etapa `callback@103:16` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `toast.remove`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@107:44 — linha 107

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:107)

```javascript
() =>
```

Executa a etapa `callback@107:44` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `resetChatState`; `stopNotificationPolling`; `stopCourierLocation`; `localStorage.removeItem`; `readLocalNotifications`; `notify`; `render`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
localStorage.removeItem('pronto_account');
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### role — linha 130

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:130)

```javascript
() =>
```

Executa a etapa `role` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Array.isArray`; `granted.includes`.

**Decisões condicionais examinadas:**
- `(Array.isArray(granted)&&granted.length)`
- `(state.activeRole&&granted.includes(state.activeRole))`
- `(granted.includes(state.account.role))`

**Saída declarada:**
- `return state.activeRole;`
- `return state.account.role;`
- `return granted[0];`
- `return state.account?.role || 'CUSTOMER';`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### canShop — linha 135

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:135)

```javascript
() =>
```

Executa a etapa `can Shop` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `role`; `state.account?.grantedRoles?.includes`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### initials — linha 136

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:136)

```javascript
name =>
```

Executa a etapa `initials` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** ``.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@136:79 — linha 136

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:136)

```javascript
n =>
```

Executa a etapa `callback@136:79` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### fullAddress — linha 137

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:137)

```javascript
account =>
```

Executa a etapa `full Address` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `[account?.addressLine,account?.addressComplement,account?.neighborhood,account?.city,account?.state,account?.postalCode].filter`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### displayName — linha 138

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:138)

```javascript
() =>
```

Executa a etapa `display Name` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** ``.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### formatStatus — linha 139

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:139)

```javascript
s =>
```

Executa a etapa `format Status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### pill — linha 140

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:140)

```javascript
s =>
```

Executa a etapa `pill` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `statusClass`; `esc`; `formatStatus`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refundNotice — linha 141

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:141)

```javascript
status =>
```

Executa a etapa `refund Notice` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `status==='REFUND_PENDING'`
- `status==='REFUND_REVIEW'`
- `status==='REFUNDED'`
- `globalThis.FOODDELIVERY_DEMO`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### applyPublicCategories — linha 143

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:143)

```javascript
function applyPublicCategories(categories)
```

Executa a etapa `apply Public Categories` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Array.isArray`; `categoryOptions.splice`; `categories.map`; `categories.forEach`.

**Decisões condicionais examinadas:**
- `(!Array.isArray(categories)||!categories.length)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@143:169 — linha 143

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:143)

```javascript
category=>
```

Executa a etapa `callback@143:169` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@143:214 — linha 143

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:143)

```javascript
category=>
```

Executa a etapa `callback@143:214` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### boot — linha 145

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:145)

```javascript
async function boot()
```

Executa a etapa `boot` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.createElement`; `document.body.classList.add`; `document.body.prepend`; `new ResizeObserver`; `document.body.style.setProperty`; `location.hash.slice`; `knownPage`; `render`; `api`; `applyPublicCategories`; `state.account.grantedRoles?.includes`; `persistAccountHint`; `new URLSearchParams`; `notify`; `url.searchParams.delete`; `history.replaceState`; `params.get`; `['success','pending','failure'].includes`; `handlePaymentReturn`; `navByRole[role`; `role`; `['categories','categoryStores'].includes`; `canShop`; `readLocalNotifications`; `loadNotifications`; `startNotificationPolling`; `loadAddresses`; `loadCurrentPage`.

**Decisões condicionais examinadas:**
- `(globalThis.FOODDELIVERY_DEMO)`
- `(knownPage(initialPage))`
- `state.account.grantedRoles?.includes(preferredRole)`
- `(mpResult==='connected'||mpResult==='error')`
- `mpResult==='connected'`
- `(state.account)`
- `(paymentId&&['success','pending','failure'].includes(paymentReturn))`
- `(state.mustChangePassword)`
- `(!navByRole[role()].some(([page])=>page===state.page)&&!(['categories','categoryStores'].includes(state.page)&&canShop()))`
- `role()==='CUSTOMER'`
- `role()==='MERCHANT'`
- `role()==='COURIER'`
- `(state.account.grantedRoles?.includes('CUSTOMER'))`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if(globalThis.FOODDELIVERY_DEMO){const notice=document.createElement('aside');notice.className='demo-environment-banner';notice.innerHTML='HOMOLOGAÇÃO · Cadastros fictícios · Sem dinheiro ou mensagens reais <a href="/demo.html" target="_blank" rel="noopener">Abrir caixa de mensagens de teste ↗</a>';document.body.classList.add('demo-mode');document.body.prepend(notice);new ResizeObserver(()=>document.body.style.setProperty('--demo-banner-height',`${notice.offsetHeight}px`)).observe(notice);}
try { await api('/status'); state.backendStatus = 'online'; } catch { state.backendStatus = 'offline'; }
try { applyPublicCategories(await api('/store-categories')); } catch { /* categorias locais mantêm a navegação disponível durante indisponibilidade da API */ }
state.profile=await api('/account/me');
if(mpResult==='connected'||mpResult==='error'){notify(mpResult==='connected'?'Conta Mercado Pago conectada':'Não foi possível conectar o Mercado Pago',mpResult==='connected'?'Esta loja já pode receber pagamentos pelo checkout.':'A autorização foi cancelada ou expirou. Tente conectar novamente.',mpResult==='connected'?'success':'error');const url=new URL(location.href);url.searchParams.delete('mp');history.replaceState({page:'merchant'},'',url.pathname+url.search+'#merchant');}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@146:400 — linha 146

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:146)

```javascript
()=>
```

Executa a etapa `callback@146:400` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.body.style.setProperty`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
()=>document.body.style.setProperty('--demo-banner-height',`${notice.offsetHeight}px`)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@162:436 — linha 162

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:162)

```javascript
([page])=>
```

Executa a etapa `callback@162:436` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@162:735 — linha 162

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:162)

```javascript
()=>
```

Executa a etapa `callback@162:735` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### loadCurrentPage — linha 166

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:166)

```javascript
async function loadCurrentPage()
```

Executa a etapa `load Current Page` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `loadStores`; `api`; `loadNotifications`; `role`; `Promise.allSettled`; `results.filter`; `notify`; `messages.push`; `state.ownStores.some`; `String`; `activeMerchantStore`; `messages.join`; `Promise.all`; `Promise.resolve`; `loadAdminData`.

**Decisões condicionais examinadas:**
- `(state.page === 'home')`
- `(state.page === 'categories' || state.page === 'categoryStores')`
- `(state.page === 'orders')`
- `(state.page === 'reservations')`
- `(state.page === 'notifications')`
- `(state.page === 'account')`
- `(role()==='ADMIN')`
- `(results[0].status==='fulfilled')`
- `(results[1].status==='fulfilled')`
- `(results[2].status==='fulfilled')`
- `(results[3].status==='fulfilled')`
- `(failed)`
- `(state.page === 'merchant')`
- `(base[0].status==='fulfilled')`
- `(base[1].status==='fulfilled')`
- `(base[2].status==='fulfilled')`
- `(!state.ownStores.some(s=>String(s.id)===String(state.merchantStoreId)))`
- `(active)`
- `(extras[0].status==='fulfilled')`
- `(extras[1].status==='fulfilled')`
- `(extras[2].status==='fulfilled')`
- `messages.length`
- `(state.page === 'merchantOrders')`
- `(state.page === 'merchantReservations')`
- `store`
- `(state.page === 'courier')`
- `(state.page === 'finance')`
- `(role()==='MERCHANT')`
- `(!state.ownStores.length)`
- `(role()==='COURIER')`
- `(state.page === 'admin')`
- `(state.page==='admin')`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if (state.page === 'orders') state.orders = await api('/orders/mine');
if (state.page === 'reservations') state.reservations = await api('/reservations/mine');
} else state.platformRequests=await api('/account/platform-requests');
if (state.page === 'merchantOrders') state.orders = await api('/merchant/orders');
if (state.page === 'courier') { state.offers = await api('/courier/offers'); state.deliveries = await api('/courier/deliveries'); }
if (!state.ownStores.length) state.ownStores=await api('/merchant/stores/mine');
const store=activeMerchantStore(); state.paymentConnection=store?await api(`/merchant/stores/${store.id}/mercadopago/status`):null;
} else if (role()==='COURIER') state.deliveries=await api('/courier/deliveries');
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@180:37 — linha 180

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:180)

```javascript
result=>
```

Executa a etapa `callback@180:37` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@191:32 — linha 191

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:191)

```javascript
s=>
```

Executa a etapa `callback@191:32` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@203:35 — linha 203

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:203)

```javascript
s=>
```

Executa a etapa `callback@203:35` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### loadAdminData — linha 211

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:211)

```javascript
async function loadAdminData()
```

Executa a etapa `load Admin Data` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Promise.allSettled`; `jobs.map`; `api`; `results.forEach`; `failed.push`.

**Decisões condicionais examinadas:**
- `(result.status==='fulfilled')`
- `failed.length===jobs.length`
- `failed.length`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@213:51 — linha 213

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:213)

```javascript
([,url])=>
```

Executa a etapa `callback@213:51` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@214:35 — linha 214

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:214)

```javascript
(result,index)=>
```

Executa a etapa `callback@214:35` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `failed.push`.

**Decisões condicionais examinadas:**
- `(result.status==='fulfilled')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### loadNotifications — linha 218

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:218)

```javascript
async function loadNotifications()
```

Executa a etapa `load Notifications` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `readLocalNotifications`; `api`; `[...local, ...remote.map`; `remote.map`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const remote = await api('/account/notifications');
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@221:50 — linha 221

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:221)

```javascript
n =>
```

Executa a etapa `callback@221:50` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@222:11 — linha 222

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:222)

```javascript
(a,b)=>
```

Executa a etapa `callback@222:11` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### addressText — linha 225

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:225)

```javascript
address =>
```

Executa a etapa `address Text` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `[address.addressLine, address.addressComplement, address.neighborhood, `${address.city}${address.state ? `/${address.state}` : ''}`, `CEP ${address.postalCode}`].filter`.

**Decisões condicionais examinadas:**
- `address.state`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### loadAddresses — linha 228

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:228)

```javascript
async function loadAddresses()
```

Executa a etapa `load Addresses` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `JSON.stringify`; `state.addresses.find`; `notify`.

**Decisões condicionais examinadas:**
- `(!state.addresses.length && state.account.addressLine && state.account.neighborhood && state.account.city && state.account.state && state.account.postalCode)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
state.addresses = await api('/account/addresses');
const imported = await api('/account/addresses', {method:'POST', body:JSON.stringify({label:'Casa',addressLine:state.account.addressLine,addressComplement:state.account.addressComplement,neighborhood:state.account.neighborhood,city:state.account.city,state:state.account.state,postalCode:state.account.postalCode,defaultAddress:true})});
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@235:52 — linha 235

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:235)

```javascript
a=>
```

Executa a etapa `callback@235:52` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### startNotificationPolling — linha 239

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:239)

```javascript
function startNotificationPolling()
```

Executa a etapa `start Notification Polling` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stopNotificationPolling`; `setInterval`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### stopNotificationPolling — linha 244

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:244)

```javascript
function stopNotificationPolling()
```

Executa a etapa `stop Notification Polling` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `clearInterval`.

**Decisões condicionais examinadas:**
- `(notificationPollHandle)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isTypingInPage — linha 249

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:249)

```javascript
function isTypingInPage()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `el.matches?.`.

**Saída declarada:**
- `return !!el&&el.matches?.('#page-content textarea, #page-content input, #page-content select');`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refreshNotifications — linha 251

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:251)

```javascript
async function refreshNotifications()
```

Executa a etapa `refresh Notifications` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `String`; `state.notifications.filter`; `remote.filter`; `existing.has`; `[...readLocalNotifications`; `readLocalNotifications`; `remote.map`; `fresh.slice`; `notify`; `renderPage`; `isTypingInPage`.

**Decisões condicionais examinadas:**
- `(!state.account || document.visibilityState !== 'visible')`
- `(!state.account || String(state.account.id) !== String(accountId))`
- `(state.page === 'notifications' && fresh.length)`
- `(state.page === 'orders')`
- `(!isTypingInPage())`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const remote = await api('/account/notifications');
try { state.orders = await api('/orders/mine'); if(!isTypingInPage())renderPage(); } catch { /* o próximo ciclo atualiza o acompanhamento */ }
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@257:57 — linha 257

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:257)

```javascript
n =>
```

Executa a etapa `callback@257:57` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@257:89 — linha 257

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:257)

```javascript
n =>
```

Executa a etapa `callback@257:89` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@258:33 — linha 258

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:258)

```javascript
n =>
```

Executa a etapa `callback@258:33` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `existing.has`; `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@259:87 — linha 259

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:259)

```javascript
n =>
```

Executa a etapa `callback@259:87` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@260:13 — linha 260

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:260)

```javascript
(a,b)=>
```

Executa a etapa `callback@260:13` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@261:40 — linha 261

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:261)

```javascript
n =>
```

Executa a etapa `callback@261:40` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notify`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### handlePaymentReturn — linha 269

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:269)

```javascript
async function handlePaymentReturn(paymentId,result,providerPaymentId)
```

Executa a etapa `handle Payment Return` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `encodeURIComponent`; `setTimeout`; `JSON.stringify`; `notify`; `url.searchParams.delete`; `history.replaceState`.

**Decisões condicionais examinadas:**
- `providerPaymentId`
- `payment.reservationId`
- `payment.status==='PAID'`
- `payment.status==='PENDING'`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
let payment=await api(`/payments/${encodeURIComponent(paymentId)}`);
for(let attempt=0;payment.status==='PENDING'&&attempt<5;attempt++){await new Promise(resolve=>setTimeout(resolve,1600));payment=providerPaymentId?await api(`/payments/${encodeURIComponent(paymentId)}/sync`,{method:'POST',body:JSON.stringify({providerPaymentId})}):await api(`/payments/${encodeURIComponent(paymentId)}`);}
const url=new URL(location.href);for(const key of ['mp','app_payment_id','payment_id','collection_id','collection_status','status','external_reference'])url.searchParams.delete(key);history.replaceState({page:target},'',url.pathname+url.search+`#${target}`);
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@272:90 — linha 272

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:272)

```javascript
resolve=>
```

Executa a etapa `callback@272:90` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setTimeout`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### loadStores — linha 280

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:280)

```javascript
async function loadStores()
```

Executa a etapa `load Stores` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`; `encodeURIComponent`; `api`; `filterStores`.

**Decisões condicionais examinadas:**
- `city`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
state.stores = await api(`/stores${query}`);
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### filterStores — linha 287

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:287)

```javascript
function filterStores()
```

Executa a etapa `filter Stores` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.search.trim`; `state.stores.filter`; `categoryMatches`; ``${store.name} ${store.description || ''} ${store.category || ''} ${store.city||''} ${store.state||''}`.toLocaleLowerCase`.

**Saída declarada:**
- `return categoryMatch && searchMatch;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@289:46 — linha 289

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:289)

```javascript
store =>
```

Executa a etapa `callback@289:46` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryMatches`; ``${store.name} ${store.description || ''} ${store.category || ''} ${store.city||''} ${store.state||''}`.toLocaleLowerCase`.

**Saída declarada:**
- `return categoryMatch && searchMatch;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isCustomerOutsideStoreCity — linha 296

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:296)

```javascript
function isCustomerOutsideStoreCity(store)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `customerOutsideStoreCity`.

**Saída declarada:**
- `return customerOutsideStoreCity(state.profile||state.account,store);`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### render — linha 298

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:298)

```javascript
function render()
```

Executa a etapa `render` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`; `renderAuth`; `renderShell`; `renderPage`; `renderCartDrawer`; `renderStoreModal`.

**Decisões condicionais examinadas:**
- `(!state.account)`
- `(state.cartOpen)`
- `(state.selectedStore)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderAuth — linha 308

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:308)

```javascript
function renderAuth()
```

Executa a etapa `render Auth` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `icon`; `esc`; `$`; `document.querySelectorAll`; `button.addEventListener`; `renderAuth`; `modal.querySelector`.

**Decisões condicionais examinadas:**
- `state.backendStatus==='offline'`
- `recovery`
- `state.recoveryStep==='request'`
- `state.recoveryChannel==='EMAIL'`
- `state.recoveryChannel==='WHATSAPP'`
- `state.authMode === 'login'`
- `state.authMode==='login'`
- `state.authMode==='register'`
- `(event.target===event.currentTarget)`
- `(event.key==='Escape')`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
app.innerHTML = `<main class="auth-screen">
$('#auth-form').addEventListener('submit', recovery ? submitRecovery : submitAuth);
document.querySelectorAll('[data-auth-mode]').forEach(button => button.addEventListener('click', () => { state.authMode = button.dataset.authMode; renderAuth(); }));
$('[data-forgot-password]')?.addEventListener('click',()=>{state.recoveryMode=true;state.recoveryStep='request';state.recoveryEmail=$('#auth-email')?.value||'';renderAuth();});
$('[data-recovery-channel]')?.addEventListener('change', event=>state.recoveryChannel=event.target.value);
$('#recovery-channel')?.addEventListener('change', event=>state.recoveryChannel=event.target.value);
$('[data-recovery-back]')?.addEventListener('click',()=>{state.recoveryMode=false;state.recoveryStep='request';renderAuth();});
document.querySelectorAll('[data-about-us]').forEach(button=>button.addEventListener('click',()=>{const modal=$('[data-about-modal]');modal.hidden=false;modal.querySelector('[data-close-about]')?.focus();}));
document.querySelectorAll('[data-close-about]').forEach(button=>button.addEventListener('click',()=>{$('[data-about-modal]').hidden=true;}));
$('[data-about-modal]')?.addEventListener('click',event=>{if(event.target===event.currentTarget)event.currentTarget.hidden=true;});
$('[data-about-modal]')?.addEventListener('keydown',event=>{if(event.key==='Escape'){event.currentTarget.hidden=true;}});
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@340:57 — linha 340

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:340)

```javascript
button =>
```

Executa a etapa `callback@340:57` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `renderAuth`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button => button.addEventListener('click', () => { state.authMode = button.dataset.authMode; renderAuth(); })
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@340:100 — linha 340

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:340)

```javascript
() =>
```

Executa a etapa `callback@340:100` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `renderAuth`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@341:57 — linha 341

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:341)

```javascript
()=>
```

Executa a etapa `callback@341:57` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`; `renderAuth`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@342:60 — linha 342

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:342)

```javascript
event=>
```

Executa a etapa `callback@342:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@343:54 — linha 343

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:343)

```javascript
event=>
```

Executa a etapa `callback@343:54` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@344:55 — linha 344

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:344)

```javascript
()=>
```

Executa a etapa `callback@344:55` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `renderAuth`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@345:56 — linha 345

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:345)

```javascript
button=>
```

Executa a etapa `callback@345:56` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `$`; `modal.querySelector`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>{const modal=$('[data-about-modal]');modal.hidden=false;modal.querySelector('[data-close-about]')?.focus();})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@345:96 — linha 345

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:345)

```javascript
()=>
```

Executa a etapa `callback@345:96` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`; `modal.querySelector`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@346:59 — linha 346

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:346)

```javascript
button=>
```

Executa a etapa `callback@346:59` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `$`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>{$('[data-about-modal]').hidden=true;})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@346:99 — linha 346

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:346)

```javascript
()=>
```

Executa a etapa `callback@346:99` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@347:53 — linha 347

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:347)

```javascript
event=>
```

Executa a etapa `callback@347:53` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `(event.target===event.currentTarget)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@348:55 — linha 348

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:348)

```javascript
event=>
```

Executa a etapa `callback@348:55` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `(event.key==='Escape')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### submitRecovery — linha 351

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:351)

```javascript
async function submitRecovery(event)
```

Executa a etapa `submit Recovery` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `$`; `api`; `JSON.stringify`; `notify`; `renderAuth`.

**Decisões condicionais examinadas:**
- `(state.recoveryStep === 'request')`
- `(newPassword!==$('#recovery-confirm-password').value)`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
await api('/auth/password-recovery',{method:'POST',body:JSON.stringify({email:state.recoveryEmail,channel:state.recoveryChannel})});
await api('/auth/password-recovery/reset',{method:'POST',body:JSON.stringify({email:state.recoveryEmail,channel:state.recoveryChannel,code:$('#recovery-code').value.trim(),newPassword})});
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### submitAuth — linha 371

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:371)

```javascript
async function submitAuth(event)
```

Executa a etapa `submit Auth` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `String`; `form.get`; `api`; `JSON.stringify`; `notify`; `renderAuth`; `$`; `persistAccountHint`; `role`; `readLocalNotifications`; `loadNotifications`; `canShop`; `loadAddresses`; `startNotificationPolling`; `loadCurrentPage`; `render`; `displayName`.

**Decisões condicionais examinadas:**
- `(state.authMode === 'register')`
- `(!state.mustChangePassword)`
- `state.mustChangePassword`
- `role() === 'CUSTOMER'`
- `role() === 'MERCHANT'`
- `role() === 'COURIER'`
- `(canShop())`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
await api('/auth/register', { method:'POST', body:JSON.stringify({ name:String(form.get('name')).trim(), email, password, cpf:String(form.get('cpf')).replace(/\D/g,'') }) });
const response = await api('/auth/login', { method:'POST', body:JSON.stringify({ email, password }) });
if(!state.mustChangePassword){state.profile = await api('/account/me');state.account = state.profile;persistAccountHint(state.account);}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@393:67 — linha 393

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:393)

```javascript
()=>
```

Executa a etapa `callback@393:67` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderShell — linha 399

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:399)

```javascript
function renderShell()
```

Executa a etapa `render Shell` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `navByRole[role`; `role`; `mobileNavigation`; `icon`; `esc`; `links.map`; `state.notifications.some`; `initials`; `mobileNav.visible.map`; `window.matchMedia`; `canShop`; `state.cart.reduce`; `$`; `render`; `String`; `app.querySelector`; `filterStores`; `renderPage`.

**Decisões condicionais examinadas:**
- `state.mustChangePassword`
- `state.sidebarCollapsed`
- `state.mobileMenu`
- `role()==='CUSTOMER'`
- `role()==='MERCHANT'`
- `role()==='COURIER'`
- `state.page===page`
- `page==='notifications'&&state.notifications.some(n=>!n.read)`
- `role()==='ADMIN'`
- `mobileNav.hasMore`
- `window.matchMedia('(max-width: 820px)').matches`
- `(state.browseLocation?.city||state.account.city)`
- `(state.browseLocation?.state||state.account.state)`
- `state.notifications.some(n=>!n.read)`
- `canShop()`
- `(window.matchMedia('(max-width: 820px)').matches)`
- `(canShop()&&state.page==='home')`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
app.innerHTML = `<div class="app-shell ${state.sidebarCollapsed?'sidebar-collapsed':''}">
$('#mobile-menu')?.addEventListener('click',()=>{
else {state.sidebarCollapsed=!state.sidebarCollapsed;$('#mobile-menu').setAttribute('aria-expanded',String(!state.sidebarCollapsed));$('#mobile-menu').setAttribute('aria-label',state.sidebarCollapsed?'Expandir menu':'Recolher menu');app.querySelector('.app-shell')?.classList.toggle('sidebar-collapsed',state.sidebarCollapsed);}
$('#nav-backdrop')?.addEventListener('click',()=>{state.mobileMenu=false;render();});
$('[data-mobile-more]')?.addEventListener('click',()=>{state.mobileMenu=!state.mobileMenu;render();});
$('#open-cart')?.addEventListener('click',()=>{state.cartOpen=true;render();});
$('#global-search')?.addEventListener('input',event=>{state.search=event.target.value;if(canShop()&&state.page==='home'){filterStores();renderPage();}});
$('[data-location-picker]')?.addEventListener('click',openLocationPicker);
$('[data-help]')?.addEventListener('click',openSupportCenter);
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@400:69 — linha 400

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:400)

```javascript
([page])=>
```

Executa a etapa `callback@400:69` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@406:84 — linha 406

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:406)

```javascript
([page,iconName,label])=>
```

Executa a etapa `callback@406:84` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `icon`; `state.notifications.some`.

**Decisões condicionais examinadas:**
- `state.page===page`
- `page==='notifications'&&state.notifications.some(n=>!n.read)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@406:294 — linha 406

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:406)

```javascript
n=>
```

Executa a etapa `callback@406:294` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@410:174 — linha 410

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:410)

```javascript
([page,iconName,label])=>
```

Executa a etapa `callback@410:174` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `icon`; `state.notifications.some`.

**Decisões condicionais examinadas:**
- `state.page===page`
- `page==='notifications'&&state.notifications.some(n=>!n.read)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@410:428 — linha 410

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:410)

```javascript
n=>
```

Executa a etapa `callback@410:428` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@415:156 — linha 415

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:415)

```javascript
n=>
```

Executa a etapa `callback@415:156` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@415:285 — linha 415

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:415)

```javascript
(n,i)=>
```

Executa a etapa `callback@415:285` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@418:47 — linha 418

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:418)

```javascript
()=>
```

Executa a etapa `callback@418:47` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `window.matchMedia`; `render`; `$`; `String`; `app.querySelector`.

**Decisões condicionais examinadas:**
- `(window.matchMedia('(max-width: 820px)').matches)`
- `state.sidebarCollapsed`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
else {state.sidebarCollapsed=!state.sidebarCollapsed;$('#mobile-menu').setAttribute('aria-expanded',String(!state.sidebarCollapsed));$('#mobile-menu').setAttribute('aria-label',state.sidebarCollapsed?'Expandir menu':'Recolher menu');app.querySelector('.app-shell')?.classList.toggle('sidebar-collapsed',state.sidebarCollapsed);}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@422:48 — linha 422

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:422)

```javascript
()=>
```

Executa a etapa `callback@422:48` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `render`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@423:53 — linha 423

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:423)

```javascript
()=>
```

Executa a etapa `callback@423:53` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `render`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@424:45 — linha 424

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:424)

```javascript
()=>
```

Executa a etapa `callback@424:45` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `render`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@425:49 — linha 425

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:425)

```javascript
event=>
```

Executa a etapa `callback@425:49` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `canShop`; `filterStores`; `renderPage`.

**Decisões condicionais examinadas:**
- `(canShop()&&state.page==='home')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### navigate — linha 430

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:430)

```javascript
async function navigate(page)
```

Executa a etapa `navigate` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `['categories','categoryStores'].includes`; `canShop`; `navByRole[role`; `role`; `stopChat`; `history.pushState`; `render`; `loadCurrentPage`; `state.notifications.filter`; `Promise.all`; `unread.map`; `api`; `state.notifications.map`; `localStorage.setItem`; `notificationKey`; `JSON.stringify`; `window.scrollTo`.

**Decisões condicionais examinadas:**
- `(state.mustChangePassword&&page!=='account')`
- `(!navByRole[role()]?.some(([allowed])=>allowed===page) && page!=='checkout' && !categoryPage)`
- `(state.page!==page)`
- `(location.hash!==\`#${page}\`)`
- `(page==='notifications')`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
localStorage.setItem(notificationKey(state.account.id),JSON.stringify(state.notifications.filter(n=>n.localOnly!==false).slice(0,40)));
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@433:31 — linha 433

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:433)

```javascript
([allowed])=>
```

Executa a etapa `callback@433:31` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@440:45 — linha 440

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:440)

```javascript
n=>
```

Executa a etapa `callback@440:45` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@441:34 — linha 441

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:441)

```javascript
n=>
```

Executa a etapa `callback@441:34` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@441:103 — linha 441

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:441)

```javascript
()=>
```

Executa a etapa `callback@441:103` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@442:49 — linha 442

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:442)

```javascript
n=>
```

Executa a etapa `callback@442:49` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@443:102 — linha 443

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:443)

```javascript
n=>
```

Executa a etapa `callback@443:102` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderPage — linha 449

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:449)

```javascript
function renderPage()
```

Executa a etapa `render Page` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`; `fn`; `bindPage`.

**Decisões condicionais examinadas:**
- `(!content)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
content.innerHTML=fn();
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### openLocationPicker — linha 457

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457)

```javascript
async function openLocationPicker()
```

Executa a etapa `open Location Picker` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`; `document.createElement`; `icon`; `document.body.append`; `modal.querySelector`; `modal.remove`; `localStorage.removeItem`; `canShop`; `['home','categories','categoryStores'].includes`; `loadStores`; `render`; `selectBrowseLocation`; `e.preventDefault`; `String`; `new FormData`; `searchBrazilPlaces`; `renderLocationResults`; `esc`; `detectCurrentCity`; `input.value.replace`; `postalCodeResponse`; `AbortSignal.timeout`; `response.json`; `[data.logradouro,data.bairro].filter`; `places.find`; `chooseLocationPoint`; `cep.slice`; `api`; `cities.some`; `c.city.toLowerCase`; `state.account.city.toLowerCase`; `cities.push`; `cities.map`; `list.querySelectorAll`; `selectDirectoryCity`; `initializeLocationMap`; `cities.find`; `active.city.toLowerCase`.

**Rejeições locais:**
- `throw new Error('service');`
- `throw new Error('not-found');`

**Decisões condicionais examinadas:**
- `(e.target===modal)`
- `(canShop()&&['home','categories','categoryStores'].includes(state.page))`
- `(query.length<2)`
- `(cep.length!==8)`
- `(!response.ok)`
- `(data.erro)`
- `(places[0])`
- `(viaCepUnavailable)`
- `(!place)`
- `viaCepUnavailable`
- `(state.account?.city&&state.account?.state&&!cities.some(c=>c.city.toLowerCase()===state.account.city.toLowerCase()&&c.state===state.account.state))`
- `cities.length`
- `(active?.city)`
- `(match)`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
modal.innerHTML=`<section class="modal location-modal" role="dialog" aria-modal="true" aria-labelledby="location-title"><div class="modal-head"><div><span class="eyebrow">Sua região</span><h2 id="location-title">Onde você quer pedir?</h2><p>Busque uma cidade, use sua localização ou escolha um ponto no mapa.</p></div><button class="close-button" data-close-location aria-label="Fechar">×</button></div><div class="location-search-row"><form data-location-search class="location-search-form"><label class="field"><span>Nome da cidade</span><div class="location-search-control"><input name="query" autocomplete="address-level2" placeholder="Ex.: Iepê, SP" required/><button class="btn small" type="submit">Buscar cidade</button></div></label></form><button type="button" class="btn outline location-gps" data-location-gps>${icon('pin',17)} Usar minha localização</button></div><p class="location-privacy-note">O GPS só é consultado quando você toca no botão. Usamos a posição para identificar cidade e CEP; não salvamos suas coordenadas precisas.</p><div class="location-results" data-location-results aria-live="polite"></div><div class="location-picker-grid"><section class="location-map-column"><div class="location-map-heading"><div><h3>Escolha no mapa</h3><p class="store-desc">Toque no mapa para identificar a cidade daquele ponto.</p></div><span class="map-badge">Mapa interativo</span></div><div class="location-map-preview" data-location-map><div class="location-map-loading">Carregando mapa…</div></div><small class="map-attribution">© OpenStreetMap contributors</small><a class="map-issue-link" href="https://www.openstreetmap.org/fixthemap" target="_blank" rel="noopener noreferrer">Informar problema no mapa</a></section><aside class="location-side-column"><section class="location-cep-panel"><h3>Buscar por CEP</h3><form data-location-cep><label class="field"><span>CEP</span><div class="cep-control"><input name="postalCode" inputmode="numeric" autocomplete="postal-code" maxlength="9" placeholder="00000-000" required/><button class="btn small" type="submit">Buscar CEP</button></div></label></form><p class="store-desc" data-cep-feedback>Um CEP identifica uma área/endereço; a cidade pode ter vários CEPs.</p></section><section class="location-city-panel"><div class="location-city-heading"><h3>Cidades disponíveis</h3><span data-city-count></span></div><div class="location-city-list" data-city-list><p class="store-desc">Carregando cidades…</p></div></section></aside></div><div class="location-selection" data-location-selection><span class="location-selection-icon">${icon('pin',18)}</span><span data-location-selection-label>Selecione uma cidade ou ponto no mapa.</span></div><div class="location-modal-foot"><button class="btn outline small" data-location-clear>Remover filtro de cidade</button><button class="btn" data-location-confirm disabled>Usar esta região</button></div></section>`;
modal.querySelector('[data-location-clear]').onclick=async()=>{state.browseLocation=null;localStorage.removeItem('fooddelivery_location');modal.remove();locationMap=null;if(canShop()&&['home','categories','categoryStores'].includes(state.page))await loadStores().catch(()=>{});render();};
modal.querySelector('[data-location-search]').onsubmit=async e=>{e.preventDefault();const query=String(new FormData(e.currentTarget).get('query')||'').trim();if(query.length<2)return;const result=modal.querySelector('[data-location-results]');result.innerHTML='<p class="store-desc">Buscando cidade…</p>';try{const places=await searchBrazilPlaces(query);renderLocationResults(modal,places);}catch(error){result.innerHTML=`<p class="location-error">${esc(error.message||'Não foi possível buscar essa cidade agora.')}</p>`;}};
modal.querySelector('[data-location-cep]').onsubmit=async e=>{e.preventDefault();const input=e.currentTarget.elements.postalCode,feedback=modal.querySelector('[data-cep-feedback]'),cep=input.value.replace(/\D/g,'');if(cep.length!==8){feedback.textContent='Digite os oito números do CEP.';return;}feedback.textContent='Consultando CEP…';let viaCepUnavailable=false,place=null;try{const response=await postalCodeResponse(cep,{signal:AbortSignal.timeout(8000)});if(!response.ok)throw new Error('service');const data=await response.json();if(data.erro)throw new Error('not-found');place={city:data.localidade,state:data.uf,postalCode:String(data.cep||cep).replace(/\D/g,''),address:[data.logradouro,data.bairro].filter(Boolean).join(', ')};try{const places=await searchBrazilPlaces(`${data.localidade}, ${data.uf}`);if(places[0])place={...places[0],...place};}catch{}}catch(error){viaCepUnavailable=error.message!=='not-found';try{const places=await searchBrazilPlaces(cep);place=places.find(item=>String(item.postalCode||'').replace(/\D/g,'')===cep)||null;}catch{if(viaCepUnavailable){feedback.textContent='A consulta de CEP está temporariamente indisponível. Tente pelo nome da cidade ou pelo mapa.';return;}}}if(!place){feedback.textContent=viaCepUnavailable?'Não foi possível validar este CEP agora. Tente pelo nome da cidade ou pelo mapa.':'Não encontramos esse CEP. Confira os números ou escolha uma cidade no mapa.';return;}await chooseLocationPoint(modal,place);feedback.textContent=`CEP encontrado: ${cep.slice(0,5)}-${cep.slice(5)} · ${place.city}, ${place.state}. Confirme a região para aplicar.`;};
try{state.locationCities=await api('/store-locations');}catch{state.locationCities=[];}
const list=modal.querySelector('[data-city-list]');modal.querySelector('[data-city-count]').textContent=String(cities.length);list.innerHTML=cities.length?cities.map(c=>`<button type="button" class="location-city-option" data-city="${esc(c.city)}" data-uf="${esc(c.state)}"><span class="location-option-pin">${icon('pin',17)}</span><span><strong>${esc(c.city)}</strong><small>${esc(c.state)}</small></span><b>›</b></button>`).join(''):'<p class="store-desc">Ainda não há cidades cadastradas. Busque qualquer cidade ou use o mapa.</p>';
try{await initializeLocationMap(modal);const active=state.browseLocation||{city:state.account?.city,state:state.account?.state};if(active?.city){const match=cities.find(c=>c.city.toLowerCase()===active.city.toLowerCase()&&c.state===active.state);if(match)selectDirectoryCity(modal,match);}}catch{modal.querySelector('[data-location-map]').innerHTML='<div class="location-map-error">O mapa não carregou. Você ainda pode buscar a cidade, usar o CEP ou selecionar uma cidade cadastrada.</div>';}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@463:56 — linha 463

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:463)

```javascript
()=>
```

Executa a etapa `callback@463:56` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `modal.remove`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@463:109 — linha 463

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:463)

```javascript
e=>
```

Executa a etapa `callback@463:109` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `modal.remove`.

**Decisões condicionais examinadas:**
- `(e.target===modal)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@464:56 — linha 464

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:464)

```javascript
async()=>
```

Executa a etapa `callback@464:56` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `localStorage.removeItem`; `modal.remove`; `canShop`; `['home','categories','categoryStores'].includes`; `loadStores`; `render`.

**Decisões condicionais examinadas:**
- `(canShop()&&['home','categories','categoryStores'].includes(state.page))`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async()=>{state.browseLocation=null;localStorage.removeItem('fooddelivery_location');modal.remove();locationMap=null;if(canShop()&&['home','categories','categoryStores'].includes(state.page))await loadStores().catch(()=>{});render();}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@464:272 — linha 464

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:464)

```javascript
()=>
```

Executa a etapa `callback@464:272` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@465:58 — linha 465

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:465)

```javascript
()=>
```

Executa a etapa `callback@465:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `selectBrowseLocation`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@466:58 — linha 466

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:466)

```javascript
async e=>
```

Executa a etapa `callback@466:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `e.preventDefault`; `String`; `new FormData`; `modal.querySelector`; `searchBrazilPlaces`; `renderLocationResults`; `esc`.

**Decisões condicionais examinadas:**
- `(query.length<2)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async e=>{e.preventDefault();const query=String(new FormData(e.currentTarget).get('query')||'').trim();if(query.length<2)return;const result=modal.querySelector('[data-location-results]');result.innerHTML='<p class="store-desc">Buscando cidade…</p>';try{const places=await searchBrazilPlaces(query);renderLocationResults(modal,places);}catch(error){result.innerHTML=`<p class="location-error">${esc(error.message||'Não foi possível buscar essa cidade agora.')}</p>`;}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@467:54 — linha 467

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:467)

```javascript
()=>
```

Executa a etapa `callback@467:54` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `detectCurrentCity`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@468:55 — linha 468

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:468)

```javascript
async e=>
```

Executa a etapa `callback@468:55` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `e.preventDefault`; `modal.querySelector`; `input.value.replace`; `postalCodeResponse`; `AbortSignal.timeout`; `response.json`; `String`; `[data.logradouro,data.bairro].filter`; `searchBrazilPlaces`; `places.find`; `chooseLocationPoint`; `cep.slice`.

**Rejeições locais:**
- `throw new Error('service');`
- `throw new Error('not-found');`

**Decisões condicionais examinadas:**
- `(cep.length!==8)`
- `(!response.ok)`
- `(data.erro)`
- `(places[0])`
- `(viaCepUnavailable)`
- `(!place)`
- `viaCepUnavailable`

**Saída declarada:**
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async e=>{e.preventDefault();const input=e.currentTarget.elements.postalCode,feedback=modal.querySelector('[data-cep-feedback]'),cep=input.value.replace(/\D/g,'');if(cep.length!==8){feedback.textContent='Digite os oito números do CEP.';return;}feedback.textContent='Consultando CEP…';let viaCepUnavailable=false,place=null;try{const response=await postalCodeResponse(cep,{signal:AbortSignal.timeout(8000)});if(!response.ok)throw new Error('service');const data=await response.json();if(data.erro)throw new Error('not-found');place={city:data.localidade,state:data.uf,postalCode:String(data.cep||cep).replace(/\D/g,''),address:[data.logradouro,data.bairro].filter(Boolean).join(', ')};try{const places=await searchBrazilPlaces(`${data.localidade}, ${data.uf}`);if(places[0])place={...places[0],...place};}catch{}}catch(error){viaCepUnavailable=error.message!=='not-found';try{const places=await searchBrazilPlaces(cep);place=places.find(item=>String(item.postalCode||'').replace(/\D/g,'')===cep)||null;}catch{if(viaCepUnavailable){feedback.textContent='A consulta de CEP está temporariamente indisponível. Tente pelo nome da cidade ou pelo mapa.';return;}}}if(!place){feedback.textContent=viaCepUnavailable?'Não foi possível validar este CEP agora. Tente pelo nome da cidade ou pelo mapa.':'Não encontramos esse CEP. Confira os números ou escolha uma cidade no mapa.';return;}await chooseLocationPoint(modal,place);feedback.textContent=`CEP encontrado: ${cep.slice(0,5)}-${cep.slice(5)} · ${place.city}, ${place.state}. Confirme a região para aplicar.`;}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@468:994 — linha 468

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:468)

```javascript
item=>
```

Executa a etapa `callback@468:994` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@470:101 — linha 470

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:470)

```javascript
c=>
```

Executa a etapa `callback@470:101` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `c.city.toLowerCase`; `state.account.city.toLowerCase`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@471:169 — linha 471

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:471)

```javascript
c=>
```

Executa a etapa `callback@471:169` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `icon`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@472:48 — linha 472

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:472)

```javascript
b=>
```

Executa a etapa `callback@472:48` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `selectDirectoryCity`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@472:61 — linha 472

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:472)

```javascript
()=>
```

Executa a etapa `callback@472:61` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `selectDirectoryCity`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@473:172 — linha 473

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:473)

```javascript
c=>
```

Executa a etapa `callback@473:172` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `c.city.toLowerCase`; `active.city.toLowerCase`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### initializeLocationMap — linha 475

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:475)

```javascript
async function initializeLocationMap(modal)
```

Executa a etapa `initialize Location Map` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `loadLeaflet`; `modal.querySelector`; `L.map`; `L.tileLayer`; `locationMap.on`; `reverseBrazilPlace`; `chooseLocationPoint`; `esc`; `setTimeout`; `locationMap?.invalidateSize`.

**Decisões condicionais examinadas:**
- `(!box?.isConnected)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function initializeLocationMap(modal){const L=await loadLeaflet(),box=modal.querySelector('[data-location-map]');if(!box?.isConnected)return;box.innerHTML='<div id="location-leaflet-map" aria-label="Mapa interativo; clique para selecionar uma cidade"></div>';locationMap=L.map('location-leaflet-map',{scrollWheelZoom:false}).setView([-14.235,-51.925],4);L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png',{maxZoom:19,attribution:'&copy; <a href="https://www.openstreetmap.org/copyright" target="_blank" rel="noopener noreferrer">OpenStreetMap contributors</a>'}).addTo(locationMap);locationMap.on('click',async event=>{const result=modal.querySelector('[data-location-results]');result.innerHTML='<p class="store-desc">Identificando o local…</p>';try{const place=await reverseBrazilPlace(event.latlng.lat,event.latlng.lng);await chooseLocationPoint(modal,{...place,latitude:event.latlng.lat,longitude:event.latlng.lng});result.innerHTML='';}catch(error){result.innerHTML=`<p class="location-error">${esc(error.message||'Não foi possível identificar a cidade deste ponto.')}</p>`;}});setTimeout(()=>locationMap?.invalidateSize(),80);}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@475:622 — linha 475

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:475)

```javascript
async event=>
```

Executa a etapa `callback@475:622` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `modal.querySelector`; `reverseBrazilPlace`; `chooseLocationPoint`; `esc`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async event=>{const result=modal.querySelector('[data-location-results]');result.innerHTML='<p class="store-desc">Identificando o local…</p>';try{const place=await reverseBrazilPlace(event.latlng.lat,event.latlng.lng);await chooseLocationPoint(modal,{...place,latitude:event.latlng.lat,longitude:event.latlng.lng});result.innerHTML='';}catch(error){result.innerHTML=`<p class="location-error">${esc(error.message||'Não foi possível identificar a cidade deste ponto.')}</p>`;}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@475:1115 — linha 475

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:475)

```javascript
()=>
```

Executa a etapa `callback@475:1115` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `locationMap?.invalidateSize`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### waitForGeocodeSlot — linha 476

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:476)

```javascript
async function waitForGeocodeSlot()
```

Executa a etapa `wait For Geocode Slot` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Math.max`; `Date.now`; `setTimeout`.

**Decisões condicionais examinadas:**
- `(wait)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@476:125 — linha 476

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:476)

```javascript
resolve=>
```

Executa a etapa `callback@476:125` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setTimeout`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### geocodeJson — linha 477

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477)

```javascript
async function geocodeJson(path)
```

Executa a etapa `geocode Json` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `locationGeocodeCache.has`; `locationGeocodeCache.get`; `waitForGeocodeSlot`; `api`; `locationGeocodeCache.set`; `locationGeocodeCache.delete`; `locationGeocodeCache.keys`.

**Decisões condicionais examinadas:**
- `(locationGeocodeCache.has(cacheKey))`
- `(locationGeocodeCache.size>50)`

**Saída declarada:**
- `return locationGeocodeCache.get(cacheKey);`
- `return data;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function geocodeJson(path){const cacheKey=path;if(locationGeocodeCache.has(cacheKey))return locationGeocodeCache.get(cacheKey);await waitForGeocodeSlot();const data=await api(path);locationGeocodeCache.set(cacheKey,data);if(locationGeocodeCache.size>50)locationGeocodeCache.delete(locationGeocodeCache.keys().next().value);return data;}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### searchBrazilPlaces — linha 478

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:478)

```javascript
async function searchBrazilPlaces(query)
```

Executa a etapa `search Brazil Places` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `geocodeJson`; `encodeURIComponent`; `records.map`; `placeFromGeocode`; `place.city.normalize`; `unique.has`; `unique.set`; `unique.values`.

**Decisões condicionais examinadas:**
- `(!unique.has(key))`

**Saída declarada:**
- `return [...unique.values()];`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@478:149 — linha 478

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:478)

```javascript
record=>
```

Executa a etapa `callback@478:149` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `placeFromGeocode`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@478:190 — linha 478

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:478)

```javascript
place=>
```

Executa a etapa `callback@478:190` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### selectDirectoryCity — linha 479

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:479)

```javascript
async function selectDirectoryCity(modal,city)
```

Executa a etapa `select Directory City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `searchBrazilPlaces`; `chooseLocationPoint`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### placeFromGeocode — linha 480

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:480)

```javascript
function placeFromGeocode(record)
```

Executa a etapa `place From Geocode` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `/^BR-`; `String`; `[a.road,a.house_number,a.suburb].filter`; `Number`.

**Saída declarada:**
- `return {city:a.city||a.town||a.village||a.municipality||a.county||'',state:String(uf).toUpperCase(),postalCode:a.postcode||'',address:[a.road,a.house_number,a.suburb].filter(Boolean).join(', '),latitude:Number(record.lat),longitude:Number(record.lon),label:record.display_name||''};`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### reverseBrazilPlace — linha 481

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:481)

```javascript
async function reverseBrazilPlace(latitude,longitude)
```

Executa a etapa `reverse Brazil Place` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `geocodeJson`; `encodeURIComponent`; `placeFromGeocode`.

**Rejeições locais:**
- `throw new Error('Não encontramos uma cidade nesse ponto. Aproxime o mapa de uma área urbana.');`

**Decisões condicionais examinadas:**
- `(!place.city||!place.state)`

**Saída declarada:**
- `return place;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### chooseLocationPoint — linha 482

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:482)

```javascript
async function chooseLocationPoint(modal,place)
```

Executa a etapa `choose Location Point` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`; `Number.isFinite`; `Number`; `modal.querySelector`; `esc`; `locationMap.setView`; `Math.max`; `locationMap.getZoom`; `locationMarker.setLatLng`; `window.L.marker`; `locationMarker.bindPopup`.

**Decisões condicionais examinadas:**
- `(!place?.city||!place?.state)`
- `Number.isFinite(Number(place.latitude))`
- `Number.isFinite(Number(place.longitude))`
- `(result&&place.label)`
- `(locationMap&&locationSelected.latitude!==null&&locationSelected.longitude!==null)`
- `(locationMarker)`
- `(cep&&locationSelected.postalCode)`
- `(label)`
- `locationSelected.postalCode`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function chooseLocationPoint(modal,place){if(!place?.city||!place?.state)return;locationSelected={city:String(place.city).trim(),state:String(place.state).trim().toUpperCase(),postalCode:String(place.postalCode||'').trim(),latitude:Number.isFinite(Number(place.latitude))?Number(place.latitude):null,longitude:Number.isFinite(Number(place.longitude))?Number(place.longitude):null};const result=modal.querySelector('[data-location-results]');if(result&&place.label)result.innerHTML=`<p class="location-result-context">Local selecionado: ${esc(place.label)}</p>`;if(locationMap&&locationSelected.latitude!==null&&locationSelected.longitude!==null){const point=[locationSelected.latitude,locationSelected.longitude];locationMap.setView(point,Math.max(locationMap.getZoom(),11));if(locationMarker)locationMarker.setLatLng(point);else locationMarker=window.L.marker(point).addTo(locationMap);locationMarker.bindPopup(`${esc(locationSelected.city)}, ${esc(locationSelected.state)}`).openPopup();}const cep=modal.querySelector('[data-location-cep] [name="postalCode"]');if(cep&&locationSelected.postalCode)cep.value=locationSelected.postalCode;const label=modal.querySelector('[data-location-selection-label]');if(label)label.innerHTML=`<strong>${esc(locationSelected.city)}, ${esc(locationSelected.state)}</strong><small>${locationSelected.postalCode?`CEP ${esc(locationSelected.postalCode)} · `:''}confirme para ver os estabelecimentos desta cidade</small>`;modal.querySelector('[data-location-confirm]').disabled=false;}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderLocationResults — linha 483

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:483)

```javascript
function renderLocationResults(modal,places)
```

Executa a etapa `render Location Results` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `modal.querySelector`; `places.map`; `icon`; `esc`; `box.querySelectorAll`; `chooseLocationPoint`; `Number`.

**Decisões condicionais examinadas:**
- `(!places.length)`
- `place.postalCode`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
function renderLocationResults(modal,places){const box=modal.querySelector('[data-location-results]');if(!places.length){box.innerHTML='<p class="location-error">Nenhuma cidade brasileira encontrada. Confira o nome e a UF.</p>';return;}box.innerHTML=`<div class="location-result-list">${places.map((place,index)=>`<button type="button" class="location-result-option" data-location-result="${index}"><span>${icon('pin',17)}</span><strong>${esc(place.city)}, ${esc(place.state)}</strong><small>${place.postalCode?`CEP ${esc(place.postalCode)}`:'CEP não informado pelo mapa'}</small></button>`).join('')}</div>`;box.querySelectorAll('[data-location-result]').forEach(button=>button.onclick=()=>chooseLocationPoint(modal,places[Number(button.dataset.locationResult)]));}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@483:299 — linha 483

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:483)

```javascript
(place,index)=>
```

Executa a etapa `callback@483:299` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `icon`; `esc`.

**Decisões condicionais examinadas:**
- `place.postalCode`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@483:666 — linha 483

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:483)

```javascript
button=>
```

Executa a etapa `callback@483:666` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `chooseLocationPoint`; `Number`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@483:689 — linha 483

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:483)

```javascript
()=>
```

Executa a etapa `callback@483:689` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `chooseLocationPoint`; `Number`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### detectCurrentCity — linha 484

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:484)

```javascript
async function detectCurrentCity(modal)
```

Executa a etapa `detect Current City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `modal.querySelector`; `icon`; `navigator.geolocation.getCurrentPosition`; `reverseBrazilPlace`; `chooseLocationPoint`; `esc`.

**Decisões condicionais examinadas:**
- `(!navigator.geolocation)`
- `error.code===1`
- `error.code===3`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function detectCurrentCity(modal){const button=modal.querySelector('[data-location-gps]'),result=modal.querySelector('[data-location-results]');if(!navigator.geolocation){result.innerHTML='<p class="location-error">Este navegador não oferece localização. Busque a cidade ou informe o CEP.</p>';return;}button.disabled=true;button.innerHTML=`${icon('pin',17)} Identificando…`;result.innerHTML='<p class="store-desc">Aguardando a permissão de localização do navegador…</p>';navigator.geolocation.getCurrentPosition(async position=>{try{const {latitude,longitude}=position.coords,place=await reverseBrazilPlace(latitude,longitude);await chooseLocationPoint(modal,{...place,latitude,longitude});}catch(error){result.innerHTML=`<p class="location-error">${esc(error.message||'Não foi possível identificar sua cidade. Use a busca ou o CEP.')}</p>`;}finally{button.disabled=false;button.innerHTML=`${icon('pin',17)} Usar minha localização`; }},error=>{const message=error.code===1?'Permita a localização no navegador ou escolha sua cidade manualmente.':error.code===3?'A localização demorou demais. Tente novamente ou escolha sua cidade.':'Não foi possível acessar sua localização. Busque a cidade ou o CEP.';result.innerHTML=`<p class="location-error">${message}</p>`;button.disabled=false;button.innerHTML=`${icon('pin',17)} Usar minha localização`;},{enableHighAccuracy:false,timeout:12000,maximumAge:300000});}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@484:530 — linha 484

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:484)

```javascript
async position=>
```

Executa a etapa `callback@484:530` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reverseBrazilPlace`; `chooseLocationPoint`; `esc`; `icon`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async position=>{try{const {latitude,longitude}=position.coords,place=await reverseBrazilPlace(latitude,longitude);await chooseLocationPoint(modal,{...place,latitude,longitude});}catch(error){result.innerHTML=`<p class="location-error">${esc(error.message||'Não foi possível identificar sua cidade. Use a busca ou o CEP.')}</p>`;}finally{button.disabled=false;button.innerHTML=`${icon('pin',17)} Usar minha localização`; }}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@484:958 — linha 484

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:484)

```javascript
error=>
```

Executa a etapa `callback@484:958` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `icon`.

**Decisões condicionais examinadas:**
- `error.code===1`
- `error.code===3`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
error=>{const message=error.code===1?'Permita a localização no navegador ou escolha sua cidade manualmente.':error.code===3?'A localização demorou demais. Tente novamente ou escolha sua cidade.':'Não foi possível acessar sua localização. Busque a cidade ou o CEP.';result.innerHTML=`<p class="location-error">${message}</p>`;button.disabled=false;button.innerHTML=`${icon('pin',17)} Usar minha localização`;}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### selectBrowseLocation — linha 485

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:485)

```javascript
async function selectBrowseLocation(location,modal)
```

Executa a etapa `select Browse Location` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `localStorage.setItem`; `JSON.stringify`; `notify`; `modal.remove`; `canShop`; `['home','categories','categoryStores'].includes`; `loadStores`; `render`.

**Decisões condicionais examinadas:**
- `(!location?.city||!location?.state)`
- `(canShop()&&['home','categories','categoryStores'].includes(state.page))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function selectBrowseLocation(location,modal){if(!location?.city||!location?.state)return;state.browseLocation={city:location.city,state:location.state,postalCode:location.postalCode||''};localStorage.setItem('fooddelivery_location',JSON.stringify(state.browseLocation));notify('Região atualizada',`Agora você está vendo lojas de ${location.city}, ${location.state}.`,'success');modal.remove();locationMap=null;locationMarker=null;if(canShop()&&['home','categories','categoryStores'].includes(state.page))await loadStores().catch(()=>{});render();}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@485:540 — linha 485

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:485)

```javascript
()=>
```

Executa a etapa `callback@485:540` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### openSupportCenter — linha 487

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:487)

```javascript
async function openSupportCenter()
```

Executa a etapa `open Support Center` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `role`; `loadAdminData`; `render`; `document.createElement`; `document.body.append`; `modal.remove`; `loadSupportTicketList`.

**Decisões condicionais examinadas:**
- `(role()==='ADMIN')`
- `(e.target===modal)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const modal=document.createElement('div');modal.id='support-modal';modal.className='modal-backdrop';modal.innerHTML='<section class="modal support-modal" role="dialog" aria-modal="true" aria-labelledby="support-title"></section>';document.body.append(modal);modal.onclick=e=>{if(e.target===modal)modal.remove();};await loadSupportTicketList();
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@489:275 — linha 489

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:489)

```javascript
e=>
```

Executa a etapa `callback@489:275` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `modal.remove`.

**Decisões condicionais examinadas:**
- `(e.target===modal)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### loadSupportTicketList — linha 491

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:491)

```javascript
async function loadSupportTicketList()
```

Executa a etapa `load Support Ticket List` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `renderSupportModal`; `notify`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function loadSupportTicketList(){try{state.supportAdminView=false;state.supportTickets=await api('/support/tickets/mine');state.activeTicket=null;renderSupportModal();}catch(error){notify('Não foi possível carregar seus chamados',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderSupportModal — linha 492

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:492)

```javascript
function renderSupportModal()
```

Executa a etapa `render Support Modal` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`; `esc`; `ticketConversationHtml`; `state.supportTickets.map`; `shortDate`; `pill`; `root.querySelector`; `root.querySelectorAll`; `openTicket`; `returnToAdminTickets`; `loadSupportTicketList`.

**Decisões condicionais examinadas:**
- `(!root)`
- `state.supportAdminView`
- `state.activeTicket`
- `state.supportTickets.length`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
root.innerHTML=`<div class="modal-head"><div><span class="eyebrow">${state.supportAdminView?'Central de chamados':'Atendimento FoodDelivery'}</span><h2 id="support-title">${state.activeTicket?esc(state.activeTicket.ticket.subject):'Como podemos ajudar?'}</h2><p>${state.activeTicket?'Acompanhe as respostas e envie novas mensagens.':'Crie um chamado e acompanhe a resposta nesta conversa.'}</p></div><button class="close-button" data-close-support aria-label="Fechar">×</button></div>${state.activeTicket?ticketConversationHtml(state.activeTicket,state.supportAdminView):`<div class="support-columns"><form class="panel support-new" data-ticket-create><h3>Novo chamado</h3><label class="field"><span>Assunto</span><input name="subject" maxlength="140" required placeholder="Ex.: dúvida sobre meu pedido"/></label><label class="field"><span>Categoria</span><select name="category"><option value="GENERAL">Dúvida geral</option><option value="ORDER">Pedido</option><option value="RESERVATION">Reserva</option><option value="PAYMENT">Pagamento</option><option value="ACCOUNT">Minha conta</option></select></label><label class="field"><span>Mensagem</span><textarea name="body" maxlength="2000" required placeholder="Conte para a gente como podemos ajudar."></textarea></label><button class="btn" type="submit">Enviar ao atendimento</button></form><section class="panel support-list"><div class="section-head"><div><h3>Meus chamados</h3><p class="store-desc">Consulte atualizações anteriores.</p></div></div>${state.supportTickets.length?state.supportTickets.map(t=>`<button class="support-ticket-row" type="button" data-user-ticket="${esc(t.id)}"><span><strong>${esc(t.subject)}</strong><small>${esc(t.lastMessage||'Sem mensagens')} · ${shortDate(t.updatedAt)}</small></span>${pill(t.status)}</button>`).join(''):'<p class="store-desc">Você ainda não abriu um chamado.</p>'}</section></div>`}`;
root.querySelector('[data-close-support]').onclick=()=>$('#support-modal')?.remove();root.querySelector('[data-ticket-create]')?.addEventListener('submit',createSupportTicket);root.querySelectorAll('[data-user-ticket]').forEach(b=>b.onclick=()=>openTicket(b.dataset.userTicket,false));root.querySelector('[data-ticket-back]')?.addEventListener('click',()=>state.supportAdminView?returnToAdminTickets():loadSupportTicketList());root.querySelector('[data-ticket-compose]')?.addEventListener('submit',sendTicketMessage);
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@494:1566 — linha 494

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:494)

```javascript
t=>
```

Executa a etapa `callback@494:1566` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `shortDate`; `pill`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
t=>`<button class="support-ticket-row" type="button" data-user-ticket="${esc(t.id)}"><span><strong>${esc(t.subject)}</strong><small>${esc(t.lastMessage||'Sem mensagens')} · ${shortDate(t.updatedAt)}</small></span>${pill(t.status)}</button>`
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@495:54 — linha 495

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:495)

```javascript
()=>
```

Executa a etapa `callback@495:54` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@495:231 — linha 495

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:495)

```javascript
b=>
```

Executa a etapa `callback@495:231` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openTicket`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@495:244 — linha 495

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:495)

```javascript
()=>
```

Executa a etapa `callback@495:244` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openTicket`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@495:355 — linha 495

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:495)

```javascript
()=>
```

Executa a etapa `callback@495:355` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `returnToAdminTickets`; `loadSupportTicketList`.

**Decisões condicionais examinadas:**
- `state.supportAdminView`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### returnToAdminTickets — linha 497

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:497)

```javascript
async function returnToAdminTickets()
```

Executa a etapa `return To Admin Tickets` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `$`; `render`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function returnToAdminTickets(){state.adminTickets=await api('/admin/support-tickets');state.adminSection='tickets';$('#support-modal')?.remove();render();}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### ticketConversationHtml — linha 498

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:498)

```javascript
function ticketConversationHtml(detail,isAdmin)
```

Executa a etapa `ticket Conversation Html` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `pill`; `esc`; `detail.messages.map`; `shortDate`.

**Decisões condicionais examinadas:**
- `isAdmin`
- `m.senderRole==='ADMIN'`
- `t.status==='CLOSED'`

**Saída declarada:**
- `return \`<div class="ticket-detail-top"><button class="btn outline small" data-ticket-back>← ${isAdmin?'Chamados':'Meus chamados'}</button>${pill(t.status)}<span>${esc(ticketCategories[t.category]||t.category)}</span></div><div class="ticket-message-list">${detail.messages.map(m=>\`<article class="ticket-message ${m.senderRole==='ADMIN'?'staff':''}"><div><strong>${esc(m.senderName)} ${m.senderRole==='ADMIN'?'<small>Atendimento</small>':''}</strong><time>${shortDate(m.createdAt)}</time></div><p>${esc(m.body)}</p></article>\`).join('')}</div>${t.status==='CLOSED'?'<div class="alert-strip info">Este chamado foi encerrado. Se ainda precisar de ajuda, abra um novo.</div>':\`<form class="ticket-compose" data-ticket-compose ${isAdmin?'data-admin-ticket="true"':''} data-ticket-id="${esc(t.id)}"><textarea name="body" maxlength="2000" required placeholder="${isAdmin?'Escreva uma resposta para o cliente…':'Digite sua mensagem…'}"></textarea><button class="btn small" type="submit">Enviar resposta</button></form>\`}\`;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
function ticketConversationHtml(detail,isAdmin){const t=detail.ticket;const ticketCategories={GENERAL:'Dúvida geral',ORDER:'Pedido',RESERVATION:'Reserva',PAYMENT:'Pagamento',ACCOUNT:'Minha conta'};return `<div class="ticket-detail-top"><button class="btn outline small" data-ticket-back>← ${isAdmin?'Chamados':'Meus chamados'}</button>${pill(t.status)}<span>${esc(ticketCategories[t.category]||t.category)}</span></div><div class="ticket-message-list">${detail.messages.map(m=>`<article class="ticket-message ${m.senderRole==='ADMIN'?'staff':''}"><div><strong>${esc(m.senderName)} ${m.senderRole==='ADMIN'?'<small>Atendimento</small>':''}</strong><time>${shortDate(m.createdAt)}</time></div><p>${esc(m.body)}</p></article>`).join('')}</div>${t.status==='CLOSED'?'<div class="alert-strip info">Este chamado foi encerrado. Se ainda precisar de ajuda, abra um novo.</div>':`<form class="ticket-compose" data-ticket-compose ${isAdmin?'data-admin-ticket="true"':''} data-ticket-id="${esc(t.id)}"><textarea name="body" maxlength="2000" required placeholder="${isAdmin?'Escreva uma resposta para o cliente…':'Digite sua mensagem…'}"></textarea><button class="btn small" type="submit">Enviar resposta</button></form>`}`;}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@498:478 — linha 498

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:498)

```javascript
m=>
```

Executa a etapa `callback@498:478` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `shortDate`.

**Decisões condicionais examinadas:**
- `m.senderRole==='ADMIN'`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
m=>`<article class="ticket-message ${m.senderRole==='ADMIN'?'staff':''}"><div><strong>${esc(m.senderName)} ${m.senderRole==='ADMIN'?'<small>Atendimento</small>':''}</strong><time>${shortDate(m.createdAt)}</time></div><p>${esc(m.body)}</p></article>`
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### openTicket — linha 499

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:499)

```javascript
async function openTicket(id,isAdmin)
```

Executa a etapa `open Ticket` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`; `document.createElement`; `document.body.append`; `dialog.remove`; `root.setAttribute`; `root.querySelector`; `api`; `supportDetailPath`; `currentRoot.removeAttribute`; `esc`; `ticketConversationHtml`; `currentRoot.querySelector`; `returnToAdminTickets`; `loadSupportTicketList`; `notify`.

**Decisões condicionais examinadas:**
- `(!$('#support-modal'))`
- `(e.target===dialog)`
- `isAdmin`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function openTicket(id,isAdmin){try{if(!$('#support-modal')){const dialog=document.createElement('div');dialog.id='support-modal';dialog.className='modal-backdrop';dialog.innerHTML='<section class="modal support-modal" role="dialog" aria-modal="true" aria-labelledby="ticket-loading-title"></section>';document.body.append(dialog);dialog.onclick=e=>{if(e.target===dialog)dialog.remove();};}const root=$('#support-modal .support-modal');root.setAttribute('aria-busy','true');root.innerHTML='<div class="ticket-loading" role="status"><span class="loading-spinner" aria-hidden="true"></span><h2 id="ticket-loading-title">Abrindo chamado…</h2><p>Estamos carregando as mensagens.</p><button type="button" class="btn outline small" data-close-support>Fechar</button></div>';root.querySelector('[data-close-support]').onclick=()=>$('#support-modal')?.remove();state.supportAdminView=isAdmin;state.activeTicket=await api(supportDetailPath(isAdmin,id));if(!$('#support-modal'))return;const currentRoot=$('#support-modal .support-modal');currentRoot.removeAttribute('aria-busy');currentRoot.innerHTML=`<div class="modal-head"><div><span class="eyebrow">${isAdmin?'Central de chamados':'Atendimento FoodDelivery'}</span><h2 id="support-title">${esc(state.activeTicket.ticket.subject)}</h2><p>${isAdmin?`${esc(state.activeTicket.ticket.accountName)} · ${esc(state.activeTicket.ticket.accountEmail)}`:'Acompanhe as respostas e envie novas mensagens.'}</p></div><button class="close-button" data-close-support aria-label="Fechar">×</button></div>${ticketConversationHtml(state.activeTicket,isAdmin)}`;currentRoot.querySelector('[data-close-support]').onclick=()=>$('#support-modal')?.remove();currentRoot.querySelector('[data-ticket-back]')?.addEventListener('click',()=>isAdmin?returnToAdminTickets():loadSupportTicketList());currentRoot.querySelector('[data-ticket-compose]')?.addEventListener('submit',sendTicketMessage);}catch(error){$('#support-modal')?.remove();state.activeTicket=null;notify('Não foi possível abrir o chamado',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@499:353 — linha 499

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:499)

```javascript
e=>
```

Executa a etapa `callback@499:353` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `dialog.remove`.

**Decisões condicionais examinadas:**
- `(e.target===dialog)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@499:828 — linha 499

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:499)

```javascript
()=>
```

Executa a etapa `callback@499:828` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@499:1656 — linha 499

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:499)

```javascript
()=>
```

Executa a etapa `callback@499:1656` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@499:1764 — linha 499

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:499)

```javascript
()=>
```

Executa a etapa `callback@499:1764` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `returnToAdminTickets`; `loadSupportTicketList`.

**Decisões condicionais examinadas:**
- `isAdmin`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createSupportTicket — linha 500

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:500)

```javascript
async function createSupportTicket(event)
```

Executa a etapa `create Support Ticket` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `api`; `JSON.stringify`; `String`; `data.get`; `notify`; `renderSupportModal`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function createSupportTicket(event){event.preventDefault();const form=event.currentTarget,data=new FormData(form);try{const detail=await api('/support/tickets',{method:'POST',body:JSON.stringify({subject:String(data.get('subject')).trim(),category:String(data.get('category')),body:String(data.get('body')).trim()})});state.supportTickets=await api('/support/tickets/mine');state.activeTicket=detail;notify('Chamado enviado','A equipe de atendimento recebeu sua mensagem.','success');renderSupportModal();}catch(error){notify('Não conseguimos enviar o chamado',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sendTicketMessage — linha 501

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:501)

```javascript
async function sendTicketMessage(event)
```

Executa a etapa `send Ticket Message` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `String`; `new FormData`; `api`; `supportMessagePath`; `JSON.stringify`; `renderSupportModal`; `notify`.

**Decisões condicionais examinadas:**
- `(!body)`
- `(isAdmin)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function sendTicketMessage(event){event.preventDefault();const form=event.currentTarget,body=String(new FormData(form).get('body')||'').trim(),id=form.dataset.ticketId,isAdmin=form.dataset.adminTicket==='true';if(!body)return;try{state.activeTicket=await api(supportMessagePath(isAdmin,id),{method:'POST',body:JSON.stringify({body})});if(isAdmin)state.adminTickets=await api('/admin/support-tickets');else state.supportTickets=await api('/support/tickets/mine');renderSupportModal(isAdmin);}catch(error){notify('Mensagem não enviada',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateTicketStatus — linha 502

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:502)

```javascript
async function updateTicketStatus(id,status)
```

Executa a etapa `update Ticket Status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `encodeURIComponent`; `JSON.stringify`; `renderPage`; `notify`.

**Decisões condicionais examinadas:**
- `status==='CLOSED'`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function updateTicketStatus(id,status){try{await api(`/admin/support-tickets/${encodeURIComponent(id)}/status`,{method:'PATCH',body:JSON.stringify({status})});state.adminTickets=await api('/admin/support-tickets');renderPage();notify(status==='CLOSED'?'Chamado encerrado':'Chamado reaberto','O status foi atualizado.','success');}catch(error){notify('Não foi possível atualizar o chamado',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderHome — linha 504

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:504)

```javascript
function renderHome()
```

Executa a etapa `render Home` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryOptions.slice`; `state.filteredStores.filter`; `categoryMatches`; `state.stores.filter`; `state.search.trim`; `sectionCategories.map`; `esc`; `section.stores.slice`; `storeCard`; `state.filteredStores.map`; `icon`; `ranked.map`; `categoryTile`; `emptyState`.

**Decisões condicionais examinadas:**
- `state.category==='Todas'&&!state.search.trim()`
- `state.filteredStores.length`
- `activeCity`
- `activeUf`
- `city`
- `count===1`
- `state.category==='Todas'`
- `state.stores.length`

**Saída declarada:**
- `return \`<section class="home-location"><span class="home-location-icon">${icon('pin',20)}</span><span><strong>${city?\`Lojas e serviços em ${esc(city)}\`:'Escolha sua cidade para encontrar lojas próximas'}</strong><small>${city?'Mostrando estabelecimentos disponíveis nesta região.':'Informe seu endereço para ver estabelecimentos da sua região.'}</small></span><button class="btn outline small" data-location-picker>${city?'Alterar cidade':'Escolher cidade'}</button></section><section class="hero"><div class="hero-copy"><span class="eyebrow">Um bairro inteiro para descobrir</span><h1>Seu dia fica melhor<br/>quando é por perto.</h1><p>Peça seu favorito, marque um horário e apoie quem faz parte da sua rotina.</p><button data-scroll-stores>Explorar agora <span>→</span></button></div><div class="hero-art">🥗</div><div class="hero-note">Hoje é dia de <b>pedir algo gostoso</b> ✨</div></section> <section class="discovery-panel" aria-label="Categorias de lugares"><div class="discovery-heading"><div><span class="eyebrow">Escolha o seu momento</span><h2>O que combina com hoje?</h2><p>Explore os comércios da sua cidade por tipo.</p></div><span class="home-results-count"><strong>${count}</strong><span>${count===1?'lugar encontrado':'lugares encontrados'}</span></span></div> <div class="category-tiles" role="group" aria-label="Categorias em destaque">${ranked.map((item,index)=>categoryTile(item,index)).join('')}<button type="button" class="category-tile category-tile-all" data-page="categories"><span class="category-tile-icon">${icon('grid',29)}</span><strong>Todas as categorias</strong><small>Explore todos os tipos</small><b>Ver categorias <span aria-hidden="true">→</span></b></button></div></section> <section class="panel store-public-map" data-store-directory-map></section> <div class="section-head discovery-results-heading" id="stores-heading"><div><span class="eyebrow">Perto de você</span><h2>${state.category==='Todas'?'Descubra por perto':\`Lugares em ${esc(state.category)}\`}</h2></div><button data-category="Todas">Ver todos <span aria-hidden="true">→</span></button></div> ${storeSections||emptyState('⌕',state.stores.length?'Ainda não encontramos lugares por aqui':'Ainda não há lojas disponíveis nesta cidade',state.stores.length?'Tente outra busca ou escolha uma categoria diferente.':\`Não encontramos estabelecimentos aprovados em ${city||'uma cidade selecionada'}. As lojas aparecem na região em que foram cadastradas; escolha outra cidade para continuar explorando.\` ,state.stores.length?'Atualizar lista':'Ver outra cidade',state.stores.length?'refresh-stores':'choose-city')}\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@506:56 — linha 506

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:506)

```javascript
category=>
```

Executa a etapa `callback@506:56` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.filteredStores.filter`; `categoryMatches`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@506:112 — linha 506

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:506)

```javascript
store=>
```

Executa a etapa `callback@506:112` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryMatches`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@506:171 — linha 506

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:506)

```javascript
section=>
```

Executa a etapa `callback@506:171` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@507:45 — linha 507

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:507)

```javascript
(name,order)=>
```

Executa a etapa `callback@507:45` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.stores.filter`; `categoryMatches`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@507:132 — linha 507

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:507)

```javascript
store=>
```

Executa a etapa `callback@507:132` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryMatches`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@507:200 — linha 507

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:507)

```javascript
item=>
```

Executa a etapa `callback@507:200` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@507:232 — linha 507

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:507)

```javascript
(a,b)=>
```

Executa a etapa `callback@507:232` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@509:29 — linha 509

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:509)

```javascript
section=>
```

Executa a etapa `callback@509:29` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `section.stores.slice`; `storeCard`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@509:335 — linha 509

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:509)

```javascript
(store,index)=>
```

Executa a etapa `callback@509:335` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `storeCard`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@510:87 — linha 510

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:510)

```javascript
(store,index)=>
```

Executa a etapa `callback@510:87` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `storeCard`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@514:95 — linha 514

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:514)

```javascript
(item,index)=>
```

Executa a etapa `callback@514:95` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryTile`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### categoryTile — linha 520

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:520)

```javascript
function categoryTile(item,index=0)
```

Executa a etapa `category Tile` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.stores.filter`; `categoryMatches`; `esc`.

**Decisões condicionais examinadas:**
- `stores===1`

**Saída declarada:**
- `return \`<button type="button" class="category-tile category-tone-${index%6}" data-category-select="${esc(item.name)}"><span class="category-tile-icon">${esc(item.emoji||categoryEmoji[item.name]||'✦')}</span><strong>${esc(item.name)}</strong><small>${stores} ${stores===1?'loja disponível':'lojas disponíveis'}</small><b>Explorar <span aria-hidden="true">→</span></b></button>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@521:36 — linha 521

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:521)

```javascript
store=>
```

Executa a etapa `callback@521:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryMatches`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderAllCategories — linha 525

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:525)

```javascript
function renderAllCategories()
```

Executa a etapa `render All Categories` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryOptions.slice`; `state.stores.filter`; `categoryMatches`; `categories.map`; `categoryTile`.

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Comércio local</span><h1>Todas as categorias</h1><p>Escolha o que procura e veja os estabelecimentos disponíveis na sua cidade.</p></div><button class="btn outline" data-page="home">← Voltar para descobrir</button></div><div class="category-tiles category-tiles-all">${categories.map((item,index)=>categoryTile(item,index)).join('')}</div>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@526:49 — linha 526

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:526)

```javascript
(name,index)=>
```

Executa a etapa `callback@526:49` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.stores.filter`; `categoryMatches`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@526:136 — linha 526

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:526)

```javascript
store=>
```

Executa a etapa `callback@526:136` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryMatches`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@527:349 — linha 527

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:527)

```javascript
(item,index)=>
```

Executa a etapa `callback@527:349` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryTile`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderCategoryStores — linha 530

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:530)

```javascript
function renderCategoryStores()
```

Executa a etapa `render Category Stores` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.stores.filter`; `categoryMatches`; `esc`; `stores.map`; `storeCard`; `emptyState`.

**Decisões condicionais examinadas:**
- `state.category==='Todas'`
- `stores.length===1`
- `state.browseLocation?.city`
- `stores.length`

**Saída declarada:**
- `return \`<div class="page-head category-store-head"><div><span class="eyebrow">Estabelecimentos na sua cidade</span><h1>${esc(selected||'Escolha uma categoria')}</h1><p>${stores.length} ${stores.length===1?'opção disponível':'opções disponíveis'}${state.browseLocation?.city?\` em ${esc(state.browseLocation.city)}, ${esc(state.browseLocation.state||'')}\`:''}.</p></div><button class="btn outline" data-page="categories">← Todas as categorias</button></div><section class="panel store-public-map" data-store-directory-map></section>${stores.length?\`<div class="store-grid">${stores.map((store,index)=>storeCard(store,index)).join('')}</div>\`:emptyState('⌕','Ainda não há estabelecimentos nesta categoria','Volte para a lista completa e escolha outro tipo de comércio.','Ver todas as categorias','go-categories')}\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@532:36 — linha 532

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:532)

```javascript
store=>
```

Executa a etapa `callback@532:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryMatches`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@533:595 — linha 533

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:533)

```javascript
(store,index)=>
```

Executa a etapa `callback@533:595` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `storeCard`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeCard — linha 536

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:536)

```javascript
function storeCard(store,index=0)
```

Executa a etapa `store Card` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `apiAssetUrl`; `fulfillmentBadges`.

**Decisões condicionais examinadas:**
- `store.coverImageUrl`

**Saída declarada:**
- `return \`<article class="store-card" data-store="${esc(store.id)}" tabindex="0" role="button" aria-label="Abrir ${esc(store.name)}"><div class="store-cover ${style} ${store.coverImageUrl?'has-cover':''}" ${cover}><span class="store-badge">${esc(store.category||'Comércio local')}</span>${store.coverImageUrl?'':\`<span>${emoji}</span>\`}</div><div class="store-card-body"><div class="store-title-row"><h3>${esc(store.name)}</h3><span class="status-pill">Local</span></div><p class="store-desc">${esc(store.description||'Uma boa escolha pertinho de você.')}</p><div class="store-meta"><span>⌖ ${esc(store.address||'Na sua região')}</span></div>${fulfillmentBadges(store)}<div class="store-card-foot"><span>Consulte opções e valores</span><strong>Ver opções →</strong></div></div></article>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### emptyState — linha 543

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:543)

```javascript
function emptyState(icon,title,message,button,label)
```

Executa a etapa `empty State` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`.

**Decisões condicionais examinadas:**
- `button`

**Saída declarada:**
- `return \`<div class="empty-state"><div class="empty-icon">${icon}</div><h3>${esc(title)}</h3><p>${esc(message)}</p>${button?\`<button class="btn light small" data-action="${label}">${esc(button)}</button>\`:''}</div>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### bindPage — linha 545

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:545)

```javascript
function bindPage()
```

Executa a etapa `bind Page` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `cleanupStoreMaps`; `document.querySelectorAll`; `mountLocationPicker`; `$`; `mountStoreDirectoryMap`; `state.stores.filter`; `categoryMatches`; `formatCpfMask`; `formatCnpjMask`; `input.addEventListener`; `input.select`; `formatter`; `b.addEventListener`; `navigate`; `openStore`; `card.addEventListener`; `e.preventDefault`; `open`; `runAction`; `f.addEventListener`; `form.addEventListener`; `button.addEventListener`; `toggleServiceCity`; `deleteStoreCategory`; `openAdminStoreEditor`; `loadAddresses`; `renderPage`; `loadAdminAuditEvents`; `openTicket`; `updateTicketStatus`; `switchRole`; `reviewPlatformRequest`; `resubmitStore`; `mountOperatingHours`; `activeMerchantStore`; `notify`; `setCatalogItemActive`; `setProfessionalActive`; `select.addEventListener`; `syncCatalogItemFields`; `syncCatalogItemRisk`; `reviewOperatorApplication`; `setOperatorActive`; `setAccountActive`; `openAdminAccountEditor`; `setAccountRole`; `resetAccountPassword`; `api`; `mountStoreCover`; `mountCoverReview`; `updateOrder`; `offerDelivery`; `updateReservation`; `approveStore`; `storeApprovalId`; `setStoreActive`; `acceptDelivery`; `updateDelivery`; `shareCourierLocation`; `render`; `document.createElement`; `decline.addEventListener`; `simulatePayment`; `pay.after`; `role`; `window.scrollTo`; `openChat`; `area.addEventListener`; `closeChat`.

**Decisões condicionais examinadas:**
- `($('[data-store-directory-map]'))`
- `state.page==='categoryStores'`
- `(!formatter)`
- `(input.dataset.mask==='cpf'&&input.value)`
- `(input.dataset.mask==='cnpj'&&input.value)`
- `(input.dataset.mask==='currency')`
- `(e.key==='Enter'||e.key===' ')`
- `(state.accountSection==='addresses')`
- `(state.adminDefinitionSection==='audit')`
- `($('[data-operating-hours]'))`
- `summary?.notified`
- `($('[data-store-cover]'))`
- `($('[data-cover-review]'))`
- `approved`
- `(globalThis.FOODDELIVERY_DEMO&&$('[data-checkout-pay]'))`
- `(role()==='ADMIN')`
- `(role()==='MERCHANT')`
- `state.trackingMapOrderId===button.dataset.trackingMap`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
input.addEventListener('focus',()=>{if(input.dataset.mask==='currency')input.select();});
input.addEventListener('input',()=>{input.value=formatter(input.value);});
document.querySelectorAll('[data-category-select]').forEach(b=>b.addEventListener('click',()=>{state.category=b.dataset.categorySelect;state.search='';$('#global-search')?.setAttribute('value','');navigate('categoryStores');}));
document.querySelectorAll('[data-category]').forEach(b=>b.addEventListener('click',()=>{state.category=b.dataset.category;state.search='';$('#global-search')?.setAttribute('value','');navigate('categoryStores');}));
$('[data-action="go-categories"]')?.addEventListener('click',()=>navigate('categories'));
document.querySelectorAll('[data-store]').forEach(card=>{const open=()=>openStore(card.dataset.store);card.addEventListener('click',open);card.addEventListener('keydown',e=>{if(e.key==='Enter'||e.key===' '){e.preventDefault();open();}});});
document.querySelectorAll('[data-action]').forEach(b=>b.addEventListener('click',()=>runAction(b.dataset.action)));
document.querySelectorAll('[data-checkout]').forEach(b=>b.addEventListener('click',checkout));
document.querySelectorAll('[data-profile-save]').forEach(f=>f.addEventListener('submit',saveProfile));
$('[data-admin-profile-save]')?.addEventListener('submit',saveAdminProfile);
$('[data-service-city-create]')?.addEventListener('submit',createServiceCity);
$('[data-store-category-create]')?.addEventListener('submit',createStoreCategory);
document.querySelectorAll('[data-service-city-update]').forEach(form=>form.addEventListener('submit',updateServiceCity));
document.querySelectorAll('[data-service-city-toggle]').forEach(button=>button.addEventListener('click',()=>toggleServiceCity(button.dataset.serviceCityToggle,button.dataset.enabled==='true')));
document.querySelectorAll('[data-store-category-delete]').forEach(button=>button.addEventListener('click',()=>deleteStoreCategory(button.dataset.storeCategoryDelete)));
$('[data-admin-account-edit]')?.addEventListener('submit',saveAdminAccount);
document.querySelectorAll('[data-admin-edit-store]').forEach(button=>button.addEventListener('click',()=>openAdminStoreEditor(button.dataset.adminEditStore)));
document.querySelectorAll('[data-password-change]').forEach(f=>f.addEventListener('submit',changePassword));
$('[data-phone-verify-request]')?.addEventListener('click',requestPhoneVerification);
$('[data-phone-verify-confirm]')?.addEventListener('submit',confirmPhoneVerification);
document.querySelectorAll('[data-account-section]').forEach(b=>b.addEventListener('click',async()=>{state.accountSection=b.dataset.accountSection;if(state.accountSection==='addresses')await loadAddresses();renderPage();}));
document.querySelectorAll('[data-admin-definition-section]').forEach(button=>button.addEventListener('click',async()=>{state.adminDefinitionSection=button.dataset.adminDefinitionSection;if(state.adminDefinitionSection==='audit')await loadAdminAuditEvents();renderPage();}));
document.querySelectorAll('[data-ticket-open]').forEach(b=>b.addEventListener('click',()=>openTicket(b.dataset.ticketOpen,b.dataset.ticketAdmin==='true')));
document.querySelectorAll('[data-ticket-status]').forEach(b=>b.addEventListener('click',()=>updateTicketStatus(b.dataset.ticketStatus,b.dataset.status)));
document.querySelectorAll('[data-ticket-compose]').forEach(f=>f.addEventListener('submit',sendTicketMessage));
document.querySelectorAll('[data-ticket-create]').forEach(f=>f.addEventListener('submit',createSupportTicket));
document.querySelectorAll('[data-support-list]').forEach(b=>b.addEventListener('click',loadSupportTicketList));
document.querySelectorAll('[data-switch-role]').forEach(b=>b.addEventListener('click',()=>switchRole(b.dataset.switchRole)));
document.querySelectorAll('[data-operator-request]').forEach(f=>f.addEventListener('submit',requestOperatorAccess));
document.querySelectorAll('[data-city-request]').forEach(f=>f.addEventListener('submit',requestCityAccess));
document.querySelectorAll('[data-platform-review]').forEach(b=>b.addEventListener('click',()=>reviewPlatformRequest(b.dataset.platformReview,b.dataset.decision)));
document.querySelectorAll('[data-create-store]').forEach(f=>f.addEventListener('submit',createStore));
document.querySelectorAll('[data-resubmit-store]').forEach(b=>b.addEventListener('click',()=>resubmitStore(b.dataset.resubmitStore)));
$('[data-merchant-store-select]')?.addEventListener('change',selectMerchantStore);
document.querySelectorAll('[data-item-deactivate]').forEach(button=>button.addEventListener('click',()=>setCatalogItemActive(button.dataset.itemDeactivate,false)));
document.querySelectorAll('[data-item-activate]').forEach(button=>button.addEventListener('click',()=>setCatalogItemActive(button.dataset.itemActivate,true)));
document.querySelectorAll('[data-professional-create]').forEach(form=>form.addEventListener('submit',createProfessional));
document.querySelectorAll('[data-professional-active]').forEach(button=>button.addEventListener('click',()=>setProfessionalActive(button.dataset.professionalActive,button.dataset.active==='true')));
document.querySelectorAll('[data-item-kind]').forEach(select=>select.addEventListener('change',()=>syncCatalogItemFields(select.form)));
document.querySelectorAll('[data-pay-after-service]').forEach(select=>select.addEventListener('change',()=>syncCatalogItemRisk(select.form)));
document.querySelectorAll('[data-store-filter]').forEach(button=>button.addEventListener('click',()=>{state.adminStoreFilter=button.dataset.storeFilter;renderPage();}));
document.querySelectorAll('[data-update-store]').forEach(f=>f.addEventListener('submit',updateStore));
document.querySelectorAll('[data-create-operator]').forEach(f=>f.addEventListener('submit',createOperator));
document.querySelectorAll('[data-operator-application]').forEach(b=>b.addEventListener('click',()=>reviewOperatorApplication(b.dataset.operatorApplication,b.dataset.decision)));
document.querySelectorAll('[data-operator-active]').forEach(b=>b.addEventListener('click',()=>setOperatorActive(b.dataset.operatorActive,b.dataset.active==='true')));
document.querySelectorAll('[data-account-active]').forEach(b=>b.addEventListener('click',()=>setAccountActive(b.dataset.accountActive,b.dataset.active==='true')));
document.querySelectorAll('[data-admin-edit-account]').forEach(b=>b.addEventListener('click',()=>openAdminAccountEditor(b.dataset.adminEditAccount)));
document.querySelectorAll('[data-account-role]').forEach(b=>b.addEventListener('click',()=>setAccountRole(b.dataset.accountRole,b.dataset.role,b.dataset.active==='true')));
document.querySelectorAll('[data-reset-password]').forEach(b=>b.addEventListener('click',()=>resetAccountPassword(b.dataset.resetPassword,b.dataset.name)));
$('[data-dismiss-temp-password]')?.addEventListener('click',()=>{state.adminTemporaryPassword='';renderPage();});
$('[data-refresh-integrations]')?.addEventListener('click',async()=>{try{state.adminIntegrations=await api('/admin/integrations');renderPage();notify('Status atualizado','A API informou a presença das configurações do servidor.','info');}catch(error){notify('Não foi possível atualizar os serviços',error.message,'error');}});
$('[data-refresh-audit]')?.addEventListener('click',async()=>{await loadAdminAuditEvents();renderPage();});
document.querySelectorAll('[data-order-status]').forEach(b=>b.addEventListener('click',()=>updateOrder(b.dataset.id,b.dataset.orderStatus)));
document.querySelectorAll('[data-offer-delivery]').forEach(b=>b.addEventListener('click',()=>offerDelivery(b.dataset.offerDelivery)));
document.querySelectorAll('[data-reservation-status]').forEach(b=>b.addEventListener('click',()=>updateReservation(b.dataset.id,b.dataset.reservationStatus)));
document.querySelectorAll('[data-approve-store]').forEach(b=>b.addEventListener('click',()=>approveStore(storeApprovalId(b))));
document.querySelectorAll('[data-store-active]').forEach(b=>b.addEventListener('click',()=>setStoreActive(b.dataset.storeActive,b.dataset.active==='true')));
document.querySelectorAll('[data-accept-delivery]').forEach(b=>b.addEventListener('click',()=>acceptDelivery(b.dataset.id)));
document.querySelectorAll('[data-delivery-status]').forEach(b=>b.addEventListener('click',()=>updateDelivery(b.dataset.id,b.dataset.deliveryStatus)));
document.querySelectorAll('[data-share-location]').forEach(b=>b.addEventListener('click',()=>shareCourierLocation(b.dataset.shareLocation)));
document.querySelectorAll('[data-reopen-store]').forEach(b=>b.addEventListener('click',()=>openStore(b.dataset.reopenStore)));
$('#open-cart')?.addEventListener('click',()=>{state.cartOpen=true;render();});
$('[data-scroll-stores]')?.addEventListener('click',()=>$('#stores-heading')?.scrollIntoView({behavior:'smooth'}));
$('[data-checkout-pay]')?.addEventListener('click',approvePayment);
if(globalThis.FOODDELIVERY_DEMO&&$('[data-checkout-pay]')){const pay=$('[data-checkout-pay]');pay.textContent='Simular pagamento aprovado';const decline=document.createElement('button');decline.className='btn danger block';decline.textContent='Simular pagamento recusado';decline.type='button';decline.addEventListener('click',()=>simulatePayment('decline'));pay.after(decline);}
document.querySelectorAll('[data-section]').forEach(button=>button.addEventListener('click',()=>{
document.querySelectorAll('[data-admin-team-section]').forEach(button=>button.addEventListener('click',()=>{
document.querySelectorAll('[data-tracking-map]').forEach(button=>button.addEventListener('click',()=>{
document.querySelectorAll('[data-open-chat-type]').forEach(button=>button.addEventListener('click',()=>openChat(button.dataset.openChatType,button.dataset.openChatId)));
document.querySelectorAll('[data-chat-form]').forEach(form=>form.addEventListener('submit',sendChatMessage));
document.querySelectorAll('[data-chat-draft]').forEach(area=>area.addEventListener('input',()=>{state.chatDrafts[area.dataset.chatDraft]=area.value;}));
document.querySelectorAll('[data-close-chat]').forEach(button=>button.addEventListener('click',()=>closeChat(button.dataset.closeChat)));
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@547:64 — linha 547

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:547)

```javascript
root=>
```

Executa a etapa `callback@547:64` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `mountLocationPicker`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@548:143 — linha 548

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:548)

```javascript
s=>
```

Executa a etapa `callback@548:143` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryMatches`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@550:52 — linha 550

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:550)

```javascript
input=>
```

Executa a etapa `callback@550:52` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `formatCpfMask`; `formatCnpjMask`; `input.addEventListener`; `input.select`; `formatter`.

**Decisões condicionais examinadas:**
- `(!formatter)`
- `(input.dataset.mask==='cpf'&&input.value)`
- `(input.dataset.mask==='cnpj'&&input.value)`
- `(input.dataset.mask==='currency')`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
input.addEventListener('focus',()=>{if(input.dataset.mask==='currency')input.select();});
input.addEventListener('input',()=>{input.value=formatter(input.value);});
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@555:36 — linha 555

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:555)

```javascript
()=>
```

Executa a etapa `callback@555:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `input.select`.

**Decisões condicionais examinadas:**
- `(input.dataset.mask==='currency')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@556:36 — linha 556

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:556)

```javascript
()=>
```

Executa a etapa `callback@556:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `formatter`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@558:63 — linha 558

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:558)

```javascript
b=>
```

Executa a etapa `callback@558:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `$`; `navigate`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>{state.category=b.dataset.categorySelect;state.search='';$('#global-search')?.setAttribute('value','');navigate('categoryStores');})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@558:93 — linha 558

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:558)

```javascript
()=>
```

Executa a etapa `callback@558:93` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`; `navigate`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
()=>{state.category=b.dataset.categorySelect;state.search='';$('#global-search')?.setAttribute('value','');navigate('categoryStores');}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@559:56 — linha 559

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:559)

```javascript
b=>
```

Executa a etapa `callback@559:56` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `$`; `navigate`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>{state.category=b.dataset.category;state.search='';$('#global-search')?.setAttribute('value','');navigate('categoryStores');})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@559:86 — linha 559

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:559)

```javascript
()=>
```

Executa a etapa `callback@559:86` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`; `navigate`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
()=>{state.category=b.dataset.category;state.search='';$('#global-search')?.setAttribute('value','');navigate('categoryStores');}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@560:64 — linha 560

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:560)

```javascript
()=>
```

Executa a etapa `callback@560:64` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `navigate`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@561:53 — linha 561

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:561)

```javascript
card=>
```

Executa a etapa `callback@561:53` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openStore`; `card.addEventListener`; `e.preventDefault`; `open`.

**Decisões condicionais examinadas:**
- `(e.key==='Enter'||e.key===' ')`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
card=>{const open=()=>openStore(card.dataset.store);card.addEventListener('click',open);card.addEventListener('keydown',e=>{if(e.key==='Enter'||e.key===' '){e.preventDefault();open();}});}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### open — linha 561

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:561)

```javascript
()=>
```

Executa a etapa `open` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openStore`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@561:173 — linha 561

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:561)

```javascript
e=>
```

Executa a etapa `callback@561:173` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `e.preventDefault`; `open`.

**Decisões condicionais examinadas:**
- `(e.key==='Enter'||e.key===' ')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@562:54 — linha 562

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:562)

```javascript
b=>
```

Executa a etapa `callback@562:54` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `runAction`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>runAction(b.dataset.action))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@562:84 — linha 562

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:562)

```javascript
()=>
```

Executa a etapa `callback@562:84` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `runAction`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@563:56 — linha 563

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:563)

```javascript
b=>
```

Executa a etapa `callback@563:56` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',checkout)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@564:60 — linha 564

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:564)

```javascript
f=>
```

Executa a etapa `callback@564:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `f.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
f=>f.addEventListener('submit',saveProfile)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@568:67 — linha 568

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:568)

```javascript
form=>
```

Executa a etapa `callback@568:67` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
form=>form.addEventListener('submit',updateServiceCity)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@569:67 — linha 569

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:569)

```javascript
button=>
```

Executa a etapa `callback@569:67` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `toggleServiceCity`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>toggleServiceCity(button.dataset.serviceCityToggle,button.dataset.enabled==='true'))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@569:107 — linha 569

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:569)

```javascript
()=>
```

Executa a etapa `callback@569:107` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `toggleServiceCity`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@570:69 — linha 570

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:570)

```javascript
button=>
```

Executa a etapa `callback@570:69` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `deleteStoreCategory`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>deleteStoreCategory(button.dataset.storeCategoryDelete))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@570:109 — linha 570

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:570)

```javascript
()=>
```

Executa a etapa `callback@570:109` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `deleteStoreCategory`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@572:64 — linha 572

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:572)

```javascript
button=>
```

Executa a etapa `callback@572:64` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `openAdminStoreEditor`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>openAdminStoreEditor(button.dataset.adminEditStore))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@572:104 — linha 572

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:572)

```javascript
()=>
```

Executa a etapa `callback@572:104` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openAdminStoreEditor`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@573:63 — linha 573

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:573)

```javascript
f=>
```

Executa a etapa `callback@573:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `f.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
f=>f.addEventListener('submit',changePassword)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@576:63 — linha 576

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:576)

```javascript
b=>
```

Executa a etapa `callback@576:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `loadAddresses`; `renderPage`.

**Decisões condicionais examinadas:**
- `(state.accountSection==='addresses')`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',async()=>{state.accountSection=b.dataset.accountSection;if(state.accountSection==='addresses')await loadAddresses();renderPage();})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@576:93 — linha 576

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:576)

```javascript
async()=>
```

Executa a etapa `callback@576:93` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `loadAddresses`; `renderPage`.

**Decisões condicionais examinadas:**
- `(state.accountSection==='addresses')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@577:72 — linha 577

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:577)

```javascript
button=>
```

Executa a etapa `callback@577:72` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `loadAdminAuditEvents`; `renderPage`.

**Decisões condicionais examinadas:**
- `(state.adminDefinitionSection==='audit')`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',async()=>{state.adminDefinitionSection=button.dataset.adminDefinitionSection;if(state.adminDefinitionSection==='audit')await loadAdminAuditEvents();renderPage();})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@577:112 — linha 577

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:577)

```javascript
async()=>
```

Executa a etapa `callback@577:112` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `loadAdminAuditEvents`; `renderPage`.

**Decisões condicionais examinadas:**
- `(state.adminDefinitionSection==='audit')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@578:59 — linha 578

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:578)

```javascript
b=>
```

Executa a etapa `callback@578:59` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `openTicket`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>openTicket(b.dataset.ticketOpen,b.dataset.ticketAdmin==='true'))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@578:89 — linha 578

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:578)

```javascript
()=>
```

Executa a etapa `callback@578:89` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openTicket`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@579:61 — linha 579

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:579)

```javascript
b=>
```

Executa a etapa `callback@579:61` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `updateTicketStatus`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>updateTicketStatus(b.dataset.ticketStatus,b.dataset.status))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@579:91 — linha 579

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:579)

```javascript
()=>
```

Executa a etapa `callback@579:91` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `updateTicketStatus`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@580:62 — linha 580

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:580)

```javascript
f=>
```

Executa a etapa `callback@580:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `f.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
f=>f.addEventListener('submit',sendTicketMessage)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@581:61 — linha 581

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:581)

```javascript
f=>
```

Executa a etapa `callback@581:61` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `f.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
f=>f.addEventListener('submit',createSupportTicket)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@582:60 — linha 582

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:582)

```javascript
b=>
```

Executa a etapa `callback@582:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',loadSupportTicketList)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@583:59 — linha 583

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:583)

```javascript
b=>
```

Executa a etapa `callback@583:59` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `switchRole`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>switchRole(b.dataset.switchRole))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@583:89 — linha 583

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:583)

```javascript
()=>
```

Executa a etapa `callback@583:89` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `switchRole`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@584:64 — linha 584

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:584)

```javascript
f=>
```

Executa a etapa `callback@584:64` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `f.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
f=>f.addEventListener('submit',requestOperatorAccess)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@585:60 — linha 585

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:585)

```javascript
f=>
```

Executa a etapa `callback@585:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `f.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
f=>f.addEventListener('submit',requestCityAccess)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@586:63 — linha 586

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:586)

```javascript
b=>
```

Executa a etapa `callback@586:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `reviewPlatformRequest`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>reviewPlatformRequest(b.dataset.platformReview,b.dataset.decision))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@586:93 — linha 586

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:586)

```javascript
()=>
```

Executa a etapa `callback@586:93` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reviewPlatformRequest`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@587:60 — linha 587

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:587)

```javascript
f=>
```

Executa a etapa `callback@587:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `f.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
f=>f.addEventListener('submit',createStore)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@588:62 — linha 588

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:588)

```javascript
b=>
```

Executa a etapa `callback@588:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `resubmitStore`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>resubmitStore(b.dataset.resubmitStore))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@588:92 — linha 588

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:588)

```javascript
()=>
```

Executa a etapa `callback@588:92` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `resubmitStore`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@590:147 — linha 590

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:590)

```javascript
(config,summary)=>
```

Executa a etapa `callback@590:147` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `summary?.notified`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@590:428 — linha 590

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:590)

```javascript
error=>
```

Executa a etapa `callback@590:428` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notify`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@591:63 — linha 591

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:591)

```javascript
button=>
```

Executa a etapa `callback@591:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `setCatalogItemActive`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>setCatalogItemActive(button.dataset.itemDeactivate,false))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@591:103 — linha 591

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:591)

```javascript
()=>
```

Executa a etapa `callback@591:103` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setCatalogItemActive`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@592:61 — linha 592

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:592)

```javascript
button=>
```

Executa a etapa `callback@592:61` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `setCatalogItemActive`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>setCatalogItemActive(button.dataset.itemActivate,true))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@592:101 — linha 592

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:592)

```javascript
()=>
```

Executa a etapa `callback@592:101` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setCatalogItemActive`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@593:67 — linha 593

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:593)

```javascript
form=>
```

Executa a etapa `callback@593:67` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
form=>form.addEventListener('submit',createProfessional)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@594:67 — linha 594

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:594)

```javascript
button=>
```

Executa a etapa `callback@594:67` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `setProfessionalActive`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>setProfessionalActive(button.dataset.professionalActive,button.dataset.active==='true'))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@594:107 — linha 594

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:594)

```javascript
()=>
```

Executa a etapa `callback@594:107` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setProfessionalActive`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@595:57 — linha 595

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:595)

```javascript
select=>
```

Executa a etapa `callback@595:57` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `select.addEventListener`; `syncCatalogItemFields`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
select=>select.addEventListener('change',()=>syncCatalogItemFields(select.form))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@595:98 — linha 595

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:595)

```javascript
()=>
```

Executa a etapa `callback@595:98` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `syncCatalogItemFields`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@596:65 — linha 596

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:596)

```javascript
select=>
```

Executa a etapa `callback@596:65` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `select.addEventListener`; `syncCatalogItemRisk`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
select=>select.addEventListener('change',()=>syncCatalogItemRisk(select.form))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@596:106 — linha 596

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:596)

```javascript
()=>
```

Executa a etapa `callback@596:106` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `syncCatalogItemRisk`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@597:60 — linha 597

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:597)

```javascript
button=>
```

Executa a etapa `callback@597:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `renderPage`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>{state.adminStoreFilter=button.dataset.storeFilter;renderPage();})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@597:100 — linha 597

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:597)

```javascript
()=>
```

Executa a etapa `callback@597:100` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `renderPage`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@598:60 — linha 598

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:598)

```javascript
f=>
```

Executa a etapa `callback@598:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `f.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
f=>f.addEventListener('submit',updateStore)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@599:63 — linha 599

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:599)

```javascript
f=>
```

Executa a etapa `callback@599:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `f.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
f=>f.addEventListener('submit',createOperator)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@600:68 — linha 600

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:600)

```javascript
b=>
```

Executa a etapa `callback@600:68` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `reviewOperatorApplication`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>reviewOperatorApplication(b.dataset.operatorApplication,b.dataset.decision))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@600:98 — linha 600

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:600)

```javascript
()=>
```

Executa a etapa `callback@600:98` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reviewOperatorApplication`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@601:63 — linha 601

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:601)

```javascript
b=>
```

Executa a etapa `callback@601:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `setOperatorActive`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>setOperatorActive(b.dataset.operatorActive,b.dataset.active==='true'))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@601:93 — linha 601

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:601)

```javascript
()=>
```

Executa a etapa `callback@601:93` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setOperatorActive`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@602:62 — linha 602

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:602)

```javascript
b=>
```

Executa a etapa `callback@602:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `setAccountActive`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>setAccountActive(b.dataset.accountActive,b.dataset.active==='true'))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@602:92 — linha 602

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:602)

```javascript
()=>
```

Executa a etapa `callback@602:92` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setAccountActive`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@603:66 — linha 603

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:603)

```javascript
b=>
```

Executa a etapa `callback@603:66` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `openAdminAccountEditor`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>openAdminAccountEditor(b.dataset.adminEditAccount))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@603:96 — linha 603

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:603)

```javascript
()=>
```

Executa a etapa `callback@603:96` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openAdminAccountEditor`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@604:60 — linha 604

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:604)

```javascript
b=>
```

Executa a etapa `callback@604:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `setAccountRole`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>setAccountRole(b.dataset.accountRole,b.dataset.role,b.dataset.active==='true'))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@604:90 — linha 604

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:604)

```javascript
()=>
```

Executa a etapa `callback@604:90` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setAccountRole`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@605:62 — linha 605

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:605)

```javascript
b=>
```

Executa a etapa `callback@605:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `resetAccountPassword`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>resetAccountPassword(b.dataset.resetPassword,b.dataset.name))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@605:92 — linha 605

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:605)

```javascript
()=>
```

Executa a etapa `callback@605:92` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `resetAccountPassword`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@606:63 — linha 606

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:606)

```javascript
()=>
```

Executa a etapa `callback@606:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `renderPage`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@607:62 — linha 607

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:607)

```javascript
async()=>
```

Executa a etapa `callback@607:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `renderPage`; `notify`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async()=>{try{state.adminIntegrations=await api('/admin/integrations');renderPage();notify('Status atualizado','A API informou a presença das configurações do servidor.','info');}catch(error){notify('Não foi possível atualizar os serviços',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@608:55 — linha 608

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:608)

```javascript
async()=>
```

Executa a etapa `callback@608:55` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `loadAdminAuditEvents`; `renderPage`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@609:101 — linha 609

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:609)

```javascript
()=>
```

Executa a etapa `callback@609:101` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notify`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@609:209 — linha 609

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:609)

```javascript
e=>
```

Executa a etapa `callback@609:209` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notify`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@610:82 — linha 610

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:610)

```javascript
approved=>
```

Executa a etapa `callback@610:82` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notify`.

**Decisões condicionais examinadas:**
- `approved`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@611:60 — linha 611

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:611)

```javascript
b=>
```

Executa a etapa `callback@611:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `updateOrder`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>updateOrder(b.dataset.id,b.dataset.orderStatus))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@611:90 — linha 611

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:611)

```javascript
()=>
```

Executa a etapa `callback@611:90` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `updateOrder`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@612:62 — linha 612

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:612)

```javascript
b=>
```

Executa a etapa `callback@612:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `offerDelivery`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>offerDelivery(b.dataset.offerDelivery))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@612:92 — linha 612

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:612)

```javascript
()=>
```

Executa a etapa `callback@612:92` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `offerDelivery`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@613:66 — linha 613

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:613)

```javascript
b=>
```

Executa a etapa `callback@613:66` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `updateReservation`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>updateReservation(b.dataset.id,b.dataset.reservationStatus))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@613:96 — linha 613

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:613)

```javascript
()=>
```

Executa a etapa `callback@613:96` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `updateReservation`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@614:61 — linha 614

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:614)

```javascript
b=>
```

Executa a etapa `callback@614:61` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `approveStore`; `storeApprovalId`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>approveStore(storeApprovalId(b)))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@614:91 — linha 614

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:614)

```javascript
()=>
```

Executa a etapa `callback@614:91` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `approveStore`; `storeApprovalId`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@615:60 — linha 615

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:615)

```javascript
b=>
```

Executa a etapa `callback@615:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `setStoreActive`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>setStoreActive(b.dataset.storeActive,b.dataset.active==='true'))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@615:90 — linha 615

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:615)

```javascript
()=>
```

Executa a etapa `callback@615:90` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setStoreActive`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@616:63 — linha 616

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:616)

```javascript
b=>
```

Executa a etapa `callback@616:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `acceptDelivery`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>acceptDelivery(b.dataset.id))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@616:93 — linha 616

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:616)

```javascript
()=>
```

Executa a etapa `callback@616:93` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `acceptDelivery`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@617:63 — linha 617

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:617)

```javascript
b=>
```

Executa a etapa `callback@617:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `updateDelivery`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>updateDelivery(b.dataset.id,b.dataset.deliveryStatus))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@617:93 — linha 617

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:617)

```javascript
()=>
```

Executa a etapa `callback@617:93` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `updateDelivery`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@618:62 — linha 618

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:618)

```javascript
b=>
```

Executa a etapa `callback@618:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `shareCourierLocation`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>shareCourierLocation(b.dataset.shareLocation))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@618:92 — linha 618

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:618)

```javascript
()=>
```

Executa a etapa `callback@618:92` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `shareCourierLocation`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@619:60 — linha 619

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:619)

```javascript
b=>
```

Executa a etapa `callback@619:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `openStore`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>openStore(b.dataset.reopenStore))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@619:90 — linha 619

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:619)

```javascript
()=>
```

Executa a etapa `callback@619:90` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openStore`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@620:45 — linha 620

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:620)

```javascript
()=>
```

Executa a etapa `callback@620:45` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `render`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@621:55 — linha 621

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:621)

```javascript
()=>
```

Executa a etapa `callback@621:55` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@623:330 — linha 623

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:623)

```javascript
()=>
```

Executa a etapa `callback@623:330` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `simulatePayment`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@624:55 — linha 624

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:624)

```javascript
button=>
```

Executa a etapa `callback@624:55` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `role`; `renderPage`; `window.scrollTo`.

**Decisões condicionais examinadas:**
- `(role()==='ADMIN')`
- `(role()==='MERCHANT')`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>{
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@624:95 — linha 624

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:624)

```javascript
()=>
```

Executa a etapa `callback@624:95` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `role`; `renderPage`; `window.scrollTo`.

**Decisões condicionais examinadas:**
- `(role()==='ADMIN')`
- `(role()==='MERCHANT')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@630:66 — linha 630

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:630)

```javascript
button=>
```

Executa a etapa `callback@630:66` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `renderPage`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>{
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@630:106 — linha 630

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:630)

```javascript
()=>
```

Executa a etapa `callback@630:106` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `renderPage`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@633:60 — linha 633

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:633)

```javascript
button=>
```

Executa a etapa `callback@633:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `renderPage`.

**Decisões condicionais examinadas:**
- `state.trackingMapOrderId===button.dataset.trackingMap`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>{
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@633:100 — linha 633

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:633)

```javascript
()=>
```

Executa a etapa `callback@633:100` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `renderPage`.

**Decisões condicionais examinadas:**
- `state.trackingMapOrderId===button.dataset.trackingMap`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@637:62 — linha 637

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:637)

```javascript
button=>
```

Executa a etapa `callback@637:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `openChat`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>openChat(button.dataset.openChatType,button.dataset.openChatId))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@637:102 — linha 637

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:637)

```javascript
()=>
```

Executa a etapa `callback@637:102` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openChat`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@638:57 — linha 638

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:638)

```javascript
form=>
```

Executa a etapa `callback@638:57` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
form=>form.addEventListener('submit',sendChatMessage)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@639:58 — linha 639

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:639)

```javascript
area=>
```

Executa a etapa `callback@639:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `area.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
area=>area.addEventListener('input',()=>{state.chatDrafts[area.dataset.chatDraft]=area.value;})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@639:94 — linha 639

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:639)

```javascript
()=>
```

Executa a etapa `callback@639:94` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@640:58 — linha 640

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:640)

```javascript
button=>
```

Executa a etapa `callback@640:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `closeChat`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>closeChat(button.dataset.closeChat))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@640:98 — linha 640

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:640)

```javascript
()=>
```

Executa a etapa `callback@640:98` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `closeChat`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### runAction — linha 643

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:643)

```javascript
async function runAction(action)
```

Executa a etapa `run Action` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `loadStores`; `renderPage`; `notify`; `openLocationPicker`; `loadAdminData`.

**Decisões condicionais examinadas:**
- `(action==='refresh-stores')`
- `(action==='choose-city')`
- `(action==='reload-admin')`
- `(state.adminLoadError)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### openStore — linha 649

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:649)

```javascript
async function openStore(id)
```

Executa a etapa `open Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.stores.find`; `String`; `Promise.all`; `api`; `encodeURIComponent`; `render`; `notify`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@650:47 — linha 650

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:650)

```javascript
s=>
```

Executa a etapa `callback@650:47` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@650:271 — linha 650

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:650)

```javascript
()=>
```

Executa a etapa `callback@650:271` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### currentStoreOpen — linha 654

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:654)

```javascript
function currentStoreOpen(store, schedule=state.storeHours)
```

Executa a etapa `current Store Open` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `storeOpenAt`.

**Saída declarada:**
- `return storeOpenAt(schedule, store?.timezone || 'America/Sao_Paulo');`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### operatingHoursPanel — linha 655

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:655)

```javascript
function operatingHoursPanel()
```

Executa a etapa `operating Hours Panel` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return '<section class="panel operating-hours-panel" data-operating-hours></section>';`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderStoreModal — linha 657

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:657)

```javascript
function renderStoreModal()
```

Executa a etapa `render Store Modal` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `currentStoreOpen`; `storeHoursStatusRelevant`; `document.createElement`; `esc`; `fulfillmentBadges`; `apiAssetUrl`; `storeMapUrl`; `renderPublicHours`; `state.storeItems.map`; `catalogItemIcon`; `money`; `emptyState`; `document.body.append`; `modal.querySelector`; `mountStoreDirectoryMap`; `$`; `modal.remove`; `cleanupStoreMaps`; `modal.addEventListener`; `modal.querySelectorAll`; `b.addEventListener`; `addToCart`; `showReserveForm`.

**Decisões condicionais examinadas:**
- `(!store)`
- `isOpen`
- `store.latitude!=null&&store.longitude!=null`
- `store.coverImageUrl`
- `showHoursStatus`
- `store.inPersonEnabled!==false`
- `state.storeItems.length`
- `item.serviceItem`
- `item.payAfterService`
- `item.stock>0`
- `store.deliveryEnabled===false&&store.pickupEnabled===false`
- `item.stock<1`
- `(modal.querySelector('[data-profile-store-map]'))`
- `(e.target===modal)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
modal.innerHTML=`<section class="modal" role="dialog" aria-modal="true" aria-labelledby="store-modal-title"><div class="modal-head"><div><span class="eyebrow">${esc(store.category||'Comércio local')}</span><h2 id="store-modal-title">${esc(store.name)}</h2><p>${esc(store.description||store.address||'Escolha uma opção para continuar.')}</p></div><button class="close-button" data-close-modal aria-label="Fechar">×</button></div>
$('[data-close-modal]',modal).addEventListener('click',()=>{state.selectedStore=null;modal.remove();cleanupStoreMaps();});
modal.addEventListener('click',e=>{if(e.target===modal){state.selectedStore=null;modal.remove();cleanupStoreMaps();}});
modal.querySelectorAll('[data-add-item]').forEach(b=>b.addEventListener('click',()=>addToCart(b.dataset.addItem)));
modal.querySelectorAll('[data-reserve]').forEach(b=>b.addEventListener('click',()=>showReserveForm(b.dataset.reserve)));
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@667:78 — linha 667

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:667)

```javascript
(item,i)=>
```

Executa a etapa `callback@667:78` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `catalogItemIcon`; `esc`; `money`.

**Decisões condicionais examinadas:**
- `item.serviceItem`
- `item.payAfterService`
- `item.stock>0`
- `store.inPersonEnabled!==false`
- `store.deliveryEnabled===false&&store.pickupEnabled===false`
- `item.stock<1`
- `isOpen`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@671:58 — linha 671

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:671)

```javascript
()=>
```

Executa a etapa `callback@671:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `modal.remove`; `cleanupStoreMaps`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@672:34 — linha 672

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:672)

```javascript
e=>
```

Executa a etapa `callback@672:34` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `modal.remove`; `cleanupStoreMaps`.

**Decisões condicionais examinadas:**
- `(e.target===modal)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@673:53 — linha 673

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:673)

```javascript
b=>
```

Executa a etapa `callback@673:53` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `addToCart`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>addToCart(b.dataset.addItem))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@673:83 — linha 673

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:673)

```javascript
()=>
```

Executa a etapa `callback@673:83` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `addToCart`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@674:52 — linha 674

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:674)

```javascript
b=>
```

Executa a etapa `callback@674:52` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `showReserveForm`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>showReserveForm(b.dataset.reserve))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@674:82 — linha 674

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:674)

```javascript
()=>
```

Executa a etapa `callback@674:82` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `showReserveForm`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### addToCart — linha 677

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:677)

```javascript
function addToCart(itemId)
```

Executa a etapa `add To Cart` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.storeItems.find`; `String`; `currentStoreOpen`; `notify`; `state.cart.find`; `state.cart.push`; `render`.

**Decisões condicionais examinadas:**
- `(!item)`
- `(!currentStoreOpen(state.selectedStore))`
- `(state.cart.length&&String(state.cart[0].storeId)!==String(state.selectedStore.id))`
- `(state.selectedStore.deliveryEnabled===false&&state.selectedStore.pickupEnabled===false)`
- `(!state.cart.length)`
- `state.selectedStore.pickupEnabled!==false`
- `(line&&line.quantity>=item.stock)`
- `(line)`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@678:36 — linha 678

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:678)

```javascript
i=>
```

Executa a etapa `callback@678:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@683:30 — linha 683

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:683)

```javascript
i=>
```

Executa a etapa `callback@683:30` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### showReserveForm — linha 689

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:689)

```javascript
function showReserveForm(itemId)
```

Executa a etapa `show Reserve Form` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.storeItems.find`; `String`; `$`; `notify`; `esc`; `money`; `isCustomerOutsideStoreCity`; `state.storeProfessionals.map`; `localDateTimeMinimum`; `backdrop.remove`; `e.preventDefault`; `fields.get`; `storeScheduleAllowsAppointment`; `navigate`; `api`; `JSON.stringify`; `localDateTimeInZoneToIso`; `render`.

**Decisões condicionais examinadas:**
- `(!item||!backdrop)`
- `(state.selectedStore.inPersonEnabled===false)`
- `item.payAfterService&&!isCustomerOutsideStoreCity(state.selectedStore)`
- `isCustomerOutsideStoreCity(state.selectedStore)`
- `item.payAfterService`
- `state.storeProfessionals.length`
- `(!local)`
- `(!storeScheduleAllowsAppointment(state.storeHours,local,item.appointmentDurationMinutes||30,state.selectedStore.timezone||'America/Sao_Paulo'))`
- `(!state.profile?.cpf||!state.profile?.phone)`
- `(created.paymentId)`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
modal.innerHTML=`<div class="modal-head"><div><span class="eyebrow">Reserva presencial</span><h2>Escolha um horário</h2><p>${esc(item.name)} · ${money(item.priceCents)}</p></div><button class="close-button" data-close-modal aria-label="Fechar">×</button></div>
$('[data-close-modal]',modal).addEventListener('click',()=>{state.selectedStore=null;backdrop.remove();});
$('#reserve-form',modal).addEventListener('submit',async e=>{e.preventDefault();const fields=new FormData(e.currentTarget),local=fields.get('startsAt');if(!local)return;if(!storeScheduleAllowsAppointment(state.storeHours,local,item.appointmentDurationMinutes||30,state.selectedStore.timezone||'America/Sao_Paulo')){notify('Atendimento fora do expediente','O serviço inteiro precisa caber em um turno, sem atravessar pausas ou o fechamento.','info');return;}if(!state.profile?.cpf||!state.profile?.phone){notify('Complete seu cadastro','CPF e telefone são necessários para confirmar a reserva.','info');state.accountSection='profile';state.selectedStore=null;backdrop.remove();navigate('account');return;}try{const created=await api('/reservations',{method:'POST',body:JSON.stringify({storeId:state.selectedStore.id,serviceItemId:item.id,startsAt:localDateTimeInZoneToIso(local,state.selectedStore.timezone||'America/Sao_Paulo'),customerNote:String(fields.get('customerNote')||'').trim()||null,professionalId:String(fields.get('professionalId')||'')||null})});state.selectedStore=null;backdrop.remove();if(created.paymentId){state.checkout={type:'RESERVATION',paymentId:created.paymentId,reservation:created.reservation};state.page='checkout';render();}else{state.page='reservations';state.reservations=[created.reservation,...(state.reservations||[])];render();notify('Reserva confirmada','O pagamento será feito diretamente à loja após o atendimento.');}}catch(error){notify('Não foi possível iniciar a reserva',error.message,'error');}});
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@690:36 — linha 690

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:690)

```javascript
i=>
```

Executa a etapa `callback@690:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@695:270 — linha 695

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:695)

```javascript
person=>
```

Executa a etapa `callback@695:270` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@696:58 — linha 696

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:696)

```javascript
()=>
```

Executa a etapa `callback@696:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `backdrop.remove`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@697:54 — linha 697

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:697)

```javascript
async e=>
```

Executa a etapa `callback@697:54` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `e.preventDefault`; `fields.get`; `storeScheduleAllowsAppointment`; `notify`; `backdrop.remove`; `navigate`; `api`; `JSON.stringify`; `localDateTimeInZoneToIso`; `String`; `render`.

**Decisões condicionais examinadas:**
- `(!local)`
- `(!storeScheduleAllowsAppointment(state.storeHours,local,item.appointmentDurationMinutes||30,state.selectedStore.timezone||'America/Sao_Paulo'))`
- `(!state.profile?.cpf||!state.profile?.phone)`
- `(created.paymentId)`

**Saída declarada:**
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async e=>{e.preventDefault();const fields=new FormData(e.currentTarget),local=fields.get('startsAt');if(!local)return;if(!storeScheduleAllowsAppointment(state.storeHours,local,item.appointmentDurationMinutes||30,state.selectedStore.timezone||'America/Sao_Paulo')){notify('Atendimento fora do expediente','O serviço inteiro precisa caber em um turno, sem atravessar pausas ou o fechamento.','info');return;}if(!state.profile?.cpf||!state.profile?.phone){notify('Complete seu cadastro','CPF e telefone são necessários para confirmar a reserva.','info');state.accountSection='profile';state.selectedStore=null;backdrop.remove();navigate('account');return;}try{const created=await api('/reservations',{method:'POST',body:JSON.stringify({storeId:state.selectedStore.id,serviceItemId:item.id,startsAt:localDateTimeInZoneToIso(local,state.selectedStore.timezone||'America/Sao_Paulo'),customerNote:String(fields.get('customerNote')||'').trim()||null,professionalId:String(fields.get('professionalId')||'')||null})});state.selectedStore=null;backdrop.remove();if(created.paymentId){state.checkout={type:'RESERVATION',paymentId:created.paymentId,reservation:created.reservation};state.page='checkout';render();}else{state.page='reservations';state.reservations=[created.reservation,...(state.reservations||[])];render();notify('Reserva confirmada','O pagamento será feito diretamente à loja após o atendimento.');}}catch(error){notify('Não foi possível iniciar a reserva',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderCartDrawer — linha 700

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:700)

```javascript
function renderCartDrawer()
```

Executa a etapa `render Cart Drawer` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.createElement`; `storeOpenAt`; `state.cart.reduce`; `esc`; `state.cart.map`; `money`; `isCustomerOutsideStoreCity`; `state.addresses.map`; `String`; `addressText`; `emptyState`; `document.body.append`; `$`; `backdrop.addEventListener`; `closeCart`; `backdrop.querySelectorAll`; `b.addEventListener`; `state.cart.find`; `Number`; `notify`; `state.cart.filter`; `render`; `navigate`; `radio.addEventListener`; `lookupCep`; `e.currentTarget.closest`; `e.target.value.replace`; `e.target.closest`.

**Decisões condicionais examinadas:**
- `state.cart.length`
- `state.cartMode==='DELIVERY'`
- `state.cart[0]?.pickupEnabled===false`
- `state.cartMode==='PICKUP'`
- `state.cart[0]?.deliveryEnabled===false`
- `!cartStoreOpen`
- `isCustomerOutsideStoreCity({city:state.cart[0]?.storeCity,state:state.cart[0]?.storeState})`
- `state.addresses.length`
- `String(state.selectedAddressId)===String(a.id)`
- `a.defaultAddress`
- `state.addressFormOpen`
- `missingCustomerData`
- `missingCustomerData||!cartStoreOpen`
- `cartStoreOpen`
- `(e.target===backdrop)`
- `(!item)`
- `(change>0&&item.quantity>=item.stock)`
- `(item.quantity<1)`
- `(e.target.value.replace(/\D/g,'').length===8)`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
backdrop.innerHTML=`<aside class="drawer" aria-label="Sacola"><div class="drawer-head"><div><span class="eyebrow">Seu pedido</span><h2>Sua sacola</h2></div><button class="close-button" data-close-cart aria-label="Fechar sacola">×</button></div>
$('[data-close-cart]',backdrop).addEventListener('click',closeCart);
backdrop.addEventListener('click',e=>{if(e.target===backdrop)closeCart();});
backdrop.querySelectorAll('[data-qty]').forEach(b=>b.addEventListener('click',()=>{const item=state.cart.find(i=>String(i.id)===String(b.dataset.id));if(!item)return;const change=Number(b.dataset.qty);if(change>0&&item.quantity>=item.stock){notify('Limite de estoque','A loja não tem mais unidades disponíveis deste item.','info');return;}item.quantity+=change;if(item.quantity<1)state.cart=state.cart.filter(i=>i!==item);render();}));
backdrop.querySelectorAll('[data-cart-mode]').forEach(b=>b.addEventListener('click',()=>{state.cartMode=b.dataset.cartMode;render();}));
$('[data-checkout]',backdrop)?.addEventListener('click',checkout);
$('[data-action="close-empty-cart"]',backdrop)?.addEventListener('click',closeCart);
$('[data-page="account"]',backdrop)?.addEventListener('click',()=>navigate('account'));
backdrop.querySelectorAll('[name="selected-address"]').forEach(radio=>radio.addEventListener('change',()=>{state.selectedAddressId=radio.value;render();}));
$('[data-checkout-note]',backdrop)?.addEventListener('input',event=>{state.checkoutNote=event.target.value;});
$('[data-add-address]',backdrop)?.addEventListener('click',()=>{state.addressFormOpen=true;render();});
$('[data-cancel-address]',backdrop)?.addEventListener('click',()=>{state.addressFormOpen=false;render();});
$('[data-lookup-cep]',backdrop)?.addEventListener('click',e=>lookupCep(e.currentTarget.closest('form')));
$('[data-cep]',backdrop)?.addEventListener('blur',e=>{if(e.target.value.replace(/\D/g,'').length===8)lookupCep(e.target.closest('form'));});
$('[data-address-form]',backdrop)?.addEventListener('submit',saveCheckoutAddress);
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@704:36 — linha 704

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:704)

```javascript
(sum,line)=>
```

Executa a etapa `callback@704:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@708:105 — linha 708

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:708)

```javascript
line=>
```

Executa a etapa `callback@708:105` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `money`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@711:54 — linha 711

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:711)

```javascript
a=>
```

Executa a etapa `callback@711:54` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`; `esc`; `addressText`.

**Decisões condicionais examinadas:**
- `String(state.selectedAddressId)===String(a.id)`
- `a.defaultAddress`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@722:37 — linha 722

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:722)

```javascript
e=>
```

Executa a etapa `callback@722:37` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `closeCart`.

**Decisões condicionais examinadas:**
- `(e.target===backdrop)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@723:51 — linha 723

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:723)

```javascript
b=>
```

Executa a etapa `callback@723:51` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `state.cart.find`; `String`; `Number`; `notify`; `state.cart.filter`; `render`.

**Decisões condicionais examinadas:**
- `(!item)`
- `(change>0&&item.quantity>=item.stock)`
- `(item.quantity<1)`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>{const item=state.cart.find(i=>String(i.id)===String(b.dataset.id));if(!item)return;const change=Number(b.dataset.qty);if(change>0&&item.quantity>=item.stock){notify('Limite de estoque','A loja não tem mais unidades disponíveis deste item.','info');return;}item.quantity+=change;if(item.quantity<1)state.cart=state.cart.filter(i=>i!==item);render();})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@723:81 — linha 723

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:723)

```javascript
()=>
```

Executa a etapa `callback@723:81` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.cart.find`; `String`; `Number`; `notify`; `state.cart.filter`; `render`.

**Decisões condicionais examinadas:**
- `(!item)`
- `(change>0&&item.quantity>=item.stock)`
- `(item.quantity<1)`

**Saída declarada:**
- `return;`
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@723:113 — linha 723

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:723)

```javascript
i=>
```

Executa a etapa `callback@723:113` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@723:414 — linha 723

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:723)

```javascript
i=>
```

Executa a etapa `callback@723:414` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@724:57 — linha 724

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:724)

```javascript
b=>
```

Executa a etapa `callback@724:57` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `b.addEventListener`; `render`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
b=>b.addEventListener('click',()=>{state.cartMode=b.dataset.cartMode;render();})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@724:87 — linha 724

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:724)

```javascript
()=>
```

Executa a etapa `callback@724:87` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `render`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@727:65 — linha 727

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:727)

```javascript
()=>
```

Executa a etapa `callback@727:65` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `navigate`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@728:66 — linha 728

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:728)

```javascript
radio=>
```

Executa a etapa `callback@728:66` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `radio.addEventListener`; `render`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
radio=>radio.addEventListener('change',()=>{state.selectedAddressId=radio.value;render();})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@728:105 — linha 728

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:728)

```javascript
()=>
```

Executa a etapa `callback@728:105` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `render`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@729:64 — linha 729

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:729)

```javascript
event=>
```

Executa a etapa `callback@729:64` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@730:62 — linha 730

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:730)

```javascript
()=>
```

Executa a etapa `callback@730:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `render`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@731:65 — linha 731

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:731)

```javascript
()=>
```

Executa a etapa `callback@731:65` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `render`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@732:61 — linha 732

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:732)

```javascript
e=>
```

Executa a etapa `callback@732:61` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `lookupCep`; `e.currentTarget.closest`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@733:53 — linha 733

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:733)

```javascript
e=>
```

Executa a etapa `callback@733:53` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `e.target.value.replace`; `lookupCep`; `e.target.closest`.

**Decisões condicionais examinadas:**
- `(e.target.value.replace(/\D/g,'').length===8)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### closeCart — linha 737

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:737)

```javascript
function closeCart()
```

Executa a etapa `close Cart` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `render`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### checkout — linha 739

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:739)

```javascript
async function checkout()
```

Executa a etapa `checkout` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `storeOpenAt`; `notify`; `render`; `navigate`; `state.addresses.find`; `String`; `addressText`; `address.trim`; `api`; `JSON.stringify`; `state.checkoutNote.trim`; `state.cart.map`.

**Decisões condicionais examinadas:**
- `(!state.cart.length)`
- `(!storeOpenAt(state.cart[0].storeOperatingHours,state.cart[0].storeTimezone))`
- `(!state.account?.cpf||!state.account?.phone)`
- `selected`
- `(state.cartMode==='DELIVERY'&&!address.trim())`
- `state.cartMode==='DELIVERY'`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const response=await api('/orders',{method:'POST',body:JSON.stringify({storeId:state.cart[0].storeId,fulfillmentType:state.cartMode,deliveryAddress:state.cartMode==='DELIVERY'?address:null,customerNote:state.checkoutNote.trim()||null,items:state.cart.map(line=>({itemId:line.id,quantity:line.quantity}))})});
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@743:39 — linha 743

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:743)

```javascript
a=>
```

Executa a etapa `callback@743:39` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@747:260 — linha 747

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:747)

```javascript
line=>
```

Executa a etapa `callback@747:260` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### saveCheckoutAddress — linha 752

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:752)

```javascript
async function saveCheckoutAddress(event)
```

Executa a etapa `save Checkout Address` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `api`; `JSON.stringify`; `String`; `fields.get`; `persistAccountHint`; `notify`; `render`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const address=await api('/account/addresses',{method:'POST',body:JSON.stringify({label:String(fields.get('label')).trim(),addressLine:String(fields.get('addressLine')).trim(),addressComplement:String(fields.get('addressComplement')||'').trim()||null,neighborhood:String(fields.get('neighborhood')).trim(),city:String(fields.get('city')).trim(),state:String(fields.get('state')).trim().toUpperCase(),postalCode:String(fields.get('postalCode')).trim(),defaultAddress:fields.get('defaultAddress')==='on'})});
state.addresses=await api('/account/addresses'); state.selectedAddressId=address.id; state.addressFormOpen=false;
state.profile=await api('/account/me');state.account=state.profile;persistAccountHint(state.account);
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### lookupCep — linha 762

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:762)

```javascript
async function lookupCep(form)
```

Executa a etapa `lookup Cep` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form?.querySelector`; `input.value.replace`; `notify`; `form.querySelector`; `postalCodeResponse`; `response.json`; `fill`.

**Rejeições locais:**
- `throw new Error('O serviço de CEP está indisponível. Você ainda pode preencher o endereço manualmente.');`
- `throw new Error('Não encontramos esse CEP. Confira os números ou preencha o endereço manualmente.');`

**Decisões condicionais examinadas:**
- `(!input)`
- `(cep.length!==8)`
- `(form.dataset.cepLookup===cep)`
- `(button)`
- `(!response.ok)`
- `(data.erro)`
- `(field&&value&&!field.value)`

**Saída declarada:**
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const button=form.querySelector('[data-lookup-cep]'); if(button){button.disabled=true;button.textContent='Buscando…';}
finally {delete form.dataset.cepLookup;if(button){button.disabled=false;button.textContent='Buscar';}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### fill — linha 772

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:772)

```javascript
(name,value)=>
```

Executa a etapa `fill` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form.querySelector`.

**Decisões condicionais examinadas:**
- `(field&&value&&!field.value)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderCheckout — linha 779

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:779)

```javascript
function renderCheckout()
```

Executa a etapa `render Checkout` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `money`; `emptyState`; `shortDate`; `pill`; `order.lines.map`.

**Decisões condicionais examinadas:**
- `(globalThis.FOODDELIVERY_DEMO&&state.checkout)`
- `state.paymentStarting`
- `(!state.checkout)`
- `(state.checkout.type==='RESERVATION')`
- `order.fulfillmentType==='DELIVERY'`
- `order.deliveryFeeCents`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Homologação local</span><h1>Simular pagamento</h1><p>Teste aprovação e recusa sem cartão, cobrança ou provedor externo.</p></div></div><section class="panel"><h2>${esc(operation.store.name)}</h2><div class="cart-total grand"><span>Valor fictício</span><span>${money(operation.amountCents??operation.totalCents)}</span></div><p class="store-desc">A aprovação confirma esta operação e avisa a loja de demonstração. A recusa permite tentar novamente.</p><button class="btn block" data-checkout-pay ${state.paymentStarting?'disabled':''}>Simular pagamento aprovado</button></section>\`;`
- `return \`<div class="page-head"><div><span class="eyebrow">Pagamento</span><h1>Finalizar pedido</h1><p>Não encontramos uma compra para continuar.</p></div></div>${emptyState('🛍️','Sua sacola está vazia','Volte às lojas para escolher seus itens.','Ver lojas','go-home')}\`;`
- `return \`<div class="page-head"><div><span class="eyebrow">Pagamento da reserva</span><h1>Confirme seu horário</h1><p>O horário fica reservado após a aprovação do pagamento.</p></div></div><div class="split-layout"><section class="panel"><span class="eyebrow">Atendimento</span><h3 style="margin-top:8px">${esc(r.serviceName)}</h3><p class="store-desc">${esc(r.store.name)} · ${shortDate(r.startsAt)}</p><div class="alert-strip info"><span>✓</span><span>Pagamento realizado no ambiente seguro do Mercado Pago. O estabelecimento só confirma sua reserva após a aprovação.</span></div></section><aside class="panel"><span class="eyebrow">Resumo</span><div class="cart-total grand"><span>Total</span><span>${money(r.amountCents)}</span></div><button class="btn block" data-checkout-pay ${state.paymentStarting?'disabled':''}>${state.paymentStarting?'Abrindo pagamento…':'Pagar e confirmar horário'}</button><button class="btn outline block" style="margin-top:8px" data-page="reservations">Voltar às reservas</button></aside></div>\`;`
- `return \`<div class="page-head"><div><span class="eyebrow">Quase lá</span><h1>Revise seu pedido</h1><p>Confira os detalhes antes de confirmar o pagamento.</p></div></div> <div class="split-layout"><section><div class="panel"><h3>Resumo da compra</h3><div class="store-title-row"><strong>${esc(order.store.name)}</strong>${pill(order.status)}</div><p class="store-desc">${order.fulfillmentType==='DELIVERY'?'Entrega em':'Retirada em'} ${esc(order.fulfillmentType==='DELIVERY'?order.deliveryAddress:order.store.address)}</p><div class="order-items">${order.lines.map(line=>\`<div>${line.quantity} × ${esc(line.name)} <span style="float:right">${money(line.unitPriceCents*line.quantity)}</span></div>\`).join('')}${order.deliveryFeeCents?\`<div>Taxa de entrega <span style="float:right">${money(order.deliveryFeeCents)}</span></div>\`:''}</div><div class="cart-total"><span>Itens</span><span>${money(order.subtotalCents)}</span></div><div class="cart-total"><span>Entrega</span><span>${order.fulfillmentType==='DELIVERY'?money(order.deliveryFeeCents):'Grátis'}</span></div><div class="cart-total grand"><span>Total</span><span>${money(order.totalCents)}</span></div></div> <div class="alert-strip info"><span>✓</span><span>Você continuará para o checkout seguro do Mercado Pago. O pedido só será confirmado depois que o provedor aprovar o pagamento.</span></div></section> <aside class="panel"><span class="eyebrow">Checkout seguro</span><h3 style="margin-top:7px">Finalize o pagamento</h3><p class="store-desc">Total a pagar · <strong>${money(order.totalCents)}</strong></p><div class="alert-strip info"><span>✓</span><span>Os dados do cartão são processados pelo Mercado Pago e não são armazenados pelo FoodDelivery.</span></div><button class="btn block" data-checkout-pay ${state.paymentStarting?'disabled':''}>${state.paymentStarting?'Abrindo pagamento…':'Pagar com Mercado Pago'}</button><button class="btn outline block" style="margin-top:8px" data-page="orders">Deixar para depois</button></aside></div>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@788:399 — linha 788

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:788)

```javascript
line=>
```

Executa a etapa `callback@788:399` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `money`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### approvePayment — linha 793

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:793)

```javascript
async function approvePayment()
```

Executa a etapa `approve Payment` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `simulatePayment`; `render`; `api`; `window.location.assign`; `notify`.

**Decisões condicionais examinadas:**
- `(!state.checkout)`
- `(globalThis.FOODDELIVERY_DEMO)`

**Saída declarada:**
- `return;`
- `return simulatePayment('approve');`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try{const checkout=await api(`/payments/${state.checkout.paymentId}/checkout`,{method:'POST'});window.location.assign(checkout.checkoutUrl);}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### simulatePayment — linha 801

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:801)

```javascript
async function simulatePayment(outcome)
```

Executa a etapa `simulate Payment` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `render`; `api`; `encodeURIComponent`; `navigate`; `notify`.

**Decisões condicionais examinadas:**
- `(!state.checkout||state.paymentStarting)`
- `(outcome==='approve')`
- `state.checkout.type==='RESERVATION'`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try{await api(`/demo/payments/${encodeURIComponent(state.checkout.paymentId)}/${outcome}`,{method:'POST'});
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderOrders — linha 811

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:811)

```javascript
function renderOrders()
```

Executa a etapa `render Orders` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.map`; `esc`; `String`; `shortDate`; `pill`; `trackingMessage`; `Number`; `order.lines.map`; `money`; `encodeURIComponent`; `['AWAITING_ACCEPTANCE','ACCEPTED','PREPARING','READY'].includes`; `chatPanel`; `refundNotice`; `emptyState`.

**Decisões condicionais examinadas:**
- `orders.length`
- `order.tracking?.deliveryId`
- `order.fulfillmentType==='DELIVERY'`
- `order.tracking?.latitude!=null&&order.tracking?.longitude!=null`
- `state.trackingMapOrderId===String(order.id)`
- `order.customerNote`
- `order.cancellationRequestStatus==='REQUESTED'`
- `order.fulfillmentType==='DELIVERY'&&order.deliveryAddress`
- `order.tracking?.deliveryId&&order.tracking.status!=='OFFERED'`
- `order.tracking.status==='ASSIGNED'`
- `order.status!=='AWAITING_PAYMENT'`
- `order.status==='AWAITING_PAYMENT'&&order.paymentId`
- `['AWAITING_ACCEPTANCE','ACCEPTED','PREPARING','READY'].includes(order.status)&&order.paymentStatus==='PAID'&&order.cancellationRequestStatus!=='REQUESTED'`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Acompanhe de perto</span><h1>Meus pedidos</h1><p>Veja o andamento e os detalhes das suas compras.</p></div><button class="btn light" data-page="home">+ Fazer um pedido</button></div> ${orders.length?orders.map(order=>\`<article class="order-card" data-order-card="${esc(order.id)}" ${order.tracking?.deliveryId?\`data-delivery-card="${esc(order.tracking.deliveryId)}"\`:''}><div class="order-card-top"><div><h3>${esc(order.store.name)} · ${order.fulfillmentType==='DELIVERY'?'Entrega':'Retirada'}</h3><p>Pedido ${esc(String(order.id).slice(0,8).toUpperCase())} · ${shortDate(order.createdAt)}</p></div>${pill(order.tracking?.status||order.status)}</div> ${order.fulfillmentType==='DELIVERY'?\`<div class="tracking-panel"><span class="tracking-mark">⌖</span><div><strong>${esc(trackingMessage(order))}</strong><small>As atualizações aparecem automaticamente nesta tela.</small></div></div>${order.tracking?.latitude!=null&&order.tracking?.longitude!=null?\`<div class="tracking-map-actions"><button class="btn outline small" data-tracking-map="${esc(order.id)}">${state.trackingMapOrderId===String(order.id)?'Fechar mapa':'Ver mapa do entregador'}</button><small>O mapa externo recebe a posição compartilhada para mostrar o trajeto.</small></div>${state.trackingMapOrderId===String(order.id)?\`<iframe class="tracking-map" title="Localização aproximada do entregador" loading="lazy" referrerpolicy="no-referrer" src="https://www.openstreetmap.org/export/embed.html?bbox=${Number(order.tracking.longitude)-0.006}%2C${Number(order.tracking.latitude)-0.006}%2C${Number(order.tracking.longitude)+0.006}%2C${Number(order.tracking.latitude)+0.006}&layer=mapnik&marker=${Number(order.tracking.latitude)}%2C${Number(order.tracking.longitude)}"></iframe>\`:''}\`:''}\`:''} <div class="order-items">${order.lines.map(line=>\`${line.quantity} × ${esc(line.name)}\`).join('<br/>')}</div>${order.customerNote?\`<div class="customer-note"><strong>Observação enviada à loja</strong><p>${esc(order.customerNote)}</p></div>\`:''}<div class="order-bottom"><span>${money(order.totalCents)}</span><span>${order.fulfillmentType==='DELIVERY'?'⌖ '+esc(order.deliveryAddress||'Endereço informado'):'Retirada na loja'}</span></div> ${order.cancellationRequestStatus==='REQUESTED'?'<div class="alert-strip warn"><span>◷</span><span>Seu pedido está aguardando a resposta da loja sobre o cancelamento.</span></div>':''}<div class="action-row">${order.fulfillmentType==='DELIVERY'&&order.deliveryAddress?\`<a class="btn outline small" href="https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(order.deliveryAddress)}" target="_blank" rel="noopener noreferrer">Ver endereço no mapa</a>\`:''}${order.tracking?.deliveryId&&order.tracking.status!=='OFFERED'?\`<button class="btn outline small" data-open-chat-type="deliveries" data-open-chat-id="${esc(order.tracking.deliveryId)}">${order.tracking.status==='ASSIGNED'?'Conversar com entregador':'Histórico do chat de entrega'}</button>\`:''}${order.tracking?.latitude!=null&&order.tracking?.longitude!=null?\`<a class="btn small" href="https://maps.google.com/?q=${encodeURIComponent(\`${order.tracking.latitude},${order.tracking.longitude}\`)}" target="_blank" rel="noopener noreferrer">Ver localização do entregador</a><small class="location-updated">Atualizado ${shortDate(order.tracking.locationUpdatedAt)}</small>\`:''}${order.status!=='AWAITING_PAYMENT'?\`<button class="btn outline small" data-open-chat-type="orders" data-open-chat-id="${esc(order.id)}">Mensagens com a loja</button>\`:''}${order.status==='AWAITING_PAYMENT'&&order.paymentId?'<button class="btn small" data-resume-order="'+esc(order.id)+'">Continuar pagamento</button><button class="btn outline small" data-cancel-order="'+esc(order.id)+'">Cancelar pedido</button>':''}${['AWAITING_ACCEPTANCE','ACCEPTED','PREPARING','READY'].includes(order.status)&&order.paymentStatus==='PAID'&&order.cancellationRequestStatus!=='REQUESTED'?\`<button class="btn outline small" data-request-order-cancellation="${esc(order.id)}">Solicitar cancelamento</button>\`:''}</div>${chatPanel('orders',order.id)}${order.tracking?.deliveryId?chatPanel('deliveries',order.tracking.deliveryId):''}${refundNotice(order.paymentStatus)}</article>\`).join(''):emptyState('🧾','Seus pedidos vão aparecer aqui','Quando fizer seu primeiro pedido, você acompanha cada etapa nesta tela.','Encontrar uma loja','go-home')}\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@814:32 — linha 814

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:814)

```javascript
order=>
```

Executa a etapa `callback@814:32` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `String`; `shortDate`; `pill`; `trackingMessage`; `Number`; `order.lines.map`; `money`; `encodeURIComponent`; `['AWAITING_ACCEPTANCE','ACCEPTED','PREPARING','READY'].includes`; `chatPanel`; `refundNotice`.

**Decisões condicionais examinadas:**
- `order.tracking?.deliveryId`
- `order.fulfillmentType==='DELIVERY'`
- `order.tracking?.latitude!=null&&order.tracking?.longitude!=null`
- `state.trackingMapOrderId===String(order.id)`
- `order.customerNote`
- `order.cancellationRequestStatus==='REQUESTED'`
- `order.fulfillmentType==='DELIVERY'&&order.deliveryAddress`
- `order.tracking?.deliveryId&&order.tracking.status!=='OFFERED'`
- `order.tracking.status==='ASSIGNED'`
- `order.status!=='AWAITING_PAYMENT'`
- `order.status==='AWAITING_PAYMENT'&&order.paymentId`
- `['AWAITING_ACCEPTANCE','ACCEPTED','PREPARING','READY'].includes(order.status)&&order.paymentStatus==='PAID'&&order.cancellationRequestStatus!=='REQUESTED'`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@816:50 — linha 816

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:816)

```javascript
line=>
```

Executa a etapa `callback@816:50` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### trackingMessage — linha 820

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:820)

```javascript
function trackingMessage(order)
```

Executa a etapa `tracking Message` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `formatStatus`.

**Decisões condicionais examinadas:**
- `(order.tracking?.status)`

**Saída declarada:**
- `return delivery[order.tracking.status]||formatStatus(order.tracking.status);`
- `return status[order.status]||formatStatus(order.status);`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderReservations — linha 827

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:827)

```javascript
function renderReservations()
```

Executa a etapa `render Reservations` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `list.map`; `esc`; `dateTimeInZone`; `pill`; `money`; `String`; `chatPanel`; `refundNotice`; `emptyState`.

**Decisões condicionais examinadas:**
- `list.length`
- `r.professionalName`
- `r.operatingHoursNotice`
- `r.status==='CANCELLED'`
- `r.cancellationRequestStatus==='REQUESTED'`
- `r.customerNote`
- `r.payAfterService`
- `r.status!=='PENDING_PAYMENT'`
- `r.status==='PENDING_PAYMENT'&&r.paymentId`
- `r.status==='CONFIRMED'&&r.cancellationRequestStatus!=='REQUESTED'`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Um tempo reservado para você</span><h1>Minhas reservas</h1><p>Seus próximos momentos e atendimentos.</p></div><button class="btn light" data-page="home">Descobrir serviços</button></div> ${list.length?list.map(r=>\`<article class="order-card" data-reservation-card="${esc(r.id)}"><div class="order-card-top"><div><h3>${esc(r.serviceName)}</h3><p>${esc(r.store.name)} · ${dateTimeInZone(r.startsAt,r.store.timezone||'America/Sao_Paulo')}${r.professionalName?\` · Profissional: ${esc(r.professionalName)}\`:''}</p></div>${pill(r.status)}</div>${r.operatingHoursNotice?\`<div class="alert-strip ${r.status==='CANCELLED'?'warn':'info'}"><span>◷</span><span>${esc(r.operatingHoursNotice)}</span></div>\`:''}${r.cancellationRequestStatus==='REQUESTED'?'<div class="alert-strip warn"><span>◷</span><span>A loja ainda não respondeu ao seu pedido de cancelamento.</span></div>':''}${r.customerNote?\`<div class="customer-note"><strong>Comentário enviado à loja</strong><p>${esc(r.customerNote)}</p></div>\`:''}${r.payAfterService?'<div class="alert-strip info"><span>R$</span><span>Pagamento combinado diretamente com a loja após o atendimento.</span></div>':''}<div class="order-bottom"><span>${money(r.amountCents)}</span><span>Reserva ${esc(String(r.id).slice(0,8).toUpperCase())}</span></div><div class="action-row">${r.status!=='PENDING_PAYMENT'?\`<button class="btn outline small" data-open-chat-type="reservations" data-open-chat-id="${esc(r.id)}">Mensagens com a loja</button>\`:''}${r.status==='PENDING_PAYMENT'&&r.paymentId?'<button class="btn small" data-resume-reservation="'+esc(r.id)+'">Continuar pagamento</button><button class="btn outline small" data-cancel-reservation="'+esc(r.id)+'">Cancelar reserva</button>':''}${r.status==='CONFIRMED'&&r.cancellationRequestStatus!=='REQUESTED'?\`<button class="btn outline small" data-request-reservation-cancellation="${esc(r.id)}">Solicitar cancelamento</button>\`:''}</div>${chatPanel('reservations',r.id)}${refundNotice(r.paymentStatus)}</article>\`).join(''):emptyState('🗓️','Ainda não há reservas','Escolha um serviço perto de você e marque um horário que combine com a sua agenda.','Explorar serviços','go-home')}\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@830:28 — linha 830

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:830)

```javascript
r=>
```

Executa a etapa `callback@830:28` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `dateTimeInZone`; `pill`; `money`; `String`; `chatPanel`; `refundNotice`.

**Decisões condicionais examinadas:**
- `r.professionalName`
- `r.operatingHoursNotice`
- `r.status==='CANCELLED'`
- `r.cancellationRequestStatus==='REQUESTED'`
- `r.customerNote`
- `r.payAfterService`
- `r.status!=='PENDING_PAYMENT'`
- `r.status==='PENDING_PAYMENT'&&r.paymentId`
- `r.status==='CONFIRMED'&&r.cancellationRequestStatus!=='REQUESTED'`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### chatKey — linha 833

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:833)

```javascript
function chatKey(type,id)
```

Executa a etapa `chat Key` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return \`${type}:${id}\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### chatMessagesHtml — linha 834

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:834)

```javascript
function chatMessagesHtml(thread)
```

Executa a etapa `chat Messages Html` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `thread.messages.map`; `String`; `esc`; `shortDate`.

**Decisões condicionais examinadas:**
- `thread.messages.length`
- `String(m.senderId)===String(state.account?.id)`

**Saída declarada:**
- `return thread.messages.length?thread.messages.map(m=>\`<article class="chat-message ${String(m.senderId)===String(state.account?.id)?'mine':''}"><strong>${esc(m.senderName)}</strong><p>${esc(m.body)}</p><time>${shortDate(m.createdAt)}</time></article>\`).join(''):'<p class="chat-empty">Ainda não há mensagens. Escreva se precisar combinar algum detalhe.</p>';`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
function chatMessagesHtml(thread){return thread.messages.length?thread.messages.map(m=>`<article class="chat-message ${String(m.senderId)===String(state.account?.id)?'mine':''}"><strong>${esc(m.senderName)}</strong><p>${esc(m.body)}</p><time>${shortDate(m.createdAt)}</time></article>`).join(''):'<p class="chat-empty">Ainda não há mensagens. Escreva se precisar combinar algum detalhe.</p>';}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@834:85 — linha 834

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:834)

```javascript
m=>
```

Executa a etapa `callback@834:85` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`; `esc`; `shortDate`.

**Decisões condicionais examinadas:**
- `String(m.senderId)===String(state.account?.id)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
m=>`<article class="chat-message ${String(m.senderId)===String(state.account?.id)?'mine':''}"><strong>${esc(m.senderName)}</strong><p>${esc(m.body)}</p><time>${shortDate(m.createdAt)}</time></article>`
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### stopChat — linha 835

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:835)

```javascript
function stopChat()
```

Executa a etapa `stop Chat` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `clearInterval`.

**Decisões condicionais examinadas:**
- `(state.chatPollHandle)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### resetChatState — linha 836

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:836)

```javascript
function resetChatState()
```

Executa a etapa `reset Chat State` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stopChat`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### applyChatUpdate — linha 837

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:837)

```javascript
function applyChatUpdate(key,next)
```

Executa a etapa `apply Chat Update` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.querySelector`; `CSS.escape`; `panel?.querySelector`; `renderPage`; `previous.messages.at`; `next.messages.at`; `chatMessagesHtml`.

**Decisões condicionais examinadas:**
- `(!panel||!box||!previous||previous.open!==next.open)`
- `(previous.messages.length===next.messages.length&&previous.messages.at(-1)?.id===next.messages.at(-1)?.id)`
- `(atBottom)`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
box.innerHTML=chatMessagesHtml(next);
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### chatPanel — linha 846

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:846)

```javascript
function chatPanel(type,id)
```

Executa a etapa `chat Panel` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `chatKey`; `esc`; `chatMessagesHtml`.

**Decisões condicionais examinadas:**
- `(state.openChat!==key)`
- `(!thread)`
- `type==='deliveries'`
- `type==='reservations'`
- `thread.open`

**Saída declarada:**
- `return '';`
- `return '<section class="chat-panel"><p>Carregando conversa…</p></section>';`
- `return \`<section class="chat-panel" data-chat-panel="${esc(key)}"><div class="chat-heading"><div><strong>${type==='deliveries'?'Chat com o entregador':type==='reservations'?'Conversa sobre a reserva':'Conversa sobre o pedido'}</strong><small>${thread.open?'Mensagens visíveis para as pessoas envolvidas nesta operação.':esc(thread.closedReason||'Conversa encerrada; o histórico continua disponível.')}</small></div><button class="btn outline small" data-close-chat="${esc(key)}">Fechar</button></div><div class="chat-messages">${chatMessagesHtml(thread)}</div>${thread.open?\`<form class="chat-compose" data-chat-form data-chat-type="${type}" data-chat-id="${esc(id)}"><label class="sr-only" for="message-${esc(key)}">Escreva uma mensagem</label><textarea id="message-${esc(key)}" name="body" maxlength="1000" rows="2" required placeholder="Escreva sua mensagem…" data-chat-draft="${esc(key)}">${esc(state.chatDrafts[key]||'')}</textarea><button class="btn small" type="submit">Enviar</button></form>\`:'<div class="chat-closed">Este chat foi encerrado conforme o andamento da operação. Você ainda pode consultar as mensagens anteriores.</div>'}</section>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### openChat — linha 848

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:848)

```javascript
async function openChat(type,id)
```

Executa a etapa `open Chat` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `chatKey`; `stopChat`; `renderPage`; `api`; `encodeURIComponent`; `clearInterval`; `setInterval`; `refreshOpenChat`; `notify`.

**Decisões condicionais examinadas:**
- `(state.chatPollHandle)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function openChat(type,id){const key=chatKey(type,id);stopChat();state.openChat=key;state.chatThreads[key]=null;renderPage();try{state.chatThreads[key]=await api(`/${type}/${encodeURIComponent(id)}/messages`);renderPage();if(state.chatPollHandle)clearInterval(state.chatPollHandle);state.chatPollHandle=setInterval(()=>refreshOpenChat(key),5000);}catch(error){state.openChat=null;notify('Não foi possível abrir a conversa',error.message,'error');renderPage();}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@848:322 — linha 848

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:848)

```javascript
()=>
```

Executa a etapa `callback@848:322` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `refreshOpenChat`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refreshOpenChat — linha 849

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:849)

```javascript
async function refreshOpenChat(key)
```

Executa a etapa `refresh Open Chat` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `key.split`; `api`; `encodeURIComponent`; `applyChatUpdate`.

**Decisões condicionais examinadas:**
- `(state.openChat!==key||document.visibilityState!=='visible')`
- `(state.openChat===key)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function refreshOpenChat(key){if(state.openChat!==key||document.visibilityState!=='visible')return;const [type,id]=key.split(':');try{const next=await api(`/${type}/${encodeURIComponent(id)}/messages`);if(state.openChat===key)applyChatUpdate(key,next);}catch{/* a próxima atualização tenta novamente sem interromper o atendimento */}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### closeChat — linha 850

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:850)

```javascript
function closeChat(key)
```

Executa a etapa `close Chat` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stopChat`; `renderPage`.

**Decisões condicionais examinadas:**
- `(state.openChat!==key)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sendChatMessage — linha 851

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:851)

```javascript
async function sendChatMessage(event)
```

Executa a etapa `send Chat Message` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `String`; `new FormData`; `form.querySelector`; `chatKey`; `api`; `encodeURIComponent`; `JSON.stringify`; `renderPage`; `notify`.

**Decisões condicionais examinadas:**
- `(!body)`
- `(button)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function sendChatMessage(event){event.preventDefault();const form=event.currentTarget;const type=form.dataset.chatType,id=form.dataset.chatId,body=String(new FormData(form).get('body')||'').trim();if(!body)return;const button=form.querySelector('button[type="submit"]');if(button)button.disabled=true;try{state.chatThreads[chatKey(type,id)]=await api(`/${type}/${encodeURIComponent(id)}/messages`,{method:'POST',body:JSON.stringify({body})});delete state.chatDrafts[chatKey(type,id)];renderPage();}catch(error){if(button)button.disabled=false;notify('Mensagem não enviada',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderAdminDefinitions — linha 853

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:853)

```javascript
function renderAdminDefinitions()
```

Executa a etapa `render Admin Definitions` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SE','SP','TO'].map`; `state.adminServiceCities.map`; `esc`; `state.adminStoreCategories.some`; `state.adminStoreCategories.filter`; `categoryOptions.slice`; `[['🏪','Loja'],['🐾','Pet'],['🌷','Flores'],['🧰','Manutenção'],['📚','Livros'],['👗','Moda'],['🚗','Automóveis'],['🧁','Doces'],['🎁','Presentes'],['🧼','Limpeza'],['🩺','Saúde'],['🏋️','Esportes']].map`; `builtInCategories.map`; `customCategories.map`; `state.adminIntegrations.map`; `icon`; `['VIACEP','MAPAS','STORE_IMAGES'].includes`; `emptyState`; `state.adminAuditEvents.map`; `String`; `shortDate`; `formatCpfMask`; `sections.map`.

**Decisões condicionais examinadas:**
- `state.adminServiceCities.length`
- `city.state===uf`
- `city.enabled`
- `state.adminStoreCategories.some(category=>category.builtIn)`
- `customCategories.length`
- `state.adminIntegrations.length`
- `item.id==='MERCADOPAGO'`
- `item.id==='WHATSAPP'`
- `item.id==='VIACEP'`
- `item.id==='STORE_IMAGES'`
- `item.configured`
- `['VIACEP','MAPAS','STORE_IMAGES'].includes(item.id)`
- `state.adminAuditEvents.length`
- `event.targetType==='STORE'`
- `event.targetType==='ACCOUNT'`
- `event.targetType==='SERVICE_CITY'`
- `event.targetId`
- `profile.cpf`
- `selected===id`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Controle da plataforma</span><h1>Definições</h1><p>Gerencie cobertura, categorias, integrações e dados administrativos em espaços separados.</p></div></div><nav class="section-tabs account-tabs" aria-label="Definições da plataforma">${sections.map(([id,label])=>\`<button class="section-tab ${selected===id?'active':''}" data-admin-definition-section="${id}" aria-current="${selected===id?'page':'false'}">${label}</button>\`).join('')}</nav><div class="account-section-view definitions-view">${content}</div>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@857:733 — linha 857

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:857)

```javascript
uf=>
```

Executa a etapa `callback@857:733` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@857:1517 — linha 857

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:857)

```javascript
city=>
```

Executa a etapa `callback@857:1517` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SE','SP','TO'].map`.

**Decisões condicionais examinadas:**
- `city.state===uf`
- `city.enabled`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@857:2048 — linha 857

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:857)

```javascript
uf=>
```

Executa a etapa `callback@857:2048` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `city.state===uf`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@858:59 — linha 858

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:858)

```javascript
category=>
```

Executa a etapa `callback@858:59` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@858:121 — linha 858

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:858)

```javascript
category=>
```

Executa a etapa `callback@858:121` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@858:178 — linha 858

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:858)

```javascript
name=>
```

Executa a etapa `callback@858:178` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@859:60 — linha 859

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:859)

```javascript
category=>
```

Executa a etapa `callback@859:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@860:982 — linha 860

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:860)

```javascript
([emoji,label])=>
```

Executa a etapa `callback@860:982` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@860:1685 — linha 860

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:860)

```javascript
category=>
```

Executa a etapa `callback@860:1685` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@860:2258 — linha 860

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:860)

```javascript
category=>
```

Executa a etapa `callback@860:2258` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@861:495 — linha 861

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:861)

```javascript
item=>
```

Executa a etapa `callback@861:495` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `icon`; `esc`; `['VIACEP','MAPAS','STORE_IMAGES'].includes`.

**Decisões condicionais examinadas:**
- `item.id==='MERCADOPAGO'`
- `item.id==='WHATSAPP'`
- `item.id==='VIACEP'`
- `item.id==='STORE_IMAGES'`
- `item.configured`
- `['VIACEP','MAPAS','STORE_IMAGES'].includes(item.id)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@863:460 — linha 863

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:863)

```javascript
event=>
```

Executa a etapa `callback@863:460` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `icon`; `esc`; `String`; `shortDate`.

**Decisões condicionais examinadas:**
- `event.targetType==='STORE'`
- `event.targetType==='ACCOUNT'`
- `event.targetType==='SERVICE_CITY'`
- `event.targetId`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@866:316 — linha 866

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:866)

```javascript
([id,label])=>
```

Executa a etapa `callback@866:316` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `selected===id`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### loadAdminAuditEvents — linha 869

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:869)

```javascript
async function loadAdminAuditEvents()
```

Executa a etapa `load Admin Audit Events` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `notify`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function loadAdminAuditEvents(){try{const result=await api('/admin/audit-events?page=0&size=50');state.adminAuditEvents=result.content||[];}catch(error){notify('Não foi possível carregar a auditoria',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### openAdminStoreEditor — linha 871

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:871)

```javascript
function openAdminStoreEditor(id)
```

Executa a etapa `open Admin Store Editor` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.adminStores.find`; `String`; `editStoreDialog`; `categoryOptions.filter`; `state.adminStores.map`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `(!store)`
- `item.id===updated.id`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@871:70 — linha 871

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:871)

```javascript
item=>
```

Executa a etapa `callback@871:70` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@871:168 — linha 871

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:871)

```javascript
name=>
```

Executa a etapa `callback@871:168` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@871:199 — linha 871

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:871)

```javascript
async updated=>
```

Executa a etapa `callback@871:199` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.adminStores.map`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `item.id===updated.id`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@871:255 — linha 871

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:871)

```javascript
item=>
```

Executa a etapa `callback@871:255` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `item.id===updated.id`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@871:421 — linha 871

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:871)

```javascript
summary=>
```

Executa a etapa `callback@871:421` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notify`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@871:591 — linha 871

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:871)

```javascript
error=>
```

Executa a etapa `callback@871:591` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notify`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderAccount — linha 873

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:873)

```javascript
function renderAccount()
```

Executa a etapa `render Account` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `role`; `renderAdminDefinitions`; `sections.filter`; `roles.includes`; `esc`; `formatCpfMask`; `['AC','AL','AP','AM','BA','CE','DF','ES','GO','MA','MT','MS','MG','PA','PB','PR','PE','PI','RJ','RN','RS','RO','RR','SC','SP','SE','TO'].map`; `state.addresses.map`; `addressText`; ``; `roles.map`; `['MERCHANT','COURIER'].some`; `registered.includes`; `pendingRoles.includes`; `['MERCHANT','COURIER'].filter`; `state.platformRequests.map`; `pill`; `tabs.map`.

**Decisões condicionais examinadas:**
- `(role()==='ADMIN')`
- `profile.role==='ADMIN'`
- `profile.cpf`
- `profile.state===uf`
- `state.addresses.length`
- `a.defaultAddress`
- `state.addressFormOpen`
- `profile.phoneVerified`
- `profile.phone`
- `state.phoneVerificationSent`
- `roles.length>1`
- `role()===r`
- `['MERCHANT','COURIER'].some(r=>!registered.includes(r)&&!pendingRoles.includes(r))`
- `state.platformRequests?.length`
- `r.requestType==='ROLE'`
- `r.requestType==='CITY'`
- `r.requestType==='STORE'`
- `r.status==='PENDING'`
- `r.status==='APPROVED'`
- `state.mustChangePassword`
- `selected===id`

**Saída declarada:**
- `return renderAdminDefinitions();`
- `return \`${state.mustChangePassword?'<div class="alert-strip warn"><span>!</span><span>Altere a senha temporária antes de continuar.</span></div>':''}<div class="page-head"><div><span class="eyebrow">Seu perfil</span><h1>Minha conta</h1><p>Encontre cada configuração no seu próprio espaço.</p></div></div><nav class="section-tabs account-tabs" aria-label="Seções da conta">${tabs.map(([id,label])=>\`<button class="section-tab ${selected===id?'active':''}" data-account-section="${id}" aria-current="${selected===id?'page':'false'}">${label}</button>\`).join('')}</nav><div class="account-section-view">${content}</div><button class="btn danger account-logout" data-logout>Sair da conta</button>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@877:30 — linha 877

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:877)

```javascript
([key])=>
```

Executa a etapa `callback@877:30` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `roles.includes`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@877:93 — linha 877

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:877)

```javascript
([key])=>
```

Executa a etapa `callback@877:93` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@887:436 — linha 887

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:887)

```javascript
uf=>
```

Executa a etapa `callback@887:436` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `profile.state===uf`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@889:349 — linha 889

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:889)

```javascript
a=>
```

Executa a etapa `callback@889:349` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `addressText`.

**Decisões condicionais examinadas:**
- `a.defaultAddress`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@892:58 — linha 892

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:892)

```javascript
r=>
```

Executa a etapa `callback@892:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@892:111 — linha 892

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:892)

```javascript
r=>
```

Executa a etapa `callback@892:111` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@893:246 — linha 893

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:893)

```javascript
r=>
```

Executa a etapa `callback@893:246` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `role`; `esc`.

**Decisões condicionais examinadas:**
- `role()===r`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@893:523 — linha 893

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:893)

```javascript
r=>
```

Executa a etapa `callback@893:523` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `registered.includes`; `pendingRoles.includes`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@893:724 — linha 893

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:893)

```javascript
r=>
```

Executa a etapa `callback@893:724` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `registered.includes`; `pendingRoles.includes`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@893:783 — linha 893

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:893)

```javascript
r=>
```

Executa a etapa `callback@893:783` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@895:142 — linha 895

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:895)

```javascript
r=>
```

Executa a etapa `callback@895:142` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `pill`.

**Decisões condicionais examinadas:**
- `r.requestType==='ROLE'`
- `r.requestType==='CITY'`
- `r.requestType==='STORE'`
- `r.status==='PENDING'`
- `r.status==='APPROVED'`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@897:393 — linha 897

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:897)

```javascript
([id,label])=>
```

Executa a etapa `callback@897:393` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `selected===id`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestOperatorAccess — linha 900

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:900)

```javascript
async function requestOperatorAccess(event)
```

Executa a etapa `request Operator Access` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `String`; `new FormData`; `api`; `JSON.stringify`; `notify`; `render`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function requestOperatorAccess(event){event.preventDefault();const roleName=String(new FormData(event.currentTarget).get('role'));try{await api('/account/platform-requests/roles',{method:'POST',body:JSON.stringify({role:roleName})});state.platformRequests=await api('/account/platform-requests');notify('Solicitação enviada','O administrador analisará o novo perfil vinculado à sua conta.','info');render();}catch(error){notify('Não foi possível solicitar o acesso',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestCityAccess — linha 901

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:901)

```javascript
async function requestCityAccess(event)
```

Executa a etapa `request City Access` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `api`; `JSON.stringify`; `String`; `form.get`; `notify`; `render`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function requestCityAccess(event){event.preventDefault();const form=new FormData(event.currentTarget);try{await api('/account/platform-requests/cities',{method:'POST',body:JSON.stringify({city:String(form.get('city')).trim(),state:String(form.get('state')).trim().toUpperCase(),postalCode:String(form.get('postalCode')||'').trim()||null})});state.platformRequests=await api('/account/platform-requests');notify('Cidade solicitada','A administração recebeu o pedido e fará a análise.','info');render();}catch(error){notify('Não foi possível solicitar a cidade',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refreshCitiesAfterSave — linha 903

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:903)

```javascript
async function refreshCitiesAfterSave(saved)
```

Executa a etapa `refresh Cities After Save` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `state.adminServiceCities.some`; `String`; `[...state.adminServiceCities,saved].sort`; `a.city.localeCompare`.

**Decisões condicionais examinadas:**
- `(saved&&!state.adminServiceCities.some(city=>String(city.id)===String(saved.id)))`

**Saída declarada:**
- `return true;`
- `return false;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function refreshCitiesAfterSave(saved){try{state.adminServiceCities=await api('/admin/service-cities');return true;}catch{if(saved&&!state.adminServiceCities.some(city=>String(city.id)===String(saved.id)))state.adminServiceCities=[...state.adminServiceCities,saved].sort((a,b)=>a.city.localeCompare(b.city,'pt-BR'));return false;}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@903:170 — linha 903

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:903)

```javascript
city=>
```

Executa a etapa `callback@903:170` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@903:278 — linha 903

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:903)

```javascript
(a,b)=>
```

Executa a etapa `callback@903:278` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `a.city.localeCompare`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createServiceCity — linha 904

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:904)

```javascript
async function createServiceCity(event)
```

Executa a etapa `create Service City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `api`; `JSON.stringify`; `String`; `form.get`; `formElement.reset`; `refreshCitiesAfterSave`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `refreshed`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function createServiceCity(event){event.preventDefault();const formElement=event.currentTarget,form=new FormData(formElement);try{const saved=await api('/admin/service-cities',{method:'POST',body:JSON.stringify({city:String(form.get('city')).trim(),state:String(form.get('state')).trim().toUpperCase(),postalCode:String(form.get('postalCode')||'').trim()||null,enabled:true})});formElement.reset();const refreshed=await refreshCitiesAfterSave(saved);notify('Cidade cadastrada',refreshed?'A cidade já está disponível na plataforma.':'A cidade foi salva; a lista será atualizada na próxima consulta.','success');renderPage();}catch(error){notify('Não foi possível cadastrar a cidade',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refreshStoreCategories — linha 905

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:905)

```javascript
async function refreshStoreCategories()
```

Executa a etapa `refresh Store Categories` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Promise.allSettled`; `api`; `applyPublicCategories`; `results.every`.

**Decisões condicionais examinadas:**
- `(results[0].status==='fulfilled')`
- `(results[1].status==='fulfilled')`

**Saída declarada:**
- `return results.every(result=>result.status==='fulfilled');`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@905:314 — linha 905

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:905)

```javascript
result=>
```

Executa a etapa `callback@905:314` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createStoreCategory — linha 906

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:906)

```javascript
async function createStoreCategory(event)
```

Executa a etapa `create Store Category` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `api`; `JSON.stringify`; `String`; `data.get`; `formElement.reset`; `refreshStoreCategories`; `state.adminStoreCategories.some`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `(!refreshed&&saved&&!state.adminStoreCategories.some(category=>String(category.id)===String(saved.id)))`
- `refreshed`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function createStoreCategory(event){event.preventDefault();const formElement=event.currentTarget,data=new FormData(formElement);try{const saved=await api('/admin/store-categories',{method:'POST',body:JSON.stringify({name:String(data.get('name')).trim(),emoji:String(data.get('emoji')).trim()})});formElement.reset();const refreshed=await refreshStoreCategories();if(!refreshed&&saved&&!state.adminStoreCategories.some(category=>String(category.id)===String(saved.id)))state.adminStoreCategories=[...state.adminStoreCategories,saved];notify('Seção criada',refreshed?'Ela já pode ser selecionada por vendedores e encontrada pelos clientes.':'A seção foi salva; a lista será atualizada na próxima consulta.','success');renderPage();}catch(error){notify('Não foi possível criar a seção',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@906:425 — linha 906

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:906)

```javascript
category=>
```

Executa a etapa `callback@906:425` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deleteStoreCategory — linha 907

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:907)

```javascript
async function deleteStoreCategory(id)
```

Executa a etapa `delete Store Category` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.adminStoreCategories.find`; `String`; `window.confirm`; `api`; `encodeURIComponent`; `state.adminStoreCategories.filter`; `refreshStoreCategories`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `(!category||category.builtIn)`
- `(!window.confirm(\`Excluir a seção “${category.name}”? Essa ação não pode ser desfeita.\`))`
- `refreshed`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function deleteStoreCategory(id){const category=state.adminStoreCategories.find(item=>String(item.id)===String(id));if(!category||category.builtIn)return;if(!window.confirm(`Excluir a seção “${category.name}”? Essa ação não pode ser desfeita.`))return;try{await api(`/admin/store-categories/${encodeURIComponent(id)}`,{method:'DELETE'});state.adminStoreCategories=state.adminStoreCategories.filter(item=>String(item.id)!==String(id));const refreshed=await refreshStoreCategories();notify('Seção excluída',refreshed?'A categoria foi removida do catálogo.':'A seção foi excluída; a lista será atualizada na próxima consulta.','success');renderPage();}catch(error){notify('Não foi possível excluir a seção',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@907:87 — linha 907

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:907)

```javascript
item=>
```

Executa a etapa `callback@907:87` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@907:414 — linha 907

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:907)

```javascript
item=>
```

Executa a etapa `callback@907:414` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateServiceCity — linha 908

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:908)

```javascript
async function updateServiceCity(event)
```

Executa a etapa `update Service City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `state.adminServiceCities.find`; `String`; `api`; `encodeURIComponent`; `JSON.stringify`; `data.get`; `refreshCitiesAfterSave`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `(!current)`
- `refreshed`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function updateServiceCity(event){event.preventDefault();const form=event.currentTarget,data=new FormData(form),current=state.adminServiceCities.find(city=>String(city.id)===String(form.dataset.serviceCityUpdate));if(!current)return;try{const saved=await api(`/admin/service-cities/${encodeURIComponent(current.id)}`,{method:'PUT',body:JSON.stringify({city:String(data.get('city')).trim(),state:String(data.get('state')).trim().toUpperCase(),postalCode:String(data.get('postalCode')||'').trim()||null,enabled:current.enabled})});const refreshed=await refreshCitiesAfterSave(saved);notify('Cidade atualizada',refreshed?'As alterações foram salvas.':'As alterações foram salvas; a lista será atualizada na próxima consulta.','success');renderPage();}catch(error){notify('Não foi possível atualizar a cidade',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@908:157 — linha 908

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:908)

```javascript
city=>
```

Executa a etapa `callback@908:157` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### toggleServiceCity — linha 909

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:909)

```javascript
async function toggleServiceCity(id,enabled)
```

Executa a etapa `toggle Service City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.adminServiceCities.find`; `String`; `api`; `encodeURIComponent`; `JSON.stringify`; `refreshCitiesAfterSave`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `(!city)`
- `enabled`
- `refreshed`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function toggleServiceCity(id,enabled){const city=state.adminServiceCities.find(item=>String(item.id)===String(id));if(!city)return;try{const saved=await api(`/admin/service-cities/${encodeURIComponent(id)}`,{method:'PUT',body:JSON.stringify({city:city.city,state:city.state,postalCode:city.postalCode||null,enabled})});const refreshed=await refreshCitiesAfterSave(saved);notify(enabled?'Cidade ativada':'Cidade suspensa',refreshed?(enabled?'Ela voltou a aparecer na seleção de cidades.':'Ela foi retirada da seleção de novas cidades.'):'A alteração foi salva; a lista será atualizada na próxima consulta.','info');renderPage();}catch(error){notify('Não foi possível alterar a cidade',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@909:87 — linha 909

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:909)

```javascript
item=>
```

Executa a etapa `callback@909:87` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### saveAdminProfile — linha 910

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:910)

```javascript
async function saveAdminProfile(event)
```

Executa a etapa `save Admin Profile` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `adminProfilePayload`; `api`; `encodeURIComponent`; `JSON.stringify`; `applyAdminProfileResponse`; `persistAccountHint`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `payload.clearCpf`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function saveAdminProfile(event){event.preventDefault();const payload=adminProfilePayload(new FormData(event.currentTarget),state.account);try{const updated=await api(`/admin/accounts/${encodeURIComponent(state.account.id)}/profile`,{method:'PUT',body:JSON.stringify(payload)});state.account=applyAdminProfileResponse(state.account,updated,payload);state.profile={...state.profile,...state.account};persistAccountHint(state.account);notify('Dados administrativos atualizados',payload.clearCpf?'O CPF foi removido da conta administrativa.':'O perfil foi salvo.','success');renderPage();}catch(error){notify('Não foi possível salvar o perfil',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### openAdminAccountEditor — linha 912

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:912)

```javascript
function openAdminAccountEditor(id)
```

Executa a etapa `open Admin Account Editor` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.adminAccounts.find`; `String`; `$`; `document.createElement`; `esc`; `account.maskedCpf?.includes`; `document.body.append`; `modal.querySelectorAll`; `button.addEventListener`; `modal.remove`; `modal.addEventListener`; `modal.querySelector`; `input.addEventListener`; `formatCpfMask`; `input.value.replace`.

**Decisões condicionais examinadas:**
- `(!account)`
- `account.role==='ADMIN'`
- `account.maskedCpf?.includes('•')`
- `account.role==='ADMIN'||account.maskedCpf!=='Não informado'`
- `(event.target===modal)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
function openAdminAccountEditor(id){const account=state.adminAccounts.find(item=>String(item.id)===String(id));if(!account)return;$('#admin-account-modal')?.remove();const modal=document.createElement('div');modal.id='admin-account-modal';modal.className='modal-backdrop';modal.innerHTML=`<section class="modal admin-edit-modal" role="dialog" aria-modal="true" aria-labelledby="admin-edit-title"><div class="modal-head"><div><span class="eyebrow">Gestão de conta</span><h2 id="admin-edit-title">Editar dados da conta</h2><p>O CPF de cliente e operador continua obrigatório. O CPF da conta administrativa é opcional.</p></div><button class="close-button" type="button" data-close-admin-edit aria-label="Fechar">×</button></div><form data-admin-account-edit="${esc(account.id)}" class="form-grid"><div class="field"><label>Nome completo</label><input name="name" required maxlength="120" value="${esc(account.name)}"/></div><div class="field"><label>E-mail</label><input name="email" type="email" required maxlength="254" value="${esc(account.email)}"/></div><div class="field"><label>CPF ${account.role==='ADMIN'?'(opcional)':''}</label><input name="cpf" data-mask="cpf" inputmode="numeric" maxlength="14" value="" placeholder="${account.maskedCpf?.includes('•')?`Atual: ${account.maskedCpf}`:'000.000.000-00'}" ${account.role==='ADMIN'||account.maskedCpf!=='Não informado'?'':'required'}/><small>Deixe em branco para manter o CPF. Informe o número completo somente se for corrigir.</small></div><div class="field"><label>Telefone</label><input name="phone" maxlength="30" value="${esc(account.phone||'')}"/></div><div class="field span-2"><label>Rua e número</label><input name="addressLine" maxlength="250" value="${esc(account.addressLine||'')}"/></div><div class="field"><label>Complemento</label><input name="addressComplement" maxlength="120" value="${esc(account.addressComplement||'')}"/></div><div class="field"><label>Bairro</label><input name="neighborhood" maxlength="120" value="${esc(account.neighborhood||'')}"/></div><div class="field"><label>Cidade</label><input name="city" maxlength="100" value="${esc(account.city||'')}"/></div><div class="field"><label>Estado</label><input name="state" maxlength="2" value="${esc(account.state||'')}"/></div><div class="field"><label>CEP</label><input name="postalCode" data-cep maxlength="9" value="${esc(account.postalCode||'')}"/></div>${account.role==='ADMIN'?'<label class="check-row span-2"><input name="clearCpf" type="checkbox"/> Remover o CPF da conta administrativa</label>':''}<div class="field span-2"><div class="alert-strip info"><span>i</span><span>Atualizar e-mail ou CPF altera dados de identificação; confira os documentos antes de salvar.</span></div></div><div class="span-2 action-row"><button class="btn" type="submit">Salvar dados</button><button class="btn outline" type="button" data-close-admin-edit>Cancelar</button></div></form></section>`;document.body.append(modal);modal.querySelectorAll('[data-close-admin-edit]').forEach(button=>button.addEventListener('click',()=>modal.remove()));modal.addEventListener('click',event=>{if(event.target===modal)modal.remove();});modal.querySelector('[data-admin-account-edit]').addEventListener('submit',saveAdminAccount);modal.querySelectorAll('[data-mask="cpf"]').forEach(input=>input.addEventListener('input',()=>input.value=formatCpfMask(input.value)));modal.querySelectorAll('[data-cep]').forEach(input=>input.addEventListener('input',()=>input.value=input.value.replace(/\D/g,'').slice(0,8).replace(/(\d{5})(\d)/,'$1-$2')));}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@912:76 — linha 912

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:912)

```javascript
item=>
```

Executa a etapa `callback@912:76` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@912:3020 — linha 912

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:912)

```javascript
button=>
```

Executa a etapa `callback@912:3020` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `modal.remove`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>modal.remove())
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@912:3060 — linha 912

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:912)

```javascript
()=>
```

Executa a etapa `callback@912:3060` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `modal.remove`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@912:3112 — linha 912

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:912)

```javascript
event=>
```

Executa a etapa `callback@912:3112` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `modal.remove`.

**Decisões condicionais examinadas:**
- `(event.target===modal)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@912:3307 — linha 912

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:912)

```javascript
input=>
```

Executa a etapa `callback@912:3307` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `input.addEventListener`; `formatCpfMask`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
input=>input.addEventListener('input',()=>input.value=formatCpfMask(input.value))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@912:3345 — linha 912

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:912)

```javascript
()=>
```

Executa a etapa `callback@912:3345` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `formatCpfMask`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@912:3435 — linha 912

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:912)

```javascript
input=>
```

Executa a etapa `callback@912:3435` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `input.addEventListener`; `input.value.replace`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
input=>input.addEventListener('input',()=>input.value=input.value.replace(/\D/g,'').slice(0,8).replace(/(\d{5})(\d)/,'$1-$2'))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@912:3473 — linha 912

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:912)

```javascript
()=>
```

Executa a etapa `callback@912:3473` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `input.value.replace`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### saveAdminAccount — linha 913

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:913)

```javascript
async function saveAdminAccount(event)
```

Executa a etapa `save Admin Account` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `String`; `data.get`; `state.adminAccounts.find`; `api`; `encodeURIComponent`; `JSON.stringify`; `$`; `notify`; `renderPage`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function saveAdminAccount(event){event.preventDefault();const form=event.currentTarget,data=new FormData(form),cpf=String(data.get('cpf')||'').replace(/\D/g,'');const account=state.adminAccounts.find(item=>String(item.id)===String(form.dataset.adminAccountEdit));try{await api(`/admin/accounts/${encodeURIComponent(account.id)}/profile`,{method:'PUT',body:JSON.stringify({name:String(data.get('name')).trim(),email:String(data.get('email')).trim(),phone:String(data.get('phone')||'').trim()||null,cpf:cpf||null,clearCpf:data.get('clearCpf')==='on',addressLine:String(data.get('addressLine')||'').trim()||null,addressComplement:String(data.get('addressComplement')||'').trim()||null,neighborhood:String(data.get('neighborhood')||'').trim()||null,city:String(data.get('city')||'').trim()||null,state:String(data.get('state')||'').trim().toUpperCase()||null,postalCode:String(data.get('postalCode')||'').replace(/\D/g,'')||null})});state.adminAccounts=await api('/admin/accounts');$('#admin-account-modal')?.remove();notify('Conta atualizada','Os dados informados foram salvos.','success');renderPage();}catch(error){notify('Não foi possível atualizar a conta',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@913:207 — linha 913

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:913)

```javascript
item=>
```

Executa a etapa `callback@913:207` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### saveProfile — linha 915

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:915)

```javascript
async function saveProfile(event)
```

Executa a etapa `save Profile` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `api`; `JSON.stringify`; `Object.fromEntries`; `['name','cpf','phone','addressLine','addressComplement','neighborhood','city','state','postalCode'].map`; `String`; `form.get`; `persistAccountHint`; `state.account.grantedRoles?.includes`; `state.addresses.find`; `loadAddresses`; `loadStores`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `(state.account.grantedRoles?.includes('CUSTOMER')&&state.profile.addressLine&&state.profile.neighborhood&&state.profile.city&&state.profile.state&&state.profile.postalCode)`
- `(current)`
- `(state.page==='account'&&state.account.grantedRoles?.includes('CUSTOMER'))`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try{state.profile=await api('/account/profile',{method:'PUT',body:JSON.stringify(Object.fromEntries(['name','cpf','phone','addressLine','addressComplement','neighborhood','city','state','postalCode'].map(k=>[k,String(form.get(k)||'').trim()||null]))) });state.account={...state.account,...state.profile,activeRole:state.activeRole};persistAccountHint(state.account);if(state.account.grantedRoles?.includes('CUSTOMER')&&state.profile.addressLine&&state.profile.neighborhood&&state.profile.city&&state.profile.state&&state.profile.postalCode){const current=state.addresses.find(a=>a.defaultAddress);const body={label:current?.label||'Casa',addressLine:state.profile.addressLine,addressComplement:state.profile.addressComplement,neighborhood:state.profile.neighborhood,city:state.profile.city,state:state.profile.state,postalCode:state.profile.postalCode,defaultAddress:true};if(current)await api(`/account/addresses/${current.id}`,{method:'PUT',body:JSON.stringify(body)});else await api('/account/addresses',{method:'POST',body:JSON.stringify(body)});await loadAddresses();}if(state.page==='account'&&state.account.grantedRoles?.includes('CUSTOMER'))await loadStores().catch(()=>{});notify('Perfil atualizado','Seus dados foram salvos com sucesso.');render();}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@917:207 — linha 917

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:917)

```javascript
k=>
```

Executa a etapa `callback@917:207` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`; `form.get`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@917:579 — linha 917

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:917)

```javascript
a=>
```

Executa a etapa `callback@917:579` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@917:1177 — linha 917

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:917)

```javascript
()=>
```

Executa a etapa `callback@917:1177` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### changePassword — linha 921

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:921)

```javascript
async function changePassword(event)
```

Executa a etapa `change Password` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `String`; `form.get`; `notify`; `api`; `JSON.stringify`; `persistAccountHint`; `render`.

**Decisões condicionais examinadas:**
- `(newPassword!==String(form.get('confirmPassword')))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function changePassword(event){event.preventDefault();const form=new FormData(event.currentTarget);const currentPassword=String(form.get('currentPassword')),newPassword=String(form.get('newPassword'));if(newPassword!==String(form.get('confirmPassword'))){notify('As senhas não conferem','Digite a mesma senha nos dois campos.','error');return;}try{const response=await api('/account/password',{method:'POST',body:JSON.stringify({currentPassword,newPassword})});state.account={...state.account,...response.account,activeRole:state.activeRole};state.profile=state.account;state.mustChangePassword=false;persistAccountHint(state.account);notify('Senha alterada','Sua conta está protegida com a nova senha.');render();}catch(error){notify('Não foi possível alterar a senha',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestPhoneVerification — linha 923

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:923)

```javascript
async function requestPhoneVerification()
```

Executa a etapa `request Phone Verification` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `notify`; `renderPage`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function requestPhoneVerification(){try{await api('/account/phone-verification',{method:'POST'});state.phoneVerificationSent=true;notify('Código solicitado','Se o WhatsApp estiver configurado, você receberá um código no telefone cadastrado.');renderPage();}catch(error){notify('Não foi possível solicitar o código',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### confirmPhoneVerification — linha 924

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:924)

```javascript
async function confirmPhoneVerification(event)
```

Executa a etapa `confirm Phone Verification` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `String`; `new FormData`; `api`; `JSON.stringify`; `persistAccountHint`; `notify`; `renderPage`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function confirmPhoneVerification(event){event.preventDefault();const code=String(new FormData(event.currentTarget).get('code')||'').trim();try{await api('/account/phone-verification/confirm',{method:'POST',body:JSON.stringify({code})});state.profile={...state.profile,phoneVerified:true};state.account={...state.account,phoneVerified:true};state.phoneVerificationSent=false;persistAccountHint(state.account);notify('Telefone confirmado','A recuperação por WhatsApp está habilitada para esta conta.');renderPage();}catch(error){notify('Código não confirmado',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### switchRole — linha 926

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:926)

```javascript
async function switchRole(nextRole)
```

Executa a etapa `switch Role` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** ``; `stopChat`; `persistAccountHint`; `loadCurrentPage`; `render`.

**Decisões condicionais examinadas:**
- `(!(state.account?.grantedRoles||[]).includes(nextRole))`
- `nextRole==='ADMIN'`
- `nextRole==='MERCHANT'`
- `nextRole==='COURIER'`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderNotifications — linha 928

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:928)

```javascript
function renderNotifications()
```

Executa a etapa `render Notifications` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `list.map`; `esc`; `['error','warn','warning'].includes`; `shortDate`; `emptyState`.

**Decisões condicionais examinadas:**
- `list.length`
- `n.read`
- `n.targetType`
- `['error','warn','warning'].includes(n.kind)`
- `n.kind==='info'`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Atualizações da sua conta</span><h1>Notificações</h1><p>Pedidos, reservas e avisos vinculados somente a este perfil.</p></div>${list.length?'<button class="btn outline small" data-clear-notifications>Limpar avisos</button>':''}</div> ${list.length?\`<div class="notification-list">${list.map(n=>\`<article class="notification-item ${n.read?'read':''} ${n.targetType?'is-actionable':''}" ${n.targetType?\`role="button" tabindex="0" data-notification-target="${esc(n.targetType)}" data-target-id="${esc(n.targetId||'')}"\`:''}><span class="notification-symbol">${['error','warn','warning'].includes(n.kind)?'⚠':n.kind==='info'?'ℹ':'✓'}</span><div><strong>${esc(n.title)}</strong><p>${esc(n.message)}</p><time>${shortDate(n.date)}</time></div>${n.read?'':'<span class="notification-new">Nova</span>'}${n.targetType?'<span class="notification-open-hint" aria-hidden="true">Abrir →</span>':''}</article>\`).join('')}</div>\`:emptyState('♧','Tudo em dia','Quando houver novidade sobre seus pedidos ou reservas, ela aparecerá aqui.')}\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@931:62 — linha 931

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:931)

```javascript
n=>
```

Executa a etapa `callback@931:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `['error','warn','warning'].includes`; `shortDate`.

**Decisões condicionais examinadas:**
- `n.read`
- `n.targetType`
- `['error','warn','warning'].includes(n.kind)`
- `n.kind==='info'`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderFinance — linha 934

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:934)

```javascript
function renderFinance()
```

Executa a etapa `render Finance` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `role`; `activeMerchantStore`; `esc`; `icon`; ``.

**Decisões condicionais examinadas:**
- `(role()==='MERCHANT')`
- `(!store)`
- `state.paymentConnection?.connected`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Financeiro da loja</span><h1>Recebimentos</h1><p>Cadastre uma loja para configurar os recebimentos.</p></div></div>\`;`
- `return \`<div class="page-head"><div><span class="eyebrow">Financeiro · ${esc(store.name)}</span><h1>Recebimentos e saques</h1><p>Os pagamentos online são enviados diretamente à conta Mercado Pago conectada a esta loja.</p></div></div><section class="panel finance-card"><div class="finance-icon">${icon('receipt',24)}</div><div><span class="eyebrow">Conta de recebimento</span><h2>Mercado Pago</h2><p class="store-desc">${state.paymentConnection?.connected?'Conta conectada. Seu saldo, prazos e saques ficam disponíveis no Mercado Pago.':'Conecte sua conta para receber pagamentos online. A conta deve pertencer ao responsável pela loja.'}</p><div class="action-row">${state.paymentConnection?.connected?'<a class="btn" href="https://www.mercadopago.com.br/home" target="_blank" rel="noopener noreferrer">Abrir Mercado Pago ↗</a>':\`<button class="btn" data-connect-mercadopago="${esc(store.id)}">Conectar Mercado Pago</button>\`}</div></div></section><div class="alert-strip info"><span>i</span><span>O FoodDelivery não guarda a senha do Mercado Pago nem inicia saques em seu nome. Confira saldo, tarifas, dados bancários e confirme a retirada dentro do próprio Mercado Pago. Reservas com pagamento após o serviço não passam pelo checkout da plataforma.</span></div>\`;`
- `return \`<div class="page-head"><div><span class="eyebrow">Financeiro do entregador</span><h1>Ganhos e repasses</h1><p>Acompanhe o status dos repasses da plataforma.</p></div></div><section class="panel finance-card"><div class="finance-icon">${icon('truck',24)}</div><div><span class="eyebrow">Entregas concluídas</span><h2>${delivered}</h2><p class="store-desc">As corridas ainda não têm remuneração nem repasse configurados pelo FoodDelivery. Por isso, esta tela não apresenta saldo fictício nem permite solicitar saque sem valor devido registrado.</p><button class="btn outline" data-help>Falar com atendimento</button></div></section><div class="alert-strip warn"><span>!</span><span>Para habilitar saques automáticos, a plataforma precisa definir a remuneração por entrega, registrar os valores devidos e integrar um provedor de repasses Pix. Até essa configuração, combine qualquer pagamento diretamente com a administração e não informe senhas ou códigos de acesso.</span></div>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@940:49 — linha 940

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:940)

```javascript
d=>
```

Executa a etapa `callback@940:49` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### openNotificationTarget — linha 944

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:944)

```javascript
async function openNotificationTarget(target,id)
```

Executa a etapa `open Notification Target` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `role`; `loadAdminData`; `render`; `openTicket`; `openSupportCenter`; `navByRole[role`; `navigate`; `CSS.escape`; `document.querySelector`; `card?.scrollIntoView`; `card?.classList.add`; `window.setTimeout`; `card?.classList.remove`.

**Decisões condicionais examinadas:**
- `role()==='MERCHANT'`
- `role()==='COURIER'`
- `(target==='SUPPORT')`
- `(role()==='ADMIN')`
- `(id)`
- `(!destination||!navByRole[role()]?.some(([page])=>page===destination))`
- `(target==='ADMIN_REQUESTS')`
- `(target==='STORE'&&role()==='MERCHANT')`
- `selectors[target]`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if(id){const selectors={ORDER:`[data-order-card="${CSS.escape(id)}"]`,RESERVATION:`[data-reservation-card="${CSS.escape(id)}"]`,DELIVERY:`[data-delivery-card="${CSS.escape(id)}"]`};const card=selectors[target]?document.querySelector(selectors[target]):null;card?.scrollIntoView({behavior:'smooth',block:'center'});card?.classList.add('notification-target-highlight');window.setTimeout(()=>card?.classList.remove('notification-target-highlight'),2200);}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@951:45 — linha 951

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:951)

```javascript
([page])=>
```

Executa a etapa `callback@951:45` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@955:388 — linha 955

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:955)

```javascript
()=>
```

Executa a etapa `callback@955:388` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `card?.classList.remove`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderMerchant — linha 958

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:958)

```javascript
function renderMerchant()
```

Executa a etapa `render Merchant` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `activeMerchantStore`; `renderMerchantAll`; `document.createElement`; `parts[1]?.classList.contains`; `parts.find`; `node.classList.contains`; `node.querySelector?.`; `headerNode.querySelector`; `operatingHoursPanel`; `storeFields`; `state.orders.filter`; `['COMPLETED','CANCELLED','REJECTED'].includes`; `state.platformRequests.find`; `String`; `esc`; `state.ownStores.map`; `tabs.map`.

**Decisões condicionais examinadas:**
- `(!store)`
- `parts[1]?.classList.contains('alert-strip')`
- `(merchantHeadings[selected]&&headerNode)`
- `selected==='catalog'`
- `selected==='settings'`
- `!store.city||!store.state`
- `selected==='branding'`
- `rejectedRequest`
- `state.ownStores.length>1`
- `String(s.id)===String(store.id)`
- `selected===id`

**Saída declarada:**
- `return renderMerchantAll();`
- `return \`${header}${rejectedRequest?\`<div class="alert-strip warn"><span>!</span><span>A loja não foi aprovada. ${esc(rejectedRequest.reviewNote||'Revise os dados e envie novamente para análise.')} <button class="btn outline small" data-resubmit-store="${esc(store.id)}">Reenviar para análise</button></span></div>\`:''}${state.ownStores.length>1?\`<div class="panel merchant-store-picker"><label for="merchant-store-select">Loja em edição</label><select id="merchant-store-select" data-merchant-store-select>${state.ownStores.map(s=>\`<option value="${esc(s.id)}" ${String(s.id)===String(store.id)?'selected':''}>${esc(s.name)} · ${esc(s.city||'')}</option>\`).join('')}</select></div>\`:''}<nav class="section-tabs" aria-label="Seções da loja">${tabs.map(([id,label])=>\`<button class="section-tab ${selected===id?'active':''}" data-section="${id}" aria-current="${selected===id?'page':'false'}">${label}</button>\`).join('')}</nav><div class="section-view">${body}</div>\`;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const source=document.createElement('template');source.innerHTML=renderMerchantAll();
if(merchantHeadings[selected]&&headerNode){headerNode.querySelector('h1').textContent=merchantHeadings[selected][0];headerNode.querySelector('p').textContent=merchantHeadings[selected][1];}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@964:26 — linha 964

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:964)

```javascript
node=>
```

Executa a etapa `callback@964:26` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `node.classList.contains`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@965:28 — linha 965

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:965)

```javascript
node=>
```

Executa a etapa `callback@965:28` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `node.classList.contains`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@966:29 — linha 966

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:966)

```javascript
node=>
```

Executa a etapa `callback@966:29` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `node.querySelector?.`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@967:29 — linha 967

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:967)

```javascript
node=>
```

Executa a etapa `callback@967:29` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `node.classList.contains`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@968:28 — linha 968

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:968)

```javascript
node=>
```

Executa a etapa `callback@968:28` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `node.classList.contains`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@975:1138 — linha 975

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:975)

```javascript
o=>
```

Executa a etapa `callback@975:1138` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `['COMPLETED','CANCELLED','REJECTED'].includes`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@976:53 — linha 976

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:976)

```javascript
r=>
```

Executa a etapa `callback@976:53` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@977:536 — linha 977

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:977)

```javascript
s=>
```

Executa a etapa `callback@977:536` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `String`.

**Decisões condicionais examinadas:**
- `String(s.id)===String(store.id)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@977:762 — linha 977

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:977)

```javascript
([id,label])=>
```

Executa a etapa `callback@977:762` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `selected===id`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### activeMerchantStore — linha 980

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:980)

```javascript
function activeMerchantStore()
```

Executa a etapa `active Merchant Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.ownStores.find`; `String`.

**Saída declarada:**
- `return state.ownStores.find(s=>String(s.id)===String(state.merchantStoreId))||state.ownStores[0]||null;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@980:60 — linha 980

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:980)

```javascript
s=>
```

Executa a etapa `callback@980:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### selectMerchantStore — linha 981

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:981)

```javascript
async function selectMerchantStore(event)
```

Executa a etapa `select Merchant Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `activeMerchantStore`; `Promise.all`; `api`; `renderPage`; `notify`.

**Decisões condicionais examinadas:**
- `store`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderMerchantAll — linha 983

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:983)

```javascript
function renderMerchantAll()
```

Executa a etapa `render Merchant All` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `activeMerchantStore`; `esc`; `storeFields`; `displayName`; `pill`; `state.orders.filter`; `['COMPLETED','CANCELLED','REJECTED'].includes`; `state.storeItems.map`; `catalogItemIcon`; `money`; `emptyState`; `storeCategoryOptions`; `storeMapFields`; `fulfillmentFields`; `storeTimezoneOptions`; `formatCurrencyValue`.

**Decisões condicionais examinadas:**
- `(!store)`
- `state.merchantLoadError`
- `!store.active`
- `store.approved`
- `!store.approved`
- `state.account.operatorApplicationStatus==='APPROVED'`
- `(state.storeItems||[]).length`
- `item.active`
- `item.serviceItem`
- `item.payAfterService`
- `state.paymentConnection?.connected`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Seu negócio no FoodDelivery</span><h1>Vamos abrir sua loja?</h1><p>Cadastre o perfil para começar a incluir produtos e serviços.</p></div></div>${state.merchantLoadError?\`<div class="alert-strip warn"><span>!</span><span>${esc(state.merchantLoadError)}</span></div>\`:''} <div class="split-layout"><form class="panel" data-create-store><h3>Dados da loja</h3>${storeFields()}<button class="btn" type="submit">Cadastrar loja</button><p class="warning-small">Sua loja ficará oculta para clientes até a aprovação do administrador.</p></form><aside class="panel"><h3>Próximos passos</h3><p class="store-desc">Depois do cadastro, adicione itens ao catálogo. O administrador revisará e publicará a loja para que os clientes possam encontrá-la.</p><div class="alert-strip info"><span>✓</span><span>Você controla os pedidos, as reservas e o catálogo por este painel.</span></div></aside></div>\`;`
- `return \`<div class="page-head"><div><span class="eyebrow">Painel do vendedor</span><h1>Olá, ${esc(displayName())}.</h1><p>Cuide da sua loja e deixe o resto com a gente.</p></div>${pill(!store.active?'STORE_SUSPENDED':store.approved?'CONFIRMED':'PENDING_REVIEW')}</div> ${state.merchantLoadError?\`<div class="alert-strip warn"><span>!</span><span>${esc(state.merchantLoadError)}</span></div>\`:''} ${!store.active?'<div class="alert-strip warn"><span>!</span><span>A loja está suspensa pela administração. Ela não aparece para clientes; entre em contato com o suporte para solicitar uma revisão.</span></div>':!store.approved?\`<div class="alert-strip warn"><span>◷</span><span>${state.account.operatorApplicationStatus==='APPROVED'?'Seu acesso de vendedor já foi aprovado. Agora a loja aguarda uma revisão própria antes de aparecer para os clientes.':'A loja aguarda a aprovação da administração antes de aparecer para os clientes.'}</span></div>\`:''} <div class="stat-grid"><div class="stat-card"><small>Loja</small><strong>${esc(store.name)}</strong><span>${esc(store.category)}</span></div><div class="stat-card"><small>Pedidos para atender</small><strong>${state.orders.filter(o=>!['COMPLETED','CANCELLED','REJECTED'].includes(o.status)).length||'—'}</strong><span>Acompanhe na aba Pedidos</span></div><div class="stat-card"><small>Como encontrar</small><strong>${!store.active?'Suspensa':store.approved?'Publicada':'Em revisão'}</strong><span>${esc(store.address)}</span></div></div> <div class="split-layout merchant-catalog-layout"><section class="panel"><div class="section-head" style="margin-top:0"><h3>Seu catálogo</h3></div>${(state.storeItems||[]).length?\`<div class="menu-list">${state.storeItems.map((item,i)=>\`<div class="menu-row ${item.active?'':'menu-row-inactive'}"><div class="menu-row-icon">${catalogItemIcon(item.serviceItem)}</div><div class="menu-row-copy"><strong>${esc(item.name)}${item.active?'':' · Pausado'}</strong><small>${item.serviceItem?\`Serviço · ${item.payAfterService?'pagamento após o atendimento':'pagamento antecipado'}\`:\`Produto · ${item.stock} em estoque\`}</small></div><span class="menu-row-price">${money(item.priceCents)}</span><div class="action-row"><button class="btn outline small" type="button" data-item-edit="${esc(item.id)}">Editar</button>${item.active?\`<button class="btn danger small" type="button" data-item-deactivate="${esc(item.id)}">Remover</button>\`:\`<button class="btn light small" type="button" data-item-activate="${esc(item.id)}">Reativar</button>\`}</div></div>\`).join('')}</div>\`:emptyState('☕','Seu catálogo começa aqui','Inclua produtos ou serviços para que a loja possa receber pedidos.')}</section> <form class="panel catalog-item-form" data-create-item><h3>Adicionar ao catálogo</h3><div class="field"><label>Nome do item</label><input name="name" required maxlength="140" placeholder="Ex.: Corte clássico ou pomada modeladora" /></div><div class="field"><label>Descrição</label><textarea name="description" maxlength="1000" placeholder="Conte um pouco sobre esta opção"></textarea></div><div class="form-grid"><div class="field"><label>Preço (R$)</label><input name="price" data-mask="currency" inputmode="numeric" value="R$ 0,00" required aria-describedby="price-help" /><small id="price-help">Digite os números do valor; os centavos são preenchidos automaticamente.</small></div><div class="field"><label>O que você oferece?</label><select name="serviceItem" data-item-kind><option value="false">Produto à venda</option><option value="true">Serviço agendado</option></select></div><div class="field" data-stock-field><label>Estoque inicial</label><input name="stock" type="number" min="0" value="20" required /></div><div class="field" data-service-duration-field hidden><label>Duração do serviço (minutos)</label><input name="appointmentDurationMinutes" type="number" min="5" max="480" step="5" value="30" required disabled/><small>O horário precisa caber inteiro dentro do expediente e não pode coincidir com outro atendimento do profissional.</small></div><div class="field" data-service-payment-field hidden><label>Quando cobrar?</label><select name="payAfterService" data-pay-after-service><option value="false">Antes: pagamento online para confirmar</option><option value="true">Depois: pagamento direto à loja</option></select></div></div><div class="alert-strip warn" data-no-show-warning hidden><span>!</span><span>Pagamento após o atendimento: se o cliente não comparecer ou não pagar, você pode perder o horário reservado e o valor do serviço. O pagamento será combinado diretamente com a loja, fora do checkout.</span></div><label class="check-row" data-risk-ack-field hidden><input type="checkbox" name="acceptNoShowRisk" /><span>Entendi o risco e quero oferecer pagamento após o atendimento.</span></label><button class="btn block" type="submit">Adicionar item</button></form></div> <div class="panel" style="margin-top:18px"><h3>Editar perfil da loja</h3><form data-update-store="${esc(store.id)}" class="form-grid"><div class="field"><label>Nome</label><input name="name" value="${esc(store.name)}" required maxlength="140" /></div><div class="field"><label>Categoria</label><select name="category" required>${storeCategoryOptions(store.category)}</select></div><div class="field span-2"><label>Endereço</label><input name="address" value="${esc(store.address)}" required maxlength="250" /></div><div class="field"><label>Cidade</label><input name="city" value="${esc(store.city||state.account?.city||state.browseLocation?.city||'')}" required maxlength="100" /></div><div class="field"><label>Estado</label><input name="state" value="${esc(store.state||state.account?.state||state.browseLocation?.state||'')}" required minlength="2" maxlength="2" /></div>${storeMapFields(store)}${fulfillmentFields(store)}<div class="field span-2"><label>Descrição</label><textarea name="description" maxlength="1000">${esc(store.description||'')}</textarea></div><div class="field"><label>Fuso horário da loja</label><select name="timezone">${storeTimezoneOptions(store.timezone)}</select><small>Os horários de reserva seguem o relógio local da loja.</small></div><div class="field"><label>Taxa fixa de entrega (R$)</label><input name="deliveryFee" data-mask="currency" inputmode="numeric" value="${esc(formatCurrencyValue((store.deliveryFeeCents||0)/100))}" required /><small>Este valor será somado ao checkout dos pedidos com delivery.</small></div><div class="span-2"><button class="btn light" type="submit">Salvar perfil</button></div></form></div> <section class="panel store-branding-panel" data-store-cover style="margin-top:18px"></section> <div class="panel payment-connect-panel" style="margin-top:18px"><div><span class="eyebrow">Receba pelos seus pedidos</span><h3>Conta Mercado Pago</h3><p class="store-desc">${state.paymentConnection?.connected?'Conta conectada. Os pagamentos são direcionados ao seu Mercado Pago.':'Conecte a conta Mercado Pago da loja para receber pagamentos e habilitar checkout.'}</p></div><button class="btn ${state.paymentConnection?.connected?'outline':'primary'}" data-connect-mercadopago="${esc(store.id)}">${state.paymentConnection?.connected?'Reconectar conta':'Conectar Mercado Pago'}</button></div>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@990:234 — linha 990

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:990)

```javascript
o=>
```

Executa a etapa `callback@990:234` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `['COMPLETED','CANCELLED','REJECTED'].includes`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@991:232 — linha 991

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:991)

```javascript
(item,i)=>
```

Executa a etapa `callback@991:232` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `catalogItemIcon`; `esc`; `money`.

**Decisões condicionais examinadas:**
- `item.active`
- `item.serviceItem`
- `item.payAfterService`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeCategoryOptions — linha 998

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:998)

```javascript
function storeCategoryOptions(selected='')
```

Executa a etapa `store Category Options` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `categoryOptions.slice`; `esc`.

**Decisões condicionais examinadas:**
- `c===selected`

**Saída declarada:**
- `return categoryOptions.slice(1).map(c=>\`<option value="${esc(c)}" ${c===selected?'selected':''}>${esc(c)}</option>\`).join('');`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@998:80 — linha 998

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:998)

```javascript
c=>
```

Executa a etapa `callback@998:80` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`.

**Decisões condicionais examinadas:**
- `c===selected`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeTimezoneOptions — linha 999

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:999)

```javascript
function storeTimezoneOptions(selected='America/Sao_Paulo')
```

Executa a etapa `store Timezone Options` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `zones.map`.

**Decisões condicionais examinadas:**
- `value===(selected||'America/Sao_Paulo')`

**Saída declarada:**
- `return zones.map(([value,label])=>\`<option value="${value}" ${value===(selected||'America/Sao_Paulo')?'selected':''}>${label} · ${value}</option>\`).join('');`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@999:246 — linha 999

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:999)

```javascript
([value,label])=>
```

Executa a etapa `callback@999:246` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `value===(selected||'America/Sao_Paulo')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeFields — linha 1000

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1000)

```javascript
function storeFields()
```

Executa a etapa `store Fields` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `storeCategoryOptions`; `esc`; `storeMapFields`; `fulfillmentFields`; `storeTimezoneOptions`.

**Saída declarada:**
- `return \`<div class="field"><label>Nome da loja</label><input name="name" required maxlength="140" placeholder="Ex.: Cantina da Praça" /></div><div class="field"><label>CNPJ <small>(opcional)</small></label><input name="cnpj" data-mask="cnpj" inputmode="numeric" maxlength="18" placeholder="00.000.000/0000-00" /><small>Informe somente se o estabelecimento possuir CNPJ. Se preenchido, ficará vinculado a esta loja.</small></div><div class="field"><label>Categoria</label><select name="category" required>${storeCategoryOptions()}</select></div><div class="field"><label>Endereço</label><input name="address" required maxlength="250" placeholder="Rua, número e bairro" /></div><div class="field"><label>Cidade</label><input name="city" required maxlength="100" value="${esc(state.account?.city||'')}" placeholder="Sua cidade" /></div><div class="field"><label>Estado</label><input name="state" required minlength="2" maxlength="2" value="${esc(state.account?.state||'')}" placeholder="SP" /></div>${storeMapFields()}${fulfillmentFields()}<div class="field"><label>Fuso horário da loja</label><select name="timezone">${storeTimezoneOptions()}</select><small>As reservas usam o horário local do estabelecimento.</small></div><div class="field"><label>Taxa fixa de entrega (R$)</label><input name="deliveryFee" data-mask="currency" inputmode="numeric" value="R$ 0,00" required /></div><div class="field"><label>Descrição</label><textarea name="description" maxlength="1000" placeholder="O que torna sua loja especial?"></textarea></div>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderMerchantOrders — linha 1002

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1002)

```javascript
function renderMerchantOrders()
```

Executa a etapa `render Merchant Orders` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.map`; `esc`; `String`; `shortDate`; `o.lines.map`; `chatPanel`; `money`; `pill`; `merchantOrderAction`; `emptyState`.

**Decisões condicionais examinadas:**
- `orders.length`
- `o.fulfillmentType==='DELIVERY'`
- `o.customerNote`
- `o.status!=='AWAITING_PAYMENT'`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Sua operação</span><h1>Pedidos da loja</h1><p>Novos pedidos e andamento em um só lugar.</p></div><button class="btn light" data-action="reload-merchant-orders">↻ Atualizar</button></div> ${orders.length?\`<div class="panel table-wrap"><table class="data-table"><thead><tr><th>Pedido</th><th>Modalidade</th><th>Itens e observação</th><th>Valor</th><th>Status</th><th>Ação</th></tr></thead><tbody>${orders.map(o=>\`<tr data-order-card="${esc(o.id)}"><td><strong>${esc(String(o.id).slice(0,8).toUpperCase())}</strong><br><small>${shortDate(o.createdAt)}</small></td><td>${o.fulfillmentType==='DELIVERY'?'Entrega':'Retirada'}</td><td>${o.lines.map(i=>\`${i.quantity} × ${esc(i.name)}\`).join(', ')}${o.customerNote?\`<div class="customer-note"><strong>Observação</strong><p>${esc(o.customerNote)}</p></div>\`:''}${o.status!=='AWAITING_PAYMENT'?\`<button class="btn outline small" data-open-chat-type="orders" data-open-chat-id="${esc(o.id)}">Mensagens</button>${chatPanel('orders',o.id)}\`:''}</td><td>${money(o.totalCents)}</td><td>${pill(o.status)}</td><td>${merchantOrderAction(o)}</td></tr>\`).join('')}</tbody></table></div>\`:emptyState('🧾','Nenhum pedido por enquanto','Assim que um cliente confirmar o pagamento, o pedido aparecerá aqui.')}\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1005:229 — linha 1005

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1005)

```javascript
o=>
```

Executa a etapa `callback@1005:229` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `String`; `shortDate`; `o.lines.map`; `chatPanel`; `money`; `pill`; `merchantOrderAction`.

**Decisões condicionais examinadas:**
- `o.fulfillmentType==='DELIVERY'`
- `o.customerNote`
- `o.status!=='AWAITING_PAYMENT'`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1005:464 — linha 1005

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1005)

```javascript
i=>
```

Executa a etapa `callback@1005:464` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### merchantOrderAction — linha 1008

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1008)

```javascript
function merchantOrderAction(order)
```

Executa a etapa `merchant Order Action` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `['AWAITING_ACCEPTANCE','ACCEPTED','PREPARING'].includes`.

**Decisões condicionais examinadas:**
- `(order.cancellationRequestStatus==='REQUESTED')`
- `order.cancellationRequestNote`
- `(order.status==='READY'&&order.fulfillmentType==='DELIVERY')`
- `action`
- `canCancel`
- `order.status==='AWAITING_ACCEPTANCE'`
- `progress||cancellation`

**Saída declarada:**
- `return \`<div class="alert-strip warn"><span>◷</span><span>Cliente solicitou cancelamento${order.cancellationRequestNote?\`: ${esc(order.cancellationRequestNote)}\`:''}. Ao aceitar, o sistema solicitará o estorno integral.</span></div><div class="action-row"><button class="btn danger small" data-order-cancellation-decision="${esc(order.id)}" data-accepted="true">Aceitar e estornar</button><button class="btn outline small" data-order-cancellation-decision="${esc(order.id)}" data-accepted="false">Recusar solicitação</button></div>\`;`
- `return \`<button class="btn small" data-offer-delivery="${esc(order.id)}">Enviar para entregadores</button>\`;`
- `return progress||cancellation?\`<div class="action-row">${progress}${cancellation}</div>\`:'—';`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderMerchantReservations — linha 1017

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1017)

```javascript
function renderMerchantReservations()
```

Executa a etapa `render Merchant Reservations` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.merchantProfessionals.map`; `esc`; `person.name.slice`; `list.map`; `dateTimeInZone`; `String`; `pill`; `money`; `reservationActions`; `chatPanel`; `emptyState`.

**Decisões condicionais examinadas:**
- `state.merchantProfessionals.length`
- `person.active`
- `state.merchantLoadError`
- `list.length`
- `r.professionalName`
- `r.operatingHoursNotice`
- `r.payAfterService`
- `r.customerNote`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Agenda do seu negócio</span><h1>Reservas</h1><p>Organize os próximos atendimentos.</p></div><button class="btn light" data-action="reload-merchant-reservations">↻ Atualizar</button></div> <section class="panel professional-panel"><div class="section-head"><div><h3>Profissionais da agenda</h3><p class="store-desc">Cada profissional atende uma reserva por horário. O cliente pode escolher ou pedir o primeiro disponível.</p></div></div><form class="action-row" data-professional-create><label class="sr-only" for="professional-name">Nome do profissional</label><input id="professional-name" name="name" required maxlength="100" placeholder="Nome do profissional"/><button class="btn small" type="submit">Adicionar profissional</button></form>${state.merchantProfessionals.length?\`<div class="professional-list">${state.merchantProfessionals.map(person=>\`<article class="professional-row"><span class="professional-avatar">${esc(person.name.slice(0,1).toUpperCase())}</span><strong>${esc(person.name)}</strong><span class="status-pill ${person.active?'done':'cancel'}">${person.active?'Ativo':'Inativo'}</span><button class="btn outline small" type="button" data-professional-active="${esc(person.id)}" data-active="${!person.active}">${person.active?'Desativar':'Ativar'}</button></article>\`).join('')}</div>\`:'<p class="store-desc">Adicione os profissionais que atendem nesta loja. O cliente poderá escolher um ou pedir o primeiro disponível.</p>'}</section> ${state.merchantLoadError?\`<div class="alert-strip warn"><span>!</span><span>${esc(state.merchantLoadError)}</span></div>\`:''}${list.length?list.map(r=>\`<article class="order-card" data-reservation-card="${esc(r.id)}"><div class="order-card-top"><div><h3>${esc(r.serviceName)}</h3><p>${dateTimeInZone(r.startsAt,r.store.timezone||'America/Sao_Paulo')} · Reserva ${esc(String(r.id).slice(0,8).toUpperCase())}${r.professionalName?\` · ${esc(r.professionalName)}\`:''}</p></div>${pill(r.status)}</div>${r.operatingHoursNotice?\`<div class="alert-strip info"><span>◷</span><span>${esc(r.operatingHoursNotice)}</span></div>\`:''}${r.payAfterService?'<div class="alert-strip info"><span>R$</span><span>Pagamento será feito diretamente à loja após o atendimento. Não há cobrança pelo site.</span></div>':''}${r.customerNote?\`<div class="customer-note"><strong>Comentário do cliente</strong><p>${esc(r.customerNote)}</p></div>\`:''}<div class="order-bottom"><span>${money(r.amountCents)}</span><span>${reservationActions(r)}</span></div><div class="action-row"><button class="btn outline small" data-open-chat-type="reservations" data-open-chat-id="${esc(r.id)}">Mensagens</button></div>${chatPanel('reservations',r.id)}</article>\`).join(''):emptyState('🗓️','Sua agenda está livre','Reservas confirmadas aparecerão aqui, mesmo quando o pagamento for combinado no local.')}\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1020:664 — linha 1020

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1020)

```javascript
person=>
```

Executa a etapa `callback@1020:664` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `person.name.slice`.

**Decisões condicionais examinadas:**
- `person.active`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1021:154 — linha 1021

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1021)

```javascript
r=>
```

Executa a etapa `callback@1021:154` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `dateTimeInZone`; `String`; `pill`; `money`; `reservationActions`; `chatPanel`.

**Decisões condicionais examinadas:**
- `r.professionalName`
- `r.operatingHoursNotice`
- `r.payAfterService`
- `r.customerNote`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### reservationActions — linha 1024

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1024)

```javascript
function reservationActions(r)
```

Executa a etapa `reservation Actions` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`.

**Decisões condicionais examinadas:**
- `(r.cancellationRequestStatus==='REQUESTED')`
- `r.cancellationRequestNote`
- `r.status==='CONFIRMED'`
- `r.status==='IN_SERVICE'`
- `action`
- `progress||cancel`

**Saída declarada:**
- `return \`<div class="alert-strip warn"><span>◷</span><span>Cliente solicitou cancelamento${r.cancellationRequestNote?\`: ${esc(r.cancellationRequestNote)}\`:''}. Ao aceitar, qualquer pagamento online será estornado integralmente.</span></div><div class="action-row"><button class="btn danger small" data-reservation-cancellation-decision="${esc(r.id)}" data-accepted="true">Aceitar cancelamento</button><button class="btn outline small" data-reservation-cancellation-decision="${esc(r.id)}" data-accepted="false">Recusar solicitação</button></div>\`;`
- `return progress||cancel?\`<div class="action-row">${progress}${cancel}</div>\`:'—';`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderCourier — linha 1026

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1026)

```javascript
function renderCourier()
```

Executa a etapa `render Courier` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.offers.map`; `esc`; `String`; `money`; `emptyState`; `state.deliveries.map`; `['DELIVERED','CANCELLED'].includes`; `pill`; `courierNext`; `chatPanel`.

**Decisões condicionais examinadas:**
- `state.offers.length`
- `state.deliveries.length`
- `['DELIVERED','CANCELLED'].includes(d.status)`
- `d.customerNote`
- `d.status==='ASSIGNED'`
- `d.status==='PICKED_UP'||d.status==='IN_TRANSIT'||d.status==='DELIVERED'`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Área do entregador</span><h1>Suas entregas</h1><p>Escolha uma oferta e acompanhe o trajeto até a conclusão.</p></div><button class="btn light" data-action="reload-courier">↻ Atualizar</button></div> <div class="section-head"><h2>Ofertas disponíveis</h2><span class="store-meta">${state.offers.length} aguardando aceite</span></div> ${state.offers.length?\`<div class="store-grid">${state.offers.map(o=>\`<article class="panel"><span class="eyebrow">Nova entrega</span><h3 style="margin-top:8px">${esc(o.storeName)}</h3><p class="store-desc">Pedido ${esc(String(o.orderId).slice(0,8).toUpperCase())} · valor ${money(o.orderTotalCents)}</p><div class="alert-strip info">⌖ Endereço e dados do cliente ficam visíveis depois que você aceitar.</div><button class="btn block" data-accept-delivery="${esc(o.deliveryId)}">Aceitar entrega</button></article>\`).join('')}</div>\`:emptyState('🚲','Nenhuma oferta por enquanto','Fique de olho. As novas entregas aparecerão nesta tela.')} <div class="section-head"><h2>Entregas e histórico</h2></div> ${state.deliveries.length?state.deliveries.map(d=>\`<article class="order-card" data-delivery-card="${esc(d.deliveryId)}"><div class="order-card-top"><div><h3>${esc(d.storeName)}</h3><p>Valor do pedido: ${money(d.orderTotalCents)} · Não representa remuneração da entrega.</p><p>${['DELIVERED','CANCELLED'].includes(d.status)?'Operação encerrada. Endereço e observação do cliente ficam ocultos.':\`Retirar na loja e entregar em: ${esc(d.deliveryAddress||'Endereço não informado')}\`}</p></div>${pill(d.status)}</div>${d.customerNote?\`<div class="customer-note"><strong>Observação do cliente</strong><p>${esc(d.customerNote)}</p></div>\`:''}<div class="order-bottom"><span>Pedido ${esc(String(d.orderId).slice(0,8).toUpperCase())}</span><span>${courierNext(d)}</span></div>${d.status==='ASSIGNED'?\`<div class="action-row"><button class="btn outline small" data-open-chat-type="deliveries" data-open-chat-id="${esc(d.deliveryId)}">Conversar com cliente</button></div>\`:d.status==='PICKED_UP'||d.status==='IN_TRANSIT'||d.status==='DELIVERED'?\`<div class="action-row"><button class="btn outline small" data-open-chat-type="deliveries" data-open-chat-id="${esc(d.deliveryId)}">Ver histórico do chat</button></div>\`:''}${chatPanel('deliveries',d.deliveryId)}</article>\`).join(''):''}\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1029:71 — linha 1029

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1029)

```javascript
o=>
```

Executa a etapa `callback@1029:71` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `String`; `money`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1031:52 — linha 1031

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1031)

```javascript
d=>
```

Executa a etapa `callback@1031:52` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `money`; `['DELIVERED','CANCELLED'].includes`; `pill`; `String`; `courierNext`; `chatPanel`.

**Decisões condicionais examinadas:**
- `['DELIVERED','CANCELLED'].includes(d.status)`
- `d.customerNote`
- `d.status==='ASSIGNED'`
- `d.status==='PICKED_UP'||d.status==='IN_TRANSIT'||d.status==='DELIVERED'`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### courierNext — linha 1034

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1034)

```javascript
function courierNext(d)
```

Executa a etapa `courier Next` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `String`.

**Decisões condicionais examinadas:**
- `action`
- `d.status==='IN_TRANSIT'`
- `state.sharingDeliveryId===String(d.deliveryId)`

**Saída declarada:**
- `return \`<div class="action-row">${action?\`<button class="btn small" data-delivery-status="${action[0]}" data-id="${esc(d.deliveryId)}">${action[1]}</button>\`:''}${d.status==='IN_TRANSIT'?\`<button class="btn outline small" data-share-location="${esc(d.deliveryId)}">${state.sharingDeliveryId===String(d.deliveryId)?'Localização compartilhada':'Compartilhar localização'}</button>\`:''}</div>\`;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderAdmin — linha 1036

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1036)

```javascript
function renderAdmin()
```

Executa a etapa `render Admin` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.createElement`; `renderAdminAll`; `Object.fromEntries`; `adminAreaNames.map`; `[...source.content.querySelectorAll`; `source.content.querySelectorAll`; `state.adminTickets.filter`; `state.adminPlatformRequests.filter`; `state.adminStores.filter`; `source.content.querySelector`; `headerNode.querySelector`; `[['profiles','Perfis operacionais'],['accounts','Contas e segurança'],['create','Criar acesso']].map`; `renderAdminTeamArea`; `renderAdminAreas`; `esc`; `sections.map`.

**Decisões condicionais examinadas:**
- `state.adminTickets.filter(t=>t.status!=='CLOSED').length`
- `selected==='team'`
- `(heading&&headerNode)`
- `state.adminTeamSection===id`
- `selected==='covers'`
- `state.adminLoadError`
- `selected===id`

**Saída declarada:**
- `return \`${header}${errorMessage}<nav class="section-tabs" aria-label="Áreas administrativas">${sections.map(([id,label])=>\`<button class="section-tab ${selected===id?'active':''}" data-section="${id}" aria-current="${selected===id?'page':'false'}">${label}</button>\`).join('')}</nav><div class="section-view">${body}</div>\`;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const source=document.createElement('template');source.innerHTML=renderAdminAll();
if(heading&&headerNode){headerNode.querySelector('h1').textContent=heading[0];headerNode.querySelector('p').textContent=heading[1];}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1038:56 — linha 1038

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1038)

```javascript
name=>
```

Executa a etapa `callback@1038:56` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `[...source.content.querySelectorAll`; `source.content.querySelectorAll`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1038:140 — linha 1038

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1038)

```javascript
node=>
```

Executa a etapa `callback@1038:140` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1040:95 — linha 1040

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1040)

```javascript
t=>
```

Executa a etapa `callback@1040:95` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1040:159 — linha 1040

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1040)

```javascript
t=>
```

Executa a etapa `callback@1040:159` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1040:266 — linha 1040

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1040)

```javascript
r=>
```

Executa a etapa `callback@1040:266` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1041:189 — linha 1041

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1041)

```javascript
r=>
```

Executa a etapa `callback@1041:189` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1041:438 — linha 1041

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1041)

```javascript
store=>
```

Executa a etapa `callback@1041:438` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1041:663 — linha 1041

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1041)

```javascript
t=>
```

Executa a etapa `callback@1041:663` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1048:214 — linha 1048

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1048)

```javascript
([id,label])=>
```

Executa a etapa `callback@1048:214` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `state.adminTeamSection===id`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1051:112 — linha 1051

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1051)

```javascript
([id,label])=>
```

Executa a etapa `callback@1051:112` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `selected===id`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderAdminAll — linha 1054

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1054)

```javascript
function renderAdminAll()
```

Executa a etapa `render Admin All` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.adminStores.filter`; `filterAdminStores`; `state.adminPlatformRequests.filter`; `pendingRequests.filter`; `pendingRequests.map`; `esc`; `shortDate`; `pill`; `emptyState`; `state.adminApplications.map`; `state.adminOperators.map`; `state.adminRefunds.map`; `String`; `money`; `storeFilters.map`; `visibleStores.map`; `adminStoreStatus`; `Number`; `state.adminAccounts.map`; ``; `state.adminTickets.map`.

**Decisões condicionais examinadas:**
- `state.adminLoadError`
- `pendingRequests.length`
- `r.requestType==='ROLE'`
- `r.requestType==='CITY'`
- `r.requestType==='STORE'`
- `state.adminApplications.length`
- `operator.role==='MERCHANT'`
- `state.adminPlatformRequests.filter(r=>r.status!=='PENDING').length`
- `r.reviewNote`
- `r.status==='APPROVED'`
- `state.adminOperators.length`
- `operator.active`
- `state.adminRefunds.length`
- `r.operationType==='ORDER'`
- `r.providerPaymentId`
- `r.attentionMessage`
- `r.lastAttemptAt`
- `state.adminStoreFilter===key`
- `key==='ALL'`
- `visibleStores.length`
- `!s.approved&&s.active`
- `s.active`
- `state.adminStores.length`
- `state.adminTemporaryPassword`
- `state.adminAccounts.length`
- `a.active`
- `a.role!=='ADMIN'`
- `enabled`
- `state.adminTickets.length`
- `t.status==='OPEN'`
- `t.status!=='CLOSED'`

**Saída declarada:**
- `return \`<div class="page-head"><div><span class="eyebrow">Administração da plataforma</span><h1>Visão geral</h1><p>Controle solicitações, vendedores, entregadores, lojas e pagamentos.</p></div><button class="btn light" data-action="reload-admin">↻ Atualizar dados</button></div> ${state.adminLoadError?'<div class="alert-strip warn admin-load-error"><span>!</span><span>Falha temporária ao carregar informações do servidor.</span></div>':''} <div class="stat-grid" data-admin-area="stats"><button class="stat-card stat-card-action" data-section="stores"><small>Lojas cadastradas</small><strong>${state.adminStores.length}</strong><span>${pendingStores.length} aguardando aprovação</span><span class="stat-action-hint">Ver lojas e analisar →</span></button><button class="stat-card stat-card-action" data-section="applications"><small>Solicitações de acesso</small><strong>${pendingOperators}</strong><span>Vendedores e entregadores</span><span class="stat-action-hint">Revisar solicitações →</span></button><button class="stat-card stat-card-action" data-section="team"><small>Contas operacionais</small><strong>${state.adminOperators.length}</strong><span>Comerciantes e entregadores</span><span class="stat-action-hint">Gerenciar contas →</span></button></div> <section class="panel" data-admin-area="applications"><div class="section-head"><div><h3>Solicitações da plataforma</h3><p class="store-desc">Analise novos perfis, cidades e lojas adicionais antes de liberar cada recurso.</p></div></div> ${pendingRequests.length?pendingRequests.map(r=>{const title=r.requestType==='ROLE'?\`Novo perfil: ${roleNames[r.requestedRole]||r.requestedRole}\`:r.requestType==='CITY'?\`Atender em ${r.city}/${r.state}\`:\`Nova loja: ${r.storeName||'Estabelecimento'}\`;const detail=r.requestType==='STORE'?\`CNPJ ${r.storeCnpj||'—'} · \`:'';return \`<article class="order-card"><div class="order-card-top"><div><h3>${esc(title)}</h3><p>${esc(r.accountName)} · ${esc(r.accountEmail)}<br/>${detail}${shortDate(r.createdAt)}</p></div>${pill('PENDING_REVIEW')}</div><div class="action-row"><button class="btn small" data-platform-review="${esc(r.id)}" data-decision="true">Aprovar</button><button class="btn outline small" data-platform-review="${esc(r.id)}" data-decision="false">Reprovar</button></div></article>\`}).join(''):emptyState('✓','Nenhuma solicitação pendente','Novos perfis, cidades e lojas serão exibidos aqui.')} ${state.adminApplications.length?\`<h3>Cadastros antigos</h3>${state.adminApplications.map(operator=>\`<article class="order-card"><div class="order-card-top"><div><h3>${esc(operator.name)}</h3><p>${esc(operator.email)} · ${operator.role==='MERCHANT'?'Vendedor':'Entregador'}<br/>Solicitado em ${shortDate(operator.createdAt)}</p></div>${pill('PENDING_REVIEW')}</div><div class="action-row"><button class="btn small" data-operator-application="${esc(operator.id)}" data-decision="approve">Aprovar acesso</button><button class="btn outline small" data-operator-application="${esc(operator.id)}" data-decision="reject">Recusar</button></div></article>\`).join('')}\`:''} </section> <section class="panel" data-admin-area="application-history" style="margin-top:16px"><h3>Histórico das solicitações</h3>${state.adminPlatformRequests.filter(r=>r.status!=='PENDING').length?state.adminPlatformRequests.filter(r=>r.status!=='PENDING').map(r=>\`<article class="saved-address"><div><strong>${esc(r.requestType==='ROLE'?\`Perfil ${roleNames[r.requestedRole]||r.requestedRole}\`:r.requestType==='CITY'?\`${r.city}/${r.state}\`:\`Loja ${r.storeName||''}\`)}</strong><p>${esc(r.accountName)} · ${esc(r.accountEmail)}${r.reviewNote?' · '+esc(r.reviewNote):''}</p></div>${pill(r.status==='APPROVED'?'CONFIRMED':'REJECTED')}</article>\`).join(''):'<p class="store-desc">As decisões tomadas aparecerão neste histórico.</p>'}</section> <section class="panel" data-admin-area="operator-profiles" style="margin-top:18px"><div class="section-head"><div><h3>Perfis operacionais</h3><p class="store-desc">Cada pessoa usa uma única conta. Consulte e altere acessos individuais na seção Contas e segurança, sem suspender por engano os outros perfis vinculados.</p></div><button class="btn outline small" data-admin-team-section="accounts">Abrir contas e segurança →</button></div>${state.adminOperators.length?\`<div class="account-admin-list">${state.adminOperators.map(operator=>\`<article class="saved-address"><div><strong>${esc(operator.name)}</strong><p>${esc(operator.email)} · ${operator.role==='MERCHANT'?'Vendedor':'Entregador'} · ${operator.active?'Conta ativa':'Conta suspensa'}</p></div>${pill(operator.active?'CONFIRMED':'CANCELLED')}</article>\`).join('')}</div>\`:emptyState('♙','Nenhuma conta operacional','Os perfis aprovados aparecerão aqui.')}</section> <section class="panel admin-refunds" data-admin-area="refunds" style="margin-top:18px"><div class="section-head"><div><h3>Estornos para acompanhar</h3><p class="store-desc">Casos em análise precisam de verificação no Mercado Pago.</p></div></div> ${state.adminRefunds.length?state.adminRefunds.map(r=>\`<article class="order-card"><div class="order-card-top"><div><h3>${esc(r.storeName)} · ${r.operationType==='ORDER'?'Pedido':'Reserva'}</h3><p>${esc(String(r.operationId).slice(0,8).toUpperCase())} · Pagamento ${money(r.amountCents)}<br/>Tentativas: ${r.attempts}${r.providerPaymentId?' · Mercado Pago: '+esc(r.providerPaymentId):''}</p></div>${pill(r.status)}</div>${r.attentionMessage?\`<p class="warning-small">${esc(r.attentionMessage)}</p>\`:''}${r.lastAttemptAt?\`<small class="store-meta">Última tentativa: ${shortDate(r.lastAttemptAt)}</small>\`:''}</article>\`).join(''):emptyState('✓','Nenhum estorno pendente','Solicitações pendentes ou que precisem de análise aparecerão aqui.')}</section> <section class="panel" data-admin-area="stores" style="margin-top:18px"><div class="section-head"><div><h3>Revisar estabelecimentos</h3><p class="store-desc">A aprovação administrativa publica a loja. Não existe uma etapa de pagamento para aprovar o cadastro.</p></div><span class="store-meta">${state.adminStores.length} lojas</span></div><nav class="section-tabs compact-tabs" aria-label="Filtrar lojas por situação">${storeFilters.map(([key,label])=>\`<button class="section-tab ${state.adminStoreFilter===key?'active':''}" data-store-filter="${key}" aria-pressed="${state.adminStoreFilter===key}">${label}${key==='ALL'?'':\` · ${filterAdminStores(state.adminStores,key).length}\`}</button>\`).join('')}</nav>${visibleStores.length?visibleStores.map(s=>\`<article class="order-card"><div class="order-card-top"><div><h3>${esc(s.name)}</h3><p>${esc(s.category)} · ${esc(s.address)}<br/>Responsável: ${esc(s.ownerName)} · ${esc(s.ownerEmail)}</p></div>${pill(adminStoreStatus(s))}</div><button type="button" class="btn outline small" data-admin-edit-store="${esc(s.id)}">Editar dados da loja</button><form class="commission-form" data-store-commission="${esc(s.id)}"><label>Comissão do marketplace (%)</label><input name="percent" type="number" min="0" max="50" step="0.01" value="${Number(s.commissionPercent||0).toFixed(2)}" required /><button class="btn outline small" type="submit">Salvar comissão</button></form><div class="action-row">${!s.approved&&s.active?\`<button class="btn small" data-approve-store="${esc(s.id)}">Aprovar loja</button>\`:''}${s.active?\`<button class="btn outline small" data-store-active="${esc(s.id)}" data-active="false">Suspender loja</button>\`:\`<button class="btn small" data-store-active="${esc(s.id)}" data-active="true">Reativar loja</button>\`}</div></article>\`).join(''):emptyState('🏪',state.adminStores.length?'Nenhuma loja nesta situação':'Nenhuma loja cadastrada',state.adminStores.length?'Escolha outro filtro para ver os demais estabelecimentos.':'Quando vendedores enviarem seus perfis, você poderá revisá-los aqui.')}</section> <section class="panel" data-admin-area="accounts" style="margin-top:18px"><div class="section-head"><div><h3>Contas e segurança</h3><p class="store-desc">Ative ou suspenda perfis. Ao suspender o perfil profissional, a área de cliente continua disponível.</p></div></div>${state.adminTemporaryPassword?\`<div class="alert-strip warn"><span>!</span><span>Senha temporária de ${esc(state.adminTemporaryPassword.name)}: <strong>${esc(state.adminTemporaryPassword.password)}</strong> · informe por um canal seguro. Será solicitada a troca no próximo acesso. <button type="button" class="text-button" data-dismiss-temp-password>Ocultar</button></span></div>\`:''}${state.adminAccounts.length?\`<div class="account-admin-list">${state.adminAccounts.map(a=>\`<article class="order-card"><div class="order-card-top"><div><h3>${esc(a.name)}</h3><p>${esc(a.email)} · CPF ${esc(a.maskedCpf)}<br/>Perfis: ${esc((a.registeredRoles||[]).map(r=>roleNames[r]||r).join(', ')||roleNames[a.role])}</p></div>${pill(a.active?'CONFIRMED':'CANCELLED')}</div><details class="admin-action-menu"><summary aria-label="Mais ações para ${esc(a.name)}" title="Mais ações">⋯</summary><div class="admin-action-menu-panel"><button class="btn outline small" data-admin-edit-account="${esc(a.id)}">Editar dados</button>${a.role!=='ADMIN'?\`<button class="btn outline small" data-account-active="${esc(a.id)}" data-active="${!a.active}">${a.active?'Suspender conta':'Reativar conta'}</button>${a.active?(a.registeredRoles||[]).map(r=>{const enabled=(a.grantedRoles||[]).includes(r);return \`<button class="btn outline small" data-account-role="${esc(a.id)}" data-role="${r}" data-active="${!enabled}">${enabled?'Suspender':'Ativar'} ${esc(roleNames[r]||r)}</button>\`}).join(''):''}\`:'<span class="store-meta">Perfis administrativos protegidos contra suspensão</span>'}<button class="btn light small" data-reset-password="${esc(a.id)}" data-name="${esc(a.name)}">Redefinir senha</button></div></details></article>\`).join('')}</div>\`:emptyState('♙','Nenhuma conta cadastrada','As contas de clientes e operadores aparecerão aqui.')}</section> <form class="panel" data-admin-area="create-operator" style="margin-top:18px" data-create-operator><h3>Criar acesso de vendedor ou entregador</h3><p class="store-desc">O novo perfil será vinculado à conta criada para essa pessoa.</p><div class="form-grid"><div class="field"><label>Nome</label><input name="name" required maxlength="120" /></div><div class="field"><label>E-mail</label><input name="email" type="email" required /></div><div class="field"><label>CPF</label><input name="cpf" data-mask="cpf" maxlength="14" required inputmode="numeric" placeholder="000.000.000-00" /></div><div class="field"><label>Senha inicial</label><input name="password" type="password" required minlength="12" maxlength="72" /></div><div class="field"><label>Perfil</label><select name="role"><option value="MERCHANT">Vendedor</option><option value="COURIER">Entregador</option></select></div></div><button class="btn" type="submit">Criar acesso</button></form><section class="panel admin-ticket-panel" data-admin-area="tickets" style="margin-top:18px"><h3>Chamados de atendimento</h3>${state.adminTickets.length?state.adminTickets.map(t=>\`<article class="order-card"><div class="order-card-top"><div><h3>${esc(t.subject)}</h3><p>${esc(t.accountName)} · ${esc(t.accountEmail)}<br/>${esc(t.lastMessage||'Sem mensagens')}</p></div>${pill(t.status)}</div><div class="action-row"><button class="btn outline small" data-ticket-open="${esc(t.id)}" data-ticket-admin="true">Abrir chamado</button>${t.status==='OPEN'?\`<button class="btn light small" data-ticket-status="${esc(t.id)}" data-status="IN_PROGRESS">Assumir</button>\`:''}${t.status!=='CLOSED'?\`<button class="btn outline small" data-ticket-status="${esc(t.id)}" data-status="CLOSED">Encerrar</button>\`:\`<button class="btn light small" data-ticket-status="${esc(t.id)}" data-status="OPEN">Reabrir</button>\`}</div></article>\`).join(''):emptyState('✉','Nenhum chamado aberto','Quando alguém falar com o atendimento, a conversa aparecerá aqui.')}</section>\`;`
- `return \`<article class="order-card"><div class="order-card-top"><div><h3>${esc(title)}</h3><p>${esc(r.accountName)} · ${esc(r.accountEmail)}<br/>${detail}${shortDate(r.createdAt)}</p></div>${pill('PENDING_REVIEW')}</div><div class="action-row"><button class="btn small" data-platform-review="${esc(r.id)}" data-decision="true">Aprovar</button><button class="btn outline small" data-platform-review="${esc(r.id)}" data-decision="false">Reprovar</button></div></article>\``
- `return \`<button class="btn outline small" data-account-role="${esc(a.id)}" data-role="${r}" data-active="${!enabled}">${enabled?'Suspender':'Ativar'} ${esc(roleNames[r]||r)}</button>\``

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1055:48 — linha 1055

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1055)

```javascript
store=>
```

Executa a etapa `callback@1055:48` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1058:60 — linha 1058

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1058)

```javascript
r=>
```

Executa a etapa `callback@1058:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1059:49 — linha 1059

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1059)

```javascript
r=>
```

Executa a etapa `callback@1059:49` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1064:52 — linha 1064

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1064)

```javascript
r=>
```

Executa a etapa `callback@1064:52` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `shortDate`; `pill`.

**Decisões condicionais examinadas:**
- `r.requestType==='ROLE'`
- `r.requestType==='CITY'`
- `r.requestType==='STORE'`

**Saída declarada:**
- `return \`<article class="order-card"><div class="order-card-top"><div><h3>${esc(title)}</h3><p>${esc(r.accountName)} · ${esc(r.accountEmail)}<br/>${detail}${shortDate(r.createdAt)}</p></div>${pill('PENDING_REVIEW')}</div><div class="action-row"><button class="btn small" data-platform-review="${esc(r.id)}" data-decision="true">Aprovar</button><button class="btn outline small" data-platform-review="${esc(r.id)}" data-decision="false">Reprovar</button></div></article>\``

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1065:97 — linha 1065

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1065)

```javascript
operator=>
```

Executa a etapa `callback@1065:97` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `shortDate`; `pill`.

**Decisões condicionais examinadas:**
- `operator.role==='MERCHANT'`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1067:165 — linha 1067

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1067)

```javascript
r=>
```

Executa a etapa `callback@1067:165` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1067:232 — linha 1067

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1067)

```javascript
r=>
```

Executa a etapa `callback@1067:232` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1067:261 — linha 1067

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1067)

```javascript
r=>
```

Executa a etapa `callback@1067:261` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `pill`.

**Decisões condicionais examinadas:**
- `r.requestType==='ROLE'`
- `r.requestType==='CITY'`
- `r.reviewNote`
- `r.status==='APPROVED'`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1069:539 — linha 1069

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1069)

```javascript
operator=>
```

Executa a etapa `callback@1069:539` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `pill`.

**Decisões condicionais examinadas:**
- `operator.role==='MERCHANT'`
- `operator.active`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1071:58 — linha 1071

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1071)

```javascript
r=>
```

Executa a etapa `callback@1071:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `String`; `money`; `pill`; `shortDate`.

**Decisões condicionais examinadas:**
- `r.operationType==='ORDER'`
- `r.providerPaymentId`
- `r.attentionMessage`
- `r.lastAttemptAt`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1072:448 — linha 1072

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1072)

```javascript
([key,label])=>
```

Executa a etapa `callback@1072:448` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `filterAdminStores`.

**Decisões condicionais examinadas:**
- `state.adminStoreFilter===key`
- `key==='ALL'`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1072:760 — linha 1072

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1072)

```javascript
s=>
```

Executa a etapa `callback@1072:760` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `pill`; `adminStoreStatus`; `Number`.

**Decisões condicionais examinadas:**
- `!s.approved&&s.active`
- `s.active`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1073:755 — linha 1073

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1073)

```javascript
a=>
```

Executa a etapa `callback@1073:755` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; ``; `pill`.

**Decisões condicionais examinadas:**
- `a.active`
- `a.role!=='ADMIN'`
- `enabled`

**Saída declarada:**
- `return \`<button class="btn outline small" data-account-role="${esc(a.id)}" data-role="${r}" data-active="${!enabled}">${enabled?'Suspender':'Ativar'} ${esc(roleNames[r]||r)}</button>\``

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1073:935 — linha 1073

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1073)

```javascript
r=>
```

Executa a etapa `callback@1073:935` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1073:1508 — linha 1073

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1073)

```javascript
r=>
```

Executa a etapa `callback@1073:1508` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** ``; `esc`.

**Decisões condicionais examinadas:**
- `enabled`

**Saída declarada:**
- `return \`<button class="btn outline small" data-account-role="${esc(a.id)}" data-role="${r}" data-active="${!enabled}">${enabled?'Suspender':'Ativar'} ${esc(roleNames[r]||r)}</button>\``

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1074:1131 — linha 1074

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1074)

```javascript
t=>
```

Executa a etapa `callback@1074:1131` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `pill`.

**Decisões condicionais examinadas:**
- `t.status==='OPEN'`
- `t.status!=='CLOSED'`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createStore — linha 1077

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1077)

```javascript
async function createStore(event)
```

Executa a etapa `create Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `parseCurrencyDecimal`; `form.get`; `Number.isFinite`; `notify`; `coordinatesFromForm`; `String`; `Math.round`; `fulfillmentPayload`; `api`; `JSON.stringify`; `render`.

**Decisões condicionais examinadas:**
- `(!Number.isFinite(fee)||fee<0||fee>1000)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function createStore(event){event.preventDefault();const form=new FormData(event.currentTarget);const fee=parseCurrencyDecimal(form.get('deliveryFee'));if(!Number.isFinite(fee)||fee<0||fee>1000){notify('Confira a taxa de entrega','Informe um valor entre R$ 0,00 e R$ 1.000,00.','info');return;}try{const coordinates=coordinatesFromForm(form);const body={name:String(form.get('name')).trim(),cnpj:String(form.get('cnpj')||'').replace(/\D/g,''),description:String(form.get('description')||'').trim(),address:String(form.get('address')).trim(),city:String(form.get('city')).trim(),state:String(form.get('state')).trim().toUpperCase(),category:String(form.get('category')).trim(),active:true,deliveryFeeCents:Math.round(fee*100),timezone:String(form.get('timezone')||'America/Sao_Paulo'),...coordinates,...fulfillmentPayload(form)};const created=await api('/merchant/stores',{method:'POST',body:JSON.stringify(body)});state.ownStores=await api('/merchant/stores/mine');state.merchantStoreId=created.id;state.platformRequests=await api('/account/platform-requests');notify('Loja enviada para revisão','O administrador analisará os dados da loja.');render();}catch(e){notify('Não conseguimos cadastrar a loja',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### resubmitStore — linha 1078

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1078)

```javascript
async function resubmitStore(id)
```

Executa a etapa `resubmit Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `notify`; `render`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function resubmitStore(id){try{await api(`/merchant/stores/${id}/resubmit`,{method:'POST'});state.platformRequests=await api('/account/platform-requests');notify('Loja reenviada','A administração receberá uma nova solicitação para análise.');render();}catch(e){notify('Não foi possível reenviar a loja',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateStore — linha 1081

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1081)

```javascript
async function updateStore(event)
```

Executa a etapa `update Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `activeMerchantStore`; `parseCurrencyDecimal`; `form.get`; `Number.isFinite`; `notify`; `coordinatesFromForm`; `String`; `Math.round`; `fulfillmentPayload`; `api`; `JSON.stringify`; `render`.

**Decisões condicionais examinadas:**
- `(!store)`
- `(!Number.isFinite(fee)||fee<0||fee>1000)`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function updateStore(event){event.preventDefault();const form=new FormData(event.currentTarget);const store=activeMerchantStore();if(!store)return;const fee=parseCurrencyDecimal(form.get('deliveryFee'));if(!Number.isFinite(fee)||fee<0||fee>1000){notify('Confira a taxa de entrega','Informe um valor entre R$ 0,00 e R$ 1.000,00.','info');return;}try{const coordinates=coordinatesFromForm(form);const body={cnpj:store.cnpj,name:String(form.get('name')).trim(),description:String(form.get('description')||'').trim(),address:String(form.get('address')).trim(),city:String(form.get('city')).trim(),state:String(form.get('state')).trim().toUpperCase(),category:String(form.get('category')).trim(),active:store.active,deliveryFeeCents:Math.round(fee*100),timezone:String(form.get('timezone')||store.timezone||'America/Sao_Paulo'),...coordinates,...fulfillmentPayload(form)};await api(`/merchant/stores/${store.id}`,{method:'PUT',body:JSON.stringify(body)});state.ownStores=await api('/merchant/stores/mine');notify('Perfil atualizado','As informações e a localização da loja foram salvas.');render();}catch(e){notify('Não conseguimos atualizar a loja',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### syncCatalogItemFields — linha 1083

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1083)

```javascript
function syncCatalogItemFields(form)
```

Executa a etapa `sync Catalog Item Fields` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form.querySelector`; `stock?.querySelector`; `duration?.querySelector`; `syncCatalogItemRisk`.

**Decisões condicionais examinadas:**
- `(!form)`
- `(stock)`
- `(stockInput)`
- `(duration)`
- `(durationInput)`
- `(payment)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### syncCatalogItemRisk — linha 1084

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1084)

```javascript
function syncCatalogItemRisk(form)
```

Executa a etapa `sync Catalog Item Risk` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form.querySelector`.

**Decisões condicionais examinadas:**
- `(!form)`
- `(warning)`
- `(ack)`
- `(checkbox)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createItem — linha 1085

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1085)

```javascript
async function createItem(event)
```

Executa a etapa `create Item` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `activeMerchantStore`; `parseCurrencyDecimal`; `form.get`; `Number.isFinite`; `notify`; `Number`; `Number.isInteger`; `String`; `Math.round`; `api`; `JSON.stringify`; `render`.

**Decisões condicionais examinadas:**
- `(!store)`
- `(!Number.isFinite(price)||price<=0)`
- `serviceItem`
- `(serviceItem&&(!Number.isInteger(appointmentDurationMinutes)||appointmentDurationMinutes<5||appointmentDurationMinutes>480))`
- `(payAfterService&&form.get('acceptNoShowRisk')!=='on')`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function createItem(event){event.preventDefault();const store=activeMerchantStore();if(!store)return;const form=new FormData(event.currentTarget);const price=parseCurrencyDecimal(form.get('price'));if(!Number.isFinite(price)||price<=0){notify('Confira o preço','Informe um valor maior que zero.','info');return;}const serviceItem=form.get('serviceItem')==='true',payAfterService=serviceItem&&form.get('payAfterService')==='true',appointmentDurationMinutes=serviceItem?Number(form.get('appointmentDurationMinutes')):30;if(serviceItem&&(!Number.isInteger(appointmentDurationMinutes)||appointmentDurationMinutes<5||appointmentDurationMinutes>480)){notify('Confira a duração','Escolha de 5 minutos a 8 horas.','info');return;}if(payAfterService&&form.get('acceptNoShowRisk')!=='on'){notify('Confirme que entendeu o risco','Marque a confirmação para oferecer pagamento após o atendimento.','info');return;}const body={name:String(form.get('name')).trim(),description:String(form.get('description')||'').trim(),priceCents:Math.round(price*100),stock:serviceItem?0:Number(form.get('stock')),serviceItem,payAfterService,appointmentDurationMinutes,active:true};try{await api(`/merchant/stores/${store.id}/items`,{method:'POST',body:JSON.stringify(body)});state.storeItems=await api(`/merchant/stores/${store.id}/items`);notify('Item adicionado','O catálogo da sua loja foi atualizado.');render();}catch(e){notify('Não conseguimos adicionar o item',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setCatalogItemActive — linha 1087

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1087)

```javascript
async function setCatalogItemActive(id,active)
```

Executa a etapa `set Catalog Item Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `activeMerchantStore`; `state.storeItems.find`; `String`; `runOnce`; `document.querySelector`; `CSS.escape`; `api`; `encodeURIComponent`; `JSON.stringify`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `(!store)`
- `(!item)`
- `active`
- `(button)`
- `(active)`
- `(button?.isConnected)`

**Saída declarada:**
- `return;`
- `return;`
- `return runOnce(\`catalog:${id}:${active?'activate':'remove'}\`,async()=>{ const button=document.querySelector(\`[data-item-${active?'activate':'deactivate'}="${CSS.escape(String(id))}"]\`); if(button){button.disabled=true;button.textContent=active?'Reativando…':'Removendo…';} try{ if(active){ await api(\`/merchant/items/${encodeURIComponent(id)}\`,{method:'PUT',body:JSON.stringify({name:item.name,description:item.description||'',priceCents:item.priceCents,stock:item.stock,active:true,serviceItem:item.serviceItem,payAfterService:item.payAfterService,appointmentDurationMinutes:item.appointmentDurationMinutes||30})}); }else{ await api(\`/merchant/items/${encodeURIComponent(id)}\`,{method:'DELETE'}); } state.storeItems=await api(\`/merchant/stores/${encodeURIComponent(store.id)}/items\`); notify(active?'Item reativado':'Item removido do catálogo',active?'Ele voltou a ficar disponível para os clientes.':'O histórico foi preservado; o item deixou de aparecer para clientes.','success'); renderPage(); }catch(error){ notify(active?'Não foi possível reativar o item':'Não foi possível remover o item',error.message,'error'); if(button?.isConnected){button.disabled=false;button.textContent=active?'Reativar':'Remover';} } });`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if(button){button.disabled=true;button.textContent=active?'Reativando…':'Removendo…';}
await api(`/merchant/items/${encodeURIComponent(id)}`,{method:'PUT',body:JSON.stringify({name:item.name,description:item.description||'',priceCents:item.priceCents,stock:item.stock,active:true,serviceItem:item.serviceItem,payAfterService:item.payAfterService,appointmentDurationMinutes:item.appointmentDurationMinutes||30})});
await api(`/merchant/items/${encodeURIComponent(id)}`,{method:'DELETE'});
state.storeItems=await api(`/merchant/stores/${encodeURIComponent(store.id)}/items`);
if(button?.isConnected){button.disabled=false;button.textContent=active?'Reativar':'Remover';}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1090:36 — linha 1090

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1090)

```javascript
row=>
```

Executa a etapa `callback@1090:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1092:64 — linha 1092

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1092)

```javascript
async()=>
```

Executa a etapa `callback@1092:64` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.querySelector`; `CSS.escape`; `String`; `api`; `encodeURIComponent`; `JSON.stringify`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `active`
- `(button)`
- `(active)`
- `(button?.isConnected)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if(button){button.disabled=true;button.textContent=active?'Reativando…':'Removendo…';}
await api(`/merchant/items/${encodeURIComponent(id)}`,{method:'PUT',body:JSON.stringify({name:item.name,description:item.description||'',priceCents:item.priceCents,stock:item.stock,active:true,serviceItem:item.serviceItem,payAfterService:item.payAfterService,appointmentDurationMinutes:item.appointmentDurationMinutes||30})});
await api(`/merchant/items/${encodeURIComponent(id)}`,{method:'DELETE'});
state.storeItems=await api(`/merchant/stores/${encodeURIComponent(store.id)}/items`);
if(button?.isConnected){button.disabled=false;button.textContent=active?'Reativar':'Remover';}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### openCatalogItemEditor — linha 1111

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1111)

```javascript
function openCatalogItemEditor(id)
```

Executa a etapa `open Catalog Item Editor` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.storeItems.find`; `String`; `$`; `document.createElement`; `esc`; `formatCurrencyValue`; `document.body.append`; `wrap.querySelectorAll`; `button.addEventListener`; `wrap.remove`; `wrap.addEventListener`; `wrap.querySelector`; `form.addEventListener`; `form.querySelector`; `formatCurrencyMask`; `syncCatalogItemFields`; `syncCatalogItemRisk`.

**Decisões condicionais examinadas:**
- `(!item)`
- `item.serviceItem`
- `item.payAfterService`
- `item.serviceItem&&item.payAfterService`
- `(event.target===wrap)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
wrap.innerHTML=`<section class="modal catalog-edit-modal" role="dialog" aria-modal="true" aria-labelledby="catalog-item-title"><div class="modal-head"><div><span class="eyebrow">Editar catálogo</span><h2 id="catalog-item-title">${esc(item.name)}</h2><p>Altere o produto ou transforme este item em um serviço agendado.</p></div><button class="close-button" type="button" data-close-item aria-label="Fechar">×</button></div><form class="form-grid catalog-edit-form" data-catalog-item-update="${esc(item.id)}"><div class="field span-2"><label for="edit-item-name">Nome</label><input id="edit-item-name" name="name" required maxlength="140" value="${esc(item.name)}"/></div><div class="field span-2"><label for="edit-item-description">Descrição</label><textarea id="edit-item-description" name="description" maxlength="1000">${esc(item.description||'')}</textarea></div><div class="field"><label for="edit-item-price">Preço</label><input id="edit-item-price" name="price" data-mask="currency" inputmode="numeric" required value="${esc(formatCurrencyValue(item.priceCents/100))}"/></div><div class="field"><label for="edit-item-kind">Tipo</label><select id="edit-item-kind" name="serviceItem" data-item-kind><option value="false" ${item.serviceItem?'':'selected'}>Produto à venda</option><option value="true" ${item.serviceItem?'selected':''}>Serviço agendado</option></select></div><div class="field" data-stock-field ${item.serviceItem?'hidden':''}><label for="edit-item-stock">Estoque</label><input id="edit-item-stock" name="stock" type="number" min="0" required value="${item.stock}" ${item.serviceItem?'disabled':''}/></div><div class="field" data-service-duration-field ${item.serviceItem?'':'hidden'}><label for="edit-item-duration">Duração (minutos)</label><input id="edit-item-duration" name="appointmentDurationMinutes" type="number" min="5" max="480" step="5" required value="${item.appointmentDurationMinutes||30}" ${item.serviceItem?'':'disabled'}/><small>O atendimento precisa caber no expediente e não pode coincidir com outro do profissional.</small></div><div class="field" data-service-payment-field ${item.serviceItem?'':'hidden'}><label for="edit-item-payment">Quando cobrar?</label><select id="edit-item-payment" name="payAfterService" data-pay-after-service><option value="false" ${item.payAfterService?'':'selected'}>Antes: pagamento online para confirmar</option><option value="true" ${item.payAfterService?'selected':''}>Depois: pagamento direto à loja</option></select></div><div class="alert-strip warn span-2" data-no-show-warning ${item.serviceItem&&item.payAfterService?'':'hidden'}><span>!</span><span>Pagamento após o atendimento traz risco de falta ou não pagamento. Fora da cidade do cliente, a plataforma exige pagamento antecipado.</span></div><label class="check-row span-2" data-risk-ack-field ${item.serviceItem&&item.payAfterService?'':'hidden'}><input type="checkbox" name="acceptNoShowRisk"/><span>Confirmo que aceito o risco de oferecer pagamento após o atendimento.</span></label><div class="span-2 action-row"><button class="btn" type="submit">Salvar alterações</button><button class="btn outline" type="button" data-close-item>Cancelar</button></div></form></section>`;
document.body.append(wrap);wrap.querySelectorAll('[data-close-item]').forEach(button=>button.addEventListener('click',()=>wrap.remove()));wrap.addEventListener('click',event=>{if(event.target===wrap)wrap.remove();});const form=wrap.querySelector('form');form.addEventListener('submit',saveCatalogItem);form.querySelector('[data-mask="currency"]').addEventListener('input',event=>event.target.value=formatCurrencyMask(event.target.value));form.querySelector('[name="serviceItem"]').addEventListener('change',()=>syncCatalogItemFields(form));form.querySelector('[name="payAfterService"]').addEventListener('change',()=>syncCatalogItemRisk(form));
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1112:36 — linha 1112

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1112)

```javascript
row=>
```

Executa a etapa `callback@1112:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1115:81 — linha 1115

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1115)

```javascript
button=>
```

Executa a etapa `callback@1115:81` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `wrap.remove`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>wrap.remove())
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1115:121 — linha 1115

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1115)

```javascript
()=>
```

Executa a etapa `callback@1115:121` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `wrap.remove`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1115:171 — linha 1115

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1115)

```javascript
event=>
```

Executa a etapa `callback@1115:171` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `wrap.remove`.

**Decisões condicionais examinadas:**
- `(event.target===wrap)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1115:375 — linha 1115

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1115)

```javascript
event=>
```

Executa a etapa `callback@1115:375` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `formatCurrencyMask`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1115:510 — linha 1115

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1115)

```javascript
()=>
```

Executa a etapa `callback@1115:510` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `syncCatalogItemFields`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1115:616 — linha 1115

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1115)

```javascript
()=>
```

Executa a etapa `callback@1115:616` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `syncCatalogItemRisk`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### saveCatalogItem — linha 1117

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1117)

```javascript
async function saveCatalogItem(event)
```

Executa a etapa `save Catalog Item` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `state.storeItems.find`; `String`; `parseCurrencyDecimal`; `data.get`; `Number.isFinite`; `notify`; `Number`; `Number.isInteger`; `api`; `JSON.stringify`; `Math.round`; `activeMerchantStore`; `$`; `renderPage`.

**Decisões condicionais examinadas:**
- `(!item)`
- `(!Number.isFinite(price)||price<=0)`
- `(payAfterService&&data.get('acceptNoShowRisk')!=='on')`
- `serviceItem`
- `(!Number.isInteger(stock)||stock<0)`
- `(serviceItem&&(!Number.isInteger(appointmentDurationMinutes)||appointmentDurationMinutes<5||appointmentDurationMinutes>480))`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try{await api(`/merchant/items/${item.id}`,{method:'PUT',body:JSON.stringify({name:String(data.get('name')).trim(),description:String(data.get('description')||'').trim(),priceCents:Math.round(price*100),stock,active:item.active,serviceItem,payAfterService,appointmentDurationMinutes})});state.storeItems=await api(`/merchant/stores/${activeMerchantStore().id}/items`);$('#catalog-item-modal')?.remove();notify('Item atualizado','Tipo, duração, preço e forma de pagamento foram salvos.','success');renderPage();}catch(error){notify('Não foi possível atualizar o item',error.message,'error');}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1118:84 — linha 1118

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1118)

```javascript
row=>
```

Executa a etapa `callback@1118:84` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createProfessional — linha 1126

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1126)

```javascript
async function createProfessional(event)
```

Executa a etapa `create Professional` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `activeMerchantStore`; `String`; `new FormData`; `api`; `JSON.stringify`; `form.reset`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `(!store||!name)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function createProfessional(event){event.preventDefault();const form=event.currentTarget,store=activeMerchantStore(),name=String(new FormData(form).get('name')||'').trim();if(!store||!name)return;try{await api(`/merchant/stores/${store.id}/professionals`,{method:'POST',body:JSON.stringify({name})});state.merchantProfessionals=await api(`/merchant/stores/${store.id}/professionals`);form.reset();notify('Profissional adicionado','Agora os clientes podem escolher essa pessoa ou pedir o primeiro disponível.','success');renderPage();}catch(error){notify('Não foi possível adicionar o profissional',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setProfessionalActive — linha 1127

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1127)

```javascript
async function setProfessionalActive(id,active)
```

Executa a etapa `set Professional Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `JSON.stringify`; `activeMerchantStore`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `active`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function setProfessionalActive(id,active){try{await api(`/merchant/professionals/${id}/active`,{method:'PATCH',body:JSON.stringify({active})});state.merchantProfessionals=await api(`/merchant/stores/${activeMerchantStore().id}/professionals`);notify(active?'Profissional ativado':'Profissional desativado',active?'Ele voltou a aparecer nas reservas.':'Novas reservas não serão atribuídas a essa pessoa.','info');renderPage();}catch(error){notify('Não foi possível atualizar o profissional',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createOperator — linha 1129

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1129)

```javascript
async function createOperator(event)
```

Executa a etapa `create Operator` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `runOnce`; `element.querySelector`; `api`; `JSON.stringify`; `String`; `form.get`; `element.reset`; `notify`; `loadAdminData`; `renderPage`.

**Decisões condicionais examinadas:**
- `(submit)`
- `(state.adminLoadError)`
- `(submit?.isConnected)`

**Saída declarada:**
- `return runOnce(\`admin:create-operator:${state.account?.id}\`,async()=>{const form=new FormData(element),submit=element.querySelector('[type="submit"]');if(submit){submit.disabled=true;submit.textContent='Criando acesso…';}try{await api('/admin/operators',{method:'POST',body:JSON.stringify({name:String(form.get('name')).trim(),email:String(form.get('email')).trim(),cpf:String(form.get('cpf')).replace(/\D/g,''),password:String(form.get('password')),role:String(form.get('role'))})});element.reset();notify('Acesso criado','O novo perfil foi vinculado a uma conta.');await loadAdminData();renderPage();if(state.adminLoadError)notify('Acesso criado, atualização parcial',state.adminLoadError,'info');}catch(e){notify('Não conseguimos criar o acesso',e.message,'error');}finally{if(submit?.isConnected){submit.disabled=false;submit.textContent='Criar acesso';}}});`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function createOperator(event){event.preventDefault();const element=event.currentTarget;return runOnce(`admin:create-operator:${state.account?.id}`,async()=>{const form=new FormData(element),submit=element.querySelector('[type="submit"]');if(submit){submit.disabled=true;submit.textContent='Criando acesso…';}try{await api('/admin/operators',{method:'POST',body:JSON.stringify({name:String(form.get('name')).trim(),email:String(form.get('email')).trim(),cpf:String(form.get('cpf')).replace(/\D/g,''),password:String(form.get('password')),role:String(form.get('role'))})});element.reset();notify('Acesso criado','O novo perfil foi vinculado a uma conta.');await loadAdminData();renderPage();if(state.adminLoadError)notify('Acesso criado, atualização parcial',state.adminLoadError,'info');}catch(e){notify('Não conseguimos criar o acesso',e.message,'error');}finally{if(submit?.isConnected){submit.disabled=false;submit.textContent='Criar acesso';}}});}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1129:155 — linha 1129

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1129)

```javascript
async()=>
```

Executa a etapa `callback@1129:155` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `element.querySelector`; `api`; `JSON.stringify`; `String`; `form.get`; `element.reset`; `notify`; `loadAdminData`; `renderPage`.

**Decisões condicionais examinadas:**
- `(submit)`
- `(state.adminLoadError)`
- `(submit?.isConnected)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async()=>{const form=new FormData(element),submit=element.querySelector('[type="submit"]');if(submit){submit.disabled=true;submit.textContent='Criando acesso…';}try{await api('/admin/operators',{method:'POST',body:JSON.stringify({name:String(form.get('name')).trim(),email:String(form.get('email')).trim(),cpf:String(form.get('cpf')).replace(/\D/g,''),password:String(form.get('password')),role:String(form.get('role'))})});element.reset();notify('Acesso criado','O novo perfil foi vinculado a uma conta.');await loadAdminData();renderPage();if(state.adminLoadError)notify('Acesso criado, atualização parcial',state.adminLoadError,'info');}catch(e){notify('Não conseguimos criar o acesso',e.message,'error');}finally{if(submit?.isConnected){submit.disabled=false;submit.textContent='Criar acesso';}}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setAccountActive — linha 1131

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1131)

```javascript
async function setAccountActive(id,active)
```

Executa a etapa `set Account Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `JSON.stringify`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `active`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function setAccountActive(id,active){try{await api(`/admin/accounts/${id}/active`,{method:'PATCH',body:JSON.stringify({active})});state.adminAccounts=await api('/admin/accounts');notify(active?'Conta reativada':'Conta suspensa',active?'A pessoa pode voltar a acessar os perfis ativos.':'A conta não poderá entrar enquanto estiver suspensa.','info');renderPage();}catch(e){notify('Não foi possível alterar a conta',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setAccountRole — linha 1132

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1132)

```javascript
async function setAccountRole(id,roleName,active)
```

Executa a etapa `set Account Role` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `JSON.stringify`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `active`
- `roleName==='MERCHANT'&&!active`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function setAccountRole(id,roleName,active){try{await api(`/admin/accounts/${id}/roles/${roleName}`,{method:'PATCH',body:JSON.stringify({active})});state.adminAccounts=await api('/admin/accounts');state.adminOperators=await api('/admin/operators');notify(active?'Acesso ativado':'Acesso suspenso',roleName==='MERCHANT'&&!active?'A área de cliente continua disponível; a loja foi suspensa.':'As permissões da conta foram atualizadas.','info');renderPage();}catch(e){notify('Não foi possível alterar esse acesso',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### resetAccountPassword — linha 1133

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1133)

```javascript
async function resetAccountPassword(id,name)
```

Executa a etapa `reset Account Password` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `window.confirm`; `api`; `notify`; `renderPage`.

**Decisões condicionais examinadas:**
- `(!window.confirm(\`Gerar uma senha temporária para ${name}? A pessoa deverá alterá-la no próximo acesso.\`))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function resetAccountPassword(id,name){if(!window.confirm(`Gerar uma senha temporária para ${name}? A pessoa deverá alterá-la no próximo acesso.`))return;try{const result=await api(`/admin/accounts/${id}/password-reset`,{method:'POST'});state.adminTemporaryPassword={name,password:result.temporaryPassword};notify('Senha temporária gerada','Compartilhe a senha diretamente com a pessoa por um canal seguro.','info');renderPage();}catch(e){notify('Não foi possível redefinir a senha',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### reviewPlatformRequest — linha 1135

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1135)

```javascript
async function reviewPlatformRequest(id,decision)
```

Executa a etapa `review Platform Request` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `runOnce`; `document.querySelector`; `CSS.escape`; `String`; `api`; `JSON.stringify`; `notify`; `loadAdminData`; `renderPage`.

**Decisões condicionais examinadas:**
- `(button)`
- `decision==='true'`
- `(state.adminLoadError)`
- `(button?.isConnected)`

**Saída declarada:**
- `return runOnce(\`admin:review-request:${id}\`,async()=>{const button=document.querySelector(\`[data-platform-review="${CSS.escape(String(id))}"][data-decision="${decision}"]\`);if(button){button.disabled=true;button.textContent=decision==='true'?'Aprovando…':'Reprovando…';}try{await api(\`/admin/platform-requests/${id}/review\`,{method:'PATCH',body:JSON.stringify({approved:decision==='true'})});notify(decision==='true'?'Solicitação aprovada':'Solicitação reprovada','A decisão foi registrada e a pessoa será notificada.',decision==='true'?'success':'info');await loadAdminData();renderPage();if(state.adminLoadError)notify('Decisão registrada, atualização parcial',state.adminLoadError,'info');}catch(error){notify('Não foi possível analisar a solicitação',error.message,'error');if(button?.isConnected)button.disabled=false;}});`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function reviewPlatformRequest(id,decision){return runOnce(`admin:review-request:${id}`,async()=>{const button=document.querySelector(`[data-platform-review="${CSS.escape(String(id))}"][data-decision="${decision}"]`);if(button){button.disabled=true;button.textContent=decision==='true'?'Aprovando…':'Reprovando…';}try{await api(`/admin/platform-requests/${id}/review`,{method:'PATCH',body:JSON.stringify({approved:decision==='true'})});notify(decision==='true'?'Solicitação aprovada':'Solicitação reprovada','A decisão foi registrada e a pessoa será notificada.',decision==='true'?'success':'info');await loadAdminData();renderPage();if(state.adminLoadError)notify('Decisão registrada, atualização parcial',state.adminLoadError,'info');}catch(error){notify('Não foi possível analisar a solicitação',error.message,'error');if(button?.isConnected)button.disabled=false;}});}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1135:95 — linha 1135

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1135)

```javascript
async()=>
```

Executa a etapa `callback@1135:95` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.querySelector`; `CSS.escape`; `String`; `api`; `JSON.stringify`; `notify`; `loadAdminData`; `renderPage`.

**Decisões condicionais examinadas:**
- `(button)`
- `decision==='true'`
- `(state.adminLoadError)`
- `(button?.isConnected)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async()=>{const button=document.querySelector(`[data-platform-review="${CSS.escape(String(id))}"][data-decision="${decision}"]`);if(button){button.disabled=true;button.textContent=decision==='true'?'Aprovando…':'Reprovando…';}try{await api(`/admin/platform-requests/${id}/review`,{method:'PATCH',body:JSON.stringify({approved:decision==='true'})});notify(decision==='true'?'Solicitação aprovada':'Solicitação reprovada','A decisão foi registrada e a pessoa será notificada.',decision==='true'?'success':'info');await loadAdminData();renderPage();if(state.adminLoadError)notify('Decisão registrada, atualização parcial',state.adminLoadError,'info');}catch(error){notify('Não foi possível analisar a solicitação',error.message,'error');if(button?.isConnected)button.disabled=false;}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### reviewOperatorApplication — linha 1137

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1137)

```javascript
async function reviewOperatorApplication(id,decision)
```

Executa a etapa `review Operator Application` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `Promise.all`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `decision==='approve'`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try{await api(`/admin/operator-applications/${id}/${verb}`,{method:'PATCH'});[state.adminApplications,state.adminOperators]=await Promise.all([api('/admin/operator-applications'),api('/admin/operators')]);notify(decision==='approve'?'Acesso aprovado':'Solicitação recusada',decision==='approve'?'A conta já pode entrar na área de vendedor ou entregador.':'A conta não poderá entrar no sistema.','info');render();}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setOperatorActive — linha 1143

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1143)

```javascript
async function setOperatorActive(id,active)
```

Executa a etapa `set Operator Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.adminOperators.find`; `String`; `api`; `role`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `active`
- `(role()==='ADMIN'&&state.page==='admin')`
- `operator?.role==='MERCHANT'`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try{const operator=state.adminOperators.find(account=>String(account.id)===String(id));await api(`/admin/operators/${id}/${active?'activate':'deactivate'}`,{method:'PATCH'});state.adminOperators=await api('/admin/operators');if(role()==='ADMIN'&&state.page==='admin')state.adminStores=await api('/admin/stores');notify(active?'Acesso reativado':'Acesso suspenso',active?'A conta pode entrar novamente.':operator?.role==='MERCHANT'?'A loja também foi retirada do ar e precisará de nova aprovação.':'A sessão da conta foi bloqueada.','info');render();}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1144:48 — linha 1144

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1144)

```javascript
account=>
```

Executa a etapa `callback@1144:48` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### approveStore — linha 1148

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1148)

```javascript
async function approveStore(id)
```

Executa a etapa `approve Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `runOnce`; `document.querySelector`; `CSS.escape`; `String`; `state.adminPlatformRequests.find`; `api`; `storeApprovalPath`; `JSON.stringify`; `notify`; `loadAdminData`; `renderPage`.

**Decisões condicionais examinadas:**
- `(button)`
- `(state.adminLoadError)`
- `(button?.isConnected)`

**Saída declarada:**
- `return runOnce(\`admin:approve-store:${id}\`,async()=>{const button=document.querySelector(\`[data-approve-store="${CSS.escape(String(id))}"]\`);if(button){button.disabled=true;button.textContent='Aprovando…';}try{const request=state.adminPlatformRequests.find(r=>r.requestType==='STORE'&&String(r.storeId)===String(id)&&r.status==='PENDING');await api(storeApprovalPath(id,request?.id),{method:'PATCH',body:JSON.stringify({approved:true})});notify('Loja aprovada','Ela já pode aparecer para os clientes.');await loadAdminData();renderPage();if(state.adminLoadError)notify('Loja aprovada, atualização parcial',state.adminLoadError,'info');}catch(e){notify('Não conseguimos aprovar a loja',e.message,'error');if(button?.isConnected)button.disabled=false;}});`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function approveStore(id){return runOnce(`admin:approve-store:${id}`,async()=>{const button=document.querySelector(`[data-approve-store="${CSS.escape(String(id))}"]`);if(button){button.disabled=true;button.textContent='Aprovando…';}try{const request=state.adminPlatformRequests.find(r=>r.requestType==='STORE'&&String(r.storeId)===String(id)&&r.status==='PENDING');await api(storeApprovalPath(id,request?.id),{method:'PATCH',body:JSON.stringify({approved:true})});notify('Loja aprovada','Ela já pode aparecer para os clientes.');await loadAdminData();renderPage();if(state.adminLoadError)notify('Loja aprovada, atualização parcial',state.adminLoadError,'info');}catch(e){notify('Não conseguimos aprovar a loja',e.message,'error');if(button?.isConnected)button.disabled=false;}});}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1148:76 — linha 1148

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1148)

```javascript
async()=>
```

Executa a etapa `callback@1148:76` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.querySelector`; `CSS.escape`; `String`; `state.adminPlatformRequests.find`; `api`; `storeApprovalPath`; `JSON.stringify`; `notify`; `loadAdminData`; `renderPage`.

**Decisões condicionais examinadas:**
- `(button)`
- `(state.adminLoadError)`
- `(button?.isConnected)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async()=>{const button=document.querySelector(`[data-approve-store="${CSS.escape(String(id))}"]`);if(button){button.disabled=true;button.textContent='Aprovando…';}try{const request=state.adminPlatformRequests.find(r=>r.requestType==='STORE'&&String(r.storeId)===String(id)&&r.status==='PENDING');await api(storeApprovalPath(id,request?.id),{method:'PATCH',body:JSON.stringify({approved:true})});notify('Loja aprovada','Ela já pode aparecer para os clientes.');await loadAdminData();renderPage();if(state.adminLoadError)notify('Loja aprovada, atualização parcial',state.adminLoadError,'info');}catch(e){notify('Não conseguimos aprovar a loja',e.message,'error');if(button?.isConnected)button.disabled=false;}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1148:292 — linha 1148

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1148)

```javascript
r=>
```

Executa a etapa `callback@1148:292` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setStoreActive — linha 1150

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1150)

```javascript
async function setStoreActive(id,active)
```

Executa a etapa `set Store Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `JSON.stringify`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `active`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function setStoreActive(id,active){try{await api(`/admin/stores/${id}/active`,{method:'PATCH',body:JSON.stringify({active})});state.adminStores=await api('/admin/stores');notify(active?'Loja reativada':'Loja suspensa',active?'A loja voltou a ficar disponível para revisão.':'A loja foi retirada da descoberta até nova ação administrativa.','info');render();}catch(error){notify('Não foi possível atualizar a loja',error.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateOrder — linha 1152

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1152)

```javascript
async function updateOrder(id,status)
```

Executa a etapa `update Order` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `['CANCELLED','REJECTED'].includes`; `window.confirm`; `api`; `JSON.stringify`; `notify`; `formatStatus`; `render`.

**Decisões condicionais examinadas:**
- `(['CANCELLED','REJECTED'].includes(status)&&!window.confirm('Este pedido será encerrado. Se o pagamento foi aprovado, o estorno será solicitado ao Mercado Pago e pode depender do saldo disponível na conta da loja. Deseja continuar?'))`
- `['CANCELLED','REJECTED'].includes(status)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function updateOrder(id,status){if(['CANCELLED','REJECTED'].includes(status)&&!window.confirm('Este pedido será encerrado. Se o pagamento foi aprovado, o estorno será solicitado ao Mercado Pago e pode depender do saldo disponível na conta da loja. Deseja continuar?'))return;try{await api(`/merchant/orders/${id}/status`,{method:'PATCH',body:JSON.stringify({status})});state.orders=await api('/merchant/orders');notify('Pedido atualizado',`Novo status: ${formatStatus(status)}. ${['CANCELLED','REJECTED'].includes(status)?'Confira o acompanhamento de estorno na conta do cliente.':''}`);render();}catch(e){notify('Não conseguimos atualizar o pedido',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### offerDelivery — linha 1154

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1154)

```javascript
async function offerDelivery(id)
```

Executa a etapa `offer Delivery` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `notify`; `render`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function offerDelivery(id){try{await api(`/merchant/orders/${id}/delivery`,{method:'POST'});state.orders=await api('/merchant/orders');notify('Entrega oferecida','Os entregadores disponíveis já podem aceitar esta corrida.');render();}catch(e){notify('Não conseguimos enviar para entrega',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateReservation — linha 1156

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1156)

```javascript
async function updateReservation(id,status)
```

Executa a etapa `update Reservation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `window.confirm`; `api`; `JSON.stringify`; `notify`; `formatStatus`; `render`.

**Decisões condicionais examinadas:**
- `(status==='CANCELLED'&&!window.confirm('A reserva será cancelada. Se já foi paga, o estorno será solicitado ao Mercado Pago e pode depender do saldo disponível na conta da loja. Deseja continuar?'))`
- `status==='CANCELLED'`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function updateReservation(id,status){if(status==='CANCELLED'&&!window.confirm('A reserva será cancelada. Se já foi paga, o estorno será solicitado ao Mercado Pago e pode depender do saldo disponível na conta da loja. Deseja continuar?'))return;try{await api(`/merchant/reservations/${id}`,{method:'PATCH',body:JSON.stringify({status})});state.reservations=await api('/merchant/reservations');notify('Reserva atualizada',`Novo status: ${formatStatus(status)}.${status==='CANCELLED'?' A solicitação de estorno está sendo processada.':''}`);render();}catch(e){notify('Não conseguimos atualizar a reserva',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### acceptDelivery — linha 1158

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1158)

```javascript
async function acceptDelivery(id)
```

Executa a etapa `accept Delivery` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `notify`; `render`; `loadCurrentPage`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function acceptDelivery(id){try{await api(`/courier/offers/${id}/accept`,{method:'POST'});state.offers=await api('/courier/offers');state.deliveries=await api('/courier/deliveries');notify('Entrega atribuída','Agora você pode consultar o endereço e atualizar o trajeto.');render();}catch(e){notify('Essa oferta já não está disponível',e.message,'info');await loadCurrentPage();render();}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateDelivery — linha 1160

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1160)

```javascript
async function updateDelivery(id,status)
```

Executa a etapa `update Delivery` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `JSON.stringify`; `String`; `stopCourierLocation`; `notify`; `formatStatus`; `render`.

**Decisões condicionais examinadas:**
- `(status==='DELIVERED'&&state.sharingDeliveryId===String(id))`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function updateDelivery(id,status){try{await api(`/courier/deliveries/${id}`,{method:'PATCH',body:JSON.stringify({status})});if(status==='DELIVERED'&&state.sharingDeliveryId===String(id))stopCourierLocation();state.deliveries=await api('/courier/deliveries');notify('Etapa atualizada',`Entrega marcada como ${formatStatus(status).toLowerCase()}.`);render();}catch(e){notify('Não conseguimos atualizar a entrega',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### stopCourierLocation — linha 1162

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1162)

```javascript
function stopCourierLocation()
```

Executa a etapa `stop Courier Location` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `navigator.geolocation.clearWatch`.

**Decisões condicionais examinadas:**
- `(state.locationWatchId!==null&&navigator.geolocation)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### shareCourierLocation — linha 1164

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1164)

```javascript
function shareCourierLocation(id)
```

Executa a etapa `share Courier Location` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notify`; `String`; `stopCourierLocation`; `navigator.geolocation.watchPosition`; `Date.now`; `api`; `encodeURIComponent`; `JSON.stringify`.

**Decisões condicionais examinadas:**
- `(!navigator.geolocation)`
- `(state.sharingDeliveryId===String(id)&&state.locationWatchId!==null)`
- `(now-state.lastLocationSentAt<15000)`
- `error.code===1`

**Saída declarada:**
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try{await api(`/courier/deliveries/${encodeURIComponent(id)}/location`,{method:'PATCH',body:JSON.stringify({latitude:position.coords.latitude,longitude:position.coords.longitude})});}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1168:61 — linha 1168

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1168)

```javascript
async position=>
```

Executa a etapa `callback@1168:61` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Date.now`; `api`; `encodeURIComponent`; `JSON.stringify`; `notify`; `stopCourierLocation`.

**Decisões condicionais examinadas:**
- `(now-state.lastLocationSentAt<15000)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try{await api(`/courier/deliveries/${encodeURIComponent(id)}/location`,{method:'PATCH',body:JSON.stringify({latitude:position.coords.latitude,longitude:position.coords.longitude})});}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1172:5 — linha 1172

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1172)

```javascript
error=>
```

Executa a etapa `callback@1172:5` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stopCourierLocation`; `notify`.

**Decisões condicionais examinadas:**
- `error.code===1`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancelUnpaidOrder — linha 1176

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1176)

```javascript
async function cancelUnpaidOrder(id)
```

Executa a etapa `cancel Unpaid Order` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `window.confirm`; `api`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `(!window.confirm('Cancelar este pedido não pago? O estoque reservado será liberado.'))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function cancelUnpaidOrder(id){if(!window.confirm('Cancelar este pedido não pago? O estoque reservado será liberado.'))return;try{await api(`/orders/${id}/cancel`,{method:'POST'});state.orders=await api('/orders/mine');notify('Pedido cancelado','O estoque foi liberado e a loja recebeu um aviso.','info');render();}catch(e){notify('Não foi possível cancelar o pedido',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancelUnpaidReservation — linha 1177

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1177)

```javascript
async function cancelUnpaidReservation(id)
```

Executa a etapa `cancel Unpaid Reservation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `window.confirm`; `api`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `(!window.confirm('Cancelar esta reserva não paga? O horário será liberado para outra pessoa.'))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function cancelUnpaidReservation(id){if(!window.confirm('Cancelar esta reserva não paga? O horário será liberado para outra pessoa.'))return;try{await api(`/reservations/${id}/cancel`,{method:'POST'});state.reservations=await api('/reservations/mine');notify('Reserva cancelada','O horário foi liberado e a loja recebeu um aviso.','info');render();}catch(e){notify('Não foi possível cancelar a reserva',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestOrderCancellation — linha 1178

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1178)

```javascript
async function requestOrderCancellation(id)
```

Executa a etapa `request Order Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `window.confirm`; `api`; `JSON.stringify`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `(!window.confirm('A loja analisará seu pedido. Se aceitar, o FoodDelivery solicitará o estorno integral ao Mercado Pago; a confirmação e o prazo dependem do provedor. Deseja enviar?'))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function requestOrderCancellation(id){if(!window.confirm('A loja analisará seu pedido. Se aceitar, o FoodDelivery solicitará o estorno integral ao Mercado Pago; a confirmação e o prazo dependem do provedor. Deseja enviar?'))return;try{await api(`/orders/${id}/cancellation-request`,{method:'POST',body:JSON.stringify({note:null})});state.orders=await api('/orders/mine');notify('Solicitação enviada','A loja recebeu seu pedido de cancelamento.','success');render();}catch(e){notify('Não foi possível pedir o cancelamento',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestReservationCancellation — linha 1179

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1179)

```javascript
async function requestReservationCancellation(id)
```

Executa a etapa `request Reservation Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `window.confirm`; `api`; `JSON.stringify`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `(!window.confirm('A loja analisará sua solicitação. Se aceitar, qualquer pagamento feito pelo site será enviado para estorno. Deseja continuar?'))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function requestReservationCancellation(id){if(!window.confirm('A loja analisará sua solicitação. Se aceitar, qualquer pagamento feito pelo site será enviado para estorno. Deseja continuar?'))return;try{await api(`/reservations/${id}/cancellation-request`,{method:'POST',body:JSON.stringify({note:null})});state.reservations=await api('/reservations/mine');notify('Solicitação enviada','A loja recebeu seu pedido de cancelamento.','success');render();}catch(e){notify('Não foi possível pedir o cancelamento',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### decideOrderCancellation — linha 1180

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1180)

```javascript
async function decideOrderCancellation(id,accepted)
```

Executa a etapa `decide Order Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `window.confirm`; `api`; `JSON.stringify`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `(accepted&&!window.confirm('Aceitar encerrará o pedido, devolverá o estoque e solicitará o estorno integral ao Mercado Pago. Confirma?'))`
- `accepted`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function decideOrderCancellation(id,accepted){if(accepted&&!window.confirm('Aceitar encerrará o pedido, devolverá o estoque e solicitará o estorno integral ao Mercado Pago. Confirma?'))return;try{await api(`/merchant/orders/${id}/cancellation-request`,{method:'PATCH',body:JSON.stringify({accepted})});state.orders=await api('/merchant/orders');notify(accepted?'Cancelamento aceito':'Solicitação recusada',accepted?'O estoque foi devolvido e o estorno foi solicitado ao provedor.':'O cliente foi avisado e o pedido continua em andamento.',accepted?'success':'info');render();}catch(e){notify('Não foi possível responder à solicitação',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### decideReservationCancellation — linha 1181

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1181)

```javascript
async function decideReservationCancellation(id,accepted)
```

Executa a etapa `decide Reservation Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `window.confirm`; `api`; `JSON.stringify`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `(accepted&&!window.confirm('Aceitar liberará o horário e solicitará o estorno integral de um pagamento feito pelo site. Confirma?'))`
- `accepted`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function decideReservationCancellation(id,accepted){if(accepted&&!window.confirm('Aceitar liberará o horário e solicitará o estorno integral de um pagamento feito pelo site. Confirma?'))return;try{await api(`/merchant/reservations/${id}/cancellation-request`,{method:'PATCH',body:JSON.stringify({accepted})});state.reservations=await api('/merchant/reservations');notify(accepted?'Cancelamento aceito':'Solicitação recusada',accepted?'O horário foi liberado e o estorno foi enviado para processamento.':'O cliente foi avisado; a reserva continua confirmada.',accepted?'success':'info');render();}catch(e){notify('Não foi possível responder à solicitação',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### bindPageActions — linha 1183

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1183)

```javascript
async function bindPageActions()
```

Executa a etapa `bind Page Actions` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.querySelectorAll`; `button.addEventListener`; `renderPage`; `form.addEventListener`; `$`; `api`; `localStorage.removeItem`; `notificationKey`; `render`; `notify`; `openNotificationTarget`; `item.addEventListener`; `event.preventDefault`; `activate`; `navigate`; `loadStores`; `resumeOrderPayment`; `resumeReservationPayment`; `cancelUnpaidOrder`; `cancelUnpaidReservation`; `requestOrderCancellation`; `requestReservationCancellation`; `decideOrderCancellation`; `decideReservationCancellation`; `setDefaultAddress`; `deleteAddress`; `lookupCep`; `button.closest`; `input.addEventListener`; `input.value.replace`; `input.closest`.

**Decisões condicionais examinadas:**
- `(event.key==='Enter'||event.key===' ')`
- `(input.value.replace(/\D/g,'').length===8)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
document.querySelectorAll('#page-content [data-add-address]').forEach(button=>button.addEventListener('click',()=>{state.addressFormOpen=true;renderPage();}));
document.querySelectorAll('#page-content [data-cancel-address]').forEach(button=>button.addEventListener('click',()=>{state.addressFormOpen=false;renderPage();}));
document.querySelectorAll('#page-content [data-address-form]').forEach(form=>form.addEventListener('submit',saveCheckoutAddress));
$('[data-clear-notifications]')?.addEventListener('click',async()=>{try{await api('/account/notifications',{method:'DELETE'});localStorage.removeItem(notificationKey(state.account.id));localStorage.removeItem(`pronto_notifications:${state.account.id}`);state.notifications=[];render();notify('Avisos limpos','Você está em dia.','info',false);}catch(e){notify('Não foi possível limpar os avisos',e.message,'error');}});
item.addEventListener('click',activate);
item.addEventListener('keydown',event=>{if(event.key==='Enter'||event.key===' '){event.preventDefault();activate();}});
$('[data-action="reload-merchant-orders"]')?.addEventListener('click',async()=>{try{state.orders=await api('/merchant/orders');render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}});
$('[data-action="reload-merchant-reservations"]')?.addEventListener('click',async()=>{try{state.reservations=await api('/merchant/reservations');render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}});
$('[data-action="reload-courier"]')?.addEventListener('click',async()=>{try{state.offers=await api('/courier/offers');state.deliveries=await api('/courier/deliveries');render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}});
$('[data-action="go-home"]')?.addEventListener('click',()=>navigate('home'));
$('[data-action="reload-stores"]')?.addEventListener('click',async()=>{try{await loadStores();render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}});
document.querySelectorAll('[data-store-commission]').forEach(form=>form.addEventListener('submit',saveCommission));
document.querySelectorAll('[data-connect-mercadopago]').forEach(button=>button.addEventListener('click',connectMercadoPago));
document.querySelectorAll('[data-resume-order]').forEach(button=>button.addEventListener('click',()=>resumeOrderPayment(button.dataset.resumeOrder)));
document.querySelectorAll('[data-resume-reservation]').forEach(button=>button.addEventListener('click',()=>resumeReservationPayment(button.dataset.resumeReservation)));
document.querySelectorAll('[data-cancel-order]').forEach(button=>button.addEventListener('click',()=>cancelUnpaidOrder(button.dataset.cancelOrder)));
document.querySelectorAll('[data-cancel-reservation]').forEach(button=>button.addEventListener('click',()=>cancelUnpaidReservation(button.dataset.cancelReservation)));
document.querySelectorAll('[data-request-order-cancellation]').forEach(button=>button.addEventListener('click',()=>requestOrderCancellation(button.dataset.requestOrderCancellation)));
document.querySelectorAll('[data-request-reservation-cancellation]').forEach(button=>button.addEventListener('click',()=>requestReservationCancellation(button.dataset.requestReservationCancellation)));
document.querySelectorAll('[data-order-cancellation-decision]').forEach(button=>button.addEventListener('click',()=>decideOrderCancellation(button.dataset.orderCancellationDecision,button.dataset.accepted==='true')));
document.querySelectorAll('[data-reservation-cancellation-decision]').forEach(button=>button.addEventListener('click',()=>decideReservationCancellation(button.dataset.reservationCancellationDecision,button.dataset.accepted==='true')));
document.querySelectorAll('[data-default-address]').forEach(button=>button.addEventListener('click',()=>setDefaultAddress(button.dataset.defaultAddress)));
document.querySelectorAll('[data-delete-address]').forEach(button=>button.addEventListener('click',()=>deleteAddress(button.dataset.deleteAddress)));
document.querySelectorAll('[data-lookup-cep]').forEach(button=>button.addEventListener('click',()=>lookupCep(button.closest('form'))));
document.querySelectorAll('[data-cep]').forEach(input=>input.addEventListener('blur',()=>{if(input.value.replace(/\D/g,'').length===8)lookupCep(input.closest('form'));}));
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1184:73 — linha 1184

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1184)

```javascript
button=>
```

Executa a etapa `callback@1184:73` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `renderPage`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>{state.addressFormOpen=true;renderPage();})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1184:113 — linha 1184

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1184)

```javascript
()=>
```

Executa a etapa `callback@1184:113` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `renderPage`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1185:76 — linha 1185

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1185)

```javascript
button=>
```

Executa a etapa `callback@1185:76` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `renderPage`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>{state.addressFormOpen=false;renderPage();})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1185:116 — linha 1185

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1185)

```javascript
()=>
```

Executa a etapa `callback@1185:116` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `renderPage`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1186:74 — linha 1186

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1186)

```javascript
form=>
```

Executa a etapa `callback@1186:74` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
form=>form.addEventListener('submit',saveCheckoutAddress)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1187:61 — linha 1187

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1187)

```javascript
async()=>
```

Executa a etapa `callback@1187:61` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `localStorage.removeItem`; `notificationKey`; `render`; `notify`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async()=>{try{await api('/account/notifications',{method:'DELETE'});localStorage.removeItem(notificationKey(state.account.id));localStorage.removeItem(`pronto_notifications:${state.account.id}`);state.notifications=[];render();notify('Avisos limpos','Você está em dia.','info',false);}catch(e){notify('Não foi possível limpar os avisos',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1188:67 — linha 1188

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1188)

```javascript
item=>
```

Executa a etapa `callback@1188:67` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openNotificationTarget`; `item.addEventListener`; `event.preventDefault`; `activate`.

**Decisões condicionais examinadas:**
- `(event.key==='Enter'||event.key===' ')`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
item.addEventListener('click',activate);
item.addEventListener('keydown',event=>{if(event.key==='Enter'||event.key===' '){event.preventDefault();activate();}});
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### activate — linha 1189

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1189)

```javascript
()=>
```

Executa a etapa `activate` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `openNotificationTarget`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1191:37 — linha 1191

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1191)

```javascript
event=>
```

Executa a etapa `callback@1191:37` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `activate`.

**Decisões condicionais examinadas:**
- `(event.key==='Enter'||event.key===' ')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1193:73 — linha 1193

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1193)

```javascript
async()=>
```

Executa a etapa `callback@1193:73` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `render`; `notify`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async()=>{try{state.orders=await api('/merchant/orders');render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1194:79 — linha 1194

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1194)

```javascript
async()=>
```

Executa a etapa `callback@1194:79` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `render`; `notify`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async()=>{try{state.reservations=await api('/merchant/reservations');render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1195:65 — linha 1195

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1195)

```javascript
async()=>
```

Executa a etapa `callback@1195:65` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `render`; `notify`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async()=>{try{state.offers=await api('/courier/offers');state.deliveries=await api('/courier/deliveries');render();}catch(e){notify('Não foi possível atualizar',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1196:58 — linha 1196

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1196)

```javascript
()=>
```

Executa a etapa `callback@1196:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `navigate`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1197:64 — linha 1197

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1197)

```javascript
async()=>
```

Executa a etapa `callback@1197:64` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `loadStores`; `render`; `notify`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1198:64 — linha 1198

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1198)

```javascript
form=>
```

Executa a etapa `callback@1198:64` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
form=>form.addEventListener('submit',saveCommission)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1199:67 — linha 1199

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1199)

```javascript
button=>
```

Executa a etapa `callback@1199:67` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',connectMercadoPago)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1200:60 — linha 1200

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1200)

```javascript
button=>
```

Executa a etapa `callback@1200:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `resumeOrderPayment`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>resumeOrderPayment(button.dataset.resumeOrder))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1200:100 — linha 1200

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1200)

```javascript
()=>
```

Executa a etapa `callback@1200:100` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `resumeOrderPayment`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1201:66 — linha 1201

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1201)

```javascript
button=>
```

Executa a etapa `callback@1201:66` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `resumeReservationPayment`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>resumeReservationPayment(button.dataset.resumeReservation))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1201:106 — linha 1201

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1201)

```javascript
()=>
```

Executa a etapa `callback@1201:106` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `resumeReservationPayment`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1202:60 — linha 1202

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1202)

```javascript
button=>
```

Executa a etapa `callback@1202:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `cancelUnpaidOrder`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>cancelUnpaidOrder(button.dataset.cancelOrder))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1202:100 — linha 1202

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1202)

```javascript
()=>
```

Executa a etapa `callback@1202:100` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `cancelUnpaidOrder`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1203:66 — linha 1203

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1203)

```javascript
button=>
```

Executa a etapa `callback@1203:66` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `cancelUnpaidReservation`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>cancelUnpaidReservation(button.dataset.cancelReservation))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1203:106 — linha 1203

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1203)

```javascript
()=>
```

Executa a etapa `callback@1203:106` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `cancelUnpaidReservation`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1204:74 — linha 1204

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1204)

```javascript
button=>
```

Executa a etapa `callback@1204:74` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `requestOrderCancellation`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>requestOrderCancellation(button.dataset.requestOrderCancellation))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1204:114 — linha 1204

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1204)

```javascript
()=>
```

Executa a etapa `callback@1204:114` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requestOrderCancellation`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1205:80 — linha 1205

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1205)

```javascript
button=>
```

Executa a etapa `callback@1205:80` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `requestReservationCancellation`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>requestReservationCancellation(button.dataset.requestReservationCancellation))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1205:120 — linha 1205

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1205)

```javascript
()=>
```

Executa a etapa `callback@1205:120` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requestReservationCancellation`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1206:75 — linha 1206

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1206)

```javascript
button=>
```

Executa a etapa `callback@1206:75` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `decideOrderCancellation`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>decideOrderCancellation(button.dataset.orderCancellationDecision,button.dataset.accepted==='true'))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1206:115 — linha 1206

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1206)

```javascript
()=>
```

Executa a etapa `callback@1206:115` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `decideOrderCancellation`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1207:81 — linha 1207

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1207)

```javascript
button=>
```

Executa a etapa `callback@1207:81` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `decideReservationCancellation`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>decideReservationCancellation(button.dataset.reservationCancellationDecision,button.dataset.accepted==='true'))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1207:121 — linha 1207

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1207)

```javascript
()=>
```

Executa a etapa `callback@1207:121` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `decideReservationCancellation`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1208:63 — linha 1208

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1208)

```javascript
button=>
```

Executa a etapa `callback@1208:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `setDefaultAddress`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>setDefaultAddress(button.dataset.defaultAddress))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1208:103 — linha 1208

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1208)

```javascript
()=>
```

Executa a etapa `callback@1208:103` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setDefaultAddress`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1209:62 — linha 1209

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1209)

```javascript
button=>
```

Executa a etapa `callback@1209:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `deleteAddress`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>deleteAddress(button.dataset.deleteAddress))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1209:102 — linha 1209

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1209)

```javascript
()=>
```

Executa a etapa `callback@1209:102` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `deleteAddress`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
()=>deleteAddress(button.dataset.deleteAddress)
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1210:58 — linha 1210

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1210)

```javascript
button=>
```

Executa a etapa `callback@1210:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `button.addEventListener`; `lookupCep`; `button.closest`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
button=>button.addEventListener('click',()=>lookupCep(button.closest('form')))
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1210:98 — linha 1210

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1210)

```javascript
()=>
```

Executa a etapa `callback@1210:98` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `lookupCep`; `button.closest`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1211:51 — linha 1211

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1211)

```javascript
input=>
```

Executa a etapa `callback@1211:51` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `input.addEventListener`; `input.value.replace`; `lookupCep`; `input.closest`.

**Decisões condicionais examinadas:**
- `(input.value.replace(/\D/g,'').length===8)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
input=>input.addEventListener('blur',()=>{if(input.value.replace(/\D/g,'').length===8)lookupCep(input.closest('form'));})
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1211:88 — linha 1211

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1211)

```javascript
()=>
```

Executa a etapa `callback@1211:88` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `input.value.replace`; `lookupCep`; `input.closest`.

**Decisões condicionais examinadas:**
- `(input.value.replace(/\D/g,'').length===8)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setDefaultAddress — linha 1214

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1214)

```javascript
async function setDefaultAddress(id)
```

Executa a etapa `set Default Address` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `loadAddresses`; `persistAccountHint`; `notify`; `render`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try { await api(`/account/addresses/${id}/default`,{method:'PATCH'});await loadAddresses();state.profile=await api('/account/me');state.account=state.profile;persistAccountHint(state.account);notify('Endereço principal atualizado','Este será o endereço sugerido nos próximos pedidos.');render(); }
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deleteAddress — linha 1219

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1219)

```javascript
async function deleteAddress(id)
```

Executa a etapa `delete Address` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `window.confirm`; `api`; `loadAddresses`; `persistAccountHint`; `notify`; `render`.

**Decisões condicionais examinadas:**
- `(!window.confirm('Remover este endereço salvo?'))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try { await api(`/account/addresses/${id}`,{method:'DELETE'});await loadAddresses();state.profile=await api('/account/me');state.account=state.profile;persistAccountHint(state.account);notify('Endereço removido','O endereço foi retirado da sua lista.','info');render(); }
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### saveCommission — linha 1225

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1225)

```javascript
async function saveCommission(event)
```

Executa a etapa `save Commission` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `Number`; `new FormData`; `Number.isFinite`; `notify`; `api`; `JSON.stringify`; `render`.

**Decisões condicionais examinadas:**
- `(!Number.isFinite(percent)||percent<0||percent>50)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function saveCommission(event){event.preventDefault();const id=event.currentTarget.dataset.storeCommission;const percent=Number(new FormData(event.currentTarget).get('percent'));if(!Number.isFinite(percent)||percent<0||percent>50){notify('Comissão inválida','Informe um percentual entre 0% e 50%.','error');return;}try{await api(`/admin/stores/${id}/commission`,{method:'PATCH',body:JSON.stringify({percent})});state.adminStores=await api('/admin/stores');notify('Comissão atualizada','A nova comissão será aplicada aos próximos pagamentos da loja.');render();}catch(e){notify('Não foi possível salvar a comissão',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### connectMercadoPago — linha 1227

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1227)

```javascript
async function connectMercadoPago(event)
```

Executa a etapa `connect Mercado Pago` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `window.location.assign`; `notify`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async function connectMercadoPago(event){const button=event.currentTarget;button.disabled=true;try{const result=await api(`/merchant/stores/${button.dataset.connectMercadopago}/mercadopago/connect`,{method:'POST'});window.location.assign(result.authorizationUrl);}catch(e){button.disabled=false;notify('Não foi possível conectar a conta',e.message,'error');}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### resumeOrderPayment — linha 1229

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1229)

```javascript
function resumeOrderPayment(id)
```

Executa a etapa `resume Order Payment` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.orders.find`; `String`; `navigate`.

**Decisões condicionais examinadas:**
- `(!order)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1229:63 — linha 1229

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1229)

```javascript
item=>
```

Executa a etapa `callback@1229:63` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### resumeReservationPayment — linha 1230

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1230)

```javascript
function resumeReservationPayment(id)
```

Executa a etapa `resume Reservation Payment` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.reservations.find`; `String`; `navigate`.

**Decisões condicionais examinadas:**
- `(!reservation)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1230:81 — linha 1230

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1230)

```javascript
item=>
```

Executa a etapa `callback@1230:81` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### bindEvents — linha 1232

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1232)

```javascript
function bindEvents()
```

Executa a etapa `bind Events` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `app.addEventListener`; `document.querySelectorAll`; `menu.contains`; `event.target.closest`; `event.preventDefault`; `showLogoutConfirmation`; `navigate`; `document.addEventListener`; `menu.querySelector`.

**Decisões condicionais examinadas:**
- `(!menu.contains(event.target))`
- `(logout)`
- `(page)`
- `(event.key==='Escape')`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
app.addEventListener('click', event => {
document.addEventListener('keydown',event=>{
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1233:33 — linha 1233

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1233)

```javascript
event =>
```

Executa a etapa `callback@1233:33` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.querySelectorAll`; `menu.contains`; `event.target.closest`; `event.preventDefault`; `showLogoutConfirmation`; `navigate`.

**Decisões condicionais examinadas:**
- `(!menu.contains(event.target))`
- `(logout)`
- `(page)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1234:67 — linha 1234

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1234)

```javascript
menu=>
```

Executa a etapa `callback@1234:67` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `menu.contains`.

**Decisões condicionais examinadas:**
- `(!menu.contains(event.target))`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1238:39 — linha 1238

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1238)

```javascript
event=>
```

Executa a etapa `callback@1238:39` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.querySelectorAll`; `menu.querySelector`.

**Decisões condicionais examinadas:**
- `(event.key==='Escape')`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1239:91 — linha 1239

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1239)

```javascript
menu=>
```

Executa a etapa `callback@1239:91` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `menu.querySelector`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1243:36 — linha 1243

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1243)

```javascript
async()=>
```

Executa a etapa `callback@1243:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `location.hash.slice`; `knownPage`; `navByRole[role`; `role`; `['categories','categoryStores'].includes`; `canShop`; `stopChat`; `loadCurrentPage`; `render`.

**Decisões condicionais examinadas:**
- `(knownPage(page)&&(navByRole[role()]?.some(([allowed])=>allowed===page)||(['categories','categoryStores'].includes(page)&&canShop())))`
- `(state.page!==page)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1243:125 — linha 1243

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1243)

```javascript
([allowed])=>
```

Executa a etapa `callback@1243:125` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### showLogoutConfirmation — linha 1245

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1245)

```javascript
function showLogoutConfirmation()
```

Executa a etapa `show Logout Confirmation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `$`; `document.createElement`; `document.body.append`; `dialog.querySelector`; `dialog.remove`; `dialog.addEventListener`; `performLogout`.

**Decisões condicionais examinadas:**
- `(event.target===dialog)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
dialog.innerHTML='<section class="confirm-dialog" role="dialog" aria-modal="true" aria-labelledby="logout-title"><span class="confirm-mark">↗</span><h2 id="logout-title">Quer sair da sua conta?</h2><p>Você precisará entrar novamente para acessar seus pedidos e dados.</p><div class="confirm-actions"><button type="button" class="btn outline" data-cancel-logout>Continuar conectado</button><button type="button" class="btn danger" data-confirm-logout>Sair da conta</button></div></section>';
dialog.querySelector('[data-cancel-logout]').addEventListener('click',()=>dialog.remove());
dialog.addEventListener('click',event=>{if(event.target===dialog)dialog.remove();});
dialog.querySelector('[data-confirm-logout]').addEventListener('click',()=>{dialog.remove();performLogout();});
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1250:73 — linha 1250

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1250)

```javascript
()=>
```

Executa a etapa `callback@1250:73` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `dialog.remove`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1251:35 — linha 1251

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1251)

```javascript
event=>
```

Executa a etapa `callback@1251:35` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `dialog.remove`.

**Decisões condicionais examinadas:**
- `(event.target===dialog)`

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1252:74 — linha 1252

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1252)

```javascript
()=>
```

Executa a etapa `callback@1252:74` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `dialog.remove`; `performLogout`.

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### performLogout — linha 1255

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1255)

```javascript
async function performLogout()
```

Executa a etapa `perform Logout` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `notify`; `resetChatState`; `stopNotificationPolling`; `stopCourierLocation`; `localStorage.removeItem`; `readLocalNotifications`; `history.replaceState`; `render`.

**Decisões condicionais examinadas:**
- `(location.hash)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try { await api('/auth/logout',{method:'POST'}); }
resetChatState();stopNotificationPolling();stopCourierLocation();localStorage.removeItem('pronto_account');state.account=null;state.profile=null;state.cart=[];state.addresses=[];state.selectedAddressId='';state.addressFormOpen=false;state.orders=[];state.reservations=[];state.deliveries=[];state.offers=[];state.ownStores=[];state.checkout=null;state.selectedStore=null;state.storeItems=[];state.paymentConnection=null;state.notifications=readLocalNotifications(null);state.authMode='login';state.page='home';if(location.hash)history.replaceState({page:'home'},'',location.pathname+location.search);render();notify('Você saiu da conta','Até a próxima!','info',false);
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@1265:14 — linha 1265

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1265)

```javascript
function()
```

Executa a etapa `callback@1265:14` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `el.matches?.`; `originalRenderPage`; `bindPageActions`; `document.getElementById`; `next.focus`; `next.setSelectionRange`.

**Decisões condicionais examinadas:**
- `el&&el.id&&el.matches?.('#page-content textarea, #page-content input')`
- `(keep)`
- `(next)`
- `(next.value!==keep.value)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if(keep){const next=document.getElementById(keep.id);if(next){if(next.value!==keep.value)next.value=keep.value;next.focus({preventScroll:true});try{next.setSelectionRange(keep.start,keep.end);}catch{/* tipos sem seleção */}}}
```

**Decisão de engenharia:** Coordenação das telas, estado e eventos. O servidor continua responsável por permissão, dinheiro, estoque e agenda. O tamanho deste módulo torna a extração gradual de telas preferível a uma reescrita simultânea. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/mobileNavigation.js

Navegação móvel e prioridade de destinos. As opções devem permanecer acessíveis sem depender de rolagem horizontal.

Fonte: [mobileNavigation.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/mobileNavigation.js) · SHA-256 `4604fcc1df562fe43cff7697bb29b4a535d3d949487496500842bf240f179d86`

### mobileNavigation — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/mobileNavigation.js:8)

```javascript
function mobileNavigation(links, role, limit = 4)
```

Executa a etapa `mobile Navigation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `links.map`; ``; `byPage.get`; `ordered.some`; `ordered.push`; `ordered.slice`.

**Decisões condicionais examinadas:**
- `(!ordered.some(([page]) => page === link[0]))`

**Saída declarada:**
- `return { visible: ordered.slice(0, limit), hasMore: ordered.length > limit };`

**Decisão de engenharia:** Navegação móvel e prioridade de destinos. As opções devem permanecer acessíveis sem depender de rolagem horizontal. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@9:36 — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/mobileNavigation.js:9)

```javascript
link =>
```

Executa a etapa `callback@9:36` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Navegação móvel e prioridade de destinos. As opções devem permanecer acessíveis sem depender de rolagem horizontal. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@10:48 — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/mobileNavigation.js:10)

```javascript
page =>
```

Executa a etapa `callback@10:48` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `byPage.get`.

**Decisão de engenharia:** Navegação móvel e prioridade de destinos. As opções devem permanecer acessíveis sem depender de rolagem horizontal. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@12:23 — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/mobileNavigation.js:12)

```javascript
([page]) =>
```

Executa a etapa `callback@12:23` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Navegação móvel e prioridade de destinos. As opções devem permanecer acessíveis sem depender de rolagem horizontal. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/postalCode.js

Consulta de CEP com alternativa local de homologação. Falhas de conexão precisam permitir correção manual e não bloquear toda a navegação.

Fonte: [postalCode.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/postalCode.js) · SHA-256 `c329963ba5a2ea2fdcf0da76bd4ff7a0627b163d26130ffba4e75f9f2a2d876d`

### postalCodeResponse — linha 1

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/postalCode.js:1)

```javascript
async function postalCodeResponse(cep, options={})
```

Executa a etapa `postal Code Response` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `fetch`.

**Decisões condicionais examinadas:**
- `(!globalThis.FOODDELIVERY_DEMO)`
- `cep==='19645000'`
- `cep==='19640000'`

**Saída declarada:**
- `return fetch(\`https://viacep.com.br/ws/${cep}/json/\`,options);`
- `return {ok:true,json:async()=>({...data,logradouro:'Rua de Demonstração',bairro:'Centro'})};`

**Decisão de engenharia:** Consulta de CEP com alternativa local de homologação. Falhas de conexão precisam permitir correção manual e não bloquear toda a navegação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@4:24 — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/postalCode.js:4)

```javascript
async()=>
```

Executa a etapa `callback@4:24` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Consulta de CEP com alternativa local de homologação. Falhas de conexão precisam permitir correção manual e não bloquear toda a navegação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/storeCovers.js

Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise.

Fonte: [storeCovers.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js) · SHA-256 `60706ba584a99322c5cc245fc6b1ca5ad92726e83f0b5f7cede2810fd5323d6d`

### coverPlacement — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:6)

```javascript
function coverPlacement(width,height,mode='contain')
```

Executa a etapa `cover Placement` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Number.isFinite`; `Math.max`; `Math.min`.

**Rejeições locais:**
- `throw new Error('A imagem deve ter até 4.096 × 4.096 pixels.');`

**Decisões condicionais examinadas:**
- `(!Number.isFinite(width)||!Number.isFinite(height)||width<1||height<1||width>4096||height>4096)`
- `mode==='crop'`

**Saída declarada:**
- `return {x:(COVER_WIDTH-width*scale)/2,y:(COVER_HEIGHT-height*scale)/2,width:width*scale,height:height*scale};`

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mountStoreCover — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:12)

```javascript
async function mountStoreCover(root,store,{onSaved=()=>{},onError=()=>{}}={})
```

Executa a etapa `mount Store Cover` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `root.querySelector`; `form.querySelector`; `document.createElement`; `apiAssetUrl`; `container.replaceChildren`; `canvas.getContext`; `coverPlacement`; `context.fillRect`; `context.drawImage`; `file.addEventListener`; `['image/png','image/jpeg'].includes`; `URL.createObjectURL`; `URL.revokeObjectURL`; `draw`; `fit.addEventListener`; `api`; `encodeURIComponent`; `published.replaceChildren`; `published.append`; `showImage`; `container.append`; `onError`; `form.addEventListener`; `event.preventDefault`; `canvas.toDataURL`; `JSON.stringify`; `refresh`; `onSaved`.

**Rejeições locais:**
- `throw new Error('A imagem processada ultrapassa 5 MB. Escolha uma imagem mais simples.');`

**Decisões condicionais examinadas:**
- `(!image)`
- `(!selected)`
- `(!['image/png','image/jpeg'].includes(selected.type)||selected.size>COVER_MAX_BYTES)`
- `(ticket!==reading||!root.isConnected)`
- `(ticket===reading)`
- `(!root.isConnected)`
- `(result.publishedUrl)`
- `s`
- `(s&&s.status!=='APPROVED')`
- `(s.reviewNote)`
- `(root.isConnected)`
- `(!image||busy)`
- `(dataUrl.length>7_000_000)`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
root.innerHTML=`<div class="section-head"><div><span class="eyebrow">Identidade do seu comércio</span><h3>Capa da loja</h3><p>Envie uma foto do comércio ou sua logo. A publicação depende da aprovação do administrador.</p></div><span class="cover-ratio-badge">2:1 · 1.200 × 600 px</span></div><div class="cover-published"></div><p class="cover-status" role="status">Consultando o último envio…</p><div class="cover-submission"></div><form class="cover-upload-form"><label class="field"><span>Escolher foto ou logo</span><input type="file" name="cover" accept="image/png,image/jpeg" required/><small>PNG ou JPG, até 5 MB e 4.096 × 4.096 pixels. A prévia será ajustada ao tamanho da capa.</small></label><label class="field"><span>Como ajustar a imagem?</span><select name="fit"><option value="contain">Logo: mostrar tudo</option><option value="crop">Foto: preencher a capa</option></select><small>Logo: preserva a imagem inteira com margens. Foto: preenche a capa com recorte central, sem distorção.</small></label><div class="cover-local-preview" hidden><canvas width="1200" height="600" role="img" aria-label="Prévia da capa antes do envio"></canvas><p>Confira o enquadramento. Esta prévia ainda não foi enviada nem publicada.</p></div><p class="cover-error" role="alert"></p><button class="btn" type="submit" disabled>Enviar para aprovação</button></form>`;
file.addEventListener('change',()=>{
const selected=file.files?.[0],ticket=++reading;image=null;submit.disabled=true;error.textContent='';root.querySelector('.cover-local-preview').hidden=true;
if(!['image/png','image/jpeg'].includes(selected.type)||selected.size>COVER_MAX_BYTES){error.textContent='Escolha um arquivo PNG ou JPG de até 5 MB.';file.value='';return;}
loaded.onload=()=>{URL.revokeObjectURL(url);if(ticket!==reading||!root.isConnected)return;try{coverPlacement(loaded.naturalWidth,loaded.naturalHeight);image=loaded;draw();}catch(e){error.textContent=e.message;file.value='';}};
loaded.onerror=()=>{URL.revokeObjectURL(url);if(ticket===reading){error.textContent='Não foi possível ler essa imagem. Escolha outro arquivo.';file.value='';}};
fit.addEventListener('change',draw);
try{const result=await api(`/merchant/stores/${encodeURIComponent(store.id)}/cover-submission`);if(!root.isConnected)return;
if(result.publishedUrl){const label=document.createElement('p');label.textContent='Capa aprovada';published.append(label);const frame=document.createElement('div');published.append(frame);showImage(frame,result.publishedUrl,'Capa aprovada da loja');}
status.textContent=s?labels[s.status]||'Confira o último envio.':'Nenhuma imagem enviada para análise.';
if(s&&s.status!=='APPROVED'){showImage(container,s.imageUrl,'Imagem enviada para análise');if(s.reviewNote){const note=document.createElement('p');note.textContent='Motivo: '+s.reviewNote;container.append(note);}}
}catch(e){if(root.isConnected){status.textContent='Não foi possível consultar o envio. Reabra esta seção para tentar novamente.';onError(e);}}
form.addEventListener('submit',async event=>{
event.preventDefault();if(!image||busy)return;busy=true;submit.disabled=true;file.disabled=true;fit.disabled=true;error.textContent='';submit.textContent='Enviando imagem…';
await api(`/merchant/stores/${encodeURIComponent(store.id)}/cover`,{method:'POST',body:JSON.stringify({dataUrl})});
}catch(e){if(root.isConnected)error.textContent=e.message;}
finally{busy=false;submit.disabled=!image;file.disabled=false;fit.disabled=false;submit.textContent='Enviar para aprovação';}
```

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@12:59 — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:12)

```javascript
()=>
```

Executa a etapa `callback@12:59` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@12:74 — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:12)

```javascript
()=>
```

Executa a etapa `callback@12:74` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### showImage — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:17)

```javascript
(container,url,alt)=>
```

Executa a etapa `show Image` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.createElement`; `apiAssetUrl`; `container.replaceChildren`.

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### draw — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:18)

```javascript
function draw()
```

Executa a etapa `draw` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `canvas.getContext`; `coverPlacement`; `context.fillRect`; `context.drawImage`; `root.querySelector`.

**Decisões condicionais examinadas:**
- `(!image)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@25:34 — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:25)

```javascript
()=>
```

Executa a etapa `callback@25:34` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `root.querySelector`; `['image/png','image/jpeg'].includes`; `URL.createObjectURL`; `URL.revokeObjectURL`; `coverPlacement`; `draw`.

**Decisões condicionais examinadas:**
- `(!selected)`
- `(!['image/png','image/jpeg'].includes(selected.type)||selected.size>COVER_MAX_BYTES)`
- `(ticket!==reading||!root.isConnected)`
- `(ticket===reading)`

**Saída declarada:**
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const selected=file.files?.[0],ticket=++reading;image=null;submit.disabled=true;error.textContent='';root.querySelector('.cover-local-preview').hidden=true;
if(!['image/png','image/jpeg'].includes(selected.type)||selected.size>COVER_MAX_BYTES){error.textContent='Escolha um arquivo PNG ou JPG de até 5 MB.';file.value='';return;}
loaded.onload=()=>{URL.revokeObjectURL(url);if(ticket!==reading||!root.isConnected)return;try{coverPlacement(loaded.naturalWidth,loaded.naturalHeight);image=loaded;draw();}catch(e){error.textContent=e.message;file.value='';}};
loaded.onerror=()=>{URL.revokeObjectURL(url);if(ticket===reading){error.textContent='Não foi possível ler essa imagem. Escolha outro arquivo.';file.value='';}};
```

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@30:19 — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:30)

```javascript
()=>
```

Executa a etapa `callback@30:19` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `URL.revokeObjectURL`; `coverPlacement`; `draw`.

**Decisões condicionais examinadas:**
- `(ticket!==reading||!root.isConnected)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
()=>{URL.revokeObjectURL(url);if(ticket!==reading||!root.isConnected)return;try{coverPlacement(loaded.naturalWidth,loaded.naturalHeight);image=loaded;draw();}catch(e){error.textContent=e.message;file.value='';}}
```

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@31:20 — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:31)

```javascript
()=>
```

Executa a etapa `callback@31:20` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `URL.revokeObjectURL`.

**Decisões condicionais examinadas:**
- `(ticket===reading)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
()=>{URL.revokeObjectURL(url);if(ticket===reading){error.textContent='Não foi possível ler essa imagem. Escolha outro arquivo.';file.value='';}}
```

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refresh — linha 35

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:35)

```javascript
async function refresh()
```

Executa a etapa `refresh` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `encodeURIComponent`; `root.querySelector`; `published.replaceChildren`; `document.createElement`; `published.append`; `showImage`; `container.replaceChildren`; `container.append`; `onError`.

**Decisões condicionais examinadas:**
- `(!root.isConnected)`
- `(result.publishedUrl)`
- `s`
- `(s&&s.status!=='APPROVED')`
- `(s.reviewNote)`
- `(root.isConnected)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try{const result=await api(`/merchant/stores/${encodeURIComponent(store.id)}/cover-submission`);if(!root.isConnected)return;
if(result.publishedUrl){const label=document.createElement('p');label.textContent='Capa aprovada';published.append(label);const frame=document.createElement('div');published.append(frame);showImage(frame,result.publishedUrl,'Capa aprovada da loja');}
status.textContent=s?labels[s.status]||'Confira o último envio.':'Nenhuma imagem enviada para análise.';
if(s&&s.status!=='APPROVED'){showImage(container,s.imageUrl,'Imagem enviada para análise');if(s.reviewNote){const note=document.createElement('p');note.textContent='Motivo: '+s.reviewNote;container.append(note);}}
}catch(e){if(root.isConnected){status.textContent='Não foi possível consultar o envio. Reabra esta seção para tentar novamente.';onError(e);}}
```

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@45:34 — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:45)

```javascript
async event=>
```

Executa a etapa `callback@45:34` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `canvas.toDataURL`; `api`; `encodeURIComponent`; `JSON.stringify`; `root.querySelector`; `refresh`; `onSaved`.

**Rejeições locais:**
- `throw new Error('A imagem processada ultrapassa 5 MB. Escolha uma imagem mais simples.');`

**Decisões condicionais examinadas:**
- `(!image||busy)`
- `(dataUrl.length>7_000_000)`
- `(!root.isConnected)`
- `(root.isConnected)`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
event.preventDefault();if(!image||busy)return;busy=true;submit.disabled=true;file.disabled=true;fit.disabled=true;error.textContent='';submit.textContent='Enviando imagem…';
await api(`/merchant/stores/${encodeURIComponent(store.id)}/cover`,{method:'POST',body:JSON.stringify({dataUrl})});
}catch(e){if(root.isConnected)error.textContent=e.message;}
finally{busy=false;submit.disabled=!image;file.disabled=false;fit.disabled=false;submit.textContent='Enviar para aprovação';}
```

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mountCoverReview — linha 56

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:56)

```javascript
async function mountCoverReview(root,{onSaved=()=>{}}={})
```

Executa a etapa `mount Cover Review` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `api`; `root.replaceChildren`; `document.createElement`; `root.append`; `apiAssetUrl`; `form.addEventListener`; `event.preventDefault`; `form.elements.note.value.trim`; `form.querySelector`; `form.elements.note.focus`; `form.querySelectorAll`; `buttons.some`; `buttons.forEach`; `encodeURIComponent`; `JSON.stringify`; `mountCoverReview`; `onSaved`; `card.append`; `p.setAttribute`.

**Decisões condicionais examinadas:**
- `(!root.isConnected)`
- `(!submissions.length)`
- `(!approved&&!note)`
- `(buttons.some(b=>b.disabled))`
- `(root.isConnected)`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
root.innerHTML='<p role="status">Carregando imagens para aprovação…</p>';
try{const submissions=await api('/admin/store-covers');if(!root.isConnected)return;root.replaceChildren();
if(!submissions.length){const p=document.createElement('p');p.className='cover-empty';p.textContent='Nenhuma imagem aguardando aprovação.';root.append(p);return;}
const title=document.createElement('h3');title.textContent=s.storeName;
const owner=document.createElement('p');owner.textContent='Vendedor: '+s.ownerName;
const form=document.createElement('form');form.innerHTML='<label class="field"><span>Motivo da recusa ou orientação ao vendedor</span><textarea name="note" maxlength="500" placeholder="Obrigatório para recusar a imagem"></textarea></label><p class="cover-error" role="alert"></p><div class="action-row"><button class="btn" type="submit" value="approve">Aprovar e publicar</button><button class="btn danger" type="submit" value="reject">Recusar imagem</button></div>';
form.addEventListener('submit',async event=>{event.preventDefault();const approved=event.submitter?.value==='approve',note=form.elements.note.value.trim(),error=form.querySelector('.cover-error');error.textContent='';if(!approved&&!note){error.textContent='Informe o motivo para o vendedor corrigir a imagem.';form.elements.note.focus();return;}
try{await api(`/admin/store-covers/${encodeURIComponent(s.id)}/review`,{method:'PATCH',body:JSON.stringify({approved,note})});await mountCoverReview(root,{onSaved});onSaved(approved);}
catch(e){error.textContent=e.message;buttons.forEach(b=>b.disabled=false);}
}catch(e){if(root.isConnected){root.replaceChildren();const p=document.createElement('p');p.className='cover-error';p.setAttribute('role','alert');p.textContent=e.message;const retry=document.createElement('button');retry.className='btn outline';retry.textContent='Tentar novamente';retry.onclick=()=>mountCoverReview(root,{onSaved});root.append(p,retry);}}
```

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@56:54 — linha 56

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:56)

```javascript
()=>
```

Executa a etapa `callback@56:54` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@66:38 — linha 66

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:66)

```javascript
async event=>
```

Executa a etapa `callback@66:38` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `event.preventDefault`; `form.elements.note.value.trim`; `form.querySelector`; `form.elements.note.focus`; `form.querySelectorAll`; `buttons.some`; `buttons.forEach`; `api`; `encodeURIComponent`; `JSON.stringify`; `mountCoverReview`; `onSaved`.

**Decisões condicionais examinadas:**
- `(!approved&&!note)`
- `(buttons.some(b=>b.disabled))`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
async event=>{event.preventDefault();const approved=event.submitter?.value==='approve',note=form.elements.note.value.trim(),error=form.querySelector('.cover-error');error.textContent='';if(!approved&&!note){error.textContent='Informe o motivo para o vendedor corrigir a imagem.';form.elements.note.focus();return;}
try{await api(`/admin/store-covers/${encodeURIComponent(s.id)}/review`,{method:'PATCH',body:JSON.stringify({approved,note})});await mountCoverReview(root,{onSaved});onSaved(approved);}
catch(e){error.textContent=e.message;buttons.forEach(b=>b.disabled=false);}
```

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@67:76 — linha 67

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:67)

```javascript
b=>
```

Executa a etapa `callback@67:76` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@67:114 — linha 67

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:67)

```javascript
b=>
```

Executa a etapa `callback@67:114` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@69:62 — linha 69

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:69)

```javascript
b=>
```

Executa a etapa `callback@69:62` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@73:300 — linha 73

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeCovers.js:73)

```javascript
()=>
```

Executa a etapa `callback@73:300` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `mountCoverReview`.

**Decisão de engenharia:** Envio e moderação de capas. A imagem é normalizada para 1200 × 600, com enquadramento sem distorção. A API valida tamanho e conteúdo; apenas ADMIN publica, com prévias privadas e preservação da capa aprovada enquanto há análise. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/storeHours.js

Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas.

Fonte: [storeHours.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js) · SHA-256 `a10da76ba85d3760452d3bf806ad4cc437b9ab662e1df592ee6ef83ebbf3ae18`

### esc — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:4)

```javascript
value=>
```

Executa a etapa `esc` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@4:55 — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:4)

```javascript
c=>
```

Executa a etapa `callback@4:55` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### today — linha 5

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:5)

```javascript
function today(zone)
```

Executa a etapa `today` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Object.fromEntries`; `new Intl.DateTimeFormat`.

**Saída declarada:**
- `return \`${p.year}-${p.month}-${p.day}\`;`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@5:176 — linha 5

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:5)

```javascript
p=>
```

Executa a etapa `callback@5:176` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@5:203 — linha 5

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:5)

```javascript
p=>
```

Executa a etapa `callback@5:203` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### allDay — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:6)

```javascript
function allDay(shifts)
```

Executa a etapa `all Day` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return shifts.length===1&&shifts[0].opensAt==='00:00'&&shifts[0].closesAt==='24:00';`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### describe — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:7)

```javascript
function describe(shifts)
```

Executa a etapa `describe` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `allDay`; `shifts.map`.

**Decisões condicionais examinadas:**
- `allDay(shifts)`
- `shifts.length`
- `v.closesAt==='24:00'`
- `v.closesAt<v.opensAt`

**Saída declarada:**
- `return allDay(shifts)?'24 horas':shifts.length?shifts.map(v=>\`${v.opensAt}–${v.closesAt==='24:00'?'00:00':v.closesAt}${v.closesAt<v.opensAt?' (dia seguinte)':''}\`).join(' e '):'Fechada';`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@7:85 — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:7)

```javascript
v=>
```

Executa a etapa `callback@7:85` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `v.closesAt==='24:00'`
- `v.closesAt<v.opensAt`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderPublicHours — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:8)

```javascript
function renderPublicHours(configuration)
```

Executa a etapa `render Public Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `days.map`; `esc`; `describe`; `intervalsForDay`; `configuration.exceptions.map`; `e.date.split`.

**Decisões condicionais examinadas:**
- `(!configuration)`
- `configuration.enabled`
- `(configuration.exceptions||[]).length`
- `e.closed`

**Saída declarada:**
- `return '';`
- `return \`<details class="public-hours"><summary>Horários de funcionamento</summary><p>${configuration.enabled?'O agendamento deve caber por inteiro em um turno.':'Sem restrição semanal cadastrada. Consulte a loja sobre a disponibilidade.'}</p>${configuration.enabled?\`<dl>${days.map(([key,label])=>\`<div><dt>${label}</dt><dd>${esc(describe(intervalsForDay(configuration,key)))}</dd></div>\`).join('')}</dl>\`:''}${(configuration.exceptions||[]).length?\`<h4>Datas com funcionamento especial</h4><ul>${configuration.exceptions.map(e=>\`<li>${esc(e.date.split('-').reverse().join('/'))}: ${e.closed?'Fechada':esc(describe(e.shifts??[{opensAt:e.opensAt,closesAt:e.closesAt}]))}</li>\`).join('')}</ul>\`:''}</details>\`;`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@10:288 — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:10)

```javascript
([key,label])=>
```

Executa a etapa `callback@10:288` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `describe`; `intervalsForDay`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@10:532 — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:10)

```javascript
e=>
```

Executa a etapa `callback@10:532` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `esc`; `e.date.split`; `describe`.

**Decisões condicionais examinadas:**
- `e.closed`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### addShift — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:12)

```javascript
function addShift(container,shift={opensAt:'08:00',closesAt:'18:00'})
```

Executa a etapa `add Shift` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.createElement`; `row.querySelector`; `row.remove`; `container.dispatchEvent`; `container.append`.

**Decisões condicionais examinadas:**
- `shift.closesAt==='24:00'`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
row.innerHTML='<label><span>Abre às</span><input type="time" data-open required/></label><span aria-hidden="true">até</span><label><span>Fecha às</span><input type="time" data-close required/></label><button type="button" class="btn outline small" data-remove-shift>Remover turno</button><small>Se o fechamento for antes da abertura, termina no dia seguinte.</small>';
```

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@16:39 — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:16)

```javascript
()=>
```

Executa a etapa `callback@16:39` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `row.remove`; `container.dispatchEvent`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### readShifts — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:18)

```javascript
function readShifts(container)
```

Executa a etapa `read Shifts` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `container.querySelectorAll`; `rows.map`; `row.querySelector`.

**Rejeições locais:**
- `throw new Error('Adicione ao menos um turno para cada dia aberto.');`
- `throw new Error('Confira os turnos: abertura e fechamento precisam ser diferentes. Para o dia inteiro, marque “24 horas”.');`

**Decisões condicionais examinadas:**
- `(!rows.length)`
- `(!opensAt||!closesAt||opensAt===closesAt)`

**Saída declarada:**
- `return rows.map(row=>{const opensAt=row.querySelector('[data-open]').value,closesAt=row.querySelector('[data-close]').value;if(!opensAt||!closesAt||opensAt===closesAt)throw new Error('Confira os turnos: abertura e fechamento precisam ser diferentes. Para o dia inteiro, marque “24 horas”.');return {opensAt,closesAt};});`
- `return {opensAt,closesAt};`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@18:191 — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:18)

```javascript
row=>
```

Executa a etapa `callback@18:191` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `row.querySelector`.

**Rejeições locais:**
- `throw new Error('Confira os turnos: abertura e fechamento precisam ser diferentes. Para o dia inteiro, marque “24 horas”.');`

**Decisões condicionais examinadas:**
- `(!opensAt||!closesAt||opensAt===closesAt)`

**Saída declarada:**
- `return {opensAt,closesAt};`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### shiftEditor — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:19)

```javascript
function shiftEditor(root,shifts,enabled=()=>true)
```

Executa a etapa `shift Editor` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `root.querySelector`; `enabled`; `list.querySelectorAll`; `list.replaceChildren`; `allDay`; `addShift`; `sync`; `fill`; `readShifts`.

**Decisões condicionais examinadas:**
- `whole.checked`
- `values.length`
- `(list.children.length<6)`

**Saída declarada:**
- `return {sync,fill,read:()=>whole.checked?[{opensAt:'00:00',closesAt:'24:00'}]:readShifts(list)};`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@19:42 — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:19)

```javascript
()=>
```

Executa a etapa `callback@19:42` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sync — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:21)

```javascript
function sync()
```

Executa a etapa `sync` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `enabled`; `list.querySelectorAll`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### fill — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:22)

```javascript
function fill(values)
```

Executa a etapa `fill` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `list.replaceChildren`; `allDay`; `addShift`; `sync`.

**Decisões condicionais examinadas:**
- `whole.checked`
- `values.length`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@23:15 — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:23)

```javascript
()=>
```

Executa a etapa `callback@23:15` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `addShift`; `sync`.

**Decisões condicionais examinadas:**
- `(list.children.length<6)`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@24:26 — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:24)

```javascript
()=>
```

Executa a etapa `callback@24:26` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `readShifts`.

**Decisões condicionais examinadas:**
- `whole.checked`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### editorMarkup — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:26)

```javascript
function editorMarkup()
```

Executa a etapa `editor Markup` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return '<label class="check-row"><input type="checkbox" data-whole-day/><span>24 horas</span></label><div class="hours-shifts" data-shifts></div><button class="btn outline small" type="button" data-add-shift>+ Adicionar turno</button>';`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### conflictDecision — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:27)

```javascript
function conflictDecision(preview,zone)
```

Executa a etapa `conflict Decision` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.createElement`; `preview.affected.filter`; `overlay.querySelector`; `dateTimeInZone`; `list.append`; `finish`; `observer.disconnect`; `overlay.remove`; `resolve`; `overlay.querySelectorAll`; `overlay.addEventListener`; `document.body.append`; `observer.observe`.

**Decisões condicionais examinadas:**
- `cancellable`
- `r.status==='IN_SERVICE'`
- `r.payAfterService`
- `(!overlay.isConnected)`
- `(resolved)`
- `(e.target===overlay)`
- `(e.key==='Escape')`

**Saída declarada:**
- `return new Promise(resolve=>{ const overlay=document.createElement('div');overlay.className='modal-backdrop';const cancellable=preview.affected.filter(r=>r.status!=='IN_SERVICE').length; overlay.innerHTML=\`<section class="modal hours-conflict-modal" role="dialog" aria-modal="true" aria-labelledby="hours-conflict-title"><div class="modal-head"><div><span class="eyebrow">Antes de salvar</span><h2 id="hours-conflict-title">${preview.affected.length} reserva(s) afetada(s)</h2></div><button class="close-button" type="button" data-dismiss aria-label="Fechar">×</button></div><p>O novo expediente não comporta os atendimentos abaixo. Escolha como tratar os clientes antes de confirmar.</p><ul class="hours-conflict-list"></ul><p>Manter preserva o horário combinado, inclusive o pagamento pendente. Cancelar libera o horário e solicita estorno integral de pagamentos online aprovados. O estorno depende da confirmação do provedor.</p><p class="warning-small">Atendimentos em andamento serão mantidos e avisados. Pedidos de produtos já aceitos continuam em atendimento; este ajuste não cancela entregas.</p><div class="hours-conflict-actions"><button class="btn" type="button" data-decision="KEEP">Manter reservas e avisar</button>${cancellable?'<button class="btn danger" type="button" data-decision="CANCEL">Cancelar reservas e avisar</button>':''}<button class="btn outline" type="button" data-dismiss>Voltar sem salvar</button></div></section>\`; const list=overlay.querySelector('ul');for(const r of preview.affected){const li=document.createElement('li');li.textContent=\`${r.serviceName} · ${r.customerName} · ${dateTimeInZone(r.startsAt,zone)}${r.status==='IN_SERVICE'?' · Em andamento: será mantido':r.payAfterService?' · Pagamento na loja':' · Pagamento online'}\`;list.append(li);} let resolved=false;const observer=new MutationObserver(()=>{if(!overlay.isConnected)finish(null);});function finish(action){if(resolved)return;resolved=true;observer.disconnect();overlay.remove();resolve(action);} overlay.querySelectorAll('[data-decision]').forEach(b=>b.onclick=()=>finish(b.dataset.decision));overlay.querySelectorAll('[data-dismiss]').forEach(b=>b.onclick=()=>finish(null));overlay.onclick=e=>{if(e.target===overlay)finish(null);};overlay.addEventListener('keydown',e=>{if(e.key==='Escape')finish(null);});document.body.append(overlay);observer.observe(document.body,{childList:true}); });`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
overlay.innerHTML=`<section class="modal hours-conflict-modal" role="dialog" aria-modal="true" aria-labelledby="hours-conflict-title"><div class="modal-head"><div><span class="eyebrow">Antes de salvar</span><h2 id="hours-conflict-title">${preview.affected.length} reserva(s) afetada(s)</h2></div><button class="close-button" type="button" data-dismiss aria-label="Fechar">×</button></div><p>O novo expediente não comporta os atendimentos abaixo. Escolha como tratar os clientes antes de confirmar.</p><ul class="hours-conflict-list"></ul><p>Manter preserva o horário combinado, inclusive o pagamento pendente. Cancelar libera o horário e solicita estorno integral de pagamentos online aprovados. O estorno depende da confirmação do provedor.</p><p class="warning-small">Atendimentos em andamento serão mantidos e avisados. Pedidos de produtos já aceitos continuam em atendimento; este ajuste não cancela entregas.</p><div class="hours-conflict-actions"><button class="btn" type="button" data-decision="KEEP">Manter reservas e avisar</button>${cancellable?'<button class="btn danger" type="button" data-decision="CANCEL">Cancelar reservas e avisar</button>':''}<button class="btn outline" type="button" data-dismiss>Voltar sem salvar</button></div></section>`;
const list=overlay.querySelector('ul');for(const r of preview.affected){const li=document.createElement('li');li.textContent=`${r.serviceName} · ${r.customerName} · ${dateTimeInZone(r.startsAt,zone)}${r.status==='IN_SERVICE'?' · Em andamento: será mantido':r.payAfterService?' · Pagamento na loja':' · Pagamento online'}`;list.append(li);}
overlay.querySelectorAll('[data-decision]').forEach(b=>b.onclick=()=>finish(b.dataset.decision));overlay.querySelectorAll('[data-dismiss]').forEach(b=>b.onclick=()=>finish(null));overlay.onclick=e=>{if(e.target===overlay)finish(null);};overlay.addEventListener('keydown',e=>{if(e.key==='Escape')finish(null);});document.body.append(overlay);observer.observe(document.body,{childList:true});
```

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@28:22 — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:28)

```javascript
resolve=>
```

Executa a etapa `callback@28:22` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.createElement`; `preview.affected.filter`; `overlay.querySelector`; `dateTimeInZone`; `list.append`; `finish`; `observer.disconnect`; `overlay.remove`; `resolve`; `overlay.querySelectorAll`; `overlay.addEventListener`; `document.body.append`; `observer.observe`.

**Decisões condicionais examinadas:**
- `cancellable`
- `r.status==='IN_SERVICE'`
- `r.payAfterService`
- `(!overlay.isConnected)`
- `(resolved)`
- `(e.target===overlay)`
- `(e.key==='Escape')`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
overlay.innerHTML=`<section class="modal hours-conflict-modal" role="dialog" aria-modal="true" aria-labelledby="hours-conflict-title"><div class="modal-head"><div><span class="eyebrow">Antes de salvar</span><h2 id="hours-conflict-title">${preview.affected.length} reserva(s) afetada(s)</h2></div><button class="close-button" type="button" data-dismiss aria-label="Fechar">×</button></div><p>O novo expediente não comporta os atendimentos abaixo. Escolha como tratar os clientes antes de confirmar.</p><ul class="hours-conflict-list"></ul><p>Manter preserva o horário combinado, inclusive o pagamento pendente. Cancelar libera o horário e solicita estorno integral de pagamentos online aprovados. O estorno depende da confirmação do provedor.</p><p class="warning-small">Atendimentos em andamento serão mantidos e avisados. Pedidos de produtos já aceitos continuam em atendimento; este ajuste não cancela entregas.</p><div class="hours-conflict-actions"><button class="btn" type="button" data-decision="KEEP">Manter reservas e avisar</button>${cancellable?'<button class="btn danger" type="button" data-decision="CANCEL">Cancelar reservas e avisar</button>':''}<button class="btn outline" type="button" data-dismiss>Voltar sem salvar</button></div></section>`;
const list=overlay.querySelector('ul');for(const r of preview.affected){const li=document.createElement('li');li.textContent=`${r.serviceName} · ${r.customerName} · ${dateTimeInZone(r.startsAt,zone)}${r.status==='IN_SERVICE'?' · Em andamento: será mantido':r.payAfterService?' · Pagamento na loja':' · Pagamento online'}`;list.append(li);}
overlay.querySelectorAll('[data-decision]').forEach(b=>b.onclick=()=>finish(b.dataset.decision));overlay.querySelectorAll('[data-dismiss]').forEach(b=>b.onclick=()=>finish(null));overlay.onclick=e=>{if(e.target===overlay)finish(null);};overlay.addEventListener('keydown',e=>{if(e.key==='Escape')finish(null);});document.body.append(overlay);observer.observe(document.body,{childList:true});
```

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@29:126 — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:29)

```javascript
r=>
```

Executa a etapa `callback@29:126` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@32:60 — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:32)

```javascript
()=>
```

Executa a etapa `callback@32:60` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `finish`.

**Decisões condicionais examinadas:**
- `(!overlay.isConnected)`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### finish — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:32)

```javascript
function finish(action)
```

Executa a etapa `finish` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `observer.disconnect`; `overlay.remove`; `resolve`.

**Decisões condicionais examinadas:**
- `(resolved)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@33:57 — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:33)

```javascript
b=>
```

Executa a etapa `callback@33:57` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `finish`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@33:70 — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:33)

```javascript
()=>
```

Executa a etapa `callback@33:70` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `finish`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@33:153 — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:33)

```javascript
b=>
```

Executa a etapa `callback@33:153` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `finish`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@33:166 — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:33)

```javascript
()=>
```

Executa a etapa `callback@33:166` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `finish`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@33:200 — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:33)

```javascript
e=>
```

Executa a etapa `callback@33:200` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `finish`.

**Decisões condicionais examinadas:**
- `(e.target===overlay)`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@33:276 — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:33)

```javascript
e=>
```

Executa a etapa `callback@33:276` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `finish`.

**Decisões condicionais examinadas:**
- `(e.key==='Escape')`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mountOperatingHours — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:36)

```javascript
function mountOperatingHours(root,store,{configuration,onSaved=()=>{},onError=()=>{},basePath=null}={})
```

Executa a etapa `mount Operating Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `today`; `editorMarkup`; `esc`; `root.querySelector`; `intervalsForDay`; `document.createElement`; `week.append`; `day.querySelector`; `shiftEditor`; `editors.set`; `editors.forEach`; `editor.sync`; ``; `exceptionEditor.fill`; `form.elements.date.focus`; `list.replaceChildren`; `e.date.split`; `describe`; `fillException`; `save`; `exceptions.filter`; `row.append`; `list.append`; `editor.read`; `form.querySelectorAll`; `buttons.map`; `buttons.forEach`; `weekly`; `encodeURIComponent`; `api`; `JSON.stringify`; `conflictDecision`; `preview.affected.map`; `onSaved`; `onError`; `e.preventDefault`; `exceptionEditor.read`; `form.elements.note.value.trim`; `renderExceptions`.

**Rejeições locais:**
- `throw new Error('Escolha hoje ou uma data futura.');`

**Decisões condicionais examinadas:**
- `schedule.enabled`
- `values.length`
- `e.closed`
- `e.note`
- `(!exceptions.length)`
- `(check.checked)`
- `(busy)`
- `(!root.isConnected)`
- `preview.affected.length`
- `(!action)`
- `(root.isConnected)`
- `(!date||date<today(zone))`
- `closed`

**Saída declarada:**
- `return result;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
root.innerHTML=`<div class="section-head"><div><span class="eyebrow">Disponibilidade do estabelecimento</span><h3>Funcionamento e exceções</h3><p>Defina dias, turnos e pausas. Reservas futuras podem ser feitas mesmo quando a loja está fechada agora.</p></div></div><form data-operating-hours-editor><label class="check-row operating-hours-toggle"><input name="enabled" type="checkbox" ${schedule.enabled?'checked':''}/><span><strong>Usar horário de funcionamento</strong><small>Desativado: sem restrição semanal. As exceções por data continuam valendo.</small></span></label><div class="hours-week"></div><section class="hours-special"><h4>Imprevisto ou funcionamento especial</h4><p>Feche hoje ou escolha uma data para fechar mais cedo, abrir em outro horário ou definir vários turnos.</p><button class="btn danger" type="button" data-close-today>Fechar hoje</button><div class="form-grid"><label class="field"><span>Data especial</span><input name="date" type="date" value="${today(zone)}" min="${today(zone)}"/></label><label class="check-row"><input name="closed" type="checkbox"/><span>Fechada o dia todo</span></label><label class="field span-2"><span>Observação interna (opcional)</span><input name="note" maxlength="160" placeholder="Não aparece para os clientes"/></label></div><div data-exception-editor>${editorMarkup()}</div><button class="btn outline" type="button" data-save-exception>Salvar data especial</button><div class="hours-exception-list"></div></section><p class="hours-feedback" role="status">Fuso da loja: ${esc(zone)}. Até seis turnos por dia. Abertura inclusiva e fechamento exclusivo.</p><button class="btn" type="submit">Salvar funcionamento</button></form>`;
for(const [key,label] of days){const values=intervalsForDay(schedule,key),day=document.createElement('section');day.className='hours-day';day.dataset.day=key;day.innerHTML=`<label class="check-row hours-day-toggle"><input type="checkbox" ${values.length?'checked':''}/><strong>${label}</strong></label>${editorMarkup()}`;week.append(day);const check=day.querySelector('.hours-day-toggle input'),editor=shiftEditor(day,values,()=>check.checked&&enabled.checked);editors.set(key,{check,editor});check.onchange=editor.sync;}
function renderExceptions(){const list=root.querySelector('.hours-exception-list');list.replaceChildren();for(const e of exceptions){const row=document.createElement('div');row.className='hours-exception-row';const text=document.createElement('span');text.textContent=`${e.date.split('-').reverse().join('/')} · ${e.closed?'Fechada':describe(e.shifts??[{opensAt:e.opensAt,closesAt:e.closesAt}])}${e.note?' · '+e.note:''}`;const edit=document.createElement('button');edit.type='button';edit.className='btn outline small';edit.textContent='Editar';edit.disabled=e.date<today(zone);edit.onclick=()=>fillException(e);const remove=document.createElement('button');remove.type='button';remove.className='btn danger small';remove.textContent='Remover';remove.onclick=()=>save(exceptions.filter(other=>other.date!==e.date));row.append(text,edit,remove);list.append(row);}if(!exceptions.length)list.textContent='Nenhuma exceção cadastrada.';}
if(busy)return;busy=true;const buttons=[...form.querySelectorAll('button,input,select,textarea')],states=buttons.map(b=>b.disabled);buttons.forEach(b=>b.disabled=true);const feedback=root.querySelector('.hours-feedback');feedback.textContent='Conferindo o expediente e as reservas…';
try{const payload={enabled:enabled.checked,weeklyShifts:weekly(),exceptions:next};const url=basePath||`/merchant/stores/${encodeURIComponent(store.id)}/hours`;const preview=await api(url+'/preview',{method:'POST',body:JSON.stringify(payload)});if(!root.isConnected)return;
const action=preview.affected.length?await conflictDecision(preview,zone):'KEEP';if(!action){feedback.textContent='Alteração não salva. Seu expediente continua como antes.';return;}
const result=await api(url,{method:'PUT',body:JSON.stringify({...payload,conflictAction:action,expectedAffectedIds:preview.affected.map(r=>r.id),revision:preview.revision})});const {changeSummary,...config}=result;if(root.isConnected)await onSaved(config,changeSummary);
}catch(error){feedback.textContent=error.message;onError(error);}finally{busy=false;buttons.forEach((button,i)=>button.disabled=states[i]);}
```

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@36:71 — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:36)

```javascript
()=>
```

Executa a etapa `callback@36:71` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@36:86 — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:36)

```javascript
()=>
```

Executa a etapa `callback@36:86` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@40:428 — linha 40

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:40)

```javascript
()=>
```

Executa a etapa `callback@40:428` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@41:148 — linha 41

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:41)

```javascript
()=>
```

Executa a etapa `callback@41:148` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@42:71 — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:42)

```javascript
()=>
```

Executa a etapa `callback@42:71` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `editors.forEach`; `editor.sync`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@42:91 — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:42)

```javascript
({editor})=>
```

Executa a etapa `callback@42:91` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `editor.sync`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@43:50 — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:43)

```javascript
e=>
```

Executa a etapa `callback@43:50` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### fillException — linha 44

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:44)

```javascript
function fillException(e)
```

Executa a etapa `fill Exception` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `exceptionEditor.fill`; `form.elements.date.focus`.

**Decisões condicionais examinadas:**
- `e.closed`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### renderExceptions — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:45)

```javascript
function renderExceptions()
```

Executa a etapa `render Exceptions` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `root.querySelector`; `list.replaceChildren`; `document.createElement`; `e.date.split`; `describe`; `today`; `fillException`; `save`; `exceptions.filter`; `row.append`; `list.append`.

**Decisões condicionais examinadas:**
- `e.closed`
- `e.note`
- `(!exceptions.length)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
function renderExceptions(){const list=root.querySelector('.hours-exception-list');list.replaceChildren();for(const e of exceptions){const row=document.createElement('div');row.className='hours-exception-row';const text=document.createElement('span');text.textContent=`${e.date.split('-').reverse().join('/')} · ${e.closed?'Fechada':describe(e.shifts??[{opensAt:e.opensAt,closesAt:e.closesAt}])}${e.note?' · '+e.note:''}`;const edit=document.createElement('button');edit.type='button';edit.className='btn outline small';edit.textContent='Editar';edit.disabled=e.date<today(zone);edit.onclick=()=>fillException(e);const remove=document.createElement('button');remove.type='button';remove.className='btn danger small';remove.textContent='Remover';remove.onclick=()=>save(exceptions.filter(other=>other.date!==e.date));row.append(text,edit,remove);list.append(row);}if(!exceptions.length)list.textContent='Nenhuma exceção cadastrada.';}
```

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@45:597 — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:45)

```javascript
()=>
```

Executa a etapa `callback@45:597` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `fillException`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@45:765 — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:45)

```javascript
()=>
```

Executa a etapa `callback@45:765` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `save`; `exceptions.filter`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@45:792 — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:45)

```javascript
other=>
```

Executa a etapa `callback@45:792` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### weekly — linha 46

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:46)

```javascript
function weekly()
```

Executa a etapa `weekly` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `editor.read`.

**Decisões condicionais examinadas:**
- `(check.checked)`

**Saída declarada:**
- `return result;`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### save — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:47)

```javascript
async function save(next=exceptions)
```

Executa a etapa `save` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form.querySelectorAll`; `buttons.map`; `buttons.forEach`; `root.querySelector`; `weekly`; `encodeURIComponent`; `api`; `JSON.stringify`; `conflictDecision`; `preview.affected.map`; `onSaved`; `onError`.

**Decisões condicionais examinadas:**
- `(busy)`
- `(!root.isConnected)`
- `preview.affected.length`
- `(!action)`
- `(root.isConnected)`

**Saída declarada:**
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if(busy)return;busy=true;const buttons=[...form.querySelectorAll('button,input,select,textarea')],states=buttons.map(b=>b.disabled);buttons.forEach(b=>b.disabled=true);const feedback=root.querySelector('.hours-feedback');feedback.textContent='Conferindo o expediente e as reservas…';
try{const payload={enabled:enabled.checked,weeklyShifts:weekly(),exceptions:next};const url=basePath||`/merchant/stores/${encodeURIComponent(store.id)}/hours`;const preview=await api(url+'/preview',{method:'POST',body:JSON.stringify(payload)});if(!root.isConnected)return;
const action=preview.affected.length?await conflictDecision(preview,zone):'KEEP';if(!action){feedback.textContent='Alteração não salva. Seu expediente continua como antes.';return;}
const result=await api(url,{method:'PUT',body:JSON.stringify({...payload,conflictAction:action,expectedAffectedIds:preview.affected.map(r=>r.id),revision:preview.revision})});const {changeSummary,...config}=result;if(root.isConnected)await onSaved(config,changeSummary);
}catch(error){feedback.textContent=error.message;onError(error);}finally{busy=false;buttons.forEach((button,i)=>button.disabled=states[i]);}
```

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@48:122 — linha 48

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:48)

```javascript
b=>
```

Executa a etapa `callback@48:122` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@48:153 — linha 48

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:48)

```javascript
b=>
```

Executa a etapa `callback@48:153` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@51:143 — linha 51

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:51)

```javascript
r=>
```

Executa a etapa `callback@51:143` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@52:105 — linha 52

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:52)

```javascript
(button,i)=>
```

Executa a etapa `callback@52:105` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@54:17 — linha 54

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:54)

```javascript
e=>
```

Executa a etapa `callback@54:17` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `e.preventDefault`; `save`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@55:52 — linha 55

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:55)

```javascript
()=>
```

Executa a etapa `callback@55:52` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `today`; `save`; `exceptions.filter`.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@55:107 — linha 55

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:55)

```javascript
e=>
```

Executa a etapa `callback@55:107` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@56:55 — linha 56

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:56)

```javascript
()=>
```

Executa a etapa `callback@56:55` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `today`; `exceptionEditor.read`; `save`; `exceptions.filter`; `form.elements.note.value.trim`; `onError`.

**Rejeições locais:**
- `throw new Error('Escolha hoje ou uma data futura.');`

**Decisões condicionais examinadas:**
- `(!date||date<today(zone))`
- `closed`

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@56:288 — linha 56

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:56)

```javascript
e=>
```

Executa a etapa `callback@56:288` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Editor compartilhado de turnos semanais e exceções. Consulta prévia, exige decisão sobre reservas afetadas e trata desistência, concorrência e avisos. A configuração pública não revela observações internas. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/storeLocation.js

Coordenadas e links de mapa. Coordenadas precisam formar um par válido; link de mapa não cadastra automaticamente uma empresa no Google.

Fonte: [storeLocation.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeLocation.js) · SHA-256 `d719456f47cd3a13a8261f82aea71cca58aa85cee95621add217fdf74e72cb24`

### storeMapUrl — linha 1

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeLocation.js:1)

```javascript
function storeMapUrl(store)
```

Executa a etapa `store Map Url` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `encodeURIComponent`.

**Decisões condicionais examinadas:**
- `store?.latitude != null && store?.longitude != null`

**Saída declarada:**
- `return \`https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(query)}\`;`

**Decisão de engenharia:** Coordenadas e links de mapa. Coordenadas precisam formar um par válido; link de mapa não cadastra automaticamente uma empresa no Google. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### coordinatesFromForm — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeLocation.js:8)

```javascript
function coordinatesFromForm(form)
```

Executa a etapa `coordinates From Form` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`; `form.get`; `Boolean`; `Number`; `Number.isFinite`.

**Rejeições locais:**
- `throw new Error('Informe latitude e longitude juntas ou deixe os dois campos vazios.');`
- `throw new Error('As coordenadas informadas estão fora dos limites válidos.');`

**Decisões condicionais examinadas:**
- `(Boolean(latitudeText) !== Boolean(longitudeText))`
- `(!latitudeText)`
- `(!Number.isFinite(latitude) || latitude < -90 || latitude > 90 || !Number.isFinite(longitude) || longitude < -180 || longitude > 180)`

**Saída declarada:**
- `return { latitude: null, longitude: null };`
- `return { latitude, longitude };`

**Decisão de engenharia:** Coordenadas e links de mapa. Coordenadas precisam formar um par válido; link de mapa não cadastra automaticamente uma empresa no Google. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/storeMaps.js

Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista.

Fonte: [storeMaps.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js) · SHA-256 `33e0cc1ec586b0be1a728b6d1511ad7545e8e702a4ead404bd3d67ddf7330a7f`

### cleanupStoreMaps — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:4)

```javascript
function cleanupStoreMaps()
```

Executa a etapa `cleanup Store Maps` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `entry.observer.disconnect`; `entry.map.remove`; `maps.delete`.

**Decisões condicionais examinadas:**
- `(!entry.root.isConnected)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
function cleanupStoreMaps(){for(const entry of maps)if(!entry.root.isConnected){entry.observer.disconnect();entry.map.remove();maps.delete(entry);}}
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### loadLeaflet — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:6)

```javascript
async function loadLeaflet()
```

Executa a etapa `load Leaflet` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.createElement`; `document.head.append`; `resolve`; `script.remove`; `css.remove`; `reject`; `window.__foodDeliveryLeaflet.catch`.

**Decisões condicionais examinadas:**
- `(window.L)`
- `(!window.__foodDeliveryLeaflet)`

**Saída declarada:**
- `return window.L;`
- `return window.__foodDeliveryLeaflet;`

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@9:46 — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:9)

```javascript
(resolve,reject)=>
```

Executa a etapa `callback@9:46` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `document.createElement`; `document.head.append`; `resolve`; `script.remove`; `css.remove`; `reject`.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@12:21 — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:12)

```javascript
()=>
```

Executa a etapa `callback@12:21` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `resolve`.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@12:58 — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:12)

```javascript
()=>
```

Executa a etapa `callback@12:58` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `script.remove`; `css.remove`; `reject`.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@14:40 — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:14)

```javascript
()=>
```

Executa a etapa `callback@14:40` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createMap — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:18)

```javascript
function createMap(L,root)
```

Executa a etapa `create Map` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `L.map`; `L.control.zoom`; `L.tileLayer`; `document.createElement`; `message.setAttribute`; `root.append`; `tiles.on`; `map.invalidateSize`; `observer.observe`; `maps.add`.

**Saída declarada:**
- `return map;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const map=L.map(root,{scrollWheelZoom:false,zoomControl:false}).setView([-14.235,-51.925],4);
const message=document.createElement('p');message.className='store-map-tile-status';message.setAttribute('role','status');message.hidden=true;root.append(message);
tiles.on('tileerror',()=>{message.hidden=false;message.textContent='Parte do mapa não carregou. Confira sua conexão; o endereço e a lista de lojas continuam disponíveis.';});
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@23:24 — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:23)

```javascript
()=>
```

Executa a etapa `callback@23:24` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
()=>{message.hidden=false;message.textContent='Parte do mapa não carregou. Confira sua conexão; o endereço e a lista de lojas continuam disponíveis.';}
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@24:23 — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:24)

```javascript
()=>
```

Executa a etapa `callback@24:23` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@25:37 — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:25)

```javascript
()=>
```

Executa a etapa `callback@25:37` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `map.invalidateSize`.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### point — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:27)

```javascript
function point(store)
```

Executa a etapa `point` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Number.isFinite`; `Number`; `Math.abs`.

**Saída declarada:**
- `return store.latitude!=null&&store.longitude!=null&&Number.isFinite(Number(store.latitude))&&Number.isFinite(Number(store.longitude))&&Math.abs(Number(store.latitude))<=90&&Math.abs(Number(store.longitude))<=180;`

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### markerIcon — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:28)

```javascript
function markerIcon(L)
```

Executa a etapa `marker Icon` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `L.divIcon`.

**Saída declarada:**
- `return L.divIcon({className:'shop-map-pin',html:'<span aria-hidden="true">●</span>',iconSize:[30,38],iconAnchor:[15,38]});`

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeMapFields — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:30)

```javascript
function storeMapFields(store={})
```

Executa a etapa `store Map Fields` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `point`; `Number`.

**Decisões condicionais examinadas:**
- `point(store)`
- `lat!==''`

**Saída declarada:**
- `return \`<section class="store-map-editor span-2" data-store-map-picker><div class="section-head"><div><h3>Localização no mapa <small>(opcional)</small></h3><p>Marque a entrada do comércio para facilitar a chegada. O ponto salvo ficará público quando a loja for aprovada.</p></div></div><button class="btn outline" type="button" data-map-toggle>Abrir mapa para marcar</button><div data-map-content hidden><label class="field"><span>Buscar endereço ou cidade</span><input name="mapSearch" maxlength="100" placeholder="Ex.: Rua Central, Nantes, SP"/></label><button class="btn outline" type="button" data-map-search>Localizar endereço</button><div class="store-map-results" data-map-results></div><div class="store-interactive-map" data-map-canvas role="region" aria-label="Mapa da loja: clique para marcar a entrada"></div><p class="store-desc">Clique no mapa ou arraste o marcador. Confira também o endereço escrito: marcar o mapa não altera cidade ou endereço.</p></div><p class="store-map-feedback" data-map-feedback role="status">${lat!==''?'Ponto definido. Confira no mapa antes de salvar.':'Sem marcação: sua loja continua disponível na lista.'}</p><button class="text-button" type="button" data-map-clear>Remover marcação</button><details><summary>Informar coordenadas manualmente</summary><div class="form-grid"><label class="field"><span>Latitude</span><input name="latitude" type="number" min="-90" max="90" step="any" value="${lat}"/></label><label class="field"><span>Longitude</span><input name="longitude" type="number" min="-180" max="180" step="any" value="${lon}"/></label></div></details></section>\`;`

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mountLocationPicker — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:36)

```javascript
function mountLocationPicker(root)
```

Executa a etapa `mount Location Picker` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `root.closest`; `root.querySelector`; `Number`; `point`; `latitude.toFixed`; `longitude.toFixed`; `marker.setLatLng`; `window.L.marker`; `markerIcon`; `marker.on`; `marker.getLatLng`; `move`; `map.invalidateSize`; `loadLeaflet`; `createMap`; `map.on`; `selected`; `map.setView`; `marker.remove`; `input.addEventListener`; `map?.setView`; `form.elements.mapSearch.value.trim`; `['city','state','address'].map`; `results.replaceChildren`; `api`; `encodeURIComponent`; `found.filter`; `document.createElement`; `results.append`; `form.elements.mapSearch.addEventListener`; `e.preventDefault`.

**Decisões condicionais examinadas:**
- `lat.value!==''&&lon.value!==''`
- `(!point(selected))`
- `(map)`
- `(marker)`
- `(loading)`
- `(!root.isConnected)`
- `(point(p))`
- `(query.length<3)`
- `(ticket!==searchTicket||!root.isConnected)`
- `(!places.length)`
- `(ticket===searchTicket)`
- `(e.key==='Enter')`

**Saída declarada:**
- `return lat.value!==''&&lon.value!==''?{latitude:Number(lat.value),longitude:Number(lon.value)}:{};`
- `return;`
- `return true;`
- `return false;`
- `return false;`
- `return true;`
- `return false;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
lat.value=latitude.toFixed(6);lon.value=longitude.toFixed(6);feedback.textContent='Ponto marcado. Use “Salvar perfil” ou envie o cadastro para confirmar.';
if(map){if(marker)marker.setLatLng([latitude,longitude]);else{marker=window.L.marker([latitude,longitude],{draggable:true,icon:markerIcon(window.L),title:'Entrada do comércio'}).addTo(map);marker.on('dragend',()=>{const p=marker.getLatLng();move(p.lat,p.lng);});}}
loading=true;toggle.disabled=true;feedback.textContent='Carregando mapa…';
try{const L=await loadLeaflet();if(!root.isConnected)return false;content.hidden=false;map=createMap(L,root.querySelector('[data-map-canvas]'));map.on('click',e=>move(e.latlng.lat,e.latlng.lng));const p=selected();if(point(p)){map.setView([p.latitude,p.longitude],17);move(p.latitude,p.longitude);}else feedback.textContent='Busque o endereço e clique na entrada do comércio.';toggle.textContent='Mapa aberto: marque a entrada';return true;}
catch(error){feedback.textContent=error.message;return false;}finally{loading=false;toggle.disabled=false;}
root.querySelector('[data-map-clear]').onclick=()=>{searchTicket++;lat.value='';lon.value='';if(marker){marker.remove();marker=null;}feedback.textContent='Marcação removida. Salve o perfil para confirmar. O endereço escrito será mantido.';};
for(const input of [lat,lon])input.addEventListener('change',()=>{const p=selected();if(point(p)){move(p.latitude,p.longitude);map?.setView([p.latitude,p.longitude],17);}});
if(query.length<3){feedback.textContent='Digite o endereço ou a cidade para buscar.';return;}
const ticket=++searchTicket,results=root.querySelector('[data-map-results]');results.replaceChildren();feedback.textContent='Buscando endereço…';
try{const found=await api('/geography/search?query='+encodeURIComponent(query));if(ticket!==searchTicket||!root.isConnected)return;
const places=found.filter(p=>point({latitude:p.lat,longitude:p.lon}));if(!places.length){feedback.textContent='Endereço não encontrado. Você pode navegar no mapa e marcar o ponto manualmente.';return;}
feedback.textContent='Escolha o resultado e ajuste o marcador na entrada exata.';
for(const place of places){const button=document.createElement('button');button.type='button';button.className='store-map-result';button.textContent=place.display_name;button.onclick=()=>{move(Number(place.lat),Number(place.lon));map?.setView([Number(place.lat),Number(place.lon)],17);results.replaceChildren();};results.append(button);}
}catch(error){if(ticket===searchTicket)feedback.textContent=error.message;}
form.elements.mapSearch.addEventListener('keydown',e=>{if(e.key==='Enter'){e.preventDefault();root.querySelector('[data-map-search]').click();}});
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### selected — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:39)

```javascript
function selected()
```

Executa a etapa `selected` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Number`.

**Decisões condicionais examinadas:**
- `lat.value!==''&&lon.value!==''`

**Saída declarada:**
- `return lat.value!==''&&lon.value!==''?{latitude:Number(lat.value),longitude:Number(lon.value)}:{};`

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### move — linha 40

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:40)

```javascript
function move(latitude,longitude)
```

Executa a etapa `move` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `point`; `latitude.toFixed`; `longitude.toFixed`; `marker.setLatLng`; `window.L.marker`; `markerIcon`; `marker.on`; `marker.getLatLng`; `move`.

**Decisões condicionais examinadas:**
- `(!point(selected))`
- `(map)`
- `(marker)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
lat.value=latitude.toFixed(6);lon.value=longitude.toFixed(6);feedback.textContent='Ponto marcado. Use “Salvar perfil” ou envie o cadastro para confirmar.';
if(map){if(marker)marker.setLatLng([latitude,longitude]);else{marker=window.L.marker([latitude,longitude],{draggable:true,icon:markerIcon(window.L),title:'Entrada do comércio'}).addTo(map);marker.on('dragend',()=>{const p=marker.getLatLng();move(p.lat,p.lng);});}}
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@43:215 — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:43)

```javascript
()=>
```

Executa a etapa `callback@43:215` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `marker.getLatLng`; `move`.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### open — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:45)

```javascript
async function open()
```

Executa a etapa `open` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `map.invalidateSize`; `loadLeaflet`; `createMap`; `root.querySelector`; `map.on`; `move`; `selected`; `point`; `map.setView`.

**Decisões condicionais examinadas:**
- `(map)`
- `(loading)`
- `(!root.isConnected)`
- `(point(p))`

**Saída declarada:**
- `return true;`
- `return false;`
- `return false;`
- `return true;`
- `return false;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
loading=true;toggle.disabled=true;feedback.textContent='Carregando mapa…';
try{const L=await loadLeaflet();if(!root.isConnected)return false;content.hidden=false;map=createMap(L,root.querySelector('[data-map-canvas]'));map.on('click',e=>move(e.latlng.lat,e.latlng.lng));const p=selected();if(point(p)){map.setView([p.latitude,p.longitude],17);move(p.latitude,p.longitude);}else feedback.textContent='Busque o endereço e clique na entrada do comércio.';toggle.textContent='Mapa aberto: marque a entrada';return true;}
catch(error){feedback.textContent=error.message;return false;}finally{loading=false;toggle.disabled=false;}
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@48:164 — linha 48

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:48)

```javascript
e=>
```

Executa a etapa `callback@48:164` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `move`.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@52:50 — linha 52

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:52)

```javascript
()=>
```

Executa a etapa `callback@52:50` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `marker.remove`.

**Decisões condicionais examinadas:**
- `(marker)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
()=>{searchTicket++;lat.value='';lon.value='';if(marker){marker.remove();marker=null;}feedback.textContent='Marcação removida. Salve o perfil para confirmar. O endereço escrito será mantido.';}
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@53:64 — linha 53

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:53)

```javascript
()=>
```

Executa a etapa `callback@53:64` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `selected`; `point`; `move`; `map?.setView`.

**Decisões condicionais examinadas:**
- `(point(p))`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
()=>{const p=selected();if(point(p)){move(p.latitude,p.longitude);map?.setView([p.latitude,p.longitude],17);}}
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@54:51 — linha 54

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:54)

```javascript
async()=>
```

Executa a etapa `callback@54:51` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `form.elements.mapSearch.value.trim`; `['city','state','address'].map`; `root.querySelector`; `results.replaceChildren`; `api`; `encodeURIComponent`; `found.filter`; `point`; `document.createElement`; `move`; `Number`; `map?.setView`; `results.append`.

**Decisões condicionais examinadas:**
- `(query.length<3)`
- `(ticket!==searchTicket||!root.isConnected)`
- `(!places.length)`
- `(ticket===searchTicket)`

**Saída declarada:**
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if(query.length<3){feedback.textContent='Digite o endereço ou a cidade para buscar.';return;}
const ticket=++searchTicket,results=root.querySelector('[data-map-results]');results.replaceChildren();feedback.textContent='Buscando endereço…';
try{const found=await api('/geography/search?query='+encodeURIComponent(query));if(ticket!==searchTicket||!root.isConnected)return;
const places=found.filter(p=>point({latitude:p.lat,longitude:p.lon}));if(!places.length){feedback.textContent='Endereço não encontrado. Você pode navegar no mapa e marcar o ponto manualmente.';return;}
feedback.textContent='Escolha o resultado e ajuste o marcador na entrada exata.';
for(const place of places){const button=document.createElement('button');button.type='button';button.className='store-map-result';button.textContent=place.display_name;button.onclick=()=>{move(Number(place.lat),Number(place.lon));map?.setView([Number(place.lat),Number(place.lon)],17);results.replaceChildren();};results.append(button);}
}catch(error){if(ticket===searchTicket)feedback.textContent=error.message;}
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@55:86 — linha 55

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:55)

```javascript
k=>
```

Executa a etapa `callback@55:86` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@59:33 — linha 59

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:59)

```javascript
p=>
```

Executa a etapa `callback@59:33` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `point`.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@61:190 — linha 61

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:61)

```javascript
()=>
```

Executa a etapa `callback@61:190` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `move`; `Number`; `map?.setView`; `results.replaceChildren`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
()=>{move(Number(place.lat),Number(place.lon));map?.setView([Number(place.lat),Number(place.lon)],17);results.replaceChildren();}
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@64:54 — linha 64

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:64)

```javascript
e=>
```

Executa a etapa `callback@64:54` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `e.preventDefault`; `root.querySelector`.

**Decisões condicionais examinadas:**
- `(e.key==='Enter')`

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mountStoreDirectoryMap — linha 67

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:67)

```javascript
function mountStoreDirectoryMap(root,stores,{onOpen=null,label='Ver lojas no mapa'}={})
```

Executa a etapa `mount Store Directory Map` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.filter`; `document.createElement`; `root.replaceChildren`; `loadLeaflet`; `box.setAttribute`; `root.append`; `createMap`; `Number`; `bounds.push`; `[store.deliveryEnabled!==false?`; `onOpen?.`; `popup.append`; `L.marker`; `markerIcon`; `map.setView`; `map.fitBounds`; `onOpen`; `list.append`.

**Decisões condicionais examinadas:**
- `located.length`
- `located.length===1`
- `missing`
- `(map)`
- `(!root.isConnected)`
- `store.deliveryEnabled!==false`
- `store.pickupEnabled===false&&store.inPersonEnabled===false`
- `store.pickupEnabled!==false`
- `store.inPersonEnabled!==false`
- `(onOpen)`
- `(bounds.length===1)`
- `onOpen`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const button=document.createElement('button');button.type='button';button.className='btn outline';button.textContent=label;button.disabled=!located.length;
const hint=document.createElement('p');hint.className='store-desc';hint.textContent=located.length?`${located.length} ${located.length===1?'loja com ponto marcado':'lojas com ponto marcado'}${missing?` · ${missing} sem marcação, disponíveis na lista`:''}.`:'Nenhuma loja deste filtro marcou a localização. Confira os estabelecimentos na lista.';
try{const L=await loadLeaflet();if(!root.isConnected)return;const box=document.createElement('div');box.className='store-interactive-map';box.setAttribute('role','region');box.setAttribute('aria-label','Mapa de estabelecimentos');root.append(box);map=createMap(L,box);
for(const store of located){const coords=[Number(store.latitude),Number(store.longitude)];bounds.push(coords);const popup=document.createElement('div');popup.className='shop-map-popup';const title=document.createElement('strong');title.textContent=store.name;const address=document.createElement('p');address.textContent=store.address||'';const modes=document.createElement('p');modes.textContent=[store.deliveryEnabled!==false?(store.pickupEnabled===false&&store.inPersonEnabled===false?'Somente delivery':'Entrega'):null,store.pickupEnabled!==false?'Retirada':null,store.inPersonEnabled!==false?'Agendamento presencial':null].filter(Boolean).join(' · ');const open=document.createElement('button');open.type='button';open.className='btn small';open.textContent='Ver loja';open.onclick=()=>onOpen?.(store.id);popup.append(title,address,modes);if(onOpen)popup.append(open);L.marker(coords,{icon:markerIcon(L),title:store.name,alt:store.name}).addTo(map).bindPopup(popup);}
if(bounds.length===1)map.setView(bounds[0],16);else map.fitBounds(bounds,{padding:[35,35],maxZoom:16});button.textContent='Lojas no mapa';
const list=document.createElement('div');list.className='map-store-links';for(const store of onOpen?located:[]){const link=document.createElement('button');link.type='button';link.className='btn outline small';link.textContent=store.name;link.onclick=()=>onOpen(store.id);list.append(link);}root.append(list);
}catch(error){hint.textContent=error.message;button.disabled=false;}
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@72:18 — linha 72

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:72)

```javascript
async()=>
```

Executa a etapa `callback@72:18` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `loadLeaflet`; `document.createElement`; `box.setAttribute`; `root.append`; `createMap`; `Number`; `bounds.push`; `[store.deliveryEnabled!==false?`; `onOpen?.`; `popup.append`; `L.marker`; `markerIcon`; `map.setView`; `map.fitBounds`; `onOpen`; `list.append`.

**Decisões condicionais examinadas:**
- `(map)`
- `(!root.isConnected)`
- `store.deliveryEnabled!==false`
- `store.pickupEnabled===false&&store.inPersonEnabled===false`
- `store.pickupEnabled!==false`
- `store.inPersonEnabled!==false`
- `(onOpen)`
- `(bounds.length===1)`
- `onOpen`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
try{const L=await loadLeaflet();if(!root.isConnected)return;const box=document.createElement('div');box.className='store-interactive-map';box.setAttribute('role','region');box.setAttribute('aria-label','Mapa de estabelecimentos');root.append(box);map=createMap(L,box);
for(const store of located){const coords=[Number(store.latitude),Number(store.longitude)];bounds.push(coords);const popup=document.createElement('div');popup.className='shop-map-popup';const title=document.createElement('strong');title.textContent=store.name;const address=document.createElement('p');address.textContent=store.address||'';const modes=document.createElement('p');modes.textContent=[store.deliveryEnabled!==false?(store.pickupEnabled===false&&store.inPersonEnabled===false?'Somente delivery':'Entrega'):null,store.pickupEnabled!==false?'Retirada':null,store.inPersonEnabled!==false?'Agendamento presencial':null].filter(Boolean).join(' · ');const open=document.createElement('button');open.type='button';open.className='btn small';open.textContent='Ver loja';open.onclick=()=>onOpen?.(store.id);popup.append(title,address,modes);if(onOpen)popup.append(open);L.marker(coords,{icon:markerIcon(L),title:store.name,alt:store.name}).addTo(map).bindPopup(popup);}
if(bounds.length===1)map.setView(bounds[0],16);else map.fitBounds(bounds,{padding:[35,35],maxZoom:16});button.textContent='Lojas no mapa';
const list=document.createElement('div');list.className='map-store-links';for(const store of onOpen?located:[]){const link=document.createElement('button');link.type='button';link.className='btn outline small';link.textContent=store.name;link.onclick=()=>onOpen(store.id);list.append(link);}root.append(list);
}catch(error){hint.textContent=error.message;button.disabled=false;}
```

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@75:795 — linha 75

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:75)

```javascript
()=>
```

Executa a etapa `callback@75:795` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `onOpen?.`.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@78:258 — linha 78

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:78)

```javascript
()=>
```

Executa a etapa `callback@78:258` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `onOpen`.

**Decisão de engenharia:** Mapas de lojas. Seleção opcional, marcador arrastável, busca explícita, popups textuais e lista alternativa. Somente pontos publicados entram no mapa; ausência de coordenadas não oculta a loja da lista. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/storeOptions.js

Modalidades de entrega, retirada e agendamento presencial. Bandeiras são informativas; a API rejeita modalidades desativadas e exige ao menos uma opção.

Fonte: [storeOptions.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeOptions.js) · SHA-256 `92b7f48083f584065e50994bdf5e6c3eabb626b3c04ce8cccfe7a5ca4264ac7d`

### fulfillmentFields — linha 2

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeOptions.js:2)

```javascript
function fulfillmentFields(store={})
```

Executa a etapa `fulfillment Fields` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `fulfillmentOptions.map`.

**Decisões condicionais examinadas:**
- `store[key]!==false`

**Saída declarada:**
- `return \`<fieldset class="store-fulfillment span-2"><legend>Como sua loja atende?</legend><p>Marque as formas de atendimento oferecidas. Para trabalhar somente com delivery, deixe apenas Entrega selecionada.</p><div class="fulfillment-options">${fulfillmentOptions.map(([key,label,hint])=>\`<label class="fulfillment-option"><input type="checkbox" name="${key}" ${store[key]!==false?'checked':''}/><span><strong>${label}</strong><small>${hint}</small></span></label>\`).join('')}</div><small>Desativar uma modalidade impede novas operações nela. Pedidos e reservas já confirmados continuam sob sua responsabilidade.</small></fieldset>\`;`

**Decisão de engenharia:** Modalidades de entrega, retirada e agendamento presencial. Bandeiras são informativas; a API rejeita modalidades desativadas e exige ao menos uma opção. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@2:313 — linha 2

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeOptions.js:2)

```javascript
([key,label,hint])=>
```

Executa a etapa `callback@2:313` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `store[key]!==false`

**Decisão de engenharia:** Modalidades de entrega, retirada e agendamento presencial. Bandeiras são informativas; a API rejeita modalidades desativadas e exige ao menos uma opção. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### fulfillmentPayload — linha 3

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeOptions.js:3)

```javascript
function fulfillmentPayload(data)
```

Executa a etapa `fulfillment Payload` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Object.fromEntries`; `fulfillmentOptions.map`; `data.has`; `Object.values`.

**Rejeições locais:**
- `throw new Error('Escolha ao menos uma modalidade de atendimento.');`

**Decisões condicionais examinadas:**
- `(!Object.values(result).some(Boolean))`

**Saída declarada:**
- `return result;`

**Decisão de engenharia:** Modalidades de entrega, retirada e agendamento presencial. Bandeiras são informativas; a API rejeita modalidades desativadas e exige ao menos uma opção. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@3:97 — linha 3

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeOptions.js:3)

```javascript
([key])=>
```

Executa a etapa `callback@3:97` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `data.has`.

**Decisão de engenharia:** Modalidades de entrega, retirada e agendamento presencial. Bandeiras são informativas; a API rejeita modalidades desativadas e exige ao menos uma opção. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### fulfillmentBadges — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeOptions.js:4)

```javascript
function fulfillmentBadges(store)
```

Executa a etapa `fulfillment Badges` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `d`
- `!p&&!i`
- `p`
- `i`

**Saída declarada:**
- `return \`<div class="store-service-badges" aria-label="Modalidades de atendimento">${d?\`<span class="service-badge delivery">${!p&&!i?'Somente delivery':'Entrega'}</span>\`:''}${p?'<span class="service-badge pickup">Retirada na loja</span>':''}${i?'<span class="service-badge in-person">Agendamento presencial</span>':''}</div>\`;`

**Decisão de engenharia:** Modalidades de entrega, retirada e agendamento presencial. Bandeiras são informativas; a API rejeita modalidades desativadas e exige ao menos uma opção. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Frontend/src/storeTime.js

Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior.

Fonte: [storeTime.js](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js) · SHA-256 `84e814a90d5ba18d1979b57bf5f520c69797b44baa372c4c672c1a9bd7042e0e`

### partsAt — linha 1

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:1)

```javascript
function partsAt(instant, timeZone)
```

Executa a etapa `parts At` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `new Intl.DateTimeFormat`; `Object.fromEntries`; `parts.filter`; `Number`.

**Saída declarada:**
- `return Object.fromEntries(parts.filter(part => part.type !== 'literal').map(part => [part.type, Number(part.value)]));`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@6:42 — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:6)

```javascript
part =>
```

Executa a etapa `callback@6:42` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@6:79 — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:6)

```javascript
part =>
```

Executa a etapa `callback@6:79` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Number`.

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### localDateTimeInZoneToIso — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:9)

```javascript
function localDateTimeInZoneToIso(value, timeZone)
```

Executa a etapa `local Date Time In Zone To Iso` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `/^`; `match.slice`; `Date.UTC`; `partsAt`; `new Date`.

**Rejeições locais:**
- `throw new Error('Escolha uma data e um horário válidos.');`
- `throw new Error('Esse horário não existe no fuso da loja por causa da mudança de relógio. Escolha outro horário.');`

**Decisões condicionais examinadas:**
- `(!match)`
- `(verified.year !== year || verified.month !== month || verified.day !== day || verified.hour !== hour || verified.minute !== minute)`

**Saída declarada:**
- `return new Date(candidate).toISOString();`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### localDateTimeMinimum — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:27)

```javascript
function localDateTimeMinimum(timeZone, minutesFromNow = 31, now = new Date())
```

Executa a etapa `local Date Time Minimum` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `partsAt`; `now.getTime`; `String`.

**Saída declarada:**
- `return \`${local.year}-${String(local.month).padStart(2, '0')}-${String(local.day).padStart(2, '0')}T${String(local.hour).padStart(2, '0')}:${String(local.minute).padStart(2, '0')}\`;`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### dateTimeInZone — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:32)

```javascript
function dateTimeInZone(value, timeZone)
```

Executa a etapa `date Time In Zone` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `new Intl.DateTimeFormat`.

**Saída declarada:**
- `return new Intl.DateTimeFormat('pt-BR', { dateStyle: 'medium', timeStyle: 'short', timeZone }).format(new Date(value));`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### intervalsForDay — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:36)

```javascript
function intervalsForDay(configuration, day)
```

Executa a etapa `intervals For Day` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `configuration?.weekly?.[day]`

**Saída declarada:**
- `return configuration?.weeklyShifts?.[day] ?? (configuration?.weekly?.[day] ? [configuration.weekly[day]] : []);`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### minutes — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:39)

```javascript
function minutes(value)
```

Executa a etapa `minutes` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `String`.

**Decisões condicionais examinadas:**
- `(value==='24:00')`

**Saída declarada:**
- `return 1440;`
- `return h*60+m;`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### exceptionIntervals — linha 40

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:40)

```javascript
function exceptionIntervals(exception)
```

Executa a etapa `exception Intervals` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `exception.closed`

**Saída declarada:**
- `return exception.closed ? [] : exception.shifts ?? [{opensAt:exception.opensAt,closesAt:exception.closesAt}];`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isoDate — linha 41

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:41)

```javascript
function isoDate(date)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `date.toISOString`.

**Saída declarada:**
- `return date.toISOString().slice(0,10);`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### rangesForDate — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:42)

```javascript
function rangesForDate(configuration, date)
```

Executa a etapa `ranges For Date` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `configuration?.exceptions?.find`; `Number.isNaN`; `day.getTime`; `isoDate`; ``; `exceptionIntervals`; `intervalsForDay`; `day.getUTCDay`; `minutes`; `Math.min`; `day.setUTCDate`; `day.getUTCDate`; `ranges.push`; `ranges.sort`.

**Decisões condicionais examinadas:**
- `(today?.closed)`
- `(!today&&!configuration?.enabled)`
- `(Number.isNaN(day.getTime())||isoDate(day)!==date)`
- `today`
- `minutes(v.closesAt)>minutes(v.opensAt)`
- `(!today)`
- `previous`
- `(close<open)`

**Saída declarada:**
- `return [];`
- `return [[0,1440]];`
- `return [];`
- `return ranges.sort((a,b)=>a[0]-b[0]);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
if(!today){day.setUTCDate(day.getUTCDate()-1);const previous=configuration?.exceptions?.find(e=>e.date===isoDate(day));
```

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@43:47 — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:43)

```javascript
e=>
```

Executa a etapa `callback@43:47` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@49:107 — linha 49

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:49)

```javascript
v=>
```

Executa a etapa `callback@49:107` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `minutes`; `Math.min`.

**Decisões condicionais examinadas:**
- `minutes(v.closesAt)>minutes(v.opensAt)`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@50:96 — linha 50

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:50)

```javascript
e=>
```

Executa a etapa `callback@50:96` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `isoDate`.

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@53:22 — linha 53

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:53)

```javascript
(a,b)=>
```

Executa a etapa `callback@53:22` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeOpenAt — linha 55

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:55)

```javascript
function storeOpenAt(configuration,timeZone,instant=new Date())
```

Executa a etapa `store Open At` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `partsAt`; `String`; `rangesForDate`.

**Decisões condicionais examinadas:**
- `(!configuration)`

**Saída declarada:**
- `return true;`
- `return rangesForDate(configuration,date).some(([start,end])=>minute>=start&&minute<end);`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@60:49 — linha 60

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:60)

```javascript
([start,end])=>
```

Executa a etapa `callback@60:49` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeHoursStatusRelevant — linha 62

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:62)

```javascript
function storeHoursStatusRelevant(configuration,timeZone,instant=new Date())
```

Executa a etapa `store Hours Status Relevant` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `partsAt`; `Date.UTC`; `isoDate`; `date.setUTCDate`; `date.getUTCDate`; ``.

**Decisões condicionais examinadas:**
- `(!configuration)`
- `(configuration.enabled)`

**Saída declarada:**
- `return false;`
- `return true;`
- `return (configuration.exceptions||[]).some(e=>e.date===today||e.date===yesterday);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```javascript
const today=isoDate(date);date.setUTCDate(date.getUTCDate()-1);const yesterday=isoDate(date);
```

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@66:46 — linha 66

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:66)

```javascript
e=>
```

Executa a etapa `callback@66:46` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeScheduleAllowsLocalDateTime — linha 68

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:68)

```javascript
function storeScheduleAllowsLocalDateTime(configuration,value)
```

Executa a etapa `store Schedule Allows Local Date Time` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `/^`; `Number`; `rangesForDate`.

**Decisões condicionais examinadas:**
- `(!match)`
- `(Number(h)>23||Number(m)>59)`

**Saída declarada:**
- `return false;`
- `return false;`
- `return rangesForDate(configuration,date).some(([start,end])=>minute>=start&&minute<end);`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback@71:49 — linha 71

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:71)

```javascript
([start,end])=>
```

Executa a etapa `callback@71:49` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeScheduleAllowsAppointment — linha 73

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeTime.js:73)

```javascript
function storeScheduleAllowsAppointment(configuration,value,duration,timeZone)
```

Executa a etapa `store Schedule Allows Appointment` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Number.isInteger`; `localDateTimeInZoneToIso`; `storeOpenAt`; `start.getTime`.

**Decisões condicionais examinadas:**
- `(!Number.isInteger(duration)||duration<1||duration>480)`
- `(!storeOpenAt(configuration,timeZone,new Date(start.getTime()+minute*60000)))`

**Saída declarada:**
- `return false;`
- `return false;`
- `return false;`
- `return storeOpenAt(configuration,timeZone,new Date(start.getTime()+duration*60000-1));`

**Decisão de engenharia:** Regras puras de fuso e horário para feedback imediato. A API valida novamente antes de comprar/reservar. Madrugada pertence à abertura do dia anterior. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

