package com.fx.user.adapter.`in`.web.dto

import com.fx.user.domain.AuthenticatedUserInfo

data class TokenValidationResponse(
    val userId: Long,
    val role: String
) {

    companion object {
        @JvmStatic
        fun of(authenticatedUserInfo: AuthenticatedUserInfo): TokenValidationResponse =
            TokenValidationResponse(
                userId = authenticatedUserInfo.userId,
                role = authenticatedUserInfo.role.toString(),
            )
    }

}