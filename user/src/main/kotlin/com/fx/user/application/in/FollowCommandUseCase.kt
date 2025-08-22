package com.fx.user.application.`in`

import com.fx.user.application.`in`.dto.FollowQueryCommand
import com.fx.user.domain.Follow
import com.fx.user.domain.FollowUserInfo
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface FollowCommandUseCase {

    fun followUser(followerId:Long, followingId: Long): Follow

    fun unfollowUser(requestUserId: Long, followId: Long): Boolean

    fun approveFollowUser(userId: Long, followId: Long): Follow

    fun getUserFollowings(@Valid followQueryCommand: FollowQueryCommand): List<FollowUserInfo>

    fun getUserFollowers(@Valid followQueryCommand: FollowQueryCommand): List<FollowUserInfo>

    fun getFollowerByUserId(userId: Long): List<Long>

}