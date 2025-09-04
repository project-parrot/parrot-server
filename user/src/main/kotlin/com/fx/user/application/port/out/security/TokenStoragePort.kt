package com.fx.user.application.port.out.security

import java.util.*

interface TokenStoragePort {
    fun saveToken(refreshToken: String, expiration: Date)

    fun getToken(): String?
}