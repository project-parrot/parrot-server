package com.fx.chat.domain

import java.time.LocalDateTime

data class ChatRoom(

    val id: String? = null,
    val lastMessageId: String? = null,
    val type: ChatRoomType,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null

)