package br.com.fooddelivery.admin;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.catalog.StoreCategories;
import br.com.fooddelivery.catalog.StoreCategoryRepository;
import br.com.fooddelivery.catalog.StoreRepository;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRepository;
import br.com.fooddelivery.identity.AccountRole;
import br.com.fooddelivery.notifications.NotificationService;
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

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final br.com.fooddelivery.identity.PasswordRecoveryService recovery;
    private final AccountRepository accounts;
    private final StoreRepository stores;
    private final PasswordEncoder encoder;
    private final NotificationService notifications;
    private final StoreCategoryRepository storeCategories;
    private final AdminAuditService auditService;
    private final br.com.fooddelivery.catalog.StoreHoursChangeService hoursChanges;

    public AdminController(AccountRepository accounts, StoreRepository stores, PasswordEncoder encoder, NotificationService notifications, StoreCategoryRepository storeCategories, AdminAuditService auditService, br.com.fooddelivery.identity.PasswordRecoveryService recovery, br.com.fooddelivery.catalog.StoreHoursChangeService hoursChanges) {
        this.recovery = recovery;this.hoursChanges=hoursChanges;
        this.accounts = accounts;
        this.stores = stores;
        this.encoder = encoder;
        this.notifications = notifications;
        this.storeCategories = storeCategories;
        this.auditService = auditService;
    }

    /** Returns readiness only; secret values are never returned to the browser. */

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
        if (!account.getEmail().equalsIgnoreCase(email)
                || !java.util.Objects.equals(phoneDigits(account.getPhone()),phoneDigits(request.phone()))) recovery.invalidatePhoneCodes(account);
        account.updateProfile(request.name().trim(), clean(request.phone()), clean(request.addressLine()), clean(request.addressComplement()),
                clean(request.neighborhood()), clean(request.city()), clean(request.state()), clean(request.postalCode()));
        account.changeEmailByAdministrator(email);
        if (cpf != null) account.setCpfByAdministrator(cpf);
        else if (request.clearCpf()) {
            // A conta pode ter mais de um papel registrado. A regra de CPF opcional
            // acompanha a permissão administrativa, não o último papel ativo.
            if (!account.getRegisteredRoles().contains(AccountRole.ADMIN)) throw new IllegalArgumentException("O CPF continua obrigatório para clientes e perfis profissionais.");
            account.setCpfByAdministrator(null);
        }
        Account saved = accounts.save(account);
        audit("ACCOUNT_PROFILE_UPDATED", "ACCOUNT", id, "Dados de perfil atualizados pelo administrador.");
        return adminAccountView(saved);
    }

    private static String clean(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    private static String phoneDigits(String value) { return value == null ? "" : value.replaceAll("\\D", ""); }

    @PutMapping("/stores/{id}/profile")
    @org.springframework.transaction.annotation.Transactional
    public AdminStoreView updateStoreProfile(@PathVariable UUID id,@Valid @RequestBody br.com.fooddelivery.catalog.CatalogController.StoreRequest request) {
        Store store=stores.findByIdForUpdate(id).orElseThrow();
        String category=request.category().trim();
        if(!StoreCategories.isSupported(category)&&storeCategories.findByNameIgnoreCase(category).isEmpty())throw new IllegalArgumentException("Escolha uma categoria cadastrada.");
        store.update(request.name().trim(),clean(request.description()),request.address().trim(),request.city().trim(),request.state(),category,store.isActive(),request.deliveryFeeCents());
        hoursChanges.requireSafeTimezoneChange(store,request.timezone()==null||request.timezone().isBlank()?"America/Sao_Paulo":request.timezone());
        store.setTimezone(request.timezone());store.setCoordinates(request.latitude(),request.longitude());store.configureFulfillment(request.deliveryEnabled(),request.pickupEnabled(),request.inPersonEnabled());
        store.correctCnpjByAdministrator(request.cnpj());
        stores.save(store);
        audit("STORE_PROFILE_UPDATED","STORE",id,"Dados do estabelecimento corrigidos pelo administrador.");
        return adminStoreView(store);
    }

    @PostMapping("/operators")
    @ResponseStatus(HttpStatus.CREATED)
    @org.springframework.transaction.annotation.Transactional
    public Account createOperator(@Valid @RequestBody OperatorRequest request) {
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        String cpf = request.cpf().replaceAll("\\D", "");
        if (!br.com.fooddelivery.identity.Cpf.isValid(cpf)) throw new IllegalArgumentException("Informe um CPF válido.");
        if (accounts.existsByCpf(cpf)) {
            Account existing=accounts.findByCpf(cpf).orElseThrow();
            if(!existing.getEmail().equalsIgnoreCase(email))throw new IllegalArgumentException("Este CPF já pertence a outra conta. Peça à pessoa que solicite o novo perfil pela própria conta.");
            if(existing.getRegisteredRoles().contains(request.role()))throw new IllegalArgumentException("Este perfil já está vinculado à conta existente.");
            existing.setRole(request.role());Account saved=accounts.save(existing);audit("OPERATOR_PROFILE_LINKED", "ACCOUNT", saved.getId(), "Perfil profissional vinculado à conta existente.");return saved;
        }
        if (accounts.existsByEmailIgnoreCase(email)) throw new IllegalArgumentException("E-mail já cadastrado.");
        Account account = new Account(email, request.name().trim(), encoder.encode(request.password()));
        account.setCpfOnce(cpf);
        account.setRole(request.role());
        Account saved=accounts.save(account);
        audit("OPERATOR_ACCOUNT_CREATED", "ACCOUNT", saved.getId(), "Acesso profissional criado pelo administrador.");
        return saved;
    }

    @GetMapping("/operators")
    public List<OperatorView> operators() {
        return operatorPage(0, 50).content();
    }

    @GetMapping("/accounts")
    public List<AdminAccountView> allAccounts() {
        return accountPage(0, 50).content();
    }

    @GetMapping("/operators/page")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public AdminPage<OperatorView> operatorPage(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25") int size) {
        return AdminPage.from(accounts.findOperators(AdminPage.request(page, size, "name")).map(AdminController::operatorView));
    }

    @GetMapping("/accounts/page")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public AdminPage<AdminAccountView> accountPage(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25") int size) {
        return AdminPage.from(accounts.findAll(AdminPage.request(page, size, "name")).map(AdminController::adminAccountView));
    }

    @GetMapping("/stores/page")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public AdminPage<AdminStoreView> storePage(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="25") int size,
            @RequestParam(defaultValue="ALL") String status) {
        var request = AdminPage.request(page, size, "name");
        var result = switch (status) {
            case "ALL" -> stores.findAll(request);
            case "PENDING_REVIEW" -> stores.findByActiveAndApproved(true, false, request);
            case "CONFIRMED" -> stores.findByActiveAndApproved(true, true, request);
            case "STORE_SUSPENDED" -> stores.findByActiveFalse(request);
            default -> throw new IllegalArgumentException("Filtro de lojas inválido.");
        };
        return AdminPage.from(result.map(AdminController::adminStoreView));
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
        audit(request.active()?"ACCOUNT_ACTIVATED":"ACCOUNT_SUSPENDED", "ACCOUNT", id, "Situação da conta alterada pelo administrador.");
        return adminAccountView(saved);
    }

    @PatchMapping("/accounts/{id}/roles/{role}")
    @org.springframework.transaction.annotation.Transactional
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
        audit(request.active()?"ACCOUNT_ROLE_ACTIVATED":"ACCOUNT_ROLE_SUSPENDED", "ACCOUNT", id, "Acesso de perfil " + role.name() + " alterado.");
        return adminAccountView(saved);
    }

    @PostMapping("/accounts/{id}/password-reset")
    @org.springframework.transaction.annotation.Transactional
    public Map<String, Object> resetPassword(@PathVariable UUID id) {
        Account account = accounts.findById(id).orElseThrow();
        byte[] secret = new byte[18]; new SecureRandom().nextBytes(secret);
        String temporaryPassword = Base64.getUrlEncoder().withoutPadding().encodeToString(secret);
        account.requirePasswordChange(encoder.encode(temporaryPassword));
        accounts.save(account);
        audit("PASSWORD_RESET", "ACCOUNT", id, "Senha redefinida pelo administrador; troca obrigatória no próximo acesso.");
        return Map.of("temporaryPassword", temporaryPassword, "mustChangePassword", true);
    }

    @GetMapping("/operator-applications")
    public List<OperatorView> pendingApplications() {
        return accounts.findByOperatorApplicationStatus("PENDING").stream().map(AdminController::operatorView).toList();
    }

    @PatchMapping("/operator-applications/{id}/approve")
    @org.springframework.transaction.annotation.Transactional
    public OperatorView approveApplication(@PathVariable UUID id) {
        Account account = accounts.findById(id).orElseThrow();
        account.approveOperatorApplication();
        Account saved=accounts.save(account);
        notifications.send(saved, "Acesso aprovado", "Seu acesso de "+(saved.getRole()==AccountRole.MERCHANT?"vendedor":"entregador")+" foi aprovado. Entre novamente para escolher esse espaço.", "success");
        audit("OPERATOR_REQUEST_APPROVED", "ACCOUNT", id, "Solicitação de acesso profissional aprovada.");
        return operatorView(saved);
    }

    @PatchMapping("/operator-applications/{id}/reject")
    @org.springframework.transaction.annotation.Transactional
    public OperatorView rejectApplication(@PathVariable UUID id) {
        Account account = accounts.findById(id).orElseThrow();
        account.rejectOperatorApplication();
        Account saved=accounts.save(account);
        notifications.send(saved, "Solicitação analisada", "Sua solicitação de acesso não foi aprovada desta vez. Sua conta de cliente continua disponível.", "info");
        audit("OPERATOR_REQUEST_REJECTED", "ACCOUNT", id, "Solicitação de acesso profissional recusada.");
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
        audit("OPERATOR_SUSPENDED", "ACCOUNT", id, "Perfil profissional suspenso pelo administrador.");
        return Map.of("status", "deactivated");
    }

    @PatchMapping("/operators/{id}/activate")
    @org.springframework.transaction.annotation.Transactional
    public Map<String, String> activate(@PathVariable UUID id) {
        Account account = accounts.findById(id).orElseThrow();
        if ((account.getRole() != AccountRole.MERCHANT && account.getRole() != AccountRole.COURIER)
                || "PENDING".equals(account.getOperatorApplicationStatus()))
            throw new IllegalArgumentException("Esta conta não pode ser ativada por este fluxo.");
        account.setRoleActive(account.getRole(), true);
        accounts.save(account);
        audit("OPERATOR_REACTIVATED", "ACCOUNT", id, "Perfil profissional reativado pelo administrador.");
        return Map.of("status", "active");
    }

    @GetMapping("/stores")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<AdminStoreView> stores() {
        return storePage(0, 50, "ALL").content();
    }
    private static AdminStoreView adminStoreView(Store store){return new AdminStoreView(store.getId(), store.getName(),
                store.getDescription(), store.getCategory(), store.getAddress(), store.getCity(), store.getState(), store.getCnpj(), store.isActive(), store.isApproved(),
                store.getOwner().getName(), store.getOwner().getEmail(), store.getMarketplaceFeeBps() / 100.0,
                store.getDeliveryFeeCents(), store.getCoverImageUrl(),store.getTimezone(),store.getLatitude(),store.getLongitude(),store.isDeliveryEnabled(),store.isPickupEnabled(),store.isInPersonEnabled());
    }

    @PatchMapping("/stores/{id}/approval")
    @org.springframework.transaction.annotation.Transactional
    public Store approveStore(@PathVariable UUID id, @RequestBody Approval request) {
        Store store = stores.findById(id).orElseThrow();
        if (request.approved() && (!store.getOwner().isActive() || !store.getOwner().getGrantedRoles().contains(AccountRole.MERCHANT)))
            throw new IllegalArgumentException("A conta do vendedor está suspensa ou sem acesso ativo. Reative o perfil antes de aprovar a loja.");
        store.setApproved(request.approved());
        if (!request.approved()) store.setActive(false);
        Store saved=stores.save(store);
        audit(request.approved()?"STORE_APPROVED":"STORE_REJECTED", "STORE", id, request.approved()?"Loja aprovada pelo administrador.":"Loja reprovada pelo administrador.");
        return saved;
    }

    @PatchMapping("/stores/{id}/active")
    @org.springframework.transaction.annotation.Transactional
    public Store setStoreActive(@PathVariable UUID id, @RequestBody StoreActivity request) {
        Store store = stores.findById(id).orElseThrow();
        store.setActive(request.active());
        if (!request.active()) store.setApproved(false);
        Store saved=stores.save(store);
        audit(request.active()?"STORE_ACTIVATED":"STORE_SUSPENDED", "STORE", id, "Disponibilidade da loja alterada pelo administrador.");
        return saved;
    }

    @PatchMapping("/stores/{id}/commission")
    @org.springframework.transaction.annotation.Transactional
    public Map<String, Object> setCommission(@PathVariable UUID id, @Valid @RequestBody Commission request) {
        Store store = stores.findById(id).orElseThrow();
        store.setMarketplaceFeeBps(request.percent().movePointRight(2).intValueExact());
        stores.save(store);
        audit("STORE_COMMISSION_UPDATED", "STORE", id, "Comissão do marketplace atualizada.");
        return Map.of("storeId", id, "commissionPercent", request.percent());
    }

    @GetMapping("/audit-events")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public org.springframework.data.domain.Page<AdminAuditView> auditEvents(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {
        return auditService.latest(page, size).map(event -> new AdminAuditView(event.getId(), event.getActorId(),
                event.getAction(), event.getTargetType(), event.getTargetId(), event.getSummary(), event.getCreatedAt()));
    }

    private void audit(String action, String targetType, Object targetId, String summary) {
        auditService.recordCurrent(action, targetType, targetId, summary);
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
                                 double commissionPercent, long deliveryFeeCents, String coverImageUrl,String timezone,Double latitude,Double longitude,boolean deliveryEnabled,boolean pickupEnabled,boolean inPersonEnabled) {}

    public record AdminAuditView(UUID id, UUID actorId, String action, String targetType, String targetId, String summary, java.time.Instant createdAt) {}
    public record AdminProfileUpdate(@NotBlank @Size(max=120) String name, @Email @NotBlank @Size(max=254) String email,
                                     @Size(max=30) String phone, @Size(max=14) String cpf,
                                     @Size(max=250) String addressLine, @Size(max=120) String addressComplement,
                                     @Size(max=120) String neighborhood, @Size(max=100) String city,
                                     @Size(max=2) String state, @Size(max=9) String postalCode, boolean clearCpf) {}
}
