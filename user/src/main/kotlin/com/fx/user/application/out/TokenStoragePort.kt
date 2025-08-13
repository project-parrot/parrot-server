package com.fx.user.application.out

import java.util.*

interface TokenStoragePort {
    fun saveToken(refreshToken: String, expiration: Date)

    fun getToken(): String?
}