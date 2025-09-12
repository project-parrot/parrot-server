package com.fx.user.application.service

import com.fx.user.application.port.`in`.UserQueryUseCase
import com.fx.user.application.port.out.persistence.UserPersistencePort
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userPersistencePort: UserPersistencePort
) : UserQueryUseCase {

    override fun existsUsers(userIds: List<Long>): Boolean {
        val existsUserIds = userPersistencePort.findAllById(userIds)
        return userIds.all { existsUserIds.contains(it) }
    }

}