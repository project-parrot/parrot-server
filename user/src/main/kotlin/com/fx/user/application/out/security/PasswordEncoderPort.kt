package com.fx.user.application.out.security

interface PasswordEncoderPort {

    fun encode(rawPassword: String): String

    fun matches(rawPassword: String, encodedPassword: String): Boolean

}