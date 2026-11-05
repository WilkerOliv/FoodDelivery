package br.com.fooddelivery.identity;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TotpTest {
 static final String SECRET="GEZDGNBVGY3TQOJQGEZDGNBVGY3TQOJQ";
 @Test void followsRfc6238Sha1Vectors(){assertEquals("287082",Totp.code(SECRET,59/30));assertEquals("081804",Totp.code(SECRET,1111111109L/30));assertEquals("050471",Totp.code(SECRET,1111111111L/30));assertEquals("005924",Totp.code(SECRET,1234567890L/30));}
 @Test void rejectsReplayAndOutOfWindowCodes(){assertEquals(1,Totp.matchingCounter(SECRET,"287082",59,-1));assertEquals(-1,Totp.matchingCounter(SECRET,"287082",59,1));assertEquals(-1,Totp.matchingCounter(SECRET,"287082",300,-1));assertEquals(-1,Totp.matchingCounter(SECRET,"abc",59,-1));}
 @Test void createsDistinctBase32Secrets(){String a=Totp.secret(),b=Totp.secret();assertTrue(a.matches("[A-Z2-7]{32}"));assertNotEquals(a,b);}
}
