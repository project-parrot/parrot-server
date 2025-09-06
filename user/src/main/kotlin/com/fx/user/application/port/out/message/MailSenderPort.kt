package com.fx.user.application.port.out.message

interface MailSenderPort {

    fun sendVerificationCode(email: String, code: String)

}