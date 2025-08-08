package com.fx.user.application.out

import com.fx.global.dto.UserRole
import com.fx.user.domain.AuthenticatedUserInfo
import com.fx.user.domain.TokenInfo
import com.fx.user.domain.User

interface JwtProviderPort {

    fun generateTokens(userId: Long, role: UserRole): TokenInfo

    fun validateToken(token: String): Boolean

    fun getAuthenticatedUserInfo(accessToken: String): AuthenticatedUserInfo

}