package br.com.fooddelivery.admin;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.catalog.StoreCategories;
import br.com.fooddelivery.catalog.StoreCategory;
import br.com.fooddelivery.catalog.StoreCategoryRepository;
import br.com.fooddelivery.catalog.StoreRepository;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRepository;
import br.com.fooddelivery.identity.AccountRole;
import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.platform.ServiceCity;
import br.com.fooddelivery.platform.ServiceCityRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AccountRepository accounts;
    private final StoreRepository stores;
    private final PasswordEncoder encoder;
    private final NotificationService notifications;
    private final ServiceCityRepository serviceCities;
    private final StoreCategoryRepository storeCategories;
    @Value("${spring.mail.host:}") private String smtpHost;
    @Value("${spring.mail.username:}") private String smtpUsername;
    @Value("${spring.mail.password:}") private String smtpPassword;
    @Value("${app.recovery.whatsapp.enabled:false}") private boolean whatsappEnabled;
    @Value("${app.recovery.whatsapp.phone-number-id:}") private String whatsappPhoneId;
    @Value("${app.recovery.whatsapp.access-token:}") private String whatsappToken;
    @Value("${app.ai.openai.api-key:}") private String openAiKey;
    @Value("${app.payment.mercadopago.client-id:}") private String mercadoPagoClientId;
    @Value("${app.payment.mercadopago.client-secret:}") private String mercadoPagoClientSecret;
    @Value("${app.payment.mercadopago.token-encryption-key:}") private String mercadoPagoEncryptionKey;
    @Value("${app.payment.mercadopago.webhook-secret:}") private String mercadoPagoWebhookSecret;
    @Value("${app.payment.mercadopago.notification-base-url:}") private String mercadoPagoNotificationUrl;

    public AdminController(AccountRepository accounts, StoreRepository stores, PasswordEncoder encoder, NotificationService notifications, ServiceCityRepository serviceCities, StoreCategoryRepository storeCategories) {
        this.accounts = accounts;
        this.stores = stores;
        this.encoder = encoder;
        this.notifications = notifications;
        this.serviceCities = serviceCities;
        this.storeCategories = storeCategories;
    }

    @GetMapping("/store-categories")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<AdminStoreCategoryView> storeCategories() {
        List<AdminStoreCategoryView> builtIns = StoreCategories.ALL.stream()
                .map(category -> new AdminStoreCategoryView(null, category.name(), category.emoji(), true)).toList();
        List<AdminStoreCategoryView> custom = storeCategories.findAllByOrderByNameAsc().stream()
                .map(category -> new AdminStoreCategoryView(category.getId(), category.getName(), category.getEmoji(), false)).toList();
        return java.util.stream.Stream.concat(builtIns.stream(), custom.stream()).toList();
    }

    @PostMapping("/store-categories")
    @ResponseStatus(HttpStatus.CREATED)
    @org.springframework.transaction.annotation.Transactional
    public AdminStoreCategoryView createStoreCategory(@Valid @RequestBody StoreCategoryRequest request) {
        String name = request.name().trim();
        if (StoreCategories.isSupported(name))
            throw new IllegalArgumentException("Essa categoria já existe e não pode ser duplicada.");
        StoreCategory category = new StoreCategory(name, request.emoji());
        if (storeCategories.findBySlugIgnoreCase(category.getSlug()).isPresent())
            throw new IllegalArgumentException("Já existe uma categoria com esse nome ou uma forma equivalente.");
        StoreCategory saved = storeCategories.save(category);
        return new AdminStoreCategoryView(saved.getId(), saved.getName(), saved.getEmoji(), false);
    }

    @DeleteMapping("/store-categories/{id}")
    @org.springframework.transaction.annotation.Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStoreCategory(@PathVariable UUID id) {
        StoreCategory category = storeCategories.findById(id).orElseThrow();
        long storesUsingCategory = stores.countByCategoryIgnoreCase(category.getName());
        if (storesUsingCategory > 0)
            throw new IllegalArgumentException("Esta seção está vinculada a " + storesUsingCategory + " loja(s). Altere a categoria dessas lojas antes de excluí-la.");
        storeCategories.delete(category);
    }

    @GetMapping("/service-cities")
    public List<ServiceCityView> serviceCities() {
        return serviceCities.findAllByOrderByCityAscStateAsc().stream().map(ServiceCityView::of).toList();
    }

    @PostMapping("/service-cities")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceCityView createServiceCity(@Valid @RequestBody ServiceCityRequest request) {
        String city = request.city().trim();
        String state = request.state().trim().toUpperCase(Locale.ROOT);
        if (serviceCities.findByCityIgnoreCaseAndStateIgnoreCase(city, state).isPresent())
            throw new IllegalArgumentException("Esta cidade já está cadastrada. Reative ou edite o cadastro existente.");
        return ServiceCityView.of(serviceCities.save(new ServiceCity(city, state, request.postalCode())));
    }

    @PutMapping("/service-cities/{id}")
    public ServiceCityView updateServiceCity(@PathVariable UUID id, @Valid @RequestBody ServiceCityRequest request) {
        ServiceCity city = serviceCities.findById(id).orElseThrow();
        String name = request.city().trim();
        String state = request.state().trim().toUpperCase(Locale.ROOT);
        serviceCities.findByCityIgnoreCaseAndStateIgnoreCase(name, state)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> { throw new IllegalArgumentException("Já existe um cadastro para esta cidade e estado."); });
        city.update(name, state, request.postalCode(), request.enabled());
        return ServiceCityView.of(serviceCities.save(city));
    }

    /** Returns readiness only; credentials are always configured outside the browser. */
    @GetMapping("/integrations")
    public List<IntegrationView> integrations() {
        return List.of(
                integration("SMTP", "E-mail de recuperação e avisos", ready(smtpHost, smtpUsername, smtpPassword), "SMTP_HOST, SMTP_PORT, SMTP_USERNAME, SMTP_PASSWORD, RECOVERY_EMAIL_FROM"),
                integration("WHATSAPP", "WhatsApp Cloud para códigos e avisos", whatsappEnabled && ready(whatsappPhoneId, whatsappToken), "WHATSAPP_CLOUD_ENABLED, WHATSAPP_PHONE_NUMBER_ID, WHATSAPP_ACCESS_TOKEN, WHATSAPP_OTP_TEMPLATE"),
                integration("OPENAI", "Geração de imagens para lojas", ready(openAiKey), "OPENAI_API_KEY, OPENAI_IMAGE_MODEL"),
                integration("MERCADOPAGO", "Checkout, pagamentos e webhooks", ready(mercadoPagoClientId, mercadoPagoClientSecret, mercadoPagoEncryptionKey, mercadoPagoWebhookSecret, mercadoPagoNotificationUrl), "MP_CLIENT_ID, MP_CLIENT_SECRET, MP_TOKEN_ENCRYPTION_KEY, MP_WEBHOOK_SECRET, MP_NOTIFICATION_BASE_URL"),
                integration("VIACEP", "Consulta pública de CEP", true, "Integração pública; não exige chave"),
                integration("MAPAS", "Mapa e localização das cidades", true, "Mapa incorporado; requer conexão com a internet")
        );
    }

    private static boolean ready(String... values) { return java.util.Arrays.stream(values).allMatch(value -> value != null && !value.isBlank()); }
    private static IntegrationView integration(String id, String name, boolean ready, String variables) {
        return new IntegrationView(id, name, ready, ready ? "Configurada (conexão não homologada)" : "Configuração incompleta", variables);
    }

    @PutMapping("/accounts/{id}/profile")
    @org.springframework.transaction.annotation.Transactional
    public AdminAccountView updateAccountProfile(@PathVariable UUID id, @Valid @RequestBody AdminProfileUpdate request) {
        Account account = accounts.findByIdForUpdate(id).orElseThrow();
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        if (accounts.existsByEmailIgnoreCaseAndIdNot(email, id)) throw new IllegalArgumentException("Este e-mail já está vinculado a outra conta.");
        String cpf = request.cpf() == null || request.cpf().isBlank() ? null : request.cpf().replaceAll("\\D", "");
        if (cpf != null) {
            if (!br.com.fooddelivery.identity.Cpf.isValid(cpf)) throw new IllegalArgumentException("Informe um CPF válido ou deixe o campo vazio.");
            accounts.findByCpf(cpf).filter(existing -> !existing.getId().equals(id))
                    .ifPresent(existing -> { throw new IllegalArgumentException("Este CPF já está vinculado a outra conta."); });
        }
        account.updateProfile(request.name().trim(), clean(request.phone()), clean(request.addressLine()), clean(request.addressComplement()),
                clean(request.neighborhood()), clean(request.city()), clean(request.state()), clean(request.postalCode()));
        account.changeEmailByAdministrator(email);
        if (cpf != null) account.setCpfByAdministrator(cpf);
        else if (request.clearCpf()) {
            if (account.getRole() != AccountRole.ADMIN) throw new IllegalArgumentException("O CPF continua obrigatório para clientes e perfis profissionais.");
            account.setCpfByAdministrator(null);
        }
        return adminAccountView(accounts.save(account));
    }

    private static String clean(String value) { return value == null || value.isBlank() ? null : value.trim(); }

    @PostMapping("/operators")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createOperator(@Valid @RequestBody OperatorRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        String cpf = request.cpf().replaceAll("\\D", "");
        if (!br.com.fooddelivery.identity.Cpf.isValid(cpf)) throw new IllegalArgumentException("Informe um CPF válido.");
        if (accounts.existsByCpf(cpf)) {
            Account existing=accounts.findByCpf(cpf).orElseThrow();
            if(!existing.getEmail().equalsIgnoreCase(email))throw new IllegalArgumentException("Este CPF já pertence a outra conta. Peça à pessoa que solicite o novo perfil pela própria conta.");
            if(existing.getRegisteredRoles().contains(request.role()))throw new IllegalArgumentException("Este perfil já está vinculado à conta existente.");
            existing.setRole(request.role());return accounts.save(existing);
        }
        if (accounts.existsByEmailIgnoreCase(email)) throw new IllegalArgumentException("E-mail já cadastrado.");
        Account account = new Account(email, request.name().trim(), encoder.encode(request.password()));
        account.setCpfOnce(cpf);
        account.setRole(request.role());
        return accounts.save(account);
    }

    @GetMapping("/operators")
    public List<OperatorView> operators() {
        return accounts.findAll().stream().filter(a -> a.getRegisteredRoles().stream().anyMatch(r -> r==AccountRole.MERCHANT||r==AccountRole.COURIER))
                .map(AdminController::operatorView).toList();
    }

    @GetMapping("/accounts")
    public List<AdminAccountView> allAccounts() {
        return accounts.findAll().stream().map(AdminController::adminAccountView).toList();
    }

    @PatchMapping("/accounts/{id}/active")
    @org.springframework.transaction.annotation.Transactional
    public AdminAccountView setAccountActive(@PathVariable UUID id, @RequestBody AccountActivity request) {
        Account account = accounts.findById(id).orElseThrow();
        if (account.getRole() == AccountRole.ADMIN) throw new IllegalArgumentException("A conta administrativa não pode ser suspensa por este painel.");
        if (request.active()) account.activate(); else account.deactivate();
        Account saved = accounts.save(account);
        if (!request.active()) {
            List<Store> ownedStores = stores.findByOwnerId(saved.getId());
            ownedStores.forEach(store -> { store.setActive(false); store.setApproved(false); });
            stores.saveAll(ownedStores);
        }
        return adminAccountView(saved);
    }

    @PatchMapping("/accounts/{id}/roles/{role}")
    public AdminAccountView setRoleActive(@PathVariable UUID id, @PathVariable AccountRole role, @RequestBody AccountActivity request) {
        Account account = accounts.findById(id).orElseThrow();
        if (account.getRole() == AccountRole.ADMIN) throw new IllegalArgumentException("A conta administrativa não pode ser alterada por este painel.");
        account.setRoleActive(role, request.active());
        Account saved = accounts.save(account);
        if (role == AccountRole.MERCHANT && !request.active()) {
            List<Store> ownedStores = stores.findByOwnerId(saved.getId());
            ownedStores.forEach(store -> { store.setActive(false); store.setApproved(false); });
            stores.saveAll(ownedStores);
        }
        return adminAccountView(saved);
    }

    @PostMapping("/accounts/{id}/password-reset")
    public Map<String, Object> resetPassword(@PathVariable UUID id) {
        Account account = accounts.findById(id).orElseThrow();
        byte[] secret = new byte[18]; new SecureRandom().nextBytes(secret);
        String temporaryPassword = Base64.getUrlEncoder().withoutPadding().encodeToString(secret);
        account.requirePasswordChange(encoder.encode(temporaryPassword));
        accounts.save(account);
        return Map.of("temporaryPassword", temporaryPassword, "mustChangePassword", true);
    }

    @GetMapping("/operator-applications")
    public List<OperatorView> pendingApplications() {
        return accounts.findByOperatorApplicationStatus("PENDING").stream().map(AdminController::operatorView).toList();
    }

    @PatchMapping("/operator-applications/{id}/approve")
    public OperatorView approveApplication(@PathVariable UUID id) {
        Account account = accounts.findById(id).orElseThrow();
        account.approveOperatorApplication();
        Account saved=accounts.save(account);
        notifications.send(saved, "Acesso aprovado", "Seu acesso de "+(saved.getRole()==AccountRole.MERCHANT?"vendedor":"entregador")+" foi aprovado. Entre novamente para escolher esse espaço.", "success");
        return operatorView(saved);
    }

    @PatchMapping("/operator-applications/{id}/reject")
    public OperatorView rejectApplication(@PathVariable UUID id) {
        Account account = accounts.findById(id).orElseThrow();
        account.rejectOperatorApplication();
        Account saved=accounts.save(account);
        notifications.send(saved, "Solicitação analisada", "Sua solicitação de acesso não foi aprovada desta vez. Sua conta de cliente continua disponível.", "info");
        return operatorView(saved);
    }

    @PatchMapping("/operators/{id}/deactivate")
    @org.springframework.transaction.annotation.Transactional
    public Map<String, String> deactivate(@PathVariable UUID id) {
        Account account = accounts.findById(id).orElseThrow();
        if (account.getRole() != AccountRole.MERCHANT && account.getRole() != AccountRole.COURIER)
            throw new IllegalArgumentException("Somente contas de vendedor ou entregador podem ser suspensas por este fluxo.");
        AccountRole role = account.getRole();
        account.setRoleActive(role, false);
        accounts.save(account);
        if (role == AccountRole.MERCHANT) {
            List<Store> ownedStores = stores.findByOwnerId(account.getId());
            ownedStores.forEach(store -> { store.setActive(false); store.setApproved(false); });
            stores.saveAll(ownedStores);
        }
        return Map.of("status", "deactivated");
    }

    @PatchMapping("/operators/{id}/activate")
    public Map<String, String> activate(@PathVariable UUID id) {
        Account account = accounts.findById(id).orElseThrow();
        if ((account.getRole() != AccountRole.MERCHANT && account.getRole() != AccountRole.COURIER)
                || "PENDING".equals(account.getOperatorApplicationStatus()))
            throw new IllegalArgumentException("Esta conta não pode ser ativada por este fluxo.");
        account.setRoleActive(account.getRole(), true);
        accounts.save(account);
        return Map.of("status", "active");
    }

    @GetMapping("/stores")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<AdminStoreView> stores() {
        return stores.findAll().stream().map(store -> new AdminStoreView(store.getId(), store.getName(),
                store.getDescription(), store.getCategory(), store.getAddress(), store.getCity(), store.getState(), store.getCnpj(), store.isActive(), store.isApproved(),
                store.getOwner().getName(), store.getOwner().getEmail(), store.getMarketplaceFeeBps() / 100.0,
                store.getDeliveryFeeCents(), store.getCoverImageUrl())).toList();
    }

    @PatchMapping("/stores/{id}/approval")
    @org.springframework.transaction.annotation.Transactional
    public Store approveStore(@PathVariable UUID id, @RequestBody Approval request) {
        Store store = stores.findById(id).orElseThrow();
        if (request.approved() && (!store.getOwner().isActive() || !store.getOwner().getGrantedRoles().contains(AccountRole.MERCHANT)))
            throw new IllegalArgumentException("A conta do vendedor está suspensa ou sem acesso ativo. Reative o perfil antes de aprovar a loja.");
        store.setApproved(request.approved());
        if (!request.approved()) store.setActive(false);
        return stores.save(store);
    }

    @PatchMapping("/stores/{id}/active")
    public Store setStoreActive(@PathVariable UUID id, @RequestBody StoreActivity request) {
        Store store = stores.findById(id).orElseThrow();
        store.setActive(request.active());
        if (!request.active()) store.setApproved(false);
        return stores.save(store);
    }

    @PatchMapping("/stores/{id}/commission")
    public Map<String, Object> setCommission(@PathVariable UUID id, @Valid @RequestBody Commission request) {
        Store store = stores.findById(id).orElseThrow();
        store.setMarketplaceFeeBps(request.percent().movePointRight(2).intValueExact());
        stores.save(store);
        return Map.of("storeId", id, "commissionPercent", request.percent());
    }

    private static OperatorView operatorView(Account account) {
        AccountRole viewRole = account.getRequestedOperatorRole() != null ? account.getRequestedOperatorRole() : account.getRole();
        return new OperatorView(account.getId(), account.getName(), account.getEmail(), viewRole,
                account.getGrantedRoles().contains(viewRole), account.getOperatorApplicationStatus(), account.getCreatedAt());
    }

    private static AdminAccountView adminAccountView(Account account) {
        String cpf = account.getCpf();
        String maskedCpf = cpf == null ? "Não informado" : "•••.•••.•••-" + cpf.substring(7);
        return new AdminAccountView(account.getId(), account.getName(), account.getEmail(), maskedCpf, account.getPhone(), account.getAddressLine(),
                account.getAddressComplement(), account.getNeighborhood(), account.getCity(), account.getState(), account.getPostalCode(),
                account.getRole(), account.getRegisteredRoles(), account.getGrantedRoles(), account.isActive(), account.getOperatorApplicationStatus(), account.getCreatedAt());
    }

    public record OperatorRequest(@NotBlank @Size(max = 120) String name, @Email @NotBlank String email,
                                  @Size(min = 12, max = 72) String password, @NotNull AccountRole role,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") String cpf) {
        public OperatorRequest {
            if (role != AccountRole.MERCHANT && role != AccountRole.COURIER)
                throw new IllegalArgumentException("Este fluxo cria somente vendedores ou entregadores.");
        }
    }
    public record Approval(boolean approved) {}
    public record StoreCategoryRequest(@NotBlank @Size(max=60) String name,
                                       @NotBlank @Size(max=16) String emoji) {}
    public record AdminStoreCategoryView(UUID id, String name, String emoji, boolean builtIn) {}
    public record StoreActivity(boolean active) {}
    public record AccountActivity(boolean active) {}
    public record Commission(@NotNull @DecimalMin("0.00") @DecimalMax("50.00") BigDecimal percent) {}
    public record OperatorView(UUID id, String name, String email, AccountRole role, boolean active,
                               String applicationStatus, java.time.Instant createdAt) {}
    public record AdminAccountView(UUID id, String name, String email, String maskedCpf, String phone, String addressLine,
                                   String addressComplement, String neighborhood, String city, String state, String postalCode, AccountRole role,
                                   List<AccountRole> registeredRoles, List<AccountRole> grantedRoles, boolean active, String applicationStatus,
                                   java.time.Instant createdAt) {}
    public record AdminStoreView(UUID id, String name, String description, String category, String address, String city, String state, String cnpj,
                                 boolean active, boolean approved, String ownerName, String ownerEmail,
                                 double commissionPercent, long deliveryFeeCents, String coverImageUrl) {}
    public record ServiceCityRequest(@NotBlank @Size(max=100) String city,
                                     @NotBlank @jakarta.validation.constraints.Pattern(regexp="[A-Za-z]{2}") String state,
                                     @jakarta.validation.constraints.Pattern(regexp="^$|\\d{5}-?\\d{3}") String postalCode,
                                     boolean enabled) {}
    public record ServiceCityView(UUID id, String city, String state, String postalCode, boolean enabled) {
        static ServiceCityView of(ServiceCity city) { return new ServiceCityView(city.getId(), city.getCity(), city.getState(), city.getPostalCode(), city.isEnabled()); }
    }
    public record IntegrationView(String id, String name, boolean configured, String status, String configuration) {}
    public record AdminProfileUpdate(@NotBlank @Size(max=120) String name, @Email @NotBlank @Size(max=254) String email,
                                     @Size(max=30) String phone, @Size(max=14) String cpf,
                                     @Size(max=250) String addressLine, @Size(max=120) String addressComplement,
                                     @Size(max=120) String neighborhood, @Size(max=100) String city,
                                     @Size(max=2) String state, @Size(max=9) String postalCode, boolean clearCpf) {}
}
