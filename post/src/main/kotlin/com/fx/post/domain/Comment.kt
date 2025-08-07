package com.fx.post.domain

import java.time.LocalDateTime

data class Comment(
    val id: Long,
    val userId: Long,
    val postId: Long,
    val parentId: Long? = null,
    val content:String,
    val isDeleted: Boolean = false,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {

    companion object {

    }
}