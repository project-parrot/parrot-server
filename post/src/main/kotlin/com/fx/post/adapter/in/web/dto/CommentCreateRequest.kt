package com.fx.post.adapter.`in`.web.dto

import com.fx.post.application.`in`.dto.CommentCreateCommand
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CommentCreateRequest(
    // 임시로 userId를 받음
    val userId: Long,
    @field:NotBlank(message = "댓글 내용은 필수입니다.")
    @field:Size(max = 500, message = "댓글은 500자 이내여야 합니다.")
    val content: String,
    val parentId: Long? = null
) {
    fun toCommand(): CommentCreateCommand {
        return CommentCreateCommand(
            userId = this.userId,
            content = this.content,
            parentId = this.parentId
        )
    }
}