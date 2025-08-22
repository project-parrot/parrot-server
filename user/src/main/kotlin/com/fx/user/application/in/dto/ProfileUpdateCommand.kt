package com.fx.user.application.`in`.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ProfileUpdateCommand(

    @field:NotNull
    val userId: Long,

    @field:NotBlank(message = "닉네임은 필수입니다.")
    @field:Size(min = 2, max = 30, message = "닉네임은 2자 이상 30자 이하여야 합니다.")
    val nickname: String,

    @field:Size(max = 200, message = "상태 메시지는 최대 200자 까지입니다.")
    val statusMessage: String?,

    @field:NotNull
    val isPrivate: Boolean,

    val mediaId: Long?= null
)