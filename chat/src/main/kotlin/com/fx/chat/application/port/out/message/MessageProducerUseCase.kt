package com.fx.chat.application.port.out.message

import com.fx.global.dto.ChatMessageEventDto

interface MessageProducerUseCase {

    fun sendMessage(chatMessageEventDto: ChatMessageEventDto)

}