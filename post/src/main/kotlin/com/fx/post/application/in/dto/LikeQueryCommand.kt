package com.fx.post.application.`in`.dto

import org.springframework.data.domain.Pageable

data class LikeQueryCommand(
    val userId: Long,
    val postId: Long?,

    val pageable: Pageable
)
