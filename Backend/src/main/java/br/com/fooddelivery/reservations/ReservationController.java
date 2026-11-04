package br.com.fooddelivery.reservations;

import br.com.fooddelivery.catalog.CatalogItemRepository;
import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.catalog.StoreRepository;
import br.com.fooddelivery.catalog.StoreProfessional;
import br.com.fooddelivery.catalog.StoreProfessionalRepository;
import br.com.fooddelivery.platform.CityMatcher;
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
    private final StoreProfessionalRepository professionals;
    private final br.com.fooddelivery.catalog.StoreOperatingHours operatingHours;

    public ReservationController(ReservationRepository reservations, ReservationSlotRepository slots,
            StoreRepository stores, AccountRepository accounts, CatalogItemRepository items,
            NotificationService notifications, PaymentRepository payments, StoreProfessionalRepository professionals,
            br.com.fooddelivery.catalog.StoreOperatingHours operatingHours) {
        this.reservations = reservations;
        this.slots = slots;
        this.stores = stores;
        this.accounts = accounts;
        this.items = items;
        this.notifications = notifications;
        this.payments = payments;
        this.professionals = professionals;
        this.operatingHours = operatingHours;
    }

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CUSTOMER')")
    @Transactional
    public CreateResponse create(@Valid @RequestBody Request request, Authentication auth) {
        UUID customerId = subject(auth);
        Account customer = accounts.findById(customerId).orElseThrow();
        if (!br.com.fooddelivery.identity.Cpf.isValid(customer.getCpf())) throw new IllegalArgumentException("Cadastre um CPF válido no seu perfil antes de reservar.");
        if (customer.getPhone() == null || customer.getPhone().isBlank()) {
            throw new IllegalArgumentException("Complete seu telefone antes de reservar.");
        }
        Store store = stores.findByIdForUpdate(request.storeId()).filter(Store::isAvailable)
                .orElseThrow();
        var item = items.findById(request.serviceItemId())
                .filter(i -> i.isActive() && i.isServiceItem() && i.getStore().getId().equals(request.storeId()))
                .orElseThrow(() -> new IllegalArgumentException("Serviço indisponível nesta loja."));
        if (request.startsAt().isBefore(Instant.now().plusSeconds(1800))) {
            throw new IllegalArgumentException("A reserva precisa ser feita com ao menos 30 minutos de antecedência.");
        }
        operatingHours.requireOpenAt(store, request.startsAt());
        Instant endsAt=request.startsAt().plusSeconds(item.getAppointmentDurationMinutes()*60L);
        operatingHours.requireOpenForInterval(store,request.startsAt(),endsAt);
        List<StoreProfessional> activeProfessionals=professionals.findByStoreIdAndActiveTrueOrderByNameAsc(store.getId());
        List<UUID> occupied=slots.occupiedResources(store.getId(),request.startsAt(),endsAt);
        StoreProfessional selected=null;
        if(!activeProfessionals.isEmpty()&&occupied.contains(ReservationSlot.STORE_RESOURCE_KEY))throw new IllegalStateException("Este horário já está reservado. Escolha outro horário.");
        if(request.professionalId()!=null){selected=activeProfessionals.stream().filter(p->p.getId().equals(request.professionalId())).findFirst().orElseThrow(()->new IllegalArgumentException("Escolha um profissional ativo desta loja."));if(occupied.contains(selected.getId()))throw new IllegalStateException("Este profissional já tem uma reserva neste intervalo. Escolha outro horário.");}
        else if(!activeProfessionals.isEmpty()){
            selected=activeProfessionals.stream().filter(p->!occupied.contains(p.getId())).findFirst().orElseThrow(()->new IllegalStateException("Não há profissional disponível nesse horário. Escolha outro horário."));
        } else if(!occupied.isEmpty()) throw new IllegalStateException("Horário indisponível neste intervalo.");
        boolean payAfterService = CityMatcher.allowPayAfterService(item.isPayAfterService(), customer.getCity(),
                customer.getState(), store.getCity(), store.getState());
        Reservation reservation = reservations.save(new Reservation(store, customer, request.startsAt(),
                item.getId(), item.getName(), item.getPriceCents(), request.customerNote(), payAfterService,selected,endsAt));
        slots.save(new ReservationSlot(store.getId(), reservation.getId(), reservation.getStartsAt(),endsAt,selected==null?null:selected.getId()));
        Payment payment = null;
        if (payAfterService) {
            notifications.send(customer, "Reserva confirmada", "Seu horário na " + store.getName()
                    + " foi confirmado. O pagamento de R$ " + String.format(java.util.Locale.forLanguageTag("pt-BR"), "%.2f", item.getPriceCents() / 100.0)
                    + " será feito diretamente à loja após o atendimento.", "success");
            notifications.send(store.getOwner(), "Nova reserva confirmada", "Uma reserva foi adicionada à agenda."
                    + " O cliente pagará diretamente à loja após o atendimento.", "info");
        } else {
            payment = payments.save(new Payment(reservation));
        }
        return new CreateResponse(view(reservation, payment), payment == null ? null : payment.getId(), payment == null ? null : payment.getStatus());
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

    @PostMapping("/reservations/{id}/cancellation-request")
    @PreAuthorize("hasRole('CUSTOMER')")
    @Transactional
    public ReservationView requestCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationNote request,Authentication auth){
        Reservation reservation=reservations.findById(id).orElseThrow();
        if(!reservation.getCustomer().getId().equals(subject(auth)))throw new org.springframework.security.access.AccessDeniedException("Reserva de outra conta.");
        Payment payment=payments.findByReservationId(id).orElse(null);
        if(reservation.getStatus()==ReservationStatus.PENDING_PAYMENT){
            if(payment!=null&&payment.getStatus()==PaymentStatus.PAID)throw new IllegalStateException("O pagamento foi aprovado. Atualize a tela e solicite o cancelamento à loja.");
            reservation.cancelUnpaid();slots.deleteByReservationId(id);if(payment!=null)payment.cancelPendingCheckout();
            notifications.sendLinked(reservation.getStore().getOwner(),"Reserva não paga cancelada","O cliente cancelou uma reserva ainda não paga; o horário foi liberado.","info",br.com.fooddelivery.notifications.NotificationTarget.RESERVATION,id);
            return view(reservations.save(reservation),payment);
        }
        boolean alreadyRequested="REQUESTED".equals(reservation.getCancellationRequestStatus());
        reservation.requestCancellation(request.note());
        if(!alreadyRequested)notifications.sendLinked(reservation.getStore().getOwner(),"Cancelamento de reserva solicitado","O cliente pediu o cancelamento de um horário. Revise a solicitação na aba Agenda.","info",br.com.fooddelivery.notifications.NotificationTarget.RESERVATION,id);
        return view(reservations.save(reservation),payment);
    }

    @PatchMapping("/merchant/reservations/{id}/cancellation-request")
    @PreAuthorize("hasRole('MERCHANT')")
    @Transactional
    public ReservationView decideCancellation(@PathVariable UUID id,@Valid @RequestBody CancellationDecision request,Authentication auth){
        Reservation reservation=reservations.findById(id).orElseThrow();
        if(!reservation.getStore().getOwner().getId().equals(subject(auth)))throw new org.springframework.security.access.AccessDeniedException("Reserva de outra loja.");
        reservation.decideCancellation(request.accepted());
        if(request.accepted()){reservation.change(ReservationStatus.CANCELLED);slots.deleteByReservationId(id);}
        Payment payment=payments.findByReservationId(id).orElse(null);
        if(request.accepted()&&payment!=null&&payment.getStatus()==PaymentStatus.PAID){payment.requestFullRefund();notifications.send(reservation.getCustomer(),"Cancelamento aceito","A loja aceitou o cancelamento. O estorno foi solicitado ao Mercado Pago e será atualizado após a confirmação do provedor.","info");}
        else if(request.accepted())notifications.send(reservation.getCustomer(),"Cancelamento aceito","A loja aceitou o cancelamento e liberou o horário.","success");
        else notifications.send(reservation.getCustomer(),"Cancelamento recusado","A loja recusou a solicitação. Fale com ela pelo chat se precisar de ajuda.","warn");
        return view(reservations.save(reservation),payment);
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
                        + change.status().descriptionPtBr() + "”.",
                "info");
        return view(reservations.save(reservation), payment);
    }

    private static ReservationView view(Reservation reservation, Payment payment) {
        Store store = reservation.getStore();
        StoreView storeView = new StoreView(store.getId(), store.getName(), store.getAddress(), store.getCategory(),store.getTimezone());
        return new ReservationView(reservation.getId(), storeView, reservation.getStartsAt(),
                reservation.getEndsAt(),reservation.getServiceName(), reservation.getAmountCents(), reservation.getCustomerNote(), reservation.getStatus(),
                reservation.getCreatedAt(), reservation.isPayAfterService(), payment == null ? null : payment.getId(),
                payment == null ? null : payment.getStatus(),reservation.getProfessional()==null?null:reservation.getProfessional().getId(),reservation.getProfessional()==null?null:reservation.getProfessional().getName(),reservation.getCancellationRequestedAt(),reservation.getCancellationRequestStatus(),reservation.getCancellationRequestNote());
    }

    private UUID subject(Authentication auth) {
        return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject());
    }

    public record Request(@NotNull UUID storeId, @NotNull UUID serviceItemId, @NotNull Instant startsAt, @jakarta.validation.constraints.Size(max=500) String customerNote,UUID professionalId) {}
    public record Change(@NotNull ReservationStatus status) {}
    public record CancellationNote(@jakarta.validation.constraints.Size(max=500) String note) {}
    public record CancellationDecision(@NotNull Boolean accepted) {}
    public record CreateResponse(ReservationView reservation, UUID paymentId, PaymentStatus paymentStatus) {}
    public record ReservationView(UUID id, StoreView store, Instant startsAt, Instant endsAt, String serviceName, long amountCents, String customerNote,
                                  ReservationStatus status, Instant createdAt, boolean payAfterService, UUID paymentId,
                                  PaymentStatus paymentStatus,UUID professionalId,String professionalName,Instant cancellationRequestedAt,String cancellationRequestStatus,String cancellationRequestNote) {}
    public record StoreView(UUID id, String name, String address, String category,String timezone) {}
}
