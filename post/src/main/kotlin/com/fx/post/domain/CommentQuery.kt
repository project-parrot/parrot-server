package com.fx.post.domain

import com.fx.post.application.`in`.dto.CommentQueryCommand
import org.springframework.data.domain.Pageable

data class CommentQuery(
    val userId: Long?= null,
    val postId: Long?= null,
    val commentId: Long?= null,
    val parentId: Long?= null,

    val pageable: Pageable,
    val isDeleted: Boolean?= false,
) {
    companion object {
        @JvmStatic
        fun searchCondition(commentQueryCommand: CommentQueryCommand, isDeleted: Boolean): CommentQuery =
            CommentQuery(
                userId = commentQueryCommand.userId,
                postId = commentQueryCommand.postId,
                commentId = commentQueryCommand.commentId,
                parentId = commentQueryCommand.parentId,
                pageable = commentQueryCommand.pageable,
                isDeleted = isDeleted
            )
    }
}