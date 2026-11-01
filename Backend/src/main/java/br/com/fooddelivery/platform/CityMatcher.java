package br.com.fooddelivery.platform;

import java.text.Normalizer;
import java.util.Locale;

/** Shared rule for deciding whether a customer and a store belong to the same city. */
public final class CityMatcher {
    private CityMatcher() {}

    public static boolean sameCity(String customerCity, String customerState, String storeCity, String storeState) {
        return normalize(customerCity).equals(normalize(storeCity))
                && normalize(customerState).equals(normalize(storeState))
                && !normalize(customerCity).isEmpty()
                && !normalize(customerState).isEmpty();
    }

    public static boolean allowPayAfterService(boolean sellerAllowsIt, String customerCity, String customerState,
            String storeCity, String storeState) {
        return sellerAllowsIt && sameCity(customerCity, customerState, storeCity, storeState);
    }

    private static String normalize(String value) {
        if (value == null) return "";
        return Normalizer.normalize(value.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .toUpperCase(Locale.ROOT);
    }
}
