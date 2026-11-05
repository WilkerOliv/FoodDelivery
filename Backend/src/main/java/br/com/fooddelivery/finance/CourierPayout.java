package br.com.fooddelivery.finance;
import jakarta.persistence.*;
import java.util.UUID;
import java.time.Instant;
@Entity @Table(name="courier_payouts")
public class CourierPayout {
 public enum Status {REQUESTED,APPROVED,PAID,REJECTED,CANCELLED}
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(name="courier_id",nullable=false) private UUID courierId;
 @Column(name="amount_cents",nullable=false) private long amountCents;
 @Column(name="destination_cpf",nullable=false,length=11) private String destinationCpf;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private Status status=Status.REQUESTED;
 @Column(name="request_key",nullable=false) private UUID requestKey;
 @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt=Instant.now();
 @Column(name="decided_at") private Instant decidedAt;
 @Column(name="paid_at") private Instant paidAt;
 @Column(name="decided_by") private UUID decidedBy;
 @Column(name="external_reference",length=120,unique=true) private String externalReference;
 @Column(name="decision_note",length=300) private String decisionNote;
 @Version private long version;
 protected CourierPayout(){}
 public CourierPayout(UUID courierId,long amount,String cpf,UUID key){if(amount<1)throw new IllegalArgumentException("Valor inválido.");this.courierId=courierId;amountCents=amount;destinationCpf=cpf;requestKey=key;}
 public UUID getId(){return id;} public UUID getCourierId(){return courierId;} public long getAmountCents(){return amountCents;} public String getDestinationCpf(){return destinationCpf;} public Status getStatus(){return status;} public UUID getRequestKey(){return requestKey;} public Instant getCreatedAt(){return createdAt;} public Instant getDecidedAt(){return decidedAt;} public Instant getPaidAt(){return paidAt;} public String getExternalReference(){return externalReference;} public String getDecisionNote(){return decisionNote;}
 public void cancel(){if(status!=Status.REQUESTED)throw new IllegalStateException("Só é possível desistir de um repasse ainda não aprovado.");status=Status.CANCELLED;decidedAt=Instant.now();}
 public void decide(boolean approve,UUID actor,String note){if(status!=Status.REQUESTED)throw new IllegalStateException("Esta solicitação já foi decidida.");if(!approve&&(note==null||note.isBlank()))throw new IllegalArgumentException("Informe o motivo da recusa.");status=approve?Status.APPROVED:Status.REJECTED;decidedBy=actor;decidedAt=Instant.now();decisionNote=note;}
 public void recordPaid(UUID actor,String reference){if(status==Status.PAID&&reference.equals(externalReference))return;if(status!=Status.APPROVED)throw new IllegalStateException("Aprove a solicitação antes de registrar o pagamento externo.");if(reference==null||!reference.matches("[A-Za-z0-9._:/-]{6,120}"))throw new IllegalArgumentException("Informe a referência do comprovante externo, de 6 a 120 caracteres.");status=Status.PAID;decidedBy=actor;paidAt=Instant.now();externalReference=reference;}
}
