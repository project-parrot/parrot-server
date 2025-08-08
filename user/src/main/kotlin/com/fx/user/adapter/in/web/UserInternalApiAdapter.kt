package com.fx.user.adapter.`in`.web

import com.fx.user.adapter.`in`.web.dto.TokenValidationResponse
import com.fx.user.application.`in`.UserCommandUseCase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal")
class UserInternalApiAdapter(
    private val userCommandUseCase: UserCommandUseCase
) {

    @PostMapping("/token/validate")
    fun validateToken(@RequestHeader("Authorization") authorizationHeaderToken: String): TokenValidationResponse {
        val authenticatedUserInfo =
            userCommandUseCase.getAuthenticatedUserInfo(authorizationHeaderToken)
        return TokenValidationResponse.of(authenticatedUserInfo)
    }

}