package br.com.fooddelivery.finance;
import org.springframework.data.jpa.repository.*;
import jakarta.persistence.LockModeType;
import java.util.Optional;
public interface CourierCompensationRepository extends JpaRepository<CourierCompensation,Integer>{
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select c from CourierCompensation c where c.id=1") Optional<CourierCompensation> lockPolicy();
}
