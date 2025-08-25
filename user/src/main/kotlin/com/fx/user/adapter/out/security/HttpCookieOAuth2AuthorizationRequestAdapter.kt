package com.fx.user.adapter.out.security

import com.fx.global.annotation.hexagonal.SecurityAdapter
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.util.SerializationUtils
import java.util.Base64

@SecurityAdapter
class HttpCookieOAuth2AuthorizationRequestAdapter:
    AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    companion object {
        private const val COOKIE_NAME = "oauth2_auth_request"
        private const val COOKIE_EXPIRE_SECONDS = 180
    }

    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return request.cookies?.firstOrNull { it.name == COOKIE_NAME }?.let {
            deserialize(it.value)
        }
    }

    override fun saveAuthorizationRequest(
        authorizationRequest: OAuth2AuthorizationRequest?,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        if (authorizationRequest == null) {
            removeAuthorizationRequest(request, response)
            return
        }

        val cookie = Cookie(COOKIE_NAME, serialize(authorizationRequest))
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = COOKIE_EXPIRE_SECONDS
        response.addCookie(cookie)
    }

    override fun removeAuthorizationRequest(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): OAuth2AuthorizationRequest? {
        val authRequest = loadAuthorizationRequest(request)
        request.cookies?.firstOrNull { it.name == COOKIE_NAME }?.let {
            val cookie = Cookie(it.name, null)
            cookie.path = "/"
            cookie.maxAge = 0
            response.addCookie(cookie)
        }
        return authRequest
    }

    private fun serialize(obj: Any): String {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj))
    }

    private fun deserialize(cookie: String): OAuth2AuthorizationRequest {
        return SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie)) as OAuth2AuthorizationRequest
    }

}