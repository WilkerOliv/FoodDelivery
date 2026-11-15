package br.com.fooddelivery.admin;

import br.com.fooddelivery.catalog.StoreCategories;
import br.com.fooddelivery.catalog.StoreCategory;
import br.com.fooddelivery.catalog.StoreCategoryRepository;
import br.com.fooddelivery.catalog.StoreRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {
    private final StoreCategoryRepository storeCategories;
    private final StoreRepository stores;
    private final AdminAuditService auditService;
    public AdminCategoryController(StoreCategoryRepository storeCategories, StoreRepository stores, AdminAuditService auditService) {
        this.storeCategories=storeCategories;
        this.stores=stores;
        this.auditService=auditService;
    }
    @GetMapping("/store-categories")
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<AdminStoreCategoryView> storeCategories() {
        List<AdminStoreCategoryView> builtIns = StoreCategories.ALL.stream()
                .map(category -> new AdminStoreCategoryView(null, category.name(), category.emoji(), true)).toList();
        List<AdminStoreCategoryView> custom = storeCategories.findAllByOrderByNameAsc().stream()
                .map(category -> new AdminStoreCategoryView(category.getId(), category.getName(), category.getEmoji(), false)).toList();
        return java.util.stream.Stream.concat(builtIns.stream(), custom.stream()).toList();
    }

    @PostMapping("/store-categories")
    @ResponseStatus(HttpStatus.CREATED)
    @org.springframework.transaction.annotation.Transactional
    public AdminStoreCategoryView createStoreCategory(@Valid @RequestBody StoreCategoryRequest request) {
        String name = request.name().trim();
        if (StoreCategories.isSupported(name))
            throw new IllegalArgumentException("Essa categoria já existe e não pode ser duplicada.");
        StoreCategory category = new StoreCategory(name, request.emoji());
        if (storeCategories.findBySlugIgnoreCase(category.getSlug()).isPresent())
            throw new IllegalArgumentException("Já existe uma categoria com esse nome ou uma forma equivalente.");
        StoreCategory saved = storeCategories.save(category);
        auditService.recordCurrent("CATEGORY_CREATED", "STORE_CATEGORY", saved.getId(), "Seção personalizada criada.");
        return new AdminStoreCategoryView(saved.getId(), saved.getName(), saved.getEmoji(), false);
    }

    @DeleteMapping("/store-categories/{id}")
    @org.springframework.transaction.annotation.Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStoreCategory(@PathVariable UUID id) {
        StoreCategory category = storeCategories.findById(id).orElseThrow();
        long storesUsingCategory = stores.countByCategoryIgnoreCase(category.getName());
        if (storesUsingCategory > 0)
            throw new IllegalArgumentException("Esta seção está vinculada a " + storesUsingCategory + " loja(s). Altere a categoria dessas lojas antes de excluí-la.");
        storeCategories.delete(category);
        auditService.recordCurrent("CATEGORY_DELETED", "STORE_CATEGORY", id, "Seção personalizada excluída.");
    }

    public record StoreCategoryRequest(@NotBlank @Size(max=60) String name,
                                       @NotBlank @Size(max=16) String emoji) {}

    public record AdminStoreCategoryView(UUID id, String name, String emoji, boolean builtIn) {}
}
