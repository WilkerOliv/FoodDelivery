# Inventário de interface, HTTP e migrações

Data: 04/11/2026. Inventário estático; uma linha aqui não significa que a ação foi executada em todas as combinações. Consulte a matriz de validação.

## Atributos de interação do frontend

Os atributos `data-*` conectam botões, formulários e estados aos handlers documentados na referência. Alguns são seletores de apresentação, não ações. Valores dinâmicos são escapados no template e revalidados no servidor.

| Atributo | Primeira ocorrência |
|---|---|
| `data-about-modal` | [main.js:334](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:334) |
| `data-about-us` | [main.js:310](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:310) |
| `data-accept-delivery` | [main.js:618](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:618) |
| `data-accepted` | [main.js:1055](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1055) |
| `data-account-active` | [main.js:604](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:604) |
| `data-account-role` | [main.js:606](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:606) |
| `data-account-section` | [main.js:569](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:569) |
| `data-action` | [main.js:540](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:540) |
| `data-active` | [main.js:1066](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1066) |
| `data-add-address` | [main.js:756](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:756) |
| `data-add-item` | [main.js:716](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:716) |
| `data-address-form` | [main.js:758](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:758) |
| `data-admin-account-edit` | [main.js:564](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:564) |
| `data-admin-area` | [main.js:1084](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1084) |
| `data-admin-definition-section` | [main.js:570](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:570) |
| `data-admin-edit-account` | [main.js:605](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:605) |
| `data-admin-edit-store` | [main.js:565](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:565) |
| `data-admin-profile-save` | [main.js:558](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:558) |
| `data-admin-team-section` | [main.js:632](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:632) |
| `data-admin-ticket` | [main.js:496](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:496) |
| `data-approve-store` | [main.js:616](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:616) |
| `data-auth-mode` | [main.js:325](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:325) |
| `data-cancel-address` | [main.js:758](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:758) |
| `data-cancel-logout` | [main.js:1294](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1294) |
| `data-cancel-order` | [main.js:863](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:863) |
| `data-cancel-reservation` | [main.js:876](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:876) |
| `data-cart-mode` | [main.js:755](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:755) |
| `data-catalog-item-update` | [main.js:1160](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1160) |
| `data-category` | [main.js:507](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:507) |
| `data-category-select` | [main.js:519](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:519) |
| `data-cep` | [main.js:758](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:758) |
| `data-cep-feedback` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-chat-draft` | [main.js:641](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:641) |
| `data-chat-form` | [main.js:640](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:640) |
| `data-chat-id` | [main.js:892](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:892) |
| `data-chat-panel` | [main.js:885](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:885) |
| `data-chat-type` | [main.js:892](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:892) |
| `data-checkout` | [main.js:556](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:556) |
| `data-checkout-note` | [main.js:760](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:760) |
| `data-checkout-pay` | [main.js:624](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:624) |
| `data-city` | [main.js:468](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:468) |
| `data-city-count` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-city-list` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-city-request` | [main.js:578](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:578) |
| `data-clear-notifications` | [main.js:976](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:976) |
| `data-close-about` | [main.js:334](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:334) |
| `data-close-admin-edit` | [accessibility.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:33) |
| `data-close-cart` | [accessibility.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:33) |
| `data-close-catalog-edit` | [accessibility.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:33) |
| `data-close-chat` | [main.js:642](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:642) |
| `data-close-item` | [main.js:1160](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1160) |
| `data-close-location` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-close-modal` | [accessibility.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:33) |
| `data-close-support` | [accessibility.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:33) |
| `data-confirm-logout` | [main.js:1294](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1294) |
| `data-connect-mercadopago` | [main.js:984](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:984) |
| `data-cover-review` | [main.js:612](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:612) |
| `data-create-item` | [main.js:591](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:591) |
| `data-create-operator` | [main.js:601](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:601) |
| `data-create-store` | [main.js:580](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:580) |
| `data-decision` | [main.js:1110](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1110) |
| `data-default-address` | [main.js:935](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:935) |
| `data-delete-address` | [main.js:935](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:935) |
| `data-delivery-card` | [main.js:860](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:860) |
| `data-delivery-status` | [main.js:619](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:619) |
| `data-dismiss-temp-password` | [main.js:608](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:608) |
| `data-enabled` | [main.js:903](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:903) |
| `data-exception-closed` | [main.js:586](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:586) |
| `data-forgot-password` | [main.js:332](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:332) |
| `data-help` | [main.js:404](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:404) |
| `data-hours-add-exception` | [main.js:587](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:587) |
| `data-hours-close-today` | [main.js:588](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:588) |
| `data-hours-day` | [main.js:585](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:585) |
| `data-hours-remove-exception` | [main.js:589](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:589) |
| `data-id` | [main.js:754](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:754) |
| `data-item-` | [main.js:1139](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1139) |
| `data-item-activate` | [main.js:594](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:594) |
| `data-item-deactivate` | [main.js:593](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:593) |
| `data-item-edit` | [main.js:592](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:592) |
| `data-item-kind` | [main.js:597](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:597) |
| `data-location-cep` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-location-clear` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-location-confirm` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-location-gps` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-location-map` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-location-picker` | [main.js:410](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:410) |
| `data-location-result` | [main.js:481](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:481) |
| `data-location-results` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-location-search` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-location-selection` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-location-selection-label` | [main.js:457](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:457) |
| `data-logout` | [main.js:412](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:412) |
| `data-lookup-cep` | [main.js:758](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:758) |
| `data-mask` | [main.js:327](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:327) |
| `data-merchant-store-select` | [main.js:582](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:582) |
| `data-mobile-more` | [main.js:407](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:407) |
| `data-name` | [main.js:1119](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1119) |
| `data-no-show-warning` | [main.js:1038](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1038) |
| `data-notification-target` | [main.js:977](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:977) |
| `data-offer-delivery` | [main.js:614](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:614) |
| `data-open-chat-id` | [main.js:863](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:863) |
| `data-open-chat-type` | [main.js:639](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:639) |
| `data-operator-active` | [main.js:603](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:603) |
| `data-operator-application` | [main.js:602](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:602) |
| `data-operator-request` | [main.js:577](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:577) |
| `data-order-cancellation-decision` | [main.js:1055](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1055) |
| `data-order-card` | [main.js:860](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:860) |
| `data-order-status` | [main.js:613](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:613) |
| `data-page` | [main.js:402](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:402) |
| `data-password-change` | [main.js:566](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:566) |
| `data-pay-after-service` | [main.js:598](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:598) |
| `data-phone-verify-confirm` | [main.js:568](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:568) |
| `data-phone-verify-request` | [main.js:567](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:567) |
| `data-platform-review` | [main.js:579](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:579) |
| `data-professional-active` | [main.js:596](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:596) |
| `data-professional-create` | [main.js:595](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:595) |
| `data-profile-save` | [main.js:557](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:557) |
| `data-qty` | [main.js:754](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:754) |
| `data-recovery-back` | [main.js:323](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:323) |
| `data-recovery-channel` | [main.js:339](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:339) |
| `data-refresh-audit` | [main.js:610](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:610) |
| `data-refresh-integrations` | [main.js:609](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:609) |
| `data-reopen-store` | [main.js:621](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:621) |
| `data-request-order-cancellation` | [main.js:863](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:863) |
| `data-request-reservation-cancellation` | [main.js:876](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:876) |
| `data-reservation-cancellation-decision` | [main.js:1070](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1070) |
| `data-reservation-card` | [main.js:876](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:876) |
| `data-reservation-status` | [main.js:615](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:615) |
| `data-reserve` | [main.js:716](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:716) |
| `data-reset-password` | [main.js:607](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:607) |
| `data-resubmit-store` | [main.js:581](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:581) |
| `data-resume-order` | [main.js:863](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:863) |
| `data-resume-reservation` | [main.js:876](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:876) |
| `data-risk-ack-field` | [main.js:1038](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1038) |
| `data-role` | [main.js:1119](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1119) |
| `data-scroll-stores` | [main.js:510](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:510) |
| `data-section` | [main.js:626](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:626) |
| `data-service-city-create` | [main.js:559](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:559) |
| `data-service-city-toggle` | [main.js:562](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:562) |
| `data-service-city-update` | [main.js:561](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:561) |
| `data-service-duration-field` | [main.js:1038](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1038) |
| `data-service-payment-field` | [main.js:1038](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1038) |
| `data-share-location` | [main.js:620](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:620) |
| `data-status` | [main.js:1120](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1120) |
| `data-stock-field` | [main.js:1038](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1038) |
| `data-store` | [main.js:537](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:537) |
| `data-store-active` | [main.js:617](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:617) |
| `data-store-category-create` | [main.js:560](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:560) |
| `data-store-category-delete` | [main.js:563](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:563) |
| `data-store-commission` | [main.js:1118](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1118) |
| `data-store-cover` | [main.js:611](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:611) |
| `data-store-filter` | [main.js:599](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:599) |
| `data-store-hours` | [main.js:583](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:583) |
| `data-support-list` | [main.js:575](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:575) |
| `data-switch-role` | [main.js:576](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:576) |
| `data-table` | [main.js:1051](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1051) |
| `data-target-id` | [main.js:977](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:977) |
| `data-ticket-admin` | [main.js:1120](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1120) |
| `data-ticket-back` | [main.js:493](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:493) |
| `data-ticket-compose` | [main.js:493](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:493) |
| `data-ticket-create` | [main.js:492](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:492) |
| `data-ticket-id` | [main.js:496](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:496) |
| `data-ticket-open` | [main.js:571](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:571) |
| `data-ticket-status` | [main.js:572](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:572) |
| `data-tracking-map` | [main.js:635](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:635) |
| `data-uf` | [main.js:468](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:468) |
| `data-update-store` | [main.js:600](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:600) |
| `data-user-ticket` | [main.js:492](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:492) |

