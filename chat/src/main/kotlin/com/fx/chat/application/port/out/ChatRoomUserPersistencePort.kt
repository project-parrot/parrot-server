package com.fx.chat.application.port.out

import com.fx.chat.domain.ChatRoomUser
import reactor.core.publisher.Mono

interface ChatRoomUserPersistencePort {

    fun saveChatRoomUsers(chatRoomUsers: List<ChatRoomUser>): Mono<List<ChatRoomUser>>

    fun findByChatRoomIdAndUserId(chatRoomId: String, userId: Long): Mono<ChatRoomUser>

}