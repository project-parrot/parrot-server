package com.fx.user.adapter.`in`.web.dto

import com.fx.user.application.`in`.dto.UserLoginCommand

data class UserLoginRequest(
    val email: String,
    val password: String
) {

    fun toCommand(): UserLoginCommand =
        UserLoginCommand(
            email = this.email,
            password = this.password
        )
}