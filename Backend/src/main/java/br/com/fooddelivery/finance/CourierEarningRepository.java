package br.com.fooddelivery.finance;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.*;
import java.util.UUID;
public interface CourierEarningRepository extends JpaRepository<CourierEarning,UUID>{
 boolean existsByDeliveryId(UUID deliveryId);
 @Query("select coalesce(sum(e.amountCents),0) from CourierEarning e where e.courierId=:id") long total(@Param("id") UUID id);
 Page<CourierEarning> findByCourierIdOrderByCreatedAtDesc(UUID id,Pageable page);
}
