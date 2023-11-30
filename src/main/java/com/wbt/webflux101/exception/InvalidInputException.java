package com.wbt.webflux101.exception;

public class InvalidInputException extends RuntimeException {
    private final static Integer errorCode = 101; // some fake error code (learning purpose)
    private final Integer input;

    public InvalidInputException(String message, final Integer input) {
        super(message);
        this.input = input;
    }

    public Integer getInput() {
        return input;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
