package com.fx.post.application.`in`.dto

import jakarta.validation.constraints.NotNull

data class LikeAddCommand(
    @field:NotNull
    val postId: Long,
    @field:NotNull
    val userId: Long
)
