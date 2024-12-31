package com.legoassistant.legoassibackend.exception;

public class LegoAppException extends RuntimeException {
    public LegoAppException(String message) {
        super(message);
    }

    public LegoAppException(String message, Throwable cause) {
        super(message, cause);
    }
}
