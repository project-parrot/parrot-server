package com.fx.user.application.service

import com.fx.user.application.`in`.FollowCommandUseCase
import com.fx.user.application.out.FollowPersistencePort
import com.fx.user.application.out.ProfilePersistencePort
import com.fx.user.domain.Follow
import com.fx.user.domain.FollowStatus
import com.fx.user.exception.FollowException
import com.fx.user.exception.ProfileException
import com.fx.user.exception.errorcode.FollowErrorCode
import com.fx.user.exception.errorcode.ProfileErrorCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FollowCommandService(
    private val followPersistencePort: FollowPersistencePort,
    private val profilePersistencePort: ProfilePersistencePort
): FollowCommandUseCase {

    @Transactional
    override fun followUser(followerId: Long, followingId: Long): Follow {

        // 자기 자신에게는 팔로잉할 수 없다.
        if (followerId == followingId) {
            throw FollowException(FollowErrorCode.SELF_FOLLOW_NOT_ALLOWED)
        }

        // 이미 팔로잉 요청이 된 경우 예외
        followPersistencePort.getFollow(followerId, followingId)?.let {
            when (it.status) {
                FollowStatus.APPROVED -> throw FollowException(FollowErrorCode.ALREADY_FOLLOWING)
                FollowStatus.PENDING -> throw FollowException(FollowErrorCode.FOLLOW_REQUEST_ALREADY_SENT)
            }
        }

        val profile = profilePersistencePort.findByUserId(followingId) ?: throw ProfileException(
            ProfileErrorCode.PROFILE_NOT_FOUND
        )

        // 상대 프로필이 Private 인 경우, status PENDING 
        if (profile.isPrivate) {
            return saveFollow(followerId, followingId, FollowStatus.PENDING)
        }

        return saveFollow(followerId, followingId, FollowStatus.APPROVED)

    }

    @Transactional
    override fun unfollowUser(requestUserId: Long, targetUserId: Long, mode: String): Boolean =
        when (mode.lowercase()) {
            "following" -> unfollowByFollowing(requestUserId, targetUserId)
            "follower" -> unfollowByFollower(requestUserId, targetUserId)
            else -> throw FollowException(FollowErrorCode.INVALID_MODE)
        }

    private fun unfollowByFollowing(requestUserId: Long, targetUserId: Long): Boolean {
        val follow = followPersistencePort.getFollow(requestUserId, targetUserId)
            ?: throw FollowException(FollowErrorCode.NOT_FOLLOWING)
        followPersistencePort.deleteFollow(follow.id!!)
        return true
    }

    private fun unfollowByFollower(requestUserId: Long, targetUserId: Long): Boolean {
        val follow = followPersistencePort.getFollow(targetUserId, requestUserId)
            ?: throw FollowException(FollowErrorCode.NOT_FOLLOWER)
        followPersistencePort.deleteFollow(follow.id!!)
        return true
    }

    private fun saveFollow(followerId: Long, followingId: Long, status: FollowStatus): Follow =
        followPersistencePort.saveFollow(
            Follow.followUser(
                followerId,
                followingId,
                status
            )
        )

}