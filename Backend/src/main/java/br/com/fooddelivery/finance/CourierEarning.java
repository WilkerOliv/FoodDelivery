package br.com.fooddelivery.finance;
import jakarta.persistence.*;
import java.util.UUID;
import java.time.Instant;
@Entity @Table(name="courier_earnings")
public class CourierEarning {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(name="delivery_id",nullable=false,unique=true) private UUID deliveryId;
 @Column(name="courier_id",nullable=false) private UUID courierId;
 @Column(name="amount_cents",nullable=false) private long amountCents;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt=Instant.now();
 protected CourierEarning(){}
 public CourierEarning(UUID deliveryId,UUID courierId,long amount){if(amount<1)throw new IllegalArgumentException("Crédito precisa ser positivo.");this.deliveryId=deliveryId;this.courierId=courierId;this.amountCents=amount;}
 public UUID getId(){return id;} public UUID getDeliveryId(){return deliveryId;} public long getAmountCents(){return amountCents;} public Instant getCreatedAt(){return createdAt;}
}