## Contratos HTTP declarados

A rota efetiva combina o prefixo da classe e o sufixo do método. `SecurityConfig` decide acesso público/autenticado; `@PreAuthorize` e as verificações de titularidade no corpo restringem a operação. Uma anotação ausente não significa acesso público.

### AdminController

- [storeCategories](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:64)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/store-categories") @org.springframework.transaction.annotation.Transactional(readOnly = true) public List<AdminStoreCategoryView> storeCategories()`
- [createStoreCategory](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:74)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PostMapping("/store-categories") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public AdminStoreCategoryView createStoreCategory(@Valid @RequestBody StoreCategoryRequest request)`
- [deleteStoreCategory](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:89)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@DeleteMapping("/store-categories/{id}") @org.springframework.transaction.annotation.Transactional @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteStoreCategory(@PathVariable UUID id)`
- [serviceCities](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:101)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/service-cities") public List<ServiceCityView> serviceCities()`
- [createServiceCity](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:106)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PostMapping("/service-cities") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public ServiceCityView createServiceCity(@Valid @RequestBody ServiceCityRequest request)`
- [updateServiceCity](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:119)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PutMapping("/service-cities/{id}") @org.springframework.transaction.annotation.Transactional public ServiceCityView updateServiceCity(@PathVariable UUID id, @Valid @RequestBody ServiceCityRequest request)`
- [integrations](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:135)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/integrations") public List<IntegrationView> integrations()`
- [updateAccountProfile](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:152)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PutMapping("/accounts/{id}/profile") @org.springframework.transaction.annotation.Transactional public AdminAccountView updateAccountProfile(@PathVariable UUID id, @Valid @RequestBody AdminProfileUpdate request)`
- [updateStoreProfile](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:184)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PutMapping("/stores/{id}/profile") @org.springframework.transaction.annotation.Transactional public AdminStoreView updateStoreProfile(@PathVariable UUID id,@Valid @RequestBody br.com.fooddelivery.catalog.CatalogController.StoreRequest request)`
- [createOperator](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:198)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PostMapping("/operators") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public Account createOperator(@Valid @RequestBody OperatorRequest request)`
- [operators](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:220)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/operators") public List<OperatorView> operators()`
- [allAccounts](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:226)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/accounts") public List<AdminAccountView> allAccounts()`
- [setAccountActive](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:231)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/accounts/{id}/active") @org.springframework.transaction.annotation.Transactional public AdminAccountView setAccountActive(@PathVariable UUID id, @RequestBody AccountActivity request)`
- [setRoleActive](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:247)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/accounts/{id}/roles/{role}") @org.springframework.transaction.annotation.Transactional public AdminAccountView setRoleActive(@PathVariable UUID id, @PathVariable AccountRole role, @RequestBody AccountActivity request)`
- [resetPassword](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:263)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PostMapping("/accounts/{id}/password-reset") @org.springframework.transaction.annotation.Transactional public Map<String, Object> resetPassword(@PathVariable UUID id)`
- [pendingApplications](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:275)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/operator-applications") public List<OperatorView> pendingApplications()`
- [approveApplication](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:280)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operator-applications/{id}/approve") @org.springframework.transaction.annotation.Transactional public OperatorView approveApplication(@PathVariable UUID id)`
- [rejectApplication](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:291)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operator-applications/{id}/reject") @org.springframework.transaction.annotation.Transactional public OperatorView rejectApplication(@PathVariable UUID id)`
- [deactivate](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:302)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operators/{id}/deactivate") @org.springframework.transaction.annotation.Transactional public Map<String, String> deactivate(@PathVariable UUID id)`
- [activate](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:320)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operators/{id}/activate") @org.springframework.transaction.annotation.Transactional public Map<String, String> activate(@PathVariable UUID id)`
- [stores](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:333)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/stores") @org.springframework.transaction.annotation.Transactional(readOnly = true) public List<AdminStoreView> stores()`
- [approveStore](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:344)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/stores/{id}/approval") @org.springframework.transaction.annotation.Transactional public Store approveStore(@PathVariable UUID id, @RequestBody Approval request)`
- [setStoreActive](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:357)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/stores/{id}/active") @org.springframework.transaction.annotation.Transactional public Store setStoreActive(@PathVariable UUID id, @RequestBody StoreActivity request)`
- [setCommission](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:368)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/stores/{id}/commission") @org.springframework.transaction.annotation.Transactional public Map<String, Object> setCommission(@PathVariable UUID id, @Valid @RequestBody Commission request)`
- [auditEvents](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:378)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/audit-events") @org.springframework.transaction.annotation.Transactional(readOnly = true) public org.springframework.data.domain.Page<AdminAuditView> auditEvents( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size)`

