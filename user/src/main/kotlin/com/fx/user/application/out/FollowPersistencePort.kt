package com.fx.user.application.out

import com.fx.user.domain.Follow
import com.fx.user.domain.FollowQuery
import com.fx.user.domain.FollowStatus
import com.fx.user.domain.FollowUserInfo

interface FollowPersistencePort {

    fun saveFollow(follow: Follow): Follow

    fun getFollowerCount(userId: Long): Int

    fun getFollowingCount(userId: Long): Int

    fun isFollowing(viewerId: Long, targetUserId: Long, status: FollowStatus): Boolean

    fun getFollow(followerId: Long, followingId: Long): Follow?

    fun getFollow(followId: Long): Follow?

    fun deleteFollow(followId: Long)

    fun getUserFollowings(followQuery: FollowQuery): List<FollowUserInfo>

    fun getUserFollowers(followQuery: FollowQuery): List<FollowUserInfo>

    fun findByUserId(userId: Long): List<Long>

}