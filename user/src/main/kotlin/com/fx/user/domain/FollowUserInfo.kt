package com.fx.user.domain

import java.time.LocalDateTime

data class FollowUserInfo(
    val followId: Long,
    val userId: Long,
    val nickname: String,
    val mediaId: Long?,
    val status: FollowStatus,
    val followCreatedAt: LocalDateTime
)