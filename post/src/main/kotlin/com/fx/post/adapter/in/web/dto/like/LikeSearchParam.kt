package com.fx.post.adapter.`in`.web.dto.like

import com.fx.post.application.`in`.dto.LikeQueryCommand
import org.springframework.data.domain.Pageable

data class LikeSearchParam(
    val postId: Long?= null
) {
    fun toCommand(userId: Long, pageable: Pageable): LikeQueryCommand =
        LikeQueryCommand(
            userId = userId,
            postId = this.postId,
            pageable = pageable,
        )
}
