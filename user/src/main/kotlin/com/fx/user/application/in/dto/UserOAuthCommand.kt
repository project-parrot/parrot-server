package com.fx.user.application.`in`.dto

data class UserOAuthCommand(
    val oauthId: String,
    val email: String,
    val nickname: String,
    val phone: String?
)