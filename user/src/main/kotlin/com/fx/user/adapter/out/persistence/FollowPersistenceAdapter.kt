package com.fx.user.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.user.adapter.out.persistence.entity.FollowEntity
import com.fx.user.adapter.out.persistence.repository.FollowQueryRepository
import com.fx.user.adapter.out.persistence.repository.FollowRepository
import com.fx.user.application.out.persistence.FollowPersistencePort
import com.fx.user.domain.Follow
import com.fx.user.domain.FollowQuery
import com.fx.user.domain.FollowStatus
import com.fx.user.application.out.persistence.dto.FollowUserInfo

@PersistenceAdapter
class FollowPersistenceAdapter(
    private val followRepository: FollowRepository,
    private val followQueryRepository: FollowQueryRepository
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

    override fun getFollow(followId: Long): Follow? =
        followRepository.findById(followId).orElse(null)?.toDomain()

    override fun deleteFollow(followId: Long) =
        followRepository.deleteById(followId)

    override fun getUserFollowings(followQuery: FollowQuery): List<FollowUserInfo> =
        followQueryRepository.findByFollowings(followQuery)

    override fun getUserFollowers(followQuery: FollowQuery): List<FollowUserInfo> =
        followQueryRepository.findByFollowers(followQuery)

    override fun findByUserId(userId: Long): List<Long> =
        followRepository.findByFollowerId(userId).map { it.toDomain().followingId }
}
