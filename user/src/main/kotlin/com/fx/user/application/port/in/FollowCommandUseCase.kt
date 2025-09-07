package com.fx.user.application.port.`in`

import com.fx.user.domain.Follow
import org.springframework.validation.annotation.Validated

@Validated
interface FollowCommandUseCase {

    fun followUser(followerId:Long, followingId: Long): Follow

    fun unfollowUser(requestUserId: Long, followId: Long): Boolean

    fun approveFollowUser(userId: Long, followId: Long): Follow

    fun getFollowerByUserId(userId: Long): List<Long>

}