package br.com.fooddelivery.catalog;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreProfessionalRepository extends JpaRepository<StoreProfessional,UUID> {
    List<StoreProfessional> findByStoreIdOrderByNameAsc(UUID storeId);
    List<StoreProfessional> findByStoreIdAndActiveTrueOrderByNameAsc(UUID storeId);
}
