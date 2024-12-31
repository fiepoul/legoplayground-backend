package com.legoassistant.legoassibackend.exception;


public class FileProcessingException extends LegoAppException {
    public FileProcessingException(String message) {
        super(message);
    }

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
