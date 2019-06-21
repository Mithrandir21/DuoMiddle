package com.duopoints.errorhandling

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
data class ConflictException(val conflictReason: String) : RuntimeException()