package com.fx.post.application.out.persistence.dto

import java.time.LocalDateTime

data class CommentInfo @JvmOverloads constructor(
    val id: Long? = null,
    val userId: Long,
    val postId: Long? = null,
    val content: String,
    val createdAt: LocalDateTime? = null,
    val parentId: Long? = null,
    val replyCount: Long? = null,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val isDeleted: Boolean = false,
    val updatedAt: LocalDateTime? = null
)
