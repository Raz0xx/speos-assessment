package com.devaleriola.speos_assessment.exceptions;

public class ResourceNotFoundException extends GenericException {

    public ResourceNotFoundException() {
        super("The resource couldn't be found");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
