package com.fx.user.adapter.`in`.web.dto

import com.fx.user.application.`in`.dto.UserSignUpCommand

data class UserSignUpRequest(
    val email: String,
    val password: String,
    val phone: String,
    val nickname: String
) {
    fun toCommand(): UserSignUpCommand {
        return UserSignUpCommand(
            email = this.email,
            password = this.password,
            phone = this.phone,
            nickname = this.nickname
        )
    }
}