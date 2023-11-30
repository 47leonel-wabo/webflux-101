package com.wbt.webflux101.service;

import com.wbt.webflux101.dto.request.MultiplyRequest;
import com.wbt.webflux101.dto.response.MathResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public record ReactiveMathService() {

    public Mono<MathResponse> reactiveSquare(final Integer number) {
        return Mono.fromSupplier(() -> number * number)
                .map(i -> new MathResponse(LocalDateTime.now(), i));
    }

    public Flux<MathResponse> reactiveTable(final Integer number) {
        return Flux.range(1, 10)
                // .doOnNext(integer -> SleepUtil.sleepThread(1)) // blocking sleep way
                .delayElements(Duration.ofSeconds(1)) // non blocking sleep
                .map(i -> new MathResponse(LocalDateTime.now(), number * i))
                .doOnNext(System.out::println);
    }

    public Mono<MathResponse> multiply(final Mono<MultiplyRequest> request) {
        return request.map(r -> new MathResponse(LocalDateTime.now(), r.a() * r.b()));
    }

}
