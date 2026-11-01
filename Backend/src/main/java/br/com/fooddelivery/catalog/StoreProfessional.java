package br.com.fooddelivery.catalog;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name="store_professionals", uniqueConstraints=@UniqueConstraint(name="uk_store_professional_name",columnNames={"store_id","name"}))
public class StoreProfessional {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @ManyToOne(optional=false,fetch=FetchType.LAZY) @JoinColumn(name="store_id",nullable=false) private Store store;
    @Column(nullable=false,length=100) private String name;
    @Column(nullable=false) private boolean active=true;
    protected StoreProfessional(){}
    public StoreProfessional(Store store,String name){this.store=store;this.name=name.trim();}
    public UUID getId(){return id;} public Store getStore(){return store;} public String getName(){return name;} public boolean isActive(){return active;}
    public void updateName(String name){this.name=name.trim();}
    public void setActive(boolean active){this.active=active;}
}
