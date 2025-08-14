package com.fx.post.exception.handler

import com.fx.global.api.Api
import com.fx.global.api.ErrorCodeIfs
import com.fx.post.exception.PostException
import com.fx.post.exception.PostMediaException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class PostMediaExceptionHandler {
    private val log = LoggerFactory.getLogger(PostMediaExceptionHandler::class.java)

    @ExceptionHandler(PostMediaException::class)
    fun handlePostMediaException(e: PostMediaException): ResponseEntity<Api<ErrorCodeIfs>> {
        log.error("", e)
        return Api.ERROR(e.errorCodeIfs)
    }
}