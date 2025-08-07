package com.fx.post.adapter.`in`.web.dto

import com.fx.post.application.`in`.dto.PostCreateCommand
import jakarta.validation.constraints.Size

data class PostCreateRequest(
    // 임시로 userId를 직접 받음
    val userId: Long,
    @field:Size(max = 2000, message = "게시글은 2000자 이내여야 합니다.")
    val content: String,
    //val mediaIds: List<Long>
) {
    fun toCommand(): PostCreateCommand {
        return PostCreateCommand(
            userId = this.userId,
            content = this.content,
            //mediaIds = this.mediaIds
        )
    }

}