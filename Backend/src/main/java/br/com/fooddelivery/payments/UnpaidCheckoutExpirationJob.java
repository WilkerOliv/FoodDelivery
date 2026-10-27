package br.com.fooddelivery.payments;

import br.com.fooddelivery.catalog.CatalogItemRepository;
import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.orders.OrderRepository;
import br.com.fooddelivery.orders.OrderStatus;
import br.com.fooddelivery.reservations.ReservationRepository;
import br.com.fooddelivery.reservations.ReservationSlotRepository;
import br.com.fooddelivery.reservations.ReservationStatus;
import java.time.Instant;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UnpaidCheckoutExpirationJob {
    private static final long PAYMENT_WINDOW_SECONDS=15*60;
    private final OrderRepository orders;
    private final ReservationRepository reservations;
    private final ReservationSlotRepository slots;
    private final PaymentRepository payments;
    private final CatalogItemRepository items;
    private final NotificationService notifications;

    public UnpaidCheckoutExpirationJob(OrderRepository orders,ReservationRepository reservations,ReservationSlotRepository slots,
            PaymentRepository payments,CatalogItemRepository items,NotificationService notifications){
        this.orders=orders;this.reservations=reservations;this.slots=slots;this.payments=payments;this.items=items;this.notifications=notifications;
    }

    @Scheduled(fixedDelay=60000)
    @Transactional
    public void expireUnpaidOperations(){
        Instant cutoff=Instant.now().minusSeconds(PAYMENT_WINDOW_SECONDS);
        for(var order:orders.findByStatusAndCreatedAtBefore(OrderStatus.AWAITING_PAYMENT,cutoff)){
            var payment=payments.findByOrderId(order.getId()).orElse(null);
            if(payment!=null&&payment.getStatus()==PaymentStatus.PAID)continue;
            for(var line:order.getLines())items.findById(line.getItemId()).ifPresent(item->{item.restore(line.getQuantity());items.save(item);});
            order.cancelUnpaid();
            if(payment!=null)payment.fail("checkout-expired");
            notifications.send(order.getCustomer(),"Pedido expirado","O tempo para pagamento terminou. O estoque foi liberado; se quiser, faça o pedido novamente.","info");
            notifications.send(order.getStore().getOwner(),"Pagamento não concluído","Um pedido expirou sem pagamento e foi cancelado automaticamente.","info");
        }
        for(var reservation:reservations.findByStatusAndCreatedAtBefore(ReservationStatus.PENDING_PAYMENT,cutoff)){
            var payment=payments.findByReservationId(reservation.getId()).orElse(null);
            if(payment!=null&&payment.getStatus()==PaymentStatus.PAID)continue;
            reservation.expireUnpaid();slots.deleteByReservationId(reservation.getId());
            if(payment!=null)payment.fail("checkout-expired");
            notifications.send(reservation.getCustomer(),"Horário liberado","O tempo para pagamento terminou. Escolha outro horário quando estiver pronto.","info");
            notifications.send(reservation.getStore().getOwner(),"Reserva expirada","Uma reserva sem pagamento foi removida da agenda.","info");
        }
    }
}
