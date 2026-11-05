package br.com.fooddelivery.whatsapp;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="whatsapp_events")
public class WhatsAppEvent {
 @Id @Column(name="message_id",length=200) String messageId;
 @Column(name="phone_hash",nullable=false,length=64) String phoneHash;
 @Column(name="encrypted_phone",nullable=false,columnDefinition="TEXT") String encryptedPhone;
 @Column(name="encrypted_text",nullable=false,columnDefinition="TEXT") String encryptedText;
 @Column(name="created_at",nullable=false) Instant createdAt=Instant.now();
 @Column(nullable=false,length=20) String status="PENDING";
 @Column(nullable=false) int attempts;
 protected WhatsAppEvent(){}WhatsAppEvent(String id,String hash,String phone,String text){messageId=id;phoneHash=hash;encryptedPhone=phone;encryptedText=text;}
}
