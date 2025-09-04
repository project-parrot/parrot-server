package com.fx.user.application.port.`in`

import com.fx.user.application.port.`in`.dto.ProfileUpdateCommand
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface ProfileCommandUseCase {

    fun updateProfile(@Valid updateCommand: ProfileUpdateCommand): Boolean

}