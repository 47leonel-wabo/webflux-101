package com.wbt.webflux101.functional;

import com.wbt.webflux101.dto.request.MultiplyRequest;
import com.wbt.webflux101.dto.response.InvalidInputResponse;
import com.wbt.webflux101.dto.response.MathResponse;
import com.wbt.webflux101.exception.InvalidInputException;
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

    public Mono<ServerResponse> multiplicationHandler(final ServerRequest request) {
        final var inputMono = request.bodyToMono(MultiplyRequest.class);
        return ServerResponse.ok().body(this.mathService.multiply(inputMono), MathResponse.class);
    }

    private static int getVariable(ServerRequest request) {
        return Integer.parseInt(request.pathVariable("value"));
    }

    // WITH VALIDATION
    public Mono<ServerResponse> squareRootWithValidationHandler(final ServerRequest request) {
        final int variable = getVariable(request);
        if (variable < 7 || variable > 15) // NOT THE BEST TO HANDLE IT
            return ServerResponse.badRequest().bodyValue(new InvalidInputResponse(101, variable, "Only values between 7 - 15"));
        return this.mathService.reactiveSquare(variable)
                .flatMap(mathResponse -> ServerResponse.ok().bodyValue(mathResponse));
    }

    public Mono<ServerResponse> squareRootWithValidation2Handler(final ServerRequest request) {
        final int variable = getVariable(request);
        if (variable < 7 || variable > 15) // NOT THE BEST TO HANDLE IT
            return Mono.error(new InvalidInputException("Only values between 7 to 15", variable)); // the functional endpoint will intercept
        return this.mathService.reactiveSquare(variable)
                .flatMap(mathResponse -> ServerResponse.ok().bodyValue(mathResponse));
    }

}
