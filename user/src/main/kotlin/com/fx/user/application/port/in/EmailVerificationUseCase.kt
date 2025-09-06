package com.fx.user.application.port.`in`

interface EmailVerificationUseCase {
    fun sendVerificationCode(email: String): Boolean
}