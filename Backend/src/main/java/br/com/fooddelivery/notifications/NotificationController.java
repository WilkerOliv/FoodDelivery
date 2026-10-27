package br.com.fooddelivery.notifications;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account/notifications")
public class NotificationController {
    private final NotificationService service;
    public NotificationController(NotificationService service) { this.service = service; }

    @GetMapping
    public List<AccountNotification> list(Authentication auth) { return service.list(subject(auth)); }

    @PatchMapping("/{id}/read")
    public AccountNotification read(@PathVariable UUID id, Authentication auth) {
        return service.markRead(subject(auth), id);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clear(Authentication auth) { service.clear(subject(auth)); }

    private UUID subject(Authentication auth) { return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject()); }
}
