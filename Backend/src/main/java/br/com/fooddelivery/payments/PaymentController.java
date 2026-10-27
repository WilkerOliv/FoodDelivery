package br.com.fooddelivery.payments;

import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@PreAuthorize("hasRole('CUSTOMER')")
public class PaymentController {
    private final PaymentRepository payments;
    private final MercadoPagoCheckoutService checkout;
    public PaymentController(PaymentRepository payments,MercadoPagoCheckoutService checkout){this.payments=payments;this.checkout=checkout;}

    @PostMapping("/{id}/checkout")
    public MercadoPagoCheckoutService.CheckoutView createCheckout(@PathVariable UUID id,Authentication auth){
        return checkout.create(id,subject(auth));
    }

    @GetMapping("/{id}")
    @org.springframework.transaction.annotation.Transactional(readOnly=true)
    public PaymentView get(@PathVariable UUID id,Authentication auth){
        Payment payment=payments.findById(id).orElseThrow();
        UUID customerId=payment.getOrder()!=null?payment.getOrder().getCustomer().getId():payment.getReservation().getCustomer().getId();
        if(!customerId.equals(subject(auth)))throw new org.springframework.security.access.AccessDeniedException("Este pagamento pertence a outra conta.");
        return new PaymentView(payment.getId(),payment.getAmountCents(),payment.getStatus(),payment.getOrder()==null?null:payment.getOrder().getId(),payment.getReservation()==null?null:payment.getReservation().getId());
    }

    @PostMapping("/{id}/sync")
    public PaymentView sync(@PathVariable UUID id,@RequestBody SyncRequest request,Authentication auth){return checkout.syncFromProvider(id,subject(auth),request.providerPaymentId());}

    private UUID subject(Authentication auth){return UUID.fromString(((Jwt)auth.getPrincipal()).getSubject());}
    public record PaymentView(UUID id,long amountCents,PaymentStatus status,UUID orderId,UUID reservationId){}
    public record SyncRequest(String providerPaymentId){}
}
