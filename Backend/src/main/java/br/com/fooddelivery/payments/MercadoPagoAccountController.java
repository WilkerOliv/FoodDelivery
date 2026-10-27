package br.com.fooddelivery.payments;

import java.util.Map;
import java.util.UUID;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/v1")
public class MercadoPagoAccountController {
    private final MarketplaceAccountService service;
    private final MercadoPagoProperties properties;
    public MercadoPagoAccountController(MarketplaceAccountService service,MercadoPagoProperties properties){this.service=service;this.properties=properties;}

    @PostMapping("/merchant/stores/{storeId}/mercadopago/connect")
    @PreAuthorize("hasRole('MERCHANT')")
    public Map<String,String> connect(@PathVariable UUID storeId,Authentication auth){
        return Map.of("authorizationUrl",service.authorizationUrl(storeId,subject(auth)));
    }

    @GetMapping("/merchant/stores/{storeId}/mercadopago/status")
    @PreAuthorize("hasRole('MERCHANT')")
    public MarketplaceAccountService.ConnectionStatus status(@PathVariable UUID storeId,Authentication auth){return service.status(storeId,subject(auth));}

    @GetMapping("/payments/mercadopago/callback")
    public RedirectView callback(@RequestParam(required=false) String code,@RequestParam(required=false) String state,
            @RequestParam(required=false) String error){
        String destination=properties.frontendUrl()+"?mp=";
        try {
            if(error!=null||code==null||state==null)throw new IllegalArgumentException("Autorização cancelada.");
            service.completeAuthorization(code,state);
            return new RedirectView(destination+"connected#merchant",true);
        } catch(RuntimeException e) {
            return new RedirectView(destination+"error#merchant",true);
        }
    }

    private UUID subject(Authentication auth){return UUID.fromString(((Jwt)auth.getPrincipal()).getSubject());}
}
