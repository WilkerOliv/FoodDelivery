package br.com.fooddelivery.orders;

import br.com.fooddelivery.catalog.*;
import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.payments.*;
import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.notifications.NotificationTarget;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final br.com.fooddelivery.delivery.DeliveryRepository deliveries;
    private final OrderRepository orders; private final AccountRepository accounts; private final StoreRepository stores;
    private final CatalogItemRepository items; private final PaymentRepository payments; private final NotificationService notifications;
    private final StoreOperatingHours operatingHours;
    public OrderService(OrderRepository orders,AccountRepository accounts,StoreRepository stores,CatalogItemRepository items,PaymentRepository payments,NotificationService notifications,StoreOperatingHours operatingHours,br.com.fooddelivery.delivery.DeliveryRepository deliveries){this.orders=orders;this.accounts=accounts;this.stores=stores;this.items=items;this.payments=payments;this.notifications=notifications;this.operatingHours=operatingHours;this.deliveries=deliveries;}
    @Transactional public Order create(UUID customerId,UUID storeId,FulfillmentType type,String address,String customerNote,List<RequestedLine> requested){
        Account customer=accounts.findById(customerId).orElseThrow();
        if(!Cpf.isValid(customer.getCpf()))throw new IllegalArgumentException("Cadastre um CPF válido no seu perfil antes de comprar.");
        if(customer.getPhone()==null||customer.getPhone().isBlank()||(type==FulfillmentType.DELIVERY&&(address==null||address.isBlank())))throw new IllegalArgumentException("Complete telefone e endereço de entrega antes de pedir.");
        Store store=stores.findByIdForUpdate(storeId).filter(Store::isAvailable).orElseThrow(()->new NoSuchElementException("Loja indisponível."));
        store.requireFulfillment(type);
        operatingHours.requireOpenNow(store);
        if(requested==null||requested.isEmpty())throw new IllegalArgumentException("O pedido precisa ter ao menos um item.");
        long total=0; List<CatalogItem> selected=new ArrayList<>();
        for(RequestedLine line:requested){if(line.quantity()<1||line.quantity()>50)throw new IllegalArgumentException("Quantidade inválida.");CatalogItem item=items.findById(line.itemId()).orElseThrow();if(!item.getStore().getId().equals(storeId))throw new IllegalArgumentException("Item não pertence à loja.");if(item.isServiceItem())throw new IllegalArgumentException("Este serviço deve ser reservado, não adicionado a um pedido de entrega ou retirada.");item.reserve(line.quantity());selected.add(item);total=Math.addExact(total,Math.multiplyExact(item.getPriceCents(),line.quantity()));}
        long deliveryFee=type==FulfillmentType.DELIVERY?store.getDeliveryFeeCents():0;
        Order order=new Order(customer,store,type,address,total,deliveryFee,customerNote);for(int i=0;i<requested.size();i++){CatalogItem item=selected.get(i);order.addLine(item.getId(),item.getName(),requested.get(i).quantity(),item.getPriceCents());}
        Order saved=orders.save(order);payments.save(new Payment(saved));return saved;
    }
    @Transactional(readOnly=true) public List<Order> customerOrders(UUID id){return orders.findByCustomerIdOrderByCreatedAtDesc(id);}
    @Transactional public Order merchantTransition(UUID id,UUID merchantId,OrderStatus status){
        Order order=orders.findByIdForUpdate(id).orElseThrow();
        if(!order.getStore().getOwner().getId().equals(merchantId))throw new org.springframework.security.access.AccessDeniedException("Pedido de outra loja.");
        if(status==OrderStatus.CANCELLED||status==OrderStatus.REJECTED)cancelDelivery(order.getId());
        if(status==OrderStatus.COMPLETED&&order.getFulfillmentType()==FulfillmentType.DELIVERY)throw new IllegalStateException("A conclusão de um delivery é confirmada pelo entregador ao marcar a entrega.");
        order.transition(status);
        if(status==OrderStatus.CANCELLED||status==OrderStatus.REJECTED){
            for(Order.OrderLine line:order.getLines())items.findById(line.getItemId()).ifPresent(item->{item.restore(line.getQuantity());items.save(item);});
            payments.findByOrderId(id).filter(p->p.getStatus()==PaymentStatus.PAID).ifPresent(payment->{
                payment.requestFullRefund();
                notifications.send(order.getCustomer(),"Estorno solicitado","A loja encerrou o pedido. Solicitamos o estorno pelo Mercado Pago; o status será atualizado quando o provedor confirmar.","info");
            });
        }
        notifications.sendLinked(order.getCustomer(),"Pedido atualizado",order.getStore().getName()+": seu pedido agora está “"+status.descriptionPtBr()+"”.","info",NotificationTarget.ORDER,order.getId());
        return order;
    }
    @Transactional public Order cancelUnpaidByCustomer(UUID id,UUID customerId){
        Order order=orders.findByIdForUpdate(id).orElseThrow();
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
    @Transactional public Order requestCustomerCancellation(UUID id,UUID customerId,String note){
        Order order=orders.findByIdForUpdate(id).orElseThrow();
        if(!order.getCustomer().getId().equals(customerId))throw new org.springframework.security.access.AccessDeniedException("Pedido de outra conta.");
        if(order.getStatus()==OrderStatus.AWAITING_PAYMENT)return cancelUnpaidByCustomer(id,customerId);
        Payment payment=payments.findByOrderId(id).orElseThrow(()->new IllegalStateException("Não encontramos o pagamento deste pedido."));
        if(payment.getStatus()!=PaymentStatus.PAID)throw new IllegalStateException("Somente pedidos com pagamento aprovado podem solicitar cancelamento por este fluxo.");
        deliveries.findByOrderId(id).ifPresent(br.com.fooddelivery.delivery.DeliveryTask::requireCancellable);
        boolean alreadyRequested="REQUESTED".equals(order.getCancellationRequestStatus());
        order.requestCancellation(note);
        if(!alreadyRequested)notifications.sendLinked(order.getStore().getOwner(),"Pedido com cancelamento solicitado","O cliente pediu o cancelamento do pedido "+order.getId().toString().substring(0,8)+". Revise a solicitação na aba Pedidos.","info",NotificationTarget.ORDER,order.getId());
        return orders.save(order);
    }
    @Transactional public Order decideCustomerCancellation(UUID id,UUID merchantId,boolean accepted){
        Order order=orders.findByIdForUpdate(id).orElseThrow();
        if(!order.getStore().getOwner().getId().equals(merchantId))throw new org.springframework.security.access.AccessDeniedException("Pedido de outra loja.");
        order.decideCancellation(accepted);
        if(accepted){
            cancelDelivery(id);
            order.transition(OrderStatus.CANCELLED);
            for(Order.OrderLine line:order.getLines())items.findById(line.getItemId()).ifPresent(item->{item.restore(line.getQuantity());items.save(item);});
            payments.findByOrderId(id).filter(p->p.getStatus()==PaymentStatus.PAID).ifPresent(Payment::requestFullRefund);
        }
        notifications.sendLinked(order.getCustomer(),accepted?"Cancelamento aceito":"Cancelamento recusado",accepted?"A loja aceitou o cancelamento. O estorno foi solicitado ao Mercado Pago e será atualizado após a confirmação do provedor.":"A loja recusou a solicitação de cancelamento. Fale com ela pelo chat se precisar de ajuda.",accepted?"info":"warn",NotificationTarget.ORDER,order.getId());
        return orders.save(order);
    }
    public record RequestedLine(UUID itemId,int quantity){}
    private void cancelDelivery(UUID orderId){deliveries.findByOrderId(orderId).ifPresent(task->{task.cancel();deliveries.save(task);});}
}
