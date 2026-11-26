package br.com.fooddelivery.config;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.jdbc.core.JdbcTemplate;

/** Cada aquisição usa a hora do banco e o bloqueio da linha compartilhada pelas réplicas. */
final class PostgresRateWindows {
    private final JdbcTemplate jdbc;
    private final AtomicInteger requests = new AtomicInteger();

    PostgresRateWindows(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    AuthenticationRateLimitFilter.Permit acquire(String key, int limit, long windowMillis) {
        if (requests.incrementAndGet() % 256 == 0) {
            jdbc.update("DELETE FROM authentication_rate_windows WHERE expires_at <= CURRENT_TIMESTAMP");
        }
        // Atualização condicional: uma tentativa negada não consome outro crédito.
        var windows = jdbc.query("""
                INSERT INTO authentication_rate_windows AS w (bucket_key, expires_at, attempts)
                VALUES (?, statement_timestamp() + (? * interval '1 millisecond'), 1)
                ON CONFLICT (bucket_key) DO UPDATE SET
                    window_id = CASE WHEN w.expires_at <= statement_timestamp()
                                     THEN excluded.window_id ELSE w.window_id END,
                    attempts = CASE WHEN w.expires_at <= statement_timestamp() THEN 1 ELSE w.attempts + 1 END,
                    expires_at = CASE WHEN w.expires_at <= statement_timestamp()
                                      THEN excluded.expires_at ELSE w.expires_at END
                WHERE w.attempts < ? OR w.expires_at <= statement_timestamp()
                RETURNING window_id
                """, (rs, i) -> rs.getObject(1, UUID.class), key, windowMillis, limit);
        if (windows.isEmpty()) {
            Long seconds = jdbc.queryForObject("""
                    SELECT COALESCE(MAX(CEIL(EXTRACT(EPOCH FROM expires_at - statement_timestamp()))), 1)::bigint
                    FROM authentication_rate_windows WHERE bucket_key = ?
                    """, Long.class, key);
            return new AuthenticationRateLimitFilter.Permit(Math.max(1L, seconds), () -> {});
        }
        return new AuthenticationRateLimitFilter.Permit(0, () -> jdbc.update("""
                UPDATE authentication_rate_windows SET attempts = attempts - 1
                WHERE bucket_key = ? AND window_id = ? AND attempts > 0
                """, key, windows.get(0)));
    }
}
