package com.wbt.webflux101;

import com.wbt.webflux101.dto.response.MathResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class GetMultipleResponseTest extends BaseTestClass {
    @Autowired
    private WebClient webClient;

    @Test
    void getFluxOfResponse() {
        // Streaming by default
        final var fluxResponse = webClient.get()
                .uri("/reactive/math/table/{input}", 3)
                .retrieve()
                .bodyToFlux(MathResponse.class);

        // expect table of 10 elements
        StepVerifier.create(fluxResponse).expectNextCount(10).verifyComplete();
    }
}
