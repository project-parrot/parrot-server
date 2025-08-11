package com.fx.global.resolver

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.dto.UserRole
import com.fx.global.exception.UnauthorizedException
import com.fx.global.exception.errorcode.UnauthorizedErrorCode
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
        parameter.getParameterAnnotation(AuthenticatedUser::class.java)?.required ?: true

        val requestContext = RequestContextHolder.getRequestAttributes()
            ?: throw UnauthorizedException(UnauthorizedErrorCode.NO_REQUEST_CONTEXT)

        val userIdStr = requestContext.getAttribute(X_USER_ID, RequestAttributes.SCOPE_REQUEST) as? String
            ?: throw UnauthorizedException(UnauthorizedErrorCode.MISSING_USER_ID_HEADER)

        val userRoleStr = requestContext.getAttribute(X_USER_ROLE, RequestAttributes.SCOPE_REQUEST) as? String
            ?: throw UnauthorizedException(UnauthorizedErrorCode.MISSING_USER_ROLE_HEADER)

        val userId = userIdStr.toLongOrNull() ?: throw UnauthorizedException(UnauthorizedErrorCode.INVALID_USER_ID_FORMAT)

        val userRole = try {
            UserRole.valueOf(userRoleStr)
        } catch (ex: Exception) {
            throw UnauthorizedException(UnauthorizedErrorCode.INVALID_USER_ROLE, ex)
        }

        return AuthUser(
            userId = userId,
            role = userRole
        )
    }
}