package com.wbt.webflux101;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class ParamTest extends BaseTestClass {

    @Autowired
    private WebClient webClient;

    @Test
    void paramsTest() {
        final var responseMono = this.webClient.get()
                .uri("/params?page={page}&size={size}", 3, 2)
                .retrieve()
                .bodyToMono(String.class);

        StepVerifier.create(responseMono).expectNext("Page is 3 and size is 2!").verifyComplete();
    }
}
