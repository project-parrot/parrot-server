package com.fx.post.adapter.out.web.client

import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "user-service", path = "/internal")
interface UserFeignClient {
}