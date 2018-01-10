package com.duopoints.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoMatchingRowException extends RuntimeException {
    public final String matchNotFoundCause;

    public NoMatchingRowException(String matchNotFoundCause) {
        this.matchNotFoundCause = matchNotFoundCause;
    }
}
