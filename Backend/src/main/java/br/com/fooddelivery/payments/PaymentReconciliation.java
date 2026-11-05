package br.com.fooddelivery.payments;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="payment_reconciliations")
public class PaymentReconciliation {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(name="payment_id",nullable=false) private UUID paymentId;
 @Column(name="actor_id",nullable=false) private UUID actorId;
 @Column(nullable=false,length=24) private String result;
 @Column(name="provider_status",length=40) private String providerStatus;
 @Column(nullable=false,length=300) private String summary;
 @Column(name="created_at",nullable=false) private Instant createdAt=Instant.now();
 protected PaymentReconciliation(){}
 public PaymentReconciliation(UUID payment,UUID actor,String result,String status,String summary){paymentId=payment;actorId=actor;this.result=result;providerStatus=status;this.summary=summary;}
 public UUID getId(){return id;}public UUID getPaymentId(){return paymentId;}public String getResult(){return result;}public String getProviderStatus(){return providerStatus;}public String getSummary(){return summary;}public Instant getCreatedAt(){return createdAt;}
}
