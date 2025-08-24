package com.fx.user.domain

import com.fx.user.adapter.out.web.impl.dto.MediaInfo

data class ProfileInfo(
    val id: Long,
    val userId: Long,
    val mediaInfo: MediaInfo?,
    val nickname: String,
    val statusMessage: String? = null,
    val isPrivate: Boolean = false,
    val followerCount: Int? = 0,
    val followingCount: Int? = 0,
    val isFollowing: Boolean?
) {
    companion object {
        @JvmStatic
        fun createProfileInfo(
            profile: Profile,
            mediaInfo: MediaInfo?,
            followerCount: Int,
            followingCount: Int,
            isFollowing: Boolean
        ) = ProfileInfo(
            id = profile.id!!,
            userId = profile.userId,
            mediaInfo = mediaInfo,
            nickname = profile.nickname,
            statusMessage = profile.statusMessage,
            isPrivate = profile.isPrivate,
            followerCount = followerCount,
            followingCount = followingCount,
            isFollowing = isFollowing
        )
    }
}
