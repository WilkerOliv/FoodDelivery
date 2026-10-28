package br.com.fooddelivery.support;

import br.com.fooddelivery.identity.Account;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "support_ticket_messages")
public class SupportTicketMessage {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "ticket_id", nullable = false) private SupportTicket ticket;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "sender_id", nullable = false) private Account sender;
    @Column(nullable = false, length = 2000) private String body;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();
    protected SupportTicketMessage() {}
    public SupportTicketMessage(SupportTicket ticket, Account sender, String body){this.ticket=ticket;this.sender=sender;this.body=body;}
    public UUID getId(){return id;} public SupportTicket getTicket(){return ticket;} public Account getSender(){return sender;}
    public String getBody(){return body;} public Instant getCreatedAt(){return createdAt;}
}
