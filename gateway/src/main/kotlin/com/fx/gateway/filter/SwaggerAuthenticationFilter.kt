package com.fx.gateway.filter

import org.apache.http.HttpHeaders
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.Base64

@Component
class SwaggerAuthenticationFilter(
    @Value("\${swagger.username}") private val username: String,
    @Value("\${swagger.password}") private val password: String
) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val path = exchange.request.uri.path
        if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
            val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            if (authHeader == null || !authHeader.startsWith("Basic ")) {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                exchange.response.headers.set(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Swagger\"")
                return exchange.response.setComplete()
            }

            val base64Credentials = authHeader.removePrefix("Basic ")
            val (decodeUsername, decodePassword) = String(Base64.getDecoder().decode(base64Credentials)).split(":")
            if (decodeUsername != username || decodePassword != password) {
                exchange.response.statusCode = HttpStatus.UNAUTHORIZED
                return exchange.response.setComplete()
            }
        }
        return chain.filter(exchange)
    }
}