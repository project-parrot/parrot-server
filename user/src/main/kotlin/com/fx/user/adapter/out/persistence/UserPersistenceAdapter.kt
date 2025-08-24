package com.fx.user.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.user.adapter.out.persistence.entity.UserEntity
import com.fx.user.adapter.out.persistence.repository.UserRepository
import com.fx.user.application.out.persistence.UserPersistencePort
import com.fx.user.domain.User

@PersistenceAdapter
class UserPersistenceAdapter(
    private val userRepository: UserRepository
) : UserPersistencePort {

    override fun save(user: User): User =
        userRepository.save(UserEntity.fromDomain(user)).toDomain()

    override fun existsByEmail(email: String): Boolean =
        userRepository.existsByEmail(email)


    override fun findByEmail(email: String): User? =
        userRepository.findByEmail(email)?.orElse(null)?.toDomain()

    override fun findByOauthId(oauthId: String): User? =
        userRepository.findByOauthId(oauthId).orElse(null)?.toDomain()

}