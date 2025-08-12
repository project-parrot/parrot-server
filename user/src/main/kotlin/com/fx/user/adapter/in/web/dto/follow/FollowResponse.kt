package com.fx.user.adapter.`in`.web.dto.follow

import com.fx.user.domain.Follow
import com.fx.user.domain.FollowStatus

data class FollowResponse(
    val followId: Long,
    val status: FollowStatus
) {
    companion object {
        fun from(follow: Follow): FollowResponse =
            FollowResponse(
                followId = follow.id!!,
                status = follow.status
            )
    }
}