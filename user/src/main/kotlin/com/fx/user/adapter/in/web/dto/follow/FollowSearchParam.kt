package com.fx.user.adapter.`in`.web.dto.follow

import com.fx.user.application.`in`.dto.FollowQueryCommand
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

data class FollowSearchParam(
    val followId: Long? = null,
    val createdAt: LocalDateTime? = null,
    val nickname: String? = null
) {

    fun toCommand(requestUserId: Long, targetUserId: Long, pageable: Pageable): FollowQueryCommand =
        FollowQueryCommand(
            requestUserId = requestUserId,
            targetUserId = targetUserId,
            followId = this.followId,
            createdAt = this.createdAt,
            nickname = this.nickname,
            pageable = pageable
        )

}