package br.com.fooddelivery.catalog;

import br.com.fooddelivery.identity.Account;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "stores")
@org.hibernate.annotations.DynamicUpdate
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "owner_id") private Account owner;
    @Column(nullable = false, length = 140) private String name;
    @Column(length = 1000) private String description;
    @Column(nullable = false, length = 250) private String address;
    @Column(length = 100) private String city;
    @Column(length = 2) private String state;
    @Column private Double latitude;
    @Column private Double longitude;
    @Column(nullable=false) private boolean deliveryEnabled=true;
    @Column(nullable=false) private boolean pickupEnabled=true;
    @Column(nullable=false) private boolean inPersonEnabled=true;
    @Column(length = 14, unique = true) private String cnpj;
    @Column(nullable = false, length = 60) private String category;
    @Column(name = "cover_image_url", length = 255) private String coverImageUrl;
    @Column(nullable = false) private boolean active = true;
    @Column(nullable = false) private boolean approved = false;
    @Column(nullable = false, length = 80) private String timezone = "America/Sao_Paulo";
    @Column(name="booking_notice_minutes",nullable=false) private int bookingNoticeMinutes=30;
    @Column(name="booking_step_minutes",nullable=false) private int bookingStepMinutes=15;
    @Column(name="automatic_cancellation_notice_minutes",nullable=false) private int automaticCancellationNoticeMinutes=-1;
    public int getBookingNoticeMinutes(){return bookingNoticeMinutes;}public int getBookingStepMinutes(){return bookingStepMinutes;}public int getAutomaticCancellationNoticeMinutes(){return automaticCancellationNoticeMinutes;}
    public void configureBookingPolicy(int notice,int step,int cancellation){if(notice<0||notice>10080||!java.util.Set.of(5,10,15,20,30,60).contains(step)||cancellation < -1||cancellation>10080)throw new IllegalArgumentException("Revise a antecedência, o intervalo e a política de cancelamento.");bookingNoticeMinutes=notice;bookingStepMinutes=step;automaticCancellationNoticeMinutes=cancellation;}
    @Column(name = "operating_hours_enabled", nullable = false) private boolean operatingHoursEnabled;
    @Column(name = "weekly_hours_json", columnDefinition = "TEXT", nullable = false) private String weeklyHoursJson = "{}";
    @Column(name = "hours_exceptions_json", columnDefinition = "TEXT", nullable = false) private String hoursExceptionsJson = "[]";
    @Column(name = "marketplace_fee_bps", nullable = false) private int marketplaceFeeBps;
    @Column(name = "delivery_fee_cents", nullable = false) private long deliveryFeeCents;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();
    protected Store() {}
    public Store(Account owner, String name, String description, String address, String category) {
        this.owner = owner; this.name = name; this.description = description; this.address = address; this.category = category;
    }
    public UUID getId() { return id; }
    public Account getOwner() { return owner; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public Double getLatitude(){return latitude;} public Double getLongitude(){return longitude;}
    public boolean isDeliveryEnabled(){return deliveryEnabled;}
    public boolean isPickupEnabled(){return pickupEnabled;}
    public boolean isInPersonEnabled(){return inPersonEnabled;}
    public void configureFulfillment(Boolean delivery,Boolean pickup,Boolean inPerson){
        boolean d=delivery==null?deliveryEnabled:delivery,p=pickup==null?pickupEnabled:pickup,i=inPerson==null?inPersonEnabled:inPerson;
        if(!d&&!p&&!i)throw new IllegalArgumentException("Escolha ao menos uma modalidade de atendimento.");
        deliveryEnabled=d;pickupEnabled=p;inPersonEnabled=i;
    }
    public void requireFulfillment(br.com.fooddelivery.orders.FulfillmentType type){
        if(type==null)throw new IllegalArgumentException("Escolha entrega ou retirada.");
        if(type==br.com.fooddelivery.orders.FulfillmentType.DELIVERY&&!deliveryEnabled)throw new IllegalArgumentException("Esta loja não oferece entrega. Confira as modalidades disponíveis.");
        if(type==br.com.fooddelivery.orders.FulfillmentType.PICKUP&&!pickupEnabled)throw new IllegalArgumentException("Esta loja não oferece retirada no local.");
    }
    public void requireInPerson(){if(!inPersonEnabled)throw new IllegalArgumentException("Esta loja não oferece agendamentos presenciais.");}
    public String getCnpj() { return cnpj; }
    public String getCategory() { return category; }
    public String getTimezone() { return timezone; }
    public boolean isOperatingHoursEnabled() { return operatingHoursEnabled; }
    public String getWeeklyHoursJson() { return weeklyHoursJson; }
    public String getHoursExceptionsJson() { return hoursExceptionsJson; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public boolean isActive() { return active; }
    public boolean isApproved() { return approved; }
    public boolean isAvailable() { return active && approved && owner != null && owner.isActive() && owner.getGrantedRoles().contains(br.com.fooddelivery.identity.AccountRole.MERCHANT); }
    @JsonIgnore public int getMarketplaceFeeBps() { return marketplaceFeeBps; }
    public long getDeliveryFeeCents() { return deliveryFeeCents; }
    public void setDeliveryFeeCents(long feeCents) { if(feeCents<0||feeCents>100000)throw new IllegalArgumentException("A taxa de entrega deve ficar entre R$ 0,00 e R$ 1.000,00.");this.deliveryFeeCents=feeCents; }
    public void setMarketplaceFeeBps(int feeBps) { if(feeBps<0||feeBps>5000)throw new IllegalArgumentException("A comissão por loja deve ficar entre 0% e 50%.");this.marketplaceFeeBps=feeBps; }
    public void setLocation(String city, String state) { this.city=city; this.state=state==null?null:state.toUpperCase(java.util.Locale.ROOT); }
    public void setCoordinates(Double latitude,Double longitude){if((latitude==null)!=(longitude==null))throw new IllegalArgumentException("Informe latitude e longitude juntas ou deixe os dois campos vazios.");if(latitude!=null&&(!Double.isFinite(latitude)||latitude < -90||latitude > 90||!Double.isFinite(longitude)||longitude < -180||longitude > 180))throw new IllegalArgumentException("As coordenadas informadas estão fora dos limites válidos.");this.latitude=latitude;this.longitude=longitude;}
    public void setCnpjOnce(String value) { String normalized=br.com.fooddelivery.identity.Cnpj.normalize(value); if(!br.com.fooddelivery.identity.Cnpj.isValid(normalized))throw new IllegalArgumentException("Informe um CNPJ válido."); if(cnpj!=null&&!cnpj.equals(normalized))throw new IllegalArgumentException("O CNPJ não pode ser alterado após o cadastro da loja."); cnpj=normalized; }
    public void correctCnpjByAdministrator(String value) {if(value==null||value.isBlank()){cnpj=null;return;}String normalized=br.com.fooddelivery.identity.Cnpj.normalize(value);if(!br.com.fooddelivery.identity.Cnpj.isValid(normalized))throw new IllegalArgumentException("Informe um CNPJ válido ou deixe vazio.");cnpj=normalized;}
    public void update(String name, String description, String address, String city, String state, String category, boolean active, long deliveryFeeCents) {
        this.name = name; this.description = description; this.address = address; setLocation(city,state); this.category = category; this.active = active; setDeliveryFeeCents(deliveryFeeCents);
    }
    public void setApproved(boolean approved) { this.approved = approved; }
    public void setActive(boolean active) { this.active = active; }
    public void setTimezone(String timezone) {
        String value=timezone==null||timezone.isBlank()?"America/Sao_Paulo":timezone.trim();
        try { java.time.ZoneId.of(value); } catch (java.time.DateTimeException ex) { throw new IllegalArgumentException("Escolha um fuso horário válido."); }
        this.timezone=value;
    }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
    public void setOperatingHours(boolean enabled, String weeklyHoursJson, String hoursExceptionsJson) {
        this.operatingHoursEnabled = enabled;
        this.weeklyHoursJson = weeklyHoursJson == null || weeklyHoursJson.isBlank() ? "{}" : weeklyHoursJson;
        this.hoursExceptionsJson = hoursExceptionsJson == null || hoursExceptionsJson.isBlank() ? "[]" : hoursExceptionsJson;
    }
}
