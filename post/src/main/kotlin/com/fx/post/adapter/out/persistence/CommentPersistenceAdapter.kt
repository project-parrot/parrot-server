package com.fx.post.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.post.adapter.out.persistence.entity.CommentEntity
import com.fx.post.adapter.out.persistence.repository.CommentRepository
import com.fx.post.application.out.persistence.CommentPersistencePort
import com.fx.post.domain.Comment

@PersistenceAdapter
class CommentPersistenceAdapter(
    private val commentRepository: CommentRepository
) : CommentPersistencePort {

    override fun save(comment: Comment): Comment =
        commentRepository.save(CommentEntity.from(comment)).toDomain()

    override fun existsById(parentId: Long): Boolean =
        commentRepository.existsById(parentId)

    override fun findByPostIdOrderByCreatedAtAsc(postId: Long): List<Comment> =
        commentRepository.findByPostIdOrderByCreatedAtAsc(postId).map { it.toDomain() }

    override fun findByUserIdOrderByCreatedAtDesc(userId: Long): List<Comment> =
        commentRepository.findByUserIdOrderByCreatedAtDesc(userId).map { it.toDomain() }

}