package com.fx.user.application.`in`.dto

data class ProfileUpdateCommand(

    val nickname: String,

    val statusMessage: String,

    val isPrivate: Boolean,

    val mediaId: Long?= null
)