### RefundAdminController

- [list](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/RefundAdminController.java:25)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/refunds") @PreAuthorize("hasRole('ADMIN')") public class RefundAdminController`
  - Método: `@GetMapping @Transactional(readOnly = true) public List<RefundCase> list()`

### HealthController

- [status](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/HealthController.java:11)
  - Classe: `@RestController @RequestMapping("/api/v1") public class HealthController`
  - Método: `@GetMapping("/status") public Map<String, String> status()`

### CatalogController

- [categories](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:31)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/store-categories") @Transactional(readOnly=true) public List<StoreCategories.Category> categories()`
- [locations](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:37)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/store-locations") @Transactional(readOnly=true) public List<LocationView> locations()`
- [stores](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:46)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores") @Transactional(readOnly=true) public List<StoreView> stores(@RequestParam(required=false) String city,@RequestParam(required=false) String state)`
- [items](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:53)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores/{id}/items") @Transactional(readOnly=true) public List<CatalogItemView> items(@PathVariable UUID id)`
- [createStore](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:58)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") @Transactional public StoreView createStore(@Valid @RequestBody StoreRequest req, Authentication auth)`
- [myStores](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:66)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/mine") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<StoreView> myStores(Authentication auth)`
- [resubmitStore](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:69)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/resubmit") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public StoreView resubmitStore(@PathVariable UUID id,Authentication auth)`
- [updateStore](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:71)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/stores/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public StoreView updateStore(@PathVariable UUID id, @Valid @RequestBody StoreRequest req, Authentication auth)`
- [merchantHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:77)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/{id}/hours") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public StoreOperatingHours.Configuration merchantHours(@PathVariable UUID id, Authentication auth)`
- [updateMerchantHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:79)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/stores/{id}/hours") @PreAuthorize("hasRole('MERCHANT')") @Transactional public StoreOperatingHours.Configuration updateMerchantHours(@PathVariable UUID id, @RequestBody StoreOperatingHours.Configuration request, Authentication auth)`
- [publicHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:83)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores/{id}/hours") @Transactional(readOnly=true) public StoreOperatingHours.Configuration publicHours(@PathVariable UUID id)`
- [createItem](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:93)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/items") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") public CatalogItemView createItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth)`
- [myStoreItems](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:99)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/{id}/items") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<CatalogItemView> myStoreItems(@PathVariable UUID id, Authentication auth)`
- [storeProfessionals](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:105)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores/{id}/professionals") @Transactional(readOnly=true) public List<ProfessionalView> storeProfessionals(@PathVariable UUID id)`
- [myProfessionals](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:110)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/{id}/professionals") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<ProfessionalView> myProfessionals(@PathVariable UUID id, Authentication auth)`
- [createProfessional](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:114)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/professionals") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public ProfessionalView createProfessional(@PathVariable UUID id,@Valid @RequestBody ProfessionalRequest request,Authentication auth)`
- [updateProfessional](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:118)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/professionals/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ProfessionalView updateProfessional(@PathVariable UUID id,@Valid @RequestBody ProfessionalRequest request,Authentication auth)`
- [setProfessionalActive](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:124)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PatchMapping("/merchant/professionals/{id}/active") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ProfessionalView setProfessionalActive(@PathVariable UUID id,@RequestBody ProfessionalActiveRequest request,Authentication auth)`
- [updateItem](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:130)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/items/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public CatalogItemView updateItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth)`
- [deactivateItem](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:135)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@DeleteMapping("/merchant/items/{id}") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional public void deactivateItem(@PathVariable UUID id,Authentication auth)`

