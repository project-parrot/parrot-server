package com.fx.user.application.`in`.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserOAuthCommand(

    @field:NotBlank(message = "oauth id는 필수입니다.")
    val oauthId: String,

    @field:NotBlank(message = "이메일은 필수입니다.")
    @field:Email(message ="이메일 형식이 올바르지 않습니다.")
    val email: String,

    @field:NotBlank(message = "닉네임은 필수입니다.")
    val nickname: String,

    val phone: String? // Google 의 경우 Phone 미재공
)