package com.fx.global.dto

enum class Context(
    private val description: String
) {
    POST("POST"),
    CHAT("CHAT"),
    PROFILE("PROFILE")
}