### StoreAssetController

- [submit](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:40)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@PostMapping("/merchant/stores/{id}/cover") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public CoverView submit(@PathVariable UUID id,@Valid @RequestBody CoverRequest request,Authentication auth)`
- [mine](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:57)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@GetMapping("/merchant/stores/{id}/cover-submission") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public CoverState mine(@PathVariable UUID id,Authentication auth)`
- [ownPublishedImage](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:63)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@GetMapping("/merchant/stores/{id}/published-cover") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public ResponseEntity<FileSystemResource> ownPublishedImage(@PathVariable UUID id,Authentication auth)`
- [pending](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:70)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@GetMapping("/admin/store-covers") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public List<CoverView> pending()`
- [review](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:73)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@PatchMapping("/admin/store-covers/{id}/review") @PreAuthorize("hasRole('ADMIN')") @Transactional public CoverView review(@PathVariable UUID id,@Valid @RequestBody ReviewRequest request,Authentication auth)`
- [privateImage](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:88)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@GetMapping("/store-cover-submissions/{id}/image") @Transactional(readOnly=true) public ResponseEntity<FileSystemResource> privateImage(@PathVariable UUID id,Authentication auth)`
- [readCover](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java:95)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@GetMapping("/store-assets/{fileName}") @Transactional(readOnly=true) public ResponseEntity<FileSystemResource> readCover(@PathVariable String fileName)`

### OperationMessageController

- [orderMessages](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:50)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@GetMapping("/orders/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional(readOnly = true) public ThreadView orderMessages(@PathVariable UUID id, Authentication auth)`
- [sendOrderMessage](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:64)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@PostMapping("/orders/{id}/messages") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional public ThreadView sendOrderMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth)`
- [reservationMessages](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:80)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@GetMapping("/reservations/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional(readOnly = true) public ThreadView reservationMessages(@PathVariable UUID id, Authentication auth)`
- [sendReservationMessage](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:94)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@PostMapping("/reservations/{id}/messages") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional public ThreadView sendReservationMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth)`
- [deliveryMessages](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:110)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@GetMapping("/deliveries/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','COURIER')") @Transactional(readOnly = true) public ThreadView deliveryMessages(@PathVariable UUID id, Authentication auth)`
- [sendDeliveryMessage](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java:121)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@PostMapping("/deliveries/{id}/messages") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','COURIER')") @Transactional public ThreadView sendDeliveryMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth)`

### DemoController

- [status](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoController.java:24)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/demo") public class DemoController`
  - Método: `@GetMapping("/status") public Object status()`
- [messages](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoController.java:25)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/demo") public class DemoController`
  - Método: `@GetMapping("/messages") @PreAuthorize("hasRole('ADMIN')") public Object messages()`
- [payment](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/config/DemoController.java:28)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/demo") public class DemoController`
  - Método: `@PostMapping("/payments/{id}/{outcome}") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public Object payment(@PathVariable UUID id, @PathVariable String outcome, Authentication auth)`

### DeliveryController

