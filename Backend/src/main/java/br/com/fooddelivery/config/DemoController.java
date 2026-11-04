package br.com.fooddelivery.config;

import br.com.fooddelivery.payments.*;
import java.util.Map;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Profile("homologacao")
@RequestMapping("/api/v1/demo")
public class DemoController {
    private final DemoEnvironment demo;
    private final PaymentRepository payments;
    private final PaymentService service;
    private final br.com.fooddelivery.identity.AccountRepository accounts;
    public DemoController(DemoEnvironment demo, PaymentRepository payments, PaymentService service, br.com.fooddelivery.identity.AccountRepository accounts) {
        this.demo=demo; this.payments=payments; this.service=service;this.accounts=accounts;
    }
    @GetMapping("/status") public Object status() {return Map.of("mode","homologacao","ready",accounts.count()>0);}
    @GetMapping("/messages") @PreAuthorize("hasRole('ADMIN')")
    public Object messages() { return demo.messages(); }

    @PostMapping("/payments/{id}/{outcome}") @PreAuthorize("hasRole('CUSTOMER')") @Transactional
    public Object payment(@PathVariable UUID id, @PathVariable String outcome, Authentication auth) {
        Payment payment=payments.findById(id).orElseThrow();
        var customer=payment.getOrder()!=null?payment.getOrder().getCustomer():payment.getReservation().getCustomer();
        if(!customer.getId().toString().equals(((Jwt)auth.getPrincipal()).getSubject()))
            throw new org.springframework.security.access.AccessDeniedException("Pagamento de outra conta.");
        if (!outcome.equals("approve") && !outcome.equals("decline")) throw new IllegalArgumentException("Resultado inválido.");
        if(outcome.equals("approve")) service.confirmForDevelopment(id);
        else service.failProvider(id,"local-simulator-declined-"+id);
        return Map.of("simulated",true,"status",payment.getStatus());
    }
}
