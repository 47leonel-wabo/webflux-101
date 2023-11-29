package com.wbt.webflux101.dto.response;

import java.time.LocalDateTime;

public record MathResponse(LocalDateTime date, Integer output) {
}
