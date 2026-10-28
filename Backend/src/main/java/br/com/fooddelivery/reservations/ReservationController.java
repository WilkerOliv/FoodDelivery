package br.com.fooddelivery.reservations;

import br.com.fooddelivery.catalog.CatalogItemRepository;
import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.catalog.StoreRepository;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRepository;
import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.payments.Payment;
import br.com.fooddelivery.payments.PaymentRepository;
import br.com.fooddelivery.payments.PaymentStatus;
import jakarta.validation.Valid;
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
public class ReservationController {
    private final ReservationRepository reservations;
    private final ReservationSlotRepository slots;
    private final StoreRepository stores;
    private final AccountRepository accounts;
    private final CatalogItemRepository items;
    private final NotificationService notifications;
    private final PaymentRepository payments;

    public ReservationController(ReservationRepository reservations, ReservationSlotRepository slots,
            StoreRepository stores, AccountRepository accounts, CatalogItemRepository items,
            NotificationService notifications, PaymentRepository payments) {
        this.reservations = reservations;
        this.slots = slots;
        this.stores = stores;
        this.accounts = accounts;
        this.items = items;
        this.notifications = notifications;
        this.payments = payments;
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    @Transactional
    public CreateResponse create(@Valid @RequestBody Request request, Authentication auth) {
        UUID customerId = subject(auth);
        Account customer = accounts.findById(customerId).orElseThrow();
        if (customer.getPhone() == null || customer.getPhone().isBlank()) {
            throw new IllegalArgumentException("Complete seu telefone antes de reservar.");
        }
        Store store = stores.findById(request.storeId()).filter(Store::isAvailable)
                .orElseThrow();
        var item = items.findById(request.serviceItemId())
                .filter(i -> i.isActive() && i.isServiceItem() && i.getStore().getId().equals(request.storeId()))
                .orElseThrow(() -> new IllegalArgumentException("Serviço indisponível nesta loja."));
        if (request.startsAt().isBefore(Instant.now().plusSeconds(1800))) {
            throw new IllegalArgumentException("A reserva precisa ser feita com ao menos 30 minutos de antecedência.");
        }
        if (slots.existsByStoreIdAndStartsAt(request.storeId(), request.startsAt())) {
            throw new IllegalStateException("Horário indisponível.");
        }
        Reservation reservation = reservations.save(new Reservation(store, customer, request.startsAt(),
                item.getId(), item.getName(), item.getPriceCents(), request.customerNote()));
        slots.save(new ReservationSlot(store.getId(), reservation.getId(), reservation.getStartsAt()));
        Payment payment = payments.save(new Payment(reservation));
        return new CreateResponse(view(reservation, payment), payment.getId(), payment.getStatus());
    }

    @GetMapping("/reservations/mine")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Transactional(readOnly = true)
    public List<ReservationView> mine(Authentication auth) {
        return reservations.findByCustomerIdOrderByStartsAtDesc(subject(auth)).stream().map(reservation -> {
            Payment payment = payments.findByReservationId(reservation.getId()).orElse(null);
            return view(reservation, payment);
        }).toList();
    }

    @GetMapping("/merchant/reservations")
    @PreAuthorize("hasRole('MERCHANT')")
    @Transactional(readOnly = true)
    public List<ReservationView> merchant(Authentication auth) {
        return reservations.findByStoreOwnerIdOrderByStartsAt(subject(auth)).stream()
                .filter(reservation -> reservation.getStatus() != ReservationStatus.PENDING_PAYMENT)
                .map(reservation -> view(reservation, payments.findByReservationId(reservation.getId()).orElse(null)))
                .toList();
    }

    @PostMapping("/reservations/{id}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Transactional
    public ReservationView cancelUnpaid(@PathVariable UUID id, Authentication auth) {
        Reservation reservation = reservations.findById(id).orElseThrow();
        if (!reservation.getCustomer().getId().equals(subject(auth))) {
            throw new org.springframework.security.access.AccessDeniedException("Reserva de outra conta.");
        }
        Payment payment = payments.findByReservationId(id).orElse(null);
        if (reservation.getStatus() == ReservationStatus.CANCELLED) return view(reservation, payment);
        if (reservation.getStatus() != ReservationStatus.PENDING_PAYMENT) {
            throw new IllegalStateException("A reserva já foi confirmada. Fale com a loja para solicitar o cancelamento e o estorno.");
        }
        if (payment != null && payment.getStatus() == PaymentStatus.PAID) {
            throw new IllegalStateException("O pagamento foi aprovado. Fale com a loja para solicitar o cancelamento e o estorno.");
        }
        reservation.cancelUnpaid();
        slots.deleteByReservationId(id);
        if (payment != null) payment.cancelPendingCheckout();
        notifications.send(reservation.getStore().getOwner(), "Reserva cancelada pelo cliente",
                "Uma reserva ainda não paga foi cancelada e o horário foi liberado.", "info");
        return view(reservations.save(reservation), payment);
    }

    @PatchMapping("/merchant/reservations/{id}")
    @PreAuthorize("hasRole('MERCHANT')")
    @Transactional
    public ReservationView update(@PathVariable UUID id, @Valid @RequestBody Change change, Authentication auth) {
        Reservation reservation = reservations.findById(id).orElseThrow();
        if (!reservation.getStore().getOwner().getId().equals(subject(auth))) {
            throw new org.springframework.security.access.AccessDeniedException("Reserva de outra loja.");
        }
        reservation.change(change.status());
        if (change.status() == ReservationStatus.CANCELLED || change.status() == ReservationStatus.COMPLETED
                || change.status() == ReservationStatus.NO_SHOW) {
            slots.deleteByReservationId(id);
        }
        Payment payment = payments.findByReservationId(id).orElse(null);
        if (change.status() == ReservationStatus.CANCELLED && payment != null
                && payment.getStatus() == PaymentStatus.PAID) {
            payment.requestFullRefund();
            notifications.send(reservation.getCustomer(), "Estorno solicitado",
                    "A loja cancelou a reserva. Solicitamos o estorno pelo Mercado Pago; o status será atualizado quando o provedor confirmar.",
                    "info");
        }
        notifications.send(reservation.getCustomer(), "Reserva atualizada",
                reservation.getStore().getName() + ": sua reserva agora está “"
                        + change.status().name().toLowerCase(java.util.Locale.ROOT).replace('_', ' ') + "”.",
                "info");
        return view(reservations.save(reservation), payment);
    }

    private static ReservationView view(Reservation reservation, Payment payment) {
        Store store = reservation.getStore();
        StoreView storeView = new StoreView(store.getId(), store.getName(), store.getAddress(), store.getCategory());
        return new ReservationView(reservation.getId(), storeView, reservation.getStartsAt(),
                reservation.getServiceName(), reservation.getAmountCents(), reservation.getCustomerNote(), reservation.getStatus(),
                reservation.getCreatedAt(), payment == null ? null : payment.getId(),
                payment == null ? null : payment.getStatus());
    }

    private UUID subject(Authentication auth) {
        return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject());
    }

    public record Request(@NotNull UUID storeId, @NotNull UUID serviceItemId, @NotNull Instant startsAt, @jakarta.validation.constraints.Size(max=500) String customerNote) {}
    public record Change(@NotNull ReservationStatus status) {}
    public record CreateResponse(ReservationView reservation, UUID paymentId, PaymentStatus paymentStatus) {}
    public record ReservationView(UUID id, StoreView store, Instant startsAt, String serviceName, long amountCents, String customerNote,
                                  ReservationStatus status, Instant createdAt, UUID paymentId,
                                  PaymentStatus paymentStatus) {}
    public record StoreView(UUID id, String name, String address, String category) {}
}
