package br.com.fooddelivery.identity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/account") public class ProfileController {
 private final AccountRepository accounts;
 private final org.springframework.security.crypto.password.PasswordEncoder encoder;
 private final AuthService authService;
 private final PasswordRecoveryService recovery;
 public ProfileController(AccountRepository accounts,org.springframework.security.crypto.password.PasswordEncoder encoder,AuthService authService,PasswordRecoveryService recovery){this.accounts=accounts;this.encoder=encoder;this.authService=authService;this.recovery=recovery;}
 @GetMapping("/me") public Account me(Authentication auth){return accounts.findById(subject(auth)).orElseThrow();}
 @PutMapping("/profile") public Account update(@Valid @RequestBody ProfileUpdate p,Authentication auth){Account a=accounts.findById(subject(auth)).orElseThrow();String requestedCpf=clean(p.cpf());if(requestedCpf!=null){String normalized=requestedCpf.replaceAll("\\D","");if(a.getCpf()==null&&accounts.existsByCpf(normalized))throw new IllegalArgumentException("Este CPF já está vinculado a outra conta.");a.setCpfOnce(normalized);}a.updateProfile(p.name().trim(),p.phone().trim(),clean(p.addressLine()),clean(p.addressComplement()),clean(p.neighborhood()),clean(p.city()),clean(p.state()),clean(p.postalCode()));return accounts.save(a);}
 @PostMapping("/operator-applications") @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.GONE) public Account applyForOperator(@Valid @RequestBody OperatorApplication p,Authentication auth){throw new IllegalArgumentException("Use as solicitações de perfil em Minha conta > Acessos.");}
 @PostMapping("/password") public java.util.Map<String,Object> changePassword(@Valid @RequestBody PasswordChange p,Authentication auth){Account a=accounts.findById(subject(auth)).orElseThrow();if(!encoder.matches(p.currentPassword(),a.getPasswordHash()))throw new IllegalArgumentException("A senha atual não confere.");if(encoder.matches(p.newPassword(),a.getPasswordHash()))throw new IllegalArgumentException("Escolha uma senha diferente da atual.");a.changePassword(encoder.encode(p.newPassword()));accounts.save(a);return authService.issueToken(a);}
 @PostMapping("/phone-verification") @org.springframework.web.bind.annotation.ResponseStatus(org.springframework.http.HttpStatus.ACCEPTED) public java.util.Map<String,String> requestPhoneVerification(Authentication auth){Account a=accounts.findById(subject(auth)).orElseThrow();recovery.requestPhoneVerification(a);return java.util.Map.of("message","Se houver um telefone válido, enviaremos um código pelo WhatsApp.");}
 @PostMapping("/phone-verification/confirm") public java.util.Map<String,String> confirmPhone(@Valid @RequestBody PhoneVerification p,Authentication auth){Account a=accounts.findById(subject(auth)).orElseThrow();if(!recovery.verifyPhone(a,p.code()))throw new IllegalArgumentException("Código inválido ou expirado.");accounts.save(a);return java.util.Map.of("message","Telefone confirmado.");}
 private String clean(String value){return value==null||value.isBlank()?null:value.trim();}
 private UUID subject(Authentication a){return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());}
 public record ProfileUpdate(@NotBlank @Size(max=120) String name,
   @NotBlank @Size(max=30) String phone,
   @Size(max=250) String addressLine,
   @Size(max=120) String addressComplement,
   @Size(max=120) String neighborhood,
   @Size(max=100) String city,
   @Size(max=2) String state,
   @Size(max=9) String postalCode,
   @Size(max=14) @jakarta.validation.constraints.Pattern(regexp="^$|\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}") String cpf){}
 public record PasswordChange(@NotBlank String currentPassword,@NotBlank @Size(min=12,max=72) String newPassword){}
 public record PhoneVerification(@NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d{6}") String code){}
 public record OperatorApplication(@jakarta.validation.constraints.NotNull AccountRole role){public OperatorApplication{if(role!=AccountRole.MERCHANT&&role!=AccountRole.COURIER)throw new IllegalArgumentException("Escolha vendedor ou entregador.");}}
}
