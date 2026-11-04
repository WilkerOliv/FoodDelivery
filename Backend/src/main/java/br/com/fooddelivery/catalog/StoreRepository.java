package br.com.fooddelivery.catalog;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select store from Store store where store.id=:id")
    Optional<Store> findByIdForUpdate(@org.springframework.data.repository.query.Param("id") UUID id);
    List<Store> findByActiveTrueAndApprovedTrue();
    List<Store> findByOwnerId(UUID ownerId);
    long countByCategoryIgnoreCase(String category);
    Optional<Store> findByCoverImageUrl(String coverImageUrl);
}
