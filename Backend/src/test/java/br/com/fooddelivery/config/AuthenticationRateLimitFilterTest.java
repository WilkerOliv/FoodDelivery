package br.com.fooddelivery.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.servlet.FilterChain;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

class AuthenticationRateLimitFilterTest {
    @Test
    void throttlesLoginAttemptsPerRemoteAddressAndReturnsRetryInformation() throws Exception {
        var filter = new AuthenticationRateLimitFilter();
        var passed = new AtomicInteger();
        FilterChain chain = (request, response) -> passed.incrementAndGet();

        for (int i = 0; i < 10; i++) invoke(filter, "/api/v1/auth/login", "192.0.2.10", chain);
        var limited = invoke(filter, "/api/v1/auth/login", "192.0.2.10", chain);

        assertEquals(10, passed.get());
        assertEquals(429, limited.getStatus());
        assertTrue(Long.parseLong(limited.getHeader("Retry-After")) > 0);
        assertTrue(limited.getContentAsString().contains("Muitas tentativas"));
    }

    @Test
    void limitsDoNotShareBucketsAcrossClientsOrAuthenticationFlows() throws Exception {
        var filter = new AuthenticationRateLimitFilter();
        var passed = new AtomicInteger();
        FilterChain chain = (request, response) -> passed.incrementAndGet();
        for (int i = 0; i < 10; i++) invoke(filter, "/api/v1/auth/login", "192.0.2.11", chain);

        assertEquals(200, invoke(filter, "/api/v1/auth/login", "192.0.2.12", chain).getStatus());
        assertEquals(200, invoke(filter, "/api/v1/auth/password-recovery", "192.0.2.11", chain).getStatus());
        assertEquals(12, passed.get());
    }

    private static MockHttpServletResponse invoke(AuthenticationRateLimitFilter filter, String path, String address,
                                                   FilterChain chain) throws Exception {
        var request = new MockHttpServletRequest("POST", path);
        request.setRemoteAddr(address);
        var response = new MockHttpServletResponse();
        filter.doFilter(request, response, chain);
        if (response.getStatus() == 200) response.setStatus(200);
        return response;
    }
}
