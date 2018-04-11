package com.duopoints.errorhandling.auth;


import com.google.firebase.internal.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.Nullable;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class TokenNoValidException extends RuntimeException {
    public TokenNoValidException(@NonNull String errorCode, @NonNull String detailMessage, @Nullable Throwable throwable) {
        super("ErrorCode: " + errorCode + "|Message:" + detailMessage, throwable);
    }
}
