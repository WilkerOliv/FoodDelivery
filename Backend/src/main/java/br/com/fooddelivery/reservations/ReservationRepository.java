package br.com.fooddelivery.reservations;
import java.time.Instant;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ReservationRepository extends JpaRepository<Reservation,UUID>{List<Reservation> findByCustomerIdOrderByStartsAtDesc(UUID id);List<Reservation> findByStoreOwnerIdOrderByStartsAt(UUID id);boolean existsByStoreIdAndStartsAt(UUID store,Instant startsAt);List<Reservation> findByStatusAndCreatedAtBefore(ReservationStatus status,Instant before);}
