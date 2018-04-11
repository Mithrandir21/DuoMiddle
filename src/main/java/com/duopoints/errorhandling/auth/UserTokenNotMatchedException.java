package com.duopoints.errorhandling.auth;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserTokenNotMatchedException extends RuntimeException {
    public final String tokenMismatchReason;

    public UserTokenNotMatchedException(String tokenMismatchReason) {
        this.tokenMismatchReason = tokenMismatchReason;
    }
}
