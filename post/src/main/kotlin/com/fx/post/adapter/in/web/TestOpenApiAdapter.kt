package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.resolver.AuthUser
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestOpenApiAdapter { // TODO 테스트 클래스, 제거해 주세요

    private val log = LoggerFactory.getLogger(TestOpenApiAdapter::class.java)

    @GetMapping("/api/test")
    fun test(@AuthenticatedUser authUser: AuthUser) {
        log.info("userID : ${authUser.userId}")
        log.info("userRole : ${authUser.role}")
    }

}