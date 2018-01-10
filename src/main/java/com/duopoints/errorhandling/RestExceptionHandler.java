package com.duopoints.errorhandling;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullResultException.class)
    protected ResponseEntity handleNullResultException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity handleConflictException(ConflictException ex) {
        return new ResponseEntity<>(Collections.singletonMap("conflict", ex.conflictReason), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoMatchingRowException.class)
    protected ResponseEntity handleNoMatchingRowException(NoMatchingRowException ex) {
        return new ResponseEntity<>(Collections.singletonMap("no_match_found", ex.matchNotFoundCause), HttpStatus.NOT_FOUND);
    }
}
