package com.fx.user.adapter.security

import com.fx.user.adapter.security.dto.AuthenticatedUser
import com.fx.user.application.out.JwtProviderPort
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val securityPort: JwtProviderPort
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        if (!header.isNullOrEmpty() && header.startsWith("Bearer ")) {
            val token = header.removePrefix("Bearer ").trim()

            if (securityPort.validateToken(token)) {
                val userId = securityPort.getUserId(token)
                val userRole = securityPort.getUserRole(token)

                val userDetails = AuthenticatedUser(
                    userId = userId,
                    role = userRole
                )

                val authentication =
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            }

        }
        filterChain.doFilter(request, response)
    }
}