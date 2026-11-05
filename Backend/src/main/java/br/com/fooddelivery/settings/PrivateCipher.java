package br.com.fooddelivery.settings;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.util.*;
import java.nio.*;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
@Component
public class PrivateCipher {
 private final String encodedKey;private final SecureRandom random=new SecureRandom();
 public PrivateCipher(@Value("${app.private.encryption-key:}")String key){encodedKey=key;}
 public boolean configured(){try{return Base64.getDecoder().decode(encodedKey).length==32;}catch(RuntimeException invalid){return false;}}
 private SecretKeySpec key(){if(!configured())throw new IllegalStateException("Configure PRIVATE_CONFIG_ENCRYPTION_KEY no servidor antes de proteger integrações ou ativar segundo fator.");return new SecretKeySpec(Base64.getDecoder().decode(encodedKey),"AES");}
 public String fingerprint(String context,String value){try{Mac mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(key().getEncoded(),"HmacSHA256"));return HexFormat.of().formatHex(mac.doFinal((context+":"+value).getBytes(StandardCharsets.UTF_8)));}catch(Exception error){throw new IllegalStateException("Não foi possível proteger o identificador.");}}
 public String encrypt(String context,String value){try{byte[] nonce=new byte[12];random.nextBytes(nonce);Cipher cipher=Cipher.getInstance("AES/GCM/NoPadding");cipher.init(Cipher.ENCRYPT_MODE,key(),new GCMParameterSpec(128,nonce));cipher.updateAAD(context.getBytes(StandardCharsets.UTF_8));byte[] body=cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));return Base64.getEncoder().encodeToString(ByteBuffer.allocate(12+body.length).put(nonce).put(body).array());}catch(IllegalStateException known){throw known;}catch(Exception error){throw new IllegalStateException("Não foi possível proteger a configuração.");}}
 public String decrypt(String context,String value){try{byte[] packed=Base64.getDecoder().decode(value);if(packed.length<28)throw new IllegalArgumentException();Cipher cipher=Cipher.getInstance("AES/GCM/NoPadding");cipher.init(Cipher.DECRYPT_MODE,key(),new GCMParameterSpec(128,Arrays.copyOfRange(packed,0,12)));cipher.updateAAD(context.getBytes(StandardCharsets.UTF_8));return new String(cipher.doFinal(Arrays.copyOfRange(packed,12,packed.length)),StandardCharsets.UTF_8);}catch(Exception error){throw new IllegalStateException("Não foi possível abrir a configuração protegida. Confira a chave privada do servidor.");}}
}
