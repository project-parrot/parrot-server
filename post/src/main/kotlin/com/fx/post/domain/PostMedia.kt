package com.fx.post.domain

import java.time.LocalDateTime

data class PostMedia(
    val id: Long,
    val postId: Long,
    val mediaId: Long,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {

    companion object {

    }

}