package br.com.fooddelivery.location;

import java.util.*;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController @Profile("homologacao") @RequestMapping("/api/v1/geography")
public class DemoGeocodingController {
    private static Map<String,Object> city(boolean iepe) {
        return Map.of("lat",iepe?"-22.66":"-22.62","lon",iepe?"-51.08":"-51.24",
            "display_name",iepe?"Iepê, São Paulo, Brasil (simulado)":"Nantes, São Paulo, Brasil (simulado)",
            "address",Map.of("city",iepe?"Iepê":"Nantes","state","São Paulo","ISO3166-2-lvl4","BR-SP",
            "postcode",iepe?"19640-000":"19645-000","country_code","br"));
    }
    @GetMapping("/search") public Object search(@RequestParam String query) {
        String q=java.text.Normalizer.normalize(query,java.text.Normalizer.Form.NFD).replaceAll("\\p{M}","").toLowerCase(Locale.ROOT);
        if(q.contains("iepe")||q.replaceAll("\\D","").startsWith("19640"))return List.of(city(true));
        if(q.contains("nantes")||q.replaceAll("\\D","").startsWith("19645"))return List.of(city(false));
        return List.of();
    }
    @GetMapping("/reverse") public Object reverse(@RequestParam double latitude,@RequestParam double longitude) {
        if(!Double.isFinite(latitude)||!Double.isFinite(longitude)||latitude < -90||latitude > 90||longitude < -180||longitude > 180)
            throw new IllegalArgumentException("Coordenadas inválidas.");
        return city(longitude > -51.16);
    }
}
