package br.com.fooddelivery.platform;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="service_cities", uniqueConstraints=@UniqueConstraint(columnNames={"city","state"}))
public class ServiceCity {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @Column(nullable=false,length=100) private String city;
    @Column(nullable=false,length=2) private String state;
    @Column(name="postal_code",length=9) private String postalCode;
    @Column(nullable=false) private boolean enabled;
    @Column(name="created_at",nullable=false,updatable=false) private Instant createdAt=Instant.now();
    protected ServiceCity(){}
    public ServiceCity(String city,String state,String postalCode){this.city=city.trim();this.state=state.trim().toUpperCase(java.util.Locale.ROOT);this.postalCode=postalCode==null||postalCode.isBlank()?null:postalCode.replaceAll("\\D","");this.enabled=true;}
    public void enable(String postalCode){this.enabled=true;if(postalCode!=null&&!postalCode.isBlank())this.postalCode=postalCode;}
    public void update(String city,String state,String postalCode,boolean enabled){this.city=city.trim();this.state=state.trim().toUpperCase(java.util.Locale.ROOT);this.postalCode=postalCode==null||postalCode.isBlank()?null:postalCode.replaceAll("\\D","");this.enabled=enabled;}
    public UUID getId(){return id;} public String getCity(){return city;} public String getState(){return state;} public String getPostalCode(){return postalCode;} public boolean isEnabled(){return enabled;}
}
