package com.fx.post.adapter.`in`.web.dto.post

import com.fx.global.resolver.AuthUser
import com.fx.post.application.`in`.dto.PostUpdateCommand
import jakarta.validation.constraints.Size

data class PostUpdateRequest(
    @field:Size(max = 2000, message = "게시글은 2000자 이내여야 합니다.")
    val content: String,
    val mediaIds: List<Long>?
) {
    fun toCommand(postId:Long, authUser: AuthUser): PostUpdateCommand {
        return PostUpdateCommand(
            userId = authUser.userId,
            role = authUser.role,
            postId = postId,
            content = this.content,
            mediaIds = this.mediaIds
        )
    }
}