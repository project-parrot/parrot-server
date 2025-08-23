package com.fx.post.domain

import com.fx.post.application.`in`.dto.LikeAddCommand
import com.fx.post.application.`in`.dto.LikeCancelCommand
import java.time.LocalDateTime

data class Like(
    val id: Long?= null,
    val userId: Long,
    val postId: Long,
    val createdAt: LocalDateTime?= null,
    val updatedAt: LocalDateTime?= null
) {

    companion object {
        @JvmStatic
        fun addLike(likeAddCommand: LikeAddCommand): Like {
            return Like(
                postId = likeAddCommand.postId,
                userId = likeAddCommand.userId
            )
        }

        @JvmStatic
        fun cancelLike(likeCancelCommand: LikeCancelCommand): Like {
            return Like(
                postId = likeCancelCommand.postId,
                userId = likeCancelCommand.userId
            )
        }
    }
}