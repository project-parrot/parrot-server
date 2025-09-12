package com.fx.chat.application.port.out

import com.fx.chat.domain.ChatRoom
import reactor.core.publisher.Mono

interface ChatRoomPersistencePort {

    fun saveChatRoom(chatRoom: ChatRoom): Mono<ChatRoom>

    fun findById(chatRoomId: String): Mono<ChatRoom>

}