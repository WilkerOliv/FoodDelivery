package br.com.fooddelivery.orders;

import static org.junit.jupiter.api.Assertions.*;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.identity.Account;
import org.junit.jupiter.api.Test;

class OrderCancellationRequestTest {
    private Order paidOrder() {
        Account owner = new Account("owner@example.test", "Owner", "encoded");
        Account customer = new Account("customer@example.test", "Customer", "encoded");
        Store store = new Store(owner, "Loja", null, "Rua", "Mercados");
        Order order = new Order(customer, store, FulfillmentType.PICKUP, null, 1000, 0, null);
        order.paid();
        return order;
    }

    @Test void acceptedCancellationCanClosePaidOrder() {
        Order order = paidOrder();
        order.requestCancellation("Imprevisto");
        assertEquals("REQUESTED", order.getCancellationRequestStatus());
        assertThrows(IllegalStateException.class, () -> order.transition(OrderStatus.PREPARING));
        order.decideCancellation(true);
        order.transition(OrderStatus.CANCELLED);
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertEquals("ACCEPTED", order.getCancellationRequestStatus());
    }

    @Test void decliningCancellationPreservesNormalOrderProgress() {
        Order order = paidOrder();
        order.requestCancellation(null);
        order.decideCancellation(false);
        order.transition(OrderStatus.ACCEPTED);
        assertEquals("DECLINED", order.getCancellationRequestStatus());
        assertEquals(OrderStatus.ACCEPTED, order.getStatus());
    }
}
