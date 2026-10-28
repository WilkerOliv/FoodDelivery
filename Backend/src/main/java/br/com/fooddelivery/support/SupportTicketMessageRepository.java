package br.com.fooddelivery.support;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTicketMessageRepository extends JpaRepository<SupportTicketMessage, UUID> {
    List<SupportTicketMessage> findByTicketIdOrderByCreatedAtAsc(UUID ticketId);
    SupportTicketMessage findTopByTicketIdOrderByCreatedAtDesc(UUID ticketId);
}
