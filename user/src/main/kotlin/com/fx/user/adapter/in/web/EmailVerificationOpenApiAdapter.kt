package com.fx.user.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.user.adapter.`in`.web.dto.user.EmailRequest
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

    @PostMapping
    @Operation(summary = "이메일 인증", description = "회원가입 전 이메일 인증을 진행합니다.")
    fun sendVerificationCode(
        @RequestBody @Valid emailRequest: EmailRequest
    ): ResponseEntity<Api<Boolean>> =
        Api.OK(emailVerificationUseCase.sendVerificationCode(emailRequest.email), "인증코드가 발송되었습니다.")



}