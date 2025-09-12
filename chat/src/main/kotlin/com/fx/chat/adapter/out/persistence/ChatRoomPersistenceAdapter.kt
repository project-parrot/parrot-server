package com.fx.chat.adapter.out.persistence

import com.fx.chat.adapter.out.persistence.document.ChatRoomDocument
import com.fx.chat.adapter.out.persistence.repository.ChatRoomMongoRepository
import com.fx.chat.application.port.out.ChatRoomPersistencePort
import com.fx.chat.domain.ChatRoom
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ChatRoomPersistenceAdapter(
    private val chatRoomMongoRepository: ChatRoomMongoRepository
) : ChatRoomPersistencePort {

    override fun saveChatRoom(chatRoom: ChatRoom): Mono<ChatRoom> =
        chatRoomMongoRepository.save(ChatRoomDocument.from(chatRoom))
            .map { it.toDomain() }

    override fun findById(chatRoomId: String): Mono<ChatRoom> =
        chatRoomMongoRepository.findById(chatRoomId)
            .map { it.toDomain() }

}