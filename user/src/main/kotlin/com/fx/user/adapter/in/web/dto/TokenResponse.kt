package com.fx.user.adapter.`in`.web.dto

import com.fx.user.domain.TokenInfo
import java.time.LocalDateTime

data class TokenResponse(
    val accessToken: String,
    val accessTokenExpiredAt: LocalDateTime,
    val refreshToken: String,
    val refreshTokenExpiredAt: LocalDateTime
) {

    companion object {
        @JvmStatic
        fun of(tokenInfo: TokenInfo): TokenResponse =
            TokenResponse(
                accessToken = tokenInfo.accessToken,
                accessTokenExpiredAt = tokenInfo.accessTokenExpiredAt,
                refreshToken = tokenInfo.refreshToken,
                refreshTokenExpiredAt = tokenInfo.refreshTokenExpiredAt
            )
    }

}