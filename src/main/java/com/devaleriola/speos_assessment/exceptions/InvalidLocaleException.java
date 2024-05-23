package com.devaleriola.speos_assessment.exceptions;

import java.io.IOException;

public class InvalidLocaleException extends IOException {
    public InvalidLocaleException(String localeString) {
        super("The locale " + localeString + " is invalid");
    }

}
