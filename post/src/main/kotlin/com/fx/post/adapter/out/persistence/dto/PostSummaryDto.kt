package com.fx.post.adapter.out.persistence.dto

import java.time.LocalDateTime

data class PostSummaryDto(
    var id: Long,
    val userId: Long,
    val content: String,
    val createdAt: LocalDateTime,
    val likeCount: Long,
    val commentCount: Long,
)
