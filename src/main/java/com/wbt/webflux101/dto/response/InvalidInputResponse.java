package com.wbt.webflux101.dto.response;

public record InvalidInputResponse(Integer errorCode, Integer input, String message) {
}
