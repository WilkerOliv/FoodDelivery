package br.com.fooddelivery.config;

import br.com.fooddelivery.identity.AuthSessionCookie;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;

/** Reads the browser session from an HttpOnly cookie; Authorization remains for non-browser API clients. */
public final class CookieOrBearerTokenResolver implements BearerTokenResolver {
    private final BearerTokenResolver bearer = new DefaultBearerTokenResolver();

    @Override
    public String resolve(HttpServletRequest request) {
        String explicitBearer = bearer.resolve(request);
        if (explicitBearer != null) return explicitBearer;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AuthSessionCookie.NAME.equals(cookie.getName()) && !cookie.getValue().isBlank()) return cookie.getValue();
            }
        }
        return null;
    }
}
