package com.fx.post.application.`in`

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto
import com.fx.post.application.`in`.dto.PostQueryCommand

interface PostQueryUseCase {

    fun getFollowersPosts(postQueryCommand: PostQueryCommand): List<PostSummaryDto>

    fun getUserPosts(postQueryCommand: PostQueryCommand): List<PostSummaryDto>

}