package com.fx.chat.config.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.dto.UserRole
import com.fx.global.exception.UnauthorizedException
import com.fx.global.exception.errorcode.UnauthorizedErrorCode
import com.fx.global.resolver.AuthUser
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.reactive.BindingContext
import org.springframework.web.reactive.result.method.HandlerMethodArgumentResolver
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Component
class ReactiveAuthenticatedUserResolver : HandlerMethodArgumentResolver {

    companion object {
        private const val X_USER_ID = "X-User-Id"
        private const val X_USER_ROLE = "X-User-Role"
    }

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        val hasAnnotation = parameter.hasParameterAnnotation(AuthenticatedUser::class.java)
        val isAuthUserType = parameter.parameterType == AuthUser::class.java
        return hasAnnotation && isAuthUserType
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        bindingContext: BindingContext,
        exchange: ServerWebExchange
    ): Mono<Any> {

        val required = parameter.getParameterAnnotation(AuthenticatedUser::class.java)?.required ?: true
        if (!required) return Mono.empty()

        val userIdStr = exchange.attributes[X_USER_ID] as? String
            ?: return Mono.error(UnauthorizedException(UnauthorizedErrorCode.MISSING_USER_ID_HEADER))

        val userRoleStr = exchange.attributes[X_USER_ROLE] as? String
            ?: return Mono.error(UnauthorizedException(UnauthorizedErrorCode.MISSING_USER_ROLE_HEADER))

        val userId = userIdStr.toLongOrNull()
            ?: return Mono.error(UnauthorizedException(UnauthorizedErrorCode.INVALID_USER_ID_FORMAT))

        val userRole = try {
            UserRole.valueOf(userRoleStr)
        } catch (ex: Exception) {
            return Mono.error(UnauthorizedException(UnauthorizedErrorCode.INVALID_USER_ROLE, ex))
        }

        return Mono.just(
            AuthUser(
                userId = userId,
                role = userRole
            )
        )
    }
}