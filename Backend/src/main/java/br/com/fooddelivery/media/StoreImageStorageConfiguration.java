package br.com.fooddelivery.media;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
public class StoreImageStorageConfiguration {
    @Bean StoreImageStorage storeImageStorage(Environment env) {
        String provider=env.getProperty("app.media.provider","local");
        if(env.matchesProfiles("homologacao","dev-h2")&&!provider.equals("local"))
            throw new IllegalStateException("Demonstração e testes locais não podem usar armazenamento externo.");
        return switch(provider) {
            case "local" -> new LocalStoreImageStorage(env.getProperty("app.media.store-assets-dir","./data/store-assets"));
            case "supabase" -> new SupabaseStoreImageStorage(env.getRequiredProperty("app.media.supabase.url"),
                env.getProperty("app.media.supabase.bucket","store-covers"),env.getRequiredProperty("app.media.supabase.key"));
            default -> throw new IllegalStateException("Provedor de imagens não reconhecido.");
        };
    }
}
