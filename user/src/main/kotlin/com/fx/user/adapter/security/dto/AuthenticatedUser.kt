package com.fx.user.adapter.security.dto

import com.fx.global.dto.UserRole
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

data class AuthenticatedUser(
    val userId: Long,
    val role: UserRole
) : UserDetails, OAuth2User {

    override fun getAuthorities() = listOf(SimpleGrantedAuthority(role.name))
    override fun getPassword() = ""
    override fun getUsername() = userId.toString()
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true

    override fun getAttributes(): Map<String, Any> = attributes
    override fun getName(): String = userId.toString()

}