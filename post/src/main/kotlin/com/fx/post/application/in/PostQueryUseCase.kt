package com.fx.post.application.`in`

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import java.time.LocalDateTime

interface PostQueryUseCase {

    fun getFollowersPosts(userId: Long, postId: Long): List<PostSummaryDto>

    fun getMyPosts(userId: Long, postId: Long): List<PostSummaryDto>

    fun getUserPosts(userId: Long, postId: Long): List<PostSummaryDto>
}