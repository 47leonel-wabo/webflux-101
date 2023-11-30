package com.wbt.webflux101.controller;

import com.wbt.webflux101.dto.response.MathResponse;
import com.wbt.webflux101.service.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/reactive/math"})
public record ReactiveMathController(ReactiveMathService mathService) {
    @GetMapping(path = {"/square/{input}"})
    public Mono<MathResponse> square(final @PathVariable(name = "input") Integer input) {
        return this.mathService.reactiveSquare(input);
    }

    @GetMapping(path = {"/table/{input}"})
    public Flux<MathResponse> table(final @PathVariable(name = "input") Integer input) {
        return this.mathService.reactiveTable(input);
    }

    @GetMapping(path = {"/table/{input}/stream"}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<MathResponse> tableStream(final @PathVariable(name = "input") Integer input) {
        return this.mathService.reactiveTable(input);
    }

}
