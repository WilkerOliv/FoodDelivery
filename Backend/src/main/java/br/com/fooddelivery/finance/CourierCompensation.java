package br.com.fooddelivery.finance;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="courier_compensation")
public class CourierCompensation {
 @Id private Integer id=1;
 @Column(nullable=false) private boolean configured;
 @Column(name="amount_cents",nullable=false) private long amountCents;
 @Column(name="updated_at",nullable=false) private Instant updatedAt;
 public boolean isConfigured(){return configured;} public long getAmountCents(){return amountCents;} public Instant getUpdatedAt(){return updatedAt;}
 public void configure(long amount){if(amount<1||amount>100_000)throw new IllegalArgumentException("Defina uma remuneração entre R$ 0,01 e R$ 1.000,00 por entrega.");configured=true;amountCents=amount;updatedAt=Instant.now();}
}
