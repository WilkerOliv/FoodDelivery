package br.com.fooddelivery.config;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
    "spring.datasource.url=${QA_DATABASE_URL}", "spring.datasource.username=${QA_DATABASE_USERNAME}",
    "spring.datasource.password=${QA_DATABASE_PASSWORD}", "app.security.shared-rate-limit=true",
    "app.security.jwt-secret=dGVzdC1vbmx5LWtleS1uZXZlci11c2UtaW4tcHJvZHVjdGlvbg=="
})
@ActiveProfiles("postgres")
class PostgresRateWindowsIT {
    @Autowired DataSource dataSource;

    @Test void workersShareOneBudgetAndOldReleasesCannotCreditANewWindow() throws Exception {
        var jdbc = new JdbcTemplate(dataSource);
        var workers = new PostgresRateWindows[] {new PostgresRateWindows(jdbc), new PostgresRateWindows(jdbc)};
        String key = UUID.randomUUID().toString();
        var first = workers[0].acquire(key, 3, 60000);
        assertEquals(0, first.retryAfter());
        try (var pool = Executors.newFixedThreadPool(4)) {
            var attempts = IntStream.range(0, 10)
                .<Callable<Boolean>>mapToObj(i -> () -> workers[i % 2].acquire(key, 3, 60000).retryAfter() == 0)
                .toList();
            int allowed = 0;
            for (var result : pool.invokeAll(attempts)) if (result.get()) allowed++;
            assertEquals(2, allowed);
        }
        first.release().run();
        var last = workers[1].acquire(key, 3, 60000);
        assertEquals(0, last.retryAfter());
        jdbc.update("UPDATE authentication_rate_windows SET expires_at = CURRENT_TIMESTAMP WHERE bucket_key = ?", key);
        assertEquals(0, workers[0].acquire(key, 1, 60000).retryAfter());
        last.release().run();
        assertTrue(workers[1].acquire(key, 1, 60000).retryAfter() > 0);
    }
}
