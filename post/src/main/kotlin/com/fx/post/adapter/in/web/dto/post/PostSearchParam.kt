package com.fx.post.adapter.`in`.web.dto.post

import com.fx.post.application.`in`.dto.PostQueryCommand
import org.springframework.data.domain.Pageable

data class PostSearchParam(
    val postId: Long?= null
) {

    fun toCommand(userId:Long?= null, pageable: Pageable): PostQueryCommand =
        PostQueryCommand(
            userId = userId,
            postId = this.postId,
            pageable = pageable
        )

}
