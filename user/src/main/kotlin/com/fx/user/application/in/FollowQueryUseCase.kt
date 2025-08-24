package com.fx.user.application.`in`

import com.fx.user.application.`in`.dto.FollowQueryCommand
import com.fx.user.application.out.persistence.dto.FollowUserInfo
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface FollowQueryUseCase {

    fun getUserFollowings(@Valid followQueryCommand: FollowQueryCommand): List<FollowUserInfo>

    fun getUserFollowers(@Valid followQueryCommand: FollowQueryCommand): List<FollowUserInfo>

}