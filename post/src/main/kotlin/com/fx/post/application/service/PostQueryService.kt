package com.fx.post.application.service

import com.fx.post.application.out.persistence.dto.PostInfo
import com.fx.post.adapter.out.persistence.repository.PostQueryRepository
import com.fx.post.adapter.out.web.impl.dto.ProfileCommand
import com.fx.post.application.`in`.PostQueryUseCase
import com.fx.post.application.`in`.dto.PostQueryCommand
import com.fx.post.application.out.persistence.PostMediaPersistencePort
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.application.out.web.MediaWebPort
import com.fx.post.application.out.web.UserWebPort
import com.fx.post.domain.PostQuery
import org.springframework.stereotype.Service

@Service
class PostQueryService(
    private val postPersistencePort: PostPersistencePort,
    private val postMediaPersistencePort: PostMediaPersistencePort,
    private val mediaWebPort: MediaWebPort,
    private val postQueryRepository: PostQueryRepository,
    private val userWebPort: UserWebPort
) : PostQueryUseCase {

    override fun getFollowersPosts(postQueryCommand: PostQueryCommand): List<PostInfo> {
        val followingUsers = userWebPort.getFollowersInfo(postQueryCommand.userId!!)
        val postQueryCommandWithFollowers = postQueryCommand.copy(userId = null, userIds = followingUsers)
        val posts = postQueryRepository.findPosts(PostQuery.searchCondition(postQueryCommandWithFollowers, false))

        val mappedList = mappedByMediaUrls(posts)

        return mappedByProfile(mappedList)
    }

    override fun getUserPosts(postQueryCommand: PostQueryCommand): List<PostInfo> {
        val posts = PostQuery.searchCondition(postQueryCommand, false)
        val mappedList =  mappedByMediaUrls(postPersistencePort.getPosts(posts))

        return mappedByProfile(mappedList)
    }

    private fun mappedByMediaUrls(posts: List<PostInfo>): List<PostInfo> {
        if (posts.isEmpty()) return emptyList()

        val postIds = posts.map { it.id }

        val postToMediaIds = postMediaPersistencePort
            .findByPostIdIn(postIds)
            .groupBy({ it.postId }, { it.mediaId })

        val mediaUrls = mediaWebPort
            .getUrl(postToMediaIds.values.flatten())
            .orEmpty()
            .associate { it.mediaId to it.mediaUrl }

        return posts.map { post ->
            val urls = postToMediaIds[post.id]
                ?.mapNotNull(mediaUrls::get)
                .orEmpty()

            post.copy(mediaUrls = urls)
        }
    }

    private fun mappedByProfile(posts: List<PostInfo>): List<PostInfo> {
        if (posts.isEmpty()) return emptyList()

        val userMap: Map<Long, ProfileCommand> = userWebPort.getUsersInfo(posts.map { it.userId }.distinct())
            .orEmpty()
            .associateBy { it.userId }

        return posts.map { post ->
            val userInfo = userMap[post.userId]
            post.copy(
                nickname = userInfo?.nickname,
                profileImageUrl = userInfo?.profileImageUrl
            )
        }
    }
}