package com.fx.post.application.service

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import com.fx.post.adapter.out.web.impl.dto.ProfileCommand
import com.fx.post.application.`in`.LikeQueryUseCase
import com.fx.post.application.`in`.dto.LikeQueryCommand
import com.fx.post.application.`in`.dto.LikeUserCommand
import com.fx.post.application.out.persistence.LikePersistencePort
import com.fx.post.application.out.persistence.PostMediaPersistencePort
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.application.out.web.MediaWebPort
import com.fx.post.application.out.web.UserWebPort
import com.fx.post.domain.LikeQuery
import org.springframework.stereotype.Service

@Service
class LikeQueryService(
    private val likePersistencePort: LikePersistencePort,
    private val postPersistencePort: PostPersistencePort,
    private val postMediaPersistencePort: PostMediaPersistencePort,
    private val mediaWebPort: MediaWebPort,
    private val userWebPort: UserWebPort
) : LikeQueryUseCase{

    override fun getLikeUsers(postId: Long): List<LikeUserCommand> {
        val users = likePersistencePort.findByPostId(postId)

        val userMap: Map<Long, ProfileCommand> = userWebPort.getUsersInfo(users.map { it }.distinct())
            .orEmpty()
            .associateBy { it.userId }


        return users.map { user ->
            val userInfo = userMap[user]
            LikeUserCommand(
                user, userInfo?.nickname, userInfo?.profileImageUrl
            )
        }
    }

    override fun getMyLikedPosts(likeQueryCommand: LikeQueryCommand): List<PostSummaryDto> {
        val posts = postPersistencePort.getLikedPosts(LikeQuery.searchCondition(likeQueryCommand, false))

        val mappedList = mappedByMediaUrls(posts)

        return mappedByProfile(mappedList)
    }

    private fun mappedByMediaUrls(posts: List<PostSummaryDto>): List<PostSummaryDto> {
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

    private fun mappedByProfile(posts: List<PostSummaryDto>): List<PostSummaryDto> {
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