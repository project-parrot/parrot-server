package com.fx.user.application.out

import java.util.*

interface JwtCachePort {
    fun saveToken(refreshToken: String, expiration: Date)

    fun getToken(): String?
}