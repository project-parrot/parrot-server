package com.fx.chat.adapter.`in`.socket

import com.fx.chat.application.port.`in`.ChatSocketCommandUseCase
import com.fx.global.exception.UnauthorizedException
import com.fx.global.exception.errorcode.UnauthorizedErrorCode
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

@Component
class ChatSocketAdapter(
    private val chatSocketCommandUseCase: ChatSocketCommandUseCase
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        val userId = session.handshakeInfo.headers.getFirst("X-User-Id")?.toLongOrNull()
            ?: throw UnauthorizedException(UnauthorizedErrorCode.INVALID_USER_ID_FORMAT)

        val chatRoomId = UriComponentsBuilder.fromUri(session.handshakeInfo.uri)
            .build().queryParams["chatRoomId"]?.firstOrNull()
            ?: return session.close()

        return chatSocketCommandUseCase.connect(userId, chatRoomId, session)
    }

}