package com.fx.user.application.out

import com.fx.user.domain.Follow
import com.fx.user.domain.FollowStatus

interface FollowPersistencePort {

    fun saveFollow(follow: Follow): Follow

    fun getFollowerCount(userId: Long): Int

    fun getFollowingCount(userId: Long): Int

    fun isFollowing(viewerId: Long, targetUserId: Long, status: FollowStatus): Boolean

    fun getFollow(followerId: Long, followingId: Long): Follow?

}