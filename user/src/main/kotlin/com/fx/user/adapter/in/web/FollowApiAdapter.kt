package com.fx.user.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.user.adapter.`in`.web.dto.follow.FollowResponse
import com.fx.user.adapter.security.dto.AuthenticatedUser
import com.fx.user.application.`in`.FollowCommandUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@WebInputAdapter
@RequestMapping("/api/v1/follows")
class FollowApiAdapter(
    private val followCommandUseCase: FollowCommandUseCase
) {

    @PostMapping("/{userId}")
    fun followUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable userId: Long
    ): ResponseEntity<Api<FollowResponse>> =
        Api.OK(FollowResponse.from(followCommandUseCase.followUser(authenticatedUser.userId, userId)))

    @DeleteMapping("/{followId}")
    fun unfollowUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable followId: Long,
    ): ResponseEntity<Api<Boolean>> {
        followCommandUseCase.unfollowUser(authenticatedUser.userId, followId)
        return Api.OK(true)
    }

}