package com.fx.user.domain

import java.time.LocalDateTime

data class Profile(
    val id: Long? = null,
    val userId: Long,
    val mediaId: Long? = null,
    val nickname: String,
    val statusMessage: String? = null,
    val isPrivate: Boolean = false,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {

    companion object {

        @JvmStatic
        fun createProfile(userId: Long, nickname: String): Profile {
            return Profile(
                userId = userId,
                nickname = nickname,
                isPrivate = false
            )
        }

    }

}