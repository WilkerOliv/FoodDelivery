package br.com.fooddelivery.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Account-scoped throttling with a coarse peer budget. Forwarding headers are never trusted, with one
 * explicit exception: when a client-address header is configured (the local gateway overwrites it on every
 * request), it is honoured only for connections coming from the loopback interface and only when it holds
 * a literal IP address. Without that configuration the peer is always the TCP remote address.
 */
public final class AuthenticationRateLimitFilter extends OncePerRequestFilter {
    private static final int MAX_BUCKETS = 50_000;
    private static final int MAX_AUTH_BODY_BYTES = 8192;
    private static final ObjectMapper JSON = new ObjectMapper();
    private static final Pattern IPV4 = Pattern.compile("(?:(?:25[0-5]|2[0-4]\\d|1?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|1?\\d?\\d)");
    private static final Pattern IPV6 = Pattern.compile("[0-9a-fA-F:.]{2,45}");
    private final Map<String, Window> windows = new LinkedHashMap<>();
    private final String clientAddressHeader;
    private final PostgresRateWindows sharedWindows;
    private long requests;

    public AuthenticationRateLimitFilter() { this(""); }

    public AuthenticationRateLimitFilter(String clientAddressHeader) {
        this(clientAddressHeader, null);
    }

    AuthenticationRateLimitFilter(String clientAddressHeader, PostgresRateWindows sharedWindows) {
        this.sharedWindows = sharedWindows;
        this.clientAddressHeader = clientAddressHeader == null ? "" : clientAddressHeader.trim();
    }

    private String peerAddress(HttpServletRequest request) {
        String remote = String.valueOf(request.getRemoteAddr());
        if (clientAddressHeader.isEmpty() || !isLoopback(remote)) return remote;
        String candidate = request.getHeader(clientAddressHeader);
        return candidate != null && isLiteralAddress(candidate.trim()) ? candidate.trim().toLowerCase(Locale.ROOT) : remote;
    }

    private static boolean isLoopback(String address) {
        return address.equals("::1") || address.equals("0:0:0:0:0:0:0:1") || address.startsWith("127.");
    }

