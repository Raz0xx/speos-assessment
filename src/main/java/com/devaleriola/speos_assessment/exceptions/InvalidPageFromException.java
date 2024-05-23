package com.devaleriola.speos_assessment.exceptions;

public class InvalidPageFromException extends GenericException {

    public InvalidPageFromException() {
        super("The page offset is invalid");
    }
}
