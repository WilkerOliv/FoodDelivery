package br.com.fooddelivery.identity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;

class AuthSessionCookieTest {
    @Test
    void tokenIsIssuedOnlyInHttpOnlyCookieAndRemovedFromJsonBody() {
        var cookie = new AuthSessionCookie(false);
        var response = cookie.responseWithToken(Map.of("accessToken", "secret-token", "tokenType", "Bearer",
                "expiresIn", 3600, "account", Map.of("role", "CUSTOMER")));
        String header = response.getHeaders().getFirst("Set-Cookie");
        assertNotNull(header);
        assertTrue(header.contains("fooddelivery_session=secret-token"));
        assertTrue(header.contains("HttpOnly"));
        assertTrue(header.contains("SameSite=Lax"));
        assertEquals("no-store", response.getHeaders().getCacheControl());
        assertEquals("no-cache", response.getHeaders().getFirst("Pragma"));
        assertFalse(response.getBody().containsKey("accessToken"));
        assertFalse(response.getBody().containsKey("tokenType"));
        assertEquals(3600L, response.getBody().get("expiresIn"));
    }

    @Test
    void clearingCookieUsesSamePathAndImmediateExpiry() {
        var cookie = new AuthSessionCookie(true).clear().toString();
        assertTrue(cookie.contains("Path=/api/v1"));
        assertTrue(cookie.contains("Max-Age=0"));
        assertTrue(cookie.contains("Secure"));
        assertTrue(cookie.contains("HttpOnly"));
    }

    @Test
    void accountSerializationNeverExposesPasswordHash() throws Exception {
        String serialized = new com.fasterxml.jackson.databind.ObjectMapper().findAndRegisterModules()
                .writeValueAsString(new Account("private@example.com", "Private", "bcrypt-secret-hash"));
        assertFalse(serialized.contains("passwordHash"));
        assertFalse(serialized.contains("bcrypt-secret-hash"));
    }
}
