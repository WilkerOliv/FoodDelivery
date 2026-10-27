package br.com.fooddelivery.payments;

import br.com.fooddelivery.catalog.Store;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="marketplace_payment_accounts")
public class StorePaymentAccount {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @JsonIgnore @OneToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="store_id",nullable=false,unique=true) private Store store;
    @Column(name="mp_user_id",length=80) private String mpUserId;
    @JsonIgnore @Column(name="access_token_encrypted",length=2048) private String accessTokenEncrypted;
    @JsonIgnore @Column(name="refresh_token_encrypted",length=2048) private String refreshTokenEncrypted;
    @Column(name="token_expires_at") private Instant tokenExpiresAt;
    @JsonIgnore @Column(name="oauth_state_hash",length=128) private String stateHash;
    @JsonIgnore @Column(name="oauth_state_expires_at") private Instant stateExpiresAt;
    @JsonIgnore @Column(name="oauth_code_verifier_encrypted",length=256) private String codeVerifierEncrypted;
    @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt=Instant.now();
    @Column(name="updated_at",nullable=false) private Instant updatedAt=Instant.now();
    protected StorePaymentAccount(){}
    public StorePaymentAccount(Store store){this.store=store;}
    public Store getStore(){return store;} public String getMpUserId(){return mpUserId;} public Instant getTokenExpiresAt(){return tokenExpiresAt;}
    public boolean isConnected(){return accessTokenEncrypted!=null&&tokenExpiresAt!=null&&tokenExpiresAt.isAfter(Instant.now());}
    String accessTokenEncrypted(){return accessTokenEncrypted;} String refreshTokenEncrypted(){return refreshTokenEncrypted;}
    String stateHash(){return stateHash;} Instant stateExpiresAt(){return stateExpiresAt;} String codeVerifierEncrypted(){return codeVerifierEncrypted;}
    void beginAuthorization(String stateHash,String verifierCiphertext){this.stateHash=stateHash;this.stateExpiresAt=Instant.now().plusSeconds(600);this.codeVerifierEncrypted=verifierCiphertext;this.updatedAt=Instant.now();}
    void completeAuthorization(String userId,String access,String refresh,long expiresSeconds){this.mpUserId=userId;this.accessTokenEncrypted=access;this.refreshTokenEncrypted=refresh;this.tokenExpiresAt=Instant.now().plusSeconds(expiresSeconds);this.stateHash=null;this.stateExpiresAt=null;this.codeVerifierEncrypted=null;this.updatedAt=Instant.now();}
    void refresh(String access,String refresh,long expiresSeconds){this.accessTokenEncrypted=access;this.refreshTokenEncrypted=refresh;this.tokenExpiresAt=Instant.now().plusSeconds(expiresSeconds);this.updatedAt=Instant.now();}
    void clearAuthorizationState(){this.stateHash=null;this.stateExpiresAt=null;this.codeVerifierEncrypted=null;this.updatedAt=Instant.now();}
}
