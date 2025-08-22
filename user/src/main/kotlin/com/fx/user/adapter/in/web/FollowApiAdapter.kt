package com.fx.user.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.user.adapter.`in`.web.dto.follow.FollowResponse
import com.fx.user.adapter.`in`.web.dto.follow.FollowSearchParam
import com.fx.user.adapter.`in`.web.dto.follow.FollowUserResponse
import com.fx.user.adapter.security.dto.AuthenticatedUser
import com.fx.user.application.`in`.FollowCommandUseCase
import io.swagger.v3.oas.annotations.Operation
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

    @Operation(summary = "상대 팔로우하기", description = "{userId} 는 팔로우 대상 userId 입니다.")
    @PostMapping("/{userId}")
    fun followUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable userId: Long
    ): ResponseEntity<Api<FollowResponse>> =
        Api.OK(FollowResponse.from(followCommandUseCase.followUser(authenticatedUser.userId, userId)))

    @Operation(summary = "팔로우/팔로워/팔로잉요청 삭제")
    @DeleteMapping("/{followId}")
    fun unfollowUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable followId: Long
    ): ResponseEntity<Api<Boolean>> =
        Api.OK(followCommandUseCase.unfollowUser(authenticatedUser.userId, followId))

    @Operation(summary = "팔로우 요청 승인")
    @PatchMapping("/{followId}/approve")
    fun approveFollowUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable followId: Long
    ): ResponseEntity<Api<FollowResponse>> =
        Api.OK(FollowResponse.from(
            followCommandUseCase.approveFollowUser(authenticatedUser.userId, followId)),
            "팔로우 요청을 수락했습니다."
        )


    // 예시 : /api/v1/follows/{targetUserId}/followings?sort=id,DESC&size=20&nickname=user-nickname
    @Operation(summary = "팔로잉 목록 조회",
        description = "default : [sort=id,DESC], [size:20] <br>" +
            "요청 예시 : /api/v1/follows/{userId}/followings?sort=id,DESC&size=20&nickname=user-nickname <br>" +
            "다음 요청 파라미터 예시 : ?sort=id,DESC&size=20&followId=15 (**followId** 를 기준으로 조회합니다.)"
    )
    @GetMapping("/{userId}/followings")
    fun getUserFollowings(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable userId: Long,
        @ModelAttribute followSearchParam: FollowSearchParam,
        @PageableDefault(sort = ["id"] , direction = Sort.Direction.DESC, size = 20) pageable: Pageable // DEFAULT : 최신순 조회
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

    @Operation(summary = "팔로워 목록 조회")
    @GetMapping("/{userId}/followers")
    fun getUserFollowers(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @PathVariable userId: Long,
        @ModelAttribute followSearchParam: FollowSearchParam,
        @PageableDefault(sort = ["id"] , direction = Sort.Direction.DESC, size = 20) pageable: Pageable // DEFAULT : 최신순 조회
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