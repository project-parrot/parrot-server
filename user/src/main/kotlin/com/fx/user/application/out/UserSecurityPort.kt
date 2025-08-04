package com.fx.user.application.out

import com.fx.user.domain.TokenInfo
import com.fx.user.domain.User
import java.time.LocalDateTime

interface UserSecurityPort {

    fun generateTokens(user: User): TokenInfo

}