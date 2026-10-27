package br.com.fooddelivery.payments;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import java.time.Duration;
import java.net.http.HttpClient;

@Configuration
@EnableConfigurationProperties(MercadoPagoProperties.class)
public class MercadoPagoConfiguration {
    @Bean("mercadoPagoRestClient")
    RestClient mercadoPagoRestClient() {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(client);
        requestFactory.setReadTimeout(Duration.ofSeconds(15));
        return RestClient.builder().requestFactory(requestFactory).build();
    }
}
