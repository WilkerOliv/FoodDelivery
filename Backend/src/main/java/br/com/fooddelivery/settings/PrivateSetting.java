package br.com.fooddelivery.settings;
import jakarta.persistence.*;
import java.time.Instant;
@Entity @Table(name="private_settings")
public class PrivateSetting {
 @Id @Column(name="setting_key",length=100) private String key;
 @Column(name="encrypted_value",nullable=false,columnDefinition="TEXT") private String encryptedValue;
 @Column(name="updated_at",nullable=false) private Instant updatedAt;
 protected PrivateSetting(){}public PrivateSetting(String key,String encrypted){this.key=key;encryptedValue=encrypted;updatedAt=Instant.now();}
 public String getEncryptedValue(){return encryptedValue;}
}
