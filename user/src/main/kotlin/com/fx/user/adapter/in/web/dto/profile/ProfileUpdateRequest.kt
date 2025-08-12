package com.fx.user.adapter.`in`.web.dto.profile

import com.fx.user.application.`in`.dto.ProfileUpdateCommand

data class ProfileUpdateRequest(

    val nickname: String,

    val statusMessage: String,

    val isPrivate: Boolean

) {
    fun toCommand(): ProfileUpdateCommand =
        ProfileUpdateCommand(
            nickname = this.nickname,
            statusMessage = this.statusMessage,
            isPrivate = this.isPrivate
        )
}