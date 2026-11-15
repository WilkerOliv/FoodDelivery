package br.com.fooddelivery.media;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;

public final class LocalStoreImageStorage implements StoreImageStorage {
    private final Path directory;
    public LocalStoreImageStorage(String directory) { this.directory=Path.of(directory).toAbsolutePath().normalize(); }
    private Path path(String name) { StoreImageStorage.validateName(name); return directory.resolve(name); }
    public void put(String name, byte[] image) throws IOException {
        if(image.length>MAX_BYTES)throw new IOException("Imagem acima do limite.");
        Path path=path(name);Files.createDirectories(directory);
        Files.write(path,image,StandardOpenOption.CREATE_NEW);
    }
    public Optional<byte[]> read(String name) throws IOException {
        Path path=path(name);
        if(!Files.isRegularFile(path,LinkOption.NOFOLLOW_LINKS))return Optional.empty();
        try(var input=Files.newInputStream(path)) {
            byte[] image=input.readNBytes(MAX_BYTES+1);
            if(image.length>MAX_BYTES)throw new IOException("Imagem acima do limite.");
            return Optional.of(image);
        }
    }
    public void delete(String name) throws IOException { Files.deleteIfExists(path(name)); }
}
