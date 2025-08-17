package com.fx.user.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.user.adapter.`in`.web.dto.follow.FollowResponse
import com.fx.user.adapter.`in`.web.dto.follow.FollowSearchParam
import com.fx.user.adapter.`in`.web.dto.follow.FollowUserResponse
import com.fx.user.adapter.security.dto.AuthenticatedUser
import com.fx.user.application.`in`.FollowCommandUseCase
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

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
        @PathVariable followId: Long
    ): ResponseEntity<Api<Boolean>> =
        Api.OK(followCommandUseCase.unfollowUser(authenticatedUser.userId, followId))

    @PatchMapping("/{followId}/approve")
    fun approveFollowUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable followId: Long
    ): ResponseEntity<Api<FollowResponse>> =
        Api.OK(FollowResponse.from(
            followCommandUseCase.approveFollowUser(authenticatedUser.userId, followId)),
            "팔로우 요청을 수락했습니다."
        )

    // 예시 : /api/v1/follows/{targetUserId}/followings?sort=createdAt,DESC&size=20
    @GetMapping("/{userId}/followings")
    fun getUserFollowings(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable userId: Long,
        @ModelAttribute followSearchParam: FollowSearchParam,
        @PageableDefault(sort = ["createdAt"] , direction = Sort.Direction.DESC, size = 20) pageable: Pageable // DEFAULT : 최신순 조회
    ): ResponseEntity<Api<List<FollowUserResponse>>> {
        val followUserInfoList = followCommandUseCase.getUserFollowings(
            followSearchParam.toCommand(
                authenticatedUser.userId,
                userId,
                pageable
            )
        )
        return Api.OK(FollowUserResponse.from(followUserInfoList))
    }

    @GetMapping("/{userId}/followers")
    fun getUserFollowers(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable userId: Long,
        @ModelAttribute followSearchParam: FollowSearchParam,
        @PageableDefault(sort = ["createdAt"] , direction = Sort.Direction.DESC, size = 20) pageable: Pageable // DEFAULT : 최신순 조회
    ): ResponseEntity<Api<List<FollowUserResponse>>> {
        val followUserInfoList = followCommandUseCase.getUserFollowers(
            followSearchParam.toCommand(
                authenticatedUser.userId,
                userId,
                pageable
            )
        )
        return Api.OK(FollowUserResponse.from(followUserInfoList))
    }


}