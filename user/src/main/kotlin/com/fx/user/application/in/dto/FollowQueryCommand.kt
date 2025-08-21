package com.fx.user.application.`in`.dto

import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

data class FollowQueryCommand(
    val requestUserId: Long,
    val targetUserId: Long,

    val followId: Long?,
    val nickname: String?,

    val pageable: Pageable
)