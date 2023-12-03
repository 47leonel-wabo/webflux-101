package com.wbt.webflux101.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .filter((request, next) -> { // passing authorization
                    final var clientRequest = ClientRequest.from(request)
                            .headers(httpHeaders -> httpHeaders.setBearerAuth("secret-jwt-token-here!!"))
                            .build();
                    return next.exchange(clientRequest);
                })
                .build();
    }
}
