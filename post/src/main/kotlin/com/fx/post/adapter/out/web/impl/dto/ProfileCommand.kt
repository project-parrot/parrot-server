package com.fx.post.adapter.out.web.impl.dto

data class ProfileCommand(
    val userId: Long,
    val nickname: String?,
    val profileImageUrl: String?
)
