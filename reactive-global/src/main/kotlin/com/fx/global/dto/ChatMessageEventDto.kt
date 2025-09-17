package com.fx.global.dto

import java.time.LocalDateTime

data class ChatMessageEventDto(

    val chatRoomId: String,
    val senderId: Long,
    val content: String,
    val type: MessageType,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null

)
