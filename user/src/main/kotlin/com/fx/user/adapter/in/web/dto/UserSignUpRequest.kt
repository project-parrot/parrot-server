package com.fx.user.adapter.`in`.web.dto

import com.fx.user.application.`in`.dto.UserSignUpCommand

data class UserSignUpRequest(
    val email: String,
    val password: String,
    val phone: String,
    val nickname: String
) {
    fun toCommand(signUpRequest: UserSignUpRequest): UserSignUpCommand {
        return UserSignUpCommand(
            email = signUpRequest.email,
            password = signUpRequest.password,
            phone = signUpRequest.phone,
            nickname = signUpRequest.nickname
        )
    }
}