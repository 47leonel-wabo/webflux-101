package com.wbt.webflux101.functional.error;

import com.wbt.webflux101.dto.response.InvalidInputResponse;
import com.wbt.webflux101.exception.InvalidInputException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

public record ExceptionHandler() {
    public static BiFunction<Throwable, ServerRequest, Mono<ServerResponse>> handle() {
        return (throwable, request) -> {
            final var ex = (InvalidInputException) throwable;
            final var response = new InvalidInputResponse(ex.getErrorCode(), ex.getInput(), ex.getMessage());
            return ServerResponse.badRequest().bodyValue(response);
        };
    }
}
