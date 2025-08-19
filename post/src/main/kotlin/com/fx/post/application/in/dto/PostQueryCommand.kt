package com.fx.post.application.`in`.dto

import org.springframework.data.domain.Pageable

data class PostQueryCommand(
    val userId: Long?= null,
    val userIds: List<Long>?= null,
    val postId: Long?,

    val pageable: Pageable
)
