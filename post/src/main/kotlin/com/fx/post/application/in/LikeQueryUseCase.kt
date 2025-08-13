package com.fx.post.application.`in`

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto

interface LikeQueryUseCase {

    fun getLikeUsers(postId: Long): List<Long>

    fun getMyLikedPosts(userId: Long, postId: Long): List<PostSummaryDto>

}