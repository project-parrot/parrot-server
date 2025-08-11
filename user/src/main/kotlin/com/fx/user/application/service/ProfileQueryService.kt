package com.fx.user.application.service

import com.fx.user.application.`in`.ProfileQueryUseCase
import com.fx.user.application.out.FollowPersistencePort
import com.fx.user.application.out.ProfilePersistencePort
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
        val profile = profilePersistencePort.findByProfile(userId)?: throw ProfileException(
            ProfileErrorCode.PROFILE_NOT_FOUND)
        val followerCount = followPersistencePort.countFollowers(userId)
        val followingCount = followPersistencePort.countFollowing(userId)

        // TODO Image 조회 로직 구현
        val profileImageUrl: String? = null

        return ProfileInfo.createMyProfileInfo(
            profile,
            profileImageUrl,
            followerCount,
            followingCount
        )
    }

}