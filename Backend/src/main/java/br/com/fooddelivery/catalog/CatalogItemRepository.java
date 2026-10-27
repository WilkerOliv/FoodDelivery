package br.com.fooddelivery.catalog;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, UUID> {
    List<CatalogItem> findByStoreIdAndActiveTrue(UUID storeId);
    List<CatalogItem> findByStoreId(UUID storeId);
}
