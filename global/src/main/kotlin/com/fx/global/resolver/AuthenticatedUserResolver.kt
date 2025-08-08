package com.fx.global.resolver

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.dto.UserRole
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthenticatedUserResolver: HandlerMethodArgumentResolver {

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
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val required = parameter.getParameterAnnotation(AuthenticatedUser::class.java)?.required ?: true

        val requestContext = RequestContextHolder.getRequestAttributes()
            ?: throw RuntimeException("No request context available")

        val userIdStr = requestContext.getAttribute(X_USER_ID, RequestAttributes.SCOPE_REQUEST) as? String
            ?: throw RuntimeException("X-User-Id header not found")

        val userRoleStr = requestContext.getAttribute(X_USER_ROLE, RequestAttributes.SCOPE_REQUEST) as? String
            ?: throw RuntimeException("X-User-Role header not found")

        val userId = userIdStr.toLongOrNull() ?: throw RuntimeException("Invalid userId format")
        val userRole = UserRole.valueOf(userRoleStr)

        return AuthUser(
            userId = userId,
            role = userRole
        )
    }
}


