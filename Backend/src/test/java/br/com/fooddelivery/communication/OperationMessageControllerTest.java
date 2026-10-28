package br.com.fooddelivery.communication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.delivery.DeliveryRepository;
import br.com.fooddelivery.delivery.DeliveryStatus;
import br.com.fooddelivery.delivery.DeliveryTask;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRepository;
import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.orders.Order;
import br.com.fooddelivery.orders.OrderRepository;
import br.com.fooddelivery.orders.OrderStatus;
import br.com.fooddelivery.reservations.ReservationRepository;
import jakarta.validation.Validation;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

/** Testes unitários das regras do chat: participantes, pagamento, encerramento e aviso de mensagem nova. */
class OperationMessageControllerTest {
    private final OperationMessageRepository messages = mock(OperationMessageRepository.class);
    private final OrderRepository orders = mock(OrderRepository.class);
    private final ReservationRepository reservations = mock(ReservationRepository.class);
    private final DeliveryRepository deliveries = mock(DeliveryRepository.class);
    private final AccountRepository accounts = mock(AccountRepository.class);
    private final NotificationService notifications = mock(NotificationService.class);
    private OperationMessageController controller;

    private final UUID customerId = UUID.randomUUID(), merchantId = UUID.randomUUID(),
            courierId = UUID.randomUUID(), strangerId = UUID.randomUUID(), orderId = UUID.randomUUID(),
            deliveryId = UUID.randomUUID();
    private Account customer, merchant, courier;
    private Order order;

    @BeforeEach
    void setUp() {
        controller = new OperationMessageController(messages, orders, reservations, deliveries, accounts, notifications);
        customer = account(customerId);
        merchant = account(merchantId);
        courier = account(courierId);
        Store store = mock(Store.class);
        when(store.getOwner()).thenReturn(merchant);
        order = mock(Order.class);
        when(order.getCustomer()).thenReturn(customer);
        when(order.getStore()).thenReturn(store);
        when(order.getStatus()).thenReturn(OrderStatus.PREPARING);
        when(orders.findById(orderId)).thenReturn(Optional.of(order));
        when(messages.findByOrder_IdOrderByCreatedAtDesc(eq(orderId), any())).thenReturn(List.of());
        when(messages.findByDeliveryTask_IdOrderByCreatedAtDesc(eq(deliveryId), any())).thenReturn(List.of());
        when(accounts.findById(customerId)).thenReturn(Optional.of(customer));
        when(accounts.findById(merchantId)).thenReturn(Optional.of(merchant));
        when(accounts.findById(courierId)).thenReturn(Optional.of(courier));
    }

    @Test
    void strangerCannotReadOrderThread() {
        assertThatThrownBy(() -> controller.orderMessages(orderId, auth(strangerId))).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void merchantCannotSeeUnpaidOrderButCustomerSeesClosedThread() {
        when(order.getStatus()).thenReturn(OrderStatus.AWAITING_PAYMENT);
        assertThatThrownBy(() -> controller.orderMessages(orderId, auth(merchantId))).isInstanceOf(AccessDeniedException.class);
        var thread = controller.orderMessages(orderId, auth(customerId));
        assertThat(thread.open()).isFalse();
        assertThat(thread.closedReason()).contains("depois do pagamento");
    }

    @Test
    void cannotSendBeforePaymentOrAfterOrderEnds() {
        var body = new OperationMessageController.SendMessage("Oi");
        when(order.getStatus()).thenReturn(OrderStatus.AWAITING_PAYMENT);
        assertThatThrownBy(() -> controller.sendOrderMessage(orderId, body, auth(customerId))).isInstanceOf(IllegalStateException.class);
        when(order.getStatus()).thenReturn(OrderStatus.COMPLETED);
        assertThatThrownBy(() -> controller.sendOrderMessage(orderId, body, auth(customerId))).isInstanceOf(IllegalStateException.class);
        verify(messages, never()).save(any());
    }

    @Test
    void sendingNotifiesTheOtherParty() {
        controller.sendOrderMessage(orderId, new OperationMessageController.SendMessage("Sem cebola, por favor"), auth(customerId));
        verify(messages).save(any(OperationMessage.class));
        verify(notifications).sendUnreadOnce(eq(merchant), any(), any(), eq("info"));
    }

    @Test
    void deliveryChatIsOpenOnlyWhileAssigned() {
        DeliveryTask task = delivery(DeliveryStatus.ASSIGNED);
        assertThat(controller.deliveryMessages(deliveryId, auth(courierId)).open()).isTrue();
        when(task.getStatus()).thenReturn(DeliveryStatus.PICKED_UP);
        var closed = controller.deliveryMessages(deliveryId, auth(customerId));
        assertThat(closed.open()).isFalse();
        assertThat(closed.closedReason()).isNotBlank();
        assertThatThrownBy(() -> controller.sendDeliveryMessage(deliveryId,
                new OperationMessageController.SendMessage("Cheguei"), auth(courierId))).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void strangerCannotReadDeliveryChat() {
        delivery(DeliveryStatus.ASSIGNED);
        assertThatThrownBy(() -> controller.deliveryMessages(deliveryId, auth(strangerId))).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void messageBodyIsLimitedToThousandCharacters() {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            assertThat(validator.validate(new OperationMessageController.SendMessage("a".repeat(1000)))).isEmpty();
            assertThat(validator.validate(new OperationMessageController.SendMessage("a".repeat(1001)))).isNotEmpty();
            assertThat(validator.validate(new OperationMessageController.SendMessage("   "))).isNotEmpty();
        }
    }

    private DeliveryTask delivery(DeliveryStatus status) {
        DeliveryTask task = mock(DeliveryTask.class);
        when(task.getOrder()).thenReturn(order);
        when(task.getCourier()).thenReturn(courier);
        when(task.getStatus()).thenReturn(status);
        when(deliveries.findById(deliveryId)).thenReturn(Optional.of(task));
        return task;
    }

    private static Account account(UUID id) {
        Account account = mock(Account.class);
        when(account.getId()).thenReturn(id);
        when(account.getName()).thenReturn("Conta " + id.toString().substring(0, 4));
        return account;
    }

    private static Authentication auth(UUID subject) {
        Jwt jwt = Jwt.withTokenValue("token").header("alg", "none").subject(subject.toString()).build();
        return new JwtAuthenticationToken(jwt);
    }
}