- [offer](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:17)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@PostMapping("/merchant/orders/{orderId}/delivery") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") @org.springframework.transaction.annotation.Transactional public DeliveryTask offer(@PathVariable UUID orderId,Authentication a)`
- [offers](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:20)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@GetMapping("/courier/offers") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional(readOnly=true) public List<OfferView> offers()`
- [mine](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:21)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@GetMapping("/courier/deliveries") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional(readOnly=true) public List<AssignedView> mine(Authentication a)`
- [accept](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:22)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@PostMapping("/courier/offers/{id}/accept") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public DeliveryTask accept(@PathVariable UUID id,Authentication a)`
- [progress](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:24)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@PatchMapping("/courier/deliveries/{id}") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public AssignedView progress(@PathVariable UUID id,@Valid @RequestBody Update u,Authentication a)`
- [location](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java:26)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@PatchMapping("/courier/deliveries/{id}/location") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public void location(@PathVariable UUID id,@Valid @RequestBody LocationUpdate u,Authentication a)`

### AuthController

- [register](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:34)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) public Map<String, Object> register(@Valid @RequestBody RegisterRequest request)`
- [applyForOperator](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:41)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/operator-applications") @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.GONE) public Map<String, Object> applyForOperator(@Valid @RequestBody OperatorApplicationRequest request)`
- [login](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:47)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/login") public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request)`
- [logout](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:52)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/logout") public ResponseEntity<Void> logout()`
- [requestPasswordRecovery](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:57)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/password-recovery") @ResponseStatus(HttpStatus.ACCEPTED) public Map<String, String> requestPasswordRecovery(@Valid @RequestBody RecoveryRequest request)`
- [resetPassword](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java:64)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/password-recovery/reset") public Map<String, String> resetPassword(@Valid @RequestBody RecoveryResetRequest request)`

### CustomerAddressController

- [list](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:26)
  - Classe: `@RestController @RequestMapping("/api/v1/account/addresses") @PreAuthorize("hasRole('CUSTOMER')") public class CustomerAddressController`
  - Método: `@GetMapping @Transactional(readOnly = true) public List<AddressView> list(Authentication auth)`
- [create](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:32)
  - Classe: `@RestController @RequestMapping("/api/v1/account/addresses") @PreAuthorize("hasRole('CUSTOMER')") public class CustomerAddressController`
  - Método: `@PostMapping @ResponseStatus(HttpStatus.CREATED) @Transactional public AddressView create(@Valid @RequestBody AddressRequest request, Authentication auth)`
- [update](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:45)
  - Classe: `@RestController @RequestMapping("/api/v1/account/addresses") @PreAuthorize("hasRole('CUSTOMER')") public class CustomerAddressController`
  - Método: `@PutMapping("/{id}") @Transactional public AddressView update(@PathVariable UUID id, @Valid @RequestBody AddressRequest request, Authentication auth)`
- [makeDefault](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:57)
  - Classe: `@RestController @RequestMapping("/api/v1/account/addresses") @PreAuthorize("hasRole('CUSTOMER')") public class CustomerAddressController`
  - Método: `@PatchMapping("/{id}/default") @Transactional public AddressView makeDefault(@PathVariable UUID id, Authentication auth)`
- [delete](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java:66)
  - Classe: `@RestController @RequestMapping("/api/v1/account/addresses") @PreAuthorize("hasRole('CUSTOMER')") public class CustomerAddressController`
  - Método: `@DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional public void delete(@PathVariable UUID id, Authentication auth)`

### ProfileController

- [me](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:18)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@GetMapping("/me") public Account me(Authentication auth)`
- [update](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:19)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@PutMapping("/profile") public Account update(@Valid @RequestBody ProfileUpdate p,Authentication auth)`
- [applyForOperator](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:20)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@PostMapping("/operator-applications") @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.GONE) public Account applyForOperator(@Valid @RequestBody OperatorApplication p,Authentication auth)`
- [changePassword](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:21)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@PostMapping("/password") public org.springframework.http.ResponseEntity<java.util.Map<String,Object>> changePassword(@Valid @RequestBody PasswordChange p,Authentication auth)`
- [requestPhoneVerification](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:22)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@PostMapping("/phone-verification") @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.ACCEPTED) public java.util.Map<String,String> requestPhoneVerification(Authentication auth)`
- [confirmPhone](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java:23)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@PostMapping("/phone-verification/confirm") public java.util.Map<String,String> confirmPhone(@Valid @RequestBody PhoneVerification p,Authentication auth)`

### DemoGeocodingController

- [search](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/DemoGeocodingController.java:15)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/geography") public class DemoGeocodingController`
  - Método: `@GetMapping("/search") public Object search(@RequestParam String query)`
- [reverse](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/DemoGeocodingController.java:21)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/geography") public class DemoGeocodingController`
  - Método: `@GetMapping("/reverse") public Object reverse(@RequestParam double latitude,@RequestParam double longitude)`

### GeocodingController

- [search](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/GeocodingController.java:40)
  - Classe: `@RestController @org.springframework.context.annotation.Profile("!homologacao") @RequestMapping("/api/v1/geography") public class GeocodingController`
  - Método: `@GetMapping("/search") public JsonNode search(@RequestParam("query") String query)`
- [reverse](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/location/GeocodingController.java:57)
  - Classe: `@RestController @org.springframework.context.annotation.Profile("!homologacao") @RequestMapping("/api/v1/geography") public class GeocodingController`
  - Método: `@GetMapping("/reverse") public JsonNode reverse(@RequestParam double latitude, @RequestParam double longitude)`

### NotificationController

- [list](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java:17)
  - Classe: `@RestController @RequestMapping("/api/v1/account/notifications") public class NotificationController`
  - Método: `@GetMapping public List<AccountNotification> list(Authentication auth)`
