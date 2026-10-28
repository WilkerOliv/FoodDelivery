package br.com.fooddelivery.catalog;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.text.Normalizer;
import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

/** Additional marketplace category created by an administrator. Built-in categories stay immutable in code. */
@Entity
@Table(name = "store_categories")
public class StoreCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(nullable = false, unique = true, length = 60)
    private String name;

    @Column(nullable = false, length = 16)
    private String emoji;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    protected StoreCategory() {}

    public StoreCategory(String name, String emoji) {
        this.name = name.trim();
        this.slug = slugify(name);
        this.emoji = emoji.trim();
    }

    private static String slugify(String value) {
        String normalized = Normalizer.normalize(value.trim().toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");
        String slug = normalized.replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
        if (slug.isBlank()) throw new IllegalArgumentException("Informe um nome de categoria com letras ou números.");
        return slug;
    }

    public UUID getId() { return id; }
    public String getSlug() { return slug; }
    public String getName() { return name; }
    public String getEmoji() { return emoji; }
    public Instant getCreatedAt() { return createdAt; }
}
