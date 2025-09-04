package com.fx.user.application.port.`in`

import com.fx.user.application.port.`in`.dto.FollowQueryCommand
import com.fx.user.application.port.out.persistence.dto.FollowUserInfo
import jakarta.validation.Valid
import org.springframework.validation.annotation.Validated

@Validated
interface FollowQueryUseCase {

    fun getUserFollowings(@Valid followQueryCommand: FollowQueryCommand): List<FollowUserInfo>

    fun getUserFollowers(@Valid followQueryCommand: FollowQueryCommand): List<FollowUserInfo>

    fun getFollowPendingRequests(@Valid followQueryCommand: FollowQueryCommand): List<FollowUserInfo>

}