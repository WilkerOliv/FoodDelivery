package br.com.fooddelivery.identity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "recovery_codes")
public class RecoveryCode {
    @Id private UUID id = UUID.randomUUID();
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @Column(nullable = false, length = 24) private String purpose;
    @Column(nullable = false, length = 16) private String channel;
    @Column(name = "code_hash", nullable = false, length = 100) private String codeHash;
    @Column(name = "created_at", nullable = false) private Instant createdAt;
    @Column(name = "expires_at", nullable = false) private Instant expiresAt;
    @Column(name = "next_allowed_at", nullable = false) private Instant nextAllowedAt;
    @Column(nullable = false) private int attempts;
    @Column(name = "consumed_at") private Instant consumedAt;

    protected RecoveryCode() {}
    RecoveryCode(Account account, String purpose, String channel, String codeHash, Instant now) {
        this.account = account; this.purpose = purpose; this.channel = channel; this.codeHash = codeHash;
        this.createdAt = now; this.expiresAt = now.plusSeconds(600); this.nextAllowedAt = now.plusSeconds(60);
    }
    public UUID getId() { return id; }
    public Account getAccount() { return account; }
    public String getPurpose() { return purpose; }
    public String getChannel() { return channel; }
    public String getCodeHash() { return codeHash; }
    public Instant getExpiresAt() { return expiresAt; }
    public Instant getNextAllowedAt() { return nextAllowedAt; }
    public Instant getConsumedAt() { return consumedAt; }
    public int getAttempts() { return attempts; }
    public boolean isReadyAt(Instant now) { return consumedAt == null && expiresAt.isAfter(now) && attempts < 5; }
    public void recordFailedAttempt() { attempts++; if (attempts >= 5) consumedAt = Instant.now(); }
    public void consume(Instant now) { consumedAt = now; }
}
