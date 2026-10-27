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
 private final DeliveryRepository deliveries;private final OrderRepository orders;private final AccountRepository accounts;
 public DeliveryController(DeliveryRepository d,OrderRepository o,AccountRepository a){deliveries=d;orders=o;accounts=a;}
 @PostMapping("/merchant/orders/{orderId}/delivery") @ResponseStatus(HttpStatus.CREATED) @PreAuthorize("hasRole('MERCHANT')")
 public DeliveryTask offer(@PathVariable UUID orderId,Authentication a){Order o=orders.findById(orderId).orElseThrow();if(!o.getStore().getOwner().getId().equals(subject(a)))throw new org.springframework.security.access.AccessDeniedException("Pedido de outra loja.");if(o.getFulfillmentType()!=FulfillmentType.DELIVERY||o.getStatus()!=OrderStatus.READY)throw new IllegalStateException("Pedido precisa estar pronto para delivery.");return deliveries.save(new DeliveryTask(o));}
 @GetMapping("/courier/offers") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional(readOnly=true) public List<OfferView> offers(){return deliveries.findByStatusOrderByCreatedAt(DeliveryStatus.OFFERED).stream().map(d->new OfferView(d.getId(),d.getOrder().getId(),d.getOrder().getStore().getName(),d.getOrder().getTotalCents())).toList();}
 @GetMapping("/courier/deliveries") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional(readOnly=true) public List<AssignedView> mine(Authentication a){return deliveries.findByCourierIdOrderByCreatedAtDesc(subject(a)).stream().map(this::view).toList();}
 @PostMapping("/courier/offers/{id}/accept") @PreAuthorize("hasRole('COURIER')") public DeliveryTask accept(@PathVariable UUID id,Authentication a){var courier=accounts.findById(subject(a)).orElseThrow();if(deliveries.claim(id,courier)!=1)throw new IllegalStateException("Oferta já aceita ou encerrada.");return deliveries.findById(id).orElseThrow();}
 @PatchMapping("/courier/deliveries/{id}") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public AssignedView progress(@PathVariable UUID id,@Valid @RequestBody Update u,Authentication a){var d=deliveries.findById(id).orElseThrow();if(d.getCourier()==null||!d.getCourier().getId().equals(subject(a)))throw new org.springframework.security.access.AccessDeniedException("Entrega não atribuída a esta conta.");d.advance(u.status());return view(deliveries.save(d));}
 @PatchMapping("/courier/deliveries/{id}/location") @PreAuthorize("hasRole('COURIER')") @org.springframework.transaction.annotation.Transactional public void location(@PathVariable UUID id,@Valid @RequestBody LocationUpdate u,Authentication a){var d=deliveries.findById(id).orElseThrow();if(d.getCourier()==null||!d.getCourier().getId().equals(subject(a)))throw new org.springframework.security.access.AccessDeniedException("Entrega não atribuída a esta conta.");d.updateLocation(u.latitude(),u.longitude());deliveries.save(d);}
 private UUID subject(Authentication a){return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());}
 public record Update(@NotNull DeliveryStatus status){}
 public record LocationUpdate(@jakarta.validation.constraints.NotNull @jakarta.validation.constraints.DecimalMin("-90.0") @jakarta.validation.constraints.DecimalMax("90.0") Double latitude,@jakarta.validation.constraints.NotNull @jakarta.validation.constraints.DecimalMin("-180.0") @jakarta.validation.constraints.DecimalMax("180.0") Double longitude){}
 public record OfferView(UUID deliveryId,UUID orderId,String storeName,long orderTotalCents){}
 private AssignedView view(DeliveryTask d){return new AssignedView(d.getId(),d.getStatus(),d.getOrder().getId(),d.getOrder().getStore().getName(),d.getOrder().getDeliveryAddress(),d.getOrder().getTotalCents());}
 public record AssignedView(UUID deliveryId,DeliveryStatus status,UUID orderId,String storeName,String deliveryAddress,long orderTotalCents){}
}
