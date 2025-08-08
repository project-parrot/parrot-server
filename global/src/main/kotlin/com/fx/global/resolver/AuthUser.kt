package com.fx.global.resolver

import com.fx.global.dto.UserRole

data class AuthUser(
    val userId: Long,
    val role: UserRole
)