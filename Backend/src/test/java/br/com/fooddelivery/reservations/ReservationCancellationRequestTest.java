package br.com.fooddelivery.reservations;

import static org.junit.jupiter.api.Assertions.*;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.identity.Account;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ReservationCancellationRequestTest {
    private Reservation confirmedReservation() {
        Account owner = new Account("owner@example.test", "Owner", "encoded");
        Account customer = new Account("customer@example.test", "Customer", "encoded");
        Store store = new Store(owner, "Loja", null, "Rua", "Barbearias");
        Instant start = Instant.parse("2026-11-02T10:00:00Z");
        return new Reservation(store, customer, start, UUID.randomUUID(), "Corte", 3000, null, true);
    }

    @Test void merchantDecisionControlsCancellationAndNormalProgress() {
        Reservation reservation = confirmedReservation();
        reservation.requestCancellation("Não poderei comparecer");
        assertEquals("REQUESTED", reservation.getCancellationRequestStatus());
        assertThrows(IllegalStateException.class, () -> reservation.change(ReservationStatus.IN_SERVICE));
        reservation.decideCancellation(false);
        reservation.change(ReservationStatus.IN_SERVICE);
        assertEquals("DECLINED", reservation.getCancellationRequestStatus());
        assertEquals(ReservationStatus.IN_SERVICE, reservation.getStatus());
    }
}
