package com.fx.user.application.out.persistence.dto

import com.fx.user.domain.FollowStatus
import java.time.LocalDateTime

data class FollowUserInfo @JvmOverloads constructor (
    val followId: Long,
    val userId: Long,
    val nickname: String,
    val status: FollowStatus,
    val followCreatedAt: LocalDateTime,
    val profileImageUrl: String? = null,
)