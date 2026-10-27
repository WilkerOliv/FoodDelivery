package br.com.fooddelivery.reservations;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSlotRepository extends JpaRepository<ReservationSlot,UUID>{
    boolean existsByStoreIdAndStartsAt(UUID storeId,java.time.Instant startsAt);
    void deleteByReservationId(UUID reservationId);
}
