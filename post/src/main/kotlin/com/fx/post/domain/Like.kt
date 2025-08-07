package com.fx.post.domain

import java.time.LocalDateTime

data class Like(
    val id: Long,
    val userId: Long,
    val postId: Long,
    val createdAt: LocalDateTime?= null,
    val updatedAt: LocalDateTime? = null
) {

    companion object {

    }
}