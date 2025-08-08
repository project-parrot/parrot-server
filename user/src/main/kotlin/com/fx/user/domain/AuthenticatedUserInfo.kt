package com.fx.user.domain

import com.fx.global.dto.UserRole

data class AuthenticatedUserInfo(
    val userId: Long,
    val role: UserRole
)