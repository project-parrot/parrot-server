package com.fx.chatconsumer.application.port.`in`.message

import com.fx.global.dto.ChatMessageEventDto

interface MessageConsumerUseCase {
    
    fun consumeMessage(chatMessageEventDto: ChatMessageEventDto)

}