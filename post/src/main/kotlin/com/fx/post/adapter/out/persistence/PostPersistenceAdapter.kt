package com.fx.post.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import com.fx.post.adapter.out.persistence.entity.PostEntity
import com.fx.post.adapter.out.persistence.repository.PostQueryRepository
import com.fx.post.adapter.out.persistence.repository.PostRepository
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.domain.LikeQuery
import com.fx.post.domain.Post
import com.fx.post.domain.PostQuery
import java.time.LocalDateTime

@PersistenceAdapter
class PostPersistenceAdapter(
    private val postRepository: PostRepository,
    private val postQueryRepository: PostQueryRepository
) : PostPersistencePort {

    override fun save(post: Post): Post =
        postRepository.save(PostEntity.from(post)).toDomain()

    override fun existsByUserIdAndCreatedAtBetween(userId:Long, start: LocalDateTime, end: LocalDateTime): Boolean =
        postRepository.existsByUserIdAndCreatedAtBetween(userId, start, end)

    override fun existsById(postId: Long): Boolean =
        postRepository.existsById(postId)

    override fun findByIdAndIsDeletedNot(postId: Long): Post? =
        postRepository.findByIdAndIsDeletedNot(postId, true).orElse(null)?.toDomain()

    override fun getPosts(postQuery: PostQuery): List<PostSummaryDto> =
        postQueryRepository.findPosts(postQuery)

    override fun getLikedPosts(likeQuery: LikeQuery): List<PostSummaryDto> =
        postQueryRepository.findLikedPosts(likeQuery)

}