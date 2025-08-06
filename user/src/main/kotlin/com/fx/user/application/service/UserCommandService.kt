package com.fx.user.application.service

import com.fx.user.application.`in`.UserCommandUseCase
import com.fx.user.application.`in`.dto.UserLoginCommand
import com.fx.user.application.`in`.dto.UserOAuthCommand
import com.fx.user.application.`in`.dto.UserSignUpCommand
import com.fx.user.application.out.ProfilePersistencePort
import com.fx.user.application.out.UserPersistencePort
import com.fx.user.application.out.JwtProviderPort
import com.fx.user.application.out.PasswordEncoderPort
import com.fx.user.domain.Profile
import com.fx.user.domain.TokenInfo
import com.fx.user.domain.User
import com.fx.user.exception.UserException
import com.fx.user.exception.errorcode.UserErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserCommandService(
    private val userPersistencePort: UserPersistencePort,
    private val profilePersistencePort: ProfilePersistencePort,
    private val jwtProviderPort: JwtProviderPort,
    private val passwordEncoderPort: PasswordEncoderPort
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

        // 암호화
        signUpCommand.password = passwordEncoderPort.encode(signUpCommand.password)

        // 등록
        val savedUser = userPersistencePort.save(User.createUser(signUpCommand))
        savedUser.id?.let { userId ->
            profilePersistencePort.save(Profile.createProfile(userId, signUpCommand.nickname))
        } ?: throw IllegalStateException("User ID must not be null")

        return savedUser
    }

    @Transactional
    override fun login(loginCommand: UserLoginCommand): TokenInfo {

        val user = userPersistencePort.findByEmail(loginCommand.email)
            ?: throw RuntimeException("사용자가 존재하지 않음")

        if (!passwordEncoderPort.matches(loginCommand.password, user.password!!)) {
            throw RuntimeException("비밀번호 다름")
        }


        return jwtProviderPort.generateTokens(user.id!!, user.role!!)
    }

    @Transactional
    override fun findOrCreateOAuthUser(oauthCommand: UserOAuthCommand): User {
        return userPersistencePort.findByOauthId(oauthCommand.oauthId)?: run {
            val savedUser = userPersistencePort.save(User.createOAuthUser(oauthCommand))
            savedUser.id?.let {
                userId ->
                profilePersistencePort.save(Profile.createOAuthProfile(userId, oauthCommand.nickname))
            }
            return savedUser
        }
    }

}