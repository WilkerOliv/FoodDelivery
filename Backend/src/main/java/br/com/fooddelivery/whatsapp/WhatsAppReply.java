package br.com.fooddelivery.whatsapp;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="whatsapp_replies")
public class WhatsAppReply {
 @Id UUID id=UUID.randomUUID();
 @Column(name="message_id",nullable=false,unique=true,length=200) String messageId;
 @Column(name="encrypted_phone",nullable=false,columnDefinition="TEXT") String encryptedPhone;
 @Column(name="encrypted_text",nullable=false,columnDefinition="TEXT") String encryptedText;
 @Column(name="payment_id") UUID paymentId;
 @Column(name="customer_id") UUID customerId;
 @Column(nullable=false,length=20) String status="PENDING";
 @Column(nullable=false) int attempts;
 @Column(name="next_attempt_at",nullable=false) Instant nextAttemptAt=Instant.now();
 @Column(name="lease_id") UUID leaseId;
 @Column(name="created_at",nullable=false) Instant createdAt=Instant.now();
 protected WhatsAppReply(){}WhatsAppReply(String message,String phone,String text,UUID payment,UUID customer){messageId=message;encryptedPhone=phone;encryptedText=text;paymentId=payment;customerId=customer;}
}
