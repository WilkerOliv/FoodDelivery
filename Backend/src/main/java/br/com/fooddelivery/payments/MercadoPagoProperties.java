package br.com.fooddelivery.payments;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="app.payment.mercadopago")
public record MercadoPagoProperties(String clientId, String clientSecret, String redirectUri,
        String frontendUrl, String tokenEncryptionKey, String webhookSecret,
        String notificationBaseUrl, boolean sandbox, boolean testWebhookSignature, String currency) {
    public boolean oauthConfigured() { return present(clientId) && present(clientSecret) && present(redirectUri); }
    public boolean checkoutConfigured() { return oauthConfigured() && present(tokenEncryptionKey); }
    public boolean webhookConfigured() { return present(webhookSecret); }
    public boolean hasNotificationUrl() { return present(notificationBaseUrl); }
    private static boolean present(String value) { return value != null && !value.isBlank(); }
}
