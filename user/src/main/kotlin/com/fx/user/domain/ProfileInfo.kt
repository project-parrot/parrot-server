package com.fx.user.domain

data class ProfileInfo(
    val id: Long,
    val userId: Long,
    val profileImageUrl: String?,
    val nickname: String,
    val statusMessage: String? = null,
    val isPrivate: Boolean = false,
    val followerCount: Int? = 0,
    val followingCount: Int? = 0,
    val isFollowing: Boolean?
) {
    companion object {
        @JvmStatic
        fun createMyProfileInfo(
            profile: Profile,
            profileImageUrl: String?,
            followerCount: Int,
            followingCount: Int
        ) = ProfileInfo(
            id = profile.id!!,
            userId = profile.userId,
            profileImageUrl = profileImageUrl,
            nickname = profile.nickname,
            statusMessage = profile.statusMessage,
            isPrivate = profile.isPrivate,
            followerCount = followerCount,
            followingCount = followingCount,
            isFollowing = false
        )
    }
}
