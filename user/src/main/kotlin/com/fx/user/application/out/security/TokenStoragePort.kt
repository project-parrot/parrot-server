package com.fx.user.application.out.security

import java.util.*

interface TokenStoragePort {
    fun saveToken(refreshToken: String, expiration: Date)

    fun getToken(): String?
}