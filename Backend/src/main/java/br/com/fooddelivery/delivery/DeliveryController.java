package br.com.fooddelivery.delivery;

import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.orders.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1") public class DeliveryController {
 private final DeliveryRepository deliveries;private final OrderRepository orders;private final AccountRepository accounts;private final br.com.fooddelivery.finance.CourierFinanceService finance;
 public DeliveryController(DeliveryRepository d,OrderRepository o,AccountRepository a,br.com.fooddelivery.finance.CourierFinanceService finance){deliveries=d;orders=o;accounts=a;this.finance=finance;}
 @PostMapping("/merchant/orders/{orderId}/delivery") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')")
 @org.springframework.transaction.annotation.Transactional
 public DeliveryTask offer(@PathVariable UUID orderId,Authentication a){Order o=orders.findByIdForUpdate(orderId).orElseThrow();if(!o.getStore().getOwner().getId().equals(subject(a)))throw new org.springframework.security.access.AccessDeniedException("Pedido de outra loja.");if(o.getFulfillmentType()!=FulfillmentType.DELIVERY||o.getStatus()!=OrderStatus.READY||"REQUESTED".equals(o.getCancellationRequestStatus()))throw new IllegalStateException("Pedido precisa estar pronto e sem cancelamento pendente para delivery.");return deliveries.findByOrderId(orderId).orElseGet(()->{var task=new DeliveryTask(o);task.setCourierFeeOnce(finance.requiredFee());return deliveries.save(task);});}
 @GetMapping("/courier/offers") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional(readOnly=true) public List<OfferView> offers(){return deliveries.findByStatusOrderByCreatedAt(DeliveryStatus.OFFERED).stream().filter(d->d.getOrder().getStatus()==OrderStatus.READY&&!"REQUESTED".equals(d.getOrder().getCancellationRequestStatus())).map(d->new OfferView(d.getId(),d.getOrder().getId(),d.getOrder().getStore().getName(),d.getOrder().getTotalCents(),d.getCourierFeeCents())).toList();}
 @GetMapping("/courier/deliveries") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional(readOnly=true) public List<AssignedView> mine(Authentication a){return deliveries.findByCourierIdOrderByCreatedAtDesc(subject(a)).stream().map(this::view).toList();}
 @PostMapping("/courier/offers/{id}/accept") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional
 public DeliveryTask accept(@PathVariable UUID id,Authentication a){var task=deliveries.findById(id).orElseThrow();Order order=orders.findByIdForUpdate(task.getOrder().getId()).orElseThrow();requireReady(order);if(task.getCourierFeeCents()==null){task.setCourierFeeOnce(finance.requiredFee());deliveries.saveAndFlush(task);}var courier=accounts.findById(subject(a)).orElseThrow();if(deliveries.claim(id,courier)!=1)throw new IllegalStateException("Oferta já aceita ou encerrada.");return deliveries.findById(id).orElseThrow();}
 @PatchMapping("/courier/deliveries/{id}") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional
 public AssignedView progress(@PathVariable UUID id,@Valid @RequestBody Update u,Authentication a){var d=deliveries.findById(id).orElseThrow();if(d.getCourier()==null||!d.getCourier().getId().equals(subject(a)))throw new org.springframework.security.access.AccessDeniedException("Entrega não atribuída a esta conta.");Order order=orders.findByIdForUpdate(d.getOrder().getId()).orElseThrow();requireReady(order);d.advance(u.status());if(u.status()==DeliveryStatus.DELIVERED){order.transition(OrderStatus.COMPLETED);finance.creditDelivered(d);}return view(deliveries.save(d));}
 @PatchMapping("/courier/deliveries/{id}/location") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public void location(@PathVariable UUID id,@Valid @RequestBody LocationUpdate u,Authentication a){var d=deliveries.findById(id).orElseThrow();if(d.getCourier()==null||!d.getCourier().getId().equals(subject(a)))throw new org.springframework.security.access.AccessDeniedException("Entrega não atribuída a esta conta.");d.updateLocation(u.latitude(),u.longitude());deliveries.save(d);}
 private UUID subject(Authentication a){return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());}
 public record Update(@NotNull DeliveryStatus status){}
 public record LocationUpdate(@jakarta.validation.constraints.NotNull @jakarta.validation.constraints.DecimalMin("-90.0") @jakarta.validation.constraints.DecimalMax("90.0") Double latitude,@jakarta.validation.constraints.NotNull @jakarta.validation.constraints.DecimalMin("-180.0") @jakarta.validation.constraints.DecimalMax("180.0") Double longitude){}
 public record OfferView(UUID deliveryId,UUID orderId,String storeName,long orderTotalCents,Long courierFeeCents){}
 private void requireReady(Order order){if(order.getStatus()!=OrderStatus.READY||"REQUESTED".equals(order.getCancellationRequestStatus()))throw new IllegalStateException("O pedido não está disponível para entrega ou tem cancelamento pendente.");}
 private AssignedView view(DeliveryTask d){boolean terminal=d.getStatus()==DeliveryStatus.DELIVERED||d.getStatus()==DeliveryStatus.CANCELLED;String note=terminal?null:d.getOrder().getCustomerNote();return new AssignedView(d.getId(),d.getStatus(),d.getOrder().getId(),d.getOrder().getStore().getName(),terminal?null:d.getOrder().getDeliveryAddress(),d.getOrder().getTotalCents(),note,d.getCourierFeeCents());}
 public record AssignedView(UUID deliveryId,DeliveryStatus status,UUID orderId,String storeName,String deliveryAddress,long orderTotalCents,String customerNote,Long courierFeeCents){}
}
