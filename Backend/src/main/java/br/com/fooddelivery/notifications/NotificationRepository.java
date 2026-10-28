package br.com.fooddelivery.notifications;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<AccountNotification, UUID> {
    List<AccountNotification> findTop100ByAccountIdOrderByCreatedAtDesc(UUID accountId);
    Optional<AccountNotification> findByIdAndAccountId(UUID id, UUID accountId);
    long deleteByAccountId(UUID accountId);
    boolean existsByAccountIdAndTitleAndReadAtIsNull(UUID accountId, String title);
}
