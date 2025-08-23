package com.fx.post.adapter.`in`.web.dto.comment

import com.fx.post.application.out.persistence.dto.CommentInfo
import java.time.LocalDateTime

data class MyCommentResponse(
    val commentId: Long?= null,
    val postId: Long,
    val content: String,
    val createdAt: LocalDateTime?= null
) {
    companion object {
        fun from(commentInfo: CommentInfo): MyCommentResponse {
            return MyCommentResponse(
                commentId = commentInfo.id,
                postId = commentInfo.postId!!,
                content = commentInfo.content,
                createdAt = commentInfo.createdAt
            )
        }

        fun from(commentInfoList: List<CommentInfo>): List<MyCommentResponse> {
            return commentInfoList.map { from(it) }
        }
    }
}