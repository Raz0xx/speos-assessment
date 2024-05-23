package com.devaleriola.speos_assessment.utils;

import com.devaleriola.speos_assessment.exceptions.FatalException;
import com.devaleriola.speos_assessment.exceptions.GenericException;
import com.devaleriola.speos_assessment.exceptions.ResourceNotFoundException;
import com.devaleriola.speos_assessment.models.ExceptionResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ResponseEntityExceptionHandler.class);

    private ResponseEntity<Object> buildException(GenericException exception, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ExceptionResponse(exception, httpStatus), httpStatus);
    }

    @Override
    @ApiResponse(responseCode = "400", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
    })
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        String message = exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();
        logger.info(message);
        return this.buildException(new GenericException(message), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    @ApiResponse(responseCode = "404", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
    })
    protected ResponseEntity handleNoDelegationRightsForUserException(ResourceNotFoundException exception, WebRequest request) {
        logger.debug(exception.getMessage());
        return this.buildException(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({FatalException.class})
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
    })
    public ResponseEntity<Object> handleFatalException(FatalException exception, WebRequest request) {
        String message = exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();
        logger.error(message);
        return this.buildException(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RuntimeException.class})
    @ApiResponse(responseCode = "500", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
    })
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception, WebRequest request) {
        String message = exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();
        logger.error(message);
        return this.buildException(new FatalException(message, exception), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({GenericException.class})
    @ApiResponse(responseCode = "400", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponse.class))
    })
    public ResponseEntity<Object> handleGenericException(GenericException exception, WebRequest request) {
        String message = exception.getCause() != null ? exception.getCause().getMessage() : exception.getMessage();
        logger.info(message);
        return this.buildException(new GenericException(message), HttpStatus.BAD_REQUEST);
    }
}
