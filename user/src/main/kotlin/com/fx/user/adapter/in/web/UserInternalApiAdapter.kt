package com.fx.user.adapter.`in`.web

import com.fx.user.application.`in`.UserCommandUseCase
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal")
class UserInternalApiAdapter(
    private val userCommandUseCase: UserCommandUseCase
) {

}