package com.fx.post.adapter.out.web.impl.dto

data class InternalProfileInfoResponse(
    val userId: Long,
    val nickname: String?= null,
    val profileImageUrl: String?= null
)