    /** Syntax-only check: a hostname must never reach a resolver. */
    private static boolean isLiteralAddress(String value) {
        if (IPV4.matcher(value).matches()) return true;
        if (!value.contains(":") || !IPV6.matcher(value).matches()) return false;
        try {
            java.net.InetAddress.getByName(value); // a colon forces literal parsing, never a DNS lookup
            return true;
        } catch (java.net.UnknownHostException invalid) {
            return false;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        Limit limit = limitFor(request.getMethod(), request.getRequestURI());
        if (limit == null) {
            chain.doFilter(request, response);
            return;
        }

        String remoteAddress = peerAddress(request);
        String subject = null;
        HttpServletRequest forwarded = request;
        if (request.getRequestURI().startsWith("/api/v1/auth/")) {
            byte[] body = request.getInputStream().readNBytes(MAX_AUTH_BODY_BYTES + 1);
            if (body.length > MAX_AUTH_BODY_BYTES) {
                response.sendError(413, "Requisição de autenticação muito grande.");
                return;
            }
            forwarded = new ReplayBodyRequest(request, body);
            try {
                var json = JSON.readTree(body);
                var email = json == null ? null : json.get("email");
                if (email != null && email.isTextual()) {
                    String normalized = email.textValue().trim().toLowerCase(Locale.ROOT);
                    if (!normalized.isBlank() && normalized.length() <= 254) subject = "account:" + digest(normalized);
                }
            } catch (IOException invalidJson) {
                // The controller still receives the original bytes and returns the validation error.
            }
        } else {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof JwtAuthenticationToken jwt && jwt.isAuthenticated()) {
                subject = "actor:" + digest(jwt.getToken().getSubject());
            }
        }
        String key = limit.key() + ':' + (subject == null ? "peer:" + remoteAddress : subject);
        Permit primary = acquire(key, limit.maxAttempts(), limit.windowMillis(), System.currentTimeMillis());
        long retryAfter = primary.retryAfter();
        Permit coarse = null;
        if (retryAfter == 0 && subject != null && request.getRequestURI().startsWith("/api/v1/auth/")) {
            String coarseKey = limit.key() + ":coarse-peer:" + remoteAddress;
            coarse = acquire(coarseKey, limit.maxAttempts() * 10, limit.windowMillis(), System.currentTimeMillis());
            retryAfter = coarse.retryAfter();
            if (retryAfter > 0) primary.release().run();
        }
        if (retryAfter > 0) {
            response.setStatus(429);
            response.setHeader("Retry-After", Long.toString(retryAfter));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\":\"Muitas tentativas. Aguarde antes de tentar novamente.\"}");
            return;
        }
        chain.doFilter(forwarded, response);
        // Correct sign-ins must not exhaust a shared gateway/NAT budget.
        if (request.getRequestURI().equals("/api/v1/auth/login") && response.getStatus() >= 200 && response.getStatus() < 300) {
            primary.release().run();
            if (coarse != null) coarse.release().run();
        }
    }

    private static String digest(String value) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 indisponível.", impossible);
        }
    }

    private synchronized void release(Window window) {
        // The original window may have expired while authentication was running.
        // Never subtract a successful old request from a replacement window.
        if (window.count > 0) window.count--;
    }

    private Permit acquire(String key, int maxAttempts, long windowMillis, long now) {
        if (sharedWindows != null) return sharedWindows.acquire(digest(key), maxAttempts, windowMillis);
        return acquireLocal(key, maxAttempts, windowMillis, now);
    }

    private synchronized Permit acquireLocal(String key, int maxAttempts, long windowMillis, long now) {
        requests++;
        if (requests % 256 == 0 || windows.size() >= MAX_BUCKETS) evictExpired(now);
        Window current = windows.get(key);
        if (current == null || now - current.startedAt >= windowMillis) {
            if (current == null && windows.size() >= MAX_BUCKETS) windows.remove(windows.keySet().iterator().next());
            Window created = new Window(now, 1);
            windows.put(key, created);
            return new Permit(0, () -> release(created));
        }
        if (current.count >= maxAttempts) return new Permit(Math.max(1, (current.startedAt + windowMillis - now + 999) / 1000), () -> {});
        current.count++;
        return new Permit(0, () -> release(current));
    }

    private void evictExpired(long now) {
        Iterator<Window> iterator = windows.values().iterator();
        while (iterator.hasNext()) if (now - iterator.next().startedAt >= 60 * 60 * 1000L) iterator.remove();
    }

    private static Limit limitFor(String method, String path) {
        boolean mutation = "POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method) || "DELETE".equalsIgnoreCase(method);
        if (!mutation) return null;
        if(path.equals("/api/v1/admin/integration-settings"))return new Limit("integration-reauth",20,15*60*1000L);
        if(path.equals("/api/v1/account/whatsapp-link")||path.startsWith("/api/v1/admin/mfa")||path.startsWith("/api/v1/courier/payout")||path.startsWith("/api/v1/admin/courier-finance"))return new Limit("finance-reauth",20,15*60*1000L);
        if (!"POST".equalsIgnoreCase(method)) return null;
        if ("/api/v1/auth/login".equals(path)) return new Limit("login", 10, 15 * 60 * 1000L);
        if ("/api/v1/auth/password-recovery".equals(path)
                || "/api/v1/auth/password-recovery/reset".equals(path)) return new Limit("recovery", 5, 60 * 60 * 1000L);
        if ("/api/v1/auth/register".equals(path)
                || "/api/v1/auth/operator-applications".equals(path)) return new Limit("registration", 10, 60 * 60 * 1000L);
        return null;
    }

    private record Limit(String key, int maxAttempts, long windowMillis) {}
    record Permit(long retryAfter, Runnable release) {}
    private static final class Window {
        private final long startedAt;
        private int count;
        private Window(long startedAt, int count) { this.startedAt = startedAt; this.count = count; }
    }

    private static final class ReplayBodyRequest extends HttpServletRequestWrapper {
        private final byte[] body;
        private ReplayBodyRequest(HttpServletRequest request, byte[] body) { super(request); this.body = body; }
        @Override public ServletInputStream getInputStream() {
            ByteArrayInputStream input = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override public int read() { return input.read(); }
                @Override public int read(byte[] target, int offset, int length) { return input.read(target, offset, length); }
                @Override public boolean isFinished() { return input.available() == 0; }
                @Override public boolean isReady() { return true; }
                @Override public void setReadListener(ReadListener listener) { throw new UnsupportedOperationException("Leitura assíncrona não disponível nesta requisição."); }
            };
        }
        @Override public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
        }
    }
}
