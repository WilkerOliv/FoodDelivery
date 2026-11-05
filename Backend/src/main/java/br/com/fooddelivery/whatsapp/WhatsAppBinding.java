package br.com.fooddelivery.whatsapp;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="whatsapp_bindings")
public class WhatsAppBinding {
 @Id @Column(name="phone_hash",length=64) String phoneHash;
 @Column(name="account_id",nullable=false,unique=true) UUID accountId;
 @Column(name="credential_version",nullable=false) int credentialVersion;
 @Column(name="created_at",nullable=false) Instant createdAt=Instant.now();
 protected WhatsAppBinding(){}WhatsAppBinding(String hash,UUID account,int version){phoneHash=hash;accountId=account;credentialVersion=version;}
}
