package br.com.fooddelivery;

import java.sql.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import static org.junit.jupiter.api.Assertions.*;

/** Uses only the disposable database supplied by scripts/test_postgres.py. */
class V9MigrationEquivalenceIT {
    @Test void bothDialectsPreserveTheSameColumnsDefaultsAndConstraints() throws Exception {
        String schema = "v9_qa_" + UUID.randomUUID().toString().replace("-", "");
        try (Connection pg = DriverManager.getConnection(System.getenv("QA_DATABASE_URL"),
                System.getenv("QA_DATABASE_USERNAME"), System.getenv("QA_DATABASE_PASSWORD"));
             Connection h2 = DriverManager.getConnection("jdbc:h2:mem:" + schema + ";MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE", "sa", "")) {
            execute(pg, "CREATE SCHEMA " + schema);
            try {
                execute(pg, "SET search_path TO " + schema);
                prepare(pg, "postgres");
                prepare(h2, "h2");
                assertEquals(columns(h2, "public"), columns(pg, schema));
                checkBehavior(pg);
                checkBehavior(h2);
            } finally {
                execute(pg, "DROP SCHEMA " + schema + " CASCADE");
            }
        }
    }

    private static void prepare(Connection connection, String dialect) throws Exception {
        execute(connection, "CREATE TABLE accounts (id INT PRIMARY KEY, role VARCHAR(20))");
        execute(connection, "CREATE TABLE stores (id INT PRIMARY KEY, active BOOLEAN, approved BOOLEAN)");
        execute(connection, "INSERT INTO accounts(id,role) VALUES(1,'ADMIN')");
        ScriptUtils.executeSqlScript(connection, new ClassPathResource("db/migration-" + dialect + "/V9__account_security_and_store_location.sql"));
    }

    private static SortedMap<String,String> columns(Connection connection, String schema) throws Exception {
        SortedMap<String,String> values = new TreeMap<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT table_name,column_name,data_type,character_maximum_length,is_nullable FROM information_schema.columns WHERE table_schema=? AND table_name IN ('accounts','stores')")) {
            statement.setString(1, schema);
            try (ResultSet rows = statement.executeQuery()) {
                while(rows.next()) values.put(rows.getString(1) + "." + rows.getString(2),
                    rows.getString(3) + ":" + rows.getString(4) + ":" + rows.getString(5));
            }
        }
        return values;
    }

    private static void checkBehavior(Connection connection) throws Exception {
        try (Statement statement = connection.createStatement(); ResultSet rows = statement.executeQuery("SELECT granted_roles FROM accounts WHERE id=1")) {
            assertTrue(rows.next()); assertEquals("ADMIN", rows.getString(1));
        }
        // Several absent documents remain valid; an existing CPF must stay unique.
        execute(connection, "INSERT INTO accounts(id) VALUES(2),(3)");
        execute(connection, "UPDATE accounts SET cpf='12345678901',requested_operator_role='MERCHANT' WHERE id=2");
        execute(connection, "UPDATE accounts SET requested_operator_role='COURIER' WHERE id=3");
        assertThrows(SQLException.class, () -> execute(connection, "UPDATE accounts SET cpf='12345678901' WHERE id=3"));
        assertThrows(SQLException.class, () -> execute(connection, "UPDATE accounts SET requested_operator_role='ADMIN' WHERE id=3"));
        assertThrows(SQLException.class, () -> execute(connection, "UPDATE accounts SET granted_roles=NULL WHERE id=3"));
        try (Statement statement = connection.createStatement(); ResultSet rows = statement.executeQuery("SELECT granted_roles,suspended_roles,password_change_required FROM accounts WHERE id=3")) {
            assertTrue(rows.next()); assertEquals("CUSTOMER", rows.getString(1));
            assertEquals("", rows.getString(2)); assertFalse(rows.getBoolean(3));
        }
    }

    private static void execute(Connection connection, String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) { statement.execute(sql); }
    }
}
