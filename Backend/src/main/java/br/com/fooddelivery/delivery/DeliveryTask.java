package br.com.fooddelivery.delivery;

import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.orders.Order;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @Table(name="delivery_tasks",uniqueConstraints=@UniqueConstraint(name="uk_delivery_order",columnNames="order_id"))
public class DeliveryTask {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @OneToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="order_id") @JsonIgnore private Order order;
 @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="courier_id") @JsonIgnore private Account courier;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private DeliveryStatus status=DeliveryStatus.OFFERED;
 @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
 @Column(name="courier_latitude") private Double courierLatitude;
 @Column(name="courier_longitude") private Double courierLongitude;
 @Column(name="location_updated_at") private Instant locationUpdatedAt;
 @Version private long version;
 protected DeliveryTask(){} public DeliveryTask(Order order){this.order=order;}
 public UUID getId(){return id;} public Order getOrder(){return order;} public Account getCourier(){return courier;} public DeliveryStatus getStatus(){return status;} public Instant getCreatedAt(){return createdAt;}
 public void assign(Account courier){if(status!=DeliveryStatus.OFFERED||this.courier!=null)throw new IllegalStateException("Entrega já atribuída.");this.courier=courier;this.status=DeliveryStatus.ASSIGNED;}
 public void advance(DeliveryStatus next){boolean valid=(status==DeliveryStatus.ASSIGNED&&next==DeliveryStatus.PICKED_UP)||(status==DeliveryStatus.PICKED_UP&&next==DeliveryStatus.IN_TRANSIT)||(status==DeliveryStatus.IN_TRANSIT&&next==DeliveryStatus.DELIVERED);if(!valid)throw new IllegalStateException("Transição de entrega inválida.");status=next;if(next==DeliveryStatus.DELIVERED){courierLatitude=null;courierLongitude=null;locationUpdatedAt=null;}}
 public void updateLocation(double latitude,double longitude){if(status!=DeliveryStatus.IN_TRANSIT)throw new IllegalStateException("A localização só é compartilhada durante o trajeto.");courierLatitude=latitude;courierLongitude=longitude;locationUpdatedAt=Instant.now();}
 public Double getCourierLatitude(){return courierLatitude;} public Double getCourierLongitude(){return courierLongitude;} public Instant getLocationUpdatedAt(){return locationUpdatedAt;}
}
