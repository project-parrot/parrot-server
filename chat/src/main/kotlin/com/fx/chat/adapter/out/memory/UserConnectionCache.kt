package com.fx.chat.adapter.out.memory

import com.fx.chat.application.port.out.memory.UserConnectionCachePort
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class UserConnectionCache(
    private val redisTemplate: ReactiveStringRedisTemplate
) : UserConnectionCachePort {

    private val ops = redisTemplate.opsForSet()

    override fun addUserToRoom(userId: Long, chatRoomId: String, serverId: String): Mono<Boolean> {
        val key = buildKey(chatRoomId)
        val value = buildValue(userId, serverId)
        return ops.add(key, value).map { it > 0 }
    }

    override fun removeUserFromRoom(userId: Long, chatRoomId: String, serverId: String): Mono<Boolean> {
        val key = buildKey(chatRoomId)
        val value = buildValue(userId, serverId)
        return ops.remove(key, value).map { it > 0 }
    }

    override fun getRoomMembers(chatRoomId: String): Mono<Set<String>> {
        val key = buildKey(chatRoomId)
        return ops.members(key).collectList().map { it.toSet() }
    }

    private fun buildKey(chatRoomId: String): String =
        "chatroom:$chatRoomId:members"

    private fun buildValue(userId: Long, serverId: String): String =
        "$userId:$serverId"

}