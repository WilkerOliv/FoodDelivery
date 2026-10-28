package br.com.fooddelivery.identity;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Locale;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordRecoveryService {
    private final AccountRepository accounts;
    private final RecoveryCodeRepository codes;
    private final RecoveryMessageSender sender;
    private final PasswordEncoder encoder;
    private final SecureRandom random = new SecureRandom();

    public PasswordRecoveryService(AccountRepository accounts, RecoveryCodeRepository codes,
            RecoveryMessageSender sender, PasswordEncoder encoder) {
        this.accounts = accounts; this.codes = codes; this.sender = sender; this.encoder = encoder;
    }

    @Transactional
    public void requestPasswordReset(String email, String channel) {
        accounts.findByEmailForUpdate(normalize(email)).filter(Account::isActive).ifPresent(account ->
                issue(account, "PASSWORD_RESET", channel, false));
    }

    @Transactional
    public boolean resetPassword(String email, String channel, String rawCode, String newPassword) {
        Account account = accounts.findByEmailForUpdate(normalize(email)).filter(Account::isActive).orElse(null);
        if (account == null) return false;
        RecoveryCode code = latest(account, "PASSWORD_RESET");
        if (code == null || !"EMAIL".equals(channel) && !"WHATSAPP".equals(channel)) return false;
        Instant now = Instant.now();
        if (!code.isReadyAt(now) || !code.getChannel().equals(channel)) return false;
        if (!encoder.matches(rawCode, code.getCodeHash())) { code.recordFailedAttempt(); return false; }
        code.consume(now);
        account.changePassword(encoder.encode(newPassword));
        return true;
    }

    @Transactional
    public void requestPhoneVerification(Account account) {
        if (account.getPhone() == null || account.getPhone().isBlank()) return;
        issue(account, "PHONE_VERIFY", "WHATSAPP", true);
    }

    @Transactional
    public boolean verifyPhone(Account account, String rawCode) {
        RecoveryCode code = latest(account, "PHONE_VERIFY");
        if (code == null || !code.isReadyAt(Instant.now())) return false;
        if (!encoder.matches(rawCode, code.getCodeHash())) { code.recordFailedAttempt(); return false; }
        code.consume(Instant.now()); account.markPhoneVerified(); return true;
    }

    private void issue(Account account, String purpose, String channel, boolean phoneVerification) {
        if (!"EMAIL".equals(channel) && !"WHATSAPP".equals(channel)) return;
        if ("WHATSAPP".equals(channel) && phoneVerification && (account.getPhone() == null || account.getPhone().isBlank())) return;
        if ("WHATSAPP".equals(channel) && !phoneVerification && !account.isPhoneVerified()) return;
        Instant now = Instant.now();
        RecoveryCode previous = latest(account, purpose);
        if (previous != null && previous.getNextAllowedAt().isAfter(now)) return;
        codes.deleteForPurpose(account.getId(), purpose);
        String code = String.format(Locale.ROOT, "%06d", random.nextInt(1_000_000));
        codes.save(new RecoveryCode(account, purpose, channel, encoder.encode(code), now));
        try {
            if ("EMAIL".equals(channel)) sender.sendEmail(account.getEmail(), code);
            else sender.sendWhatsApp(account.getPhone(), code);
        } catch (RuntimeException deliveryFailure) {
            // No credential is sent over the API and provider details do not escape this service.
            codes.deleteForPurpose(account.getId(), purpose);
        }
    }

    private RecoveryCode latest(Account account, String purpose) {
        return codes.findLatest(account.getId(), purpose).stream().findFirst().orElse(null);
    }

    private static String normalize(String email) { return email == null ? "" : email.trim().toLowerCase(Locale.ROOT); }
}
