package br.com.fooddelivery.payments;

import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RefundRetryJob {
    private final PaymentRepository payments;
    private final MercadoPagoRefundService refunds;

    public RefundRetryJob(PaymentRepository payments, MercadoPagoRefundService refunds) {
        this.payments = payments;
        this.refunds = refunds;
    }

    @Scheduled(fixedDelay = 30000)
    public void processPendingRefunds() {
        List<Payment> pending = payments.findByStatusAndRefundProviderReferenceIsNullOrderByLastRefundAttemptAtAsc(PaymentStatus.REFUND_PENDING);
        pending.stream().limit(50).map(Payment::getId).forEach(refunds::attempt);
    }
}
