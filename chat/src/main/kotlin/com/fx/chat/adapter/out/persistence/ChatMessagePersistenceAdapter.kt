package com.fx.chat.adapter.out.persistence

import com.fx.chat.adapter.out.persistence.document.ChatMessageDocument
import com.fx.chat.adapter.out.persistence.repository.ChatMessageMongoRepository
import com.fx.chat.application.port.out.ChatMessagePersistencePort
import com.fx.chat.domain.ChatMessage
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ChatMessagePersistenceAdapter(
    private val chatMessageMongoRepository: ChatMessageMongoRepository
) : ChatMessagePersistencePort {

    override fun saveChatMessage(chatMessage: ChatMessage): Mono<ChatMessage> =
        chatMessageMongoRepository.save(ChatMessageDocument.from(chatMessage)).map { it.toDomain() }

}