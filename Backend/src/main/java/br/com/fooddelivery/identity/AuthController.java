package br.com.fooddelivery.identity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final PasswordRecoveryService recovery;

    public AuthController(AuthService authService, PasswordEncoder passwordEncoder, PasswordRecoveryService recovery) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.recovery = recovery;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(@Valid @RequestBody RegisterRequest request) {
        Account account = authService.register(request.name(), request.email(), request.password(), request.cpf(), passwordEncoder);
        return Map.of("id", account.getId(), "name", account.getName(), "email", account.getEmail(), "role", account.getRole());
    }

    @PostMapping("/operator-applications")
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.GONE)
    public Map<String, Object> applyForOperator(@Valid @RequestBody OperatorApplicationRequest request) {
        throw new IllegalArgumentException("Entre na sua conta existente e solicite o perfil profissional em Minha conta > Acessos. Não crie uma nova conta para outro perfil.");
    }

    @PostMapping("/login")
    public Map<String, Object> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request.email(), request.password());
    }

    @PostMapping("/password-recovery")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, String> requestPasswordRecovery(@Valid @RequestBody RecoveryRequest request) {
        recovery.requestPasswordReset(request.email(), request.channel());
        return Map.of("message", "Se os dados forem elegíveis, enviaremos instruções pelo canal escolhido.");
    }

    @PostMapping("/password-recovery/reset")
    public Map<String, String> resetPassword(@Valid @RequestBody RecoveryResetRequest request) {
        if (!recovery.resetPassword(request.email(), request.channel(), request.code(), request.newPassword()))
            throw new IllegalArgumentException("Código inválido ou expirado. Solicite outro código e tente novamente.");
        return Map.of("message", "Senha redefinida. Entre com sua nova senha.");
    }

    public record RegisterRequest(@NotBlank @Size(max = 120) String name,
                                  @NotBlank @Email @Size(max = 254) String email,
                                  @NotBlank @Size(min = 12, max = 72) String password,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") String cpf) {}
    public record OperatorApplicationRequest(@NotBlank @Size(max = 120) String name,
                                  @NotBlank @Email @Size(max = 254) String email,
                                  @NotBlank @Size(min = 12, max = 72) String password,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") String cpf,
                                  @jakarta.validation.constraints.NotNull br.com.fooddelivery.identity.AccountRole role) {}
    public record LoginRequest(@NotBlank @Email String email, @NotBlank String password) {}
    public record RecoveryRequest(@NotBlank @Email @Size(max=254) String email,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="EMAIL|WHATSAPP") String channel) {}
    public record RecoveryResetRequest(@NotBlank @Email @Size(max=254) String email,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="EMAIL|WHATSAPP") String channel,
                                  @NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d{6}") String code,
                                  @NotBlank @Size(min=12,max=72) String newPassword) {}
}
