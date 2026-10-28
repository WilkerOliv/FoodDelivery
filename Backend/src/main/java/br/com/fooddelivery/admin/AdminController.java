package br.com.fooddelivery.admin;

import br.com.fooddelivery.catalog.Store;
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
    private final AccountRepository accounts;
    private final StoreRepository stores;
    private final PasswordEncoder encoder;
    private final NotificationService notifications;

    public AdminController(AccountRepository accounts, StoreRepository stores, PasswordEncoder encoder, NotificationService notifications) {
        this.accounts = accounts;
        this.stores = stores;
        this.encoder = encoder;
        this.notifications = notifications;
    }

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
        if (account.getRole() == AccountRole.ADMIN) throw new IllegalArgumentException("O painel não pode redefinir a senha de outra conta administrativa.");
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
        return new AdminAccountView(account.getId(), account.getName(), account.getEmail(), maskedCpf,
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
    public record AdminAccountView(UUID id, String name, String email, String maskedCpf, AccountRole role,
                                   List<AccountRole> registeredRoles, List<AccountRole> grantedRoles, boolean active, String applicationStatus,
                                   java.time.Instant createdAt) {}
    public record AdminStoreView(UUID id, String name, String description, String category, String address, String city, String state, String cnpj,
                                 boolean active, boolean approved, String ownerName, String ownerEmail,
                                 double commissionPercent, long deliveryFeeCents, String coverImageUrl) {}
}
