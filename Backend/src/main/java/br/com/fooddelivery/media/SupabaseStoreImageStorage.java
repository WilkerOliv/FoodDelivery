package br.com.fooddelivery.media;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.Optional;

/** No public bucket or signed URL: authorization remains in the backend. */
public final class SupabaseStoreImageStorage implements StoreImageStorage {
    private final URI base;
    private final String key;
    private final HttpClient client;
    public SupabaseStoreImageStorage(String projectUrl,String bucket,String key) {
        this(projectUrl,bucket,key,HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10))
            .followRedirects(HttpClient.Redirect.NEVER).build());
    }
    SupabaseStoreImageStorage(String projectUrl,String bucket,String key,HttpClient client) {
        URI project=URI.create(projectUrl);
        if(!"https".equals(project.getScheme())||project.getHost()==null||!project.getHost().matches("[a-z0-9-]+\\.supabase\\.co")
            ||project.getPort()!=-1||project.getUserInfo()!=null||project.getQuery()!=null||project.getFragment()!=null
            ||!(project.getPath().isEmpty()||project.getPath().equals("/")))
            throw new IllegalArgumentException("Informe a URL HTTPS do projeto Supabase.");
        if(!bucket.matches("[a-z0-9][a-z0-9-]{0,62}")||key==null||key.isBlank())
            throw new IllegalArgumentException("Configure o bucket e a chave privada do armazenamento.");
        this.base=URI.create("https://"+project.getHost()+"/storage/v1/object/"+bucket+"/");this.key=key;this.client=client;
    }
    private HttpRequest.Builder request(String name) {
        StoreImageStorage.validateName(name);
        return HttpRequest.newBuilder(base.resolve(name)).timeout(Duration.ofSeconds(20))
            .header("Authorization","Bearer "+key).header("apikey",key);
    }
    private HttpResponse<byte[]> send(HttpRequest request) throws IOException {
        var pending = client.sendAsync(request, info -> new LimitedImageSubscriber());
        try {
            // Includes the entire body, unlike a timeout that ends when headers arrive.
            return pending.get(20, java.util.concurrent.TimeUnit.SECONDS);
        } catch (InterruptedException error) {
            pending.cancel(true);
            Thread.currentThread().interrupt();
            throw new IOException("Armazenamento interrompido.");
        } catch (java.util.concurrent.TimeoutException | java.util.concurrent.ExecutionException error) {
            pending.cancel(true);
            throw new IOException("Armazenamento indisponível.");
        }
    }
    public void put(String name,byte[] image) throws IOException {
        if(image.length>MAX_BYTES)throw new IOException("Imagem acima do limite.");
        var response=send(request(name).header("Content-Type","image/png").header("x-upsert","false")
            .POST(HttpRequest.BodyPublishers.ofByteArray(image)).build());
        if(response.statusCode()<200||response.statusCode()>=300)throw new IOException("Armazenamento recusou o envio.");
    }
    public Optional<byte[]> read(String name) throws IOException {
        var response=send(request(name).uri(URI.create(base.toString().replace("/object/", "/object/authenticated/")+name)).GET().build());
            if(response.statusCode()==404)return Optional.empty();
            if(response.statusCode()!=200)throw new IOException("Armazenamento indisponível.");
            byte[] image=response.body();
            if(image.length>MAX_BYTES)throw new IOException("Imagem acima do limite.");
            return Optional.of(image);
    }
    public void delete(String name) throws IOException {
        var response=send(request(name).uri(URI.create(base.toString().replaceAll("/$", ""))).header("Content-Type","application/json")
            .method("DELETE",HttpRequest.BodyPublishers.ofString("{\"prefixes\":[\""+name+"\"]}")).build());
        if(response.statusCode()!=404&&(response.statusCode()<200||response.statusCode()>=300))throw new IOException("Não foi possível remover a imagem.");
    }
}
