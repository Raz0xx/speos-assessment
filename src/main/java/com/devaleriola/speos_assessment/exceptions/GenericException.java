package com.devaleriola.speos_assessment.exceptions;

public class GenericException extends RuntimeException {

    public GenericException(String message) {
        super(message);
    }

    public GenericException(String message, Exception exception) {
        super(message, exception);
    }
}
