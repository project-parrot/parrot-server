package com.fx.user.adapter.`in`.web.dto

import com.fx.user.domain.TokenInfo

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
) {

    companion object {
        @JvmStatic
        fun of(tokenInfo: TokenInfo): TokenResponse =
            TokenResponse(
                accessToken = tokenInfo.accessToken,
                refreshToken = tokenInfo.refreshToken,
            )
    }

}