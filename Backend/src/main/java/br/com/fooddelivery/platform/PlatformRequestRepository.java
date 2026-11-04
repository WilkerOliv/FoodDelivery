package br.com.fooddelivery.platform;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface PlatformRequestRepository extends JpaRepository<PlatformRequest,UUID>{
    @org.springframework.data.jpa.repository.Query("select request.account.id from PlatformRequest request where request.id=:id")
    Optional<UUID> findAccountId(@org.springframework.data.repository.query.Param("id") UUID id);
    @org.springframework.data.jpa.repository.Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @org.springframework.data.jpa.repository.Query("select request from PlatformRequest request where request.id=:id")
    Optional<PlatformRequest> findByIdForUpdate(@org.springframework.data.repository.query.Param("id") UUID id);
    List<PlatformRequest> findByAccountIdOrderByCreatedAtDesc(UUID accountId);
    List<PlatformRequest> findAllByOrderByCreatedAtDesc();
    boolean existsByAccountIdAndRequestTypeAndStatus(UUID accountId,String type,String status);
    boolean existsByAccountIdAndRequestTypeAndRequestedRoleAndStatus(UUID accountId,String type,br.com.fooddelivery.identity.AccountRole role,String status);
    boolean existsByAccountIdAndRequestTypeAndCityIgnoreCaseAndStateIgnoreCaseAndStatus(UUID accountId,String type,String city,String state,String status);
    Optional<PlatformRequest> findFirstByStoreIdOrderByCreatedAtDesc(UUID storeId);
}
