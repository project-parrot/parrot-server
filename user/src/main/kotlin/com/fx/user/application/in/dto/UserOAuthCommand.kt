package com.fx.user.application.`in`.dto

import java.security.AuthProvider

data class UserOAuthCommand(
    val oAuthProvider: AuthProvider
) {
}