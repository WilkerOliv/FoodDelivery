package br.com.fooddelivery.admin;
import br.com.fooddelivery.payments.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.data.domain.PageRequest;
@RestController @RequestMapping("/api/v1/admin/payment-reconciliations") @PreAuthorize("hasRole('ADMIN')")
public class ReconciliationController {
 private final PaymentReconciliationService service;private final PaymentReconciliationRepository reports;
 public ReconciliationController(PaymentReconciliationService service,PaymentReconciliationRepository reports){this.service=service;this.reports=reports;}
 @GetMapping public Object list(@RequestParam(defaultValue="0")int page){return reports.findAllByOrderByCreatedAtDesc(PageRequest.of(Math.max(0,page),30));}
 @PostMapping("/{paymentId}") public Object reconcile(@PathVariable UUID paymentId,@Valid @RequestBody Request request,Authentication auth){return service.reconcile(paymentId,UUID.fromString(((Jwt)auth.getPrincipal()).getSubject()),request.providerPaymentId());}
 public record Request(@Pattern(regexp="[0-9]{1,30}") String providerPaymentId){}
}
