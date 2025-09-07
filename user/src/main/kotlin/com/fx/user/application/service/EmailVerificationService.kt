package com.fx.user.application.service

import com.fx.user.application.port.`in`.EmailVerificationUseCase
import com.fx.user.application.port.`in`.dto.EmailVerifyCommand
import com.fx.user.application.port.out.message.MailSenderPort
import com.fx.user.application.port.out.persistence.SignUpVerificationPort
import com.fx.user.exception.EmailVerificationException
import com.fx.user.exception.errorcode.EmailVerificationErrorCode
import jakarta.transaction.Transactional
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import java.util.UUID
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


    @Transactional
    override fun verifyCodeAndIssueTempToken(emailVerifyCommand: EmailVerifyCommand): String {
        val savedVerificationCode = signUpVerificationPort.getVerificationCode(emailVerifyCommand.email)
            ?: throw EmailVerificationException(EmailVerificationErrorCode.CODE_NOT_FOUND)

        if (savedVerificationCode != emailVerifyCommand.verificationCode)
            throw EmailVerificationException(EmailVerificationErrorCode.CODE_MISMATCH)

        // 인증 성공 → 임시 토큰 발급
        val tempToken = UUID.randomUUID().toString()
        signUpVerificationPort.saveTempToken(emailVerifyCommand.email, tempToken) // TTL 10분
        signUpVerificationPort.deleteVerificationCode(emailVerifyCommand.email) // 인증 코드 삭제

        return tempToken
    }

}