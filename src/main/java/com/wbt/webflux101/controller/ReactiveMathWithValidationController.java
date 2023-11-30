package com.wbt.webflux101.controller;

import com.wbt.webflux101.dto.response.MathResponse;
import com.wbt.webflux101.exception.InvalidInputException;
import com.wbt.webflux101.service.ReactiveMathService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/reactive/valid/math"})
public record ReactiveMathWithValidationController(ReactiveMathService mathService) {
    @GetMapping(path = {"/square/{input}/throw"})
    public Mono<MathResponse> square(final @PathVariable(name = "input") Integer input) {
        if (input < 7 || input > 15) throw new InvalidInputException("Allowed values are from 7 to 15", input);
        return mathService.reactiveSquare(input);
    }

    @GetMapping(path = {"/square/{input}/reactive/throw"})
    public Mono<MathResponse> squareReactiveThrow(final @PathVariable(name = "input") Integer input) {
        return Mono.fromSupplier(() -> input)
                .handle((integer, synchronousSink) -> {
                    if (integer >= 7 && integer <= 15) synchronousSink.next(integer);
                    else synchronousSink.error(new InvalidInputException("Allowed values between 7 to 15", integer));
                })
                .cast(Integer.class)
                .flatMap(this.mathService::reactiveSquare);
    }


    @GetMapping(path = {"/square/{input}/reactive/throw2"})
    public Mono<ResponseEntity<MathResponse>> squareReactiveThrow2(final @PathVariable(name = "input") Integer input) {
        return Mono.fromSupplier(() -> input)
                .filter(i -> i >= 7 && i <= 15)
                .flatMap(this.mathService::reactiveSquare)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }
}
