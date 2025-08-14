package com.fx.media.exception.handler

import com.fx.global.api.Api
import com.fx.global.api.ErrorCodeIfs
import com.fx.media.exception.MediaException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class MediaExceptionHandler {

    private val log = LoggerFactory.getLogger(MediaExceptionHandler::class.java)

    @ExceptionHandler(MediaException::class)
    fun handleMediaException(e: MediaException): ResponseEntity<Api<ErrorCodeIfs>> {
        log.error("", e)
        return Api.ERROR(e.errorCodeIfs)
    }

}