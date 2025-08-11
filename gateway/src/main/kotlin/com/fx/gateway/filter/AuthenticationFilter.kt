package com.fx.gateway.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthenticationFilter(
    private val objectMapper: ObjectMapper,
    @Value("\${jwt.secret.key}") private val secretKey: String,
) : GlobalFilter, Ordered {


    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val path = exchange.request.path.toString()

        if (path.contains("/open-api")) {
            return chain.filter(exchange)
        }

        val token = extractToken(exchange) ?: return unauthorizedResponse(exchange, "Authorization header missing or invalid")

        val claims = try {
            parseToken(token)
        } catch (e: ExpiredJwtException) {
            return unauthorizedResponse(exchange, "Token expired")
        } catch (e: SignatureException) {
            return unauthorizedResponse(exchange, "Invalid token signature")
        } catch (e: Exception) {
            return unauthorizedResponse(exchange, "Invalid token")
        }

        val mutatedExchange = try {
            addUserHeaders(exchange, claims)
        } catch (e: IllegalArgumentException) {
            return unauthorizedResponse(exchange, e.message ?: "Missing required claims")
        }

        return chain.filter(mutatedExchange)
    }

    private fun extractToken(exchange: ServerWebExchange): String? {
        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION) ?: return null
        if (!authHeader.startsWith("Bearer ")) return null
        return authHeader.removePrefix("Bearer ").trim()
    }

    private fun parseToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun addUserHeaders(exchange: ServerWebExchange, claims: Claims): ServerWebExchange {
        val userId = claims["userId"]?.toString()?.takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("userId claim missing or empty")
        val role = claims["role"]?.toString()?.takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("role claim missing or empty")

        val mutatedRequest = exchange.request.mutate()
            .header("X-User-Id", userId)
            .header("X-User-Role", role)
            .build()

        return exchange.mutate().request(mutatedRequest).build()
    }

    private fun unauthorizedResponse(exchange: ServerWebExchange, message: String): Mono<Void> {
        val response = exchange.response
        response.statusCode = HttpStatus.UNAUTHORIZED
        response.headers.contentType = MediaType.APPLICATION_JSON

        val errorBody = mapOf(
            "success" to false,
            "code" to HttpStatus.UNAUTHORIZED.value(),
            "message" to message,
            "body" to null
        )

        val bytes = objectMapper.writeValueAsBytes(errorBody)

        return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)))
    }

    override fun getOrder(): Int = -1 // 높은 우선순위
}