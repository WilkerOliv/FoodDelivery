# Inventário de interface, HTTP e migrações

Inventário estático da árvore de código documentada; uma linha aqui não significa que a ação foi executada em todas as combinações. Consulte a matriz de validação.

## Atributos de interação do frontend

Os atributos `data-*` conectam botões, formulários e estados aos handlers documentados na referência. Alguns são seletores de apresentação, não ações. Valores dinâmicos são escapados no template e revalidados no servidor.

| Atributo | Primeira ocorrência |
|---|---|
| `data-about-modal` | [main.js:343](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:343) |
| `data-about-us` | [main.js:318](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:318) |
| `data-accept-delivery` | [deliveryBindings.js:3](/home/wilker/Projetos/FoodDelivery/Frontend/src/deliveryBindings.js:3) |
| `data-accepted` | [main.js:1037](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1037) |
| `data-account-active` | [main.js:625](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:625) |
| `data-account-role` | [main.js:627](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:627) |
| `data-account-section` | [main.js:598](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:598) |
| `data-action` | [main.js:560](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:560) |
| `data-active` | [main.js:1048](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1048) |
| `data-add-address` | [main.js:734](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:734) |
| `data-add-item` | [main.js:690](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:690) |
| `data-add-shift` | [storeHours.js:20](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:20) |
| `data-address-form` | [main.js:736](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:736) |
| `data-admin-account-edit` | [main.js:593](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:593) |
| `data-admin-area` | [main.js:1066](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1066) |
| `data-admin-definition-section` | [main.js:599](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:599) |
| `data-admin-edit-account` | [main.js:626](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:626) |
| `data-admin-edit-store` | [main.js:594](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:594) |
| `data-admin-hours` | [adminStoreEditor.js:37](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:37) |
| `data-admin-mfa` | [main.js:567](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:567) |
| `data-admin-profile-save` | [main.js:587](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:587) |
| `data-admin-team-section` | [main.js:653](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:653) |
| `data-admin-ticket` | [main.js:515](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:515) |
| `data-approve-store` | [main.js:637](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:637) |
| `data-approved` | [courierFinance.js:9](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:9) |
| `data-auth-mode` | [main.js:333](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:333) |
| `data-available-slots` | [main.js:718](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:718) |
| `data-back` | [courierFinance.js:10](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:10) |
| `data-booking-date` | [bookingAvailability.js:6](/home/wilker/Projetos/FoodDelivery/Frontend/src/bookingAvailability.js:6) |
| `data-booking-policy` | [main.js:565](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:565) |
| `data-cancel-address` | [main.js:736](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:736) |
| `data-cancel-logout` | [main.js:1276](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1276) |
| `data-cancel-order` | [main.js:841](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:841) |
| `data-cancel-payout` | [courierFinance.js:9](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:9) |
| `data-cancel-reservation` | [main.js:854](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:854) |
| `data-cart-mode` | [main.js:733](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:733) |
| `data-catalog-item-update` | [main.js:1142](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1142) |
| `data-category` | [main.js:526](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:526) |
| `data-category-select` | [main.js:539](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:539) |
| `data-cep` | [main.js:736](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:736) |
| `data-cep-feedback` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-chat-draft` | [main.js:662](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:662) |
| `data-chat-form` | [main.js:661](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:661) |
| `data-chat-id` | [main.js:870](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:870) |
| `data-chat-panel` | [main.js:863](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:863) |
| `data-chat-type` | [main.js:870](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:870) |
| `data-checkout` | [main.js:585](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:585) |
| `data-checkout-note` | [main.js:738](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:738) |
| `data-checkout-pay` | [main.js:645](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:645) |
| `data-city` | [main.js:488](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:488) |
| `data-city-count` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-city-list` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-city-request` | [main.js:607](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:607) |
| `data-clear-notifications` | [main.js:954](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:954) |
| `data-close` | [storeHours.js:14](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:14) |
| `data-close-about` | [main.js:343](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:343) |
| `data-close-admin-edit` | [accessibility.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:33) |
| `data-close-cart` | [accessibility.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:33) |
| `data-close-catalog-edit` | [accessibility.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:33) |
| `data-close-chat` | [main.js:663](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:663) |
| `data-close-item` | [main.js:1142](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1142) |
| `data-close-location` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-close-modal` | [accessibility.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:33) |
| `data-close-support` | [accessibility.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/accessibility.js:33) |
| `data-close-today` | [storeHours.js:38](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:38) |
| `data-code` | [courierFinance.js:15](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:15) |
| `data-confirm-logout` | [main.js:1276](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1276) |
| `data-connect-mercadopago` | [main.js:962](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:962) |
| `data-courier-finance` | [main.js:568](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:568) |
| `data-cover-review` | [main.js:633](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:633) |
| `data-create-item` | [catalogBindings.js:3](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogBindings.js:3) |
| `data-create-operator` | [main.js:622](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:622) |
| `data-create-store` | [main.js:609](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:609) |
| `data-decide` | [courierFinance.js:9](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:9) |
| `data-decision` | [main.js:1092](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1092) |
| `data-default-address` | [main.js:913](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:913) |
| `data-delete-address` | [main.js:913](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:913) |
| `data-delivery-card` | [main.js:838](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:838) |
| `data-delivery-status` | [main.js:640](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:640) |
| `data-dismiss` | [storeHours.js:30](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:30) |
| `data-dismiss-temp-password` | [main.js:629](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:629) |
| `data-enabled` | [main.js:881](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:881) |
| `data-enrollment` | [adminSecurity.js:13](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSecurity.js:13) |
| `data-exception-editor` | [storeHours.js:38](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:38) |
| `data-forgot-password` | [main.js:341](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:341) |
| `data-help` | [main.js:414](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:414) |
| `data-id` | [main.js:732](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:732) |
| `data-item-` | [main.js:1121](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1121) |
| `data-item-activate` | [main.js:614](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:614) |
| `data-item-deactivate` | [main.js:613](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:613) |
| `data-item-edit` | [catalogBindings.js:4](/home/wilker/Projetos/FoodDelivery/Frontend/src/catalogBindings.js:4) |
| `data-item-kind` | [main.js:618](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:618) |
| `data-location-cep` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-location-clear` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-location-confirm` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-location-gps` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-location-map` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-location-picker` | [main.js:420](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:420) |
| `data-location-result` | [main.js:500](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:500) |
| `data-location-results` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-location-search` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-location-selection` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-location-selection-label` | [main.js:477](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:477) |
| `data-logout` | [main.js:422](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:422) |
| `data-lookup-cep` | [main.js:736](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:736) |
| `data-map-canvas` | [storeMaps.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:33) |
| `data-map-clear` | [storeMaps.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:33) |
| `data-map-content` | [storeMaps.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:33) |
| `data-map-feedback` | [storeMaps.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:33) |
| `data-map-results` | [storeMaps.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:33) |
| `data-map-search` | [storeMaps.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:33) |
| `data-map-toggle` | [storeMaps.js:33](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeMaps.js:33) |
| `data-mask` | [main.js:335](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:335) |
| `data-merchant-store-select` | [main.js:611](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:611) |
| `data-mobile-more` | [main.js:417](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:417) |
| `data-money` | [courierFinance.js:8](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:8) |
| `data-name` | [main.js:1101](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1101) |
| `data-next` | [courierFinance.js:16](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:16) |
| `data-no-show-warning` | [main.js:1020](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1020) |
| `data-notification-target` | [main.js:955](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:955) |
| `data-offer-delivery` | [main.js:635](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:635) |
| `data-open` | [storeHours.js:14](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:14) |
| `data-open-chat-id` | [main.js:841](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:841) |
| `data-open-chat-type` | [main.js:660](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:660) |
| `data-operating-hours` | [main.js:612](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:612) |
| `data-operating-hours-editor` | [storeHours.js:38](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:38) |
| `data-operator-active` | [main.js:624](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:624) |
| `data-operator-application` | [main.js:623](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:623) |
| `data-operator-request` | [main.js:606](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:606) |
| `data-order-cancellation-decision` | [main.js:1037](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1037) |
| `data-order-card` | [main.js:838](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:838) |
| `data-order-status` | [main.js:634](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:634) |
| `data-page` | [main.js:412](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:412) |
| `data-paid` | [courierFinance.js:9](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:9) |
| `data-password-change` | [main.js:595](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:595) |
| `data-pay-after-service` | [main.js:619](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:619) |
| `data-phone-verify-confirm` | [main.js:597](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:597) |
| `data-phone-verify-request` | [main.js:596](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:596) |
| `data-platform-review` | [main.js:608](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:608) |
| `data-policy` | [courierFinance.js:15](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:15) |
| `data-prev` | [courierFinance.js:16](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:16) |
| `data-private-fields` | [adminSecurity.js:6](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSecurity.js:6) |
| `data-private-integrations` | [main.js:566](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:566) |
| `data-professional-active` | [main.js:617](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:617) |
| `data-professional-create` | [main.js:615](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:615) |
| `data-professional-hours` | [main.js:616](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:616) |
| `data-professional-schedule` | [bookingAvailability.js:17](/home/wilker/Projetos/FoodDelivery/Frontend/src/bookingAvailability.js:17) |
| `data-profile-save` | [main.js:586](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:586) |
| `data-profile-store-map` | [main.js:685](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:685) |
| `data-qty` | [main.js:732](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:732) |
| `data-recovery-back` | [main.js:331](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:331) |
| `data-recovery-channel` | [main.js:348](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:348) |
| `data-refresh` | [courierFinance.js:16](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:16) |
| `data-refresh-audit` | [main.js:631](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:631) |
| `data-refresh-integrations` | [main.js:630](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:630) |
| `data-remove-key` | [adminSecurity.js:7](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminSecurity.js:7) |
| `data-remove-shift` | [storeHours.js:14](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:14) |
| `data-reopen-store` | [main.js:642](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:642) |
| `data-request` | [courierFinance.js:15](/home/wilker/Projetos/FoodDelivery/Frontend/src/courierFinance.js:15) |
| `data-request-order-cancellation` | [main.js:841](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:841) |
| `data-request-reservation-cancellation` | [main.js:854](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:854) |
| `data-reservation-cancellation-decision` | [main.js:1052](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1052) |
| `data-reservation-card` | [main.js:854](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:854) |
| `data-reservation-status` | [main.js:636](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:636) |
| `data-reserve` | [main.js:690](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:690) |
| `data-reset-password` | [main.js:628](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:628) |
| `data-resubmit-store` | [main.js:610](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:610) |
| `data-resume-order` | [main.js:841](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:841) |
| `data-resume-reservation` | [main.js:854](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:854) |
| `data-retry-page` | [main.js:465](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:465) |
| `data-risk-ack-field` | [main.js:1020](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1020) |
| `data-role` | [main.js:1101](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1101) |
| `data-save-exception` | [storeHours.js:38](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:38) |
| `data-scroll-stores` | [main.js:529](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:529) |
| `data-section` | [main.js:647](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:647) |
| `data-service-city-create` | [main.js:588](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:588) |
| `data-service-city-toggle` | [main.js:591](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:591) |
| `data-service-city-update` | [main.js:590](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:590) |
| `data-service-duration-field` | [main.js:1020](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1020) |
| `data-service-payment-field` | [main.js:1020](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1020) |
| `data-share-location` | [main.js:641](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:641) |
| `data-shift` | [storeHours.js:18](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:18) |
| `data-shifts` | [storeHours.js:20](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:20) |
| `data-status` | [main.js:1102](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1102) |
| `data-stock-field` | [main.js:1020](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1020) |
| `data-store` | [main.js:557](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:557) |
| `data-store-active` | [main.js:638](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:638) |
| `data-store-category-create` | [main.js:589](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:589) |
| `data-store-category-delete` | [main.js:592](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:592) |
| `data-store-commission` | [main.js:1100](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1100) |
| `data-store-cover` | [main.js:632](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:632) |
| `data-store-directory-map` | [main.js:532](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:532) |
| `data-store-filter` | [main.js:620](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:620) |
| `data-store-map-picker` | [adminStoreEditor.js:42](/home/wilker/Projetos/FoodDelivery/Frontend/src/adminStoreEditor.js:42) |
| `data-support-list` | [main.js:604](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:604) |
| `data-switch-role` | [main.js:605](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:605) |
| `data-table` | [main.js:1033](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1033) |
| `data-target-id` | [main.js:955](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:955) |
| `data-ticket-admin` | [main.js:1102](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:1102) |
| `data-ticket-back` | [main.js:512](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:512) |
| `data-ticket-compose` | [main.js:512](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:512) |
| `data-ticket-create` | [main.js:511](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:511) |
| `data-ticket-id` | [main.js:515](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:515) |
| `data-ticket-open` | [main.js:600](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:600) |
| `data-ticket-status` | [main.js:601](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:601) |
| `data-tracking-map` | [main.js:656](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:656) |
| `data-uf` | [main.js:488](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:488) |
| `data-update-store` | [main.js:621](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:621) |
| `data-user-ticket` | [main.js:511](/home/wilker/Projetos/FoodDelivery/Frontend/src/main.js:511) |
| `data-whole-day` | [storeHours.js:20](/home/wilker/Projetos/FoodDelivery/Frontend/src/storeHours.js:20) |

