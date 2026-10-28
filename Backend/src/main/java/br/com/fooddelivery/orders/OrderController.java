package br.com.fooddelivery.orders;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.delivery.DeliveryRepository;
import br.com.fooddelivery.payments.Payment;
import br.com.fooddelivery.payments.PaymentRepository;
import br.com.fooddelivery.payments.PaymentStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService service;
    private final OrderRepository orders;
    private final PaymentRepository payments;
    private final DeliveryRepository deliveries;

    public OrderController(OrderService service, OrderRepository orders, PaymentRepository payments, DeliveryRepository deliveries) {
        this.service = service;
        this.orders = orders;
        this.payments = payments;
        this.deliveries = deliveries;
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    @Transactional
    public CheckoutResponse create(@Valid @RequestBody CreateOrder request, Authentication auth) {
        Order order = service.create(subject(auth), request.storeId(), request.fulfillmentType(),
                request.deliveryAddress(), request.customerNote(), request.items().stream()
                        .map(item -> new OrderService.RequestedLine(item.itemId(), item.quantity())).toList());
        Payment payment = payments.findByOrderId(order.getId()).orElseThrow();
        return new CheckoutResponse(view(order, payment, false), payment.getId(), payment.getStatus());
    }

    @GetMapping("/orders/mine")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Transactional(readOnly = true)
    public List<OrderView> mine(Authentication auth) {
        return service.customerOrders(subject(auth)).stream().map(order -> {
            Payment payment = payments.findByOrderId(order.getId()).orElse(null);
            return view(order, payment, true);
        }).toList();
    }

    @GetMapping("/merchant/orders")
    @PreAuthorize("hasRole('MERCHANT')")
    @Transactional(readOnly = true)
    public List<OrderView> merchantOrders(Authentication auth) {
        return orders.findByStoreOwnerIdOrderByCreatedAtDesc(subject(auth)).stream()
                .filter(order -> order.getStatus() != OrderStatus.AWAITING_PAYMENT)
                .map(order -> view(order, null, false)).toList();
    }

    @PostMapping("/orders/{id}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Transactional
    public OrderView cancelUnpaid(@PathVariable UUID id, Authentication auth) {
        Order order = service.cancelUnpaidByCustomer(id, subject(auth));
        return view(order, payments.findByOrderId(id).orElse(null), false);
    }

    @PatchMapping("/merchant/orders/{id}/status")
    @PreAuthorize("hasRole('MERCHANT')")
    @Transactional
    public OrderView status(@PathVariable UUID id, @Valid @RequestBody StatusChange request, Authentication auth) {
        Order order = service.merchantTransition(id, subject(auth), request.status());
        return view(order, payments.findByOrderId(id).orElse(null), false);
    }

    private OrderView view(Order order, Payment payment, boolean includeTracking) {
        Store store = order.getStore();
        StoreView storeView = new StoreView(store.getId(), store.getName(), store.getDescription(), store.getAddress(),
                store.getCategory(), store.isActive(), store.isApproved(), store.getDeliveryFeeCents());
        List<OrderLineView> lines = order.getLines().stream()
                .map(line -> new OrderLineView(line.getItemId(), line.getName(), line.getQuantity(), line.getUnitPriceCents()))
                .toList();
        return new OrderView(order.getId(), storeView, order.getStatus(), order.getFulfillmentType(),
                order.getDeliveryAddress(), order.getCustomerNote(), order.getTotalCents(), order.getSubtotalCents(), order.getDeliveryFeeCents(),
                order.getCurrency(), order.getCreatedAt(), lines, payment == null ? null : payment.getId(),
                payment == null ? null : payment.getStatus(),
                includeTracking ? deliveries.findByOrderId(order.getId()).map(d -> new TrackingView(d.getId(), d.getStatus().name(), d.getCourierLatitude(),
                        d.getCourierLongitude(), d.getLocationUpdatedAt())).orElse(null) : null);
    }

    private UUID subject(Authentication auth) {
        return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject());
    }

    public record ItemLine(@NotNull UUID itemId, @Min(1) @Max(50) int quantity) {}
    public record CreateOrder(@NotNull UUID storeId, @NotNull FulfillmentType fulfillmentType,
                              String deliveryAddress, @jakarta.validation.constraints.Size(max=500) String customerNote, @NotEmpty List<@Valid ItemLine> items) {}
    public record StatusChange(@NotNull OrderStatus status) {}
    public record CheckoutResponse(OrderView order, UUID paymentId, PaymentStatus paymentStatus) {}
    public record OrderView(UUID id, StoreView store, OrderStatus status, FulfillmentType fulfillmentType,
                            String deliveryAddress, String customerNote, long totalCents, long subtotalCents, long deliveryFeeCents,
                            String currency, Instant createdAt, List<OrderLineView> lines, UUID paymentId,
                            PaymentStatus paymentStatus, TrackingView tracking) {}
    public record TrackingView(UUID deliveryId, String status, Double latitude, Double longitude, Instant locationUpdatedAt) {}
    public record StoreView(UUID id, String name, String description, String address, String category,
                            boolean active, boolean approved, long deliveryFeeCents) {}
    public record OrderLineView(UUID itemId, String name, int quantity, long unitPriceCents) {}
}
