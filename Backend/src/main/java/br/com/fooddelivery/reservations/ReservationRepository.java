package br.com.fooddelivery.reservations;
import java.time.Instant;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ReservationRepository extends JpaRepository<Reservation,UUID>{List<Reservation> findByCustomerIdOrderByStartsAtDesc(UUID id);List<Reservation> findByStoreOwnerIdOrderByStartsAt(UUID id);boolean existsByStoreIdAndStartsAt(UUID store,Instant startsAt);List<Reservation> findByStatusAndCreatedAtBefore(ReservationStatus status,Instant before);
@org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
@Query("select r from Reservation r where r.store.id=:storeId and r.status in :statuses and r.endsAt > :now order by r.id")
List<Reservation> lockFutureForHours(@Param("storeId") UUID storeId,@Param("statuses") Collection<ReservationStatus> statuses,@Param("now") Instant now);
@Query("select r.store.category, count(r) from Reservation r where r.status = br.com.fooddelivery.reservations.ReservationStatus.COMPLETED and r.createdAt >= :since and r.store.active = true and r.store.approved = true and (:city is null or lower(r.store.city) = lower(:city)) and (:state is null or lower(r.store.state) = lower(:state)) group by r.store.category")
List<Object[]> completedByCategorySince(@Param("since") Instant since, @Param("city") String city, @Param("state") String state);
}
