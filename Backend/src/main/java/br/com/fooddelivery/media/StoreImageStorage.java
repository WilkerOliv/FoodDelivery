package br.com.fooddelivery.media;

import java.io.IOException;
import java.util.Optional;

/** Private object storage. Public visibility is always decided by the catalog API. */
public interface StoreImageStorage {
    int MAX_BYTES = 5 * 1024 * 1024;
    void put(String name, byte[] image) throws IOException;
    Optional<byte[]> read(String name) throws IOException;
    void delete(String name) throws IOException;
    static void validateName(String name) {
        if (name == null || !name.matches("cover-[0-9a-fA-F-]{36}\\.png"))
            throw new IllegalArgumentException("Nome de imagem inválido.");
    }
}
