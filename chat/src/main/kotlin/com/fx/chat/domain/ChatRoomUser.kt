package com.fx.chat.domain

import java.time.LocalDateTime

data class ChatRoomUser(

    val id: String? = null,
    val chatRoomId: String,
    val userId: Long,
    val lastReadMessageId: String? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null

)
