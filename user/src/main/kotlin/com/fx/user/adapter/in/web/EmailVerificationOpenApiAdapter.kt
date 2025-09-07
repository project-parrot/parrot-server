package com.fx.user.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.user.adapter.`in`.web.dto.user.EmailRequest
import com.fx.user.adapter.`in`.web.dto.user.EmailVerifyRequest
import com.fx.user.adapter.`in`.web.dto.user.EmailVerifyResponse
import com.fx.user.application.port.`in`.EmailVerificationUseCase
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@WebInputAdapter
@RequestMapping("/open-api/v1/email")
class EmailVerificationOpenApiAdapter(
    private val emailVerificationUseCase: EmailVerificationUseCase
) {

    @Operation(summary = "이메일 인증코드 발송", description = "회원가입 전 이메일 인증을 진행합니다.<br> 입력한 이메일로 5자리 인증코드가 발송됩니다.")
    @PostMapping
    fun sendVerificationCode(
        @RequestBody @Valid emailRequest: EmailRequest
    ): ResponseEntity<Api<Boolean>> =
        Api.OK(emailVerificationUseCase.sendVerificationCode(emailRequest.email), "인증코드가 발송되었습니다.")

    @Operation(summary = "이메일 인증 확인", description = "사용자가 입력한 인증 코드를 확인합니다. <br> 인증이 완료되면 임시토큰을 발급하며, 이 토큰은 회원가입 시 사용됩니다.")
    @PostMapping("/verify")
    fun verifyCode(
        @RequestBody @Valid emailVerifyRequest: EmailVerifyRequest
    ): ResponseEntity<Api<EmailVerifyResponse>> =
        Api.OK(
            EmailVerifyResponse(
                emailVerificationUseCase.verifyCodeAndIssueTempToken(emailVerifyRequest.toCommand())
            ), "검증이 완료되었습니다.")

}