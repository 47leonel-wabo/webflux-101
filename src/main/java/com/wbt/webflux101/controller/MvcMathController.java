package com.wbt.webflux101.controller;

import com.wbt.webflux101.dto.response.MathResponse;
import com.wbt.webflux101.service.MathService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = {"/mvc/math"})
public record MvcMathController(MathService mathService) {
    @GetMapping(path = {"/square/{value}"})
    public MathResponse square(final @PathVariable(name = "value") Integer value) {
        return this.mathService.findSquare(value);
    }

    @GetMapping(path = {"/table/{input}"})
    public List<MathResponse> table(final @PathVariable(name = "input") Integer input) {
        return this.mathService.multiplicationTable(input);
    }
}
