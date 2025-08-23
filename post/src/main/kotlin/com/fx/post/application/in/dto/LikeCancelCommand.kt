package com.fx.post.application.`in`.dto

import jakarta.validation.constraints.NotNull

data class LikeCancelCommand(
    @field:NotNull
    val postId: Long,
    @field:NotNull
    val userId: Long
)
