package com.wbt.webflux101;

import com.wbt.webflux101.dto.response.InvalidInputResponse;
import com.wbt.webflux101.dto.response.MathResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

public class ExchangeTest extends BaseTestClass {

    @Autowired
    private WebClient webClient;

    @Test
    void badRequestTest() {
        // This path only expect values between 7 and 15
        // 'exchange' is same as 'retrieve' but with more information about the request going on
        final var responseMono = this.webClient.get()
                .uri("/reactive/valid/math/square/{input}/reactive/throw2", 8)
                .exchangeToMono(clientResponse -> {
                    if (clientResponse.statusCode().isSameCodeAs(HttpStatusCode.valueOf(402)))
                        return clientResponse.bodyToMono(InvalidInputResponse.class);
                    else
                        return clientResponse.bodyToMono(MathResponse.class);
                });

        StepVerifier.create(responseMono).expectNextCount(1).verifyComplete();
    }

}
