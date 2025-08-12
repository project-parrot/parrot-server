package com.fx.user.application.service

import com.fx.user.application.`in`.ProfileCommandUseCase
import com.fx.user.application.`in`.dto.ProfileUpdateCommand
import com.fx.user.application.out.ProfilePersistencePort
import com.fx.user.exception.ProfileException
import com.fx.user.exception.errorcode.ProfileErrorCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ProfileCommandService(
    private val profilePersistencePort: ProfilePersistencePort
): ProfileCommandUseCase {

    @Transactional
    override fun updateProfile(
        userId: Long,
        updateCommand: ProfileUpdateCommand
    ): Boolean {
        val profile = profilePersistencePort.findByProfile(userId)?: throw ProfileException(
            ProfileErrorCode.PROFILE_NOT_FOUND)

        if (profile.nickname != updateCommand.nickname) {
            isDuplicateNicknameWithThrow(updateCommand.nickname)
        }

        val updatedProfile = profile.updateProfile(updateCommand)

        return profilePersistencePort.updateProfile(updatedProfile).id != null
    }

    private fun isDuplicateNicknameWithThrow(nickname: String) {
        val isDuplicateNickname = profilePersistencePort.existsByNickname(nickname)
        if (isDuplicateNickname) {
            throw ProfileException(ProfileErrorCode.NICKNAME_EXISTS)
        }
    }

}