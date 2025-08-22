package com.fx.user.adapter.`in`.web

import com.fx.user.adapter.`in`.web.dto.profile.InternalProfileInfoResponse
import com.fx.user.application.`in`.FollowCommandUseCase
import com.fx.user.application.`in`.ProfileQueryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/internal")
class UserInternalApiAdapter(
    private val profileQueryUseCase: ProfileQueryUseCase,
    private val followCommandUseCase: FollowCommandUseCase
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

}