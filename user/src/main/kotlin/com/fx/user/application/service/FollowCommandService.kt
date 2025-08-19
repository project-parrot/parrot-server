package com.fx.user.application.service

import com.fx.user.application.`in`.FollowCommandUseCase
import com.fx.user.application.`in`.dto.FollowQueryCommand
import com.fx.user.application.out.FollowPersistencePort
import com.fx.user.application.out.MediaWebPort
import com.fx.user.application.out.ProfilePersistencePort
import com.fx.user.domain.Follow
import com.fx.user.domain.FollowQuery
import com.fx.user.domain.FollowStatus
import com.fx.user.domain.FollowUserInfo
import com.fx.user.exception.FollowException
import com.fx.user.exception.ProfileException
import com.fx.user.exception.errorcode.FollowErrorCode
import com.fx.user.exception.errorcode.ProfileErrorCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class FollowCommandService(
    private val followPersistencePort: FollowPersistencePort,
    private val profilePersistencePort: ProfilePersistencePort,
    private val mediaWebPort: MediaWebPort
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
    override fun unfollowUser(requestUserId: Long, followId: Long): Boolean {

        val follow = followPersistencePort.getFollow(followId)?: throw FollowException(FollowErrorCode.FOLLOW_NOT_FOUND)

        if (follow.followerId != requestUserId && follow.followingId != requestUserId) {
            throw FollowException(FollowErrorCode.UNAUTHORIZED_UNFOLLOW)
        }

        followPersistencePort.deleteFollow(followId)
        return true
    }

    @Transactional
    override fun approveFollowUser(userId: Long, followId: Long): Follow {
        val follow = followPersistencePort.getFollow(followId)?: throw FollowException(FollowErrorCode.FOLLOW_NOT_FOUND)

        // 이미 Approved 상태인 경우 예외
        if (follow.status == FollowStatus.APPROVED) {
            throw FollowException(FollowErrorCode.ALREADY_FOLLOWING)
        }

        // 자신에게 온 following 인지 확인
        if (follow.followingId != userId) {
            throw FollowException(FollowErrorCode.UNAUTHORIZED_UNFOLLOW)
        }

        follow.approveFollow()
        return followPersistencePort.saveFollow(follow)
    }

    override fun getUserFollowings(followQueryCommand: FollowQueryCommand): List<FollowUserInfo> {
        checkFollowListAccess(followQueryCommand)

        val followingUserList = followPersistencePort.getUserFollowings(
            FollowQuery.searchCondition(followQueryCommand, FollowStatus.APPROVED)
        )
        
        return mapMediaUrls(followingUserList)
    }


    override fun getUserFollowers(followQueryCommand: FollowQueryCommand): List<FollowUserInfo> {
        checkFollowListAccess(followQueryCommand)

        val followerList = followPersistencePort.getUserFollowers(
            FollowQuery.searchCondition(followQueryCommand, FollowStatus.APPROVED)
        )

        return mapMediaUrls(followerList)
    }

    override fun getFollowerByUserId(userId: Long): List<Long> =
        followPersistencePort.findByUserId(userId)


    private fun checkFollowListAccess(followQueryCommand: FollowQueryCommand) {
        val targetUserId = followQueryCommand.targetUserId
        val requestUserId = followQueryCommand.requestUserId

        // 자기 자신을 조회하는 경우 항상 허용
        if (targetUserId == requestUserId) {
            return
        }

        // 다른 사용자의 목록을 조회하는 경우
        val targetUser = profilePersistencePort.findByUserId(targetUserId)
            ?: throw ProfileException(ProfileErrorCode.PROFILE_NOT_FOUND)

        // 대상 유저가 비공개 계정인 경우, 요청한 유저가 팔로우하고 있는지 확인
        if (targetUser.isPrivate) {
            val isFollowing = followPersistencePort.isFollowing(
                requestUserId, targetUserId, FollowStatus.APPROVED
            )
            if (!isFollowing) {
                throw FollowException(FollowErrorCode.UNAUTHORIZED_FOLLOW_LIST_ACCESS)
            }
        }
    }

    private fun saveFollow(followerId: Long, followingId: Long, status: FollowStatus): Follow =
        followPersistencePort.saveFollow(
            Follow.followUser(
                followerId,
                followingId,
                status
            )
        )

    private fun mapMediaUrls(followUserInfoList: List<FollowUserInfo>): List<FollowUserInfo> {

        val mediaIdList: List<Long> = followUserInfoList.mapNotNull { it.mediaId }

        val mediaUrlMap: Map<Long, String> = if (mediaIdList.isNotEmpty()) {
            mediaWebPort.getUrl(mediaIdList)
                .orEmpty()
                .associate { it.mediaId to it.mediaUrl }
        } else {
            emptyMap()
        }

        return followUserInfoList.map { user ->
            user.copy(
                profileImageUrl = user.mediaId?.let { mediaUrlMap[it] }
            )
        }
    }

}