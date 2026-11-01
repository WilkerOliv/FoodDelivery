package br.com.fooddelivery.catalog;

import static org.junit.jupiter.api.Assertions.*;

import br.com.fooddelivery.identity.Account;
import org.junit.jupiter.api.Test;

class StoreCoordinatesTest {
    private Store store() { return new Store(new Account("maps@example.test", "Maps", "encoded"), "Loja", null, "Rua", "Mercados"); }

    @Test void acceptsCoordinatesAndAllowsBothToBeCleared() {
        Store store = store();
        store.setCoordinates(-23.55052, -46.633308);
        assertEquals(-23.55052, store.getLatitude());
        assertEquals(-46.633308, store.getLongitude());
        store.setCoordinates(null, null);
        assertNull(store.getLatitude());
        assertNull(store.getLongitude());
    }

    @Test void rejectsPartialOrOutOfRangeCoordinates() {
        Store store = store();
        assertThrows(IllegalArgumentException.class, () -> store.setCoordinates(-23.5, null));
        assertThrows(IllegalArgumentException.class, () -> store.setCoordinates(91.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> store.setCoordinates(0.0, Double.NaN));
    }
}
