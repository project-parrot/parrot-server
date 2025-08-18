package com.fx.user.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.user.adapter.`in`.web.dto.profile.ProfileInfoResponse
import com.fx.user.adapter.`in`.web.dto.profile.ProfileUpdateRequest
import com.fx.user.adapter.security.dto.AuthenticatedUser
import com.fx.user.application.`in`.ProfileCommandUseCase
import com.fx.user.application.`in`.ProfileQueryUseCase
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@WebInputAdapter
@RequestMapping("/api/v1/profiles")
class ProfileApiAdapter(
    private val profileQueryUseCase: ProfileQueryUseCase,
    private val profileCommandUseCase: ProfileCommandUseCase
) {

    @Operation(summary = "나의 프로필 조회")
    @GetMapping("/me")
    fun myProfile(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser
    ): ResponseEntity<Api<ProfileInfoResponse>> =
        Api.OK(ProfileInfoResponse.from(profileQueryUseCase.getMyProfile(authenticatedUser.userId)))

    @Operation(summary = "상대 프로필 조회", description = "상대 계정이 비공개이면서 팔로우되어 있지 않은 경우 예외발생")
    @GetMapping("{userId}")
    fun otherProfile(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable userId: Long
    ): ResponseEntity<Api<ProfileInfoResponse>> =
        Api.OK(ProfileInfoResponse.from(profileQueryUseCase.getOtherProfile(
            viewerId = authenticatedUser.userId,
            targetUserId = userId))
        )

    @Operation(summary = "내 프로필 수정")
    @PatchMapping("/me")
    fun updateProfile(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @RequestBody profileUpdateRequest: ProfileUpdateRequest
    ): ResponseEntity<Api<Boolean>> =
        Api.OK(profileCommandUseCase.updateProfile(
            authenticatedUser.userId, profileUpdateRequest.toCommand()),
            "프로필 수정이 완료되었습니다."
        )

}