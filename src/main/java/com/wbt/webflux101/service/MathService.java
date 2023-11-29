package com.wbt.webflux101.service;

import com.wbt.webflux101.dto.response.MathResponse;
import com.wbt.webflux101.util.SleepUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public record MathService() {

    public MathResponse findSquare(final Integer number) {
        return new MathResponse(LocalDateTime.now(), number * number);
    }

    public List<MathResponse> multiplicationTable(final Integer number) {
        return IntStream.rangeClosed(1, 10)
                .peek(value -> SleepUtil.sleepThread(1))
                .mapToObj(value -> value * number)
                .map(integer -> new MathResponse(LocalDateTime.now(), integer))
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

}
