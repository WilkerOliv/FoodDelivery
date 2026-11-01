package br.com.fooddelivery.reservations;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="reservation_slots",uniqueConstraints=@UniqueConstraint(name="uk_reservation_resource_start",columnNames={"store_id","slot_key","starts_at"}))
public class ReservationSlot {
    public static final UUID STORE_RESOURCE_KEY = UUID.fromString("00000000-0000-0000-0000-000000000000");
    @Id private UUID id;
    @Column(name="store_id",nullable=false) private UUID storeId;
    @Column(name="reservation_id",nullable=false,unique=true) private UUID reservationId;
    @Column(name="professional_id") private UUID professionalId;
    @Column(name="slot_key",nullable=false) private UUID slotKey;
    @Column(name="starts_at",nullable=false) private Instant startsAt;
    @Column(name="ends_at",nullable=false) private Instant endsAt;
    protected ReservationSlot(){}
    public ReservationSlot(UUID storeId,UUID reservationId,Instant startsAt,Instant endsAt,UUID professionalId){this.id=reservationId;this.storeId=storeId;this.reservationId=reservationId;this.startsAt=startsAt;this.endsAt=endsAt;this.professionalId=professionalId;this.slotKey=professionalId==null?STORE_RESOURCE_KEY:professionalId;}
    public UUID getSlotKey(){return slotKey;}
}
