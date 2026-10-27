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

    public AuthController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(@Valid @RequestBody RegisterRequest request) {
        Account account = authService.register(request.name(), request.email(), request.password(), request.cpf(), passwordEncoder);
        return Map.of("id", account.getId(), "name", account.getName(), "email", account.getEmail(), "role", account.getRole());
    }

    @PostMapping("/operator-applications")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> applyForOperator(@Valid @RequestBody OperatorApplicationRequest request) {
        Account account = authService.applyForOperator(request.name(), request.email(), request.password(), request.cpf(), request.role(), passwordEncoder);
        return Map.of("id", account.getId(), "role", account.getRole(), "requestedRole", account.getRequestedOperatorRole(), "status", account.getOperatorApplicationStatus());
    }

    @PostMapping("/login")
    public Map<String, Object> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request.email(), request.password());
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
}
