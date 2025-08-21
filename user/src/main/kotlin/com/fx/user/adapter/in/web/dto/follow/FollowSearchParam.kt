package com.fx.user.adapter.`in`.web.dto.follow

import com.fx.user.application.`in`.dto.FollowQueryCommand
import org.springframework.data.domain.Pageable

data class FollowSearchParam(
    val followId: Long? = null,
    val nickname: String? = null
) {

    fun toCommand(requestUserId: Long, targetUserId: Long, pageable: Pageable): FollowQueryCommand =
        FollowQueryCommand(
            requestUserId = requestUserId,
            targetUserId = targetUserId,
            followId = this.followId,
            nickname = this.nickname,
            pageable = pageable
        )

}