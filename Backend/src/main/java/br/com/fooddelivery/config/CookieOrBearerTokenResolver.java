package br.com.fooddelivery.config;

import br.com.fooddelivery.identity.AuthSessionCookie;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

/** Reads the browser session from an HttpOnly cookie; Authorization remains for non-browser API clients. */
public final class CookieOrBearerTokenResolver implements BearerTokenResolver {
    private final BearerTokenResolver bearer = new DefaultBearerTokenResolver();
    private final String cookieName;
    public CookieOrBearerTokenResolver() { this(AuthSessionCookie.NAME); }
    public CookieOrBearerTokenResolver(String cookieName) { this.cookieName=cookieName; }

    @Override
    public String resolve(HttpServletRequest request) {
        String explicitBearer = bearer.resolve(request);
        if (explicitBearer != null) return explicitBearer;
        // A browser may retain an expired cookie after a restart. It must still
        // be able to sign in again, sign out, recover access and browse public data.
        String path=request.getRequestURI();
        if(path.equals("/api/v1/auth/login")||path.equals("/api/v1/auth/register")||path.equals("/api/v1/auth/logout")
                ||path.startsWith("/api/v1/auth/password-recovery"))return null;
        if("GET".equals(request.getMethod()) && (path.equals("/api/v1/status")||path.equals("/api/v1/demo/status")
                ||path.equals("/api/v1/store-categories")||path.equals("/api/v1/store-locations")
                ||path.equals("/api/v1/stores")||path.startsWith("/api/v1/stores/")
                ||path.startsWith("/api/v1/geography/")||path.startsWith("/api/v1/store-assets/")
                ||path.equals("/actuator/health")))return null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName()) && !cookie.getValue().isBlank()) return cookie.getValue();
            }
        }
        return null;
    }
}
