package com.fx.user.domain

import java.time.LocalDateTime

data class TokenInfo(
    val accessToken: String,
    val accessTokenExpiredAt: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiredAt: LocalDateTime
)