package br.com.fooddelivery.whatsapp;
import com.fasterxml.jackson.databind.*;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
@RestController @RequestMapping("/api/v1")
public class WhatsAppController {
 private final WhatsAppCommerce commerce;private final ObjectMapper mapper;private final WhatsAppEventRepository events;private final WhatsAppReplyRepository replies;
 public WhatsAppController(WhatsAppCommerce commerce,ObjectMapper mapper,WhatsAppEventRepository events,WhatsAppReplyRepository replies){this.commerce=commerce;this.mapper=mapper;this.events=events;this.replies=replies;}
 private UUID id(Authentication a){return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());}
 @GetMapping("/account/whatsapp-link") public Object status(Authentication a){return commerce.status(id(a));}
 @PostMapping("/account/whatsapp-link") public Object bind(Authentication a,@Valid @RequestBody Password request){return commerce.bind(id(a),request.password());}
 @DeleteMapping("/account/whatsapp-link") public Object unbind(Authentication a,@Valid @RequestBody Password request){return commerce.unlink(id(a),request.password());}
 public record Password(@NotBlank @Size(max=72)String password){}
 @GetMapping(value="/webhooks/whatsapp",produces="text/plain") public String verify(@RequestParam(name="hub.mode")String mode,@RequestParam(name="hub.verify_token")String token,@RequestParam(name="hub.challenge")String challenge){String configured=commerce.setting("WHATSAPP_VERIFY_TOKEN","");if(!commerce.enabled()||!"subscribe".equals(mode)||configured.isBlank()||challenge.length()>200||!MessageDigest.isEqual(configured.getBytes(StandardCharsets.UTF_8),token.getBytes(StandardCharsets.UTF_8)))throw new ResponseStatusException(HttpStatus.FORBIDDEN);return challenge;}
 public static boolean validSignature(byte[] body,String secret,String signature){if(secret==null||secret.isBlank()||signature==null||!signature.matches("sha256=[a-fA-F0-9]{64}"))return false;try{Mac mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),"HmacSHA256"));return MessageDigest.isEqual(mac.doFinal(body),HexFormat.of().parseHex(signature.substring(7)));}catch(Exception invalid){return false;}}
 @PostMapping("/webhooks/whatsapp") public Object receive(@RequestBody byte[] body,@RequestHeader(name="X-Hub-Signature-256",required=false)String signature){if(body.length>262144)throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE);if(!commerce.enabled())throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE);if(!validSignature(body,commerce.setting("WHATSAPP_APP_SECRET",""),signature))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);try{JsonNode root=mapper.readTree(body);int count=0;for(var entry:root.path("entry"))for(var change:entry.path("changes")){var value=change.path("value");if(!commerce.setting("WHATSAPP_PHONE_NUMBER_ID","").equals(value.path("metadata").path("phone_number_id").asText()))continue;for(var message:value.path("messages")){if(++count>20)throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE);if(!message.path("type").asText().equals("text"))continue;commerce.accept(message.path("id").asText(),message.path("from").asText(),message.path("text").path("body").asText());}}return Map.of("status","accepted");}catch(java.io.IOException invalid){throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}}
 @GetMapping("/admin/whatsapp-status") @PreAuthorize("hasRole('ADMIN')") public Object admin(){return Map.of("enabled",commerce.enabled(),"incomingPending",events.countByStatus("PENDING"),"incomingFailed",events.countByStatus("FAILED"),"outgoingPending",replies.countByStatus("PENDING"),"outgoingReview",replies.countByStatus("REVIEW"));}
}
