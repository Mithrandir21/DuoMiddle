package com.duopoints.errorhandling.auth


import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
data class TokenNoValidException(val errorCode: String, val detailMessage: String?, val throwable: Throwable?) : RuntimeException("ErrorCode: $errorCode|Message:$detailMessage", throwable)