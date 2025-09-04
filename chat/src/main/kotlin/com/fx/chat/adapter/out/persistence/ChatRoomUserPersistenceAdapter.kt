package com.fx.chat.adapter.out.persistence

import com.fx.chat.adapter.out.persistence.document.ChatRoomUserDocument
import com.fx.chat.adapter.out.persistence.repository.ChatRoomUserMongoRepository
import com.fx.chat.application.port.out.ChatRoomUserPersistencePort
import com.fx.chat.domain.ChatRoomUser
import com.fx.global.annotation.hexagonal.PersistenceAdapter

@PersistenceAdapter
class ChatRoomUserPersistenceAdapter(
    private val chatRoomUserMongoRepository: ChatRoomUserMongoRepository
) : ChatRoomUserPersistencePort {

    override fun saveChatRoomUser(chatRoomUser: ChatRoomUser) =
        chatRoomUserMongoRepository.save(ChatRoomUserDocument.from(chatRoomUser)).toDomain()

}