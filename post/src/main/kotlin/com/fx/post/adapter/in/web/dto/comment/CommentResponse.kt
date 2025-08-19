package com.fx.post.adapter.`in`.web.dto.comment

import com.fx.post.domain.Comment
import com.fx.post.domain.CommentInfo
import java.time.LocalDateTime

data class CommentResponse(
    val commentId: Long?= null,
    val userId: Long,
    val nickname: String?= null,
    val profileImageUrl: String?= null,
    val content: String,
    val createdAt: LocalDateTime?= null,
    val parentId: Long?= null
) {
    companion object {
        fun from(commentInfo: CommentInfo): CommentResponse {
            return CommentResponse(
                commentId = commentInfo.id,
                userId = commentInfo.userId,
                nickname = commentInfo.nickname,
                profileImageUrl = commentInfo.profileImageUrl,
                content = commentInfo.content,
                createdAt = commentInfo.createdAt,
                parentId = commentInfo.parentId
            )
        }

        fun from(commentInfoList: List<CommentInfo>): List<CommentResponse> {
            return commentInfoList.map { from(it) }
        }
    }
}
