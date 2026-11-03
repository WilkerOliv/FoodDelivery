package br.com.fooddelivery.admin;

import br.com.fooddelivery.catalog.StoreRepository;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRepository;
import br.com.fooddelivery.identity.AccountRole;
import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.platform.ServiceCityRepository;
import br.com.fooddelivery.catalog.StoreCategoryRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminProfileCpfTest {
    private final AccountRepository accounts = mock(AccountRepository.class);
    private final AdminAuditService audit = mock(AdminAuditService.class);
    private final AdminController controller = new AdminController(accounts, mock(StoreRepository.class), mock(org.springframework.security.crypto.password.PasswordEncoder.class), mock(NotificationService.class), mock(ServiceCityRepository.class), mock(StoreCategoryRepository.class), audit);
    private final UUID id = UUID.fromString("00000000-0000-0000-0000-000000000011");

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void administratorCanExplicitlyRemoveTheirOptionalCpf() {
        Account account = account(AccountRole.ADMIN);
        when(accounts.findByIdForUpdate(id)).thenReturn(Optional.of(account));
        when(accounts.existsByEmailIgnoreCaseAndIdNot("admin@fooddelivery.local", id)).thenReturn(false);
        when(accounts.save(account)).thenReturn(account);
        authenticateAs(id);

        controller.updateAccountProfile(id, request(true));

        assertNull(account.getCpf());
    }

    @Test
    void registeredAdministratorKeepsOptionalCpfRuleEvenWhenAnotherRoleIsActive() {
        Account account = account(AccountRole.ADMIN);
        account.setRole(AccountRole.MERCHANT);
        when(accounts.findByIdForUpdate(id)).thenReturn(Optional.of(account));
        when(accounts.existsByEmailIgnoreCaseAndIdNot("admin@fooddelivery.local", id)).thenReturn(false);
        when(accounts.save(account)).thenReturn(account);
        authenticateAs(id);

        controller.updateAccountProfile(id, request(true));

        assertNull(account.getCpf());
        assertTrue(account.getRegisteredRoles().contains(AccountRole.ADMIN));
    }

    @Test
    void administratorCannotRemoveCpfFromCustomerOrProfessionalAccount() {
        Account account = account(AccountRole.MERCHANT);
        when(accounts.findByIdForUpdate(id)).thenReturn(Optional.of(account));
        when(accounts.existsByEmailIgnoreCaseAndIdNot("admin@fooddelivery.local", id)).thenReturn(false);
        authenticateAs(id);

        assertThrows(IllegalArgumentException.class, () -> controller.updateAccountProfile(id, request(true)));
    }

    private static Account account(AccountRole role) {
        Account account = new Account("admin@fooddelivery.local", "Administrador", "hash");
        account.setRole(role);
        account.setCpfOnce("47329438880");
        return account;
    }

    private static AdminController.AdminProfileUpdate request(boolean clearCpf) {
        return new AdminController.AdminProfileUpdate("Administrador", "admin@fooddelivery.local", null, null,
                null, null, null, null, null, null, clearCpf);
    }

    private static void authenticateAs(UUID id) {
        Instant now = Instant.now();
        Jwt jwt = Jwt.withTokenValue("test-token").header("alg", "HS256").subject(id.toString())
                .issuedAt(now).expiresAt(now.plusSeconds(300)).build();
        SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(jwt, List.of()));
    }
}
