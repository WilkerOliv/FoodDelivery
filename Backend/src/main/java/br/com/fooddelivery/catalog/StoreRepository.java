package br.com.fooddelivery.catalog;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select store from Store store where store.id=:id")
    Optional<Store> findByIdForUpdate(@org.springframework.data.repository.query.Param("id") UUID id);
    List<Store> findByActiveTrueAndApprovedTrue();
    List<Store> findByOwnerId(UUID ownerId);
    org.springframework.data.domain.Page<Store> findByActiveAndApproved(boolean active, boolean approved, org.springframework.data.domain.Pageable pageable);
    org.springframework.data.domain.Page<Store> findByActiveFalse(org.springframework.data.domain.Pageable pageable);
    long countByActiveAndApproved(boolean active, boolean approved);
    long countByActiveFalse();
    long countByCategoryIgnoreCase(String category);
    Optional<Store> findByCoverImageUrl(String coverImageUrl);
}
