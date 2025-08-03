package com.fx.user.exception.handler

import com.fx.global.api.Api
import com.fx.global.api.ErrorCodeIfs
import com.fx.user.exception.UserException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserExceptionHandler {

    private val log = LoggerFactory.getLogger(UserExceptionHandler::class.java)

    @ExceptionHandler(UserException::class)
    fun handleUserException(e: UserException): ResponseEntity<Api<ErrorCodeIfs>> {
        log.error("", e)
        return Api.ERROR(e.errorCodeIfs)
    }

}