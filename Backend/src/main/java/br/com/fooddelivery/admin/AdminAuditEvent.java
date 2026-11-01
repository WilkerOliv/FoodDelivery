package br.com.fooddelivery.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "admin_audit_events")
public class AdminAuditEvent {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "actor_id") private UUID actorId;
    @Column(nullable = false, length = 80) private String action;
    @Column(name = "target_type", nullable = false, length = 40) private String targetType;
    @Column(name = "target_id", length = 80) private String targetId;
    @Column(nullable = false, length = 300) private String summary;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt = Instant.now();

    protected AdminAuditEvent() {}

    public AdminAuditEvent(UUID actorId, String action, String targetType, String targetId, String summary) {
        this.actorId = actorId;
        this.action = required(action, 80);
        this.targetType = required(targetType, 40);
        this.targetId = clean(targetId, 80);
        this.summary = required(summary, 300);
    }

    private static String required(String value, int max) {
        String clean = value == null ? "" : value.trim();
        if (clean.isEmpty() || clean.length() > max) throw new IllegalArgumentException("Campo de auditoria inválido.");
        return clean;
    }
    private static String clean(String value, int max) {
        if (value == null || value.isBlank()) return null;
        String clean = value.trim();
        if (clean.length() > max) throw new IllegalArgumentException("Campo de auditoria inválido.");
        return clean;
    }

    public UUID getId() { return id; }
    public UUID getActorId() { return actorId; }
    public String getAction() { return action; }
    public String getTargetType() { return targetType; }
    public String getTargetId() { return targetId; }
    public String getSummary() { return summary; }
    public Instant getCreatedAt() { return createdAt; }
}
