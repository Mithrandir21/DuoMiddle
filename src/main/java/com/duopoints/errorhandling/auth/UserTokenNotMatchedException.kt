package com.duopoints.errorhandling.auth


import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
data class UserTokenNotMatchedException(val tokenMismatchReason: String) : RuntimeException()