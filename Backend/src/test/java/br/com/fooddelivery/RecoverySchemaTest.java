package br.com.fooddelivery;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:recovery-schema;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa", "spring.datasource.password="
})
@ActiveProfiles("dev-h2")
class RecoverySchemaTest {
    @Autowired JdbcTemplate jdbc;

    @Test void flywayAndHibernateAcceptTheRecoverySchema() {
        Integer column = jdbc.queryForObject("select count(*) from information_schema.columns where table_name='ACCOUNTS' and column_name='PHONE_VERIFIED'", Integer.class);
        Integer table = jdbc.queryForObject("select count(*) from information_schema.tables where table_name='RECOVERY_CODES'", Integer.class);
        assertTrue(column != null && column == 1);
        assertTrue(table != null && table == 1);
    }
}
