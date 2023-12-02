package com.wbt.webflux101;

import com.wbt.webflux101.dto.response.MathResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.test.StepVerifier;

public class BadRequestTest extends BaseTestClass {

    @Autowired
    private WebClient webClient;

    @Test
    void badRequestTest() {
        // This path only expect values between 7 and 15
        final var responseMono = this.webClient.get()
                .uri("/reactive/valid/math/square/{input}/reactive/throw2", 5)
                .retrieve()
                .bodyToMono(MathResponse.class)
                .doOnError(throwable -> System.out.println(throwable.getMessage()));

        StepVerifier.create(responseMono)
                .verifyError(WebClientResponseException.BadRequest.class);
    }
}
