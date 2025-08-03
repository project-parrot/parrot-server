package com.fx.user.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.user.adapter.out.persistence.entity.UserEntity
import com.fx.user.adapter.out.persistence.repository.UserRepository
import com.fx.user.application.out.UserPersistencePort
import com.fx.user.domain.User

@PersistenceAdapter
class UserPersistenceAdapter(
    private val userRepository: UserRepository
) : UserPersistencePort {

    override fun save(user: User): User {
        return userRepository.save(UserEntity.fromDomain(user)).toDomain()
    }

    override fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

}