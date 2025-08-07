package com.fx.post.exception.handler

import com.fx.global.api.Api
import com.fx.global.api.ErrorCodeIfs
import com.fx.post.exception.LikeException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class LikeExceptionHandler {
    private val log = LoggerFactory.getLogger(LikeExceptionHandler::class.java)

    @ExceptionHandler(LikeException::class)
    fun handleLikeException(e: LikeException): ResponseEntity<Api<ErrorCodeIfs>> {
        log.error("", e)
        return Api.ERROR(e.errorCodeIfs)
    }

}