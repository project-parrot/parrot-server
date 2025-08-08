package com.fx.user.domain

import com.fx.global.dto.UserRole
import com.fx.user.application.`in`.dto.UserOAuthCommand
import com.fx.user.application.`in`.dto.UserSignUpCommand
import java.time.LocalDateTime

data class User(
    val id: Long? = null,
    val email: String,
    val password: String? = null,
    val phone: String? = null,
    val oauthId: String? = null,
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

        @JvmStatic
        fun createOAuthUser(oauthCommand: UserOAuthCommand): User {
            return User(
                email = oauthCommand.email,
                oauthId = oauthCommand.oauthId,
                phone = oauthCommand.phone,
                role = UserRole.USER
            )
        }
    }

}