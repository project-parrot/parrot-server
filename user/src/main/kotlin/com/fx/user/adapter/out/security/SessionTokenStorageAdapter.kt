package com.fx.user.adapter.out.security

import com.fx.global.annotation.hexagonal.TokenStorageAdapter
import com.fx.user.application.out.security.TokenStoragePort
import jakarta.servlet.http.HttpSession
import java.util.*

@TokenStorageAdapter
class SessionTokenStorageAdapter(
    private val httpSession: HttpSession
): TokenStoragePort {
    private val REFRESHTOKEN: String = "refreshToken"

    override fun saveToken(refreshToken: String, expiration: Date) {
        httpSession.setAttribute(REFRESHTOKEN, refreshToken)
        httpSession.maxInactiveInterval = ((expiration.time - Date().time) / 1000).toInt()
    }

    override fun getToken(): String? {
        return httpSession.getAttribute(REFRESHTOKEN)?.toString()
    }

}