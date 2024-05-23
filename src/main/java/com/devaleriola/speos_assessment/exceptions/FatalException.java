package com.devaleriola.speos_assessment.exceptions;

public class FatalException extends GenericException {

    public FatalException(String message) {
        super(message);
    }

    public FatalException(String message, Exception exception) {
        super(message, exception);
    }
}
