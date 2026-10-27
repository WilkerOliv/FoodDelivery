package br.com.fooddelivery.payments;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import jakarta.persistence.LockModeType;

public interface StorePaymentAccountRepository extends JpaRepository<StorePaymentAccount,UUID> {
    Optional<StorePaymentAccount> findByStoreId(UUID storeId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select link from StorePaymentAccount link where link.store.id=:storeId")
    Optional<StorePaymentAccount> findByStoreIdForUpdate(@Param("storeId") UUID storeId);
    Optional<StorePaymentAccount> findByStateHashAndStateExpiresAtAfter(String stateHash,Instant now);
}
