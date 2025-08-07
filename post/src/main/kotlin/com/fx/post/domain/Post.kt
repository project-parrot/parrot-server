package com.fx.post.domain

import java.time.LocalDateTime

data class Post(
    val id: Long? = null,
    val userId: Long,
    val content: String?= null,
    val isDeleted: Boolean = false,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null

) {
    companion object {

    }
}