## Contratos HTTP declarados

A rota efetiva combina o prefixo da classe e o sufixo do método. `SecurityConfig` decide acesso público/autenticado; `@PreAuthorize` e as verificações de titularidade no corpo restringem a operação. Uma anotação ausente não significa acesso público.

### AdminController

- [storeCategories](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:65)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/store-categories") @org.springframework.transaction.annotation.Transactional(readOnly = true) public List<AdminStoreCategoryView> storeCategories()`
- [createStoreCategory](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:75)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PostMapping("/store-categories") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public AdminStoreCategoryView createStoreCategory(@Valid @RequestBody StoreCategoryRequest request)`
- [deleteStoreCategory](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:90)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@DeleteMapping("/store-categories/{id}") @org.springframework.transaction.annotation.Transactional @ResponseStatus(HttpStatus.NO_CONTENT) public void deleteStoreCategory(@PathVariable UUID id)`
- [serviceCities](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:102)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/service-cities") public List<ServiceCityView> serviceCities()`
- [createServiceCity](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:107)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PostMapping("/service-cities") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public ServiceCityView createServiceCity(@Valid @RequestBody ServiceCityRequest request)`
- [updateServiceCity](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:120)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PutMapping("/service-cities/{id}") @org.springframework.transaction.annotation.Transactional public ServiceCityView updateServiceCity(@PathVariable UUID id, @Valid @RequestBody ServiceCityRequest request)`
- [integrations](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:138)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/integrations") public List<IntegrationView> integrations()`
- [updateAccountProfile](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:155)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PutMapping("/accounts/{id}/profile") @org.springframework.transaction.annotation.Transactional public AdminAccountView updateAccountProfile(@PathVariable UUID id, @Valid @RequestBody AdminProfileUpdate request)`
- [updateStoreProfile](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:187)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PutMapping("/stores/{id}/profile") @org.springframework.transaction.annotation.Transactional public AdminStoreView updateStoreProfile(@PathVariable UUID id,@Valid @RequestBody br.com.fooddelivery.catalog.CatalogController.StoreRequest request)`
- [createOperator](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:202)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PostMapping("/operators") @ResponseStatus(HttpStatus.CREATED) @org.springframework.transaction.annotation.Transactional public Account createOperator(@Valid @RequestBody OperatorRequest request)`
- [operators](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:224)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/operators") public List<OperatorView> operators()`
- [allAccounts](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:230)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/accounts") public List<AdminAccountView> allAccounts()`
- [setAccountActive](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:235)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/accounts/{id}/active") @org.springframework.transaction.annotation.Transactional public AdminAccountView setAccountActive(@PathVariable UUID id, @RequestBody AccountActivity request)`
- [setRoleActive](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:251)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/accounts/{id}/roles/{role}") @org.springframework.transaction.annotation.Transactional public AdminAccountView setRoleActive(@PathVariable UUID id, @PathVariable AccountRole role, @RequestBody AccountActivity request)`
- [resetPassword](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:267)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PostMapping("/accounts/{id}/password-reset") @org.springframework.transaction.annotation.Transactional public Map<String, Object> resetPassword(@PathVariable UUID id)`
- [pendingApplications](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:279)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/operator-applications") public List<OperatorView> pendingApplications()`
- [approveApplication](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:284)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operator-applications/{id}/approve") @org.springframework.transaction.annotation.Transactional public OperatorView approveApplication(@PathVariable UUID id)`
- [rejectApplication](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:295)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operator-applications/{id}/reject") @org.springframework.transaction.annotation.Transactional public OperatorView rejectApplication(@PathVariable UUID id)`
- [deactivate](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:306)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operators/{id}/deactivate") @org.springframework.transaction.annotation.Transactional public Map<String, String> deactivate(@PathVariable UUID id)`
- [activate](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:324)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/operators/{id}/activate") @org.springframework.transaction.annotation.Transactional public Map<String, String> activate(@PathVariable UUID id)`
- [stores](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:337)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/stores") @org.springframework.transaction.annotation.Transactional(readOnly = true) public List<AdminStoreView> stores()`
- [approveStore](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:348)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/stores/{id}/approval") @org.springframework.transaction.annotation.Transactional public Store approveStore(@PathVariable UUID id, @RequestBody Approval request)`
- [setStoreActive](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:361)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/stores/{id}/active") @org.springframework.transaction.annotation.Transactional public Store setStoreActive(@PathVariable UUID id, @RequestBody StoreActivity request)`
- [setCommission](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:372)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@PatchMapping("/stores/{id}/commission") @org.springframework.transaction.annotation.Transactional public Map<String, Object> setCommission(@PathVariable UUID id, @Valid @RequestBody Commission request)`
- [auditEvents](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminController.java:382)
  - Classe: `@RestController @RequestMapping("/api/v1/admin") @PreAuthorize("hasRole('ADMIN')") public class AdminController`
  - Método: `@GetMapping("/audit-events") @org.springframework.transaction.annotation.Transactional(readOnly = true) public org.springframework.data.domain.Page<AdminAuditView> auditEvents( @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size)`

### AdminMfaController

- [status](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminMfaController.java:16)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/mfa") @PreAuthorize("hasRole('ADMIN')") public class AdminMfaController`
  - Método: `@GetMapping public Object status(Authentication a)`
