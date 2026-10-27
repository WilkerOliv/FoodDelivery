package br.com.fooddelivery.reservations;

import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import br.com.fooddelivery.payments.PaymentRepository;
import br.com.fooddelivery.payments.PaymentService;

@RestController @RequestMapping("/api/v1/dev/reservations") @Profile("dev-h2")
public class DevelopmentReservationPaymentController {
 private final ReservationRepository reservations;
 private final PaymentRepository payments; private final PaymentService paymentService;
 public DevelopmentReservationPaymentController(ReservationRepository reservations,PaymentRepository payments,PaymentService paymentService){this.reservations=reservations;this.payments=payments;this.paymentService=paymentService;}
 @PostMapping("/{id}/approve-payment") @PreAuthorize("hasRole('CUSTOMER')")
 public Object approve(@PathVariable UUID id,Authentication auth){var r=reservations.findById(id).orElseThrow();if(!r.getCustomer().getId().toString().equals(((Jwt)auth.getPrincipal()).getSubject()))throw new org.springframework.security.access.AccessDeniedException("Reserva de outra conta.");var payment=payments.findByReservationId(id).orElseThrow(()->new IllegalStateException("Pagamento da reserva não encontrado."));return paymentService.confirmForDevelopment(payment.getId());}
}
