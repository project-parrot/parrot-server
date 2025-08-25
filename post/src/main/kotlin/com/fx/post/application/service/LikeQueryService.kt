package com.fx.post.application.service

import com.fx.global.dto.Context
import com.fx.post.application.out.persistence.dto.PostInfo
import com.fx.post.adapter.out.web.impl.dto.ProfileCommand
import com.fx.post.application.`in`.LikeQueryUseCase
import com.fx.post.application.`in`.dto.LikeQueryCommand
import com.fx.post.application.`in`.dto.LikeUserCommand
import com.fx.post.application.out.persistence.LikePersistencePort
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.application.out.web.MediaWebPort
import com.fx.post.application.out.web.UserWebPort
import com.fx.post.domain.LikeQuery
import org.springframework.stereotype.Service

@Service
class LikeQueryService(
    private val likePersistencePort: LikePersistencePort,
    private val postPersistencePort: PostPersistencePort,
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

    override fun getMyLikedPosts(likeQueryCommand: LikeQueryCommand): List<PostInfo> =
         enrichPosts(
             postPersistencePort.getLikedPosts(
                 LikeQuery.searchCondition(likeQueryCommand, false)
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

        return map { post -> post.copy(mediaInfos = mediaMap[post.id].orEmpty()) }
    }

    private fun List<PostInfo>.attachProfileInfos(): List<PostInfo> {
        if (isEmpty()) return emptyList()

        val userMap = userWebPort.getUsersInfo(map { it.userId }.distinct())
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