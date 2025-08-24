package com.fx.user.adapter.security

import com.fx.global.annotation.hexagonal.SecurityAdapter
import com.fx.user.application.out.security.PasswordEncoderPort
import org.springframework.security.crypto.password.PasswordEncoder

@SecurityAdapter
class PasswordEncoderAdapter(
    private val passwordEncoder: PasswordEncoder
) : PasswordEncoderPort {

    override fun encode(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }

}