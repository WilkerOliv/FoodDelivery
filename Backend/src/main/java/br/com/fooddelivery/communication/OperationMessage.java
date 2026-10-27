package br.com.fooddelivery.communication;

import br.com.fooddelivery.delivery.DeliveryTask;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.orders.Order;
import br.com.fooddelivery.reservations.Reservation;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "operation_messages")
public class OperationMessage {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id") private Order order;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "reservation_id") private Reservation reservation;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "delivery_task_id") private DeliveryTask deliveryTask;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "sender_id") private Account sender;
    @Column(nullable = false, length = 1000) private String body;
    @Column(name = "created_at", nullable = false, updatable = false) private Instant createdAt = Instant.now();

    protected OperationMessage() {}
    private OperationMessage(Account sender, String body) { this.sender = sender; this.body = body.trim(); }
    public static OperationMessage forOrder(Order order, Account sender, String body) { var m = new OperationMessage(sender, body); m.order = order; return m; }
    public static OperationMessage forReservation(Reservation reservation, Account sender, String body) { var m = new OperationMessage(sender, body); m.reservation = reservation; return m; }
    public static OperationMessage forDelivery(DeliveryTask delivery, Account sender, String body) { var m = new OperationMessage(sender, body); m.deliveryTask = delivery; return m; }
    public UUID getId() { return id; }
    public Account getSender() { return sender; }
    public String getBody() { return body; }
    public Instant getCreatedAt() { return createdAt; }
}
