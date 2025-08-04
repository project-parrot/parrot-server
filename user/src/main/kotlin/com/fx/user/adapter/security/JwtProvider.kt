package com.fx.user.adapter.security

import com.fx.global.dto.user.UserRole
import com.fx.user.application.out.JwtProviderPort
import com.fx.user.domain.TokenInfo
import com.fx.user.domain.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Component
class JwtProvider(
    @Value("\${jwt.secret.key}") private val secretKey: String,
    @Value("\${jwt.access-token.plus-hour}") private val accessTokenPlusHour: Long,
    @Value("\${jwt.refresh-token.plus-hour}") private val refreshTokenPlusHour: Long
) : JwtProviderPort {

    override fun generateTokens(user: User): TokenInfo {
        val access = createToken(user, accessTokenPlusHour)
        val refresh = createToken(user, refreshTokenPlusHour)

        return TokenInfo(
            accessToken = access.token,
            accessTokenExpiredAt = access.expiredAt,
            refreshToken = refresh.token,
            refreshTokenExpiredAt = refresh.expiredAt
        )
    }

    fun createAccessToken(user: User): JwtToken {
        return createToken(user, accessTokenPlusHour)
    }

    fun createRefreshToken(user: User): JwtToken {
        return createToken(user, refreshTokenPlusHour)
    }

    override fun getUserId(accessToken: String): Long {
        val claims = parseClaims(accessToken)
        return claims["userId"].toString().toLong()
    }

    override fun getUserRole(accessToken: String): UserRole {
        val claims = parseClaims(accessToken)
        val roleString = claims["role"].toString()
        return UserRole.valueOf(roleString)
    }

    override fun validateToken(token: String): Boolean {
        try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
                .build()
                .parseSignedClaims(token)
            return true
        } catch (exception: Exception) {
            throw RuntimeException("예외처리")
        }
    }

    private fun parseClaims(accessToken: String): Claims {
        try {
            return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
                .build()
                .parseSignedClaims(accessToken)
                .payload
        } catch (exception: Exception) {
            throw RuntimeException("예외처리")
        }
    }

    /**
     * JWT 생성
     */
    private fun createToken(user: User, expireHour: Long): JwtToken {
        val claims = mutableMapOf<String, Any>()
        claims["userId"] = user.id!!
        claims["role"] = user.role.toString()

        val expiredLocalDateTime = LocalDateTime.now().plusHours(expireHour)
        val expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant())

        val token = Jwts.builder()
            .signWith(Keys.hmacShaKeyFor(secretKey.toByteArray()))
            .claims(claims)
            .expiration(expiredAt)
            .compact()

        return JwtToken(token, expiredLocalDateTime)
    }

    data class JwtToken(
        val token: String,
        val expiredAt: LocalDateTime
    )

}