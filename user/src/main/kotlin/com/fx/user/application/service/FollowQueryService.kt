package com.fx.user.application.service

import com.fx.global.dto.Context
import com.fx.user.adapter.out.web.impl.dto.MediaInfo
import com.fx.user.application.`in`.FollowQueryUseCase
import com.fx.user.application.`in`.dto.FollowQueryCommand
import com.fx.user.application.out.persistence.FollowPersistencePort
import com.fx.user.application.out.persistence.ProfilePersistencePort
import com.fx.user.application.out.persistence.dto.FollowUserInfo
import com.fx.user.application.out.web.MediaWebPort
import com.fx.user.domain.FollowQuery
import com.fx.user.domain.FollowStatus
import com.fx.user.exception.FollowException
import com.fx.user.exception.ProfileException
import com.fx.user.exception.errorcode.FollowErrorCode
import com.fx.user.exception.errorcode.ProfileErrorCode
import org.springframework.stereotype.Service

@Service
class FollowQueryService(
    private val followPersistencePort: FollowPersistencePort,
    private val profilePersistencePort: ProfilePersistencePort,
    private val mediaWebPort: MediaWebPort
): FollowQueryUseCase {

    override fun getUserFollowings(followQueryCommand: FollowQueryCommand): List<FollowUserInfo> {
        checkFollowListAccess(followQueryCommand)

        val followingUserList = followPersistencePort.getUserFollowings(
            FollowQuery.searchCondition(followQueryCommand, FollowStatus.APPROVED)
        )
        
        return mapMediaInfoToUsers(followingUserList)
    }


    override fun getUserFollowers(followQueryCommand: FollowQueryCommand): List<FollowUserInfo> {
        checkFollowListAccess(followQueryCommand)

        val followerList = followPersistencePort.getUserFollowers(
            FollowQuery.searchCondition(followQueryCommand, FollowStatus.APPROVED)
        )

        return mapMediaInfoToUsers(followerList)
    }

    override fun getFollowPendingRequests(followQueryCommand: FollowQueryCommand): List<FollowUserInfo> {
        val followerList = followPersistencePort.getUserFollowers(
            FollowQuery.searchCondition(followQueryCommand, FollowStatus.PENDING)
        )
        return mapMediaInfoToUsers(followerList)
    }

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

    private fun mapMediaInfoToUsers(followUserInfoList: List<FollowUserInfo>): List<FollowUserInfo> {

        val referenceIdList: List<Long> = followUserInfoList.map { it.userId }

        val mediaInfoMap: Map<Long, MediaInfo?> = if (referenceIdList.isNotEmpty()) {
            mediaWebPort.getUrls(Context.PROFILE, referenceIdList)
                .orEmpty()
                .associate { it.referenceId to it.mediaInfos?.firstOrNull() }
        } else {
            emptyMap()
        }

        return followUserInfoList.map { user ->
            user.copy(
                mediaInfo = mediaInfoMap[user.userId]
            )
        }
    }

}