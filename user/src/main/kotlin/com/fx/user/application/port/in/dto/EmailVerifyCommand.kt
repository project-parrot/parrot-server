package com.fx.user.application.port.`in`.dto

data class EmailVerifyCommand(

    val email: String,

    val verificationCode: String

)
