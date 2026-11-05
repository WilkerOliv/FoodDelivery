package br.com.fooddelivery.identity;
import jakarta.persistence.*;
import java.util.UUID;
import java.time.Instant;
@Entity @Table(name="administrator_mfa")
public class AdminMfa {
 @Id @Column(name="account_id") private UUID accountId;
 @Column(name="encrypted_secret",nullable=false,columnDefinition="TEXT") private String encryptedSecret;
 @Column(nullable=false) private boolean enabled;
 @Column(name="last_counter",nullable=false) private long lastCounter=-1;
 @Column(name="recovery_hashes",nullable=false,columnDefinition="TEXT") private String recoveryHashes="[]";
 @Column(name="failed_attempts",nullable=false) private int failedAttempts;
 @Column(name="locked_until") private Instant lockedUntil;
 @Column(name="updated_at",nullable=false) private Instant updatedAt=Instant.now();
 protected AdminMfa(){}public AdminMfa(UUID account,String secret){accountId=account;encryptedSecret=secret;}
 public boolean isEnabled(){return enabled;}public String getEncryptedSecret(){return encryptedSecret;}public long getLastCounter(){return lastCounter;}public String getRecoveryHashes(){return recoveryHashes;}public Instant getUpdatedAt(){return updatedAt;}
 public boolean ready(){if(lockedUntil!=null&&lockedUntil.isAfter(Instant.now()))return false;if(lockedUntil!=null){lockedUntil=null;failedAttempts=0;}return true;}
 public void failed(){if(++failedAttempts>=5)lockedUntil=Instant.now().plusSeconds(900);}
 public void accepted(long counter){lastCounter=counter;failedAttempts=0;lockedUntil=null;}
 public void enable(String hashes){enabled=true;recoveryHashes=hashes;updatedAt=Instant.now();}
 public void useRecovery(String hashes){recoveryHashes=hashes;failedAttempts=0;lockedUntil=null;}
}
