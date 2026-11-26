package br.com.fooddelivery.config;

import java.util.function.Supplier;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

/** Default-deny policy for every route not explicitly public in SecurityConfig. */
public final class AuthenticatedAccessManager implements AuthorizationManager<RequestAuthorizationContext> {
    @Override
    public AuthorizationDecision authorize(Supplier<? extends Authentication> authentication, RequestAuthorizationContext context) {
        Authentication current = authentication.get();
        if (!(current instanceof JwtAuthenticationToken jwt) || !current.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }
        boolean forcedPasswordChange = Boolean.TRUE.equals(jwt.getToken().getClaim("password_change_required"));
        return new AuthorizationDecision(!forcedPasswordChange);
    }
}
