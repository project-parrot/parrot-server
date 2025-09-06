package com.fx.user.adapter.`in`.web.dto.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class EmailRequest(

    @field:NotBlank(message = "이메일은 필수입니다.")
    @field:Email(message ="이메일 형식이 올바르지 않습니다.")
    @field:Size(max = 200, message = "이메일은 200자 이내여야 합니다.")
    val email: String,

)
