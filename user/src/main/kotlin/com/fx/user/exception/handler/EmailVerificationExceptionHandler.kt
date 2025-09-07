package com.fx.user.exception.handler

import com.fx.global.api.Api
import com.fx.global.api.ErrorCodeIfs
import com.fx.user.exception.EmailVerificationException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class EmailVerificationExceptionHandler {

    private val log = LoggerFactory.getLogger(EmailVerificationExceptionHandler::class.java)

    @ExceptionHandler(EmailVerificationException::class)
    fun handleUserException(e: EmailVerificationException): ResponseEntity<Api<ErrorCodeIfs>> {
        log.error("", e)
        return Api.ERROR(e.errorCodeIfs)
    }

}