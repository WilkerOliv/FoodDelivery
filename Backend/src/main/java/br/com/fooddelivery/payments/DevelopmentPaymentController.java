package br.com.fooddelivery.payments;

import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/dev/payments") @Profile("dev-h2")
public class DevelopmentPaymentController {
    private final PaymentService service; private final PaymentRepository payments;
    public DevelopmentPaymentController(PaymentService service,PaymentRepository payments){this.service=service;this.payments=payments;}
    @GetMapping("/{id}") @PreAuthorize("hasRole('CUSTOMER')") public Payment get(@PathVariable UUID id,Authentication auth){Payment p=payments.findById(id).orElseThrow();if(!p.getOrder().getCustomer().getId().toString().equals(((Jwt)auth.getPrincipal()).getSubject()))throw new org.springframework.security.access.AccessDeniedException("Pagamento de outra conta.");return p;}
    @PostMapping("/{id}/approve") @PreAuthorize("hasRole('CUSTOMER')") public Payment approve(@PathVariable UUID id,Authentication auth){Payment p=payments.findById(id).orElseThrow();if(!p.getOrder().getCustomer().getId().toString().equals(((Jwt)auth.getPrincipal()).getSubject()))throw new org.springframework.security.access.AccessDeniedException("Pagamento de outra conta.");return service.confirmForDevelopment(id);}
}
