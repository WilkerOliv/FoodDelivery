package br.com.fooddelivery.admin;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminAuditService {
    private final AdminAuditRepository events;
    public AdminAuditService(AdminAuditRepository events) { this.events = events; }

    @Transactional
    public void recordCurrent(String action, String targetType, Object targetId, String summary) {
        var authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof org.springframework.security.oauth2.jwt.Jwt jwt))
            throw new IllegalStateException("Não foi possível identificar o administrador da alteração.");
        record(UUID.fromString(jwt.getSubject()), action, targetType, targetId, summary);
    }

    @Transactional
    public void record(UUID actorId, String action, String targetType, Object targetId, String summary) {
        events.save(new AdminAuditEvent(actorId, action, targetType,
                targetId == null ? null : targetId.toString(), summary));
    }

    @Transactional(readOnly = true)
    public Page<AdminAuditEvent> latest(int page, int size) {
        return events.findAllByOrderByCreatedAtDesc(PageRequest.of(Math.max(0, page), Math.max(1, Math.min(size, 100))));
    }
}
