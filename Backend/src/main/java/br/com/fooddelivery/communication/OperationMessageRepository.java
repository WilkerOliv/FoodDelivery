package br.com.fooddelivery.communication;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationMessageRepository extends JpaRepository<OperationMessage, UUID> {
    List<OperationMessage> findByOrder_IdOrderByCreatedAtDesc(UUID id, Pageable pageable);
    List<OperationMessage> findByReservation_IdOrderByCreatedAtDesc(UUID id, Pageable pageable);
    List<OperationMessage> findByDeliveryTask_IdOrderByCreatedAtDesc(UUID id, Pageable pageable);
}
