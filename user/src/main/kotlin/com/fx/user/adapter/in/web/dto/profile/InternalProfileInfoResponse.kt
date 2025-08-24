package com.fx.user.adapter.`in`.web.dto.profile

import com.fx.user.application.out.persistence.dto.FollowUserInfo
import com.fx.user.domain.ProfileInfo

data class InternalProfileInfoResponse(
    val userId: Long,
    val nickname: String?= null,
    val profileImageUrl: String?= null
) {
    companion object {
        fun fromProfileInfo(profileInfo: ProfileInfo): InternalProfileInfoResponse {
            return InternalProfileInfoResponse(
                userId = profileInfo.userId,
                nickname = profileInfo.nickname,
                profileImageUrl = profileInfo.profileImageUrl
            )
        }

        fun fromProfileInfo(profileInfoList: List<ProfileInfo>): List<InternalProfileInfoResponse> {
            return profileInfoList.map { fromProfileInfo(it) }
        }

        fun fromFollowUserInfo(followUserInfo: FollowUserInfo): InternalProfileInfoResponse {
            return InternalProfileInfoResponse(
                userId = followUserInfo.userId,
                nickname = followUserInfo.nickname,
                profileImageUrl = followUserInfo.profileImageUrl
            )
        }

        fun fromFollowUserInfo(followUserInfoList: List<FollowUserInfo>): List<InternalProfileInfoResponse> {
            return followUserInfoList.map { fromFollowUserInfo(it) }
        }
    }
}
