package com.devaleriola.speos_assessment.exceptions;

public class InvalidPageSizeException extends GenericException {

    public InvalidPageSizeException() {
        super("The page size is invalid");
    }
}
