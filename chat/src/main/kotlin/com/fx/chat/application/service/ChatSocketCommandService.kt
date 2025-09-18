package com.fx.chat.application.service

import com.fx.chat.adapter.out.memory.UserConnectionCache
import com.fx.chat.application.port.`in`.ChatSocketCommandUseCase
import com.fx.chat.application.port.out.ChatMessagePersistencePort
import com.fx.chat.application.port.out.ChatRoomUserPersistencePort
import com.fx.chat.application.port.out.memory.SessionStorePort
import com.fx.chat.application.port.out.memory.UserConnectionCachePort
import com.fx.chat.domain.ChatMessage
import com.fx.chat.domain.MessageType
import com.fx.chat.exception.ChatRoomException
import com.fx.chat.exception.errorcode.ChatRoomErrorCode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ChatSocketCommandService(
    private val chatRoomUserPersistencePort: ChatRoomUserPersistencePort,
    private val chatMessagePersistencePort: ChatMessagePersistencePort,
    private val sessionStorePort: SessionStorePort,
    private val userConnectionCachePort: UserConnectionCachePort,
) : ChatSocketCommandUseCase {


    private val log = LoggerFactory.getLogger(ChatSocketCommandService::class.java)

    override fun connect(userId: Long, chatRoomId: String, session: WebSocketSession): Mono<Void> {
        val serverId = System.getenv("HOSTNAME") ?: "localhost"

        sessionStorePort.addSession(chatRoomId, session)

        val cleanup = session.closeStatus()
            .doOnNext {
                sessionStorePort.removeSession(chatRoomId, session)
                userConnectionCachePort.removeUserFromRoom(userId, chatRoomId, serverId).subscribe()
            }

        return userConnectionCachePort.addUserToRoom(userId, chatRoomId, serverId)
            .flatMap {
                chatRoomUserPersistencePort.findByChatRoomIdAndUserId(chatRoomId, userId)
                    .switchIfEmpty(Mono.error(ChatRoomException(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND)))
            }
            .thenMany(
                session.receive()
                    .map { it.payloadAsText }
                    .flatMap { message ->
                        log.info("수신 메시지: {}", message)

                        val broadcast = sessionStorePort.getSessions(chatRoomId)
                            .filter { it != session }
                            .map { it.send(Mono.just(it.textMessage(message))) }
                            .let { Flux.merge(it) }

                        chatMessagePersistencePort.saveChatMessage(
                            ChatMessage(
                                chatRoomId = chatRoomId,
                                senderId = userId,
                                content = message,
                                type = MessageType.TEXT
                            )
                        ).thenMany(broadcast)
                    }
            )
            .then()
            .and(cleanup)
    }
}