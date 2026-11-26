# Inventário de interface, HTTP e migrações

> [NÃO VERIFICADO] Escopo da revisão de 25/11: instruções e resultados históricos sem evidência individual nesta página aguardam confirmação. Os testes repetidos têm comando e resultado na [matriz de aceite](Aceite_2026-11-25.md).

Inventário estático da árvore de código documentada; uma linha aqui não significa que a ação foi executada em todas as combinações. Consulte a matriz de validação.

## Atributos de interação do frontend

Os atributos `data-*` conectam botões, formulários e estados aos handlers documentados na referência. Alguns são seletores de apresentação, não ações. Valores dinâmicos são escapados no template e revalidados no servidor.

| Atributo | Primeira ocorrência |
|---|---|
| `data-about-modal` | [authViews.js:33](../Frontend/src/views/authViews.js#L33) |
| `data-about-us` | [authViews.js:8](../Frontend/src/views/authViews.js#L8) |
| `data-accept-delivery` | [deliveryBindings.js:3](../Frontend/src/deliveryBindings.js#L3) |
| `data-accepted` | [main.js:819](../Frontend/src/main.js#L819) |
| `data-account-active` | [main.js:570](../Frontend/src/main.js#L570) |
| `data-account-role` | [main.js:572](../Frontend/src/main.js#L572) |
| `data-account-section` | [main.js:537](../Frontend/src/main.js#L537) |
| `data-action` | [shoppingViews.js:49](../Frontend/src/features/shoppingViews.js#L49) |
| `data-active` | [adminViews.js:41](../Frontend/src/views/adminViews.js#L41) |
| `data-add-address` | [shoppingViews.js:33](../Frontend/src/features/shoppingViews.js#L33) |
| `data-add-item` | [shoppingViews.js:13](../Frontend/src/features/shoppingViews.js#L13) |
| `data-add-shift` | [storeHours.js:20](../Frontend/src/storeHours.js#L20) |
| `data-address-form` | [shoppingViews.js:35](../Frontend/src/features/shoppingViews.js#L35) |
| `data-admin-account-edit` | [adminActions.js:23](../Frontend/src/features/adminActions.js#L23) |
| `data-admin-area` | [adminViews.js:6](../Frontend/src/views/adminViews.js#L6) |
| `data-admin-definition-section` | [main.js:538](../Frontend/src/main.js#L538) |
| `data-admin-edit-account` | [main.js:571](../Frontend/src/main.js#L571) |
| `data-admin-edit-store` | [main.js:533](../Frontend/src/main.js#L533) |
| `data-admin-hours` | [adminStoreEditor.js:37](../Frontend/src/adminStoreEditor.js#L37) |
| `data-admin-mfa` | [main.js:506](../Frontend/src/main.js#L506) |
| `data-admin-page` | [adminPagination.js:21](../Frontend/src/adminPagination.js#L21) |
| `data-admin-profile-save` | [main.js:526](../Frontend/src/main.js#L526) |
| `data-admin-team-section` | [main.js:598](../Frontend/src/main.js#L598) |
| `data-admin-ticket` | [supportActions.js:18](../Frontend/src/features/supportActions.js#L18) |
| `data-approve-store` | [adminActions.js:48](../Frontend/src/features/adminActions.js#L48) |
| `data-approved` | [courierFinance.js:9](../Frontend/src/courierFinance.js#L9) |
| `data-auth-mode` | [authViews.js:23](../Frontend/src/views/authViews.js#L23) |
| `data-available-slots` | [shoppingViews.js:66](../Frontend/src/features/shoppingViews.js#L66) |
| `data-back` | [courierFinance.js:10](../Frontend/src/courierFinance.js#L10) |
| `data-booking-date` | [bookingAvailability.js:6](../Frontend/src/bookingAvailability.js#L6) |
| `data-booking-policy` | [main.js:504](../Frontend/src/main.js#L504) |
| `data-cancel-address` | [shoppingViews.js:35](../Frontend/src/features/shoppingViews.js#L35) |
| `data-cancel-logout` | [main.js:986](../Frontend/src/main.js#L986) |
| `data-cancel-order` | [main.js:947](../Frontend/src/main.js#L947) |
| `data-cancel-payout` | [courierFinance.js:9](../Frontend/src/courierFinance.js#L9) |
| `data-cancel-reservation` | [main.js:948](../Frontend/src/main.js#L948) |
| `data-cart-mode` | [shoppingViews.js:32](../Frontend/src/features/shoppingViews.js#L32) |
| `data-catalog-item-update` | [main.js:866](../Frontend/src/main.js#L866) |
| `data-category` | [main.js:520](../Frontend/src/main.js#L520) |
| `data-category-select` | [main.js:519](../Frontend/src/main.js#L519) |
| `data-cep` | [adminActions.js:23](../Frontend/src/features/adminActions.js#L23) |
| `data-cep-feedback` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-chat-draft` | [main.js:607](../Frontend/src/main.js#L607) |
| `data-chat-form` | [main.js:606](../Frontend/src/main.js#L606) |
| `data-chat-id` | [main.js:731](../Frontend/src/main.js#L731) |
| `data-chat-panel` | [main.js:724](../Frontend/src/main.js#L724) |
| `data-chat-type` | [main.js:731](../Frontend/src/main.js#L731) |
| `data-checkout` | [shoppingViews.js:40](../Frontend/src/features/shoppingViews.js#L40) |
| `data-checkout-note` | [shoppingViews.js:37](../Frontend/src/features/shoppingViews.js#L37) |
| `data-checkout-pay` | [main.js:590](../Frontend/src/main.js#L590) |
| `data-city` | [main.js:454](../Frontend/src/main.js#L454) |
| `data-city-count` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-city-list` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-city-request` | [main.js:546](../Frontend/src/main.js#L546) |
| `data-clear-notifications` | [main.js:932](../Frontend/src/main.js#L932) |
| `data-close` | [storeHours.js:14](../Frontend/src/storeHours.js#L14) |
| `data-close-about` | [authViews.js:33](../Frontend/src/views/authViews.js#L33) |
| `data-close-admin-edit` | [accessibility.js:33](../Frontend/src/accessibility.js#L33) |
| `data-close-cart` | [accessibility.js:33](../Frontend/src/accessibility.js#L33) |
| `data-close-catalog-edit` | [accessibility.js:33](../Frontend/src/accessibility.js#L33) |
| `data-close-chat` | [main.js:608](../Frontend/src/main.js#L608) |
| `data-close-item` | [main.js:866](../Frontend/src/main.js#L866) |
| `data-close-location` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-close-modal` | [accessibility.js:33](../Frontend/src/accessibility.js#L33) |
| `data-close-support` | [accessibility.js:33](../Frontend/src/accessibility.js#L33) |
| `data-close-today` | [storeHours.js:38](../Frontend/src/storeHours.js#L38) |
| `data-code` | [courierFinance.js:15](../Frontend/src/courierFinance.js#L15) |
| `data-confirm-logout` | [main.js:986](../Frontend/src/main.js#L986) |
| `data-connect-mercadopago` | [main.js:944](../Frontend/src/main.js#L944) |
| `data-courier-finance` | [main.js:507](../Frontend/src/main.js#L507) |
| `data-cover-review` | [main.js:578](../Frontend/src/main.js#L578) |
| `data-create-item` | [catalogBindings.js:3](../Frontend/src/catalogBindings.js#L3) |
| `data-create-operator` | [main.js:567](../Frontend/src/main.js#L567) |
| `data-create-store` | [main.js:548](../Frontend/src/main.js#L548) |
| `data-decide` | [courierFinance.js:9](../Frontend/src/courierFinance.js#L9) |
| `data-decision` | [main.js:887](../Frontend/src/main.js#L887) |
| `data-default-address` | [main.js:953](../Frontend/src/main.js#L953) |
| `data-delete-address` | [main.js:954](../Frontend/src/main.js#L954) |
| `data-delivery-card` | [main.js:795](../Frontend/src/main.js#L795) |
| `data-delivery-status` | [main.js:585](../Frontend/src/main.js#L585) |
| `data-dismiss` | [storeHours.js:30](../Frontend/src/storeHours.js#L30) |
| `data-dismiss-temp-password` | [main.js:574](../Frontend/src/main.js#L574) |
| `data-enabled` | [adminViews.js:50](../Frontend/src/views/adminViews.js#L50) |
| `data-enrollment` | [adminSecurity.js:13](../Frontend/src/adminSecurity.js#L13) |
| `data-exception-editor` | [storeHours.js:38](../Frontend/src/storeHours.js#L38) |
| `data-forgot-password` | [authViews.js:31](../Frontend/src/views/authViews.js#L31) |
| `data-help` | [main.js:380](../Frontend/src/main.js#L380) |
| `data-id` | [shoppingViews.js:31](../Frontend/src/features/shoppingViews.js#L31) |
| `data-item-` | [main.js:845](../Frontend/src/main.js#L845) |
| `data-item-activate` | [main.js:553](../Frontend/src/main.js#L553) |
| `data-item-deactivate` | [main.js:552](../Frontend/src/main.js#L552) |
| `data-item-edit` | [catalogBindings.js:4](../Frontend/src/catalogBindings.js#L4) |
| `data-item-kind` | [main.js:557](../Frontend/src/main.js#L557) |
| `data-location-cep` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-location-clear` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-location-confirm` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-location-gps` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-location-map` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-location-picker` | [main.js:386](../Frontend/src/main.js#L386) |
| `data-location-result` | [main.js:466](../Frontend/src/main.js#L466) |
| `data-location-results` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-location-search` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-location-selection` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-location-selection-label` | [main.js:443](../Frontend/src/main.js#L443) |
| `data-logout` | [main.js:388](../Frontend/src/main.js#L388) |
| `data-lookup-cep` | [shoppingViews.js:35](../Frontend/src/features/shoppingViews.js#L35) |
| `data-map-canvas` | [storeMaps.js:33](../Frontend/src/storeMaps.js#L33) |
| `data-map-clear` | [storeMaps.js:33](../Frontend/src/storeMaps.js#L33) |
| `data-map-content` | [storeMaps.js:33](../Frontend/src/storeMaps.js#L33) |
| `data-map-feedback` | [storeMaps.js:33](../Frontend/src/storeMaps.js#L33) |
| `data-map-results` | [storeMaps.js:33](../Frontend/src/storeMaps.js#L33) |
| `data-map-search` | [storeMaps.js:33](../Frontend/src/storeMaps.js#L33) |
| `data-map-toggle` | [storeMaps.js:33](../Frontend/src/storeMaps.js#L33) |
| `data-mask` | [adminActions.js:23](../Frontend/src/features/adminActions.js#L23) |
| `data-merchant-store-select` | [main.js:550](../Frontend/src/main.js#L550) |
| `data-mobile-more` | [main.js:383](../Frontend/src/main.js#L383) |
| `data-money` | [courierFinance.js:8](../Frontend/src/courierFinance.js#L8) |
| `data-name` | [adminViews.js:42](../Frontend/src/views/adminViews.js#L42) |
| `data-next` | [courierFinance.js:16](../Frontend/src/courierFinance.js#L16) |
| `data-no-show-warning` | [main.js:836](../Frontend/src/main.js#L836) |
| `data-notification-target` | [main.js:933](../Frontend/src/main.js#L933) |
| `data-offer-delivery` | [main.js:580](../Frontend/src/main.js#L580) |
| `data-open` | [storeHours.js:14](../Frontend/src/storeHours.js#L14) |
| `data-open-chat-id` | [courierViews.js:8](../Frontend/src/views/courierViews.js#L8) |
| `data-open-chat-type` | [main.js:605](../Frontend/src/main.js#L605) |
| `data-operating-hours` | [main.js:551](../Frontend/src/main.js#L551) |
| `data-operating-hours-editor` | [storeHours.js:38](../Frontend/src/storeHours.js#L38) |
| `data-operator-active` | [main.js:569](../Frontend/src/main.js#L569) |
| `data-operator-application` | [main.js:568](../Frontend/src/main.js#L568) |
| `data-operator-request` | [main.js:545](../Frontend/src/main.js#L545) |
| `data-order-cancellation-decision` | [main.js:951](../Frontend/src/main.js#L951) |
| `data-order-card` | [main.js:795](../Frontend/src/main.js#L795) |
| `data-order-status` | [main.js:579](../Frontend/src/main.js#L579) |
| `data-page` | [adminPagination.js:21](../Frontend/src/adminPagination.js#L21) |
| `data-paid` | [courierFinance.js:9](../Frontend/src/courierFinance.js#L9) |
| `data-password-change` | [main.js:534](../Frontend/src/main.js#L534) |
| `data-pay-after-service` | [main.js:558](../Frontend/src/main.js#L558) |
| `data-phone-verify-confirm` | [main.js:536](../Frontend/src/main.js#L536) |
| `data-phone-verify-request` | [main.js:535](../Frontend/src/main.js#L535) |
| `data-platform-review` | [main.js:547](../Frontend/src/main.js#L547) |
| `data-policy` | [courierFinance.js:15](../Frontend/src/courierFinance.js#L15) |
| `data-prev` | [courierFinance.js:16](../Frontend/src/courierFinance.js#L16) |
| `data-private-fields` | [adminSecurity.js:6](../Frontend/src/adminSecurity.js#L6) |
| `data-private-integrations` | [main.js:505](../Frontend/src/main.js#L505) |
| `data-professional-active` | [main.js:556](../Frontend/src/main.js#L556) |
| `data-professional-create` | [main.js:554](../Frontend/src/main.js#L554) |
| `data-professional-hours` | [main.js:555](../Frontend/src/main.js#L555) |
| `data-professional-schedule` | [bookingAvailability.js:17](../Frontend/src/bookingAvailability.js#L17) |
| `data-profile-save` | [main.js:525](../Frontend/src/main.js#L525) |
| `data-profile-store-map` | [shoppingViews.js:8](../Frontend/src/features/shoppingViews.js#L8) |
| `data-qty` | [shoppingViews.js:31](../Frontend/src/features/shoppingViews.js#L31) |
| `data-recovery-back` | [authViews.js:21](../Frontend/src/views/authViews.js#L21) |
| `data-recovery-channel` | [authViews.js:38](../Frontend/src/views/authViews.js#L38) |
| `data-refresh` | [courierFinance.js:16](../Frontend/src/courierFinance.js#L16) |
| `data-refresh-audit` | [main.js:576](../Frontend/src/main.js#L576) |
| `data-refresh-integrations` | [main.js:575](../Frontend/src/main.js#L575) |
| `data-remove-key` | [adminSecurity.js:7](../Frontend/src/adminSecurity.js#L7) |
| `data-remove-shift` | [storeHours.js:14](../Frontend/src/storeHours.js#L14) |
| `data-reopen-store` | [main.js:587](../Frontend/src/main.js#L587) |
| `data-request` | [courierFinance.js:15](../Frontend/src/courierFinance.js#L15) |
| `data-request-order-cancellation` | [main.js:949](../Frontend/src/main.js#L949) |
| `data-request-reservation-cancellation` | [main.js:950](../Frontend/src/main.js#L950) |
| `data-reservation-cancellation-decision` | [main.js:819](../Frontend/src/main.js#L819) |
| `data-reservation-card` | [main.js:795](../Frontend/src/main.js#L795) |
| `data-reservation-status` | [main.js:581](../Frontend/src/main.js#L581) |
| `data-reserve` | [shoppingViews.js:13](../Frontend/src/features/shoppingViews.js#L13) |
| `data-reset-password` | [main.js:573](../Frontend/src/main.js#L573) |
| `data-resubmit-store` | [main.js:549](../Frontend/src/main.js#L549) |
| `data-resume-order` | [main.js:945](../Frontend/src/main.js#L945) |
| `data-resume-reservation` | [main.js:946](../Frontend/src/main.js#L946) |
| `data-retry-page` | [main.js:431](../Frontend/src/main.js#L431) |
| `data-risk-ack-field` | [main.js:836](../Frontend/src/main.js#L836) |
| `data-role` | [adminViews.js:42](../Frontend/src/views/adminViews.js#L42) |
| `data-save-exception` | [storeHours.js:38](../Frontend/src/storeHours.js#L38) |
| `data-scroll-stores` | [main.js:589](../Frontend/src/main.js#L589) |
| `data-section` | [main.js:592](../Frontend/src/main.js#L592) |
| `data-service-city-create` | [main.js:527](../Frontend/src/main.js#L527) |
| `data-service-city-toggle` | [main.js:530](../Frontend/src/main.js#L530) |
| `data-service-city-update` | [main.js:529](../Frontend/src/main.js#L529) |
| `data-service-duration-field` | [main.js:835](../Frontend/src/main.js#L835) |
| `data-service-payment-field` | [main.js:835](../Frontend/src/main.js#L835) |
| `data-share-location` | [main.js:586](../Frontend/src/main.js#L586) |
| `data-shift` | [storeHours.js:18](../Frontend/src/storeHours.js#L18) |
| `data-shifts` | [storeHours.js:20](../Frontend/src/storeHours.js#L20) |
| `data-status` | [adminViews.js:43](../Frontend/src/views/adminViews.js#L43) |
| `data-stock-field` | [main.js:835](../Frontend/src/main.js#L835) |
| `data-store` | [main.js:522](../Frontend/src/main.js#L522) |
| `data-store-active` | [main.js:583](../Frontend/src/main.js#L583) |
| `data-store-category-create` | [main.js:528](../Frontend/src/main.js#L528) |
| `data-store-category-delete` | [main.js:531](../Frontend/src/main.js#L531) |
| `data-store-commission` | [main.js:943](../Frontend/src/main.js#L943) |
| `data-store-cover` | [main.js:577](../Frontend/src/main.js#L577) |
| `data-store-directory-map` | [main.js:509](../Frontend/src/main.js#L509) |
| `data-store-filter` | [main.js:565](../Frontend/src/main.js#L565) |
| `data-store-map-picker` | [adminStoreEditor.js:42](../Frontend/src/adminStoreEditor.js#L42) |
| `data-support-list` | [main.js:543](../Frontend/src/main.js#L543) |
| `data-switch-role` | [main.js:544](../Frontend/src/main.js#L544) |
| `data-table` | [merchantViews.js:49](../Frontend/src/views/merchantViews.js#L49) |
| `data-target-id` | [accountViews.js:33](../Frontend/src/views/accountViews.js#L33) |
| `data-ticket-admin` | [adminViews.js:43](../Frontend/src/views/adminViews.js#L43) |
| `data-ticket-back` | [supportActions.js:13](../Frontend/src/features/supportActions.js#L13) |
| `data-ticket-compose` | [supportActions.js:13](../Frontend/src/features/supportActions.js#L13) |
| `data-ticket-create` | [supportActions.js:12](../Frontend/src/features/supportActions.js#L12) |
| `data-ticket-id` | [supportActions.js:18](../Frontend/src/features/supportActions.js#L18) |
| `data-ticket-open` | [main.js:539](../Frontend/src/main.js#L539) |
| `data-ticket-status` | [main.js:540](../Frontend/src/main.js#L540) |
| `data-tracking-map` | [main.js:601](../Frontend/src/main.js#L601) |
| `data-uf` | [main.js:454](../Frontend/src/main.js#L454) |
| `data-update-store` | [main.js:566](../Frontend/src/main.js#L566) |
| `data-user-ticket` | [supportActions.js:12](../Frontend/src/features/supportActions.js#L12) |
| `data-whole-day` | [storeHours.js:20](../Frontend/src/storeHours.js#L20) |

## Contratos HTTP declarados

A rota efetiva combina o prefixo da classe e o sufixo do método. `SecurityConfig` decide acesso público/autenticado; `@PreAuthorize` e as verificações de titularidade no corpo restringem a operação. Uma anotação ausente não significa acesso público.

### AdminCategoryController

- [storeCategories](../Backend/src/main/java/br/com/fooddelivery/admin/AdminCategoryController.java#L27)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminCategoryController`
  - Método: `@GetMapping("/store-categories") @org.springframework.transaction.annotation.Transactional(readOnly = true) public List<AdminStoreCategoryView> storeCategories()`
- [createStoreCategory](../Backend/src/main/java/br/com/fooddelivery/admin/AdminCategoryController.java#L37)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminCategoryController`
  - Método: `@PostMapping("/store-categories") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public AdminStoreCategoryView createStoreCategory(@Valid @RequestBody StoreCategoryRequest request)`
- [deleteStoreCategory](../Backend/src/main/java/br/com/fooddelivery/admin/AdminCategoryController.java#L52)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminCategoryController`
  - Método: `@DeleteMapping("/store-categories/{id}") @org.springframework.transaction.annotation.Transactional @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteStoreCategory(@PathVariable UUID id)`

### AdminController

- [updateAccountProfile](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L50)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PutMapping("/accounts/{id}/profile") @org.springframework.transaction.annotation.Transactional public AdminAccountView updateAccountProfile(@PathVariable UUID id, @Valid @RequestBody AdminProfileUpdate request)`
- [updateStoreProfile](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L82)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PutMapping("/stores/{id}/profile") @org.springframework.transaction.annotation.Transactional public AdminStoreView updateStoreProfile(@PathVariable UUID id,@Valid @RequestBody br.com.fooddelivery.catalog.CatalogController.StoreRequest request)`
- [createOperator](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L97)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PostMapping("/operators") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public Account createOperator(@Valid @RequestBody OperatorRequest request)`
- [operators](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L119)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/operators") public List<OperatorView> operators()`
- [allAccounts](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L124)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/accounts") public List<AdminAccountView> allAccounts()`
- [operatorPage](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L129)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/operators/page") @org.springframework.transaction.annotation.Transactional(readOnly = true) public AdminPage<OperatorView> operatorPage(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25") int size)`
- [accountPage](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L135)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/accounts/page") @org.springframework.transaction.annotation.Transactional(readOnly = true) public AdminPage<AdminAccountView> accountPage(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25") int size)`
- [storePage](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L141)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/stores/page") @org.springframework.transaction.annotation.Transactional(readOnly = true) public AdminPage<AdminStoreView> storePage(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25") int size, @RequestParam(defaultValue="ALL") String status)`
- [setAccountActive](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L156)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/accounts/{id}/active") @org.springframework.transaction.annotation.Transactional public AdminAccountView setAccountActive(@PathVariable UUID id, @RequestBody AccountActivity request)`
- [setRoleActive](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L172)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/accounts/{id}/roles/{role}") @org.springframework.transaction.annotation.Transactional public AdminAccountView setRoleActive(@PathVariable UUID id, @PathVariable AccountRole role, @RequestBody AccountActivity request)`
- [resetPassword](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L188)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PostMapping("/accounts/{id}/password-reset") @org.springframework.transaction.annotation.Transactional public Map<String, Object> resetPassword(@PathVariable UUID id)`
- [pendingApplications](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L200)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/operator-applications") public List<OperatorView> pendingApplications()`
- [approveApplication](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L205)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operator-applications/{id}/approve") @org.springframework.transaction.annotation.Transactional public OperatorView approveApplication(@PathVariable UUID id)`
- [rejectApplication](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L216)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operator-applications/{id}/reject") @org.springframework.transaction.annotation.Transactional public OperatorView rejectApplication(@PathVariable UUID id)`
- [deactivate](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L227)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operators/{id}/deactivate") @org.springframework.transaction.annotation.Transactional public Map<String, String> deactivate(@PathVariable UUID id)`
- [activate](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L245)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operators/{id}/activate") @org.springframework.transaction.annotation.Transactional public Map<String, String> activate(@PathVariable UUID id)`
- [stores](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L258)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/stores") @org.springframework.transaction.annotation.Transactional(readOnly = true) public List<AdminStoreView> stores()`
- [approveStore](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L269)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/stores/{id}/approval") @org.springframework.transaction.annotation.Transactional public Store approveStore(@PathVariable UUID id, @RequestBody Approval request)`
- [setStoreActive](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L282)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/stores/{id}/active") @org.springframework.transaction.annotation.Transactional public Store setStoreActive(@PathVariable UUID id, @RequestBody StoreActivity request)`
- [setCommission](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L293)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/stores/{id}/commission") @org.springframework.transaction.annotation.Transactional public Map<String, Object> setCommission(@PathVariable UUID id, @Valid @RequestBody Commission request)`
- [auditEvents](../Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java#L303)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/audit-events") @org.springframework.transaction.annotation.Transactional(readOnly = true) public org.springframework.data.domain.Page<AdminAuditView> auditEvents( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size)`

### AdminCoverageController

- [serviceCities](../Backend/src/main/java/br/com/fooddelivery/admin/AdminCoverageController.java#L24)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminCoverageController`
  - Método: `@GetMapping("/service-cities") public List<ServiceCityView> serviceCities()`
- [createServiceCity](../Backend/src/main/java/br/com/fooddelivery/admin/AdminCoverageController.java#L29)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminCoverageController`
  - Método: `@PostMapping("/service-cities") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public ServiceCityView createServiceCity(@Valid @RequestBody ServiceCityRequest request)`
- [updateServiceCity](../Backend/src/main/java/br/com/fooddelivery/admin/AdminCoverageController.java#L42)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminCoverageController`
  - Método: `@PutMapping("/service-cities/{id}") @org.springframework.transaction.annotation.Transactional public ServiceCityView updateServiceCity(@PathVariable UUID id, @Valid @RequestBody ServiceCityRequest request)`

### AdminIntegrationStatusController

- [integrations](../Backend/src/main/java/br/com/fooddelivery/admin/AdminIntegrationStatusController.java#L26)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminIntegrationStatusController`
  - Método: `@GetMapping("/integrations") public List<IntegrationView> integrations()`

### AdminListSummaryController

- [summary](../Backend/src/main/java/br/com/fooddelivery/admin/AdminListSummaryController.java#L22)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/list-summary") @PreAuthorize("hasRole('ADMIN')") public class AdminListSummaryController`
  - Método: `@GetMapping @Transactional(readOnly=true) public Map<String, Long> summary()`

### AdminMfaController

- [status](../Backend/src/main/java/br/com/fooddelivery/admin/AdminMfaController.java#L16)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/mfa") @PreAuthorize("hasRole('ADMIN')") public class AdminMfaController`
  - Método: `@GetMapping public Object status(Authentication a)`
- [begin](../Backend/src/main/java/br/com/fooddelivery/admin/AdminMfaController.java#L17)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/mfa") @PreAuthorize("hasRole('ADMIN')") public class AdminMfaController`
  - Método: `@PostMapping("/begin") public Object begin(Authentication a,@Valid @RequestBody Request r)`
- [enable](../Backend/src/main/java/br/com/fooddelivery/admin/AdminMfaController.java#L18)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/mfa") @PreAuthorize("hasRole('ADMIN')") public class AdminMfaController`
  - Método: `@PostMapping("/enable") public Object enable(Authentication a,@Valid @RequestBody Request r)`
- [disable](../Backend/src/main/java/br/com/fooddelivery/admin/AdminMfaController.java#L19)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/mfa") @PreAuthorize("hasRole('ADMIN')") public class AdminMfaController`
  - Método: `@PostMapping("/disable") public Object disable(Authentication a,@Valid @RequestBody Request r)`

### IntegrationSettingsController

- [status](../Backend/src/main/java/br/com/fooddelivery/admin/IntegrationSettingsController.java#L13)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/integration-settings") @PreAuthorize("hasRole('ADMIN')") public class IntegrationSettingsController`
  - Método: `@GetMapping public Object status()`
- [update](../Backend/src/main/java/br/com/fooddelivery/admin/IntegrationSettingsController.java#L14)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/integration-settings") @PreAuthorize("hasRole('ADMIN')") public class IntegrationSettingsController`
  - Método: `@PutMapping public Object update(Authentication auth,@Valid @RequestBody Request request)`

### ReconciliationController

- [list](../Backend/src/main/java/br/com/fooddelivery/admin/ReconciliationController.java#L15)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/payment-reconciliations") @PreAuthorize("hasRole('ADMIN')") public class ReconciliationController`
  - Método: `@GetMapping public Object list(@RequestParam(defaultValue="0")int page)`
- [reconcile](../Backend/src/main/java/br/com/fooddelivery/admin/ReconciliationController.java#L16)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/payment-reconciliations") @PreAuthorize("hasRole('ADMIN')") public class ReconciliationController`
  - Método: `@PostMapping("/{paymentId}") public Object reconcile(@PathVariable UUID paymentId,@Valid @RequestBody Request request,Authentication auth)`

### RefundAdminController

- [list](../Backend/src/main/java/br/com/fooddelivery/admin/RefundAdminController.java#L25)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/refunds") @PreAuthorize("hasRole('ADMIN')") public class RefundAdminController`
  - Método: `@GetMapping @Transactional(readOnly = true) public List<RefundCase> list()`

### HealthController

- [status](../Backend/src/main/java/br/com/fooddelivery/api/HealthController.java#L11)
  - Classe: `@RestController @RequestMapping("/api/v1") public class HealthController`
  - Método: `@GetMapping("/status") public Map<String, String> status()`

### BookingAvailabilityController

- [publicPolicy](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L24)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/stores/{id}/booking-policy") @Transactional(readOnly=true) public Policy publicPolicy(@PathVariable UUID id)`
- [merchantPolicy](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L25)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/merchant/stores/{id}/booking-policy") @PreAuthorize("hasRole('MERCHANT')") @Transactional public Policy merchantPolicy(@PathVariable UUID id,Authentication a)`
- [merchantPolicySave](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L26)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PutMapping("/merchant/stores/{id}/booking-policy") @PreAuthorize("hasRole('MERCHANT')") @Transactional public Policy merchantPolicySave(@PathVariable UUID id,Authentication a,@Valid @RequestBody Policy r)`
- [adminPolicy](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L27)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/admin/stores/{id}/booking-policy") @PreAuthorize("hasRole('ADMIN')") @Transactional public Policy adminPolicy(@PathVariable UUID id,Authentication a)`
- [adminPolicySave](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L28)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PutMapping("/admin/stores/{id}/booking-policy") @PreAuthorize("hasRole('ADMIN')") @Transactional public Policy adminPolicySave(@PathVariable UUID id,Authentication a,@Valid @RequestBody Policy r)`
- [merchantHours](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L30)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/merchant/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('MERCHANT')") @Transactional public Object merchantHours(@PathVariable UUID id,@PathVariable UUID pid,Authentication a)`
- [merchantPreview](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L31)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PostMapping("/merchant/stores/{id}/professionals/{pid}/availability/preview") @PreAuthorize("hasRole('MERCHANT')") public Object merchantPreview(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r)`
- [merchantSave](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L32)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PutMapping("/merchant/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('MERCHANT')") public Object merchantSave(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r)`
- [adminProfessionals](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L33)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/admin/stores/{id}/professionals") @PreAuthorize("hasRole('ADMIN')") @Transactional public Object adminProfessionals(@PathVariable UUID id,Authentication a)`
- [adminHours](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L34)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/admin/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('ADMIN')") @Transactional public Object adminHours(@PathVariable UUID id,@PathVariable UUID pid,Authentication a)`
- [adminPreview](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L35)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PostMapping("/admin/stores/{id}/professionals/{pid}/availability/preview") @PreAuthorize("hasRole('ADMIN')") public Object adminPreview(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r)`
- [adminSave](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L36)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PutMapping("/admin/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('ADMIN')") public Object adminSave(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r)`
- [available](../Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java#L38)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/stores/{id}/availability") @Transactional(readOnly=true) public Object available(@PathVariable UUID id,@RequestParam UUID serviceItemId,@RequestParam LocalDate date,@RequestParam(required=false)UUID professionalId)`

### CatalogController

- [categories](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L32)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/store-categories") @Transactional(readOnly=true) public List<StoreCategories.Category> categories()`
- [locations](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L38)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/store-locations") @Transactional(readOnly=true) public List<LocationView> locations()`
- [stores](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L47)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores") @Transactional(readOnly=true) public List<StoreView> stores(@RequestParam(required=false) String city,@RequestParam(required=false) String state)`
- [items](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L54)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores/{id}/items") @Transactional(readOnly=true) public List<CatalogItemView> items(@PathVariable UUID id)`
- [createStore](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L59)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") @Transactional public StoreView createStore(@Valid @RequestBody StoreRequest req, Authentication auth)`
- [myStores](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L67)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/mine") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<StoreView> myStores(Authentication auth)`
- [resubmitStore](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L70)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/resubmit") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public StoreView resubmitStore(@PathVariable UUID id,Authentication auth)`
- [updateStore](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L72)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/stores/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public StoreView updateStore(@PathVariable UUID id, @Valid @RequestBody StoreRequest req, Authentication auth)`
- [merchantHours](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L80)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/{id}/hours") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public StoreOperatingHours.Configuration merchantHours(@PathVariable UUID id, Authentication auth)`
- [previewHours](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L82)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/hours/preview") @PreAuthorize("hasRole('MERCHANT')") public StoreHoursChangeService.Preview previewHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)`
- [updateMerchantHours](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L84)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/stores/{id}/hours") @PreAuthorize("hasRole('MERCHANT')") public StoreHoursChangeService.Saved updateMerchantHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)`
- [administratorHours](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L86)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/admin/stores/{id}/hours") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public StoreOperatingHours.Configuration administratorHours(@PathVariable UUID id)`
- [administratorPreviewHours](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L88)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/admin/stores/{id}/hours/preview") @PreAuthorize("hasRole('ADMIN')") public StoreHoursChangeService.Preview administratorPreviewHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)`
- [administratorSaveHours](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L90)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/admin/stores/{id}/hours") @PreAuthorize("hasRole('ADMIN')") public StoreHoursChangeService.Saved administratorSaveHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)`
- [publicHours](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L92)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores/{id}/hours") @Transactional(readOnly=true) public StoreOperatingHours.Configuration publicHours(@PathVariable UUID id)`
- [createItem](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L102)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/items") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") public CatalogItemView createItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth)`
- [myStoreItems](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L108)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/{id}/items") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<CatalogItemView> myStoreItems(@PathVariable UUID id, Authentication auth)`
- [storeProfessionals](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L114)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores/{id}/professionals") @Transactional(readOnly=true) public List<ProfessionalView> storeProfessionals(@PathVariable UUID id)`
- [myProfessionals](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L119)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/{id}/professionals") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<ProfessionalView> myProfessionals(@PathVariable UUID id, Authentication auth)`
- [createProfessional](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L123)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/professionals") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public ProfessionalView createProfessional(@PathVariable UUID id,@Valid @RequestBody ProfessionalRequest request,Authentication auth)`
- [updateProfessional](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L127)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/professionals/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ProfessionalView updateProfessional(@PathVariable UUID id,@Valid @RequestBody ProfessionalRequest request,Authentication auth)`
- [setProfessionalActive](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L133)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PatchMapping("/merchant/professionals/{id}/active") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ProfessionalView setProfessionalActive(@PathVariable UUID id,@RequestBody ProfessionalActiveRequest request,Authentication auth)`
- [updateItem](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L139)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/items/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public CatalogItemView updateItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth)`
- [deactivateItem](../Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java#L144)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@DeleteMapping("/merchant/items/{id}") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional public void deactivateItem(@PathVariable UUID id,Authentication auth)`

### StoreAssetController

- [submit](../Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java#L38)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@PostMapping("/merchant/stores/{id}/cover") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public CoverView submit(@PathVariable UUID id,@Valid @RequestBody CoverRequest request,Authentication auth)`
- [mine](../Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java#L55)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@GetMapping("/merchant/stores/{id}/cover-submission") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public CoverState mine(@PathVariable UUID id,Authentication auth)`
- [ownPublishedImage](../Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java#L61)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@GetMapping("/merchant/stores/{id}/published-cover") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public ResponseEntity<byte[]> ownPublishedImage(@PathVariable UUID id,Authentication auth)`
- [pending](../Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java#L68)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@GetMapping("/admin/store-covers") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public List<CoverView> pending()`
- [review](../Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java#L71)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@PatchMapping("/admin/store-covers/{id}/review") @PreAuthorize("hasRole('ADMIN')") @Transactional public CoverView review(@PathVariable UUID id,@Valid @RequestBody ReviewRequest request,Authentication auth)`
- [privateImage](../Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java#L87)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@GetMapping("/store-cover-submissions/{id}/image") @Transactional(readOnly=true) public ResponseEntity<byte[]> privateImage(@PathVariable UUID id,Authentication auth)`
- [readCover](../Backend/src/main/java/br/com/fooddelivery/catalog/StoreAssetController.java#L94)
  - Classe: `@RestController @RequestMapping("/api/v1") public class StoreAssetController`
  - Método: `@GetMapping("/store-assets/{fileName}") @Transactional(readOnly=true) public ResponseEntity<byte[]> readCover(@PathVariable String fileName)`

### OperationMessageController

- [orderMessages](../Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java#L50)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@GetMapping("/orders/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional(readOnly = true) public ThreadView orderMessages(@PathVariable UUID id, Authentication auth)`
- [sendOrderMessage](../Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java#L64)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@PostMapping("/orders/{id}/messages") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional public ThreadView sendOrderMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth)`
- [reservationMessages](../Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java#L80)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@GetMapping("/reservations/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional(readOnly = true) public ThreadView reservationMessages(@PathVariable UUID id, Authentication auth)`
- [sendReservationMessage](../Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java#L94)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@PostMapping("/reservations/{id}/messages") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')") @Transactional public ThreadView sendReservationMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth)`
- [deliveryMessages](../Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java#L110)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@GetMapping("/deliveries/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','COURIER')") @Transactional(readOnly = true) public ThreadView deliveryMessages(@PathVariable UUID id, Authentication auth)`
- [sendDeliveryMessage](../Backend/src/main/java/br/com/fooddelivery/communication/OperationMessageController.java#L121)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OperationMessageController`
  - Método: `@PostMapping("/deliveries/{id}/messages") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','COURIER')") @Transactional public ThreadView sendDeliveryMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth)`

### DemoController

- [status](../Backend/src/main/java/br/com/fooddelivery/config/DemoController.java#L24)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/demo") public class DemoController`
  - Método: `@GetMapping("/status") public Object status()`
- [messages](../Backend/src/main/java/br/com/fooddelivery/config/DemoController.java#L25)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/demo") public class DemoController`
  - Método: `@GetMapping("/messages") @PreAuthorize("hasRole('ADMIN')") public Object messages()`
- [payment](../Backend/src/main/java/br/com/fooddelivery/config/DemoController.java#L28)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/demo") public class DemoController`
  - Método: `@PostMapping("/payments/{id}/{outcome}") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public Object payment(@PathVariable UUID id, @PathVariable String outcome, Authentication auth)`

### DeliveryController

- [offer](../Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java#L17)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@PostMapping("/merchant/orders/{orderId}/delivery") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") @org.springframework.transaction.annotation.Transactional public DeliveryTask offer(@PathVariable UUID orderId,Authentication a)`
- [offers](../Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java#L20)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@GetMapping("/courier/offers") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional(readOnly=true) public List<OfferView> offers()`
- [mine](../Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java#L21)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@GetMapping("/courier/deliveries") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional(readOnly=true) public List<AssignedView> mine(Authentication a)`
- [accept](../Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java#L22)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@PostMapping("/courier/offers/{id}/accept") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public DeliveryTask accept(@PathVariable UUID id,Authentication a)`
- [progress](../Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java#L24)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@PatchMapping("/courier/deliveries/{id}") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public AssignedView progress(@PathVariable UUID id,@Valid @RequestBody Update u,Authentication a)`
- [location](../Backend/src/main/java/br/com/fooddelivery/delivery/DeliveryController.java#L26)
  - Classe: `@RestController @RequestMapping("/api/v1") public class DeliveryController`
  - Método: `@PatchMapping("/courier/deliveries/{id}/location") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public void location(@PathVariable UUID id,@Valid @RequestBody LocationUpdate u,Authentication a)`

### CourierFinanceController

- [mine](../Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java#L16)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@GetMapping("/courier/finance") @PreAuthorize("hasRole('COURIER')") public Object mine(Authentication a,@RequestParam(defaultValue="0") int page)`
- [code](../Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java#L17)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/courier/payout-code") @PreAuthorize("hasRole('COURIER')") public Object code(Authentication a,@Valid @RequestBody Password r)`
- [request](../Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java#L18)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/courier/payouts") @PreAuthorize("hasRole('COURIER')") public Object request(Authentication a,@Valid @RequestBody Request r)`
- [cancel](../Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java#L19)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/courier/payouts/{id}/cancel") @PreAuthorize("hasRole('COURIER')") public Object cancel(Authentication a,@PathVariable UUID id)`
- [admin](../Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java#L20)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@GetMapping("/admin/courier-finance") @PreAuthorize("hasRole('ADMIN')") public Object admin(@RequestParam(defaultValue="0") int page)`
- [policy](../Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java#L21)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/admin/courier-finance/policy") @PreAuthorize("hasRole('ADMIN')") public Object policy(Authentication a,@Valid @RequestBody Policy r)`
- [decision](../Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java#L22)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/admin/courier-finance/payouts/{id}/decision") @PreAuthorize("hasRole('ADMIN')") public Object decision(Authentication a,@PathVariable UUID id,@Valid @RequestBody Decision r)`
- [paid](../Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java#L23)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/admin/courier-finance/payouts/{id}/paid") @PreAuthorize("hasRole('ADMIN')") public Object paid(Authentication a,@PathVariable UUID id,@Valid @RequestBody Paid r)`

### AuthController

- [register](../Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java#L34)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/register") @ResponseStatus(HttpStatus.CREATED) public Map<String, Object> register(@Valid @RequestBody RegisterRequest request)`
- [applyForOperator](../Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java#L41)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/operator-applications") @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.GONE) public Map<String, Object> applyForOperator(@Valid @RequestBody OperatorApplicationRequest request)`
- [login](../Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java#L47)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/login") public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request)`
- [logout](../Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java#L52)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/logout") public ResponseEntity<Void> logout()`
- [requestPasswordRecovery](../Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java#L57)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/password-recovery") @ResponseStatus(HttpStatus.ACCEPTED) public Map<String, String> requestPasswordRecovery(@Valid @RequestBody RecoveryRequest request)`
- [resetPassword](../Backend/src/main/java/br/com/fooddelivery/identity/AuthController.java#L64)
  - Classe: `@RestController @RequestMapping("/api/v1/auth") public class AuthController`
  - Método: `@PostMapping("/password-recovery/reset") public Map<String, String> resetPassword(@Valid @RequestBody RecoveryResetRequest request)`

### CustomerAddressController

- [list](../Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java#L26)
  - Classe: `@RestController @RequestMapping("/api/v1/account/addresses") @PreAuthorize("hasRole('CUSTOMER')") public class CustomerAddressController`
  - Método: `@GetMapping @Transactional(readOnly = true) public List<AddressView> list(Authentication auth)`
- [create](../Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java#L32)
  - Classe: `@RestController @RequestMapping("/api/v1/account/addresses") @PreAuthorize("hasRole('CUSTOMER')") public class CustomerAddressController`
  - Método: `@PostMapping @ResponseStatus(HttpStatus.CREATED) @Transactional public AddressView create(@Valid @RequestBody AddressRequest request, Authentication auth)`
- [update](../Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java#L45)
  - Classe: `@RestController @RequestMapping("/api/v1/account/addresses") @PreAuthorize("hasRole('CUSTOMER')") public class CustomerAddressController`
  - Método: `@PutMapping("/{id}") @Transactional public AddressView update(@PathVariable UUID id, @Valid @RequestBody AddressRequest request, Authentication auth)`
- [makeDefault](../Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java#L57)
  - Classe: `@RestController @RequestMapping("/api/v1/account/addresses") @PreAuthorize("hasRole('CUSTOMER')") public class CustomerAddressController`
  - Método: `@PatchMapping("/{id}/default") @Transactional public AddressView makeDefault(@PathVariable UUID id, Authentication auth)`
- [delete](../Backend/src/main/java/br/com/fooddelivery/identity/CustomerAddressController.java#L66)
  - Classe: `@RestController @RequestMapping("/api/v1/account/addresses") @PreAuthorize("hasRole('CUSTOMER')") public class CustomerAddressController`
  - Método: `@DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional public void delete(@PathVariable UUID id, Authentication auth)`

### ProfileController

- [me](../Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java#L18)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@GetMapping("/me") public Account me(Authentication auth)`
- [update](../Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java#L19)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@PutMapping("/profile") public Account update(@Valid @RequestBody ProfileUpdate p,Authentication auth)`
- [applyForOperator](../Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java#L20)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@PostMapping("/operator-applications") @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.GONE) public Account applyForOperator(@Valid @RequestBody OperatorApplication p,Authentication auth)`
- [changePassword](../Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java#L21)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@PostMapping("/password") public org.springframework.http.ResponseEntity<java.util.Map<String,Object>> changePassword(@Valid @RequestBody PasswordChange p,Authentication auth)`
- [requestPhoneVerification](../Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java#L22)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@PostMapping("/phone-verification") @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.ACCEPTED) public java.util.Map<String,String> requestPhoneVerification(Authentication auth)`
- [confirmPhone](../Backend/src/main/java/br/com/fooddelivery/identity/ProfileController.java#L23)
  - Classe: `@RestController @RequestMapping("/api/v1/account") @org.springframework.transaction.annotation.Transactional public class ProfileController`
  - Método: `@PostMapping("/phone-verification/confirm") public java.util.Map<String,String> confirmPhone(@Valid @RequestBody PhoneVerification p,Authentication auth)`

### DemoGeocodingController

- [search](../Backend/src/main/java/br/com/fooddelivery/location/DemoGeocodingController.java#L15)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/geography") public class DemoGeocodingController`
  - Método: `@GetMapping("/search") public Object search(@RequestParam String query)`
- [reverse](../Backend/src/main/java/br/com/fooddelivery/location/DemoGeocodingController.java#L21)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/geography") public class DemoGeocodingController`
  - Método: `@GetMapping("/reverse") public Object reverse(@RequestParam double latitude,@RequestParam double longitude)`

### GeocodingController

- [search](../Backend/src/main/java/br/com/fooddelivery/location/GeocodingController.java#L40)
  - Classe: `@RestController @org.springframework.context.annotation.Profile("!homologacao") @RequestMapping("/api/v1/geography") public class GeocodingController`
  - Método: `@GetMapping("/search") public JsonNode search(@RequestParam("query") String query)`
- [reverse](../Backend/src/main/java/br/com/fooddelivery/location/GeocodingController.java#L57)
  - Classe: `@RestController @org.springframework.context.annotation.Profile("!homologacao") @RequestMapping("/api/v1/geography") public class GeocodingController`
  - Método: `@GetMapping("/reverse") public JsonNode reverse(@RequestParam double latitude, @RequestParam double longitude)`

### NotificationController

- [list](../Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java#L17)
  - Classe: `@RestController @RequestMapping("/api/v1/account/notifications") public class NotificationController`
  - Método: `@GetMapping public List<AccountNotification> list(Authentication auth)`
- [read](../Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java#L20)
  - Classe: `@RestController @RequestMapping("/api/v1/account/notifications") public class NotificationController`
  - Método: `@PatchMapping("/{id}/read") public AccountNotification read(@PathVariable UUID id, Authentication auth)`
- [clear](../Backend/src/main/java/br/com/fooddelivery/notifications/NotificationController.java#L25)
  - Classe: `@RestController @RequestMapping("/api/v1/account/notifications") public class NotificationController`
  - Método: `@DeleteMapping @ResponseStatus(HttpStatus.NO_CONTENT) public void clear(Authentication auth)`

### OrderController

- [create](../Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java#L45)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@PostMapping("/orders") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('CUSTOMER')") @Transactional public CheckoutResponse create(@Valid @RequestBody CreateOrder request, Authentication auth)`
- [mine](../Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java#L57)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@GetMapping("/orders/mine") @PreAuthorize("hasRole('CUSTOMER')") @Transactional(readOnly = true) public List<OrderView> mine(Authentication auth)`
- [merchantOrders](../Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java#L67)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@GetMapping("/merchant/orders") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly = true) public List<OrderView> merchantOrders(Authentication auth)`
- [cancelUnpaid](../Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java#L76)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@PostMapping("/orders/{id}/cancel") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public OrderView cancelUnpaid(@PathVariable UUID id, Authentication auth)`
- [requestCancellation](../Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java#L84)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@PostMapping("/orders/{id}/cancellation-request") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public OrderView requestCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationRequest request,Authentication auth)`
- [decideCancellation](../Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java#L92)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@PatchMapping("/merchant/orders/{id}/cancellation-request") @PreAuthorize("hasRole('MERCHANT')") @Transactional public OrderView decideCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationDecision request,Authentication auth)`
- [status](../Backend/src/main/java/br/com/fooddelivery/orders/OrderController.java#L100)
  - Classe: `@RestController @RequestMapping("/api/v1") public class OrderController`
  - Método: `@PatchMapping("/merchant/orders/{id}/status") @PreAuthorize("hasRole('MERCHANT')") @Transactional public OrderView status(@PathVariable UUID id, @Valid @RequestBody StatusChange request, Authentication auth)`

### DevelopmentPaymentController

- [get](../Backend/src/main/java/br/com/fooddelivery/payments/DevelopmentPaymentController.java#L14)
  - Classe: `@RestController @RequestMapping("/api/v1/dev/payments") @Profile("dev-h2") public class DevelopmentPaymentController`
  - Método: `@GetMapping("/{id}") @PreAuthorize("hasRole('CUSTOMER')") public Payment get(@PathVariable UUID id,Authentication auth)`
- [approve](../Backend/src/main/java/br/com/fooddelivery/payments/DevelopmentPaymentController.java#L15)
  - Classe: `@RestController @RequestMapping("/api/v1/dev/payments") @Profile("dev-h2") public class DevelopmentPaymentController`
  - Método: `@PostMapping("/{id}/approve") @PreAuthorize("hasRole('CUSTOMER')") public Payment approve(@PathVariable UUID id,Authentication auth)`

### MercadoPagoAccountController

- [connect](../Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java#L18)
  - Classe: `@RestController @RequestMapping("/api/v1") public class MercadoPagoAccountController`
  - Método: `@PostMapping("/merchant/stores/{storeId}/mercadopago/connect") @PreAuthorize("hasRole('MERCHANT')") public Map<String,String> connect(@PathVariable UUID storeId,Authentication auth)`
- [status](../Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java#L24)
  - Classe: `@RestController @RequestMapping("/api/v1") public class MercadoPagoAccountController`
  - Método: `@GetMapping("/merchant/stores/{storeId}/mercadopago/status") @PreAuthorize("hasRole('MERCHANT')") public MarketplaceAccountService.ConnectionStatus status(@PathVariable UUID storeId,Authentication auth)`
- [callback](../Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoAccountController.java#L28)
  - Classe: `@RestController @RequestMapping("/api/v1") public class MercadoPagoAccountController`
  - Método: `@GetMapping("/payments/mercadopago/callback") public RedirectView callback(@RequestParam(required=false) String code,@RequestParam(required=false) String state, @RequestParam(required=false) String error)`

### MercadoPagoWebhookController

- [receive](../Backend/src/main/java/br/com/fooddelivery/payments/MercadoPagoWebhookController.java#L31)
  - Classe: `@RestController @RequestMapping("/api/v1/webhooks/mercadopago") public class MercadoPagoWebhookController`
  - Método: `@PostMapping @ResponseStatus(HttpStatus.OK) public Map<String,String> receive(@RequestParam(name="data.id",required=false) String dataId, @RequestParam(required=false) String type,@RequestParam(name="payment_id") UUID internalPaymentId, @RequestHeader(name="x-signature",required=false) String signature, @RequestHeader(name="x-request-id",required=false) String requestId)`

### PaymentController

- [createCheckout](../Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java#L17)
  - Classe: `@RestController @RequestMapping("/api/v1/payments") @PreAuthorize("hasRole('CUSTOMER')") public class PaymentController`
  - Método: `@PostMapping("/{id}/checkout") public MercadoPagoCheckoutService.CheckoutView createCheckout(@PathVariable UUID id,Authentication auth)`
- [get](../Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java#L22)
  - Classe: `@RestController @RequestMapping("/api/v1/payments") @PreAuthorize("hasRole('CUSTOMER')") public class PaymentController`
  - Método: `@GetMapping("/{id}") @org.springframework.transaction.annotation.Transactional(readOnly=true) public PaymentView get(@PathVariable UUID id,Authentication auth)`
- [sync](../Backend/src/main/java/br/com/fooddelivery/payments/PaymentController.java#L31)
  - Classe: `@RestController @RequestMapping("/api/v1/payments") @PreAuthorize("hasRole('CUSTOMER')") public class PaymentController`
  - Método: `@PostMapping("/{id}/sync") public PaymentView sync(@PathVariable UUID id,@RequestBody SyncRequest request,Authentication auth)`

### PlatformRequestController

- [mine](../Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java#L24)
  - Classe: `@RestController @RequestMapping("/api/v1") public class PlatformRequestController`
  - Método: `@GetMapping("/account/platform-requests") @Transactional(readOnly=true) public List<RequestView> mine(Authentication auth)`
- [requestRole](../Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java#L26)
  - Classe: `@RestController @RequestMapping("/api/v1") public class PlatformRequestController`
  - Método: `@PostMapping("/account/platform-requests/roles") @ResponseStatus(HttpStatus.CREATED) @Transactional public RequestView requestRole(@Valid @RequestBody RoleRequest body,Authentication auth)`
- [requestCity](../Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java#L36)
  - Classe: `@RestController @RequestMapping("/api/v1") public class PlatformRequestController`
  - Método: `@PostMapping("/account/platform-requests/cities") @ResponseStatus(HttpStatus.CREATED) @Transactional public RequestView requestCity(@Valid @RequestBody CityRequest body,Authentication auth)`
- [all](../Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java#L45)
  - Classe: `@RestController @RequestMapping("/api/v1") public class PlatformRequestController`
  - Método: `@GetMapping("/admin/platform-requests") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public List<RequestView> all()`
- [review](../Backend/src/main/java/br/com/fooddelivery/platform/PlatformRequestController.java#L48)
  - Classe: `@RestController @RequestMapping("/api/v1") public class PlatformRequestController`
  - Método: `@PatchMapping("/admin/platform-requests/{id}/review") @PreAuthorize("hasRole('ADMIN')") @Transactional public RequestView review(@PathVariable UUID id,@Valid @RequestBody Review body,Authentication auth)`

### DevelopmentReservationPaymentController

- [approve](../Backend/src/main/java/br/com/fooddelivery/reservations/DevelopmentReservationPaymentController.java#L17)
  - Classe: `@RestController @RequestMapping("/api/v1/dev/reservations") @Profile("dev-h2") public class DevelopmentReservationPaymentController`
  - Método: `@PostMapping("/{id}/approve-payment") @PreAuthorize("hasRole('CUSTOMER')") public Object approve(@PathVariable UUID id,Authentication auth)`

### ReservationController

- [create](../Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java#L62)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PostMapping("/reservations") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('CUSTOMER')") @Transactional public CreateResponse create(@Valid @RequestBody Request request, Authentication auth)`
- [mine](../Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java#L111)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@GetMapping("/reservations/mine") @PreAuthorize("hasRole('CUSTOMER')") @Transactional(readOnly = true) public List<ReservationView> mine(Authentication auth)`
- [merchant](../Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java#L121)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@GetMapping("/merchant/reservations") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly = true) public List<ReservationView> merchant(Authentication auth)`
- [cancelUnpaid](../Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java#L131)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PostMapping("/reservations/{id}/cancel") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public ReservationView cancelUnpaid(@PathVariable UUID id, Authentication auth)`
- [requestCancellation](../Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java#L155)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PostMapping("/reservations/{id}/cancellation-request") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public ReservationView requestCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationNote request,Authentication auth)`
- [decideCancellation](../Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java#L176)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PatchMapping("/merchant/reservations/{id}/cancellation-request") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ReservationView decideCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationDecision request,Authentication auth)`
- [update](../Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java#L191)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PatchMapping("/merchant/reservations/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ReservationView update(@PathVariable UUID id, @Valid @RequestBody Change change, Authentication auth)`

### SupportTicketController

- [mine](../Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java#L31)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@GetMapping("/support/tickets/mine") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER','ADMIN')") @Transactional(readOnly=true) public List<TicketView> mine(Authentication auth)`
- [create](../Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java#L39)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@PostMapping("/support/tickets") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')") @Transactional public TicketDetail create(@Valid @RequestBody CreateRequest request, Authentication auth)`
- [mineDetail](../Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java#L54)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@GetMapping("/support/tickets/{id}") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')") @Transactional(readOnly=true) public TicketDetail mineDetail(@PathVariable UUID id, Authentication auth)`
- [reply](../Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java#L59)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@PostMapping("/support/tickets/{id}/messages") @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')") @Transactional public TicketDetail reply(@PathVariable UUID id,@Valid @RequestBody MessageRequest request,Authentication auth)`
- [all](../Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java#L70)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@GetMapping("/admin/support-tickets") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public List<TicketView> all()`
- [page](../Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java#L75)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@GetMapping("/admin/support-tickets/page") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public br.com.fooddelivery.admin.AdminPage<TicketView> page(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25") int size)`
- [adminDetail](../Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java#L86)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@GetMapping("/admin/support-tickets/{id}") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public TicketDetail adminDetail(@PathVariable UUID id)`
- [adminReply](../Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java#L91)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@PostMapping("/admin/support-tickets/{id}/messages") @PreAuthorize("hasRole('ADMIN')") @Transactional public TicketDetail adminReply(@PathVariable UUID id,@Valid @RequestBody MessageRequest request,Authentication auth)`
- [updateStatus](../Backend/src/main/java/br/com/fooddelivery/support/SupportTicketController.java#L102)
  - Classe: `@RestController @RequestMapping("/api/v1") public class SupportTicketController`
  - Método: `@PatchMapping("/admin/support-tickets/{id}/status") @PreAuthorize("hasRole('ADMIN')") @Transactional public TicketView updateStatus(@PathVariable UUID id,@Valid @RequestBody StatusRequest request)`

### DemoWhatsAppController

- [accept](../Backend/src/main/java/br/com/fooddelivery/whatsapp/DemoWhatsAppController.java#L11)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/demo/whatsapp") @PreAuthorize("hasRole('ADMIN')") public class DemoWhatsAppController`
  - Método: `@PostMapping public Object accept(@Valid @RequestBody Request r)`

### WhatsAppController

- [status](../Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java#L21)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@GetMapping("/account/whatsapp-link") public Object status(Authentication a)`
- [bind](../Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java#L22)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@PostMapping("/account/whatsapp-link") public Object bind(Authentication a,@Valid @RequestBody Password request)`
- [unbind](../Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java#L23)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@DeleteMapping("/account/whatsapp-link") public Object unbind(Authentication a,@Valid @RequestBody Password request)`
- [verify](../Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java#L25)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@GetMapping(value="/webhooks/whatsapp",produces="text/plain") public String verify(@RequestParam(name="hub.mode")String mode,@RequestParam(name="hub.verify_token")String token,@RequestParam(name="hub.challenge")String challenge)`
- [receive](../Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java#L27)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@PostMapping("/webhooks/whatsapp") public Object receive(@RequestBody byte[] body,@RequestHeader(name="X-Hub-Signature-256",required=false)String signature)`
- [admin](../Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java#L28)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@GetMapping("/admin/whatsapp-status") @PreAuthorize("hasRole('ADMIN')") public Object admin()`

## Migrações e razão da separação

Flyway executa a sequência V1–V31. V9 tem variantes H2/PostgreSQL para diferenças de mecanismo. O perfil escolhe uma variante, nunca as duas. Os demais scripts são comuns. Hibernate valida o resultado; não é proprietário do esquema.

| Versão / arquivo | DDL e finalidade identificável |
|---|---|
| [V1__initial_schema.sql](../Backend/src/main/resources/db/migration-common/V1__initial_schema.sql) | 13 instruções; relações/índices: `accounts`, `catalog_items`, `customer_orders`, `delivery_tasks`, `order_lines`, `payments`, `reservations`, `stores` |
| [V2__account_profiles_and_notifications.sql](../Backend/src/main/resources/db/migration-common/V2__account_profiles_and_notifications.sql) | 7 instruções; relações/índices: `account_notifications`, `accounts` |
| [V3__marketplace_payment_support.sql](../Backend/src/main/resources/db/migration-common/V3__marketplace_payment_support.sql) | 13 instruções; relações/índices: `customer_orders`, `marketplace_payment_accounts`, `payments`, `stores` |
| [V4__payment_expiration_and_reservation_capacity.sql](../Backend/src/main/resources/db/migration-common/V4__payment_expiration_and_reservation_capacity.sql) | 4 instruções; relações/índices: `reservation_slots`, `reservations` |
| [V5__refund_tracking.sql](../Backend/src/main/resources/db/migration-common/V5__refund_tracking.sql) | 5 instruções; relações/índices: `payments` |
| [V6__operator_applications_and_store_branding.sql](../Backend/src/main/resources/db/migration-common/V6__operator_applications_and_store_branding.sql) | 4 instruções; relações/índices: `accounts`, `stores` |
| [V7__customer_address_book.sql](../Backend/src/main/resources/db/migration-common/V7__customer_address_book.sql) | 2 instruções; relações/índices: `customer_addresses` |
| [V8__delivery_location_sharing.sql](../Backend/src/main/resources/db/migration-common/V8__delivery_location_sharing.sql) | 3 instruções; relações/índices: `delivery_tasks` |
| [V9__account_security_and_store_location.sql](../Backend/src/main/resources/db/migration-h2/V9__account_security_and_store_location.sql) | 13 instruções; relações/índices: `accounts`, `stores` |
| [V9__account_security_and_store_location.sql](../Backend/src/main/resources/db/migration-postgres/V9__account_security_and_store_location.sql) | 13 instruções; relações/índices: `accounts`, `stores` |
| [V10__account_credential_version.sql](../Backend/src/main/resources/db/migration-common/V10__account_credential_version.sql) | 1 instruções; relações/índices: `accounts` |
| [V11__operation_messages.sql](../Backend/src/main/resources/db/migration-common/V11__operation_messages.sql) | 6 instruções; relações/índices: `customer_orders`, `operation_messages`, `reservations` |
| [V12__support_tickets.sql](../Backend/src/main/resources/db/migration-common/V12__support_tickets.sql) | 5 instruções; relações/índices: `support_ticket_messages`, `support_tickets` |
| [V13__account_platform_requests.sql](../Backend/src/main/resources/db/migration-common/V13__account_platform_requests.sql) | 6 instruções; relações/índices: `platform_requests`, `service_cities`, `stores` |
| [V14__password_recovery.sql](../Backend/src/main/resources/db/migration-common/V14__password_recovery.sql) | 3 instruções; relações/índices: `accounts`, `recovery_codes` |
| [V15__service_payment_options.sql](../Backend/src/main/resources/db/migration-common/V15__service_payment_options.sql) | 2 instruções; relações/índices: `catalog_items`, `reservations` |
| [V16__administrator_store_categories.sql](../Backend/src/main/resources/db/migration-common/V16__administrator_store_categories.sql) | 2 instruções; relações/índices: `store_categories` |
| [V17__store_professionals_and_soft_delete.sql](../Backend/src/main/resources/db/migration-common/V17__store_professionals_and_soft_delete.sql) | 6 instruções; relações/índices: `reservation_slots`, `reservations`, `store_professionals` |
| [V18__notification_navigation_targets.sql](../Backend/src/main/resources/db/migration-common/V18__notification_navigation_targets.sql) | 3 instruções; relações/índices: `account_notifications` |
| [V19__store_operating_hours.sql](../Backend/src/main/resources/db/migration-common/V19__store_operating_hours.sql) | 3 instruções; relações/índices: `stores` |
| [V20__reservation_capacity_and_service_duration.sql](../Backend/src/main/resources/db/migration-common/V20__reservation_capacity_and_service_duration.sql) | 15 instruções; relações/índices: `catalog_items`, `reservation_slots`, `reservations` |
| [V21__customer_cancellation_requests.sql](../Backend/src/main/resources/db/migration-common/V21__customer_cancellation_requests.sql) | 8 instruções; relações/índices: `customer_orders`, `reservations` |
| [V22__store_map_coordinates.sql](../Backend/src/main/resources/db/migration-common/V22__store_map_coordinates.sql) | 2 instruções; relações/índices: `stores` |
| [V23__admin_audit_events.sql](../Backend/src/main/resources/db/migration-common/V23__admin_audit_events.sql) | 3 instruções; relações/índices: `admin_audit_events` |
| [V24__store_cover_moderation.sql](../Backend/src/main/resources/db/migration-common/V24__store_cover_moderation.sql) | 3 instruções; relações/índices: `store_cover_submissions` |
| [V25__store_fulfillment_options.sql](../Backend/src/main/resources/db/migration-common/V25__store_fulfillment_options.sql) | 4 instruções; relações/índices: `stores` |
| [V26__reservation_hours_change_notice.sql](../Backend/src/main/resources/db/migration-common/V26__reservation_hours_change_notice.sql) | 4 instruções; relações/índices: `reservations` |
| [V27__courier_finance.sql](../Backend/src/main/resources/db/migration-common/V27__courier_finance.sql) | 7 instruções; relações/índices: `courier_compensation`, `courier_earnings`, `courier_payouts`, `delivery_tasks` |
| [V28__payment_reconciliation.sql](../Backend/src/main/resources/db/migration-common/V28__payment_reconciliation.sql) | 2 instruções; relações/índices: `payment_reconciliations` |
| [V29__private_configuration_and_admin_mfa.sql](../Backend/src/main/resources/db/migration-common/V29__private_configuration_and_admin_mfa.sql) | 2 instruções; relações/índices: `administrator_mfa`, `private_settings` |
| [V30__professional_availability_and_booking_policy.sql](../Backend/src/main/resources/db/migration-common/V30__professional_availability_and_booking_policy.sql) | 6 instruções; relações/índices: `store_professionals`, `stores` |
| [V31__whatsapp_commerce.sql](../Backend/src/main/resources/db/migration-common/V31__whatsapp_commerce.sql) | 7 instruções; relações/índices: `whatsapp_bindings`, `whatsapp_events`, `whatsapp_replies`, `whatsapp_sessions` |

## Arquivos de operação

| Arquivo | Papel e avaliação |
|---|---|
| `scripts/dev.py` | Supervisor local; identifica serviços existentes para não iniciar cópias. Seis testes unitários cobrem sondagem/reutilização. |
| `scripts/homologacao.py` | Supervisor isolado; não lê `.env`, gera chave própria, valida portas e inicia fixtures. SO_REUSEADDR permite reinício após fechamento sem compartilhar listener ocupado. |
| `Frontend/scripts/start.py` | Servidor local de arquivos e proxy exclusivo de demonstração; não é servidor de borda para produção. |
| `Backend/scripts/backup-db.sh` | Dump e checksum. Ensaio PostgreSQL descartável coberto por test_postgres.py; recuperação completa de produção permanece pendente. |
| `Backend/scripts/restore-db.sh` | Confere checksum e confirmação de banco; é destrutivo no destino escolhido. Não foi executado no banco real. |
| `scripts/test_homologacao.py` | Contratos da simulação, cinco logins por execução; não rodar repetidamente junto de outros logins sem considerar rate limit. |
| `scripts/test_jornadas.py` | CRUD, autorização, suporte, administração e sequência completa de entrega em fixtures. |
| `.github/workflows` | Qualidade/segurança declaradas; presença de YAML não demonstra execução bem-sucedida no GitHub. |
| `Frontend/styles.css` / `theme.css` | Base e refinamentos de componentes, responsividade e foco; estilos sobrepostos exigem inspeção do resultado computado. |
| `Frontend/index.html` / `demo.html` | Entrada normal e caixa de mensagens fictícias; metadados e carregamento de módulos. |
| `Frontend/runtime-config.js` | Em estático mantém modo normal; o servidor fictício fornece marcador explícito de homologação. |
| `Backend/src/main/resources/application*.yml` | Configuração por perfil; credenciais privadas e ambiente não devem ser publicados como documentação. |
