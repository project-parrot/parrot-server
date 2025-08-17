package com.fx.user.application.`in`

import com.fx.user.application.`in`.dto.FollowQueryCommand
import com.fx.user.domain.Follow
import com.fx.user.domain.FollowUserInfo

interface FollowCommandUseCase {

    fun followUser(followerId:Long, followingId: Long): Follow

    fun unfollowUser(requestUserId: Long, followId: Long): Boolean

    fun approveFollowUser(userId: Long, followId: Long): Follow

    fun getUserFollowings(followQueryCommand: FollowQueryCommand): List<FollowUserInfo>

    fun getUserFollowers(followQueryCommand: FollowQueryCommand): List<FollowUserInfo>

}