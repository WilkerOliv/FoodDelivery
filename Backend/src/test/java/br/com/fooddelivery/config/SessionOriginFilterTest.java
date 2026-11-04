package br.com.fooddelivery.config;

import static org.junit.jupiter.api.Assertions.*;

import br.com.fooddelivery.identity.AuthSessionCookie;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class SessionOriginFilterTest {
    private final SessionOriginFilter filter = new SessionOriginFilter("http://localhost:5173,http://127.0.0.1:5173");

    @Test void customDemoCookieStillRequiresAnAllowedOrigin() throws Exception {
        var request=new MockHttpServletRequest();request.setMethod("POST");
        request.setCookies(new jakarta.servlet.http.Cookie("fooddelivery_demo_session","opaque"));
        request.addHeader("Origin","https://attacker.example");
        var response=new MockHttpServletResponse();var chain=new TrackingChain();
        new SessionOriginFilter("http://127.0.0.1:5180","fooddelivery_demo_session").doFilter(request,response,chain);
        assertFalse(chain.called);assertEquals(403,response.getStatus());
    }

    @Test
    void permitsMutationFromConfiguredOrigin() throws ServletException, IOException {
        var request = request("POST", "http://localhost:5173");
        var response = new MockHttpServletResponse();
        var chain = new TrackingChain();
        filter.doFilter(request, response, chain);
        assertTrue(chain.called);
        assertEquals(200, response.getStatus());
    }

    @Test
    void rejectsCrossOriginMutationUsingAmbientCookie() throws ServletException, IOException {
        var request = request("POST", "https://attacker.example");
        var response = new MockHttpServletResponse();
        var chain = new TrackingChain();
        filter.doFilter(request, response, chain);
        assertFalse(chain.called);
        assertEquals(403, response.getStatus());
    }

    @Test
    void allowsBearerClientsWithoutCookieAndDoesNotBlockReads() throws ServletException, IOException {
        var bearer = new MockHttpServletRequest();
        bearer.setMethod("POST");
        bearer.addHeader("Authorization", "Bearer integration-token");
        var chain = new TrackingChain();
        filter.doFilter(bearer, new MockHttpServletResponse(), chain);
        assertTrue(chain.called);

        var read = request("GET", "https://attacker.example");
        chain = new TrackingChain();
        filter.doFilter(read, new MockHttpServletResponse(), chain);
        assertTrue(chain.called);
    }

    private static MockHttpServletRequest request(String method, String origin) {
        var request = new MockHttpServletRequest();
        request.setMethod(method);
        request.setCookies(new jakarta.servlet.http.Cookie(AuthSessionCookie.NAME, "opaque"));
        request.addHeader("Origin", origin);
        return request;
    }

    private static final class TrackingChain implements FilterChain {
        private boolean called;
        @Override public void doFilter(jakarta.servlet.ServletRequest request, jakarta.servlet.ServletResponse response) {
            called = true;
        }
    }
}
