package br.com.fooddelivery.identity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class AuthSessionCookie {
    public static final String NAME = "fooddelivery_session";
    private static final String PATH = "/api/v1";
    private static final long MAX_AGE_SECONDS = 3600;
    private final boolean secure;

    public AuthSessionCookie(@Value("${app.security.cookie-secure:false}") boolean secure) {
        this.secure = secure;
    }

    public ResponseCookie issue(String token) {
        return ResponseCookie.from(NAME, token)
                .httpOnly(true)
                .secure(secure)
                .sameSite("Lax")
                .path(PATH)
                .maxAge(MAX_AGE_SECONDS)
                .build();
    }

    public ResponseCookie clear() {
        return ResponseCookie.from(NAME, "")
                .httpOnly(true)
                .secure(secure)
                .sameSite("Lax")
                .path(PATH)
                .maxAge(0)
                .build();
    }

    public ResponseEntity<Map<String, Object>> responseWithToken(Map<String, Object> tokenResponse) {
        Object token = tokenResponse.get("accessToken");
        if (!(token instanceof String value) || value.isBlank()) throw new IllegalStateException("A sessão não pôde ser criada.");
        Map<String, Object> body = new java.util.LinkedHashMap<>(tokenResponse);
        body.remove("accessToken");
        body.remove("tokenType");
        body.remove("expiresIn");
        body.put("expiresIn", MAX_AGE_SECONDS);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, issue(value).toString())
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .header(HttpHeaders.PRAGMA, "no-cache")
                .body(body);
    }
}
