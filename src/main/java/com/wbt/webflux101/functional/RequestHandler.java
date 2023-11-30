package com.wbt.webflux101.functional;

import com.wbt.webflux101.dto.response.MathResponse;
import com.wbt.webflux101.service.ReactiveMathService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public record RequestHandler(ReactiveMathService mathService) {

    public Mono<ServerResponse> squareRootHandler(final ServerRequest request) {
        // 'bodyValue()' method is used when it comes to return none publisher value
        final int variable = getVariable(request);
        return this.mathService.reactiveSquare(variable)
                .flatMap(mathResponse -> ServerResponse.ok().bodyValue(mathResponse));
    }

    public Mono<ServerResponse> tableHandler(final ServerRequest request) {
        final var value = getVariable(request);
        Flux<MathResponse> mathResponseFlux = this.mathService.reactiveTable(value);
        // 'body()' method is used when it comes to return a publisher element in ServerResponse object
        return ServerResponse.ok().body(mathResponseFlux, MathResponse.class);
    }

    private static int getVariable(ServerRequest request) {
        return Integer.parseInt(request.pathVariable("value"));
    }
}
