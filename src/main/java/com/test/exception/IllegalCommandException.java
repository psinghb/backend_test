package com.test.exception;

public class IllegalCommandException extends RuntimeException {

    private String message;
    public IllegalCommandException(String s) {
        message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
