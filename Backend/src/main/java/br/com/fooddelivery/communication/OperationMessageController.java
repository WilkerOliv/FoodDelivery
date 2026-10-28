package br.com.fooddelivery.communication;

import br.com.fooddelivery.delivery.DeliveryRepository;
import br.com.fooddelivery.delivery.DeliveryStatus;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRepository;
import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.orders.Order;
import br.com.fooddelivery.orders.OrderRepository;
import br.com.fooddelivery.orders.OrderStatus;
import br.com.fooddelivery.reservations.Reservation;
import br.com.fooddelivery.reservations.ReservationRepository;
import br.com.fooddelivery.reservations.ReservationStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class OperationMessageController {
    private static final int THREAD_LIMIT = 100;
    private final OperationMessageRepository messages;
    private final OrderRepository orders;
    private final ReservationRepository reservations;
    private final DeliveryRepository deliveries;
    private final AccountRepository accounts;
    private final NotificationService notifications;

    public OperationMessageController(OperationMessageRepository messages, OrderRepository orders,
            ReservationRepository reservations, DeliveryRepository deliveries, AccountRepository accounts,
            NotificationService notifications) {
        this.messages = messages; this.orders = orders; this.reservations = reservations;
        this.deliveries = deliveries; this.accounts = accounts; this.notifications = notifications;
    }

    @GetMapping("/orders/{id}/messages")
    @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')")
    @Transactional(readOnly = true)
    public ThreadView orderMessages(@PathVariable UUID id, Authentication auth) {
        Order order = orders.findById(id).orElseThrow();
        UUID userId = subject(auth);
        requireOrderParticipant(order, userId);
        boolean unpaid = isUnpaid(order.getStatus());
        if (unpaid && !order.getCustomer().getId().equals(userId)) throw new AccessDeniedException(UNPAID_ORDER);
        List<MessageView> items = chronological(messages.findByOrder_IdOrderByCreatedAtDesc(id, newestFirst()));
        boolean open = !unpaid && !isTerminal(order.getStatus());
        return new ThreadView(items, open, open ? null : unpaid ? UNPAID_ORDER : terminalOrderMessage(order.getStatus()));
    }

    @PostMapping("/orders/{id}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')")
    @Transactional
    public ThreadView sendOrderMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth) {
        Order order = orders.findById(id).orElseThrow(); UUID senderId = subject(auth);
        requireOrderParticipant(order, senderId);
        if (isUnpaid(order.getStatus())) throw new IllegalStateException(UNPAID_ORDER);
        if (isTerminal(order.getStatus())) throw new IllegalStateException(terminalOrderMessage(order.getStatus()));
        Account sender = account(senderId);
        messages.save(OperationMessage.forOrder(order, sender, request.body()));
        Account recipient = order.getCustomer().getId().equals(senderId) ? order.getStore().getOwner() : order.getCustomer();
        notifications.sendUnreadOnce(recipient, "Nova mensagem sobre o pedido " + shortId(id), "Abra o pedido para ler e responder.", "info");
        return orderMessages(id, auth);
    }

    @GetMapping("/reservations/{id}/messages")
    @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')")
    @Transactional(readOnly = true)
    public ThreadView reservationMessages(@PathVariable UUID id, Authentication auth) {
        Reservation reservation = reservations.findById(id).orElseThrow();
        UUID userId = subject(auth);
        requireReservationParticipant(reservation, userId);
        boolean unpaid = reservation.getStatus() == ReservationStatus.PENDING_PAYMENT;
        if (unpaid && !reservation.getCustomer().getId().equals(userId)) throw new AccessDeniedException(UNPAID_RESERVATION);
        List<MessageView> items = chronological(messages.findByReservation_IdOrderByCreatedAtDesc(id, newestFirst()));
        boolean open = !unpaid && !isTerminal(reservation.getStatus());
        return new ThreadView(items, open, open ? null : unpaid ? UNPAID_RESERVATION : terminalReservationMessage(reservation.getStatus()));
    }

    @PostMapping("/reservations/{id}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT')")
    @Transactional
    public ThreadView sendReservationMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth) {
        Reservation reservation = reservations.findById(id).orElseThrow(); UUID senderId = subject(auth);
        requireReservationParticipant(reservation, senderId);
        if (reservation.getStatus() == ReservationStatus.PENDING_PAYMENT) throw new IllegalStateException(UNPAID_RESERVATION);
        if (isTerminal(reservation.getStatus())) throw new IllegalStateException(terminalReservationMessage(reservation.getStatus()));
        Account sender = account(senderId);
        messages.save(OperationMessage.forReservation(reservation, sender, request.body()));
        Account recipient = reservation.getCustomer().getId().equals(senderId) ? reservation.getStore().getOwner() : reservation.getCustomer();
        notifications.sendUnreadOnce(recipient, "Nova mensagem sobre a reserva " + shortId(id), "Abra a reserva para ler e responder.", "info");
        return reservationMessages(id, auth);
    }

    @GetMapping("/deliveries/{id}/messages")
    @PreAuthorize("hasAnyRole('CUSTOMER','COURIER')")
    @Transactional(readOnly = true)
    public ThreadView deliveryMessages(@PathVariable UUID id, Authentication auth) {
        var delivery = deliveries.findById(id).orElseThrow(); UUID userId = subject(auth);
        requireDeliveryParticipant(delivery.getOrder(), delivery.getCourier(), userId);
        List<MessageView> items = chronological(messages.findByDeliveryTask_IdOrderByCreatedAtDesc(id, newestFirst()));
        boolean open = delivery.getCourier() != null && delivery.getStatus() == DeliveryStatus.ASSIGNED;
        return new ThreadView(items, open, open ? null : "O chat do entregador é encerrado quando o pedido é coletado ou a entrega termina.");
    }

    @PostMapping("/deliveries/{id}/messages")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER','COURIER')")
    @Transactional
    public ThreadView sendDeliveryMessage(@PathVariable UUID id, @Valid @RequestBody SendMessage request, Authentication auth) {
        var delivery = deliveries.findById(id).orElseThrow(); UUID userId = subject(auth);
        requireDeliveryParticipant(delivery.getOrder(), delivery.getCourier(), userId);
        if (delivery.getStatus() != DeliveryStatus.ASSIGNED) {
            throw new IllegalStateException("O chat foi encerrado após a coleta do pedido.");
        }
        Account sender = account(userId);
        messages.save(OperationMessage.forDelivery(delivery, sender, request.body()));
        Account recipient = delivery.getOrder().getCustomer().getId().equals(userId) ? delivery.getCourier() : delivery.getOrder().getCustomer();
        notifications.sendUnreadOnce(recipient, "Nova mensagem do chat de entrega " + shortId(id), "Abra a entrega para ler e responder.", "info");
        return deliveryMessages(id, auth);
    }

    private PageRequest newestFirst() { return PageRequest.of(0, THREAD_LIMIT, Sort.by(Sort.Direction.DESC, "createdAt")); }
    private List<MessageView> chronological(List<OperationMessage> newestFirst) { var result = new ArrayList<>(newestFirst.stream().map(this::view).toList()); java.util.Collections.reverse(result); return result; }
    private MessageView view(OperationMessage m) { return new MessageView(m.getId(), m.getSender().getId(), m.getSender().getName(), m.getBody(), m.getCreatedAt()); }
    private Account account(UUID id) { return accounts.findById(id).orElseThrow(); }
    private UUID subject(Authentication auth) { return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject()); }
    private void requireOrderParticipant(Order o, UUID user) { if (!o.getCustomer().getId().equals(user) && !o.getStore().getOwner().getId().equals(user)) throw new AccessDeniedException("Este pedido pertence a outra conta."); }
    private void requireReservationParticipant(Reservation r, UUID user) { if (!r.getCustomer().getId().equals(user) && !r.getStore().getOwner().getId().equals(user)) throw new AccessDeniedException("Esta reserva pertence a outra conta."); }
    private void requireDeliveryParticipant(Order order, Account courier, UUID user) { if (!order.getCustomer().getId().equals(user) && (courier == null || !courier.getId().equals(user))) throw new AccessDeniedException("Esta conversa não pertence a esta conta."); }
    private static final String UNPAID_ORDER = "A conversa com a loja é liberada depois do pagamento do pedido.";
    private static final String UNPAID_RESERVATION = "A conversa com a loja é liberada depois do pagamento da reserva.";
    private static String shortId(UUID id) { return id.toString().substring(0, 8).toUpperCase(); }
    private boolean isUnpaid(OrderStatus status) { return status == OrderStatus.AWAITING_PAYMENT; }
    private boolean isTerminal(OrderStatus status) { return status == OrderStatus.COMPLETED || status == OrderStatus.REJECTED || status == OrderStatus.CANCELLED; }
    private boolean isTerminal(ReservationStatus status) { return status == ReservationStatus.COMPLETED || status == ReservationStatus.CANCELLED || status == ReservationStatus.NO_SHOW; }
    private String terminalOrderMessage(OrderStatus status) { return "A conversa com a loja foi encerrada porque o pedido está " + status.name().toLowerCase().replace('_', ' ') + "."; }
    private String terminalReservationMessage(ReservationStatus status) { return "A conversa foi encerrada porque a reserva está " + status.name().toLowerCase().replace('_', ' ') + "."; }

    public record SendMessage(@NotBlank @Size(max = 1000) String body) {}
    public record MessageView(UUID id, UUID senderId, String senderName, String body, Instant createdAt) {}
    public record ThreadView(List<MessageView> messages, boolean open, String closedReason) {
        public ThreadView { messages = List.copyOf(messages); }
    }
}
