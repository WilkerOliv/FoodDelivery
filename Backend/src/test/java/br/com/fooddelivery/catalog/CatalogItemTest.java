package br.com.fooddelivery.catalog;

import br.com.fooddelivery.identity.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatalogItemTest {
    @Test void updateCanSwitchProductAndServiceAndPreservesPaymentRules() {
        Account owner = new Account("seller@example.com", "Seller", "encoded");
        Store store = new Store(owner, "Loja", null, "Rua A", "Serviços");
        CatalogItem item = new CatalogItem(store, "Corte", "", 2500, 0, true, true);

        item.update("Pomada", "Produto", 3000, 12, true, false, false);
        assertFalse(item.isServiceItem());
        assertFalse(item.isPayAfterService());
        assertEquals(12, item.getStock());

        item.update("Aplicação", "Serviço", 4000, 0, true, true, true);
        assertTrue(item.isServiceItem());
        assertTrue(item.isPayAfterService());
        assertThrows(IllegalArgumentException.class,
                () -> item.update("Inválido", "", 100, 0, true, false, true));
    }

    @Test void deactivationCanBeReversedWithoutDeletingCatalogHistory() {
        Account owner = new Account("seller@example.com", "Seller", "encoded");
        CatalogItem item = new CatalogItem(new Store(owner, "Loja", null, "Rua A", "Mercados"),
                "Feijão", "", 1000, 4, false);

        item.deactivate();
        assertFalse(item.isActive());
        item.update(item.getName(), item.getDescription(), item.getPriceCents(), item.getStock(),
                true, item.isServiceItem(), item.isPayAfterService());
        assertTrue(item.isActive());
    }
}
