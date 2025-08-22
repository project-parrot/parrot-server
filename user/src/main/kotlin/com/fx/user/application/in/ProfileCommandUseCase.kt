package com.fx.user.application.`in`

import com.fx.user.application.`in`.dto.ProfileUpdateCommand
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface ProfileCommandUseCase {

    fun updateProfile(@Valid updateCommand: ProfileUpdateCommand): Boolean

}