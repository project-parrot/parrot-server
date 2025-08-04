package com.fx.user.adapter.security.dto

import com.fx.global.dto.user.UserRole
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AuthenticatedUser(
    val userId: Long,
    val role: UserRole
) : UserDetails {

    override fun getAuthorities() = listOf(SimpleGrantedAuthority(role.name))
    override fun getPassword() = ""
    override fun getUsername() = userId.toString()
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}