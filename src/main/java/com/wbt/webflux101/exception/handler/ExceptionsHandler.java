package com.wbt.webflux101.exception.handler;

import com.wbt.webflux101.dto.response.InvalidInputResponse;
import com.wbt.webflux101.exception.InvalidInputException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public record ExceptionsHandler() {
    @ExceptionHandler(value = {InvalidInputException.class})
    public ResponseEntity<InvalidInputResponse> invalidInputHandler(final InvalidInputException exception) {
        return ResponseEntity.badRequest().body(new InvalidInputResponse(exception.getErrorCode(), exception.getInput(), exception.getMessage()));
    }
}
