package com.fx.post.domain

import com.fx.post.application.`in`.dto.CommentCreateCommand
import java.time.LocalDateTime

data class Comment(
    val id: Long? = null,
    val userId: Long,
    val postId: Long,
    val parentId: Long? = null,
    val content:String,
    val isDeleted: Boolean = false,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {

    companion object {
        @JvmStatic
        fun createComment(postId: Long, commentCreateCommand: CommentCreateCommand): Comment {
            return Comment(
                userId = commentCreateCommand.userId,
                postId = postId,
                parentId = commentCreateCommand.parentId,
                content = commentCreateCommand.content
            )
        }
    }
}