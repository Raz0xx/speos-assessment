package com.devaleriola.speos_assessment.exceptions;

public class ReferenceAlreadyUsedException extends GenericException {

    public ReferenceAlreadyUsedException(String reference) {
        super("Partner with reference " + reference + " already exists.");
    }
    
}
