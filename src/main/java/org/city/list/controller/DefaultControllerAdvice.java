package org.city.list.controller;

import org.city.list.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class DefaultControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(DefaultControllerAdvice.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, new ErrorMessage(ex.getMessage()), new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> notFoundException(NotFoundException ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, new ErrorMessage(ex.getMessage()), new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> commonExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        return handleExceptionInternal(ex, new ErrorMessage(INTERNAL_SERVER_ERROR.getReasonPhrase()), new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }

    private record ErrorMessage(String error) { }
}
