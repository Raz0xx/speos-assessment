package com.devaleriola.speos_assessment.exceptions;

public class PartnerNotFoundException extends ResourceNotFoundException {

    public PartnerNotFoundException(long id) {
        super("Partner with id " + id + " not found.");
    }

}
