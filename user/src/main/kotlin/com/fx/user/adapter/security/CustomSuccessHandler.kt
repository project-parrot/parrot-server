package com.fx.user.adapter.security

import com.fx.global.annotation.hexagonal.SecurityAdapter
import com.fx.global.dto.UserRole
import com.fx.user.adapter.security.dto.AuthenticatedUser
import com.fx.user.application.out.JwtProviderPort
import com.fx.user.exception.UserException
import com.fx.user.exception.errorcode.UserErrorCode
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler

@SecurityAdapter
class CustomSuccessHandler(
    private val jwtProviderPort: JwtProviderPort,
    @Value("\${jwt.access-token.plus-hour}") private val accessTokenPlusHour: Int,
    @Value("\${jwt.refresh-token.plus-hour}") private val refreshTokenPlusHour: Int
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication
    ) {
        val authenticatedUser = authentication.principal as AuthenticatedUser

        val role = authentication.authorities
            .firstOrNull()
            ?.authority ?: throw UserException(UserErrorCode.ROLE_NOT_FOUND)

        val tokenInfo =
            jwtProviderPort.generateTokens(authenticatedUser.userId, UserRole.valueOf(role))

        response?.apply {
            addCookie(createCookie("accessToken", tokenInfo.accessToken, (accessTokenPlusHour * 60 * 60)))
            addCookie(createCookie("refreshToken", tokenInfo.refreshToken, (refreshTokenPlusHour * 60 * 60)))
            sendRedirect("http://localhost:3000/")
        }

    }

    private fun createCookie(key: String, value: String, maxAge: Int): Cookie {
        return Cookie(key, value).apply {
//            this.secure = true
            this.maxAge = maxAge
            this.path = "/"
            this.isHttpOnly = true
        }
    }

}