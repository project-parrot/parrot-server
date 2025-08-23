package com.fx.post.application.`in`.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class CommentCreateCommand(
    @field:NotNull
    val userId: Long,
    @field:NotBlank(message = "댓글 내용은 필수입니다.")
    @field:Size(max = 500, message = "댓글은 500자 이내여야 합니다.")
    val content: String,
    val parentId: Long? = null
) {
}