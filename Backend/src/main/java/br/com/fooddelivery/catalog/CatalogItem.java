package br.com.fooddelivery.catalog;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "catalog_items")
public class CatalogItem {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "store_id") private Store store;
    @Column(nullable = false, length = 140) private String name;
    @Column(length = 1000) private String description;
    @Column(nullable = false) private long priceCents;
    @Column(nullable = false) private int stock;
    @Column(nullable = false) private boolean active = true;
    @Column(nullable = false) private boolean serviceItem = false;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();
    @Version private long version;
    protected CatalogItem() {}
    public CatalogItem(Store store, String name, String description, long priceCents, int stock, boolean serviceItem) {
        this.store = store; this.name = name; this.description = description; this.priceCents = priceCents; this.stock = stock; this.serviceItem = serviceItem;
    }
    public UUID getId() { return id; }
    public Store getStore() { return store; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public long getPriceCents() { return priceCents; }
    public int getStock() { return stock; }
    public boolean isActive() { return active; }
    public boolean isServiceItem() { return serviceItem; }
    public void update(String name, String description, long priceCents, int stock, boolean active) {
        this.name = name; this.description = description; this.priceCents = priceCents; this.stock = stock; this.active = active;
    }
    public void reserve(int quantity) {
        if (!active || (!serviceItem && stock < quantity)) throw new IllegalStateException("Item indisponível ou sem estoque suficiente.");
        if (!serviceItem) stock -= quantity;
    }
    public void restore(int quantity) { if (!serviceItem) stock += quantity; }
}
