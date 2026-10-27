package br.com.fooddelivery.payments;

import br.com.fooddelivery.orders.Order;
import br.com.fooddelivery.reservations.Reservation;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @Table(name="payments", uniqueConstraints={@UniqueConstraint(name="uk_payment_order", columnNames="order_id"),@UniqueConstraint(name="uk_payment_reservation",columnNames="reservation_id")})
public class Payment {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @OneToOne(fetch=FetchType.LAZY) @JoinColumn(name="order_id") @JsonIgnore private Order order;
    @OneToOne(fetch=FetchType.LAZY) @JoinColumn(name="reservation_id") @JsonIgnore private Reservation reservation;
    @Column(nullable=false) private long amountCents;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private PaymentStatus status=PaymentStatus.PENDING;
    @Column(name="provider_reference",length=120) private String providerReference;
    @Column(name="provider_preference_id",length=120) private String providerPreferenceId;
    @Column(name="checkout_url",length=1000) private String checkoutUrl;
    @Column(name="refund_idempotency_key") private UUID refundIdempotencyKey;
    @Column(name="refund_provider_reference",length=120) private String refundProviderReference;
    @Column(name="refund_attempts",nullable=false) private int refundAttempts;
    @Column(name="last_refund_attempt_at") private Instant lastRefundAttemptAt;
    @Column(name="refund_last_error",length=300) private String refundLastError;
    @Version private long version;
    protected Payment() {}
    public Payment(Order order){this.order=order;this.amountCents=order.getTotalCents();}
    public Payment(Reservation reservation){this.reservation=reservation;this.amountCents=reservation.getAmountCents();}
    public UUID getId(){return id;} public long getAmountCents(){return amountCents;} public PaymentStatus getStatus(){return status;} public Order getOrder(){return order;} public Reservation getReservation(){return reservation;}
    public String getProviderPreferenceId(){return providerPreferenceId;} public String getCheckoutUrl(){return checkoutUrl;}
    public String getProviderReference(){return providerReference;} public String getRefundProviderReference(){return refundProviderReference;}
    public int getRefundAttempts(){return refundAttempts;} public Instant getLastRefundAttemptAt(){return lastRefundAttemptAt;} public String getRefundLastError(){return refundLastError;}
    public void checkoutCreated(String preferenceId,String url){if(status!=PaymentStatus.PENDING&&status!=PaymentStatus.FAILED)throw new IllegalStateException("Este pagamento não pode abrir um novo checkout.");status=PaymentStatus.PENDING;providerPreferenceId=preferenceId;checkoutUrl=url;}
    public void confirm(String reference){if(status==PaymentStatus.PAID||status==PaymentStatus.REFUND_PENDING||status==PaymentStatus.REFUNDED||status==PaymentStatus.REFUND_REVIEW)return;status=PaymentStatus.PAID;providerReference=reference;}
    public void fail(String reference){if(status==PaymentStatus.PAID||status==PaymentStatus.REFUND_PENDING||status==PaymentStatus.REFUNDED||status==PaymentStatus.REFUND_REVIEW)return;status=PaymentStatus.FAILED;providerReference=reference;}
    public void cancelPendingCheckout(){if(status==PaymentStatus.PENDING)status=PaymentStatus.FAILED;else if(status!=PaymentStatus.FAILED)throw new IllegalStateException("O pagamento não pode ser cancelado neste estado.");}
    public void requestFullRefund(){
        if(status==PaymentStatus.REFUND_PENDING||status==PaymentStatus.REFUNDED)return;
        if(status!=PaymentStatus.PAID)throw new IllegalStateException("Somente pagamentos aprovados podem ser reembolsados.");
        status=PaymentStatus.REFUND_PENDING;refundIdempotencyKey=UUID.randomUUID();refundAttempts=0;refundLastError=null;refundProviderReference=null;
    }
    public UUID getRefundIdempotencyKey(){return refundIdempotencyKey;}
    public boolean beginRefundAttempt(){
        if(status!=PaymentStatus.REFUND_PENDING||refundProviderReference!=null)return false;
        refundAttempts++;lastRefundAttemptAt=Instant.now();return true;
    }
    public void refundAccepted(String providerRefundId){refundProviderReference=providerRefundId;refundLastError=null;}
    public void refundSucceeded(String providerRefundId){status=PaymentStatus.REFUNDED;refundProviderReference=providerRefundId;refundLastError=null;}
    public void refundNeedsReview(String safeReason){status=PaymentStatus.REFUND_REVIEW;refundLastError=safeReason;}
    public void refundAttemptFailed(String safeReason){refundLastError=safeReason;}
}
