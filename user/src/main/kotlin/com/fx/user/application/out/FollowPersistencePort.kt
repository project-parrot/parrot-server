package com.fx.user.application.out

interface FollowPersistencePort {

    fun countFollowers(userId: Long): Int

    fun countFollowing(userId: Long): Int

}