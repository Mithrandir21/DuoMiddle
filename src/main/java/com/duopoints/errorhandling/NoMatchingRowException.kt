package com.duopoints.errorhandling

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
data class NoMatchingRowException(val matchNotFoundCause: String) : RuntimeException()