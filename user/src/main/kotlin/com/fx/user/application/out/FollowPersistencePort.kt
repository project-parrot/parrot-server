package com.fx.user.application.out

interface FollowPersistencePort {

    fun getFollowerCount(userId: Long): Int

    fun getFollowingCount(userId: Long): Int

    fun isFollowing(viewerId: Long, targetUserId: Long): Boolean

}