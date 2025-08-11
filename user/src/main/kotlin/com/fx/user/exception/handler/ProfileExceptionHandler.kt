package com.fx.user.exception.handler

import com.fx.global.api.Api
import com.fx.global.api.ErrorCodeIfs
import com.fx.user.exception.ProfileException
import com.fx.user.exception.UserException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ProfileExceptionHandler {

    private val log = LoggerFactory.getLogger(ProfileExceptionHandler::class.java)

    @ExceptionHandler(ProfileException::class)
    fun handleProfileException(e: ProfileException): ResponseEntity<Api<ErrorCodeIfs>> {
        log.error("", e)
        return Api.ERROR(e.errorCodeIfs)
    }

}