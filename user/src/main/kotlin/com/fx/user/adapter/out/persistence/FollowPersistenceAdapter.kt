package com.fx.user.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.user.adapter.out.persistence.repository.FollowRepository
import com.fx.user.application.out.FollowPersistencePort
import com.fx.user.domain.FollowStatus

@PersistenceAdapter
class FollowPersistenceAdapter(
    private val followRepository: FollowRepository
) : FollowPersistencePort {

    override fun countFollowers(userId: Long): Int =
        followRepository.countByFollowingIdAndStatus(userId, FollowStatus.APPROVED)

    override fun countFollowing(userId: Long): Int =
        followRepository.countByFollowerIdAndStatus(userId, FollowStatus.APPROVED)

}
