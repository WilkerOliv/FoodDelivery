package br.com.fooddelivery.location;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/geography")
public class GeocodingController {
    private static final long CACHE_TTL_MILLIS = Duration.ofHours(24).toMillis();
    private static final long MIN_REQUEST_INTERVAL_MILLIS = 1_100;

    private final RestClient nominatim;
    private final Map<String, CacheEntry> cache = new java.util.LinkedHashMap<>();
    private long lastProviderRequestAt;

    public GeocodingController() {
        var client = java.net.http.HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(4)).build();
        var requestFactory = new JdkClientHttpRequestFactory(client);
        requestFactory.setReadTimeout(Duration.ofSeconds(8));
        this.nominatim = RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .requestFactory(requestFactory)
                .defaultHeader("User-Agent", "FoodDelivery/1.0")
                .defaultHeader("Accept-Language", "pt-BR")
                .build();
    }

    @GetMapping("/search")
    public JsonNode search(@RequestParam("query") String query) {
        String normalized = query == null ? "" : query.trim().replaceAll("\\s+", " ");
        if (normalized.length() < 2 || normalized.length() > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe uma cidade com até 100 caracteres.");
        }
        URI uri = UriComponentsBuilder.fromPath("/search")
                .queryParam("format", "jsonv2")
                .queryParam("q", normalized + ", Brasil")
                .queryParam("countrycodes", "br")
                .queryParam("addressdetails", "1")
                .queryParam("limit", "5")
                .queryParam("accept_language", "pt-BR")
                .build().encode().toUri();
        return lookup(uri);
    }

    @GetMapping("/reverse")
    public JsonNode reverse(@RequestParam double latitude, @RequestParam double longitude) {
        if (!Double.isFinite(latitude) || latitude < -34 || latitude > 6
                || !Double.isFinite(longitude) || longitude < -74 || longitude > -32) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "As coordenadas precisam estar dentro do Brasil.");
        }
        URI uri = UriComponentsBuilder.fromPath("/reverse")
                .queryParam("format", "jsonv2")
                .queryParam("lat", latitude)
                .queryParam("lon", longitude)
                .queryParam("zoom", "18")
                .queryParam("addressdetails", "1")
                .queryParam("accept_language", "pt-BR")
                .build().encode().toUri();
        return lookup(uri);
    }

    private JsonNode lookup(URI uri) {
        String key = uri.toString();
        synchronized (this) {
            CacheEntry cached = cache.get(key);
            if (cached != null && cached.expiresAt > System.currentTimeMillis()) return cached.body;
            long now = System.currentTimeMillis();
            long waitMillis = MIN_REQUEST_INTERVAL_MILLIS - (now - lastProviderRequestAt);
            if (waitMillis > 0) throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "A busca no mapa está ocupada. Tente novamente em instantes.");
            lastProviderRequestAt = now;
            try {
                JsonNode body = nominatim.get().uri(uri).retrieve().body(JsonNode.class);
                if (body == null) throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "O serviço de mapas não retornou dados.");
                cache.put(key, new CacheEntry(body, System.currentTimeMillis() + CACHE_TTL_MILLIS));
                while (cache.size() > 2_000) cache.remove(cache.keySet().iterator().next());
                return body;
            } catch (ResponseStatusException exception) {
                throw exception;
            } catch (org.springframework.web.client.RestClientException exception) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "O serviço de mapas está indisponível. Tente novamente ou escolha uma cidade cadastrada.");
            }
        }
    }

    private record CacheEntry(JsonNode body, long expiresAt) { }
}
