package com.fx.user.domain

import com.fx.user.application.`in`.dto.UserSignUpCommand
import java.time.LocalDateTime

data class User(
    val id: Long? = null,
    val email: String,
    val password: String,
    val phone: String?,
    val oAuthProvider: String? = null,
    val oAuthId: Long? = null,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null
) {

    companion object {

        @JvmStatic
        fun createUser(signUpCommand: UserSignUpCommand): User {
            return User(
                email = signUpCommand.email,
                password = signUpCommand.password,
                phone = signUpCommand.phone
            )
        }
    }

}