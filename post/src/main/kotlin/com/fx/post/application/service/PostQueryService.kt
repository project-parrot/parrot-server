package com.fx.post.application.service

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import com.fx.post.application.`in`.PostQueryUseCase
import com.fx.post.application.out.persistence.PostMediaPersistencePort
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.application.out.web.MediaWebPort
import org.springframework.stereotype.Service

@Service
class PostQueryService(
    private val postPersistencePort: PostPersistencePort,
    private val postMediaPersistencePort: PostMediaPersistencePort,
    private val mediaWebPort: MediaWebPort
    ) : PostQueryUseCase {

    override fun getFollowersPosts(userId: Long, postId: Long): List<PostSummaryDto> {
        // 유저 모듈에서 FeignClient로 팔로우 리스트 가져오기(미구현)
        val followers = listOf(1L, 2L) // 임시 구현
        val posts = postPersistencePort.findByUserIdAndPostIdBeforeAndIsDeleted(followers, postId)

        val mappedList = mappedByMediaUrls(posts)

        // 유저 모듈에서 FeignClient로 follower들의 닉네임을 가져온 뒤 매핑하기(미구현)
        return mappedList
    }

    override fun getMyPosts(userId: Long, postId: Long): List<PostSummaryDto> {
        val posts = postPersistencePort.findByUserIdAndPostIdBeforeAndIsDeleted(listOf(userId), postId)

        val mappedList = mappedByMediaUrls(posts)
        // 유저 모듈에서 FeignClient로 userId들의 닉네임들 가져온 뒤 매핑하기(미구현)
        return mappedList
    }

    override fun getUserPosts(userId: Long, postId: Long): List<PostSummaryDto> {
        val posts = postPersistencePort.findByUserIdAndPostIdBeforeAndIsDeleted(listOf(userId), postId)

        val mappedList = mappedByMediaUrls(posts)
        // 유저 모듈에서 FeignClient로 userId들의 닉네임들 가져온 뒤 매핑하기(미구현)
        return mappedList
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