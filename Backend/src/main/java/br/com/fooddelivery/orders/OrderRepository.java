package br.com.fooddelivery.orders;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface OrderRepository extends JpaRepository<Order,UUID>{List<Order> findByCustomerIdOrderByCreatedAtDesc(UUID id);List<Order> findByStoreOwnerIdOrderByCreatedAtDesc(UUID id);List<Order> findByStatusAndCreatedAtBefore(OrderStatus status,java.time.Instant before);
@org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
@Query("select o from Order o where o.id=:id")
Optional<Order> findByIdForUpdate(@Param("id") UUID id);
@Query("select o.store.category, count(o) from Order o where o.status = br.com.fooddelivery.orders.OrderStatus.COMPLETED and o.createdAt >= :since and o.store.active = true and o.store.approved = true and (:city is null or lower(o.store.city) = lower(:city)) and (:state is null or lower(o.store.state) = lower(:state)) group by o.store.category")
List<Object[]> completedByCategorySince(@Param("since") java.time.Instant since, @Param("city") String city, @Param("state") String state);
}
