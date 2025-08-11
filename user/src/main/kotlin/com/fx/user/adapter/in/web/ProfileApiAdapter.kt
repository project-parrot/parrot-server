package com.fx.user.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebAdapter
import com.fx.global.api.Api
import com.fx.user.adapter.`in`.web.dto.ProfileInfoResponse
import com.fx.user.adapter.security.dto.AuthenticatedUser
import com.fx.user.application.`in`.ProfileQueryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@WebAdapter
@RequestMapping("/api/v1/profiles")
class ProfileApiAdapter(
    private val profileQueryAdapter: ProfileQueryUseCase
) {

    @GetMapping("/me")
    fun myProfile(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser
    ): ResponseEntity<Api<ProfileInfoResponse>> =
        Api.OK(ProfileInfoResponse.from(profileQueryAdapter.getMyProfile(authenticatedUser.userId)))

    @GetMapping("{userId}")
    fun otherProfile(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable userId: Long
    ): ResponseEntity<Api<ProfileInfoResponse>> =
        Api.OK(ProfileInfoResponse.from(profileQueryAdapter.getOtherProfile(
            viewerId = authenticatedUser.userId,
            targetUserId = userId))
        )

}