package com.fx.user.adapter.`in`.web.dto.follow

import com.fx.user.domain.FollowStatus
import com.fx.user.application.out.persistence.dto.FollowUserInfo
import java.time.LocalDateTime

data class FollowUserResponse(
    val followId: Long,
    val userId: Long,
    val nickname: String,
    val profileImageUrl: String?,
    val status: FollowStatus,
    val followCreatedAt: LocalDateTime
) {

    companion object {
        @JvmStatic
        fun from(followUserInfoList: List<FollowUserInfo>): List<FollowUserResponse> =
            followUserInfoList.map { info ->
                FollowUserResponse(
                    followId = info.followId,
                    userId = info.userId,
                    nickname = info.nickname,
                    profileImageUrl = info.profileImageUrl,
                    status = info.status,
                    followCreatedAt = info.followCreatedAt
                )
            }
    }

}