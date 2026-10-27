package br.com.fooddelivery.api;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthController {
    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of("status", "online", "service", "food-delivery-api");
    }
}
