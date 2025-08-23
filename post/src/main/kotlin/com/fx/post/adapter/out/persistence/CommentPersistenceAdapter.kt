package com.fx.post.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.post.adapter.out.persistence.entity.CommentEntity
import com.fx.post.adapter.out.persistence.repository.CommentQueryRepository
import com.fx.post.adapter.out.persistence.repository.CommentRepository
import com.fx.post.application.out.persistence.CommentPersistencePort
import com.fx.post.domain.Comment
import com.fx.post.application.out.persistence.dto.CommentInfo
import com.fx.post.domain.CommentQuery

@PersistenceAdapter
class CommentPersistenceAdapter(
    private val commentRepository: CommentRepository,
    private val commentQueryRepository: CommentQueryRepository,
) : CommentPersistencePort {

    override fun save(comment: Comment): Comment =
        commentRepository.save(CommentEntity.from(comment)).toDomain()

    override fun existsById(parentId: Long): Boolean =
        commentRepository.existsById(parentId)

    override fun getComments(commentQuery: CommentQuery): List<CommentInfo> =
        commentQueryRepository.findComments(commentQuery)


}