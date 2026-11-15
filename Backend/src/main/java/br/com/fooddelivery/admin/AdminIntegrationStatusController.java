package br.com.fooddelivery.admin;

import jakarta.validation.constraints.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminIntegrationStatusController {
    @Value("${spring.mail.host:}") private String smtpHost;
    @Value("${spring.mail.username:}") private String smtpUsername;
    @Value("${spring.mail.password:}") private String smtpPassword;
    @Value("${app.recovery.whatsapp.enabled:false}") private boolean whatsappEnabled;
    @Value("${app.recovery.whatsapp.phone-number-id:}") private String whatsappPhoneId;
    @Value("${app.recovery.whatsapp.access-token:}") private String whatsappToken;
    @Value("${app.payment.mercadopago.client-id:}") private String mercadoPagoClientId;
    @Value("${app.payment.mercadopago.client-secret:}") private String mercadoPagoClientSecret;
    @Value("${app.payment.mercadopago.token-encryption-key:}") private String mercadoPagoEncryptionKey;
    @Value("${app.payment.mercadopago.webhook-secret:}") private String mercadoPagoWebhookSecret;
    @Value("${app.payment.mercadopago.notification-base-url:}") private String mercadoPagoNotificationUrl;
    @org.springframework.beans.factory.annotation.Autowired private br.com.fooddelivery.settings.IntegrationSettings privateSettings;
    private String setting(String key,String fallback){return privateSettings==null?fallback:privateSettings.value(key,fallback);}
    @GetMapping("/integrations")
    public List<IntegrationView> integrations() {
        return List.of(
                integration("SMTP", "E-mail de recuperação e avisos", ready(setting("SMTP_HOST",smtpHost), setting("SMTP_USERNAME",smtpUsername), setting("SMTP_PASSWORD",smtpPassword)), "SMTP_HOST, SMTP_PORT, SMTP_USERNAME, SMTP_PASSWORD, RECOVERY_EMAIL_FROM"),
                integration("WHATSAPP", "WhatsApp Cloud para códigos e avisos", Boolean.parseBoolean(setting("WHATSAPP_CLOUD_ENABLED",Boolean.toString(whatsappEnabled))) && ready(setting("WHATSAPP_PHONE_NUMBER_ID",whatsappPhoneId),setting("WHATSAPP_ACCESS_TOKEN",whatsappToken)), "WHATSAPP_CLOUD_ENABLED, WHATSAPP_PHONE_NUMBER_ID, WHATSAPP_ACCESS_TOKEN, WHATSAPP_OTP_TEMPLATE"),
                new IntegrationView("STORE_IMAGES", "Capas enviadas pelo vendedor", true, "Upload com aprovação administrativa", "Sem API de IA; armazenamento definido no servidor; PNG/JPG ajustado para 1.200 × 600 pixels"),
                integration("MERCADOPAGO", "Checkout, pagamentos e webhooks", ready(setting("MP_CLIENT_ID",mercadoPagoClientId),setting("MP_CLIENT_SECRET",mercadoPagoClientSecret),mercadoPagoEncryptionKey,setting("MP_WEBHOOK_SECRET",mercadoPagoWebhookSecret),mercadoPagoNotificationUrl), "MP_CLIENT_ID, MP_CLIENT_SECRET, MP_TOKEN_ENCRYPTION_KEY, MP_WEBHOOK_SECRET, MP_NOTIFICATION_BASE_URL"),
                integration("VIACEP", "Consulta pública de CEP", true, "Integração pública; não exige chave"),
                integration("MAPAS", "Mapa e localização das cidades", true, "Mapa incorporado; requer conexão com a internet")
        );
    }

    private static boolean ready(String... values) { return java.util.Arrays.stream(values).allMatch(value -> value != null && !value.isBlank()); }

    private static IntegrationView integration(String id, String name, boolean ready, String variables) {
        return new IntegrationView(id, name, ready, ready ? "Configurada (conexão não homologada)" : "Configuração incompleta", variables);
    }

    public record IntegrationView(String id, String name, boolean configured, String status, String configuration) {}
}
