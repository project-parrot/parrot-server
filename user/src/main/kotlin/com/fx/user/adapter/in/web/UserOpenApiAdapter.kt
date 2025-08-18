package com.fx.user.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.user.adapter.`in`.web.dto.user.TokenResponse
import com.fx.user.adapter.`in`.web.dto.user.UserIdResponse
import com.fx.user.adapter.`in`.web.dto.user.UserLoginRequest
import com.fx.user.adapter.`in`.web.dto.user.UserSignUpRequest
import com.fx.user.application.`in`.UserCommandUseCase
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

@WebInputAdapter
@RequestMapping("/open-api/v1/users")
class UserOpenApiAdapter(
    private val userCommandUseCase: UserCommandUseCase
) {

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    fun signUp(
        @RequestBody @Valid signUpRequest: UserSignUpRequest
    ): ResponseEntity<Api<UserIdResponse>> =
        Api.OK(UserIdResponse(
            userCommandUseCase.signUp(signUpRequest.toCommand()).id),
            "회원가입이 완료되었습니다.",
            HttpStatus.CREATED,
        )

    @Operation(summary = "로그인")
    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: UserLoginRequest
    ): ResponseEntity<Api<TokenResponse>> =
        Api.OK(
            TokenResponse.from(
                userCommandUseCase.login(loginRequest.toCommand()),
            ),
            "로그인되었습니다."
        )

    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    fun reIssue(@RequestHeader("Authorization") header: String): ResponseEntity<Api<TokenResponse>> =
        Api.OK(
            TokenResponse.from(
                userCommandUseCase.reIssue(header)
            )
        )

}