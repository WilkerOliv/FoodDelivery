package br.com.fooddelivery.catalog;

import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/v1")
public class CatalogController {
    private final StoreRepository stores;
    private final CatalogItemRepository items;
    private final AccountRepository accounts;
    public CatalogController(StoreRepository stores, CatalogItemRepository items, AccountRepository accounts) {
        this.stores = stores; this.items = items; this.accounts = accounts;
    }
    @GetMapping("/store-categories") public List<StoreCategories.Category> categories() { return StoreCategories.ALL; }
    @GetMapping("/stores") public List<Store> stores(@RequestParam(required=false) String city,@RequestParam(required=false) String state) {
        List<Store> available=stores.findByActiveTrueAndApprovedTrue();
        if(city==null||city.isBlank()) return available;
        return available.stream().filter(store->store.getCity()!=null&&store.getCity().equalsIgnoreCase(city.trim()))
                .filter(store->state==null||state.isBlank()||(store.getState()!=null&&store.getState().equalsIgnoreCase(state.trim())))
                .toList();
    }
    @GetMapping("/stores/{id}/items") public List<CatalogItem> items(@PathVariable UUID id) {
        Store store = stores.findById(id).orElseThrow();
        if (!store.isActive() || !store.isApproved()) throw new NoSuchElementException("Loja não encontrada.");
        return items.findByStoreIdAndActiveTrue(id);
    }
    @PostMapping("/merchant/stores") @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MERCHANT')")
    public Store createStore(@Valid @RequestBody StoreRequest req, Authentication auth) {
        validateCategory(req.category());
        Account owner = current(auth);
        Store store=new Store(owner, req.name(), req.description(), req.address(), req.category());store.setLocation(req.city().trim(),req.state());store.setDeliveryFeeCents(req.deliveryFeeCents());return stores.save(store);
    }
    @GetMapping("/merchant/stores/mine") @PreAuthorize("hasRole('MERCHANT')")
    public List<Store> myStores(Authentication auth) { return stores.findByOwnerId(subject(auth)); }
    @PutMapping("/merchant/stores/{id}") @PreAuthorize("hasRole('MERCHANT')") public Store updateStore(@PathVariable UUID id, @Valid @RequestBody StoreRequest req, Authentication auth) {
        validateCategory(req.category());
        Store store = owned(id, auth); store.update(req.name(), req.description(), req.address(), req.city().trim(), req.state(), req.category(), req.active(),req.deliveryFeeCents()); return stores.save(store);
    }
    @PostMapping("/merchant/stores/{id}/items") @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MERCHANT')")
    public CatalogItem createItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth) {
        Store store = owned(id, auth);
        return items.save(new CatalogItem(store, req.name(), req.description(), req.priceCents(), req.stock(), req.serviceItem()));
    }
    @GetMapping("/merchant/stores/{id}/items") @PreAuthorize("hasRole('MERCHANT')")
    public List<CatalogItem> myStoreItems(@PathVariable UUID id, Authentication auth) {
        owned(id, auth);
        return items.findByStoreId(id);
    }
    @PutMapping("/merchant/items/{id}") @PreAuthorize("hasRole('MERCHANT')") public CatalogItem updateItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth) {
        CatalogItem item = items.findById(id).orElseThrow();
        if (!item.getStore().getOwner().getId().equals(subject(auth))) throw new AccessDeniedException("Item não pertence à sua loja.");
        item.update(req.name(), req.description(), req.priceCents(), req.stock(), req.active()); return items.save(item);
    }
    private Store owned(UUID id, Authentication auth) {
        Store store = stores.findById(id).orElseThrow();
        if (!store.getOwner().getId().equals(subject(auth))) throw new AccessDeniedException("Loja não pertence à sua conta.");
        return store;
    }
    private Account current(Authentication auth) { return accounts.findById(subject(auth)).orElseThrow(); }
    private UUID subject(Authentication auth) { return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject()); }
    private void validateCategory(String category) {
        if (!StoreCategories.isSupported(category)) throw new IllegalArgumentException("Escolha uma categoria disponível no catálogo.");
    }
    public record StoreRequest(@NotBlank @Size(max=140) String name, @Size(max=1000) String description,
                               @NotBlank @Size(max=250) String address, @NotBlank @Size(max=60) String category,
                               @NotBlank @Size(max=100) String city,@NotBlank @Size(min=2,max=2) String state,
                               boolean active,@jakarta.validation.constraints.PositiveOrZero @jakarta.validation.constraints.Max(100000) long deliveryFeeCents) {}
    public record ItemRequest(@NotBlank @Size(max=140) String name, @Size(max=1000) String description,
                              @PositiveOrZero long priceCents, @PositiveOrZero int stock, boolean serviceItem, boolean active) {}
}
