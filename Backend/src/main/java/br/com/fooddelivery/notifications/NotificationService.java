package br.com.fooddelivery.notifications;

import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.identity.AccountRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {
    private final NotificationRepository notifications;
    private final AccountRepository accounts;

    public NotificationService(NotificationRepository notifications, AccountRepository accounts) {
        this.notifications = notifications;
        this.accounts = accounts;
    }

    @Transactional
    public void send(Account recipient, String title, String message, String kind) {
        notifications.save(new AccountNotification(recipient, title, message, kind));
    }

    /** Envia o aviso apenas se a pessoa ainda não leu outro com o mesmo título (evita uma notificação por mensagem). */
    @Transactional
    public void sendUnreadOnce(Account recipient, String title, String message, String kind) {
        if (notifications.existsByAccountIdAndTitleAndReadAtIsNull(recipient.getId(), title)) return;
        notifications.save(new AccountNotification(recipient, title, message, kind));
    }

    @Transactional(readOnly = true)
    public List<AccountNotification> list(UUID accountId) {
        return notifications.findTop100ByAccountIdOrderByCreatedAtDesc(accountId);
    }

    @Transactional
    public AccountNotification markRead(UUID accountId, UUID notificationId) {
        AccountNotification notification = notifications.findByIdAndAccountId(notificationId, accountId)
                .orElseThrow(() -> new java.util.NoSuchElementException("Notificação não encontrada."));
        notification.markRead();
        return notification;
    }

    @Transactional
    public void clear(UUID accountId) {
        notifications.deleteByAccountId(accountId);
    }

    @Transactional(readOnly = true)
    public Account account(UUID id) { return accounts.findById(id).orElseThrow(); }
}
