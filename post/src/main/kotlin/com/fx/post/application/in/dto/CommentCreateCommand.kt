package com.fx.post.application.`in`.dto

data class CommentCreateCommand(
    val userId: Long,
    val content: String,
    val parentId: Long? = null
) {
}