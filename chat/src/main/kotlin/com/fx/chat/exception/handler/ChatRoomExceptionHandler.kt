package com.fx.chat.exception.handler

import com.fx.chat.exception.ChatRoomException
import com.fx.global.api.Api
import com.fx.global.api.ErrorCodeIfs
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ChatRoomExceptionHandler {

    private val log = LoggerFactory.getLogger(ChatRoomExceptionHandler::class.java)

    @ExceptionHandler(ChatRoomException::class)
    fun handleChatRoomException(e: ChatRoomException): ResponseEntity<Api<ErrorCodeIfs>> {
        log.error("", e)
        return Api.ERROR(e.errorCodeIfs)
    }
}