- [read](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java:20)
  - Classe: `@RestController @RequestMapping("/api/v1/account/notifications") public class NotificationController`
  - Método: `@PatchMapping("/{id}/read") public AccountNotification read(@PathVariable UUID id, Authentication auth)`
- [clear](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java:25)
  - Classe: `@RestController @RequestMapping("/api/v1/account/notifications") public class NotificationController`
  - Método: `@DeleteMapping @ResponseStatus(HttpStatus.NO_CONTENT) public void clear(Authentication auth)`

### OrderController

- [create](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:45)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@PostMapping("/orders") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('CUSTOMER')") @Transactional public CheckoutResponse create(@Valid @RequestBody CreateOrder request, Authentication auth)`
- [mine](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:57)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@GetMapping("/orders/mine") @PreAuthorize("hasRole('CUSTOMER')") @Transactional(readOnly = true) public List<OrderView> mine(Authentication auth)`
- [merchantOrders](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:67)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@GetMapping("/merchant/orders") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly = true) public List<OrderView> merchantOrders(Authentication auth)`
- [cancelUnpaid](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:76)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@PostMapping("/orders/{id}/cancel") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public OrderView cancelUnpaid(@PathVariable UUID id, Authentication auth)`
- [requestCancellation](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:84)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@PostMapping("/orders/{id}/cancellation-request") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public OrderView requestCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationRequest request,Authentication auth)`
- [decideCancellation](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:92)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@PatchMapping("/merchant/orders/{id}/cancellation-request") @PreAuthorize("hasRole('MERCHANT')") @Transactional public OrderView decideCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationDecision request,Authentication auth)`
- [status](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java:100)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@PatchMapping("/merchant/orders/{id}/status") @PreAuthorize("hasRole('MERCHANT')") @Transactional public OrderView status(@PathVariable UUID id, @Valid @RequestBody StatusChange request, Authentication auth)`

### DevelopmentPaymentController

- [get](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/DevelopmentPaymentController.java:14)
  - Classe: `@RestController @RequestMapping("/api/v1/dev/payments") @Profile("dev-h2") public class DevelopmentPaymentController`
  - Método: `@GetMapping("/{id}") @PreAuthorize("hasRole('CUSTOMER')") public Payment get(@PathVariable UUID id,Authentication auth)`
- [approve](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/DevelopmentPaymentController.java:15)
  - Classe: `@RestController @RequestMapping("/api/v1/dev/payments") @Profile("dev-h2") public class DevelopmentPaymentController`
  - Método: `@PostMapping("/{id}/approve") @PreAuthorize("hasRole('CUSTOMER')") public Payment approve(@PathVariable UUID id,Authentication auth)`

### MercadoPagoAccountController

- [connect](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java:18)
  - Classe: `@RestController @RequestMapping("/api/v1") public class MercadoPagoAccountController`
  - Método: `@PostMapping("/merchant/stores/{storeId}/mercadopago/connect") @PreAuthorize("hasRole('MERCHANT')") public Map<String,String> connect(@PathVariable UUID storeId,Authentication auth)`
- [status](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java:24)
  - Classe: `@RestController @RequestMapping("/api/v1") public class MercadoPagoAccountController`
  - Método: `@GetMapping("/merchant/stores/{storeId}/mercadopago/status") @PreAuthorize("hasRole('MERCHANT')") public MarketplaceAccountService.ConnectionStatus status(@PathVariable UUID storeId,Authentication auth)`
- [callback](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java:28)
  - Classe: `@RestController @RequestMapping("/api/v1") public class MercadoPagoAccountController`
  - Método: `@GetMapping("/payments/mercadopago/callback") public RedirectView callback(@RequestParam(required=false) String code,@RequestParam(required=false) String state, @RequestParam(required=false) String error)`

### MercadoPagoWebhookController

- [receive](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoWebhookController.java:31)
  - Classe: `@RestController @RequestMapping("/api/v1/webhooks/mercadopago") public class MercadoPagoWebhookController`
  - Método: `@PostMapping @ResponseStatus(HttpStatus.OK) public Map<String,String> receive(@RequestParam(name="data.id",required=false) String dataId, @RequestParam(required=false) String type,@RequestParam(name="payment_id") UUID internalPaymentId, @RequestHeader(name="x-signature",required=false) String signature, @RequestHeader(name="x-request-id",required=false) String requestId)`

### PaymentController

- [createCheckout](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java:17)
  - Classe: `@RestController @RequestMapping("/api/v1/payments") @PreAuthorize("hasRole('CUSTOMER')") public class PaymentController`
  - Método: `@PostMapping("/{id}/checkout") public MercadoPagoCheckoutService.CheckoutView createCheckout(@PathVariable UUID id,Authentication auth)`
- [get](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java:22)
  - Classe: `@RestController @RequestMapping("/api/v1/payments") @PreAuthorize("hasRole('CUSTOMER')") public class PaymentController`
  - Método: `@GetMapping("/{id}") @org.springframework.transaction.annotation.Transactional(readOnly=true) public PaymentView get(@PathVariable UUID id,Authentication auth)`
- [sync](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java:31)
  - Classe: `@RestController @RequestMapping("/api/v1/payments") @PreAuthorize("hasRole('CUSTOMER')") public class PaymentController`
  - Método: `@PostMapping("/{id}/sync") public PaymentView sync(@PathVariable UUID id,@RequestBody SyncRequest request,Authentication auth)`

