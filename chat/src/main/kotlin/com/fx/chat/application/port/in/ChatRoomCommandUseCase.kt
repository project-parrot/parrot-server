package com.fx.chat.application.port.`in`

import com.fx.chat.domain.ChatRoom

interface ChatRoomCommandUseCase {

    suspend fun createOrEnterChatRoom(requesterId: Long, chatRoomId: String?, targetUserIds: List<Long>?): ChatRoom

}