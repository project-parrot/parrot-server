package com.fx.user.adapter.`in`.web.dto.user

import com.fx.user.application.`in`.dto.UserSignUpCommand
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class UserSignUpRequest(

    @field:NotBlank(message = "이메일은 필수입니다.")
    @field:Email(message ="이메일 형식이 올바르지 않습니다.")
    @field:Size(max = 200, message = "이메일은 200자 이내여야 합니다.")
    val email: String,

    @field:NotBlank(message = "비밀번호는 필수입니다.")
    @field:Size(min = 5, max = 20, message = "비밀번호는 5자 이상 20자 이하여야 합니다.")
    @field:Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]*\$",
        message = "비밀번호는 영문과 숫자를 포함해야 합니다."
    )
    val password: String,

    @field:Pattern(regexp = "^01[016789]-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 형식이 올바르지 않습니다. 예: 010-1234-5678")
    val phone: String,

    @field:NotBlank(message = "닉네임은 필수입니다.")
    @field:Size(min = 2, max = 30, message = "닉네임은 2자 이상 30자 이하여야 합니다.")
    val nickname: String,

    val mediaId: Long?= null
) {
    fun toCommand(): UserSignUpCommand {
        return UserSignUpCommand(
            email = this.email,
            password = this.password,
            phone = this.phone,
            nickname = this.nickname,
            mediaId = this.mediaId
        )
    }
}