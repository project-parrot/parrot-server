package com.fx.post.adapter.`in`.web.dto.comment

import com.fx.post.domain.Comment
import java.time.LocalDateTime

data class CommentResponse(
    val commentId: Long?= null,
    val userId: Long,
    //val nickname: String,
    //val profileImageUrl: String?= null,
    val content: String,
    val createdAt: LocalDateTime?= null,
    val parentId: Long?= null
) {
    companion object {
        fun from(comment: Comment): CommentResponse {
            return CommentResponse(
                commentId = comment.id,
                userId = comment.userId,
                content = comment.content,
                createdAt = comment.createdAt,
                parentId = comment.parentId
            )
        }
    }
}
