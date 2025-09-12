package com.fx.chat.application.port.out

import com.fx.chat.domain.ChatMessage
import reactor.core.publisher.Mono

interface ChatMessagePersistencePort {

    fun saveChatMessage(chatMessage: ChatMessage): Mono<ChatMessage>

}