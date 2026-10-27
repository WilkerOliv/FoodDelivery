package br.com.fooddelivery.payments;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.catalog.StoreRepository;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.beans.factory.annotation.Qualifier;

@Service
public class MarketplaceAccountService {
    private static final String MP_API="https://api.mercadopago.com";
    private final StorePaymentAccountRepository links;
    private final StoreRepository stores;
    private final MercadoPagoProperties properties;
    private final MarketplaceTokenCipher cipher;
    private final MarketplaceTokenRefreshService tokenRefresh;
    private final RestClient http;
    private final SecureRandom random=new SecureRandom();

    public MarketplaceAccountService(StorePaymentAccountRepository links, StoreRepository stores,
            MercadoPagoProperties properties, MarketplaceTokenCipher cipher,
            MarketplaceTokenRefreshService tokenRefresh,
            @Qualifier("mercadoPagoRestClient") RestClient http) {
        this.links=links;this.stores=stores;this.properties=properties;this.cipher=cipher;
        this.tokenRefresh=tokenRefresh;
        this.http=http;
    }

    @Transactional
    public String authorizationUrl(UUID storeId, UUID ownerId) {
        requireOAuth();
        Store store=stores.findById(storeId).filter(s->s.getOwner().getId().equals(ownerId))
                .orElseThrow(()->new org.springframework.security.access.AccessDeniedException("Loja não encontrada nesta conta."));
        StorePaymentAccount link=links.findByStoreId(storeId).orElseGet(()->new StorePaymentAccount(store));
        String state=randomToken(32),verifier=randomToken(48),challenge=Base64.getUrlEncoder().withoutPadding()
                .encodeToString(sha256(verifier.getBytes(StandardCharsets.US_ASCII)));
        link.beginAuthorization(sha256Hex(state),cipher.encrypt(verifier));links.save(link);
        return UriComponentsBuilder.fromUriString("https://auth.mercadopago.com/authorization")
                .queryParam("client_id",properties.clientId()).queryParam("response_type","code")
                .queryParam("platform_id","mp").queryParam("state",state)
                .queryParam("redirect_uri",properties.redirectUri()).queryParam("code_challenge",challenge)
                .queryParam("code_challenge_method","S256").build().encode().toUriString();
    }

    @Transactional
    public void completeAuthorization(String code,String state) {
        requireOAuth();
        StorePaymentAccount link=links.findByStateHashAndStateExpiresAtAfter(sha256Hex(state),Instant.now())
                .orElseThrow(()->new IllegalArgumentException("A autorização expirou. Volte à loja e tente conectar novamente."));
        String verifier=cipher.decrypt(link.codeVerifierEncrypted());
        Map<String,Object> body=Map.of("client_id",properties.clientId(),"client_secret",properties.clientSecret(),
                "code",code,"grant_type","authorization_code","redirect_uri",properties.redirectUri(),
                "code_verifier",verifier,"test_token",Boolean.toString(properties.sandbox()));
        Map<String,Object> response=post("/oauth/token",null,body,"Não conseguimos conectar a conta Mercado Pago.");
        link.completeAuthorization(text(response,"user_id"),cipher.encrypt(text(response,"access_token")),
                cipher.encrypt(text(response,"refresh_token")),number(response,"expires_in",15552000));
        links.save(link);
    }

    @Transactional
    public String accessTokenForStore(UUID storeId) {
        if(!properties.checkoutConfigured())throw new IllegalStateException("A integração do Mercado Pago ainda não foi configurada pelo administrador.");
        StorePaymentAccount link=links.findByStoreId(storeId)
                .orElseThrow(()->new IllegalStateException("Esta loja ainda não conectou sua conta Mercado Pago."));
        if(link.accessTokenEncrypted()==null)throw new IllegalStateException("Esta loja ainda não conectou sua conta Mercado Pago.");
        String encryptedAccessToken=link.accessTokenEncrypted();
        if(link.getTokenExpiresAt()==null||!link.getTokenExpiresAt().isAfter(Instant.now().plusSeconds(300)))
            encryptedAccessToken=tokenRefresh.refreshIfNeeded(storeId);
        return cipher.decrypt(encryptedAccessToken);
    }

    @Transactional(readOnly=true)
    public ConnectionStatus status(UUID storeId,UUID ownerId) {
        stores.findById(storeId).filter(s->s.getOwner().getId().equals(ownerId))
                .orElseThrow(()->new org.springframework.security.access.AccessDeniedException("Loja não encontrada nesta conta."));
        return links.findByStoreId(storeId).filter(l->l.accessTokenEncrypted()!=null)
                .map(l->new ConnectionStatus(true,l.getMpUserId(),l.getTokenExpiresAt()))
                .orElseGet(()->new ConnectionStatus(false,null,null));
    }

    private Map<String,Object> post(String path,String bearer,Map<String,?> body,String error) {
        try {
            RestClient.RequestBodySpec request=http.post().uri(MP_API+path).contentType(org.springframework.http.MediaType.APPLICATION_JSON);
            if(bearer!=null)request.header("Authorization","Bearer "+bearer);
            return request.body(body).retrieve().body(new ParameterizedTypeReference<>(){});
        } catch(RestClientException e) { throw new IllegalStateException(error+" Confira as credenciais e a conexão e tente novamente."); }
    }

    private void requireOAuth(){if(!properties.oauthConfigured())throw new IllegalStateException("O administrador precisa configurar MP_CLIENT_ID e MP_CLIENT_SECRET no arquivo de ambiente.");}
    private String randomToken(int size){byte[] bytes=new byte[size];random.nextBytes(bytes);return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);}
    private static byte[] sha256(byte[] bytes){try{return MessageDigest.getInstance("SHA-256").digest(bytes);}catch(Exception e){throw new IllegalStateException(e);}}
    private static String sha256Hex(String value){return java.util.HexFormat.of().formatHex(sha256(value.getBytes(StandardCharsets.UTF_8)));}
    private static String text(Map<String,Object> body,String key){Object value=body==null?null:body.get(key);if(value==null||value.toString().isBlank())throw new IllegalStateException("O Mercado Pago não retornou os dados esperados da autorização.");return value.toString();}
    private static long number(Map<String,Object> body,String key,long fallback){Object value=body==null?null:body.get(key);return value instanceof Number n?n.longValue():fallback;}
    public record ConnectionStatus(boolean connected,String marketplaceAccountId,Instant expiresAt){}
}
