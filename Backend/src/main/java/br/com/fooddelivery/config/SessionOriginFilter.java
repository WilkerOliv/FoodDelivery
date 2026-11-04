package br.com.fooddelivery.config;

import br.com.fooddelivery.identity.AuthSessionCookie;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.web.filter.OncePerRequestFilter;

/** Blocks cross-origin state changes made with the browser's ambient session cookie. */
public final class SessionOriginFilter extends OncePerRequestFilter {
    private final Set<String> allowedOrigins;
    private final String cookieName;

    public SessionOriginFilter(String configuredOrigins) {
        this(configuredOrigins, AuthSessionCookie.NAME);
    }
    public SessionOriginFilter(String configuredOrigins, String cookieName) {
        this.cookieName=cookieName;
        allowedOrigins = Stream.of(configuredOrigins.split(","))
                .map(String::trim).filter(value -> !value.isEmpty()).collect(Collectors.toUnmodifiableSet());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (hasSessionCookie(request) && isMutation(request.getMethod())) {
            String origin = request.getHeader("Origin");
            if (origin == null || !allowedOrigins.contains(origin)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Origem da sessão não autorizada.");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean hasSessionCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) return false;
        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) return true;
        }
        return false;
    }

    private static boolean isMutation(String method) {
        return "POST".equals(method) || "PUT".equals(method) || "PATCH".equals(method) || "DELETE".equals(method);
    }
}
