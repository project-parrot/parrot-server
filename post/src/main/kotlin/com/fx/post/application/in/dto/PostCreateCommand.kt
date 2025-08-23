package com.fx.post.application.`in`.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size


data class PostCreateCommand(
    @field:NotNull
    val userId: Long,
    @field:Size(max = 2000, message = "게시글은 2000자 이내여야 합니다.")
    val content: String? = null,
    val mediaIds: List<Long>? = null
)