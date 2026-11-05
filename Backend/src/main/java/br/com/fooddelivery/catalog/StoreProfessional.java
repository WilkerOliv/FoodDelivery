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
    @Column(name="operating_hours_enabled",nullable=false) private boolean operatingHoursEnabled;
    @Column(name="weekly_hours_json",nullable=false,columnDefinition="TEXT") private String weeklyHoursJson="{}";
    @Column(name="hours_exceptions_json",nullable=false,columnDefinition="TEXT") private String hoursExceptionsJson="[]";
    public boolean isOperatingHoursEnabled(){return operatingHoursEnabled;}public String getWeeklyHoursJson(){return weeklyHoursJson;}public String getHoursExceptionsJson(){return hoursExceptionsJson;}
    public void setOperatingHours(boolean enabled,String weekly,String exceptions){operatingHoursEnabled=enabled;weeklyHoursJson=weekly;hoursExceptionsJson=exceptions;}
    protected StoreProfessional(){}
    public StoreProfessional(Store store,String name){this.store=store;this.name=name.trim();}
    public UUID getId(){return id;} public Store getStore(){return store;} public String getName(){return name;} public boolean isActive(){return active;}
    public void updateName(String name){this.name=name.trim();}
    public void setActive(boolean active){this.active=active;}
}
