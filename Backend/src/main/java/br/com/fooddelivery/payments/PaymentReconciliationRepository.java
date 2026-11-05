package br.com.fooddelivery.payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import java.util.UUID;
public interface PaymentReconciliationRepository extends JpaRepository<PaymentReconciliation,UUID>{Page<PaymentReconciliation> findAllByOrderByCreatedAtDesc(Pageable page);}
