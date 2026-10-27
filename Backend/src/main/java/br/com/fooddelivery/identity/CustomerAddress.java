package br.com.fooddelivery.identity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "customer_addresses", indexes = @Index(name = "idx_customer_address_owner", columnList = "account_id"))
public class CustomerAddress {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(optional = false, fetch = FetchType.LAZY) @JoinColumn(name = "account_id") private Account account;
    @Column(nullable = false, length = 40) private String label;
    @Column(name = "address_line", nullable = false, length = 250) private String addressLine;
    @Column(name = "address_complement", length = 120) private String addressComplement;
    @Column(nullable = false, length = 120) private String neighborhood;
    @Column(nullable = false, length = 100) private String city;
    @Column(nullable = false, length = 2) private String state;
    @Column(name = "postal_code", nullable = false, length = 9) private String postalCode;
    @Column(name = "is_default", nullable = false) private boolean defaultAddress;

    protected CustomerAddress() {}
    public CustomerAddress(Account account, String label, String addressLine, String complement,
                           String neighborhood, String city, String state, String postalCode, boolean isDefault) {
        this.account = account; update(label, addressLine, complement, neighborhood, city, state, postalCode); this.defaultAddress = isDefault;
    }
    public void update(String label, String addressLine, String complement, String neighborhood,
                       String city, String state, String postalCode) {
        this.label = label; this.addressLine = addressLine; this.addressComplement = complement;
        this.neighborhood = neighborhood; this.city = city; this.state = state; this.postalCode = postalCode;
    }
    public UUID getId() { return id; }
    public Account getAccount() { return account; }
    public String getLabel() { return label; }
    public String getAddressLine() { return addressLine; }
    public String getAddressComplement() { return addressComplement; }
    public String getNeighborhood() { return neighborhood; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getPostalCode() { return postalCode; }
    public boolean isDefaultAddress() { return defaultAddress; }
    public void setDefaultAddress(boolean value) { defaultAddress = value; }
}
