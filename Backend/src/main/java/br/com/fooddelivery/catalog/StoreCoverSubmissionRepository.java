package br.com.fooddelivery.catalog;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import jakarta.persistence.LockModeType;
public interface StoreCoverSubmissionRepository extends JpaRepository<StoreCoverSubmission,UUID> {
    List<StoreCoverSubmission> findByStoreIdAndStatus(UUID storeId,String status);
    Optional<StoreCoverSubmission> findFirstByStoreIdOrderByCreatedAtDesc(UUID storeId);
    List<StoreCoverSubmission> findByStatusOrderByCreatedAtAsc(String status);
    long countByStoreIdAndCreatedAtAfter(UUID storeId,java.time.Instant since);
    @Query("select submission.store.id from StoreCoverSubmission submission where submission.id=:id")
    Optional<UUID> findStoreId(@Param("id") UUID id);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select submission from StoreCoverSubmission submission where submission.id=:id")
    Optional<StoreCoverSubmission> findByIdForUpdate(@Param("id") UUID id);
}
