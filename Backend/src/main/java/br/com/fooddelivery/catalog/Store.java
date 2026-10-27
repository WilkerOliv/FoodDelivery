package br.com.fooddelivery.catalog;

import br.com.fooddelivery.identity.Account;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "stores")
public class Store {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "owner_id") private Account owner;
    @Column(nullable = false, length = 140) private String name;
    @Column(length = 1000) private String description;
    @Column(nullable = false, length = 250) private String address;
    @Column(length = 100) private String city;
    @Column(length = 2) private String state;
    @Column(nullable = false, length = 60) private String category;
    @Column(name = "cover_image_url", length = 255) private String coverImageUrl;
    @Column(nullable = false) private boolean active = true;
    @Column(nullable = false) private boolean approved = false;
    @Column(nullable = false, length = 80) private String timezone = "America/Sao_Paulo";
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
    public String getCategory() { return category; }
    public String getCoverImageUrl() { return coverImageUrl; }
    public boolean isActive() { return active; }
    public boolean isApproved() { return approved; }
    @JsonIgnore public int getMarketplaceFeeBps() { return marketplaceFeeBps; }
    public long getDeliveryFeeCents() { return deliveryFeeCents; }
    public void setDeliveryFeeCents(long feeCents) { if(feeCents<0||feeCents>100000)throw new IllegalArgumentException("A taxa de entrega deve ficar entre R$ 0,00 e R$ 1.000,00.");this.deliveryFeeCents=feeCents; }
    public void setMarketplaceFeeBps(int feeBps) { if(feeBps<0||feeBps>5000)throw new IllegalArgumentException("A comissão por loja deve ficar entre 0% e 50%.");this.marketplaceFeeBps=feeBps; }
    public void setLocation(String city, String state) { this.city=city; this.state=state==null?null:state.toUpperCase(java.util.Locale.ROOT); }
    public void update(String name, String description, String address, String city, String state, String category, boolean active, long deliveryFeeCents) {
        this.name = name; this.description = description; this.address = address; setLocation(city,state); this.category = category; this.active = active; setDeliveryFeeCents(deliveryFeeCents);
    }
    public void setApproved(boolean approved) { this.approved = approved; }
    public void setActive(boolean active) { this.active = active; }
    public void setCoverImageUrl(String coverImageUrl) { this.coverImageUrl = coverImageUrl; }
}
