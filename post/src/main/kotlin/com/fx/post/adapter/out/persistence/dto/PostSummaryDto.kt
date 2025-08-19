package com.fx.post.adapter.out.persistence.dto

import java.time.LocalDateTime

data class PostSummaryDto(
    var id: Long,
    val userId: Long,
    val nickname: String?= null,
    val profileImageUrl: String? = null,
    val content: String,
    var mediaUrls: List<String>?= null,
    val createdAt: LocalDateTime,
    val likeCount: Long,
    val commentCount: Long,
) {
    constructor(id: Long, userId: Long, content: String, createdAt: LocalDateTime, likeCount: Long, commentCount: Long)
            : this(id, userId, null, null, content, null, createdAt, likeCount, commentCount)
}