- [begin](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminMfaController.java:17)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/mfa") @PreAuthorize("hasRole('ADMIN')") public class AdminMfaController`
  - Método: `@PostMapping("/begin") public Object begin(Authentication a,@Valid @RequestBody Request r)`
- [enable](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminMfaController.java:18)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/mfa") @PreAuthorize("hasRole('ADMIN')") public class AdminMfaController`
  - Método: `@PostMapping("/enable") public Object enable(Authentication a,@Valid @RequestBody Request r)`
- [disable](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/AdminMfaController.java:19)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/mfa") @PreAuthorize("hasRole('ADMIN')") public class AdminMfaController`
  - Método: `@PostMapping("/disable") public Object disable(Authentication a,@Valid @RequestBody Request r)`

### IntegrationSettingsController

- [status](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/IntegrationSettingsController.java:13)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/integration-settings") @PreAuthorize("hasRole('ADMIN')") public class IntegrationSettingsController`
  - Método: `@GetMapping public Object status()`
- [update](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/IntegrationSettingsController.java:14)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/integration-settings") @PreAuthorize("hasRole('ADMIN')") public class IntegrationSettingsController`
  - Método: `@PutMapping public Object update(Authentication auth,@Valid @RequestBody Request request)`

### ReconciliationController

- [list](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/ReconciliationController.java:15)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/payment-reconciliations") @PreAuthorize("hasRole('ADMIN')") public class ReconciliationController`
  - Método: `@GetMapping public Object list(@RequestParam(defaultValue="0")int page)`
