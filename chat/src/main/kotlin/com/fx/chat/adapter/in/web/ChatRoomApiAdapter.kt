package com.fx.chat.adapter.`in`.web

import com.fx.chat.application.port.`in`.ChatRoomCommandUseCase
import com.fx.chat.domain.ChatRoom
import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/chat-rooms")
class ChatRoomApiAdapter(
    private val chatRoomCommandUseCase: ChatRoomCommandUseCase
) {

    @GetMapping("/enter")
    suspend fun createOrEnterChatRoom(
        @AuthenticatedUser authUser: AuthUser,
        @RequestParam(required = false) chatRoomId: String?,
        @RequestParam(required = false) targetUserIds: List<Long>?
    ): ResponseEntity<Api<ChatRoom>> =
        Api.OK(chatRoomCommandUseCase.createOrEnterChatRoom(authUser.userId, chatRoomId, targetUserIds))


}