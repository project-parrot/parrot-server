package com.fx.user.application.service

import com.fx.user.application.port.`in`.EmailVerificationUseCase
import com.fx.user.application.port.out.message.MailSenderPort
import com.fx.user.application.port.out.persistence.SignUpVerificationPort
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class EmailVerificationService(
    private val signUpVerificationPort: SignUpVerificationPort,
    private val mailSenderPort: MailSenderPort
) : EmailVerificationUseCase {

    private val backgroundScope = CoroutineScope(Dispatchers.IO)

    override fun sendVerificationCode(email: String): Boolean {
        // 인증코드 5자리 생성
        val verificationCode = Random.nextInt(0, 100000).toString().padStart(5, '0')

        // TTL 5분
        signUpVerificationPort.saveVerificationCode(email, verificationCode, 5L);

        backgroundScope.launch {
            mailSenderPort.sendVerificationCode(email, verificationCode)
        }

        return true
    }
}