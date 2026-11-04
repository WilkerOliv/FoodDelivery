package br.com.fooddelivery.config;

import static org.junit.jupiter.api.Assertions.*;

import br.com.fooddelivery.identity.AuthSessionCookie;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class CookieOrBearerTokenResolverTest {
    private final CookieOrBearerTokenResolver resolver = new CookieOrBearerTokenResolver();

    @Test void staleCookieCannotPreventLoginOrPublicDiscoveryButStillProtectsPrivateRoutes() {
        var request=new MockHttpServletRequest();request.setCookies(new Cookie(AuthSessionCookie.NAME,"expired-token"));
        request.setMethod("POST");request.setRequestURI("/api/v1/auth/login");assertNull(resolver.resolve(request));
        request.setMethod("GET");request.setRequestURI("/api/v1/stores");assertNull(resolver.resolve(request));
        request.setRequestURI("/api/v1/account/me");assertEquals("expired-token",resolver.resolve(request));
    }

    @Test void demoAndNormalSessionsDoNotReplaceEachOther() {
        var request=new MockHttpServletRequest();
        request.setCookies(new Cookie(AuthSessionCookie.NAME,"normal"),new Cookie("fooddelivery_demo_session","demo"));
        assertEquals("normal",resolver.resolve(request));
        assertEquals("demo",new CookieOrBearerTokenResolver("fooddelivery_demo_session").resolve(request));
    }

    @Test
    void resolvesBrowserSessionCookie() {
        var request = new MockHttpServletRequest();
        request.setCookies(new Cookie(AuthSessionCookie.NAME, "cookie-token"));
        assertEquals("cookie-token", resolver.resolve(request));
    }

    @Test
    void keepsBearerSupportForNonBrowserClients() {
        var request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer api-token");
        assertEquals("api-token", resolver.resolve(request));
    }

    @Test
    void explicitApiBearerTakesPrecedenceOverBrowserCookie() {
        var request = new MockHttpServletRequest();
        request.setCookies(new Cookie(AuthSessionCookie.NAME, "browser-token"));
        request.addHeader("Authorization", "Bearer api-token");
        assertEquals("api-token", resolver.resolve(request));
    }
}
