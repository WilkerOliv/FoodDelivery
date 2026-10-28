package br.com.fooddelivery.identity;

import static org.junit.jupiter.api.Assertions.*;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class RecoveryCodeTest {
    @Test void codeExpiresAfterTenMinutesAndCanOnlyBeConsumedOnce() {
        Instant now = Instant.parse("2026-10-28T12:00:00Z");
        Account account = new Account("ana@example.com", "Ana", "hash");
        RecoveryCode code = new RecoveryCode(account, "PASSWORD_RESET", "EMAIL",
                new BCryptPasswordEncoder().encode("123456"), now);
        assertTrue(code.isReadyAt(now.plusSeconds(599)));
        assertFalse(code.isReadyAt(now.plusSeconds(600)));
        code.consume(now.plusSeconds(20));
        assertFalse(code.isReadyAt(now.plusSeconds(21)));
    }

    @Test void codeStopsWorkingAfterFiveWrongAttempts() {
        Instant now = Instant.now();
        RecoveryCode code = new RecoveryCode(new Account("ana@example.com", "Ana", "hash"),
                "PASSWORD_RESET", "EMAIL", "hash", now);
        for (int attempt = 0; attempt < 4; attempt++) {
            code.recordFailedAttempt();
            assertTrue(code.isReadyAt(Instant.now()));
        }
        code.recordFailedAttempt();
        assertFalse(code.isReadyAt(Instant.now()));
    }
}
