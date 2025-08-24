package com.fx.user.adapter.security

import com.fx.global.annotation.hexagonal.SecurityAdapter
import com.fx.user.adapter.security.dto.AuthenticatedUser
import com.fx.user.application.out.security.JwtProviderPort
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

@SecurityAdapter
class JwtAuthenticationFilter(
    private val jwtProviderPort: JwtProviderPort
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        if (!header.isNullOrEmpty() && header.startsWith("Bearer ")) {
            val token = header.removePrefix("Bearer ").trim()

            if (jwtProviderPort.validateToken(token)) {
                val authenticatedUserInfo = jwtProviderPort.getAuthenticatedUserInfo(token)

                val userDetails = AuthenticatedUser(
                    userId = authenticatedUserInfo.userId,
                    role = authenticatedUserInfo.role
                )

                val authentication =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }

        }
        filterChain.doFilter(request, response)
    }
}