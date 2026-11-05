package br.com.fooddelivery.whatsapp;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="whatsapp_sessions")
public class WhatsAppSession {
 @Id @Column(name="phone_hash",length=64) String phoneHash;
 @Column(name="encrypted_state",nullable=false,columnDefinition="TEXT") String encryptedState;
 @Column(name="updated_at",nullable=false) Instant updatedAt=Instant.now();
 protected WhatsAppSession(){}WhatsAppSession(String hash,String state){phoneHash=hash;encryptedState=state;}
}
