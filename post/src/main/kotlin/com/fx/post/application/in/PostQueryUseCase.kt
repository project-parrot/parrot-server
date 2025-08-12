package com.fx.post.application.`in`

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import java.time.LocalDateTime

interface PostQueryUseCase {

    fun getFollowersPosts(userId: Long, before: LocalDateTime): List<PostSummaryDto>

    fun getMyPosts(userId: Long, before: LocalDateTime): List<PostSummaryDto>

    fun getUserPosts(userId: Long, before: LocalDateTime): List<PostSummaryDto>
}