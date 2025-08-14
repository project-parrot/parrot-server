package com.fx.user.domain

import java.time.LocalDateTime

data class Follow(
    val id: Long? = null,
    val followerId: Long,
    val followingId: Long,
    var status: FollowStatus,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {

    fun approveFollow() {
        this.status = FollowStatus.APPROVED
    }

    companion object {
        @JvmStatic
        fun followUser(
            followerId: Long,
            followingId: Long,
            status: FollowStatus
        ) = Follow(
            followerId = followerId,
            followingId = followingId,
            status = status
        )
    }
}
