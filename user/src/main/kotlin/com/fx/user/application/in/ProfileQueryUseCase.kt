package com.fx.user.application.`in`

import com.fx.user.domain.ProfileInfo

interface ProfileQueryUseCase {

    fun getMyProfile(userId: Long): ProfileInfo

}