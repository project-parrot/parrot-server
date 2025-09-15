package com.fx.chat.adapter.out.persistence

import com.fx.chat.adapter.out.persistence.document.ChatRoomUserDocument
import com.fx.chat.adapter.out.persistence.repository.ChatRoomUserMongoRepository
import com.fx.chat.application.port.out.ChatRoomUserPersistencePort
import com.fx.chat.domain.ChatRoomUser
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class ChatRoomUserPersistenceAdapter(
    private val chatRoomUserMongoRepository: ChatRoomUserMongoRepository
) : ChatRoomUserPersistencePort {

    override fun saveChatRoomUsers(chatRoomUsers: List<ChatRoomUser>): Mono<List<ChatRoomUser>> =
        chatRoomUserMongoRepository.saveAll(chatRoomUsers.map { ChatRoomUserDocument.from(it) })
            .map { it.toDomain() }
            .collectList()

    override fun findByChatRoomIdAndUserId(chatRoomId: String, userId: Long): Mono<ChatRoomUser> =
        chatRoomUserMongoRepository.findByChatRoomIdAndUserId(chatRoomId, userId).map { it.toDomain() }

}