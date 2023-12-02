package com.wbt.webflux101.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = {"/params"})
public class ParamController {

    @GetMapping
    public Mono<String> playground(
            final @RequestParam(name = "page") Integer page,
            final @RequestParam(name = "size") Integer size) {

        return Mono.fromSupplier(() -> "Page is %s and size is %s!".formatted(page, size));
    }

}
