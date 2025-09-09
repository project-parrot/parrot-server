package com.fx.chat.adapter.`in`.web

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiTestController {

    @GetMapping("/api/test")
    fun testApi(): String {
        return "ok"
    }


}