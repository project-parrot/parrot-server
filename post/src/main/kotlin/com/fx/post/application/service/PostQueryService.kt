package com.fx.post.application.service

import com.fx.global.dto.Context
import com.fx.post.application.out.persistence.dto.PostInfo
import com.fx.post.adapter.out.persistence.repository.PostQueryRepository
import com.fx.post.adapter.out.web.impl.dto.ProfileCommand
import com.fx.post.application.`in`.PostQueryUseCase
import com.fx.post.application.`in`.dto.PostQueryCommand
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.application.out.web.MediaWebPort
import com.fx.post.application.out.web.UserWebPort
import com.fx.post.domain.PostQuery
import org.springframework.stereotype.Service

@Service
class PostQueryService(
    private val postPersistencePort: PostPersistencePort,
    private val mediaWebPort: MediaWebPort,
    private val userWebPort: UserWebPort
) : PostQueryUseCase {

    override fun getFollowersPosts(postQueryCommand: PostQueryCommand): List<PostInfo> {
        val followingUsers = userWebPort.getFollowersInfo(postQueryCommand.userId!!)
        if (followingUsers.isNullOrEmpty()) {
            return emptyList()
        }
        return enrichPosts(
            postPersistencePort.getPosts(
                PostQuery.searchCondition(postQueryCommand.copy(userId = null, userIds = followingUsers), false)
            )
        )
    }

    override fun getUserPosts(postQueryCommand: PostQueryCommand): List<PostInfo> =
        enrichPosts(
            postPersistencePort.getPosts(
                PostQuery.searchCondition(postQueryCommand, false)
            )
        )

    private fun enrichPosts(posts: List<PostInfo>): List<PostInfo> =
        posts
            .attachMediaInfos()
            .attachProfileInfos()

    private fun List<PostInfo>.attachMediaInfos(): List<PostInfo> {
        if (isEmpty()) return emptyList()

        val mediaMap = mediaWebPort.getUrls(Context.POST, map { it.id })
            ?.associate { it.referenceId to it.mediaInfos }
            .orEmpty()

        return map { post ->
            post.copy(mediaInfos = mediaMap[post.id].orEmpty())
        }
    }

    private fun List<PostInfo>.attachProfileInfos(): List<PostInfo> {
        if (isEmpty()) return emptyList()

        val userMap: Map<Long, ProfileCommand> = userWebPort.getUsersInfo(map { it.userId }.distinct())
            .orEmpty()
            .associateBy { it.userId }

        return map { post ->
            val userInfo = userMap[post.userId]
            post.copy(
                nickname = userInfo?.nickname,
                profileImageUrl = userInfo?.profileImageUrl
            )
        }
    }

}