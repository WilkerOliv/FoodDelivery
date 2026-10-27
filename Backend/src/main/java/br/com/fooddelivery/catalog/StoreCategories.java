package br.com.fooddelivery.catalog;

import java.util.List;

public final class StoreCategories {
    private StoreCategories() {}

    public static final List<Category> ALL = List.of(
            new Category("Restaurantes", "🍽️"),
            new Category("Mercados", "🛒"),
            new Category("Conveniências", "🧃"),
            new Category("Lanchonetes", "🍔"),
            new Category("Pizzarias", "🍕"),
            new Category("Cafeterias", "☕"),
            new Category("Farmácias", "💊"),
            new Category("Beleza", "💇"),
            new Category("Barbearias", "💈"),
            new Category("Serviços", "✂️")
    );

    public static boolean isSupported(String value) {
        if (value == null) return false;
        return ALL.stream().anyMatch(category -> category.name().equalsIgnoreCase(value.trim()))
                || List.of("Restaurante", "Pizzaria", "Cafeteria", "Barbearia")
                .stream().anyMatch(category -> category.equalsIgnoreCase(value.trim()));
    }

    public record Category(String name, String emoji) {}
}
