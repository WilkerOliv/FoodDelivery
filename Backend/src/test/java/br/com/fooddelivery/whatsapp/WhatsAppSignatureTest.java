package br.com.fooddelivery.whatsapp;
import org.junit.jupiter.api.Test;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import static org.junit.jupiter.api.Assertions.*;
class WhatsAppSignatureTest {
 @Test void acceptsOnlyHmacOfExactRawBody()throws Exception{byte[] body="{\"message\":\"teste\"}".getBytes(java.nio.charset.StandardCharsets.UTF_8);String secret="fake-meta-app-secret-for-tests";Mac mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8),"HmacSHA256"));String signature="sha256="+java.util.HexFormat.of().formatHex(mac.doFinal(body));assertTrue(WhatsAppController.validSignature(body,secret,signature));assertFalse(WhatsAppController.validSignature("modified".getBytes(),secret,signature));assertFalse(WhatsAppController.validSignature(body,"other",signature));assertFalse(WhatsAppController.validSignature(body,secret,null));assertFalse(WhatsAppController.validSignature(body,"",signature));}
 @Test void normalizesBrazilianPhonesAndRejectsMalformedOnes(){assertEquals("5511999999999",WhatsAppCommerce.normalizePhone("(11) 99999-9999"));assertEquals("5511999999999",WhatsAppCommerce.normalizePhone("+55 11 99999-9999"));assertThrows(IllegalArgumentException.class,()->WhatsAppCommerce.normalizePhone("123"));}
}
