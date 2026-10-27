package br.com.fooddelivery.orders;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.identity.Account;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @Table(name="customer_orders", indexes=@Index(name="idx_order_customer_created", columnList="customer_id,created_at"))
public class Order {
    @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
    @ManyToOne(optional=false, fetch=FetchType.LAZY) @JoinColumn(name="customer_id") @JsonIgnore private Account customer;
    @ManyToOne(optional=false, fetch=FetchType.LAZY) @JoinColumn(name="store_id") private Store store;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=24) private OrderStatus status=OrderStatus.AWAITING_PAYMENT;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16) private FulfillmentType fulfillmentType;
    @Column(name="delivery_address", length=400) private String deliveryAddress;
    @Column(name="customer_note", length=500) private String customerNote;
    @Column(nullable=false) private long totalCents;
    @Column(name="subtotal_cents",nullable=false) private long subtotalCents;
    @Column(name="delivery_fee_cents",nullable=false) private long deliveryFeeCents;
    @Column(nullable=false, length=3) private String currency="BRL";
    @Column(name="created_at", nullable=false, updatable=false) private Instant createdAt=Instant.now();
    @ElementCollection @CollectionTable(name="order_lines", joinColumns=@JoinColumn(name="order_id")) private List<OrderLine> lines=new ArrayList<>();
    @Version private long version;
    protected Order() {}
    public Order(Account customer, Store store, FulfillmentType type, String address, long subtotal,long deliveryFee, String customerNote) { this.customer=customer; this.store=store; this.fulfillmentType=type; this.deliveryAddress=address; this.customerNote=normalizeNote(customerNote); this.subtotalCents=subtotal;this.deliveryFeeCents=deliveryFee;this.totalCents=Math.addExact(subtotal,deliveryFee); }
    private static String normalizeNote(String note) { if(note==null||note.isBlank())return null;String value=note.trim();if(value.length()>500)throw new IllegalArgumentException("A observação pode ter até 500 caracteres.");return value; }
    public void addLine(UUID itemId,String name,int quantity,long unitPrice){lines.add(new OrderLine(itemId,name,quantity,unitPrice));}
    public UUID getId(){return id;} public Account getCustomer(){return customer;} public Store getStore(){return store;} public OrderStatus getStatus(){return status;}
    public FulfillmentType getFulfillmentType(){return fulfillmentType;} public String getDeliveryAddress(){return deliveryAddress;} public long getTotalCents(){return totalCents;}
    public long getSubtotalCents(){return subtotalCents;} public long getDeliveryFeeCents(){return deliveryFeeCents;}
    public String getCurrency(){return currency;} public Instant getCreatedAt(){return createdAt;} public List<OrderLine> getLines(){return lines;}
    public String getCustomerNote(){return customerNote;}
    public void paid(){if(status!=OrderStatus.AWAITING_PAYMENT)throw new IllegalStateException("Pedido não aguarda pagamento.");status=OrderStatus.AWAITING_ACCEPTANCE;}
    public void cancelUnpaid(){if(status!=OrderStatus.AWAITING_PAYMENT)throw new IllegalStateException("Pedido não aguarda pagamento.");status=OrderStatus.CANCELLED;}
    public void transition(OrderStatus next){boolean valid=switch(status){case AWAITING_ACCEPTANCE->next==OrderStatus.ACCEPTED||next==OrderStatus.REJECTED||next==OrderStatus.CANCELLED;case ACCEPTED->next==OrderStatus.PREPARING||next==OrderStatus.CANCELLED;case PREPARING->next==OrderStatus.READY||next==OrderStatus.CANCELLED;case READY->next==OrderStatus.COMPLETED;default->false;};if(!valid)throw new IllegalStateException("Transição inválida: "+status+" → "+next);status=next;}
    @Embeddable public static class OrderLine {
        @Column(name="catalog_item_id",nullable=false) private UUID itemId; @Column(nullable=false,length=140) private String name;
        @Column(nullable=false) private int quantity; @Column(name="unit_price_cents",nullable=false) private long unitPriceCents;
        protected OrderLine(){} OrderLine(UUID id,String name,int quantity,long price){this.itemId=id;this.name=name;this.quantity=quantity;this.unitPriceCents=price;}
        public UUID getItemId(){return itemId;} public String getName(){return name;} public int getQuantity(){return quantity;} public long getUnitPriceCents(){return unitPriceCents;}
    }
}
