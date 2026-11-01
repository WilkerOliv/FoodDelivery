package br.com.fooddelivery.catalog;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CatalogItemRepository extends JpaRepository<CatalogItem, UUID> {
    List<CatalogItem> findByStoreIdAndActiveTrue(UUID storeId);
    List<CatalogItem> findByStoreId(UUID storeId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update CatalogItem item set item.active = false, item.version = item.version + 1 " +
            "where item.id = :itemId and item.active = true and item.store.id in " +
            "(select store.id from Store store where store.owner.id = :ownerId)")
    int deactivateOwned(@Param("itemId") UUID itemId, @Param("ownerId") UUID ownerId);
}
