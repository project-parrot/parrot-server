package com.fx.user.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.user.adapter.out.persistence.entity.FollowEntity
import com.fx.user.adapter.out.persistence.repository.FollowRepository
import com.fx.user.application.out.FollowPersistencePort
import com.fx.user.domain.Follow
import com.fx.user.domain.FollowStatus

@PersistenceAdapter
class FollowPersistenceAdapter(
    private val followRepository: FollowRepository
) : FollowPersistencePort {

    override fun saveFollow(follow: Follow): Follow =
        followRepository.save(FollowEntity.fromDomain(follow)).toDomain()

    override fun getFollowerCount(userId: Long): Int =
        followRepository.countByFollowingIdAndStatus(userId, FollowStatus.APPROVED)

    override fun getFollowingCount(userId: Long): Int =
        followRepository.countByFollowerIdAndStatus(userId, FollowStatus.APPROVED)

    override fun isFollowing(viewerId: Long, targetUserId: Long, status: FollowStatus): Boolean =
        followRepository.existsByFollowerIdAndFollowingIdAndStatus(viewerId, targetUserId, status)

    override fun getFollow(followerId: Long, followingId: Long): Follow? =
        followRepository.findByFollowerIdAndFollowingId(followerId, followingId).orElse(null)?.toDomain()

}
