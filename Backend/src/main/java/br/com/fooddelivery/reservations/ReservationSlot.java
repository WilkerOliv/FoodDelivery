package br.com.fooddelivery.reservations;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="reservation_slots",uniqueConstraints=@UniqueConstraint(name="uk_reservation_slot_active",columnNames={"store_id","starts_at"}))
public class ReservationSlot {
    @Id private UUID id;
    @Column(name="store_id",nullable=false) private UUID storeId;
    @Column(name="reservation_id",nullable=false,unique=true) private UUID reservationId;
    @Column(name="starts_at",nullable=false) private Instant startsAt;
    protected ReservationSlot(){}
    public ReservationSlot(UUID storeId,UUID reservationId,Instant startsAt){this.id=reservationId;this.storeId=storeId;this.reservationId=reservationId;this.startsAt=startsAt;}
}
