package com.fx.user.application.`in`

import com.fx.user.domain.Follow

interface FollowCommandUseCase {

    fun followUser(followerId:Long, followingId: Long): Follow

}