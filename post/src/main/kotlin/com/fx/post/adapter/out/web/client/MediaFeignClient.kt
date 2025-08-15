package com.fx.post.adapter.out.web.client

import com.fx.post.adapter.out.web.impl.dto.MediaUrlResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "media-service", path = "/internal")
interface MediaFeignClient {
    @GetMapping("/url")
    fun getUrl(@RequestParam mediaIds: List<Long>): ResponseEntity<List<MediaUrlResponse>>
}