package com.fx.post.adapter.`in`.web.dto

import com.fx.post.application.`in`.dto.PostCreateCommand

data class PostCreateRequest(
    // 임시로 userId를 직접 받음
    val userId: Long,
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