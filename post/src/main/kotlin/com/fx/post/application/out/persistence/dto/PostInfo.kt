package com.fx.post.application.out.persistence.dto

import com.fx.post.adapter.out.web.impl.dto.MediaInfo
import java.time.LocalDateTime

data class PostInfo(
    var id: Long,
    val userId: Long,
    val nickname: String?= null,
    val profileImageUrl: String? = null,
    val content: String,
    var mediaInfos: List<MediaInfo>?= null,
    val createdAt: LocalDateTime,
    val likeCount: Long,
    val commentCount: Long,
) {
    constructor(id: Long, userId: Long, content: String, createdAt: LocalDateTime, likeCount: Long, commentCount: Long)
            : this(id, userId, null, null, content, null, createdAt, likeCount, commentCount)
}
