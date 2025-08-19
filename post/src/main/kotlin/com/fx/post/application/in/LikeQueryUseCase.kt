package com.fx.post.application.`in`

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import com.fx.post.application.`in`.dto.LikeQueryCommand

interface LikeQueryUseCase {

    fun getLikeUsers(postId: Long): List<Long>

    fun getMyLikedPosts(likeQueryCommand: LikeQueryCommand): List<PostSummaryDto>
}