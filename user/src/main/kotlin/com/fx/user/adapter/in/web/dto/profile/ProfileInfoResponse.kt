package com.fx.user.adapter.`in`.web.dto.profile

import com.fx.user.domain.ProfileInfo

data class ProfileInfoResponse(
    val profileId: Long,
    val userId: Long,
    val profileImageUrl: String? = null,
    val nickname: String,
    val statusMessage: String? = null,
    val isPrivate: Boolean,
    val followerCount: Int? = 0,
    val followingCount: Int? = 0,
    val isFollowing: Boolean? = null
) {

    companion object {
        @JvmStatic
        fun from(profileInfo: ProfileInfo): ProfileInfoResponse =
            ProfileInfoResponse(
                profileId = profileInfo.id,
                userId = profileInfo.userId,
                profileImageUrl = profileInfo.profileImageUrl,
                nickname = profileInfo.nickname,
                statusMessage = profileInfo.statusMessage,
                isPrivate = profileInfo.isPrivate,
                followerCount = profileInfo.followerCount,
                followingCount = profileInfo.followingCount,
                isFollowing = profileInfo.isFollowing
            )
    }

}