package com.fx.post.adapter.out.web.client

import com.fx.post.adapter.out.web.impl.dto.InternalProfileInfoResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "user-service", path = "/internal")
interface UserFeignClient {

    @GetMapping("/follower/{userId}")
    fun getFollowersInfo(
        @PathVariable userId: Long,
    ): ResponseEntity<List<Long>>

    @GetMapping("/users")
    fun getUsersInfo(
        @RequestParam userIds: List<Long>
    ): ResponseEntity<List<InternalProfileInfoResponse>>

}