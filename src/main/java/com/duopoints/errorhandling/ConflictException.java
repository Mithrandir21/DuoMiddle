package com.duopoints.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public final String conflictReason;

    public ConflictException(String conflictReason) {
        this.conflictReason = conflictReason;
    }
}
