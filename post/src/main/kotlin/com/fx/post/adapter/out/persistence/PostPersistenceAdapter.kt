package com.fx.post.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import com.fx.post.adapter.out.persistence.entity.PostEntity
import com.fx.post.adapter.out.persistence.repository.PostRepository
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.domain.Post
import java.time.LocalDateTime

@PersistenceAdapter
class PostPersistenceAdapter(
    private val postRepository: PostRepository
) : PostPersistencePort {

    override fun save(post: Post): Post =
        postRepository.save(PostEntity.from(post)).toDomain()

    override fun findByUserIdAndPostIdBeforeAndIsDeleted(userIds: List<Long>, postId: Long): List<PostSummaryDto> =
        postRepository.findByUserIdAndPostIdBeforeAndIsDeleted(userIds, postId, false)

    override fun existsByUserIdAndCreatedAtBetween(userId:Long, start: LocalDateTime, end: LocalDateTime): Boolean =
        postRepository.existsByUserIdAndCreatedAtBetween(userId, start, end)

    override fun existsById(postId: Long): Boolean =
        postRepository.existsById(postId)

    override fun findByIdAndIsDeletedNot(postId: Long): Post? =
        postRepository.findByIdAndIsDeletedNot(postId, true).orElse(null)?.toDomain()

    override fun findLikedPostsByUserIdAndCreatedAtBeforeAndIsDeleted(userId: Long, before: LocalDateTime): List<PostSummaryDto> =
        postRepository.findLikedPostsByUserIdAndCreatedAtBeforeAndIsDeleted(userId, before, false)
}