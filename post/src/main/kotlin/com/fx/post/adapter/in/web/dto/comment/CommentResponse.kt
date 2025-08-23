package com.fx.post.adapter.`in`.web.dto.comment

import com.fx.post.application.out.persistence.dto.CommentInfo
import java.time.LocalDateTime

data class CommentResponse(
    val commentId: Long?= null,
    val userId: Long,
    val nickname: String?= null,
    val profileImageUrl: String?= null,
    val content: String,
    val createdAt: LocalDateTime?= null,
    val parentId: Long?= null,
    val replyCount: Long?= null,
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
                parentId = commentInfo.parentId,
                replyCount = commentInfo.replyCount
            )
        }

        fun from(commentInfoList: List<CommentInfo>): List<CommentResponse> {
            return commentInfoList.map { from(it) }
        }
    }
}
