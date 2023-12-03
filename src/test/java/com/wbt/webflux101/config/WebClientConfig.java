package com.wbt.webflux101.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .filter(WebClientConfig::sessionToken)
                .build();
    }

//    private static Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction next) {
//        // passing authorization
//        final var clientRequest = ClientRequest.from(request)
//                .headers(httpHeaders -> httpHeaders.setBearerAuth(""))
//                .build();
//        return next.exchange(clientRequest);
//    }

    private Mono<ClientResponse> sessionToken(ClientRequest request, ExchangeFunction next) {
        // ClientRequest is immutable
        // looking for attribute set by the client requesting this method auth --> oauth or basic
        final var clientRequest = request.attribute("auth")
                .map(o -> o.equals("oauth") ? withOAuth(request) : withBasicAuth(request))
                .orElse(request);
        return next.exchange(clientRequest);
    }

    private ClientRequest withBasicAuth(final ClientRequest request) {
        return ClientRequest.from(request)
                .headers(httpHeaders -> httpHeaders.setBasicAuth("my-username", "my-password"))
                .build();
    }

    private ClientRequest withOAuth(final ClientRequest request) {
        return ClientRequest.from(request)
                .headers(httpHeaders -> httpHeaders.setBearerAuth("lengthy-secret-jwt-token-here"))
                .build();
    }
}
