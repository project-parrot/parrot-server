package com.fx.post.domain

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
        fun createLike(postId: Long, userId: Long): Like {
            return Like(
                postId = postId,
                userId = userId
            )
        }
    }
}