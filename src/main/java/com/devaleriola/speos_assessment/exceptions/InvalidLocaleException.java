package com.devaleriola.speos_assessment.exceptions;

public class InvalidLocaleException extends GenericException {
    public InvalidLocaleException(String localeString) {
        super("The locale " + localeString + " is invalid");
    }

}
