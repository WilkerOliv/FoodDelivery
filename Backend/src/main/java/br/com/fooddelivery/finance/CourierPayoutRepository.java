package br.com.fooddelivery.finance;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.*;
import java.util.*;
public interface CourierPayoutRepository extends JpaRepository<CourierPayout,UUID>{
 Optional<CourierPayout> findByCourierIdAndRequestKey(UUID courierId,UUID key);
 @Query("select coalesce(sum(p.amountCents),0) from CourierPayout p where p.courierId=:id and p.status in :statuses") long total(@Param("id") UUID id,@Param("statuses") Collection<CourierPayout.Status> statuses);
 Page<CourierPayout> findByCourierIdOrderByCreatedAtDesc(UUID id,Pageable page);
 Page<CourierPayout> findAllByOrderByCreatedAtDesc(Pageable page);
}
