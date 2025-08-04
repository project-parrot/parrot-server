package com.fx.user.application.out

import com.fx.global.dto.user.UserRole
import com.fx.user.domain.TokenInfo
import com.fx.user.domain.User

interface JwtProviderPort {

    fun generateTokens(user: User): TokenInfo

    fun validateToken(token: String): Boolean

    fun getUserId(accessToken: String): Long

    fun getUserRole(accessToken: String): UserRole

}