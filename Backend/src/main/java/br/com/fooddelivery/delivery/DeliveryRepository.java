package br.com.fooddelivery.delivery;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
public interface DeliveryRepository extends JpaRepository<DeliveryTask,UUID>{
 List<DeliveryTask> findByStatusOrderByCreatedAt(DeliveryStatus status);
 List<DeliveryTask> findByCourierIdOrderByCreatedAtDesc(UUID id);
 Optional<DeliveryTask> findByOrderId(UUID id);
 @Modifying @Transactional @Query("update DeliveryTask d set d.courier=:courier, d.status=br.com.fooddelivery.delivery.DeliveryStatus.ASSIGNED, d.version=d.version+1 where d.id=:id and d.courier is null and d.status=br.com.fooddelivery.delivery.DeliveryStatus.OFFERED")
 int claim(@Param("id") UUID id,@Param("courier") br.com.fooddelivery.identity.Account courier);
}
