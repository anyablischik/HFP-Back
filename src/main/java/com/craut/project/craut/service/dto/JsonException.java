package com.craut.project.craut.service.dto;

public class JsonException extends RuntimeException {

    public JsonException(final String message) {
        super(message);
    }

    public JsonException(final String message, Throwable exception) {
        super(message, exception);
    }
}
