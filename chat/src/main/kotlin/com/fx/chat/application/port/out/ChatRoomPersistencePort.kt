package com.fx.chat.application.port.out

import com.fx.chat.domain.ChatRoom

interface ChatRoomPersistencePort {

    fun saveChatRoom(chatRoom: ChatRoom): ChatRoom

}