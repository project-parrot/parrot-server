package com.fx.chat.application.port.out

import com.fx.chat.domain.ChatMessage

interface ChatMessagePersistencePort {

    fun saveChatMessage(chatMessage: ChatMessage): ChatMessage

}