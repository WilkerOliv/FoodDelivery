package br.com.fooddelivery.payments;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

@Component
public class MarketplaceTokenCipher {
    private static final int NONCE_BYTES = 12;
    private final MercadoPagoProperties properties;
    private final SecureRandom random = new SecureRandom();
    public MarketplaceTokenCipher(MercadoPagoProperties properties) { this.properties = properties; }

    public String encrypt(String value) {
        try {
            byte[] nonce = new byte[NONCE_BYTES]; random.nextBytes(nonce);
            byte[] plaintext=value.getBytes(java.nio.charset.StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key(), new GCMParameterSpec(128, nonce));
            return Base64.getEncoder().encodeToString(ByteBuffer.allocate(nonce.length + cipher.getOutputSize(plaintext.length))
                    .put(nonce).put(cipher.doFinal(plaintext)).array());
        } catch (Exception e) { throw new IllegalStateException("Não foi possível proteger as credenciais da loja.", e); }
    }

    public String decrypt(String value) {
        try {
            byte[] packed=Base64.getDecoder().decode(value);ByteBuffer buffer=ByteBuffer.wrap(packed);
            byte[] nonce=new byte[NONCE_BYTES];buffer.get(nonce);byte[] ciphertext=new byte[buffer.remaining()];buffer.get(ciphertext);
            Cipher cipher=Cipher.getInstance("AES/GCM/NoPadding");cipher.init(Cipher.DECRYPT_MODE,key(),new GCMParameterSpec(128,nonce));
            return new String(cipher.doFinal(ciphertext),java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) { throw new IllegalStateException("Não foi possível acessar com segurança a conta de pagamentos desta loja.", e); }
    }

    private SecretKeySpec key() {
        String encoded=properties.tokenEncryptionKey();
        if(encoded==null||encoded.isBlank())throw new IllegalStateException("Configure MP_TOKEN_ENCRYPTION_KEY antes de conectar contas de pagamento.");
        byte[] bytes=Base64.getDecoder().decode(encoded);
        if(bytes.length!=32)throw new IllegalStateException("MP_TOKEN_ENCRYPTION_KEY deve ser uma chave Base64 de 32 bytes.");
        return new SecretKeySpec(bytes,"AES");
    }
}
