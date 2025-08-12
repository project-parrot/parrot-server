package com.fx.post.application.`in`

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import java.time.LocalDateTime

interface LikeQueryUseCase {

    fun getLikeUsers(postId: Long): List<Long>

    fun getMyLikedPosts(userId: Long, before: LocalDateTime): List<PostSummaryDto>

}