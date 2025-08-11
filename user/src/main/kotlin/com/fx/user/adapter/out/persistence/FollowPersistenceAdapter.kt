package com.fx.user.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.user.adapter.out.persistence.repository.FollowRepository
import com.fx.user.application.out.FollowPersistencePort
import com.fx.user.domain.FollowStatus

@PersistenceAdapter
class FollowPersistenceAdapter(
    private val followRepository: FollowRepository
) : FollowPersistencePort {

    override fun getFollowerCount(userId: Long): Int =
        followRepository.countByFollowingIdAndStatus(userId, FollowStatus.APPROVED)

    override fun getFollowingCount(userId: Long): Int =
        followRepository.countByFollowerIdAndStatus(userId, FollowStatus.APPROVED)

    override fun isFollowing(viewerId: Long, targetUserId: Long): Boolean =
        followRepository.existsByFollowerIdAndFollowingIdAndStatus(viewerId, targetUserId, FollowStatus.APPROVED)

}
