package com.fx.chat.config.socket

import com.fx.chat.adapter.out.cache.UserConnectionCache
import com.fx.chat.application.port.out.ChatMessagePersistencePort
import com.fx.chat.application.port.out.ChatRoomPersistencePort
import com.fx.chat.application.port.out.ChatRoomUserPersistencePort
import com.fx.chat.domain.ChatMessage
import com.fx.chat.domain.MessageType
import com.fx.chat.exception.ChatRoomException
import com.fx.chat.exception.errorcode.ChatRoomErrorCode
import com.fx.global.exception.UnauthorizedException
import com.fx.global.exception.errorcode.UnauthorizedErrorCode
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@Component
class WebSocketHandler(
    private val userConnectionCache: UserConnectionCache,
    private val chatRoomUserPersistencePort: ChatRoomUserPersistencePort,
    private val chatMessagePersistencePort: ChatMessagePersistencePort
) : WebSocketHandler {

    private val log = LoggerFactory.getLogger(WebSocketHandler::class.java)
    private val sessions = ConcurrentHashMap<String, MutableSet<WebSocketSession>>()

    override fun handle(session: WebSocketSession): Mono<Void> = Mono.defer {
        val userId = getUserId(session)
        val chatRoomId = UriComponentsBuilder.fromUri(session.handshakeInfo.uri)
            .build().queryParams["chatRoomId"]?.firstOrNull()
            ?: return@defer session.close()

        val serverId = getServerId()
        log.info("WebSocket 연결: userId={}, chatRoomId={}, serverId={}", userId, chatRoomId, serverId)

        val roomSessions = sessions.computeIfAbsent(chatRoomId) { mutableSetOf() }
        roomSessions += session

        val cleanup = session.closeStatus()
            .doOnNext {
                roomSessions -= session
                userConnectionCache.removeUserFromRoom(userId, chatRoomId, serverId)
                    .doOnNext { log.info("User removed from Redis cache") }
                    .subscribe()
            }

        userConnectionCache.addUserToRoom(userId, chatRoomId, serverId)
            .flatMap {
                log.info(if (it) "User added to Redis cache" else "User already in Redis cache")
                chatRoomUserPersistencePort.findByChatRoomIdAndUserId(chatRoomId, userId)
                    .switchIfEmpty(
                        Mono.defer {
                            log.warn("User {} not found in chatRoom {}", userId, chatRoomId)
                            Mono.error(ChatRoomException(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND))
                        }
                    )
            }
            .thenMany(
                session.receive()
                    .map { it.payloadAsText }
                    .flatMap { message ->
                        log.info("수신 메시지: {}", message)
                        val broadcast = roomSessions
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

    private fun getUserId(session: WebSocketSession) =
        session.handshakeInfo.headers.getFirst("X-User-Id")?.toLongOrNull()
            ?: throw UnauthorizedException(UnauthorizedErrorCode.INVALID_USER_ID_FORMAT)

    private fun getServerId() =
        System.getenv("HOSTNAME") ?: "localhost"

}