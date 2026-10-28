package br.com.fooddelivery.reservations;

import br.com.fooddelivery.catalog.CatalogItem;
import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.identity.Account;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayAfterServiceTest {
    @Test void catalogAllowsPaymentAfterServiceOnlyForServices() {
        Store store = new Store(null, "Barbearia", null, "Rua A", "Barbearias");
        CatalogItem service = new CatalogItem(store, "Corte", null, 3500, 0, true, true);
        assertTrue(service.isPayAfterService());
        assertThrows(IllegalArgumentException.class,
                () -> new CatalogItem(store, "Pomada", null, 2500, 5, false, true));
    }

    @Test void payAfterServiceReservationStartsConfirmedWithoutPayment() {
        Store store = new Store(null, "Barbearia", null, "Rua A", "Barbearias");
        Account customer = new Account("client@example.com", "Cliente", "hash");
        Reservation reservation = new Reservation(store, customer, Instant.now().plusSeconds(3600),
                UUID.randomUUID(), "Corte", 3500, null, true);

        assertTrue(reservation.isPayAfterService());
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
    }
}
