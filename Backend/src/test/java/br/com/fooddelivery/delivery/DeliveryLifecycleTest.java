package br.com.fooddelivery.delivery;

import br.com.fooddelivery.catalog.*;
import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.orders.*;
import br.com.fooddelivery.payments.*;
import br.com.fooddelivery.notifications.NotificationService;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeliveryLifecycleTest {
    private final UUID id=UUID.randomUUID(),courierId=UUID.randomUUID(),merchantId=UUID.randomUUID();
    private Account account(UUID id){Account a=new Account(id+"@example.test","Teste","hash");ReflectionTestUtils.setField(a,"id",id);return a;}
    private Order order(){Store store=new Store(account(merchantId),"Loja",null,"Rua A","Mercados");Order order=new Order(account(UUID.randomUUID()),store,FulfillmentType.DELIVERY,"Endereço privado",1000,0,"Nota privada");ReflectionTestUtils.setField(order,"id",id);order.paid();order.transition(OrderStatus.ACCEPTED);order.transition(OrderStatus.PREPARING);order.transition(OrderStatus.READY);return order;}
    @Test void deliveredCompletesOrderAndRedactsAddress(){
        Order order=order();DeliveryTask task=new DeliveryTask(order);task.assign(account(courierId));task.advance(DeliveryStatus.PICKED_UP);task.advance(DeliveryStatus.IN_TRANSIT);
        var deliveries=mock(DeliveryRepository.class);var orders=mock(OrderRepository.class);
        when(deliveries.findById(id)).thenReturn(Optional.of(task));when(orders.findByIdForUpdate(id)).thenReturn(Optional.of(order));when(deliveries.save(task)).thenReturn(task);
        var auth=new JwtAuthenticationToken(Jwt.withTokenValue("test").header("alg","HS256").subject(courierId.toString()).build());
        var response=new DeliveryController(deliveries,orders,mock(AccountRepository.class),mock(br.com.fooddelivery.finance.CourierFinanceService.class)).progress(id,new DeliveryController.Update(DeliveryStatus.DELIVERED),auth);
        assertEquals(OrderStatus.COMPLETED,order.getStatus());assertEquals(DeliveryStatus.DELIVERED,response.status());assertNull(response.deliveryAddress());assertNull(response.customerNote());
    }
    @Test void cancellationRemovesOfferedDelivery(){
        Order order=order();DeliveryTask task=new DeliveryTask(order);var deliveries=mock(DeliveryRepository.class);var orders=mock(OrderRepository.class);
        when(orders.findByIdForUpdate(id)).thenReturn(Optional.of(order));when(deliveries.findByOrderId(id)).thenReturn(Optional.of(task));
        new OrderService(orders,mock(AccountRepository.class),mock(StoreRepository.class),mock(CatalogItemRepository.class),mock(PaymentRepository.class),mock(NotificationService.class),mock(StoreOperatingHours.class),deliveries).merchantTransition(id,merchantId,OrderStatus.CANCELLED);
        assertEquals(DeliveryStatus.CANCELLED,task.getStatus());assertEquals(OrderStatus.CANCELLED,order.getStatus());
    }
    @Test void collectedDeliveryCannotBeCancelled(){
        DeliveryTask task=new DeliveryTask(order());task.assign(account(courierId));task.advance(DeliveryStatus.PICKED_UP);
        assertThrows(IllegalStateException.class,task::cancel);assertEquals(DeliveryStatus.PICKED_UP,task.getStatus());
    }
    @Test void merchantCannotCompleteDeliveryAheadOfCourier(){
        Order order=order();var orders=mock(OrderRepository.class);when(orders.findByIdForUpdate(id)).thenReturn(Optional.of(order));
        var service=new OrderService(orders,mock(AccountRepository.class),mock(StoreRepository.class),mock(CatalogItemRepository.class),mock(PaymentRepository.class),mock(NotificationService.class),mock(StoreOperatingHours.class),mock(DeliveryRepository.class));
        assertThrows(IllegalStateException.class,()->service.merchantTransition(id,merchantId,OrderStatus.COMPLETED));
        assertEquals(OrderStatus.READY,order.getStatus());
    }
}
