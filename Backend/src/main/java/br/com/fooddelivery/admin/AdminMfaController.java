package br.com.fooddelivery.admin;
import br.com.fooddelivery.identity.AdminMfaService;
import java.util.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@RestController @RequestMapping("/api/v1/admin/mfa") @PreAuthorize("hasRole('ADMIN')")
public class AdminMfaController {
 private final AdminMfaService service;public AdminMfaController(AdminMfaService service){this.service=service;}
 private UUID id(Authentication a){return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());}
 @GetMapping public Object status(Authentication a){return service.status(id(a));}
 @PostMapping("/begin") public Object begin(Authentication a,@Valid @RequestBody Request r){return service.begin(id(a),r.password());}
 @PostMapping("/enable") public Object enable(Authentication a,@Valid @RequestBody Request r){var codes=service.enable(id(a),r.password(),r.code());if(codes==null)throw new ResponseStatusException(HttpStatus.CONFLICT,"Código inválido, expirado ou já utilizado.");return Map.of("recoveryCodes",codes,"message","Guarde os códigos em local seguro. Entre novamente.");}
 @PostMapping("/disable") public Object disable(Authentication a,@Valid @RequestBody Request r){if(!service.disable(id(a),r.password(),r.code()))throw new ResponseStatusException(HttpStatus.CONFLICT,"Código inválido, expirado ou já utilizado.");return Map.of("message","Segundo fator desativado. Entre novamente.");}
 public record Request(@NotBlank @Size(max=72)String password,@Size(max=32)String code){}
}
