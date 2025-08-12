package com.fx.user.exception.handler

import com.fx.global.api.Api
import com.fx.global.api.ErrorCodeIfs
import com.fx.user.exception.FollowException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class FollowExceptionHandler {

    private val log = LoggerFactory.getLogger(FollowExceptionHandler::class.java)

    @ExceptionHandler(FollowException::class)
    fun handleFollowException(e: FollowException): ResponseEntity<Api<ErrorCodeIfs>> {
        log.error("", e)
        return Api.ERROR(e.errorCodeIfs)
    }
}