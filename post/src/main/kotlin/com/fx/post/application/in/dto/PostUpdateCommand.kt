package com.fx.post.application.`in`.dto

import com.fx.global.dto.UserRole
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class PostUpdateCommand(
    @field:NotNull
    val userId: Long,
    @field:NotNull
    val role: UserRole,
    @field:NotNull
    val postId: Long,
    @field:Size(max = 2000, message = "게시글은 2000자 이내여야 합니다.")
    val content: String,
    val mediaIds: List<Long>?
)