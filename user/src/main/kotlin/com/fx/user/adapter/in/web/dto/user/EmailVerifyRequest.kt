package com.fx.user.adapter.`in`.web.dto.user

import com.fx.user.application.port.`in`.dto.EmailVerifyCommand
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class EmailVerifyRequest(

    @field:NotBlank(message = "이메일은 필수입니다.")
    @field:Email(message ="이메일 형식이 올바르지 않습니다.")
    @field:Size(max = 200, message = "이메일은 200자 이내여야 합니다.")
    val email: String,

    @field:Size(min = 5, max = 5, message = "인증코드는 5자리 숫자여야 합니다.")
    @field:Pattern(regexp = "^[0-9]+$", message = "인증코드는 숫자만 입력 가능합니다.")
    val verificationCode: String

) {

    fun toCommand(): EmailVerifyCommand =
        EmailVerifyCommand(
            email = this.email,
            verificationCode = this.verificationCode
        )

}
