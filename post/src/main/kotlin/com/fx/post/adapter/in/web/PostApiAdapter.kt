package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.post.adapter.`in`.web.dto.*
import com.fx.post.application.`in`.PostCommandUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.Objects
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@WebAdapter
@RequestMapping("/api/v1/post")
class PostApiAdapter(
    private val postCommandUseCase: PostCommandUseCase
) {
    @PostMapping("/posts")
    fun createPost(
        @RequestBody @Valid postCreateRequest: PostCreateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<PostCreateResponse>> {
        val post = postCommandUseCase.createPost(postCreateRequest.toCommand(authUser))
        return Api.OK(PostCreateResponse(post.id))
    }

    @PutMapping("/posts/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody @Valid postUpdateRequest: PostUpdateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<PostUpdateResponse>> {
        val post = postCommandUseCase.updatePost(postId, postUpdateRequest.toCommand(authUser))
        return Api.OK(PostUpdateResponse(post.id))
    }


    @PostMapping("/posts/{postId}/comments")
    fun createComment(
        @PathVariable postId: Long,
        @RequestBody @Valid commentCreateRequest: CommentCreateRequest,
        @AuthenticatedUser authUser: AuthUser): ResponseEntity<Api<CommentCreateResponse>> {
        val comment = postCommandUseCase.createComment(postId,commentCreateRequest.toCommand(authUser))
        return Api.OK(CommentCreateResponse(comment.id))
    }

    @PostMapping("/posts/{postId}/likes")
    fun addLike(
        @PathVariable postId: Long,
        @AuthenticatedUser authUser: AuthUser
        ): ResponseEntity<Api<Unit?>> {
        postCommandUseCase.addLike(postId, authUser.userId)
        return Api.OK(null)
    }

    @DeleteMapping("/posts/{postId}/likes")
    fun cancelLike(
        @PathVariable postId: Long,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<Unit?>> {
        postCommandUseCase.cancelLike(postId, authUser.userId)
        return Api.OK(null)
    }
}