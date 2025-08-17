package com.fx.user.domain

import com.fx.user.application.`in`.dto.FollowQueryCommand
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

data class FollowQuery(
    val targetUserId: Long,

    val followId: Long? = null,
    val createdAt: LocalDateTime? = null,
    val nickname: String? = null,
    val pageable: Pageable,

    val status: FollowStatus
) {
    companion object {
        @JvmStatic
        fun searchCondition(followQueryCommand: FollowQueryCommand, status: FollowStatus): FollowQuery =
            FollowQuery(
                targetUserId = followQueryCommand.targetUserId,
                followId = followQueryCommand.followId,
                createdAt = followQueryCommand.createdAt,
                nickname = followQueryCommand.nickname,
                pageable = followQueryCommand.pageable,
                status = status
            )

    }
}
