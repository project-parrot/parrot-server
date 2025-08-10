package com.fx.post.adapter.`in`.web.dto

import com.fx.post.domain.Comment
import java.time.LocalDateTime

data class MyCommentResponse(
    val commentId: Long?= null,
    val postId: Long,
    val content: String,
    val createdAt: LocalDateTime?= null
) {
    companion object {
        fun from(comment: Comment): MyCommentResponse {
            return MyCommentResponse(
                commentId = comment.id,
                postId = comment.postId,
                content = comment.content,
                createdAt = comment.createdAt
            )
        }
    }
}