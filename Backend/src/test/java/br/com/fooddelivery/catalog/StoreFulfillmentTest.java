package br.com.fooddelivery.catalog;

import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.orders.FulfillmentType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StoreFulfillmentTest {
    Store store(){return new Store(new Account("store@example.test","Loja","hash"),"Loja",null,"Rua","Mercados");}
    @Test void oldStoresAndOmittedFieldsKeepExistingOptions(){var s=store();s.configureFulfillment(null,null,null);assertTrue(s.isDeliveryEnabled());assertTrue(s.isPickupEnabled());assertTrue(s.isInPersonEnabled());s.configureFulfillment(true,false,false);s.configureFulfillment(null,null,null);assertFalse(s.isPickupEnabled());}
    @Test void deliveryOnlyRejectsPickupAndAppointments(){var s=store();s.configureFulfillment(true,false,false);assertDoesNotThrow(()->s.requireFulfillment(FulfillmentType.DELIVERY));assertThrows(IllegalArgumentException.class,()->s.requireFulfillment(FulfillmentType.PICKUP));assertThrows(IllegalArgumentException.class,s::requireInPerson);}
    @Test void serviceOnlyRejectsProductOrders(){var s=store();s.configureFulfillment(false,false,true);assertDoesNotThrow(s::requireInPerson);assertThrows(IllegalArgumentException.class,()->s.requireFulfillment(FulfillmentType.DELIVERY));assertThrows(IllegalArgumentException.class,()->s.requireFulfillment(FulfillmentType.PICKUP));}
    @Test void cannotDisableEveryModeOrUseNullMode(){var s=store();assertThrows(IllegalArgumentException.class,()->s.configureFulfillment(false,false,false));assertTrue(s.isDeliveryEnabled());assertThrows(IllegalArgumentException.class,()->s.requireFulfillment(null));}
}