### PlatformRequestController

- [mine](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:24)
  - Classe: `@RestController @RequestMapping("/api/v1") public class PlatformRequestController`
  - Método: `@GetMapping("/account/platform-requests") @Transactional(readOnly=true) public List<RequestView> mine(Authentication auth)`
- [requestRole](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:26)
  - Classe: `@RestController @RequestMapping("/api/v1") public class PlatformRequestController`
  - Método: `@PostMapping("/account/platform-requests/roles") @ResponseStatus(HttpStatus.CREATED) @Transactional public RequestView requestRole(@Valid @RequestBody RoleRequest body,Authentication auth)`
- [requestCity](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:36)
  - Classe: `@RestController @RequestMapping("/api/v1") public class PlatformRequestController`
  - Método: `@PostMapping("/account/platform-requests/cities") @ResponseStatus(HttpStatus.CREATED) @Transactional public RequestView requestCity(@Valid @RequestBody CityRequest body,Authentication auth)`
- [all](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:45)
  - Classe: `@RestController @RequestMapping("/api/v1") public class PlatformRequestController`
  - Método: `@GetMapping("/admin/platform-requests") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public List<RequestView> all()`
- [review](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java:48)
  - Classe: `@RestController @RequestMapping("/api/v1") public class PlatformRequestController`
  - Método: `@PatchMapping("/admin/platform-requests/{id}/review") @PreAuthorize("hasRole('ADMIN')") @Transactional public RequestView review(@PathVariable UUID id,@Valid @RequestBody Review body,Authentication auth)`

### DevelopmentReservationPaymentController

- [approve](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/DevelopmentReservationPaymentController.java:17)
  - Classe: `@RestController @RequestMapping("/api/v1/dev/reservations") @Profile("dev-h2") public class DevelopmentReservationPaymentController`
  - Método: `@PostMapping("/{id}/approve-payment") @PreAuthorize("hasRole('CUSTOMER')") public Object approve(@PathVariable UUID id,Authentication auth)`

### ReservationController

- [create](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:62)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PostMapping("/reservations") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('CUSTOMER')") @Transactional public CreateResponse create(@Valid @RequestBody Request request, Authentication auth)`
- [mine](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:110)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@GetMapping("/reservations/mine") @PreAuthorize("hasRole('CUSTOMER')") @Transactional(readOnly = true) public List<ReservationView> mine(Authentication auth)`
- [merchant](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:120)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@GetMapping("/merchant/reservations") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly = true) public List<ReservationView> merchant(Authentication auth)`
- [cancelUnpaid](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:130)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PostMapping("/reservations/{id}/cancel") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public ReservationView cancelUnpaid(@PathVariable UUID id, Authentication auth)`
- [requestCancellation](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:154)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PostMapping("/reservations/{id}/cancellation-request") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public ReservationView requestCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationNote request,Authentication auth)`
- [decideCancellation](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:173)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PatchMapping("/merchant/reservations/{id}/cancellation-request") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ReservationView decideCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationDecision request,Authentication auth)`
- [update](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:188)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PatchMapping("/merchant/reservations/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ReservationView update(@PathVariable UUID id, @Valid @RequestBody Change change, Authentication auth)`

### SupportTicketController

