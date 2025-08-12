package com.fx.user.application.service

import com.fx.user.application.`in`.ProfileQueryUseCase
import com.fx.user.application.out.FollowPersistencePort
import com.fx.user.application.out.ProfilePersistencePort
import com.fx.user.domain.FollowStatus
import com.fx.user.domain.Profile
import com.fx.user.domain.ProfileInfo
import com.fx.user.exception.ProfileException
import com.fx.user.exception.errorcode.ProfileErrorCode
import org.springframework.stereotype.Service

@Service
class ProfileQueryService(
    private val profilePersistencePort: ProfilePersistencePort,
    private val followPersistencePort: FollowPersistencePort,
): ProfileQueryUseCase {

    override fun getMyProfile(userId: Long): ProfileInfo {
        val profile = profilePersistencePort.findByUserId(userId)?: throw ProfileException(
            ProfileErrorCode.PROFILE_NOT_FOUND)
        val followerCount = followPersistencePort.getFollowerCount(userId)
        val followingCount = followPersistencePort.getFollowingCount(userId)

        // TODO Image 조회 로직 구현
        val profileImageUrl: String? = null

        return ProfileInfo.createProfileInfo(
            profile = profile,
            profileImageUrl = profileImageUrl,
            followerCount = followerCount,
            followingCount = followingCount,
            isFollowing = false
        )
    }

    override fun getOtherProfile(viewerId: Long, targetUserId: Long): ProfileInfo {
        val targetUserProfile = profilePersistencePort.findByUserId(targetUserId)
            ?: throw ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND)

        val isFollowing = followPersistencePort.isFollowing(viewerId, targetUserId, FollowStatus.APPROVED)

        // 상대 프로필이 Private 이고 팔로우 상태가 아닌 경우
        if (targetUserProfile.isPrivate && !isFollowing) {
            throw ProfileException(ProfileErrorCode.PROFILE_PRIVATE)
        }

        val followerCount = followPersistencePort.getFollowerCount(targetUserId)
        val followingCount = followPersistencePort.getFollowingCount(targetUserId)

        // TODO Image 조회 로직 구현
        val profileImageUrl: String? = null

        return ProfileInfo.createProfileInfo(
            profile = targetUserProfile,
            profileImageUrl = profileImageUrl,
            followerCount = followerCount,
            followingCount = followingCount,
            isFollowing = isFollowing
        )
    }
}