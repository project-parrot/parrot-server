package com.fx.user.adapter.`in`.web

import com.fx.global.api.Api
import com.fx.user.adapter.`in`.web.dto.internal.UserExistsRequest
import com.fx.user.adapter.`in`.web.dto.profile.InternalProfileInfoResponse
import com.fx.user.application.port.`in`.FollowCommandUseCase
import com.fx.user.application.port.`in`.ProfileQueryUseCase
import com.fx.user.application.port.`in`.UserQueryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/internal")
class UserInternalApiAdapter(
    private val profileQueryUseCase: ProfileQueryUseCase,
    private val followCommandUseCase: FollowCommandUseCase,
    private val userQueryUseCase: UserQueryUseCase
) {
    @GetMapping("/follower/{userId}")
    fun getFollowersInfo(
        @PathVariable userId: Long,
    ): ResponseEntity<List<Long>> =
        ResponseEntity.ok(
            followCommandUseCase.getFollowerByUserId(userId)
        )

    @GetMapping("/users")
    fun getUsersInfo(
        @RequestParam userIds: List<Long>
    ): ResponseEntity<List<InternalProfileInfoResponse>> =
        ResponseEntity.ok(
            InternalProfileInfoResponse.fromProfileInfo(
                profileQueryUseCase.getUsersProfile(userIds)
            )
        )

    @PostMapping("/users/exists")
    fun existsUser(
        @RequestBody userExistsRequest: UserExistsRequest
    ): ResponseEntity<Api<Boolean>> =
        Api.OK(userQueryUseCase.existsUsers(userExistsRequest.userIds))

}