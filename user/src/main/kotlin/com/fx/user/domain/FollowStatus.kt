package com.fx.user.domain

enum class FollowStatus(
    private val description: String
) {

    PENDING("대기"),
    APPROVED("승인")

}