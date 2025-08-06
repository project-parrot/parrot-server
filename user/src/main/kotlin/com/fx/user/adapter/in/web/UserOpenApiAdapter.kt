package com.fx.user.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebAdapter
import com.fx.global.api.Api
import com.fx.user.adapter.`in`.web.dto.TokenResponse
import com.fx.user.adapter.`in`.web.dto.UserIdResponse
import com.fx.user.adapter.`in`.web.dto.UserLoginRequest
import com.fx.user.adapter.`in`.web.dto.UserSignUpRequest
import com.fx.user.application.`in`.UserCommandUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@WebAdapter
@RequestMapping("/open-api/v1/user")
class UserOpenApiAdapter(
    private val userCommandUseCase: UserCommandUseCase
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid signUpRequest: UserSignUpRequest): ResponseEntity<Api<UserIdResponse>> {
        val user = userCommandUseCase.signUp(signUpRequest.toCommand())
        return Api.OK(UserIdResponse(user.id))
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: UserLoginRequest): ResponseEntity<Api<TokenResponse>> {
        val tokenInfo = userCommandUseCase.login(loginRequest.toCommand())
        return Api.OK(TokenResponse.of(tokenInfo))
    }

}