package br.com.fooddelivery.config;

import static org.junit.jupiter.api.Assertions.*;

import br.com.fooddelivery.identity.AuthSessionCookie;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

class CookieOrBearerTokenResolverTest {
    private final CookieOrBearerTokenResolver resolver = new CookieOrBearerTokenResolver();

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
