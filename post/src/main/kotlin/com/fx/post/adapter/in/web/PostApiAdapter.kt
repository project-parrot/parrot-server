package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebAdapter
import com.fx.post.application.`in`.PostCommandUseCase
import org.springframework.web.bind.annotation.RequestMapping

@WebAdapter
@RequestMapping("/api/v1/post")
class PostApiAdapter(
    private val postCommandUseCase: PostCommandUseCase
) {
}