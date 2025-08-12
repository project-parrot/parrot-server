package com.fx.user.adapter.out.cache

import com.fx.global.annotation.hexagonal.CacheAdapter
import com.fx.user.application.out.JwtCachePort
import jakarta.servlet.http.HttpSession
import java.util.*

@CacheAdapter
class RedisJwtCacheAdapter(
    private val httpSession: HttpSession
): JwtCachePort {
    private val REFRESHTOKEN: String = "refreshToken"

    override fun saveToken(refreshToken: String, expiration: Date) {
        httpSession.setAttribute(REFRESHTOKEN, refreshToken)
        httpSession.maxInactiveInterval = ((expiration.time - Date().time) / 1000).toInt()
    }

    override fun getToken(): String? {
        return httpSession.getAttribute(REFRESHTOKEN)?.toString()
    }

}