- [reconcile](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/ReconciliationController.java:16)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/payment-reconciliations") @PreAuthorize("hasRole('ADMIN')") public class ReconciliationController`
  - Método: `@PostMapping("/{paymentId}") public Object reconcile(@PathVariable UUID paymentId,@Valid @RequestBody Request request,Authentication auth)`

### RefundAdminController

- [list](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/admin/RefundAdminController.java:25)
  - Classe: `@RestController @RequestMapping("/api/v1/admin/refunds") @PreAuthorize("hasRole('ADMIN')") public class RefundAdminController`
  - Método: `@GetMapping @Transactional(readOnly = true) public List<RefundCase> list()`

### HealthController

- [status](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/api/HealthController.java:11)
  - Classe: `@RestController @RequestMapping("/api/v1") public class HealthController`
  - Método: `@GetMapping("/status") public Map<String, String> status()`

### BookingAvailabilityController

- [publicPolicy](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:24)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/stores/{id}/booking-policy") @Transactional(readOnly=true) public Policy publicPolicy(@PathVariable UUID id)`
- [merchantPolicy](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:25)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/merchant/stores/{id}/booking-policy") @PreAuthorize("hasRole('MERCHANT')") @Transactional public Policy merchantPolicy(@PathVariable UUID id,Authentication a)`
- [merchantPolicySave](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:26)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PutMapping("/merchant/stores/{id}/booking-policy") @PreAuthorize("hasRole('MERCHANT')") @Transactional public Policy merchantPolicySave(@PathVariable UUID id,Authentication a,@Valid @RequestBody Policy r)`
- [adminPolicy](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:27)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/admin/stores/{id}/booking-policy") @PreAuthorize("hasRole('ADMIN')") @Transactional public Policy adminPolicy(@PathVariable UUID id,Authentication a)`
- [adminPolicySave](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:28)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PutMapping("/admin/stores/{id}/booking-policy") @PreAuthorize("hasRole('ADMIN')") @Transactional public Policy adminPolicySave(@PathVariable UUID id,Authentication a,@Valid @RequestBody Policy r)`
- [merchantHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:30)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/merchant/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('MERCHANT')") @Transactional public Object merchantHours(@PathVariable UUID id,@PathVariable UUID pid,Authentication a)`
- [merchantPreview](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:31)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PostMapping("/merchant/stores/{id}/professionals/{pid}/availability/preview") @PreAuthorize("hasRole('MERCHANT')") public Object merchantPreview(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r)`
- [merchantSave](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:32)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PutMapping("/merchant/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('MERCHANT')") public Object merchantSave(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r)`
- [adminProfessionals](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:33)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/admin/stores/{id}/professionals") @PreAuthorize("hasRole('ADMIN')") @Transactional public Object adminProfessionals(@PathVariable UUID id,Authentication a)`
- [adminHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:34)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/admin/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('ADMIN')") @Transactional public Object adminHours(@PathVariable UUID id,@PathVariable UUID pid,Authentication a)`
- [adminPreview](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:35)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PostMapping("/admin/stores/{id}/professionals/{pid}/availability/preview") @PreAuthorize("hasRole('ADMIN')") public Object adminPreview(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r)`
- [adminSave](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:36)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@PutMapping("/admin/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('ADMIN')") public Object adminSave(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r)`
- [available](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/BookingAvailabilityController.java:38)
  - Classe: `@RestController @RequestMapping("/api/v1") public class BookingAvailabilityController`
  - Método: `@GetMapping("/stores/{id}/availability") @Transactional(readOnly=true) public Object available(@PathVariable UUID id,@RequestParam UUID serviceItemId,@RequestParam LocalDate date,@RequestParam(required=false)UUID professionalId)`

