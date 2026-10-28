package br.com.fooddelivery.support;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, UUID> {
    List<SupportTicket> findByAccountIdOrderByUpdatedAtDesc(UUID accountId);
    List<SupportTicket> findAllByOrderByUpdatedAtDesc();
}
