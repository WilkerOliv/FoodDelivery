package br.com.fooddelivery.catalog;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, UUID> {
    List<Store> findByActiveTrueAndApprovedTrue();
    List<Store> findByOwnerId(UUID ownerId);
    long countByCategoryIgnoreCase(String category);
}
