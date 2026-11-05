package br.com.fooddelivery.settings;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PrivateCipherTest {
 final PrivateCipher cipher=new PrivateCipher("ZmFrZS1kZW1vLWtleS1uZXZlci11c2UtaW4tcHJvZCE=");
 @Test void encryptsWithoutReturningPlaintextAndBindsToContext(){String encrypted=cipher.encrypt("SMTP_PASSWORD","fake-test-only");assertFalse(encrypted.contains("fake-test-only"));assertEquals("fake-test-only",cipher.decrypt("SMTP_PASSWORD",encrypted));assertThrows(IllegalStateException.class,()->cipher.decrypt("MP_CLIENT_SECRET",encrypted));}
 @Test void differentNoncesAndTamperingAreHandled(){String a=cipher.encrypt("A","value"),b=cipher.encrypt("A","value");assertNotEquals(a,b);byte[] modified=java.util.Base64.getDecoder().decode(a);modified[modified.length-1]^=1;assertThrows(IllegalStateException.class,()->cipher.decrypt("A",java.util.Base64.getEncoder().encodeToString(modified)));}
 @Test void missingMasterKeyFailsClosed(){assertFalse(new PrivateCipher("").configured());assertThrows(IllegalStateException.class,()->new PrivateCipher("").encrypt("A","B"));}
 @Test void phoneFingerprintsArePrivateAndStable(){assertEquals(cipher.fingerprint("WA","5511000000000"),cipher.fingerprint("WA","5511000000000"));assertNotEquals(cipher.fingerprint("WA","5511000000000"),cipher.fingerprint("WA","5511000000001"));}
}
