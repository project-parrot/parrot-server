package com.fx.post.application.service

import com.fx.post.application.`in`.CommentCommandUseCase
import com.fx.post.application.`in`.dto.CommentCreateCommand
import com.fx.post.application.out.persistence.CommentPersistencePort
import com.fx.post.application.out.persistence.PostPersistencePort
import com.fx.post.domain.Comment
import com.fx.post.exception.CommentException
import com.fx.post.exception.PostException
import com.fx.post.exception.errorcode.CommentErrorCode
import com.fx.post.exception.errorcode.PostErrorCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class CommentCommandService(
    private val commentPersistencePort: CommentPersistencePort,
    private val postPersistencePort: PostPersistencePort
) : CommentCommandUseCase {

    @Transactional
    override fun createComment(postId:Long, commentCreateCommand: CommentCreateCommand): Comment {
        if (!postPersistencePort.existsById(postId))
            throw PostException(PostErrorCode.POST_NOT_EXIST)

        val parentId = commentCreateCommand.parentId

        return if (parentId != null) {
            if (!commentPersistencePort.existsById(parentId)) {
                throw CommentException(CommentErrorCode.PARENT_NOT_EXIST)
            }
            commentPersistencePort.save(Comment.createComment(postId, commentCreateCommand))
        } else {
            commentPersistencePort.save(Comment.createComment(postId, commentCreateCommand))
        }
    }
}