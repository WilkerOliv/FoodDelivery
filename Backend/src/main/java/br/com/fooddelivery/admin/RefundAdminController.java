package br.com.fooddelivery.admin;

import br.com.fooddelivery.orders.Order;
import br.com.fooddelivery.payments.*;
import br.com.fooddelivery.reservations.Reservation;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/v1/admin/refunds")
@PreAuthorize("hasRole('ADMIN')")
public class RefundAdminController {
    private final PaymentRepository payments;

    public RefundAdminController(PaymentRepository payments) {
        this.payments = payments;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<RefundCase> list() {
        return payments.findByStatusInOrderByLastRefundAttemptAtAsc(List.of(PaymentStatus.REFUND_PENDING, PaymentStatus.REFUND_REVIEW))
                .stream().map(RefundAdminController::view).toList();
    }

    private static RefundCase view(Payment payment) {
        Order order = payment.getOrder();
        Reservation reservation = payment.getReservation();
        var store = order != null ? order.getStore() : reservation.getStore();
        return new RefundCase(payment.getId(), payment.getProviderReference(), store.getName(),
                order != null ? order.getId() : reservation.getId(), order != null ? "ORDER" : "RESERVATION",
                payment.getAmountCents(), payment.getStatus(), payment.getRefundAttempts(),
                payment.getLastRefundAttemptAt(), payment.getRefundLastError());
    }

    public record RefundCase(UUID paymentId, String providerPaymentId, String storeName, UUID operationId,
            String operationType, long amountCents, PaymentStatus status, int attempts,
            Instant lastAttemptAt, String attentionMessage) {}
}
