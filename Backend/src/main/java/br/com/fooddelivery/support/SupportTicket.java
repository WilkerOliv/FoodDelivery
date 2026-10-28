package br.com.fooddelivery.support;

import br.com.fooddelivery.identity.Account;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "support_tickets")
public class SupportTicket {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "account_id", nullable = false) private Account account;
    @Column(nullable = false, length = 140) private String subject;
    @Column(nullable = false, length = 30) private String category;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private SupportTicketStatus status = SupportTicketStatus.OPEN;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();
    @Column(nullable = false) private Instant updatedAt = Instant.now();
    protected SupportTicket() {}
    public SupportTicket(Account account, String subject, String category) { this.account=account; this.subject=subject; this.category=category; }
    public UUID getId(){return id;} public Account getAccount(){return account;} public String getSubject(){return subject;}
    public String getCategory(){return category;} public SupportTicketStatus getStatus(){return status;}
    public Instant getCreatedAt(){return createdAt;} public Instant getUpdatedAt(){return updatedAt;}
    public void setStatus(SupportTicketStatus status){this.status=status; this.updatedAt=Instant.now();}
    public void touch(){this.updatedAt=Instant.now();}
}
