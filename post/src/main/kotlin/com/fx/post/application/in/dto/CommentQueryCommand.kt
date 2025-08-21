package com.fx.post.application.`in`.dto

import org.springframework.data.domain.Pageable

data class CommentQueryCommand(
    val userId: Long?= null,
    val postId: Long?= null,
    val commentId: Long?= null,
    val parentId: Long?= null,

    val pageable: Pageable
)