### CatalogController

- [categories](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:32)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/store-categories") @Transactional(readOnly=true) public List<StoreCategories.Category> categories()`
- [locations](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:38)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/store-locations") @Transactional(readOnly=true) public List<LocationView> locations()`
- [stores](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:47)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores") @Transactional(readOnly=true) public List<StoreView> stores(@RequestParam(required=false) String city,@RequestParam(required=false) String state)`
- [items](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:54)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores/{id}/items") @Transactional(readOnly=true) public List<CatalogItemView> items(@PathVariable UUID id)`
- [createStore](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:59)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") @Transactional public StoreView createStore(@Valid @RequestBody StoreRequest req, Authentication auth)`
- [myStores](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:67)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/mine") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<StoreView> myStores(Authentication auth)`
- [resubmitStore](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:70)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/resubmit") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public StoreView resubmitStore(@PathVariable UUID id,Authentication auth)`
- [updateStore](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:72)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/stores/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public StoreView updateStore(@PathVariable UUID id, @Valid @RequestBody StoreRequest req, Authentication auth)`
- [merchantHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:80)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/{id}/hours") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public StoreOperatingHours.Configuration merchantHours(@PathVariable UUID id, Authentication auth)`
- [previewHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:82)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/hours/preview") @PreAuthorize("hasRole('MERCHANT')") public StoreHoursChangeService.Preview previewHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)`
- [updateMerchantHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:84)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/stores/{id}/hours") @PreAuthorize("hasRole('MERCHANT')") public StoreHoursChangeService.Saved updateMerchantHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)`
- [administratorHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:86)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/admin/stores/{id}/hours") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true) public StoreOperatingHours.Configuration administratorHours(@PathVariable UUID id)`
- [administratorPreviewHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:88)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/admin/stores/{id}/hours/preview") @PreAuthorize("hasRole('ADMIN')") public StoreHoursChangeService.Preview administratorPreviewHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)`
- [administratorSaveHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:90)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/admin/stores/{id}/hours") @PreAuthorize("hasRole('ADMIN')") public StoreHoursChangeService.Saved administratorSaveHours(@PathVariable UUID id,@RequestBody StoreHoursChangeService.ChangeRequest request,Authentication auth)`
- [publicHours](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:92)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores/{id}/hours") @Transactional(readOnly=true) public StoreOperatingHours.Configuration publicHours(@PathVariable UUID id)`
- [createItem](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:102)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/items") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')") public CatalogItemView createItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth)`
- [myStoreItems](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:108)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/{id}/items") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<CatalogItemView> myStoreItems(@PathVariable UUID id, Authentication auth)`
- [storeProfessionals](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:114)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/stores/{id}/professionals") @Transactional(readOnly=true) public List<ProfessionalView> storeProfessionals(@PathVariable UUID id)`
- [myProfessionals](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:119)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@GetMapping("/merchant/stores/{id}/professionals") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true) public List<ProfessionalView> myProfessionals(@PathVariable UUID id, Authentication auth)`
- [createProfessional](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:123)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PostMapping("/merchant/stores/{id}/professionals") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional public ProfessionalView createProfessional(@PathVariable UUID id,@Valid @RequestBody ProfessionalRequest request,Authentication auth)`
- [updateProfessional](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:127)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/professionals/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ProfessionalView updateProfessional(@PathVariable UUID id,@Valid @RequestBody ProfessionalRequest request,Authentication auth)`
- [setProfessionalActive](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:133)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PatchMapping("/merchant/professionals/{id}/active") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ProfessionalView setProfessionalActive(@PathVariable UUID id,@RequestBody ProfessionalActiveRequest request,Authentication auth)`
- [updateItem](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:139)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CatalogController`
  - Método: `@PutMapping("/merchant/items/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public CatalogItemView updateItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth)`
- [deactivateItem](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/catalog/CatalogController.java:144)
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

### CourierFinanceController

- [mine](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java:16)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@GetMapping("/courier/finance") @PreAuthorize("hasRole('COURIER')") public Object mine(Authentication a,@RequestParam(defaultValue="0") int page)`
- [code](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java:17)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/courier/payout-code") @PreAuthorize("hasRole('COURIER')") public Object code(Authentication a,@Valid @RequestBody Password r)`
- [request](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java:18)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/courier/payouts") @PreAuthorize("hasRole('COURIER')") public Object request(Authentication a,@Valid @RequestBody Request r)`
- [cancel](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java:19)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/courier/payouts/{id}/cancel") @PreAuthorize("hasRole('COURIER')") public Object cancel(Authentication a,@PathVariable UUID id)`
- [admin](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java:20)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@GetMapping("/admin/courier-finance") @PreAuthorize("hasRole('ADMIN')") public Object admin(@RequestParam(defaultValue="0") int page)`
- [policy](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java:21)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/admin/courier-finance/policy") @PreAuthorize("hasRole('ADMIN')") public Object policy(Authentication a,@Valid @RequestBody Policy r)`
- [decision](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java:22)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/admin/courier-finance/payouts/{id}/decision") @PreAuthorize("hasRole('ADMIN')") public Object decision(Authentication a,@PathVariable UUID id,@Valid @RequestBody Decision r)`
- [paid](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/finance/CourierFinanceController.java:23)
  - Classe: `@RestController @RequestMapping("/api/v1") public class CourierFinanceController`
  - Método: `@PostMapping("/admin/courier-finance/payouts/{id}/paid") @PreAuthorize("hasRole('ADMIN')") public Object paid(Authentication a,@PathVariable UUID id,@Valid @RequestBody Paid r)`

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
- [mine](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:111)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@GetMapping("/reservations/mine") @PreAuthorize("hasRole('CUSTOMER')") @Transactional(readOnly = true) public List<ReservationView> mine(Authentication auth)`
- [merchant](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:121)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@GetMapping("/merchant/reservations") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly = true) public List<ReservationView> merchant(Authentication auth)`
- [cancelUnpaid](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:131)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PostMapping("/reservations/{id}/cancel") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public ReservationView cancelUnpaid(@PathVariable UUID id, Authentication auth)`
- [requestCancellation](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:155)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PostMapping("/reservations/{id}/cancellation-request") @PreAuthorize("hasRole('CUSTOMER')") @Transactional public ReservationView requestCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationNote request,Authentication auth)`
- [decideCancellation](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:176)
  - Classe: `@RestController @RequestMapping("/api/v1") public class ReservationController`
  - Método: `@PatchMapping("/merchant/reservations/{id}/cancellation-request") @PreAuthorize("hasRole('MERCHANT')") @Transactional public ReservationView decideCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationDecision request,Authentication auth)`
- [update](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/reservations/ReservationController.java:191)
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

### DemoWhatsAppController

- [accept](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/whatsapp/DemoWhatsAppController.java:11)
  - Classe: `@RestController @Profile("homologacao") @RequestMapping("/api/v1/demo/whatsapp") @PreAuthorize("hasRole('ADMIN')") public class DemoWhatsAppController`
  - Método: `@PostMapping public Object accept(@Valid @RequestBody Request r)`

### WhatsAppController

- [status](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java:21)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@GetMapping("/account/whatsapp-link") public Object status(Authentication a)`
- [bind](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java:22)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@PostMapping("/account/whatsapp-link") public Object bind(Authentication a,@Valid @RequestBody Password request)`
- [unbind](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java:23)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@DeleteMapping("/account/whatsapp-link") public Object unbind(Authentication a,@Valid @RequestBody Password request)`
- [verify](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java:25)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@GetMapping(value="/webhooks/whatsapp",produces="text/plain") public String verify(@RequestParam(name="hub.mode")String mode,@RequestParam(name="hub.verify_token")String token,@RequestParam(name="hub.challenge")String challenge)`
- [receive](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java:27)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@PostMapping("/webhooks/whatsapp") public Object receive(@RequestBody byte[] body,@RequestHeader(name="X-Hub-Signature-256",required=false)String signature)`
- [admin](/home/wilker/Projetos/FoodDelivery/Backend/src/main/java/br/com/fooddelivery/whatsapp/WhatsAppController.java:28)
  - Classe: `@RestController @RequestMapping("/api/v1") public class WhatsAppController`
  - Método: `@GetMapping("/admin/whatsapp-status") @PreAuthorize("hasRole('ADMIN')") public Object admin()`

## Migrações e razão da separação

Flyway executa a sequência V1–V31. V9 tem variantes H2/PostgreSQL para diferenças de mecanismo. O perfil escolhe uma variante, nunca as duas. Os demais scripts são comuns. Hibernate valida o resultado; não é proprietário do esquema.

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
| [V25__store_fulfillment_options.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V25__store_fulfillment_options.sql) | 4 instruções; relações/índices: `stores` |
| [V26__reservation_hours_change_notice.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V26__reservation_hours_change_notice.sql) | 4 instruções; relações/índices: `reservations` |
| [V27__courier_finance.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V27__courier_finance.sql) | 7 instruções; relações/índices: `courier_compensation`, `courier_earnings`, `courier_payouts`, `delivery_tasks` |
| [V28__payment_reconciliation.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V28__payment_reconciliation.sql) | 2 instruções; relações/índices: `payment_reconciliations` |
| [V29__private_configuration_and_admin_mfa.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V29__private_configuration_and_admin_mfa.sql) | 2 instruções; relações/índices: `administrator_mfa`, `private_settings` |
| [V30__professional_availability_and_booking_policy.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V30__professional_availability_and_booking_policy.sql) | 6 instruções; relações/índices: `store_professionals`, `stores` |
| [V31__whatsapp_commerce.sql](/home/wilker/Projetos/FoodDelivery/Backend/src/main/resources/db/migration-common/V31__whatsapp_commerce.sql) | 7 instruções; relações/índices: `whatsapp_bindings`, `whatsapp_events`, `whatsapp_replies`, `whatsapp_sessions` |

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
