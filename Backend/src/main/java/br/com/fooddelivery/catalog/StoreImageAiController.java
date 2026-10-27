package br.com.fooddelivery.catalog;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/merchant/stores/{storeId}/ai")
@PreAuthorize("hasRole('MERCHANT')")
public class StoreImageAiController {
    private final StoreRepository stores;
    private final RestClient openAi;
    private final String apiKey;
    private final String model;
    private final Map<UUID, ArrayDeque<Instant>> recentGenerations = new HashMap<>();

    public StoreImageAiController(StoreRepository stores, @Qualifier("openAiRestClient") RestClient openAi,
                                  @Value("${app.ai.openai.api-key:}") String apiKey,
                                  @Value("${app.ai.openai.image-model:gpt-image-2.5-sunburst}") String model) {
        this.stores = stores;
        this.openAi = openAi;
        this.apiKey = apiKey;
        this.model = model;
    }

    @PostMapping("/cover")
    public GeneratedImage generateCover(@PathVariable UUID storeId, @Valid @RequestBody PromptRequest request,
                                        Authentication authentication) {
        UUID accountId = UUID.fromString(((Jwt) authentication.getPrincipal()).getSubject());
        Store store = stores.findById(storeId).orElseThrow();
        if (!store.getOwner().getId().equals(accountId)) throw new org.springframework.security.access.AccessDeniedException("Loja não pertence à sua conta.");
        if (apiKey.isBlank()) throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                "A geração por IA ainda não foi configurada pela administração.");
        String prompt = request.prompt().trim();
        reserveGeneration(accountId);

        String imagePrompt = "Crie uma imagem horizontal de capa para a loja local \"" + store.getName()
                + "\" na categoria \"" + store.getCategory() + "\". Direção visual: fotografia comercial atraente e autêntica, composição limpa, sem palavras, letras, logotipos ou marcas registradas. Pedido do comerciante: " + prompt;
        String safetyId = safetyIdentifier(accountId);
        try {
            JsonNode result = openAi.post().uri("/images/generations")
                    .header("Authorization", "Bearer " + apiKey)
                    .body(Map.of("model", model, "prompt", imagePrompt, "size", "1536x1024",
                            "quality", "medium", "output_format", "png", "n", 1,
                            "safety_identifier", safetyId))
                    .retrieve().body(JsonNode.class);
            String base64 = result == null ? null : result.path("data").path(0).path("b64_json").asText(null);
            if (base64 == null || base64.isBlank()) throw new IllegalStateException("O serviço de imagens não retornou uma imagem.");
            return new GeneratedImage("data:image/png;base64," + base64);
        } catch (org.springframework.web.client.RestClientResponseException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "A geração de imagem está indisponível no momento.");
        }
    }

    private synchronized void reserveGeneration(UUID accountId) {
        Instant now = Instant.now();
        Instant cutoff = now.minusSeconds(3600);
        boolean knownAccount = recentGenerations.containsKey(accountId);
        if (!knownAccount && recentGenerations.size() >= 10000) {
            recentGenerations.entrySet().removeIf(entry -> {
                while (!entry.getValue().isEmpty() && entry.getValue().peekFirst().isBefore(cutoff)) entry.getValue().removeFirst();
                return entry.getValue().isEmpty();
            });
            if (recentGenerations.size() >= 10000)
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "O limite de solicitações de imagem foi atingido. Tente mais tarde.");
        }
        ArrayDeque<Instant> entries = recentGenerations.computeIfAbsent(accountId, ignored -> new ArrayDeque<>());
        while (!entries.isEmpty() && entries.peekFirst().isBefore(cutoff)) entries.removeFirst();
        if (entries.size() >= 5) throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                "Você atingiu o limite de 5 imagens por hora. Tente novamente mais tarde.");
        entries.addLast(now);
    }

    private static String safetyIdentifier(UUID accountId) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(accountId.toString().getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (java.security.NoSuchAlgorithmException impossible) { throw new IllegalStateException(impossible); }
    }

    public record PromptRequest(@NotBlank @Size(max = 1200) String prompt) {}
    public record GeneratedImage(String dataUrl) {}
}
