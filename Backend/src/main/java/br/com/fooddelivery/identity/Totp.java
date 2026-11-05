package br.com.fooddelivery.identity;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
/** RFC 6238 SHA-1, 30 seconds, six digits; no provider sees the secret. */
public final class Totp {
 private static final String ALPHABET="ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";
 private Totp(){}
 public static String secret(){byte[] bytes=new byte[20];new SecureRandom().nextBytes(bytes);StringBuilder result=new StringBuilder();int bits=0,buffer=0;for(byte b:bytes){buffer=(buffer<<8)|(b&255);bits+=8;while(bits>=5){bits-=5;result.append(ALPHABET.charAt((buffer>>bits)&31));}}return result.toString();}
 public static String code(String encoded,long counter){try{byte[] bytes=new byte[encoded.length()*5/8];int bits=0,buffer=0,index=0;for(char c:encoded.toCharArray()){int value=ALPHABET.indexOf(c);if(value<0)throw new IllegalArgumentException();buffer=(buffer<<5)|value;bits+=5;if(bits>=8){bits-=8;bytes[index++]=(byte)(buffer>>bits);}}
  Mac mac=Mac.getInstance("HmacSHA1");mac.init(new SecretKeySpec(bytes,"HmacSHA1"));byte[] hash=mac.doFinal(ByteBuffer.allocate(8).putLong(counter).array());int offset=hash[hash.length-1]&15;int binary=((hash[offset]&127)<<24)|((hash[offset+1]&255)<<16)|((hash[offset+2]&255)<<8)|(hash[offset+3]&255);return String.format(java.util.Locale.ROOT,"%06d",binary%1_000_000);
 }catch(Exception error){throw new IllegalStateException("Não foi possível verificar o autenticador.");}}
 public static long matchingCounter(String secret,String code,long epochSeconds,long last){if(code==null||!code.matches("[0-9]{6}"))return -1;long current=epochSeconds/30;for(long candidate=current-1;candidate<=current+1;candidate++)if(candidate>last&&java.security.MessageDigest.isEqual(code(secret,candidate).getBytes(java.nio.charset.StandardCharsets.US_ASCII),code.getBytes(java.nio.charset.StandardCharsets.US_ASCII)))return candidate;return -1;}
}
