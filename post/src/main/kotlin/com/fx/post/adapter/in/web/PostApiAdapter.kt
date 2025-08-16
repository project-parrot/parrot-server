package com.fx.post.adapter.`in`.web

import com.fx.global.annotation.AuthenticatedUser
import com.fx.global.annotation.hexagonal.WebInputAdapter
import com.fx.global.api.Api
import com.fx.global.resolver.AuthUser
import com.fx.post.adapter.`in`.web.dto.post.*
import com.fx.post.application.`in`.PostCommandUseCase
import com.fx.post.application.`in`.PostQueryUseCase
import com.fx.post.application.out.web.MediaWebPort
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@WebInputAdapter
@RequestMapping("/api/v1/posts")
class PostApiAdapter(
    private val postCommandUseCase: PostCommandUseCase,
    private val postQueryUseCase: PostQueryUseCase,
    private val mediaWebPort: MediaWebPort
) {
    @PostMapping
    fun createPost(
        @RequestBody @Valid postCreateRequest: PostCreateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<PostCreateResponse>> {
        val post = postCommandUseCase.createPost(postCreateRequest.toCommand(authUser))
        return Api.OK(PostCreateResponse(post.id))
    }

    @GetMapping
    fun getFollowersPosts(@RequestParam postId: Long = Long.MAX_VALUE,
                          @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<List<PostResponse>>> {
        val posts = postQueryUseCase.getFollowersPosts(authUser.userId, postId)
        return Api.OK(posts.map { PostResponse.from(it) })
    }

    @GetMapping("/me")
    fun getMyPosts(@RequestParam postId: Long = Long.MAX_VALUE,
                 @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<List<PostResponse>>> {
        val posts = postQueryUseCase.getMyPosts(authUser.userId, postId)
        return Api.OK(posts.map { PostResponse.from(it) })
    }

    @GetMapping("/{userId}")
    fun getUserPosts(@PathVariable userId: Long,
                     @RequestParam postId: Long = Long.MAX_VALUE
    ): ResponseEntity<Api<List<PostResponse>>> {
        val posts = postQueryUseCase.getUserPosts(userId, postId)
        return Api.OK(posts.map { PostResponse.from(it) })
    }


    @PutMapping("/{postId}")
    fun updatePost(
        @PathVariable postId: Long,
        @RequestBody @Valid postUpdateRequest: PostUpdateRequest,
        @AuthenticatedUser authUser: AuthUser
    ): ResponseEntity<Api<PostUpdateResponse>> {
        val post = postCommandUseCase.updatePost(postId, postUpdateRequest.toCommand(authUser))
        return Api.OK(PostUpdateResponse(post.id))
    }

}