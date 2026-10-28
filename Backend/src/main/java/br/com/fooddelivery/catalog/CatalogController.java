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
import br.com.fooddelivery.platform.*;
import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/v1")
public class CatalogController {
    private final StoreRepository stores;
    private final CatalogItemRepository items;
    private final AccountRepository accounts;
    private final PlatformRequestRepository platformRequests;
    private final ServiceCityRepository serviceCities;
    private final StoreCategoryRepository storeCategories;
    public CatalogController(StoreRepository stores, CatalogItemRepository items, AccountRepository accounts, PlatformRequestRepository platformRequests, ServiceCityRepository serviceCities, StoreCategoryRepository storeCategories) {
        this.stores = stores; this.items = items; this.accounts = accounts; this.platformRequests=platformRequests;this.serviceCities=serviceCities;this.storeCategories=storeCategories;
    }
    @GetMapping("/store-categories") @Transactional(readOnly=true)
    public List<StoreCategories.Category> categories() {
        return java.util.stream.Stream.concat(StoreCategories.ALL.stream(), storeCategories.findAllByOrderByNameAsc().stream()
                .map(category -> new StoreCategories.Category(category.getName(), category.getEmoji())))
                .toList();
    }
    @GetMapping("/store-locations") @Transactional(readOnly=true) public List<LocationView> locations() {
        List<LocationView> fromStores=stores.findByActiveTrueAndApprovedTrue().stream().filter(Store::isAvailable)
                .filter(store -> store.getCity() != null && store.getState() != null)
                .map(store -> new LocationView(store.getCity().trim(), store.getState().trim().toUpperCase(Locale.ROOT)))
                .toList();
        return java.util.stream.Stream.concat(fromStores.stream(),serviceCities.findByEnabledTrue().stream().map(c->new LocationView(c.getCity(),c.getState())))
                .distinct().sorted(Comparator.comparing(LocationView::city, String.CASE_INSENSITIVE_ORDER).thenComparing(LocationView::state)).toList();
    }
    public record LocationView(String city, String state) {}
    @GetMapping("/stores") @Transactional(readOnly=true) public List<StoreView> stores(@RequestParam(required=false) String city,@RequestParam(required=false) String state) {
        List<Store> available=stores.findByActiveTrueAndApprovedTrue().stream().filter(Store::isAvailable).toList();
        if(city==null||city.isBlank()) return available.stream().map(StoreView::of).toList();
        return available.stream().filter(store->store.getCity()!=null&&store.getCity().equalsIgnoreCase(city.trim()))
                .filter(store->state==null||state.isBlank()||(store.getState()!=null&&store.getState().equalsIgnoreCase(state.trim())))
                .map(StoreView::of).toList();
    }
    @GetMapping("/stores/{id}/items") @Transactional(readOnly=true) public List<CatalogItemView> items(@PathVariable UUID id) {
        Store store = stores.findById(id).orElseThrow();
        if (!store.isAvailable()) throw new NoSuchElementException("Loja não encontrada.");
        return items.findByStoreIdAndActiveTrue(id).stream().map(CatalogItemView::of).toList();
    }
    @PostMapping("/merchant/stores") @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MERCHANT')")
    @Transactional
    public StoreView createStore(@Valid @RequestBody StoreRequest req, Authentication auth) {
        validateCategory(req.category());
        Account owner = current(auth);
        Store store=new Store(owner, req.name(), req.description(), req.address(), req.category());store.setLocation(req.city().trim(),req.state());store.setCnpjOnce(req.cnpj());store.setDeliveryFeeCents(req.deliveryFeeCents());store=stores.save(store);platformRequests.save(PlatformRequest.store(owner,store));return StoreView.of(store);
    }
    @GetMapping("/merchant/stores/mine") @PreAuthorize("hasRole('MERCHANT')")
    @Transactional(readOnly=true)
    public List<StoreView> myStores(Authentication auth) { return stores.findByOwnerId(subject(auth)).stream().map(StoreView::of).toList(); }
    @PostMapping("/merchant/stores/{id}/resubmit") @PreAuthorize("hasRole('MERCHANT')") @ResponseStatus(HttpStatus.CREATED) @Transactional
    public StoreView resubmitStore(@PathVariable UUID id,Authentication auth){Store store=owned(id,auth);var previous=platformRequests.findFirstByStoreIdOrderByCreatedAtDesc(id).orElseThrow(()->new IllegalArgumentException("Não encontramos uma solicitação anterior desta loja."));if(!"REJECTED".equals(previous.getStatus()))throw new IllegalArgumentException("Só é possível reenviar uma loja que já foi reprovada.");store.setApproved(false);store.setActive(true);stores.save(store);platformRequests.save(PlatformRequest.store(store.getOwner(),store));return StoreView.of(store);}
    @PutMapping("/merchant/stores/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public StoreView updateStore(@PathVariable UUID id, @Valid @RequestBody StoreRequest req, Authentication auth) {
        validateCategory(req.category());
        Store store = owned(id, auth);
        if (platformRequests.findFirstByStoreIdOrderByCreatedAtDesc(id).filter(r -> "PENDING".equals(r.getStatus())).isPresent()) throw new IllegalStateException("A loja está em análise. Aguarde a decisão da administração antes de alterar os dados.");
        store.update(req.name(), req.description(), req.address(), req.city().trim(), req.state(), req.category(), req.active(),req.deliveryFeeCents()); return StoreView.of(stores.save(store));
    }
    @PostMapping("/merchant/stores/{id}/items") @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MERCHANT')")
    public CatalogItemView createItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth) {
        Store store = owned(id, auth);
        return CatalogItemView.of(items.save(new CatalogItem(store, req.name(), req.description(), req.priceCents(), req.stock(), req.serviceItem(), req.payAfterService())));
    }
    @GetMapping("/merchant/stores/{id}/items") @PreAuthorize("hasRole('MERCHANT')")
    @Transactional(readOnly=true)
    public List<CatalogItemView> myStoreItems(@PathVariable UUID id, Authentication auth) {
        owned(id, auth);
        return items.findByStoreId(id).stream().map(CatalogItemView::of).toList();
    }
    @PutMapping("/merchant/items/{id}") @PreAuthorize("hasRole('MERCHANT')") @Transactional public CatalogItemView updateItem(@PathVariable UUID id, @Valid @RequestBody ItemRequest req, Authentication auth) {
        CatalogItem item = items.findById(id).orElseThrow();
        if (!item.getStore().getOwner().getId().equals(subject(auth))) throw new AccessDeniedException("Item não pertence à sua loja.");
        item.update(req.name(), req.description(), req.priceCents(), req.stock(), req.active(), req.serviceItem(), req.payAfterService()); return CatalogItemView.of(items.save(item));
    }
    private Store owned(UUID id, Authentication auth) {
        Store store = stores.findById(id).orElseThrow();
        if (!store.getOwner().getId().equals(subject(auth))) throw new AccessDeniedException("Loja não pertence à sua conta.");
        return store;
    }
    private Account current(Authentication auth) { return accounts.findById(subject(auth)).orElseThrow(); }
    private UUID subject(Authentication auth) { return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject()); }
    private void validateCategory(String category) {
        if (!StoreCategories.isSupported(category) && storeCategories.findByNameIgnoreCase(category.trim()).isEmpty())
            throw new IllegalArgumentException("Escolha uma categoria disponível no catálogo.");
    }
    public record StoreRequest(@NotBlank @Size(max=140) String name, @Size(max=1000) String description,
                               @NotBlank @Size(max=250) String address, @NotBlank @Size(max=60) String category,
                               @NotBlank @Size(max=100) String city,@NotBlank @Size(min=2,max=2) String state,
                               @NotBlank @jakarta.validation.constraints.Pattern(regexp="\\d{14}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}") String cnpj,
                               boolean active,@jakarta.validation.constraints.PositiveOrZero @jakarta.validation.constraints.Max(100000) long deliveryFeeCents) {}
    public record ItemRequest(@NotBlank @Size(max=140) String name, @Size(max=1000) String description,
                              @PositiveOrZero long priceCents, @PositiveOrZero int stock, boolean serviceItem, boolean active,
                              boolean payAfterService) {}
    public record StoreView(UUID id, String name, String description, String address, String city, String state,
                            String cnpj, String category, String coverImageUrl, boolean active, boolean approved,
                            long deliveryFeeCents, boolean available) {
        static StoreView of(Store s) { return new StoreView(s.getId(), s.getName(), s.getDescription(), s.getAddress(),
                s.getCity(), s.getState(), s.getCnpj(), s.getCategory(), s.getCoverImageUrl(), s.isActive(),
                s.isApproved(), s.getDeliveryFeeCents(), s.isAvailable()); }
    }
    public record CatalogItemView(UUID id, String name, String description, long priceCents, int stock,
                                  boolean active, boolean serviceItem, boolean payAfterService) {
        static CatalogItemView of(CatalogItem i) { return new CatalogItemView(i.getId(), i.getName(), i.getDescription(),
                i.getPriceCents(), i.getStock(), i.isActive(), i.isServiceItem(), i.isPayAfterService()); }
    }
}
