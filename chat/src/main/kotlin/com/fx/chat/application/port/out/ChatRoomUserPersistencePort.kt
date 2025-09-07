package com.fx.chat.application.port.out

import com.fx.chat.domain.ChatRoomUser

interface ChatRoomUserPersistencePort {

    fun saveChatRoomUser(chatRoomUser: ChatRoomUser): ChatRoomUser

}