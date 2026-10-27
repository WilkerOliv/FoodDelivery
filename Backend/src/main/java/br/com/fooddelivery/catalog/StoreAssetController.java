package br.com.fooddelivery.catalog;

import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.util.Iterator;
import java.awt.image.BufferedImage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class StoreAssetController {
    private static final int MAX_IMAGE_BYTES = 5 * 1024 * 1024;
    private final StoreRepository stores;
    private final Path assetDirectory;

    public StoreAssetController(StoreRepository stores,
                                @Value("${app.media.store-assets-dir:./data/store-assets}") String directory) {
        this.stores = stores;
        this.assetDirectory = Path.of(directory).toAbsolutePath().normalize();
    }

    @PostMapping("/merchant/stores/{id}/cover")
    @PreAuthorize("hasRole('MERCHANT')")
    public Store updateCover(@PathVariable UUID id, @Valid @RequestBody CoverRequest request, Authentication authentication) {
        Store store = stores.findById(id).orElseThrow();
        UUID accountId = UUID.fromString(((Jwt) authentication.getPrincipal()).getSubject());
        if (!store.getOwner().getId().equals(accountId)) throw new AccessDeniedException("Loja não pertence à sua conta.");
        String value = request.dataUrl();
        String prefix = "data:image/png;base64,";
        if (value == null || value.length() > ((MAX_IMAGE_BYTES + 2L) / 3L) * 4L + prefix.length()
                || !value.startsWith(prefix))
            throw new IllegalArgumentException("Envie uma imagem PNG válida.");
        byte[] image;
        try { image = Base64.getDecoder().decode(value.substring(prefix.length())); }
        catch (IllegalArgumentException invalid) { throw new IllegalArgumentException("A imagem enviada não é válida."); }
        if (image.length == 0 || image.length > MAX_IMAGE_BYTES || !isPng(image))
            throw new IllegalArgumentException("A imagem deve ser PNG válido e ter até 5 MB.");
        image = sanitizePng(image);

        String fileName = "cover-" + UUID.randomUUID() + ".png";
        try {
            Files.createDirectories(assetDirectory);
            Files.write(assetDirectory.resolve(fileName), image);
        } catch (IOException exception) {
            throw new IllegalStateException("Não foi possível salvar a imagem da loja.");
        }
        String oldFile = store.getCoverImageUrl();
        store.setCoverImageUrl("/api/v1/store-assets/" + fileName);
        Store saved = stores.save(store);
        deleteOldCover(oldFile, fileName);
        return saved;
    }

    @GetMapping(value = "/store-assets/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<FileSystemResource> readCover(@PathVariable String fileName) {
        if (!fileName.matches("cover-[0-9a-fA-F-]{36}\\.png")) return ResponseEntity.notFound().build();
        Path file = assetDirectory.resolve(fileName).normalize();
        if (!file.startsWith(assetDirectory) || !Files.isRegularFile(file)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(java.time.Duration.ofDays(30)).cachePublic())
                .contentType(MediaType.IMAGE_PNG).body(new FileSystemResource(file));
    }

    private static boolean isPng(byte[] image) {
        return image.length >= 8 && (image[0] & 255) == 137 && image[1] == 80 && image[2] == 78
                && image[3] == 71 && image[4] == 13 && image[5] == 10 && image[6] == 26 && image[7] == 10;
    }

    private static byte[] sanitizePng(byte[] image) {
        try (ImageInputStream input = ImageIO.createImageInputStream(new ByteArrayInputStream(image))) {
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);
            if (!readers.hasNext()) throw new IllegalArgumentException("A imagem enviada não pôde ser lida.");
            ImageReader reader = readers.next();
            try {
                reader.setInput(input, true, true);
                int width = reader.getWidth(0), height = reader.getHeight(0);
                if (width < 1 || height < 1 || width > 4096 || height > 4096 || (long) width * height > 16_000_000)
                    throw new IllegalArgumentException("A imagem precisa ter no máximo 4.096 × 4.096 pixels.");
                BufferedImage decoded = reader.read(0);
                ByteArrayOutputStream clean = new ByteArrayOutputStream();
                if (!ImageIO.write(decoded, "png", clean) || clean.size() > MAX_IMAGE_BYTES)
                    throw new IllegalArgumentException("A imagem convertida ultrapassa o limite de 5 MB.");
                return clean.toByteArray();
            } finally { reader.dispose(); }
        } catch (IOException exception) {
            throw new IllegalArgumentException("A imagem enviada não pôde ser lida.");
        }
    }

    private void deleteOldCover(String oldUrl, String newName) {
        if (oldUrl == null || oldUrl.equals("/api/v1/store-assets/" + newName)) return;
        String prefix = "/api/v1/store-assets/";
        if (!oldUrl.startsWith(prefix)) return;
        String oldName = oldUrl.substring(prefix.length());
        if (!oldName.matches("cover-[0-9a-fA-F-]{36}\\.png")) return;
        try { Files.deleteIfExists(assetDirectory.resolve(oldName).normalize()); } catch (IOException ignored) { }
    }

    public record CoverRequest(@NotBlank @Size(max = 7_000_600) String dataUrl) {}
}
