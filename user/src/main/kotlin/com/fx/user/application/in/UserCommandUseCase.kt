package com.fx.user.application.`in`

import com.fx.user.application.`in`.dto.UserSignUpCommand
import com.fx.user.domain.User

interface UserCommandUseCase{

    fun signUp(signUpCommand: UserSignUpCommand): User

}
