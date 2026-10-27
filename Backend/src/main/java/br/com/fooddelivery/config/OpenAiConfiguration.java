package br.com.fooddelivery.config;

import java.net.http.HttpClient;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class OpenAiConfiguration {
    @Bean
    RestClient openAiRestClient() {
        HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(client);
        requestFactory.setReadTimeout(Duration.ofSeconds(120));
        return RestClient.builder().baseUrl("https://api.openai.com/v1").requestFactory(requestFactory).build();
    }
}
