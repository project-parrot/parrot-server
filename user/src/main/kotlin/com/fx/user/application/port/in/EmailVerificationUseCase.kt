package com.fx.user.application.port.`in`

import com.fx.user.application.port.`in`.dto.EmailVerifyCommand

interface EmailVerificationUseCase {

    fun sendVerificationCode(email: String): Boolean

    fun verifyCodeAndIssueTempToken(emailVerifyCommand: EmailVerifyCommand): String

}