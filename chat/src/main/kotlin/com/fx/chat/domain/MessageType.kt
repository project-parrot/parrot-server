package com.fx.chat.domain

enum class MessageType(
    val description: String,
) {

    TEXT("텍스트"),
    IMAGE("사진"),
    VIDEO("영상"),
    FILE("파일")

}