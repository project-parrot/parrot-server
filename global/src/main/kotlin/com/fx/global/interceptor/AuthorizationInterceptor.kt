package com.fx.global.interceptor

import com.fx.global.exception.UnauthorizedException
import com.fx.global.exception.errorcode.UnauthorizedErrorCode
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler

@Component
class AuthorizationInterceptor(
    private val X_USER_ID: String = "X-User-Id",
    private val X_USER_ROLE: String = "X-User-Role"
) : HandlerInterceptor {

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if (handler is ResourceHttpRequestHandler) {
            return true;
        }


        val userId = request.getHeader(X_USER_ID)
            ?: throw UnauthorizedException(UnauthorizedErrorCode.MISSING_USER_ID_HEADER)

        val userRole = request.getHeader(X_USER_ROLE)
            ?: throw UnauthorizedException(UnauthorizedErrorCode.MISSING_USER_ROLE_HEADER)

        val requestContext = RequestContextHolder.getRequestAttributes()
            ?: throw UnauthorizedException(UnauthorizedErrorCode.NO_REQUEST_CONTEXT)

        requestContext.setAttribute(X_USER_ID, userId, RequestAttributes.SCOPE_REQUEST)
        requestContext.setAttribute(X_USER_ROLE, userRole, RequestAttributes.SCOPE_REQUEST)

        return true

    }
}