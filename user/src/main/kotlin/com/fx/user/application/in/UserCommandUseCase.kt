package com.fx.user.application.`in`

import com.fx.user.application.`in`.dto.UserLoginCommand
import com.fx.user.application.`in`.dto.UserOAuthCommand
import com.fx.user.application.`in`.dto.UserSignUpCommand
import com.fx.user.domain.TokenInfo
import com.fx.user.domain.User
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface UserCommandUseCase{

    fun signUp(@Valid signUpCommand: UserSignUpCommand): User

    fun login(@Valid loginCommand: UserLoginCommand): TokenInfo

    fun findOrCreateOAuthUser(@Valid oauthCommand: UserOAuthCommand): User

    fun reIssue(header: String): TokenInfo

}
