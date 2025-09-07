package com.fx.user.adapter.out.message

import com.fx.global.annotation.hexagonal.MessageOutputAdapter
import com.fx.user.application.port.out.message.MailSenderPort
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender

@MessageOutputAdapter
class SmtpMailAdapter(
    private val javaMailSender: JavaMailSender // `Could not autowire` IntelliJ 문제이므로 무시
) : MailSenderPort {

    override fun sendVerificationCode(email: String, code: String) {
        try {
            val message: MimeMessage = javaMailSender.createMimeMessage()
            message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email))
            message.subject = "[Parrot] 이메일 인증 코드 발송"
            message.setContent(buildEmailBody(code), "text/html; charset=utf-8")

            javaMailSender.send(message)
        } catch (e: Exception) {
            throw RuntimeException("메일 전송 실패", e) // TODO Exception 처리
        }
    }

    private fun buildEmailBody(code: String): String {
        return """
        <html>
        <body style="background-color:#ffffff; max-width:600px; margin:0 auto; padding:40px; font-family:sans-serif;">
            <h1 style="color:#4D6EF4;">Parrot 이메일 인증</h1>
            <p style="font-size:16px; line-height:1.6;">
                안녕하세요, Parrot입니다.<br/>
                서비스 이용을 위해 이메일 주소 확인이 필요합니다.<br/>
                아래 인증 코드를 입력해주세요.
            </p>
            <div style="margin:30px auto; font-size:30px; text-align:center; padding:20px; background:#f4f4f4; border-radius:8px; color:#000000; font-weight:bold; letter-spacing:5px;">
                $code
            </div>
            <p style="font-size:12px; color:#888888; text-align:center; margin-top:40px;">
                ※ 본 메일은 발신 전용입니다. 궁금한 점은 Parrot 고객센터로 문의해주세요.
            </p>
        </body>
        </html>
    """.trimIndent()
    }

}