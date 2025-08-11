package com.fx.user.adapter.security

import com.fx.global.annotation.hexagonal.SecurityAdapter
import com.fx.global.dto.UserRole
import com.fx.user.application.out.JwtProviderPort
import com.fx.user.domain.AuthenticatedUserInfo
import com.fx.user.domain.TokenInfo
import com.fx.user.exception.JwtException
import com.fx.user.exception.errorcode.JwtErrorCode
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@SecurityAdapter
class JwtProvider(
    @Value("\${jwt.secret.key}") private val secretKey: String,
    @Value("\${jwt.access-token.plus-hour}") private val accessTokenPlusHour: Long,
    @Value("\${jwt.refresh-token.plus-hour}") private val refreshTokenPlusHour: Long
) : JwtProviderPort {

    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    override fun generateTokens(userId: Long, role: UserRole): TokenInfo {
        val accessToken = createToken(userId, role, accessTokenPlusHour)
        val refreshToken = createToken(userId, role, refreshTokenPlusHour)

        return TokenInfo(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }

    override fun getAuthenticatedUserInfo(accessToken: String): AuthenticatedUserInfo {
        val claims = parseTokenSafely(accessToken)

        val userId = claims["userId"].toString().toLong()
        val role = UserRole.valueOf(claims["role"].toString())

        return AuthenticatedUserInfo(
            userId = userId,
            role = role
        )
    }

    override fun validateToken(token: String): Boolean {
        parseTokenSafely(token) // 예외 없으면 유효한 토큰
        return true
    }

    private fun parseTokenSafely(token: String): Claims {
        return try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: Exception) {
            throw when (e) {
                is SignatureException -> JwtException(JwtErrorCode.INVALID_TOKEN)
                is ExpiredJwtException -> JwtException(JwtErrorCode.EXPIRED_TOKEN)
                else -> JwtException(JwtErrorCode.TOKEN_EXCEPTION)
            }
        }
    }

    /**
     * JWT 생성
     */
    private fun createToken(userId: Long, role: UserRole, expireHour: Long): String {
        val claims = mutableMapOf<String, Any>()
        claims["userId"] = userId
        claims["role"] = role.toString()

        val expiredLocalDateTime = LocalDateTime.now().plusHours(expireHour)
        val expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault()).toInstant())

        return Jwts.builder()
            .signWith(key)
            .claims(claims)
            .expiration(expiredAt)
            .compact()
    }

}