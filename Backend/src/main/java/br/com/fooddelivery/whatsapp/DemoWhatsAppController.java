package br.com.fooddelivery.whatsapp;
import java.util.Map;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
@RestController @Profile("homologacao") @RequestMapping("/api/v1/demo/whatsapp") @PreAuthorize("hasRole('ADMIN')")
public class DemoWhatsAppController {
 private final WhatsAppCommerce commerce;public DemoWhatsAppController(WhatsAppCommerce commerce){this.commerce=commerce;}
 @PostMapping public Object accept(@Valid @RequestBody Request r){commerce.accept(r.messageId(),r.phone(),r.text());return Map.of("simulated",true);}
 public record Request(@NotBlank @Size(max=200)String messageId,@NotBlank @Size(max=20)String phone,@NotBlank @Size(max=2000)String text){}
}
