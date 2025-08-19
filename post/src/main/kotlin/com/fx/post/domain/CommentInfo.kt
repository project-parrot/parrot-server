package com.fx.post.domain

import java.time.LocalDateTime

data class CommentInfo(
    val id: Long? = null,
    val userId: Long,
    val nickname: String?= null,
    val profileImageUrl: String?= null,
    val postId: Long,
    val parentId: Long? = null,
    val content:String,
    val isDeleted: Boolean = false,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {
    companion object {
        @JvmStatic
        fun from(nickname:String?, profileImageUrl:String?, comment:Comment): CommentInfo =
            CommentInfo(
                id = comment.id,
                userId = comment.userId,
                nickname = nickname,
                profileImageUrl = profileImageUrl,
                postId = comment.postId,
                parentId = comment.parentId,
                content = comment.content,
                isDeleted = comment.isDeleted,
                createdAt = comment.createdAt,
                updatedAt = comment.updatedAt
            )

    }

}
