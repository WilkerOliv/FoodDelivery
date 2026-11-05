package br.com.fooddelivery.admin;
import br.com.fooddelivery.settings.IntegrationSettings;
import java.util.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
@RestController @RequestMapping("/api/v1/admin/integration-settings") @PreAuthorize("hasRole('ADMIN')")
public class IntegrationSettingsController {
 private final IntegrationSettings settings;public IntegrationSettingsController(IntegrationSettings settings){this.settings=settings;}
 @GetMapping public Object status(){return settings.status();}
 @PutMapping public Object update(Authentication auth,@Valid @RequestBody Request request){settings.update(UUID.fromString(((Jwt)auth.getPrincipal()).getSubject()),request.password(),request.values()==null?Map.of():request.values(),request.remove()==null?List.of():request.remove());return settings.status();}
 public record Request(@NotBlank @Size(max=72) String password,@Size(max=16) Map<String,String> values,@Size(max=16) List<String> remove){}
}
