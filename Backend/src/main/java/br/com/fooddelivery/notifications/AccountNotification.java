package br.com.fooddelivery.notifications;

import br.com.fooddelivery.identity.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "account_notifications", indexes =
        @Index(name = "idx_notifications_account_created", columnList = "account_id,created_at"))
public class AccountNotification {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(nullable = false, length = 120)
    private String title;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(nullable = false, length = 16)
    private String kind;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "read_at")
    private Instant readAt;

    protected AccountNotification() {}

    public AccountNotification(Account account, String title, String message, String kind) {
        this.account = account;
        this.title = title;
        this.message = message;
        this.kind = kind;
    }

    public UUID getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getKind() { return kind; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getReadAt() { return readAt; }
    public void markRead() { if (readAt == null) readAt = Instant.now(); }
}
