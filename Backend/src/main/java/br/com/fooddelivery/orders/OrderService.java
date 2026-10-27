package br.com.fooddelivery.orders;

import br.com.fooddelivery.catalog.*;
import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.payments.*;
import br.com.fooddelivery.notifications.NotificationService;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orders; private final AccountRepository accounts; private final StoreRepository stores;
    private final CatalogItemRepository items; private final PaymentRepository payments; private final NotificationService notifications;
    public OrderService(OrderRepository orders,AccountRepository accounts,StoreRepository stores,CatalogItemRepository items,PaymentRepository payments,NotificationService notifications){this.orders=orders;this.accounts=accounts;this.stores=stores;this.items=items;this.payments=payments;this.notifications=notifications;}
    @Transactional public Order create(UUID customerId,UUID storeId,FulfillmentType type,String address,String customerNote,List<RequestedLine> requested){
        Account customer=accounts.findById(customerId).orElseThrow();
        if(customer.getPhone()==null||customer.getPhone().isBlank()||(type==FulfillmentType.DELIVERY&&(address==null||address.isBlank())))throw new IllegalArgumentException("Complete telefone e endereço de entrega antes de pedir.");
        Store store=stores.findById(storeId).filter(s->s.isActive()&&s.isApproved()).orElseThrow(()->new NoSuchElementException("Loja indisponível."));
        if(requested==null||requested.isEmpty())throw new IllegalArgumentException("O pedido precisa ter ao menos um item.");
        long total=0; List<CatalogItem> selected=new ArrayList<>();
        for(RequestedLine line:requested){if(line.quantity()<1||line.quantity()>50)throw new IllegalArgumentException("Quantidade inválida.");CatalogItem item=items.findById(line.itemId()).orElseThrow();if(!item.getStore().getId().equals(storeId))throw new IllegalArgumentException("Item não pertence à loja.");if(item.isServiceItem())throw new IllegalArgumentException("Este serviço deve ser reservado, não adicionado a um pedido de entrega ou retirada.");item.reserve(line.quantity());selected.add(item);total=Math.addExact(total,Math.multiplyExact(item.getPriceCents(),line.quantity()));}
        long deliveryFee=type==FulfillmentType.DELIVERY?store.getDeliveryFeeCents():0;
        Order order=new Order(customer,store,type,address,total,deliveryFee,customerNote);for(int i=0;i<requested.size();i++){CatalogItem item=selected.get(i);order.addLine(item.getId(),item.getName(),requested.get(i).quantity(),item.getPriceCents());}
        Order saved=orders.save(order);payments.save(new Payment(saved));return saved;
    }
    @Transactional(readOnly=true) public List<Order> customerOrders(UUID id){return orders.findByCustomerIdOrderByCreatedAtDesc(id);}
    @Transactional public Order merchantTransition(UUID id,UUID merchantId,OrderStatus status){
        Order order=orders.findById(id).orElseThrow();
        if(!order.getStore().getOwner().getId().equals(merchantId))throw new org.springframework.security.access.AccessDeniedException("Pedido de outra loja.");
        order.transition(status);
        if(status==OrderStatus.CANCELLED||status==OrderStatus.REJECTED){
            for(Order.OrderLine line:order.getLines())items.findById(line.getItemId()).ifPresent(item->{item.restore(line.getQuantity());items.save(item);});
            payments.findByOrderId(id).filter(p->p.getStatus()==PaymentStatus.PAID).ifPresent(payment->{
                payment.requestFullRefund();
                notifications.send(order.getCustomer(),"Estorno solicitado","A loja encerrou o pedido. Solicitamos o estorno pelo Mercado Pago; o status será atualizado quando o provedor confirmar.","info");
            });
        }
        notifications.send(order.getCustomer(),"Pedido atualizado",order.getStore().getName()+": seu pedido agora está “"+status.name().toLowerCase(Locale.ROOT).replace('_',' ')+"”.","info");
        return order;
    }
    @Transactional public Order cancelUnpaidByCustomer(UUID id,UUID customerId){
        Order order=orders.findById(id).orElseThrow();
        if(!order.getCustomer().getId().equals(customerId))throw new org.springframework.security.access.AccessDeniedException("Pedido de outra conta.");
        Payment payment=payments.findByOrderId(id).orElse(null);
        if(order.getStatus()==OrderStatus.CANCELLED||order.getStatus()==OrderStatus.REJECTED)return order;
        if(order.getStatus()!=OrderStatus.AWAITING_PAYMENT)throw new IllegalStateException("O pedido já foi enviado à loja e não pode ser cancelado por este fluxo.");
        if(payment!=null&&payment.getStatus()==PaymentStatus.PAID)throw new IllegalStateException("O pagamento foi aprovado. Fale com a loja para solicitar o cancelamento e o estorno.");
        order.cancelUnpaid();
        for(Order.OrderLine line:order.getLines())items.findById(line.getItemId()).ifPresent(item->{item.restore(line.getQuantity());items.save(item);});
        if(payment!=null)payment.cancelPendingCheckout();
        notifications.send(order.getStore().getOwner(),"Pedido cancelado pelo cliente","Um pedido ainda não pago foi cancelado e o estoque foi liberado.","info");
        return order;
    }
    public record RequestedLine(UUID itemId,int quantity){}
}
