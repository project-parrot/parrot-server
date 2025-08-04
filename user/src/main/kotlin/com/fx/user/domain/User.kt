package com.fx.user.domain

import com.fx.global.dto.user.UserRole
import com.fx.user.application.`in`.dto.UserSignUpCommand
import java.time.LocalDateTime

data class User(
    val id: Long? = null,
    val email: String,
    val password: String,
    val phone: String?,
    val oAuthProvider: String? = null,
    val oAuthId: String? = null,
    val oAuthProfileImageUrl: String? = null,
    val role: UserRole? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {

    companion object {

        @JvmStatic
        fun createUser(signUpCommand: UserSignUpCommand): User {
            return User(
                email = signUpCommand.email,
                password = signUpCommand.password,
                phone = signUpCommand.phone,
                role = UserRole.USER
            )
        }

        fun createOAuthUser(email: String, provider: String, oAuthId: String, profileImageUrl: String?): User {
            return User(
                email = email,
                password = "oauth2user",
                phone = null,
                oAuthProvider = provider,
                oAuthId = oAuthId,
                oAuthProfileImageUrl = profileImageUrl,
                role = UserRole.USER
            )
        }
    }

}