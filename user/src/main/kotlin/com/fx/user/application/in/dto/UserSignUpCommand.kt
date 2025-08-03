package com.fx.user.application.`in`.dto

data class UserSignUpCommand(
    val email: String,
    val password: String,
    val phone: String,
    val nickname: String
)