- [mine](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:31)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@GetMapping("/support/tickets/mine") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER','ADMIN')") @Transactional(readOnly=true) public List<TicketView> mine(Authentication auth)`
- [create](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:39)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@PostMapping("/support/tickets") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')") @Transactional public TicketDetail create(@Valid @RequestBody CreateRequest request, Authentication auth)`
- [mineDetail](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:54)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@GetMapping("/support/tickets/{id}") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')") @Transactional(readOnly=true) public TicketDetail mineDetail(@PathVariable UUID id, Authentication auth)`
- [reply](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:59)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@PostMapping("/support/tickets/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')") @Transactional public TicketDetail reply(@PathVariable UUID id,@Valid @RequestBody MessageRequest request,Authentication auth)`
- [all](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:70)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@GetMapping("/admin/support-tickets") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public List<TicketView> all()`
- [adminDetail](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:75)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@GetMapping("/admin/support-tickets/{id}") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public TicketDetail adminDetail(@PathVariable UUID id)`
- [adminReply](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:80)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@PostMapping("/admin/support-tickets/{id}/messages") @PreAuthorize("hasRole('ADMIN')") @Transactional public TicketDetail adminReply(@PathVariable UUID id,@Valid @RequestBody MessageRequest request,Authentication auth)`
- [updateStatus](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java:91)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@PatchMapping("/admin/support-tickets/{id}/status") @PreAuthorize("hasRole('ADMIN')") @Transactional public TicketView updateStatus(@PathVariable UUID id,@Valid @RequestBody StatusRequest request)`

## Migrações e razão da separação

Flyway executa a sequência V1–V24. V9 tem variantes H2/PostgreSQL para diferenças de mecanismo. O perfil escolhe uma variante, nunca as duas. Os demais scripts são comuns. Hibernate valida o resultado; não é proprietário do esquema.

| Versão / arquivo | DDL e finalidade identificável |
|---|---|
| [V1__initial_schema.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V1__initial_schema.sql) | 13 instruções; relações/índices: `accounts`, `catalog_items`, `customer_orders`, `delivery_tasks`, `order_lines`, `payments`, `reservations`, `stores` |
| [V2__account_profiles_and_notifications.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V2__account_profiles_and_notifications.sql) | 7 instruções; relações/índices: `account_notifications`, `accounts` |
| [V3__marketplace_payment_support.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V3__marketplace_payment_support.sql) | 13 instruções; relações/índices: `customer_orders`, `marketplace_payment_accounts`, `payments`, `stores` |
| [V4__payment_expiration_and_reservation_capacity.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V4__payment_expiration_and_reservation_capacity.sql) | 4 instruções; relações/índices: `reservation_slots`, `reservations` |
| [V5__refund_tracking.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V5__refund_tracking.sql) | 5 instruções; relações/índices: `payments` |
| [V6__operator_applications_and_store_branding.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V6__operator_applications_and_store_branding.sql) | 4 instruções; relações/índices: `accounts`, `stores` |
| [V7__customer_address_book.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V7__customer_address_book.sql) | 2 instruções; relações/índices: `customer_addresses` |
| [V8__delivery_location_sharing.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V8__delivery_location_sharing.sql) | 3 instruções; relações/índices: `delivery_tasks` |
| [V9__account_security_and_store_location.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-h2/V9__account_security_and_store_location.sql) | 13 instruções; relações/índices: `accounts`, `stores` |
| [V9__account_security_and_store_location.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-postgres/V9__account_security_and_store_location.sql) | 13 instruções; relações/índices: `accounts`, `stores` |
| [V10__account_credential_version.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V10__account_credential_version.sql) | 1 instruções; relações/índices: `accounts` |
| [V11__operation_messages.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V11__operation_messages.sql) | 6 instruções; relações/índices: `customer_orders`, `operation_messages`, `reservations` |
| [V12__support_tickets.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V12__support_tickets.sql) | 5 instruções; relações/índices: `support_ticket_messages`, `support_tickets` |
| [V13__account_platform_requests.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V13__account_platform_requests.sql) | 6 instruções; relações/índices: `platform_requests`, `service_cities`, `stores` |
| [V14__password_recovery.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V14__password_recovery.sql) | 3 instruções; relações/índices: `accounts`, `recovery_codes` |
| [V15__service_payment_options.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V15__service_payment_options.sql) | 2 instruções; relações/índices: `catalog_items`, `reservations` |
| [V16__administrator_store_categories.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V16__administrator_store_categories.sql) | 2 instruções; relações/índices: `store_categories` |
| [V17__store_professionals_and_soft_delete.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V17__store_professionals_and_soft_delete.sql) | 6 instruções; relações/índices: `reservation_slots`, `reservations`, `store_professionals` |
| [V18__notification_navigation_targets.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V18__notification_navigation_targets.sql) | 3 instruções; relações/índices: `account_notifications` |
| [V19__store_operating_hours.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V19__store_operating_hours.sql) | 3 instruções; relações/índices: `stores` |
| [V20__reservation_capacity_and_service_duration.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V20__reservation_capacity_and_service_duration.sql) | 15 instruções; relações/índices: `catalog_items`, `reservation_slots`, `reservations` |
| [V21__customer_cancellation_requests.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V21__customer_cancellation_requests.sql) | 8 instruções; relações/índices: `customer_orders`, `reservations` |
| [V22__store_map_coordinates.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V22__store_map_coordinates.sql) | 2 instruções; relações/índices: `stores` |
| [V23__admin_audit_events.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V23__admin_audit_events.sql) | 3 instruções; relações/índices: `admin_audit_events` |
| [V24__store_cover_moderation.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V24__store_cover_moderation.sql) | 3 instruções; relações/índices: `store_cover_submissions` |

## Arquivos de operação

| Arquivo | Papel e avaliação |
|---|---|
| `scripts/dev.py` | Supervisor local; identifica serviços existentes para não iniciar cópias. Seis testes unitários cobrem sondagem/reutilização. |
| `scripts/homologacao.py` | Supervisor isolado; não lê `.env`, gera chave própria, valida portas e inicia fixtures. SO_REUSEADDR permite reinício após fechamento sem compartilhar listener ocupado. |
| `Frontend/scripts/start.py` | Servidor local de arquivos e proxy exclusivo de demonstração; não é servidor de borda para produção. |
| `Backend/scripts/backup-db.sh` | Dump e checksum. Arquivos privados; teste de restauração independente ainda necessário. |
| `Backend/scripts/restore-db.sh` | Confere checksum e confirmação de banco; é destrutivo no destino escolhido. Não foi executado no banco real. |
| `scripts/test_homologacao.py` | Contratos da simulação, cinco logins por execução; não rodar repetidamente junto de outros logins sem considerar rate limit. |
| `scripts/test_jornadas.py` | CRUD, autorização, suporte, administração e sequência completa de entrega em fixtures. |
| `.github/workflows` | Qualidade/segurança declaradas; presença de YAML não demonstra execução bem-sucedida no GitHub. |
| `Frontend/styles.css` / `theme.css` | Base e refinamentos de componentes, responsividade e foco; estilos sobrepostos exigem inspeção do resultado computado. |
| `Frontend/index.html` / `demo.html` | Entrada normal e caixa de mensagens fictícias; metadados e carregamento de módulos. |
| `Frontend/runtime-config.js` | Em estático mantém modo normal; o servidor fictício fornece marcador explícito de homologação. |
| `Backend/src/main/resources/application*.yml` | Configuração por perfil; credenciais privadas e ambiente não devem ser publicados como documentação. |
