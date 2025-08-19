package com.fx.post.application.service

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import com.fx.post.application.`in`.LikeQueryUseCase
import com.fx.post.application.`in`.dto.LikeQueryCommand
import com.fx.post.application.out.persistence.LikePersistencePort
import com.fx.post.application.out.persistence.PostMediaPersistencePort
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.application.out.web.MediaWebPort
import com.fx.post.domain.LikeQuery
import org.springframework.stereotype.Service

@Service
class LikeQueryService(
    private val likePersistencePort: LikePersistencePort,
    private val postPersistencePort: PostPersistencePort,
    private val postMediaPersistencePort: PostMediaPersistencePort,
    private val mediaWebPort: MediaWebPort
) : LikeQueryUseCase{

    override fun getLikeUsers(postId: Long): List<Long> {
        val users = likePersistencePort.findByPostId(postId)
        // 유저 모듈에서 FeignClient로 userId들의 닉네임들 가져온 뒤 매핑하기(미구현)
        // 이후 userId나 닉네임 순으로 정렬(미구현)

        return users
    }

    override fun getMyLikedPosts(likeQueryCommand: LikeQueryCommand): List<PostSummaryDto> {
        val posts = postPersistencePort.getLikedPosts(LikeQuery.searchCondition(likeQueryCommand, false))

        val mappedList = mappedByMediaUrls(posts)
        // 유저 모듈에서 FeignClient로 userId들의 닉네임들 가져온 뒤 매핑하기(미구현)

        return posts
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

}