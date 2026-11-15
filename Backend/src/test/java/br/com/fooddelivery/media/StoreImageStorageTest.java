package br.com.fooddelivery.media;

import java.nio.file.*;
import java.io.*;
import java.net.http.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoreImageStorageTest {
    @TempDir Path directory;
    final String name="cover-00000000-0000-0000-0000-000000000001.png";
    @Test void localRoundTripRejectsTraversalAndOverwrites() throws Exception {
        var storage=new LocalStoreImageStorage(directory.toString());
        storage.put(name,new byte[]{1,2,3});assertArrayEquals(new byte[]{1,2,3},storage.read(name).orElseThrow());
        assertThrows(IOException.class,()->storage.put(name,new byte[]{4}));
        assertThrows(IllegalArgumentException.class,()->storage.read("../private"));
        storage.delete(name);assertTrue(storage.read(name).isEmpty());
    }
    @Test void externalConfigurationRejectsUntrustedEndpointsAndMissingSecrets(){
        for(String url:new String[]{"http://project.supabase.co","https://localhost","https://project.supabase.co.evil.test","https://user@project.supabase.co","https://project.supabase.co/path"})
            assertThrows(IllegalArgumentException.class,()->new SupabaseStoreImageStorage(url,"covers","fake"));
        assertThrows(IllegalArgumentException.class,()->new SupabaseStoreImageStorage("https://project.supabase.co","../covers","fake"));
        assertThrows(IllegalArgumentException.class,()->new SupabaseStoreImageStorage("https://project.supabase.co","covers",""));
    }
    @SuppressWarnings("unchecked")
    @Test void downloadIsAuthenticatedAndProviderErrorsDoNotExposeCredentials() throws Exception {
        var client=mock(HttpClient.class);HttpResponse<byte[]> response=mock(HttpResponse.class);
        when(client.sendAsync(any(HttpRequest.class),any(HttpResponse.BodyHandler.class))).thenReturn(java.util.concurrent.CompletableFuture.completedFuture(response));
        when(response.statusCode()).thenReturn(200);when(response.body()).thenReturn(new byte[]{1,2});
        var storage=new SupabaseStoreImageStorage("https://project.supabase.co","covers","test-private-key",client);
        assertArrayEquals(new byte[]{1,2},storage.read(name).orElseThrow());
        var request=ArgumentCaptor.forClass(HttpRequest.class);verify(client).sendAsync(request.capture(),any(HttpResponse.BodyHandler.class));
        assertEquals("/storage/v1/object/authenticated/covers/"+name,request.getValue().uri().getPath());
        assertEquals("Bearer test-private-key",request.getValue().headers().firstValue("Authorization").orElseThrow());
        when(response.statusCode()).thenReturn(403);when(response.body()).thenReturn("secret".getBytes());
        assertEquals("Armazenamento indisponível.",assertThrows(IOException.class,()->storage.read(name)).getMessage());
    }
    @SuppressWarnings("unchecked")
    @Test void oversizedDownloadsAreRejectedAndDeleteUsesBucketEndpoint() throws Exception {
        var client=mock(HttpClient.class);HttpResponse<byte[]> response=mock(HttpResponse.class);
        when(client.sendAsync(any(HttpRequest.class),any(HttpResponse.BodyHandler.class))).thenReturn(java.util.concurrent.CompletableFuture.completedFuture(response));
        when(response.statusCode()).thenReturn(200);when(response.body()).thenReturn(new byte[StoreImageStorage.MAX_BYTES+1]);
        var storage=new SupabaseStoreImageStorage("https://project.supabase.co","covers","test-key",client);
        assertThrows(IOException.class,()->storage.read(name));
        clearInvocations(client);when(response.body()).thenReturn(new byte[0]);storage.delete(name);
        var request=ArgumentCaptor.forClass(HttpRequest.class);verify(client).sendAsync(request.capture(),any(HttpResponse.BodyHandler.class));
        assertEquals("DELETE",request.getValue().method());assertEquals("/storage/v1/object/covers",request.getValue().uri().getPath());
    }
    @Test void bodySubscriberCancelsBeforeBufferingOversizedContent() {
        var subscriber = new LimitedImageSubscriber();
        var subscription = mock(java.util.concurrent.Flow.Subscription.class);
        subscriber.onSubscribe(subscription);
        subscriber.onNext(java.util.List.of(java.nio.ByteBuffer.wrap(new byte[StoreImageStorage.MAX_BYTES + 1])));
        verify(subscription).cancel();
        assertTrue(subscriber.getBody().toCompletableFuture().isCompletedExceptionally());
    }
    @Test void bodySubscriberPreservesChunkOrder() {
        var subscriber = new LimitedImageSubscriber();
        subscriber.onSubscribe(mock(java.util.concurrent.Flow.Subscription.class));
        subscriber.onNext(java.util.List.of(java.nio.ByteBuffer.wrap(new byte[]{1, 2}), java.nio.ByteBuffer.wrap(new byte[]{3})));
        subscriber.onComplete();
        assertArrayEquals(new byte[]{1, 2, 3}, subscriber.getBody().toCompletableFuture().join());
    }
    @Test void demonstrationCannotConnectToExternalStorage(){
        var env=new org.springframework.mock.env.MockEnvironment().withProperty("app.media.provider","supabase");env.setActiveProfiles("homologacao");
        assertThrows(IllegalStateException.class,()->new StoreImageStorageConfiguration().storeImageStorage(env));
    }
}
