package com.fx.user.application.`in`

import com.fx.user.application.`in`.dto.ProfileUpdateCommand

interface ProfileCommandUseCase {

    fun updateProfile(userId: Long, updateCommand: ProfileUpdateCommand): Boolean

}