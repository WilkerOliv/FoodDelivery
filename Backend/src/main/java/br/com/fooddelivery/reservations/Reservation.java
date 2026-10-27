package br.com.fooddelivery.reservations;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.identity.Account;
import jakarta.persistence.*;
import java.time.*;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @Table(name="reservations", uniqueConstraints=@UniqueConstraint(name="uk_reservation_slot",columnNames={"store_id","starts_at"}))
public class Reservation {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="store_id") private Store store;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="customer_id") @JsonIgnore private Account customer;
    @Column(name="starts_at",nullable=false) private Instant startsAt;
    @Column(name="service_name",nullable=false,length=140) private String serviceName;
    @Column(name="catalog_item_id",nullable=false) private UUID catalogItemId;
    @Column(name="amount_cents",nullable=false) private long amountCents;
    @Column(name="customer_note",length=500) private String customerNote;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private ReservationStatus status=ReservationStatus.PENDING_PAYMENT;
    @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
    @Version private long version;
    protected Reservation(){}
    public Reservation(Store store,Account customer,Instant startsAt,UUID catalogItemId,String serviceName,long amount,String customerNote){this.store=store;this.customer=customer;this.startsAt=startsAt;this.catalogItemId=catalogItemId;this.serviceName=serviceName;this.amountCents=amount;this.customerNote=customerNote==null||customerNote.isBlank()?null:customerNote.trim();if(this.customerNote!=null&&this.customerNote.length()>500)throw new IllegalArgumentException("O comentário pode ter até 500 caracteres.");}
    public UUID getId(){return id;} public Store getStore(){return store;} public Account getCustomer(){return customer;} public Instant getStartsAt(){return startsAt;} public Instant getCreatedAt(){return createdAt;} public String getServiceName(){return serviceName;} public UUID getCatalogItemId(){return catalogItemId;} public long getAmountCents(){return amountCents;} public ReservationStatus getStatus(){return status;}
    public String getCustomerNote(){return customerNote;}
    public void confirmPayment(){if(status!=ReservationStatus.PENDING_PAYMENT)throw new IllegalStateException("Reserva não aguarda pagamento.");status=ReservationStatus.CONFIRMED;}
    public void cancelUnpaid(){if(status!=ReservationStatus.PENDING_PAYMENT)throw new IllegalStateException("Somente reservas que aguardam pagamento podem ser canceladas por este fluxo.");status=ReservationStatus.CANCELLED;}
    public void expireUnpaid(){if(status==ReservationStatus.PENDING_PAYMENT)status=ReservationStatus.CANCELLED;}
    public void change(ReservationStatus next){boolean valid=(status==ReservationStatus.CONFIRMED&&(next==ReservationStatus.IN_SERVICE||next==ReservationStatus.CANCELLED||next==ReservationStatus.NO_SHOW))||(status==ReservationStatus.IN_SERVICE&&next==ReservationStatus.COMPLETED);if(!valid)throw new IllegalStateException("Transição de reserva inválida.");status=next;}
}
