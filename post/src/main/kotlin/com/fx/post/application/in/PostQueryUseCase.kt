package com.fx.post.application.`in`

import com.fx.post.application.out.persistence.dto.PostInfo
import com.fx.post.application.`in`.dto.PostQueryCommand

interface PostQueryUseCase {

    fun getFollowersPosts(postQueryCommand: PostQueryCommand): List<PostInfo>

    fun getUserPosts(postQueryCommand: PostQueryCommand): List<PostInfo>

}