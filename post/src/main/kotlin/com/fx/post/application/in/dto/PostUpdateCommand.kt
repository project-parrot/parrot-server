package com.fx.post.application.`in`.dto

import com.fx.global.dto.UserRole

class PostUpdateCommand(
    val userId: Long,
    val role: UserRole,
    val content: String,
    //val mediaIds: List<Long>
)