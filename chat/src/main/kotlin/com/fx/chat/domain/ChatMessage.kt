package com.fx.chat.domain

import java.time.LocalDateTime

data class ChatMessage(

    val id: String? = null,
    val chatRoomId: String,
    val senderId: Long,
    val content: String,
    val type: MessageType,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null

)
