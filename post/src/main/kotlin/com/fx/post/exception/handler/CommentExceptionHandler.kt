package com.fx.post.exception.handler

import com.fx.global.api.Api
import com.fx.global.api.ErrorCodeIfs
import com.fx.post.exception.CommentException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommentExceptionHandler {
    private val log = LoggerFactory.getLogger(CommentExceptionHandler::class.java)

    @ExceptionHandler(CommentException::class)
    fun handlePostException(e: CommentException): ResponseEntity<Api<ErrorCodeIfs>> {
        log.error("", e)
        return Api.ERROR(e.errorCodeIfs)
    }
}