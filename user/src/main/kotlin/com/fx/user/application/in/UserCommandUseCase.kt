package com.fx.user.application.`in`

import com.fx.user.application.`in`.dto.UserLoginCommand
import com.fx.user.application.`in`.dto.UserOAuthCommand
import com.fx.user.application.`in`.dto.UserSignUpCommand
import com.fx.user.domain.TokenInfo
import com.fx.user.domain.User

interface UserCommandUseCase{

    fun signUp(signUpCommand: UserSignUpCommand): User

    fun login(loginCommand: UserLoginCommand): TokenInfo

    fun findOrCreateOAuthUser(oauthCommand: UserOAuthCommand): User

    fun reIssue(header: String): TokenInfo

}
