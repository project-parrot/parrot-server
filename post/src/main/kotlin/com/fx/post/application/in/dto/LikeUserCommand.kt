package com.fx.post.application.`in`.dto

data class LikeUserCommand(
    val userId: Long,
    val nickname: String?,
    val profileImageUrl: String?
)
