package com.fx.user.application.out

import com.fx.user.domain.Profile

interface ProfilePersistencePort {

    fun save(profile: Profile): Profile

    fun existsByNickname(nickname: String): Boolean

}
