package com.fx.user.application.`in`.dto

import org.jetbrains.annotations.NotNull
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

data class FollowQueryCommand(

    @field:NotNull
    val requestUserId: Long,

    @field:NotNull
    val targetUserId: Long,

    val followId: Long?,

    val nickname: String?,

    @field:NotNull
    val pageable: Pageable
)