# Referência completa de funções — backend Java

Gerada em 05/11/2026 a partir da árvore sintática do código final. Inclui funções explícitas, métodos privados, construtores e callbacks. Não é uma certificação de segurança: use junto da análise de engenharia e da matriz de testes. Cobertura estrutural: **107 arquivos e 857 funções explícitas**. Hash agregado: `356284b7321508f651091a7ed1a721202d0084cbf19ac468f1a52ab71f1b4417`.

Cada seção identifica localização, assinatura, regras locais, chamadas e efeitos observáveis. Getters implícitos de `record`, métodos herdados de bibliotecas e APIs nativas são descritos por seus contratos, não reproduzidos como código do projeto.

Regeneração: criar ambiente Python isolado, instalar `scripts/reference-requirements.txt` e executar `python scripts/generate_reference.py` na raiz.

## Backend/src/main/java/br/com/fooddelivery/FoodDeliveryApplication.java

Inicialização da aplicação e ligação dos módulos; manter separação entre configuração e regra de negócio.

Fonte: [FoodDeliveryApplication.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/FoodDeliveryApplication.java) · SHA-256 `4a15a7b8b81e8e62c51d5deeca77cb698f9b86f4f5f39eca6af07c65450f1757`

### main — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/FoodDeliveryApplication.java:9)

```java
public static void main(String[] args)
```

Executa a etapa `main` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `SpringApplication.run`.

**Decisão de engenharia:** Inicialização da aplicação e ligação dos módulos; manter separação entre configuração e regra de negócio. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java

Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria.

Fonte: [AdminAuditEvent.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java) · SHA-256 `88576f48d1593d69068992340989fb2e6ac89442012025da4f227606bc975676`

### AdminAuditEvent — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:24)

```java
protected AdminAuditEvent()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### AdminAuditEvent — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:26)

```java
public AdminAuditEvent(UUID actorId, String action, String targetType, String targetId, String summary)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `required`; `clean`.

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### required — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:34)

```java
private static String required(String value, int max)
```

Executa a etapa `required` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.trim`; `clean.isEmpty`; `clean.length`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Campo de auditoria inválido.");`

**Decisões condicionais examinadas:**
- `value == null`
- `(clean.isEmpty() || clean.length() > max)`

**Saída declarada:**
- `return clean;`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### clean — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:39)

```java
private static String clean(String value, int max)
```

Executa a etapa `clean` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.isBlank`; `value.trim`; `clean.length`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Campo de auditoria inválido.");`

**Decisões condicionais examinadas:**
- `(value == null || value.isBlank())`
- `(clean.length() > max)`

**Saída declarada:**
- `return null;`
- `return clean;`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 46

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:46)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getActorId — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:47)

```java
public UUID getActorId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return actorId;`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAction — linha 48

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:48)

```java
public String getAction()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return action;`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getTargetType — linha 49

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:49)

```java
public String getTargetType()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return targetType;`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getTargetId — linha 50

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:50)

```java
public String getTargetId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return targetId;`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getSummary — linha 51

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:51)

```java
public String getSummary()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return summary;`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 52

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditEvent.java:52)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditRepository.java

Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria.

Fonte: [AdminAuditRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditRepository.java) · SHA-256 `1534ee3b37fb6fafe926f8bd1a311403971cfeda891d6116d1aab036cc98ab06`

### findAllByOrderByCreatedAtDesc — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditRepository.java:9)

```java
Page<AdminAuditEvent> findAllByOrderByCreatedAtDesc(Pageable pageable);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditService.java

Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria.

Fonte: [AdminAuditService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditService.java) · SHA-256 `53c8ecbc5e9b10841ee480896cebb1c0bee6d8153bb5e688430dca9309a6c0ef`

### AdminAuditService — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditService.java:12)

```java
public AdminAuditService(AdminAuditRepository events)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### record — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditService.java:14)

```java
@Transactional public void record(UUID actorId, String action, String targetType, Object targetId, String summary)
```

Executa a etapa `record` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `events.save`; `targetId.toString`.

**Decisões condicionais examinadas:**
- `targetId == null`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
events.save(new AdminAuditEvent(actorId, action, targetType,
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### latest — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminAuditService.java:20)

```java
@Transactional(readOnly = true) public Page<AdminAuditEvent> latest(int page, int size)
```

Executa a etapa `latest` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `events.findAllByOrderByCreatedAtDesc`; `PageRequest.of`; `Math.max`; `Math.min`.

**Saída declarada:**
- `return events.findAllByOrderByCreatedAtDesc(PageRequest.of(Math.max(0, page), Math.max(1, Math.min(size, 100))));`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java

Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria.

Fonte: [AdminController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java) · SHA-256 `b12e2f1483df1b625a10d48eb38c6162215f8d1668cd30ef6870355bf37a29d5`

**Contrato de dados/estados:**
```java
public record OperatorRequest(@NotBlank @Size(max = 120) String name, @Email @NotBlank String email,
                                  @Size(min = 12, max = 72) String password, @NotNull AccountRole role,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record Approval(boolean approved)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record StoreCategoryRequest(@NotBlank @Size(max=60) String name,
                                       @NotBlank @Size(max=16) String emoji)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record AdminStoreCategoryView(UUID id, String name, String emoji, boolean builtIn)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record StoreActivity(boolean active)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record AccountActivity(boolean active)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record Commission(@NotNull @DecimalMin("0.00") @DecimalMax("50.00") BigDecimal percent)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record OperatorView(UUID id, String name, String email, AccountRole role, boolean active,
                               String applicationStatus, java.time.Instant createdAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record AdminAccountView(UUID id, String name, String email, String maskedCpf, String phone, String addressLine,
                                   String addressComplement, String neighborhood, String city, String state, String postalCode, AccountRole role,
                                   List<AccountRole> registeredRoles, List<AccountRole> grantedRoles, boolean active, String applicationStatus,
                                   java.time.Instant createdAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record AdminStoreView(UUID id, String name, String description, String category, String address, String city, String state, String cnpj,
                                 boolean active, boolean approved, String ownerName, String ownerEmail,
                                 double commissionPercent, long deliveryFeeCents, String coverImageUrl,String timezone,Double latitude,Double longitude,boolean deliveryEnabled,boolean pickupEnabled,boolean inPersonEnabled)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record ServiceCityRequest(@NotBlank @Size(max=100) String city,
                                     @NotBlank @jakarta.validation.constraints.Pattern(regexp="[A-Za-z]
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record ServiceCityView(UUID id, String city, String state, String postalCode, boolean enabled)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record IntegrationView(String id, String name, boolean configured, String status, String configuration)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record AdminAuditView(UUID id, UUID actorId, String action, String targetType, String targetId, String summary, java.time.Instant createdAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record AdminProfileUpdate(@NotBlank @Size(max=120) String name, @Email @NotBlank @Size(max=254) String email,
                                     @Size(max=30) String phone, @Size(max=14) String cpf,
                                     @Size(max=250) String addressLine, @Size(max=120) String addressComplement,
                                     @Size(max=120) String neighborhood, @Size(max=100) String city,
                                     @Size(max=2) String state, @Size(max=9) String postalCode, boolean clearCpf)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### AdminController — linha 54

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:54)

```java
public AdminController(AccountRepository accounts, StoreRepository stores, PasswordEncoder encoder, NotificationService notifications, ServiceCityRepository serviceCities, StoreCategoryRepository storeCategories, AdminAuditService auditService, br.com.fooddelivery.identity.PasswordRecoveryService recovery, br.com.fooddelivery.catalog.StoreHoursChangeService hoursChanges)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeCategories — linha 65

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:65)

```java
@GetMapping("/store-categories") @org.springframework.transaction.annotation.Transactional(readOnly = true) public List<AdminStoreCategoryView> storeCategories()
```

Executa a etapa `store Categories` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `StoreCategories.ALL.stream`; `category.name`; `category.emoji`; `storeCategories.findAllByOrderByNameAsc`; `category.getId`; `category.getName`; `category.getEmoji`; `java.util.stream.Stream.concat`; `builtIns.stream`; `custom.stream`.

**Saída declarada:**
- `return java.util.stream.Stream.concat(builtIns.stream(), custom.stream()).toList();`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createStoreCategory — linha 75

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:75)

```java
@PostMapping("/store-categories") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public AdminStoreCategoryView createStoreCategory(@Valid @RequestBody StoreCategoryRequest request)
```

Executa a etapa `create Store Category` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `request.name`; `StoreCategories.isSupported`; `request.emoji`; `storeCategories.findBySlugIgnoreCase`; `category.getSlug`; `storeCategories.save`; `audit`; `saved.getId`; `saved.getName`; `saved.getEmoji`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Essa categoria já existe e não pode ser duplicada.");`
- `throw new IllegalArgumentException("Já existe uma categoria com esse nome ou uma forma equivalente.");`

**Decisões condicionais examinadas:**
- `(StoreCategories.isSupported(name))`
- `(storeCategories.findBySlugIgnoreCase(category.getSlug()).isPresent())`

**Saída declarada:**
- `return new AdminStoreCategoryView(saved.getId(), saved.getName(), saved.getEmoji(), false);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
StoreCategory saved = storeCategories.save(category);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deleteStoreCategory — linha 90

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:90)

```java
@DeleteMapping("/store-categories/{id}") @org.springframework.transaction.annotation.Transactional @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteStoreCategory(@PathVariable UUID id)
```

Executa a etapa `delete Store Category` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `storeCategories.findById`; `stores.countByCategoryIgnoreCase`; `category.getName`; `storeCategories.delete`; `audit`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Esta seção está vinculada a " + storesUsingCategory + " loja(s). Altere a categoria dessas lojas antes de excluí-la.");`

**Decisões condicionais examinadas:**
- `(storesUsingCategory > 0)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
storeCategories.delete(category);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### serviceCities — linha 102

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:102)

```java
@GetMapping("/service-cities") public List<ServiceCityView> serviceCities()
```

Executa a etapa `service Cities` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `serviceCities.findAllByOrderByCityAscStateAsc`.

**Saída declarada:**
- `return serviceCities.findAllByOrderByCityAscStateAsc().stream().map(ServiceCityView::of).toList();`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createServiceCity — linha 107

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:107)

```java
@PostMapping("/service-cities") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public ServiceCityView createServiceCity(@Valid @RequestBody ServiceCityRequest request)
```

Executa a etapa `create Service City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `request.city`; `request.state`; `serviceCities.findByCityIgnoreCaseAndStateIgnoreCase`; `serviceCities.save`; `request.postalCode`; `audit`; `saved.getId`; `ServiceCityView.of`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Esta cidade já está cadastrada. Reative ou edite o cadastro existente.");`

**Decisões condicionais examinadas:**
- `(serviceCities.findByCityIgnoreCaseAndStateIgnoreCase(city, state).isPresent())`

**Saída declarada:**
- `return ServiceCityView.of(saved);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
ServiceCity saved = serviceCities.save(new ServiceCity(city, state, request.postalCode()));
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateServiceCity — linha 120

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:120)

```java
@PutMapping("/service-cities/{id}") @org.springframework.transaction.annotation.Transactional public ServiceCityView updateServiceCity(@PathVariable UUID id, @Valid @RequestBody ServiceCityRequest request)
```

Executa a etapa `update Service City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `serviceCities.findById`; `request.city`; `request.state`; `serviceCities.findByCityIgnoreCaseAndStateIgnoreCase`; `existing.getId`; `city.update`; `request.postalCode`; `request.enabled`; `serviceCities.save`; `audit`; `ServiceCityView.of`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Já existe um cadastro para esta cidade e estado.");`

**Saída declarada:**
- `return ServiceCityView.of(saved);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
city.update(name, state, request.postalCode(), request.enabled());
ServiceCity saved = serviceCities.save(city);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### integrations — linha 136

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:136)

```java
@GetMapping("/integrations") public List<IntegrationView> integrations()
```

Executa a etapa `integrations` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `List.of`; `integration`; `ready`.

**Saída declarada:**
- `return List.of( integration("SMTP", "E-mail de recuperação e avisos", ready(smtpHost, smtpUsername, smtpPassword), "SMTP_HOST, SMTP_PORT, SMTP_USERNAME, SMTP_PASSWORD, RECOVERY_EMAIL_FROM"), integration("WHATSAPP", "WhatsApp Cloud para códigos e avisos", whatsappEnabled && ready(whatsappPhoneId, whatsappToken), "WHATSAPP_CLOUD_ENABLED, WHATSAPP_PHONE_NUMBER_ID, WHATSAPP_ACCESS_TOKEN, WHATSAPP_OTP_TEMPLATE"), new IntegrationView("STORE_IMAGES", "Capas enviadas pelo vendedor", true, "Upload local com aprovação administrativa", "Sem chave ou API de IA; PNG/JPG ajustado para 1.200 × 600 pixels"), integration("MERCADOPAGO", "Checkout, pagamentos e webhooks", ready(mercadoPagoClientId, mercadoPagoClientSecret, mercadoPagoEncryptionKey, mercadoPagoWebhookSecret, mercadoPagoNotificationUrl), "MP_CLIENT_ID, MP_CLIENT_SECRET, MP_TOKEN_ENCRYPTION_KEY, MP_WEBHOOK_SECRET, MP_NOTIFICATION_BASE_URL"), integration("VIACEP", "Consulta pública de CEP", true, "Integração pública; não exige chave"), integration("MAPAS", "Mapa e localização das cidades", true, "Mapa incorporado; requer conexão com a internet") );`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### ready — linha 148

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:148)

```java
private static boolean ready(String... values)
```

Executa a etapa `ready` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `java.util.Arrays.stream`; `value.isBlank`.

**Saída declarada:**
- `return java.util.Arrays.stream(values).allMatch(value -> value != null && !value.isBlank());`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### integration — linha 149

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:149)

```java
private static IntegrationView integration(String id, String name, boolean ready, String variables)
```

Executa a etapa `integration` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `ready`

**Saída declarada:**
- `return new IntegrationView(id, name, ready, ready ? "Configurada (conexão não homologada)" : "Configuração incompleta", variables);`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateAccountProfile — linha 153

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:153)

```java
@PutMapping("/accounts/{id}/profile") @org.springframework.transaction.annotation.Transactional public AdminAccountView updateAccountProfile(@PathVariable UUID id, @Valid @RequestBody AdminProfileUpdate request)
```

Executa a etapa `update Account Profile` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByIdForUpdate`; `request.email`; `accounts.existsByEmailIgnoreCaseAndIdNot`; `request.cpf`; `br.com.fooddelivery.identity.Cpf.isValid`; `accounts.findByCpf`; `existing.getId`; `account.getEmail`; `java.util.Objects.equals`; `phoneDigits`; `account.getPhone`; `request.phone`; `recovery.invalidatePhoneCodes`; `account.updateProfile`; `request.name`; `clean`; `request.addressLine`; `request.addressComplement`; `request.neighborhood`; `request.city`; `request.state`; `request.postalCode`; `account.changeEmailByAdministrator`; `account.setCpfByAdministrator`; `request.clearCpf`; `account.getRegisteredRoles`; `accounts.save`; `audit`; `adminAccountView`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Este e-mail já está vinculado a outra conta.");`
- `throw new IllegalArgumentException("Informe um CPF válido ou deixe o campo vazio.");`
- `throw new IllegalArgumentException("Este CPF já está vinculado a outra conta.");`
- `throw new IllegalArgumentException("O CPF continua obrigatório para clientes e perfis profissionais.");`

**Decisões condicionais examinadas:**
- `(accounts.existsByEmailIgnoreCaseAndIdNot(email, id))`
- `request.cpf() == null || request.cpf().isBlank()`
- `(cpf != null)`
- `(!br.com.fooddelivery.identity.Cpf.isValid(cpf))`
- `(!account.getEmail().equalsIgnoreCase(email) || !java.util.Objects.equals(phoneDigits(account.getPhone()),phoneDigits(request.phone())))`
- `(request.clearCpf())`
- `(!account.getRegisteredRoles().contains(AccountRole.ADMIN))`

**Saída declarada:**
- `return adminAccountView(saved);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
account.updateProfile(request.name().trim(), clean(request.phone()), clean(request.addressLine()), clean(request.addressComplement()),
if (cpf != null) account.setCpfByAdministrator(cpf);
account.setCpfByAdministrator(null);
Account saved = accounts.save(account);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### clean — linha 182

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:182)

```java
private static String clean(String value)
```

Executa a etapa `clean` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.isBlank`; `value.trim`.

**Decisões condicionais examinadas:**
- `value == null || value.isBlank()`

**Saída declarada:**
- `return value == null || value.isBlank() ? null : value.trim();`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### phoneDigits — linha 183

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:183)

```java
private static String phoneDigits(String value)
```

Executa a etapa `phone Digits` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.replaceAll`.

**Decisões condicionais examinadas:**
- `value == null`

**Saída declarada:**
- `return value == null ? "" : value.replaceAll("\\D", "");`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateStoreProfile — linha 185

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:185)

```java
@PutMapping("/stores/{id}/profile") @org.springframework.transaction.annotation.Transactional public AdminStoreView updateStoreProfile(@PathVariable UUID id,@Valid @RequestBody br.com.fooddelivery.catalog.CatalogController.StoreRequest request)
```

Executa a etapa `update Store Profile` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findByIdForUpdate`; `request.category`; `StoreCategories.isSupported`; `storeCategories.findByNameIgnoreCase`; `store.update`; `request.name`; `clean`; `request.description`; `request.address`; `request.city`; `request.state`; `store.isActive`; `request.deliveryFeeCents`; `hoursChanges.requireSafeTimezoneChange`; `request.timezone`; `store.setTimezone`; `store.setCoordinates`; `request.latitude`; `request.longitude`; `store.configureFulfillment`; `request.deliveryEnabled`; `request.pickupEnabled`; `request.inPersonEnabled`; `store.correctCnpjByAdministrator`; `request.cnpj`; `stores.save`; `audit`; `adminStoreView`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Escolha uma categoria cadastrada.");`

**Decisões condicionais examinadas:**
- `(!StoreCategories.isSupported(category)&&storeCategories.findByNameIgnoreCase(category).isEmpty())`
- `request.timezone()==null||request.timezone().isBlank()`

**Saída declarada:**
- `return adminStoreView(store);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
store.update(request.name().trim(),clean(request.description()),request.address().trim(),request.city().trim(),request.state(),category,store.isActive(),request.deliveryFeeCents());
store.setTimezone(request.timezone());store.setCoordinates(request.latitude(),request.longitude());store.configureFulfillment(request.deliveryEnabled(),request.pickupEnabled(),request.inPersonEnabled());
stores.save(store);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createOperator — linha 200

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:200)

```java
@PostMapping("/operators") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public Account createOperator(@Valid @RequestBody OperatorRequest request)
```

Executa a etapa `create Operator` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `request.email`; `request.cpf`; `br.com.fooddelivery.identity.Cpf.isValid`; `accounts.existsByCpf`; `accounts.findByCpf`; `existing.getEmail`; `existing.getRegisteredRoles`; `request.role`; `existing.setRole`; `accounts.save`; `audit`; `saved.getId`; `accounts.existsByEmailIgnoreCase`; `request.name`; `encoder.encode`; `request.password`; `account.setCpfOnce`; `account.setRole`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe um CPF válido.");`
- `throw new IllegalArgumentException("Este CPF já pertence a outra conta. Peça à pessoa que solicite o novo perfil pela própria conta.");`
- `throw new IllegalArgumentException("Este perfil já está vinculado à conta existente.");`
- `throw new IllegalArgumentException("E-mail já cadastrado.");`

**Decisões condicionais examinadas:**
- `(!br.com.fooddelivery.identity.Cpf.isValid(cpf))`
- `(accounts.existsByCpf(cpf))`
- `(!existing.getEmail().equalsIgnoreCase(email))`
- `(existing.getRegisteredRoles().contains(request.role()))`
- `(accounts.existsByEmailIgnoreCase(email))`

**Saída declarada:**
- `return saved;`
- `return saved;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
existing.setRole(request.role());Account saved=accounts.save(existing);audit("OPERATOR_PROFILE_LINKED", "ACCOUNT", saved.getId(), "Perfil profissional vinculado à conta existente.");return saved;
account.setCpfOnce(cpf);
account.setRole(request.role());
Account saved=accounts.save(account);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### operators — linha 222

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:222)

```java
@GetMapping("/operators") public List<OperatorView> operators()
```

Executa a etapa `operators` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findAll`; `a.getRegisteredRoles`.

**Saída declarada:**
- `return accounts.findAll().stream().filter(a -> a.getRegisteredRoles().stream().anyMatch(r -> r==AccountRole.MERCHANT||r==AccountRole.COURIER)) .map(AdminController::operatorView).toList();`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### allAccounts — linha 228

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:228)

```java
@GetMapping("/accounts") public List<AdminAccountView> allAccounts()
```

Executa a etapa `all Accounts` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findAll`.

**Saída declarada:**
- `return accounts.findAll().stream().map(AdminController::adminAccountView).toList();`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setAccountActive — linha 233

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:233)

```java
@PatchMapping("/accounts/{id}/active") @org.springframework.transaction.annotation.Transactional public AdminAccountView setAccountActive(@PathVariable UUID id, @RequestBody AccountActivity request)
```

Executa a etapa `set Account Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`; `account.getRole`; `request.active`; `account.activate`; `account.deactivate`; `accounts.save`; `stores.findByOwnerId`; `saved.getId`; `ownedStores.forEach`; `store.setActive`; `store.setApproved`; `stores.saveAll`; `audit`; `adminAccountView`.

**Rejeições locais:**
- `throw new IllegalArgumentException("A conta administrativa não pode ser suspensa por este painel.");`

**Decisões condicionais examinadas:**
- `(account.getRole() == AccountRole.ADMIN)`
- `(request.active())`
- `(!request.active())`
- `request.active()`

**Saída declarada:**
- `return adminAccountView(saved);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
Account saved = accounts.save(account);
ownedStores.forEach(store -> { store.setActive(false); store.setApproved(false); });
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setRoleActive — linha 249

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:249)

```java
@PatchMapping("/accounts/{id}/roles/{role}") @org.springframework.transaction.annotation.Transactional public AdminAccountView setRoleActive(@PathVariable UUID id, @PathVariable AccountRole role, @RequestBody AccountActivity request)
```

Executa a etapa `set Role Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`; `account.getRole`; `account.setRoleActive`; `request.active`; `accounts.save`; `stores.findByOwnerId`; `saved.getId`; `ownedStores.forEach`; `store.setActive`; `store.setApproved`; `stores.saveAll`; `audit`; `role.name`; `adminAccountView`.

**Rejeições locais:**
- `throw new IllegalArgumentException("A conta administrativa não pode ser alterada por este painel.");`

**Decisões condicionais examinadas:**
- `(account.getRole() == AccountRole.ADMIN)`
- `(role == AccountRole.MERCHANT && !request.active())`
- `request.active()`

**Saída declarada:**
- `return adminAccountView(saved);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
account.setRoleActive(role, request.active());
Account saved = accounts.save(account);
ownedStores.forEach(store -> { store.setActive(false); store.setApproved(false); });
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### resetPassword — linha 265

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:265)

```java
@PostMapping("/accounts/{id}/password-reset") @org.springframework.transaction.annotation.Transactional public Map<String, Object> resetPassword(@PathVariable UUID id)
```

Executa a etapa `reset Password` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`; `new SecureRandom`; `Base64.getUrlEncoder`; `account.requirePasswordChange`; `encoder.encode`; `accounts.save`; `audit`; `Map.of`.

**Saída declarada:**
- `return Map.of("temporaryPassword", temporaryPassword, "mustChangePassword", true);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
accounts.save(account);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### pendingApplications — linha 277

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:277)

```java
@GetMapping("/operator-applications") public List<OperatorView> pendingApplications()
```

Executa a etapa `pending Applications` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByOperatorApplicationStatus`.

**Saída declarada:**
- `return accounts.findByOperatorApplicationStatus("PENDING").stream().map(AdminController::operatorView).toList();`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### approveApplication — linha 282

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:282)

```java
@PatchMapping("/operator-applications/{id}/approve") @org.springframework.transaction.annotation.Transactional public OperatorView approveApplication(@PathVariable UUID id)
```

Executa a etapa `approve Application` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`; `account.approveOperatorApplication`; `accounts.save`; `notifications.send`; `saved.getRole`; `audit`; `operatorView`.

**Decisões condicionais examinadas:**
- `saved.getRole()==AccountRole.MERCHANT`

**Saída declarada:**
- `return operatorView(saved);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
Account saved=accounts.save(account);
notifications.send(saved, "Acesso aprovado", "Seu acesso de "+(saved.getRole()==AccountRole.MERCHANT?"vendedor":"entregador")+" foi aprovado. Entre novamente para escolher esse espaço.", "success");
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### rejectApplication — linha 293

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:293)

```java
@PatchMapping("/operator-applications/{id}/reject") @org.springframework.transaction.annotation.Transactional public OperatorView rejectApplication(@PathVariable UUID id)
```

Executa a etapa `reject Application` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`; `account.rejectOperatorApplication`; `accounts.save`; `notifications.send`; `audit`; `operatorView`.

**Saída declarada:**
- `return operatorView(saved);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
Account saved=accounts.save(account);
notifications.send(saved, "Solicitação analisada", "Sua solicitação de acesso não foi aprovada desta vez. Sua conta de cliente continua disponível.", "info");
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deactivate — linha 304

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:304)

```java
@PatchMapping("/operators/{id}/deactivate") @org.springframework.transaction.annotation.Transactional public Map<String, String> deactivate(@PathVariable UUID id)
```

Executa a etapa `deactivate` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`; `account.getRole`; `account.setRoleActive`; `accounts.save`; `stores.findByOwnerId`; `account.getId`; `ownedStores.forEach`; `store.setActive`; `store.setApproved`; `stores.saveAll`; `audit`; `Map.of`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Somente contas de vendedor ou entregador podem ser suspensas por este fluxo.");`

**Decisões condicionais examinadas:**
- `(account.getRole() != AccountRole.MERCHANT && account.getRole() != AccountRole.COURIER)`
- `(role == AccountRole.MERCHANT)`

**Saída declarada:**
- `return Map.of("status", "deactivated");`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
account.setRoleActive(role, false);
accounts.save(account);
ownedStores.forEach(store -> { store.setActive(false); store.setApproved(false); });
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### activate — linha 322

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:322)

```java
@PatchMapping("/operators/{id}/activate") @org.springframework.transaction.annotation.Transactional public Map<String, String> activate(@PathVariable UUID id)
```

Executa a etapa `activate` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`; `account.getRole`; `"PENDING".equals`; `account.getOperatorApplicationStatus`; `account.setRoleActive`; `accounts.save`; `audit`; `Map.of`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Esta conta não pode ser ativada por este fluxo.");`

**Decisões condicionais examinadas:**
- `((account.getRole() != AccountRole.MERCHANT && account.getRole() != AccountRole.COURIER) || "PENDING".equals(account.getOperatorApplicationStatus()))`

**Saída declarada:**
- `return Map.of("status", "active");`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
account.setRoleActive(account.getRole(), true);
accounts.save(account);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### stores — linha 335

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:335)

```java
@GetMapping("/stores") @org.springframework.transaction.annotation.Transactional(readOnly = true) public List<AdminStoreView> stores()
```

Executa a etapa `stores` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findAll`.

**Saída declarada:**
- `return stores.findAll().stream().map(AdminController::adminStoreView).toList();`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### adminStoreView — linha 340

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:340)

```java
private static AdminStoreView adminStoreView(Store store)
```

Executa a etapa `admin Store View` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `store.getId`; `store.getName`; `store.getDescription`; `store.getCategory`; `store.getAddress`; `store.getCity`; `store.getState`; `store.getCnpj`; `store.isActive`; `store.isApproved`; `store.getOwner`; `store.getMarketplaceFeeBps`; `store.getDeliveryFeeCents`; `store.getCoverImageUrl`; `store.getTimezone`; `store.getLatitude`; `store.getLongitude`; `store.isDeliveryEnabled`; `store.isPickupEnabled`; `store.isInPersonEnabled`.

**Saída declarada:**
- `return new AdminStoreView(store.getId(), store.getName(), store.getDescription(), store.getCategory(), store.getAddress(), store.getCity(), store.getState(), store.getCnpj(), store.isActive(), store.isApproved(), store.getOwner().getName(), store.getOwner().getEmail(), store.getMarketplaceFeeBps() / 100.0, store.getDeliveryFeeCents(), store.getCoverImageUrl(),store.getTimezone(),store.getLatitude(),store.getLongitude(),store.isDeliveryEnabled(),store.isPickupEnabled(),store.isInPersonEnabled());`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### approveStore — linha 346

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:346)

```java
@PatchMapping("/stores/{id}/approval") @org.springframework.transaction.annotation.Transactional public Store approveStore(@PathVariable UUID id, @RequestBody Approval request)
```

Executa a etapa `approve Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findById`; `request.approved`; `store.getOwner`; `store.setApproved`; `store.setActive`; `stores.save`; `audit`.

**Rejeições locais:**
- `throw new IllegalArgumentException("A conta do vendedor está suspensa ou sem acesso ativo. Reative o perfil antes de aprovar a loja.");`

**Decisões condicionais examinadas:**
- `(request.approved() && (!store.getOwner().isActive() || !store.getOwner().getGrantedRoles().contains(AccountRole.MERCHANT)))`
- `(!request.approved())`
- `request.approved()`

**Saída declarada:**
- `return saved;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
store.setApproved(request.approved());
if (!request.approved()) store.setActive(false);
Store saved=stores.save(store);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setStoreActive — linha 359

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:359)

```java
@PatchMapping("/stores/{id}/active") @org.springframework.transaction.annotation.Transactional public Store setStoreActive(@PathVariable UUID id, @RequestBody StoreActivity request)
```

Executa a etapa `set Store Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findById`; `store.setActive`; `request.active`; `store.setApproved`; `stores.save`; `audit`.

**Decisões condicionais examinadas:**
- `(!request.active())`
- `request.active()`

**Saída declarada:**
- `return saved;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
store.setActive(request.active());
if (!request.active()) store.setApproved(false);
Store saved=stores.save(store);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setCommission — linha 370

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:370)

```java
@PatchMapping("/stores/{id}/commission") @org.springframework.transaction.annotation.Transactional public Map<String, Object> setCommission(@PathVariable UUID id, @Valid @RequestBody Commission request)
```

Executa a etapa `set Commission` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findById`; `store.setMarketplaceFeeBps`; `request.percent`; `stores.save`; `audit`; `Map.of`.

**Saída declarada:**
- `return Map.of("storeId", id, "commissionPercent", request.percent());`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
store.setMarketplaceFeeBps(request.percent().movePointRight(2).intValueExact());
stores.save(store);
```

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### auditEvents — linha 380

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:380)

```java
@GetMapping("/audit-events") @org.springframework.transaction.annotation.Transactional(readOnly = true) public org.springframework.data.domain.Page<AdminAuditView> auditEvents( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size)
```

Executa a etapa `audit Events` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `auditService.latest`; `event.getId`; `event.getActorId`; `event.getAction`; `event.getTargetType`; `event.getTargetId`; `event.getSummary`; `event.getCreatedAt`.

**Saída declarada:**
- `return auditService.latest(page, size).map(event -> new AdminAuditView(event.getId(), event.getActorId(), event.getAction(), event.getTargetType(), event.getTargetId(), event.getSummary(), event.getCreatedAt()));`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### audit — linha 388

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:388)

```java
private void audit(String action, String targetType, Object targetId, String summary)
```

Executa a etapa `audit` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `org.springframework.security.core.context.SecurityContextHolder.getContext`; `authentication.getPrincipal`; `auditService.record`; `UUID.fromString`; `jwt.getSubject`.

**Rejeições locais:**
- `throw new IllegalStateException("Não foi possível identificar o administrador da alteração.");`

**Decisões condicionais examinadas:**
- `(authentication == null || !(authentication.getPrincipal() instanceof org.springframework.security.oauth2.jwt.Jwt jwt))`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### operatorView — linha 395

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:395)

```java
private static OperatorView operatorView(Account account)
```

Executa a etapa `operator View` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `account.getRequestedOperatorRole`; `account.getRole`; `account.getId`; `account.getName`; `account.getEmail`; `account.getGrantedRoles`; `account.getOperatorApplicationStatus`; `account.getCreatedAt`.

**Decisões condicionais examinadas:**
- `account.getRequestedOperatorRole() != null`

**Saída declarada:**
- `return new OperatorView(account.getId(), account.getName(), account.getEmail(), viewRole, account.getGrantedRoles().contains(viewRole), account.getOperatorApplicationStatus(), account.getCreatedAt());`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### adminAccountView — linha 401

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:401)

```java
private static AdminAccountView adminAccountView(Account account)
```

Executa a etapa `admin Account View` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `account.getCpf`; `cpf.substring`; `account.getId`; `account.getName`; `account.getEmail`; `account.getPhone`; `account.getAddressLine`; `account.getAddressComplement`; `account.getNeighborhood`; `account.getCity`; `account.getState`; `account.getPostalCode`; `account.getRole`; `account.getRegisteredRoles`; `account.getGrantedRoles`; `account.isActive`; `account.getOperatorApplicationStatus`; `account.getCreatedAt`.

**Decisões condicionais examinadas:**
- `cpf == null`

**Saída declarada:**
- `return new AdminAccountView(account.getId(), account.getName(), account.getEmail(), maskedCpf, account.getPhone(), account.getAddressLine(), account.getAddressComplement(), account.getNeighborhood(), account.getCity(), account.getState(), account.getPostalCode(), account.getRole(), account.getRegisteredRoles(), account.getGrantedRoles(), account.isActive(), account.getOperatorApplicationStatus(), account.getCreatedAt());`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### OperatorRequest — linha 412

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:412)

```java
public OperatorRequest
```

Executa a etapa `Operator Request` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalArgumentException("Este fluxo cria somente vendedores ou entregadores.");`

**Decisões condicionais examinadas:**
- `(role != AccountRole.MERCHANT && role != AccountRole.COURIER)`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### of — linha 438

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:438)

```java
static ServiceCityView of(ServiceCity city)
```

Executa a etapa `of` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `city.getId`; `city.getCity`; `city.getState`; `city.getPostalCode`; `city.isEnabled`.

**Saída declarada:**
- `return new ServiceCityView(city.getId(), city.getCity(), city.getState(), city.getPostalCode(), city.isEnabled());`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/admin/RefundAdminController.java

Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria.

Fonte: [RefundAdminController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/RefundAdminController.java) · SHA-256 `b2b512c34a7b398579c06e5df94dde1ba9a072400f92f38a2df16a2dc33b4278`

**Contrato de dados/estados:**
```java
public record RefundCase(UUID paymentId, String providerPaymentId, String storeName, UUID operationId,
            String operationType, long amountCents, PaymentStatus status, int attempts,
            Instant lastAttemptAt, String attentionMessage)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### RefundAdminController — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/RefundAdminController.java:21)

```java
public RefundAdminController(PaymentRepository payments)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### list — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/RefundAdminController.java:25)

```java
@GetMapping @Transactional(readOnly = true) public List<RefundCase> list()
```

Executa a etapa `list` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payments.findByStatusInOrderByLastRefundAttemptAtAsc`; `List.of`.

**Saída declarada:**
- `return payments.findByStatusInOrderByLastRefundAttemptAtAsc(List.of(PaymentStatus.REFUND_PENDING, PaymentStatus.REFUND_REVIEW)) .stream().map(RefundAdminController::view).toList();`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### view — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/RefundAdminController.java:32)

```java
private static RefundCase view(Payment payment)
```

Executa a etapa `view` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payment.getOrder`; `payment.getReservation`; `order.getStore`; `reservation.getStore`; `payment.getId`; `payment.getProviderReference`; `store.getName`; `order.getId`; `reservation.getId`; `payment.getAmountCents`; `payment.getStatus`; `payment.getRefundAttempts`; `payment.getLastRefundAttemptAt`; `payment.getRefundLastError`.

**Decisões condicionais examinadas:**
- `order != null`

**Saída declarada:**
- `return new RefundCase(payment.getId(), payment.getProviderReference(), store.getName(), order != null ? order.getId() : reservation.getId(), order != null ? "ORDER" : "RESERVATION", payment.getAmountCents(), payment.getStatus(), payment.getRefundAttempts(), payment.getLastRefundAttemptAt(), payment.getRefundLastError());`

**Decisão de engenharia:** Administração. Operações exigem papel ADMIN; alterações sensíveis instrumentadas são registradas sem copiar segredos para a auditoria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java

Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade.

Fonte: [ApiExceptionHandler.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java) · SHA-256 `446decae2b1b10ff10b974f29dca6ab29bc27ade02afe082a23ecb4a90ec7504`

### malformedRequest — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java:19)

```java
@ExceptionHandler({org.springframework.http.converter.HttpMessageNotReadableException.class, org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class, org.springframework.web.bind.MissingServletRequestParameterException.class}) ResponseEntity<Map<String,String>> malformedRequest(Exception exception)
```

Executa a etapa `malformed Request` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ResponseEntity.badRequest`; `Map.of`.

**Saída declarada:**
- `return ResponseEntity.badRequest().body(Map.of("error","Confira o formato dos dados enviados e tente novamente."));`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### unauthenticated — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java:26)

```java
@ExceptionHandler(AuthenticationException.class) ResponseEntity<Map<String, String>> unauthenticated(AuthenticationException exception)
```

Executa a etapa `unauthenticated` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ResponseEntity.status`; `Map.of`.

**Saída declarada:**
- `return ResponseEntity.status(HttpStatus.UNAUTHORIZED) .body(Map.of("error", "E-mail ou senha inválidos."));`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### forbidden — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java:32)

```java
@ExceptionHandler(AccessDeniedException.class) ResponseEntity<Map<String, String>> forbidden(AccessDeniedException exception)
```

Executa a etapa `forbidden` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ResponseEntity.status`; `Map.of`.

**Saída declarada:**
- `return ResponseEntity.status(HttpStatus.FORBIDDEN) .body(Map.of("error", "Você não tem permissão para realizar esta ação."));`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### responseStatus — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java:38)

```java
@ExceptionHandler(ResponseStatusException.class) ResponseEntity<Map<String, String>> responseStatus(ResponseStatusException exception)
```

Executa a etapa `response Status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `exception.getReason`; `message.isBlank`; `ResponseEntity.status`; `exception.getStatusCode`; `Map.of`.

**Decisões condicionais examinadas:**
- `(message == null || message.isBlank())`

**Saída declarada:**
- `return ResponseEntity.status(exception.getStatusCode()).body(Map.of("error", message));`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### conflict — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java:45)

```java
@ExceptionHandler(IllegalArgumentException.class) ResponseEntity<Map<String, String>> conflict(IllegalArgumentException exception)
```

Executa a etapa `conflict` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ResponseEntity.status`; `Map.of`; `exception.getMessage`.

**Saída declarada:**
- `return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", exception.getMessage()));`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### invalidTransition — linha 50

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java:50)

```java
@ExceptionHandler(IllegalStateException.class) ResponseEntity<Map<String, String>> invalidTransition(IllegalStateException exception)
```

Executa a etapa `invalid Transition` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ResponseEntity.status`; `Map.of`; `exception.getMessage`.

**Saída declarada:**
- `return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", exception.getMessage()));`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### notFound — linha 55

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java:55)

```java
@ExceptionHandler(NoSuchElementException.class) ResponseEntity<Map<String, String>> notFound(NoSuchElementException exception)
```

Executa a etapa `not Found` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `exception.getMessage`; `ResponseEntity.status`; `Map.of`; `message.isBlank`.

**Decisões condicionais examinadas:**
- `message == null || message.isBlank()`

**Saída declarada:**
- `return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", message == null || message.isBlank() ? "Registro não encontrado." : message));`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### internalError — linha 62

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java:62)

```java
@ExceptionHandler(Exception.class) ResponseEntity<Map<String, String>> internalError(Exception exception)
```

Executa a etapa `internal Error` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `LOG.error`; `ResponseEntity.status`; `Map.of`.

**Saída declarada:**
- `return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) .body(Map.of("error", "Ocorreu um erro inesperado. Tente novamente em instantes."));`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### validation — linha 69

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java:69)

```java
@ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String, String>> validation(MethodArgumentNotValidException exception)
```

Executa a etapa `validation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `exception.getBindingResult`; `error.getField`; `error.getDefaultMessage`; `ResponseEntity.badRequest`; `Map.of`.

**Saída declarada:**
- `return ResponseEntity.badRequest().body(Map.of("error", message));`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### duplicate — linha 77

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/ApiExceptionHandler.java:77)

```java
@ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<Map<String, String>> duplicate(DataIntegrityViolationException exception)
```

Executa a etapa `duplicate` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ResponseEntity.status`; `Map.of`.

**Saída declarada:**
- `return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", "Conflito de dados: registro duplicado ou indisponível."));`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/api/HealthController.java

Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade.

Fonte: [HealthController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/HealthController.java) · SHA-256 `1087c493c11d4bf2c30049adce590fdd6a195355e5dde263f1f84aafaf154fda`

### status — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/HealthController.java:11)

```java
@GetMapping("/status") public Map<String, String> status()
```

Executa a etapa `status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Map.of`.

**Saída declarada:**
- `return Map.of("status", "online", "service", "food-delivery-api");`

**Decisão de engenharia:** Transporte HTTP e sessão. Cookies não ficam acessíveis ao JavaScript; mensagens de falha precisam distinguir autorização, validação e indisponibilidade. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [CatalogController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java) · SHA-256 `d8a44eb4ff1fe369c402c178314ddd936d47a3c505a98acb9c4305449c30a940`

**Contrato de dados/estados:**
```java
public record LocationView(String city, String state)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record StoreRequest(@NotBlank @Size(max=140) String name, @Size(max=1000) String description,
                               @NotBlank @Size(max=250) String address, @NotBlank @Size(max=60) String category,
                               @NotBlank @Size(max=100) String city,@NotBlank @Size(min=2,max=2) String state,
                               @jakarta.validation.constraints.Pattern(regexp="|\\d
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record ItemRequest(@NotBlank @Size(max=140) String name, @Size(max=1000) String description,
                              @PositiveOrZero long priceCents, @PositiveOrZero int stock, boolean serviceItem, boolean active,
                              boolean payAfterService,@Min(5) @Max(480) Integer appointmentDurationMinutes)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record ProfessionalRequest(@NotBlank @Size(max=100) String name)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record ProfessionalActiveRequest(boolean active)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record ProfessionalView(UUID id,String name,boolean active)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record StoreView(UUID id, String name, String description, String address, String city, String state,
                            String cnpj, String category, String coverImageUrl, boolean active, boolean approved,
                            long deliveryFeeCents, boolean available,String timezone,Double latitude,Double longitude,boolean deliveryEnabled,boolean pickupEnabled,boolean inPersonEnabled)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CatalogItemView(UUID id, String name, String description, long priceCents, int stock,
                                  boolean active, boolean serviceItem, boolean payAfterService,int appointmentDurationMinutes)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### CatalogController — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:29)

```java
public CatalogController(StoreRepository stores, CatalogItemRepository items, AccountRepository accounts, PlatformRequestRepository platformRequests, ServiceCityRepository serviceCities, StoreCategoryRepository storeCategories, StoreProfessionalRepository professionals, StoreOperatingHours operatingHours,StoreHoursChangeService hoursChanges)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### categories — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:32)

```java
@GetMapping("/store-categories") @Transactional(readOnly=true) public List<StoreCategories.Category> categories()
```

Executa a etapa `categories` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `java.util.stream.Stream.concat`; `StoreCategories.ALL.stream`; `storeCategories.findAllByOrderByNameAsc`; `category.getName`; `category.getEmoji`.

**Saída declarada:**
- `return java.util.stream.Stream.concat(StoreCategories.ALL.stream(), storeCategories.findAllByOrderByNameAsc().stream() .map(category -> new StoreCategories.Category(category.getName(), category.getEmoji()))) .toList();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### locations — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:38)

```java
@GetMapping("/store-locations") @Transactional(readOnly=true) public List<LocationView> locations()
```

Executa a etapa `locations` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findByActiveTrueAndApprovedTrue`; `store.getCity`; `store.getState`; `java.util.stream.Stream.concat`; `fromStores.stream`; `serviceCities.findByEnabledTrue`; `c.getCity`; `c.getState`; `Comparator.comparing`.

**Saída declarada:**
- `return java.util.stream.Stream.concat(fromStores.stream(),serviceCities.findByEnabledTrue().stream().map(c->new LocationView(c.getCity(),c.getState()))) .distinct().sorted(Comparator.comparing(LocationView::city, String.CASE_INSENSITIVE_ORDER).thenComparing(LocationView::state)).toList();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### stores — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:47)

```java
@GetMapping("/stores") @Transactional(readOnly=true) public List<StoreView> stores(@RequestParam(required=false) String city,@RequestParam(required=false) String state)
```

Executa a etapa `stores` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findByActiveTrueAndApprovedTrue`; `city.isBlank`; `available.stream`; `br.com.fooddelivery.platform.CityMatcher.sameCity`; `state.isBlank`; `store.getState`; `store.getCity`.

**Decisões condicionais examinadas:**
- `(city==null||city.isBlank())`
- `state==null||state.isBlank()`

**Saída declarada:**
- `return available.stream().map(StoreView::of).toList();`
- `return available.stream().filter(store->br.com.fooddelivery.platform.CityMatcher.sameCity( city, state==null||state.isBlank()?store.getState():state, store.getCity(), store.getState())) .map(StoreView::of).toList();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### items — linha 54

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:54)

```java
@GetMapping("/stores/{id}/items") @Transactional(readOnly=true) public List<CatalogItemView> items(@PathVariable UUID id)
```

Executa a etapa `items` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findById`; `store.isAvailable`; `items.findByStoreIdAndActiveTrue`.

**Rejeições locais:**
- `throw new NoSuchElementException("Loja não encontrada.");`

**Decisões condicionais examinadas:**
- `(!store.isAvailable())`

**Saída declarada:**
- `return items.findByStoreIdAndActiveTrue(id).stream().map(CatalogItemView::of).toList();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createStore — linha 59

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:59)

```java
@PostMapping("/merchant/stores") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") @Transactional public StoreView createStore(@Valid @RequestBody StoreRequest req, Authentication auth)
```

Executa a etapa `create Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `validateCategory`; `req.category`; `current`; `req.name`; `req.description`; `req.address`; `store.setLocation`; `req.city`; `req.state`; `store.setCoordinates`; `req.latitude`; `req.longitude`; `store.configureFulfillment`; `req.deliveryEnabled`; `req.pickupEnabled`; `req.inPersonEnabled`; `req.cnpj`; `store.setCnpjOnce`; `store.setDeliveryFeeCents`; `req.deliveryFeeCents`; `store.setTimezone`; `req.timezone`; `stores.save`; `platformRequests.save`; `PlatformRequest.store`; `StoreView.of`.

**Decisões condicionais examinadas:**
- `(req.cnpj()!=null&&!req.cnpj().isBlank())`

**Saída declarada:**
- `return StoreView.of(store);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
Store store=new Store(owner, req.name(), req.description(), req.address(), req.category());store.setLocation(req.city().trim(),req.state());store.setCoordinates(req.latitude(),req.longitude());store.configureFulfillment(req.deliveryEnabled(),req.pickupEnabled(),req.inPersonEnabled());if(req.cnpj()!=null&&!req.cnpj().isBlank())store.setCnpjOnce(req.cnpj());store.setDeliveryFeeCents(req.deliveryFeeCents());store.setTimezone(req.timezone());store=stores.save(store);platformRequests.save(PlatformRequest.store(owner,store));return StoreView.of(store);
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### myStores — linha 67

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:67)

```java
@GetMapping("/merchant/stores/mine") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<StoreView> myStores(Authentication auth)
```

Executa a etapa `my Stores` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findByOwnerId`; `subject`.

**Saída declarada:**
- `return stores.findByOwnerId(subject(auth)).stream().map(StoreView::of).toList();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### resubmitStore — linha 70

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:70)

```java
@PostMapping("/merchant/stores/{id}/resubmit") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public StoreView resubmitStore(@PathVariable UUID id,Authentication auth)
```

Executa a etapa `resubmit Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `owned`; `platformRequests.findFirstByStoreIdOrderByCreatedAtDesc`; `"REJECTED".equals`; `previous.getStatus`; `store.setApproved`; `store.setActive`; `stores.save`; `platformRequests.save`; `PlatformRequest.store`; `store.getOwner`; `StoreView.of`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Só é possível reenviar uma loja que já foi reprovada.");`

**Decisões condicionais examinadas:**
- `(!"REJECTED".equals(previous.getStatus()))`

**Saída declarada:**
- `return StoreView.of(store);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
public StoreView resubmitStore(@PathVariable UUID id,Authentication auth){Store store=owned(id,auth);var previous=platformRequests.findFirstByStoreIdOrderByCreatedAtDesc(id).orElseThrow(()->new IllegalArgumentException("Não encontramos uma solicitação anterior desta loja."));if(!"REJECTED".equals(previous.getStatus()))throw new IllegalArgumentException("Só é possível reenviar uma loja que já foi reprovada.");store.setApproved(false);store.setActive(true);stores.save(store);platformRequests.save(PlatformRequest.store(store.getOwner(),store));return StoreView.of(store);}
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateStore — linha 72

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:72)

```java
@PutMapping("/merchant/stores/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public StoreView updateStore(@PathVariable UUID id, @Valid @RequestBody StoreRequest req, Authentication auth)
```

Executa a etapa `update Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `validateCategory`; `req.category`; `stores.findByIdForUpdate`; `store.getOwner`; `subject`; `platformRequests.findFirstByStoreIdOrderByCreatedAtDesc`; `"PENDING".equals`; `r.getStatus`; `hoursChanges.requireSafeTimezoneChange`; `req.timezone`; `store.update`; `req.name`; `req.description`; `req.address`; `req.city`; `req.state`; `req.active`; `req.deliveryFeeCents`; `store.setCoordinates`; `req.latitude`; `req.longitude`; `store.configureFulfillment`; `req.deliveryEnabled`; `req.pickupEnabled`; `req.inPersonEnabled`; `store.setTimezone`; `StoreView.of`; `stores.save`.

**Rejeições locais:**
- `throw new AccessDeniedException("Loja não pertence à sua conta.");`
- `throw new IllegalStateException("A loja está em análise. Aguarde a decisão da administração antes de alterar os dados.");`

**Decisões condicionais examinadas:**
- `(!store.getOwner().getId().equals(subject(auth)))`
- `(platformRequests.findFirstByStoreIdOrderByCreatedAtDesc(id).filter(r -> "PENDING".equals(r.getStatus())).isPresent())`
- `(req.timezone()!=null&&!req.timezone().isBlank())`

**Saída declarada:**
- `return StoreView.of(stores.save(store));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
store.update(req.name(), req.description(), req.address(), req.city().trim(), req.state(), req.category(), req.active(),req.deliveryFeeCents());store.setCoordinates(req.latitude(),req.longitude());store.configureFulfillment(req.deliveryEnabled(),req.pickupEnabled(),req.inPersonEnabled());if(req.timezone()!=null&&!req.timezone().isBlank())store.setTimezone(req.timezone()); return StoreView.of(stores.save(store));
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### merchantHours — linha 80

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:80)

```java
@GetMapping("/merchant/stores/{id}/hours") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public StoreOperatingHours.Configuration merchantHours(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `merchant Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `operatingHours.read`; `owned`.

**Saída declarada:**
- `return operatingHours.read(owned(id, auth));`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### previewHours — linha 82

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:82)

```java
@PostMapping("/merchant/stores/{id}/hours/preview") @PreAuthorize("hasRole('MERCHANT')") public StoreHoursChangeService.Preview previewHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)
```

Executa a etapa `preview Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `hoursChanges.preview`; `subject`.

**Saída declarada:**
- `return hoursChanges.preview(id,subject(auth),request);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateMerchantHours — linha 84

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:84)

```java
@PutMapping("/merchant/stores/{id}/hours") @PreAuthorize("hasRole('MERCHANT')") public StoreHoursChangeService.Saved updateMerchantHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)
```

Executa a etapa `update Merchant Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `hoursChanges.save`; `subject`.

**Saída declarada:**
- `return hoursChanges.save(id,subject(auth),request);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
public StoreHoursChangeService.Saved updateMerchantHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth){return hoursChanges.save(id,subject(auth),request);}
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### administratorHours — linha 86

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:86)

```java
@GetMapping("/admin/stores/{id}/hours") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public StoreOperatingHours.Configuration administratorHours(@PathVariable UUID id)
```

Executa a etapa `administrator Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `operatingHours.read`; `stores.findById`.

**Saída declarada:**
- `return operatingHours.read(stores.findById(id).orElseThrow());`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### administratorPreviewHours — linha 88

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:88)

```java
@PostMapping("/admin/stores/{id}/hours/preview") @PreAuthorize("hasRole('ADMIN')") public StoreHoursChangeService.Preview administratorPreviewHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)
```

Executa a etapa `administrator Preview Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `hoursChanges.preview`; `subject`.

**Saída declarada:**
- `return hoursChanges.preview(id,subject(auth),request,true);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### administratorSaveHours — linha 90

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:90)

```java
@PutMapping("/admin/stores/{id}/hours") @PreAuthorize("hasRole('ADMIN')") public StoreHoursChangeService.Saved administratorSaveHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)
```

Executa a etapa `administrator Save Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `hoursChanges.save`; `subject`.

**Saída declarada:**
- `return hoursChanges.save(id,subject(auth),request,true);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
public StoreHoursChangeService.Saved administratorSaveHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth){return hoursChanges.save(id,subject(auth),request,true);}
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### publicHours — linha 92

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:92)

```java
@GetMapping("/stores/{id}/hours") @Transactional(readOnly=true) public StoreOperatingHours.Configuration publicHours(@PathVariable UUID id)
```

Executa a etapa `public Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findById`; `operatingHours.read`; `configuration.exceptions`; `exception.date`; `exception.closed`; `exception.opensAt`; `exception.closesAt`; `exception.shifts`; `configuration.enabled`; `configuration.weekly`; `configuration.weeklyShifts`.

**Saída declarada:**
- `return new StoreOperatingHours.Configuration(configuration.enabled(),configuration.weekly(),publicExceptions,configuration.weeklyShifts());`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createItem — linha 102

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:102)

```java
@PostMapping("/merchant/stores/{id}/items") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") public CatalogItemView createItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth)
```

Executa a etapa `create Item` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `owned`; `CatalogItemView.of`; `items.save`; `req.name`; `req.description`; `req.priceCents`; `req.stock`; `req.serviceItem`; `req.payAfterService`; `req.appointmentDurationMinutes`.

**Decisões condicionais examinadas:**
- `req.appointmentDurationMinutes()==null`

**Saída declarada:**
- `return CatalogItemView.of(items.save(new CatalogItem(store, req.name(), req.description(), req.priceCents(), req.stock(), req.serviceItem(), req.payAfterService(), req.appointmentDurationMinutes()==null?30:req.appointmentDurationMinutes())));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
return CatalogItemView.of(items.save(new CatalogItem(store, req.name(), req.description(), req.priceCents(), req.stock(), req.serviceItem(), req.payAfterService(), req.appointmentDurationMinutes()==null?30:req.appointmentDurationMinutes())));
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### myStoreItems — linha 108

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:108)

```java
@GetMapping("/merchant/stores/{id}/items") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<CatalogItemView> myStoreItems(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `my Store Items` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `owned`; `items.findByStoreId`.

**Saída declarada:**
- `return items.findByStoreId(id).stream().map(CatalogItemView::of).toList();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### storeProfessionals — linha 114

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:114)

```java
@GetMapping("/stores/{id}/professionals") @Transactional(readOnly=true) public List<ProfessionalView> storeProfessionals(@PathVariable UUID id)
```

Executa a etapa `store Professionals` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findById`; `professionals.findByStoreIdAndActiveTrueOrderByNameAsc`.

**Rejeições locais:**
- `throw new NoSuchElementException("Loja não encontrada.");`

**Decisões condicionais examinadas:**
- `(stores.findById(id).filter(Store::isAvailable).isEmpty())`

**Saída declarada:**
- `return professionals.findByStoreIdAndActiveTrueOrderByNameAsc(id).stream().map(ProfessionalView::of).toList();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### myProfessionals — linha 119

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:119)

```java
@GetMapping("/merchant/stores/{id}/professionals") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<ProfessionalView> myProfessionals(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `my Professionals` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `owned`; `professionals.findByStoreIdOrderByNameAsc`.

**Saída declarada:**
- `return professionals.findByStoreIdOrderByNameAsc(id).stream().map(ProfessionalView::of).toList();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createProfessional — linha 123

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:123)

```java
@PostMapping("/merchant/stores/{id}/professionals") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public ProfessionalView createProfessional(@PathVariable UUID id,@Valid @RequestBody ProfessionalRequest request,Authentication auth)
```

Executa a etapa `create Professional` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `owned`; `ProfessionalView.of`; `professionals.save`; `request.name`.

**Saída declarada:**
- `return ProfessionalView.of(professionals.save(new StoreProfessional(store,request.name())));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
Store store=owned(id,auth); return ProfessionalView.of(professionals.save(new StoreProfessional(store,request.name())));
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateProfessional — linha 127

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:127)

```java
@PutMapping("/merchant/professionals/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ProfessionalView updateProfessional(@PathVariable UUID id,@Valid @RequestBody ProfessionalRequest request,Authentication auth)
```

Executa a etapa `update Professional` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `professionals.findById`; `professional.getStore`; `subject`; `professional.updateName`; `request.name`; `ProfessionalView.of`; `professionals.save`.

**Rejeições locais:**
- `throw new AccessDeniedException("Profissional não pertence à sua loja.");`

**Decisões condicionais examinadas:**
- `(!professional.getStore().getOwner().getId().equals(subject(auth)))`

**Saída declarada:**
- `return ProfessionalView.of(professionals.save(professional));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
professional.updateName(request.name()); return ProfessionalView.of(professionals.save(professional));
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setProfessionalActive — linha 133

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:133)

```java
@PatchMapping("/merchant/professionals/{id}/active") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ProfessionalView setProfessionalActive(@PathVariable UUID id,@RequestBody ProfessionalActiveRequest request,Authentication auth)
```

Executa a etapa `set Professional Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `professionals.findById`; `professional.getStore`; `subject`; `professional.setActive`; `request.active`; `ProfessionalView.of`; `professionals.save`.

**Rejeições locais:**
- `throw new AccessDeniedException("Profissional não pertence à sua loja.");`

**Decisões condicionais examinadas:**
- `(!professional.getStore().getOwner().getId().equals(subject(auth)))`

**Saída declarada:**
- `return ProfessionalView.of(professionals.save(professional));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
professional.setActive(request.active()); return ProfessionalView.of(professionals.save(professional));
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateItem — linha 139

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:139)

```java
@PutMapping("/merchant/items/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public CatalogItemView updateItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth)
```

Executa a etapa `update Item` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `items.findById`; `item.getStore`; `subject`; `item.update`; `req.name`; `req.description`; `req.priceCents`; `req.stock`; `req.active`; `req.serviceItem`; `req.payAfterService`; `req.appointmentDurationMinutes`; `CatalogItemView.of`; `items.save`.

**Rejeições locais:**
- `throw new AccessDeniedException("Item não pertence à sua loja.");`

**Decisões condicionais examinadas:**
- `(!item.getStore().getOwner().getId().equals(subject(auth)))`
- `req.appointmentDurationMinutes()==null`

**Saída declarada:**
- `return CatalogItemView.of(items.save(item));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
item.update(req.name(), req.description(), req.priceCents(), req.stock(), req.active(), req.serviceItem(), req.payAfterService(), req.appointmentDurationMinutes()==null?30:req.appointmentDurationMinutes()); return CatalogItemView.of(items.save(item));
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deactivateItem — linha 144

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:144)

```java
@DeleteMapping("/merchant/items/{id}") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional public void deactivateItem(@PathVariable UUID id,Authentication auth)
```

Executa a etapa `deactivate Item` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `items.findById`; `item.getStore`; `subject`; `item.isActive`; `items.deactivateOwned`.

**Rejeições locais:**
- `throw new AccessDeniedException("Item não pertence à sua loja.");`
- `throw new IllegalStateException("O item mudou em outra sessão. Atualize o catálogo e tente novamente.");`

**Decisões condicionais examinadas:**
- `(!item.getStore().getOwner().getId().equals(subject(auth)))`
- `(!item.isActive())`
- `(items.deactivateOwned(id, subject(auth)) != 1)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### owned — linha 151

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:151)

```java
private Store owned(UUID id, Authentication auth)
```

Executa a etapa `owned` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findById`; `store.getOwner`; `subject`.

**Rejeições locais:**
- `throw new AccessDeniedException("Loja não pertence à sua conta.");`

**Decisões condicionais examinadas:**
- `(!store.getOwner().getId().equals(subject(auth)))`

**Saída declarada:**
- `return store;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### current — linha 156

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:156)

```java
private Account current(Authentication auth)
```

Executa a etapa `current` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`; `subject`.

**Saída declarada:**
- `return accounts.findById(subject(auth)).orElseThrow();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 157

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:157)

```java
private UUID subject(Authentication auth)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `auth.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject());`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### validateCategory — linha 158

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:158)

```java
private void validateCategory(String category)
```

Executa a etapa `validate Category` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `StoreCategories.isSupported`; `storeCategories.findByNameIgnoreCase`; `category.trim`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Escolha uma categoria disponível no catálogo.");`

**Decisões condicionais examinadas:**
- `(!StoreCategories.isSupported(category) && storeCategories.findByNameIgnoreCase(category.trim()).isEmpty())`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### of — linha 175

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:175)

```java
static ProfessionalView of(StoreProfessional p)
```

Executa a etapa `of` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `p.getId`; `p.getName`; `p.isActive`.

**Saída declarada:**
- `return new ProfessionalView(p.getId(),p.getName(),p.isActive());`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### of — linha 179

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:179)

```java
static StoreView of(Store s)
```

Executa a etapa `of` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `s.getId`; `s.getName`; `s.getDescription`; `s.getAddress`; `s.getCity`; `s.getState`; `s.getCnpj`; `s.getCategory`; `s.getCoverImageUrl`; `s.isActive`; `s.isApproved`; `s.getDeliveryFeeCents`; `s.isAvailable`; `s.getTimezone`; `s.getLatitude`; `s.getLongitude`; `s.isDeliveryEnabled`; `s.isPickupEnabled`; `s.isInPersonEnabled`.

**Saída declarada:**
- `return new StoreView(s.getId(), s.getName(), s.getDescription(), s.getAddress(), s.getCity(), s.getState(), s.getCnpj(), s.getCategory(), s.getCoverImageUrl(), s.isActive(), s.isApproved(), s.getDeliveryFeeCents(), s.isAvailable(),s.getTimezone(),s.getLatitude(),s.getLongitude(),s.isDeliveryEnabled(),s.isPickupEnabled(),s.isInPersonEnabled());`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### of — linha 185

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:185)

```java
static CatalogItemView of(CatalogItem i)
```

Executa a etapa `of` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `i.getId`; `i.getName`; `i.getDescription`; `i.getPriceCents`; `i.getStock`; `i.isActive`; `i.isServiceItem`; `i.isPayAfterService`; `i.getAppointmentDurationMinutes`.

**Saída declarada:**
- `return new CatalogItemView(i.getId(), i.getName(), i.getDescription(), i.getPriceCents(), i.getStock(), i.isActive(), i.isServiceItem(), i.isPayAfterService(),i.getAppointmentDurationMinutes());`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [CatalogItem.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java) · SHA-256 `a87e6355e279146116517599b884cb1ef09f2aa4f188901d96968863104de41a`

### CatalogItem — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:24)

```java
protected CatalogItem()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### CatalogItem — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:25)

```java
public CatalogItem(Store store, String name, String description, long priceCents, int stock, boolean serviceItem)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### CatalogItem — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:28)

```java
public CatalogItem(Store store, String name, String description, long priceCents, int stock, boolean serviceItem, boolean payAfterService)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### CatalogItem — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:31)

```java
public CatalogItem(Store store, String name, String description, long priceCents, int stock, boolean serviceItem, boolean payAfterService, int appointmentDurationMinutes)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `validateDuration`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Pagamento após o atendimento só pode ser usado em serviços.");`

**Decisões condicionais examinadas:**
- `(payAfterService && !serviceItem)`
- `serviceItem`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:36)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStore — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:37)

```java
public Store getStore()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return store;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getName — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:38)

```java
public String getName()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return name;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getDescription — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:39)

```java
public String getDescription()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return description;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getPriceCents — linha 40

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:40)

```java
public long getPriceCents()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return priceCents;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStock — linha 41

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:41)

```java
public int getStock()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return stock;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isActive — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:42)

```java
public boolean isActive()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return active;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isServiceItem — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:43)

```java
public boolean isServiceItem()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return serviceItem;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isPayAfterService — linha 44

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:44)

```java
public boolean isPayAfterService()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return payAfterService;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAppointmentDurationMinutes — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:45)

```java
public int getAppointmentDurationMinutes()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return appointmentDurationMinutes;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### update — linha 46

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:46)

```java
public void update(String name, String description, long priceCents, int stock, boolean active, boolean serviceItem, boolean payAfterService)
```

Executa a etapa `update` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `update`.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### update — linha 49

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:49)

```java
public void update(String name, String description, long priceCents, int stock, boolean active, boolean serviceItem, boolean payAfterService, int appointmentDurationMinutes)
```

Executa a etapa `update` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `validateDuration`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Pagamento após o atendimento só pode ser usado em serviços.");`

**Decisões condicionais examinadas:**
- `(payAfterService && !serviceItem)`
- `serviceItem`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### validateDuration — linha 55

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:55)

```java
private static void validateDuration(boolean serviceItem, int duration)
```

Executa a etapa `validate Duration` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalArgumentException("A duração do serviço deve ficar entre 5 minutos e 8 horas.");`

**Decisões condicionais examinadas:**
- `(serviceItem && (duration < 5 || duration > 480))`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### reserve — linha 58

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:58)

```java
public void reserve(int quantity)
```

Executa a etapa `reserve` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalStateException("Item indisponível ou sem estoque suficiente.");`

**Decisões condicionais examinadas:**
- `(!active || (!serviceItem && stock < quantity))`
- `(!serviceItem)`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### restore — linha 62

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:62)

```java
public void restore(int quantity)
```

Executa a etapa `restore` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `(!serviceItem)`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deactivate — linha 63

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItem.java:63)

```java
public void deactivate()
```

Executa a etapa `deactivate` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItemRepository.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [CatalogItemRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItemRepository.java) · SHA-256 `177f17625e50e94ddc6b06bf708ffe3c369bc74242e26a47cc967a967a392d9d`

### findByStoreIdAndActiveTrue — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItemRepository.java:10)

```java
List<CatalogItem> findByStoreIdAndActiveTrue(UUID storeId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStoreId — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItemRepository.java:11)

```java
List<CatalogItem> findByStoreId(UUID storeId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deactivateOwned — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogItemRepository.java:13)

```java
@Modifying(clearAutomatically = true, flushAutomatically = true) @Query("update CatalogItem item set item.active = false, item.version = item.version + 1 " + "where item.id = :itemId and item.active = true and item.store.id in " + "(select store.id from Store store where store.owner.id = :ownerId)") int deactivateOwned(@Param("itemId") UUID itemId, @Param("ownerId") UUID ownerId);
```

Executa a etapa `deactivate Owned` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/Store.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [Store.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java) · SHA-256 `c8b23cbace52ec12db69d8549eee8a4dd117d73c0c0af6b3bce5bff594b1b290`

### Store — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:38)

```java
protected Store()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### Store — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:39)

```java
public Store(Account owner, String name, String description, String address, String category)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:42)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getOwner — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:43)

```java
public Account getOwner()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return owner;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getName — linha 44

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:44)

```java
public String getName()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return name;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getDescription — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:45)

```java
public String getDescription()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return description;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAddress — linha 46

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:46)

```java
public String getAddress()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return address;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCity — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:47)

```java
public String getCity()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return city;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getState — linha 48

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:48)

```java
public String getState()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return state;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getLatitude — linha 49

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:49)

```java
public Double getLatitude()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return latitude;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getLongitude — linha 49

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:49)

```java
public Double getLongitude()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return longitude;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isDeliveryEnabled — linha 50

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:50)

```java
public boolean isDeliveryEnabled()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return deliveryEnabled;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isPickupEnabled — linha 51

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:51)

```java
public boolean isPickupEnabled()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return pickupEnabled;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isInPersonEnabled — linha 52

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:52)

```java
public boolean isInPersonEnabled()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return inPersonEnabled;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### configureFulfillment — linha 53

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:53)

```java
public void configureFulfillment(Boolean delivery,Boolean pickup,Boolean inPerson)
```

Executa a etapa `configure Fulfillment` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalArgumentException("Escolha ao menos uma modalidade de atendimento.");`

**Decisões condicionais examinadas:**
- `delivery==null`
- `pickup==null`
- `inPerson==null`
- `(!d&&!p&&!i)`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireFulfillment — linha 58

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:58)

```java
public void requireFulfillment(br.com.fooddelivery.orders.FulfillmentType type)
```

Executa a etapa `require Fulfillment` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalArgumentException("Escolha entrega ou retirada.");`
- `throw new IllegalArgumentException("Esta loja não oferece entrega. Confira as modalidades disponíveis.");`
- `throw new IllegalArgumentException("Esta loja não oferece retirada no local.");`

**Decisões condicionais examinadas:**
- `(type==null)`
- `(type==br.com.fooddelivery.orders.FulfillmentType.DELIVERY&&!deliveryEnabled)`
- `(type==br.com.fooddelivery.orders.FulfillmentType.PICKUP&&!pickupEnabled)`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireInPerson — linha 63

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:63)

```java
public void requireInPerson()
```

Executa a etapa `require In Person` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalArgumentException("Esta loja não oferece agendamentos presenciais.");`

**Decisões condicionais examinadas:**
- `(!inPersonEnabled)`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCnpj — linha 64

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:64)

```java
public String getCnpj()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return cnpj;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCategory — linha 65

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:65)

```java
public String getCategory()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return category;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getTimezone — linha 66

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:66)

```java
public String getTimezone()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return timezone;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isOperatingHoursEnabled — linha 67

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:67)

```java
public boolean isOperatingHoursEnabled()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return operatingHoursEnabled;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getWeeklyHoursJson — linha 68

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:68)

```java
public String getWeeklyHoursJson()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return weeklyHoursJson;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getHoursExceptionsJson — linha 69

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:69)

```java
public String getHoursExceptionsJson()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return hoursExceptionsJson;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCoverImageUrl — linha 70

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:70)

```java
public String getCoverImageUrl()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return coverImageUrl;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isActive — linha 71

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:71)

```java
public boolean isActive()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return active;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isApproved — linha 72

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:72)

```java
public boolean isApproved()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return approved;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isAvailable — linha 73

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:73)

```java
public boolean isAvailable()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `owner.isActive`; `owner.getGrantedRoles`.

**Saída declarada:**
- `return active && approved && owner != null && owner.isActive() && owner.getGrantedRoles().contains(br.com.fooddelivery.identity.AccountRole.MERCHANT);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getMarketplaceFeeBps — linha 74

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:74)

```java
@JsonIgnore public int getMarketplaceFeeBps()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return marketplaceFeeBps;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getDeliveryFeeCents — linha 75

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:75)

```java
public long getDeliveryFeeCents()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return deliveryFeeCents;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setDeliveryFeeCents — linha 76

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:76)

```java
public void setDeliveryFeeCents(long feeCents)
```

Executa a etapa `set Delivery Fee Cents` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalArgumentException("A taxa de entrega deve ficar entre R$ 0,00 e R$ 1.000,00.");`

**Decisões condicionais examinadas:**
- `(feeCents<0||feeCents>100000)`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setMarketplaceFeeBps — linha 77

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:77)

```java
public void setMarketplaceFeeBps(int feeBps)
```

Executa a etapa `set Marketplace Fee Bps` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalArgumentException("A comissão por loja deve ficar entre 0% e 50%.");`

**Decisões condicionais examinadas:**
- `(feeBps<0||feeBps>5000)`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setLocation — linha 78

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:78)

```java
public void setLocation(String city, String state)
```

Executa a etapa `set Location` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `state.toUpperCase`.

**Decisões condicionais examinadas:**
- `state==null`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setCoordinates — linha 79

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:79)

```java
public void setCoordinates(Double latitude,Double longitude)
```

Executa a etapa `set Coordinates` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Double.isFinite`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe latitude e longitude juntas ou deixe os dois campos vazios.");`
- `throw new IllegalArgumentException("As coordenadas informadas estão fora dos limites válidos.");`

**Decisões condicionais examinadas:**
- `((latitude==null)!=(longitude==null))`
- `(latitude!=null&&(!Double.isFinite(latitude)||latitude < -90||latitude > 90||!Double.isFinite(longitude)||longitude < -180||longitude > 180))`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setCnpjOnce — linha 80

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:80)

```java
public void setCnpjOnce(String value)
```

Executa a etapa `set Cnpj Once` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `br.com.fooddelivery.identity.Cnpj.normalize`; `br.com.fooddelivery.identity.Cnpj.isValid`; `cnpj.equals`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe um CNPJ válido.");`
- `throw new IllegalArgumentException("O CNPJ não pode ser alterado após o cadastro da loja.");`

**Decisões condicionais examinadas:**
- `(!br.com.fooddelivery.identity.Cnpj.isValid(normalized))`
- `(cnpj!=null&&!cnpj.equals(normalized))`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### correctCnpjByAdministrator — linha 81

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:81)

```java
public void correctCnpjByAdministrator(String value)
```

Executa a etapa `correct Cnpj By Administrator` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.isBlank`; `br.com.fooddelivery.identity.Cnpj.normalize`; `br.com.fooddelivery.identity.Cnpj.isValid`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe um CNPJ válido ou deixe vazio.");`

**Decisões condicionais examinadas:**
- `(value==null||value.isBlank())`
- `(!br.com.fooddelivery.identity.Cnpj.isValid(normalized))`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### update — linha 82

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:82)

```java
public void update(String name, String description, String address, String city, String state, String category, boolean active, long deliveryFeeCents)
```

Executa a etapa `update` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `setLocation`; `setDeliveryFeeCents`.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setApproved — linha 85

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:85)

```java
public void setApproved(boolean approved)
```

Executa a etapa `set Approved` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setActive — linha 86

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:86)

```java
public void setActive(boolean active)
```

Executa a etapa `set Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setTimezone — linha 87

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:87)

```java
public void setTimezone(String timezone)
```

Executa a etapa `set Timezone` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `timezone.isBlank`; `timezone.trim`; `java.time.ZoneId.of`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Escolha um fuso horário válido.");`

**Decisões condicionais examinadas:**
- `timezone==null||timezone.isBlank()`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setCoverImageUrl — linha 92

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:92)

```java
public void setCoverImageUrl(String coverImageUrl)
```

Executa a etapa `set Cover Image Url` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setOperatingHours — linha 93

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/Store.java:93)

```java
public void setOperatingHours(boolean enabled, String weeklyHoursJson, String hoursExceptionsJson)
```

Executa a etapa `set Operating Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `weeklyHoursJson.isBlank`; `hoursExceptionsJson.isBlank`.

**Decisões condicionais examinadas:**
- `weeklyHoursJson == null || weeklyHoursJson.isBlank()`
- `hoursExceptionsJson == null || hoursExceptionsJson.isBlank()`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreAssetController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java) · SHA-256 `b5801f5c5c8acf3efc797ad2088f58e868a50f0f4923c1792fd98e702420324a`

**Contrato de dados/estados:**
```java
public record CoverRequest(@NotBlank @Size(max=7_000_600) String dataUrl)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record ReviewRequest(@NotNull Boolean approved,@Size(max=500) String note)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CoverState(String publishedUrl,CoverView submission)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CoverView(UUID id,UUID storeId,String storeName,String ownerName,String status,String reviewNote,Instant createdAt,Instant reviewedAt,String imageUrl)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### StoreAssetController — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:33)

```java
public StoreAssetController(StoreRepository stores,StoreCoverSubmissionRepository submissions, AdminAuditService audit,NotificationService notifications, @Value("${app.media.store-assets-dir:./data/store-assets}") String directory)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `Path.of`.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### submit — linha 40

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:40)

```java
@PostMapping("/merchant/stores/{id}/cover") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public CoverView submit(@PathVariable UUID id,@Valid @RequestBody CoverRequest request,Authentication auth)
```

Executa a etapa `submit` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findByIdForUpdate`; `requireOwner`; `submissions.countByStoreIdAndCreatedAtAfter`; `Instant.now`; `sanitize`; `request.dataUrl`; `UUID.randomUUID`; `Files.createDirectories`; `Files.write`; `directory.resolve`; `TransactionSynchronizationManager.isSynchronizationActive`; `TransactionSynchronizationManager.registerSynchronization`; `Files.deleteIfExists`; `submissions.findByStoreIdAndStatus`; `view`; `submissions.save`.

**Rejeições locais:**
- `throw new org.springframework.web.server.ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,"Limite de 10 envios por hora para esta loja. Tente mais tarde.");`
- `throw new IllegalStateException("Não foi possível salvar a imagem. Tente novamente.");`

**Decisões condicionais examinadas:**
- `(submissions.countByStoreIdAndCreatedAtAfter(id,Instant.now().minusSeconds(3600))>=10)`
- `(TransactionSynchronizationManager.isSynchronizationActive())`
- `(status!=STATUS_COMMITTED)`

**Saída declarada:**
- `return view(submissions.save(new StoreCoverSubmission(store,name)));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
@Override public void afterCompletion(int status){if(status!=STATUS_COMMITTED)try{Files.deleteIfExists(directory.resolve(name));}catch(IOException ignored){}}
return view(submissions.save(new StoreCoverSubmission(store,name)));
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### afterCompletion — linha 51

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:51)

```java
@Override public void afterCompletion(int status)
```

Executa a etapa `after Completion` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Files.deleteIfExists`; `directory.resolve`.

**Decisões condicionais examinadas:**
- `(status!=STATUS_COMMITTED)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
@Override public void afterCompletion(int status){if(status!=STATUS_COMMITTED)try{Files.deleteIfExists(directory.resolve(name));}catch(IOException ignored){}}
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mine — linha 57

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:57)

```java
@GetMapping("/merchant/stores/{id}/cover-submission") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public CoverState mine(@PathVariable UUID id,Authentication auth)
```

Executa a etapa `mine` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findById`; `requireOwner`; `store.getCoverImageUrl`; `submissions.findFirstByStoreIdOrderByCreatedAtDesc`.

**Decisões condicionais examinadas:**
- `store.getCoverImageUrl()==null`

**Saída declarada:**
- `return new CoverState(store.getCoverImageUrl()==null?null:"/api/v1/merchant/stores/"+id+"/published-cover",submissions.findFirstByStoreIdOrderByCreatedAtDesc(id).map(this::view).orElse(null));`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### ownPublishedImage — linha 63

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:63)

```java
@GetMapping("/merchant/stores/{id}/published-cover") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public ResponseEntity<FileSystemResource> ownPublishedImage(@PathVariable UUID id,Authentication auth)
```

Executa a etapa `own Published Image` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findById`; `requireOwner`; `store.getCoverImageUrl`; `url.startsWith`; `file`; `url.substring`; `prefix.length`; `ResponseEntity.notFound`.

**Decisões condicionais examinadas:**
- `url!=null&&url.startsWith(prefix)`

**Saída declarada:**
- `return url!=null&&url.startsWith(prefix)?file(url.substring(prefix.length()),true):ResponseEntity.notFound().build();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### pending — linha 70

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:70)

```java
@GetMapping("/admin/store-covers") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public List<CoverView> pending()
```

Executa a etapa `pending` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `submissions.findByStatusOrderByCreatedAtAsc`.

**Saída declarada:**
- `return submissions.findByStatusOrderByCreatedAtAsc("PENDING").stream().map(this::view).toList();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### review — linha 73

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:73)

```java
@PatchMapping("/admin/store-covers/{id}/review") @PreAuthorize("hasRole('ADMIN')") @Transactional public CoverView review(@PathVariable UUID id,@Valid @RequestBody ReviewRequest request,Authentication auth)
```

Executa a etapa `review` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `submissions.findStoreId`; `stores.findByIdForUpdate`; `submissions.findByIdForUpdate`; `Files.isRegularFile`; `directory.resolve`; `submission.getFileName`; `submission.review`; `request.approved`; `request.note`; `subject`; `store.setCoverImageUrl`; `audit.record`; `notifications.sendLinked`; `store.getOwner`; `store.getName`; `submission.getReviewNote`; `view`.

**Rejeições locais:**
- `throw new IllegalStateException("Arquivo indisponível. Solicite um novo envio ao vendedor.");`

**Decisões condicionais examinadas:**
- `(!Files.isRegularFile(directory.resolve(submission.getFileName())))`
- `(request.approved())`
- `request.approved()`

**Saída declarada:**
- `return view(submission);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
if(request.approved())store.setCoverImageUrl("/api/v1/store-assets/"+submission.getFileName());
notifications.sendLinked(store.getOwner(),request.approved()?"Capa aprovada":"Revise a capa da sua loja",
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### privateImage — linha 88

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:88)

```java
@GetMapping("/store-cover-submissions/{id}/image") @Transactional(readOnly=true) public ResponseEntity<FileSystemResource> privateImage(@PathVariable UUID id,Authentication auth)
```

Executa a etapa `private Image` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `submissions.findById`; `auth.getAuthorities`; `a.getAuthority`; `requireOwner`; `submission.getStore`; `file`; `submission.getFileName`.

**Decisões condicionais examinadas:**
- `(!auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN")))`

**Saída declarada:**
- `return file(submission.getFileName(),true);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### readCover — linha 95

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:95)

```java
@GetMapping("/store-assets/{fileName}") @Transactional(readOnly=true) public ResponseEntity<FileSystemResource> readCover(@PathVariable String fileName)
```

Executa a etapa `read Cover` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `validName`; `stores.findByCoverImageUrl`; `ResponseEntity.notFound`; `file`.

**Decisões condicionais examinadas:**
- `(!validName(fileName)||stores.findByCoverImageUrl("/api/v1/store-assets/"+fileName).filter(Store::isAvailable).isEmpty())`

**Saída declarada:**
- `return ResponseEntity.notFound().build();`
- `return file(fileName,false);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### file — linha 101

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:101)

```java
private ResponseEntity<FileSystemResource> file(String name,boolean confidential)
```

Executa a etapa `file` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `validName`; `ResponseEntity.notFound`; `directory.resolve`; `path.startsWith`; `Files.isRegularFile`; `ResponseEntity.ok`; `CacheControl.noStore`; `CacheControl.noCache`.

**Decisões condicionais examinadas:**
- `(!validName(name))`
- `(!path.startsWith(directory)||!Files.isRegularFile(path))`
- `confidential`

**Saída declarada:**
- `return ResponseEntity.notFound().build();`
- `return ResponseEntity.notFound().build();`
- `return ResponseEntity.ok().cacheControl(confidential?CacheControl.noStore():CacheControl.noCache()) .header("X-Content-Type-Options","nosniff").contentType(MediaType.IMAGE_PNG).body(new FileSystemResource(path));`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### validName — linha 108

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:108)

```java
private static boolean validName(String name)
```

Executa a etapa `valid Name` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `name.matches`.

**Saída declarada:**
- `return name.matches("cover-[0-9a-fA-F-]{36}\\.png");`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 109

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:109)

```java
private static UUID subject(Authentication auth)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `auth.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt)auth.getPrincipal()).getSubject());`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireOwner — linha 110

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:110)

```java
private static void requireOwner(Store store,Authentication auth)
```

Executa a etapa `require Owner` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `store.getOwner`; `subject`.

**Rejeições locais:**
- `throw new AccessDeniedException("Loja não pertence à sua conta.");`

**Decisões condicionais examinadas:**
- `(!store.getOwner().getId().equals(subject(auth)))`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### view — linha 111

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:111)

```java
private CoverView view(StoreCoverSubmission submission)
```

Executa a etapa `view` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `submission.getStore`; `submission.getId`; `s.getId`; `s.getName`; `s.getOwner`; `submission.getStatus`; `submission.getReviewNote`; `submission.getCreatedAt`; `submission.getReviewedAt`.

**Saída declarada:**
- `return new CoverView(submission.getId(),s.getId(),s.getName(),s.getOwner().getName(),submission.getStatus(),submission.getReviewNote(),submission.getCreatedAt(),submission.getReviewedAt(),"/api/v1/store-cover-submissions/"+submission.getId()+"/image");`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sanitize — linha 113

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:113)

```java
static byte[] sanitize(String dataUrl)
```

Executa a etapa `sanitize` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `dataUrl.startsWith`; `dataUrl.length`; `prefix.length`; `Base64.getDecoder`; `dataUrl.substring`; `ImageIO.createImageInputStream`; `ImageIO.getImageReaders`; `readers.hasNext`; `readers.next`; `reader.setInput`; `reader.getWidth`; `reader.getHeight`; `reader.read`; `ImageIO.write`; `clean.size`; `clean.toByteArray`; `reader.dispose`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Envie uma imagem PNG de até 5 MB.");`
- `throw new IllegalArgumentException("Arquivo de imagem inválido.");`
- `throw new IllegalArgumentException("Arquivo PNG inválido.");`
- `throw new IllegalArgumentException("A imagem não pôde ser lida.");`
- `throw new IllegalArgumentException("A capa precisa ter 1.200 × 600 pixels, proporção 2:1. Ajuste a prévia antes de enviar.");`
- `throw new IllegalArgumentException("A capa ultrapassa 5 MB após processamento.");`
- `throw new IllegalArgumentException("Arquivo de imagem inválido.");`

**Decisões condicionais examinadas:**
- `(dataUrl==null||!dataUrl.startsWith(prefix)||dataUrl.length()>((MAX_IMAGE_BYTES+2L)/3)*4+prefix.length())`
- `(bytes.length<8||bytes.length>MAX_IMAGE_BYTES||(bytes[0]&255)!=137||bytes[1]!=80||bytes[2]!=78||bytes[3]!=71)`
- `(!readers.hasNext())`
- `(reader.getWidth(0)!=WIDTH||reader.getHeight(0)!=HEIGHT)`
- `(!ImageIO.write(decoded,"png",clean)||clean.size()>MAX_IMAGE_BYTES)`

**Saída declarada:**
- `return clean.toByteArray();`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
try{reader.setInput(input,true,true);if(reader.getWidth(0)!=WIDTH||reader.getHeight(0)!=HEIGHT)throw new IllegalArgumentException("A capa precisa ter 1.200 × 600 pixels, proporção 2:1. Ajuste a prévia antes de enviar.");
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategories.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreCategories.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategories.java) · SHA-256 `4591853c1e76b3009ec9723d37ed8a28372092e4fca83e593e23be1607494421`

**Contrato de dados/estados:**
```java
public record Category(String name, String emoji)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### StoreCategories — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategories.java:6)

```java
private StoreCategories()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isSupported — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategories.java:21)

```java
public static boolean isSupported(String value)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `ALL.stream`; `category.name`; `value.trim`; `List.of`; `category.equalsIgnoreCase`.

**Decisões condicionais examinadas:**
- `(value == null)`

**Saída declarada:**
- `return false;`
- `return ALL.stream().anyMatch(category -> category.name().equalsIgnoreCase(value.trim())) || List.of("Restaurante", "Pizzaria", "Cafeteria", "Barbearia") .stream().anyMatch(category -> category.equalsIgnoreCase(value.trim()));`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategory.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreCategory.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategory.java) · SHA-256 `beeb822bcff2d0b1083b51db2b3d375c80ca1d9206e54bfb2e5084514effc2e4`

### StoreCategory — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategory.java:34)

```java
protected StoreCategory()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### StoreCategory — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategory.java:36)

```java
public StoreCategory(String name, String emoji)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `name.trim`; `slugify`; `emoji.trim`.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### slugify — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategory.java:42)

```java
private static String slugify(String value)
```

Executa a etapa `slugify` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Normalizer.normalize`; `value.trim`; `normalized.replaceAll`; `slug.isBlank`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe um nome de categoria com letras ou números.");`

**Decisões condicionais examinadas:**
- `(slug.isBlank())`

**Saída declarada:**
- `return slug;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 50

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategory.java:50)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getSlug — linha 51

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategory.java:51)

```java
public String getSlug()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return slug;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getName — linha 52

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategory.java:52)

```java
public String getName()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return name;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getEmoji — linha 53

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategory.java:53)

```java
public String getEmoji()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return emoji;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 54

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategory.java:54)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategoryRepository.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreCategoryRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategoryRepository.java) · SHA-256 `f429404ae6b2eb500fe19d8aae61e30df4037ab80e164be74f777bba943283c6`

### findAllByOrderByNameAsc — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategoryRepository.java:9)

```java
List<StoreCategory> findAllByOrderByNameAsc();
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findBySlugIgnoreCase — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategoryRepository.java:10)

```java
Optional<StoreCategory> findBySlugIgnoreCase(String slug);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByNameIgnoreCase — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCategoryRepository.java:11)

```java
Optional<StoreCategory> findByNameIgnoreCase(String name);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreCoverSubmission.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java) · SHA-256 `713d71e3c1d11f29c38781ac7849ca045641ac8854ede2e9453d2994cc77db15`

### StoreCoverSubmission — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:18)

```java
protected StoreCoverSubmission()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### StoreCoverSubmission — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:19)

```java
public StoreCoverSubmission(Store store,String fileName)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### supersede — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:20)

```java
public void supersede()
```

Executa a etapa `supersede` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requirePending`.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### review — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:21)

```java
public void review(boolean approved,String note,UUID reviewer)
```

Executa a etapa `review` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requirePending`; `note.isBlank`; `note.trim`; `Instant.now`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe o motivo para o vendedor corrigir a imagem.");`

**Decisões condicionais examinadas:**
- `(!approved&&(note==null||note.isBlank()))`
- `approved`
- `note==null||note.isBlank()`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requirePending — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:26)

```java
private void requirePending()
```

Executa a etapa `require Pending` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"PENDING".equals`.

**Rejeições locais:**
- `throw new IllegalStateException("Esta imagem não está mais aguardando aprovação. Atualize a lista.");`

**Decisões condicionais examinadas:**
- `(!"PENDING".equals(status))`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:27)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStore — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:28)

```java
public Store getStore()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return store;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getFileName — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:29)

```java
public String getFileName()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return fileName;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStatus — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:30)

```java
public String getStatus()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return status;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getReviewNote — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:31)

```java
public String getReviewNote()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return reviewNote;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:32)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getReviewedAt — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmission.java:33)

```java
public Instant getReviewedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return reviewedAt;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmissionRepository.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreCoverSubmissionRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmissionRepository.java) · SHA-256 `73d0c78e15444257defbcdf1d229a7a409bfc25259cb4eb40586b7dc87bbbdc1`

### findByStoreIdAndStatus — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmissionRepository.java:7)

```java
List<StoreCoverSubmission> findByStoreIdAndStatus(UUID storeId,String status);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findFirstByStoreIdOrderByCreatedAtDesc — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmissionRepository.java:8)

```java
Optional<StoreCoverSubmission> findFirstByStoreIdOrderByCreatedAtDesc(UUID storeId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStatusOrderByCreatedAtAsc — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmissionRepository.java:9)

```java
List<StoreCoverSubmission> findByStatusOrderByCreatedAtAsc(String status);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### countByStoreIdAndCreatedAtAfter — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmissionRepository.java:10)

```java
long countByStoreIdAndCreatedAtAfter(UUID storeId,java.time.Instant since);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findStoreId — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmissionRepository.java:11)

```java
@Query("select submission.store.id from StoreCoverSubmission submission where submission.id=:id") Optional<UUID> findStoreId(@Param("id") UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByIdForUpdate — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreCoverSubmissionRepository.java:13)

```java
@Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select submission from StoreCoverSubmission submission where submission.id=:id") Optional<StoreCoverSubmission> findByIdForUpdate(@Param("id") UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreHoursChangeService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java) · SHA-256 `604dafee49b40d6b5c2c45c90e5f0b3c2b52418349c544c2ec2233921457b9a0`

**Contrato de dados/estados:**
```java
public record ChangeRequest(boolean enabled,Map<DayOfWeek,StoreOperatingHours.Interval> weekly,List<StoreOperatingHours.ExceptionHours> exceptions,Map<DayOfWeek,List<StoreOperatingHours.Interval>> weeklyShifts,String conflictAction,List<UUID> expectedAffectedIds,String revision)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record Affected(UUID id,String serviceName,String customerName,Instant startsAt,Instant endsAt,ReservationStatus status,boolean payAfterService)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record Preview(StoreOperatingHours.Configuration configuration,String revision,List<Affected> affected)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record Summary(int cancelled,int kept,int notified)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record Saved(@JsonUnwrapped StoreOperatingHours.Configuration configuration,Summary changeSummary)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### StoreHoursChangeService — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:20)

```java
public StoreHoursChangeService(StoreRepository stores,StoreOperatingHours hours,ReservationRepository reservations,ReservationSlotRepository slots,PaymentRepository payments,NotificationService notifications,AdminAuditService audit)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### configuration — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:22)

```java
public StoreOperatingHours.Configuration configuration()
```

Executa a etapa `configuration` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return new StoreOperatingHours.Configuration(enabled,weekly,exceptions,weeklyShifts);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### owned — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:28)

```java
private Store owned(UUID storeId,UUID ownerId,boolean administrator)
```

Executa a etapa `owned` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findByIdForUpdate`; `s.getOwner`.

**Rejeições locais:**
- `throw new AccessDeniedException("Loja não pertence à sua conta.");`

**Decisões condicionais examinadas:**
- `(!administrator&&!s.getOwner().getId().equals(ownerId))`

**Saída declarada:**
- `return s;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### future — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:29)

```java
private List<Reservation> future(Store store)
```

Executa a etapa `future` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservations.lockFutureForHours`; `store.getId`; `List.of`; `Instant.now`.

**Saída declarada:**
- `return reservations.lockFutureForHours(store.getId(),List.of(ReservationStatus.PENDING_PAYMENT,ReservationStatus.CONFIRMED,ReservationStatus.IN_SERVICE),Instant.now());`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireSafeTimezoneChange — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:31)

```java
public void requireSafeTimezoneChange(Store store,String requested)
```

Executa a etapa `require Safe Timezone Change` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requested.isBlank`; `ZoneId.of`; `requested.trim`; `zone.equals`; `store.getTimezone`; `future`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Escolha um fuso horário válido.");`
- `throw new IllegalStateException("Há reservas futuras ou em atendimento. Resolva esses compromissos antes de mudar o fuso da loja; os horários combinados não podem ser alterados silenciosamente.");`

**Decisões condicionais examinadas:**
- `(requested==null||requested.isBlank())`
- `(!zone.equals(ZoneId.of(store.getTimezone()))&&!future(store).isEmpty())`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### shortName — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:36)

```java
private static String shortName(String value)
```

Executa a etapa `short Name` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.codePointCount`; `value.length`; `value.substring`; `value.offsetByCodePoints`.

**Decisões condicionais examinadas:**
- `value.codePointCount(0,value.length())<=30`

**Saída declarada:**
- `return value.codePointCount(0,value.length())<=30?value:value.substring(0,value.offsetByCodePoints(0,29))+"…";`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### revision — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:37)

```java
private static String revision(Store store)
```

Executa a etapa `revision` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `store.isOperatingHoursEnabled`; `store.getTimezone`; `store.getWeeklyHoursJson`; `store.getHoursExceptionsJson`; `java.util.HexFormat.of`; `java.security.MessageDigest.getInstance`; `raw.getBytes`.

**Rejeições locais:**
- `throw new IllegalStateException(impossible);`

**Saída declarada:**
- `return java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(raw.getBytes(java.nio.charset.StandardCharsets.UTF_8)));`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### affected — linha 41

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:41)

```java
private List<Reservation> affected(Store s,StoreOperatingHours.Configuration config,List<Reservation> future)
```

Executa a etapa `affected` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ZoneId.of`; `s.getTimezone`; `future.stream`; `r.isOperatingHoursOverride`; `hours.fits`; `r.getStartsAt`; `r.getEndsAt`.

**Saída declarada:**
- `return future.stream().filter(r->!r.isOperatingHoursOverride()&&!hours.fits(config,zone,r.getStartsAt(),r.getEndsAt())).toList();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### preview — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:42)

```java
@Transactional public Preview preview(UUID storeId,UUID ownerId,ChangeRequest request)
```

Executa a etapa `preview` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `preview`.

**Saída declarada:**
- `return preview(storeId,ownerId,request,false);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### preview — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:43)

```java
@Transactional public Preview preview(UUID storeId,UUID ownerId,ChangeRequest request,boolean administrator)
```

Executa a etapa `preview` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `owned`; `hours.validate`; `request.configuration`; `revision`; `affected`; `future`; `r.getId`; `r.getServiceName`; `r.getCustomer`; `r.getStartsAt`; `r.getEndsAt`; `r.getStatus`; `r.isPayAfterService`.

**Saída declarada:**
- `return new Preview(config,revision(s),affected(s,config,future(s)).stream().map(r->new Affected(r.getId(),r.getServiceName(),r.getCustomer().getName(),r.getStartsAt(),r.getEndsAt(),r.getStatus(),r.isPayAfterService())).toList());`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### save — linha 44

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:44)

```java
@Transactional public Saved save(UUID storeId,UUID ownerId,ChangeRequest request)
```

Executa a etapa `save` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `save`.

**Saída declarada:**
- `return save(storeId,ownerId,request,false);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### save — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreHoursChangeService.java:45)

```java
@Transactional public Saved save(UUID storeId,UUID ownerId,ChangeRequest request,boolean administrator)
```

Executa a etapa `save` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `owned`; `hours.validate`; `request.configuration`; `future`; `affected`; `request.revision`; `revision`; `affected.isEmpty`; `List.of`; `request.conflictAction`; `request.expectedAffectedIds`; `affected.stream`; `expected.equals`; `"CANCEL".equals`; `r.getStatus`; `payments.findByReservationId`; `r.getId`; `r.cancelForHoursChange`; `slots.deleteByReservationId`; `payment.getStatus`; `payment.requestFullRefund`; `payment.cancelPendingCheckout`; `r.keepAfterHoursChange`; `DateTimeFormatter.ofPattern`; `ZoneId.of`; `s.getTimezone`; `r.getStartsAt`; `notifications.sendLinked`; `r.getCustomer`; `shortName`; `s.getName`; `r.getServiceName`; `r.getOperatingHoursNotice`; `r.isOperatingHoursOverride`; `hours.fits`; `r.getEndsAt`; `r.clearHoursOverride`; `hours.save`; `s.getOwner`; `s.getId`; `audit.record`; `affected.size`.

**Rejeições locais:**
- `throw new IllegalStateException("O expediente mudou em outra sessão. Atualize a prévia antes de salvar.");`
- `throw new IllegalStateException("Há reservas afetadas. Confira a prévia e escolha como tratar os clientes antes de salvar.");`
- `throw new IllegalStateException("A agenda mudou desde a prévia. Confira novamente as reservas antes de confirmar.");`
- `throw new IllegalStateException("A agenda mudou desde a prévia. Atualize antes de confirmar.");`

**Decisões condicionais examinadas:**
- `(request.revision()!=null&&!request.revision().equals(revision(s)))`
- `(!affected.isEmpty())`
- `(!List.of("KEEP","CANCEL").contains(request.conflictAction()==null?"":request.conflictAction())||request.expectedAffectedIds()==null||request.revision()==null)`
- `request.conflictAction()==null`
- `(!expected.equals(actual))`
- `(request.expectedAffectedIds()!=null&&!request.expectedAffectedIds().isEmpty())`
- `(cancel)`
- `(payment!=null)`
- `(payment.getStatus()==PaymentStatus.PAID)`
- `(payment.getStatus()==PaymentStatus.PENDING||payment.getStatus()==PaymentStatus.FAILED)`
- `cancel`
- `(r.isOperatingHoursOverride()&&hours.fits(config,ZoneId.of(s.getTimezone()),r.getStartsAt(),r.getEndsAt()))`

**Saída declarada:**
- `return new Saved(config,new Summary(cancelled,kept,affected.size()));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
if(cancel){r.cancelForHoursChange();slots.deleteByReservationId(r.getId());if(payment!=null){if(payment.getStatus()==PaymentStatus.PAID)payment.requestFullRefund();else if(payment.getStatus()==PaymentStatus.PENDING||payment.getStatus()==PaymentStatus.FAILED)payment.cancelPendingCheckout();}cancelled++;}
notifications.sendLinked(r.getCustomer(),cancel?"Reserva cancelada por mudança no expediente":"Seu horário foi mantido",shortName(s.getName())+" · "+shortName(r.getServiceName())+" · "+when+". "+r.getOperatingHoursNotice(),cancel?"warn":"info",NotificationTarget.RESERVATION,r.getId());
hours.save(s,config);
if(!affected.isEmpty())notifications.sendLinked(s.getOwner(),"Expediente atualizado",cancelled+" reserva(s) cancelada(s), "+kept+" mantida(s). Os clientes afetados receberam aviso na plataforma.","info",NotificationTarget.STORE,s.getId());
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreOperatingHours.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java) · SHA-256 `9f1870413a947629b2ef6262337c05620032b8dd4983a5d47c95a42607a296ae`

**Contrato de dados/estados:**
```java
public record Interval(String opensAt,String closesAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record ExceptionHours(LocalDate date,boolean closed,String opensAt,String closesAt,String note,List<Interval> shifts)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record Configuration(boolean enabled,Map<DayOfWeek,Interval> weekly,List<ExceptionHours> exceptions,Map<DayOfWeek,List<Interval>> weeklyShifts)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### StoreOperatingHours — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:13)

```java
public StoreOperatingHours(ObjectMapper mapper)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### ExceptionHours — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:16)

```java
public ExceptionHours(LocalDate date,boolean closed,String opensAt,String closesAt,String note)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### Configuration — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:19)

```java
public Configuration(boolean enabled,Map<DayOfWeek,Interval> weekly,List<ExceptionHours> exceptions)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### read — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:21)

```java
public Configuration read(Store store)
```

Executa a etapa `read` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `mapper.readTree`; `store.getWeeklyHoursJson`; `node.fields`; `DayOfWeek.valueOf`; `entry.getKey`; `entry.getValue`; `values.add`; `mapper.convertValue`; `shifts.put`; `mapper.readValue`; `store.getHoursExceptionsJson`; `mapper.getTypeFactory`; `validate`; `store.isOperatingHoursEnabled`; `Map.of`.

**Rejeições locais:**
- `throw new IllegalStateException("A configuração de horário desta loja está inválida.",ex);`

**Decisões condicionais examinadas:**
- `(entry.getValue().isArray())`

**Saída declarada:**
- `return validate(new Configuration(store.isOperatingHoursEnabled(),Map.of(),exceptions,shifts));`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### save — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:31)

```java
public Configuration save(Store store,Configuration input)
```

Executa a etapa `save` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `validate`; `store.setOperatingHours`; `config.enabled`; `mapper.writeValueAsString`; `config.weeklyShifts`; `config.exceptions`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Não foi possível salvar os horários da loja.",ex);`

**Saída declarada:**
- `return config;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
try{store.setOperatingHours(config.enabled(),mapper.writeValueAsString(config.weeklyShifts()),mapper.writeValueAsString(config.exceptions()));return config;}
```

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### validate — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:36)

```java
public Configuration validate(Configuration input)
```

Executa a etapa `validate` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `input.weeklyShifts`; `normalize`; `normalized.isEmpty`; `shifts.put`; `input.weekly`; `List.of`; `input.enabled`; `shifts.isEmpty`; `shifts.forEach`; `values.forEach`; `day.getValue`; `minute`; `value.opensAt`; `endMinute`; `week.add`; `rejectOverlap`; `input.exceptions`; `e.date`; `dates.add`; `e.note`; `e.closed`; `e.shifts`; `e.opensAt`; `e.closesAt`; `values.isEmpty`; `values.get`; `exceptions.add`; `first.opensAt`; `first.closesAt`; `exceptions.size`; `legacy.put`; `Collections.unmodifiableMap`; `exceptions.stream`; `Comparator.comparing`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe a configuração de funcionamento.");`
- `throw new IllegalArgumentException("Dia de funcionamento inválido.");`
- `throw new IllegalArgumentException("Revise os dias e horários.");`
- `throw new IllegalArgumentException("Defina ao menos um dia de funcionamento antes de ativar os horários.");`
- `throw new IllegalArgumentException("Cada exceção precisa ter uma data única e válida.");`
- `throw new IllegalArgumentException("A observação da exceção pode ter até 160 caracteres.");`
- `throw new IllegalArgumentException("Defina ao menos um turno para o horário especial.");`
- `throw new IllegalArgumentException("Cadastre no máximo 90 exceções de horário. Remova as datas antigas antes de adicionar novas.");`

**Decisões condicionais examinadas:**
- `(input==null)`
- `(input.weeklyShifts()!=null)`
- `(day==null)`
- `(!normalized.isEmpty())`
- `(input.weekly()!=null)`
- `(day==null||value==null)`
- `(input.enabled()&&shifts.isEmpty())`
- `(end>10080)`
- `(input.exceptions()!=null)`
- `(e==null||e.date()==null||!dates.add(e.date()))`
- `(e.note()!=null&&e.note().length()>160)`
- `e.closed()`
- `e.shifts()!=null`
- `(!e.closed()&&values.isEmpty())`
- `values.isEmpty()`
- `first==null`
- `e.note()==null`
- `(exceptions.size()>90)`

**Saída declarada:**
- `return new Configuration(input.enabled(),Collections.unmodifiableMap(legacy),exceptions.stream().sorted(Comparator.comparing(ExceptionHours::date)).toList(),Collections.unmodifiableMap(shifts));`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### normalize — linha 57

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:57)

```java
private static List<Interval> normalize(List<Interval> values)
```

Executa a etapa `normalize` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `values.size`; `minute`; `v.opensAt`; `v.closesAt`; `ranges.add`; `result.add`; `rejectOverlap`; `result.sort`; `Comparator.comparingInt`; `List.copyOf`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe os turnos do dia.");`
- `throw new IllegalArgumentException("Use no máximo seis turnos por dia.");`
- `throw new IllegalArgumentException("Turno inválido.");`
- `throw new IllegalArgumentException("Abertura e fechamento precisam ser diferentes. Para 24 horas, use a opção correspondente.");`

**Decisões condicionais examinadas:**
- `(values==null)`
- `(values.size()>6)`
- `(v==null)`
- `(start==close)`
- `close>start`

**Saída declarada:**
- `return List.copyOf(result);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### rejectOverlap — linha 64

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:64)

```java
private static void rejectOverlap(List<int[]> ranges)
```

Executa a etapa `reject Overlap` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ranges.sort`; `Comparator.comparingInt`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Há turnos sobrepostos. Confira também os horários que atravessam a meia-noite.");`

**Decisões condicionais examinadas:**
- `(range[0]<end)`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### minute — linha 65

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:65)

```java
private static int minute(String value,boolean closing)
```

Executa a etapa `minute` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"24:00".equals`; `value.matches`; `Integer.parseInt`; `value.substring`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe horários válidos no formato HH:mm.");`

**Decisões condicionais examinadas:**
- `(closing&&"24:00".equals(value))`
- `(value==null||!value.matches("(?:[01]\\d|2[0-3]):[0-5]\\d"))`

**Saída declarada:**
- `return 1440;`
- `return Integer.parseInt(value.substring(0,2))*60+Integer.parseInt(value.substring(3));`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### endMinute — linha 70

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:70)

```java
private static int endMinute(Interval value)
```

Executa a etapa `end Minute` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `minute`; `value.opensAt`; `value.closesAt`.

**Decisões condicionais examinadas:**
- `end>start`

**Saída declarada:**
- `return end>start?end:end+1440;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### exceptionFor — linha 71

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:71)

```java
private static ExceptionHours exceptionFor(Configuration config,LocalDate date)
```

Executa a etapa `exception For` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `config.exceptions`; `e.date`.

**Saída declarada:**
- `return config.exceptions().stream().filter(e->e.date().equals(date)).findFirst().orElse(null);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### anchored — linha 72

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:72)

```java
private static List<Interval> anchored(Configuration config,LocalDate date)
```

Executa a etapa `anchored` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `exceptionFor`; `e.shifts`; `config.weeklyShifts`; `date.getDayOfWeek`; `List.of`.

**Decisões condicionais examinadas:**
- `e!=null`

**Saída declarada:**
- `return e!=null?e.shifts():config.weeklyShifts().getOrDefault(date.getDayOfWeek(),List.of());`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### dayRanges — linha 73

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:73)

```java
private static List<int[]> dayRanges(Configuration config,LocalDate date)
```

Executa a etapa `day Ranges` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `exceptionFor`; `today.closed`; `List.of`; `config.enabled`; `anchored`; `ranges.add`; `minute`; `shift.opensAt`; `Math.min`; `endMinute`; `date.minusDays`; `ranges.sort`; `Comparator.comparingInt`.

**Decisões condicionais examinadas:**
- `(today!=null&&today.closed())`
- `(today==null&&!config.enabled())`
- `(today==null)`
- `(end>1440)`

**Saída declarada:**
- `return List.of();`
- `return List.of(new int[]{0,1440});`
- `return ranges;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### boundary — linha 81

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:81)

```java
private static Instant boundary(LocalDate date,int minute,ZoneId zone)
```

Executa a etapa `boundary` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `date.plusDays`; `date.atTime`.

**Decisões condicionais examinadas:**
- `minute==1440`

**Saída declarada:**
- `return minute==1440?date.plusDays(1).atStartOfDay(zone).toInstant():date.atTime(minute/60,minute%60).atZone(zone).toInstant();`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isOpenAt — linha 82

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:82)

```java
public boolean isOpenAt(Store store,Instant instant)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `isOpenAt`; `read`; `ZoneId.of`; `store.getTimezone`.

**Saída declarada:**
- `return isOpenAt(read(store),ZoneId.of(store.getTimezone()),instant);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isOpenAt — linha 83

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:83)

```java
public boolean isOpenAt(Configuration config,ZoneId zone,Instant instant)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `instant.atZone`; `dayRanges`; `instant.isBefore`; `boundary`.

**Saída declarada:**
- `return dayRanges(config,date).stream().anyMatch(r->!instant.isBefore(boundary(date,r[0],zone))&&instant.isBefore(boundary(date,r[1],zone)));`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### fits — linha 84

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:84)

```java
public boolean fits(Store store,Instant start,Instant end)
```

Executa a etapa `fits` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `fits`; `read`; `ZoneId.of`; `store.getTimezone`.

**Saída declarada:**
- `return fits(read(store),ZoneId.of(store.getTimezone()),start,end);`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### fits — linha 86

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:86)

```java
public boolean fits(Configuration config,ZoneId zone,Instant start,Instant end)
```

Executa a etapa `fits` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `end.isAfter`; `Duration.between`; `Duration.ofHours`; `start.atZone`; `end.minusNanos`; `date.isAfter`; `date.plusDays`; `dayRanges`; `windows.add`; `boundary`; `windows.sort`; `Comparator.comparing`; `window[0].isAfter`; `window[1].isAfter`; `cursor.isBefore`.

**Decisões condicionais examinadas:**
- `(start==null||end==null||!end.isAfter(start)||Duration.between(start,end).compareTo(Duration.ofHours(8))>0)`
- `(window[0].isAfter(cursor))`
- `(window[1].isAfter(cursor))`
- `(!cursor.isBefore(end))`

**Saída declarada:**
- `return false;`
- `return true;`
- `return false;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireOpenForInterval — linha 93

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:93)

```java
public void requireOpenForInterval(Store store,Instant start,Instant end)
```

Executa a etapa `require Open For Interval` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `fits`.

**Rejeições locais:**
- `throw new IllegalArgumentException("O serviço não cabe por inteiro no horário de funcionamento da loja.");`

**Decisões condicionais examinadas:**
- `(!fits(store,start,end))`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireOpenNow — linha 94

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:94)

```java
public void requireOpenNow(Store store)
```

Executa a etapa `require Open Now` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `isOpenAt`; `Instant.now`.

**Rejeições locais:**
- `throw new IllegalStateException("A loja está fechada neste horário e não pode receber novos pedidos.");`

**Decisões condicionais examinadas:**
- `(!isOpenAt(store,Instant.now()))`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireOpenAt — linha 95

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreOperatingHours.java:95)

```java
public void requireOpenAt(Store store,Instant instant)
```

Executa a etapa `require Open At` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `isOpenAt`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Este horário está fora do funcionamento da loja. Escolha um dia e horário em que ela esteja aberta.");`

**Decisões condicionais examinadas:**
- `(!isOpenAt(store,instant))`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessional.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreProfessional.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessional.java) · SHA-256 `c178518959145e18445b9dde5a4706de23f956a04474aa7546cd80e6f6521dd5`

### StoreProfessional — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessional.java:13)

```java
protected StoreProfessional()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### StoreProfessional — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessional.java:14)

```java
public StoreProfessional(Store store,String name)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `name.trim`.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessional.java:15)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStore — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessional.java:15)

```java
public Store getStore()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return store;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getName — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessional.java:15)

```java
public String getName()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return name;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isActive — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessional.java:15)

```java
public boolean isActive()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return active;`

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateName — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessional.java:16)

```java
public void updateName(String name)
```

Executa a etapa `update Name` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `name.trim`.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setActive — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessional.java:17)

```java
public void setActive(boolean active)
```

Executa a etapa `set Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessionalRepository.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreProfessionalRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessionalRepository.java) · SHA-256 `050ae4028fd0720c09a982a145bd6f2ca936868d0ea0457dba5c1f3304c58d69`

### findByStoreIdOrderByNameAsc — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessionalRepository.java:8)

```java
List<StoreProfessional> findByStoreIdOrderByNameAsc(UUID storeId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStoreIdAndActiveTrueOrderByNameAsc — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreProfessionalRepository.java:9)

```java
List<StoreProfessional> findByStoreIdAndActiveTrueOrderByNameAsc(UUID storeId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/catalog/StoreRepository.java

Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API.

Fonte: [StoreRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreRepository.java) · SHA-256 `48066d6530314c7dff0bd9898844484f28cc8456ced8741cbb9323b8dbeeb858`

### findByIdForUpdate — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreRepository.java:7)

```java
@org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE) @org.springframework.data.jpa.repository.Query("select store from Store store where store.id=:id") Optional<Store> findByIdForUpdate(@org.springframework.data.repository.query.Param("id") UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByActiveTrueAndApprovedTrue — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreRepository.java:10)

```java
List<Store> findByActiveTrueAndApprovedTrue();
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByOwnerId — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreRepository.java:11)

```java
List<Store> findByOwnerId(UUID ownerId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### countByCategoryIgnoreCase — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreRepository.java:12)

```java
long countByCategoryIgnoreCase(String category);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByCoverImageUrl — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreRepository.java:13)

```java
Optional<Store> findByCoverImageUrl(String coverImageUrl);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Catálogo, publicação e disponibilidade. A aprovação administrativa é independente de pagamento. CNPJ é opcional. Propriedade da loja e limites de entrada devem ser validados na API. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java

Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso.

Fonte: [OperationMessage.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java) · SHA-256 `c1ea16ebca9255a23a5bd6da5880c25805d504a598b0f0a2ae2d353a9e113e23`

### OperationMessage — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java:22)

```java
protected OperationMessage()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### OperationMessage — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java:23)

```java
private OperationMessage(Account sender, String body)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `body.trim`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
private OperationMessage(Account sender, String body) { this.sender = sender; this.body = body.trim(); }
```

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### forOrder — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java:24)

```java
public static OperationMessage forOrder(Order order, Account sender, String body)
```

Executa a etapa `for Order` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return m;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### forReservation — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java:25)

```java
public static OperationMessage forReservation(Reservation reservation, Account sender, String body)
```

Executa a etapa `for Reservation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return m;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### forDelivery — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java:26)

```java
public static OperationMessage forDelivery(DeliveryTask delivery, Account sender, String body)
```

Executa a etapa `for Delivery` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return m;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java:27)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getSender — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java:28)

```java
public Account getSender()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return sender;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getBody — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java:29)

```java
public String getBody()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return body;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessage.java:30)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java

Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso.

Fonte: [OperationMessageController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java) · SHA-256 `476871495dcd537c5924bb28bf68f9ab74ed421a7ef60e7975e68c3f07877e3e`

**Contrato de dados/estados:**
```java
public record SendMessage(@NotBlank @Size(max = 1000) String body)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record MessageView(UUID id, UUID senderId, String senderName, String body, Instant createdAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record ThreadView(List<MessageView> messages, boolean open, String closedReason)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### OperationMessageController — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:43)

```java
public OperationMessageController(OperationMessageRepository messages, OrderRepository orders, ReservationRepository reservations, DeliveryRepository deliveries, AccountRepository accounts, NotificationService notifications)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### orderMessages — linha 50

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:50)

```java
@GetMapping("/orders/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional(readOnly = true) public ThreadView orderMessages(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `order Messages` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.findById`; `subject`; `requireOrderParticipant`; `isUnpaid`; `order.getStatus`; `order.getCustomer`; `chronological`; `messages.findByOrder_IdOrderByCreatedAtDesc`; `newestFirst`; `isTerminal`; `terminalOrderMessage`.

**Rejeições locais:**
- `throw new AccessDeniedException(UNPAID_ORDER);`

**Decisões condicionais examinadas:**
- `(unpaid && !order.getCustomer().getId().equals(userId))`
- `open`
- `unpaid`

**Saída declarada:**
- `return new ThreadView(items, open, open ? null : unpaid ? UNPAID_ORDER : terminalOrderMessage(order.getStatus()));`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sendOrderMessage — linha 64

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:64)

```java
@PostMapping("/orders/{id}/messages") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional public ThreadView sendOrderMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth)
```

Executa a etapa `send Order Message` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.findById`; `subject`; `requireOrderParticipant`; `isUnpaid`; `order.getStatus`; `isTerminal`; `terminalOrderMessage`; `account`; `messages.save`; `OperationMessage.forOrder`; `request.body`; `order.getCustomer`; `order.getStore`; `notifications.sendUnreadOnceLinked`; `shortId`; `orderMessages`.

**Rejeições locais:**
- `throw new IllegalStateException(UNPAID_ORDER);`
- `throw new IllegalStateException(terminalOrderMessage(order.getStatus()));`

**Decisões condicionais examinadas:**
- `(isUnpaid(order.getStatus()))`
- `(isTerminal(order.getStatus()))`
- `order.getCustomer().getId().equals(senderId)`

**Saída declarada:**
- `return orderMessages(id, auth);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
messages.save(OperationMessage.forOrder(order, sender, request.body()));
notifications.sendUnreadOnceLinked(recipient, "Nova mensagem sobre o pedido " + shortId(id), "Abra o pedido para ler e responder.", "info", NotificationTarget.ORDER, id);
```

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### reservationMessages — linha 80

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:80)

```java
@GetMapping("/reservations/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional(readOnly = true) public ThreadView reservationMessages(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `reservation Messages` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservations.findById`; `subject`; `requireReservationParticipant`; `reservation.getStatus`; `reservation.getCustomer`; `chronological`; `messages.findByReservation_IdOrderByCreatedAtDesc`; `newestFirst`; `isTerminal`; `terminalReservationMessage`.

**Rejeições locais:**
- `throw new AccessDeniedException(UNPAID_RESERVATION);`

**Decisões condicionais examinadas:**
- `(unpaid && !reservation.getCustomer().getId().equals(userId))`
- `open`
- `unpaid`

**Saída declarada:**
- `return new ThreadView(items, open, open ? null : unpaid ? UNPAID_RESERVATION : terminalReservationMessage(reservation.getStatus()));`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sendReservationMessage — linha 94

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:94)

```java
@PostMapping("/reservations/{id}/messages") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional public ThreadView sendReservationMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth)
```

Executa a etapa `send Reservation Message` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservations.findById`; `subject`; `requireReservationParticipant`; `reservation.getStatus`; `isTerminal`; `terminalReservationMessage`; `account`; `messages.save`; `OperationMessage.forReservation`; `request.body`; `reservation.getCustomer`; `reservation.getStore`; `notifications.sendUnreadOnceLinked`; `shortId`; `reservationMessages`.

**Rejeições locais:**
- `throw new IllegalStateException(UNPAID_RESERVATION);`
- `throw new IllegalStateException(terminalReservationMessage(reservation.getStatus()));`

**Decisões condicionais examinadas:**
- `(reservation.getStatus() == ReservationStatus.PENDING_PAYMENT)`
- `(isTerminal(reservation.getStatus()))`
- `reservation.getCustomer().getId().equals(senderId)`

**Saída declarada:**
- `return reservationMessages(id, auth);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
messages.save(OperationMessage.forReservation(reservation, sender, request.body()));
notifications.sendUnreadOnceLinked(recipient, "Nova mensagem sobre a reserva " + shortId(id), "Abra a reserva para ler e responder.", "info", NotificationTarget.RESERVATION, id);
```

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deliveryMessages — linha 110

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:110)

```java
@GetMapping("/deliveries/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','COURIER')") @Transactional(readOnly = true) public ThreadView deliveryMessages(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `delivery Messages` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `deliveries.findById`; `subject`; `requireDeliveryParticipant`; `delivery.getOrder`; `delivery.getCourier`; `chronological`; `messages.findByDeliveryTask_IdOrderByCreatedAtDesc`; `newestFirst`; `delivery.getStatus`.

**Decisões condicionais examinadas:**
- `open`

**Saída declarada:**
- `return new ThreadView(items, open, open ? null : "O chat do entregador é encerrado quando o pedido é coletado ou a entrega termina.");`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sendDeliveryMessage — linha 121

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:121)

```java
@PostMapping("/deliveries/{id}/messages") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','COURIER')") @Transactional public ThreadView sendDeliveryMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth)
```

Executa a etapa `send Delivery Message` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `deliveries.findById`; `subject`; `requireDeliveryParticipant`; `delivery.getOrder`; `delivery.getCourier`; `delivery.getStatus`; `account`; `messages.save`; `OperationMessage.forDelivery`; `request.body`; `notifications.sendUnreadOnceLinked`; `shortId`; `deliveryMessages`.

**Rejeições locais:**
- `throw new IllegalStateException("O chat foi encerrado após a coleta do pedido.");`

**Decisões condicionais examinadas:**
- `(delivery.getStatus() != DeliveryStatus.ASSIGNED)`
- `delivery.getOrder().getCustomer().getId().equals(userId)`

**Saída declarada:**
- `return deliveryMessages(id, auth);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
messages.save(OperationMessage.forDelivery(delivery, sender, request.body()));
notifications.sendUnreadOnceLinked(recipient, "Nova mensagem do chat de entrega " + shortId(id), "Abra a entrega para ler e responder.", "info", NotificationTarget.DELIVERY, id);
```

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### newestFirst — linha 138

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:138)

```java
private PageRequest newestFirst()
```

Executa a etapa `newest First` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `PageRequest.of`; `Sort.by`.

**Saída declarada:**
- `return PageRequest.of(0, THREAD_LIMIT, Sort.by(Sort.Direction.DESC, "createdAt"));`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### chronological — linha 139

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:139)

```java
private List<MessageView> chronological(List<OperationMessage> newestFirst)
```

Executa a etapa `chronological` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `newestFirst.stream`; `java.util.Collections.reverse`.

**Saída declarada:**
- `return result;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### view — linha 140

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:140)

```java
private MessageView view(OperationMessage m)
```

Executa a etapa `view` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `m.getId`; `m.getSender`; `m.getBody`; `m.getCreatedAt`.

**Saída declarada:**
- `return new MessageView(m.getId(), m.getSender().getId(), m.getSender().getName(), m.getBody(), m.getCreatedAt());`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### account — linha 141

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:141)

```java
private Account account(UUID id)
```

Executa a etapa `account` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`.

**Saída declarada:**
- `return accounts.findById(id).orElseThrow();`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 142

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:142)

```java
private UUID subject(Authentication auth)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `auth.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject());`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireOrderParticipant — linha 143

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:143)

```java
private void requireOrderParticipant(Order o, UUID user)
```

Executa a etapa `require Order Participant` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `o.getCustomer`; `o.getStore`.

**Rejeições locais:**
- `throw new AccessDeniedException("Este pedido pertence a outra conta.");`

**Decisões condicionais examinadas:**
- `(!o.getCustomer().getId().equals(user) && !o.getStore().getOwner().getId().equals(user))`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireReservationParticipant — linha 144

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:144)

```java
private void requireReservationParticipant(Reservation r, UUID user)
```

Executa a etapa `require Reservation Participant` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `r.getCustomer`; `r.getStore`.

**Rejeições locais:**
- `throw new AccessDeniedException("Esta reserva pertence a outra conta.");`

**Decisões condicionais examinadas:**
- `(!r.getCustomer().getId().equals(user) && !r.getStore().getOwner().getId().equals(user))`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireDeliveryParticipant — linha 145

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:145)

```java
private void requireDeliveryParticipant(Order order, Account courier, UUID user)
```

Executa a etapa `require Delivery Participant` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `order.getCustomer`; `courier.getId`.

**Rejeições locais:**
- `throw new AccessDeniedException("Esta conversa não pertence a esta conta.");`

**Decisões condicionais examinadas:**
- `(!order.getCustomer().getId().equals(user) && (courier == null || !courier.getId().equals(user)))`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### shortId — linha 148

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:148)

```java
private static String shortId(UUID id)
```

Executa a etapa `short Id` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `id.toString`.

**Saída declarada:**
- `return id.toString().substring(0, 8).toUpperCase();`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isUnpaid — linha 149

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:149)

```java
private boolean isUnpaid(OrderStatus status)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return status == OrderStatus.AWAITING_PAYMENT;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isTerminal — linha 150

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:150)

```java
private boolean isTerminal(OrderStatus status)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return status == OrderStatus.COMPLETED || status == OrderStatus.REJECTED || status == OrderStatus.CANCELLED;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isTerminal — linha 151

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:151)

```java
private boolean isTerminal(ReservationStatus status)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return status == ReservationStatus.COMPLETED || status == ReservationStatus.CANCELLED || status == ReservationStatus.NO_SHOW;`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### terminalOrderMessage — linha 152

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:152)

```java
private String terminalOrderMessage(OrderStatus status)
```

Executa a etapa `terminal Order Message` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `status.name`.

**Saída declarada:**
- `return "A conversa com a loja foi encerrada porque o pedido está " + status.name().toLowerCase().replace('_', ' ') + ".";`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### terminalReservationMessage — linha 153

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:153)

```java
private String terminalReservationMessage(ReservationStatus status)
```

Executa a etapa `terminal Reservation Message` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `status.name`.

**Saída declarada:**
- `return "A conversa foi encerrada porque a reserva está " + status.name().toLowerCase().replace('_', ' ') + ".";`

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### ThreadView — linha 158

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:158)

```java
public ThreadView
```

Executa a etapa `Thread View` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `List.copyOf`.

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageRepository.java

Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso.

Fonte: [OperationMessageRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageRepository.java) · SHA-256 `c934cbf92e713080f355817915cab585c1cea66b5db803b004f18c0778a7159a`

### findByOrder_IdOrderByCreatedAtDesc — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageRepository.java:9)

```java
List<OperationMessage> findByOrder_IdOrderByCreatedAtDesc(UUID id, Pageable pageable);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByReservation_IdOrderByCreatedAtDesc — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageRepository.java:10)

```java
List<OperationMessage> findByReservation_IdOrderByCreatedAtDesc(UUID id, Pageable pageable);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByDeliveryTask_IdOrderByCreatedAtDesc — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageRepository.java:11)

```java
List<OperationMessage> findByDeliveryTask_IdOrderByCreatedAtDesc(UUID id, Pageable pageable);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Conversa operacional. Participantes autorizados são determinados pelo pedido/reserva; conhecer um identificador não concede acesso. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/config/AdminBootstrap.java

Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.

Fonte: [AdminBootstrap.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AdminBootstrap.java) · SHA-256 `0bb588c43792d796ea9ba5ff085c1b28cc745d64a1df817d40eb31e0a2d1f584`

### AdminBootstrap — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AdminBootstrap.java:15)

```java
public AdminBootstrap(AccountRepository accounts,PasswordEncoder encoder)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### run — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AdminBootstrap.java:16)

```java
@Override public void run(ApplicationArguments args)
```

Executa a etapa `run` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `email.isBlank`; `password.isBlank`; `password.length`; `accounts.existsByEmailIgnoreCase`; `email.trim`; `encoder.encode`; `admin.setRole`; `accounts.save`.

**Rejeições locais:**
- `throw new IllegalStateException("Configure APP_ADMIN_EMAIL e APP_ADMIN_PASSWORD (mínimo 16 caracteres) juntos.");`

**Decisões condicionais examinadas:**
- `(email.isBlank()&&password.isBlank())`
- `(email.isBlank()||password.length()<16)`
- `(!accounts.existsByEmailIgnoreCase(email))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
if(!accounts.existsByEmailIgnoreCase(email)){Account admin=new Account(email.trim().toLowerCase(),"Administrador",encoder.encode(password));admin.setRole(AccountRole.ADMIN);accounts.save(admin);}
```

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/config/AuthenticatedAccessManager.java

Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.

Fonte: [AuthenticatedAccessManager.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AuthenticatedAccessManager.java) · SHA-256 `41096f4f44c13cd8df99cb7ecbe208d71c7609ab221265b041654462b2328d48`

### check — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AuthenticatedAccessManager.java:12)

```java
@Override public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext context)
```

Executa a etapa `check` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `authentication.get`; `current.isAuthenticated`; `Boolean.TRUE.equals`; `jwt.getToken`.

**Decisões condicionais examinadas:**
- `(!(current instanceof JwtAuthenticationToken jwt) || !current.isAuthenticated())`

**Saída declarada:**
- `return new AuthorizationDecision(false);`
- `return new AuthorizationDecision(!forcedPasswordChange);`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/config/AuthenticationRateLimitFilter.java

Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.

Fonte: [AuthenticationRateLimitFilter.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AuthenticationRateLimitFilter.java) · SHA-256 `3b0768257ab4968dad7ccc119cf4bd1d821b43c8655e6ee2e8002ba2736b2484`

**Contrato de dados/estados:**
```java
private record Limit(String key, int maxAttempts, long windowMillis)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### doFilterInternal — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AuthenticationRateLimitFilter.java:19)

```java
@Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException
```

Executa a etapa `do Filter Internal` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `limitFor`; `request.getMethod`; `request.getRequestURI`; `chain.doFilter`; `request.getRemoteAddr`; `acquire`; `limit.key`; `limit.maxAttempts`; `limit.windowMillis`; `System.currentTimeMillis`; `response.setStatus`; `response.setHeader`; `Long.toString`; `response.setContentType`; `response.setCharacterEncoding`; `response.getWriter`.

**Decisões condicionais examinadas:**
- `(limit == null)`
- `(retryAfter > 0)`

**Saída declarada:**
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
response.setStatus(429);
response.setHeader("Retry-After", Long.toString(retryAfter));
response.setContentType("application/json");
response.setCharacterEncoding("UTF-8");
```

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### acquire — linha 41

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AuthenticationRateLimitFilter.java:41)

```java
private synchronized long acquire(String key, int maxAttempts, long windowMillis, long now)
```

Executa a etapa `acquire` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `windows.size`; `evictExpired`; `windows.get`; `windows.remove`; `windows.keySet`; `windows.put`; `Math.max`.

**Decisões condicionais examinadas:**
- `(requests % 256 == 0 || windows.size() >= MAX_BUCKETS)`
- `(current == null || now - current.startedAt >= windowMillis)`
- `(current == null && windows.size() >= MAX_BUCKETS)`
- `(current.count >= maxAttempts)`

**Saída declarada:**
- `return 0;`
- `return Math.max(1, (current.startedAt + windowMillis - now + 999) / 1000);`
- `return 0;`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### evictExpired — linha 55

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AuthenticationRateLimitFilter.java:55)

```java
private void evictExpired(long now)
```

Executa a etapa `evict Expired` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `windows.values`; `iterator.hasNext`; `iterator.next`; `iterator.remove`.

**Decisões condicionais examinadas:**
- `(now - iterator.next().startedAt >= 60 * 60 * 1000L)`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### limitFor — linha 60

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AuthenticationRateLimitFilter.java:60)

```java
private static Limit limitFor(String method, String path)
```

Executa a etapa `limit For` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"POST".equalsIgnoreCase`; `"/api/v1/auth/login".equals`; `"/api/v1/auth/password-recovery".equals`; `"/api/v1/auth/password-recovery/reset".equals`; `"/api/v1/auth/register".equals`; `"/api/v1/auth/operator-applications".equals`.

**Decisões condicionais examinadas:**
- `(!"POST".equalsIgnoreCase(method))`
- `("/api/v1/auth/login".equals(path))`
- `("/api/v1/auth/password-recovery".equals(path) || "/api/v1/auth/password-recovery/reset".equals(path))`
- `("/api/v1/auth/register".equals(path) || "/api/v1/auth/operator-applications".equals(path))`

**Saída declarada:**
- `return null;`
- `return new Limit("login", 10, 15 * 60 * 1000L);`
- `return new Limit("recovery", 5, 60 * 60 * 1000L);`
- `return new Limit("registration", 10, 60 * 60 * 1000L);`
- `return null;`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### Window — linha 74

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/AuthenticationRateLimitFilter.java:74)

```java
private Window(long startedAt, int count)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/config/CookieOrBearerTokenResolver.java

Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.

Fonte: [CookieOrBearerTokenResolver.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/CookieOrBearerTokenResolver.java) · SHA-256 `212c2ae6c8cb9b5c87b733c4b54cf5f59eb5ee78e638e0a50dce40f377a71f6a`

### CookieOrBearerTokenResolver — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/CookieOrBearerTokenResolver.java:13)

```java
public CookieOrBearerTokenResolver()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### CookieOrBearerTokenResolver — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/CookieOrBearerTokenResolver.java:14)

```java
public CookieOrBearerTokenResolver(String cookieName)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### resolve — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/CookieOrBearerTokenResolver.java:16)

```java
@Override public String resolve(HttpServletRequest request)
```

Executa a etapa `resolve` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `bearer.resolve`; `request.getRequestURI`; `path.equals`; `path.startsWith`; `"GET".equals`; `request.getMethod`; `request.getCookies`; `cookieName.equals`; `cookie.getName`; `cookie.getValue`.

**Decisões condicionais examinadas:**
- `(explicitBearer != null)`
- `(path.equals("/api/v1/auth/login")||path.equals("/api/v1/auth/register")||path.equals("/api/v1/auth/logout") ||path.startsWith("/api/v1/auth/password-recovery"))`
- `("GET".equals(request.getMethod()) && (path.equals("/api/v1/status")||path.equals("/api/v1/demo/status") ||path.equals("/api/v1/store-categories")||path.equals("/api/v1/store-locations") ||path.equals("/api/v1/stores")||path.startsWith("/api/v1/stores/") ||path.startsWith("/api/v1/geography/")||path.startsWith("/api/v1/store-assets/") ||path.equals("/actuator/health")))`
- `(cookies != null)`
- `(cookieName.equals(cookie.getName()) && !cookie.getValue().isBlank())`

**Saída declarada:**
- `return explicitBearer;`
- `return null;`
- `return null;`
- `return cookie.getValue();`
- `return null;`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/config/DemoBootstrap.java

Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.

Fonte: [DemoBootstrap.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoBootstrap.java) · SHA-256 `54a6b395f10f99f1101bba85ef639ea9d394a5a948d51461df1cfaf4b621bfcc`

### DemoBootstrap — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoBootstrap.java:21)

```java
public DemoBootstrap(AccountRepository accounts, StoreRepository stores, CatalogItemRepository items, ServiceCityRepository cities, StoreProfessionalRepository professionals, PasswordEncoder encoder)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### run — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoBootstrap.java:25)

```java
@Override @Transactional public void run(ApplicationArguments args)
```

Executa a etapa `run` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.count`; `create`; `cities.save`; `market.setLocation`; `market.setApproved`; `stores.save`; `items.save`; `barber.setLocation`; `barber.setApproved`; `professionals.save`.

**Decisões condicionais examinadas:**
- `(accounts.count()>0)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
cities.save(new ServiceCity("Nantes","SP","19645000"));
cities.save(new ServiceCity("Iepê","SP","19640000"));
market.setLocation("Nantes","SP");market.setApproved(true);stores.save(market);
items.save(new CatalogItem(market,"Produto de teste","Produto fictício",1500,50,false));
barber.setLocation("Nantes","SP");barber.setApproved(true);stores.save(barber);
items.save(new CatalogItem(barber,"Corte de teste","Serviço fictício",2500,0,true,false,30));
items.save(new CatalogItem(barber,"Serviço com pagamento posterior","Teste de regra por cidade",3000,0,true,true,30));
professionals.save(new StoreProfessional(barber,"Profissional A"));
professionals.save(new StoreProfessional(barber,"Profissional B"));
```

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### create — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoBootstrap.java:43)

```java
private Account create(String name, AccountRole role, String cpf)
```

Executa a etapa `create` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `encoder.encode`; `account.setRole`; `account.setCpfOnce`; `account.updateProfile`; `account.getName`; `accounts.save`.

**Decisões condicionais examinadas:**
- `(cpf!=null)`

**Saída declarada:**
- `return accounts.save(account);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
account.setRole(role);if(cpf!=null)account.setCpfOnce(cpf);
account.updateProfile(account.getName(),"11900000000","Rua de Demonstração, 1",null,"Centro","Nantes","SP","19645000");
return accounts.save(account);
```

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/config/DemoController.java

Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.

Fonte: [DemoController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoController.java) · SHA-256 `b0304c16c88a0175771408d6d194cb99698a3fbbc5b1a8cd8cbbba2b7459e3cb`

### DemoController — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoController.java:21)

```java
public DemoController(DemoEnvironment demo, PaymentRepository payments, PaymentService service, br.com.fooddelivery.identity.AccountRepository accounts)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### status — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoController.java:24)

```java
@GetMapping("/status") public Object status()
```

Executa a etapa `status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Map.of`; `accounts.count`.

**Saída declarada:**
- `return Map.of("mode","homologacao","ready",accounts.count()>0);`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### messages — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoController.java:25)

```java
@GetMapping("/messages") @PreAuthorize("hasRole('ADMIN')") public Object messages()
```

Executa a etapa `messages` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `demo.messages`.

**Saída declarada:**
- `return demo.messages();`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### payment — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoController.java:28)

```java
@PostMapping("/payments/{id}/{outcome}") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public Object payment(@PathVariable UUID id, @PathVariable String outcome, Authentication auth)
```

Executa a etapa `payment` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payments.findById`; `payment.getOrder`; `payment.getReservation`; `customer.getId`; ``; `auth.getPrincipal`; `outcome.equals`; `service.confirmForDevelopment`; `service.failProvider`; `Map.of`; `payment.getStatus`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Pagamento de outra conta.");`
- `throw new IllegalArgumentException("Resultado inválido.");`

**Decisões condicionais examinadas:**
- `payment.getOrder()!=null`
- `(!customer.getId().toString().equals(((Jwt)auth.getPrincipal()).getSubject()))`
- `(!outcome.equals("approve") && !outcome.equals("decline"))`
- `(outcome.equals("approve"))`

**Saída declarada:**
- `return Map.of("simulated",true,"status",payment.getStatus());`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/config/DemoEnvironment.java

Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.

Fonte: [DemoEnvironment.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoEnvironment.java) · SHA-256 `ec88dc8cbf930c9dcfc81a12d5e967319e90a6f2072d610146890c356f708f34`

**Contrato de dados/estados:**
```java
public record Message(String channel, String recipient, String code, Instant createdAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### DemoEnvironment — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoEnvironment.java:14)

```java
public DemoEnvironment(Environment environment)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `environment.matchesProfiles`.

**Rejeições locais:**
- `throw new IllegalStateException("Homologação não pode ser combinada com outros perfis de banco/produção.");`

**Decisões condicionais examinadas:**
- `(enabled && environment.matchesProfiles("prod", "postgres", "dev-h2"))`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### enabled — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoEnvironment.java:19)

```java
public boolean enabled()
```

Executa a etapa `enabled` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return enabled;`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### capture — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoEnvironment.java:20)

```java
public synchronized void capture(String channel, String recipient, String code)
```

Executa a etapa `capture` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `messages.addFirst`; `Instant.now`; `messages.size`; `messages.removeLast`.

**Rejeições locais:**
- `throw new IllegalStateException("Simulação desativada.");`

**Decisões condicionais examinadas:**
- `(!enabled)`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### messages — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoEnvironment.java:25)

```java
public synchronized List<Message> messages()
```

Executa a etapa `messages` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `List.copyOf`.

**Saída declarada:**
- `return List.copyOf(messages);`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/config/DemoExternalGuard.java

Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.

Fonte: [DemoExternalGuard.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoExternalGuard.java) · SHA-256 `7c263811288e2c945fd7956208b4058ced526f000ca6ed0e725ab9b6dc480832`

### doFilterInternal — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoExternalGuard.java:14)

```java
@Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain) throws ServletException,IOException
```

Executa a etapa `do Filter Internal` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `request.getRequestURI`; `path.startsWith`; `path.contains`; `path.endsWith`; `path.matches`; `response.setStatus`; `response.setCharacterEncoding`; `response.setContentType`; `response.getWriter`; `chain.doFilter`.

**Decisões condicionais examinadas:**
- `(path.startsWith("/api/v1/webhooks/") || path.contains("/mercadopago/callback") || path.endsWith("/mercadopago/connect") || path.matches("/api/v1/payments/[^/]+/(checkout|sync)"))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
response.setStatus(409);response.setCharacterEncoding("UTF-8");response.setContentType("application/json");
```

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java

Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.

Fonte: [SecurityConfig.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java) · SHA-256 `b5bf4170951d0b04c016ecc51577656d0271686b61536f001e790bfafc854f66`

### authenticatedAccessManager — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:43)

```java
@Bean AuthenticatedAccessManager authenticatedAccessManager()
```

Executa a etapa `authenticated Access Manager` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return new AuthenticatedAccessManager();`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### passwordEncoder — linha 46

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:46)

```java
@Bean PasswordEncoder passwordEncoder()
```

Executa a etapa `password Encoder` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return new BCryptPasswordEncoder();`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### userDetailsService — linha 49

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:49)

```java
@Bean UserDetailsService userDetailsService(AccountRepository accounts)
```

Executa a etapa `user Details Service` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByEmailIgnoreCase`; `account.isActive`; `User.withUsername`; `account.getEmail`; `account.getPasswordHash`; `account.getGrantedRoles`.

**Saída declarada:**
- `return email -> accounts.findByEmailIgnoreCase(email) .filter(account -> account.isActive()) .map(account -> User.withUsername(account.getEmail()) .password(account.getPasswordHash()) .roles(account.getGrantedRoles().stream().map(Enum::name).toArray(String[]::new)) .build()) .orElseThrow(() -> new UsernameNotFoundException("Conta não encontrada"));`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### authenticationProvider — linha 60

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:60)

```java
@Bean DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder)
```

Executa a etapa `authentication Provider` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `provider.setPasswordEncoder`.

**Saída declarada:**
- `return provider;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
provider.setPasswordEncoder(passwordEncoder);
```

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### authenticationManager — linha 67

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:67)

```java
@Bean AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
```

Executa a etapa `authentication Manager` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `configuration.getAuthenticationManager`.

**Saída declarada:**
- `return configuration.getAuthenticationManager();`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### jwtSecretKey — linha 72

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:72)

```java
@Bean SecretKey jwtSecretKey(@Value("${app.security.jwt-secret}") String encodedSecret)
```

Executa a etapa `jwt Secret Key` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Base64.getDecoder`.

**Rejeições locais:**
- `throw new IllegalStateException("A chave JWT deve ter ao menos 256 bits.");`

**Decisões condicionais examinadas:**
- `(key.length < 32)`

**Saída declarada:**
- `return new SecretKeySpec(key, "HmacSHA256");`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### jwtEncoder — linha 79

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:79)

```java
@Bean JwtEncoder jwtEncoder(SecretKey key)
```

Executa a etapa `jwt Encoder` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return new NimbusJwtEncoder(new ImmutableSecret<>(key));`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### jwtDecoder — linha 82

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:82)

```java
@Bean JwtDecoder jwtDecoder(SecretKey key, AccountRepository accounts)
```

Executa a etapa `jwt Decoder` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `NimbusJwtDecoder.withSecretKey`; `java.util.UUID.fromString`; `jwt.getSubject`; `accounts.findById`; `account.getGrantedRoles`; `jwt.getClaimAsStringList`; `Boolean.TRUE.equals`; `jwt.getClaim`; `account.isActive`; `granted.equals`; `account.isPasswordChangeRequired`; `tokenCredentialVersion.intValue`; `account.getCredentialVersion`; `org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.success`; `org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.failure`; `decoder.setJwtValidator`; `JwtValidators.createDefaultWithIssuer`.

**Decisões condicionais examinadas:**
- `active`

**Saída declarada:**
- `return account.isActive() && tokenRoles != null && granted.equals(tokenRoles) && account.isPasswordChangeRequired() == tokenRequiresPasswordChange && tokenCredentialVersion != null && tokenCredentialVersion.intValue() == account.getCredentialVersion();`
- `return active ? org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.success() : org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.failure( new OAuth2Error("invalid_token", "Conta inativa ou não encontrada.", null));`
- `return org.springframework.security.oauth2.core.OAuth2TokenValidatorResult.failure( new OAuth2Error("invalid_token", "Token de acesso inválido.", null));`
- `return decoder;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
decoder.setJwtValidator(new DelegatingOAuth2TokenValidator<>(
```

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### jwtAuthenticationConverter — linha 111

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:111)

```java
@Bean JwtAuthenticationConverter jwtAuthenticationConverter()
```

Executa a etapa `jwt Authentication Converter` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `authorities.setAuthoritiesClaimName`; `authorities.setAuthorityPrefix`; `converter.setJwtGrantedAuthoritiesConverter`.

**Saída declarada:**
- `return converter;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
authorities.setAuthoritiesClaimName("roles");
authorities.setAuthorityPrefix("ROLE_");
converter.setJwtGrantedAuthoritiesConverter(authorities);
```

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### corsConfigurationSource — linha 121

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:121)

```java
@Bean CorsConfigurationSource corsConfigurationSource(@Value("${app.web.allowed-origins}") String configuredOrigins)
```

Executa a etapa `cors Configuration Source` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `config.setAllowedOrigins`; `java.util.Arrays.stream`; `configuredOrigins.split`; `origin.isEmpty`; `config.setAllowedMethods`; `List.of`; `config.setAllowedHeaders`; `config.setAllowCredentials`; `source.registerCorsConfiguration`.

**Saída declarada:**
- `return source;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
config.setAllowedOrigins(java.util.Arrays.stream(configuredOrigins.split(","))
config.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-CSRF-TOKEN"));
config.setAllowCredentials(true);
```

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### securityFilterChain — linha 134

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SecurityConfig.java:134)

```java
@Bean SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider provider, JwtAuthenticationConverter jwtConverter, AuthenticatedAccessManager authenticatedAccessManager, @Value("${app.web.allowed-origins}") String configuredOrigins, @Value("${app.security.cookie-name:fooddelivery_session}") String cookieName) throws Exception
```

Executa a etapa `security Filter Chain` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `http .cors`; `cors.configurationSource`; `corsConfigurationSource`; `csrf.disable`; `headers .contentTypeOptions`; `org.springframework.security.config.Customizer.withDefaults`; `frame.deny`; `csp.policyDirectives`; `referrer.policy`; `policy.policy`; `auth .requestMatchers`; `session.sessionCreationPolicy`; `oauth .bearerTokenResolver`; `jwt.jwtAuthenticationConverter`.

**Saída declarada:**
- `return http .cors(cors -> cors.configurationSource(corsConfigurationSource(configuredOrigins))) .csrf(csrf -> csrf.disable()) .headers(headers -> headers .contentTypeOptions(org.springframework.security.config.Customizer.withDefaults()) .cacheControl(org.springframework.security.config.Customizer.withDefaults()) .frameOptions(frame -> frame.deny()) .contentSecurityPolicy(csp -> csp.policyDirectives( "default-src 'none'; frame-ancestors 'none'; base-uri 'none'; form-action 'none'")) .referrerPolicy(referrer -> referrer.policy( org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN)) .permissionsPolicy(policy -> policy.policy( "camera=(), microphone=(), geolocation=(self)"))) .authenticationProvider(provider) .addFilterBefore(new SessionOriginFilter(configuredOrigins,cookieName), UsernamePasswordAuthenticationFilter.class) .addFilterBefore(new AuthenticationRateLimitFilter(), UsernamePasswordAuthenticationFilter.class) .authorizeHttpRequests(auth -> auth .requestMatchers(HttpMethod.GET, "/api/v1/demo/status").permitAll() .requestMatchers(HttpMethod.POST, "/api/v1/auth/register", "/api/v1/auth/login", "/api/v1/auth/logout", "/api/v1/auth/operator-applications", "/api/v1/auth/password-recovery", "/api/v1/auth/password-recovery/reset").permitAll() .requestMatchers(HttpMethod.GET, "/api/v1/payments/mercadopago/callback").permitAll() .requestMatchers(HttpMethod.GET, "/api/v1/store-assets/**").permitAll() .requestMatchers(HttpMethod.GET, "/api/v1/store-categories").permitAll() .requestMatchers(HttpMethod.GET, "/api/v1/store-locations", "/api/v1/stores/**").permitAll() .requestMatchers(HttpMethod.GET, "/api/v1/geography/search", "/api/v1/geography/reverse").permitAll() .requestMatchers(HttpMethod.POST, "/api/v1/webhooks/mercadopago").permitAll() .requestMatchers(HttpMethod.GET, "/api/v1/status", "/actuator/health", "/actuator/health/**").permitAll() .requestMatchers(HttpMethod.POST, "/api/v1/account/password").authenticated() .anyRequest().access(authenticatedAccessManager)) .sessionManagement(session -> session.sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)) .oauth2ResourceServer(oauth -> oauth .bearerTokenResolver(new CookieOrBearerTokenResolver(cookieName)) .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter))) .build();`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/config/SessionOriginFilter.java

Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria.

Fonte: [SessionOriginFilter.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SessionOriginFilter.java) · SHA-256 `6cbf0a90270b88456cfc75e3312673c0beb4ddc0e4875456dee8b573a1ff15a6`

### SessionOriginFilter — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SessionOriginFilter.java:20)

```java
public SessionOriginFilter(String configuredOrigins)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### SessionOriginFilter — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SessionOriginFilter.java:23)

```java
public SessionOriginFilter(String configuredOrigins, String cookieName)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `Stream.of`; `configuredOrigins.split`; `value.isEmpty`; `Collectors.toUnmodifiableSet`.

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### doFilterInternal — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SessionOriginFilter.java:29)

```java
@Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException
```

Executa a etapa `do Filter Internal` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `hasSessionCookie`; `isMutation`; `request.getMethod`; `request.getHeader`; `allowedOrigins.contains`; `response.sendError`; `chain.doFilter`.

**Decisões condicionais examinadas:**
- `(hasSessionCookie(request) && isMutation(request.getMethod()))`
- `(origin == null || !allowedOrigins.contains(origin))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
response.sendError(HttpServletResponse.SC_FORBIDDEN, "Origem da sessão não autorizada.");
```

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### hasSessionCookie — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SessionOriginFilter.java:42)

```java
private boolean hasSessionCookie(HttpServletRequest request)
```

Executa a etapa `has Session Cookie` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `request.getCookies`; `cookieName.equals`; `cookie.getName`.

**Decisões condicionais examinadas:**
- `(cookies == null)`
- `(cookieName.equals(cookie.getName()))`

**Saída declarada:**
- `return false;`
- `return true;`
- `return false;`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isMutation — linha 51

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/SessionOriginFilter.java:51)

```java
private static boolean isMutation(String method)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `"POST".equals`; `"PUT".equals`; `"PATCH".equals`; `"DELETE".equals`.

**Saída declarada:**
- `return "POST".equals(method) || "PUT".equals(method) || "PATCH".equals(method) || "DELETE".equals(method);`

**Decisão de engenharia:** Configuração e fronteira HTTP. Autenticação, origem, cookies, limitação e isolamento de homologação são definidos aqui. Configuração de produção requer validação operacional própria. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java

Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação.

Fonte: [DeliveryController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java) · SHA-256 `9cc40445608d7e18523f0f42c48a9ed2abc704cb132df127db8a1b2e6f7e0100`

**Contrato de dados/estados:**
```java
public record Update(@NotNull DeliveryStatus status)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record LocationUpdate(@jakarta.validation.constraints.NotNull @jakarta.validation.constraints.DecimalMin("-90.0") @jakarta.validation.constraints.DecimalMax("90.0") Double latitude,@jakarta.validation.constraints.NotNull @jakarta.validation.constraints.DecimalMin("-180.0") @jakarta.validation.constraints.DecimalMax("180.0") Double longitude)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record OfferView(UUID deliveryId,UUID orderId,String storeName,long orderTotalCents)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record AssignedView(UUID deliveryId,DeliveryStatus status,UUID orderId,String storeName,String deliveryAddress,long orderTotalCents,String customerNote)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### DeliveryController — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:16)

```java
public DeliveryController(DeliveryRepository d,OrderRepository o,AccountRepository a)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### offer — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:17)

```java
@PostMapping("/merchant/orders/{orderId}/delivery") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") @org.springframework.transaction.annotation.Transactional public DeliveryTask offer(@PathVariable UUID orderId,Authentication a)
```

Executa a etapa `offer` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.findByIdForUpdate`; `o.getStore`; `subject`; `o.getFulfillmentType`; `o.getStatus`; `"REQUESTED".equals`; `o.getCancellationRequestStatus`; `deliveries.findByOrderId`; `deliveries.save`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Pedido de outra loja.");`
- `throw new IllegalStateException("Pedido precisa estar pronto e sem cancelamento pendente para delivery.");`

**Decisões condicionais examinadas:**
- `(!o.getStore().getOwner().getId().equals(subject(a)))`
- `(o.getFulfillmentType()!=FulfillmentType.DELIVERY||o.getStatus()!=OrderStatus.READY||"REQUESTED".equals(o.getCancellationRequestStatus()))`

**Saída declarada:**
- `return deliveries.findByOrderId(orderId).orElseGet(()->deliveries.save(new DeliveryTask(o)));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
public DeliveryTask offer(@PathVariable UUID orderId,Authentication a){Order o=orders.findByIdForUpdate(orderId).orElseThrow();if(!o.getStore().getOwner().getId().equals(subject(a)))throw new org.springframework.security.access.AccessDeniedException("Pedido de outra loja.");if(o.getFulfillmentType()!=FulfillmentType.DELIVERY||o.getStatus()!=OrderStatus.READY||"REQUESTED".equals(o.getCancellationRequestStatus()))throw new IllegalStateException("Pedido precisa estar pronto e sem cancelamento pendente para delivery.");return deliveries.findByOrderId(orderId).orElseGet(()->deliveries.save(new DeliveryTask(o)));}
```

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### offers — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:20)

```java
@GetMapping("/courier/offers") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional(readOnly=true) public List<OfferView> offers()
```

Executa a etapa `offers` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `deliveries.findByStatusOrderByCreatedAt`; `d.getOrder`; `"REQUESTED".equals`; `d.getId`.

**Saída declarada:**
- `return deliveries.findByStatusOrderByCreatedAt(DeliveryStatus.OFFERED).stream().filter(d->d.getOrder().getStatus()==OrderStatus.READY&&!"REQUESTED".equals(d.getOrder().getCancellationRequestStatus())).map(d->new OfferView(d.getId(),d.getOrder().getId(),d.getOrder().getStore().getName(),d.getOrder().getTotalCents())).toList();`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mine — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:21)

```java
@GetMapping("/courier/deliveries") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional(readOnly=true) public List<AssignedView> mine(Authentication a)
```

Executa a etapa `mine` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `deliveries.findByCourierIdOrderByCreatedAtDesc`; `subject`.

**Saída declarada:**
- `return deliveries.findByCourierIdOrderByCreatedAtDesc(subject(a)).stream().map(this::view).toList();`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### accept — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:22)

```java
@PostMapping("/courier/offers/{id}/accept") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public DeliveryTask accept(@PathVariable UUID id,Authentication a)
```

Executa a etapa `accept` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `deliveries.findById`; `orders.findByIdForUpdate`; `task.getOrder`; `requireReady`; `accounts.findById`; `subject`; `deliveries.claim`.

**Rejeições locais:**
- `throw new IllegalStateException("Oferta já aceita ou encerrada.");`

**Decisões condicionais examinadas:**
- `(deliveries.claim(id,courier)!=1)`

**Saída declarada:**
- `return deliveries.findById(id).orElseThrow();`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### progress — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:24)

```java
@PatchMapping("/courier/deliveries/{id}") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public AssignedView progress(@PathVariable UUID id,@Valid @RequestBody Update u,Authentication a)
```

Executa a etapa `progress` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `deliveries.findById`; `d.getCourier`; `subject`; `orders.findByIdForUpdate`; `d.getOrder`; `requireReady`; `d.advance`; `u.status`; `order.transition`; `view`; `deliveries.save`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Entrega não atribuída a esta conta.");`

**Decisões condicionais examinadas:**
- `(d.getCourier()==null||!d.getCourier().getId().equals(subject(a)))`
- `(u.status()==DeliveryStatus.DELIVERED)`

**Saída declarada:**
- `return view(deliveries.save(d));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
public AssignedView progress(@PathVariable UUID id,@Valid @RequestBody Update u,Authentication a){var d=deliveries.findById(id).orElseThrow();if(d.getCourier()==null||!d.getCourier().getId().equals(subject(a)))throw new org.springframework.security.access.AccessDeniedException("Entrega não atribuída a esta conta.");Order order=orders.findByIdForUpdate(d.getOrder().getId()).orElseThrow();requireReady(order);d.advance(u.status());if(u.status()==DeliveryStatus.DELIVERED)order.transition(OrderStatus.COMPLETED);return view(deliveries.save(d));}
```

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### location — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:26)

```java
@PatchMapping("/courier/deliveries/{id}/location") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public void location(@PathVariable UUID id,@Valid @RequestBody LocationUpdate u,Authentication a)
```

Executa a etapa `location` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `deliveries.findById`; `d.getCourier`; `subject`; `d.updateLocation`; `u.latitude`; `u.longitude`; `deliveries.save`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Entrega não atribuída a esta conta.");`

**Decisões condicionais examinadas:**
- `(d.getCourier()==null||!d.getCourier().getId().equals(subject(a)))`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
@PatchMapping("/courier/deliveries/{id}/location") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public void location(@PathVariable UUID id,@Valid @RequestBody LocationUpdate u,Authentication a){var d=deliveries.findById(id).orElseThrow();if(d.getCourier()==null||!d.getCourier().getId().equals(subject(a)))throw new org.springframework.security.access.AccessDeniedException("Entrega não atribuída a esta conta.");d.updateLocation(u.latitude(),u.longitude());deliveries.save(d);}
```

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:27)

```java
private UUID subject(Authentication a)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `a.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireReady — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:31)

```java
private void requireReady(Order order)
```

Executa a etapa `require Ready` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `order.getStatus`; `"REQUESTED".equals`; `order.getCancellationRequestStatus`.

**Rejeições locais:**
- `throw new IllegalStateException("O pedido não está disponível para entrega ou tem cancelamento pendente.");`

**Decisões condicionais examinadas:**
- `(order.getStatus()!=OrderStatus.READY||"REQUESTED".equals(order.getCancellationRequestStatus()))`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### view — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:32)

```java
private AssignedView view(DeliveryTask d)
```

Executa a etapa `view` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `d.getStatus`; `d.getOrder`; `d.getId`.

**Decisões condicionais examinadas:**
- `terminal`

**Saída declarada:**
- `return new AssignedView(d.getId(),d.getStatus(),d.getOrder().getId(),d.getOrder().getStore().getName(),terminal?null:d.getOrder().getDeliveryAddress(),d.getOrder().getTotalCents(),note);`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryRepository.java

Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação.

Fonte: [DeliveryRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryRepository.java) · SHA-256 `33bf7915783919645cf298ac220938dc1d8cfe3c8d78c2970079ec9cd3c571e9`

### findByStatusOrderByCreatedAt — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryRepository.java:7)

```java
List<DeliveryTask> findByStatusOrderByCreatedAt(DeliveryStatus status);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByCourierIdOrderByCreatedAtDesc — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryRepository.java:8)

```java
List<DeliveryTask> findByCourierIdOrderByCreatedAtDesc(UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByOrderId — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryRepository.java:9)

```java
Optional<DeliveryTask> findByOrderId(UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### claim — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryRepository.java:10)

```java
@Modifying(clearAutomatically=true,flushAutomatically=true) @Transactional @Query("update DeliveryTask d set d.courier=:courier, d.status=br.com.fooddelivery.delivery.DeliveryStatus.ASSIGNED, d.version=d.version+1 where d.id=:id and d.courier is null and d.status=br.com.fooddelivery.delivery.DeliveryStatus.OFFERED") int claim(@Param("id") UUID id,@Param("courier") br.com.fooddelivery.identity.Account courier);
```

Executa a etapa `claim` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryStatus.java

Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação.

Fonte: [DeliveryStatus.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryStatus.java) · SHA-256 `43d203b641d5d2c889b7fe8e9a893122ac49bdf0c6ff1c04fccf4599d5986c64`

**Contrato de dados/estados:**
```java
public enum DeliveryStatus
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

Sem função explícita: contrato declarativo, enum ou inicializador. Os campos e as anotações da fonte são o contrato deste arquivo.

## Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java

Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação.

Fonte: [DeliveryTask.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java) · SHA-256 `2f3cf664c43311504ae8169c30bbc14f7374fb757815efd2537f5516d504d2cc`

### DeliveryTask — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:21)

```java
protected DeliveryTask()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### DeliveryTask — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:21)

```java
public DeliveryTask(Order order)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:22)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getOrder — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:22)

```java
public Order getOrder()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return order;`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCourier — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:22)

```java
public Account getCourier()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return courier;`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStatus — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:22)

```java
public DeliveryStatus getStatus()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return status;`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:22)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### assign — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:23)

```java
public void assign(Account courier)
```

Executa a etapa `assign` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalStateException("Entrega já atribuída.");`

**Decisões condicionais examinadas:**
- `(status!=DeliveryStatus.OFFERED||this.courier!=null)`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireCancellable — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:24)

```java
public void requireCancellable()
```

Executa a etapa `require Cancellable` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalStateException("A entrega já foi coletada. Fale com o atendimento para resolver a ocorrência.");`

**Decisões condicionais examinadas:**
- `(status!=DeliveryStatus.OFFERED&&status!=DeliveryStatus.ASSIGNED&&status!=DeliveryStatus.CANCELLED)`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancel — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:25)

```java
public void cancel()
```

Executa a etapa `cancel` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requireCancellable`.

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### advance — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:26)

```java
public void advance(DeliveryStatus next)
```

Executa a etapa `advance` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalStateException("Transição de entrega inválida.");`

**Decisões condicionais examinadas:**
- `(!valid)`
- `(next==DeliveryStatus.DELIVERED)`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateLocation — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:27)

```java
public void updateLocation(double latitude,double longitude)
```

Executa a etapa `update Location` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Rejeições locais:**
- `throw new IllegalStateException("A localização só é compartilhada durante o trajeto.");`

**Decisões condicionais examinadas:**
- `(status!=DeliveryStatus.IN_TRANSIT)`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCourierLatitude — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:28)

```java
public Double getCourierLatitude()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return courierLatitude;`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCourierLongitude — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:28)

```java
public Double getCourierLongitude()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return courierLongitude;`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getLocationUpdatedAt — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryTask.java:28)

```java
public Instant getLocationUpdatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return locationUpdatedAt;`

**Decisão de engenharia:** Entrega. Somente o entregador responsável acessa a tarefa. O pedido precisa estar pronto; a conclusão da entrega conclui o pedido e a visualização final omite endereço e observação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/Account.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [Account.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java) · SHA-256 `acb63672df88feac92ae839774cf65043991d8ee862f95693d1f03732fae5b93`

### Account — linha 93

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:93)

```java
protected Account()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### Account — linha 95

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:95)

```java
public Account(String email, String name, String passwordHash)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 101

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:101)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getEmail — linha 102

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:102)

```java
public String getEmail()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return email;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getName — linha 103

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:103)

```java
public String getName()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return name;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getPasswordHash — linha 104

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:104)

```java
public String getPasswordHash()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return passwordHash;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getRole — linha 105

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:105)

```java
public AccountRole getRole()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return role;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isActive — linha 106

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:106)

```java
public boolean isActive()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return active;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getOperatorApplicationStatus — linha 107

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:107)

```java
public String getOperatorApplicationStatus()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return operatorApplicationStatus;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCpf — linha 108

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:108)

```java
public String getCpf()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return cpf;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getGrantedRoles — linha 109

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:109)

```java
public List<AccountRole> getGrantedRoles()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `List.of`; `parseRoles`; `suspended.contains`.

**Decisões condicionais examinadas:**
- `(!active)`

**Saída declarada:**
- `return List.of();`
- `return parseRoles(grantedRoleCodes).stream().filter(role -> !suspended.contains(role)).sorted().toList();`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getRegisteredRoles — linha 114

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:114)

```java
public List<AccountRole> getRegisteredRoles()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `parseRoles`.

**Saída declarada:**
- `return parseRoles(grantedRoleCodes).stream().sorted().toList();`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getRequestedOperatorRole — linha 115

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:115)

```java
public AccountRole getRequestedOperatorRole()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return requestedOperatorRole;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isPasswordChangeRequired — linha 116

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:116)

```java
public boolean isPasswordChangeRequired()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return passwordChangeRequired;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCredentialVersion — linha 117

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:117)

```java
public int getCredentialVersion()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return credentialVersion;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 118

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:118)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getPhone — linha 119

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:119)

```java
public String getPhone()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return phone;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isPhoneVerified — linha 120

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:120)

```java
public boolean isPhoneVerified()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return phoneVerified;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAddressLine — linha 121

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:121)

```java
public String getAddressLine()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return addressLine;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAddressComplement — linha 122

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:122)

```java
public String getAddressComplement()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return addressComplement;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getNeighborhood — linha 123

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:123)

```java
public String getNeighborhood()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return neighborhood;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCity — linha 124

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:124)

```java
public String getCity()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return city;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getState — linha 125

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:125)

```java
public String getState()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return state;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getPostalCode — linha 126

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:126)

```java
public String getPostalCode()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return postalCode;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setRole — linha 127

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:127)

```java
public void setRole(AccountRole role)
```

Executa a etapa `set Role` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `parseRoles`; `roles.clear`; `roles.add`; `encodeRoles`.

**Decisões condicionais examinadas:**
- `(role == AccountRole.ADMIN)`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deactivate — linha 134

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:134)

```java
public void deactivate()
```

Executa a etapa `deactivate` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### submitOperatorApplication — linha 135

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:135)

```java
public void submitOperatorApplication(AccountRole requestedRole)
```

Executa a etapa `submit Operator Application` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `getActiveRoles`; `"PENDING".equals`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Escolha vendedor ou entregador.");`
- `throw new IllegalArgumentException("Esta conta já tem este tipo de acesso.");`
- `throw new IllegalArgumentException("Já existe uma solicitação aguardando análise.");`

**Decisões condicionais examinadas:**
- `(requestedRole != AccountRole.MERCHANT && requestedRole != AccountRole.COURIER)`
- `(getActiveRoles().contains(requestedRole))`
- `("PENDING".equals(operatorApplicationStatus))`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### approveOperatorApplication — linha 144

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:144)

```java
public void approveOperatorApplication()
```

Executa a etapa `approve Operator Application` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"PENDING".equals`; `parseRoles`; `roles.add`; `encodeRoles`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Esta solicitação não está pendente.");`

**Decisões condicionais examinadas:**
- `(!"PENDING".equals(operatorApplicationStatus))`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### rejectOperatorApplication — linha 151

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:151)

```java
public void rejectOperatorApplication()
```

Executa a etapa `reject Operator Application` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"PENDING".equals`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Esta solicitação não está pendente.");`

**Decisões condicionais examinadas:**
- `(!"PENDING".equals(operatorApplicationStatus))`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### activate — linha 157

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:157)

```java
public void activate()
```

Executa a etapa `activate` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setRoleActive — linha 159

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:159)

```java
public void setRoleActive(AccountRole requestedRole, boolean enabled)
```

Executa a etapa `set Role Active` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `parseRoles`; `roles.contains`; `suspended.remove`; `suspended.add`; `encodeRoles`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Este perfil não pode ser alterado por aqui.");`

**Decisões condicionais examinadas:**
- `(!roles.contains(requestedRole) || requestedRole == AccountRole.ADMIN)`
- `(enabled)`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setCpfOnce — linha 170

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:170)

```java
public void setCpfOnce(String value)
```

Executa a etapa `set Cpf Once` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.replaceAll`; `Cpf.isValid`; `cpf.equals`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe um CPF válido.");`
- `throw new IllegalArgumentException("O CPF não pode ser alterado depois do cadastro.");`

**Decisões condicionais examinadas:**
- `value == null`
- `(normalized == null || !Cpf.isValid(normalized))`
- `(cpf != null && !cpf.equals(normalized))`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setCpfByAdministrator — linha 178

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:178)

```java
public void setCpfByAdministrator(String value)
```

Executa a etapa `set Cpf By Administrator` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.isBlank`; `value.replaceAll`; `Cpf.isValid`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe um CPF válido ou deixe o campo vazio.");`

**Decisões condicionais examinadas:**
- `value == null || value.isBlank()`
- `(normalized != null && !Cpf.isValid(normalized))`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### changeEmailByAdministrator — linha 184

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:184)

```java
public void changeEmailByAdministrator(String value)
```

Executa a etapa `change Email By Administrator` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.isBlank`; `value.length`; `value.matches`; `value.trim`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Informe um e-mail válido.");`

**Decisões condicionais examinadas:**
- `(value == null || value.isBlank() || value.length() > 254 || !value.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### changePassword — linha 190

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:190)

```java
public void changePassword(String encodedPassword)
```

Executa a etapa `change Password` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Math.addExact`.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requirePasswordChange — linha 191

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:191)

```java
public void requirePasswordChange(String encodedPassword)
```

Executa a etapa `require Password Change` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Math.addExact`.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getActiveRoles — linha 193

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:193)

```java
private Set<AccountRole> getActiveRoles()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `Set.copyOf`; `getGrantedRoles`.

**Saída declarada:**
- `return Set.copyOf(getGrantedRoles());`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### parseRoles — linha 194

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:194)

```java
private static Set<AccountRole> parseRoles(String value)
```

Executa a etapa `parse Roles` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `EnumSet.noneOf`; `value.isBlank`; `Arrays.stream`; `value.split`; `s.isEmpty`.

**Decisões condicionais examinadas:**
- `(value != null && !value.isBlank())`

**Saída declarada:**
- `return result;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### encodeRoles — linha 200

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:200)

```java
private static String encodeRoles(Set<AccountRole> roles)
```

Executa a etapa `encode Roles` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `roles.stream`.

**Saída declarada:**
- `return roles.stream().sorted().map(Enum::name).reduce((a,b)->a+","+b).orElse("");`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateProfile — linha 202

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:202)

```java
public void updateProfile(String name, String phone, String addressLine, String addressComplement, String neighborhood, String city, String state, String postalCode)
```

Executa a etapa `update Profile` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `phone.replaceAll`; `this.phone.replaceAll`; `java.util.Objects.equals`.

**Decisões condicionais examinadas:**
- `phone == null`
- `this.phone == null`
- `(!java.util.Objects.equals(oldPhone, normalizedPhone))`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### markPhoneVerified — linha 217

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:217)

```java
public void markPhoneVerified()
```

Executa a etapa `mark Phone Verified` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateAddress — linha 219

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Account.java:219)

```java
public void updateAddress(String addressLine, String addressComplement, String neighborhood, String city, String state, String postalCode)
```

Executa a etapa `update Address` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [AccountRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java) · SHA-256 `4c218e797c8130cf31daed6bdb72e6025fc3f2f63541e5b3b2d033b15a62a34a`

### findByEmailIgnoreCase — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java:14)

```java
Optional<Account> findByEmailIgnoreCase(String email);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByEmailForUpdate — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java:15)

```java
@Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select a from Account a where lower(a.email) = lower(:email)") Optional<Account> findByEmailForUpdate(@Param("email") String email);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByEmailIgnoreCase — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java:18)

```java
boolean existsByEmailIgnoreCase(String email);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByEmailIgnoreCaseAndIdNot — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java:19)

```java
boolean existsByEmailIgnoreCaseAndIdNot(String email, UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByCpf — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java:20)

```java
boolean existsByCpf(String cpf);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByCpf — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java:21)

```java
Optional<Account> findByCpf(String cpf);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByRoleIn — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java:22)

```java
List<Account> findByRoleIn(Collection<AccountRole> roles);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByOperatorApplicationStatus — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java:23)

```java
List<Account> findByOperatorApplicationStatus(String status);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByIdForUpdate — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRepository.java:24)

```java
@Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select a from Account a where a.id = :id") Optional<Account> findByIdForUpdate(@Param("id") UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/AccountRole.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [AccountRole.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AccountRole.java) · SHA-256 `9bf8eb9f5ac66c43bd9f2fc4bd56aeb129ef5fc384cca174c36dc551504e825b`

**Contrato de dados/estados:**
```java
public enum AccountRole
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

Sem função explícita: contrato declarativo, enum ou inicializador. Os campos e as anotações da fonte são o contrato deste arquivo.

## Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [AuthController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java) · SHA-256 `8be203505d16718be5d94904880d78b2a61368280f4a11d0245c72475c876bfe`

**Contrato de dados/estados:**
```java
public record RegisterRequest(@NotBlank @Size(max = 120) String name,
                                  @NotBlank @Email @Size(max = 254) String email,
                                  @NotBlank @Size(min = 12, max = 72) String password,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record OperatorApplicationRequest(@NotBlank @Size(max = 120) String name,
                                  @NotBlank @Email @Size(max = 254) String email,
                                  @NotBlank @Size(min = 12, max = 72) String password,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record LoginRequest(@NotBlank @Email String email, @NotBlank String password)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record RecoveryRequest(@NotBlank @Email @Size(max=254) String email,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="EMAIL|WHATSAPP") String channel)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record RecoveryResetRequest(@NotBlank @Email @Size(max=254) String email,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="EMAIL|WHATSAPP") String channel,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### AuthController — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:26)

```java
public AuthController(AuthService authService, PasswordEncoder passwordEncoder, PasswordRecoveryService recovery, AuthSessionCookie sessionCookie)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### register — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:34)

```java
@PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) public Map<String, Object> register(@Valid @RequestBody RegisterRequest request)
```

Executa a etapa `register` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `authService.register`; `request.name`; `request.email`; `request.password`; `request.cpf`; `Map.of`; `account.getId`; `account.getName`; `account.getEmail`; `account.getRole`.

**Saída declarada:**
- `return Map.of("id", account.getId(), "name", account.getName(), "email", account.getEmail(), "role", account.getRole());`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### applyForOperator — linha 41

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:41)

```java
@PostMapping("/operator-applications") @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.GONE) public Map<String, Object> applyForOperator(@Valid @RequestBody OperatorApplicationRequest request)
```

Executa a etapa `apply For Operator` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalArgumentException("Entre na sua conta existente e solicite o perfil profissional em Minha conta > Acessos. Não crie uma nova conta para outro perfil.");`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### login — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:47)

```java
@PostMapping("/login") public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request)
```

Executa a etapa `login` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `sessionCookie.responseWithToken`; `authService.login`; `request.email`; `request.password`.

**Saída declarada:**
- `return sessionCookie.responseWithToken(authService.login(request.email(), request.password()));`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### logout — linha 52

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:52)

```java
@PostMapping("/logout") public ResponseEntity<Void> logout()
```

Executa a etapa `logout` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ResponseEntity.noContent`; `sessionCookie.clear`.

**Saída declarada:**
- `return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, sessionCookie.clear().toString()).build();`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestPasswordRecovery — linha 57

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:57)

```java
@PostMapping("/password-recovery") @ResponseStatus(HttpStatus.ACCEPTED) public Map<String, String> requestPasswordRecovery(@Valid @RequestBody RecoveryRequest request)
```

Executa a etapa `request Password Recovery` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `recovery.requestPasswordReset`; `request.email`; `request.channel`; `Map.of`.

**Saída declarada:**
- `return Map.of("message", "Se os dados forem elegíveis, enviaremos instruções pelo canal escolhido.");`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### resetPassword — linha 64

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:64)

```java
@PostMapping("/password-recovery/reset") public Map<String, String> resetPassword(@Valid @RequestBody RecoveryResetRequest request)
```

Executa a etapa `reset Password` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `recovery.resetPassword`; `request.email`; `request.channel`; `request.code`; `request.newPassword`; `Map.of`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Código inválido ou expirado. Solicite outro código e tente novamente.");`

**Decisões condicionais examinadas:**
- `(!recovery.resetPassword(request.email(), request.channel(), request.code(), request.newPassword()))`

**Saída declarada:**
- `return Map.of("message", "Senha redefinida. Entre com sua nova senha.");`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/AuthService.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [AuthService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthService.java) · SHA-256 `5a8732a6f765b7e347b4f5901c05e4458febc76e36b12c9ce6dade56ea651f64`

### AuthService — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthService.java:21)

```java
public AuthService(AccountRepository accounts, AuthenticationManager authenticationManager, JwtEncoder jwtEncoder)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### register — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthService.java:27)

```java
@Transactional public Account register(String name, String email, String password, String cpf, org.springframework.security.crypto.password.PasswordEncoder encoder)
```

Executa a etapa `register` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `createAccount`.

**Saída declarada:**
- `return createAccount(name, email, password, cpf, encoder);`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createAccount — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthService.java:32)

```java
private Account createAccount(String name, String email, String password, String cpf, org.springframework.security.crypto.password.PasswordEncoder encoder)
```

Executa a etapa `create Account` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `email.trim`; `accounts.existsByEmailIgnoreCase`; `cpf.replaceAll`; `Cpf.isValid`; `accounts.existsByCpf`; `name.trim`; `encoder.encode`; `account.setCpfOnce`; `accounts.save`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Já existe uma conta com este e-mail.");`
- `throw new IllegalArgumentException("Informe um CPF válido.");`
- `throw new IllegalArgumentException("Este CPF já está vinculado a uma conta. Entre na conta existente para solicitar outro perfil.");`

**Decisões condicionais examinadas:**
- `(accounts.existsByEmailIgnoreCase(normalizedEmail))`
- `cpf == null`
- `(!Cpf.isValid(normalizedCpf))`
- `(accounts.existsByCpf(normalizedCpf))`

**Saída declarada:**
- `return accounts.save(account);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
account.setCpfOnce(normalizedCpf);
return accounts.save(account);
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### login — linha 46

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthService.java:46)

```java
public Map<String, Object> login(String email, String password)
```

Executa a etapa `login` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `email.trim`; `authenticationManager.authenticate`; `accounts.findByEmailIgnoreCase`; `issueToken`.

**Saída declarada:**
- `return issueToken(account);`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### issueToken — linha 53

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthService.java:53)

```java
public Map<String, Object> issueToken(Account account)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `Instant.now`; `JwtClaimsSet.builder`; `now.plusSeconds`; `account.getId`; `account.getEmail`; `account.getRole`; `account.getGrantedRoles`; `account.isPasswordChangeRequired`; `account.getCredentialVersion`; `JwsHeader.with`; `Map.of`; `jwtEncoder.encode`; `JwtEncoderParameters.from`; `accountView`.

**Saída declarada:**
- `return Map.of("accessToken", jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue(), "tokenType", "Bearer", "expiresIn", 3600, "account", accountView(account));`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### accountView — linha 72

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthService.java:72)

```java
private static Map<String, Object> accountView(Account account)
```

Executa a etapa `account View` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `view.put`; `account.getId`; `account.getName`; `account.getEmail`; `account.getRole`; `account.getGrantedRoles`; `account.getRegisteredRoles`; `account.getCpf`; `account.isPhoneVerified`; `account.isPasswordChangeRequired`.

**Saída declarada:**
- `return view;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/AuthSessionCookie.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [AuthSessionCookie.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthSessionCookie.java) · SHA-256 `4c6f031f6f69e710295335bc024a93798eccf64cbd728cf3e0db4ed5319f84ae`

### AuthSessionCookie — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthSessionCookie.java:19)

```java
public AuthSessionCookie(@Value("${app.security.cookie-secure:false}") boolean secure)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### issue — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthSessionCookie.java:23)

```java
public ResponseCookie issue(String token)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `ResponseCookie.from`.

**Saída declarada:**
- `return ResponseCookie.from(name, token) .httpOnly(true) .secure(secure) .sameSite("Lax") .path(PATH) .maxAge(MAX_AGE_SECONDS) .build();`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### clear — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthSessionCookie.java:33)

```java
public ResponseCookie clear()
```

Executa a etapa `clear` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ResponseCookie.from`.

**Saída declarada:**
- `return ResponseCookie.from(name, "") .httpOnly(true) .secure(secure) .sameSite("Lax") .path(PATH) .maxAge(0) .build();`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### responseWithToken — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthSessionCookie.java:43)

```java
public ResponseEntity<Map<String, Object>> responseWithToken(Map<String, Object> tokenResponse)
```

Executa a etapa `response With Token` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `tokenResponse.get`; `value.isBlank`; `body.remove`; `body.put`; `ResponseEntity.ok`; `issue`.

**Rejeições locais:**
- `throw new IllegalStateException("A sessão não pôde ser criada.");`

**Decisões condicionais examinadas:**
- `(!(token instanceof String value) || value.isBlank())`

**Saída declarada:**
- `return ResponseEntity.ok() .header(HttpHeaders.SET_COOKIE, issue(value).toString()) .header(HttpHeaders.CACHE_CONTROL, "no-store") .header(HttpHeaders.PRAGMA, "no-cache") .body(body);`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/Cnpj.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [Cnpj.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Cnpj.java) · SHA-256 `11b28e38ca45669030a21f0baa55d70696f5d1b76973c8952bd3ab8d30650322`

### Cnpj — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Cnpj.java:4)

```java
private Cnpj()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### normalize — linha 5

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Cnpj.java:5)

```java
public static String normalize(String value)
```

Executa a etapa `normalize` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.replaceAll`.

**Decisões condicionais examinadas:**
- `value == null`

**Saída declarada:**
- `return value == null ? null : value.replaceAll("\\D", "");`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isValid — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Cnpj.java:6)

```java
public static boolean isValid(String value)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `normalize`; `c.matches`; `c.chars`; `digit`; `c.substring`; `c.charAt`.

**Decisões condicionais examinadas:**
- `(c == null || !c.matches("\\d{14}") || c.chars().distinct().count() == 1)`

**Saída declarada:**
- `return false;`
- `return digit(c.substring(0,12),w1)==c.charAt(12)-'0' && digit(c.substring(0,13),w2)==c.charAt(13)-'0';`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### digit — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Cnpj.java:12)

```java
private static int digit(String value,int[] weights)
```

Executa a etapa `digit` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.charAt`.

**Decisões condicionais examinadas:**
- `r<2`

**Saída declarada:**
- `return r<2?0:11-r;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/Cpf.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [Cpf.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Cpf.java) · SHA-256 `e52f1c362e110bbf1198b937bc9fe5482f3f644965a7548ec83cc978fae7b540`

### Cpf — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Cpf.java:4)

```java
private Cpf()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isValid — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Cpf.java:6)

```java
public static boolean isValid(String value)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `value.replaceAll`; `cpf.matches`; `cpf.chars`; `checkDigit`; `cpf.substring`; `cpf.charAt`.

**Decisões condicionais examinadas:**
- `(value == null)`
- `(!cpf.matches("\\d{11}") || cpf.chars().distinct().count() == 1)`

**Saída declarada:**
- `return false;`
- `return false;`
- `return cpf.charAt(9) - '0' == first && cpf.charAt(10) - '0' == second;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### checkDigit — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/Cpf.java:15)

```java
private static int checkDigit(String value, int weight)
```

Executa a etapa `check Digit` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.toCharArray`.

**Decisões condicionais examinadas:**
- `remainder < 2`

**Saída declarada:**
- `return remainder < 2 ? 0 : 11 - remainder;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [CustomerAddress.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java) · SHA-256 `9559da34948d9242fc5918dbc23aa0b09212879402ee7882a4914428302376f1`

### CustomerAddress — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:20)

```java
protected CustomerAddress()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### CustomerAddress — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:21)

```java
public CustomerAddress(Account account, String label, String addressLine, String complement, String neighborhood, String city, String state, String postalCode, boolean isDefault)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `update`.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### update — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:25)

```java
public void update(String label, String addressLine, String complement, String neighborhood, String city, String state, String postalCode)
```

Executa a etapa `update` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:30)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAccount — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:31)

```java
public Account getAccount()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return account;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getLabel — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:32)

```java
public String getLabel()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return label;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAddressLine — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:33)

```java
public String getAddressLine()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return addressLine;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAddressComplement — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:34)

```java
public String getAddressComplement()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return addressComplement;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getNeighborhood — linha 35

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:35)

```java
public String getNeighborhood()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return neighborhood;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCity — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:36)

```java
public String getCity()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return city;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getState — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:37)

```java
public String getState()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return state;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getPostalCode — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:38)

```java
public String getPostalCode()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return postalCode;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isDefaultAddress — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:39)

```java
public boolean isDefaultAddress()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return defaultAddress;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setDefaultAddress — linha 40

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddress.java:40)

```java
public void setDefaultAddress(boolean value)
```

Executa a etapa `set Default Address` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [CustomerAddressController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java) · SHA-256 `641960ba42d817bbb84a412e78dc34121c6a3c46e04b332b84a1b2cd3cf38ea8`

**Contrato de dados/estados:**
```java
public record AddressRequest(@Size(max=40) String label, @NotBlank @Size(max=250) String addressLine,
            @Size(max=120) String addressComplement, @NotBlank @Size(max=120) String neighborhood,
            @NotBlank @Size(max=100) String city, @NotBlank @Size(min=2,max=2) String state,
            @NotBlank @Pattern(regexp="\\d
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record AddressView(UUID id, String label, String addressLine, String addressComplement,
            String neighborhood, String city, String state, String postalCode, boolean defaultAddress)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### CustomerAddressController — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:22)

```java
public CustomerAddressController(CustomerAddressRepository addresses, AccountRepository accounts)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### list — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:26)

```java
@GetMapping @Transactional(readOnly = true) public List<AddressView> list(Authentication auth)
```

Executa a etapa `list` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `subject`; `addresses.findByAccountIdOrderByDefaultAddressDescIdAsc`.

**Saída declarada:**
- `return addresses.findByAccountIdOrderByDefaultAddressDescIdAsc(owner).stream().map(AddressView::from).toList();`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### create — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:32)

```java
@PostMapping @ResponseStatus(HttpStatus.CREATED) @Transactional public AddressView create(@Valid @RequestBody AddressRequest request, Authentication auth)
```

Executa a etapa `create` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `subject`; `accounts.findByIdForUpdate`; `request.defaultAddress`; `addresses.existsByAccountId`; `addresses.findByAccountIdOrderByDefaultAddressDescIdAsc`; `a.setDefaultAddress`; `addresses.save`; `label`; `request.label`; `request.addressLine`; `clean`; `request.addressComplement`; `request.neighborhood`; `request.city`; `request.state`; `normalizeCep`; `request.postalCode`; `updateProfileAddress`; `AddressView.from`.

**Decisões condicionais examinadas:**
- `(makeDefault)`

**Saída declarada:**
- `return AddressView.from(saved);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
if (makeDefault) addresses.findByAccountIdOrderByDefaultAddressDescIdAsc(owner).forEach(a -> a.setDefaultAddress(false));
CustomerAddress saved = addresses.save(new CustomerAddress(account, label(request.label()), request.addressLine().trim(),
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### update — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:45)

```java
@PutMapping("/{id}") @Transactional public AddressView update(@PathVariable UUID id, @Valid @RequestBody AddressRequest request, Authentication auth)
```

Executa a etapa `update` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `subject`; `accounts.findByIdForUpdate`; `addresses.findByIdAndAccountId`; `address.update`; `label`; `request.label`; `request.addressLine`; `clean`; `request.addressComplement`; `request.neighborhood`; `request.city`; `request.state`; `normalizeCep`; `request.postalCode`; `request.defaultAddress`; `setDefault`; `address.isDefaultAddress`; `updateProfileAddress`; `address.getAccount`; `AddressView.from`.

**Decisões condicionais examinadas:**
- `(request.defaultAddress())`
- `(address.isDefaultAddress())`

**Saída declarada:**
- `return AddressView.from(address);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
address.update(label(request.label()), request.addressLine().trim(), clean(request.addressComplement()),
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### makeDefault — linha 57

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:57)

```java
@PatchMapping("/{id}/default") @Transactional public AddressView makeDefault(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `make Default` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `subject`; `accounts.findByIdForUpdate`; `addresses.findByIdAndAccountId`; `setDefault`; `updateProfileAddress`; `address.getAccount`; `AddressView.from`.

**Saída declarada:**
- `return AddressView.from(address);`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### delete — linha 66

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:66)

```java
@DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional public void delete(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `delete` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `subject`; `accounts.findByIdForUpdate`; `addresses.findByIdAndAccountId`; `address.isDefaultAddress`; `addresses.delete`; `addresses.findByAccountIdOrderByDefaultAddressDescIdAsc`; `next.setDefaultAddress`; `updateProfileAddress`; `address.getAccount`; `accounts.save`.

**Decisões condicionais examinadas:**
- `(wasDefault)`
- `(next != null)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
addresses.delete(address);
if (next != null) { next.setDefaultAddress(true); updateProfileAddress(address.getAccount(), next); }
else { address.getAccount().updateAddress(null, null, null, null, null, null); accounts.save(address.getAccount()); }
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setDefault — linha 80

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:80)

```java
private void setDefault(UUID owner, CustomerAddress selected)
```

Executa a etapa `set Default` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `addresses.findByAccountIdOrderByDefaultAddressDescIdAsc`; `a.setDefaultAddress`; `a.getId`; `selected.getId`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
addresses.findByAccountIdOrderByDefaultAddressDescIdAsc(owner).forEach(a -> a.setDefaultAddress(a.getId().equals(selected.getId())));
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateProfileAddress — linha 83

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:83)

```java
private void updateProfileAddress(Account account, CustomerAddress address)
```

Executa a etapa `update Profile Address` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `account.updateAddress`; `address.getAddressLine`; `address.getAddressComplement`; `address.getNeighborhood`; `address.getCity`; `address.getState`; `address.getPostalCode`; `accounts.save`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
account.updateAddress(address.getAddressLine(), address.getAddressComplement(), address.getNeighborhood(),
accounts.save(account);
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### clean — linha 88

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:88)

```java
private static String clean(String value)
```

Executa a etapa `clean` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.isBlank`; `value.trim`.

**Decisões condicionais examinadas:**
- `value == null || value.isBlank()`

**Saída declarada:**
- `return value == null || value.isBlank() ? null : value.trim();`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### label — linha 89

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:89)

```java
private static String label(String value)
```

Executa a etapa `label` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `clean`.

**Decisões condicionais examinadas:**
- `clean == null`

**Saída declarada:**
- `return clean == null ? "Meu endereço" : clean;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### normalizeCep — linha 90

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:90)

```java
private static String normalizeCep(String value)
```

Executa a etapa `normalize Cep` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.replaceAll`.

**Saída declarada:**
- `return value.replaceAll("\\D", "");`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 91

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:91)

```java
private static UUID subject(Authentication auth)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `auth.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject());`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### from — linha 99

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:99)

```java
static AddressView from(CustomerAddress a)
```

Executa a etapa `from` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `a.getId`; `a.getLabel`; `a.getAddressLine`; `a.getAddressComplement`; `a.getNeighborhood`; `a.getCity`; `a.getState`; `a.getPostalCode`; `a.isDefaultAddress`.

**Saída declarada:**
- `return new AddressView(a.getId(), a.getLabel(), a.getAddressLine(), a.getAddressComplement(), a.getNeighborhood(), a.getCity(), a.getState(), a.getPostalCode(), a.isDefaultAddress());`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressRepository.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [CustomerAddressRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressRepository.java) · SHA-256 `b8a2df7ad757a900ea4045975dea90fca634d72edda09f6e9e6f5be88faa0702`

### findByAccountIdOrderByDefaultAddressDescIdAsc — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressRepository.java:9)

```java
List<CustomerAddress> findByAccountIdOrderByDefaultAddressDescIdAsc(UUID accountId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByIdAndAccountId — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressRepository.java:10)

```java
Optional<CustomerAddress> findByIdAndAccountId(UUID id, UUID accountId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByAccountId — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressRepository.java:11)

```java
boolean existsByAccountId(UUID accountId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [PasswordRecoveryService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java) · SHA-256 `60519d8b28c697043f0c81742457630d6a1acb5ddd763f8c325d7fc56841bb69`

### PasswordRecoveryService — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java:18)

```java
public PasswordRecoveryService(AccountRepository accounts, RecoveryCodeRepository codes, RecoveryMessageSender sender, PasswordEncoder encoder)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
this.accounts = accounts; this.codes = codes; this.sender = sender; this.encoder = encoder;
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestPasswordReset — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java:23)

```java
@Transactional public void requestPasswordReset(String email, String channel)
```

Executa a etapa `request Password Reset` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByEmailForUpdate`; `normalize`; `issue`.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### resetPassword — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java:29)

```java
@Transactional public boolean resetPassword(String email, String channel, String rawCode, String newPassword)
```

Executa a etapa `reset Password` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByEmailForUpdate`; `normalize`; `latest`; `"EMAIL".equals`; `"WHATSAPP".equals`; `Instant.now`; `code.isReadyAt`; `code.getChannel`; `encoder.matches`; `code.getCodeHash`; `code.recordFailedAttempt`; `code.consume`; `account.changePassword`; `encoder.encode`.

**Decisões condicionais examinadas:**
- `(account == null)`
- `(code == null || !"EMAIL".equals(channel) && !"WHATSAPP".equals(channel))`
- `(!code.isReadyAt(now) || !code.getChannel().equals(channel))`
- `(!encoder.matches(rawCode, code.getCodeHash()))`

**Saída declarada:**
- `return false;`
- `return false;`
- `return false;`
- `return false;`
- `return true;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestPhoneVerification — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java:43)

```java
@Transactional public void requestPhoneVerification(Account account)
```

Executa a etapa `request Phone Verification` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `account.getPhone`; `issue`.

**Decisões condicionais examinadas:**
- `(account.getPhone() == null || account.getPhone().isBlank())`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### invalidatePhoneCodes — linha 49

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java:49)

```java
@Transactional public void invalidatePhoneCodes(Account account)
```

Executa a etapa `invalidate Phone Codes` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `codes.deleteForPurpose`; `account.getId`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
codes.deleteForPurpose(account.getId(),"PHONE_VERIFY");
codes.deleteForPurpose(account.getId(),"PASSWORD_RESET");
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### verifyPhone — linha 55

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java:55)

```java
@Transactional public boolean verifyPhone(Account account, String rawCode)
```

Executa a etapa `verify Phone` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `latest`; `code.isReadyAt`; `Instant.now`; `encoder.matches`; `code.getCodeHash`; `code.recordFailedAttempt`; `code.consume`; `account.markPhoneVerified`.

**Decisões condicionais examinadas:**
- `(code == null || !code.isReadyAt(Instant.now()))`
- `(!encoder.matches(rawCode, code.getCodeHash()))`

**Saída declarada:**
- `return false;`
- `return false;`
- `return true;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### issue — linha 63

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java:63)

```java
private void issue(Account account, String purpose, String channel, boolean phoneVerification)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `"EMAIL".equals`; `"WHATSAPP".equals`; `account.getPhone`; `account.isPhoneVerified`; `Instant.now`; `latest`; `previous.getNextAllowedAt`; `codes.deleteForPurpose`; `account.getId`; `String.format`; `random.nextInt`; `codes.save`; `encoder.encode`; `sender.sendEmail`; `account.getEmail`; `sender.sendWhatsApp`.

**Decisões condicionais examinadas:**
- `(!"EMAIL".equals(channel) && !"WHATSAPP".equals(channel))`
- `("WHATSAPP".equals(channel) && phoneVerification && (account.getPhone() == null || account.getPhone().isBlank()))`
- `("WHATSAPP".equals(channel) && !phoneVerification && !account.isPhoneVerified())`
- `(previous != null && previous.getNextAllowedAt().isAfter(now))`
- `("EMAIL".equals(channel))`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
codes.deleteForPurpose(account.getId(), purpose);
codes.save(new RecoveryCode(account, purpose, channel, encoder.encode(code), now));
if ("EMAIL".equals(channel)) sender.sendEmail(account.getEmail(), code);
else sender.sendWhatsApp(account.getPhone(), code);
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### latest — linha 82

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java:82)

```java
private RecoveryCode latest(Account account, String purpose)
```

Executa a etapa `latest` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `codes.findLatest`; `account.getId`.

**Saída declarada:**
- `return codes.findLatest(account.getId(), purpose).stream().findFirst().orElse(null);`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### normalize — linha 86

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/PasswordRecoveryService.java:86)

```java
private static String normalize(String email)
```

Executa a etapa `normalize` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `email.trim`.

**Decisões condicionais examinadas:**
- `email == null`

**Saída declarada:**
- `return email == null ? "" : email.trim().toLowerCase(Locale.ROOT);`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [ProfileController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java) · SHA-256 `de3e218283d526fa29a4d08ed82b3d16fb6a12bd302da8251062587eb5ac1df1`

**Contrato de dados/estados:**
```java
public record ProfileUpdate(@NotBlank @Size(max=120) String name,
   @NotBlank @Size(max=30) String phone,
   @Size(max=250) String addressLine,
   @Size(max=120) String addressComplement,
   @Size(max=120) String neighborhood,
   @Size(max=100) String city,
   @Size(max=2) String state,
   @Size(max=9) String postalCode,
   @Size(max=14) @jakarta.validation.constraints.Pattern(regexp="^$|\\d
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record PasswordChange(@NotBlank String currentPassword,@NotBlank @Size(min=12,max=72) String newPassword)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record PhoneVerification(@NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record OperatorApplication(@jakarta.validation.constraints.NotNull AccountRole role)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### ProfileController — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:17)

```java
public ProfileController(AccountRepository accounts,org.springframework.security.crypto.password.PasswordEncoder encoder,AuthService authService,AuthSessionCookie sessionCookie,PasswordRecoveryService recovery)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### me — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:18)

```java
@GetMapping("/me") public Account me(Authentication auth)
```

Executa a etapa `me` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`; `subject`.

**Saída declarada:**
- `return accounts.findById(subject(auth)).orElseThrow();`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### update — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:19)

```java
@PutMapping("/profile") public Account update(@Valid @RequestBody ProfileUpdate p,Authentication auth)
```

Executa a etapa `update` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByIdForUpdate`; `subject`; `clean`; `p.cpf`; `requestedCpf.replaceAll`; `a.getCpf`; `accounts.existsByCpf`; `a.setCpfOnce`; `java.util.Objects.equals`; `a.getPhone`; `p.phone`; `recovery.invalidatePhoneCodes`; `a.updateProfile`; `p.name`; `p.addressLine`; `p.addressComplement`; `p.neighborhood`; `p.city`; `p.state`; `p.postalCode`; `accounts.save`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Este CPF já está vinculado a outra conta.");`

**Decisões condicionais examinadas:**
- `(requestedCpf!=null)`
- `(a.getCpf()==null&&accounts.existsByCpf(normalized))`
- `(!java.util.Objects.equals(a.getPhone()==null?"":a.getPhone().replaceAll("\\D",""),p.phone().replaceAll("\\D","")))`
- `a.getPhone()==null`

**Saída declarada:**
- `return accounts.save(a);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
@PutMapping("/profile") public Account update(@Valid @RequestBody ProfileUpdate p,Authentication auth){Account a=accounts.findByIdForUpdate(subject(auth)).orElseThrow();String requestedCpf=clean(p.cpf());if(requestedCpf!=null){String normalized=requestedCpf.replaceAll("\\D","");if(a.getCpf()==null&&accounts.existsByCpf(normalized))throw new IllegalArgumentException("Este CPF já está vinculado a outra conta.");a.setCpfOnce(normalized);}if(!java.util.Objects.equals(a.getPhone()==null?"":a.getPhone().replaceAll("\\D",""),p.phone().replaceAll("\\D","")))recovery.invalidatePhoneCodes(a);a.updateProfile(p.name().trim(),p.phone().trim(),clean(p.addressLine()),clean(p.addressComplement()),clean(p.neighborhood()),clean(p.city()),clean(p.state()),clean(p.postalCode()));return accounts.save(a);}
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### applyForOperator — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:20)

```java
@PostMapping("/operator-applications") @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.GONE) public Account applyForOperator(@Valid @RequestBody OperatorApplication p,Authentication auth)
```

Executa a etapa `apply For Operator` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalArgumentException("Use as solicitações de perfil em Minha conta > Acessos.");`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### changePassword — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:21)

```java
@PostMapping("/password") public org.springframework.http.ResponseEntity<java.util.Map<String,Object>> changePassword(@Valid @RequestBody PasswordChange p,Authentication auth)
```

Executa a etapa `change Password` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByIdForUpdate`; `subject`; `encoder.matches`; `p.currentPassword`; `a.getPasswordHash`; `p.newPassword`; `a.changePassword`; `encoder.encode`; `accounts.save`; `sessionCookie.responseWithToken`; `authService.issueToken`.

**Rejeições locais:**
- `throw new IllegalArgumentException("A senha atual não confere.");`
- `throw new IllegalArgumentException("Escolha uma senha diferente da atual.");`

**Decisões condicionais examinadas:**
- `(!encoder.matches(p.currentPassword(),a.getPasswordHash()))`
- `(encoder.matches(p.newPassword(),a.getPasswordHash()))`

**Saída declarada:**
- `return sessionCookie.responseWithToken(authService.issueToken(a));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
@PostMapping("/password") public org.springframework.http.ResponseEntity<java.util.Map<String,Object>> changePassword(@Valid @RequestBody PasswordChange p,Authentication auth){Account a=accounts.findByIdForUpdate(subject(auth)).orElseThrow();if(!encoder.matches(p.currentPassword(),a.getPasswordHash()))throw new IllegalArgumentException("A senha atual não confere.");if(encoder.matches(p.newPassword(),a.getPasswordHash()))throw new IllegalArgumentException("Escolha uma senha diferente da atual.");a.changePassword(encoder.encode(p.newPassword()));accounts.save(a);return sessionCookie.responseWithToken(authService.issueToken(a));}
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestPhoneVerification — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:22)

```java
@PostMapping("/phone-verification") @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.ACCEPTED) public java.util.Map<String,String> requestPhoneVerification(Authentication auth)
```

Executa a etapa `request Phone Verification` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByIdForUpdate`; `subject`; `recovery.requestPhoneVerification`; `java.util.Map.of`.

**Saída declarada:**
- `return java.util.Map.of("message","Se houver um telefone válido, enviaremos um código pelo WhatsApp.");`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### confirmPhone — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:23)

```java
@PostMapping("/phone-verification/confirm") public java.util.Map<String,String> confirmPhone(@Valid @RequestBody PhoneVerification p,Authentication auth)
```

Executa a etapa `confirm Phone` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByIdForUpdate`; `subject`; `recovery.verifyPhone`; `p.code`; `accounts.save`; `java.util.Map.of`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Código inválido ou expirado.");`

**Decisões condicionais examinadas:**
- `(!recovery.verifyPhone(a,p.code()))`

**Saída declarada:**
- `return java.util.Map.of("message","Telefone confirmado.");`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
@PostMapping("/phone-verification/confirm") public java.util.Map<String,String> confirmPhone(@Valid @RequestBody PhoneVerification p,Authentication auth){Account a=accounts.findByIdForUpdate(subject(auth)).orElseThrow();if(!recovery.verifyPhone(a,p.code()))throw new IllegalArgumentException("Código inválido ou expirado.");accounts.save(a);return java.util.Map.of("message","Telefone confirmado.");}
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### clean — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:24)

```java
private String clean(String value)
```

Executa a etapa `clean` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.isBlank`; `value.trim`.

**Decisões condicionais examinadas:**
- `value==null||value.isBlank()`

**Saída declarada:**
- `return value==null||value.isBlank()?null:value.trim();`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:25)

```java
private UUID subject(Authentication a)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `a.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### OperatorApplication — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:37)

```java
public OperatorApplication
```

Executa a etapa `Operator Application` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalArgumentException("Escolha vendedor ou entregador.");`

**Decisões condicionais examinadas:**
- `(role!=AccountRole.MERCHANT&&role!=AccountRole.COURIER)`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [RecoveryCode.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java) · SHA-256 `2650b44c882e134fef7539aae2c72a226b8fff01d0088f891a030f9451af746d`

### RecoveryCode — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:23)

```java
protected RecoveryCode()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### RecoveryCode — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:24)

```java
RecoveryCode(Account account, String purpose, String channel, String codeHash, Instant now)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `now.plusSeconds`.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:28)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAccount — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:29)

```java
public Account getAccount()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return account;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getPurpose — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:30)

```java
public String getPurpose()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return purpose;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getChannel — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:31)

```java
public String getChannel()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return channel;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCodeHash — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:32)

```java
public String getCodeHash()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return codeHash;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getExpiresAt — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:33)

```java
public Instant getExpiresAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return expiresAt;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getNextAllowedAt — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:34)

```java
public Instant getNextAllowedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return nextAllowedAt;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getConsumedAt — linha 35

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:35)

```java
public Instant getConsumedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return consumedAt;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAttempts — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:36)

```java
public int getAttempts()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return attempts;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isReadyAt — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:37)

```java
public boolean isReadyAt(Instant now)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `expiresAt.isAfter`.

**Saída declarada:**
- `return consumedAt == null && expiresAt.isAfter(now) && attempts < 5;`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### recordFailedAttempt — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:38)

```java
public void recordFailedAttempt()
```

Executa a etapa `record Failed Attempt` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Decisões condicionais examinadas:**
- `(attempts >= 5)`

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### consume — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCode.java:39)

```java
public void consume(Instant now)
```

Executa a etapa `consume` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCodeRepository.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [RecoveryCodeRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCodeRepository.java) · SHA-256 `2fb486f4928e9c35178e82b898cabec3623b06bc51dbac1578fd4c562106ee4d`

### findLatest — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCodeRepository.java:10)

```java
@Query("select c from RecoveryCode c where c.account.id = :accountId and c.purpose = :purpose order by c.createdAt desc") java.util.List<RecoveryCode> findLatest(@Param("accountId") UUID accountId, @Param("purpose") String purpose);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deleteForPurpose — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryCodeRepository.java:12)

```java
@Modifying @Query("delete from RecoveryCode c where c.account.id = :accountId and c.purpose = :purpose") void deleteForPurpose(@Param("accountId") UUID accountId, @Param("purpose") String purpose);
```

Executa a etapa `delete For Purpose` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/identity/RecoveryMessageSender.java

Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface.

Fonte: [RecoveryMessageSender.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryMessageSender.java) · SHA-256 `ecfa41758062ae10ea7df594476dd1b1c05fbd6f7b8fc226bd7b5bde2a161e59`

### RecoveryMessageSender — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryMessageSender.java:30)

```java
public RecoveryMessageSender(ObjectProvider<JavaMailSender> mailSender, @Value("${app.recovery.email-from}") String emailFrom, @Value("${app.recovery.whatsapp.enabled:false}") boolean whatsappEnabled, @Value("${app.recovery.whatsapp.graph-version:v23.0}") String graphVersion, @Value("${app.recovery.whatsapp.phone-number-id:}") String phoneNumberId, @Value("${app.recovery.whatsapp.access-token:}") String accessToken, @Value("${app.recovery.whatsapp.template-name:fooddelivery_password_recovery}") String templateName, @Value("${app.recovery.whatsapp.language:pt_BR}") String language)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `HttpClient.newBuilder`; `Duration.ofSeconds`; `factory.setReadTimeout`; `RestClient.builder`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
factory.setReadTimeout(Duration.ofSeconds(10));
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sendEmail — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryMessageSender.java:47)

```java
public void sendEmail(String recipient, String code)
```

Executa a etapa `send Email` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `demo.enabled`; `demo.capture`; `mailSender.getIfAvailable`; `message.setFrom`; `message.setTo`; `message.setSubject`; `message.setText`; `sender.send`.

**Rejeições locais:**
- `throw new IllegalStateException("SMTP não configurado.");`

**Decisões condicionais examinadas:**
- `(demo != null && demo.enabled())`
- `(!mailpit)`
- `(sender == null)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
message.setFrom(emailFrom); message.setTo(recipient); message.setSubject("Código de segurança FoodDelivery");
message.setText("Seu código de segurança é " + code + ". Ele expira em 10 minutos. Se você não solicitou, ignore este e-mail.");
sender.send(message);
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sendWhatsApp — linha 57

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/RecoveryMessageSender.java:57)

```java
public void sendWhatsApp(String phone, String code)
```

Executa a etapa `send Whats App` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `demo.enabled`; `demo.capture`; `phoneNumberId.isBlank`; `accessToken.isBlank`; `phone.replaceAll`; `recipient.length`; `http.post`; `h.setBearerAuth`; `java.util.Map.of`; `java.util.List.of`.

**Rejeições locais:**
- `throw new IllegalStateException("WhatsApp Cloud não configurado.");`
- `throw new IllegalArgumentException("Telefone inválido.");`

**Decisões condicionais examinadas:**
- `(demo != null && demo.enabled())`
- `(!whatsappEnabled || phoneNumberId.isBlank() || accessToken.isBlank())`
- `(recipient.length() == 10 || recipient.length() == 11)`
- `(recipient.length() < 12 || recipient.length() > 15)`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
.contentType(MediaType.APPLICATION_JSON).headers(h -> h.setBearerAuth(accessToken))
```

**Decisão de engenharia:** Identidade e dados pessoais. A API deve conferir titularidade antes de ler ou alterar dados; credenciais e verificações pertencem ao servidor, nunca à interface. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/location/DemoGeocodingController.java

Busca e geocodificação. Provedores podem falhar, não conceder CEP ou retornar uma região ambígua; o usuário precisa revisar a cidade. Homologação usa coordenadas fictícias.

Fonte: [DemoGeocodingController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/DemoGeocodingController.java) · SHA-256 `1decd4bcfb449979c640056d257703625abfa03f22cad16b74010762aec2d95b`

### city — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/DemoGeocodingController.java:9)

```java
private static Map<String,Object> city(boolean iepe)
```

Executa a etapa `city` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Map.of`.

**Decisões condicionais examinadas:**
- `iepe`

**Saída declarada:**
- `return Map.of("lat",iepe?"-22.66":"-22.62","lon",iepe?"-51.08":"-51.24", "display_name",iepe?"Iepê, São Paulo, Brasil (simulado)":"Nantes, São Paulo, Brasil (simulado)", "address",Map.of("city",iepe?"Iepê":"Nantes","state","São Paulo","ISO3166-2-lvl4","BR-SP", "postcode",iepe?"19640-000":"19645-000","country_code","br"));`

**Decisão de engenharia:** Busca e geocodificação. Provedores podem falhar, não conceder CEP ou retornar uma região ambígua; o usuário precisa revisar a cidade. Homologação usa coordenadas fictícias. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### search — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/DemoGeocodingController.java:15)

```java
@GetMapping("/search") public Object search(@RequestParam String query)
```

Executa a etapa `search` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `java.text.Normalizer.normalize`; `q.contains`; `q.replaceAll`; `List.of`; `city`.

**Decisões condicionais examinadas:**
- `(q.contains("iepe")||q.replaceAll("\\D","").startsWith("19640"))`
- `(q.contains("nantes")||q.replaceAll("\\D","").startsWith("19645"))`

**Saída declarada:**
- `return List.of(city(true));`
- `return List.of(city(false));`
- `return List.of();`

**Decisão de engenharia:** Busca e geocodificação. Provedores podem falhar, não conceder CEP ou retornar uma região ambígua; o usuário precisa revisar a cidade. Homologação usa coordenadas fictícias. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### reverse — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/DemoGeocodingController.java:21)

```java
@GetMapping("/reverse") public Object reverse(@RequestParam double latitude,@RequestParam double longitude)
```

Executa a etapa `reverse` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Double.isFinite`; `city`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Coordenadas inválidas.");`

**Decisões condicionais examinadas:**
- `(!Double.isFinite(latitude)||!Double.isFinite(longitude)||latitude < -90||latitude > 90||longitude < -180||longitude > 180)`

**Saída declarada:**
- `return city(longitude > -51.16);`

**Decisão de engenharia:** Busca e geocodificação. Provedores podem falhar, não conceder CEP ou retornar uma região ambígua; o usuário precisa revisar a cidade. Homologação usa coordenadas fictícias. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/location/GeocodingController.java

Busca e geocodificação. Provedores podem falhar, não conceder CEP ou retornar uma região ambígua; o usuário precisa revisar a cidade. Homologação usa coordenadas fictícias.

Fonte: [GeocodingController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/GeocodingController.java) · SHA-256 `841a0fdda04b4b1ced938d44cd0c4f9ea785b7ee02ad859056f4e5db990a3772`

**Contrato de dados/estados:**
```java
private record CacheEntry(JsonNode body, long expiresAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### GeocodingController — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/GeocodingController.java:28)

```java
public GeocodingController()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `java.net.http.HttpClient.newBuilder`; `Duration.ofSeconds`; `requestFactory.setReadTimeout`; `RestClient.builder`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
requestFactory.setReadTimeout(Duration.ofSeconds(8));
```

**Decisão de engenharia:** Busca e geocodificação. Provedores podem falhar, não conceder CEP ou retornar uma região ambígua; o usuário precisa revisar a cidade. Homologação usa coordenadas fictícias. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### search — linha 40

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/GeocodingController.java:40)

```java
@GetMapping("/search") public JsonNode search(@RequestParam("query") String query)
```

Executa a etapa `search` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `query.trim`; `normalized.length`; `UriComponentsBuilder.fromPath`; `lookup`.

**Rejeições locais:**
- `throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe uma cidade ou endereço com até 100 caracteres.");`

**Decisões condicionais examinadas:**
- `query == null`
- `(normalized.length() < 2 || normalized.length() > 100)`

**Saída declarada:**
- `return lookup(uri);`

**Decisão de engenharia:** Busca e geocodificação. Provedores podem falhar, não conceder CEP ou retornar uma região ambígua; o usuário precisa revisar a cidade. Homologação usa coordenadas fictícias. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### reverse — linha 57

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/GeocodingController.java:57)

```java
@GetMapping("/reverse") public JsonNode reverse(@RequestParam double latitude, @RequestParam double longitude)
```

Executa a etapa `reverse` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Double.isFinite`; `UriComponentsBuilder.fromPath`; `lookup`.

**Rejeições locais:**
- `throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "As coordenadas precisam estar dentro do Brasil.");`

**Decisões condicionais examinadas:**
- `(!Double.isFinite(latitude) || latitude < -34 || latitude > 6 || !Double.isFinite(longitude) || longitude < -74 || longitude > -32)`

**Saída declarada:**
- `return lookup(uri);`

**Decisão de engenharia:** Busca e geocodificação. Provedores podem falhar, não conceder CEP ou retornar uma região ambígua; o usuário precisa revisar a cidade. Homologação usa coordenadas fictícias. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### lookup — linha 74

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/GeocodingController.java:74)

```java
private JsonNode lookup(URI uri)
```

Executa a etapa `lookup` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `uri.toString`; `cache.get`; `System.currentTimeMillis`; `nominatim.get`; `cache.put`; `cache.size`; `cache.remove`; `cache.keySet`.

**Rejeições locais:**
- `throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "A busca no mapa está ocupada. Tente novamente em instantes.");`
- `throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "O serviço de mapas não retornou dados.");`
- `throw exception;`
- `throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "O serviço de mapas está indisponível. Tente novamente ou escolha uma cidade cadastrada.");`

**Decisões condicionais examinadas:**
- `(cached != null && cached.expiresAt > System.currentTimeMillis())`
- `(waitMillis > 0)`
- `(body == null)`

**Saída declarada:**
- `return cached.body;`
- `return body;`

**Decisão de engenharia:** Busca e geocodificação. Provedores podem falhar, não conceder CEP ou retornar uma região ambígua; o usuário precisa revisar a cidade. Homologação usa coordenadas fictícias. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java

Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação.

Fonte: [AccountNotification.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java) · SHA-256 `69777abe09b9a60dc56c0b32c1b41036120c0fef86e1cd32b932855fa9036305`

### AccountNotification — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:43)

```java
protected AccountNotification()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### AccountNotification — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:45)

```java
public AccountNotification(Account account, String title, String message, String kind)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### AccountNotification — linha 52

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:52)

```java
public AccountNotification(Account account, String title, String message, String kind, NotificationTarget targetType, UUID targetId)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 59

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:59)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getTitle — linha 60

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:60)

```java
public String getTitle()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return title;`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getMessage — linha 61

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:61)

```java
public String getMessage()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return message;`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getKind — linha 62

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:62)

```java
public String getKind()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return kind;`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 63

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:63)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getReadAt — linha 64

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:64)

```java
public Instant getReadAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return readAt;`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getTargetType — linha 65

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:65)

```java
public NotificationTarget getTargetType()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return targetType;`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getTargetId — linha 66

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:66)

```java
public UUID getTargetId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return targetId;`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### markRead — linha 67

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/AccountNotification.java:67)

```java
public void markRead()
```

Executa a etapa `mark Read` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Decisões condicionais examinadas:**
- `(readAt == null)`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java

Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação.

Fonte: [NotificationController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java) · SHA-256 `f42580adfbcc8f4381bd2745665a1b68a42caa0c9b76af2360e8cefbac4fb926`

### NotificationController — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java:15)

```java
public NotificationController(NotificationService service)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### list — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java:17)

```java
@GetMapping public List<AccountNotification> list(Authentication auth)
```

Executa a etapa `list` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `service.list`; `subject`.

**Saída declarada:**
- `return service.list(subject(auth));`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### read — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java:20)

```java
@PatchMapping("/{id}/read") public AccountNotification read(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `read` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `service.markRead`; `subject`.

**Saída declarada:**
- `return service.markRead(subject(auth), id);`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### clear — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java:25)

```java
@DeleteMapping @ResponseStatus(HttpStatus.NO_CONTENT) public void clear(Authentication auth)
```

Executa a etapa `clear` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `service.clear`; `subject`.

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java:29)

```java
private UUID subject(Authentication auth)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `auth.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject());`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/notifications/NotificationRepository.java

Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação.

Fonte: [NotificationRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationRepository.java) · SHA-256 `e9bd3c43ebbba29676a52cb7e72ee881d34058c50387f321019a91e50affa9f8`

### findTop100ByAccountIdOrderByCreatedAtDesc — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationRepository.java:9)

```java
List<AccountNotification> findTop100ByAccountIdOrderByCreatedAtDesc(UUID accountId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByIdAndAccountId — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationRepository.java:10)

```java
Optional<AccountNotification> findByIdAndAccountId(UUID id, UUID accountId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deleteByAccountId — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationRepository.java:11)

```java
long deleteByAccountId(UUID accountId);
```

Executa a etapa `delete By Account Id` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByAccountIdAndTitleAndReadAtIsNull — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationRepository.java:12)

```java
boolean existsByAccountIdAndTitleAndReadAtIsNull(UUID accountId, String title);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java

Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação.

Fonte: [NotificationService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java) · SHA-256 `edb192ac4b8e7343e1edcd850fbed0ce702f1dd808271d2c1760f30005a3ddf6`

### NotificationService — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java:15)

```java
public NotificationService(NotificationRepository notifications, AccountRepository accounts)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### send — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java:20)

```java
@Transactional public void send(Account recipient, String title, String message, String kind)
```

Executa a etapa `send` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notifications.save`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
notifications.save(new AccountNotification(recipient, title, message, kind));
```

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sendLinked — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java:25)

```java
@Transactional public void sendLinked(Account recipient, String title, String message, String kind, NotificationTarget targetType, UUID targetId)
```

Executa a etapa `send Linked` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notifications.save`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
notifications.save(new AccountNotification(recipient, title, message, kind, targetType, targetId));
```

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sendUnreadOnce — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java:32)

```java
@Transactional public void sendUnreadOnce(Account recipient, String title, String message, String kind)
```

Executa a etapa `send Unread Once` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notifications.existsByAccountIdAndTitleAndReadAtIsNull`; `recipient.getId`; `notifications.save`.

**Decisões condicionais examinadas:**
- `(notifications.existsByAccountIdAndTitleAndReadAtIsNull(recipient.getId(), title))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
notifications.save(new AccountNotification(recipient, title, message, kind));
```

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sendUnreadOnceLinked — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java:38)

```java
@Transactional public void sendUnreadOnceLinked(Account recipient, String title, String message, String kind, NotificationTarget targetType, UUID targetId)
```

Executa a etapa `send Unread Once Linked` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notifications.existsByAccountIdAndTitleAndReadAtIsNull`; `recipient.getId`; `notifications.save`.

**Decisões condicionais examinadas:**
- `(notifications.existsByAccountIdAndTitleAndReadAtIsNull(recipient.getId(), title))`

**Saída declarada:**
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
notifications.save(new AccountNotification(recipient, title, message, kind, targetType, targetId));
```

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### list — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java:45)

```java
@Transactional(readOnly = true) public List<AccountNotification> list(UUID accountId)
```

Executa a etapa `list` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notifications.findTop100ByAccountIdOrderByCreatedAtDesc`.

**Saída declarada:**
- `return notifications.findTop100ByAccountIdOrderByCreatedAtDesc(accountId);`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### markRead — linha 50

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java:50)

```java
@Transactional public AccountNotification markRead(UUID accountId, UUID notificationId)
```

Executa a etapa `mark Read` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notifications.findByIdAndAccountId`; `notification.markRead`.

**Saída declarada:**
- `return notification;`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### clear — linha 58

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java:58)

```java
@Transactional public void clear(UUID accountId)
```

Executa a etapa `clear` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notifications.deleteByAccountId`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
notifications.deleteByAccountId(accountId);
```

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### account — linha 63

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationService.java:63)

```java
@Transactional(readOnly = true) public Account account(UUID id)
```

Executa a etapa `account` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`.

**Saída declarada:**
- `return accounts.findById(id).orElseThrow();`

**Decisão de engenharia:** Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/notifications/NotificationTarget.java

Avisos privados. A conta destinatária limita leitura e alterações; destinos internos tipados relacionam o aviso à operação.

Fonte: [NotificationTarget.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationTarget.java) · SHA-256 `31973cdb55ad6c60dadcb2cab839cf57ff2d9ef288d6dcc13490f799cba48309`

**Contrato de dados/estados:**
```java
public enum NotificationTarget
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

Sem função explícita: contrato declarativo, enum ou inicializador. Os campos e as anotações da fonte são o contrato deste arquivo.

## Backend/src/main/java/br/com/fooddelivery/orders/FulfillmentType.java

Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência.

Fonte: [FulfillmentType.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/FulfillmentType.java) · SHA-256 `1338249d31fca82866374f858327c6f33ef46dcdbc87c3f0bae7b01076e8655e`

**Contrato de dados/estados:**
```java
public enum FulfillmentType
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

Sem função explícita: contrato declarativo, enum ou inicializador. Os campos e as anotações da fonte são o contrato deste arquivo.

## Backend/src/main/java/br/com/fooddelivery/orders/Order.java

Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência.

Fonte: [Order.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java) · SHA-256 `072b5abdc8f6640707b476db5c9b94ae47a4b1e7a41ee2f990ee2b333b146c33`

### Order — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:29)

```java
protected Order()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### Order — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:30)

```java
public Order(Account customer, Store store, FulfillmentType type, String address, long subtotal,long deliveryFee, String customerNote)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `normalizeNote`; `Math.addExact`.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### normalizeNote — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:31)

```java
private static String normalizeNote(String note)
```

Executa a etapa `normalize Note` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `note.isBlank`; `note.trim`; `value.length`.

**Rejeições locais:**
- `throw new IllegalArgumentException("A observação pode ter até 500 caracteres.");`

**Decisões condicionais examinadas:**
- `(note==null||note.isBlank())`
- `(value.length()>500)`

**Saída declarada:**
- `return null;`
- `return value;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### addLine — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:32)

```java
public void addLine(UUID itemId,String name,int quantity,long unitPrice)
```

Executa a etapa `add Line` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `lines.add`.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:33)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCustomer — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:33)

```java
public Account getCustomer()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return customer;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStore — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:33)

```java
public Store getStore()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return store;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStatus — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:33)

```java
public OrderStatus getStatus()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return status;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getFulfillmentType — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:34)

```java
public FulfillmentType getFulfillmentType()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return fulfillmentType;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getDeliveryAddress — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:34)

```java
public String getDeliveryAddress()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return deliveryAddress;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getTotalCents — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:34)

```java
public long getTotalCents()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return totalCents;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getSubtotalCents — linha 35

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:35)

```java
public long getSubtotalCents()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return subtotalCents;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getDeliveryFeeCents — linha 35

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:35)

```java
public long getDeliveryFeeCents()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return deliveryFeeCents;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCurrency — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:36)

```java
public String getCurrency()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return currency;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:36)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getLines — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:36)

```java
public List<OrderLine> getLines()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return lines;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCustomerNote — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:37)

```java
public String getCustomerNote()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return customerNote;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCancellationRequestedAt — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:37)

```java
public Instant getCancellationRequestedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return cancellationRequestedAt;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCancellationRequestStatus — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:37)

```java
public String getCancellationRequestStatus()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return cancellationRequestStatus;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCancellationRequestNote — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:37)

```java
public String getCancellationRequestNote()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return cancellationRequestNote;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestCancellation — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:38)

```java
public void requestCancellation(String note)
```

Executa a etapa `request Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `java.util.Set.of`; `"REQUESTED".equals`; `Instant.now`; `note.isBlank`; `note.trim`; `cancellationRequestNote.length`.

**Rejeições locais:**
- `throw new IllegalStateException("Este pedido não pode receber uma solicitação de cancelamento agora.");`
- `throw new IllegalArgumentException("O motivo pode ter até 500 caracteres.");`

**Decisões condicionais examinadas:**
- `(!java.util.Set.of(OrderStatus.AWAITING_ACCEPTANCE,OrderStatus.ACCEPTED,OrderStatus.PREPARING,OrderStatus.READY).contains(status))`
- `("REQUESTED".equals(cancellationRequestStatus))`
- `note==null||note.isBlank()`
- `(cancellationRequestNote!=null&&cancellationRequestNote.length()>500)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### decideCancellation — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:39)

```java
public void decideCancellation(boolean accepted)
```

Executa a etapa `decide Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"REQUESTED".equals`.

**Rejeições locais:**
- `throw new IllegalStateException("Não há solicitação de cancelamento pendente.");`

**Decisões condicionais examinadas:**
- `(!"REQUESTED".equals(cancellationRequestStatus))`
- `accepted`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### paid — linha 40

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:40)

```java
public void paid()
```

Executa a etapa `paid` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalStateException("Pedido não aguarda pagamento.");`

**Decisões condicionais examinadas:**
- `(status!=OrderStatus.AWAITING_PAYMENT)`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancelUnpaid — linha 41

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:41)

```java
public void cancelUnpaid()
```

Executa a etapa `cancel Unpaid` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalStateException("Pedido não aguarda pagamento.");`

**Decisões condicionais examinadas:**
- `(status!=OrderStatus.AWAITING_PAYMENT)`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### transition — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:42)

```java
public void transition(OrderStatus next)
```

Executa a etapa `transition` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"REQUESTED".equals`.

**Rejeições locais:**
- `throw new IllegalStateException("Responda à solicitação de cancelamento antes de avançar o pedido.");`
- `throw new IllegalStateException("Transição inválida: "+status+" → "+next);`

**Decisões condicionais examinadas:**
- `("REQUESTED".equals(cancellationRequestStatus)&&next!=OrderStatus.CANCELLED)`
- `(!valid)`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### OrderLine — linha 46

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:46)

```java
protected OrderLine()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### OrderLine — linha 46

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:46)

```java
OrderLine(UUID id,String name,int quantity,long price)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getItemId — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:47)

```java
public UUID getItemId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return itemId;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getName — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:47)

```java
public String getName()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return name;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getQuantity — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:47)

```java
public int getQuantity()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return quantity;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getUnitPriceCents — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/Order.java:47)

```java
public long getUnitPriceCents()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return unitPriceCents;`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java

Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência.

Fonte: [OrderController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java) · SHA-256 `7e5045629233346a285676369bd403ec261b012c68fe9a5497d7107075e25894`

**Contrato de dados/estados:**
```java
public record ItemLine(@NotNull UUID itemId, @Min(1) @Max(50) int quantity)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CreateOrder(@NotNull UUID storeId, @NotNull FulfillmentType fulfillmentType,
                              @jakarta.validation.constraints.Size(max=400) String deliveryAddress, @jakarta.validation.constraints.Size(max=500) String customerNote, @NotEmpty @jakarta.validation.constraints.Size(max=100) List<@Valid ItemLine> items)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record StatusChange(@NotNull OrderStatus status)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CancellationRequest(@jakarta.validation.constraints.Size(max=500) String note)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CancellationDecision(@NotNull Boolean accepted)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CheckoutResponse(OrderView order, UUID paymentId, PaymentStatus paymentStatus)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record OrderView(UUID id, StoreView store, OrderStatus status, FulfillmentType fulfillmentType,
                            String deliveryAddress, String customerNote, long totalCents, long subtotalCents, long deliveryFeeCents,
                            String currency, Instant createdAt, List<OrderLineView> lines, UUID paymentId,
                            PaymentStatus paymentStatus,Instant cancellationRequestedAt,String cancellationRequestStatus,String cancellationRequestNote,TrackingView tracking)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record TrackingView(UUID deliveryId, String status, Double latitude, Double longitude, Instant locationUpdatedAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record StoreView(UUID id, String name, String description, String address, String category,
                            boolean active, boolean approved, long deliveryFeeCents)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record OrderLineView(UUID itemId, String name, int quantity, long unitPriceCents)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### OrderController — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:38)

```java
public OrderController(OrderService service, OrderRepository orders, PaymentRepository payments, DeliveryRepository deliveries)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### create — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:45)

```java
@PostMapping("/orders") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('CUSTOMER')") @Transactional public CheckoutResponse create(@Valid @RequestBody CreateOrder request, Authentication auth)
```

Executa a etapa `create` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `service.create`; `subject`; `request.storeId`; `request.fulfillmentType`; `request.deliveryAddress`; `request.customerNote`; `request.items`; `item.itemId`; `item.quantity`; `payments.findByOrderId`; `order.getId`; `view`; `payment.getId`; `payment.getStatus`.

**Saída declarada:**
- `return new CheckoutResponse(view(order, payment, false), payment.getId(), payment.getStatus());`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mine — linha 57

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:57)

```java
@GetMapping("/orders/mine") @PreAuthorize("hasRole('CUSTOMER')") @Transactional(readOnly = true) public List<OrderView> mine(Authentication auth)
```

Executa a etapa `mine` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `service.customerOrders`; `subject`; `payments.findByOrderId`; `order.getId`; `view`.

**Saída declarada:**
- `return service.customerOrders(subject(auth)).stream().map(order -> { Payment payment = payments.findByOrderId(order.getId()).orElse(null); return view(order, payment, true); }).toList();`
- `return view(order, payment, true);`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### merchantOrders — linha 67

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:67)

```java
@GetMapping("/merchant/orders") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly = true) public List<OrderView> merchantOrders(Authentication auth)
```

Executa a etapa `merchant Orders` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.findByStoreOwnerIdOrderByCreatedAtDesc`; `subject`; `order.getStatus`; `view`.

**Saída declarada:**
- `return orders.findByStoreOwnerIdOrderByCreatedAtDesc(subject(auth)).stream() .filter(order -> order.getStatus() != OrderStatus.AWAITING_PAYMENT) .map(order -> view(order, null, false)).toList();`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancelUnpaid — linha 76

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:76)

```java
@PostMapping("/orders/{id}/cancel") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public OrderView cancelUnpaid(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `cancel Unpaid` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `service.cancelUnpaidByCustomer`; `subject`; `view`; `payments.findByOrderId`.

**Saída declarada:**
- `return view(order, payments.findByOrderId(id).orElse(null), false);`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestCancellation — linha 84

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:84)

```java
@PostMapping("/orders/{id}/cancellation-request") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public OrderView requestCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationRequest request,Authentication auth)
```

Executa a etapa `request Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `service.requestCustomerCancellation`; `subject`; `request.note`; `view`; `payments.findByOrderId`.

**Saída declarada:**
- `return view(order,payments.findByOrderId(id).orElse(null),false);`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### decideCancellation — linha 92

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:92)

```java
@PatchMapping("/merchant/orders/{id}/cancellation-request") @PreAuthorize("hasRole('MERCHANT')") @Transactional public OrderView decideCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationDecision request,Authentication auth)
```

Executa a etapa `decide Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `service.decideCustomerCancellation`; `subject`; `request.accepted`; `view`; `payments.findByOrderId`.

**Saída declarada:**
- `return view(order,payments.findByOrderId(id).orElse(null),false);`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### status — linha 100

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:100)

```java
@PatchMapping("/merchant/orders/{id}/status") @PreAuthorize("hasRole('MERCHANT')") @Transactional public OrderView status(@PathVariable UUID id, @Valid @RequestBody StatusChange request, Authentication auth)
```

Executa a etapa `status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `service.merchantTransition`; `subject`; `request.status`; `view`; `payments.findByOrderId`.

**Saída declarada:**
- `return view(order, payments.findByOrderId(id).orElse(null), false);`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### view — linha 108

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:108)

```java
private OrderView view(Order order, Payment payment, boolean includeTracking)
```

Executa a etapa `view` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `order.getStore`; `store.getId`; `store.getName`; `store.getDescription`; `store.getAddress`; `store.getCategory`; `store.isActive`; `store.isApproved`; `store.getDeliveryFeeCents`; `order.getLines`; `line.getItemId`; `line.getName`; `line.getQuantity`; `line.getUnitPriceCents`; `order.getId`; `order.getStatus`; `order.getFulfillmentType`; `order.getDeliveryAddress`; `order.getCustomerNote`; `order.getTotalCents`; `order.getSubtotalCents`; `order.getDeliveryFeeCents`; `order.getCurrency`; `order.getCreatedAt`; `payment.getId`; `payment.getStatus`; `order.getCancellationRequestedAt`; `order.getCancellationRequestStatus`; `order.getCancellationRequestNote`; `deliveries.findByOrderId`; `d.getId`; `d.getStatus`; `d.getCourierLatitude`; `d.getCourierLongitude`; `d.getLocationUpdatedAt`.

**Decisões condicionais examinadas:**
- `payment == null`
- `includeTracking`

**Saída declarada:**
- `return new OrderView(order.getId(), storeView, order.getStatus(), order.getFulfillmentType(), order.getDeliveryAddress(), order.getCustomerNote(), order.getTotalCents(), order.getSubtotalCents(), order.getDeliveryFeeCents(), order.getCurrency(), order.getCreatedAt(), lines, payment == null ? null : payment.getId(), payment == null ? null : payment.getStatus(),order.getCancellationRequestedAt(),order.getCancellationRequestStatus(),order.getCancellationRequestNote(), includeTracking ? deliveries.findByOrderId(order.getId()).map(d -> new TrackingView(d.getId(), d.getStatus().name(), d.getCourierLatitude(), d.getCourierLongitude(), d.getLocationUpdatedAt())).orElse(null) : null);`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 123

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:123)

```java
private UUID subject(Authentication auth)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `auth.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject());`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/orders/OrderRepository.java

Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência.

Fonte: [OrderRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderRepository.java) · SHA-256 `27412b8f3ccb328700126513590b2c004dc5184f14453e7f0a8699a90b4936ef`

### findByCustomerIdOrderByCreatedAtDesc — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderRepository.java:6)

```java
List<Order> findByCustomerIdOrderByCreatedAtDesc(UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStoreOwnerIdOrderByCreatedAtDesc — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderRepository.java:6)

```java
List<Order> findByStoreOwnerIdOrderByCreatedAtDesc(UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStatusAndCreatedAtBefore — linha 6

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderRepository.java:6)

```java
List<Order> findByStatusAndCreatedAtBefore(OrderStatus status,java.time.Instant before);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByIdForUpdate — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderRepository.java:7)

```java
@org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE) @Query("select o from Order o where o.id=:id") Optional<Order> findByIdForUpdate(@Param("id") UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### completedByCategorySince — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderRepository.java:10)

```java
@Query("select o.store.category, count(o) from Order o where o.status = br.com.fooddelivery.orders.OrderStatus.COMPLETED and o.createdAt >= :since and o.store.active = true and o.store.approved = true and (:city is null or lower(o.store.city) = lower(:city)) and (:state is null or lower(o.store.state) = lower(:state)) group by o.store.category") List<Object[]> completedByCategorySince(@Param("since") java.time.Instant since, @Param("city") String city, @Param("state") String state);
```

Executa a etapa `completed By Category Since` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/orders/OrderService.java

Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência.

Fonte: [OrderService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderService.java) · SHA-256 `2831c07a282325c3b13e30ba803ca593abd45d329878b7355921a935656f8013`

**Contrato de dados/estados:**
```java
public record RequestedLine(UUID itemId,int quantity)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### OrderService — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderService.java:18)

```java
public OrderService(OrderRepository orders,AccountRepository accounts,StoreRepository stores,CatalogItemRepository items,PaymentRepository payments,NotificationService notifications,StoreOperatingHours operatingHours,br.com.fooddelivery.delivery.DeliveryRepository deliveries)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### create — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderService.java:19)

```java
@Transactional public Order create(UUID customerId,UUID storeId,FulfillmentType type,String address,String customerNote,List<RequestedLine> requested)
```

Executa a etapa `create` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`; `Cpf.isValid`; `customer.getCpf`; `customer.getPhone`; `address.isBlank`; `stores.findByIdForUpdate`; `store.requireFulfillment`; `operatingHours.requireOpenNow`; `requested.isEmpty`; `line.quantity`; `items.findById`; `line.itemId`; `item.getStore`; `item.isServiceItem`; `item.reserve`; `selected.add`; `Math.addExact`; `Math.multiplyExact`; `item.getPriceCents`; `store.getDeliveryFeeCents`; `requested.size`; `selected.get`; `order.addLine`; `item.getId`; `item.getName`; `requested.get`; `orders.save`; `payments.save`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Cadastre um CPF válido no seu perfil antes de comprar.");`
- `throw new IllegalArgumentException("Complete telefone e endereço de entrega antes de pedir.");`
- `throw new IllegalArgumentException("O pedido precisa ter ao menos um item.");`
- `throw new IllegalArgumentException("Quantidade inválida.");`
- `throw new IllegalArgumentException("Item não pertence à loja.");`
- `throw new IllegalArgumentException("Este serviço deve ser reservado, não adicionado a um pedido de entrega ou retirada.");`

**Decisões condicionais examinadas:**
- `(!Cpf.isValid(customer.getCpf()))`
- `(customer.getPhone()==null||customer.getPhone().isBlank()||(type==FulfillmentType.DELIVERY&&(address==null||address.isBlank())))`
- `(requested==null||requested.isEmpty())`
- `(line.quantity()<1||line.quantity()>50)`
- `(!item.getStore().getId().equals(storeId))`
- `(item.isServiceItem())`
- `type==FulfillmentType.DELIVERY`

**Saída declarada:**
- `return saved;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
Order saved=orders.save(order);payments.save(new Payment(saved));return saved;
```

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### customerOrders — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderService.java:33)

```java
@Transactional(readOnly=true) public List<Order> customerOrders(UUID id)
```

Executa a etapa `customer Orders` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.findByCustomerIdOrderByCreatedAtDesc`.

**Saída declarada:**
- `return orders.findByCustomerIdOrderByCreatedAtDesc(id);`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### merchantTransition — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderService.java:34)

```java
@Transactional public Order merchantTransition(UUID id,UUID merchantId,OrderStatus status)
```

Executa a etapa `merchant Transition` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.findByIdForUpdate`; `order.getStore`; `cancelDelivery`; `order.getId`; `order.getFulfillmentType`; `order.transition`; `order.getLines`; `items.findById`; `line.getItemId`; `item.restore`; `line.getQuantity`; `items.save`; `payments.findByOrderId`; `p.getStatus`; `payment.requestFullRefund`; `notifications.send`; `order.getCustomer`; `notifications.sendLinked`; `status.descriptionPtBr`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Pedido de outra loja.");`
- `throw new IllegalStateException("A conclusão de um delivery é confirmada pelo entregador ao marcar a entrega.");`

**Decisões condicionais examinadas:**
- `(!order.getStore().getOwner().getId().equals(merchantId))`
- `(status==OrderStatus.CANCELLED||status==OrderStatus.REJECTED)`
- `(status==OrderStatus.COMPLETED&&order.getFulfillmentType()==FulfillmentType.DELIVERY)`

**Saída declarada:**
- `return order;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
order.transition(status);
for(Order.OrderLine line:order.getLines())items.findById(line.getItemId()).ifPresent(item->{item.restore(line.getQuantity());items.save(item);});
payment.requestFullRefund();
notifications.send(order.getCustomer(),"Estorno solicitado","A loja encerrou o pedido. Solicitamos o estorno pelo Mercado Pago; o status será atualizado quando o provedor confirmar.","info");
notifications.sendLinked(order.getCustomer(),"Pedido atualizado",order.getStore().getName()+": seu pedido agora está “"+status.descriptionPtBr()+"”.","info",NotificationTarget.ORDER,order.getId());
```

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancelUnpaidByCustomer — linha 50

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderService.java:50)

```java
@Transactional public Order cancelUnpaidByCustomer(UUID id,UUID customerId)
```

Executa a etapa `cancel Unpaid By Customer` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.findByIdForUpdate`; `order.getCustomer`; `payments.findByOrderId`; `order.getStatus`; `payment.getStatus`; `order.cancelUnpaid`; `order.getLines`; `items.findById`; `line.getItemId`; `item.restore`; `line.getQuantity`; `items.save`; `payment.cancelPendingCheckout`; `notifications.send`; `order.getStore`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Pedido de outra conta.");`
- `throw new IllegalStateException("O pedido já foi enviado à loja e não pode ser cancelado por este fluxo.");`
- `throw new IllegalStateException("O pagamento foi aprovado. Fale com a loja para solicitar o cancelamento e o estorno.");`

**Decisões condicionais examinadas:**
- `(!order.getCustomer().getId().equals(customerId))`
- `(order.getStatus()==OrderStatus.CANCELLED||order.getStatus()==OrderStatus.REJECTED)`
- `(order.getStatus()!=OrderStatus.AWAITING_PAYMENT)`
- `(payment!=null&&payment.getStatus()==PaymentStatus.PAID)`
- `(payment!=null)`

**Saída declarada:**
- `return order;`
- `return order;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
for(Order.OrderLine line:order.getLines())items.findById(line.getItemId()).ifPresent(item->{item.restore(line.getQuantity());items.save(item);});
notifications.send(order.getStore().getOwner(),"Pedido cancelado pelo cliente","Um pedido ainda não pago foi cancelado e o estoque foi liberado.","info");
```

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestCustomerCancellation — linha 63

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderService.java:63)

```java
@Transactional public Order requestCustomerCancellation(UUID id,UUID customerId,String note)
```

Executa a etapa `request Customer Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.findByIdForUpdate`; `order.getCustomer`; `order.getStatus`; `cancelUnpaidByCustomer`; `payments.findByOrderId`; `payment.getStatus`; `deliveries.findByOrderId`; `"REQUESTED".equals`; `order.getCancellationRequestStatus`; `order.requestCancellation`; `notifications.sendLinked`; `order.getStore`; `order.getId`; `orders.save`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Pedido de outra conta.");`
- `throw new IllegalStateException("Somente pedidos com pagamento aprovado podem solicitar cancelamento por este fluxo.");`

**Decisões condicionais examinadas:**
- `(!order.getCustomer().getId().equals(customerId))`
- `(order.getStatus()==OrderStatus.AWAITING_PAYMENT)`
- `(payment.getStatus()!=PaymentStatus.PAID)`
- `(!alreadyRequested)`

**Saída declarada:**
- `return cancelUnpaidByCustomer(id,customerId);`
- `return orders.save(order);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
if(!alreadyRequested)notifications.sendLinked(order.getStore().getOwner(),"Pedido com cancelamento solicitado","O cliente pediu o cancelamento do pedido "+order.getId().toString().substring(0,8)+". Revise a solicitação na aba Pedidos.","info",NotificationTarget.ORDER,order.getId());
return orders.save(order);
```

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### decideCustomerCancellation — linha 75

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderService.java:75)

```java
@Transactional public Order decideCustomerCancellation(UUID id,UUID merchantId,boolean accepted)
```

Executa a etapa `decide Customer Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `orders.findByIdForUpdate`; `order.getStore`; `order.decideCancellation`; `cancelDelivery`; `order.transition`; `order.getLines`; `items.findById`; `line.getItemId`; `item.restore`; `line.getQuantity`; `items.save`; `payments.findByOrderId`; `p.getStatus`; `notifications.sendLinked`; `order.getCustomer`; `order.getId`; `orders.save`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Pedido de outra loja.");`

**Decisões condicionais examinadas:**
- `(!order.getStore().getOwner().getId().equals(merchantId))`
- `(accepted)`
- `accepted`

**Saída declarada:**
- `return orders.save(order);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
order.transition(OrderStatus.CANCELLED);
for(Order.OrderLine line:order.getLines())items.findById(line.getItemId()).ifPresent(item->{item.restore(line.getQuantity());items.save(item);});
notifications.sendLinked(order.getCustomer(),accepted?"Cancelamento aceito":"Cancelamento recusado",accepted?"A loja aceitou o cancelamento. O estorno foi solicitado ao Mercado Pago e será atualizado após a confirmação do provedor.":"A loja recusou a solicitação de cancelamento. Fale com ela pelo chat se precisar de ajuda.",accepted?"info":"warn",NotificationTarget.ORDER,order.getId());
return orders.save(order);
```

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancelDelivery — linha 89

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderService.java:89)

```java
private void cancelDelivery(UUID orderId)
```

Executa a etapa `cancel Delivery` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `deliveries.findByOrderId`; `task.cancel`; `deliveries.save`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
private void cancelDelivery(UUID orderId){deliveries.findByOrderId(orderId).ifPresent(task->{task.cancel();deliveries.save(task);});}
```

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/orders/OrderStatus.java

Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência.

Fonte: [OrderStatus.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderStatus.java) · SHA-256 `0921db69057dc9e73908b7fcb52708f6fd303e83c11b172bc2e5b81d51eac5e8`

**Contrato de dados/estados:**
```java
public enum OrderStatus
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### descriptionPtBr — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderStatus.java:4)

```java
public String descriptionPtBr()
```

Executa a etapa `description Pt Br` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return switch(this) { case AWAITING_PAYMENT -> "aguardando pagamento"; case AWAITING_ACCEPTANCE -> "aguardando a loja"; case ACCEPTED -> "aceito pela loja"; case PREPARING -> "em preparação"; case READY -> "pronto"; case COMPLETED -> "concluído"; case REJECTED -> "recusado"; case CANCELLED -> "cancelado"; };`

**Decisão de engenharia:** Pedidos e estoque. A transação precisa manter pedido, pagamento e estoque coerentes; estados e bloqueios impedem operações fora de sequência. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/DevelopmentPaymentController.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [DevelopmentPaymentController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/DevelopmentPaymentController.java) · SHA-256 `663a08bc82c3f91d1684426775685b09f6d742f8ef6be81ec66e430a7a417c7d`

### DevelopmentPaymentController — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/DevelopmentPaymentController.java:13)

```java
public DevelopmentPaymentController(PaymentService service,PaymentRepository payments)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### get — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/DevelopmentPaymentController.java:14)

```java
@GetMapping("/{id}") @PreAuthorize("hasRole('CUSTOMER')") public Payment get(@PathVariable UUID id,Authentication auth)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `payments.findById`; `p.getOrder`; ``; `auth.getPrincipal`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Pagamento de outra conta.");`

**Decisões condicionais examinadas:**
- `(!p.getOrder().getCustomer().getId().toString().equals(((Jwt)auth.getPrincipal()).getSubject()))`

**Saída declarada:**
- `return p;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### approve — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/DevelopmentPaymentController.java:15)

```java
@PostMapping("/{id}/approve") @PreAuthorize("hasRole('CUSTOMER')") public Payment approve(@PathVariable UUID id,Authentication auth)
```

Executa a etapa `approve` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payments.findById`; `p.getOrder`; ``; `auth.getPrincipal`; `service.confirmForDevelopment`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Pagamento de outra conta.");`

**Decisões condicionais examinadas:**
- `(!p.getOrder().getCustomer().getId().toString().equals(((Jwt)auth.getPrincipal()).getSubject()))`

**Saída declarada:**
- `return service.confirmForDevelopment(id);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [MarketplaceAccountService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java) · SHA-256 `973d258559c9f162a06fe594f7504edaa5c47dd4bc8c7bf2bdcd0de8eed6aa5f`

**Contrato de dados/estados:**
```java
public record ConnectionStatus(boolean connected,String marketplaceAccountId,Instant expiresAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### MarketplaceAccountService — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:31)

```java
public MarketplaceAccountService(StorePaymentAccountRepository links, StoreRepository stores, MercadoPagoProperties properties, MarketplaceTokenCipher cipher, MarketplaceTokenRefreshService tokenRefresh, @Qualifier("mercadoPagoRestClient") RestClient http)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### authorizationUrl — linha 40

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:40)

```java
@Transactional public String authorizationUrl(UUID storeId, UUID ownerId)
```

Executa a etapa `authorization Url` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requireOAuth`; `stores.findById`; `s.getOwner`; `links.findByStoreId`; `randomToken`; `Base64.getUrlEncoder`; `sha256`; `verifier.getBytes`; `link.beginAuthorization`; `sha256Hex`; `cipher.encrypt`; `links.save`; `UriComponentsBuilder.fromUriString`; `properties.clientId`; `properties.redirectUri`.

**Saída declarada:**
- `return UriComponentsBuilder.fromUriString("https://auth.mercadopago.com/authorization") .queryParam("client_id",properties.clientId()).queryParam("response_type","code") .queryParam("platform_id","mp").queryParam("state",state) .queryParam("redirect_uri",properties.redirectUri()).queryParam("code_challenge",challenge) .queryParam("code_challenge_method","S256").build().encode().toUriString();`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
link.beginAuthorization(sha256Hex(state),cipher.encrypt(verifier));links.save(link);
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### completeAuthorization — linha 56

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:56)

```java
@Transactional public void completeAuthorization(String code,String state)
```

Executa a etapa `complete Authorization` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requireOAuth`; `links.findByStateHashAndStateExpiresAtAfter`; `sha256Hex`; `Instant.now`; `cipher.decrypt`; `link.codeVerifierEncrypted`; `Map.of`; `properties.clientId`; `properties.clientSecret`; `properties.redirectUri`; `Boolean.toString`; `properties.sandbox`; `post`; `link.completeAuthorization`; `text`; `cipher.encrypt`; `number`; `links.save`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
links.save(link);
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### accessTokenForStore — linha 71

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:71)

```java
@Transactional public String accessTokenForStore(UUID storeId)
```

Executa a etapa `access Token For Store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `properties.checkoutConfigured`; `links.findByStoreId`; `link.accessTokenEncrypted`; `link.getTokenExpiresAt`; `Instant.now`; `tokenRefresh.refreshIfNeeded`; `cipher.decrypt`.

**Rejeições locais:**
- `throw new IllegalStateException("A integração do Mercado Pago ainda não foi configurada pelo administrador.");`
- `throw new IllegalStateException("Esta loja ainda não conectou sua conta Mercado Pago.");`

**Decisões condicionais examinadas:**
- `(!properties.checkoutConfigured())`
- `(link.accessTokenEncrypted()==null)`
- `(link.getTokenExpiresAt()==null||!link.getTokenExpiresAt().isAfter(Instant.now().plusSeconds(300)))`

**Saída declarada:**
- `return cipher.decrypt(encryptedAccessToken);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### status — linha 83

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:83)

```java
@Transactional(readOnly=true) public ConnectionStatus status(UUID storeId,UUID ownerId)
```

Executa a etapa `status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `stores.findById`; `s.getOwner`; `links.findByStoreId`; `l.accessTokenEncrypted`; `l.getMpUserId`; `l.getTokenExpiresAt`.

**Saída declarada:**
- `return links.findByStoreId(storeId).filter(l->l.accessTokenEncrypted()!=null) .map(l->new ConnectionStatus(true,l.getMpUserId(),l.getTokenExpiresAt())) .orElseGet(()->new ConnectionStatus(false,null,null));`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### post — linha 92

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:92)

```java
private Map<String,Object> post(String path,String bearer,Map<String,?> body,String error)
```

Executa a etapa `post` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `http.post`; `request.header`; `request.body`.

**Rejeições locais:**
- `throw new IllegalStateException(error+" Confira as credenciais e a conexão e tente novamente.");`

**Decisões condicionais examinadas:**
- `(bearer!=null)`

**Saída declarada:**
- `return request.body(body).retrieve().body(new ParameterizedTypeReference<>(){});`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requireOAuth — linha 100

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:100)

```java
private void requireOAuth()
```

Executa a etapa `require OAuth` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `properties.oauthConfigured`.

**Rejeições locais:**
- `throw new IllegalStateException("O administrador precisa configurar MP_CLIENT_ID e MP_CLIENT_SECRET no arquivo de ambiente.");`

**Decisões condicionais examinadas:**
- `(!properties.oauthConfigured())`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### randomToken — linha 101

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:101)

```java
private String randomToken(int size)
```

Executa a etapa `random Token` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `random.nextBytes`; `Base64.getUrlEncoder`.

**Saída declarada:**
- `return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sha256 — linha 102

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:102)

```java
private static byte[] sha256(byte[] bytes)
```

Executa a etapa `sha256` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `MessageDigest.getInstance`.

**Rejeições locais:**
- `throw new IllegalStateException(e);`

**Saída declarada:**
- `return MessageDigest.getInstance("SHA-256").digest(bytes);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sha256Hex — linha 103

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:103)

```java
private static String sha256Hex(String value)
```

Executa a etapa `sha256 Hex` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `java.util.HexFormat.of`; `sha256`; `value.getBytes`.

**Saída declarada:**
- `return java.util.HexFormat.of().formatHex(sha256(value.getBytes(StandardCharsets.UTF_8)));`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### text — linha 104

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:104)

```java
private static String text(Map<String,Object> body,String key)
```

Executa a etapa `text` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `body.get`; `value.toString`.

**Rejeições locais:**
- `throw new IllegalStateException("O Mercado Pago não retornou os dados esperados da autorização.");`

**Decisões condicionais examinadas:**
- `body==null`
- `(value==null||value.toString().isBlank())`

**Saída declarada:**
- `return value.toString();`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### number — linha 105

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceAccountService.java:105)

```java
private static long number(Map<String,Object> body,String key,long fallback)
```

Executa a etapa `number` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `body.get`; `n.longValue`.

**Decisões condicionais examinadas:**
- `body==null`
- `value instanceof Number n`

**Saída declarada:**
- `return value instanceof Number n?n.longValue():fallback;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenCipher.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [MarketplaceTokenCipher.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenCipher.java) · SHA-256 `a7d76bd91b58360ffd13a3b276bc40dc84e6014934eb5208618c3beb90d74726`

### MarketplaceTokenCipher — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenCipher.java:16)

```java
public MarketplaceTokenCipher(MercadoPagoProperties properties)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### encrypt — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenCipher.java:18)

```java
public String encrypt(String value)
```

Executa a etapa `encrypt` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `random.nextBytes`; `value.getBytes`; `Cipher.getInstance`; `cipher.init`; `key`; `Base64.getEncoder`; `ByteBuffer.allocate`; `cipher.getOutputSize`; `cipher.doFinal`.

**Rejeições locais:**
- `throw new IllegalStateException("Não foi possível proteger as credenciais da loja.", e);`

**Saída declarada:**
- `return Base64.getEncoder().encodeToString(ByteBuffer.allocate(nonce.length + cipher.getOutputSize(plaintext.length)) .put(nonce).put(cipher.doFinal(plaintext)).array());`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### decrypt — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenCipher.java:29)

```java
public String decrypt(String value)
```

Executa a etapa `decrypt` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Base64.getDecoder`; `ByteBuffer.wrap`; `buffer.get`; `buffer.remaining`; `Cipher.getInstance`; `cipher.init`; `key`; `cipher.doFinal`.

**Rejeições locais:**
- `throw new IllegalStateException("Não foi possível acessar com segurança a conta de pagamentos desta loja.", e);`

**Saída declarada:**
- `return new String(cipher.doFinal(ciphertext),java.nio.charset.StandardCharsets.UTF_8);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### key — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenCipher.java:38)

```java
private SecretKeySpec key()
```

Executa a etapa `key` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `properties.tokenEncryptionKey`; `encoded.isBlank`; `Base64.getDecoder`.

**Rejeições locais:**
- `throw new IllegalStateException("Configure MP_TOKEN_ENCRYPTION_KEY antes de conectar contas de pagamento.");`
- `throw new IllegalStateException("MP_TOKEN_ENCRYPTION_KEY deve ser uma chave Base64 de 32 bytes.");`

**Decisões condicionais examinadas:**
- `(encoded==null||encoded.isBlank())`
- `(bytes.length!=32)`

**Saída declarada:**
- `return new SecretKeySpec(bytes,"AES");`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenRefreshService.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [MarketplaceTokenRefreshService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenRefreshService.java) · SHA-256 `5259d4eaa303c3c09260f548c12cca0d93b5b128be8a1cef276c2df1b146f4e8`

### MarketplaceTokenRefreshService — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenRefreshService.java:23)

```java
public MarketplaceTokenRefreshService(StorePaymentAccountRepository links, MercadoPagoProperties properties, MarketplaceTokenCipher cipher, @Qualifier("mercadoPagoRestClient") RestClient http)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refreshIfNeeded — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenRefreshService.java:31)

```java
@Transactional(propagation = Propagation.REQUIRES_NEW) public String refreshIfNeeded(UUID storeId)
```

Executa a etapa `refresh If Needed` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `links.findByStoreIdForUpdate`; `link.accessTokenEncrypted`; `link.getTokenExpiresAt`; `Instant.now`; `link.refreshTokenEncrypted`; `http.post`; `Map.of`; `properties.clientId`; `properties.clientSecret`; `cipher.decrypt`; `link.refresh`; `text`; `cipher.encrypt`; `number`; `links.save`.

**Rejeições locais:**
- `throw new IllegalStateException("Esta loja ainda não conectou sua conta Mercado Pago.");`
- `throw new IllegalStateException("A conexão Mercado Pago expirou. A loja precisa autorizar novamente.");`
- `throw new IllegalStateException("Não foi possível renovar a conexão Mercado Pago. Tente novamente; se persistir, a loja precisará autorizar novamente.");`

**Decisões condicionais examinadas:**
- `(link.accessTokenEncrypted() == null)`
- `(link.getTokenExpiresAt() != null && link.getTokenExpiresAt().isAfter(Instant.now().plusSeconds(REFRESH_EARLY_SECONDS)))`
- `(link.refreshTokenEncrypted() == null)`

**Saída declarada:**
- `return link.accessTokenEncrypted();`
- `return link.accessTokenEncrypted();`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
links.save(link);
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### text — linha 62

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenRefreshService.java:62)

```java
private static String text(Map<String, Object> body, String key)
```

Executa a etapa `text` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `body.get`; `value.toString`.

**Rejeições locais:**
- `throw new IllegalStateException("O Mercado Pago não retornou os dados esperados da renovação.");`

**Decisões condicionais examinadas:**
- `body == null`
- `(value == null || value.toString().isBlank())`

**Saída declarada:**
- `return value.toString();`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### number — linha 70

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MarketplaceTokenRefreshService.java:70)

```java
private static long number(Map<String, Object> body, String key, long fallback)
```

Executa a etapa `number` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `body.get`; `number.longValue`.

**Decisões condicionais examinadas:**
- `body == null`
- `value instanceof Number number`

**Saída declarada:**
- `return value instanceof Number number ? number.longValue() : fallback;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [MercadoPagoAccountController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java) · SHA-256 `aab5b7a1dc409325a1ad1dd59b8a632da3f90a86c48b571bb327e3c2f4ba3fbb`

### MercadoPagoAccountController — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java:16)

```java
public MercadoPagoAccountController(MarketplaceAccountService service,MercadoPagoProperties properties)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### connect — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java:18)

```java
@PostMapping("/merchant/stores/{storeId}/mercadopago/connect") @PreAuthorize("hasRole('MERCHANT')") public Map<String,String> connect(@PathVariable UUID storeId,Authentication auth)
```

Executa a etapa `connect` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Map.of`; `service.authorizationUrl`; `subject`.

**Saída declarada:**
- `return Map.of("authorizationUrl",service.authorizationUrl(storeId,subject(auth)));`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### status — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java:24)

```java
@GetMapping("/merchant/stores/{storeId}/mercadopago/status") @PreAuthorize("hasRole('MERCHANT')") public MarketplaceAccountService.ConnectionStatus status(@PathVariable UUID storeId,Authentication auth)
```

Executa a etapa `status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `service.status`; `subject`.

**Saída declarada:**
- `return service.status(storeId,subject(auth));`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### callback — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java:28)

```java
@GetMapping("/payments/mercadopago/callback") public RedirectView callback(@RequestParam(required=false) String code,@RequestParam(required=false) String state, @RequestParam(required=false) String error)
```

Executa a etapa `callback` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `properties.frontendUrl`; `service.completeAuthorization`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Autorização cancelada.");`

**Decisões condicionais examinadas:**
- `(error!=null||code==null||state==null)`

**Saída declarada:**
- `return new RedirectView(destination+"connected#merchant",true);`
- `return new RedirectView(destination+"error#merchant",true);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 41

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java:41)

```java
private UUID subject(Authentication auth)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `auth.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt)auth.getPrincipal()).getSubject());`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoCheckoutService.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [MercadoPagoCheckoutService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoCheckoutService.java) · SHA-256 `e2a16cb5e640d1304016f9f28366965fc9c25a4663cb59d7dac605d9c7039790`

**Contrato de dados/estados:**
```java
public record CheckoutView(UUID paymentId,String checkoutUrl,PaymentStatus status)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### MercadoPagoCheckoutService — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoCheckoutService.java:33)

```java
public MercadoPagoCheckoutService(PaymentRepository payments,MarketplaceAccountService marketplaceAccounts,MercadoPagoProperties properties,Environment environment,PaymentService paymentService,@Qualifier("mercadoPagoRestClient") RestClient http,StoreOperatingHours operatingHours)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### create — linha 35

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoCheckoutService.java:35)

```java
@Transactional public CheckoutView create(UUID paymentId,UUID customerId)
```

Executa a etapa `create` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `properties.checkoutConfigured`; `Arrays.asList`; `environment.getActiveProfiles`; `properties.hasNotificationUrl`; `properties.webhookConfigured`; `properties.notificationBaseUrl`; `payments.findById`; `payment.getOrder`; `payment.getReservation`; `order.getCustomer`; `reservation.getCustomer`; `order.getStore`; `reservation.getStore`; `customer.getId`; `payment.getStatus`; `store.isAvailable`; `store.requireFulfillment`; `order.getFulfillmentType`; `operatingHours.requireOpenNow`; `store.requireInPerson`; `reservation.isOperatingHoursOverride`; `operatingHours.requireOpenForInterval`; `reservation.getStartsAt`; `reservation.getEndsAt`; `order.getStatus`; `reservation.getStatus`; `order.getCreatedAt`; `reservation.getCreatedAt`; `operationCreatedAt.plus`; `expiresAt.isAfter`; `Instant.now`; `order.getLines`; `items.add`; `item`; `line.getItemId`; `line.getName`; `line.getQuantity`; `line.getUnitPriceCents`; `order.getDeliveryFeeCents`; `order.getId`; `reservation.getCatalogItemId`; `reservation.getServiceName`; `reservation.getAmountCents`; `body.put`; `properties.currency`; `payment.getId`; `BigDecimal.valueOf`; `payment.getAmountCents`; `store.getMarketplaceFeeBps`; `Map.of`; `customer.getEmail`; `customer.getName`; `returnUrl`; `DateTimeFormatter.ISO_OFFSET_DATE_TIME.format`; `expiresAt.atOffset`; `UriComponentsBuilder.fromUriString`; `marketplaceAccounts.accessTokenForStore`; `store.getId`; `http.post`; `Objects.toString`; `response.get`; `properties.sandbox`; `response.getOrDefault`; `preferenceId.isBlank`; `checkoutUrl.isBlank`; `payment.checkoutCreated`; `payments.save`; `stateError.getMessage`.

**Rejeições locais:**
- `throw new IllegalStateException("O Mercado Pago ainda não foi configurado. Fale com a equipe da plataforma.");`
- `throw new IllegalStateException("Pagamentos em produção exigem um endereço HTTPS público e a assinatura de webhook do Mercado Pago.");`
- `throw new IllegalStateException("Não há pedido ou reserva associado ao pagamento.");`
- `throw new org.springframework.security.access.AccessDeniedException("Este pagamento pertence a outra conta.");`
- `throw new IllegalStateException("Este pagamento não está disponível para um novo checkout.");`
- `throw new IllegalStateException("A loja não está disponível para receber pagamentos.");`
- `throw new IllegalStateException("O pedido não está mais aguardando pagamento.");`
- `throw new IllegalStateException("A reserva não está mais aguardando pagamento.");`
- `throw new IllegalStateException("O prazo de pagamento terminou. Crie um novo pedido ou escolha outro horário.");`
- `throw new IllegalStateException();`
- `throw new IllegalStateException();`
- `throw stateError;`
- `throw new IllegalStateException("Não conseguimos abrir o pagamento agora. Confira a conexão e tente novamente.");`

**Decisões condicionais examinadas:**
- `(!properties.checkoutConfigured())`
- `(Arrays.asList(environment.getActiveProfiles()).contains("prod")&&(!properties.hasNotificationUrl()||!properties.webhookConfigured()||!properties.notificationBaseUrl().startsWith("https://")))`
- `(order==null&&reservation==null)`
- `order!=null`
- `(!customer.getId().equals(customerId))`
- `(payment.getStatus()!=PaymentStatus.PENDING&&payment.getStatus()!=PaymentStatus.FAILED)`
- `(!store.isAvailable())`
- `(order!=null)`
- `(reservation!=null)`
- `(!reservation.isOperatingHoursOverride())`
- `(order!=null&&order.getStatus()!=br.com.fooddelivery.orders.OrderStatus.AWAITING_PAYMENT)`
- `(reservation!=null&&reservation.getStatus()!=br.com.fooddelivery.reservations.ReservationStatus.PENDING_PAYMENT)`
- `(!expiresAt.isAfter(Instant.now()))`
- `(order.getDeliveryFeeCents()>0)`
- `reservation!=null`
- `(properties.hasNotificationUrl())`
- `(response==null)`
- `properties.sandbox()`
- `(preferenceId.isBlank()||checkoutUrl.isBlank())`
- `(error instanceof IllegalStateException stateError&&stateError.getMessage()!=null&&stateError.getMessage().startsWith("A loja"))`

**Saída declarada:**
- `return new CheckoutView(paymentId,checkoutUrl,payment.getStatus());`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
payment.checkoutCreated(preferenceId,checkoutUrl);payments.save(payment);
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### returnUrl — linha 89

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoCheckoutService.java:89)

```java
private String returnUrl(String result,Payment payment,String page)
```

Executa a etapa `return Url` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UriComponentsBuilder.fromUriString`; `properties.frontendUrl`; `payment.getId`.

**Saída declarada:**
- `return UriComponentsBuilder.fromUriString(properties.frontendUrl()) .queryParam("mp",result).queryParam("app_payment_id",payment.getId()).fragment(page).build().encode().toUriString();`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### syncFromProvider — linha 92

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoCheckoutService.java:92)

```java
@Transactional public PaymentController.PaymentView syncFromProvider(UUID paymentId,UUID customerId,String providerPaymentId)
```

Executa a etapa `sync From Provider` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `providerPaymentId.matches`; `payments.findById`; `payment.getOrder`; `payment.getReservation`; `order.getCustomer`; `reservation.getCustomer`; `customer.getId`; `payment.getStatus`; `payment.getProviderPreferenceId`; `order.getStore`; `reservation.getStore`; `marketplaceAccounts.accessTokenForStore`; `store.getId`; `http.get`; `paymentId.toString`; `Objects.toString`; `response.get`; `amount.movePointRight`; `payment.getAmountCents`; `properties.currency`; `"approved".equals`; `paymentService.confirmProvider`; `"rejected".equals`; `"cancelled".equals`; `paymentService.failProvider`; `refreshed.getId`; `refreshed.getAmountCents`; `refreshed.getStatus`; `refreshed.getOrder`; `refreshed.getReservation`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Não recebemos um identificador válido do Mercado Pago.");`
- `throw new org.springframework.security.access.AccessDeniedException("Este pagamento pertence a outra conta.");`
- `throw new IllegalStateException("Não foi possível consultar o Mercado Pago. Tente novamente em instantes.");`
- `throw new org.springframework.security.access.AccessDeniedException("A transação não corresponde a este pagamento.");`
- `throw new IllegalStateException("O valor da transação não corresponde ao total esperado.");`

**Decisões condicionais examinadas:**
- `(providerPaymentId==null||!providerPaymentId.matches("[0-9]{1,30}"))`
- `order!=null`
- `(!customer.getId().equals(customerId))`
- `(payment.getStatus()!=PaymentStatus.PAID&&payment.getProviderPreferenceId()!=null)`
- `(response==null||!paymentId.toString().equals(Objects.toString(response.get("external_reference"),"")))`
- `(amount.movePointRight(2).longValueExact()!=payment.getAmountCents()||!properties.currency().equals(response.get("currency_id")))`
- `("approved".equals(status))`
- `("rejected".equals(status)||"cancelled".equals(status))`
- `refreshed.getOrder()==null`
- `refreshed.getReservation()==null`

**Saída declarada:**
- `return new PaymentController.PaymentView(refreshed.getId(),refreshed.getAmountCents(),refreshed.getStatus(),refreshed.getOrder()==null?null:refreshed.getOrder().getId(),refreshed.getReservation()==null?null:refreshed.getReservation().getId());`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### item — linha 113

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoCheckoutService.java:113)

```java
private Map<String,Object> item(String id,String title,int quantity,long unitPriceCents)
```

Executa a etapa `item` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Map.of`; `properties.currency`; `BigDecimal.valueOf`.

**Saída declarada:**
- `return Map.of("id",id,"title",title,"quantity",quantity,"currency_id",properties.currency(),"unit_price",BigDecimal.valueOf(unitPriceCents,2));`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoConfiguration.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [MercadoPagoConfiguration.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoConfiguration.java) · SHA-256 `0a3490491056afbe3e78f0c421fa3129c8fa5018c031ffb20e19cf7e199db7e8`

### mercadoPagoRestClient — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoConfiguration.java:14)

```java
@Bean("mercadoPagoRestClient") RestClient mercadoPagoRestClient()
```

Executa a etapa `mercado Pago Rest Client` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `HttpClient.newBuilder`; `Duration.ofSeconds`; `requestFactory.setReadTimeout`; `RestClient.builder`.

**Saída declarada:**
- `return RestClient.builder().requestFactory(requestFactory).build();`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
requestFactory.setReadTimeout(Duration.ofSeconds(15));
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoProperties.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [MercadoPagoProperties.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoProperties.java) · SHA-256 `35fa9c584d8f1f49acbb31aa91e1c0c16a449451ade0dce1b419f1f544aca124`

**Contrato de dados/estados:**
```java
@ConfigurationProperties(prefix="app.payment.mercadopago")
public record MercadoPagoProperties(String clientId, String clientSecret, String redirectUri,
        String frontendUrl, String tokenEncryptionKey, String webhookSecret,
        String notificationBaseUrl, boolean sandbox, boolean testWebhookSignature, String currency)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### oauthConfigured — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoProperties.java:9)

```java
public boolean oauthConfigured()
```

Executa a etapa `oauth Configured` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `present`.

**Saída declarada:**
- `return present(clientId) && present(clientSecret) && present(redirectUri);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### checkoutConfigured — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoProperties.java:10)

```java
public boolean checkoutConfigured()
```

Executa a etapa `checkout Configured` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `oauthConfigured`; `present`.

**Saída declarada:**
- `return oauthConfigured() && present(tokenEncryptionKey);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### webhookConfigured — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoProperties.java:11)

```java
public boolean webhookConfigured()
```

Executa a etapa `webhook Configured` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `present`.

**Saída declarada:**
- `return present(webhookSecret);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### hasNotificationUrl — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoProperties.java:12)

```java
public boolean hasNotificationUrl()
```

Executa a etapa `has Notification Url` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `present`.

**Saída declarada:**
- `return present(notificationBaseUrl);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### present — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoProperties.java:13)

```java
private static boolean present(String value)
```

Executa a etapa `present` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `value.isBlank`.

**Saída declarada:**
- `return value != null && !value.isBlank();`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoRefundService.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [MercadoPagoRefundService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoRefundService.java) · SHA-256 `f28f342243fc52eed5112fcfc719ad6a7ca165bf3bf4fddeb34bec9f27a74feb`

### MercadoPagoRefundService — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoRefundService.java:30)

```java
public MercadoPagoRefundService(PaymentRepository payments, MarketplaceAccountService marketplaceAccounts, NotificationService notifications)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createHttpClient — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoRefundService.java:37)

```java
private static RestClient createHttpClient()
```

Executa a etapa `create Http Client` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `HttpClient.newBuilder`; `Duration.ofSeconds`; `factory.setReadTimeout`; `RestClient.builder`.

**Saída declarada:**
- `return RestClient.builder().requestFactory(factory).build();`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
factory.setReadTimeout(Duration.ofSeconds(15));
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### attempt — linha 44

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoRefundService.java:44)

```java
@Transactional public void attempt(UUID paymentId)
```

Executa a etapa `attempt` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payments.findById`; `payment.beginRefundAttempt`; `demo.enabled`; `payment.refundSucceeded`; `payment.getProviderReference`; `payment.refundNeedsReview`; `payment.getOrder`; `payment.getReservation`; `order.getStore`; `reservation.getStore`; `marketplaceAccounts.accessTokenForStore`; `store.getId`; `http.post`; `payment.getRefundIdempotencyKey`; `Map.of`; `Objects.toString`; `refund.get`; `providerRefundId.isBlank`; `payment.refundAttemptFailed`; `"approved".equals`; `notifyRefundComplete`; `store.getName`; `"pending".equals`; `"in_process".equals`; `payment.refundAccepted`; `notifications.send`; `paymentCustomer`; `error.getStatusCode`; `ambiguousFailure`.

**Decisões condicionais examinadas:**
- `(payment == null || !payment.beginRefundAttempt())`
- `(demo != null && demo.enabled())`
- `(payment.getProviderReference() == null || !payment.getProviderReference().matches("[0-9]{1,30}"))`
- `(order == null && reservation == null)`
- `order != null`
- `refund == null`
- `(providerRefundId.isBlank())`
- `("approved".equals(status))`
- `("pending".equals(status) || "in_process".equals(status))`
- `(error.getStatusCode().is4xxClientError())`

**Saída declarada:**
- `return;`
- `return;`
- `return;`
- `return;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
notifications.send(paymentCustomer(payment), "Estorno em processamento",
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### providerConfirmedRefund — linha 97

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoRefundService.java:97)

```java
@Transactional public void providerConfirmedRefund(String providerPaymentId)
```

Executa a etapa `provider Confirmed Refund` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payments.findByProviderReference`; `payment.getStatus`; `payment.refundSucceeded`; `payment.getRefundProviderReference`; `payment.getOrder`; `payment.getReservation`; `notifyRefundComplete`; `store.getName`.

**Decisões condicionais examinadas:**
- `(payment == null || payment.getStatus() == PaymentStatus.REFUNDED)`
- `(payment.getStatus() != PaymentStatus.REFUND_PENDING && payment.getStatus() != PaymentStatus.REFUND_REVIEW)`
- `payment.getOrder() != null`

**Saída declarada:**
- `return;`
- `return;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### ambiguousFailure — linha 107

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoRefundService.java:107)

```java
private void ambiguousFailure(Payment payment)
```

Executa a etapa `ambiguous Failure` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payment.getRefundAttempts`; `payment.refundNeedsReview`; `payment.refundAttemptFailed`.

**Decisões condicionais examinadas:**
- `(payment.getRefundAttempts() >= MAX_AMBIGUOUS_ATTEMPTS)`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### notifyRefundComplete — linha 115

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoRefundService.java:115)

```java
private void notifyRefundComplete(Payment payment, String storeName)
```

Executa a etapa `notify Refund Complete` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `notifications.send`; `paymentCustomer`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
notifications.send(paymentCustomer(payment), "Estorno confirmado",
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### paymentCustomer — linha 120

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoRefundService.java:120)

```java
private br.com.fooddelivery.identity.Account paymentCustomer(Payment payment)
```

Executa a etapa `payment Customer` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payment.getOrder`; `payment.getReservation`.

**Decisões condicionais examinadas:**
- `payment.getOrder() != null`

**Saída declarada:**
- `return payment.getOrder() != null ? payment.getOrder().getCustomer() : payment.getReservation().getCustomer();`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoWebhookController.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [MercadoPagoWebhookController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoWebhookController.java) · SHA-256 `2fe97e695e0eeec984cda7f1a1a13f3747b6694468aa70cf00f95cf10cea2dfc`

### MercadoPagoWebhookController — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoWebhookController.java:29)

```java
public MercadoPagoWebhookController(PaymentRepository payments,PaymentService paymentService,MercadoPagoRefundService refundService,MarketplaceAccountService marketplaceAccounts,MercadoPagoProperties properties,@Qualifier("mercadoPagoRestClient") RestClient http)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### receive — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoWebhookController.java:31)

```java
@PostMapping @ResponseStatus(HttpStatus.OK) public Map<String,String> receive(@RequestParam(name="data.id",required=false) String dataId, @RequestParam(required=false) String type,@RequestParam(name="payment_id") UUID internalPaymentId, @RequestHeader(name="x-signature",required=false) String signature, @RequestHeader(name="x-request-id",required=false) String requestId)
```

Executa a etapa `receive` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"payment".equals`; `dataId.isBlank`; `Map.of`; `validSignature`; `payments.findWithOperationById`; `payment.getOrder`; `payment.getReservation`; `order.getStore`; `reservation.getStore`; `marketplaceAccounts.accessTokenForStore`; `store.getId`; `http.get`; `internalPaymentId.toString`; `String.valueOf`; `mpPayment.get`; `amount.movePointRight`; `payment.getAmountCents`; `"BRL".equals`; `"approved".equals`; `paymentService.confirmProvider`; `"refunded".equals`; `refundService.providerConfirmedRefund`; `"rejected".equals`; `"cancelled".equals`; `paymentService.failProvider`.

**Rejeições locais:**
- `throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Assinatura de webhook inválida.");`
- `throw new ResponseStatusException(HttpStatus.BAD_GATEWAY,"Não foi possível validar a notificação com o Mercado Pago.");`
- `throw new ResponseStatusException(HttpStatus.CONFLICT,"O pagamento recebido não corresponde ao registro local.");`
- `throw new ResponseStatusException(HttpStatus.CONFLICT,"O valor ou a moeda do pagamento não corresponde ao pedido.");`

**Decisões condicionais examinadas:**
- `(!"payment".equals(type)||dataId==null||dataId.isBlank())`
- `(!validSignature(dataId,requestId,signature))`
- `order!=null`
- `(mpPayment==null||!internalPaymentId.toString().equals(String.valueOf(mpPayment.get("external_reference"))))`
- `(amount.movePointRight(2).longValueExact()!=payment.getAmountCents()||!"BRL".equals(currency))`
- `("approved".equals(status))`
- `("refunded".equals(status))`
- `("rejected".equals(status)||"cancelled".equals(status))`

**Saída declarada:**
- `return Map.of("status","ignored");`
- `return Map.of("status","processed");`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### validSignature — linha 57

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoWebhookController.java:57)

```java
private boolean validSignature(String dataId,String requestId,String signature)
```

Executa a etapa `valid Signature` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `properties.testWebhookSignature`; `properties.sandbox`; `properties.webhookConfigured`; `signature.split`; `part.trim`; `pair[0].equals`; `dataId.toLowerCase`; `Mac.getInstance`; `mac.init`; `properties.webhookSecret`; `java.util.HexFormat.of`; `mac.doFinal`; `manifest.getBytes`; `MessageDigest.isEqual`; `expected.getBytes`; `provided.toLowerCase`.

**Decisões condicionais examinadas:**
- `(properties.testWebhookSignature()&&properties.sandbox())`
- `(!properties.webhookConfigured()||signature==null||requestId==null)`
- `(pair.length!=2)`
- `(pair[0].equals("ts"))`
- `(pair[0].equals("v1"))`
- `(timestamp==null||provided==null)`

**Saída declarada:**
- `return true;`
- `return false;`
- `return false;`
- `return MessageDigest.isEqual(expected.getBytes(StandardCharsets.US_ASCII),provided.toLowerCase(java.util.Locale.ROOT).getBytes(StandardCharsets.US_ASCII));`
- `return false;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/Payment.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [Payment.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java) · SHA-256 `9b3e20f0a79b68cc2afab360548f9eb7d10d9fe782a5903d3f0a98986dafcc3f`

### Payment — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:26)

```java
protected Payment()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### Payment — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:27)

```java
public Payment(Order order)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `order.getTotalCents`.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### Payment — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:28)

```java
public Payment(Reservation reservation)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `reservation.getAmountCents`.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:29)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAmountCents — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:29)

```java
public long getAmountCents()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return amountCents;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStatus — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:29)

```java
public PaymentStatus getStatus()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return status;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getOrder — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:29)

```java
public Order getOrder()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return order;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getReservation — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:29)

```java
public Reservation getReservation()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return reservation;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getProviderPreferenceId — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:30)

```java
public String getProviderPreferenceId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return providerPreferenceId;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCheckoutUrl — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:30)

```java
public String getCheckoutUrl()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return checkoutUrl;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getProviderReference — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:31)

```java
public String getProviderReference()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return providerReference;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getRefundProviderReference — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:31)

```java
public String getRefundProviderReference()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return refundProviderReference;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getRefundAttempts — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:32)

```java
public int getRefundAttempts()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return refundAttempts;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getLastRefundAttemptAt — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:32)

```java
public Instant getLastRefundAttemptAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return lastRefundAttemptAt;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getRefundLastError — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:32)

```java
public String getRefundLastError()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return refundLastError;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### checkoutCreated — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:33)

```java
public void checkoutCreated(String preferenceId,String url)
```

Executa a etapa `checkout Created` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalStateException("Este pagamento não pode abrir um novo checkout.");`

**Decisões condicionais examinadas:**
- `(status!=PaymentStatus.PENDING&&status!=PaymentStatus.FAILED)`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### confirm — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:34)

```java
public void confirm(String reference)
```

Executa a etapa `confirm` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `(status==PaymentStatus.PAID||status==PaymentStatus.REFUND_PENDING||status==PaymentStatus.REFUNDED||status==PaymentStatus.REFUND_REVIEW)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### fail — linha 35

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:35)

```java
public void fail(String reference)
```

Executa a etapa `fail` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `(status==PaymentStatus.PAID||status==PaymentStatus.REFUND_PENDING||status==PaymentStatus.REFUNDED||status==PaymentStatus.REFUND_REVIEW)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancelPendingCheckout — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:36)

```java
public void cancelPendingCheckout()
```

Executa a etapa `cancel Pending Checkout` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalStateException("O pagamento não pode ser cancelado neste estado.");`

**Decisões condicionais examinadas:**
- `(status==PaymentStatus.PENDING)`
- `(status!=PaymentStatus.FAILED)`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestFullRefund — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:37)

```java
public void requestFullRefund()
```

Executa a etapa `request Full Refund` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.randomUUID`.

**Rejeições locais:**
- `throw new IllegalStateException("Somente pagamentos aprovados podem ser reembolsados.");`

**Decisões condicionais examinadas:**
- `(status==PaymentStatus.REFUND_PENDING||status==PaymentStatus.REFUNDED)`
- `(status!=PaymentStatus.PAID)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getRefundIdempotencyKey — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:42)

```java
public UUID getRefundIdempotencyKey()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return refundIdempotencyKey;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### beginRefundAttempt — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:43)

```java
public boolean beginRefundAttempt()
```

Executa a etapa `begin Refund Attempt` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Decisões condicionais examinadas:**
- `(status!=PaymentStatus.REFUND_PENDING||refundProviderReference!=null)`

**Saída declarada:**
- `return false;`
- `return true;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refundAccepted — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:47)

```java
public void refundAccepted(String providerRefundId)
```

Executa a etapa `refund Accepted` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refundSucceeded — linha 48

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:48)

```java
public void refundSucceeded(String providerRefundId)
```

Executa a etapa `refund Succeeded` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refundNeedsReview — linha 49

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:49)

```java
public void refundNeedsReview(String safeReason)
```

Executa a etapa `refund Needs Review` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refundAttemptFailed — linha 50

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/Payment.java:50)

```java
public void refundAttemptFailed(String safeReason)
```

Executa a etapa `refund Attempt Failed` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [PaymentController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java) · SHA-256 `a9ecf1e01d0dee8e7c5388b296f20533596133a12e9a3a470991bc7a608a47f4`

**Contrato de dados/estados:**
```java
public record PaymentView(UUID id,long amountCents,PaymentStatus status,UUID orderId,UUID reservationId)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record SyncRequest(String providerPaymentId)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### PaymentController — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java:15)

```java
public PaymentController(PaymentRepository payments,MercadoPagoCheckoutService checkout)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### createCheckout — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java:17)

```java
@PostMapping("/{id}/checkout") public MercadoPagoCheckoutService.CheckoutView createCheckout(@PathVariable UUID id,Authentication auth)
```

Executa a etapa `create Checkout` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `checkout.create`; `subject`.

**Saída declarada:**
- `return checkout.create(id,subject(auth));`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### get — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java:22)

```java
@GetMapping("/{id}") @org.springframework.transaction.annotation.Transactional(readOnly=true) public PaymentView get(@PathVariable UUID id,Authentication auth)
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `payments.findById`; `payment.getOrder`; `payment.getReservation`; `customerId.equals`; `subject`; `payment.getId`; `payment.getAmountCents`; `payment.getStatus`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Este pagamento pertence a outra conta.");`

**Decisões condicionais examinadas:**
- `payment.getOrder()!=null`
- `(!customerId.equals(subject(auth)))`
- `payment.getOrder()==null`
- `payment.getReservation()==null`

**Saída declarada:**
- `return new PaymentView(payment.getId(),payment.getAmountCents(),payment.getStatus(),payment.getOrder()==null?null:payment.getOrder().getId(),payment.getReservation()==null?null:payment.getReservation().getId());`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sync — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java:31)

```java
@PostMapping("/{id}/sync") public PaymentView sync(@PathVariable UUID id,@RequestBody SyncRequest request,Authentication auth)
```

Executa a etapa `sync` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `checkout.syncFromProvider`; `subject`; `request.providerPaymentId`.

**Saída declarada:**
- `return checkout.syncFromProvider(id,subject(auth),request.providerPaymentId());`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java:34)

```java
private UUID subject(Authentication auth)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `auth.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt)auth.getPrincipal()).getSubject());`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/PaymentRepository.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [PaymentRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentRepository.java) · SHA-256 `1d7b5ef3d49a31d3a306b05ee73aabdc8f60670c24d0a0d83656a703864f77e0`

### findByOrderId — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentRepository.java:7)

```java
Optional<Payment> findByOrderId(UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByReservationId — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentRepository.java:8)

```java
Optional<Payment> findByReservationId(UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByProviderReference — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentRepository.java:9)

```java
Optional<Payment> findByProviderReference(String reference);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findWithOperationById — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentRepository.java:10)

```java
@Query("select p from Payment p left join fetch p.order left join fetch p.order.store left join fetch p.reservation left join fetch p.reservation.store where p.id=:id") Optional<Payment> findWithOperationById(@Param("id") UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStatusInOrderByLastRefundAttemptAtAsc — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentRepository.java:12)

```java
List<Payment> findByStatusInOrderByLastRefundAttemptAtAsc(Collection<PaymentStatus> statuses);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStatusOrderByLastRefundAttemptAtAsc — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentRepository.java:13)

```java
List<Payment> findByStatusOrderByLastRefundAttemptAtAsc(PaymentStatus status);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStatusAndRefundProviderReferenceIsNullOrderByLastRefundAttemptAtAsc — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentRepository.java:14)

```java
List<Payment> findByStatusAndRefundProviderReferenceIsNullOrderByLastRefundAttemptAtAsc(PaymentStatus status);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/PaymentService.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [PaymentService.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentService.java) · SHA-256 `0e0524d0a9538108a505087584e569da56ecb56ac3704c79893e1de66552122c`

### PaymentService — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentService.java:15)

```java
public PaymentService(PaymentRepository payments,OrderRepository orders,NotificationService notifications)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### confirmForDevelopment — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentService.java:16)

```java
@Transactional public Payment confirmForDevelopment(UUID paymentId)
```

Executa a etapa `confirm For Development` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `confirmProvider`.

**Saída declarada:**
- `return confirmProvider(paymentId,"local-simulator-"+paymentId);`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### confirmProvider — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentService.java:17)

```java
@Transactional public Payment confirmProvider(UUID paymentId,String providerReference)
```

Executa a etapa `confirm Provider` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payments.findById`; `payment.getStatus`; `payment.confirm`; `payment.getOrder`; `orders.findById`; `order.getStatus`; `order.paid`; `notifications.sendLinked`; `order.getCustomer`; `order.getStore`; `order.getId`; `payment.requestFullRefund`; `notifications.send`; `payment.getReservation`; `reservation.getStatus`; `reservation.confirmPayment`; `reservation.getCustomer`; `reservation.getStore`; `reservation.getId`.

**Rejeições locais:**
- `throw new IllegalStateException("Pagamento sem pedido ou reserva associados.");`

**Decisões condicionais examinadas:**
- `(payment.getStatus()==PaymentStatus.PAID||payment.getStatus()==PaymentStatus.REFUND_PENDING||payment.getStatus()==PaymentStatus.REFUNDED||payment.getStatus()==PaymentStatus.REFUND_REVIEW)`
- `(payment.getOrder()!=null)`
- `(order.getStatus()==br.com.fooddelivery.orders.OrderStatus.AWAITING_PAYMENT)`
- `(order.getStatus()==br.com.fooddelivery.orders.OrderStatus.CANCELLED||order.getStatus()==br.com.fooddelivery.orders.OrderStatus.REJECTED)`
- `(reservation==null)`
- `(reservation.getStatus()==br.com.fooddelivery.reservations.ReservationStatus.PENDING_PAYMENT)`
- `(reservation.getStatus()==br.com.fooddelivery.reservations.ReservationStatus.CANCELLED)`

**Saída declarada:**
- `return payment;`
- `return payment;`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
if(order.getStatus()==br.com.fooddelivery.orders.OrderStatus.AWAITING_PAYMENT){order.paid();notifications.sendLinked(order.getCustomer(),"Pagamento aprovado","Seu pedido para "+order.getStore().getName()+" foi enviado à loja.","success",NotificationTarget.ORDER,order.getId());notifications.sendLinked(order.getStore().getOwner(),"Novo pedido pago","Pagamento confirmado pela plataforma. Um novo pedido aguarda confirmação.","info",NotificationTarget.ORDER,order.getId());}
else if(order.getStatus()==br.com.fooddelivery.orders.OrderStatus.CANCELLED||order.getStatus()==br.com.fooddelivery.orders.OrderStatus.REJECTED){payment.requestFullRefund();notifications.send(order.getCustomer(),"Pagamento aprovado após o cancelamento","O pedido não está mais ativo. Já solicitamos o estorno e avisaremos quando o Mercado Pago atualizar o status.","info");}
if(reservation.getStatus()==br.com.fooddelivery.reservations.ReservationStatus.PENDING_PAYMENT){reservation.confirmPayment();notifications.sendLinked(reservation.getCustomer(),"Reserva confirmada","Seu horário na "+reservation.getStore().getName()+" foi confirmado.","success",NotificationTarget.RESERVATION,reservation.getId());notifications.sendLinked(reservation.getStore().getOwner(),"Nova reserva paga","Pagamento antecipado confirmado pela plataforma. A reserva já está na agenda da loja.","info",NotificationTarget.RESERVATION,reservation.getId());}
else if(reservation.getStatus()==br.com.fooddelivery.reservations.ReservationStatus.CANCELLED){payment.requestFullRefund();notifications.send(reservation.getCustomer(),"Pagamento aprovado após liberar o horário","O horário não está mais ativo. Já solicitamos o estorno e avisaremos quando o Mercado Pago atualizar o status.","info");}
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### failProvider — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentService.java:32)

```java
@Transactional public Payment failProvider(UUID paymentId,String providerReference)
```

Executa a etapa `fail Provider` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payments.findById`; `payment.getStatus`; `payment.fail`.

**Decisões condicionais examinadas:**
- `(payment.getStatus()==PaymentStatus.PAID)`

**Saída declarada:**
- `return payment;`
- `return payment;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/PaymentStatus.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [PaymentStatus.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentStatus.java) · SHA-256 `e2b4939529c34a2dcdc94b891cecc0f5aafb4258c30208f0ff69ffac497a8c9b`

**Contrato de dados/estados:**
```java
public enum PaymentStatus
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

Sem função explícita: contrato declarativo, enum ou inicializador. Os campos e as anotações da fonte são o contrato deste arquivo.

## Backend/src/main/java/br/com/fooddelivery/payments/RefundRetryJob.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [RefundRetryJob.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/RefundRetryJob.java) · SHA-256 `7328034ba0220c06f44935915f3f59f7bbf94f0ff355569678a782e6ba936791`

### RefundRetryJob — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/RefundRetryJob.java:12)

```java
public RefundRetryJob(PaymentRepository payments, MercadoPagoRefundService refunds)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### processPendingRefunds — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/RefundRetryJob.java:17)

```java
@Scheduled(fixedDelay = 30000) public void processPendingRefunds()
```

Executa a etapa `process Pending Refunds` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `payments.findByStatusAndRefundProviderReferenceIsNullOrderByLastRefundAttemptAtAsc`; `pending.stream`.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [StorePaymentAccount.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java) · SHA-256 `e3260ef17bac48e0bf7d50bdc160e5675260a3dfbe9e5acd733c0b651edaed15`

### StorePaymentAccount — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:23)

```java
protected StorePaymentAccount()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### StorePaymentAccount — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:24)

```java
public StorePaymentAccount(Store store)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStore — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:25)

```java
public Store getStore()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return store;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getMpUserId — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:25)

```java
public String getMpUserId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return mpUserId;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getTokenExpiresAt — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:25)

```java
public Instant getTokenExpiresAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return tokenExpiresAt;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isConnected — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:26)

```java
public boolean isConnected()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Colaborações:** `tokenExpiresAt.isAfter`; `Instant.now`.

**Saída declarada:**
- `return accessTokenEncrypted!=null&&tokenExpiresAt!=null&&tokenExpiresAt.isAfter(Instant.now());`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### accessTokenEncrypted — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:27)

```java
String accessTokenEncrypted()
```

Executa a etapa `access Token Encrypted` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return accessTokenEncrypted;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refreshTokenEncrypted — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:27)

```java
String refreshTokenEncrypted()
```

Executa a etapa `refresh Token Encrypted` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return refreshTokenEncrypted;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### stateHash — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:28)

```java
String stateHash()
```

Executa a etapa `state Hash` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return stateHash;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### stateExpiresAt — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:28)

```java
Instant stateExpiresAt()
```

Executa a etapa `state Expires At` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return stateExpiresAt;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### codeVerifierEncrypted — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:28)

```java
String codeVerifierEncrypted()
```

Executa a etapa `code Verifier Encrypted` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return codeVerifierEncrypted;`

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### beginAuthorization — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:29)

```java
void beginAuthorization(String stateHash,String verifierCiphertext)
```

Executa a etapa `begin Authorization` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
void beginAuthorization(String stateHash,String verifierCiphertext){this.stateHash=stateHash;this.stateExpiresAt=Instant.now().plusSeconds(600);this.codeVerifierEncrypted=verifierCiphertext;this.updatedAt=Instant.now();}
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### completeAuthorization — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:30)

```java
void completeAuthorization(String userId,String access,String refresh,long expiresSeconds)
```

Executa a etapa `complete Authorization` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
void completeAuthorization(String userId,String access,String refresh,long expiresSeconds){this.mpUserId=userId;this.accessTokenEncrypted=access;this.refreshTokenEncrypted=refresh;this.tokenExpiresAt=Instant.now().plusSeconds(expiresSeconds);this.stateHash=null;this.stateExpiresAt=null;this.codeVerifierEncrypted=null;this.updatedAt=Instant.now();}
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### refresh — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:31)

```java
void refresh(String access,String refresh,long expiresSeconds)
```

Executa a etapa `refresh` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
void refresh(String access,String refresh,long expiresSeconds){this.accessTokenEncrypted=access;this.refreshTokenEncrypted=refresh;this.tokenExpiresAt=Instant.now().plusSeconds(expiresSeconds);this.updatedAt=Instant.now();}
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### clearAuthorizationState — linha 32

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccount.java:32)

```java
void clearAuthorizationState()
```

Executa a etapa `clear Authorization State` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
void clearAuthorizationState(){this.stateHash=null;this.stateExpiresAt=null;this.codeVerifierEncrypted=null;this.updatedAt=Instant.now();}
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccountRepository.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [StorePaymentAccountRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccountRepository.java) · SHA-256 `6abf4371eddec2ef82b6e03a284ce4c99660da5c1ce192490dd1b78bdc618df0`

### findByStoreId — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccountRepository.java:13)

```java
Optional<StorePaymentAccount> findByStoreId(UUID storeId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStoreIdForUpdate — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccountRepository.java:14)

```java
@Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select link from StorePaymentAccount link where link.store.id=:storeId") Optional<StorePaymentAccount> findByStoreIdForUpdate(@Param("storeId") UUID storeId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStateHashAndStateExpiresAtAfter — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/StorePaymentAccountRepository.java:17)

```java
Optional<StorePaymentAccount> findByStateHashAndStateExpiresAtAfter(String stateHash,Instant now);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/payments/UnpaidCheckoutExpirationJob.java

Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro.

Fonte: [UnpaidCheckoutExpirationJob.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/UnpaidCheckoutExpirationJob.java) · SHA-256 `5b2d530e01b4a024ebc2f62f65551c82e19e5affdfd8ca7c37ae82f4f5727fe9`

### UnpaidCheckoutExpirationJob — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/UnpaidCheckoutExpirationJob.java:25)

```java
public UnpaidCheckoutExpirationJob(OrderRepository orders,ReservationRepository reservations,ReservationSlotRepository slots, PaymentRepository payments,CatalogItemRepository items,NotificationService notifications)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### expireUnpaidOperations — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/UnpaidCheckoutExpirationJob.java:30)

```java
@Scheduled(fixedDelay=60000) @Transactional public void expireUnpaidOperations()
```

Executa a etapa `expire Unpaid Operations` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`; `orders.findByStatusAndCreatedAtBefore`; `payments.findByOrderId`; `order.getId`; `payment.getStatus`; `order.getLines`; `items.findById`; `line.getItemId`; `item.restore`; `line.getQuantity`; `items.save`; `order.cancelUnpaid`; `payment.fail`; `notifications.send`; `order.getCustomer`; `order.getStore`; `reservations.findByStatusAndCreatedAtBefore`; `payments.findByReservationId`; `reservation.getId`; `reservation.expireUnpaid`; `slots.deleteByReservationId`; `reservation.getCustomer`; `reservation.getStore`.

**Decisões condicionais examinadas:**
- `(payment!=null&&payment.getStatus()==PaymentStatus.PAID)`
- `(payment!=null)`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
for(var line:order.getLines())items.findById(line.getItemId()).ifPresent(item->{item.restore(line.getQuantity());items.save(item);});
notifications.send(order.getCustomer(),"Pedido expirado","O tempo para pagamento terminou. O estoque foi liberado; se quiser, faça o pedido novamente.","info");
notifications.send(order.getStore().getOwner(),"Pagamento não concluído","Um pedido expirou sem pagamento e foi cancelado automaticamente.","info");
reservation.expireUnpaid();slots.deleteByReservationId(reservation.getId());
notifications.send(reservation.getCustomer(),"Horário liberado","O tempo para pagamento terminou. Escolha outro horário quando estiver pronto.","info");
notifications.send(reservation.getStore().getOwner(),"Reserva expirada","Uma reserva sem pagamento foi removida da agenda.","info");
```

**Decisão de engenharia:** Pagamentos e estornos. O provedor confirma dinheiro real; navegador não é fonte de verdade. Repetições, eventos atrasados e cancelamentos devem preservar o estado financeiro. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/platform/CityMatcher.java

Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados.

Fonte: [CityMatcher.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/CityMatcher.java) · SHA-256 `5034e4fc6fe0fe0834310df58d74e89d9c17e2b1bee6ddca7cee5f6a46a43458`

### CityMatcher — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/CityMatcher.java:8)

```java
private CityMatcher()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### sameCity — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/CityMatcher.java:10)

```java
public static boolean sameCity(String customerCity, String customerState, String storeCity, String storeState)
```

Executa a etapa `same City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `normalize`.

**Saída declarada:**
- `return normalize(customerCity).equals(normalize(storeCity)) && normalize(customerState).equals(normalize(storeState)) && !normalize(customerCity).isEmpty() && !normalize(customerState).isEmpty();`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### allowPayAfterService — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/CityMatcher.java:17)

```java
public static boolean allowPayAfterService(boolean sellerAllowsIt, String customerCity, String customerState, String storeCity, String storeState)
```

Executa a etapa `allow Pay After Service` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `sameCity`.

**Saída declarada:**
- `return sellerAllowsIt && sameCity(customerCity, customerState, storeCity, storeState);`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### normalize — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/CityMatcher.java:22)

```java
private static String normalize(String value)
```

Executa a etapa `normalize` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Normalizer.normalize`; `value.trim`.

**Decisões condicionais examinadas:**
- `(value == null)`

**Saída declarada:**
- `return "";`
- `return Normalizer.normalize(value.trim(), Normalizer.Form.NFD) .replaceAll("\\p{M}+", "") .toUpperCase(Locale.ROOT);`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java

Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados.

Fonte: [PlatformRequest.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java) · SHA-256 `e6aaa1a143027d33d63188bab2f4646f7e1e77acffd29b809be914b0a7c321ab`

### PlatformRequest — linha 25

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:25)

```java
protected PlatformRequest()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### PlatformRequest — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:26)

```java
private PlatformRequest(Account account,String type,AccountRole role,String city,String state,String postalCode,Store store)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### role — linha 27

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:27)

```java
public static PlatformRequest role(Account a,AccountRole r)
```

Executa a etapa `role` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return new PlatformRequest(a,"ROLE",r,null,null,null,null);`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### city — linha 28

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:28)

```java
public static PlatformRequest city(Account a,String c,String s,String p)
```

Executa a etapa `city` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return new PlatformRequest(a,"CITY",null,c,s,p,null);`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### store — linha 29

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:29)

```java
public static PlatformRequest store(Account a,Store store)
```

Executa a etapa `store` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `store.getCity`; `store.getState`.

**Saída declarada:**
- `return new PlatformRequest(a,"STORE",null,store.getCity(),store.getState(),null,store);`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### review — linha 30

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:30)

```java
public void review(boolean approve,Account reviewer,String note)
```

Executa a etapa `review` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"PENDING".equals`; `Instant.now`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Esta solicitação já foi analisada.");`

**Decisões condicionais examinadas:**
- `(!"PENDING".equals(status))`
- `approve`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAccount — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public Account getAccount()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return account;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getRequestType — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public String getRequestType()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return requestType;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStatus — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public String getStatus()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return status;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getRequestedRole — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public AccountRole getRequestedRole()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return requestedRole;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCity — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public String getCity()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return city;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getState — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public String getState()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return state;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getPostalCode — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public String getPostalCode()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return postalCode;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStore — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public Store getStore()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return store;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getReviewNote — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public String getReviewNote()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return reviewNote;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getReviewedAt — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequest.java:31)

```java
public Instant getReviewedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return reviewedAt;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java

Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados.

Fonte: [PlatformRequestController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java) · SHA-256 `5f10769e29b285ed46d9d84a1b60c1b02daff66c84909105c5a6f2c9ad6964fe`

**Contrato de dados/estados:**
```java
public record RoleRequest(@NotNull AccountRole role)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CityRequest(@NotBlank @Size(max=100) String city,@NotBlank @Size(min=2,max=2) String state,@Pattern(regexp="^$|\\d
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record Review(boolean approved,@Size(max=500) String note)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record RequestView(UUID id,String requestType,String status,AccountRole requestedRole,String city,String state,String postalCode,UUID storeId,String storeName,String storeCnpj,UUID accountId,String accountName,String accountEmail,Instant createdAt,Instant reviewedAt,String reviewNote)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### PlatformRequestController — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:22)

```java
public PlatformRequestController(PlatformRequestRepository requests,AccountRepository accounts,StoreRepository stores,ServiceCityRepository cities,NotificationService notifications)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mine — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:24)

```java
@GetMapping("/account/platform-requests") @Transactional(readOnly=true) public List<RequestView> mine(Authentication auth)
```

Executa a etapa `mine` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requests.findByAccountIdOrderByCreatedAtDesc`; `subject`.

**Saída declarada:**
- `return requests.findByAccountIdOrderByCreatedAtDesc(subject(auth)).stream().map(RequestView::of).toList();`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestRole — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:26)

```java
@PostMapping("/account/platform-requests/roles") @ResponseStatus(HttpStatus.CREATED) @Transactional public RequestView requestRole(@Valid @RequestBody RoleRequest body,Authentication auth)
```

Executa a etapa `request Role` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByIdForUpdate`; `subject`; `account.isActive`; `account.getCpf`; `body.role`; `account.getRegisteredRoles`; `requests.existsByAccountIdAndRequestTypeAndRequestedRoleAndStatus`; `account.getId`; `RequestView.of`; `requests.save`; `PlatformRequest.role`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Uma conta suspensa não pode enviar solicitações.");`
- `throw new IllegalArgumentException("Cadastre um CPF válido no perfil antes de solicitar outro acesso.");`
- `throw new IllegalArgumentException("Escolha vendedor ou entregador.");`
- `throw new IllegalArgumentException("Este perfil já está vinculado à sua conta. Se estiver suspenso, fale com o administrador.");`
- `throw new IllegalArgumentException("Já existe uma solicitação deste perfil em análise.");`

**Decisões condicionais examinadas:**
- `(!account.isActive())`
- `(account.getCpf()==null)`
- `(requested!=AccountRole.MERCHANT&&requested!=AccountRole.COURIER)`
- `(account.getRegisteredRoles().contains(requested))`
- `(requests.existsByAccountIdAndRequestTypeAndRequestedRoleAndStatus(account.getId(),"ROLE",requested,"PENDING"))`

**Saída declarada:**
- `return RequestView.of(requests.save(PlatformRequest.role(account,requested)));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
return RequestView.of(requests.save(PlatformRequest.role(account,requested)));
```

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestCity — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:36)

```java
@PostMapping("/account/platform-requests/cities") @ResponseStatus(HttpStatus.CREATED) @Transactional public RequestView requestCity(@Valid @RequestBody CityRequest body,Authentication auth)
```

Executa a etapa `request City` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByIdForUpdate`; `subject`; `account.isActive`; `body.city`; `body.state`; `body.postalCode`; `cities.findByCityIgnoreCaseAndStateIgnoreCase`; `requests.existsByAccountIdAndRequestTypeAndCityIgnoreCaseAndStateIgnoreCaseAndStatus`; `account.getId`; `RequestView.of`; `requests.save`; `PlatformRequest.city`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Uma conta suspensa não pode enviar solicitações.");`
- `throw new IllegalArgumentException("Esta cidade já está disponível no FoodDelivery.");`
- `throw new IllegalArgumentException("Sua solicitação para esta cidade já está em análise.");`

**Decisões condicionais examinadas:**
- `(!account.isActive())`
- `body.postalCode()==null`
- `(cities.findByCityIgnoreCaseAndStateIgnoreCase(city,state).filter(ServiceCity::isEnabled).isPresent())`
- `(requests.existsByAccountIdAndRequestTypeAndCityIgnoreCaseAndStateIgnoreCaseAndStatus(account.getId(),"CITY",city,state,"PENDING"))`

**Saída declarada:**
- `return RequestView.of(requests.save(PlatformRequest.city(account,city,state,cep)));`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
return RequestView.of(requests.save(PlatformRequest.city(account,city,state,cep)));
```

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### all — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:45)

```java
@GetMapping("/admin/platform-requests") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public List<RequestView> all()
```

Executa a etapa `all` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requests.findAllByOrderByCreatedAtDesc`.

**Saída declarada:**
- `return requests.findAllByOrderByCreatedAtDesc().stream().map(RequestView::of).toList();`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### review — linha 48

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:48)

```java
@PatchMapping("/admin/platform-requests/{id}/review") @PreAuthorize("hasRole('ADMIN')") @Transactional public RequestView review(@PathVariable UUID id,@Valid @RequestBody Review body,Authentication auth)
```

Executa a etapa `review` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `requests.findAccountId`; `accounts.findByIdForUpdate`; `requests.findByIdForUpdate`; `"PENDING".equals`; `request.getStatus`; `account`; `subject`; `body.approved`; `request.getRequestType`; `owner.isActive`; `owner.setRole`; `request.getRequestedRole`; `accounts.save`; `cities.findByCityIgnoreCaseAndStateIgnoreCase`; `request.getCity`; `request.getState`; `request.getPostalCode`; `city.enable`; `cities.save`; `request.getStore`; `stores.findByIdForUpdate`; `store.getId`; `owner.getGrantedRoles`; `store.setApproved`; `store.setActive`; `stores.save`; `request.review`; `body.note`; `requests.save`; `notifications.send`; `approvedMessage`; `RequestView.of`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Esta solicitação já foi analisada.");`
- `throw new IllegalArgumentException("Reative a conta antes de aprovar um novo perfil.");`
- `throw new IllegalArgumentException("A loja desta solicitação não foi encontrada.");`
- `throw new IllegalArgumentException("A conta do vendedor está suspensa ou sem acesso ativo. Reative o perfil antes de aprovar a loja.");`
- `throw new IllegalArgumentException("Tipo de solicitação desconhecido.");`

**Decisões condicionais examinadas:**
- `(!"PENDING".equals(request.getStatus()))`
- `(approve)`
- `(!owner.isActive())`
- `(store!=null)`
- `(store==null)`
- `(approve&&(!owner.isActive()||!owner.getGrantedRoles().contains(AccountRole.MERCHANT)))`
- `body.note()==null`
- `approve`

**Saída declarada:**
- `return RequestView.of(request);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
case "ROLE" -> {if(approve){if(!owner.isActive())throw new IllegalArgumentException("Reative a conta antes de aprovar um novo perfil.");owner.setRole(request.getRequestedRole());accounts.save(owner);}}
case "CITY" -> {if(approve){ServiceCity city=cities.findByCityIgnoreCaseAndStateIgnoreCase(request.getCity(),request.getState()).orElseGet(()->new ServiceCity(request.getCity(),request.getState(),request.getPostalCode()));city.enable(request.getPostalCode());cities.save(city);}}
case "STORE" -> {Store store=request.getStore();if(store!=null)store=stores.findByIdForUpdate(store.getId()).orElseThrow();if(store==null)throw new IllegalArgumentException("A loja desta solicitação não foi encontrada.");if(approve&&(!owner.isActive()||!owner.getGrantedRoles().contains(AccountRole.MERCHANT)))throw new IllegalArgumentException("A conta do vendedor está suspensa ou sem acesso ativo. Reative o perfil antes de aprovar a loja.");store.setApproved(approve);store.setActive(approve);stores.save(store);}
request.review(approve,reviewer,body.note()==null?null:body.note().trim());requests.save(request);
notifications.send(owner,approve?"Solicitação aprovada":"Solicitação não aprovada",approvedMessage(request,approve),approve?"success":"info");
```

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### approvedMessage — linha 66

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:66)

```java
private String approvedMessage(PlatformRequest r,boolean approved)
```

Executa a etapa `approved Message` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `r.getRequestType`; `r.getRequestedRole`; `r.getCity`; `r.getState`; `r.getStore`.

**Decisões condicionais examinadas:**
- `(!approved)`
- `r.getRequestedRole()==AccountRole.MERCHANT`
- `r.getStore()==null`

**Saída declarada:**
- `return "A administração analisou sua solicitação. Consulte os detalhes no painel da sua conta.";`
- `return switch(r.getRequestType()){case "ROLE"->"Seu perfil de "+(r.getRequestedRole()==AccountRole.MERCHANT?"vendedor":"entregador")+" foi liberado nesta mesma conta.";case "CITY"->"A cidade "+r.getCity()+"/"+r.getState()+" foi liberada na plataforma.";default->"A loja "+(r.getStore()==null?"solicitada":r.getStore().getName())+" foi aprovada e já pode ser configurada.";};`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### account — linha 67

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:67)

```java
private Account account(UUID id)
```

Executa a etapa `account` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`.

**Saída declarada:**
- `return accounts.findById(id).orElseThrow();`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 67

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:67)

```java
private UUID subject(Authentication a)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `a.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### of — linha 70

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:70)

```java
static RequestView of(PlatformRequest r)
```

Executa a etapa `of` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `r.getStore`; `r.getAccount`; `r.getId`; `r.getRequestType`; `r.getStatus`; `r.getRequestedRole`; `r.getCity`; `r.getState`; `r.getPostalCode`; `s.getId`; `s.getName`; `s.getCnpj`; `a.getId`; `a.getName`; `a.getEmail`; `r.getCreatedAt`; `r.getReviewedAt`; `r.getReviewNote`.

**Decisões condicionais examinadas:**
- `s==null`

**Saída declarada:**
- `return new RequestView(r.getId(),r.getRequestType(),r.getStatus(),r.getRequestedRole(),r.getCity(),r.getState(),r.getPostalCode(),s==null?null:s.getId(),s==null?null:s.getName(),s==null?null:s.getCnpj(),a.getId(),a.getName(),a.getEmail(),r.getCreatedAt(),r.getReviewedAt(),r.getReviewNote());`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestRepository.java

Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados.

Fonte: [PlatformRequestRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestRepository.java) · SHA-256 `652c673ce605c2da885d2f534307be28d01a20ee4b81b620a0552a9ee1498c8e`

### findAccountId — linha 5

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestRepository.java:5)

```java
@org.springframework.data.jpa.repository.Query("select request.account.id from PlatformRequest request where request.id=:id") Optional<UUID> findAccountId(@org.springframework.data.repository.query.Param("id") UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByIdForUpdate — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestRepository.java:7)

```java
@org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE) @org.springframework.data.jpa.repository.Query("select request from PlatformRequest request where request.id=:id") Optional<PlatformRequest> findByIdForUpdate(@org.springframework.data.repository.query.Param("id") UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByAccountIdOrderByCreatedAtDesc — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestRepository.java:10)

```java
List<PlatformRequest> findByAccountIdOrderByCreatedAtDesc(UUID accountId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findAllByOrderByCreatedAtDesc — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestRepository.java:11)

```java
List<PlatformRequest> findAllByOrderByCreatedAtDesc();
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByAccountIdAndRequestTypeAndStatus — linha 12

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestRepository.java:12)

```java
boolean existsByAccountIdAndRequestTypeAndStatus(UUID accountId,String type,String status);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByAccountIdAndRequestTypeAndRequestedRoleAndStatus — linha 13

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestRepository.java:13)

```java
boolean existsByAccountIdAndRequestTypeAndRequestedRoleAndStatus(UUID accountId,String type,br.com.fooddelivery.identity.AccountRole role,String status);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByAccountIdAndRequestTypeAndCityIgnoreCaseAndStateIgnoreCaseAndStatus — linha 14

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestRepository.java:14)

```java
boolean existsByAccountIdAndRequestTypeAndCityIgnoreCaseAndStateIgnoreCaseAndStatus(UUID accountId,String type,String city,String state,String status);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findFirstByStoreIdOrderByCreatedAtDesc — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestRepository.java:15)

```java
Optional<PlatformRequest> findFirstByStoreIdOrderByCreatedAtDesc(UUID storeId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java

Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados.

Fonte: [ServiceCity.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java) · SHA-256 `9d56a73003d3b0d57d650d2b67d3203dc937763ad8503d1ea90ff394152f886a`

### ServiceCity — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java:15)

```java
protected ServiceCity()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### ServiceCity — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java:16)

```java
public ServiceCity(String city,String state,String postalCode)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `city.trim`; `state.trim`; `postalCode.isBlank`; `postalCode.replaceAll`.

**Decisões condicionais examinadas:**
- `postalCode==null||postalCode.isBlank()`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### enable — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java:17)

```java
public void enable(String postalCode)
```

Executa a etapa `enable` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `postalCode.isBlank`.

**Decisões condicionais examinadas:**
- `(postalCode!=null&&!postalCode.isBlank())`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### update — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java:18)

```java
public void update(String city,String state,String postalCode,boolean enabled)
```

Executa a etapa `update` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `city.trim`; `state.trim`; `postalCode.isBlank`; `postalCode.replaceAll`.

**Decisões condicionais examinadas:**
- `postalCode==null||postalCode.isBlank()`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java:19)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCity — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java:19)

```java
public String getCity()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return city;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getState — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java:19)

```java
public String getState()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return state;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getPostalCode — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java:19)

```java
public String getPostalCode()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return postalCode;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isEnabled — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCity.java:19)

```java
public boolean isEnabled()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return enabled;`

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/platform/ServiceCityRepository.java

Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados.

Fonte: [ServiceCityRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCityRepository.java) · SHA-256 `3cefe29178ee6c7c648d0986182ffdff7fcf71a84b2fda34679b0504d234f919`

### findByCityIgnoreCaseAndStateIgnoreCase — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCityRepository.java:4)

```java
Optional<ServiceCity> findByCityIgnoreCaseAndStateIgnoreCase(String city,String state);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByEnabledTrue — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCityRepository.java:4)

```java
List<ServiceCity> findByEnabledTrue();
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findAllByOrderByCityAscStateAsc — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/ServiceCityRepository.java:4)

```java
List<ServiceCity> findAllByOrderByCityAscStateAsc();
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Cobertura e solicitações. A conta permanece única; aprovação vincula acesso/cidade/loja sem pedir novo cadastro dos mesmos dados. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/reservations/DevelopmentReservationPaymentController.java

Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja.

Fonte: [DevelopmentReservationPaymentController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/DevelopmentReservationPaymentController.java) · SHA-256 `821eaee3249ee70d7335db6ee1e364124adb6d8fe89932d2dfcfd330308bee27`

### DevelopmentReservationPaymentController — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/DevelopmentReservationPaymentController.java:16)

```java
public DevelopmentReservationPaymentController(ReservationRepository reservations,PaymentRepository payments,PaymentService paymentService)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### approve — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/DevelopmentReservationPaymentController.java:17)

```java
@PostMapping("/{id}/approve-payment") @PreAuthorize("hasRole('CUSTOMER')") public Object approve(@PathVariable UUID id,Authentication auth)
```

Executa a etapa `approve` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservations.findById`; `r.getCustomer`; ``; `auth.getPrincipal`; `payments.findByReservationId`; `paymentService.confirmForDevelopment`; `payment.getId`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Reserva de outra conta.");`

**Decisões condicionais examinadas:**
- `(!r.getCustomer().getId().toString().equals(((Jwt)auth.getPrincipal()).getSubject()))`

**Saída declarada:**
- `return paymentService.confirmForDevelopment(payment.getId());`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java

Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja.

Fonte: [Reservation.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java) · SHA-256 `82c1221cf7b3e81e82e6e1f03bd741082b9e779f49432c556280b75dfcaef8a6`

### Reservation — linha 33

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:33)

```java
protected Reservation()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### Reservation — linha 34

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:34)

```java
public Reservation(Store store,Account customer,Instant startsAt,UUID catalogItemId,String serviceName,long amount,String customerNote)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `startsAt.plusSeconds`.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### Reservation — linha 35

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:35)

```java
public Reservation(Store store,Account customer,Instant startsAt,UUID catalogItemId,String serviceName,long amount,String customerNote,boolean payAfterService)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `startsAt.plusSeconds`.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### Reservation — linha 36

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:36)

```java
public Reservation(Store store,Account customer,Instant startsAt,UUID catalogItemId,String serviceName,long amount,String customerNote,boolean payAfterService,StoreProfessional professional,Instant endsAt)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Colaborações:** `customerNote.isBlank`; `customerNote.trim`; `this.customerNote.length`; `endsAt.isAfter`.

**Rejeições locais:**
- `throw new IllegalArgumentException("O comentário pode ter até 500 caracteres.");`
- `throw new IllegalArgumentException("O fim do atendimento deve ser posterior ao início.");`

**Decisões condicionais examinadas:**
- `payAfterService`
- `customerNote==null||customerNote.isBlank()`
- `(this.customerNote!=null&&this.customerNote.length()>500)`
- `(endsAt==null||!endsAt.isAfter(startsAt))`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStore — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public Store getStore()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return store;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCustomer — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public Account getCustomer()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return customer;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStartsAt — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public Instant getStartsAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return startsAt;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getEndsAt — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public Instant getEndsAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return endsAt;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getServiceName — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public String getServiceName()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return serviceName;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCatalogItemId — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public UUID getCatalogItemId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return catalogItemId;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getProfessional — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public StoreProfessional getProfessional()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return professional;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAmountCents — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public long getAmountCents()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return amountCents;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStatus — linha 37

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:37)

```java
public ReservationStatus getStatus()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return status;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCustomerNote — linha 38

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:38)

```java
public String getCustomerNote()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return customerNote;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCancellationRequestedAt — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:39)

```java
public Instant getCancellationRequestedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return cancellationRequestedAt;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCancellationRequestStatus — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:39)

```java
public String getCancellationRequestStatus()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return cancellationRequestStatus;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCancellationRequestNote — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:39)

```java
public String getCancellationRequestNote()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return cancellationRequestNote;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestCancellation — linha 40

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:40)

```java
public void requestCancellation(String note)
```

Executa a etapa `request Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"REQUESTED".equals`; `Instant.now`; `note.isBlank`; `note.trim`; `cancellationRequestNote.length`.

**Rejeições locais:**
- `throw new IllegalStateException("Esta reserva não pode receber uma solicitação de cancelamento agora.");`
- `throw new IllegalArgumentException("O motivo pode ter até 500 caracteres.");`

**Decisões condicionais examinadas:**
- `(status!=ReservationStatus.CONFIRMED)`
- `("REQUESTED".equals(cancellationRequestStatus))`
- `note==null||note.isBlank()`
- `(cancellationRequestNote!=null&&cancellationRequestNote.length()>500)`

**Saída declarada:**
- `return;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### decideCancellation — linha 41

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:41)

```java
public void decideCancellation(boolean accepted)
```

Executa a etapa `decide Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"REQUESTED".equals`.

**Rejeições locais:**
- `throw new IllegalStateException("Não há solicitação de cancelamento pendente.");`

**Decisões condicionais examinadas:**
- `(!"REQUESTED".equals(cancellationRequestStatus))`
- `accepted`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isPayAfterService — linha 42

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:42)

```java
public boolean isPayAfterService()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return payAfterService;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### isOperatingHoursOverride — linha 43

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:43)

```java
public boolean isOperatingHoursOverride()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return operatingHoursOverride;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getOperatingHoursNotice — linha 44

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:44)

```java
public String getOperatingHoursNotice()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return operatingHoursNotice;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getOperatingHoursChangedAt — linha 45

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:45)

```java
public Instant getOperatingHoursChangedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return operatingHoursChangedAt;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### keepAfterHoursChange — linha 46

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:46)

```java
public void keepAfterHoursChange()
```

Executa a etapa `keep After Hours Change` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### clearHoursOverride — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:47)

```java
public void clearHoursOverride()
```

Executa a etapa `clear Hours Override` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancelForHoursChange — linha 48

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:48)

```java
public void cancelForHoursChange()
```

Executa a etapa `cancel For Hours Change` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`; `"REQUESTED".equals`.

**Rejeições locais:**
- `throw new IllegalStateException("Somente reservas ainda não iniciadas podem ser canceladas pela mudança de expediente.");`

**Decisões condicionais examinadas:**
- `(status!=ReservationStatus.PENDING_PAYMENT&&status!=ReservationStatus.CONFIRMED)`
- `("REQUESTED".equals(cancellationRequestStatus))`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### confirmPayment — linha 53

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:53)

```java
public void confirmPayment()
```

Executa a etapa `confirm Payment` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalStateException("Reserva não aguarda pagamento.");`

**Decisões condicionais examinadas:**
- `(status!=ReservationStatus.PENDING_PAYMENT)`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancelUnpaid — linha 54

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:54)

```java
public void cancelUnpaid()
```

Executa a etapa `cancel Unpaid` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Rejeições locais:**
- `throw new IllegalStateException("Somente reservas que aguardam pagamento podem ser canceladas por este fluxo.");`

**Decisões condicionais examinadas:**
- `(status!=ReservationStatus.PENDING_PAYMENT)`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### expireUnpaid — linha 55

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:55)

```java
public void expireUnpaid()
```

Executa a etapa `expire Unpaid` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Decisões condicionais examinadas:**
- `(status==ReservationStatus.PENDING_PAYMENT)`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### change — linha 56

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/Reservation.java:56)

```java
public void change(ReservationStatus next)
```

Executa a etapa `change` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `"REQUESTED".equals`.

**Rejeições locais:**
- `throw new IllegalStateException("Responda à solicitação de cancelamento antes de avançar o atendimento.");`
- `throw new IllegalStateException("Transição de reserva inválida.");`

**Decisões condicionais examinadas:**
- `("REQUESTED".equals(cancellationRequestStatus)&&next!=ReservationStatus.CANCELLED)`
- `(!valid)`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java

Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja.

Fonte: [ReservationController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java) · SHA-256 `16d140d81064d8d9f6f5279dc88a75a9f8f77ac420a1b5852c3fdac0582d5dd2`

**Contrato de dados/estados:**
```java
public record Request(@NotNull UUID storeId, @NotNull UUID serviceItemId, @NotNull Instant startsAt, @jakarta.validation.constraints.Size(max=500) String customerNote,UUID professionalId)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record Change(@NotNull ReservationStatus status)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CancellationNote(@jakarta.validation.constraints.Size(max=500) String note)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CancellationDecision(@NotNull Boolean accepted)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record CreateResponse(ReservationView reservation, UUID paymentId, PaymentStatus paymentStatus)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record ReservationView(UUID id, StoreView store, Instant startsAt, Instant endsAt, String serviceName, long amountCents, String customerNote,
                                  ReservationStatus status, Instant createdAt, boolean payAfterService, UUID paymentId,
                                  PaymentStatus paymentStatus,UUID professionalId,String professionalName,Instant cancellationRequestedAt,String cancellationRequestStatus,String cancellationRequestNote,boolean operatingHoursOverride,String operatingHoursNotice,Instant operatingHoursChangedAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record StoreView(UUID id, String name, String address, String category,String timezone)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### ReservationController — linha 47

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:47)

```java
public ReservationController(ReservationRepository reservations, ReservationSlotRepository slots, StoreRepository stores, AccountRepository accounts, CatalogItemRepository items, NotificationService notifications, PaymentRepository payments, StoreProfessionalRepository professionals, br.com.fooddelivery.catalog.StoreOperatingHours operatingHours)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### create — linha 62

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:62)

```java
@PostMapping("/reservations") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('CUSTOMER')") @Transactional public CreateResponse create(@Valid @RequestBody Request request, Authentication auth)
```

Executa a etapa `create` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `subject`; `accounts.findById`; `br.com.fooddelivery.identity.Cpf.isValid`; `customer.getCpf`; `customer.getPhone`; `stores.findByIdForUpdate`; `request.storeId`; `store.requireInPerson`; `items.findById`; `request.serviceItemId`; `i.isActive`; `i.isServiceItem`; `i.getStore`; `request.startsAt`; `Instant.now`; `operatingHours.requireOpenAt`; `item.getAppointmentDurationMinutes`; `operatingHours.requireOpenForInterval`; `professionals.findByStoreIdAndActiveTrueOrderByNameAsc`; `store.getId`; `slots.occupiedResources`; `activeProfessionals.isEmpty`; `occupied.contains`; `request.professionalId`; `activeProfessionals.stream`; `p.getId`; `selected.getId`; `occupied.isEmpty`; `CityMatcher.allowPayAfterService`; `item.isPayAfterService`; `customer.getCity`; `customer.getState`; `store.getCity`; `store.getState`; `reservations.save`; `item.getId`; `item.getName`; `item.getPriceCents`; `request.customerNote`; `slots.save`; `reservation.getId`; `reservation.getStartsAt`; `notifications.send`; `store.getName`; `String.format`; `java.util.Locale.forLanguageTag`; `store.getOwner`; `payments.save`; `view`; `payment.getId`; `payment.getStatus`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Cadastre um CPF válido no seu perfil antes de reservar.");`
- `throw new IllegalArgumentException("Complete seu telefone antes de reservar.");`
- `throw new IllegalArgumentException("A reserva precisa ser feita com ao menos 30 minutos de antecedência.");`
- `throw new IllegalStateException("Este horário já está reservado. Escolha outro horário.");`
- `throw new IllegalStateException("Este profissional já tem uma reserva neste intervalo. Escolha outro horário.");`
- `throw new IllegalStateException("Horário indisponível neste intervalo.");`

**Decisões condicionais examinadas:**
- `(!br.com.fooddelivery.identity.Cpf.isValid(customer.getCpf()))`
- `(customer.getPhone() == null || customer.getPhone().isBlank())`
- `(request.startsAt().isBefore(Instant.now().plusSeconds(1800)))`
- `(!activeProfessionals.isEmpty()&&occupied.contains(ReservationSlot.STORE_RESOURCE_KEY))`
- `(request.professionalId()!=null)`
- `(occupied.contains(selected.getId()))`
- `(!activeProfessionals.isEmpty())`
- `(!occupied.isEmpty())`
- `selected==null`
- `(payAfterService)`
- `payment == null`

**Saída declarada:**
- `return new CreateResponse(view(reservation, payment), payment == null ? null : payment.getId(), payment == null ? null : payment.getStatus());`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
Reservation reservation = reservations.save(new Reservation(store, customer, request.startsAt(),
slots.save(new ReservationSlot(store.getId(), reservation.getId(), reservation.getStartsAt(),endsAt,selected==null?null:selected.getId()));
notifications.send(customer, "Reserva confirmada", "Seu horário na " + store.getName()
notifications.send(store.getOwner(), "Nova reserva confirmada", "Uma reserva foi adicionada à agenda."
payment = payments.save(new Payment(reservation));
```

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mine — linha 111

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:111)

```java
@GetMapping("/reservations/mine") @PreAuthorize("hasRole('CUSTOMER')") @Transactional(readOnly = true) public List<ReservationView> mine(Authentication auth)
```

Executa a etapa `mine` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservations.findByCustomerIdOrderByStartsAtDesc`; `subject`; `payments.findByReservationId`; `reservation.getId`; `view`.

**Saída declarada:**
- `return reservations.findByCustomerIdOrderByStartsAtDesc(subject(auth)).stream().map(reservation -> { Payment payment = payments.findByReservationId(reservation.getId()).orElse(null); return view(reservation, payment); }).toList();`
- `return view(reservation, payment);`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### merchant — linha 121

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:121)

```java
@GetMapping("/merchant/reservations") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly = true) public List<ReservationView> merchant(Authentication auth)
```

Executa a etapa `merchant` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservations.findByStoreOwnerIdOrderByStartsAt`; `subject`; `reservation.getStatus`; `view`; `payments.findByReservationId`; `reservation.getId`.

**Saída declarada:**
- `return reservations.findByStoreOwnerIdOrderByStartsAt(subject(auth)).stream() .filter(reservation -> reservation.getStatus() != ReservationStatus.PENDING_PAYMENT) .map(reservation -> view(reservation, payments.findByReservationId(reservation.getId()).orElse(null))) .toList();`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### cancelUnpaid — linha 131

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:131)

```java
@PostMapping("/reservations/{id}/cancel") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public ReservationView cancelUnpaid(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `cancel Unpaid` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservations.findById`; `reservation.getCustomer`; `subject`; `payments.findByReservationId`; `reservation.getStatus`; `view`; `payment.getStatus`; `reservation.cancelUnpaid`; `slots.deleteByReservationId`; `payment.cancelPendingCheckout`; `notifications.send`; `reservation.getStore`; `reservations.save`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Reserva de outra conta.");`
- `throw new IllegalStateException("A reserva já foi confirmada. Fale com a loja para solicitar o cancelamento e o estorno.");`
- `throw new IllegalStateException("O pagamento foi aprovado. Fale com a loja para solicitar o cancelamento e o estorno.");`

**Decisões condicionais examinadas:**
- `(!reservation.getCustomer().getId().equals(subject(auth)))`
- `(reservation.getStatus() == ReservationStatus.CANCELLED)`
- `(reservation.getStatus() != ReservationStatus.PENDING_PAYMENT)`
- `(payment != null && payment.getStatus() == PaymentStatus.PAID)`
- `(payment != null)`

**Saída declarada:**
- `return view(reservation, payment);`
- `return view(reservations.save(reservation), payment);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
slots.deleteByReservationId(id);
notifications.send(reservation.getStore().getOwner(), "Reserva cancelada pelo cliente",
return view(reservations.save(reservation), payment);
```

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### requestCancellation — linha 155

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:155)

```java
@PostMapping("/reservations/{id}/cancellation-request") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public ReservationView requestCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationNote request,Authentication auth)
```

Executa a etapa `request Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservations.findById`; `reservation.getCustomer`; `subject`; `payments.findByReservationId`; `reservation.getStatus`; `payment.getStatus`; `reservation.cancelUnpaid`; `slots.deleteByReservationId`; `payment.cancelPendingCheckout`; `notifications.sendLinked`; `reservation.getStore`; `view`; `reservations.save`; `"REQUESTED".equals`; `reservation.getCancellationRequestStatus`; `reservation.requestCancellation`; `request.note`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Reserva de outra conta.");`
- `throw new IllegalStateException("O pagamento foi aprovado. Atualize a tela e solicite o cancelamento à loja.");`

**Decisões condicionais examinadas:**
- `(!reservation.getCustomer().getId().equals(subject(auth)))`
- `(reservation.getStatus()==ReservationStatus.PENDING_PAYMENT)`
- `(payment!=null&&payment.getStatus()==PaymentStatus.PAID)`
- `(payment!=null)`
- `(!alreadyRequested)`

**Saída declarada:**
- `return view(reservations.save(reservation),payment);`
- `return view(reservations.save(reservation),payment);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
reservation.cancelUnpaid();slots.deleteByReservationId(id);if(payment!=null)payment.cancelPendingCheckout();
notifications.sendLinked(reservation.getStore().getOwner(),"Reserva não paga cancelada","O cliente cancelou uma reserva ainda não paga; o horário foi liberado.","info",br.com.fooddelivery.notifications.NotificationTarget.RESERVATION,id);
return view(reservations.save(reservation),payment);
if(!alreadyRequested)notifications.sendLinked(reservation.getStore().getOwner(),"Cancelamento de reserva solicitado","O cliente pediu o cancelamento de um horário. Revise a solicitação na aba Agenda.","info",br.com.fooddelivery.notifications.NotificationTarget.RESERVATION,id);
```

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### decideCancellation — linha 174

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:174)

```java
@PatchMapping("/merchant/reservations/{id}/cancellation-request") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ReservationView decideCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationDecision request,Authentication auth)
```

Executa a etapa `decide Cancellation` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservations.findById`; `reservation.getStore`; `subject`; `reservation.decideCancellation`; `request.accepted`; `reservation.change`; `slots.deleteByReservationId`; `payments.findByReservationId`; `payment.getStatus`; `payment.requestFullRefund`; `notifications.send`; `reservation.getCustomer`; `view`; `reservations.save`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Reserva de outra loja.");`

**Decisões condicionais examinadas:**
- `(!reservation.getStore().getOwner().getId().equals(subject(auth)))`
- `(request.accepted())`
- `(request.accepted()&&payment!=null&&payment.getStatus()==PaymentStatus.PAID)`

**Saída declarada:**
- `return view(reservations.save(reservation),payment);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
if(request.accepted()){reservation.change(ReservationStatus.CANCELLED);slots.deleteByReservationId(id);}
if(request.accepted()&&payment!=null&&payment.getStatus()==PaymentStatus.PAID){payment.requestFullRefund();notifications.send(reservation.getCustomer(),"Cancelamento aceito","A loja aceitou o cancelamento. O estorno foi solicitado ao Mercado Pago e será atualizado após a confirmação do provedor.","info");}
else if(request.accepted())notifications.send(reservation.getCustomer(),"Cancelamento aceito","A loja aceitou o cancelamento e liberou o horário.","success");
else notifications.send(reservation.getCustomer(),"Cancelamento recusado","A loja recusou a solicitação. Fale com ela pelo chat se precisar de ajuda.","warn");
return view(reservations.save(reservation),payment);
```

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### update — linha 189

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:189)

```java
@PatchMapping("/merchant/reservations/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ReservationView update(@PathVariable UUID id, @Valid @RequestBody Change change, Authentication auth)
```

Executa a etapa `update` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservations.findById`; `reservation.getStore`; `subject`; `reservation.change`; `change.status`; `slots.deleteByReservationId`; `payments.findByReservationId`; `payment.getStatus`; `payment.requestFullRefund`; `notifications.send`; `reservation.getCustomer`; `view`; `reservations.save`.

**Rejeições locais:**
- `throw new org.springframework.security.access.AccessDeniedException("Reserva de outra loja.");`

**Decisões condicionais examinadas:**
- `(!reservation.getStore().getOwner().getId().equals(subject(auth)))`
- `(change.status() == ReservationStatus.CANCELLED || change.status() == ReservationStatus.COMPLETED || change.status() == ReservationStatus.NO_SHOW)`
- `(change.status() == ReservationStatus.CANCELLED && payment != null && payment.getStatus() == PaymentStatus.PAID)`

**Saída declarada:**
- `return view(reservations.save(reservation), payment);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
slots.deleteByReservationId(id);
payment.requestFullRefund();
notifications.send(reservation.getCustomer(), "Estorno solicitado",
notifications.send(reservation.getCustomer(), "Reserva atualizada",
return view(reservations.save(reservation), payment);
```

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### view — linha 217

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:217)

```java
private static ReservationView view(Reservation reservation, Payment payment)
```

Executa a etapa `view` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `reservation.getStore`; `store.getId`; `store.getName`; `store.getAddress`; `store.getCategory`; `store.getTimezone`; `reservation.getId`; `reservation.getStartsAt`; `reservation.getEndsAt`; `reservation.getServiceName`; `reservation.getAmountCents`; `reservation.getCustomerNote`; `reservation.getStatus`; `reservation.getCreatedAt`; `reservation.isPayAfterService`; `payment.getId`; `payment.getStatus`; `reservation.getProfessional`; `reservation.getCancellationRequestedAt`; `reservation.getCancellationRequestStatus`; `reservation.getCancellationRequestNote`; `reservation.isOperatingHoursOverride`; `reservation.getOperatingHoursNotice`; `reservation.getOperatingHoursChangedAt`.

**Decisões condicionais examinadas:**
- `payment == null`
- `reservation.getProfessional()==null`

**Saída declarada:**
- `return new ReservationView(reservation.getId(), storeView, reservation.getStartsAt(), reservation.getEndsAt(),reservation.getServiceName(), reservation.getAmountCents(), reservation.getCustomerNote(), reservation.getStatus(), reservation.getCreatedAt(), reservation.isPayAfterService(), payment == null ? null : payment.getId(), payment == null ? null : payment.getStatus(),reservation.getProfessional()==null?null:reservation.getProfessional().getId(),reservation.getProfessional()==null?null:reservation.getProfessional().getName(),reservation.getCancellationRequestedAt(),reservation.getCancellationRequestStatus(),reservation.getCancellationRequestNote(),reservation.isOperatingHoursOverride(),reservation.getOperatingHoursNotice(),reservation.getOperatingHoursChangedAt());`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 226

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:226)

```java
private UUID subject(Authentication auth)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `auth.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject());`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/reservations/ReservationRepository.java

Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja.

Fonte: [ReservationRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationRepository.java) · SHA-256 `364e7f21794c8a468ef6f5f871c452af236ef69926c4a61d8adc481a6765cf73`

### findByCustomerIdOrderByStartsAtDesc — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationRepository.java:7)

```java
List<Reservation> findByCustomerIdOrderByStartsAtDesc(UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStoreOwnerIdOrderByStartsAt — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationRepository.java:7)

```java
List<Reservation> findByStoreOwnerIdOrderByStartsAt(UUID id);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByStoreIdAndStartsAt — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationRepository.java:7)

```java
boolean existsByStoreIdAndStartsAt(UUID store,Instant startsAt);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findByStatusAndCreatedAtBefore — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationRepository.java:7)

```java
List<Reservation> findByStatusAndCreatedAtBefore(ReservationStatus status,Instant before);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### lockFutureForHours — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationRepository.java:8)

```java
@org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE) @Query("select r from Reservation r where r.store.id=:storeId and r.status in :statuses and r.endsAt > :now order by r.id") List<Reservation> lockFutureForHours(@Param("storeId") UUID storeId,@Param("statuses") Collection<ReservationStatus> statuses,@Param("now") Instant now);
```

Executa a etapa `lock Future For Hours` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### completedByCategorySince — linha 11

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationRepository.java:11)

```java
@Query("select r.store.category, count(r) from Reservation r where r.status = br.com.fooddelivery.reservations.ReservationStatus.COMPLETED and r.createdAt >= :since and r.store.active = true and r.store.approved = true and (:city is null or lower(r.store.city) = lower(:city)) and (:state is null or lower(r.store.state) = lower(:state)) group by r.store.category") List<Object[]> completedByCategorySince(@Param("since") Instant since, @Param("city") String city, @Param("state") String state);
```

Executa a etapa `completed By Category Since` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlot.java

Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja.

Fonte: [ReservationSlot.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlot.java) · SHA-256 `e22c6f00909f258d613a44d5640eeeecf24b772f91bf74b88680868173f97261`

### ReservationSlot — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlot.java:18)

```java
protected ReservationSlot()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### ReservationSlot — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlot.java:19)

```java
public ReservationSlot(UUID storeId,UUID reservationId,Instant startsAt,Instant endsAt,UUID professionalId)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisões condicionais examinadas:**
- `professionalId==null`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getSlotKey — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlot.java:20)

```java
public UUID getSlotKey()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return slotKey;`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlotRepository.java

Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja.

Fonte: [ReservationSlotRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlotRepository.java) · SHA-256 `0698c75b2eb1a1509771c6a6d5030632604d1c7242155588eeff9c9266208ded`

### existsByStoreIdAndStartsAt — linha 7

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlotRepository.java:7)

```java
boolean existsByStoreIdAndStartsAt(UUID storeId,java.time.Instant startsAt);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByStoreIdAndProfessionalIdAndStartsAt — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlotRepository.java:8)

```java
boolean existsByStoreIdAndProfessionalIdAndStartsAt(UUID storeId,UUID professionalId,java.time.Instant startsAt);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### existsByStoreIdAndProfessionalIdIsNullAndStartsAt — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlotRepository.java:9)

```java
boolean existsByStoreIdAndProfessionalIdIsNullAndStartsAt(UUID storeId,java.time.Instant startsAt);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findOverlapping — linha 10

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlotRepository.java:10)

```java
@org.springframework.data.jpa.repository.Query("select s from ReservationSlot s where s.storeId=:storeId and s.slotKey=:slotKey and s.startsAt < :endsAt and s.endsAt > :startsAt") java.util.List<ReservationSlot> findOverlapping(@org.springframework.data.repository.query.Param("storeId") UUID storeId, @org.springframework.data.repository.query.Param("slotKey") UUID slotKey, @org.springframework.data.repository.query.Param("startsAt") java.time.Instant startsAt, @org.springframework.data.repository.query.Param("endsAt") java.time.Instant endsAt);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### occupiedResources — linha 15

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlotRepository.java:15)

```java
@org.springframework.data.jpa.repository.Query("select s.slotKey from ReservationSlot s where s.storeId=:storeId and s.startsAt < :endsAt and s.endsAt > :startsAt") java.util.List<UUID> occupiedResources(@org.springframework.data.repository.query.Param("storeId") UUID storeId, @org.springframework.data.repository.query.Param("startsAt") java.time.Instant startsAt, @org.springframework.data.repository.query.Param("endsAt") java.time.Instant endsAt);
```

Executa a etapa `occupied Resources` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### occupiedProfessionalIds — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlotRepository.java:19)

```java
@org.springframework.data.jpa.repository.Query("select s.professionalId from ReservationSlot s where s.storeId=:storeId and s.startsAt=:startsAt and s.professionalId is not null") java.util.List<UUID> occupiedProfessionalIds(@org.springframework.data.repository.query.Param("storeId") UUID storeId,@org.springframework.data.repository.query.Param("startsAt") java.time.Instant startsAt);
```

Executa a etapa `occupied Professional Ids` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### deleteByReservationId — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationSlotRepository.java:21)

```java
void deleteByReservationId(UUID reservationId);
```

Executa a etapa `delete By Reservation Id` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/reservations/ReservationStatus.java

Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja.

Fonte: [ReservationStatus.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationStatus.java) · SHA-256 `285c1819e756a0e0f532b2135ff4161d0571717cd6d35a6780ac279c5d623452`

**Contrato de dados/estados:**
```java
public enum ReservationStatus
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### descriptionPtBr — linha 4

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationStatus.java:4)

```java
public String descriptionPtBr()
```

Executa a etapa `description Pt Br` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Saída declarada:**
- `return switch(this) { case PENDING_PAYMENT -> "aguardando pagamento"; case CONFIRMED -> "confirmada"; case IN_SERVICE -> "em atendimento"; case COMPLETED -> "concluída"; case CANCELLED -> "cancelada"; case NO_SHOW -> "não compareceu"; };`

**Decisão de engenharia:** Agenda e capacidade. A loja é bloqueada durante a criação; o profissional e o intervalo inteiro determinam conflitos. Datas são interpretadas no fuso da loja. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java

Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável.

Fonte: [SupportTicket.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java) · SHA-256 `6e1e771a14dad52dfa57e2863b30b452b8c0565993c4c4560c0b35c896bafaae`

### SupportTicket — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:18)

```java
protected SupportTicket()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### SupportTicket — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:19)

```java
public SupportTicket(Account account, String subject, String category)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:20)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getAccount — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:20)

```java
public Account getAccount()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return account;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getSubject — linha 20

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:20)

```java
public String getSubject()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return subject;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCategory — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:21)

```java
public String getCategory()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return category;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getStatus — linha 21

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:21)

```java
public SupportTicketStatus getStatus()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return status;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:22)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getUpdatedAt — linha 22

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:22)

```java
public Instant getUpdatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return updatedAt;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### setStatus — linha 23

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:23)

```java
public void setStatus(SupportTicketStatus status)
```

Executa a etapa `set Status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
public void setStatus(SupportTicketStatus status){this.status=status; this.updatedAt=Instant.now();}
```

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### touch — linha 24

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicket.java:24)

```java
public void touch()
```

Executa a etapa `touch` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `Instant.now`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
public void touch(){this.updatedAt=Instant.now();}
```

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java

Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável.

Fonte: [SupportTicketController.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java) · SHA-256 `d4b7845bbe939bc71a2df0abc5bc0ba27cb06bcc5562e492507827d81f981130`

**Contrato de dados/estados:**
```java
public record CreateRequest(@NotBlank @Size(max=140) String subject,@NotBlank @Size(max=30) String category,@NotBlank @Size(max=2000) String body)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record MessageRequest(@NotBlank @Size(max=2000) String body)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record StatusRequest(@NotNull SupportTicketStatus status)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record TicketView(UUID id,String subject,String category,SupportTicketStatus status,UUID accountId,String accountName,String accountEmail,java.time.Instant createdAt,java.time.Instant updatedAt,String lastMessage,java.time.Instant lastMessageAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record MessageView(UUID id,UUID senderId,String senderName,String senderRole,String body,java.time.Instant createdAt)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

**Contrato de dados/estados:**
```java
public record TicketDetail(TicketView ticket,List<MessageView> messages)
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

### SupportTicketController — linha 26

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:26)

```java
public SupportTicketController(SupportTicketRepository tickets, SupportTicketMessageRepository messages, AccountRepository accounts, NotificationService notifications)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mine — linha 31

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:31)

```java
@GetMapping("/support/tickets/mine") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER','ADMIN')") @Transactional(readOnly=true) public List<TicketView> mine(Authentication auth)
```

Executa a etapa `mine` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `subject`; `tickets.findByAccountIdOrderByUpdatedAtDesc`.

**Saída declarada:**
- `return tickets.findByAccountIdOrderByUpdatedAtDesc(owner).stream().map(this::view).toList();`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### create — linha 39

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:39)

```java
@PostMapping("/support/tickets") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')") @Transactional public TicketDetail create(@Valid @RequestBody CreateRequest request, Authentication auth)
```

Executa a etapa `create` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `request.category`; `Set.of`; `account`; `subject`; `tickets.save`; `request.subject`; `messages.save`; `request.body`; `accounts.findByRoleIn`; `List.of`; `notifications.sendUnreadOnceLinked`; `ticket.getId`; `detail`.

**Rejeições locais:**
- `throw new IllegalArgumentException("Escolha um assunto válido para o chamado.");`

**Decisões condicionais examinadas:**
- `(!Set.of("GENERAL","ORDER","RESERVATION","PAYMENT","ACCOUNT").contains(category))`

**Saída declarada:**
- `return detail(ticket);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
SupportTicket ticket=tickets.save(new SupportTicket(owner,request.subject().trim(),category));
messages.save(new SupportTicketMessage(ticket,owner,request.body().trim()));
accounts.findByRoleIn(List.of(AccountRole.ADMIN)).forEach(admin -> notifications.sendUnreadOnceLinked(admin,
```

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### mineDetail — linha 54

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:54)

```java
@GetMapping("/support/tickets/{id}") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')") @Transactional(readOnly=true) public TicketDetail mineDetail(@PathVariable UUID id, Authentication auth)
```

Executa a etapa `mine Detail` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `detail`; `owned`; `subject`.

**Saída declarada:**
- `return detail(owned(id,subject(auth)));`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### reply — linha 59

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:59)

```java
@PostMapping("/support/tickets/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')") @Transactional public TicketDetail reply(@PathVariable UUID id,@Valid @RequestBody MessageRequest request,Authentication auth)
```

Executa a etapa `reply` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `owned`; `subject`; `ticket.getStatus`; `ticket.setStatus`; `ticket.touch`; `messages.save`; `account`; `request.body`; `notifyAdmins`; `detail`.

**Decisões condicionais examinadas:**
- `(ticket.getStatus()==SupportTicketStatus.CLOSED)`

**Saída declarada:**
- `return detail(ticket);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
if(ticket.getStatus()==SupportTicketStatus.CLOSED) ticket.setStatus(SupportTicketStatus.OPEN); else ticket.touch();
messages.save(new SupportTicketMessage(ticket,account(subject(auth)),request.body().trim()));
```

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### all — linha 70

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:70)

```java
@GetMapping("/admin/support-tickets") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public List<TicketView> all()
```

Executa a etapa `all` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `tickets.findAllByOrderByUpdatedAtDesc`.

**Saída declarada:**
- `return tickets.findAllByOrderByUpdatedAtDesc().stream().map(this::view).toList();`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### adminDetail — linha 75

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:75)

```java
@GetMapping("/admin/support-tickets/{id}") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public TicketDetail adminDetail(@PathVariable UUID id)
```

Executa a etapa `admin Detail` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `detail`; `ticket`.

**Saída declarada:**
- `return detail(ticket(id));`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### adminReply — linha 80

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:80)

```java
@PostMapping("/admin/support-tickets/{id}/messages") @PreAuthorize("hasRole('ADMIN')") @Transactional public TicketDetail adminReply(@PathVariable UUID id,@Valid @RequestBody MessageRequest request,Authentication auth)
```

Executa a etapa `admin Reply` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ticket`; `ticket.getStatus`; `ticket.touch`; `messages.save`; `account`; `subject`; `request.body`; `notifications.sendUnreadOnceLinked`; `ticket.getAccount`; `ticket.getSubject`; `ticket.getId`; `detail`.

**Rejeições locais:**
- `throw new IllegalStateException("Reabra o chamado antes de responder.");`

**Decisões condicionais examinadas:**
- `(ticket.getStatus()==SupportTicketStatus.CLOSED)`

**Saída declarada:**
- `return detail(ticket);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
ticket.touch(); messages.save(new SupportTicketMessage(ticket,account(subject(auth)),request.body().trim()));
notifications.sendUnreadOnceLinked(ticket.getAccount(),"Resposta do atendimento", "A equipe respondeu ao seu chamado: "+ticket.getSubject(),"info", NotificationTarget.SUPPORT, ticket.getId());
```

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### updateStatus — linha 91

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:91)

```java
@PatchMapping("/admin/support-tickets/{id}/status") @PreAuthorize("hasRole('ADMIN')") @Transactional public TicketView updateStatus(@PathVariable UUID id,@Valid @RequestBody StatusRequest request)
```

Executa a etapa `update Status` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ticket`; `ticket.setStatus`; `request.status`; `view`.

**Saída declarada:**
- `return view(ticket);`

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
SupportTicket ticket=ticket(id); ticket.setStatus(request.status());
```

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### owned — linha 99

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:99)

```java
private SupportTicket owned(UUID id,UUID owner)
```

Executa a etapa `owned` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `ticket`; `ticket.getAccount`.

**Rejeições locais:**
- `throw new AccessDeniedException("Este chamado pertence a outra conta.");`

**Decisões condicionais examinadas:**
- `(!ticket.getAccount().getId().equals(owner))`

**Saída declarada:**
- `return ticket;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### ticket — linha 100

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:100)

```java
private SupportTicket ticket(UUID id)
```

Executa a etapa `ticket` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `tickets.findById`.

**Saída declarada:**
- `return tickets.findById(id).orElseThrow(()->new NoSuchElementException("Chamado não encontrado."));`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### account — linha 101

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:101)

```java
private Account account(UUID id)
```

Executa a etapa `account` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findById`.

**Saída declarada:**
- `return accounts.findById(id).orElseThrow();`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### subject — linha 102

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:102)

```java
private UUID subject(Authentication auth)
```

Executa a etapa `subject` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `UUID.fromString`; ``; `auth.getPrincipal`.

**Saída declarada:**
- `return UUID.fromString(((Jwt)auth.getPrincipal()).getSubject());`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### notifyAdmins — linha 103

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:103)

```java
private void notifyAdmins(SupportTicket ticket)
```

Executa a etapa `notify Admins` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `accounts.findByRoleIn`; `List.of`; `notifications.sendUnreadOnceLinked`; `ticket.getId`.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
private void notifyAdmins(SupportTicket ticket){accounts.findByRoleIn(List.of(AccountRole.ADMIN)).forEach(admin->notifications.sendUnreadOnceLinked(admin,"Atualização em chamado","Uma nova mensagem foi adicionada a um chamado.","info",NotificationTarget.SUPPORT,ticket.getId()));}
```

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### view — linha 104

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:104)

```java
private TicketView view(SupportTicket ticket)
```

Executa a etapa `view` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `messages.findTopByTicketIdOrderByCreatedAtDesc`; `ticket.getId`; `ticket.getSubject`; `ticket.getCategory`; `ticket.getStatus`; `ticket.getAccount`; `ticket.getCreatedAt`; `ticket.getUpdatedAt`; `last.getBody`; `last.getCreatedAt`.

**Decisões condicionais examinadas:**
- `last==null`

**Saída declarada:**
- `return new TicketView(ticket.getId(),ticket.getSubject(),ticket.getCategory(),ticket.getStatus(),ticket.getAccount().getId(),ticket.getAccount().getName(),ticket.getAccount().getEmail(),ticket.getCreatedAt(),ticket.getUpdatedAt(),last==null?"":last.getBody(),last==null?ticket.getCreatedAt():last.getCreatedAt());`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### detail — linha 108

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:108)

```java
private TicketDetail detail(SupportTicket ticket)
```

Executa a etapa `detail` neste módulo. O contrato concreto está na assinatura, nos efeitos e nas condições abaixo; callbacks recebem o evento/elemento da operação que os contém.

**Colaborações:** `view`; `messages.findByTicketIdOrderByCreatedAtAsc`; `ticket.getId`; `m.getId`; `m.getSender`; `m.getBody`; `m.getCreatedAt`.

**Saída declarada:**
- `return new TicketDetail(view(ticket),messages.findByTicketIdOrderByCreatedAtAsc(ticket.getId()).stream().map(m->new MessageView(m.getId(),m.getSender().getId(),m.getSender().getName(),m.getSender().getRole().name(),m.getBody(),m.getCreatedAt())).toList());`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessage.java

Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável.

Fonte: [SupportTicketMessage.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessage.java) · SHA-256 `df4e66ec4d5d2f695ab82c903cc7c5c79163ce076760ebe57e74f5f8fb635452`

### SupportTicketMessage — linha 16

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessage.java:16)

```java
protected SupportTicketMessage()
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### SupportTicketMessage — linha 17

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessage.java:17)

```java
public SupportTicketMessage(SupportTicket ticket, Account sender, String body)
```

Constrói a instância e recebe suas dependências/dados iniciais; a construção não deve substituir autorização da operação.

**Efeitos identificados no corpo (inclui callbacks internos):**
```java
public SupportTicketMessage(SupportTicket ticket, Account sender, String body){this.ticket=ticket;this.sender=sender;this.body=body;}
```

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getId — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessage.java:18)

```java
public UUID getId()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return id;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getTicket — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessage.java:18)

```java
public SupportTicket getTicket()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return ticket;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getSender — linha 18

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessage.java:18)

```java
public Account getSender()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return sender;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getBody — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessage.java:19)

```java
public String getBody()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return body;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### getCreatedAt — linha 19

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessage.java:19)

```java
public Instant getCreatedAt()
```

Consulta ou deriva o valor indicado pelo nome; o chamador decide se esse dado pode compor a resposta pública.

**Saída declarada:**
- `return createdAt;`

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessageRepository.java

Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável.

Fonte: [SupportTicketMessageRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessageRepository.java) · SHA-256 `fbae1d56d92e31a1fbf2fa68e9fee765f3eec65e0a5fafa34d500b3e138218d3`

### findByTicketIdOrderByCreatedAtAsc — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessageRepository.java:8)

```java
List<SupportTicketMessage> findByTicketIdOrderByCreatedAtAsc(UUID ticketId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findTopByTicketIdOrderByCreatedAtDesc — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketMessageRepository.java:9)

```java
SupportTicketMessage findTopByTicketIdOrderByCreatedAtDesc(UUID ticketId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/support/SupportTicketRepository.java

Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável.

Fonte: [SupportTicketRepository.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketRepository.java) · SHA-256 `1569641dcd53f88ecdd399753462538fa9e81baf3339ed44e82f6488e164b734`

### findByAccountIdOrderByUpdatedAtDesc — linha 8

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketRepository.java:8)

```java
List<SupportTicket> findByAccountIdOrderByUpdatedAtDesc(UUID accountId);
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

### findAllByOrderByUpdatedAtDesc — linha 9

[Abrir implementação](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketRepository.java:9)

```java
List<SupportTicket> findAllByOrderByUpdatedAtDesc();
```

Consulta persistência pelo critério declarado; confira predicado, ordenação e bloqueio abaixo. Retorno vazio não deve conceder acesso.

Implementação fornecida pelo Spring Data/contrato da interface. O nome derivado ou `@Query` determina o SQL; `@Lock`/`@Modifying` determina bloqueio ou mutação.

**Decisão de engenharia:** Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável. Evite mover esta regra apenas para o navegador; ao alterar o contrato, atualize consumidores e testes do domínio.

## Backend/src/main/java/br/com/fooddelivery/support/SupportTicketStatus.java

Atendimento. Cliente vê seus chamados e administrador gerencia a fila. Mensagens são dados, não HTML executável.

Fonte: [SupportTicketStatus.java](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketStatus.java) · SHA-256 `1545779f214c6ffe03aced2f7a0d07ecb6036f15cd4f584b32c46a51786dfee2`

**Contrato de dados/estados:**
```java
public enum SupportTicketStatus
```
Os componentes do record são seus acessores públicos implícitos; suas anotações definem validação quando o controlador usa `@Valid`. Enum limita valores aceitos, mas as transições também precisam das regras de domínio.

Sem função explícita: contrato declarativo, enum ou inicializador. Os campos e as anotações da fonte são o contrato deste arquivo.

