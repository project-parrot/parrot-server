package com.fx.chat.adapter.out.persistence

import com.fx.chat.adapter.out.persistence.document.ChatRoomDocument
import com.fx.chat.adapter.out.persistence.repository.ChatRoomMongoRepository
import com.fx.chat.application.port.out.ChatRoomPersistencePort
import com.fx.chat.domain.ChatRoom
import com.fx.global.annotation.hexagonal.PersistenceAdapter

@PersistenceAdapter
class ChatRoomPersistenceAdapter(
    private val chatRoomMongoRepository: ChatRoomMongoRepository
) : ChatRoomPersistencePort {

    override fun saveChatRoom(chatRoom: ChatRoom) =
        chatRoomMongoRepository.save(ChatRoomDocument.from(chatRoom)).toDomain()

}