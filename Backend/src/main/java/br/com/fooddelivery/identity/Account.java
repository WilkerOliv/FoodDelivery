package br.com.fooddelivery.identity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 254)
    private String email;

    @Column(nullable = false, length = 120)
    private String name;

    @JsonIgnore
    @Column(name = "password_hash", nullable = false, length = 100)
    private String passwordHash;

    @Column(name = "phone", length = 30)
    private String phone;

    @Column(name = "address_line", length = 250)
    private String addressLine;

    @Column(name = "address_complement", length = 120)
    private String addressComplement;

    @Column(name = "neighborhood", length = 120)
    private String neighborhood;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 2)
    private String state;

    @Column(name = "postal_code", length = 9)
    private String postalCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AccountRole role = AccountRole.CUSTOMER;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "operator_application_status", length = 16)
    private String operatorApplicationStatus;

    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;

    @Column(name = "granted_roles", nullable = false, length = 80)
    private String grantedRoleCodes = "CUSTOMER";

    @Column(name = "suspended_roles", nullable = false, length = 80)
    private String suspendedRoleCodes = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "requested_operator_role", length = 20)
    private AccountRole requestedOperatorRole;

    @Column(name = "password_change_required", nullable = false)
    private boolean passwordChangeRequired;

    @Column(name = "credential_version", nullable = false)
    private int credentialVersion;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected Account() {}

    public Account(String email, String name, String passwordHash) {
        this.email = email;
        this.name = name;
        this.passwordHash = passwordHash;
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getPasswordHash() { return passwordHash; }
    public AccountRole getRole() { return role; }
    public boolean isActive() { return active; }
    public String getOperatorApplicationStatus() { return operatorApplicationStatus; }
    public String getCpf() { return cpf; }
    public List<AccountRole> getGrantedRoles() {
        if (!active) return List.of();
        Set<AccountRole> suspended = parseRoles(suspendedRoleCodes);
        return parseRoles(grantedRoleCodes).stream().filter(role -> !suspended.contains(role)).sorted().toList();
    }
    public List<AccountRole> getRegisteredRoles() { return parseRoles(grantedRoleCodes).stream().sorted().toList(); }
    public AccountRole getRequestedOperatorRole() { return requestedOperatorRole; }
    public boolean isPasswordChangeRequired() { return passwordChangeRequired; }
    public int getCredentialVersion() { return credentialVersion; }
    public Instant getCreatedAt() { return createdAt; }
    public String getPhone() { return phone; }
    public String getAddressLine() { return addressLine; }
    public String getAddressComplement() { return addressComplement; }
    public String getNeighborhood() { return neighborhood; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPostalCode() { return postalCode; }
    public void setRole(AccountRole role) {
        this.role = role;
        Set<AccountRole> roles = parseRoles(grantedRoleCodes);
        if (role == AccountRole.ADMIN) { roles.clear(); roles.add(AccountRole.ADMIN); }
        else { roles.add(AccountRole.CUSTOMER); roles.add(role); }
        grantedRoleCodes = encodeRoles(roles);
    }
    public void deactivate() { this.active = false; }
    public void submitOperatorApplication(AccountRole requestedRole) {
        if (requestedRole != AccountRole.MERCHANT && requestedRole != AccountRole.COURIER)
            throw new IllegalArgumentException("Escolha vendedor ou entregador.");
        if (getActiveRoles().contains(requestedRole)) throw new IllegalArgumentException("Esta conta já tem este tipo de acesso.");
        if ("PENDING".equals(operatorApplicationStatus)) throw new IllegalArgumentException("Já existe uma solicitação aguardando análise.");
        this.requestedOperatorRole = requestedRole;
        this.operatorApplicationStatus = "PENDING";
        this.active = true;
    }
    public void approveOperatorApplication() {
        if (!"PENDING".equals(operatorApplicationStatus)) throw new IllegalArgumentException("Esta solicitação não está pendente.");
        Set<AccountRole> roles = parseRoles(grantedRoleCodes); roles.add(requestedOperatorRole);
        grantedRoleCodes = encodeRoles(roles); role = requestedOperatorRole; requestedOperatorRole = null;
        this.operatorApplicationStatus = "APPROVED";
        this.active = true;
    }
    public void rejectOperatorApplication() {
        if (!"PENDING".equals(operatorApplicationStatus)) throw new IllegalArgumentException("Esta solicitação não está pendente.");
        requestedOperatorRole = null;
        this.operatorApplicationStatus = "REJECTED";
        this.active = true;
    }
    public void activate() { this.active = true; }

    public void setRoleActive(AccountRole requestedRole, boolean enabled) {
        Set<AccountRole> roles = parseRoles(grantedRoleCodes);
        if (!roles.contains(requestedRole) || requestedRole == AccountRole.ADMIN)
            throw new IllegalArgumentException("Este perfil não pode ser alterado por aqui.");
        Set<AccountRole> suspended = parseRoles(suspendedRoleCodes);
        if (enabled) suspended.remove(requestedRole); else suspended.add(requestedRole);
        suspendedRoleCodes = encodeRoles(suspended);
        // Per-profile suspension must never reactivate an account disabled globally.
        // If every profile is suspended, authentication may still succeed but no role is granted.
    }

    public void setCpfOnce(String value) {
        String normalized = value == null ? null : value.replaceAll("\\D", "");
        if (normalized == null || !Cpf.isValid(normalized)) throw new IllegalArgumentException("Informe um CPF válido.");
        if (cpf != null && !cpf.equals(normalized)) throw new IllegalArgumentException("O CPF não pode ser alterado depois do cadastro.");
        cpf = normalized;
    }

    public void changePassword(String encodedPassword) { passwordHash = encodedPassword; passwordChangeRequired = false; credentialVersion = Math.addExact(credentialVersion, 1); }
    public void requirePasswordChange(String encodedPassword) { passwordHash = encodedPassword; passwordChangeRequired = true; credentialVersion = Math.addExact(credentialVersion, 1); }

    private Set<AccountRole> getActiveRoles() { return Set.copyOf(getGrantedRoles()); }
    private static Set<AccountRole> parseRoles(String value) {
        EnumSet<AccountRole> result = EnumSet.noneOf(AccountRole.class);
        if (value != null && !value.isBlank()) Arrays.stream(value.split(",")).map(String::trim).filter(s -> !s.isEmpty())
                .map(AccountRole::valueOf).forEach(result::add);
        return result;
    }
    private static String encodeRoles(Set<AccountRole> roles) { return roles.stream().sorted().map(Enum::name).reduce((a,b)->a+","+b).orElse(""); }

    public void updateProfile(String name, String phone, String addressLine, String addressComplement,
                              String neighborhood, String city, String state, String postalCode) {
        this.name = name;
        this.phone = phone;
        this.addressLine = addressLine;
        this.addressComplement = addressComplement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

    public void updateAddress(String addressLine, String addressComplement, String neighborhood,
                              String city, String state, String postalCode) {
        this.addressLine = addressLine;
        this.addressComplement = addressComplement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }
}
