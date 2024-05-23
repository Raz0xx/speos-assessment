package com.devaleriola.speos_assessment.models;

import com.devaleriola.speos_assessment.exceptions.GenericException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

public class ExceptionResponse {

    @Schema(type = "int", description = "The HTTP code")
    private int code;
    @Schema(type = "int", example = "... whatever error happened ...", description = "The error message")
    private String message;

    public ExceptionResponse(GenericException exception, HttpStatus httpStatus) {
        this.code = httpStatus.value();
        this.message = exception.getMessage();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
