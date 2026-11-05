package br.com.fooddelivery.finance;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@RestController @RequestMapping("/api/v1")
public class CourierFinanceController {
 private final CourierFinanceService finance;
 public CourierFinanceController(CourierFinanceService finance){this.finance=finance;}
 private UUID actor(Authentication a){return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());}
 @GetMapping("/courier/finance") @PreAuthorize("hasRole('COURIER')") public Object mine(Authentication a,@RequestParam(defaultValue="0") int page){UUID id=actor(a);return Map.of("summary",finance.summary(id),"payouts",finance.mine(id,page),"earnings",finance.earnings(id,page),"policy",finance.policy());}
 @PostMapping("/courier/payout-code") @PreAuthorize("hasRole('COURIER')") public Object code(Authentication a,@Valid @RequestBody Password r){finance.requestCode(actor(a),r.password());return Map.of("message","Código enviado ao e-mail da conta. Válido por dez minutos.");}
 @PostMapping("/courier/payouts") @PreAuthorize("hasRole('COURIER')") public Object request(Authentication a,@Valid @RequestBody Request r){var result=finance.request(actor(a),r.amountCents(),r.requestKey(),r.password(),r.code());if(result==null)throw new ResponseStatusException(HttpStatus.CONFLICT,"Código inválido ou expirado. Confira o e-mail ou solicite outro código.");return result;}
 @PostMapping("/courier/payouts/{id}/cancel") @PreAuthorize("hasRole('COURIER')") public Object cancel(Authentication a,@PathVariable UUID id){return finance.cancel(actor(a),id);}
 @GetMapping("/admin/courier-finance") @PreAuthorize("hasRole('ADMIN')") public Object admin(@RequestParam(defaultValue="0") int page){return Map.of("policy",finance.policy(),"payouts",finance.all(page));}
 @PostMapping("/admin/courier-finance/policy") @PreAuthorize("hasRole('ADMIN')") public Object policy(Authentication a,@Valid @RequestBody Policy r){return finance.configure(actor(a),r.amountCents(),r.password());}
 @PostMapping("/admin/courier-finance/payouts/{id}/decision") @PreAuthorize("hasRole('ADMIN')") public Object decision(Authentication a,@PathVariable UUID id,@Valid @RequestBody Decision r){return finance.decide(actor(a),id,r.approved(),r.note(),r.password());}
 @PostMapping("/admin/courier-finance/payouts/{id}/paid") @PreAuthorize("hasRole('ADMIN')") public Object paid(Authentication a,@PathVariable UUID id,@Valid @RequestBody Paid r){return finance.paid(actor(a),id,r.externalReference(),r.password());}
 public record Password(@NotBlank @Size(max=72) String password){}
 public record Request(@Min(1) @Max(100000000) long amountCents,@NotNull UUID requestKey,@NotBlank @Size(max=72) String password,@Pattern(regexp="\\d{6}") @NotBlank String code){}
 public record Policy(@Min(1) @Max(100000) long amountCents,@NotBlank @Size(max=72) String password){}
 public record Decision(boolean approved,@Size(max=300) String note,@NotBlank @Size(max=72) String password){}
 public record Paid(@NotBlank @Size(min=6,max=120) String externalReference,@NotBlank @Size(max=72) String password){}
}
