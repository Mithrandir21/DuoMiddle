package com.duopoints.errorhandling

import org.postgresql.util.PSQLException
import org.postgresql.util.ServerErrorMessage
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

import java.util.Collections

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(NullResultException::class)
    protected fun handleNullResultException(): ResponseEntity<*> = ResponseEntity<Any>(HttpStatus.NOT_FOUND)

    @ExceptionHandler(ConflictException::class)
    protected fun handleConflictException(ex: ConflictException): ResponseEntity<*> =
            ResponseEntity(Collections.singletonMap("conflict", ex.conflictReason), HttpStatus.CONFLICT)

    @ExceptionHandler(NoMatchingRowException::class)
    protected fun handleNoMatchingRowException(ex: NoMatchingRowException): ResponseEntity<*> =
            ResponseEntity(Collections.singletonMap("no_match_found", ex.matchNotFoundCause), HttpStatus.NOT_FOUND)

    @ExceptionHandler(PSQLException::class)
    protected fun handlePSQLException(ex: PSQLException): ResponseEntity<*> =
            ResponseEntity<Map<String, ServerErrorMessage>>(Collections.singletonMap("error_cause", ex.serverErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR)
}
