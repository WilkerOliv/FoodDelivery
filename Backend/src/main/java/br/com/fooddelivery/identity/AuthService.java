package br.com.fooddelivery.identity;

import java.time.Instant;
import java.util.Map;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final AccountRepository accounts;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public AuthService(AccountRepository accounts, AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.accounts = accounts;
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    @Transactional
    public Account register(String name, String email, String password, String cpf, org.springframework.security.crypto.password.PasswordEncoder encoder) {
        return createAccount(name, email, password, cpf, encoder);
    }

    private Account createAccount(String name, String email, String password, String cpf,
                                  org.springframework.security.crypto.password.PasswordEncoder encoder) {
        String normalizedEmail = email.trim().toLowerCase(java.util.Locale.ROOT);
        if (accounts.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new IllegalArgumentException("Já existe uma conta com este e-mail.");
        }
        String normalizedCpf = cpf == null ? null : cpf.replaceAll("\\D", "");
        if (!Cpf.isValid(normalizedCpf)) throw new IllegalArgumentException("Informe um CPF válido.");
        if (accounts.existsByCpf(normalizedCpf)) throw new IllegalArgumentException("Este CPF já está vinculado a uma conta. Entre na conta existente para solicitar outro perfil.");
        Account account = new Account(normalizedEmail, name.trim(), encoder.encode(password));
        account.setCpfOnce(normalizedCpf);
        return accounts.save(account);
    }

    public Map<String, Object> login(String email, String password) {
        String normalizedEmail = email.trim().toLowerCase(java.util.Locale.ROOT);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(normalizedEmail, password));
        Account account = accounts.findByEmailIgnoreCase(normalizedEmail).orElseThrow();
        return issueToken(account);
    }

    public Map<String, Object> issueToken(Account account) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("food-delivery-api")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600))
                .subject(account.getId().toString())
                .claim("email", account.getEmail())
                .claim("role", account.getRole().name())
                .claim("roles", account.getGrantedRoles().stream().map(Enum::name).toList())
                .claim("password_change_required", account.isPasswordChangeRequired())
                .claim("credential_version", account.getCredentialVersion())
                .build();
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return Map.of("accessToken", jwtEncoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue(),
                "tokenType", "Bearer", "expiresIn", 3600, "account", accountView(account));
    }

    /** Map.of rejeita valores nulos; contas anteriores à V9 (como o administrador inicial) não têm CPF. */
    private static Map<String, Object> accountView(Account account) {
        Map<String, Object> view = new java.util.LinkedHashMap<>();
        view.put("id", account.getId());
        view.put("name", account.getName());
        view.put("email", account.getEmail());
        view.put("role", account.getRole());
        view.put("grantedRoles", account.getGrantedRoles());
        view.put("registeredRoles", account.getRegisteredRoles());
        view.put("cpf", account.getCpf());
        view.put("phoneVerified", account.isPhoneVerified());
        view.put("passwordChangeRequired", account.isPasswordChangeRequired());
        return view;
    }
}
