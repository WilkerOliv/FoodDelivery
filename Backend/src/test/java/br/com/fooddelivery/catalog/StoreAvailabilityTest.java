package br.com.fooddelivery.catalog;

import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreAvailabilityTest {
    private Store storeFor(Account owner) {
        Store store = new Store(owner, "Loja", null, "Rua A", "Restaurantes");
        store.setApproved(true);
        return store;
    }

    @Test void approvedStoreIsAvailableOnlyWhenMerchantAccountAndRoleAreActive() {
        Account owner = new Account("seller@example.com", "Seller", "encoded");
        owner.setRole(AccountRole.MERCHANT);
        Store store = storeFor(owner);

        assertTrue(store.isAvailable());
        owner.setRoleActive(AccountRole.MERCHANT, false);
        assertFalse(store.isAvailable());
        owner.setRoleActive(AccountRole.MERCHANT, true);
        owner.deactivate();
        assertFalse(store.isAvailable());
    }

    @Test void unapprovedOrInactiveStoreIsNeverAvailable() {
        Account owner = new Account("seller@example.com", "Seller", "encoded");
        owner.setRole(AccountRole.MERCHANT);
        Store store = new Store(owner, "Loja", null, "Rua A", "Restaurantes");

        assertFalse(store.isAvailable());
        store.setApproved(true);
        store.setActive(false);
        assertFalse(store.isAvailable());
    }
}
