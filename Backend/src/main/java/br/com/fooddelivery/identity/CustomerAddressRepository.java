package br.com.fooddelivery.identity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, UUID> {
    List<CustomerAddress> findByAccountIdOrderByDefaultAddressDescIdAsc(UUID accountId);
    Optional<CustomerAddress> findByIdAndAccountId(UUID id, UUID accountId);
    boolean existsByAccountId(UUID accountId);
}
