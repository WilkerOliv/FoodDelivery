package br.com.fooddelivery.config;

import java.time.Instant;
import java.util.ArrayDeque;
import java.util.List;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/** Local test doubles; never enabled by a request or client-side flag. */
@Component
public class DemoEnvironment {
    private final boolean enabled;
    private final ArrayDeque<Message> messages = new ArrayDeque<>();
    public DemoEnvironment(Environment environment) {
        enabled = environment.matchesProfiles("homologacao");
        if (enabled && environment.matchesProfiles("prod", "postgres", "dev-h2"))
            throw new IllegalStateException("Homologação não pode ser combinada com outros perfis de banco/produção.");
    }
    public boolean enabled() { return enabled; }
    public synchronized void capture(String channel, String recipient, String code) {
        if (!enabled) throw new IllegalStateException("Simulação desativada.");
        messages.addFirst(new Message(channel, recipient, code, Instant.now()));
        while (messages.size() > 100) messages.removeLast();
    }
    public synchronized List<Message> messages() { return List.copyOf(messages); }
    public record Message(String channel, String recipient, String code, Instant createdAt) {}
}
