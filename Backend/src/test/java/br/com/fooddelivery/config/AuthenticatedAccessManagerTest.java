package br.com.fooddelivery.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

class AuthenticatedAccessManagerTest {
    private final AuthenticatedAccessManager manager = new AuthenticatedAccessManager();
    private final RequestAuthorizationContext context = new RequestAuthorizationContext(new MockHttpServletRequest());

    @Test
    void deniesAnonymousAuthenticationByDefault() {
        var anonymous = new TestingAuthenticationToken("anonymous", "", "ROLE_ANONYMOUS");
        assertFalse(manager.check(() -> anonymous, context).isGranted());
    }

    @Test
    void allowsAuthenticatedJwtWhenPasswordChangeIsNotRequired() {
        var jwt = new JwtAuthenticationToken(token(false), List.of());
        assertTrue(manager.check(() -> jwt, context).isGranted());
    }

    @Test
    void blocksOtherRoutesUntilForcedPasswordChangeIsComplete() {
        var jwt = new JwtAuthenticationToken(token(true), List.of());
        assertFalse(manager.check(() -> jwt, context).isGranted());
    }

    private static Jwt token(boolean passwordChangeRequired) {
        Instant now = Instant.now();
        return Jwt.withTokenValue("test-token").header("alg", "HS256")
                .subject("00000000-0000-0000-0000-000000000001")
                .issuedAt(now).expiresAt(now.plusSeconds(300))
                .claim("password_change_required", passwordChangeRequired).build();
    }
}
