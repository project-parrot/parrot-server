package com.fx.chat.application.port.out.memory

import org.springframework.web.reactive.socket.WebSocketSession

interface SessionStorePort {

    fun addSession(chatRoomId: String, session: WebSocketSession)
    fun removeSession(chatRoomId: String, session: WebSocketSession)
    fun getSessions(chatRoomId: String): Set<WebSocketSession>

}