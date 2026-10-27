package br.com.fooddelivery.payments;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface PaymentRepository extends JpaRepository<Payment,UUID>{
 Optional<Payment> findByOrderId(UUID id);
 Optional<Payment> findByReservationId(UUID id);
 Optional<Payment> findByProviderReference(String reference);
 @Query("select p from Payment p left join fetch p.order left join fetch p.order.store left join fetch p.reservation left join fetch p.reservation.store where p.id=:id")
 Optional<Payment> findWithOperationById(@Param("id") UUID id);
 List<Payment> findByStatusInOrderByLastRefundAttemptAtAsc(Collection<PaymentStatus> statuses);
 List<Payment> findByStatusOrderByLastRefundAttemptAtAsc(PaymentStatus status);
 List<Payment> findByStatusAndRefundProviderReferenceIsNullOrderByLastRefundAttemptAtAsc(PaymentStatus status);
}
