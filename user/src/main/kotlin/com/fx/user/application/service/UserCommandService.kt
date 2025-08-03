package com.fx.user.application.service

import com.fx.user.application.`in`.UserCommandUseCase
import com.fx.user.application.`in`.dto.UserSignUpCommand
import com.fx.user.application.out.ProfilePersistencePort
import com.fx.user.application.out.UserPersistencePort
import com.fx.user.domain.Profile
import com.fx.user.domain.User
import com.fx.user.exception.UserException
import com.fx.user.exception.errorcode.UserErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserCommandService(
    private val userPersistencePort: UserPersistencePort,
    private val profilePersistencePort: ProfilePersistencePort
) : UserCommandUseCase {

    @Transactional
    override fun signUp(signUpCommand: UserSignUpCommand): User {

        // email, nickname 존재시 예외
        if (userPersistencePort.existsByEmail(signUpCommand.email)) {
            throw UserException(UserErrorCode.EMAIL_EXISTS)
        }

        if (profilePersistencePort.existsByNickname(signUpCommand.nickname)) {
            throw UserException(UserErrorCode.NICKNAME_EXISTS)
        }

        // 등록
        val savedUser = userPersistencePort.save(User.createUser(signUpCommand))
        savedUser.id?.let { userId ->
            profilePersistencePort.save(Profile.createProfile(userId, signUpCommand.nickname))
        } ?: throw IllegalStateException("User ID must not be null")

        return savedUser
    }

}