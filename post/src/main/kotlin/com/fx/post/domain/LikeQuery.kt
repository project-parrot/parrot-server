package com.fx.post.domain

import com.fx.post.application.`in`.dto.LikeQueryCommand
import org.springframework.data.domain.Pageable

data class LikeQuery(
    val userId: Long,
    val postId: Long?= null,

    val pageable: Pageable,
    val isDeleted: Boolean = false
) {
    companion object {
        @JvmStatic
        fun searchCondition(likeQueryCommand: LikeQueryCommand, isDeleted: Boolean) =
            LikeQuery(
                userId = likeQueryCommand.userId,
                postId = likeQueryCommand.postId,
                pageable = likeQueryCommand.pageable,
                isDeleted = isDeleted
            )
    }

}
