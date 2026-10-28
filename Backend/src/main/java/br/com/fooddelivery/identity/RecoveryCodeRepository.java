package br.com.fooddelivery.identity;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface RecoveryCodeRepository extends JpaRepository<RecoveryCode, UUID> {
    @Query("select c from RecoveryCode c where c.account.id = :accountId and c.purpose = :purpose order by c.createdAt desc")
    java.util.List<RecoveryCode> findLatest(@Param("accountId") UUID accountId, @Param("purpose") String purpose);
    @Modifying
    @Query("delete from RecoveryCode c where c.account.id = :accountId and c.purpose = :purpose")
    void deleteForPurpose(@Param("accountId") UUID accountId, @Param("purpose") String purpose);
}
