package com.fx.chat.adapter.out.memory

import com.fx.chat.application.port.out.memory.SessionStorePort
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap


@Component
class InMemorySessionStore : SessionStorePort {

    private val sessions = ConcurrentHashMap<String, MutableSet<WebSocketSession>>()

    override fun addSession(chatRoomId: String, session: WebSocketSession) {
        sessions.computeIfAbsent(chatRoomId) { mutableSetOf() }.add(session)
    }

    override fun removeSession(chatRoomId: String, session: WebSocketSession) {
        sessions[chatRoomId]?.remove(session)
    }

    override fun getSessions(chatRoomId: String): Set<WebSocketSession> {
        return sessions[chatRoomId] ?: emptySet()
    }

}