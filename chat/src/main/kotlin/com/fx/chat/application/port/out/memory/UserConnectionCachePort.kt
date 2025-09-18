package com.fx.chat.application.port.out.memory

import reactor.core.publisher.Mono

interface UserConnectionCachePort {
    fun addUserToRoom(userId: Long, chatRoomId: String, serverId: String): Mono<Boolean>
    fun removeUserFromRoom(userId: Long, chatRoomId: String, serverId: String): Mono<Boolean>
    fun getRoomMembers(chatRoomId: String): Mono<Set<String>>
}