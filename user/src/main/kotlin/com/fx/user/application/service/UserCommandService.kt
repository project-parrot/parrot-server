package com.fx.user.application.service

import com.fx.global.dto.Context
import com.fx.global.dto.MediaMappingEventDto
import com.fx.user.application.`in`.UserCommandUseCase
import com.fx.user.application.`in`.dto.UserLoginCommand
import com.fx.user.application.`in`.dto.UserOAuthCommand
import com.fx.user.application.`in`.dto.UserSignUpCommand
import com.fx.user.application.out.*
import com.fx.user.application.out.message.MessageProducerUseCase
import com.fx.user.application.out.persistence.ProfilePersistencePort
import com.fx.user.application.out.persistence.UserPersistencePort
import com.fx.user.application.out.security.JwtProviderPort
import com.fx.user.application.out.security.PasswordEncoderPort
import com.fx.user.domain.Profile
import com.fx.user.domain.TokenInfo
import com.fx.user.domain.User
import com.fx.user.exception.ProfileException
import com.fx.user.exception.UserException
import com.fx.user.exception.errorcode.ProfileErrorCode
import com.fx.user.exception.errorcode.UserErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserCommandService(
    private val userPersistencePort: UserPersistencePort,
    private val profilePersistencePort: ProfilePersistencePort,
    private val jwtProviderPort: JwtProviderPort,
    private val passwordEncoderPort: PasswordEncoderPort,
    private val messageProducerUseCase: MessageProducerUseCase
) : UserCommandUseCase {

    @Transactional
    override fun signUp(signUpCommand: UserSignUpCommand): User {

        // email, nickname 존재시 예외
        if (userPersistencePort.existsByEmail(signUpCommand.email)) {
            throw UserException(UserErrorCode.EMAIL_EXISTS)
        }

        if (profilePersistencePort.existsByNickname(signUpCommand.nickname)) {
            throw ProfileException(ProfileErrorCode.NICKNAME_EXISTS)
        }

        // 암호화
        signUpCommand.password = passwordEncoderPort.encode(signUpCommand.password)

        val mediaIds = signUpCommand.mediaId?.let { listOf(it) } ?: emptyList()

        // 등록
        val savedUser = userPersistencePort.save(User.createUser(signUpCommand))
        savedUser.id?.let { userId ->
            val savedProfile = profilePersistencePort.save(
                Profile.createProfile(userId, signUpCommand.nickname)
            )
            messageProducerUseCase.sendMapping(
                MediaMappingEventDto(context = Context.PROFILE, referenceId = savedProfile.id, userId = userId, mediaIds = mediaIds)
            )
        } ?: throw UserException(UserErrorCode.USER_ID_NULL)

        return savedUser
    }

    @Transactional
    override fun login(loginCommand: UserLoginCommand): TokenInfo {

        val user = userPersistencePort.findByEmail(loginCommand.email)
            ?: throw UserException(UserErrorCode.USER_NOT_FOUND)

        if (!passwordEncoderPort.matches(loginCommand.password, user.password!!)) {
            throw UserException(UserErrorCode.INVALID_PASSWORD)
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

    override fun reIssue(header: String): TokenInfo {
        return jwtProviderPort.reIssueToken(header)
    }

}