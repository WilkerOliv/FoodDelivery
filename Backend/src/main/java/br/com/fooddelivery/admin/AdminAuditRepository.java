package br.com.fooddelivery.admin;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminAuditRepository extends JpaRepository<AdminAuditEvent, UUID> {
    Page<AdminAuditEvent> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
