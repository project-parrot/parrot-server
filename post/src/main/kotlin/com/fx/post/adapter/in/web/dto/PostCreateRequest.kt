package com.fx.post.adapter.`in`.web.dto

import com.fx.global.resolver.AuthUser
import com.fx.post.application.`in`.dto.PostCreateCommand
import jakarta.validation.constraints.Size

data class PostCreateRequest(
    @field:Size(max = 2000, message = "게시글은 2000자 이내여야 합니다.")
    val content: String,
    val mediaIds: List<Long>?
) {
    fun toCommand(authUser: AuthUser): PostCreateCommand {
        return PostCreateCommand(
            userId = authUser.userId,
            content = this.content,
            mediaIds = this.mediaIds
        )
    }

}