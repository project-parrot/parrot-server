package com.fx.global.exception.handler

import com.fx.global.api.Api
import com.fx.global.api.ErrorCodeIfs
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class MethodArgumentNotValidExceptionHandler {

    private val log = LoggerFactory.getLogger(MethodArgumentNotValidExceptionHandler::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleUserException(e: MethodArgumentNotValidException): ResponseEntity<Api<String>> {
        val errorMessage = e.bindingResult.fieldErrors.joinToString(" ") { fieldError ->
            "[${fieldError.field}](은)는 ${fieldError.defaultMessage} 입력된 값: [${fieldError.rejectedValue}]"
        }
        log.error("", e)
        return Api.ERROR(errorMessage, HttpStatus.BAD_REQUEST)
    }
}