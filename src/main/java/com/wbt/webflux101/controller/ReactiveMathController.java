package com.wbt.webflux101.controller;

import com.wbt.webflux101.dto.request.MultiplyRequest;
import com.wbt.webflux101.dto.response.MathResponse;
import com.wbt.webflux101.service.ReactiveMathService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping(path = {"/multiply"})
    public Mono<MathResponse> multiply(final @RequestBody Mono<MultiplyRequest> requestMono) {
        return this.mathService.multiply(requestMono);
    }

}
