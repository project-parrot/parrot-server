package com.fx.chat.application.port.`in`

import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

interface ChatSocketCommandUseCase {

    fun connect(userId: Long, chatRoomId: String, session: WebSocketSession): Mono<Void>

}