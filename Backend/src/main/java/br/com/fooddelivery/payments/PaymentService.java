package br.com.fooddelivery.payments;

import br.com.fooddelivery.orders.Order;
import br.com.fooddelivery.orders.OrderRepository;
import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.reservations.Reservation;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service public class PaymentService {
    private final PaymentRepository payments; private final OrderRepository orders;
    private final NotificationService notifications;
    public PaymentService(PaymentRepository payments,OrderRepository orders,NotificationService notifications){this.payments=payments;this.orders=orders;this.notifications=notifications;}
    @Transactional public Payment confirmForDevelopment(UUID paymentId){return confirmProvider(paymentId,"local-simulator-"+paymentId);}
    @Transactional public Payment confirmProvider(UUID paymentId,String providerReference){
        Payment payment=payments.findById(paymentId).orElseThrow();
        if(payment.getStatus()==PaymentStatus.PAID||payment.getStatus()==PaymentStatus.REFUND_PENDING||payment.getStatus()==PaymentStatus.REFUNDED||payment.getStatus()==PaymentStatus.REFUND_REVIEW)return payment;
        payment.confirm(providerReference);
        if(payment.getOrder()!=null){
            Order order=orders.findById(payment.getOrder().getId()).orElseThrow();
            if(order.getStatus()==br.com.fooddelivery.orders.OrderStatus.AWAITING_PAYMENT){order.paid();notifications.send(order.getCustomer(),"Pagamento aprovado","Seu pedido para "+order.getStore().getName()+" foi enviado à loja.","success");notifications.send(order.getStore().getOwner(),"Novo pedido pago","Um novo pedido aguarda confirmação.","info");}
            else if(order.getStatus()==br.com.fooddelivery.orders.OrderStatus.CANCELLED||order.getStatus()==br.com.fooddelivery.orders.OrderStatus.REJECTED){payment.requestFullRefund();notifications.send(order.getCustomer(),"Pagamento aprovado após o cancelamento","O pedido não está mais ativo. Já solicitamos o estorno e avisaremos quando o Mercado Pago atualizar o status.","info");}
        }else{
            Reservation reservation=payment.getReservation();if(reservation==null)throw new IllegalStateException("Pagamento sem pedido ou reserva associados.");
            if(reservation.getStatus()==br.com.fooddelivery.reservations.ReservationStatus.PENDING_PAYMENT){reservation.confirmPayment();notifications.send(reservation.getCustomer(),"Reserva confirmada","Seu horário na "+reservation.getStore().getName()+" foi confirmado.","success");notifications.send(reservation.getStore().getOwner(),"Nova reserva confirmada","Uma reserva paga entrou na agenda da loja.","info");}
            else if(reservation.getStatus()==br.com.fooddelivery.reservations.ReservationStatus.CANCELLED){payment.requestFullRefund();notifications.send(reservation.getCustomer(),"Pagamento aprovado após liberar o horário","O horário não está mais ativo. Já solicitamos o estorno e avisaremos quando o Mercado Pago atualizar o status.","info");}
        }
        return payment;
    }
    @Transactional public Payment failProvider(UUID paymentId,String providerReference){Payment payment=payments.findById(paymentId).orElseThrow();if(payment.getStatus()==PaymentStatus.PAID)return payment;payment.fail(providerReference);return payment;}
}
