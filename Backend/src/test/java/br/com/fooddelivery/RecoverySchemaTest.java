package br.com.fooddelivery;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import br.com.fooddelivery.catalog.CatalogItem;
import br.com.fooddelivery.catalog.CatalogItemRepository;
import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.catalog.StoreRepository;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRepository;
import br.com.fooddelivery.identity.AccountRole;
import br.com.fooddelivery.reservations.Reservation;
import br.com.fooddelivery.reservations.ReservationSlot;
import br.com.fooddelivery.reservations.ReservationRepository;
import br.com.fooddelivery.reservations.ReservationSlotRepository;
import org.springframework.dao.DataIntegrityViolationException;
import java.time.Instant;

@SpringBootTest(properties = {
    "app.security.jwt-secret=dGVzdC1vbmx5LWtleS1uZXZlci11c2UtaW4tcHJvZHVjdGlvbg==",
        "spring.datasource.url=jdbc:h2:mem:recovery-schema;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "spring.datasource.username=sa", "spring.datasource.password="
})
@ActiveProfiles("dev-h2")
class RecoverySchemaTest {
    @Autowired JdbcTemplate jdbc;
    @Autowired AccountRepository accounts;
    @Autowired StoreRepository stores;
    @Autowired CatalogItemRepository catalogItems;
    @Autowired ReservationRepository reservations;
    @Autowired ReservationSlotRepository slots;

    @Test void flywayAndHibernateAcceptTheRecoverySchema() {
        Integer column = jdbc.queryForObject("select count(*) from information_schema.columns where table_name='ACCOUNTS' and column_name='PHONE_VERIFIED'", Integer.class);
        Integer table = jdbc.queryForObject("select count(*) from information_schema.tables where table_name='RECOVERY_CODES'", Integer.class);
        assertTrue(column != null && column == 1);
        assertTrue(table != null && table == 1);
    }

    @Test void notificationNavigationColumnsAreAvailable() {
        Integer targets = jdbc.queryForObject("select count(*) from information_schema.columns where table_name='ACCOUNT_NOTIFICATIONS' and column_name in ('TARGET_TYPE','TARGET_ID')", Integer.class);
        assertTrue(targets != null && targets == 2);
    }

    @Test void adminAuditTrailIsMigratedWithoutSensitivePayloadColumns() {
        Integer table = jdbc.queryForObject("select count(*) from information_schema.tables where table_name='ADMIN_AUDIT_EVENTS'", Integer.class);
        Integer columns = jdbc.queryForObject("select count(*) from information_schema.columns where table_name='ADMIN_AUDIT_EVENTS' and column_name in ('ACTOR_ID','ACTION','TARGET_TYPE','TARGET_ID','SUMMARY','CREATED_AT')", Integer.class);
        Integer secretColumns = jdbc.queryForObject("select count(*) from information_schema.columns where table_name='ADMIN_AUDIT_EVENTS' and column_name in ('PASSWORD','CPF','TOKEN','SECRET')", Integer.class);
        assertEquals(1, table);
        assertEquals(6, columns);
        assertEquals(0, secretColumns);
    }

    @Test void serviceDurationAndNonNullableReservationResourceKeyAreMigrated() {
        Integer fields = jdbc.queryForObject("select count(*) from information_schema.columns where table_name in ('CATALOG_ITEMS','RESERVATION_SLOTS') and column_name in ('APPOINTMENT_DURATION_MINUTES','SLOT_KEY','ENDS_AT')", Integer.class);
        Integer oldStoreWideConstraint = jdbc.queryForObject("select count(*) from information_schema.table_constraints where table_name='RESERVATIONS' and constraint_name='UK_RESERVATION_SLOT'", Integer.class);
        assertEquals(3, fields);
        assertEquals(0, oldStoreWideConstraint);
    }

    @Test @Transactional void nullProfessionalReservationsCannotClaimSameStoreIntervalTwice() {
        Account owner = new Account("booking-owner@example.com", "Owner", "encoded");
        owner.setRole(AccountRole.MERCHANT);
        owner = accounts.saveAndFlush(owner);
        Account customer = accounts.saveAndFlush(new Account("booking-customer@example.com", "Customer", "encoded"));
        Store store = stores.saveAndFlush(new Store(owner, "Store", null, "Rua A", "Barbearias"));
        CatalogItem item = catalogItems.saveAndFlush(new CatalogItem(store, "Corte", null, 3000, 0, true));
        Instant start = Instant.parse("2026-11-02T10:00:00Z");
        Reservation first = reservations.saveAndFlush(new Reservation(store, customer, start, item.getId(), "Corte", 3000, null));
        slots.saveAndFlush(new ReservationSlot(store.getId(), first.getId(), start, start.plusSeconds(1800), null));

        Reservation second = reservations.saveAndFlush(new Reservation(store, customer, start, item.getId(), "Corte", 3000, null));
        assertThrows(DataIntegrityViolationException.class, () -> slots.saveAndFlush(
                new ReservationSlot(store.getId(), second.getId(), start, start.plusSeconds(1800), null)));
    }

    @Test @Transactional void catalogRemovalIsSoftAndRestrictedToTheOwningMerchant() {
        Account owner = new Account("catalog-owner@example.com", "Owner", "encoded");
        owner.setRole(AccountRole.MERCHANT);
        owner = accounts.saveAndFlush(owner);
        Store store = stores.saveAndFlush(new Store(owner, "Store", null, "Rua A", "Mercados"));
        CatalogItem item = catalogItems.saveAndFlush(new CatalogItem(store, "Rice", "", 1000, 3, false));

        assertEquals(0, catalogItems.deactivateOwned(item.getId(), java.util.UUID.randomUUID()));
        assertEquals(1, catalogItems.deactivateOwned(item.getId(), owner.getId()));
        assertFalse(catalogItems.findById(item.getId()).orElseThrow().isActive());
        assertEquals(0, catalogItems.deactivateOwned(item.getId(), owner.getId()));
    }
}
