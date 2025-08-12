package com.fx.user.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.user.adapter.security.dto.AuthenticatedUser
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@WebInputAdapter
@RequestMapping("/api/v1/user")
class UserApiAdapter {

    @GetMapping("/test")
    fun testController(@AuthenticationPrincipal authenticatedUser: AuthenticatedUser): String {
        return "Hello, userId=${authenticatedUser.userId}, role=${authenticatedUser.role}"
    }

}