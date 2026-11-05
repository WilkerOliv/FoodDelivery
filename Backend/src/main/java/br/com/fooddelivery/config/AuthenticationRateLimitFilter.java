package br.com.fooddelivery.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.web.filter.OncePerRequestFilter;

/** Per-process throttling for public authentication endpoints; proxy headers are deliberately not trusted. */
public final class AuthenticationRateLimitFilter extends OncePerRequestFilter {
    private static final int MAX_BUCKETS = 50_000;
    private final Map<String, Window> windows = new LinkedHashMap<>();
    private long requests;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Limit limit = limitFor(request.getMethod(), request.getRequestURI());
        if (limit == null) {
            chain.doFilter(request, response);
            return;
        }

        String remoteAddress = request.getRemoteAddr();
        long retryAfter = acquire(remoteAddress + ':' + limit.key(), limit.maxAttempts(), limit.windowMillis(), System.currentTimeMillis());
        if (retryAfter > 0) {
            response.setStatus(429);
            response.setHeader("Retry-After", Long.toString(retryAfter));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\":\"Muitas tentativas. Aguarde antes de tentar novamente.\"}");
            return;
        }
        chain.doFilter(request, response);
    }

    private synchronized long acquire(String key, int maxAttempts, long windowMillis, long now) {
        requests++;
        if (requests % 256 == 0 || windows.size() >= MAX_BUCKETS) evictExpired(now);
        Window current = windows.get(key);
        if (current == null || now - current.startedAt >= windowMillis) {
            if (current == null && windows.size() >= MAX_BUCKETS) windows.remove(windows.keySet().iterator().next());
            windows.put(key, new Window(now, 1));
            return 0;
        }
        if (current.count >= maxAttempts) return Math.max(1, (current.startedAt + windowMillis - now + 999) / 1000);
        current.count++;
        return 0;
    }

    private void evictExpired(long now) {
        Iterator<Window> iterator = windows.values().iterator();
        while (iterator.hasNext()) if (now - iterator.next().startedAt >= 60 * 60 * 1000L) iterator.remove();
    }

    private static Limit limitFor(String method, String path) {
        if (!"POST".equalsIgnoreCase(method)) return null;
        if(path.equals("/api/v1/account/whatsapp-link")||path.startsWith("/api/v1/admin/mfa")||path.startsWith("/api/v1/courier/payout")||path.startsWith("/api/v1/admin/courier-finance"))return new Limit("finance-reauth",20,15*60*1000L);
        if ("/api/v1/auth/login".equals(path)) return new Limit("login", 10, 15 * 60 * 1000L);
        if ("/api/v1/auth/password-recovery".equals(path)
                || "/api/v1/auth/password-recovery/reset".equals(path)) return new Limit("recovery", 5, 60 * 60 * 1000L);
        if ("/api/v1/auth/register".equals(path)
                || "/api/v1/auth/operator-applications".equals(path)) return new Limit("registration", 10, 60 * 60 * 1000L);
        return null;
    }

    private record Limit(String key, int maxAttempts, long windowMillis) {}
    private static final class Window {
        private final long startedAt;
        private int count;
        private Window(long startedAt, int count) { this.startedAt = startedAt; this.count = count; }
    }
}
