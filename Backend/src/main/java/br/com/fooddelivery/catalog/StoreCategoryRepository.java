package br.com.fooddelivery.catalog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, UUID> {
    List<StoreCategory> findAllByOrderByNameAsc();
    Optional<StoreCategory> findBySlugIgnoreCase(String slug);
    Optional<StoreCategory> findByNameIgnoreCase(String name);
}
