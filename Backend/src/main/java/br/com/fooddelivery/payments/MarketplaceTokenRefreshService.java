package br.com.fooddelivery.payments;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class MarketplaceTokenRefreshService {
    private static final String MP_API = "https://api.mercadopago.com";
    private static final long REFRESH_EARLY_SECONDS = 300;
    private final StorePaymentAccountRepository links;
    private final MercadoPagoProperties properties;
    private final MarketplaceTokenCipher cipher;
    private final RestClient http;

    public MarketplaceTokenRefreshService(StorePaymentAccountRepository links, MercadoPagoProperties properties,
            MarketplaceTokenCipher cipher, @Qualifier("mercadoPagoRestClient") RestClient http) {
        this.links = links;
        this.properties = properties;
        this.cipher = cipher;
        this.http = http;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String refreshIfNeeded(UUID storeId) {
        StorePaymentAccount link = links.findByStoreIdForUpdate(storeId)
                .orElseThrow(() -> new IllegalStateException("Esta loja ainda não conectou sua conta Mercado Pago."));
        if (link.accessTokenEncrypted() == null) {
            throw new IllegalStateException("Esta loja ainda não conectou sua conta Mercado Pago.");
        }
        if (link.getTokenExpiresAt() != null
                && link.getTokenExpiresAt().isAfter(Instant.now().plusSeconds(REFRESH_EARLY_SECONDS))) {
            return link.accessTokenEncrypted();
        }
        if (link.refreshTokenEncrypted() == null) {
            throw new IllegalStateException("A conexão Mercado Pago expirou. A loja precisa autorizar novamente.");
        }

        Map<String, Object> response;
        try {
            response = http.post().uri(MP_API + "/oauth/token")
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(Map.of("client_id", properties.clientId(), "client_secret", properties.clientSecret(),
                            "grant_type", "refresh_token", "refresh_token", cipher.decrypt(link.refreshTokenEncrypted())))
                    .retrieve().body(new ParameterizedTypeReference<>() {});
        } catch (RestClientException error) {
            throw new IllegalStateException("Não foi possível renovar a conexão Mercado Pago. Tente novamente; se persistir, a loja precisará autorizar novamente.");
        }
        link.refresh(text(response, "access_token"), cipher.encrypt(text(response, "refresh_token")),
                number(response, "expires_in", 15_552_000));
        links.save(link);
        return link.accessTokenEncrypted();
    }

    private static String text(Map<String, Object> body, String key) {
        Object value = body == null ? null : body.get(key);
        if (value == null || value.toString().isBlank()) {
            throw new IllegalStateException("O Mercado Pago não retornou os dados esperados da renovação.");
        }
        return value.toString();
    }

    private static long number(Map<String, Object> body, String key, long fallback) {
        Object value = body == null ? null : body.get(key);
        return value instanceof Number number ? number.longValue() : fallback;
    }
}
