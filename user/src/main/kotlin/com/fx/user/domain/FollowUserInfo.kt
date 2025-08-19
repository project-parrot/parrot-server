package com.fx.user.domain

import java.time.LocalDateTime

data class FollowUserInfo(
    val followId: Long,
    val userId: Long,
    val nickname: String,
    val mediaId: Long?,
    val profileImageUrl: String?,
    val status: FollowStatus,
    val followCreatedAt: LocalDateTime
) {
    // QueryDSL 전용 생성자 (profileImageUrl 제외)
    constructor(
        followId: Long,
        userId: Long,
        nickname: String,
        mediaId: Long?,
        status: FollowStatus,
        followCreatedAt: LocalDateTime
    ) : this(followId, userId, nickname, mediaId, null, status, followCreatedAt)
}