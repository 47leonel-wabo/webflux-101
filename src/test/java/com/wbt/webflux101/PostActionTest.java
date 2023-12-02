package com.wbt.webflux101;

import com.wbt.webflux101.dto.request.MultiplyRequest;
import com.wbt.webflux101.dto.response.MathResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class PostActionTest extends BaseTestClass {
    @Autowired
    private WebClient webClient;

    @Test
    void postMultiplicationTest() {
        final var responseMono = this.webClient.post()
                .uri("/reactive/math/multiply")
                .bodyValue(new MultiplyRequest(3, 5))
                .retrieve()
                .bodyToMono(MathResponse.class);

        StepVerifier.create(responseMono)
                .expectNextMatches(mathResponse -> mathResponse.output().equals(15))
                .verifyComplete();
    }
}
