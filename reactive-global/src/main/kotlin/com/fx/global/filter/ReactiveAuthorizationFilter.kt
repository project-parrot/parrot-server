package com.fx.global.filter

import com.fx.global.exception.UnauthorizedException
import com.fx.global.exception.errorcode.UnauthorizedErrorCode
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Order(1)
@Component
class ReactiveAuthorizationFilter: WebFilter {

    companion object {
        private const val X_USER_ID = "X-User-Id"
        private const val X_USER_ROLE = "X-User-Role"
    }

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void?> {
        val request = exchange.request

        if (HttpMethod.OPTIONS == request.method) {
            return chain.filter(exchange)
        }

        if (request.path.value().startsWith("/open-api/") || request.path.value().startsWith("/static/") || request.path.value().startsWith("/resources/")) {
            return chain.filter(exchange)
        }

        val userId = request.headers.getFirst(X_USER_ID)
            ?: return Mono.error(UnauthorizedException(UnauthorizedErrorCode.MISSING_USER_ID_HEADER))

        val userRole = request.headers.getFirst(X_USER_ROLE)
            ?: return Mono.error(UnauthorizedException(UnauthorizedErrorCode.MISSING_USER_ROLE_HEADER))

        // WebFlux 에서는 RequestAttributes 대신 exchange.attributes에 저장
        exchange.attributes[X_USER_ID] = userId
        exchange.attributes[X_USER_ROLE] = userRole

        return chain.filter(exchange)
    }
}