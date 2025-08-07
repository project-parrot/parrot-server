package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.hexagonal.WebAdapter
import com.fx.global.api.Api
import com.fx.post.adapter.`in`.web.dto.PostCreateRequest
import com.fx.post.adapter.`in`.web.dto.PostCreateResponse
import com.fx.post.application.`in`.PostCommandUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@WebAdapter
@RequestMapping("/api/v1/post")
class PostApiAdapter(
    private val postCommandUseCase: PostCommandUseCase
) {
    @PostMapping("/posts")
    fun createPost(@RequestBody @Valid postCreateRequest: PostCreateRequest): ResponseEntity<Api<PostCreateResponse>> {
        val post = postCommandUseCase.createPost(postCreateRequest.toCommand())
        return Api.OK(PostCreateResponse(post.id))
    }
}