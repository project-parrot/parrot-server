package com.fx.user.domain

import com.fx.user.application.`in`.dto.ProfileUpdateCommand
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

    fun updateProfile(updateCommand: ProfileUpdateCommand): Profile {
        return this.copy(
            nickname = updateCommand.nickname,
            statusMessage = updateCommand.statusMessage,
            isPrivate = updateCommand.isPrivate,
            mediaId =  updateCommand.mediaId
        )
    }

    companion object {

        @JvmStatic
        fun createProfile(userId: Long, nickname: String, mediaId: Long?): Profile {
            return Profile(
                userId = userId,
                nickname = nickname,
                isPrivate = false,
                mediaId = mediaId,
            )
        }

        @JvmStatic
        fun createOAuthProfile(userId: Long, nickname: String): Profile {
            return Profile(
                userId = userId,
                nickname = nickname,
                isPrivate = false
            )
        }

    }

}