package com.wbt.webflux101;

import com.wbt.webflux101.dto.response.MathResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

public class GetSingleResponseTest extends BaseTestClass {
    @Autowired
    private WebClient webClient;

    @Test
    void blockingTest() {
        MathResponse mathResponse = this.webClient
                .get()
                .uri("/reactive/math/square/{input}", 5)
                .retrieve()
                .bodyToMono(MathResponse.class)
                .block();
        System.out.println(mathResponse);
    }
}
