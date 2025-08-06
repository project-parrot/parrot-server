package com.fx.user.application.out

import com.fx.user.domain.User

interface UserPersistencePort {

    fun save(user: User): User

    fun existsByEmail(email: String): Boolean

    fun findByEmail(email: String): User?

    fun findByOauthId(oauthId: String): User?

}