package com.fx.gateway.filter

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fx.gateway.dto.TokenValidationResponse
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.cloud.gateway.route.Route
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils
import org.springframework.core.Ordered
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthenticationFilter(
    private val webClientBuilder: WebClient.Builder
) : GlobalFilter, Ordered {
    private val objectMapper = jacksonObjectMapper()

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {

        val path = exchange.request.path.toString()

        val route = exchange.getAttribute<Route>(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR)
        val routeId = route?.id
        if (path.contains("/open-api") || routeId == "user-service") {
            return chain.filter(exchange)
        }

        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
            ?: return unauthorized(exchange)
        return webClientBuilder
            .baseUrl("lb://user-service")
            .build()
            .post()
            .uri("/internal/token/validate")
            .header(HttpHeaders.AUTHORIZATION, authHeader)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String::class.java)
            .flatMap { bodyString ->
                val tokenResponse = objectMapper.readValue(bodyString, TokenValidationResponse::class.java)
                val mutatedRequest = exchange.request.mutate()
                    .header("X-User-Id", tokenResponse.userId.toString())
                    .header("X-User-Role", tokenResponse.role)
                    .build()
                val mutatedExchange = exchange.mutate().request(mutatedRequest).build()
                chain.filter(mutatedExchange)
            }
            .onErrorResume { e ->
                unauthorized(exchange)
            }
    }

    private fun unauthorized(exchange: ServerWebExchange): Mono<Void> {
        exchange.response.statusCode = HttpStatus.UNAUTHORIZED
        return exchange.response.setComplete()
    }

    override fun getOrder(): Int = -1 // 높은 우선순위
}