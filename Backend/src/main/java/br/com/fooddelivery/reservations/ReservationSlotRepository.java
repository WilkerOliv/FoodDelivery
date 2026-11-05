package br.com.fooddelivery.reservations;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSlotRepository extends JpaRepository<ReservationSlot,UUID>{
    @org.springframework.data.jpa.repository.Query("select s from ReservationSlot s where s.storeId=:id and s.startsAt < :end and s.endsAt > :start")
    java.util.List<ReservationSlot> findAcrossInterval(@org.springframework.data.repository.query.Param("id") UUID id,@org.springframework.data.repository.query.Param("start")java.time.Instant start,@org.springframework.data.repository.query.Param("end")java.time.Instant end);
    boolean existsByStoreIdAndStartsAt(UUID storeId,java.time.Instant startsAt);
    boolean existsByStoreIdAndProfessionalIdAndStartsAt(UUID storeId,UUID professionalId,java.time.Instant startsAt);
    boolean existsByStoreIdAndProfessionalIdIsNullAndStartsAt(UUID storeId,java.time.Instant startsAt);
    @org.springframework.data.jpa.repository.Query("select s from ReservationSlot s where s.storeId=:storeId and s.slotKey=:slotKey and s.startsAt < :endsAt and s.endsAt > :startsAt")
    java.util.List<ReservationSlot> findOverlapping(@org.springframework.data.repository.query.Param("storeId") UUID storeId,
            @org.springframework.data.repository.query.Param("slotKey") UUID slotKey,
            @org.springframework.data.repository.query.Param("startsAt") java.time.Instant startsAt,
            @org.springframework.data.repository.query.Param("endsAt") java.time.Instant endsAt);
    @org.springframework.data.jpa.repository.Query("select s.slotKey from ReservationSlot s where s.storeId=:storeId and s.startsAt < :endsAt and s.endsAt > :startsAt")
    java.util.List<UUID> occupiedResources(@org.springframework.data.repository.query.Param("storeId") UUID storeId,
            @org.springframework.data.repository.query.Param("startsAt") java.time.Instant startsAt,
            @org.springframework.data.repository.query.Param("endsAt") java.time.Instant endsAt);
    @org.springframework.data.jpa.repository.Query("select s.professionalId from ReservationSlot s where s.storeId=:storeId and s.startsAt=:startsAt and s.professionalId is not null")
    java.util.List<UUID> occupiedProfessionalIds(@org.springframework.data.repository.query.Param("storeId") UUID storeId,@org.springframework.data.repository.query.Param("startsAt") java.time.Instant startsAt);
    void deleteByReservationId(UUID reservationId);
}
