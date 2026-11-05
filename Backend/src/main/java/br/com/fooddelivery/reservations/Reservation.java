package br.com.fooddelivery.reservations;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.catalog.StoreProfessional;
import jakarta.persistence.*;
import java.time.*;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @Table(name="reservations")
public class Reservation {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="store_id") private Store store;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="customer_id") @JsonIgnore private Account customer;
    @Column(name="starts_at",nullable=false) private Instant startsAt;
    @Column(name="ends_at",nullable=false) private Instant endsAt;
    @Column(name="service_name",nullable=false,length=140) private String serviceName;
    @Column(name="catalog_item_id",nullable=false) private UUID catalogItemId;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="professional_id") private StoreProfessional professional;
    @Column(name="amount_cents",nullable=false) private long amountCents;
    @Column(name="customer_note",length=500) private String customerNote;
    @Column(name="pay_after_service", nullable=false) private boolean payAfterService;
    @Column(name="operating_hours_override",nullable=false) private boolean operatingHoursOverride;
    @Column(name="operating_hours_notice",length=500) private String operatingHoursNotice;
    @Column(name="operating_hours_changed_at") private Instant operatingHoursChangedAt;
    @Column(name="cancellation_requested_at") private Instant cancellationRequestedAt;
    @Column(name="cancellation_request_status",nullable=false,length=16) private String cancellationRequestStatus="NONE";
    @Column(name="cancellation_request_note",length=500) private String cancellationRequestNote;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private ReservationStatus status=ReservationStatus.PENDING_PAYMENT;
    @Column(nullable=false,updatable=false) private Instant createdAt=Instant.now();
    @Version private long version;
    protected Reservation(){}
    public Reservation(Store store,Account customer,Instant startsAt,UUID catalogItemId,String serviceName,long amount,String customerNote){this(store,customer,startsAt,catalogItemId,serviceName,amount,customerNote,false,null,startsAt.plusSeconds(1800));}
    public Reservation(Store store,Account customer,Instant startsAt,UUID catalogItemId,String serviceName,long amount,String customerNote,boolean payAfterService){this(store,customer,startsAt,catalogItemId,serviceName,amount,customerNote,payAfterService,null,startsAt.plusSeconds(1800));}
    public Reservation(Store store,Account customer,Instant startsAt,UUID catalogItemId,String serviceName,long amount,String customerNote,boolean payAfterService,StoreProfessional professional,Instant endsAt){this.store=store;this.customer=customer;this.startsAt=startsAt;this.endsAt=endsAt;this.catalogItemId=catalogItemId;this.professional=professional;this.serviceName=serviceName;this.amountCents=amount;this.payAfterService=payAfterService;this.status=payAfterService?ReservationStatus.CONFIRMED:ReservationStatus.PENDING_PAYMENT;this.customerNote=customerNote==null||customerNote.isBlank()?null:customerNote.trim();if(this.customerNote!=null&&this.customerNote.length()>500)throw new IllegalArgumentException("O comentário pode ter até 500 caracteres.");if(endsAt==null||!endsAt.isAfter(startsAt))throw new IllegalArgumentException("O fim do atendimento deve ser posterior ao início.");}
    public UUID getId(){return id;} public Store getStore(){return store;} public Account getCustomer(){return customer;} public Instant getStartsAt(){return startsAt;} public Instant getEndsAt(){return endsAt;} public Instant getCreatedAt(){return createdAt;} public String getServiceName(){return serviceName;} public UUID getCatalogItemId(){return catalogItemId;} public StoreProfessional getProfessional(){return professional;} public long getAmountCents(){return amountCents;} public ReservationStatus getStatus(){return status;}
    public String getCustomerNote(){return customerNote;}
    public Instant getCancellationRequestedAt(){return cancellationRequestedAt;} public String getCancellationRequestStatus(){return cancellationRequestStatus;} public String getCancellationRequestNote(){return cancellationRequestNote;}
    public void requestCancellation(String note){if(status!=ReservationStatus.CONFIRMED)throw new IllegalStateException("Esta reserva não pode receber uma solicitação de cancelamento agora.");if("REQUESTED".equals(cancellationRequestStatus))return;cancellationRequestedAt=Instant.now();cancellationRequestStatus="REQUESTED";cancellationRequestNote=note==null||note.isBlank()?null:note.trim();if(cancellationRequestNote!=null&&cancellationRequestNote.length()>500)throw new IllegalArgumentException("O motivo pode ter até 500 caracteres.");}
    public void decideCancellation(boolean accepted){if(!"REQUESTED".equals(cancellationRequestStatus))throw new IllegalStateException("Não há solicitação de cancelamento pendente.");cancellationRequestStatus=accepted?"ACCEPTED":"DECLINED";}
    public boolean isPayAfterService(){return payAfterService;}
    public boolean isOperatingHoursOverride(){return operatingHoursOverride;}
    public String getOperatingHoursNotice(){return operatingHoursNotice;}
    public Instant getOperatingHoursChangedAt(){return operatingHoursChangedAt;}
    public void keepAfterHoursChange(){operatingHoursOverride=true;operatingHoursNotice="A loja alterou o expediente, mas manteve este atendimento no horário combinado. Fale com a loja se precisar confirmar detalhes.";operatingHoursChangedAt=Instant.now();}
    public void clearHoursOverride(){operatingHoursOverride=false;}
    public void cancelForHoursChange(){
        if(status!=ReservationStatus.PENDING_PAYMENT&&status!=ReservationStatus.CONFIRMED)throw new IllegalStateException("Somente reservas ainda não iniciadas podem ser canceladas pela mudança de expediente.");
        status=ReservationStatus.CANCELLED;operatingHoursOverride=false;operatingHoursNotice="A loja cancelou este atendimento por mudança no expediente. Se houve pagamento online, consulte o andamento do estorno.";operatingHoursChangedAt=Instant.now();
        if("REQUESTED".equals(cancellationRequestStatus))cancellationRequestStatus="ACCEPTED";
    }
    public void confirmPayment(){if(status!=ReservationStatus.PENDING_PAYMENT)throw new IllegalStateException("Reserva não aguarda pagamento.");status=ReservationStatus.CONFIRMED;}
    public void cancelUnpaid(){if(status!=ReservationStatus.PENDING_PAYMENT)throw new IllegalStateException("Somente reservas que aguardam pagamento podem ser canceladas por este fluxo.");status=ReservationStatus.CANCELLED;}
    public void expireUnpaid(){if(status==ReservationStatus.PENDING_PAYMENT)status=ReservationStatus.CANCELLED;}
    public void change(ReservationStatus next){if("REQUESTED".equals(cancellationRequestStatus)&&next!=ReservationStatus.CANCELLED)throw new IllegalStateException("Responda à solicitação de cancelamento antes de avançar o atendimento.");boolean valid=(status==ReservationStatus.CONFIRMED&&(next==ReservationStatus.IN_SERVICE||next==ReservationStatus.CANCELLED||next==ReservationStatus.NO_SHOW))||(status==ReservationStatus.IN_SERVICE&&(next==ReservationStatus.COMPLETED||next==ReservationStatus.CANCELLED));if(!valid)throw new IllegalStateException("Transição de reserva inválida.");status=next;}
}
