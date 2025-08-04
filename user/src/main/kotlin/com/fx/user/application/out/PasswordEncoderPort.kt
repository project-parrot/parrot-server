package com.fx.user.application.out

interface PasswordEncoderPort {

    fun encode(rawPassword: String): String

    fun matches(rawPassword: String, encodedPassword: String): Boolean

}