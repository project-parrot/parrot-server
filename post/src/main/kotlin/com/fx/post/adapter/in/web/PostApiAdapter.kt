package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.post.adapter.`in`.web.dto.post.*
import com.fx.post.application.`in`.PostCommandUseCase
import com.fx.post.application.`in`.PostQueryUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@WebInputAdapter
@RequestMapping("/api/v1/posts")
class PostApiAdapter(
    private val postCommandUseCase: PostCommandUseCase,
    private val postQueryUseCase: PostQueryUseCase,
) {
    @PostMapping
    fun createPost(
        @RequestBody @Valid postCreateRequest: PostCreateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<PostCreateResponse>> =
        Api.OK(
            PostCreateResponse(
                postCommandUseCase.createPost(
                    postCreateRequest.toCommand(authUser)
                ).id
            )
        )


    @GetMapping
    fun getFollowersPosts(@RequestParam postId: Long = Long.MAX_VALUE,
                          @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<List<PostResponse>>> =
        Api.OK(
            PostResponse.from(
                postQueryUseCase.getFollowersPosts(authUser.userId, postId)
            )
        )

    @GetMapping("/me")
    fun getMyPosts(@RequestParam postId: Long = Long.MAX_VALUE,
                 @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<List<PostResponse>>> =
        Api.OK(
            PostResponse.from(
                postQueryUseCase.getMyPosts(authUser.userId, postId)
            )
        )

    @GetMapping("/{userId}")
    fun getUserPosts(@PathVariable userId: Long,
                     @RequestParam postId: Long = Long.MAX_VALUE
    ): ResponseEntity<Api<List<PostResponse>>> =
        Api.OK(
            PostResponse.from(
                postQueryUseCase.getUserPosts(userId, postId)
            )
        )

    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody @Valid postUpdateRequest: PostUpdateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<PostUpdateResponse>> =
        Api.OK(
            PostUpdateResponse(
                postCommandUseCase.updatePost(
                    postId, postUpdateRequest.toCommand(authUser)
                ).id
            )
        )

}