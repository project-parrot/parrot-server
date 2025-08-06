package com.fx.user.domain

data class TokenInfo(
    val accessToken: String,
    val refreshToken: String,
)