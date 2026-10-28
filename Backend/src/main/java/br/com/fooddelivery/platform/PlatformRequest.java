package br.com.fooddelivery.platform;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRole;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="platform_requests")
public class PlatformRequest {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="account_id") private Account account;
    @Column(name="request_type",nullable=false,length=16) private String requestType;
    @Column(nullable=false,length=16) private String status="PENDING";
    @Enumerated(EnumType.STRING) @Column(name="requested_role",length=20) private AccountRole requestedRole;
    @Column(length=100) private String city;
    @Column(length=2) private String state;
    @Column(name="postal_code",length=9) private String postalCode;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="store_id") private Store store;
    @Column(name="review_note",length=500) private String reviewNote;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="reviewed_by") private Account reviewedBy;
    @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt=Instant.now();
    @Column(name="reviewed_at") private Instant reviewedAt;
    protected PlatformRequest(){}
    private PlatformRequest(Account account,String type,AccountRole role,String city,String state,String postalCode,Store store){this.account=account;this.requestType=type;this.requestedRole=role;this.city=city;this.state=state;this.postalCode=postalCode;this.store=store;}
    public static PlatformRequest role(Account a,AccountRole r){return new PlatformRequest(a,"ROLE",r,null,null,null,null);}
    public static PlatformRequest city(Account a,String c,String s,String p){return new PlatformRequest(a,"CITY",null,c,s,p,null);}
    public static PlatformRequest store(Account a,Store store){return new PlatformRequest(a,"STORE",null,store.getCity(),store.getState(),null,store);}
    public void review(boolean approve,Account reviewer,String note){if(!"PENDING".equals(status))throw new IllegalArgumentException("Esta solicitação já foi analisada.");status=approve?"APPROVED":"REJECTED";reviewedBy=reviewer;reviewedAt=Instant.now();reviewNote=note;}
    public UUID getId(){return id;} public Account getAccount(){return account;} public String getRequestType(){return requestType;} public String getStatus(){return status;} public AccountRole getRequestedRole(){return requestedRole;} public String getCity(){return city;} public String getState(){return state;} public String getPostalCode(){return postalCode;} public Store getStore(){return store;} public String getReviewNote(){return reviewNote;} public Instant getCreatedAt(){return createdAt;} public Instant getReviewedAt(){return reviewedAt;}
}
